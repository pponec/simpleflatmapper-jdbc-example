package net.ponec.jdbc.dao;

import net.ponec.jdbc.entity.Department;

import java.sql.Connection;
import java.sql.SQLException;

public class DepartmentDao  extends GenericCrudManager<Department, Long> {
    final Connection dbConnection;

    public DepartmentDao(Connection dbConnection) throws SQLException {
        super(dbConnection, Department.class, Department::setId);
        this.dbConnection = dbConnection;
    }

}
