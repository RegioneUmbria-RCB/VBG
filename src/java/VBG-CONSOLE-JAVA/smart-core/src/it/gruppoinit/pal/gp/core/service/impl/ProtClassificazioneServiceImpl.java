package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProtClassificazioneDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtClassificazione;
import it.gruppoinit.pal.gp.core.service.ProtClassificazioneService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class ProtClassificazioneServiceImpl extends BaseServiceImpl<ProtClassificazione, PkId> implements ProtClassificazioneService {

    private ProtClassificazioneDAO protclassificazioneDAO;

    @Autowired
    public void setProtClassificazioneDAO(ProtClassificazioneDAO protclassificazioneDAO) {

	this.protclassificazioneDAO = protclassificazioneDAO;
    }

    @Override
    protected Class<ProtClassificazione> getEntityClass() {

	return ProtClassificazione.class;
    }

    @Override
    public List<ProtClassificazione> findAll(Integer firstResult, Integer maxResult) {

	return protclassificazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ProtClassificazione entity) {

	if (validateEntity(entity)) {
	    protclassificazioneDAO.insert(entity);
	}
    }

    @Override
    public ProtClassificazione findById(PkId id) {

	return protclassificazioneDAO.findById(id);
    }

    @Override
    public void update(ProtClassificazione entity) {

	if (validateEntity(entity)) {
	    protclassificazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(ProtClassificazione entity) {

	if (isDeleteAllowed(entity)) {
	    protclassificazioneDAO.delete(entity);
	}
    }
}
