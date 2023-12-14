package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.config.IdentifyAuthConfiguration;
import camellia.domain.UserAuthAuditRecord;
import camellia.domain.UserAuthInfo;
import camellia.domain.UserInfo;
import camellia.domain.vo.UserAuthInfoVo;
import camellia.service.impl.UserAuthAuditRecordService;
import camellia.service.impl.UserAuthInfoService;
import camellia.service.impl.UserInfoService;
import camellia.util.ImgUtil;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static camellia.constant.DBConstant.AUTH_BASIC_INFO;
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

    @ApiOperation("获取自己基本信息")
    @PostMapping("/info/get")
    public BaseResponse<Object> getInfo() {
        Long uid = TokenUtil.getUid();
        if (ObjectUtils.isEmpty(uid)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "无该用户信息，请尝试重新登录");
        }
        return BaseResponse.success(userInfoService.getById(uid));
    }

    @ApiOperation("修改个人基础信息")
    @PostMapping("/info/modify")
    public BaseResponse<Object> modifyMyInfo(@RequestBody UserInfo userInfo) {
        Long uid = TokenUtil.getUid();
        if (ObjectUtils.isEmpty(uid)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "无该用户信息，请尝试重新登录");
        }
        userInfo.setId(TokenUtil.getUid());
        userInfo.setUpdateTime(new Date());
        if (BooleanUtils.isTrue(userInfoService.updateInfo(userInfo))) {
            return BaseResponse.success("修改信息成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "修改信息失败");
    }

    @ApiOperation("设置自己账号状态")
    @PostMapping("/status/set")
    public BaseResponse<Object> setStatus(@NotNull Byte status) {
        UserInfo userInfo = new UserInfo();
        Long uid = TokenUtil.getUid();
        if (ObjectUtils.isEmpty(uid)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "无该用户信息，请尝试重新登录");
        }
        userInfo.setId(uid);
        userInfo.setStatus(status);
        userInfo.setUpdateTime(new Date());
        if (BooleanUtils.isTrue(userInfoService.updateInfo(userInfo))) {
            return BaseResponse.success("修改状态成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "修改状态失败");
    }

    @ApiOperation("身份认证")
    @PostMapping("/identify/check")
    public BaseResponse<Object> checkIdentify(@NotBlank String realName, @NotNull String idCard) {
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

    /**
     * 根据审核状态查看认证信息
     * @param uid
     * @param reviewStatus 0待审核1同意2拒绝
     * @return
     */
    @ApiOperation("根据用户id查看认证信息")
    @GetMapping("/user/detail")
    public BaseResponse<Object> getInfoDetail(@NotNull Long uid, @NotNull Byte reviewStatus) {
        UserAuthInfoVo userAuthInfoVo = null;
        List<UserAuthInfo> userAuthInfos = new ArrayList<>();
        List<UserAuthAuditRecord> userAuthAuditRecords = new ArrayList<>();
        UserInfo userInfo = userInfoService.getBySpecifiedColumns(USER_INFO, new Query().eq("id", uid));

        // 如果不存在该用户
        if (ObjectUtils.isEmpty(userInfo)) {
            return BaseResponse.fail(ResponseCodes.FAIL, "不存在该用户");
        }
        if (reviewStatus.equals(0)) {
            List<UserAuthInfo> userAuthInfoAll = userAuthInfoService.listBySpecifiedColumns(AUTH_BASIC_INFO, new Query().eq("user_id", uid));
            for (UserAuthInfo userAuthInfo : userAuthInfoAll) {
                // 是否有审核记录
                UserAuthAuditRecord userAuthAuditRecord = userAuthAuditRecordService.getByQuery(new Query().eq("auth_code", userAuthInfo.getAuthCode()));
                if (ObjectUtils.isEmpty(userAuthAuditRecord)) {
                    userAuthInfos.add(userAuthInfo);
                }
            }
            userAuthInfoVo = new UserAuthInfoVo(userInfo, userAuthInfos, userAuthAuditRecords);
            return BaseResponse.success(userAuthInfoVo);
        }
        userAuthAuditRecords = userAuthAuditRecordService.list(new Query().eq("status", reviewStatus));
//        for (UserAuthAuditRecord userAuthAuditRecord : userAuthAuditRecords) {
//            UserAuthInfo userAuthInfo = userAuthInfoService.getByQuery(new Query().eq("auth_code", userAuthAuditRecord.getAuthCode()));
//            userAuthInfos.add(userAuthInfo);
//        }
        Long authCode = userAuthInfos.get(0).getAuthCode();
        userAuthInfos = userAuthInfoService.listBySpecifiedColumns(AUTH_BASIC_INFO, new Query().eq("auth_code", authCode));
        userAuthInfoVo = new UserAuthInfoVo(userInfo, userAuthInfos, userAuthAuditRecords);
        return BaseResponse.success(userAuthInfoVo);
    }

    @ApiOperation("审核")
    @PostMapping("/auth/check")
    public BaseResponse<Object> updateReviewStatus(@NotNull Long authCode, @NotNull Byte status, String remark) {
        UserAuthAuditRecord userAuthAuditRecord = new UserAuthAuditRecord();
        userAuthAuditRecord.setAuthCode(authCode);
        userAuthAuditRecord.setAuditUserId(TokenUtil.getUid());
        userAuthAuditRecord.setStatus(status);
        // 如果拒绝
        if (status.equals(2)) {
            if (StringUtils.isEmpty(remark)) {
                return BaseResponse.fail(ResponseCodes.FAIL, "审核不通过请说明理由");
            }
            userAuthAuditRecord.setRemark(remark);
        }
        userAuthAuditRecord.setCreateTime(new Date());
        if (BooleanUtils.isTrue(userAuthAuditRecordService.checkAuth(userAuthAuditRecord))) {
            return BaseResponse.success("审核成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "审核失败");
    }

    @ApiOperation("提交资料待审核")
    @PostMapping("/auth/save")
    public BaseResponse<Object> addAuth(MultipartFile file, Integer serialno) {
        UserAuthInfo userAuthInfo = new UserAuthInfo();
        userAuthInfo.setImageUrl(ImgUtil.storeImg(file));
        Date date = new Date();
        userAuthInfo.setCreateTime(date);
        userAuthInfo.setSerialno(serialno);
        Long uid = TokenUtil.getUid();
        userAuthInfo.setUserId(uid);
        // todo auth_code创建
        userAuthInfoService.saveIgnoreNull(userAuthInfo);
        return BaseResponse.success("提交成功");
    }

    @ApiOperation("修改支付密码")
    @PostMapping("/pay_password/set")
    public BaseResponse<Object> setPayPsw(@NotBlank String newPayPsw, @NotNull String verifyCode){
        return BaseResponse.fail(ResponseCodes.FAIL, "修改失败");
    }
}
