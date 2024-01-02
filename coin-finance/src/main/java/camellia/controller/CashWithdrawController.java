package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.*;
import camellia.domain.param.CashSellParam;
import camellia.domain.param.TradeParam;
import camellia.feign.MemberServiceFeign;
import camellia.service.CashWithdrawAuditService;
import camellia.service.CashWithdrawService;
import camellia.util.FileUtil;
import camellia.util.TokenUtil;
import camellia.util.TradeUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 20:10
 */
@Api(value = "现金提现")
@RestController
@RequestMapping("/cash/withdraw")
public class CashWithdrawController {
    @Autowired
    private CashWithdrawService cashWithdrawService;

    @Autowired
    private CashWithdrawAuditService cashWithdrawAuditService;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @ApiOperation("列举记录")
    @GetMapping("/record/listPage")
    @PreAuthorize("@coin.hasPermission('cash_withdraw_record_list')")
    public BaseResponse<Object> listPageRecord(@NotNull Integer pageSize, @NotNull Integer pageNum, String realName, Long coinId, Byte status,
                                               Long coinTypeId, BigDecimal maxNum, BigDecimal minNum, Date startTime, Date endTime) {
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
        return BaseResponse.success(cashWithdrawService.page(query));
    }

    @ApiOperation("导出全部")
    @PostMapping("/export")
    @PreAuthorize("@coin.hasPermission('cash_withdraw_record_export')")
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
        PageInfo<CashWithdraw> cashWithDrawPageInfo = cashWithdrawService.page(query);
        List<CashWithdraw> cashWithdrawList = cashWithDrawPageInfo.getList();
        if (!CollectionUtils.isEmpty(cashWithdrawList)) {
            FileUtil.setExcelResponse(response, "用户现金提现记录");
            try {
                EasyExcel.write(response.getOutputStream())
                        .head(CoinWithdraw.class)
                        .excelType(ExcelTypeEnum.XLSX)
                        .sheet("用户现金提现记录")
                        .doWrite(cashWithdrawList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @ApiOperation("提现")
    @PostMapping("/withdraw")
    public BaseResponse<Object> withdraw(@RequestBody CashSellParam cashSellParam) {
        TradeUtil.sellCheck(cashSellParam);
        return BaseResponse.success(cashWithdrawService.withdraw(cashSellParam));
    }

    @ApiOperation("审核")
    @PostMapping("/check")
    @PreAuthorize("@coin.hasPermission('cash_withdraw_check')")
    public BaseResponse<Object> check(@RequestBody CashWithdrawAudit cashWithdrawAudit) {
        Long uid = TokenUtil.getUid();
        cashWithdrawAudit.setAuditUid(uid);
        UserInfo userInfo = (UserInfo) memberServiceFeign.getBasicInfo().getData();
        cashWithdrawAudit.setAuditUsername(userInfo.getUsername());
        if (BooleanUtils.isTrue(cashWithdrawAuditService.saveAudit(cashWithdrawAudit))) {
            return BaseResponse.success("提现成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "提现失败");
    }

    @ApiOperation("当前用户的提现记录")
    @GetMapping("/user/record")
    public BaseResponse<Object> userRecord(@NotNull Integer pageSize, @NotNull Integer pageNum, Long coinTypeId, Byte status,
                                           Long coinId, BigDecimal maxNum, BigDecimal minNum, Date startTime, Date endTime) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.eq("user_id", TokenUtil.getUid());
        query.eq(!ObjectUtils.isEmpty(status), "status", status);
        query.eq(!ObjectUtils.isEmpty(coinTypeId), "coin_type_id", coinTypeId);
        query.eq(!ObjectUtils.isEmpty(coinId), "coin_id", coinId);
        query.ge(!ObjectUtils.isEmpty(minNum), "num", minNum);
        query.le(!ObjectUtils.isEmpty(maxNum), "num", maxNum);
        query.ge(!ObjectUtils.isEmpty(startTime), "created", startTime);
        query.le(!ObjectUtils.isEmpty(endTime), "created", endTime);
        return BaseResponse.success(cashWithdrawService.page(query));
    }
}
