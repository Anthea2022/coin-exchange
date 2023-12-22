package camellia.exception;

import camellia.common.ResponseCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 12:46
 */
@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private ResponseCodes responseCodes;

    private String msg;
}
