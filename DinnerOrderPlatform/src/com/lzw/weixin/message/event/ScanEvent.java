package com.lzw.weixin.message.event;

import com.lzw.weixin.message.req.BaseMessage;

/**
 * Description:扫描带参数二维码事件
 * Author: lzw
 * Date: 2017/6/23.
 */
public class ScanEvent extends BaseMessage {

    private String eventKey;

    private String ticket;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
