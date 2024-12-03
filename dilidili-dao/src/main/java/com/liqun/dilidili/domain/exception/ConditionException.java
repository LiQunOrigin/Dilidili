package com.liqun.dilidili.domain.exception;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.domain.exception
 * @className: ConditionException
 * @author: LiQun
 * @description: 条件异常
 * @data 2024/12/3 23:12
 */
public class ConditionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;

    public ConditionException(String code, String name) {
        super(name);
        this.code = code;
    }

    public ConditionException(String name) {
        super(name);
        code = "500";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
