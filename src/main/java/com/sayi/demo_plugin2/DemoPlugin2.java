package com.sayi.demo_plugin2;

import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;

public class DemoPlugin2 extends BotPlugin {

    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {
        String msg = event.getRawMessage();
        if (msg.equals("ping")) {
            bot.sendGroupMsg(event.getGroupId(), "pong", false);
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }

}
