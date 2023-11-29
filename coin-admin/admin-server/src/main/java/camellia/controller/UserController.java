package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.User;
import camellia.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        if (BooleanUtils.isTrue(userService.deleteUser(uid))) {
            return BaseResponse.success("删除用户成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除用户失败");
    }
}
