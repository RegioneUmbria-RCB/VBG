package it.sgp.middleware.security.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sgp.middleware.security.dao.ComunisecurityParamDAO;
import it.sgp.middleware.security.domain.ComunisecurityParam;
import it.sgp.middleware.security.service.ComunisecurityParamService;

@Service
@Transactional
public class ComunisecurityParamServiceImpl extends BaseServiceImpl<ComunisecurityParam, String> implements ComunisecurityParamService {

    @Autowired
    private ComunisecurityParamDAO comunisecurityParamDAO;

    @Override
    public List<ComunisecurityParam> findAll() {

	return comunisecurityParamDAO.findAll();
    }

    @Override
    public ComunisecurityParam findById(String id) {

	return comunisecurityParamDAO.findById(id).orElse(null);
    }

    @Override
    public void insert(ComunisecurityParam entity) {

	if (validateEntity(entity)) {
	    comunisecurityParamDAO.save(entity);
	}
    }

    @Override
    public void update(ComunisecurityParam entity) {

	if (validateEntity(entity)) {
	    comunisecurityParamDAO.save(entity);
	}
    }

    @Override
    public void delete(ComunisecurityParam entity) {

	if (isDeleteAllowed(entity)) {
	    comunisecurityParamDAO.delete(entity);
	}
    }

    @Override
    public List<ComunisecurityParam> findAll(Integer firstResult, Integer maxResult) {

	//	DetachedCriteria det = getIdcomuneCriteria();
	//	if (null != firstResult && null != maxResult) {
	//	    return (List<E>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	//	} else {
	//	    return (List<E>) getHibernateTemplate().findByCriteria(det);
	//	}
	if (firstResult == null || maxResult == null) {
	    return findAll();
	}
	Page<ComunisecurityParam> allRecords = comunisecurityParamDAO.findAll(PageRequest.of(firstResult, maxResult));
	return allRecords.hasContent() ? allRecords.getContent() : Collections.emptyList();
    }

    @Override
    protected Class<ComunisecurityParam> getEntityClass() {

	return ComunisecurityParam.class;
    }

    @Override
    public Page<ComunisecurityParam> findAllByExamplePaginated(PageRequest pageable, Example<ComunisecurityParam> exampleFromRequest) {

	return comunisecurityParamDAO.findAll(exampleFromRequest, pageable);
    }
}
