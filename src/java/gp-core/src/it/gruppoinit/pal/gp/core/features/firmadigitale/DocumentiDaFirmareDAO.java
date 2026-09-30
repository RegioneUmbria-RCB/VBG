package it.gruppoinit.pal.gp.core.features.firmadigitale;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
public interface DocumentiDaFirmareDAO extends BaseDAO<DocumentiDaFirmare, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<DocumentiDaFirmare> findAll(Integer firstResult, Integer maxResult);

    public List<DocumentiDaFirmare> findByResponsabile(Integer codiceresponsabile);

    public List<DocumentiDaFirmare> findByIdOggetto(Integer codice);

    public List<DocumentiDaFirmare> findDocumentiDaFirmareDTOPerFirmatario(Integer codiceresponsabile, Integer firstResult, Integer maxResult);

    public List<DocumentiDaFirmare> findDocumentiDaFirmareDTOPerRichiedenteAndStato(Integer codiceresponsabile, Boolean isMessiDallutenteLoggato,
	    String flagDaFirmare, Integer firstResult, Integer maxResult);

    public Integer countDocumentiDaFirmareDTOPerRichiedenteAndStato(Integer codiceresponsabile, Boolean isMessiDallutenteLoggato,
	    String flagDaFirmare);

    public void eliminaDocDaFirmare(List<Integer> documentiDaFirmare);

    public List<Integer> findIdDocumentiDaFirmare(Integer codiceOggetto, Integer codiceFirmatario);
}
