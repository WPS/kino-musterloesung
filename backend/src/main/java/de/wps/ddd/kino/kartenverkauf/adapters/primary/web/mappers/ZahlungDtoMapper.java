package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.GeldbetragDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZahlungsstatusDto;
import de.wps.ddd.kino.kartenverkauf.application.domain.kartenverkauf.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.zahlung.Zahlungsstatus;
import org.springframework.stereotype.Component;

@Component
public class ZahlungDtoMapper {

    public GeldbetragDto toDto(Geldbetrag geldbetrag) {
        return new GeldbetragDto(geldbetrag.getBetrag(), geldbetrag.getWaehrung().toString());
    }

    public ZahlungsstatusDto toDto(Zahlungsstatus status) {
        return new ZahlungsstatusDto(status.toString());
    }
}
