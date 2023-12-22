package camellia.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 17:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterParam {
    @NotBlank
    private String account;

    @NotBlank
    private String password;

    @NotBlank
    private String code;
}
