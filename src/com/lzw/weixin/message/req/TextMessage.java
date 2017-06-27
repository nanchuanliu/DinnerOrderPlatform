package com.lzw.weixin.message.req;

/**
 * Created by Administrator on 2017/6/23.
 */
public class TextMessage extends BaseMessage {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
