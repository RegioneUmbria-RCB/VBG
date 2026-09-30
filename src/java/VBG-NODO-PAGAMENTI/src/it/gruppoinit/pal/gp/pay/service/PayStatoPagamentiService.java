/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;

/**
 * @author francol
 *
 */
public interface PayStatoPagamentiService extends BaseService<PayStatoPagamenti, PkId> {

    public List<PayStatoPagamenti> getCronologiaPosizioneDebitoria(Integer posDebId);

    public PayStatoPagamenti getStatoPosizioneDebitoria(PayPosizioniDebitorie posDeb);

    public PayStatoPagamenti registraStatoPosizioneDebitoria(EsitoOperazionePosizioneDebitoriaType statoPosDeb, PayPosizioniDebitorie posDeb)
	    throws PayException;

    public void salvaStatoNativoSuStatoCorrente(PayPosizioniDebitorie payPos, String statoPagamentoNativo);

    public StatiPagamento findStatoByPosizioneDebitoria(PayPosizioniDebitorie posizioneDebitoria);
}
