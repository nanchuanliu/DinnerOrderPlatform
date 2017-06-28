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
    <script type="text/javascript" src="js/jquery/jquery.fly.min.js"></script>

    <script type="text/javascript">

        $(function () {
            var cart = $("#ShoppingCart");
            var offset = cart.offset();
            var offsetX = offset.left + cart.width() / 2;
            var offsetY = offset.top + cart.height() / 2;

            $("input[type=checkbox]").click(function (event) {
                var count = 0;
                var badge = $(".badge");
                var html = badge.html();
                if (html != "") {
                    count = parseInt(html);
                }

                var checked = event.target.checked;
                if (checked) {
                    count++;
                    var dish = $(this);
                    var img = $(this).parent().find("img").attr("src");
                    var flyer = $("<img class='u-flyer' src='" + img + "'/>");
                    flyer.fly({
                        start: {
                            left: event.pageX,
                            top: event.pageY
                        },
                        end: {
                            left: offsetX,
                            top: offsetY,
                            width: 0,
                            height: 0
                        },
                        onEnd: function () {
                            this.destroy();
                        }
                    })
                } else {
                    count--;
                }

                $(badge).html(count);
                if (count > 0) {
                    badge.show();
                } else {
                    badge.hide();
                }
            })
        });
    </script>

    <style>
        .bar {
            height: 120px;
        }

        /*        .bar-nav span,
                .bar-nav h1
                {
                    font-size:60px;
                }*/

        .bar-tab span,
        .bar-tab button {
            display: inline-block;
            font-size: 40px;
            color: #ffffff;
        }

        .bar-tab .tab-item .icon {
            font-size: 50px;
            width:inherit;
            height:inherit;
        }

        /*        .bar-nav ~ .content{
                    top: 120px;
                }*/

        .bar-tab ~ .content {
            bottom: 120px;
        }

        .bar-tab .tab-item
        {
            display: inline;
        }

        .box {
            display: flex;
            justify-content: center;
            flex-direction: column;
        }

        #divCategory ul > li {
            font-size: 50px;
            padding: 10px;
        }

        #divCategory .list-group-title {
            font-size: 60px;
            font-weight: bold;
            /*            margin: 10px;*/
        }

        ul {
            padding: 10px;
        }

        .item-subtitle {
            font-size: 10px;
            color: green;
            font-weight: bold;
        }

        .u-flyer {
            display: block;
            width: 50px;
            height: 50px;
            border-radius: 50px;
            position: fixed;
            z-index: 9999;
        }

        .image {
            width: 200px;
            height: 100px;
            float: right;
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
            <a class="tab-item" href="#" style="height: 100%;width: 80px;">
                <span class="icon icon-cart open-panel" id="ShoppingCart" style=""></span>
                <span class="badge" style="display: none;">0</span>
            </a>
            <a class="tab-item">
                <span style="height: 100%;width: 120px;">¥0.00</span>
            </a>
            <a class="tab-item">
                <button class="button"
                        style="background-color: #2ac845;width: 260px;height:100%;top: 0;display: inline-block;right: -6px; ">
                    下一步
                </button>
            </a>

        </div>
    </nav>
    <div class="content row">
        <div class="col-33 list-block" id="divCategory">
            <ul>
                <li class="list-group-title">主菜单</li>
                <li class="item-content">
                    <div class="item-inner">
                        <div class="item-title">特色菜</div>
                    </div>
                </li>
                <li class="item-content">
                    <div class="item-inner">
                        <div class="item-title">川菜</div>
                    </div>
                </li>
                <li class="item-content">
                    <div class="item-inner">
                        <div class="item-title">湘菜</div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="col-66 list-block media-list" style="margin-left: 0;width: 66%;" id="divDish">
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
                        <img src="image/djyt.jpg" class="image"/>
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
                        <img src="image/hgr.jpg" class="image"/>
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
                        <img src="image/mpdf.jpg" class="image"/>
                    </label>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
