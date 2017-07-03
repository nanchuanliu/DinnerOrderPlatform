<%@ page import="com.lzw.weixin.pojo.SNSUserInfo" %>
<%@ page import="com.lzw.weixin.Utils.CommonUtil" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/1
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订位</title>
    <link rel="stylesheet" href="css/light7/light7.min.css" />
    <link rel="stylesheet" href="css/dinnerpicker.css" />
    <script type="text/javascript" src="js/jquery/jquery.min.js" ></script>
    <script type="text/javascript" src="js/light7/light7.min.js"></script>
    <script src="js/dinnerpicker.js" type="text/javascript"></script>
    <%
        SNSUserInfo info= (SNSUserInfo) request.getAttribute("snsUserInfo");
    %>
    <script type="text/javascript">
        $(function () {
            $(".capacitypicker").picker({
                toolbarTemplate:'<header class="bar bar-nav">\
                                  <button class="button button-link pull-right close-picker">确定</button>\
                                  <h1 class="title">选择桌别</h1></header>',
                cols:[
                    {
                        textAlign:"center",
                        values:["小桌","中桌","大桌","包房"]
                    }
                ],
                convertToPopover:false
            });
            $(".numpicker").picker({
                toolbarTemplate:'<header class="bar bar-nav">\
                                  <button class="button button-link pull-right close-picker">确定</button>\
                                  <h1 class="title">选择人数</h1></header>',
                cols:[
                    {
                        textAlign:"center",
                        values:["1人","2人","3人","4人","5人","6人","7人","8人","9人","10人"]
                    }
                ],
                convertToPopover:false
            })

            $("#txtName").val('<%=info.getNickname()%>')
        })
    </script>
    <style type="text/css">
        .bar {
            height: 120px;
        }

        header span{
            height: 100px;
            line-height: 100px;
            font-size: 50px;
            font-weight: bold;;
        }

        .bar-nav ~ .content{
            margin-top:30px;
        }

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

        .bar-tab .tab-item
        {
            display: inline;
        }

        .box {
            display: flex;
            justify-content: center;
            flex-direction: column;
        }

        .list-block,
        .item-input textarea{
            font-size: 48px;
        }

        .content-block-title{
            font-size: 40px;
        }

        .list-block .item-title.label{
            width: 25%;
        }
    </style>
</head>
<body>
    <div class="page page-current">
        <header class="bar bar-nav" style="height: 100px;">
            <span>上海人民广场店</span>
        </header>
        <nav class="bar bar-tab row box" style="display: inline-block;margin-left: 0px;background-color: black;">
            <%--<a class="tab-item " href="#"><span class="icon icon-right"></span></a>--%>
            <div class="pull-right" >
                <a class="tab-item">
                    <button class="button"
                            style="background-color: #2ac845;width: 260px;height:100%;top: 0;display: inline-block;right: -6px; ">
                        提交订单
                    </button>
                </a>

            </div>
        </nav>
        <div class="content">
            <div class="content-block-title">订单信息</div>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-clock"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">日期</div>
                                <div class="item-input">
                                    <input  value="2017-07-01 周六,晚市" class="dinnerpicker"/>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-share"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">桌别</div>
                                <div class="item-input">
                                    <input  value="小桌" class="capacitypicker"/>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-friends"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">人数</div>
                                <div class="item-input">
                                    <input  value="1人" class="numpicker"/>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block-title">联系人信息</div>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-phone"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">手机</div>
                                <div class="item-input">
                                    <input value=""/>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-card"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">称呼</div>
                                <div class="item-input">
                                    <input  value="" id="txtName"/>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block-title">备注</div>
            <div class="item-content">
                <div class="item-input">
                    <textarea rows="3" style="width: 90%;margin: 10px 5%;"></textarea>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
