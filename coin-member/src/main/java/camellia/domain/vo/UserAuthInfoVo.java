package camellia.domain.vo;

import camellia.domain.UserAuthAuditRecord;
import camellia.domain.UserAuthInfo;
import camellia.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/12/10 11:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthInfoVo {
    private UserInfo userInfo;

    private List<UserAuthInfo> userAuthInfos;

    private UserAuthAuditRecord userAuthAuditRecords;
}
