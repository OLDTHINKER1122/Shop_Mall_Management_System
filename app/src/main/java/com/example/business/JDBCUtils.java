package com.example.business;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
public class JDBCUtils {
    private static final String TAG = "postgresql-party-JDBCUtils";

    private static String driver = "org.postgresql.Driver";// 驱动

    private static String dbName = "postgres";// 数据库名称

    private static String user = "postgres";// 用户名

    private static String password = "s1410932043";// 密码

    public static Connection getConn(){

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            Log.v("DB","加載驅動成功");
        }catch( ClassNotFoundException e) {
            Log.e("DB","加載驅動失敗");
        }

        try{

            String url = "jdbc:postgresql://database-2.cpzoutzf2kww.ap-southeast-2.rds.amazonaws.com:5432/postgres?user=postgres&password=s1410932043";
            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection(url);
            Log.v("DB","遠端連接成功");
        }catch (Exception e){
            Log.e("DB","遠端連接失敗");
            e.printStackTrace();

        }
        return connection;
    }
}
