package com.ohgiraffers.section03.delete;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 메뉴 번호 : ");
        int menuCode = sc.nextInt();

        Connection con = getConnection();
        PreparedStatement ptst = null;
        int result = 0;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            /* updateMenu라는 key값을 쿼리문자열로 꺼내오기 */
            String query = prop.getProperty("deleteMenu");

            ptst = con.prepareStatement(query);
            ptst.setInt(1, menuCode);

            result = ptst.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(ptst);
            close(con);
        }
        if (result > 0) {
            System.out.print("메뉴 삭제 성공!");
        }else {
            System.out.print("메뉴 삭제 실패ㅠㅠ");
        }
    }
}
