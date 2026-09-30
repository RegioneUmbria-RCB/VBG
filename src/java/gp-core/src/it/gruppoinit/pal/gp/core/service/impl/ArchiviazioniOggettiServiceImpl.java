package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ArchiviazioniOggettiDAO;
import it.gruppoinit.pal.gp.core.domain.ArchiviazioniOggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniOggettiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class ArchiviazioniOggettiServiceImpl extends BaseServiceImpl<ArchiviazioniOggetti, PkId> implements ArchiviazioniOggettiService {

    private ArchiviazioniOggettiDAO archiviazionioggettiDAO;

    @Autowired
    public void setArchiviazioniOggettiDAO(ArchiviazioniOggettiDAO archiviazionioggettiDAO) {

	this.archiviazionioggettiDAO = archiviazionioggettiDAO;
    }

    @Override
    protected Class<ArchiviazioniOggetti> getEntityClass() {

	return ArchiviazioniOggetti.class;
    }

    @Override
    public List<ArchiviazioniOggetti> findAll(Integer firstResult, Integer maxResult) {

	return archiviazionioggettiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ArchiviazioniOggetti entity) {

	if (validateEntity(entity)) {
	    archiviazionioggettiDAO.insert(entity);
	}
    }

    @Override
    public ArchiviazioniOggetti findById(PkId id) {

	return archiviazionioggettiDAO.findById(id);
    }

    @Override
    public void update(ArchiviazioniOggetti entity) {

	if (validateEntity(entity)) {
	    archiviazionioggettiDAO.update(entity);
	}
    }

    @Override
    public void delete(ArchiviazioniOggetti entity) {

	if (isDeleteAllowed(entity)) {
	    archiviazionioggettiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ArchiviazioniOggetti entity) {

	return true;
    }

    @Override
    public void insert(Integer codiceOggetto, Integer codiceArchiviazioniIstanze) {

	archiviazionioggettiDAO.insert(codiceOggetto, codiceArchiviazioniIstanze);
    }

    @Override
    public void evict(ArchiviazioniOggetti entity) {

	archiviazionioggettiDAO.evict(entity);
    }
}
