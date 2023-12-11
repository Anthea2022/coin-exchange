package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.Address;
import camellia.service.impl.AddressService;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 墨染盛夏
 * @version 2023/12/8 0:24
 */
@Api(tags = "钱包地址")
@RestController("/wallet/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/listAll")
    @ApiOperation("获取所有地址")
    public BaseResponse<Object> listAll(Long cid) {
        Long uid = TokenUtil.getUid();
        Query query = new Query();
        query.eq("user_id", uid);
        query.eq(!ObjectUtils.isEmpty(cid), "coin_id", cid);
        return BaseResponse.success(addressService.list(query));
    }


    @PostMapping("/save")
    public BaseResponse<Object> addAddress(@RequestBody Address address) {
        if (BooleanUtils.isFalse(addressService.addAddress(address))) {
            return BaseResponse.success("添加成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "添加失败");
    }
}
