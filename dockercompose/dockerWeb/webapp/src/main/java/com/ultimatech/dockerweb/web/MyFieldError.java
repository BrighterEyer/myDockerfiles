package com.ultimatech.dockerweb.web;

/**
 * Created by zhangleping on 2017/7/25.
 */
public class MyFieldError {
    private String fieldName;

    private String message;

    private Object rejectedValue;

    private Boolean bindingfailure;

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getBindingfailure() {
        return bindingfailure;
    }

    public void setBindingfailure(Boolean bindingfailure) {
        this.bindingfailure = bindingfailure;
    }
}
