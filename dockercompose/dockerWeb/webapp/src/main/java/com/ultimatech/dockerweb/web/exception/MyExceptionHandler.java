package com.ultimatech.dockerweb.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 张乐平 on 8/12 0012.
 */
public class MyExceptionHandler extends SimpleMappingExceptionResolver {
    private Logger logger= LoggerFactory.getLogger(MyExceptionHandler.class);
//    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        Map<String, Object> model = new HashMap<String, Object>();
//        model.put("ex", e);
//        logger.error("内部错误",e);
//        return new ModelAndView("exception",model);
//    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("内部错误",ex);
        return super.doResolveException(request,response,handler,ex);
    }

}
