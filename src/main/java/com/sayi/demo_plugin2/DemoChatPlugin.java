package com.sayi.demo_plugin2;

import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.MessageHandlerFilter;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Shiro
@Component
public class DemoChatPlugin {
    @Autowired(required = false)
    private DeepSeekChatModel chatModel;
    @Autowired
    private AnnoPlugin annoPlugin;
    @PostConstruct
    public void init() {
        System.out.println("inited");
    }

    @GroupMessageHandler
    @MessageHandlerFilter(cmd = "/chat(.*)")
    public void chat(Bot bot, GroupMessageEvent event, Matcher matcher) {
        String msg = matcher.group(1);
        String call = chatModel.call(msg);
        bot.sendGroupMsg(event.getGroupId(),call,false);
        annoPlugin.call();
    }

}
