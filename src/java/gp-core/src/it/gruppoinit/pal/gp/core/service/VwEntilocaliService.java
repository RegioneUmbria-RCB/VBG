package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.VwEntilocali;

public interface VwEntilocaliService extends BaseService<VwEntilocali, String> {

    public List<VwEntilocali> findByDescrizione(String textToSearch);
}
