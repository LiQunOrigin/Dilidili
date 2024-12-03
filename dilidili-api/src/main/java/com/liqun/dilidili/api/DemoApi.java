package com.liqun.dilidili.api;

import com.liqun.dilidili.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.cert.X509Certificate;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.api
 * @className: DemoApi
 * @author: LiQun
 * @description: TODO
 * @data 2024/12/3 14:59
 */
@RestController
public class DemoApi {
    @Autowired
    private DemoService demoService;

    @GetMapping("/query")
    public Long query(Long id){
        return demoService.query(id);
    }
}
