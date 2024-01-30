package com.ohgiraffers.section01.statement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Applicaiton2 {
    public static void main(String[] args) {
        /* 1. Connection 생성
        * DB와 연결하려면 먼저 Connection과 연결시켜주기 */
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;
        EmployeeDTO selectdEmp = null;

        try {
            /* 2. Connection의 CreateStatement()를 이용한 Statement 인스턴스 생성
            *  아래 구문을 작성하면 try/catch 생성된다.
            *  Statement stmt = con.createStatement(); 생성후 전역변수로 null 정의 필요 */
            stmt = con.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.println("조회하려는 사번을 입력해주세요");
            System.out.print("=> ");
            String empId = sc.nextLine();
            String query = "SELECT emp_id, emp_name FROM employee WHERE emp_id = '" +  empId + "'";

            /* 3. Statement의 executeQuery(sql)로 쿼리문 실헹하고 결과를 ResultSet으로 반환 받음 */
            rset = stmt.executeQuery(query);


            /* 4. ResultSet에 담긴 값을 컬럼명을 이용해 꺼내어 출력 */
            if (rset.next()) {
                System.out.printf(rset.getString("emp_id") + ", " + rset.getString("emp_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
