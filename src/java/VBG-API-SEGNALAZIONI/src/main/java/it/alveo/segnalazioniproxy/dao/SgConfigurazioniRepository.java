package it.alveo.segnalazioniproxy.dao;

import it.alveo.segnalazioniproxy.entities.SgConfigurazioni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SgConfigurazioniRepository extends JpaRepository<SgConfigurazioni, Long> {
    Optional<SgConfigurazioni> findByAliasAndSoftware(String alias, String software);

    Optional<String> findStcWsUrlByAliasAndSoftware(String alias, String software);

    List<SgConfigurazioni> findAll();

}
