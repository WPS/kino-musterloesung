package de.wps.ddd.kino.kartenverkauf.application.ports.secondary;

import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import org.jmolecules.ddd.annotation.Repository;

import java.util.List;

@Repository
public interface AktuelleVorstellungen {
    List<Vorstellung> alleVorstellungen();
    Vorstellung holeVorstellung(VorstellungId vorstellungId);
}
