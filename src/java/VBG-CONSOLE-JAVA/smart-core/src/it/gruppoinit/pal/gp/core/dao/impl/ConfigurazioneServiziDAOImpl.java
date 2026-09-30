package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ConfigurazioneServiziDAO;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneServizi;

import org.springframework.stereotype.Repository;

@Repository
public class ConfigurazioneServiziDAOImpl extends BaseDAOImpl<ConfigurazioneServizi, String> implements ConfigurazioneServiziDAO {

    @Override
    public Class<ConfigurazioneServizi> getEntityClass() {

	return ConfigurazioneServizi.class;
    }
}
