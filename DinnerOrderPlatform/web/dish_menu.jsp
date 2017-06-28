<%--
  Created by IntelliJ IDEA.
  User: LZW
  Date: 2017/06/26
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>菜单</title>
    <meta name="viewport" content="initial-scale=1,maximum-scale=1">
    <link rel="stylesheet" href="css/light7/light7.css"/>
    <script type="text/javascript" src="js/jquery/jquery.js" charset="utf-8"></script>
    <script type="text/javascript" src="js/light7/light7.js" charset="utf-8"></script>
    <style type="text/css">
        .item-subtitle {
            font-size: 10px;
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="page">

    <header class="bar bar-nav">
        <button class="button button-link button-nav pull-left">
            <span class="icon icon-left"></span>
            返回
        </button>
        <h1 class="title">点餐系统</h1>
    </header>

    <div class="content">
        <div class="list-block media-list">
            <ul>
                <li>
                    <label class="label-checkbox item-content">
                        <input type="checkbox" name="my-radio">
                        <div class="item-media">
                            <i class="icon icon-form-checkbox"></i>
                        </div>
                        <div class="item-inner">
                            <div class="item-title">剁椒鱼头</div>
                            <div class="item-subtitle">¥66.00/份</div>
                        </div>
                    </label>
                </li>
                <li>
                    <label class="label-checkbox item-content">
                        <input type="checkbox" name="my-radio">
                        <div class="item-media">
                            <i class="icon icon-form-checkbox"></i>
                        </div>
                        <div class="item-inner">
                            <div class="item-title">回锅肉</div>
                            <div class="item-subtitle">¥28.00/份</div>
                        </div>
                    </label>
                </li>
                <li>
                    <label class="label-checkbox item-content">
                        <input type="checkbox" name="my-radio">
                        <div class="item-media">
                            <i class="icon icon-form-checkbox"></i>
                        </div>
                        <div class="item-inner">
                            <div class="item-title">麻婆豆腐</div>
                            <div class="item-subtitle">¥18.00/份</div>
                        </div>
                    </label>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
