package com.example.springprojectexample.aop;


import com.example.springprojectexample.pojo.PatientDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import java.io.FileWriter;
import java.io.IOException;

@Aspect
@Log4j2
@Component
public class MethodExecutionAspectTime {
    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Around("execution(* com.example.demo..*(..))")
    @Around("execution(* com.example.springprojectexample.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info(className + "." + methodName + " executed in " + executionTime + "ms");
        log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

    @Around("@annotation(com.example.springprojectexample.aop.LogExecutionTime)")
    public Object logPatientData(ProceedingJoinPoint joinPoint) throws Throwable{
        Object output = null;
        try {
            objectMapper.registerModule(new JavaTimeModule());
            String methodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            String inputArgumentString = "No Arguments";
            if (args != null && args.length >0){
                inputArgumentString = objectMapper.writeValueAsString(args);
            }
            output = joinPoint.proceed();
            String outputArgumentString = "No Output";
            if (output != null){
                outputArgumentString = objectMapper.writeValueAsString(output);
            }
            log.info("Method Name: {} Input Arguments: {} Output Arguments: {}",methodName,inputArgumentString,outputArgumentString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
