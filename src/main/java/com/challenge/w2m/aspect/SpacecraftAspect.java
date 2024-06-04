package com.challenge.w2m.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class SpacecraftAspect {

    @Before("execution(* com.challenge.w2m.controller.SpacecraftController.findById(Long)) && args(id)")
    public void logNegativeParamId(Long id){
        if(id < 0) {
            log.error("Negative Id in findById method");
        }
    }

}
