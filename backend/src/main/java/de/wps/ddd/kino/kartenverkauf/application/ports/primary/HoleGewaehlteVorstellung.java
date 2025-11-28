package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;

public interface HoleGewaehlteVorstellung {
    Vorstellung fuer(VorstellungId vorstellungId);
}
