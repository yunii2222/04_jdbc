package com.ohgiraffers.section01.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement ptst = null;
        int result = 0; // insert 된 row의 개수를 반환받을 변수

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            /* insert라는 key값을 쿼리문자열로 꺼내오기 */
            String query = prop.getProperty("insertMenu");

            ptst = con.prepareStatement(query);
            ptst.setString(1,"봉골레 청국장");
            ptst.setInt(2,5000);
            ptst.setInt(3,4);
            ptst.setString(4,"Y");

            /* 조회시에는 executeQuery() : ResultSet 메소드를 사용
            * 삽입, 수정, 삭제 시에는 executeUpdate() : int 메소드를 사용하여 수행 된 행의 갯수를 리턴 받음 */
            result = ptst.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(ptst);
            close(con);
        }
        /* executeUpdate() 메서드가 한 번 실행되어 한 행이 성공적으로 삽입되었다는 것을 의미 */
        System.out.print("result : " + result);
    }
}
