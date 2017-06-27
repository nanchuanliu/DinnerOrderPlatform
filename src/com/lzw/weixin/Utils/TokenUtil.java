package com.lzw.weixin.Utils;

import com.lzw.weixin.pojo.Token;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class TokenUtil {

    public static Token getToken() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select top 1 * from Token order by createTime desc ";
        String access_token = "";
        Token token = null;
        Integer expires_in = 0;

        try {
            con = DBUtility.getConnection();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                access_token = rs.getString("AccessToken");
                expires_in = rs.getInt("ExpiresIn");

                token = new Token();
                token.setAccessToken(access_token);
                token.setExpiresIn(expires_in);
            }
        } catch (SQLException e) {
            System.out.println("数据库操作异常：" + e.getMessage());
        } finally {
            DBUtility.closeConnection(con);
        }

        return token;
    }

    public static void saveToken(Token token) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "Insert into Token(AccessToken,ExpiresIn,CreateTime) Values(?,?,?)";

        try {
            conn = DBUtility.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, token.getAccessToken());
            stmt.setInt(2, token.getExpiresIn());
            long now = new Date().getTime();
            stmt.setTimestamp(3, new Timestamp(now));
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("数据库操作异常：" + e.getMessage());
        } finally {
            DBUtility.closeConnection(conn);
        }
    }

}
