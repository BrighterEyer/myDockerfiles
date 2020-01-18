package com.ultimatech.dockerweb.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhangleping on 2017/1/20.
 */
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            Map<String, String[]> parameterMap = request.getServletRequest().getParameterMap();
            Map<String, String> httpParams = parameterMap.entrySet().stream().filter(entry -> entry.getValue().length > 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()[0]));
            map.putAll(httpParams);
            return true;
        }else
            return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler, Exception e) {

    }
}
