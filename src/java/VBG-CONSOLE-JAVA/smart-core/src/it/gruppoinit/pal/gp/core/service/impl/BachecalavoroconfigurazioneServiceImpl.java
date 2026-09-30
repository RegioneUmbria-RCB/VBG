package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.BachecalavoroconfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Bachecalavoroconfigurazione;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.service.BachecalavoroconfigurazioneService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class BachecalavoroconfigurazioneServiceImpl extends BaseServiceImpl<Bachecalavoroconfigurazione, String> implements
	BachecalavoroconfigurazioneService {

    private BachecalavoroconfigurazioneDAO bachecalavoroconfigurazioneDAO;

    @Autowired
    public void setBachecalavoroconfigurazioneDAO(BachecalavoroconfigurazioneDAO bachecalavoroconfigurazioneDAO) {

	this.bachecalavoroconfigurazioneDAO = bachecalavoroconfigurazioneDAO;
    }

    @Override
    protected Class<Bachecalavoroconfigurazione> getEntityClass() {

	return Bachecalavoroconfigurazione.class;
    }

    @Override
    public List<Bachecalavoroconfigurazione> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return bachecalavoroconfigurazioneDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Bachecalavoroconfigurazione entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    bachecalavoroconfigurazioneDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Bachecalavoroconfigurazione findById(String id) {

	// §§§BEGIN§§§
	return bachecalavoroconfigurazioneDAO.findById(ORMHelper.getIdcomune());
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Bachecalavoroconfigurazione entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    bachecalavoroconfigurazioneDAO.update(entity);
	}
	// §§§END§§§
	
    }

    @Override
    public void delete(Bachecalavoroconfigurazione entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    bachecalavoroconfigurazioneDAO.delete(entity);
	}
	// §§§BEGIN§§§
    }

    protected boolean isDeleteAllowed(Faq entity) {

	boolean delete = true;
	return delete;
    }
}
