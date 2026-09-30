package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProtTprofilassegnazioneDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtTprofilassegnazione;
import it.gruppoinit.pal.gp.core.service.ProtTprofilassegnazioneService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class ProtTprofilassegnazioneServiceImpl extends BaseServiceImpl<ProtTprofilassegnazione, PkId> implements ProtTprofilassegnazioneService {

    private ProtTprofilassegnazioneDAO prottprofilassegnazioneDAO;

    @Autowired
    public void setProtTprofilassegnazioneDAO(ProtTprofilassegnazioneDAO prottprofilassegnazioneDAO) {

	this.prottprofilassegnazioneDAO = prottprofilassegnazioneDAO;
    }

    @Override
    protected Class<ProtTprofilassegnazione> getEntityClass() {

	return ProtTprofilassegnazione.class;
    }

    @Override
    public List<ProtTprofilassegnazione> findAll(Integer firstResult, Integer maxResult) {

	return prottprofilassegnazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ProtTprofilassegnazione entity) {

	if (validateEntity(entity)) {
	    prottprofilassegnazioneDAO.insert(entity);
	}
    }

    @Override
    public ProtTprofilassegnazione findById(PkId id) {

	return prottprofilassegnazioneDAO.findById(id);
    }

    @Override
    public void update(ProtTprofilassegnazione entity) {

	if (validateEntity(entity)) {
	    prottprofilassegnazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(ProtTprofilassegnazione entity) {

	if (isDeleteAllowed(entity)) {
	    prottprofilassegnazioneDAO.delete(entity);
	}
    }
}
