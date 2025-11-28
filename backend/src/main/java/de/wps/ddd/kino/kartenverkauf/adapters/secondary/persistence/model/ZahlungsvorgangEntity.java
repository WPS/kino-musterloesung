package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "zahlungsvorgaenge", schema = "kartenverkauf")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZahlungsvorgangEntity {
    @Id
    private UUID auftragsnummer;
    private String status;
}
