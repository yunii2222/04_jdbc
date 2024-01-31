package com.ohgiraffers.section01.transaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class Application4 {
    public static void main(String[] args) {
        /* 4. 2가지 이상의 DML이 하나의 논리적인 단위(트랜잭션)으로 묶일 경우
        * 모든 동작이 정상 수행 되었을 때 commit, 일부 동작이 비정상 수행 되었을 때는 rollback 처리하여
        * 트랜잭션을 제어한다.*/
        Connection con = getConnection();

        PreparedStatement ptsm1 = null;
        PreparedStatement ptsm2 = null;
        int result1 = 0;
        int result2 = 0;
        Properties prop = new Properties();

        /* Connection이 가지고 있는 getAutoCommit() 메소드 */
        try {
            System.out.println("autoCommit의 현재 설정 값 : " + con.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query1 = prop.getProperty("inserCategory");
            String query2 = prop.getProperty("insertMenu");

            ptsm1 = con.prepareStatement(query1);
            ptsm1.setString(1,"신카테고리");
            ptsm1.setObject(2, null);

            result1 = ptsm1.executeUpdate();

            System.out.println("result1 : " + result1);

            ptsm2 = con.prepareStatement(query2);
            ptsm2.setString(1, "초콜릿 비빔밥");
            ptsm2.setInt(2, 70000);
            ptsm2.setInt(3, 4); //존재하지 않는 category code 입력 시 오류 발생, 존재하는 category 입력 시 성공
            ptsm2.setString(4, "Y");

            // 쿼리 실행 꼭!! 넣어주기
            result2 = ptsm2.executeUpdate();

            System.out.println("result2 : " + result2);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(ptsm1);
            close(ptsm2);
        }
        if (result1 > 0 && result2 > 0) {
            System.out.printf("신규 카테고리와 메뉴 등록 성공!");
            /* JDBCTemplate에 메소드 생성해두었다. */
            commit(con);
        }else {
            System.out.printf("신규 카테고리와 메뉴 등록 실패ㅠㅠㅠㅠㅠㅠㅠ");
            rollback(con);
        }
        close(con);
    }
}
