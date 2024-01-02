package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.CashRecharge;
import camellia.domain.CashRechargeAudit;
import camellia.domain.CoinRecharge;
import camellia.domain.UserInfo;
import camellia.domain.param.TradeParam;
import camellia.feign.MemberServiceFeign;
import camellia.service.CashRechargeAuditService;
import camellia.service.CashRechargeService;
import camellia.util.FileUtil;
import camellia.util.TokenUtil;
import camellia.util.TradeUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
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
 * @version 2023/12/28 15:40
 */
@ApiOperation("现金充值")
@RestController
@RequestMapping("/cash/recharge")
public class CashRechargeController {
    @Autowired
    private CashRechargeService cashRechargeService;

    @Autowired
    private CashRechargeAuditService cashRechargeAuditService;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @ApiOperation("分页列举充值记录")
    @GetMapping("/record/listPage")
    @PreAuthorize("@coin.hasPermission('cash_recharge_record_list')")
    public BaseResponse<Object> listPageRecord(@NotNull Integer pageSize, @NotNull Integer pageNum, String realname, Long coinTypeId, Byte status,
                                               Long coinId, BigDecimal maxNum, BigDecimal minNum, Date startTime, Date endTime) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        if (StringUtils.hasText(realname)) {
            Long uid = (Long) memberServiceFeign.getUidByRealName(realname).getData();
            query.eq("user_id", uid);
        }
        query.eq(!ObjectUtils.isEmpty(status), "status", status);
        query.eq(!ObjectUtils.isEmpty(coinTypeId), "coin_type_id", coinTypeId);
        query.eq(!ObjectUtils.isEmpty(coinId), "coin_id", coinId);
        query.ge(!ObjectUtils.isEmpty(minNum), "num", minNum);
        query.le(!ObjectUtils.isEmpty(maxNum), "num", maxNum);
        query.ge(!ObjectUtils.isEmpty(startTime), "created", startTime);
        query.le(!ObjectUtils.isEmpty(endTime), "created", endTime);
        return BaseResponse.success(cashRechargeService.page(query));
    }

    @ApiOperation("导出全部")
    @PostMapping("/export")
    @PreAuthorize("@coin.hasPermission('cash_recharge_record_export')")
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
        PageInfo<CashRecharge> cashRechargePageInfo = cashRechargeService.page(query);
        List<CashRecharge> cashRechargeList = cashRechargePageInfo.getList();
        if (!CollectionUtils.isEmpty(cashRechargeList)) {
            FileUtil.setExcelResponse(response, "用户现金充值记录");
            try {
                EasyExcel.write(response.getOutputStream())
                        .head(CoinRecharge.class)
                        .excelType(ExcelTypeEnum.XLSX)
                        .sheet("用户现金充值记录")
                        .doWrite(cashRechargeList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @ApiOperation("充值")
    @PostMapping("/save")
    public BaseResponse<Object> recharge(@RequestBody TradeParam tradeParam) {
        TradeUtil.check(tradeParam);
        return BaseResponse.success(cashRechargeService.recharge(tradeParam));
    }

    @ApiOperation("审核")
    @PostMapping("/check")
    @PreAuthorize("@coin.hasPermission('cash_recharge_check')")
    public BaseResponse<Object> check(@RequestBody CashRechargeAudit cashRechargeAudit) {
        Long uid = TokenUtil.getUid();
        cashRechargeAudit.setAuditUid(uid);
        UserInfo userInfo = (UserInfo) memberServiceFeign.getBasicInfo().getData();
        cashRechargeAudit.setAuditUsername(userInfo.getUsername());
        if (BooleanUtils.isTrue(cashRechargeAuditService.saveAudit(cashRechargeAudit))) {
            return BaseResponse.success("审核成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "审核失败");
    }

    @ApiOperation("当前用户的充值记录")
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
        return BaseResponse.success(cashRechargeService.page(query));
    }
}
