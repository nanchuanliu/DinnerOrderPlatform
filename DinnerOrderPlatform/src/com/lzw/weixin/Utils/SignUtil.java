package com.lzw.weixin.Utils;

import javax.print.DocFlavor;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/24.
 */
public class SignUtil {

    private static String token="LzwWeixinPlatform";

    public static boolean checkSignature(String signature,String timestamp,String nonce)
    {
        String[] arr=new String[]{token,timestamp,nonce};
        Arrays.sort(arr);

        StringBuilder content=new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        MessageDigest md=null;
        String tmpStr=null;

        try {
            md=MessageDigest.getInstance("SHA-1");
            byte[] digest=md.digest(content.toString().getBytes());
            tmpStr=byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        content=null;
        return tmpStr!=null ? tmpStr.equalsIgnoreCase(signature):false;
    }

    private static String byteToStr(byte[] byteArray) {
        String strDigest="";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest+=byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte) {
        char[] digit={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] tempArr=new char[2];
        tempArr[0]=digit[(mByte>>>4) & 0X0F];
        tempArr[1]=digit[mByte & 0X0F];
        String str=new String(tempArr);
        return str;
    }


    public static String byteToHex(final byte[] hash)
    {
        Formatter formatter=new Formatter();
        for (byte b :
                hash) {
            formatter.format("%02x",b);
        }
        String result=formatter.toString();
        formatter.close();
        return result;
    }
}
