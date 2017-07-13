package com.lzw.order.dinnerorderapp.utils;

import java.util.List;

/**
 * Created by LZW on 2017/07/13.
 */

public class CommonUtil {
    public static String Join(List<String> list) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (i != 0)
                buffer.append(",");
            buffer.append(str);
        }

        return buffer.toString();
    }
}
