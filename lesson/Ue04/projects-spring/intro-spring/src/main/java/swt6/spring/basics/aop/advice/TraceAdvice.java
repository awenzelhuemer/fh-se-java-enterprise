package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TraceAdvice {

    @DeclareParents(value = "swt6.spring.basics.aop.logic.*", defaultImpl = TraceOptionsDefaultImpl.class)
    private static TraceOptions mixin;

    private boolean isTracingEnabledInProxy(JoinPoint joinPoint) {
        return ((TraceOptions)joinPoint.getThis()).isTracingEnabled();
    }

    @Pointcut("execution(public * swt6.spring.basics.aop.logic..*find*(..))")
    private void findMethods() {

    }

    @Before("findMethods()")
    private void traceBefore(JoinPoint joinPoint) {
        if (isTracingEnabledInProxy(joinPoint)) {
            String methodName = joinPoint.getTarget().getClass().getName() + "." +
                    joinPoint.getSignature().getName();
            System.out.println("--> " + methodName);
        }
    }

    @AfterReturning("findMethods()")
    public void traceAfter(JoinPoint joinPoint) {
        if(isTracingEnabledInProxy(joinPoint)) {
            String methodName = joinPoint.getTarget().getClass().getName() + "." +
                    joinPoint.getSignature().getName();
            System.out.println("<-- " + methodName);
        }
    }

    @Around("findMethods()")
    public Object traceAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getTarget().getClass().getName() + "." +
                proceedingJoinPoint.getSignature().getName();

        if(isTracingEnabledInProxy(proceedingJoinPoint)) {
            System.out.println("==> " + methodName);
        }

        Object returnValue = proceedingJoinPoint.proceed();

        if (isTracingEnabledInProxy(proceedingJoinPoint)) {
            System.out.println("<== " + methodName);
        }

        return returnValue;
    }

    @AfterThrowing(pointcut = "findMethods()", throwing = "exception")
    public void traceException(JoinPoint joinPoint, Throwable exception) {
        if (isTracingEnabledInProxy(joinPoint)) {
            String methodName = joinPoint.getTarget().getClass().getName() + "." +
                    joinPoint.getSignature().getName();
            System.out.printf("##> %s%n threw exception <%ss>%n", methodName, exception);
        }
    }
}
