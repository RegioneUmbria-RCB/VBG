package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.domain.Contenttypes;
import it.gruppoinit.pal.gp.core.domain.ContenttypesId;

public interface ContenttypesService extends BaseService<Contenttypes, ContenttypesId> {

    /**
     * Estrae il mimetype a partire da un nomefile, e quindi dall'estensione
     * Se non viene trovato nessun mime type per l'estensione del file restituisce 'text/plain'
     * 
     * @param nomeFile
     *            es. Trasferta.doc
     * @return la stringa che rappresenta il mimetype es. "application/msword"
     */
    public String findMimeTypeByFileName(String nomeFile);

    /**
     * Estrae il mimetype a partire da un nomefile, e quindi dall'estensione
     * Se non viene trovato nessun mime type per l'estensione del file restituisce il secondo argomento
     * 
     * @param nomeFile
     *            es. Trasferta.doc
     * @return la stringa che rappresenta il mimetype es. "application/msword"
     */
    public String findMimeTypeByFileName(String nomeFile, String defaultMimeType);
    @DeletableCacheElements
    public void resetObjectCached();
}
