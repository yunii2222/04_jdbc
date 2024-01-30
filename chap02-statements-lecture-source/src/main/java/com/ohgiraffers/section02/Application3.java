package com.ohgiraffers.section02;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application3 {
    public static void main(String[] args) {

        /* employee 테이블에서 조회할 사원의 성씨를 입력 받아 해당 성씨를 가진 사원 정보를 모두 출력 */

        /* 1. Connection 생성 */
        Connection con = getConnection();
        /* PreparedStatement 선언  */
        PreparedStatement pstm = null;
        ResultSet rset = null;
        /* 한 행의 정보를 담을 DTO */
        EmployeeDTO selectdEmp = null;
        /* 여러 DTO 타입의 객체를 담을 List */
        List<EmployeeDTO> empList = null;

        Scanner sc = new Scanner(System.in);
        System.out.printf("조회할 이름의 성을 입력하세요");
        String empName = sc.nextLine();

        /* 문자열 위치홀더 처리시에는 CONCAT()사용
        *  LIKE CONCAT(?, '%')를 사용해 입력한 성씨로 시작되는 이름 찾기 */
        String query = "SELECT * FROM employee WHERE emp_name LIKE CONCAT(?, '%')";

        try {
            pstm = con.prepareStatement(query);

            /* ? <- 위치 홀더를 채워주는 구문
            *  setInt, setDouble ... */
            pstm.setString(1, empName);

            rset = pstm.executeQuery();

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

                /* empList안에 selectEmp라는 EmployeeDTO객체를 리스트로 추가 */
                empList.add(selectdEmp);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            /* 5. 사용한 자원 반납 */
            close(rset);
            close(pstm);
            close(con);
        }

        /* empList라는 EmployeeDTO 객체를 순회하는 구문 */
        for (EmployeeDTO employeeDTO : empList) {
            /* DTO에서 꼭 toString 생성되었는지 확인! */
            System.out.println(employeeDTO);
        }
    }
}
