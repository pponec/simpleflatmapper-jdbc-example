package net.ponec.jdbc.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class EmployeeDaoTest extends AbstractDaoTest {

    /** Test the creation and retrieval of a Country entity. */
    @Test
    void selectEmployeesBySimpleflatmapper() throws SQLException {
        loadDemoData();
        var dao = new EmployeeDao(dbConnection);
        var employess = dao.findAllEmployees(0L);

        Assertions.assertEquals(3, employess.size());
        var employee1 = employess.stream().findFirst().get();
        Assertions.assertEquals("George", employee1.getName());
        Assertions.assertEquals("Paris", employee1.getCity().getName());
        Assertions.assertEquals("Developers", employee1.getDepartment().getName());
        Assertions.assertNull(employee1.getSuperior().getName());

        var employee3 = employess.stream().skip(2).findFirst().get();
        Assertions.assertEquals("Joseph", employee3.getName());
        Assertions.assertEquals("Paris", employee3.getCity().getName());
        Assertions.assertEquals("Developers", employee3.getDepartment().getName());
        Assertions.assertNotNull(employee3.getSuperior().getName());
        Assertions.assertEquals("George", employee3.getSuperior().getName());


    }


}