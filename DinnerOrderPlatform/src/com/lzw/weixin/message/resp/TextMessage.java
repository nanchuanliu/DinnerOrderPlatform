package com.lzw.weixin.message.resp;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/23.
 */
public class TextMessage extends BaseMessage {

    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }
}
