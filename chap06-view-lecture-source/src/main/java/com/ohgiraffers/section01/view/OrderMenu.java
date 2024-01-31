package com.ohgiraffers.section01.view;

import com.ohgiraffers.section01.dto.CategoryDTO;
import com.ohgiraffers.section01.dto.MenuDTO;
import com.ohgiraffers.section01.dto.OrderDTO;
import com.ohgiraffers.section01.dto.OrderMenuDTO;
import com.ohgiraffers.section01.model.service.OrderService;

import javax.swing.text.html.CSS;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OrderMenu {

    private OrderService orderService = new OrderService();
    public void displayMainMenu() {
        /* ----------반복---------
        * 1. 카테고리 조회
        * 2. 카테고리 선택하면 해당 카테고리의 메뉴 조회
        * 3. 주문할 메뉴와 수량 입력 받기
        * ----------반복---------
        * 4. 주문하기 */

        /* 주문받을 목록 선언 */
        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        /* totalOrderPrice에 주문한메뉴 목록에 누적 */
        int totalOrderPrice = 0;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("======="+"🍽"+"메뉴 주문 프로그램"+"🍽"+"=======" );
            /* 카테고리 List를 조회 해온다. */
            List<CategoryDTO> categoryList = orderService.selectAllCategory();
            /* 조회한 카테고리들을 반복문으로 출력해서 보여주겠따. */
            for (CategoryDTO category:categoryList) {
                /* 카테고리 이름만 보여주겠다. */
                System.out.println(category.getCategoryName());
            }
            System.out.println("==============================");
            System.out.println("주문하실 카테고리를 선택해주세요");
            System.out.print("=> ");
            String inputCategoryName = sc.nextLine();

            int categoryCode = 0;
            for (CategoryDTO category : categoryList) {
                /* 만약 한식을 입력했다면, 한식이라는 카테고리이름을 가지고 있는 코드를 저장 */
                if (category.getCategoryName().equals(inputCategoryName)) {
                    categoryCode = category.getCategoryCode();
                }
            }

            /* 한식을 입력했다면, 한식에 해당하는 메뉴 목록을 보여주고, 카테고리 코드로 조회 */
            System.out.println("======="+"🍽"+"주문 가능 메뉴"+"🍽"+"=======");
            List<MenuDTO> menuList = orderService.selectMenuByCategoryCode(categoryCode);
            for (MenuDTO menu : menuList){
                System.out.println(menu.getMenuName() + " : " + menu.getMenuPrice() + "원");
            }
            /* 주문할 메뉴 선택 */
            System.out.println("==============================");
            System.out.println("주문하실 메뉴를 선택해주세요");
            System.out.print("=> ");
            String inputMenuName = sc.nextLine();

            int menuCode = 0;
            int menuPrice = 0;
            for (MenuDTO menu : menuList) {
                if (menu.getMenuName().equals(inputMenuName)){
                    menuCode = menu.getMenuCode();
                    menuPrice = menu.getMenuPrice();
                }
            }
            System.out.println("==============================");
            System.out.println("주문하실 수량을 선택해주세요");
            System.out.print("=> ");
            int orderAmount = sc.nextInt();

            OrderMenuDTO orderMenu = new OrderMenuDTO();
            orderMenu.setMenuCode(menuCode);
            orderMenu.setOrderAmount(orderAmount);

            orderMenuList.add(orderMenu);
            totalOrderPrice += (menuPrice * orderAmount);

            /* 계속 주문하려는지에 대한 질문 */
            System.out.println("계속 주문 하시겠습니까? (예/아니오)");
            System.out.print("=> ");
            sc.nextLine();

            /* 다시 위코드로 돌아간다 */
        }while (sc.nextLine().equals("예"));

        System.out.println("======="+"🍽"+"주문 리스트"+"🍽"+"=======");
        for (OrderMenuDTO orderMenu : orderMenuList) {
            System.out.println(orderMenu);
        }

        Date orderTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(orderTime);
        String time = timeFormat.format (orderTime);

        /* order라는 객체에 선언되어있음. */
        OrderDTO order = new OrderDTO();
        order.setOrderDate(date);
        order.setOrderTime(time);
        order.setTotalOrderPrice(totalOrderPrice);
        order.setOrderMenuList(orderMenuList);

        /* 주문등록해달라는 요청을 했다. */
        int result = orderService.registOrder(order);

        if (result > 0) {
            System.out.println("주문이 완료 되었습니다.");
        }else {
            System.out.println("주문이 실패하였습니다.");
        }
    }
}
