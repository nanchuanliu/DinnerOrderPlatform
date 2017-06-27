package com.lzw.weixin.Utils;

import com.lzw.weixin.message.resp.AudioMessage;
import com.lzw.weixin.message.resp.ImageMessage;
import com.lzw.weixin.message.resp.TextMessage;
import com.lzw.weixin.message.resp.VideoMessage;
import com.lzw.weixin.message.resp.ArticleMessage;
import com.lzw.weixin.message.resp.Music;
import com.lzw.weixin.message.resp.MusicMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 消息处理工具类
 * Author: lzw
 * Date:  2017/6/24.
 */
public class MessageUtil {

    //消息请求类型
    public static final String REQ_MESSAGE_TYPE_TEXT="text";
    public static final String REQ_MESSAGE_TYPE_IMAGE="image";
    public static final String REQ_MESSAGE_TYPE_AUDIO="audio";
    public static final String REQ_MESSAGE_TYPE_VIDEO="video";
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO="shortvideo";
    public static final String REQ_MESSAGE_TYPE_LOCATION="location";
    public static final String REQ_MESSAGE_TYPE_LINK="link";

    //事件类型
    public static final String REQ_MESSAGE_TYPE_EVENT="event";
    public static final String EVENT_TYPE_SUBSCRIBE="subscribe";
    public static final String EVENT_TYPE_UNSUBSCRIBE="unsubscribe";
    public static final String EVENT_TYPE_SCAN="scan";
    public static final String EVENT_TYPE_LOCATION="LOCATION";
    public static final String EVENT_TYPE_CLICK="CLICK";

    //响应信息类型
    public static final String RESP_MESSAGE_TYPE_TEXT="text";
    public static final String RESP_MESSAGE_TYPE_IMAGE="image";
    public static final String RESP_MESSAGE_TYPE_AUDIO="audio";
    public static final String RESP_MESSAGE_TYPE_VIDEO="video";
    public static final String RESP_MESSAGE_TYPE_MUSIC="music";
    public static final String RESP_MESSAGE_TYPE_ARTICLE="article";


    @SuppressWarnings("unchecked")
    public static Map<String,String> parseXml(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,String> map=new HashMap<String,String>();

        InputStream stream=request.getInputStream();
        SAXReader reader=new SAXReader();
        Document document=reader.read(stream);

        Element root=document.getRootElement();
        List<Element> elements=root.elements();

        String msg="";

        for (Element ele:
             elements) {
            map.put(ele.getName(),ele.getText());

            msg+=ele.getName()+":"+ele.getText()+"\n";
        }

        System.out.println("接收消息：");
        System.out.println(msg);

        stream.close();
        stream=null;

        return map;
    }

    private static  XStream xstream=new XStream(new XppDriver(){

        public HierarchicalStreamWriter createWriter(Writer out)
        {
            return new PrettyPrintWriter(out){
                boolean cdata=true;

                @SuppressWarnings("unchecked")
                public void startNode(String name,Class className)
                {
                    super.startNode(name,className);
                }

                protected void writeText(QuickWriter writer,String text)
                {
                    if(cdata)
                    {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }else
                    {
                        writer.write(text);
                    }
                }
            };
        }
    });

    public static String messageToXml(TextMessage message)
    {
        xstream.alias("xml",message.getClass());
        return xstream.toXML(message);
    }

    public static String messageToXml(ImageMessage message)
    {
        xstream.alias("xml",message.getClass());
        return xstream.toXML(message);
    }

    public static String messageToXml(AudioMessage message)
    {
        xstream.alias("xml",message.getClass());
        return xstream.toXML(message);
    }

    public static String messageToXml(VideoMessage message)
    {
        xstream.alias("xml",message.getClass());
        return xstream.toXML(message);
    }

    public static String messageToXml(MusicMessage message)
    {
        xstream.alias("xml",message.getClass());
        return xstream.toXML(message);
    }

    public static String messageToXml(ArticleMessage message)
    {
        xstream.alias("xml",message.getClass());
        return xstream.toXML(message);
    }
}
