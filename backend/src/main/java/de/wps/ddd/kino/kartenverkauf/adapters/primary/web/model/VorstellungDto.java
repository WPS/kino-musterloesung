package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model;

import java.util.UUID;

public record VorstellungDto(UUID uuid, String beginn, String saal, String film) {
}
