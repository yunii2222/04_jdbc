package com.ohgiraffers.section01.statement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application3 {
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
            String query = "SELECT * FROM employee WHERE emp_id = '" +  empId + "'";

            /* 3. Statement의 executeQuery(sql)로 쿼리문 실헹하고 결과를 ResultSet으로 반환 받음 */
            rset = stmt.executeQuery(query);

            /* 4. ResultSet에 담긴 값을 Employee에서 꺼내오기 */
            while (rset.next()) {
                selectdEmp = new EmployeeDTO();

                selectdEmp.setEmpId(rset.getString("emp_id"));
                selectdEmp.setEmpName(rset.getString("emp_Name"));
                selectdEmp.setEmpNo(rset.getString("emp_no"));
                selectdEmp.setEmail(rset.getString("email"));
                selectdEmp.setPhone(rset.getString("phone"));
                selectdEmp.setDeptCode(rset.getString("dept_code"));
                selectdEmp.setJobCode(rset.getString("job_code"));
                selectdEmp.setSalLevel(rset.getString("sal_level"));
                selectdEmp.setSalary(rset.getInt("salary"));
                selectdEmp.setBonus(rset.getDouble("bonus"));
                selectdEmp.setManagerId(rset.getString("manager_id"));
                selectdEmp.setHireDate(rset.getDate("hire_date"));
                selectdEmp.setEntDate(rset.getDate("ent_date"));
                selectdEmp.setEntYn(rset.getString("ent_yn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            /* 5. 사용한 자원 반납 */
            close(rset);
            close(stmt);
            close(con);
        }

        System.out.printf("selectdEmp :" + selectdEmp);
    }
}
