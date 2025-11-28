package de.wps.ddd.kino.kartenverkauf.application.domain.zahlung;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public enum Zahlungsstatus {
    Ausstehend,
    Eingegangen,
    Abgebrochen,
}
