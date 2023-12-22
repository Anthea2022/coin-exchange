package camellia.service;

import camellia.common.ResponseCodes;
import camellia.domain.WorkIssue;
import camellia.exception.BusinessException;
import camellia.mapper.UserMapper;
import camellia.mapper.WorkIssueMapper;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2023/11/28 20:55
 */
@Service
public class WorkIssueService extends BaseService<WorkIssue, Long, WorkIssueMapper> {
    @Autowired
    private WorkIssueMapper workIssueMapper;

    @Autowired
    private UserMapper userMapper;

    public PageInfo<WorkIssue> listPage(Query query) {
        return workIssueMapper.page(query);
    }

    public Boolean addQuestion(WorkIssue workIssue) {
        return workIssueMapper.saveIgnoreNull(workIssue) > 0;
    }

    public Boolean replyQuestion(WorkIssue workIssue) {
        workIssue.setStatus(0);
        Long uid = TokenUtil.getUid();
        workIssue.setAnswerUid(uid);
        workIssue.setAnswerName(userMapper.getColumnValue("username", new Query().eq("id",uid), String.class));
        if (workIssueMapper.getColumnValue("status", new Query().eq("id", workIssue.getId()), Integer.class).equals(0)) {
            throw new BusinessException(ResponseCodes.FAIL, "工单状态错误");
        }
        return workIssueMapper.updateIgnoreNull(workIssue) > 0;
    }
}
