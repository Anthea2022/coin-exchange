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
        System.out.println(authentication);
        return Long.valueOf(authentication.getPrincipal().toString());
    }
}
