package net.ponec.jdbc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

@Getter @Setter @ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
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
