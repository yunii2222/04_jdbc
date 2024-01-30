package com.ohgiraffers.section3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {

    /* 일치하는 정보를 입력하면 성공하고 불일치하는 정보를 입력하면 실패하는 것이 정상 수행 흐를 */
//    private static String empId = "200";
//    private static String empName = "홍길동";

    /* 조건절에 들어갈 내용을 예상하여 OR 1=1을 삽입하여 일부 정보만 앟는 회원의 정보를 얻어올 수 있음. */
    private static String empId = "210";
    private static String empName = "'OR 1=1 AND emp_id = '200";

    public static void main(String[] args) {
        String query = "SELECT * FROM employee WHERE emp_id = '" + empId + "' AND emp_name = '" + empName + "'";

        Connection con = getConnection();
        Statement stmt = null;
        /* 조회하여 확인 */
        ResultSet rset = null;

        try {
            stmt = con.createStatement();

            rset = stmt.executeQuery(query);

            if (rset.next()){
                System.out.print(rset.getString("emp_name") + "님 환영합니다.");
            }else {
                System.out.printf("회원 정보가 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(stmt);
            close(con);
        }
    }
}
