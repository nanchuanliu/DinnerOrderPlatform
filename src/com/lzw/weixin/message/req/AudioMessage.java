package com.lzw.weixin.message.req;

/**
 * Created by Administrator on 2017/6/23.
 */
public class AudioMessage extends BaseMessage {

    private String mediaId;
    private String format;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
