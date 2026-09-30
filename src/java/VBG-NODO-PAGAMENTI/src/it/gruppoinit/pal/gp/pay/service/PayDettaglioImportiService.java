/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.command.CausaliRaggruppateBean;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaleBean;
import it.gruppoinit.pal.gp.pay.ws.schema.DettaglioImportoType;

/**
 * @author francol
 *
 */
public interface PayDettaglioImportiService extends BaseService<PayDettaglioImporti, PkId> {

    /**
     * cancella tutti i dettagli importo che sono associati alla posizione debitoria passata in input. La posizione
     * debitoria può essere valorizzata anche solo con l'id
     * 
     * @param posDeb
     */
    public void cancellaDettagliImportoPosizioneDebitoria(PayPosizioniDebitorie posDeb);

    /**
     * inserisce nel db i dettagli importo che sono restituiti dalla chiamata di posDeb.getDettagliImporto()
     * 
     * @param posDeb
     */
    public List<PayDettaglioImporti> inserisciDettagliImportoPosizioneDebitoria(PayPosizioniDebitorie posDeb, DettaglioImportoType righeImporto)
	    throws PayException;

    /**
     * Recupera dagli importi la mappa delle causali
     * 
     * @param registrazioniPosizioni
     * @return
     */
    public Map<CausaliRaggruppateBean, List<InfoCausaleBean>> findMappaCausali(List<PayRegistrazioniContabili> registrazioniPosizioni);

    /**
     * Recupera dagli importi la mappa delle causali dedlla posizione debitoria
     * 
     * @param payPos
     * @return
     */
    public Map<CausaliRaggruppateBean, List<InfoCausaleBean>> findMappaCausaliPosizioneDebitoria(PayPosizioniDebitorie payPos);

    public List<PayDettaglioImporti> findByIdPosizioneDebitoria(Integer idPosizioneDebitoria);
}
