package com.lzw.weixin.menu;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class ComplexButton extends Button {

    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }

}
