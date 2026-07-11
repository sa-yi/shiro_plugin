package com.sayi.demo_plugin2;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.MessageHandlerFilter;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Shiro
@Component
public class TestAnnoPlugin {
    @PostConstruct
    public void init() {
        System.out.println("TestPlugin 已加载! 注册的处理器: " +
                this.getClass().getDeclaredMethods().length);
    }

    @GroupMessageHandler
    @MessageHandlerFilter(cmd = "date")
    public void hutool(Bot bot, GroupMessageEvent event) {
        ChineseDate chineseDate = new ChineseDate(DateUtil.date());
        bot.sendGroupMsg(event.getGroupId(),chineseDate.toString(),false);
    }
}
