package com.ohgiraffers.section02;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.*;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {
        /* 1. Connection 생성
         * DB와 연결하려면 먼저 Connection과 연결시켜주기 */
        Connection con = getConnection();
        /* PreparedStatement 선언  */
        PreparedStatement pstm = null;
        ResultSet rset = null;
        EmployeeDTO selectdEmp = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회하려는 사번을 입력해주세요 : ");
        System.out.print("=> ");
        String empId = sc.nextLine();
        /* ? <- 위치홀더 (어떤 값이 들어오는지는 모르지만 자리를 잡아줄것이다) */
        String query = "SELECT emp_id, emp_name FROM employee WHERE emp_id = ? ";

        try {
            /* 2. Connection의 CreateStatement()를 이용한 prepareStatement 인스턴스 생성
             *  아래 구문을 작성하면 try/catch 생성된다. */
            pstm = con.prepareStatement(query);

            /* 3. sql문의 위치홀더를 설정 */
            pstm.setString(1,empId);

            /* 4. prepareStatement의 executeQuery()로 쿼리문 실행하고 결과를 ResultSet으로 반환 받음 */
            rset = pstm.executeQuery();

            /* 5. ResultSet에 담긴 값을 컬럼명을 이용해 꺼내어 출력 */
            if (rset.next()) {
                System.out.printf(rset.getString("emp_id") + ", " + rset.getString("emp_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
