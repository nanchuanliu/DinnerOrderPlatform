package com.lzw.weixin.message.event;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/23.
 */
public class MenuEvent extends  BaseEvent {

    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
