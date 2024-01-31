package com.ohgiraffers.section01.transaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {
        /* 3. 수동 커밋 설정에서 DML(insert, update, delete) 수행 후 commit을 호출하던 DB에 반영되고,
        *  rollback()
        *  을 호출하면 이전 commit() 상태로 돌아간다.*/
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

            /* 내부로 옮겨주고, con. 수정후에 메뉴 등록 완료 */
            if (result > 0) {
                System.out.println("메뉴 등록 성공!");
                con.commit();
            }else {
                System.out.println("메뉴 등록 실패ㅠㅠ");
                con.rollback();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(ptsm);
            close(con);
        }
    }
}
