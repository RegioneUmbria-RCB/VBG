package it.gruppoinit.pal.gp.pay.service;

import java.util.List;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.dao.PayPosizioniDebitorieDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.ws.rest.NuoviPagamentiRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiAnnullatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiEffettuatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PosizioneDebitoriaInfoRestResponse;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaListResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaRequestType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistraIUVRequestType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistraIUVResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;

public interface PayPosizioniDebitorieService extends BaseService<PayPosizioniDebitorie, PkId> {

    public PayPosizioniDebitorie findByIdExtended(Integer idPosizioneDebitoria) throws PayException;

    public PosizioneDebitoriaListResponseType findByJsonRequestFilter(PosizioneDebitoriaRequestType richiesta, Integer offset, Integer limit);

    public PayPosizioniDebitorie findByIUV(String iuv);

    public PayPosizioniDebitorie findByIdPosizionePSP(String idPSP);

    public List<PayPosizioniDebitorie> findAllByIdPosizionePSP(String idPSP);

    public PayPosizioniDebitorie findByRiferimentoPosizione(RiferimentoPosizioneDebitoriaType posRef);
    //    public void aggiornaDatiPosizioneDebitoria(PosizioneDebitoriaType posDeb, PayPosizioniDebitorie daAggiornare, PaySoggettiDebitori datiSoggetto,
    //	    PayRegistrazioniContabili regContabile) throws PayException;

    public void aggiornaPosizioneDebitoria(PayPosizioniDebitorie updateValues, StatiPagamento newStatus, String descStato);

    public PayPosizioniDebitorie inserisciDatiPosizioneDebitoria(PosizioneDebitoriaType posDeb, PaySoggettiDebitori datiSoggetto,
	    PayRegistrazioniContabili regCont, boolean otf) throws PayException;

    /**
     * @see PayPosizioniDebitorieDAO#findByCodiceAvviso(String)
     */
    public PayPosizioniDebitorie findByCodiceAvviso(String codiceAvviso);

    //public List<PayPosizioniDebitorie> registraPosizioniDebitorie(List<PosizioneDebitoriaType> poss, PaySoggettiDebitori soggDebitore);
    /**
     * <pre>
     * in ingresso 
     * 		CF ENTE CREDITORE 
     * 		IUV 
     * 		CODICEAVVISO 
     * 		CODICERIFERIMENTO CREDITORE (a seconda del connettore) 
     * Aggiorna le info delle posizioni debitorie trovate da CODICE_RIFERIMENTO_CREDITORE e le segna come ATTIVATE_IN_PSP solo se le
     * informazioni se (IUV/CODICEAVVISO ) non sono presenti e lo stato sia = ad ACQUISITO
     * </pre>
     * 
     * @param richiesta
     * @return
     */
    public RegistraIUVResponseType registraIUV(RegistraIUVRequestType richiesta);

    public java.util.Date dataScadenza(XMLGregorianCalendar dataScadenza);

    public java.util.Date dataFineValidita(XMLGregorianCalendar dataFineValidita);

    public List<PayPosizioniDebitorie> findByIdRegistrazioneContabile(Integer idRegistrazioneContabile);

    public PayPosizioniDebitorie findByUuid(String uuid) throws PayException;

    public List<PosizioneDebitoriaInfoRestResponse> findNuoviPagamentiDeiConnettori(NuoviPagamentiRequest richiesta);

    public List<PosizioneDebitoriaInfoRestResponse> findPagamentiAnnullatiDeiConnettori(PagamentiAnnullatiRestRequest richiesta);

    public List<PosizioneDebitoriaInfoRestResponse> findPagamentiEffettuatiDeiConnettori(PagamentiEffettuatiRestRequest richiesta);

    public PayPosizioniDebitorie findByIdPosizionePSPOrIUVOrCodiceAvviso(String idPSP, String iuv, String codiceAvviso);

    public PayPosizioniDebitorie inserisciDatiPosizioneDebitoria(PosizioneDebitoriaType posDeb, PaySoggettiDebitori datiSoggetto,
	    PayRegistrazioniContabili regCont, boolean otf, String iuv, String codiceavviso, String idpsp) throws PayException;

    /**
     * Torna tutti i riferimentiClient della posizione debitoria:
     * <ul>
     * <li>PAY_POSIZIONE_DEBITORIA.RIFERIMENTO_CLIENT</li>
     * <li>PAY_POSDEB_RIFCLIENT.RIFERIMENTO_CLIENT</li>
     * </ul>
     * 
     * @param idPosizioneDebitoria
     * @return
     */
    public Set<String> findRiferimentiClientByPosizioneDebitoria(Integer idPosizioneDebitoria);
}
