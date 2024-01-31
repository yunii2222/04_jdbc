package com.ohgiraffers.section02.model.dao;

import com.ohgiraffers.section02.model.dto.CategoryDTO;
import com.ohgiraffers.section02.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;

/* DAO(Database Access Object) : 데이터베이스 접근용 객체
* crud 연산을 담당하는 메소드들의 집합으로 이루어진 클래스 */

/* 별도의 메소드 생성 */
public class MenuDAO {
    private Properties prop = new Properties();
    public MenuDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

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

    /* 2. 신규 메뉴 등록 메소드 */
    public int insertNewMenu(Connection con, MenuDTO newMenu){
        PreparedStatement ptsm = null;
        int result = 0;
        String query = prop.getProperty("insertMenu");

        try {
            ptsm = con.prepareStatement(query);

            ptsm.setString(1, newMenu.getMenuName());
            ptsm.setInt(2, newMenu.getMenuPrice());
            ptsm.setInt(3, newMenu.getCategoryCode());
            ptsm.setString(4, newMenu.getOrderableStatus());

            result = ptsm.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(ptsm);
        }
        return result;
    }
}
