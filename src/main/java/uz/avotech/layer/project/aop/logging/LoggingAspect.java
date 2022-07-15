package uz.avotech.layer.project.aop.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
@DependsOn("projectConstants")
@RequiredArgsConstructor
public class LoggingAspect {

    public static final String NON_PROD = "!prod";
    private final Environment env;

    @Pointcut(
        "within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)"
    )
    public void springBeanPointcut() {
    }

    @Pointcut("@within(uz.avotech.layer.project.aop.logging.Loggable)"
            + " || within(uz.avotech.layer.project.repository..*)"
            + " || within(uz.avotech.layer.project.service..*)"
            + " || within(uz.avotech.layer.project.web..*)"
            + " || @annotation(uz.avotech.layer.project.aop.logging.Loggable)")
    public void applicationPackagePointcut() {
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of(NON_PROD))) {
            log.error(
                "Exception in {}.{}() with cause = '{}' and exception = '{}'",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getCause() == null ? "NULL" : e.getCause(),
                e.getMessage(),
                e);

        } else {
            log.error(
                "Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getCause() == null ? "NULL" : e.getCause());
        }
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isTraceEnabled()) {
            log.trace(
                "Enter: {}.{}()",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        }
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        if (log.isTraceEnabled()) {
            log.trace(
                "Exit: {}.{}(). Time taken: {} millis",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                end - start);
        }
        return result;
    }
}
