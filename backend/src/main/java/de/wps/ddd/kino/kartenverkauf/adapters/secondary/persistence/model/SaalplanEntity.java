package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "saalplaene", schema = "kartenverkauf")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaalplanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID vorstellungUUID;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "saalplan"
    )
    private List<PlatzEntity> plaetze = new ArrayList<>();

    public SaalplanEntity(Integer id, UUID vorstellungUUID) {
        this.id = id;
        this.vorstellungUUID = vorstellungUUID;
    }

    public void addPlatz(int reihe, int platz, String kategorie, boolean istVerkauft) {
        plaetze.add(new PlatzEntity(this, reihe, platz, kategorie, istVerkauft));
    }
}
