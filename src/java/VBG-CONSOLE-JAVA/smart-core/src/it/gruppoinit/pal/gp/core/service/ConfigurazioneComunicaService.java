package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.ConfigurazioneComunica;

public interface ConfigurazioneComunicaService extends BaseService<ConfigurazioneComunica, String> {

    public boolean existsByCodiceinventario(String idcomune, Integer codiceinventario);
}
