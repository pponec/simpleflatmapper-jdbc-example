# simpleflatmapper-jdbc-example

Example project showcasing JDBC mapping with [SimpleFlatMapper](https://simpleflatmapper.org/) — a lightweight library that makes JDBC easy, fast, and safe to use.
Includes sample entities, DAO and service layers, along with JUnit tests for studying repository and mapping patterns.

See an example of a DAO method:

```java
public List<Employee> findAllEmployees(Long id) throws SQLException {
    var sql = """
            SELECT e.id
                 , e.name
                 , c.name AS city_name
                 , e.department_id
                 , d.name AS department_name
                 , e.contract_day
                 , y.name AS city_country_name
                 , e.superior_id
                 , s.name AS superior_name
            FROM employee e
            JOIN department d ON d.id = e.department_id
            JOIN city c ON c.id = e.city_id
            JOIN country y ON y.id = c.country_id
            LEFT JOIN employee s ON s.id = e.superior_id
            WHERE e.id >= :id
            ORDER BY e.id
            """;
    try (var builder = new SqlParamBuilder(connection) {
        return builder
                .sql(sql)
                .bind("id", id)
                .streamMap(EMPLOYEE_MAPPER::map)
                .toList();
    }
}
```

for the Employee entity:

```java
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor(staticName = "of")
@Table(name = "employee")
public class Employee {
    @Column(name = "id") @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Nullable
    @JoinColumn(name = "superior_id")
    private Employee superior;
    @JoinColumn(name = "department_id")
    private Department department;
    @JoinColumn(name = "city")
    private City city;
    @Column(name = "contract_day")
    private LocalDate contractDay;
}
```

For more information about [SqlParamBuilder](https://github.com/pponec/PPScriptsForJava/blob/22c7f7995f8708268bab5ab61f5a10778c302adb/src/main/java/net/ponec/script/SqlExecutor.java#L130) class see the [home page](https://github.com/pponec/PPScriptsForJava/blob/main/docs/SqlParamBuilder.md).
For the 
