package com.lzw.weixin.message.resp;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/23.
 */
public class AudioMessage extends BaseMessage {

    private Audio audio;

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }
}
