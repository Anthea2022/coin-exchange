package camellia.exception;

import camellia.common.ResponseCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 13:06
 */
@Getter
@AllArgsConstructor
public class AccountException extends RuntimeException {
    private ResponseCodes responseCodes;

    private String msg;
}
