package com.ohgiraffers.section02.update;

import com.ohgiraffers.model.dto.MenuDTO;

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
        System.out.print("변경할 메뉴 번호 입력 : ");
        int menuCode = sc.nextInt();
        System.out.print("변경할 메뉴 이름 입력 : ");
        sc.nextLine();
        String menuName = sc.nextLine();
        System.out.print("변경할 메뉴 가격 입력 : ");
        int menuPrice = sc.nextInt();

        MenuDTO changeMenu = new MenuDTO();

        changeMenu.setCode(menuCode);
        changeMenu.setName(menuName);
        changeMenu.setPrice(menuPrice);

        /* 필요한코드들 미리 작성 */
        Connection con = getConnection();
        PreparedStatement ptst = null;
        int result = 0;
        /* 파일 가져오는 구문 */
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            /* updateMenu라는 key값을 쿼리문자열로 꺼내오기 */
            String query = prop.getProperty("updateMenu");

            /* 미리 준비할 쿼리문 작성 */
            ptst = con.prepareStatement(query);
            ptst.setString(1, changeMenu.getName());
            ptst.setInt(2, changeMenu.getPrice());
            ptst.setInt(3, changeMenu.getCode());

            result = ptst.executeUpdate();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ptst);
            close(con);
        }
        /* 변경이 되었는지 안되었는지 알기위한 구문 */
        if (result > 0) {
            System.out.printf("메뉴 변경 성공!");
        } else {
            System.out.printf("메뉴 변경 실패ㅠㅠ");
        }
    }
}
