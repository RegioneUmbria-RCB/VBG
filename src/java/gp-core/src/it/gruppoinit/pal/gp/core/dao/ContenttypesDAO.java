package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Contenttypes;
import it.gruppoinit.pal.gp.core.domain.ContenttypesId;

public interface ContenttypesDAO extends BaseDAO<Contenttypes, ContenttypesId> {

    /**
     * Estrae il mimetype a partire da un nomefile, e quindi dall'estensione
     * 
     * @param nomeFile
     *            es. Trasferta.doc
     * @return la stringa che rappresenta il mimetype es. "application/msword"
     */
    public String findMimeTypeByFileName(String nomeFile);
}
