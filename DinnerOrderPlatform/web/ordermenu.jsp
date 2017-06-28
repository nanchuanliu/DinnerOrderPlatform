<%--
  Created by IntelliJ IDEA.
  User: LZW
  Date: 2017/06/26
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>美食餐厅</title>
    <link rel="stylesheet" href="css/light7/light7.css"/>
    <link rel="stylesheet" href="css/light7-swiper.css"/>
    <script type="text/javascript" src="js/jquery/jquery.js"></script>
    <script type="text/javascript" src="js/light7/light7.js"></script>
    <script type="text/javascript" src="js/light7/light7-swiper.js"></script>

    <style>
        .bar{
            height: 120px;
        }

/*        .bar-nav span,
        .bar-nav h1
        {
            font-size:60px;
        }*/

        .bar-tab span,
        .bar-tab button
        {
            display: inline-block;
            font-size: 40px;
            color: #ffffff;
        }

        .bar .icon
        {
            font-size: 50px;
        }

/*        .bar-nav ~ .content{
            top: 120px;
        }*/

        .bar-tab ~ .content{
            bottom: 120px;
        }

        .box
        {
            display: flex;
            justify-content:center;
            flex-direction: column;
        }
    </style>
</head>
<body>
<div class="page page-current">
<%--    <header class="bar bar-nav box">
        <a class="icon icon-left pull-left " style="width: 160px;">
        </a>
        <h1 class="title">点餐系统</h1>
    </header>--%>

<nav class="bar bar-tab row box" style="display: inline-block;margin-left: 0px;background-color: black;">
    <%--<a class="tab-item " href="#"><span class="icon icon-right"></span></a>--%>
    <div class="pull-right" style="width: 480px;">
        <span class="icon icon-cart open-panel" id="ShoppingCart" style="height: 100%;width: 80px;"></span>
        <span style="height: 100%;width: 120px;">¥0.00</span>
        <button class="button" style="background-color: #2ac845;width: 260px;height:100%;top: 0;display: inline-block;right: -6px; ">
            下一步
        </button>
    </div>
</nav>
<div class="content">
    <div style="display: flex;">
        <div style="flex: 1; height: 100%;display: inline-block;">
            <iframe width="100%" height="100%" src="category.jsp"></iframe>
        </div>
        <div style="flex: 2; height:100%;display: inline-block;">
            <iframe width="100%" width="100%" height="100%" src="dish_menu.jsp"></iframe>
        </div>
    </div>
</div>

</div>
</body>
</html>
