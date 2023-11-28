package camellia.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
*@author 墨染盛夏
*@version 2023/11/23 14:24
*/
public class TokenUtil {
    public static Long getUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : Long.valueOf(authentication.getPrincipal().toString());
    }
}
