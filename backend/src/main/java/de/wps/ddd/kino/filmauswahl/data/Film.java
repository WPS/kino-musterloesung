package de.wps.ddd.kino.filmauswahl.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.util.List;

@AggregateRoot
@Entity
@Table(name = "filme", schema = "filmauswahl")
@Data
@NoArgsConstructor
public class Film {
    @Identity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String titel;
    private Integer laufzeit;
    private String posterUrl;
    private Integer fsk;
    @Lob
    private String beschreibung;
    private String genre;
    private String hauptdarsteller;
    private String regie;
    private String sprache;
    @OneToMany
    @JoinColumn(name = "filmId")
    private List<Vorstellung> vorstellungen;
}
