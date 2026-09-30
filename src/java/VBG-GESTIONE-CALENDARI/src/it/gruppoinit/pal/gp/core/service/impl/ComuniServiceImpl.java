package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.service.ComuniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComuniServiceImpl extends BaseServiceImpl<Comuni, String> implements ComuniService {

    private ComuniDAO comuniDAO;

    @Autowired
    public void setComuniDAO(ComuniDAO comuniDAO) {

	this.comuniDAO = comuniDAO;
    }

    @Override
    public void delete(Comuni entity) {

	comuniDAO.delete(entity);
    }

    @Override
    public Comuni findById(String id) {

	return comuniDAO.findById(id);
    }

    @Override
    public void insert(Comuni entity) {

	comuniDAO.insert(entity);
    }

    @Override
    public void update(Comuni entity) {

	comuniDAO.update(entity);
    }

    @Override
    public Class<Comuni> getEntityClass() {

	return Comuni.class;
    }

    @Override
    public Comuni findByCodiceComune(Comuni entity) {

	return comuniDAO.findByCodiceComune(entity);
    }

    @Override
    public List<Comuni> findByDescrizione(String comune) {

	return comuniDAO.findByDescrizione(comune);
    }
}
