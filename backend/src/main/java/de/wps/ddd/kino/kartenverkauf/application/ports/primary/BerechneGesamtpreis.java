package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;

public interface BerechneGesamtpreis {
    Geldbetrag fuer(VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze);
}
