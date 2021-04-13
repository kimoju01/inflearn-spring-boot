package kr.ac.ks.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect //AOP 쓰려면 필요함
//스프링 빈 등록해야하는데 @Component 써도 되는데 SpringConfig에서 스프링 빈으로 따로 설정하는게 더 좋다.
public class TimeTraceAop {

    @Around("execution(* kr.ac.ks.hellospring..*(..))")   //어디에 이 공통 관심 사항을 적용할건지 정해준다.
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
//            Object result = joinPoint.proceed();
//            return result; 를 인라인으로 합친 결과가 아랫줄.
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() +" " + timeMs + "ms");
        }
    }
}
