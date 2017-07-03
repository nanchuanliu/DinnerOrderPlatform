package com.lzw.weixin.Services;

import com.lzw.weixin.Utils.MessageUtil;
import com.lzw.weixin.message.resp.TextMessage;
import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/24.
 */
public class CoreService {

    public static String processRequest(HttpServletRequest request) {
        String respXml = null;

        String respContent = "未知的消息类型！";

        try {
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            String fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            String msgType = requestMap.get("MsgType");

            TextMessage textMessage = new TextMessage();
            textMessage.setFromUserName(toUserName);
            textMessage.setToUserName(fromUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respContent = "您发送的是文本消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_AUDIO)) {
                respContent = "您发送的是语音消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                respContent = "您发送的是小视频消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置信息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");
                String eventKey=requestMap.get("EventKey");

                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

                } else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {

                } else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {

                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    String keyName="";
                    switch (eventKey)
                    {
                        case "14":
                            keyName="叫号查询";
                            break;
                    }

                    respContent="新功能"+keyName+"开发中！敬请期待！";
                }
            }

            textMessage.setContent(respContent);
            respXml = MessageUtil.messageToXml(textMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return respXml;
    }
}
