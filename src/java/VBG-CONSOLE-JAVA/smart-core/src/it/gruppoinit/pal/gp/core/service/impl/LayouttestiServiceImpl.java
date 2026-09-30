package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.LayouttestiDAO;
import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.LayouttestiId;
import it.gruppoinit.pal.gp.core.service.LayouttestiService;
import it.gruppoinit.pal.gp.core.service.LayouttestibaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LayouttestiServiceImpl extends BaseServiceImpl<Layouttesti, LayouttestiId> implements LayouttestiService {

    private LayouttestiDAO layouttestiDAO;
    private LayouttestibaseService layouttestibaseService;

    @Autowired
    public void setLayouttestiDAO(LayouttestiDAO layouttestiDAO) {

	this.layouttestiDAO = layouttestiDAO;
    }

    @Autowired
    public void setLayouttestibaseService(LayouttestibaseService layouttestibaseService) {

	this.layouttestibaseService = layouttestibaseService;
    }

    @Override
    protected Class<Layouttesti> getEntityClass() {

	return Layouttesti.class;
    }

    @Override
    public void delete(Layouttesti entity) {

	layouttestiDAO.delete(entity);
    }

    @Override
    public List<Layouttesti> findAll(Integer firstResult, Integer maxResult) {

	return layouttestiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Layouttesti findById(LayouttestiId id) {

	return layouttestiDAO.findById(id);
    }

    @Override
    public void insert(Layouttesti entity) {

	layouttestiDAO.insert(entity);
    }

    @Override
    public void update(Layouttesti entity) {

	layouttestiDAO.update(entity);
    }

    @Override
    public String resolveCode(String code, String software) {

	String decodedText = layouttestiDAO.resolveCode(code, software);
	if (decodedText == null) {
	    decodedText = layouttestibaseService.resolveCode(code, software);
	}
	return decodedText;
    }
}
