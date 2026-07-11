package com.sayi.demo_plugin2;

import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.MessageHandlerFilter;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Shiro
@Component
public class AnnoPlugin {

    @PostConstruct
    public void init() {
        System.out.println("AnnoPlugin init");
    }

    public void call() {
        System.out.println("anno called");
    }

    @GroupMessageHandler
    @MessageHandlerFilter(cmd = "a")
    public void onMessage(Bot bot, GroupMessageEvent event) {
        System.out.println("I am AnnoPlugin");
        System.out.println(event.toString());

        bot.sendGroupMsg(event.getGroupId(), "good!", false);

        Student student = new Student();
        student.setName("Sayi");
        student.setAge(18);

        System.out.println(student);
    }

}
