package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface ZipLogicoDAO extends BaseDAO {

    /**
     * La funzionalità ritorna la lista di tutte le testate mancanti. NON FILTRA PER IDCOMUNE, in quanto viene
     * utilizzata in fase di Setup
     * 
     * @return
     */
    Set<MovimentiZipLogicoTestataHelper> recuperaTestateMancanti();

    void eliminaZipLogicoByCodiceMovimento(Integer codiceMovimento);

    Set<MovimentiZipLogico> findMovimentiZipLogicoByMovimento(Integer codicemovimento);

    List<MovimentiZipLogicoDTO> findMovimentiZipLogicoDTOByMovimento(Integer codicemovimento);

    Boolean isZipLogicoExistInMovimento(Integer codicemovimento);

    Boolean isDocumentoPresenteInZipLogico(Integer codiceZipLogico, Integer codiceMovimento, Integer codiceDocumento, String associationPath);

    void insertDettaglio(MovimentiZipLogico entity);

    List<MovimentiZipLogico> findAllDettagli(Integer firstResult, Integer maxResult);

    MovimentiZipLogico getMovimentiZipLogicoById(PkId id);

    void updateDettaglio(MovimentiZipLogico entity);

    void insertTestata(MovimentiZipLogicoTestataHelper testata);

    void deleteDettaglio(MovimentiZipLogico entity);

    Boolean existDettagliByCodiceMovimento(Integer codiceMovimento);

    MovimentiZipLogicoTestataHelper findByCodiceMovimento(Integer codiceMovimento);

    Integer contaDocumenti(Integer codiceMovimento);

    MovimentiZipLogicoTestata findByGuid(String guid);
}
