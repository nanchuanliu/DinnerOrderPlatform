package com.lzw.weixin.Utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class DBUtility {

    private static BasicDataSource dataSource=null;

    public DBUtility(){}

    public static void init()
    {
        Properties properties=new Properties();

        try {
            properties.load(DBUtility.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String url=properties.getProperty("url");
            String driver=properties.getProperty("driver");
            String username=properties.getProperty("username");
            String password=properties.getProperty("password");

            dataSource=new BasicDataSource();
            dataSource.setDriverClassName(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败！请检查设置！！！");
        }
    }


    public static synchronized Connection getConnection() throws SQLException {
        if(dataSource==null)
            init();

        Connection conn=null;
        if(dataSource!=null)
            conn=dataSource.getConnection();

        return conn;
    }


    public static void closeConnection(Connection conn)
    {
        if(conn!=null)
        {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("关闭资源失败！");
                e.printStackTrace();
            }
        }
    }

}
