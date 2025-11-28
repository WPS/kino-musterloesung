package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.SaalplanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SaalplanRepository extends JpaRepository<SaalplanEntity, Long> {
    SaalplanEntity findByVorstellungUUID(UUID vorstellungUUID);

    @Query("SELECT s.id FROM SaalplanEntity s WHERE s.vorstellungUUID = :vorstellungUUID")
    Optional<Integer> findIdByVorstellungUUID(@Param("vorstellungUUID") UUID vorstellungUUID);
}
