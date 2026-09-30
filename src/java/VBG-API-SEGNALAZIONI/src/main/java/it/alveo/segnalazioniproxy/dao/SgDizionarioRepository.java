package it.alveo.segnalazioniproxy.dao;

import it.alveo.segnalazioniproxy.entities.SgDizionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SgDizionarioRepository extends JpaRepository<SgDizionario, Long> {
    @Query("SELECT d FROM SgDizionario d WHERE d.configurazione.id = :configurazioneId ORDER BY d.padre.id NULLS FIRST, d.ordine")
    List<SgDizionario> findAllByConfigurazioneId(@Param("configurazioneId") Integer configurazioneId);

    Optional<SgDizionario> findById(Integer id);

}
