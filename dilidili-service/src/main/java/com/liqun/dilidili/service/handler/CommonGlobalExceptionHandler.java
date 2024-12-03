package com.liqun.dilidili.service.handler;

import com.liqun.dilidili.domain.JsonResponse;
import com.liqun.dilidili.domain.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service.handler
 * @className: CommonGlobalExceptionHandler
 * @author: LiQun
 * @description: 全局异常处理器
 * @data 2024/12/3 23:10
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> commonExceptionHandler
            (HttpServletRequest request, Exception e) {
        String errorMsg = e.getMessage();
        if (e instanceof ConditionException) {
            String errorCode = ((ConditionException) e).getCode();
            return new JsonResponse<>(errorCode, errorMsg);
        } else {
            return new JsonResponse<>("500", errorMsg);
        }
    }
}
