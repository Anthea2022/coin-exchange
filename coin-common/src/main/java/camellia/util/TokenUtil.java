package camellia.util;

import camellia.common.ResponseCodes;
import camellia.exception.AccountException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

/**
*@author 墨染盛夏
*@version 2023/11/23 14:24
*/
public class TokenUtil {
    public static Long getUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (ObjectUtils.isEmpty(principal)) {
            throw new AccountException(ResponseCodes.FAIL, "请先登录");
        }
        return Long.valueOf(principal.toString());
    }
}
