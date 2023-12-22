package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.Role;
import camellia.service.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 墨染盛夏
 * @version 2023/11/21 0:31
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation("给与角色权限")
    @PostMapping("/privilege/grant")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "角色主键"),
            @ApiImplicitParam(name = "pid", value = "权限主键")
    })
    @PreAuthorize("coin.hasPermission('sys_role_privilege_insert')")
    public BaseResponse<Object> addRolePrivilege(Long rid, Long pid) {
        if (BooleanUtils.isFalse(roleService.grantPrivilege(rid, pid))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "授权失败");
        }
        return BaseResponse.success("授权成功");
    }

    @ApiOperation("删除角色权限")
    @PostMapping("/privilege/grant/cancel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "role_privilege主键"),
    })
    @PreAuthorize("@coin.hasPermission('sys_role_privilege_delete')")
    public BaseResponse<Object> deleteRolePrivilege(Long id) {
        if (BooleanUtils.isFalse(roleService.cancelPrivilege(id))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "取消权限失败");
        }
        return BaseResponse.success("取消权限成功");
    }

    @ApiOperation("获取角色权限")
    @GetMapping("/privilege/list")
    public BaseResponse<Object> listRolePrivilege() {
        return BaseResponse.success(roleService.listRolePrivilege());
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    @PreAuthorize("@coin.hasPermission('sys_role_delete')")
    public BaseResponse<Object> deleteRole(@RequestParam("rid") Long rid) {
        Role role = new Role();
        role.setId(rid);
        role.setStatus(0);
        roleService.deleteRole(role);
        // TODO: 2023/12/22 删除角色时删除角色拥有的权限 
        return BaseResponse.success();
    }

    @ApiOperation("添加角色")
    @PostMapping("/add")
    @PreAuthorize("@coin.hasPermission('sys_role_insert')")
    public BaseResponse<Object> saveRole(@RequestBody Role role) {
        roleService.saveIgnoreNull(role);
        return BaseResponse.success();
    }
}
