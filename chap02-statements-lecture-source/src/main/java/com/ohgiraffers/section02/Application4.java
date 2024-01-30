package com.ohgiraffers.section02;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application4 {
    public static void main(String[] args) {
        /* XML 파일에 별도로 입력 한 SQL문을 통해 처리 */

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

        /* 파일을 자바에서 설정정보를 가지고 올때 */
        Properties prop = new Properties();

        try {

            /* 입출력
            * XML이라는 파일을 읽어오겠다 */
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/section02/preparedStatement/employee-query.xml"));
            /* selectEmpByFamilyName이름을 가진 key값을 쿼리라는 문자열로 가지고온다. */
            String query = prop.getProperty("selectEmpByFamilyName");

            pstm = con.prepareStatement(query);
            pstm.setString(1,empName);

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
        } catch (InvalidPropertiesFormatException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
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
