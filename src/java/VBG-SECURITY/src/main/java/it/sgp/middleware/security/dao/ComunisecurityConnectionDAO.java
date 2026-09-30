package it.sgp.middleware.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.sgp.middleware.security.domain.ComunisecurityConnection;
import it.sgp.middleware.security.domain.ComunisecurityConnectionId;

/**
 * 
 * @author
 */
@Repository
public interface ComunisecurityConnectionDAO extends JpaRepository<ComunisecurityConnection, ComunisecurityConnectionId> {
}
