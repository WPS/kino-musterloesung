package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;

public interface HoleSaalplan {
    Saalplan fuer(VorstellungId vorstellungId);
}
