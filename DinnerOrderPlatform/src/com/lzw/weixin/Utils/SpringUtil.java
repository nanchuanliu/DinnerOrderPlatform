package com.lzw.weixin.Utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext=null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext==null)
        {
            SpringUtil.applicationContext=applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public static Object getBean(String name)
    {
        return getApplicationContext().getBean(name);
    }
}
