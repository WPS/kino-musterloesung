package de.wps.ddd.kino.filmauswahl.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;
import java.util.UUID;

@org.jmolecules.ddd.annotation.Entity
@Entity
@Table(name = "vorstellungen", schema = "filmauswahl")
@Data
@NoArgsConstructor
public class Vorstellung {
    @Identity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private UUID uuid;
    @JsonIgnore
    private Long filmId;
    private LocalDateTime beginn;
    @ManyToOne
    @JoinColumn(name = "saal_id", nullable = false)
    private Saal saal;
}
