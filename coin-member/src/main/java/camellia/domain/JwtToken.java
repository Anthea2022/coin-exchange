package camellia.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 墨染盛夏
 * @version 2023/11/30 20:34
 */
@Data
public class JwtToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private Long expiresIn;

    /**
     * token范围
     */
    @JsonProperty("scope")
    private String scope;

    /**
     * 颁发的凭证
     */
    @JsonProperty("jti")
    private String jti;
}