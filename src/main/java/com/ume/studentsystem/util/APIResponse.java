package com.ume.studentsystem.util;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {
    private Boolean success;
    private Integer code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public APIResponse(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> APIResponse<T> create(T data){
        return new APIResponse<>(true, HttpStatus.CREATED.value(),"Create success",data); //ApiResponse
    }

    public static <T> APIResponse<T> create(T data,String message){ // create
        return new APIResponse<>(true, HttpStatus.CREATED.value(),message,data);
    }

    /** Success with default message */
    public static <T> APIResponse<T> ok(T data) { // //list, list_one, update, remove
        return new APIResponse<>(true, HttpStatus.OK.value(), "Success", data);
    }
    /** General success (HTTP 200) */
    public static <T> APIResponse<T> ok(T data, String message) {
        return new APIResponse<>(true, HttpStatus.OK.value(), message, data);
    }

    public static <T> APIResponse<T> error(T data,String message, Integer status){ // 401,400,500
        return new APIResponse<>(false, status, message,data);
    }

    /** Error response with custom HTTP status */
    public static <T> APIResponse<T> error(String message, HttpStatus status) {
        return new APIResponse<>(false, status.value(), message, null);
    }

    /** Error response with custom data and status */
    public static <T> APIResponse<T> error(T data, String message, HttpStatus status) {
        return new APIResponse<>(false, status.value(), message, data);
    }
}
