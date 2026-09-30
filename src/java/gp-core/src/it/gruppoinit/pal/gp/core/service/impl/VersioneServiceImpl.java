package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VersioneDAO;
import it.gruppoinit.pal.gp.core.domain.Versione;
import it.gruppoinit.pal.gp.core.service.VersioneService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersioneServiceImpl extends BaseServiceImpl<Versione, String> implements VersioneService {

    @Autowired
    private VersioneDAO versioneDAO;

    @Override
    public void delete(Versione entity) {

	versioneDAO.delete(entity);
    }

    /**
     * @see VersioneDAO#findAll(Integer, Integer)
     */
    @Override
    public List<Versione> findAll(Integer firstResult, Integer maxResult) {

	return versioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Versione findById(String id) {

	return versioneDAO.findById(id);
    }

    @Override
    public void insert(Versione entity) {

	versioneDAO.insert(entity);
    }

    @Override
    public void update(Versione entity) {

	versioneDAO.update(entity);
    }

    @Override
    protected Class<Versione> getEntityClass() {

	return Versione.class;
    }
}
