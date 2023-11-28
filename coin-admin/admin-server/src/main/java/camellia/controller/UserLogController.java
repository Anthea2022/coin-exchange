package camellia.controller;

import camellia.common.BaseResponse;
import camellia.service.UserLogService;
import com.gitee.fastmybatis.core.query.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author 墨染盛夏
 * @version 2023/11/26 23:55
 */
@Api(tags = "用户日志接口")
@RestController
@RequestMapping("/admin/userLog")
public class UserLogController {
    @Autowired
    private UserLogService userLogService;

    @ApiOperation("用户日志查看")
    @GetMapping("/list")
    @PreAuthorize("@coin.hasPermission('sys_user_select')")
    public BaseResponse<Object> userLogList(@PathVariable Map<String, Object> map) {
        Query query = new Query();
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        query.page(pageNum, pageSize);
        Integer type = (Integer) map.get("type");
        query.eq(!ObjectUtils.isEmpty(type), "type", type);
        Date createTime = (Date) map.get("createTime");
        query.eq(!ObjectUtils.isEmpty(createTime), "created", createTime);
        return BaseResponse.success(userLogService.listPage(query, (String) map.get("username")));
    }
}
