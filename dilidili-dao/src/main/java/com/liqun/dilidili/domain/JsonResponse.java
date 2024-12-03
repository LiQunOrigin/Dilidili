package com.liqun.dilidili.domain;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.domain
 * @className: JsonResponse
 * @author: LiQun
 * @description: TODO
 * @data 2024/12/3 19:51
 */
public class JsonResponse<T> {

    private String code;
    private String msg;
    private T data;

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data) {
        this.data = data;
        msg = "成功";
        code = "0";
    }

    public static JsonResponse<String> success() {
        return new JsonResponse<>(null);
    }

    public static JsonResponse<String> success(String data) {
        return new JsonResponse<>(data);
    }

    public static JsonResponse<String> fail() {
        return new JsonResponse<>("1", "失败");
    }

    public static JsonResponse<String> fail(String code, String msg) {
        return new JsonResponse<>(code, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
