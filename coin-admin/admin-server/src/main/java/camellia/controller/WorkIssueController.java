package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.WorkIssue;
import camellia.service.WorkIssueService;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 * @author 墨染盛夏
 * @version 2023/11/28 21:00
 */
@Api(tags = "工单")
@RestController
@RequestMapping("/work/issue")
public class WorkIssueController {
    @Autowired
    private WorkIssueService workIssueService;

    @ApiOperation("获取工单")
    @GetMapping("/list")
    public BaseResponse<Object> listPage(@NotNull Integer pageNum, @NotNull Integer pageSize,
                                         String question, Date startTime, Date endTime) {
        Query query = new Query();
        query.page(pageNum, pageSize);
        query.ge(!ObjectUtils.isEmpty(startTime), "last_update_time", startTime);
        query.le(!ObjectUtils.isEmpty(endTime), "last_update_time", endTime);
        query.eq("status", 1);
        return BaseResponse.success(workIssueService.listPage(query));
    }

    @ApiOperation("添加问题")
    @PostMapping("/question/save")
    public BaseResponse<Object> addQuestion(@RequestBody WorkIssue workIssue) {
        if (BooleanUtils.isTrue(workIssueService.addQuestion(workIssue))) {
            return BaseResponse.success("添加成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "添加失败");
    }

    @ApiOperation("回复问题")
    @PostMapping("/question/reply")
    public BaseResponse<Object> replyQuestion(@RequestBody WorkIssue workIssue) {
        if (BooleanUtils.isTrue(workIssueService.replyQuestion(workIssue))) {
            return BaseResponse.success("发布成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "发布失败");
    }
}
