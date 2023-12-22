package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.config.IdentifyAuthConfiguration;
import camellia.domain.SeniorAuth;
import camellia.domain.UserAuthAuditRecord;
import camellia.domain.UserAuthInfo;
import camellia.domain.UserInfo;
import camellia.domain.vo.SeniorAuthImgVo;
import camellia.domain.vo.UserAuthInfoVo;
import camellia.service.impl.SmsService;
import camellia.service.impl.UserAuthAuditRecordService;
import camellia.service.impl.UserAuthInfoService;
import camellia.service.impl.UserInfoService;
import camellia.util.TokenUtil;
import cn.hutool.core.lang.Snowflake;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static camellia.constant.DBConstant.SENIOR_AUTH_IMG;
import static camellia.constant.DBConstant.USER_INFO;

/**
 * @author 墨染盛夏
 * @version 2023/11/30 20:25
 */
@Api(tags = "会员")
@RestController
public class MemberController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAuthInfoService userAuthInfoService;

    @Autowired
    private UserAuthAuditRecordService userAuthAuditRecordService;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private SmsService smsService;

    // 用户模块

    /**
     * 身份认证获取
     * 高级认证获取
     * 基本信息获取
     * 支付密码获取
     * @return
     */
    @ApiOperation("获取自己基本信息")
    @GetMapping("/info/get")
    public BaseResponse<Object> getBasicInfo() {
        return BaseResponse.success(userInfoService.getInfo(Arrays.asList("id","username", "email", "phone")));
    }

    @ApiOperation("获取自己身份认证信息")
    @GetMapping("/identify/auth/info/get")
    public BaseResponse<Object> getIdentifyAuthInfo() {
        return BaseResponse.success(userInfoService.getInfo(Arrays.asList("id", "id_card", "real_name", "auth_status")));
    }

    @ApiOperation("获取自己高级认证信息")
    @GetMapping("/senior/auth/info/get")
    public BaseResponse<Object> getSeniorAuthInfo() {
        Long uid = TokenUtil.getUid();

        List<SeniorAuthImgVo> seniorAuthImgVos = userAuthInfoService.listBySpecifiedColumns(SENIOR_AUTH_IMG,
                new Query().eq("auth_code", userAuthInfoService.getLatestAuthCode(uid)), SeniorAuthImgVo.class);
        SeniorAuth seniorAuth = new SeniorAuth(uid, seniorAuthImgVos);
        return BaseResponse.success(seniorAuth);
    }

    @ApiOperation("修改个人基础信息")
    @PostMapping("/info/modify")
    public BaseResponse<Object> modifyMyInfo(@RequestBody UserInfo userInfo) {
        Long uid = TokenUtil.getUid();
        userInfo.setId(uid);
        userInfo.setUpdateTime(new Date());
        if (BooleanUtils.isTrue(userInfoService.updateInfo(userInfo))) {
            return BaseResponse.success("修改信息成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "修改信息失败");
    }

    @ApiOperation("修改电话号码")
    @PostMapping("/phone/update")
    public BaseResponse<Object> updatePhone(String oldCode, String phone, String code) {
        if (BooleanUtils.isFalse(smsService.checkOldCode(oldCode))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "原始电话号码验证码错误");
        }
        if (BooleanUtils.isTrue(smsService.checkCode(phone, code))) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(TokenUtil.getUid());
            userInfo.setPhone(phone);
            userInfoService.updateIgnoreNull(userInfo);
            return BaseResponse.success("修改成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "验证码错误");
    }

    @ApiOperation("通过旧密码修改登录密码")
    @PostMapping("/password/update_by_old_psw")
    public BaseResponse<Object> updatePswByOldPsw(@NotBlank String oldPsw, @NotBlank String newPsw) {
        Long uid = TokenUtil.getUid();
        if (BooleanUtils.isFalse(userInfoService.matchPsw(oldPsw))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "原始密码错误");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(uid);
        userInfo.setPassword(new BCryptPasswordEncoder().encode(newPsw));
        if (BooleanUtils.isTrue(userInfoService.updatePsw(userInfo))) {
            return BaseResponse.success("修改密码成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "修改密码失败");
    }

    @ApiOperation("通过电话验证码修改登录密码")
    @PostMapping("/password/update_by_phone")
    public BaseResponse<Object> updatePswByPhone(@NotBlank String newPsw, @NotBlank String code) {
        if (BooleanUtils.isFalse(smsService.checkOldCode(code))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "验证码错误");
        }
        Long uid = TokenUtil.getUid();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(uid);
        userInfo.setPassword(new BCryptPasswordEncoder().encode(newPsw));
        if (BooleanUtils.isTrue(userInfoService.updatePsw(userInfo))) {
            return BaseResponse.success("修改密码成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "修改密码失败");
    }

    @ApiOperation("修改支付密码")
    @PostMapping("/pay_password/set")
    public BaseResponse<Object> setPayPsw(@NotBlank String newPayPsw, @NotBlank String verifyCode){
        if (BooleanUtils.isTrue(userInfoService.updatePayPsw(newPayPsw, verifyCode))) {
            return BaseResponse.success("修改成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "修改失败");
    }

    @ApiOperation("身份认证")
    @PostMapping("/identify/auth")
    public BaseResponse<Object> checkIdentify(@NotBlank String realName, @NotNull String idCard) {
        Long uid = TokenUtil.getUid();
        if (!userInfoService.getColumnValue("auth_status", new Query().eq("id", uid), Integer.class).equals(0)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "用户已经认证成功了");
        }
        if (BooleanUtils.isTrue(IdentifyAuthConfiguration.authCheck(realName, idCard))) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(TokenUtil.getUid());
            userInfo.setRealName(realName);
            userInfo.setIdCard(idCard);
            userInfo.setUpdateTime(new Date());
            userInfoService.updateIgnoreNull(userInfo);
            return BaseResponse.success("身份认证成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "身份认证失败");
    }

    @ApiOperation("高级认证")
    @PostMapping("/senior/auth")
    public BaseResponse<Object> seniorAuth(MultipartFile image1, MultipartFile image2, MultipartFile image3) {
        Long uid = TokenUtil.getUid();
        long authCode = snowflake.nextId();
        if (BooleanUtils.isTrue(userAuthInfoService.saveSeniorAuth(image1, uid, 1, authCode)
        && BooleanUtils.isTrue(userAuthInfoService.saveSeniorAuth(image2, uid, 2, authCode))
        && BooleanUtils.isTrue(userAuthInfoService.saveSeniorAuth(image3, uid, 3, authCode)))) {
            return BaseResponse.success("认证成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "认证失败");
    }

    // 用户和管理员公共模块
    @ApiOperation("查看用户的邀请列表")
    @GetMapping("/invite/list")
    public BaseResponse<Object> listDirectInvitePage(@NotNull Integer pageNum, @NotNull Integer pageSize,
                                                     String username, String email, String phone) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.eq("direct_invite_id", TokenUtil.getUid());
        query.like(StringUtils.hasText(username), "username", username);
        query.like(StringUtils.hasText(email), "email", email);
        query.like(StringUtils.hasText(phone), "phone", phone);
        return BaseResponse.success(userInfoService.pageBySpecifiedColumns(USER_INFO, query, UserInfo.class));
    }

    // 管理员模块

    /**
     * 根据审核状态查看认证信息
     * @param uid
     * @param reviewStatus 0待审核1同意2拒绝
     * @return
     */
    @ApiOperation("根据用户id查看高级认证信息")
    @GetMapping("/senior/auth/detail")
    public BaseResponse<Object> getInfoDetail(@NotNull Long uid, @NotNull Byte reviewStatus) {
        UserAuthInfoVo userAuthInfoVo = null;
        List<SeniorAuthImgVo> seniorAuthImgVos = null;
        UserAuthAuditRecord userAuthAuditRecord = null;
        UserInfo userInfo = userInfoService.getBySpecifiedColumns(USER_INFO, new Query().eq("id", uid));

        // 如果不存在该用户
        if (ObjectUtils.isEmpty(userInfo)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "不存在该用户");
        }
        seniorAuthImgVos = userAuthInfoService.getLatestSeniorAuthImg(uid);
        if (reviewStatus.equals(0)) {
            // 待审核
             userAuthInfoVo = new UserAuthInfoVo(userInfo, seniorAuthImgVos, userAuthAuditRecord);
             return BaseResponse.success(userAuthInfoVo);
        }

        // 对应的审核记录
        userAuthAuditRecord = userAuthAuditRecordService.getByQuery(new Query().eq("auth_code", userAuthInfoService.getLatestAuthCode(uid)));
        userAuthInfoVo = new UserAuthInfoVo(userInfo, seniorAuthImgVos, userAuthAuditRecord);

        return BaseResponse.success(userAuthInfoVo);
    }

    /**
     * 分页查看用户基本信息
     * @param pageSize
     * @param pageNum
     * @param reviewStatus
     * @param name
     * @param realName
     * @param phone
     * @param email
     * @return
     */
    @ApiOperation("用户信息管理")
    @GetMapping("/info/list")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum, Integer reviewStatus,
                                         String name, String realName, String phone, String email) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.like(StringUtils.hasText(name), "username", name);
        query.like(StringUtils.hasText(phone), "phone", phone);
        query.like(StringUtils.hasText(email), "email", email);
        query.like(StringUtils.hasText(realName), "real_name", realName);
        query.eq(!ObjectUtils.isEmpty(reviewStatus), "review_status", reviewStatus);
        return BaseResponse.success(userInfoService.pageBySpecifiedColumns(USER_INFO, query, UserInfo.class));
    }

    @ApiOperation("审核")
    @PostMapping("/senior/auth/check")
    public BaseResponse<Object> updateReviewStatus(@NotNull Long authCode, @NotNull Byte status, String remark) {
        UserAuthAuditRecord userAuthAuditRecord = new UserAuthAuditRecord();
        userAuthAuditRecord.setAuthCode(authCode);
        userAuthAuditRecord.setAuditUserId(TokenUtil.getUid());
        userAuthAuditRecord.setStatus(status);
        // 如果拒绝
        if (status.equals(2)) {
            if (StringUtils.isEmpty(remark)) {
                return BaseResponse.fail(ResponseCodes.FAIL, "审核不通过,请说明理由");
            }
            userAuthAuditRecord.setRemark(remark);
        }
        userAuthAuditRecord.setCreateTime(new Date());
        if (BooleanUtils.isTrue(userAuthAuditRecordService.checkAuth(userAuthAuditRecord))) {
            return BaseResponse.success("审核成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "审核失败");
    }

    @ApiOperation("设置用户账号状态")
    @PostMapping("/status/set")
    public BaseResponse<Object> setStatus(@NotNull Long uid, @NotNull Byte status) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(uid);
        userInfo.setStatus(status);
        userInfo.setUpdateTime(new Date());
        if (BooleanUtils.isTrue(userInfoService.updateInfo(userInfo))) {
            return BaseResponse.success("修改状态成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "修改状态失败");
    }
}
