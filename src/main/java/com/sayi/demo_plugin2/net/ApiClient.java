package com.sayi.demo_plugin2.net;

import jakarta.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求客户端，基于 Retrofit2 封装
 * 管理 API 服务实例，提供统一的网络请求入口
 */
@Component
public class ApiClient {

    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

    /** 一言 API 基础地址 */
    private static final String HITOKOTO_BASE_URL = "https://v1.hitokoto.cn/";

    /** 请求超时时间 (秒) */
    private static final long TIMEOUT_SECONDS = 10;

    private HitokotoService hitokotoService;

    @PostConstruct
    public void init() {
        // 日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                message -> log.debug("HTTP: {}", message)
        );
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        // OkHttp 客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        // Retrofit 实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HITOKOTO_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建服务实例
        hitokotoService = retrofit.create(HitokotoService.class);

        log.info("ApiClient 初始化完成, 一言 API 基础地址: {}", HITOKOTO_BASE_URL);
    }

    /**
     * 获取一言服务接口 (供外部直接调用)
     */
    public HitokotoService getHitokotoService() {
        return hitokotoService;
    }

    /**
     * 同步获取随机一言
     *
     * @return 一言响应，请求失败时返回 null
     */
    public HitokotoResponse getRandomHitokoto() {
        try {
            Call<HitokotoResponse> call = hitokotoService.getHitokoto();
            Response<HitokotoResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                log.debug("获取一言成功: {}", response.body().getHitokoto());
                return response.body();
            } else {
                log.warn("一言 API 返回异常: code={}, message={}",
                        response.code(), response.message());
                return null;
            }
        } catch (IOException e) {
            log.error("请求一言 API 失败", e);
            return null;
        }
    }

    /**
     * 同步获取指定类型的随机一言
     *
     * @param type 句子类型 (a~l)，为 null 则不限制类型
     * @return 一言响应，请求失败时返回 null
     */
    public HitokotoResponse getRandomHitokoto(String type) {
        try {
            Call<HitokotoResponse> call = hitokotoService.getHitokoto(type, "json", null, null);
            Response<HitokotoResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                log.debug("获取一言成功 [type={}]: {}", type, response.body().getHitokoto());
                return response.body();
            } else {
                log.warn("一言 API 返回异常: code={}, message={}",
                        response.code(), response.message());
                return null;
            }
        } catch (IOException e) {
            log.error("请求一言 API 失败 [type={}]", type, e);
            return null;
        }
    }

    /**
     * 同步获取随机一言 (限定长度范围)
     *
     * @param type      句子类型 (a~l)，为 null 则不限制类型
     * @param minLength 最小长度，为 null 则不限制
     * @param maxLength 最大长度，为 null 则不限制
     * @return 一言响应，请求失败时返回 null
     */
    public HitokotoResponse getRandomHitokoto(String type, Integer minLength, Integer maxLength) {
        try {
            Call<HitokotoResponse> call = hitokotoService.getHitokoto(type, "json", minLength, maxLength);
            Response<HitokotoResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                log.debug("获取一言成功: {}", response.body().getHitokoto());
                return response.body();
            } else {
                log.warn("一言 API 返回异常: code={}, message={}",
                        response.code(), response.message());
                return null;
            }
        } catch (IOException e) {
            log.error("请求一言 API 失败", e);
            return null;
        }
    }
}
