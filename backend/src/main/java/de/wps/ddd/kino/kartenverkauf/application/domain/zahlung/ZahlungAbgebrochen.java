package de.wps.ddd.kino.kartenverkauf.application.domain.zahlung;

import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record ZahlungAbgebrochen(Auftragsnummer auftragsnummer) {
}
