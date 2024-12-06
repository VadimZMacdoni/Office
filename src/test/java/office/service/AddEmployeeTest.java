package office.service;

import office.Department;
import office.Employee;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static office.Config.*;
import static office.Service.addDepartment;
import static office.Service.addEmployee;

public class AddEmployeeTest {
    @Test
    @DisplayName("Проверка добавления сотрудника")
    public void addEmployeeTest() {
        Employee newEmp = new Employee(10, "Vadim", 2);

        addEmployee(newEmp);

        try(Connection con = DriverManager.getConnection(PG_BASE_URL, PG_BASE_USER, PG_BASE_PASSWORD)){
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("Select ID, NAME, departmentid from Employee WHERE ID=10");

            rs.next();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(10).isEqualTo(rs.getInt("ID"));
            softly.assertThat("Vadim").isEqualTo(rs.getString("NAME"));
            softly.assertThat(2).isEqualTo(rs.getInt("departmentid"));
            softly.assertAll();

        }catch (SQLException e) {
            System.out.println(e);
        }
    }
}
