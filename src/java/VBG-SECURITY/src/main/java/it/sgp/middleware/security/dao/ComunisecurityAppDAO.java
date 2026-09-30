package it.sgp.middleware.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.sgp.middleware.security.domain.ComunisecurityApp;

@Repository
public interface ComunisecurityAppDAO extends JpaRepository<ComunisecurityApp, String>, BaseDAO {
}
