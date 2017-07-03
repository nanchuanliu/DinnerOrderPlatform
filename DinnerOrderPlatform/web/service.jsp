<%@ page import="com.lzw.weixin.Utils.CommonUtil" %>
<%@ page import="com.lzw.weixin.servlet.InitServlet" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/2
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>餐桌扫描</title>
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
                    'scanQRCode' //获取扫码
                ]
            });

            wx.ready(function () {
                $('#btnOrder').click(orderDish);
                $("#btnPay").click(payment);
                $("#btnCall").click(callWaiter)
            });
        });

        function orderDish() {
            wx.scanQRCode({
                needResult: 1,
                scanType: ["qrCode", "barCode"],
                success: function (res) {
                    var result = res.resultStr;
                    alert("点菜员马上为您服务！" + result);
                }
            })
        }

        function payment() {
            wx.scanQRCode({
                needResult: 1,
                scanType: ["qrCode", "barCode"],
                success: function (res) {
                    var result = res.resultStr;
                    alert("已结账！" + result);
                }
            })
        }

        function callWaiter() {
            wx.scanQRCode({
                needResult: 1,
                scanType: ["qrCode", "barCode"],
                success: function (res) {
                    var result = res.resultStr;
                    alert("已收到您的呼叫！" + result);
                }
            })
        }
    </script>
    <style type="text/css">

        .list-block .item-title {
            font-size: 40px;
        }

        .list-block .item-subtitle {
            font-size: 20px;
        }

        .content .list-block ul {
            background-color: rgba(233, 235, 239, 0.39);
        }

        .content .list-block ul li {
            background-color: white;
            padding: 20px;
        }

        .content .list-block ul li + li {
            margin-top: 30px;
        }

        .icon {
            font-size: 60px;
            width: 100px;
            height: 100px;
            border-radius: 50px;
            padding-left: 20px;
        }

        .icon:not(.icon-right) {
            color: #ffffff;
        }

        .icon.icon-right {
            margin-right: 50px;
        }

    </style>
</head>
<body>
<div class="page page-current">
    <div class="content">
        <div class="list-block media-list" id="divService">
            <ul>
                <li id="btnOrder">
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <i class="icon icon-edit" style="background-color: #fd4664;"></i>
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">店内点菜</div>
                            </div>
                            <div class="item-subtitle">自助下单，无需等待</div>
                        </div>
                        <i class="icon icon-right"></i>
                    </a>
                </li>
                <li id="btnPay">
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <i class="icon icon-gift" style="background-color: #00cca7"></i>
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">结账</div>
                            </div>
                            <div class="item-subtitle">微信支付，方便快捷</div>
                        </div>
                        <i class="icon icon-right"></i>
                    </a>
                </li>
                <li id="btnCall">
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <i class="icon icon-phone" style="background-color: #00a1fc"></i>
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">呼叫服务员</div>
                            </div>
                            <div class="item-subtitle">需要帮忙，随传随到</div>
                        </div>
                        <i class="icon icon-right"></i>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
