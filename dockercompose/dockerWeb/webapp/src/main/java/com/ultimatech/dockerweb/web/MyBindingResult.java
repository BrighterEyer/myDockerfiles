package com.ultimatech.dockerweb.web;

import com.google.gson.GsonBuilder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangleping on 2017/7/25.
 */
public class MyBindingResult<T> {
    private GsonBuilder gsonBuilder;
    private BindingResult result;
    private RedirectAttributes redirectAttributes;
    private Model model;
    private T vo;

    public T getVo() {
        return vo;
    }

    public void setVo(T vo) {
        this.vo = vo;
    }

    private static final String RESULT_NAME = "result";

    private static final String CLASS_ERROR_BORDER = "error-border";

    public MyBindingResult(BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        this.result = result;
        this.redirectAttributes = redirectAttributes;
        this.model = model;
        this.gsonBuilder = new GsonBuilder();
    }

    public MyBindingResult(BindingResult result, RedirectAttributes redirectAttributes, Model model, T vo) {
        this.result = result;
        this.redirectAttributes = redirectAttributes;
        this.model = model;
        this.vo = vo;
        this.gsonBuilder = new GsonBuilder();
    }

    public MyBindingResult(BindingResult result) {
        this.result = result;
        this.gsonBuilder = new GsonBuilder();
    }

    public MyBindingResult(Model model) {
        this.model = model;
        this.gsonBuilder = new GsonBuilder();
    }

    public MyBindingResult(RedirectAttributes redirectAttributes, Model model) {
        this.redirectAttributes = redirectAttributes;
        this.model = model;
        this.gsonBuilder = new GsonBuilder();
    }

    public MyBindingResult(Model model, T vo) {
        this.model = model;
        this.vo = vo;
    }

    public GsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }

    public void setGsonBuilder(GsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
    }

    public BindingResult getResult() {
        return result;
    }

    public void setResult(BindingResult result) {
        this.result = result;
    }

    public RedirectAttributes getRedirectAttributes() {
        return redirectAttributes;
    }

    public void setRedirectAttributes(RedirectAttributes redirectAttributes) {
        this.redirectAttributes = redirectAttributes;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private static final String SUCCESS_KEY = "success";

    private static final String VO_KEY = "vo";

    /**
     * 向视图报告验证结果
     */
    public void report() {
        if (this.getModel() != null)
            this.getModel().addAttribute(RESULT_NAME, this);
        if (this.getRedirectAttributes() != null)
            this.getRedirectAttributes().addFlashAttribute(RESULT_NAME, this);
        if (this.getResult() != null && !this.getResult().hasErrors()) {
            if (this.getModel() != null)
                this.getModel().addAttribute(SUCCESS_KEY, true);
            if (this.getRedirectAttributes() != null)
                this.getRedirectAttributes().addFlashAttribute(SUCCESS_KEY, true);
        }
        if (this.getVo() != null && this.hasErrors()) {
            if (this.getModel() != null)
                this.getModel().addAttribute(VO_KEY, this.getVo());
            if (this.getRedirectAttributes() != null)
                this.getRedirectAttributes().addFlashAttribute(VO_KEY, this.getVo());
        }
    }

    /**
     * 判断是否有验证错误
     *
     * @return
     */
    public boolean hasErrors() {
        if (this.getResult() != null)
            return this.getResult().hasErrors();
        else
            return false;
    }

    /**
     * 获得所有错误信息,并排除绑定错误.
     *
     * @return
     */
    public List<ObjectError> getAllErrors() {
        List<ObjectError> list = new ArrayList<>();
        if (this.getResult() != null) {
            for (ObjectError objectError : getResult().getAllErrors()) {
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    if (!fieldError.isBindingFailure()) {
                        list.add(fieldError);
                    }
                }
            }
            return list;
        } else
            return null;
    }

    /**
     * 获取校验结果的json字符串
     *
     * @return
     */
    public List<MyFieldError> getErrorsList() {
        if (this.getResult() != null && this.getResult().hasErrors()) {
            List<MyFieldError> list = new ArrayList<>();
            for (ObjectError objectError : this.getResult().getAllErrors()) {
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    MyFieldError myFieldError = new MyFieldError();
                    myFieldError.setFieldName(fieldError.getField());
                    myFieldError.setMessage(fieldError.getDefaultMessage());
                    myFieldError.setRejectedValue(fieldError.getRejectedValue());
                    myFieldError.setBindingfailure(fieldError.isBindingFailure());
                    list.add(myFieldError);
                }
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * 获取输入域field的class
     *
     * @param field       输入域的name
     * @param originClass 原始的class样式
     * @return
     */
    public String getCssClass(String field, String originClass) {
        if (this.getResult() != null && this.getResult().hasFieldErrors(field)) {
            return CLASS_ERROR_BORDER + " " + originClass;
        } else {
            return originClass;
        }
    }

    /**
     * 设置输入项的placeholder,在发生绑定错误时,会用errStr代替原字符串
     *
     * @param field     输入项
     * @param originStr 原字符串
     * @param errStr    错误提醒字符串,其中使用{0}来做错误输入值的占位.
     * @return
     */
    public String getPlaceholder(String field, String originStr, String errStr) {
        if (this.getResult() != null && this.getResult().hasFieldErrors(field) && this.getResult().getFieldError(field).isBindingFailure()) {
            String str = MessageFormat.format(errStr, this.getResult().getFieldError(field).getRejectedValue());
            return str;
        } else {
            return originStr;
        }
    }

    public void cleanSuccess() {
        if (this.getModel() != null)
            this.getModel().addAttribute(SUCCESS_KEY, false);
        if (this.getRedirectAttributes() != null)
            this.getRedirectAttributes().addFlashAttribute(SUCCESS_KEY, false);
    }
}
