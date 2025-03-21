package com.sayi.demo_plugin;


import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class DemoPlugin extends BotPlugin {
    private static final Logger log = LoggerFactory.getLogger(DemoPlugin.class);

    static {
        try{
            Class.forName("com.zhipu.oapi.ClientV4");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {
        String msg = event.getRawMessage();
        if (msg.equals("ping")) {
            bot.sendGroupMsg(event.getGroupId(), "pong", false);
            return MESSAGE_BLOCK;
        }
        if(msg.startsWith("/聊天")){
            chat(bot,event);
        }
        return MESSAGE_IGNORE;
    }


    private static final String API_KEY = System.getenv("GLM_API_TOKEN");

    private static List<ChatTool> tools;
    private static List<ChatMessage> completionPayload;
    private static ObjectMapper MAPPER = new ObjectMapper();
    private static ModelApiResponse call_api(ChatMessage input) {
        completionPayload.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), "你是一个人工智能助手"));
        completionPayload.add(input);


        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("glm-4v")
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(completionPayload)
                .build();


        ModelApiResponse response = client.invokeModelApi(chatCompletionRequest);

        return response;
    }
    static {
        completionPayload = new ArrayList<>();
    }

    private static final ClientV4 client = new ClientV4.Builder(API_KEY)
            .enableTokenCache()
            .networkConfig(30, 10, 10, 10, TimeUnit.SECONDS)
            .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
            .build();

    private void chat(Bot bot, GroupMessageEvent event){
        String content=event.getRawMessage().substring(3);
        log.info(content);
        ChatMessage message = new ChatMessage(ChatMessageRole.USER.value(), content);
        ModelApiResponse response = call_api(message);
        if(response.isSuccess()) {
            ModelData modelData = response.getData();
            Choice choice = modelData.getChoices().get(0);
            if(choice.getFinishReason().equals("stop")) {
                String msg= (String) choice.getMessage().getContent();
                bot.sendGroupMsg(event.getGroupId(), msg, false);
            }
        }else {
            bot.sendGroupMsg(event.getGroupId(),response.toString(),false);
        }
    }
}