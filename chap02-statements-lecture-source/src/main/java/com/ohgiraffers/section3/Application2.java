package com.ohgiraffers.section3;

import java.sql.*;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {

    /* PreparedStatement를 사용하면 SQL Injection 공격을 예방할 수 있다. */
    private static String empId = "210";
    private static String empName = "'OR 1=1 AND emp_id = '200";

    public static void main(String[] args) {
        /* '" + empId + "' 제거 */
        String query = "SELECT * FROM employee WHERE emp_id = ? AND emp_name = ?";

        Connection con = getConnection();
        PreparedStatement pstm = null;
        /* 조회하여 확인 */
        ResultSet rset = null;

        try {
            pstm = con.prepareStatement(query);
            pstm.setString(1,empId);
            pstm.setString(2,empName);

            rset = pstm.executeQuery();

            if (rset.next()){
                System.out.print(rset.getString("emp_name") + "님 환영합니다.");
            }else {
                System.out.printf("회원 정보가 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstm);
            close(con);
        }
    }
}
