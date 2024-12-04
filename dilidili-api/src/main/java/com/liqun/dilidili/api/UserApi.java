package com.liqun.dilidili.api;


import com.liqun.dilidili.domain.JsonResponse;
import com.liqun.dilidili.domain.User;
import com.liqun.dilidili.service.UserService;
import com.liqun.dilidili.service.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.api
 * @className: UserApi
 * @author: LiQun
 * @description: TODO
 * @data 2024/12/3 23:46
 */
@RestController
public class UserApi {
    @Autowired
    private UserService userService;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey() {
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return JsonResponse.success();
    }

    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) {
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }
}
