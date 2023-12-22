package camellia.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/12/10 11:37
 */
public class DBConstant {
    public final static List<String> USER_INFO = Arrays.asList("id", "username", "real_name", "email",
            "phone", "id_card", "created", "review_status", "auth_status");

    public final static List<String> AUTH_BASIC_INFO = Arrays.asList("id", "user_id", "image_url", "serialno", "auth_code");
}
