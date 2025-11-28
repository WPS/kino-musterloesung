package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.VorstellungEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface VorstellungRepository extends JpaRepository<VorstellungEntity, UUID> {

}
