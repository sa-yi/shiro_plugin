package com.sayi.demo_plugin2.net;

import com.google.gson.annotations.SerializedName;

/**
 * 一言 API 返回的 JSON 数据模型
 * API 文档: https://developer.hitokoto.cn/sentence/
 */
public class HitokotoResponse {

    /** 句子唯一 ID */
    @SerializedName("id")
    private long id;

    /** 句子 UUID */
    @SerializedName("uuid")
    private String uuid;

    /** 一言正文 */
    @SerializedName("hitokoto")
    private String hitokoto;

    /** 句子类型: a~l */
    @SerializedName("type")
    private String type;

    /** 出处来源 */
    @SerializedName("from")
    private String from;

    /** 作者/人物 */
    @SerializedName("from_who")
    private String fromWho;

    /** 添加者用户名 */
    @SerializedName("creator")
    private String creator;

    /** 添加者 UID */
    @SerializedName("creator_uid")
    private long creatorUid;

    /** 审核者 ID */
    @SerializedName("reviewer")
    private long reviewer;

    /** 提交来源 */
    @SerializedName("commit_from")
    private String commitFrom;

    /** 添加时间 (Unix 时间戳) */
    @SerializedName("created_at")
    private String createdAt;

    /** 句子长度 */
    @SerializedName("length")
    private int length;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(long creatorUid) {
        this.creatorUid = creatorUid;
    }

    public long getReviewer() {
        return reviewer;
    }

    public void setReviewer(long reviewer) {
        this.reviewer = reviewer;
    }

    public String getCommitFrom() {
        return commitFrom;
    }

    public void setCommitFrom(String commitFrom) {
        this.commitFrom = commitFrom;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /**
     * 获取类型的文字描述
     */
    public String getTypeDescription() {
        if (type == null) return "未知";
        return switch (type) {
            case "a" -> "动画";
            case "b" -> "漫画";
            case "c" -> "游戏";
            case "d" -> "文学";
            case "e" -> "原创";
            case "f" -> "网络";
            case "g" -> "其他";
            case "h" -> "影视";
            case "i" -> "诗词";
            case "j" -> "网易云";
            case "k" -> "哲学";
            case "l" -> "抖机灵";
            default -> "未知";
        };
    }

    /**
     * 格式化为可读的消息内容
     */
    public String toFormattedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(hitokoto);
        if (from != null && !from.isEmpty()) {
            sb.append("\n—— ").append(from);
            if (fromWho != null && !fromWho.isEmpty()) {
                sb.append(" · ").append(fromWho);
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "HitokotoResponse{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", hitokoto='" + hitokoto + '\'' +
                ", type='" + type + '\'' +
                ", from='" + from + '\'' +
                ", fromWho='" + fromWho + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorUid=" + creatorUid +
                ", reviewer=" + reviewer +
                ", commitFrom='" + commitFrom + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", length=" + length +
                '}';
    }
}
