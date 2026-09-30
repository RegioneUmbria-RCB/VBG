package it.sgp.middleware.security.service;

import java.util.List;

import it.sgp.middleware.security.domain.ComuniSecuritySessMetadati;
import it.sgp.middleware.security.domain.ComunisecuritySession;
import it.sgp.middleware.security.domain.composedfields.ComuniSecuritySessMetaPK;

public interface ComuniSecuritySessMetadatiService extends BaseService<ComuniSecuritySessMetadati, ComuniSecuritySessMetaPK>{

	public void bulkInsert(List<ComuniSecuritySessMetadati> entities);
	public List<ComuniSecuritySessMetadati> findAllByToken(String token);
	void insertNewTokenWithMetadati(ComunisecuritySession currTokenEntity, ComunisecuritySession newTokenEntity, List<ComuniSecuritySessMetadati> entities);
}
