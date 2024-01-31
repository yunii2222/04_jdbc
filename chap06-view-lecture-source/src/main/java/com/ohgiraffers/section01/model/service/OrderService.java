package com.ohgiraffers.section01.model.service;

import com.ohgiraffers.section01.dao.OrderDAO;
import com.ohgiraffers.section01.dto.CategoryDTO;
import com.ohgiraffers.section01.dto.MenuDTO;
import com.ohgiraffers.section01.dto.OrderDTO;
import com.ohgiraffers.section01.dto.OrderMenuDTO;
import com.ohgiraffers.section01.view.OrderMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class OrderService {
    /* Service 역할 : 하나의 논리적인 작업 단위가 메소드로 정의
     *  1. Connection 생성
     *  2. DAO의 메소드 호출
     *  3. 트랜잭션 제어
     *  4. Connection 반환 */

    /* 전역변수로 사용 */
    private OrderDAO orderDAO = new OrderDAO();
    private Properties prop = new Properties();

    /* CategoryDTO 메소드 start */
    public List<CategoryDTO> selectAllCategory() {
        /* 1. Connection 생성 */
        Connection con = getConnection();

        /* 2. DAO 필요한 메소드 호출 */
        List<CategoryDTO> categoryList = orderDAO.selectAllCategory(con);

        /* 3. Connection 반납 */
        close(con);

        /* 반환 받은 값을 return */
        return categoryList;
    }
    /* CategoryDTO 메소드 end */

    /* MenuDTO 메소드 start */
    public List<MenuDTO> selectMenuByCategoryCode(int categoryCode) {
        /* 1. Connection 생성 */
        Connection con = getConnection();

        /* 2. DAO 필요한 메소드 호출 */
        List<MenuDTO> menuList = orderDAO.selectMenuByCategoryCode(con, categoryCode);

        /* 3. Connection 반납 */
        close(con);

        /* 반환 받은 값을 return */
        return menuList;
    }
    /* MenuDTO 메소드 end */

    /* registOrder메소드 start */
    public int registOrder(OrderDTO order) {
        /* 1. Connection 생성 */
        Connection con = getConnection();

        /* 2. DAO 필요한 메소드 호출 */
        /* 2-1. Order테이블에 insert */
        int orderResult = orderDAO.insertOrder(con,order);

        /* 2-2. order_code 값 조회 */
        int orderCode = orderDAO.selectLastOrderCode(con);

        /* 2-3. order menu 테이블에 insert */
        int orderMenuResult = 0;
        List<OrderMenuDTO> orderMemuList =  order.getOrderMenuList();
        for (OrderMenuDTO orderMenu : orderMemuList) {
            orderMenu.setOrderCode(orderCode);
            /* int값을 받아오면 orderMenuResult에 합산해서 보여주겠다. */
            orderMenuResult += orderDAO.insertOrderMenu(con, orderMenu);
        }

        /* 3. 성공 여부 판단 후 트랜잭션 처리 */
        int result = 0;
        /* orderResult가 0 보다 크거나, orderMenuResult가 orderMemuList와 같은길이일때 */
        if (orderResult > 0 && orderMenuResult == orderMemuList.size()) {
            commit(con);
            /* result = 1을 출력하면서 commit()과 동시에 주문완료되었다고 뜸. */
            result = 1;
        }else {
            rollback(con);
        }

        /* 4. Connection 반납 */
        close(con);

        /* 5. 결과값 반환 */
        return result;
    }
    /* registOrder메소드 end */
}
