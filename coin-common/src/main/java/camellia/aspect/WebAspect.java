package camellia.aspect;

import camellia.model.WebLog;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * @author anthea
 * @date 2023/10/29 11:42
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class WebAspect {
    @Pointcut("execution(* camellia.controller.*.*(..))") // controller 包里面所有类，类里面的所有方法 都有该切面
    public void webLog(){}

    @Around("webLog()")
    public Object recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        WebLog webLog = new WebLog();
        long start = System.currentTimeMillis();
        // 执行方法的真实调用
        try {
            result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            long end = System.currentTimeMillis();
            webLog.setSpendTime((int) (start - end) / 1000);
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            // 获取安全的上下文
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String url = request.getRequestURI().toString();
            webLog.setUri(request.getRequestURI());
            webLog.setBasePath(StrUtil.removeSuffix(url, URLUtil.url(url).getPath()));
            webLog.setUrl(authentication ==null ? "anonymous" : authentication.getPrincipal().toString());
            webLog.setIp(request.getRemoteAddr());
            //
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
            Method method = signature.getMethod();
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            webLog.setDescription(apiOperation == null ?"no desc" : apiOperation.value());
            webLog.setMethod(targetClassName + "." + method.getName());
            webLog.setParameter(getMethodParameter(method, proceedingJoinPoint.getArgs()));
            webLog.setResult(result);
            log.info(JSON.toJSONString(webLog, true));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            return result;
        }
    }

    private Object getMethodParameter(Method method, Object[] args) {
        Map<String, Object> map = new HashMap<>();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        for (int i=0;i<parameterNames.length;i++) {
            if ("password".equals(parameterNames[i]) || args[i] instanceof MultipartFile) {
                map.put(parameterNames[i], "受限的类型");
            } else {
                map.put(parameterNames[i], args[i]);
            }
        }
        return map;
    }
}
