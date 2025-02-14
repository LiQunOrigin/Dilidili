package com.liqun.dilidili.api;

import com.liqun.dilidili.api.support.UserSupport;
import com.liqun.dilidili.domain.JsonResponse;
import com.liqun.dilidili.domain.auth.UserAuthorities;
import com.liqun.dilidili.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.api
 * @className: UserAuthApi
 * @author: LiQun
 * @description: TODO
 * @data 2025/2/14 11:19
 */
@RestController
public class UserAuthApi {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserAuthService userAuthService;

    @GetMapping("user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities() {
        Long userId = userSupport.getCurrentUserId();
        UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
        return new JsonResponse<>(userAuthorities);
    }
}
