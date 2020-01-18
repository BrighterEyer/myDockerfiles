package com.ultimatech.dockerweb.websocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangleping on 2017/2/10.
 */
public class MySpringConfigurator extends SpringConfigurator {

    private static final Log logger = LogFactory.getLog(MySpringConfigurator.class);

    private static final String NO_VALUE = ObjectUtils.identityToString(new Object());


    private static final Map<String, Map<Class<?>, String>> cache =
            new ConcurrentHashMap<String, Map<Class<?>, String>>();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        ApplicationContext wac = SpringUtil.getApplicationContext();
        //ContextLoader.getCurrentWebApplicationContext();
        if (wac == null) {
            String message = "Failed to find the root WebApplicationContext. Was ContextLoaderListener not used?";
            logger.error(message);
            throw new IllegalStateException(message);
        }

        String beanName = ClassUtils.getShortNameAsProperty(endpointClass);
        if (wac.containsBean(beanName)) {
            T endpoint = wac.getBean(beanName, endpointClass);
            if (logger.isTraceEnabled()) {
                logger.trace("Using @ServerEndpoint singleton " + endpoint);
            }
            return endpoint;
        }

        Component ann = AnnotationUtils.findAnnotation(endpointClass, Component.class);
        if (ann != null && wac.containsBean(ann.value())) {
            T endpoint = wac.getBean(ann.value(), endpointClass);
            if (logger.isTraceEnabled()) {
                logger.trace("Using @ServerEndpoint singleton " + endpoint);
            }
            return endpoint;
        }

        beanName = this.getBeanNameByType(wac, endpointClass);
        if (beanName != null) {
            return (T) wac.getBean(beanName);
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Creating new @ServerEndpoint instance of type " + endpointClass);
        }
        return wac.getAutowireCapableBeanFactory().createBean(endpointClass);
    }

    private String getBeanNameByType(ApplicationContext wac, Class<?> endpointClass) {
        String wacId = wac.getId();

        Map<Class<?>, String> beanNamesByType = cache.get(wacId);
        if (beanNamesByType == null) {
            beanNamesByType = new ConcurrentHashMap<Class<?>, String>();
            cache.put(wacId, beanNamesByType);
        }

        if (!beanNamesByType.containsKey(endpointClass)) {
            String[] names = wac.getBeanNamesForType(endpointClass);
            if (names.length == 1) {
                beanNamesByType.put(endpointClass, names[0]);
            }
            else {
                beanNamesByType.put(endpointClass, NO_VALUE);
                if (names.length > 1) {
                    throw new IllegalStateException("Found multiple @ServerEndpoint's of type [" +
                            endpointClass.getName() + "]: bean names " + Arrays.asList(names));
                }
            }
        }

        String beanName = beanNamesByType.get(endpointClass);
        return (NO_VALUE.equals(beanName) ? null : beanName);
    }
}
