package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface MovimentiZipLogicoDAO extends BaseDAO<MovimentiZipLogico, PkId> {

    /**
     * Ritorna una lista paginata di record di tipo MovimentiZipLogico
     * 
     * @param firstResult
     * @param maxResult
     */
    public List<MovimentiZipLogico> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista di oggetti di tipo MovimentiZipLogicoDTO che appartengono al codicemovimento passato
     * 
     * @param codicemovimento
     * @return
     */
    public List<MovimentiZipLogicoDTO> findMovimentiZipLogicoDTOByMovimento(Integer codicemovimento);

    /**
     * Torna la lista di oggetti di tipo MovimentiZipLogico che appartengono al codicemovimento passato
     * 
     * @param codicemovimento
     * @return
     */
    public Set<MovimentiZipLogico> findMovimentiZipLogicoByMovimento(Integer codicemovimento);

    /**
     * Torna un oggetto MovimentiZipLogico in base alla sua PK
     * 
     * @param id
     * @return
     */
    public MovimentiZipLogico getMovimentiZipLogicoById(PkId id);

    /**
     * La funzionalità elimina tutti i record di MOVIMENTI_ZIP_LOGICO per il codicemovimento passato
     * 
     * @param codiceMovimento
     */
    public void eliminaZipLogicoByCodiceMovimento(Integer codiceMovimento);

    /**
     * La funzionalità ritorna la lista di tutte le testate mancanti. NON FILTRA PER IDCOMUNE, in quanto viene
     * utilizzata in fase di Setup
     * 
     * @return
     */
    Set<MovimentiZipLogicoTestataHelper> recuperaTestateMancanti();

    Boolean isDocumentoPresenteInZipLogico(Integer codiceZipLogico, Integer codiceMovimento, Integer codiceDocumento, String associationPath);
}
