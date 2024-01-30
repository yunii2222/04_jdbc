package com.ohgiraffers.section02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        /* getConnection() 미리 만들어 놓은 메소드 */
        Connection con = getConnection();
        /* PreparedStatement 선언  */
        PreparedStatement pstm = null;
        /* ResultSet 선언 */
        ResultSet rset = null;

        /* prepareStatement 객체 생성 시 수행할 sql 구문을 인자로 전달하면서 생성한다. */
        try {
            pstm = con.prepareStatement("SELECT emp_id, emp_name FROM employee");

            /* 쿼리실행 */
            rset = pstm.executeQuery();

            while (rset.next()){
                System.out.printf(rset.getString("emp_id") + ", " + rset.getString("emp_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            /* finally 구문안에서 자원 반납 필수! */
        }finally {
            close(rset);
            close(pstm);
            close(con);

        }

    }
}
