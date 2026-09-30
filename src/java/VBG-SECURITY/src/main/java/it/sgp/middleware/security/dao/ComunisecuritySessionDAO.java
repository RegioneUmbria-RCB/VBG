package it.sgp.middleware.security.dao;

import java.util.Date;

import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.sgp.middleware.security.domain.ComunisecuritySession;

@Repository
public interface ComunisecuritySessionDAO extends JpaRepository<ComunisecuritySession, String>, BaseDAO {

    Window<ComunisecuritySession> findFirst1000ByLastrequestLessThan(Date lastrequest, ScrollPosition position);
}
