package net.ponec.jdbc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name = "country")
public class Country {

    @Column(name = "id") @Id
    private Long id;
    @Column(name = "name")
    private String name;
}
