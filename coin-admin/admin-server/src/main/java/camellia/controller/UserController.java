package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.User;
import camellia.feign.MemberFeign;
import camellia.service.UserService;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2023/11/26 13:26
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MemberFeign memberFeign;

    @ApiOperation("给与用户角色")
    @PostMapping("/role/grant")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "角色主键"),
            @ApiImplicitParam(name = "uid", value = "用户主键")
    })
    @PreAuthorize("@coin.hasPermission('sys_user_role_insert')")
    public BaseResponse<Object> addUserRole(Long uid, Long rid) {
        if (BooleanUtils.isTrue(userService.addUserRole(uid, rid))) {
            return BaseResponse.success("授权成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "授权失败");
    }

    @ApiOperation("删除用户角色")
    @PostMapping("/role/grant/cancel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "user_role主键"),
    })
    @PreAuthorize("@coin.hasPermission('sys_user_role_delete')")
    public BaseResponse<Object> deleteRolePrivilege(Long id) {
        if (BooleanUtils.isTrue(userService.deleteUserRole(id))) {
            return BaseResponse.success("取消权限成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "取消权限失败");
    }

    @ApiOperation("获取用户角色")
    @GetMapping("/role/list")
    public BaseResponse<Object> listRolePrivilege(
            @NotNull Integer pageSize, @NotNull Integer pageNum, String fullname, String role
    ) {
        return BaseResponse.success(userService.listUserRole(pageSize, pageNum, fullname, role));
    }

    @ApiOperation("添加用户")
    @PostMapping("/save")
    @PreAuthorize("@coin.hasPermission('sys_user_insert')")
    public BaseResponse<Object> addUser(@RequestBody User user) {
        if (BooleanUtils.isTrue(userService.addUser(user))) {
            return BaseResponse.success("添加用户成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "添加用户失败");
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    @PreAuthorize("@coin.hasPermission('sys_user_delete')")
    public BaseResponse<Object> deleteUser(Long uid) {

        // TODO: 2023/12/22 删除用户是删除sys_user_role的记录 
        if (BooleanUtils.isTrue(userService.deleteUser(uid))) {
            return BaseResponse.success("删除用户成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除用户失败");
    }

    @ApiOperation("查看用户的基本信息")
    @GetMapping("/info/list")
    public BaseResponse<Object> listBasicInfo(@NotNull Integer pageSize, @NotNull Integer pageNum, Integer reviewStatus,
                                              String name, String realName, String phone, String email) {
        return memberFeign.listBasicInfo(pageSize, pageNum, reviewStatus, name, realName, phone, email);
    }

    @ApiOperation("查看用户的高级认证")
    @GetMapping("/senior/auth/get")
    public BaseResponse<Object> getSeniorAuth(@NotNull Long uid, @NotNull Byte reviewStatus) {
        return memberFeign.getInfoDetail(uid, reviewStatus);
    }

    @ApiOperation("审核用户高级认证")
    @PostMapping("/senior/auth/check")
    public BaseResponse<Object> checkUserSeniorAuth(@NotNull Long authCode, @NotNull Byte status, String remark) {
        return memberFeign.updateReviewStatus(authCode, status, remark);
    }

    @ApiOperation("设置用户状态")
    @PostMapping("/status/set")
    public BaseResponse<Object> setUserStatus(@NotNull Long uid, @NotNull Byte status) {
        return memberFeign.setStatus(uid, status);
    }
}
