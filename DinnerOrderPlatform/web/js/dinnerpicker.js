/**
 * Created by Administrator on 2017/7/1.
 */
$(function () {
    $(".dinnerpicker").picker({
        toolbarTemplate:'<header class="bar bar-nav">\
                                  <button class="button button-link pull-right close-picker">确定</button>\
                                  <h1 class="title">选择预定日期</h1></header>',
        cols:[
            {
                textAlign:"center",
                values:["2017-07-01  周六","2017-07-02  周日","2017-07-03  周一","2017-07-04  周二","2017-07-05  周三","2017-07-06  周四","2017-07-07  周五"]
            },
            {
                textAlign:"center",
                values:["午市","晚市"]
            }
        ],
        convertToPopover:false
    })
})