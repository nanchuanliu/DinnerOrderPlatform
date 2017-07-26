package com.lzw.weixin.action;

import com.lzw.weixin.Utils.MenuUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/25.
 */
public class MenuAction extends ActionSupport{
    @Override
    public String execute() throws Exception {
        MenuUtil.deleteMenu();
        MenuUtil.createMenu(MenuUtil.getMenu("wx2d080b58edae0b82"));
        return SUCCESS;
    }
}
