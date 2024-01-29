package com.ohgiraffers.section01.statement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application4 {
    public static void main(String[] args) {

        /* 1. Connection 생성
         * DB와 연결하려면 먼저 Connection과 연결시켜주기 */
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;
        /* 한 행의 정보를 담을 DTO */
        EmployeeDTO selectdEmp = null;
        /* 여러 DTO 타입의 객체를 담을 List */
        List<EmployeeDTO> empList = null;

        try {
            /* 2. Connection의 CreateStatement()를 이용한 Statement 인스턴스 생성
             *  아래 구문을 작성하면 try/catch 생성된다.
             *  Statement stmt = con.createStatement(); 생성후 전역변수로 null 정의 필요 */
            stmt = con.createStatement();

            /* employee의 테이블을 query변수명에 문자열로 담겠다 */
            String query = "SELECT * FROM employee";

            /* 3. Statement의 executeQuery(sql)로 쿼리문 실헹하고 결과를 ResultSet으로 반환 받음 */
            rset = stmt.executeQuery(query);

            empList = new ArrayList<>();

            /* 4. ResultSet에 담긴 값을 List 타입의 객체에 설정 */
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

                /* empList안에 selectEmp라는 EmployeeDTO객체를 추가한다는 구문 */
                empList.add(selectdEmp);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            /* 5. 사용한 자원 반납 */
            close(rset);
            close(stmt);
            close(con);
        }

        /* empList라는 EmployeeDTO 객체를 순회하는 구문 */
       for (EmployeeDTO employeeDTO : empList) {
           /* DTO에서 꼭 toString 생성되었는지 확인! */
           System.out.println(employeeDTO);
       }
    }
}
