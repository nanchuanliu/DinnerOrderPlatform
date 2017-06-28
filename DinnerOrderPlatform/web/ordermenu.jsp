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
    <title>Title</title>
    <link rel="stylesheet" href="css/light7/light7.css"/>
</head>
<body>

<header class="bar bar-nav">
    <button class="button button-link button-nav pull-left">
        <span class="icon icon-left"></span>
        返回
    </button>
    <h1 class="title">点餐系统</h1>
</header>
<div style="display: flex;">
    <div style="flex: 1; height: 100%;display: inline-block;">
        <iframe width="100%" height="100%" src="category.jsp"></iframe>
    </div>
    <div style="flex: 2; height:100%;display: inline-block;">
        <iframe width="100%" width="100%" height="100%" src="dish_menu.jsp"></iframe>
    </div>

</div>
<nav class="bar bar-tab row">
    <%--<a class="tab-item " href="#"><span class="icon icon-right"></span></a>--%>
    <div class="col-100 pull-right" >
        <button class="button  col-25 pull-right " style="background-color: #2ac845;">
            下一步
        </button>
        <span class="col-10 pull-right" style="height: 100%;">¥0.00</span>
        <span class="icon icon-cart col-5 pull-right" style="height: 100%"></span>
    </div>
</nav>
</body>
</html>
