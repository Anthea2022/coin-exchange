package camellia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 墨染盛夏
 * @version 2024/1/6 14:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePayload {
    private String uid;

    @NotBlank
    private String channel;

    @NotBlank
    private String body;
}
