package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;

import java.util.List;

public interface ErstelleKinokarten {
    List<Kinokarte> fuer(Auftragsnummer auftragsnummer, VorstellungId vorstellungId, ZusammenhaengendePlaetze gewaehltePlaetze);
}
