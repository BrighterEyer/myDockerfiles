package com.ultimatech.dockerweb.web;

import com.ultimatech.dockerweb.web.exception.MyExceptionHandler;
import com.ultimatech.dockerweb.websocket.WebSocketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.Properties;

/**
 * Created by zhangleping on 16/10/17.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ultimatech.dockerweb.web"})
@Import({WebSocketConfig.class})
public class DockerWebApp extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {

    private static Logger logger = LoggerFactory.getLogger(DockerWebApp.class);

    @Bean
    public MyExceptionHandler simpleMappingExceptionResolver() {
        MyExceptionHandler resolver = new MyExceptionHandler();
        Properties map = new Properties();
        map.put("java.lang.Throwable", "500");
        resolver.setExceptionMappings(map);
        return resolver;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DockerWebApp.class);
    }

    public static void main(String[] args) {
        logger.info("应用启动");
        ConfigurableApplicationContext ctx = SpringApplication.run(DockerWebApp.class, args);

    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
//        configurableEmbeddedServletContainer.setPort(Integer.valueOf(conf.getProperty("server.port")));
//        configurableEmbeddedServletContainer.setContextPath(conf.getProperty("server.contextPath"));
    }
}
