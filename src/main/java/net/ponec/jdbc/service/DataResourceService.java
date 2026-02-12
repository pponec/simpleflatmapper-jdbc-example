package net.ponec.jdbc.service;

import net.ponec.jdbc.entity.City;
import net.ponec.jdbc.entity.Country;
import net.ponec.jdbc.entity.Department;
import net.ponec.jdbc.entity.Employee;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;

public class DataResourceService {

    /** Get employees to save into database. */
    public List<Employee> employees() {
        var countryObj = Country.of(null, "France");
        var cityObj = City.of(null, "Paris", countryObj);
        var departmentObj = Department.of(null, "Developers");

        var employee1 = Employee.of(null, "George", null,  departmentObj, cityObj, LocalDate.of(2020, Month.JANUARY, 1));
        var employee2 = Employee.of(null, "Francis", employee1,  departmentObj, cityObj, LocalDate.of(2020, Month.JANUARY, 2));
        var employee3 = Employee.of(null, "Joseph", employee1,  departmentObj, cityObj, LocalDate.of(2020, Month.JANUARY, 3));

        return List.of(employee1, employee2, employee3);
    }

    /** Extract unique cities directly within a single method */
    public List<City> extractUniqueCities(List<Employee> employees) {
        var seenNames = new HashSet<String>(); // Maintain a set of already seen city names
        return employees.stream()
                .map(Employee::getCity)
                .distinct()
                .filter(city -> seenNames.add(city.getName()))
                .toList();
    }

    /** Extract unique departments */
    public List<Department> extractUniqueDepartments(List<Employee> employees) {
        var seenNames = new HashSet<String>(); // Maintain a set of already seen city names
        return employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .filter(city -> seenNames.add(city.getName()))
                .toList();
    }

    /** Extract unique County */
    public List<Country> extractUniqueCountries(List<Employee> employees) {
        var seenNames = new HashSet<String>(); // Maintain a set of already seen city names
        return employees.stream()
                .map(e -> e.getCity().getCountry())
                .distinct()
                .filter(city -> seenNames.add(city.getName()))
                .toList();
    }

}
