package com.ohgiraffers.section01.dao;

import com.ohgiraffers.section01.dto.CategoryDTO;
import com.ohgiraffers.section01.dto.MenuDTO;
import com.ohgiraffers.section01.dto.OrderDTO;
import com.ohgiraffers.section01.dto.OrderMenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;

/* DAO(Database Access Object) : 데이터베이스 접근용 객체
* crud 연산을 담당하는 메소드들의 집합으로 이루어진 클래스 */

/* 별도의 메소드 생성 */
public class OrderDAO {
    private Properties prop = new Properties();
    public OrderDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/order-query.xml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 1. 하위 카테고리 조회하는 메소드
    * 생성되어 있는 Connection con을 매개변수로 전달받아 사용해준다. */
    public List<CategoryDTO> selectAllCategory(Connection con){

        PreparedStatement ptsm = null;
        /* 조회하는 동작 */
        ResultSet rset = null;
        List<CategoryDTO> categoryDTOList = null;
        String query = prop.getProperty("selectAllCategoryList");

        try {
            ptsm = con.prepareStatement(query);
            rset = ptsm.executeQuery();
            categoryDTOList = new ArrayList<>();

            while (rset.next()){
                CategoryDTO categoryDTO = new CategoryDTO();

                categoryDTO.setCategoryCode(rset.getInt("category_code"));
                categoryDTO.setCategoryName(rset.getString("category_name"));

                categoryDTOList.add(categoryDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(ptsm);
        }

        return categoryDTOList;
    }

    public List<MenuDTO> selectMenuByCategoryCode(Connection con, int categoryCode) {
        PreparedStatement ptsm = null;
        /* 조회하는 동작 */
        ResultSet rset = null;
        List<MenuDTO> menuList = null;
        String query = prop.getProperty("selectMenuByCategoryCode");

        try {
            ptsm = con.prepareStatement(query);
            /* 카테고리 코드 위치홀더 처리 */
            ptsm.setInt(1,categoryCode);
            rset = ptsm.executeQuery();
            menuList = new ArrayList<>();

            while (rset.next()){
                MenuDTO menu = new MenuDTO();

                menu.setMenuCode(rset.getInt("menu_code"));
                menu.setMenuName(rset.getString("menu_name"));
                menu.setMenuPrice(rset.getInt("menu_price"));
                menu.setCategoryCode(rset.getInt("category_code"));
                menu.setOrderableStatus(rset.getString("orderable_status"));

                menuList.add(menu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(ptsm);
        }
        return menuList;
    }

    /* 주문 정보 입력용 메소드 */
    public int insertOrder(Connection con, OrderDTO order) {

        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("insertOrder");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, order.getOrderDate());
            pstmt.setString(2, order.getOrderTime());
            pstmt.setInt(3, order.getTotalOrderPrice());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }

        return result;
    }

    /* 주문 코드 조회용 메소드 */
    public int selectLastOrderCode(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        int lastOrderCode = 0;

        String query = prop.getProperty("selectLastOrderCode");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            if(rset.next()) {
                lastOrderCode = rset.getInt("order_code");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }

        return lastOrderCode;
    }

    /* 주문별 메뉴 입력용 메소드 */
    public int insertOrderMenu(Connection con, OrderMenuDTO orderMenu) {

        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("insertOrderMenu");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, orderMenu.getOrderCode());
            pstmt.setInt(2, orderMenu.getMenuCode());
            pstmt.setInt(3, orderMenu.getOrderAmount());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }

        return result;
    }

}
