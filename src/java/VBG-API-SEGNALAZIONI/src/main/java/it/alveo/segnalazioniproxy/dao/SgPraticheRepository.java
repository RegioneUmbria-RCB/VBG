package it.alveo.segnalazioniproxy.dao;

import it.alveo.segnalazioniproxy.entities.SgPratiche;
import it.alveo.segnalazioniproxy.enums.Stato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SgPraticheRepository extends JpaRepository<SgPratiche, Long> {
    Optional<SgPratiche> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    boolean existsByConfigurazioneIdAndUuid(Integer configurazioneId, String uuid);

    SgPratiche findByUuidAndStato(String uuid, Stato stato);

    List<SgPratiche> findAllByStato(Stato stato);

    @Query("SELECT p FROM SgPratiche p WHERE p.configurazione.id = :configurazioneId " +
            "AND (:utente IS NULL OR p.utente = :utente) " +
            "AND (:comuni IS NULL OR p.codiceComune IN :comuni) " +
            "AND (:categorie IS NULL OR p.dizionario.id IN :categorie) " +
            "AND (:dallaData IS NULL OR p.dataInvio >= :dallaData) " +
            "AND (:allaData IS NULL OR p.dataInvio <= :allaData) " +
            "AND (:statoAvanzamento IS NULL OR UPPER(p.statoAvanzamentoEnte) LIKE UPPER(:statoAvanzamento)) " +
            "AND (:statoPratica IS NULL OR UPPER(p.stato) LIKE UPPER(:statoPratica))" +
            "AND p.stato <> it.alveo.segnalazioniproxy.enums.Stato.ELIMINATA " +
            "ORDER BY p.dataInvio DESC, p.dataCreazione DESC")
    Page<SgPratiche> findSelezionePraticheNotEliminate(
            Integer configurazioneId,
            String utente,
            List<String> comuni,
            List<Integer> categorie,
            LocalDateTime dallaData,
            LocalDateTime allaData,
            String statoAvanzamento,
            String statoPratica,
            Pageable pageable
    );

    @Query("SELECT COUNT(p) FROM SgPratiche p WHERE p.configurazione.id = :configurazioneId " +
            "AND (:utente IS NULL OR p.utente = :utente) " +
            "AND (:comuni IS NULL OR p.codiceComune IN :comuni) " +
            "AND (:categorie IS NULL OR p.dizionario.id IN :categorie) " +
            "AND (:dallaData IS NULL OR p.dataInvio >= :dallaData) " +
            "AND (:allaData IS NULL OR p.dataInvio <= :allaData)" +
            "AND (:statoAvanzamento IS NULL OR UPPER(p.statoAvanzamentoEnte) LIKE UPPER(:statoAvanzamento)) " +
            "AND (:statoPratica IS NULL OR UPPER(p.stato) LIKE UPPER(:statoPratica))" +
            "AND p.stato <> it.alveo.segnalazioniproxy.enums.Stato.ELIMINATA ")
    long countSelezionePraticheNotEliminate(
            Integer configurazioneId,
            String utente,
            List<String> comuni,
            List<Integer> categorie,
            LocalDateTime dallaData,
            LocalDateTime allaData,
            String statoAvanzamento,
            String statoPratica
    );
}
