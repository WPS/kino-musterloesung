package de.wps.ddd.kino.filmauswahl.data;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.annotation.Identity;

@org.jmolecules.ddd.annotation.Entity
@Entity
@Table(name = "saele", schema = "filmauswahl")
@Data
@NoArgsConstructor
public class Saal {
    @Identity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonValue
    private String name;
}
