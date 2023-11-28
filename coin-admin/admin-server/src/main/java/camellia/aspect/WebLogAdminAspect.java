package camellia.aspect;

import camellia.domain.UserLog;
import camellia.model.WebLog;
import camellia.service.UserLogService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
/**
 * @author 墨染盛夏
 * @version 2023/11/27 15:54
 */
//@Component
//@Aspect
//@Order(2)
//@Slf4j
public class WebLogAdminAspect {
//    @Autowired
//    private UserLogService userLogService;
//
//    @Pointcut("execution(* camellia.controller.*.*(..))") // controller 包里面所有类，类里面的所有方法 都有该切面
//    public void webLog(){}
//
//    /**
//     * 2 记录日志的环绕通知
//     */
//
//    @Around("webLog()")
//    public Object recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Object result = null ;
//        WebLog webLog = new WebLog();
//        long start = System.currentTimeMillis() ;
//
//        // 执行方法的真实调用
//        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
//
//        long end = System.currentTimeMillis() ;
//
//
//        // 获取当前请求的request对象
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//
//        // 获取安全的上下文
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserLog userLog = new UserLog();
////        userLog.setUid(Long.valueOf(authentication==null ? "anonymous":authentication.getPrincipal().toString())); // 获取用户的id
//        userLog.setIp(request.getRemoteAddr()); // TODO 获取ip 地址
//        userLog.setTime((Long)(start-end)/1000); // 请求该接口花费的时间
//
//        // 获取方法
//        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
//        // 获取类的名称
//        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
//        Method method = signature.getMethod();
//        // 因为我们会使用Swagger 这工具，我们必须在方法上面添加@ApiOperation(value="")该注解
//        // 获取ApiOperation
//        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
//        userLog.setMethod(targetClassName + "." + method.getName());
//        userLog.setDescription(annotation==null ? "no desc":annotation.value());
//        log.info(JSON.toJSONString(userLog,true));
//        userLogService.saveUserLog(userLog);
//        return result ;
//    }
}
