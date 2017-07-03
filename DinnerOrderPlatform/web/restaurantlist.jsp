<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.lzw.weixin.Utils.CommonUtil" %>
<%@ page import="com.lzw.weixin.servlet.InitServlet" %>
<%@ page import="com.lzw.weixin.pojo.SNSUserInfo" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/29
  Time: 7:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>饭店列表</title>

    <%
        Map<String, Object> ret = new HashMap<String, Object>();
        ret = CommonUtil.getWxConfig(request);
        request.setAttribute("appId", ret.get("appId"));
        request.setAttribute("timestamp", ret.get("timestamp"));
        request.setAttribute("nonceStr", ret.get("nonceStr"));
        request.setAttribute("signature", ret.get("signature"));
        request.setAttribute("BaiduApiKey", InitServlet.baiduApiKey);
    %>
    <link rel="stylesheet" href="css/light7/light7.min.css"/>
    <link rel="stylesheet" href="css/dinnerpicker.css"/>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="js/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="js/light7/light7.min.js" type="text/javascript"></script>
    <script src="js/dinnerpicker.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {

            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: '${appId}', // 必填，公众号的唯一标识
                timestamp: '${ timestamp}', // 必填，生成签名的时间戳
                nonceStr: '${ nonceStr}', // 必填，生成签名的随机串
                signature: '${ signature}',// 必填，签名，见附录1
                jsApiList: [
                    'getLocation' //获取地理位置接口
                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });

            wx.checkJsApi({
                jsApiList: [
                    'getLocation'
                ],
                success: function (res) {
                    //alert(JSON.stringify(res.checkResult.getLocation));
                    if (res.checkResult.getLocation == false) {
                        alert('你的微信版本太低，不支持微信JS接口，请升级到最新的微信版本！');
                        return;
                    }
                }
            });
            wx.ready(function () {
                $('#refreshLocation').click(getLocation);
                getLocation();
            });

            $("#divList ul li").click(function () {
                var url = "http://mylife.51vip.biz/oauthRedirect?url=orderseat.jsp";
                window.location.href = url;
            })
        });

        var count = 0;

        function getLocation() {
            wx.getLocation({
                success: function (res) {
                    var longitude = res.longitude;
                    var latitude = res.latitude;
                    var url = "http://api.map.baidu.com/geoconv/v1/?coords=" + longitude + "," + latitude + "&from=1&to=5&ak=${BaiduApiKey}";
                    $.ajax({
                        type: "GET",
                        url: url,
                        dataType: "JSONP",
                        success: function (data) {
                            var lgd = data.result[0].x;
                            var ltd = data.result[0].y;

                            var url = "http://api.map.baidu.com/geocoder/v2/?location=" + ltd + "," + lgd + "&output=json&ak=${BaiduApiKey}";
                            $.ajax({
                                type: "GET",
                                url: url,
                                dataType: "JSONP",
                                success: function (data) {
                                    var comp = data.result.addressComponent;
                                    var province = comp.province;
                                    var city = comp.city;
                                    var district = comp.district;
                                    var street = comp.street;
                                    var street_number = comp.street_number;
                                    var html = "当前：" + province + city + district + street + street_number;
                                    $("#spLocation").html(html);
                                    $("#spCurrentCity").html(city);
                                }
                            })
                        }
                    });
                },
                cancel: function (res) {
                    alert('用户拒绝授权获取地理位置');
                }
            });
        }
    </script>
    <style type="text/css">
        .box {
            display: flex;
            /*            justify-content: center;*/
            align-items: center;
        }

        .bar-nav ~ .content {
            top: 100px;
        }

        header span {
            height: 100px;
            line-height: 100px;
            font-size: 30px;
        }

        #translateCity {
            /*transform: translate(-50px,25px) scale(2,2);*/
            font-size: 40px;
            height: 70px;
            margin-top: 10px;
        }

        .popover.modal-in .popover-inner ul li {
            height: 80px;
            line-height: 80px;
        }

        .popover.modal-in .list-block .item-link.list-button {
            line-height: inherit;
        }

        .list-block {
            font-size: 40px;
        }

        #btnChoose {
            transform: scale(2, 2);
        }

        .content-block-title {
            font-size: 30px;
        }

        .list-block .item-text {
            color: #0d913c;
            font-size: inherit;
            margin-top: 40px;
            line-height: 50px;
        }

        .list-block .item-subtitle {
            font-size: 30px;
        }

        .content {
            margin-bottom: 60px;
        }

        .content .list-block ul {
            background-color: rgba(233, 235, 239, 0.39);
        }

        .content .list-block ul li {
            background-color: white;
        }

        .content .list-block ul li + li {
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="page page-current">
    <header class="bar bar-nav" style="height: 100px;">
        <span id="spCurrentCity" class="pull-left" style="width: 60%"></span>
        <button id="translateCity" class="button button-light pull-right open-popover" data-popup=".popup-city">切换城市
        </button>
    </header>
    <div class="content">
        <p style="padding: 30px 350px" id="btnChoose">
            <a class="button button-light dinnerpicker" style="font-size: 22px;">
                2017-06-30 晚市
                <span class="icon icon-down"></span>
            </a>
        </p>
        <div class="content-block-title">可选店铺</div>
        <div class="list-block media-list" id="divList">
            <ul>
                <li>
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <img src="image/hgr.jpg" style="width: 4rem;">
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">上海人民广场店</div>
                            </div>
                            <div class="item-subtitle">上海市黄浦区河南中路68号</div>
                            <div class="item-text">小桌 4 中桌 0 大桌 0 包房 1</div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <img src="image/hgr.jpg" style="width: 4rem;">
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">上海人民广场店</div>
                            </div>
                            <div class="item-subtitle">上海市黄浦区河南中路68号</div>
                            <div class="item-text">小桌 4 中桌 0 大桌 0 包房 1</div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <img src="image/hgr.jpg" style="width: 4rem;">
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">上海人民广场店</div>
                            </div>
                            <div class="item-subtitle">上海市黄浦区河南中路68号</div>
                            <div class="item-text">小桌 4 中桌 0 大桌 0 包房 1</div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <img src="image/hgr.jpg" style="width: 4rem;">
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">上海人民广场店</div>
                            </div>
                            <div class="item-subtitle">上海市黄浦区河南中路68号</div>
                            <div class="item-text">小桌 4 中桌 0 大桌 0 包房 1</div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <img src="image/hgr.jpg" style="width: 4rem;">
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">上海人民广场店</div>
                            </div>
                            <div class="item-subtitle">上海市黄浦区河南中路68号</div>
                            <div class="item-text">小桌 4 中桌 0 大桌 0 包房 1</div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <img src="image/hgr.jpg" style="width: 4rem;">
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">上海人民广场店</div>
                            </div>
                            <div class="item-subtitle">上海市黄浦区河南中路68号</div>
                            <div class="item-text">小桌 4 中桌 0 大桌 0 包房 1</div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <img src="image/hgr.jpg" style="width: 4rem;">
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">上海人民广场店</div>
                            </div>
                            <div class="item-subtitle">上海市黄浦区河南中路68号</div>
                            <div class="item-text">小桌 4 中桌 0 大桌 0 包房 1</div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="#" class="item-link item-content">
                        <div class="item-media">
                            <img src="image/hgr.jpg" style="width: 4rem;">
                        </div>
                        <div class="item-inner">
                            <div class="item-title-row">
                                <div class="item-title">上海人民广场店</div>
                            </div>
                            <div class="item-subtitle">上海市黄浦区河南中路68号</div>
                            <div class="item-text">小桌 4 中桌 0 大桌 0 包房 1</div>
                        </div>
                    </a>
                </li>
            </ul>
        </div>

    </div>
    <div class="bar bar-footer box" style="font-size: 30px;height: 80px;">
        <span class="pull-left" style="width: 100%" id="spLocation"></span>
        <a class="icon icon-refresh pull-right" href="#" id="refreshLocation"></a>
    </div>
</div>
<div class="popover  popup-city" style="display: none;top: 154px;left: 5px;">
    <div class="popover-angle on-top" style="left: 52px;"></div>
    <div class="popover-inner">
        <div class="list-block">
            <ul>
                <li><a href="#" class="list-button item-link">上海市</a></li>
                <li><a href="#" class="list-button item-link">杭州市</a></li>
                <li><a href="#" class="list-button item-link">苏州市</a></li>
                <li><a href="#" class="list-button item-link">南京市</a></li>
                <li><a href="#" class="list-button item-link">无锡市</a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
