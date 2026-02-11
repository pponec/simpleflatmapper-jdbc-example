package net.ponec.jdbc.service;

import net.ponec.jdbc.dao.AbstractDaoTest;
import net.ponec.jdbc.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

class EmployeServiceTest extends AbstractDaoTest {

    /** Test the creation and retrieval of a Country entity. */
    @Test
    void selectEmployeesByUjorm() throws SQLException {

        var service = EmployeeService.of(super.connection);
        service.initData();

        try (var sqlBuilder = sqlBuilder()) {
            var sql = """
                    SELECT id 
                         , name          
                         , superior_id   
                         , department_id   
                         , contract_day  
                    FROM employee
                    WHERE id >= :id
                    ORDER BY id
                    """;
            var employess = sqlBuilder.sql(sql)
                    .bind("id", 0)
                    .streamMap(rs -> {
                        var result = new Employee();
                        result.setId(rs.getLong("id"));
                        result.setName(rs.getString("name"));
                        result.setContractDay(rs.getObject("contract_day", LocalDate.class));
                        return result;
                    }).toList();

            Assertions.assertEquals(3, employess.size());
            var employee = employess.stream().findFirst().get();
            Assertions.assertEquals("George", employee.getName());

        }
    }

    /** Test the creation and retrieval of a Country entity. */
    @Test
    void selectEmployeesBySimpleflatmapper() throws SQLException {
        var service = EmployeeService.of(super.connection);
        service.initData();
        var employess = service.findAllEmployees(0L);

        Assertions.assertEquals(3, employess.size());
        var employee = employess.stream().findFirst().get();
        Assertions.assertEquals("George", employee.getName());
        Assertions.assertEquals("Paris", employee.getCity().getName());
        Assertions.assertEquals("Developers", employee.getDepartment().getName());

    }
}