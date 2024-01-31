package com.ohgiraffers.section02.model.service;

import com.ohgiraffers.section02.model.dao.MenuDAO;
import com.ohgiraffers.section02.model.dto.CategoryDTO;
import com.ohgiraffers.section02.model.dto.MenuDTO;

import java.sql.Connection;

import static com.ohgiraffers.common.JDBCTemplate.*;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class MenuService {
    /* Service 역할 : 하나의 논리적인 작업 단위가 메소드로 정의
    *  1. Connection 생성
    *  2. DAO의 메소드 호출
    *  3. 트랜잭션 제어
    *  4. Connection 반환 */

    /* 신규 메뉴 카테고리 등록하는 서비스 메소드 */
    public void registNewMenu(){
        /* 1. Connection 생성 */
        Connection con = getConnection();

        /* 2. DAO!!!의 메소드 호출 */
        MenuDAO menuDAO = new MenuDAO();

        /* 2-1. 카테고리 등록 */
        CategoryDTO newCategoryDTO = new CategoryDTO();
        newCategoryDTO.setCategoryName("뉴카테");
        newCategoryDTO.setRefCategoryCode(null);

        int result1 = menuDAO.insertNewCategory(con,newCategoryDTO);

        /* 방금 입력 된 카테고리 코드 조회 */
        int newCategoryCode = menuDAO.selectLastCategoryCode(con);

        /* 2-2 메뉴 등록 */
        MenuDTO newMenu = new MenuDTO("메롱메롱스튜", 400000, newCategoryCode , "Y");

        int result2 = menuDAO.insertNewMenu(con, newMenu);

        /* 3. 트랜잭션으로 제어 */
        if (result1 > 0 && result2 > 0) {
            System.out.println("신규 카테고리와 메뉴를 추가하였습니다.");
            commit(con);
        }else {
            System.out.println("신규 카테고리와 메뉴를 추가하지 못하였습니다.");
            rollback(con);
        }

        /* 4. 마지막에서 Connection 자원 반납 */
        close(con);
    }
}
