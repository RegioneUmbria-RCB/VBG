package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ConfigurazioneComunicaDAO;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneComunica;

import org.springframework.stereotype.Repository;

@Repository
public class ConfigurazioneComunicaDAOImpl extends BaseDAOImpl<ConfigurazioneComunica, String> implements ConfigurazioneComunicaDAO {

    @Override
    public Class<ConfigurazioneComunica> getEntityClass() {

	return ConfigurazioneComunica.class;
    }
}
