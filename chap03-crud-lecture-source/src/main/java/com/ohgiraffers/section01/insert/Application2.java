package com.ohgiraffers.section01.insert;

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

public class Application2 {
    public static void main(String[] args) {
        /* 사용자 입력 값으로 insert */
        Scanner sc = new Scanner(System.in);
        System.out.print("메뉴 이름 입력 : ");
        String menuName = sc.nextLine();
        System.out.print("메뉴 가격 입력 : ");
        int menuPrice = sc.nextInt();
        System.out.print("카테고리 코드 입력 : ");
        int categoryCode = sc.nextInt();
        System.out.print("판매 여부 (Y/N) 입력 : ");
        sc.nextLine();
        String orderableStatus = sc.nextLine().toUpperCase();

        /* 다른 클래스의 메소드 호출 등에서 값을 뭉쳐서 보내기 위해서 DTO에 담고 전송 */
        MenuDTO newMenu = new MenuDTO();

        newMenu.setName(menuName);
        newMenu.setPrice(menuPrice);
        newMenu.setCategoryCode(categoryCode);
        newMenu.setOrderableStatus(orderableStatus);

        Connection con = getConnection();
        PreparedStatement ptst = null;
        int result = 0; // insert 된 row의 개수를 반환받을 변수

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            /* insertMenu라는 key값을 쿼리문자열로 꺼내오기 */
            String query = prop.getProperty("insertMenu");

            ptst = con.prepareStatement(query);
            ptst.setString(1,newMenu.getName());
            ptst.setInt(2,newMenu.getPrice());
            ptst.setInt(3,newMenu.getCategoryCode());
            ptst.setString(4,newMenu.getOrderableStatus());

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

        if (result > 0) {
            System.out.printf("메뉴 등록 완료!");
        }else {
            System.out.printf("메뉴 등록 실패!");
        }
    }
}
