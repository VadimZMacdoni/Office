package office.service;

import office.Department;
import office.Employee;
import office.Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static office.Config.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveEmployeeTest {
    @Test
    @DisplayName("Проверка удаления сотрудников")
    public void removeEmployeeTest() {
        try(Connection con = DriverManager.getConnection(PG_BASE_URL, PG_BASE_USER, PG_BASE_PASSWORD)){
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("Select ID, NAME, departmentid from Employee");
            while(rs.next()){
                Service.removeEmployee(new Employee(rs.getInt("ID"), rs.getString("NAME"),rs.getInt("departmentid")));
            }

            ResultSet rs2 = stm.executeQuery("Select count(*) as Count from Employee");
            rs2.next();
            assertEquals(0, rs2.getInt("Count"));

        }catch (SQLException e) {
            System.out.println(e);
        }
    }
}
