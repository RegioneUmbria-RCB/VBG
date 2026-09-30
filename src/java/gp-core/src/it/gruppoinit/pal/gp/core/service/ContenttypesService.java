package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.domain.Contenttypes;
import it.gruppoinit.pal.gp.core.domain.ContenttypesId;

public interface ContenttypesService extends BaseService<Contenttypes, ContenttypesId> {

    /**
     * Estrae il mimetype a partire da un nomefile, e quindi dall'estensione
     * 
     * @param nomeFile
     *            es. Trasferta.doc
     * @return la stringa che rappresenta il mimetype es. "application/msword"
     */
    public String findMimeTypeByFileName(String nomeFile);

    @DeletableCacheElements
    public void resetObjectCached();
}
