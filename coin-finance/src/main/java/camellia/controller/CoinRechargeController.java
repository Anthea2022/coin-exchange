package camellia.controller;

import camellia.common.BaseResponse;
import camellia.domain.CashRecharge;
import camellia.domain.CoinRecharge;
import camellia.feign.MemberServiceFeign;
import camellia.service.CoinRechargeService;
import camellia.util.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 21:16
 */
@Api(value = "币种充值")
@RestController
@RequestMapping("/coin/recharge")
public class CoinRechargeController {
    @Autowired
    private CoinRechargeService coinRechargeService;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @ApiOperation("列举分页")
    @GetMapping("/listPage")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum, String realName, Long coinId, Long coinTypeId,
                                         Byte status, BigDecimal maxNum, BigDecimal minNum, Date startTime, Date endTime) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        if (StringUtils.hasText(realName)) {
            Long uid = (Long) memberServiceFeign.getUidByRealName(realName).getData();
            query.eq("user_id", uid);
        }
        query.eq(!ObjectUtils.isEmpty(coinTypeId), "coin_type_id", coinTypeId);
        query.eq(!ObjectUtils.isEmpty(coinId), "coin_id", coinId);
        query.eq(!ObjectUtils.isEmpty(status), "status", status);
        query.ge(!ObjectUtils.isEmpty(minNum), "num", minNum);
        query.le(!ObjectUtils.isEmpty(maxNum), "num", maxNum);
        query.ge(!ObjectUtils.isEmpty(startTime), "created", startTime);
        query.le(!ObjectUtils.isEmpty(endTime), "created", endTime);
        return BaseResponse.success(coinRechargeService.page(query));
    }

    @ApiOperation("导出全部")
    @PostMapping("/export")
    public void export(String realname, Long coinTypeId, Long coinId, BigDecimal maxNum, BigDecimal minNum,
                       Date startTime, Date endTime, HttpServletResponse response) {
        Query query = new Query();
        query.page(1, 10000);
        if (StringUtils.hasText(realname)) {
            Long uid = (Long) memberServiceFeign.getUidByRealName(realname).getData();
            query.eq("user_id", uid);
        }
        query.eq(!ObjectUtils.isEmpty(coinTypeId), "coin_type_id", coinTypeId);
        query.eq(!ObjectUtils.isEmpty(coinId), "coin_id", coinId);
        query.ge(!ObjectUtils.isEmpty(minNum), "num", minNum);
        query.le(!ObjectUtils.isEmpty(maxNum), "num", maxNum);
        query.ge(!ObjectUtils.isEmpty(startTime), "created", startTime);
        query.le(!ObjectUtils.isEmpty(endTime), "created", endTime);
        PageInfo<CoinRecharge> coinRechargePageInfo = coinRechargeService.page(query);
        List<CoinRecharge> coinRechargeList = coinRechargePageInfo.getList();
        if (!CollectionUtils.isEmpty(coinRechargeList)) {
            FileUtil.setExcelResponse(response, "用户币种充值记录");
            try {
                EasyExcel.write(response.getOutputStream())
                        .head(CoinRecharge.class)
                        .excelType(ExcelTypeEnum.XLSX)
                        .sheet("用户币种充值记录")
                        .doWrite(coinRechargeList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
