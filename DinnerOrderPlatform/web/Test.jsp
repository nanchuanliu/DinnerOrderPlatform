<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/25
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
        wx.config({
            debug:false,
            appId:'${appId}',
            timestamp:'${timestamp}',
            nonceStr:'${nonceStr}',
            signature:'${signature}',
            jsApiList:['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone']
        });

        wx.ready(function(){
            wx.onMenuShareTimeline({
                title:'测试文章',
                link:"",
                imgUrl:""
            });

            wx.onMenuShareAppMessage({
                title:"分享朋友圈",
                desc:"朋友圈",
                link:"",
                type:"link"
            });


        });
    </script>
</head>
<body>
    测试
</body>
</html>
