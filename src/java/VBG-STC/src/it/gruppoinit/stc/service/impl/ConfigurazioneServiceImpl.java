package it.gruppoinit.stc.service.impl;

import it.gruppoinit.stc.dao.ConfigurazioneDAO;
import it.gruppoinit.stc.domain.Configurazione;
import it.gruppoinit.stc.service.ConfigurazioneService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurazioneServiceImpl extends BaseServiceImpl<Configurazione, Integer> implements ConfigurazioneService {

    @Autowired
    ConfigurazioneDAO configurazioneDAO;

    @Override
    protected Class<Configurazione> getEntityClass() {

	return Configurazione.class;
    }

    @Override
    public void delete(Configurazione entity) {

	configurazioneDAO.delete(entity);
    }

    @Override
    public List<Configurazione> findAll(Integer firstResult, Integer maxResult) {

	return configurazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Configurazione findById(Integer id) {

	return configurazioneDAO.findById(id);
    }

    @Override
    public void insert(Configurazione entity) {

	if (validate(entity)) {
	    configurazioneDAO.insert(entity);
	}
    }

    @Override
    public void update(Configurazione entity) {

	if (validate(entity)) {
	    configurazioneDAO.update(entity);
	}
    }
}
