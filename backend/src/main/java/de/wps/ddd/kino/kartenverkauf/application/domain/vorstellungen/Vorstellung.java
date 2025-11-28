package de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

@AggregateRoot
@Getter
@AllArgsConstructor
public class Vorstellung {
    @Identity
    private final VorstellungId id;
    private final Saal saal;
    private final Beginn beginn;
    private final Film film;
    private final VorstellungKategorie kategorie;
}
