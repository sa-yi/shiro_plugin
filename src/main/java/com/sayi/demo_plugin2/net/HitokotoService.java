package com.sayi.demo_plugin2.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 一言 API 的 Retrofit 服务接口
 * 文档: https://developer.hitokoto.cn/sentence/
 */
public interface HitokotoService {

    /**
     * 获取随机一言
     *
     * @param c         句子类型 (a~l)，可选，不传则随机所有类型
     * @param encode    返回编码: json / text，默认 json
     * @param minLength 句子最小长度
     * @param maxLength 句子最大长度
     * @return 一言响应
     */
    @GET("./")
    Call<HitokotoResponse> getHitokoto(
            @Query("c") String c,
            @Query("encode") String encode,
            @Query("min_length") Integer minLength,
            @Query("max_length") Integer maxLength
    );

    /**
     * 获取随机一言（默认 JSON 格式，不限类型）
     */
    @GET("./")
    Call<HitokotoResponse> getHitokoto();
}
