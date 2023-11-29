package camellia.exception;

import camellia.common.BaseResponse;
import camellia.common.ResponseCodes;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @author anthea
 * @date 2023/10/29 12:23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // api内部调用
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse<Object> handlerApiException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (!ObjectUtils.isEmpty(fieldError)) {
                return BaseResponse.fail(ResponseCodes.FAIL, fieldError.getDefaultMessage());
            }
        }
        return BaseResponse.fail(ResponseCodes.FAIL, exception.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    public BaseResponse<Object> handleBindException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (!ObjectUtils.isEmpty(fieldError)) {
                return BaseResponse.fail(ResponseCodes.FAIL, fieldError.getDefaultMessage());
            }
        }
        return BaseResponse.fail(ResponseCodes.FAIL, exception.getMessage());
    }

    @ExceptionHandler(value = RedisConnectionFailureException.class)
    public BaseResponse<Object> handleRedisConnectionFailureException(RedisConnectionFailureException exception) {
        return BaseResponse.fail(ResponseCodes.FAIL, "Redis连接异常");
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public BaseResponse<Object> handleAccessDeniedException(AccessDeniedException exception) {
        return BaseResponse.fail(ResponseCodes.NO_AUTH, "没有权限执行操作");
    }
}
