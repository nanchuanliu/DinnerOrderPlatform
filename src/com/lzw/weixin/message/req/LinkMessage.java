package com.lzw.weixin.message.req;

/**
 * Created by Administrator on 2017/6/23.
 */
public class LinkMessage extends BaseMessage {

    private String title;

    private String description;

    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
