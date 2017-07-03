<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.lzw.weixin.Utils.CommonUtil" %>
<%@ page import="com.lzw.weixin.main.Sign" %><%--
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
    <link href="css/light7/light7.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery/jquery.js" ></script>
    <script type="text/javascript" src="js/light7/light7.js"></script>
    <script type="text/javascript">

        $(function () {
            var url="http://api.map.baidu.com/geocoder/v2/?location=31.23145,121.360045&output=json&ak=48cgBuyrEDEkOnGiPi8rpFDo8Do5vo9w";
            $.ajax({
                type:"GET",
                url:url,
                dataType:"JSONP",
                success:function (data) {
                    var comp=data.result.addressComponent;
                    var province=comp.province;
                    var city=comp.city;
                    var district=comp.district;
                    var street=comp.street;
                    var street_number=comp.street_number;

                }
            });

            $("#picker").picker({
                toolbarTemplate: '<header class="bar bar-nav">\
  <button class="button button-link pull-left">按钮</button>\
  <button class="button button-link pull-right close-picker">确定</button>\
  <h1 class="title">标题</h1>\
  </header>',
                cols: [
                    {
                        textAlign: 'center',
                        values: ['iPhone 4', 'iPhone 4S', 'iPhone 5', 'iPhone 5S', 'iPhone 6', 'iPhone 6 Plus', 'iPad 2', 'iPad Retina', 'iPad Air', 'iPad mini', 'iPad mini 2', 'iPad mini 3']
                    }
                ],
                convertToPopover:false
            });
        })

    </script>
</head>
<body>
<div class="page pageInit">
<input type="text" id='picker'/></div>
<script>

</script>
</body>
</html>
