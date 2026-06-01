package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.controllers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.ZahlungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.PreisanfrageDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZahlungsstatusDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZahlungsvorgangDto;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.ZahlungEingegangen;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.BerechneGesamtpreis;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.Zahlung;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/kartenverkauf")
@RequiredArgsConstructor
class ZahlungController {

    private final SaalplanDtoMapper saalplanMapper;
    private final ZahlungDtoMapper zahlungMapper;

    private final BerechneGesamtpreis berechneGesamtpreis;

    private final Zahlung zahlung;

    @PostMapping("/zahlung")
    public ZahlungsvorgangDto starteZahlungsvorgang(@RequestBody PreisanfrageDto preisanfrageDto) {
        var vorstellungId = new VorstellungId(preisanfrageDto.vorstellungId());
        var gewaehltePlaetze = saalplanMapper.toDomain(preisanfrageDto.plaetze());

        var gesamtpreis = berechneGesamtpreis.fuer(vorstellungId, gewaehltePlaetze);
        var auftragsnummer = zahlung.starteZahlungsvorgang(gesamtpreis, vorstellungId, gewaehltePlaetze);

        return new ZahlungsvorgangDto(
                auftragsnummer.nummer().toString(),
                preisanfrageDto.vorstellungId().toString(),
                preisanfrageDto.plaetze(),
                zahlungMapper.toDto(gesamtpreis));
    }

    @GetMapping("/zahlung/{id}/status")
    public ZahlungsstatusDto zahlungStatus(@PathVariable UUID id) {
        var auftragsnummer = new Auftragsnummer(id);
        var status = zahlung.status(auftragsnummer);
        return zahlungMapper.toDto(status);
    }

    // In Wirklichkeit würde diese Bestätigung vom externen Zahlungsdienstleister kommen, nicht von der UI
    @PostMapping("/zahlung/{id}/bestaetigen")
    public ZahlungsstatusDto bestaetigeZahlungseingang(@PathVariable UUID id) {
        var auftragsnummer = new Auftragsnummer(id);
        zahlung.verarbeite(new ZahlungEingegangen(auftragsnummer));
        return zahlungMapper.toDto(zahlung.status(auftragsnummer));
    }
}
