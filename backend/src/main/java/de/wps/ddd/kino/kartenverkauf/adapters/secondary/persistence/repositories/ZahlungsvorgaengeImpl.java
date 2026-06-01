package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.common.error.RessourceNichtGefunden;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.ZahlungsvorgangEntityMapper;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsvorgang;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.Zahlungsvorgaenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZahlungsvorgaengeImpl implements Zahlungsvorgaenge {

    private final ZahlungsvorgangRepository zahlungsvorgangRepository;

    private final ZahlungsvorgangEntityMapper zahlungsvorgangMapper;

    @Override
    public void speichere(Zahlungsvorgang zahlungsvorgang) {
        var entity = zahlungsvorgangMapper.toEntity(zahlungsvorgang);
        zahlungsvorgangRepository.save(entity);
    }

    @Override
    public Zahlungsvorgang hole(Auftragsnummer auftragsnummer) {
        var entity = zahlungsvorgangRepository.findById(auftragsnummer.nummer());
        RessourceNichtGefunden.throwIf(entity.isEmpty(), "Zahlungsvorgang " + auftragsnummer + " existiert nicht");
        return zahlungsvorgangMapper.toDomain(entity.get());
    }

}
