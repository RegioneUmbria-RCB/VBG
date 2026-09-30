package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoArjDomandeOneriDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface FoArjDomandeOneriService extends BaseService<FoArjDomandeOneri, PkId> {

    /**
     * @see FoArjDomandeOneriDAO#findAll(Integer, Integer)
     */
    public List<FoArjDomandeOneri> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna i record della tabella filtrati per FO_ARJ_DOMANDE.ID ordinati per
     * INVENTARIOPROCEDIMENTI.ORDINE,INVENTARIOPROCEDIMENTI.PROCEDIMENTO,
     * TIPICAUSALIONERI.CO_ORDINAMENTO,TIPICAUSALIONERI.CO_DESCRIZIONE, FO_ARJ_DOMANDE_ONERI.ID
     * 
     * @param codiceDomanda
     * @return
     */
    public List<FoArjDomandeOneri> findByIdDomanda(Integer codiceDomanda);

    /**
     * sono gli oneri con la colonna codiceinventario==null ordinati per
     * INVENTARIOPROCEDIMENTI.ORDINE,INVENTARIOPROCEDIMENTI.PROCEDIMENTO,
     * TIPICAUSALIONERI.CO_ORDINAMENTO,TIPICAUSALIONERI.CO_DESCRIZIONE, FO_ARJ_DOMANDE_ONERI.ID
     * 
     * @param codiceDomanda
     * @return
     */
    public List<FoArjDomandeOneri> findOneriInterventoByIdDomanda(Integer codiceDomanda);

    /**
     * Torna i record della tabella filtrati per FO_ARJ_DOMANDE.ID ordinati per
     * INVENTARIOPROCEDIMENTI.ORDINE,INVENTARIOPROCEDIMENTI.PROCEDIMENTO,
     * TIPICAUSALIONERI.CO_ORDINAMENTO,TIPICAUSALIONERI.CO_DESCRIZIONE, FO_ARJ_DOMANDE_ONERI.ID Le condizioni perchè si
     * consideri pagato è che FLAG_PAGATO=1 e sia presente il codiceoggetto pdf della ricevuta
     * 
     * @param codiceDomanda
     * @return
     */
    public List<FoArjDomandeOneri> findPagatiByIdDomanda(Integer codiceDomanda);

    /**
     * Cancella tutte le righe di oneri associati ad una domanda
     * 
     * @param codiceDomanda
     */
    public void deleteByDomanda(Integer codiceDomanda);

    public void insertOneriPerIntervento(Integer codiceDomanda, Integer codiceIntervento);

    public List<FoArjDomandeOneri> findByIdDomandaAndCodiceInventario(Integer codiceDomanda, Integer codiceInventario);

    public List<FoArjDomandeOneri> findPagatiByIdDomandaAndCodiceInventario(Integer codiceDomanda, Integer codiceInventario);

    public void insertOneriPerCodiceInventario(Integer codiceDomanda, Integer codiceProcedimento);

    public void deleteByDomandaAndCodiceInventario(Integer codiceDomanda, Integer codiceProcedimento);

    public void updatePagamentiOnline(FoArjDomande domanda, String numeroOperazione, String idTransazione, Oggetti oggettoXml, Oggetti oggettoPdf);

    public void updatePagamentiOnlineSetPagato(FoArjDomande domanda, String numeroOperazione, String idTransazione);

    public void updatePagamentiOnlineSetRicevute(FoArjDomande domanda, String numeroOperazione, Oggetti oxml, Oggetti oggettiPdf);
}
