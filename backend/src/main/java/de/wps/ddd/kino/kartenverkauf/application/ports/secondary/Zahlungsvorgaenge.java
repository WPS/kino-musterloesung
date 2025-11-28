package de.wps.ddd.kino.kartenverkauf.application.ports.secondary;

import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsvorgang;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface Zahlungsvorgaenge {

    void speichere(Zahlungsvorgang zahlungsvorgang);

    Zahlungsvorgang hole(Auftragsnummer auftragsnummer);
}
