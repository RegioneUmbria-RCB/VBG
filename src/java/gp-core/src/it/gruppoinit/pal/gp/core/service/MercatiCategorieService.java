package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.MercatiCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface MercatiCategorieService extends BaseService<MercatiCategorie, PkId> {

    /**
     * Trova tutti i record della tabella per il software corrente
     * 
     * @param textToSearch
     * @return
     */
    List<MercatiCategorie> findByDescrizione(String textToSearch);
}
