package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ConfigurazioneServiziDAO;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneServizi;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneServiziService;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurazioneServiziServiceImpl extends BaseServiceImpl<ConfigurazioneServizi, String> implements ConfigurazioneServiziService {

    @Autowired
    private ConfigurazioneServiziDAO configurazioneServiziDAO;

    @Override
    public void insert(ConfigurazioneServizi entity) {

	if (validateEntity(entity)) {
	    configurazioneServiziDAO.insert(entity);
	}
    }

    @Override
    public void update(ConfigurazioneServizi entity) {

	if (validateEntity(entity)) {
	    configurazioneServiziDAO.update(entity);
	}
    }

    @Override
    public void delete(ConfigurazioneServizi entity) {

	if (isDeleteAllowed(entity)) {
	    configurazioneServiziDAO.delete(entity);
	}
    }

    @Override
    public List<ConfigurazioneServizi> findAll(Integer firstResult, Integer maxResult) {

	return configurazioneServiziDAO.findAll(firstResult, maxResult);
    }

    @Override
    public ConfigurazioneServizi findById(String id) {

	return configurazioneServiziDAO.findById(id);
    }

    @Override
    protected Class<ConfigurazioneServizi> getEntityClass() {

	return ConfigurazioneServizi.class;
    }

    @Override
    public boolean checkModulisticaNazionale(String idente) {

	ConfigurazioneServizi s = this.findById(idente);
	if (s != null) {
	    return BooleanUtils.isTrue(s.getFlagApModulisticanazionale());
	}
	return false;
    }
}
