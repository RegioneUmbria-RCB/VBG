package it.alveo.segnalazioniproxy.dao;

import it.alveo.segnalazioniproxy.entities.SgPraticheAllegati;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SgPraticheAllegatiRepository extends JpaRepository<SgPraticheAllegati, Long> {
    Optional<SgPraticheAllegati> findByUuid(String uuidAllegato);

    void deleteByUuid(String uuidAllegato);

    int countByPraticaUuid(String uuidPratica);

    List<SgPraticheAllegati> findAllByPraticaUuid(String praticaUuid);

    @Query("SELECT a.pratica.uuid, COUNT(a) " +
            "FROM SgPraticheAllegati a " +
            "WHERE a.pratica.uuid IN :praticheUuids " +
            "GROUP BY a.pratica.uuid")
    List<Object[]> countByPraticaUuidIn(@Param("praticheUuids") List<String> praticheUuids);

}
