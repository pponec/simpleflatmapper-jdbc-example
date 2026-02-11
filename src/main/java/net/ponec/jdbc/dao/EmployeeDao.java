package net.ponec.jdbc.dao;

import net.ponec.jdbc.entity.Employee;
import org.simpleflatmapper.jdbc.DynamicJdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDao  extends GenericCrudManager< Employee, Long> {
    final Connection dbConnection;

    final static DynamicJdbcMapper<Employee> mapper = JdbcMapperFactory
            .newInstance()
            .newMapper(Employee.class);

    public EmployeeDao(Connection dbConnection) throws SQLException {
        super(dbConnection,  Employee.class, Employee::setId);
        this.dbConnection = dbConnection;
    }

    /** Test the creation and retrieval of a Country entity. */
    public List<Employee> findAllEmployees(Long id) throws SQLException {
        var sql = """
                    SELECT e.id
                         , e.name
                         , e.city_id
                         , c.name AS city_name
                         , e.superior_id
                         , e.department_id
                         , d.name AS department_name
                         , e.contract_day
                    FROM employee e
                    JOIN city c ON c.id = e.city_id
                    JOIN department d ON d.id = e.department_id
                    WHERE e.id >= :id
                    ORDER BY e.id
                    """;
        try (var builder = sqlBuilder()) {
            return builder
                    .sql(sql)
                    .bind("id", id)
                    .streamMap(mapper::map)
                    .toList();
        }
    }

}
