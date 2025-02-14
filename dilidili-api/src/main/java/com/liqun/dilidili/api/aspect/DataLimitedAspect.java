package com.liqun.dilidili.api.aspect;

import com.liqun.dilidili.api.support.UserSupport;
import com.liqun.dilidili.domain.UserMoment;
import com.liqun.dilidili.domain.annotation.ApiLimitedRole;
import com.liqun.dilidili.domain.auth.UserRole;
import com.liqun.dilidili.domain.constant.AuthRoleConstant;
import com.liqun.dilidili.domain.exception.ConditionException;
import com.liqun.dilidili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.api.aspect
 * @className: ApiLimitedRoleAspect
 * @author: LiQun
 * @description: TODO
 * @data 2025/2/14 14:35
 */
@Order(1)
@Component
@Aspect
public class DataLimitedAspect {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserRoleService userRoleService;

    @Pointcut("@annotation(com.liqun.dilidili.domain.annotation.DataLimited)")
    public void check() {

    }
    @Before("check()")
    public void doBefore(JoinPoint joinPoint) {
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for(Object arg : args) {
            if(arg instanceof UserMoment){
                UserMoment userMoment = (UserMoment) arg;
                String type = userMoment.getType();
                if(roleCodeSet.contains(AuthRoleConstant.ROLE_LV0) && !"0".equals(type)){
                    throw new ConditionException("参数异常");
                }
            }
        }
    }
}
