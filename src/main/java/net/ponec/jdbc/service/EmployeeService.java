package net.ponec.jdbc.service;

import lombok.RequiredArgsConstructor;
import net.ponec.jdbc.dao.CityDao;
import net.ponec.jdbc.dao.CountryDao;
import net.ponec.jdbc.dao.DepartmentDao;
import net.ponec.jdbc.dao.EmployeeDao;
import net.ponec.jdbc.entity.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeService {

    private final CityDao cityDao;
    private final CountryDao countryDao;
    private final DepartmentDao departmentDao;
    private final EmployeeDao employeeDao;

    private final DataResourceService dataProvider = new DataResourceService();

    public void initData() throws SQLException {
        var employees = dataProvider.employees();
        var departments = dataProvider.extractUniqueDepartments(employees);
        var cities = dataProvider.extractUniqueCities(employees);
        var countries = dataProvider.extractUniqueCountries(employees);

        countryDao.createAll(countries);
        cityDao.createAll(cities);
        departmentDao.createAll(departments);
        employeeDao.createAll(employees);
    }


    public List<Employee> employees() {
        return List.of();
    }

    public List<Employee> findAllEmployees(Long id) throws SQLException {
        return employeeDao.findAllEmployees(id);
    }

    public static EmployeeService of(Connection dbConnection) {
        try {
            final var cityDao = new CityDao(dbConnection);
            final var countryDao = new CountryDao(dbConnection);
            final var departmentDao = new DepartmentDao(dbConnection);
            final var employeeDao = new EmployeeDao(dbConnection);
            return new EmployeeService(cityDao, countryDao, departmentDao, employeeDao);
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
