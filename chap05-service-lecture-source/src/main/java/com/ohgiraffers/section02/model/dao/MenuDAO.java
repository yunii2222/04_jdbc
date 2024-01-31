package com.ohgiraffers.section02.model.dao;

import com.ohgiraffers.section02.model.dto.CategoryDTO;
import com.ohgiraffers.section02.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class MenuDAO {
    private Properties prop = new Properties();

    /* 신규 카테고리 등록용 메소드 */
    public int insertNewCategory(Connection con, CategoryDTO newCategoryDTO) {
        PreparedStatement ptsm = null;
        int result = 0;

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("insertCategory");

            ptsm = con.prepareStatement(query);
            ptsm.setString(1,newCategoryDTO.getCategoryName());
            ptsm.setObject(2,newCategoryDTO.getRefCategoryCode());

            /* result를 선언했다면 result 꼭 넣어주기! */
            result = ptsm.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(ptsm);
        }
        return result;
    }


    /* 마지막 카테고리 코드 조회용 메소드 */
    public int selectLastCategoryCode(Connection con) {
        PreparedStatement ptsm = null;
        ResultSet rset = null;
        int newCategoryCode = 0;
        String query = prop.getProperty("selectLastCategoryCode");

        try {
            ptsm  = con.prepareStatement(query);
            rset = ptsm.executeQuery();


            if (rset.next()){
                newCategoryCode = rset.getInt("category_code");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(ptsm);
        }
        return newCategoryCode;
    }


    /* 신규 메뉴등록용 메소드 */
    public int insertNewMenu(Connection con, MenuDTO newMenu) {
        PreparedStatement ptsm = null;
        int result = 0;
        String query = prop.getProperty("insertMenu");

        try {
            ptsm = con.prepareStatement(query);

            ptsm.setString(1, newMenu.getMenuName());
            ptsm.setInt(2, newMenu.getMenuPrice());
            ptsm.setInt(3, newMenu.getCategoryCode()); //존재하지 않는 category code 입력 시 오류 발생, 존재하는 category 입력 시 성공
            ptsm.setString(4, newMenu.getOrderableStatus());

            /* result를 선언했다면 앞에 result 꼭 넣어주기! */
            result = ptsm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(ptsm);
        }
        return result;
    }
}
