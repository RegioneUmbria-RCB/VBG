package it.sgp.middleware.security.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sgp.middleware.security.dao.ComunisecuritySessMetadatiDAO;
import it.sgp.middleware.security.dao.ComunisecuritySessionDAO;
import it.sgp.middleware.security.domain.ComuniSecuritySessMetadati;
import it.sgp.middleware.security.domain.ComunisecuritySession;
import it.sgp.middleware.security.domain.composedfields.ComuniSecuritySessMetaPK;
import it.sgp.middleware.security.service.ComuniSecuritySessMetadatiService;

@Service
@Transactional
public class ComuniSecuritySessMetadatiServiceImpl extends BaseServiceImpl<ComuniSecuritySessMetadati, ComuniSecuritySessMetaPK> implements ComuniSecuritySessMetadatiService{

	@Autowired
	ComunisecuritySessMetadatiDAO comunisecuritySessMetadatiDAO;
	
	@Autowired
	ComunisecuritySessionDAO comunisecuritySessionDAO;
	
	@Override
	public void insert(ComuniSecuritySessMetadati entity) {
		comunisecuritySessMetadatiDAO.save(entity);
	}

	@Override
	public void update(ComuniSecuritySessMetadati entity) {
		throw new RuntimeException("Unimplemented method");
		
	}

	@Override
	public void delete(ComuniSecuritySessMetadati entity) {
		comunisecuritySessMetadatiDAO.delete(entity);
		
	}

	@Override
	public List<ComuniSecuritySessMetadati> findAll() {
		throw new RuntimeException("Unimplemented method");
	}

	@Override
	public List<ComuniSecuritySessMetadati> findAll(Integer firstResult, Integer maxResult) {
		throw new RuntimeException("Unimplemented method");
	}



	@Override
	public Page<ComuniSecuritySessMetadati> findAllByExamplePaginated(PageRequest pageable,
			Example<ComuniSecuritySessMetadati> exampleFromRequest) {
		throw new RuntimeException("Unimplemented method");
	}

	@Override
	protected Class<ComuniSecuritySessMetadati> getEntityClass() {
		throw new RuntimeException("Unimplemented method");
	}

	@Override
	public void bulkInsert(List<ComuniSecuritySessMetadati> entities) {
		for(ComuniSecuritySessMetadati entity : entities) {
			this.insert(entity);
		}
		
	}

	@Override
	public List<ComuniSecuritySessMetadati> findAllByToken(String token) {
		return comunisecuritySessMetadatiDAO.findAllByToken(token);
	}

	@Override
	public void insertNewTokenWithMetadati(ComunisecuritySession currTokenEntity, ComunisecuritySession newTokenEntity, List<ComuniSecuritySessMetadati> entities) {
		
		if(comunisecuritySessionDAO.existsById(newTokenEntity.getId())) {
			throw new RuntimeException("token already exists");
		}
		
		comunisecuritySessionDAO.save(newTokenEntity);
		
		for(ComuniSecuritySessMetadati entity : entities) {
			this.insert(entity);
		}
		
		currTokenEntity.setValid(false);
		comunisecuritySessionDAO.save(currTokenEntity);
		
	}

	@Override
	public ComuniSecuritySessMetadati findById(ComuniSecuritySessMetaPK id) {
		throw new RuntimeException("Unimplemented method");
	}
}
