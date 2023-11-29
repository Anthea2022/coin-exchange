package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.Privilege;
import camellia.service.PrivilegeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2023/11/21 0:10
 */
@RequestMapping("/privilege")
@RestController
public class PrivilegeController {
    @Autowired
    private PrivilegeService privilegeService;

    @ApiOperation("获取所有权限")
    @GetMapping("/list")
    public BaseResponse<Object> list() {
        return BaseResponse.success(privilegeService.list());
    }

    @ApiOperation("添加权限")
    @PostMapping("/add")
    @PreAuthorize("@coin.hasPermission('sys_privilege_insert')")
    public BaseResponse<Object> addPrivilege(@NotBlank String name) {
        Privilege p = new Privilege();
        p.setName(name);
        if (privilegeService.addPrivilege(p)) {
            return BaseResponse.success("添加权限成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL);
    }

    @ApiOperation("删除权限")
    @PostMapping("/delete")
    @PreAuthorize("@coin.hasPermission('sys_privilege_delete')")
    public BaseResponse<Object> deletePrivilege(@NotNull Long pid) {
        if (privilegeService.deletePrivilege(pid)) {
            return BaseResponse.success("删除权限成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "请先取消拥有该权限的角色");
    }
}
