package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import it.gruppoinit.pal.gp.core.domain.Layouttestibase;
import it.gruppoinit.pal.gp.core.domain.LayouttestibaseId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LayouttestibaseServiceImpl extends BaseServiceImpl<Layouttestibase, LayouttestibaseId> implements LayouttestibaseService {

    private LayouttestibaseDAO layouttestibaseDAO;

    @Autowired
    public void setLayouttestibaseDAO(LayouttestibaseDAO layouttestibaseDAO) {

	this.layouttestibaseDAO = layouttestibaseDAO;
    }

    @Override
    protected Class<Layouttestibase> getEntityClass() {

	return Layouttestibase.class;
    }

    @Override
    public void delete(Layouttestibase entity) {

	layouttestibaseDAO.delete(entity);
    }

    @Override
    public List<Layouttestibase> findAll(Integer firstResult, Integer maxResult) {

	return layouttestibaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Layouttestibase findById(LayouttestibaseId id) {

	return layouttestibaseDAO.findById(id);
    }

    @Override
    public void insert(Layouttestibase entity) {

	if (validateEntity(entity)) {
	    layouttestibaseDAO.insert(entity);
	}
    }

    @Override
    public void update(Layouttestibase entity) {

	if (validateEntity(entity)) {
	    layouttestibaseDAO.update(entity);
	}
    }

    @Override
    public String resolveCode(String code, String software) {

	return layouttestibaseDAO.resolveCode(code, software);
    }

    @Override
    public List<Layouttestibase> findByPrefissoOrderBySoftware(String prefissoEtichette) {

	return layouttestibaseDAO.findByPrefissoOrderBySoftware(prefissoEtichette);
    }
}
