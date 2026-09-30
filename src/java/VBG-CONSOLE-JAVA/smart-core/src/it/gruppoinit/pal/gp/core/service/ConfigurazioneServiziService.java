package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.ConfigurazioneServizi;

public interface ConfigurazioneServiziService extends BaseService<ConfigurazioneServizi, String> {

    boolean checkModulisticaNazionale(String idente);
}
