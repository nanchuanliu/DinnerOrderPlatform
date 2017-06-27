package com.lzw.weixin.message.resp;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/23.
 */
public class VideoMessage extends BaseMessage {

    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
