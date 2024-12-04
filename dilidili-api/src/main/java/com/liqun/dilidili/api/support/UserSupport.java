package com.liqun.dilidili.api.support;

import com.liqun.dilidili.domain.exception.ConditionException;
import com.liqun.dilidili.service.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.api.support
 * @className: UserSupport
 * @author: LiQun
 * @description: TODO
 * @data 2024/12/4 15:26
 */
@Component
public class UserSupport {
    public Long getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        if(userId < 0) {
            throw new ConditionException("非法用户");
        }
        return userId;
    }
}
