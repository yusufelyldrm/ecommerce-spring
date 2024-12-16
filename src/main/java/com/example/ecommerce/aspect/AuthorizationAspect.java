package com.example.ecommerce.aspect;

import com.example.ecommerce.enums.Role;
import com.example.ecommerce.manager.AuthManager;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthorizationAspect {

    private final AuthManager authManager;

    public AuthorizationAspect(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Around("@annotation(com.example.ecommerce.annotations.AdminOnly)")
    public Object checkAdminRole(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        boolean isAuthorized = authManager.authenticate(token, Role.ADMIN);
        if (!isAuthorized) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }

        return joinPoint.proceed();
    }

    @Around("@annotation(com.example.ecommerce.annotations.Authenticated)")
    public Object checkAuthenticated(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        boolean isAuthorized = authManager.authenticate(token, null);
        if (!isAuthorized) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }

        return joinPoint.proceed();
    }
}
