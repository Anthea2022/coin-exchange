package camellia.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/11/20 11:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    /**
     * token
     */
    private String token;

    /**
     * 权限
     */
    private List<SimpleGrantedAuthority> permissions;
}
