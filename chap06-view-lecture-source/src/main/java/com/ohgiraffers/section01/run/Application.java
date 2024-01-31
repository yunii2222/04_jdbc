package com.ohgiraffers.section01.run;

import com.ohgiraffers.section01.view.OrderMenu;

public class Application {
    public static void main(String[] args) {
        OrderMenu orderMenu = new OrderMenu();
        /* OrderMenu페이지에 있는 displatMainMenu 호출  */
        orderMenu.displayMainMenu();
    }
}
