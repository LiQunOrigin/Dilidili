package com.liqun.dilidili.service;

import com.liqun.dilidili.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service
 * @className: DemoService
 * @author: LiQun
 * @description: TODO
 * @data 2024/12/3 14:58
 */
@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    public Long query(Long id){
        return demoDao.query(id);
    }
}
