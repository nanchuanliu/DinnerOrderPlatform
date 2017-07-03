<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.lzw.weixin.Utils.CommonUtil" %>
<%@ page import="com.lzw.weixin.servlet.InitServlet" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/2
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>周边服务</title>
    <link rel="stylesheet" href="css/light7/light7.min.css"/>
    <script type="text/javascript" src="js/jquery/jquery.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <%
        Map<String, Object> ret = new HashMap<String, Object>();
        ret = CommonUtil.getWxConfig(request);
        request.setAttribute("appId", ret.get("appId"));
        request.setAttribute("timestamp", ret.get("timestamp"));
        request.setAttribute("nonceStr", ret.get("nonceStr"));
        request.setAttribute("signature", ret.get("signature"));
        request.setAttribute("BaiduApiKey", InitServlet.baiduApiKey);
    %>
    <script type="text/javascript">
        $(function () {

            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: '${appId}', // 必填，公众号的唯一标识
                timestamp: '${ timestamp}', // 必填，生成签名的时间戳
                nonceStr: '${ nonceStr}', // 必填，生成签名的随机串
                signature: '${ signature}',// 必填，签名，见附录1
                jsApiList: [
                    'getLocation', 'openLocation' //获取扫码
                ]
            });

            wx.ready(function () {
                wx.getLocation({
                    type : 'gcj02',
                    success: function (res) {
                        var longitude = res.longitude;
                        var latitude = res.latitude;

                        wx.openLocation({
                            latitude: latitude,
                            longitude: longitude
                        });
                    }
                });
            });
        });


    </script>
</head>
<body>

</body>
</html>
