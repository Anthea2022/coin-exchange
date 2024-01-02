package camellia.controller;

import camellia.common.BaseResponse;
import camellia.domain.CoinRecharge;
import camellia.domain.CoinWithdraw;
import camellia.feign.MemberServiceFeign;
import camellia.service.CoinWithdrawService;
import camellia.util.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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
 * @version 2023/12/29 10:57
 */
@Api("现金提现")
@RestController
@RequestMapping("/coin/withdraw")
public class CoinWithdrawController {
    @Autowired
    private CoinWithdrawService coinWithdrawService;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @ApiOperation("分页列举")
    @GetMapping("/listPage")
    public BaseResponse<Object> listPage(@NotNull Integer pageSize, @NotNull Integer pageNum, String realName, Long coinId, Long coinTypeId,
                                         BigDecimal maxNum, BigDecimal minNum, Date startTime, Date endTime) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        if (StringUtils.hasText(realName)) {
            Long uid = (Long) memberServiceFeign.getUidByRealName(realName).getData();
            query.eq("user_id", uid);
        }
        query.eq(!ObjectUtils.isEmpty(coinId), "coin_id", coinId);
        query.eq(!ObjectUtils.isEmpty(coinTypeId), "coin_type_id", coinTypeId);
        query.ge(!ObjectUtils.isEmpty(minNum), "num", minNum);
        query.le(!ObjectUtils.isEmpty(maxNum), "num", maxNum);
        query.ge(!ObjectUtils.isEmpty(startTime), "created", startTime);
        query.le(!ObjectUtils.isEmpty(endTime), "created", endTime);
        return BaseResponse.success(coinWithdrawService.page(query));
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
        query.eq(!org.apache.commons.lang3.ObjectUtils.isEmpty(coinTypeId), "coin_type_id", coinTypeId);
        query.eq(!org.apache.commons.lang3.ObjectUtils.isEmpty(coinId), "coin_id", coinId);
        query.ge(!org.apache.commons.lang3.ObjectUtils.isEmpty(minNum), "num", minNum);
        query.le(!org.apache.commons.lang3.ObjectUtils.isEmpty(maxNum), "num", maxNum);
        query.ge(!org.apache.commons.lang3.ObjectUtils.isEmpty(startTime), "created", startTime);
        query.le(!org.apache.commons.lang3.ObjectUtils.isEmpty(endTime), "created", endTime);
        PageInfo<CoinWithdraw> coinWithdrawPageInfo = coinWithdrawService.page(query);
        List<CoinWithdraw> coinWithdrawList = coinWithdrawPageInfo.getList();
        if (!CollectionUtils.isEmpty(coinWithdrawList)) {
            FileUtil.setExcelResponse(response, "用户币种提现记录");
            try {
                EasyExcel.write(response.getOutputStream())
                        .head(CoinRecharge.class)
                        .excelType(ExcelTypeEnum.XLSX)
                        .sheet("用户币种提现记录")
                        .doWrite(coinWithdrawList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
