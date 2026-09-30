package it.gruppoinit.stc.dao.impl;

import it.gruppoinit.stc.dao.ConfigurazioneDAO;
import it.gruppoinit.stc.domain.Configurazione;

import org.springframework.stereotype.Repository;

@Repository
public class ConfigurazioneDAOImpl extends BaseDAOImpl<Configurazione, Integer> implements ConfigurazioneDAO {

    @Override
    public Class<Configurazione> getEntityClass() {

	return Configurazione.class;
    }
}
