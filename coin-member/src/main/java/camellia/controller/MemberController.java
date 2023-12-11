package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.UserInfo;
import camellia.service.impl.UserInfoService;
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

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static camellia.constant.DBConstant.USER_DETAIL;
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

    @ApiOperation("根据用户id查看认证信息")
    @GetMapping("/user/detail")
    public BaseResponse<Object> getInfoDetail(@NotNull Long uid) {
        return BaseResponse.success(userInfoService.getUserAuthInfo(uid));
    }
}
