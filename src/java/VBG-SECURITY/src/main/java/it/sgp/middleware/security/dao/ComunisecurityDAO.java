package it.sgp.middleware.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.sgp.middleware.security.domain.Comunisecurity;

@Repository
public interface ComunisecurityDAO extends JpaRepository<Comunisecurity, String> {

    /**
     * if (StringUtils.isNotBlank(descrizione)) { criteria.add(Restrictions.or(Restrictions.ilike("id", "%" +
     * descrizione + "%"), Restrictions.ilike("descrizione", "%" + descrizione + "%"))); }
     * criteria.addOrder(Order.asc("descrizione"));
     * 
     * @param descrizione
     * @param id
     * @return
     */
    List<Comunisecurity> findByDescrizioneContainingOrIdContainingAllIgnoreCaseOrderByDescrizioneAsc(String descrizione, String id);
}
