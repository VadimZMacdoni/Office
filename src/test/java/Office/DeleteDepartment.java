package Office;

import office.Department;
import office.Option;
import office.Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static office.Config.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteDepartment {

    @Test
    @DisplayName("Проверка удаления сотрудников при удалении отдела")
    public void deleteDepartment() {
        try(Connection con = DriverManager.getConnection(PG_BASE_URL, PG_BASE_USER, PG_BASE_PASSWORD)){
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("Select ID, NAME from Department");
            while(rs.next()){
                Service.removeDepartment(new Department(rs.getInt("ID"), rs.getString("NAME")));
            }

            ResultSet rs2 = stm.executeQuery("Select count(*) as Count from Employee");
            rs2.next();
            assertEquals(0, rs2.getInt("Count"));

        }catch (SQLException e) {
            System.out.println(e);
        }
    }
}
