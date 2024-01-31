package com.ohgiraffers.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {

    public static Connection getConnection() {
        Properties prop = new Properties();
        Connection con = null;

        try {
            prop.load(new FileReader("src/main/java/com/ohgiraffers/config/connection-info.properties"));

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");

            Class.forName(driver);

            con = DriverManager.getConnection(url, prop);

            /* Ccnnection 객체의 default 설정은 auto commit이나 트랜젝션 관리를 위해 auto commit : false로 설정한다.
            *   */
            con.setAutoCommit(false);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /* 커넥션을 반환 */
        return con;
    }
    /* Connection을 수행하는 메소드 */
    public static void close(Connection con) {
        /* null이 아니고, 닫혀있지 않은 상태일때 */
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* Statement를 수행하는 메소드 */
    public static void close(Statement stmt) {
        /* null이 아니고, 닫혀있지 않은 상태일때 */
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* ResultSet를 수행하는 메소드 */
    public static void close(ResultSet rset) {
        /* null이 아니고, 닫혀있지 않은 상태일때 */
        try {
            if (rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* commit 을 수행하는 메소드 */
    public static void commit(Connection con){
        try {
            if (con != null && !con.isClosed()){
                con.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* rollback  을 수행하는 메소드 */
    public static void rollback(Connection con){
        try {
            if (con != null && !con.isClosed()){
                con.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
