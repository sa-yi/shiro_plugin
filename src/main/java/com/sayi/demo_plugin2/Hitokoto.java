package com.sayi.demo_plugin2;

import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.MessageHandlerFilter;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.sayi.demo_plugin2.net.ApiClient;
import com.sayi.demo_plugin2.net.HitokotoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Shiro
@Component
public class Hitokoto {

    @Autowired
    private ApiClient apiClient;

    /**
     * 一言命令处理器
     * 支持格式:
     *   一言          - 随机获取任意类型
     *   一言 a        - 指定类型 (a~l: 动画/漫画/游戏/文学/原创/网络/其他/影视/诗词/网易云/哲学/抖机灵)
     *   一言 诗词     - 按类型名称指定
     */
    @GroupMessageHandler
    @MessageHandlerFilter(cmd = "一言(.*)")
    public void hitokoto(Bot bot, GroupMessageEvent event) {
        String msg = event.getMessage();
        // 提取 "一言" 后面的参数
        String arg = msg.replaceFirst("^一言\\s*", "").trim();

        HitokotoResponse hitokoto;
        if (arg.isEmpty()) {
            // 无参数，随机获取
            hitokoto = apiClient.getRandomHitokoto();
        } else {
            // 有参数，先尝试按类型字母解析
            String typeCode = resolveTypeCode(arg);
            hitokoto = apiClient.getRandomHitokoto(typeCode);
        }

        if (hitokoto != null && hitokoto.getHitokoto() != null) {
            String reply = hitokoto.toFormattedMessage();
            bot.sendGroupMsg(event.getGroupId(), reply, false);
        } else {
            bot.sendGroupMsg(event.getGroupId(), "获取一言失败，请稍后再试~", false);
        }
    }

    /**
     * 将用户输入解析为类型代码
     * 支持中文类型名和字母代码
     */
    private String resolveTypeCode(String input) {
        return switch (input) {
            case "a", "动画" -> "a";
            case "b", "漫画" -> "b";
            case "c", "游戏" -> "c";
            case "d", "文学", "小说" -> "d";
            case "e", "原创" -> "e";
            case "f", "网络" -> "f";
            case "g", "其他" -> "g";
            case "h", "影视" -> "h";
            case "i", "诗词" -> "i";
            case "j", "网易云" -> "j";
            case "k", "哲学" -> "k";
            case "l", "抖机灵" -> "l";
            default -> input; // 原样传递，让 API 自行处理
        };
    }
}
