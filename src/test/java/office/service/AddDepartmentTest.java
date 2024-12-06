package office.service;

import office.Department;
import office.Employee;
import office.Service;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static office.Config.*;
import static office.Service.addDepartment;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddDepartmentTest {
    @Test
    @DisplayName("Проверка добавления отдела")
    public void addDepartmentTest() {
        Department newDep = new Department(10, "Cyber Security");

        addDepartment(newDep);

        try(Connection con = DriverManager.getConnection(PG_BASE_URL, PG_BASE_USER, PG_BASE_PASSWORD)){
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("Select ID, NAME from Department WHERE ID=10");

            rs.next();
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(10).isEqualTo(rs.getInt("ID"));
            softly.assertThat("Cyber Security").isEqualTo(rs.getString("NAME"));
            softly.assertAll();

        }catch (SQLException e) {
            System.out.println(e);
        }
    }
}
