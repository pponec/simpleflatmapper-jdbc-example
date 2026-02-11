package net.ponec.jdbc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name = "city")
public class City {

    @Column(name = "id") @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "country_id")
    private Country country;

}
