package it.sgp.middleware.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.sgp.middleware.security.domain.ComunisecurityParam;

/**
 * 
 * @author
 */
@Repository
public interface ComunisecurityParamDAO extends JpaRepository<ComunisecurityParam, String> {
}
