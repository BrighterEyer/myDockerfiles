package com.ultimatech.dockerweb.web.controller;

import com.google.gson.JsonObject;
import com.ultimatech.dockerweb.websocket.MsgData;
import com.ultimatech.dockerweb.websocket.MsgType;
import com.ultimatech.dockerweb.websocket.Wssmessage;
import com.ultimatech.dockerweb.websocket.service.IWebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 张乐平 on 7/28 0028.
 */
@Controller
public class HomeController extends MainsiteErrorController {

    private Logger log = LoggerFactory.getLogger(HomeController.class);

    @Resource(name="kafkaWebsocketService")
    private IWebsocketService websocketService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        model.addAttribute("ipaddress", address.getHostAddress());
        return "index";
    }

    @RequestMapping(value = "/say")
    @ResponseBody
    public String say(String toId, String fromId, String content) throws IOException {
        MsgData msgData = new MsgData(toId);
        msgData.setFromId(fromId);
        msgData.setContent(content);
        Wssmessage wssmessage = new Wssmessage(MsgType.say, msgData);
        this.websocketService.sendMsg(wssmessage);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ip", InetAddress.getLocalHost().getHostAddress());
        return jsonObject.toString();
    }

}
