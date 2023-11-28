package camellia.service;

import camellia.domain.Notice;
import camellia.mapper.NoticeMapper;
import camellia.mapper.UserMapper;
import camellia.util.MD5Util;
import camellia.util.TokenUtil;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/28 0:10
 */
@Service
public class NoticeService extends BaseService<Notice, Long, NoticeMapper> {
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserMapper userMapper;

    public Boolean isRepeated(Notice notice) {
        List<Notice> notices = noticeMapper.listTitleAndContent();
        for (Notice noticeInDB : notices) {
            if (noticeInDB.getTitle().equals(notice.getTitle())
                    || MD5Util.stringToMD5(noticeInDB.getContent()).equals(MD5Util.stringToMD5(notice.getContent()))) {
                return true;
            }
        }
        return false;
    }

    public Boolean addNotice(Notice notice) {
        Long uid = TokenUtil.getUid();
        String username = userMapper.getColumnValue("username", new Query().eq("id", uid), String.class);
        notice.setAuthor(username);
        return noticeMapper.saveIgnoreNull(notice) > 0;
    }

    public Boolean deleteNotice(Notice notice) {
        return noticeMapper.updateIgnoreNull(notice) > 0;
    }

    public PageInfo<Notice> listByQuery(Query query) {
        return noticeMapper.pageBySpecifiedColumns(Arrays.asList("id", "author", "title", "description", "remark", "sort", "created", "last_update_time"),
                query, Notice.class);
    }

    public Boolean deleteNotice(Long nid) {
        Integer status = noticeMapper.getColumnValue("status", new Query().eq("id", nid), Integer.class);
        if (!ObjectUtils.isEmpty(status) || status.equals(1)) {
            return false;
        }
        return noticeMapper.deleteById(nid) > 0;
    }

    public String getContent(Long nid) {
        return noticeMapper.getColumnValue("content", new Query().eq("id", nid), String.class);
    }
}
