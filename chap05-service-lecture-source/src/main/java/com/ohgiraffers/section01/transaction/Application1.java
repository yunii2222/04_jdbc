package com.ohgiraffers.section01.transaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {

        /* 1. Connection은 autoCommit이 default 설정이다.
        * 자동 커밋 설정에서 DML(insert, update, delete) 수행 시 바로 반영 된다. */
        Connection con = getConnection();

        /* Connection이 가지고 있는 getAutoCommit() 메소드 */
        try {
            System.out.println("autoCommit의 현재 설정 값 : " + con.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ptsm = null;
        int result = 0;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("insertMenu");

            ptsm = con.prepareStatement(query);
            ptsm.setString(1, "정어리 비빔밥");
            ptsm.setInt(2, 50000);
            ptsm.setInt(3, 4);
            ptsm.setString(4, "Y");

            // 쿼리 실행 꼭!! 넣어주기
            result = ptsm.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(ptsm);
            close(con);
        }
        if (result > 0) {
            System.out.println("메뉴 등록 성공!");
        }else {
            System.out.println("메뉴 등록 실패ㅠㅠ");
        }
    }
}
