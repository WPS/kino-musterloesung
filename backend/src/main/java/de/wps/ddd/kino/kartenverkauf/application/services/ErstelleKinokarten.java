package de.wps.ddd.kino.kartenverkauf.application.services;

import de.wps.ddd.kino.common.error.GeschaeftsregelVerletzt;
import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.KartenBlock;
import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsstatus;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.Zahlung;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.AktuelleVorstellungen;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalplanStapel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class ErstelleKinokarten implements de.wps.ddd.kino.kartenverkauf.application.ports.primary.ErstelleKinokarten {

    private final AktuelleVorstellungen aktuelleVorstellungen;

    private final KartenBlock kartenBlock;

    private final SaalplanStapel saalplanStapel;

    private final Zahlung zahlung;

    @Override
    public List<Kinokarte> fuer(Auftragsnummer auftragsnummer, VorstellungId vorstellungId, ZusammenhaengendePlaetze gewaehltePlaetze) {
        GeschaeftsregelVerletzt.throwIf(zahlung.status(auftragsnummer) != Zahlungsstatus.Eingegangen, "Zahlung ist noch nicht eingegangen");
        var vorstellung = aktuelleVorstellungen.holeVorstellung(vorstellungId);
        var kinokarten = kartenBlock.erstelleKarten(vorstellung, gewaehltePlaetze);
        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);
        saalplan.markiereAlsVerkauft(gewaehltePlaetze);
        saalplanStapel.legeZurueck(saalplan);
        return kinokarten;
    }
}
