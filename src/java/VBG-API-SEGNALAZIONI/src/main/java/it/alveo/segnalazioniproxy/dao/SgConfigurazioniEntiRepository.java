package it.alveo.segnalazioniproxy.dao;

import it.alveo.segnalazioniproxy.entities.SgConfigurazioniEnti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface SgConfigurazioniEntiRepository extends JpaRepository<SgConfigurazioniEnti, Long> {
    Optional<SgConfigurazioniEnti> findByCodiceComune(String codiceComune);

    @Query(value = "SELECT e.codice_comune AS codice, e.descrizione_comune AS descrizione " +
            "FROM sg_configurazioni_enti e " +
            "JOIN sg_configurazioni c ON e.fk_sg_configurazione_id = c.id " +
            "WHERE c.id = :configurazioneId", nativeQuery = true)
    List<Map<String, Object>> findEntiByConfigurazioneIdNative(@Param("configurazioneId") Integer configurazioneId);

    Optional<SgConfigurazioniEnti> findByCodiceComuneAndConfigurazioneId(String codiceComune, Integer configurazioneId);

    boolean existsByCodiceComuneAndConfigurazioneId(String codiceComune, Integer configurazioneId);

}
