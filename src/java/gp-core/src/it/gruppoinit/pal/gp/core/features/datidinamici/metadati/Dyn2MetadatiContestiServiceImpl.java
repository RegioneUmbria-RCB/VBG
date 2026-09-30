package it.gruppoinit.pal.gp.core.features.datidinamici.metadati;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Dyn2MetadatiContesti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.datidinamici.metadati.dao.Dyn2MetadatiContestiDAO;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class Dyn2MetadatiContestiServiceImpl extends BaseServiceImpl<Dyn2MetadatiContesti, PkId> implements Dyn2MetadatiContestiService {

    @Autowired
    private Dyn2MetadatiContestiDAO dyn2MetadatiContestiDAO;

    @Override
    public void insert(Dyn2MetadatiContesti entity) {

	if (validateEntity(entity)) {
	    dyn2MetadatiContestiDAO.insert(entity);
	}
    }

    @Override
    public void update(Dyn2MetadatiContesti entity) {

	if (validateEntity(entity)) {
	    dyn2MetadatiContestiDAO.update(entity);
	}
    }

    @Override
    public void delete(Dyn2MetadatiContesti entity) {

	if (isDeleteAllowed(entity)) {
	    dyn2MetadatiContestiDAO.delete(entity);
	}
    }

    @Override
    public List<Dyn2MetadatiContesti> findAll(Integer firstResult, Integer maxResult) {

	//throw new NotImplementedException();
	return dyn2MetadatiContestiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Dyn2MetadatiContesti findById(PkId id) {

	return dyn2MetadatiContestiDAO.findById(id);
    }

    @Override
    protected Class<Dyn2MetadatiContesti> getEntityClass() {

	return Dyn2MetadatiContesti.class;
    }
}
