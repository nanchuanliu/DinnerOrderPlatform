package com.lzw.weixin.message.req;

/**
 * Created by Administrator on 2017/6/23.
 */
public class ImageMessage extends BaseMessage {
    private String picUrl;
    private String mediaId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
