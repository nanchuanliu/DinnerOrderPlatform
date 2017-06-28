<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/29
  Time: 7:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订位</title>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"> </script>
    <script src="js/jquery/jquery.js" type="text/javascript"/>
    <script type="text/javascript">
        wx.config({
            debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: '${appId}', // 必填，公众号的唯一标识
            timestamp: '${ timestamp}' , // 必填，生成签名的时间戳
            nonceStr: '${ nonceStr}', // 必填，生成签名的随机串
            signature: '${ signature}',// 必填，签名，见附录1
            jsApiList: [
                'getLocation' //获取地理位置接口
            ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        wx.ready(function () {
            document.querySelector('#getLocation').onclick = function () {
                wx.getLocation({
                    success: function (res) {
                        alert(JSON.stringify(res));
                    },
                    cancel: function (res) {
                        alert('用户拒绝授权获取地理位置');
                    }
                });
            };
        })
    </script>
</head>
<body>
<span class="desc" style="color: red">地理位置接口-获取地理位置接口</span><br>
<button class="btn btn_primary" id="getLocation">getLocation</button><br>
</body>
</html>
