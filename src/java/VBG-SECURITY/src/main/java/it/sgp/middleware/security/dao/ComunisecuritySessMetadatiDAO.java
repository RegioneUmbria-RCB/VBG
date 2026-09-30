package it.sgp.middleware.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.sgp.middleware.security.domain.ComuniSecuritySessMetadati;
import it.sgp.middleware.security.domain.composedfields.ComuniSecuritySessMetaPK;

@Repository
public interface ComunisecuritySessMetadatiDAO extends JpaRepository<ComuniSecuritySessMetadati , ComuniSecuritySessMetaPK>{
	public List<ComuniSecuritySessMetadati> findAllByToken(String token);

}
