package camellia.feign;

import camellia.common.ResponseCodes;
import camellia.exception.BusinessException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 14:28
 */
@Slf4j
public class OAuth2FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isEmpty(requestAttributes)) {
            throw new BusinessException(ResponseCodes.FAIL, "系统繁忙");
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header)) {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, header);
        }
        throw new BusinessException(ResponseCodes.FAIL, "token为空");
    }
}
