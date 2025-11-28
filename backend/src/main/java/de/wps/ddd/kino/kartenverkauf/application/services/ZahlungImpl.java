package de.wps.ddd.kino.kartenverkauf.application.services;

import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.ZahlungAbgebrochen;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.ZahlungEingegangen;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsstatus;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsvorgang;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.Zahlung;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.Zahlungsvorgaenge;
import lombok.RequiredArgsConstructor;
import org.jmolecules.event.annotation.DomainEventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ZahlungImpl implements Zahlung {

    private final Zahlungsvorgaenge zahlungsvorgaenge;

    @Override
    public Auftragsnummer starteZahlungsvorgang(Geldbetrag gesamtpreis, VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        var zahlungsvorgang = Zahlungsvorgang.starteZahlung();
        zahlungsvorgaenge.speichere(zahlungsvorgang);
        // TODO
        // Plätze im Saalplan blocken, damit sie nicht doppelt verkauft werden
        // Zahlvorgang bei externen Zahlungsdienstleister starten
        return zahlungsvorgang.getAuftragsnummer();
    }

    @Override
    public Zahlungsstatus status(Auftragsnummer auftragsnummer) {
        var zahlungsvorgang = zahlungsvorgaenge.hole(auftragsnummer);
        return zahlungsvorgang.getStatus();
    }

    @Override
    @DomainEventHandler
    public void verarbeite(ZahlungEingegangen zahlungEingegangen) {
        var zahlungsvorgang = zahlungsvorgaenge.hole(zahlungEingegangen.auftragsnummer());
        zahlungsvorgang.zahlungEingegangen();
        zahlungsvorgaenge.speichere(zahlungsvorgang);
    }

    @Override
    @DomainEventHandler
    public void verarbeite(ZahlungAbgebrochen zahlungAbgebrochen) {
        var zahlungsvorgang = zahlungsvorgaenge.hole(zahlungAbgebrochen.auftragsnummer());
        zahlungsvorgang.zahlungAbgebrochen();
        zahlungsvorgaenge.speichere(zahlungsvorgang);
    }


}
