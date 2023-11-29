package camellia.controller;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import camellia.domain.Notice;
import camellia.service.NoticeService;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/11/28 0:05
 */
@Api(tags = "通知公告")
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @ApiOperation("发布通知公告")
//    @PreAuthorize("@coin.hasPermission('notice_insert')")
    @PostMapping("/save")
    public BaseResponse<Object> addNotice(@RequestBody Notice notice) {
        if (BooleanUtils.isTrue(noticeService.isRepeated(notice))) {
            return BaseResponse.fail(ResponseCodes.FAIL, "存在重复标题和正文，请修改后提交");
        }
        if (BooleanUtils.isTrue(noticeService.addNotice(notice))) {
            return BaseResponse.success("发布成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "发布失败");
    }

    @ApiOperation("启用/禁用通知公告")
    @PreAuthorize("@coin.hasPermission('notice_update')")
    @PostMapping("/changeStatus")
    public BaseResponse<Object> changeNoticeStatus(@NotNull Long nid, @NotNull Integer status) {
        Notice notice = new Notice();
        notice.setId(nid);
        notice.setStatus(status);
        if (BooleanUtils.isTrue(noticeService.deleteNotice(notice))) {
            return BaseResponse.success("修改成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "修改失败");
    }

    @ApiOperation("获取通知公告")
    @PreAuthorize("@coin.hasPermission('notice_select')")
    @GetMapping("/list")
    public BaseResponse<Object> list(@NotNull Integer pageSize, @NotNull Integer pageNum, String title, String author,
                                     Date startTime, Date endTime) {
        Query query =new Query();
        query.page(pageNum, pageSize);
        query.like(StringUtils.hasText(title), "title", title);
        query.like(StringUtils.hasText(author), "author", author);
        query.ge(!ObjectUtils.isEmpty(startTime), "last_update_time", startTime);
        query.le(!ObjectUtils.isEmpty(endTime), "last_update_time", endTime);
        return BaseResponse.success(noticeService.listByQuery(query));
    }

    @ApiOperation("删除通知公告")
    @PostMapping("/delete")
    @PreAuthorize("@coin.hasPermission('notice_delete')")
    public BaseResponse<Object> deleteNotice(@NotNull Long nid) {
        if (BooleanUtils.isTrue(noticeService.deleteNotice(nid))) {
            return BaseResponse.success("删除成功");
        }
        return BaseResponse.fail(ResponseCodes.FAIL, "删除失败");
    }

    @ApiOperation("获取通知公告的具体内容")
    @GetMapping("/getDetail")
    @PreAuthorize("@coin.hasPermission('notice_select')")
    public BaseResponse<Object> getContent(@NotNull Long nid) {
        return BaseResponse.success(noticeService.getContent(nid));
    }
}
