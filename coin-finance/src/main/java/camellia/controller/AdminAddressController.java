package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.AdminAddress;
import camellia.service.AdminAddressService;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 15:16
 */
@Api(value = "地址管理")
@RestController
@RequestMapping("/adminAddress")
public class AdminAddressController {
    @Autowired
    private AdminAddressService adminAddressService;

    @ApiOperation("分页列举")
    @GetMapping("/listPage")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        return BaseResponse.success(adminAddressService.page(query));
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public BaseResponse<Object> saveAddress(@RequestBody AdminAddress adminAddress) {
        if (BooleanUtils.isTrue(adminAddressService.saveAddress(adminAddress))) {
            return BaseResponse.success("保存成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "保存失败");
    }

    @ApiOperation("删除")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteAddress(@NotNull Long addressId) {
        // TODO: 2023/12/28 作为其他表的外键 提醒不能直接删除
        if (BooleanUtils.isTrue(adminAddressService.deleteAddress(addressId))) {
            return BaseResponse.success("删除成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除失败");
    }
}
