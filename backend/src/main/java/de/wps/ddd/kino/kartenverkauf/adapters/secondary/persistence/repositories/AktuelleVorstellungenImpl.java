package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.VorstellungEntityMapper;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.vorstellungen.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.AktuelleVorstellungen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AktuelleVorstellungenImpl implements AktuelleVorstellungen {

    private final VorstellungRepository vorstellungRepository;

    private final VorstellungEntityMapper vorstellungMapper;

    @Override
    public List<Vorstellung> alleVorstellungen() {
        var vorstellungen = vorstellungRepository.findAll();
        return vorstellungen.stream().map(vorstellungMapper::toDomain).toList();
    }

    @Override
    public Vorstellung holeVorstellung(VorstellungId vorstellungId) {
        var vorstellung = vorstellungRepository.findById(vorstellungId.uuid());
        return vorstellung.map(vorstellungMapper::toDomain).orElseThrow();
    }
}
