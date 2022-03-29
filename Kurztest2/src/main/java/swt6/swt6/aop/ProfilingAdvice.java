package swt6.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Aspect
@Component
public class ProfilingAdvice {

    private final HashMap<String, Integer> methodCount = new HashMap<>();

    @AfterReturning("execution(public * swt6..*.*Service*(..))")
    public void countMethodCalls(JoinPoint jp) {
        int currentCount = methodCount.getOrDefault(jp.getSignature().getName(), 0);
        methodCount.put(jp.getSignature().getName(), currentCount + 1);
        methodCount.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
