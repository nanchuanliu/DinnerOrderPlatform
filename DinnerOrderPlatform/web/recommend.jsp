<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/2
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>特色推荐</title>
    <link rel="stylesheet" href="css/light7/light7.min.css"/>
    <script type="text/javascript" src="js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="js/light7/light7.min.js"></script>
    <script type="text/javascript">
        var loading = false;
        var maxItems = 10;
        var itemsPerLoad = 3;
        var lastIndex = 3;
        var htmlTemplate;
        $(function () {
            htmlTemplate = $("#divTemplate").html();
            $(document).on("infinite", ".infinite-scroll", function () {
                if (loading)
                    return;

                loading = true;
                setTimeout(function () {
                    loading = false;
                    addItems(itemsPerLoad, lastIndex);
                    lastIndex = $("#divContent").children().length;

                    if (lastIndex >= maxItems) {
                        $.detachInfiniteScroll($(".infinite-scroll"));
                        $(".infinite-scroll-preloader").remove();
                        return;
                    }

                }, 1000);
            })
        })

        function addItems(number, last) {
            for (var i = last + 1;i<=Math.min(last+number,maxItems); i++) {
                $("#divContent").append(htmlTemplate)
            }
        }
    </script>
    <style type="text/css">
        .content-block-title {
            font-size: 36px;
        }

        .card {
            font-size: 30px;
        }

        .card-content-inner {
            text-indent: 2em;
        }

        .infinite-scroll-preloader .preloader {
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
<div class="page page-current">
    <div class="content infinite-scroll" data-distance="150">
        <div id="divContent">
            <div id="divTemplate">
                <div>
                    <div class="content-block-title">剁椒鱼头</div>
                    <div class="card">
                        <div valign="bottom" class="card-header color-white no-border no-padding">
                            <img class="card-cover" src="image/djyt.jpg"/>
                        </div>
                        <div class="card-content">
                            <div class="card-content-inner">
                                <p>剁椒鱼头（英文名：Chop bell pepper fish
                                    head）是湖南省的传统名菜，属于湘菜系。据传，起源和清代文人黄宗宪有关。通常以鳙鱼鱼头、剁椒为主料，配以豉油、姜、葱、蒜等辅料蒸制而成。菜品色泽红亮、味浓、肉质细嫩。肥而不腻、口感软糯、鲜辣适口。</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <div class="content-block-title">回锅肉</div>
                <div class="card">
                    <div valign="bottom" class="card-header color-white no-border no-padding">
                        <img class="card-cover" src="image/hgr.jpg"/>
                    </div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            <p>回锅肉是一种烹调猪肉的四川传统菜式，属于川菜系。起源四川农村地区。古代时期称作油爆锅；四川地区大部分家庭都能制作。回锅肉的特点是：口味独特，色泽红亮，肥而不腻，入口浓香。所谓回锅，就是再次烹调的意思。回锅肉在川菜中的地位是非常重要的。回锅肉一直被认为是川菜之首，川菜之化身，提到川菜必然想到回锅肉。它色香味俱全，颜色养眼，是下饭菜之首选。</p>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <div class="content-block-title">麻婆豆腐</div>
                <div class="card">
                    <div valign="bottom" class="card-header color-white no-border no-padding">
                        <img class="card-cover" src="image/mpdf.jpg"/>
                    </div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            <p>
                                麻婆豆腐是四川省汉族传统名菜之一，属于川菜系。主要原料为配料和豆腐，材料主要有豆腐、牛肉末（也可以用猪肉）、辣椒和花椒等。麻来自花椒，辣来自辣椒，这道菜突出了川菜“麻辣”的特点。其口味独特，口感顺滑，流传甚广。</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="infinite-scroll-preloader">
            <div class="preloader"></div>
        </div>
    </div>
</div>
</body>
</html>
