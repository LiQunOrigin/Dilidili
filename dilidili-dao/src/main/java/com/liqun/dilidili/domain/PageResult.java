package com.liqun.dilidili.domain;

import java.util.List;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.domain
 * @className: PageResult
 * @author: LiQun
 * @description: TODO
 * @data 2025/1/22 0:23
 */
public class PageResult<T> {
    private Integer total;
    private List<T> list;
    public PageResult(Integer total,List<T> list){
        this.total = total;
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
