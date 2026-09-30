/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.pay.dao.PayRichiesteDAO;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDocumenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;
import it.gruppoinit.pal.gp.pay.service.PayDocumentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRichiesteService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayRichiesteDataRichiestaComparator;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiFatturaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

/**
 * @author francol
 *
 */
@Service
public class PayRichiesteServiceImpl extends BaseServiceImpl<PayRichieste, PkId> implements PayRichiesteService {

    private static final Logger log = LoggerFactory.getLogger(PayRichiesteServiceImpl.class);
    private PayRichiesteDAO payRichiesteDAO;
    private PayConnectorService payConnectorService;
    private PayDocumentiService payDocumentiService;
    private PayPosizioniDebitorieService payPosizioniDebitorieService;

    @Autowired
    public void setPayRichiesteDAO(PayRichiesteDAO payRichiesteDAO) {

	this.payRichiesteDAO = payRichiesteDAO;
    }

    @Autowired
    public void setPayConnectorService(PayConnectorService payConnectorService) {

	this.payConnectorService = payConnectorService;
    }

    @Autowired
    public void setPayDocumentiService(PayDocumentiService payDocumentiService) {

	this.payDocumentiService = payDocumentiService;
    }

    @Autowired
    public void setPayPosizioniDebitorieService(PayPosizioniDebitorieService payPosizioniDebitorieService) {

	this.payPosizioniDebitorieService = payPosizioniDebitorieService;
    }

    @Override
    public void insert(PayRichieste entity) {

	if (this.validateEntity(entity)) {
	    this.payRichiesteDAO.insert(entity);
	}
    }

    @Override
    public void update(PayRichieste entity) {

	if (this.validateEntity(entity)) {
	    this.payRichiesteDAO.update(entity);
	}
    }

    @Override
    public void delete(PayRichieste entity) {

	if (this.isDeleteAllowed(entity)) {
	    this.payRichiesteDAO.delete(entity);
	}
    }

    @Override
    public List<PayRichieste> findAll(Integer firstResult, Integer maxResult) {

	return this.payRichiesteDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayRichieste findById(PkId id) {

	return this.payRichiesteDAO.findById(id);
    }

    @Override
    protected Class<PayRichieste> getEntityClass() {

	return PayRichieste.class;
    }

    @Override
    public PayRichieste registraRichiestaPerPosizioneDebitoria(PayPosizioniDebitorie payPos, TipiEvento tipoRichiesta) throws PayException {

	PayRichieste rich = null;
	if (payPos != null) {
	    PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	    if (profiloEnte == null) {
		throw new PayException("impossibile determinare il profilo ente da associare alla richiesta");
	    }
	    //il tipo richieste viene codificato con lo stesso valore enumeration che descrive la chiamata in uscita 
	    //verso i servizi del PSP che il nodo dovrà effettuare per soddisfare la richiesta che ha ricevuto. X es. se riceve una richiesta ANNULLA_POSIZIONI o  viene registrato il tipo richiesta ANNULLA_POSIZIONI_PSP
	    if (tipoRichiesta == null) {
		throw new PayException("specificare il tipo di richiesta da registrare");
	    }
	    TipiEvento tipoRichOut = null;
	    switch (tipoRichiesta) {
	    case ANNULLA_POSIZIONI:
	    case ANNULLA_POSIZIONI_PSP:
	    case REGISTRA_PAGAMENTI_OFFLINE:
	    case ANNULLA_PAGAMENTI_OFFLINE_PSP:
		tipoRichOut = TipiEvento.ANNULLA_POSIZIONI_PSP;
		break;
	    case REGISTRA_POSIZIONI:
	    case INVIA_POSIZIONI_A_PSP:
	    case ATTIVA_PAGAMENTO_OTF:
	    case ATTIVA_PAGAMENTO_OTF_PSP:
		if (BooleanUtils.isNotTrue(payPos.getFlagOTF())) {
		    //schedulare il caricamento di posizioni OTF non ha senso a meno di non prevedere chiamate al caricamento posizioni ogni pochi secondi
		    //anche ritentare il caricamento in caso di servizio non accessibile non ha senso perché in questi casi sarà il client a ritentare il caricamento
		    //in quanto un esito negativo (anche se ripristinabile) impedisce comunque il flusso dell'applicazione client che prevede il pagamento immediato per poter procedere. 
		    //le richieste di caricamento posizioni OTF vengono shcedulate solo nel caso in cui l'OTF non sia supportato 
		    //I due casi OTF e non OTF avrebbero bisogno di due scheduling distinti (uno ogni pochi secondi, l'altro su tempi più lunghi) 
		    //cosa che si potrà ottenere aggiungendo una colonna WS_CARICAMENTO_OTF in PAY_CONNECTOR_CONFIG
		    tipoRichOut = TipiEvento.INVIA_POSIZIONI_A_PSP;
		}
		break;
	    case VERIFICA_STATO_PAGAMENTO:
	    case VERIFICA_STATO_PAGAMENTO_PSP:
		tipoRichOut = TipiEvento.VERIFICA_STATO_PAGAMENTO_PSP;
		break;
	    case RENDICONTAZIONE_PAGAMENTO_PSP:
		tipoRichOut = TipiEvento.RENDICONTAZIONE_PAGAMENTO_PSP;
		break;
	    case INVIA_AVVISO:
	    case GENERA_AVVISO_PSP:
		tipoRichOut = TipiEvento.GENERA_AVVISO_PSP;
		break;
	    case RECUPERA_AVVISO_PSP:
		tipoRichOut = TipiEvento.RECUPERA_AVVISO_PSP;
		break;
	    case GENERA_FATTURA:
	    case GENERA_FATTURA_PSP:
		tipoRichOut = TipiEvento.GENERA_FATTURA_PSP;
		break;
	    case RECUPERA_FATTURA_PSP:
		tipoRichOut = TipiEvento.RECUPERA_FATTURA_PSP;
		break;
	    case RECUPERA_RICEVUTA:
	    case RECUPERA_RICEVUTA_PSP:
		tipoRichOut = TipiEvento.RECUPERA_RICEVUTA_PSP;
		break;
	    case NOTIFICA_CAMBIO_STATO:
		tipoRichOut = TipiEvento.NOTIFICA_CAMBIO_STATO;
		break;
	    default:
		//se per il tipo di richiesta in input non ha senso prevedere la schedulazione o la possibilità di ritentare automaticamente la chiamata in un secondo momento 
		//la richiesta non viene generata per evitare di sovraccaricare inutilmente il DB con dati che poi il nodo non usa.
		break;
	    }
	    if (tipoRichOut != null) {
		rich = this.getRichiestaApertaPerTipoEPosizione(payPos, tipoRichOut);
		if (rich == null) {
		    rich = new PayRichieste();
		    rich.setDataRichiesta(new Date());
		    rich.setProfiloEnteCreditore(profiloEnte);
		    rich.setTipoRichiesta(tipoRichOut.name());
		    rich.setNumeroChiamate(0);
		    rich.setPosizioneDebitoria(payPos);
		    this.payRichiesteDAO.insert(rich);
		    if (log.isInfoEnabled()) {
			log.info("registraRichiestaPerPosizioneDebitoria - creazione richiesta di tipo {} per la posizione {}", tipoRichOut.name(),
				payPos.getId());
		    }
		}
		payPos.impostaRichiestaCorrente(rich);
	    } else {
		log.warn("creaRichiestaPerPosizioneDebitoria - gestione delle richieste non supportata per il tipo evento: {}", tipoRichiesta.name());
	    }
	} else
	    throw new PayException("specificare la posizione per cui si registra la richiesta");
	return rich;
    }

    @Override
    public void aggiornaEsitoRichiesta(PayRichieste payRich, boolean conclusa) {

	if (payRich != null && payRich.getId() != null && payRich.getId().getCodice() != null) {
	    Integer nCall = payRich.getNumeroChiamate() != null ? payRich.getNumeroChiamate() : 1;
	    nCall = nCall + 1;
	    payRich.setNumeroChiamate(nCall);
	    if (conclusa) {
		payRich.setDataCompletamento(new Date());
	    }
	    this.payRichiesteDAO.update(payRich);
	}
    }

    @Override
    public void aggiornaEsitoRichiestaTrans(PayRichieste payRich, boolean conclusa) {

	this.aggiornaEsitoRichiesta(payRich, conclusa);
    }

    @Override
    public void annullaRichiestaTrans(PayRichieste payRich) {

	if (payRich != null && payRich.getId() != null && payRich.getId().getCodice() != null) {
	    payRich.setDataCompletamento(new Date());
	    this.payRichiesteDAO.update(payRich);
	}
    }

    @Override
    public PayRichieste registraRichiestaPerPosizioneDebitoriaTrans(PayPosizioniDebitorie payPos, TipiEvento tipoRichiesta) throws PayException {

	return this.registraRichiestaPerPosizioneDebitoria(payPos, tipoRichiesta);
    }

    @Override
    public PayRichieste getRichiestaApertaPerTipoEPosizione(PayPosizioniDebitorie posDeb, TipiEvento tipoRichiesta) {

	return this.getRichiestaPerTipoEPosizione(posDeb, tipoRichiesta, Boolean.TRUE);
    }

    @Override
    public PayRichieste getRichiestaChiusaPerTipoEPosizione(PayPosizioniDebitorie posDeb, TipiEvento tipoRichiesta) {

	return this.getRichiestaPerTipoEPosizione(posDeb, tipoRichiesta, Boolean.FALSE);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public PayRichieste getRichiestaPerTipoEPosizione(PayPosizioniDebitorie posDeb, TipiEvento tipoRichiesta, Boolean soloApertaChiusa) {

	if (posDeb != null && posDeb.getId() != null && posDeb.getId().getCodice() != null) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterField ff = FilterUtils.equals("id.codice", posDeb.getId().getCodice(), "posizioneDebitoria", PayPosizioniDebitorie.class);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(ff);
	    if (soloApertaChiusa != null) {
		if (soloApertaChiusa.booleanValue()) {
		    ff = FilterUtils.isNull("dataCompletamento");
		} else {
		    ff = FilterUtils.isNotNull("dataCompletamento");
		}
		fr.addFilterField(ff);
	    }
	    if (tipoRichiesta != null) {
		ff = FilterUtils.equals("tipoRichiesta", tipoRichiesta.name(), String.class);
		fr.addFilterField(ff);
	    }
	    ft.addRestriction(fr);
	    // ft.addOrder(FilterUtils.order("dataRichiesta", OrderTypeEnum.DESC)); // la richiesta con l'ordinamento degrada le prestazioni
	    List<PayRichieste> reqs = this.payRichiesteDAO.findByFilterTable(ft);
	    if (!reqs.isEmpty()) {
		// la richiesta con l'ordinamento degrada le prestazioni utilizzo l'ordinamento
		PayRichieste[] reqsArr = new PayRichieste[reqs.size()];
		reqsArr = reqs.toArray(reqsArr);
		Arrays.sort(reqsArr, new PayRichiesteDataRichiestaComparator(OrderTypeEnum.DESC));
		return reqsArr[0];
	    }
	}
	return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<PayRichieste> getRichiesteApertePerTipoEProfiloEnte(PayProfiliEntiCreditori ente, TipiEvento tipoRichiesta) {

	List<PayRichieste> reqs = new ArrayList<>();
	if (ente != null && ente.getId() != null && ente.getId().getCodice() != null) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterField ff = FilterUtils.equals("id.codice", ente.getId().getCodice(), "profiloEnteCreditore", PayProfiliEntiCreditori.class);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(ff);
	    ff = FilterUtils.isNull("dataCompletamento");
	    fr.addFilterField(ff);
	    if (tipoRichiesta != null) {
		ff = FilterUtils.equals("tipoRichiesta", tipoRichiesta.name(), String.class);
		fr.addFilterField(ff);
		PayConnectorWsEndpoint serviceCfg = this.payConnectorService.getEndpointPerTipoServizio(tipoRichiesta);
		if (serviceCfg != null && serviceCfg.getMaxChiamate() != null && serviceCfg.getMaxChiamate() > 0) {
		    ff = FilterUtils.smaller("numeroChiamate", serviceCfg.getMaxChiamate(), Integer.class);
		    fr.addFilterField(ff);
		}
	    }
	    ft.addRestriction(fr);
	    ft.addOrder(FilterUtils.order("dataRichiesta", OrderTypeEnum.ASC));
	    reqs = this.payRichiesteDAO.findByFilterTable(ft);
	}
	return reqs;
    }

    @Override
    public PayRichieste registraRichiestaNotificaCambioStato(PayPosizioniDebitorie payPos) throws PayException {

	if (payPos == null) {
	    throw new PayException("impossibile registrare la richiesta di notifica cambio stato: riferimento alla posizione debitoria manacante");
	}
	return this.registraRichiestaPerPosizioneDebitoria(payPos, TipiEvento.NOTIFICA_CAMBIO_STATO);
    }

    @Override
    public PayRichieste registraRichiestaGenerazioneFatturaTrans(PayPosizioniDebitorie payPos, DatiFatturaType datiFattura, TipiEvento tipoRichiesta)
	    throws PayException {

	if (payPos == null) {
	    throw new PayException("impossibile registrare la richiesta di generazione fattura: riferimento alla posizione debitoria manacante");
	} else if (payPos.getFattura() != null && payPos.getFattura().getBytes().length > 0) {
	    throw new PayException("la fattura o la richiesta di generazione fattura sono già presenti per la posizione debitoria");
	}
	if (datiFattura == null) {
	    throw new PayException("impossibile registrare la richiesta di generazione fattura: dati della fattura mancanti");
	}
	//recupero eventuali dati mancanti nella richiesta della fattura dalle informazioni associate alla posizione debitoria
	this.payDocumentiService.populateDatiFattura(datiFattura);
	PayRichieste retRich = this.registraRichiestaPerPosizioneDebitoria(payPos, tipoRichiesta);
	//serializzazione in XML dei dati della fattura
	String richiestaXml = IOUtils.marshallObject(datiFattura);
	StringBuilder sb = new StringBuilder("RICHIESTA_FATTURA_").append(PkId.toStringId(payPos.getId())).append(".xml");
	PayDocumenti docFatturaReq = this.payDocumentiService.creaDocumentoPerPosizioneDebitoria(payPos, TipoDocumentoType.FATTURA, sb.toString());
	docFatturaReq.setBytes(richiestaXml.getBytes());
	this.payDocumentiService.update(docFatturaReq);
	return retRich;
    }

    @Override
    public void registraEsitoRichiestaDocumentoTrans(EsitoDocumentoPosizioneDebitoriaType esitoDoc, PayPosizioniDebitorie posDeb)
	    throws PayException {

	PayRichieste payRich = posDeb.recuperaRichiestaCorrente();
	if (payRich != null && payRich.getId() != null && payRich.getId().getCodice() != null) {
	    this.aggiornaEsitoRichiesta(payRich, esitoDoc.isEsito() || !esitoDoc.isErroreTemporaneo());
	    if (esitoDoc.isEsito()) {
		/*
		 * se l'esito è positivo imposto comunque le date di generazione fattura e invio dell'avviso anche se il
		 * documento non è restituito in maniera sincrona o non è disponibile affatto questo perché i servizi di
		 * generazione fattura e invio avviso possono servire solo per integrarsi con gestionali di contabilità
		 * o sistemi di postalizzazione degli avvisi che di fatto potrebbero anche non restituire mai i
		 * documenti della fattura o dell'avviso. il discorso è diverso per il recupero della ricevuta
		 * telematica perché in quel caso i servizi del PSP sono sempre progettati per rendere disponibile al
		 * sistema informativo del creditore la ricevuta di pagamento come documento digitale.
		 * 
		 */
		if (esitoDoc.getTipoDocumento().equals(TipoDocumentoType.AVVISO)) {
		    posDeb.setDataInvioAvviso(new Date());
		} else if (esitoDoc.getTipoDocumento().equals(TipoDocumentoType.FATTURA)) {
		    posDeb.setDataGenerazioneFattura(new Date());
		}
		//se il documento è effettivamente restituito dal connettore lo scrivo in PAY_DOCUMENTI e ne imposto il riferimento nella posizione debitoria
		if (esitoDoc.getStatoDocumento() == null) {
		    esitoDoc.setStatoDocumento(esitoDoc.getDocumento() == null ? StatoDocumentoType.NON_DISPONIBILE : StatoDocumentoType.DISPONIBILE);
		}
		if (esitoDoc.getStatoDocumento().equals(StatoDocumentoType.DISPONIBILE)) {
		    DataHandler dhDoc = esitoDoc.getDocumento();
		    if (dhDoc != null) {
			//il record di PAY_DOCUMENTI associato alla posizione debitoria potrebbe anche già esistere perchè è stato usato 
			//per salvare la request xml da inviare al connettore nel caso in cui il servizio sia schedulato
			PayDocumenti docPos = null;
			switch (esitoDoc.getTipoDocumento()) {
			case FATTURA:
			    docPos = posDeb.getFattura();
			    break;
			case AVVISO:
			    docPos = posDeb.getAvviso();
			    break;
			case RICEVUTA:
			    docPos = posDeb.getRicevuta();
			    break;
			default:
			    break;
			}
			if (docPos == null || docPos.getId() == null || docPos.getId().getCodice() == null) {
			    docPos = this.payDocumentiService.creaDocumentoPerPosizioneDebitoria(posDeb, esitoDoc.getTipoDocumento(),
				    esitoDoc.getNomeDocumento());
			} else {
			    docPos.setNomeDocumento(esitoDoc.getNomeDocumento());
			}
			docPos.setDataHandler(dhDoc);
			this.payDocumentiService.update(docPos);
		    } else {
			throw new PayException(
				"risposta del connettore non coerente: stato del documento DISPONIBILE ma i dati del documentop non sono presenti");
		    }
		} else {
		    //per le fatture e gli avvisi se il documento non è disponibile o se è richiesto ma sarà recuperato tramite altri servizi 
		    //imposto null l'FK al documento in PAY_OSIZIONI_DEBITORIE
		    //per evitare che la richiesta xml venga servita al client come se fosse il documento richiesto
		    /*
		     * se nella posizione sono salvati i riferimenti alla richiesta della fattura o dell'avviso li
		     * elimino perché altrimenti il nodo li serve nelle chiamate successive come se fossero la fattura o
		     * l'avviso richiesti che invece non sono ancora (o non saranno mai) presenti nel nodo.
		     */
		    TipiEvento newRequestEvt = null;
		    switch (esitoDoc.getTipoDocumento()) {
		    case FATTURA:
			if (posDeb.getFattura() != null && posDeb.getFattura().getId() != null && posDeb.getFattura().getId().getCodice() != null) {
			    this.payDocumentiService.delete(posDeb.getFattura());
			    posDeb.setFattura(null);
			}
			newRequestEvt = TipiEvento.RECUPERA_FATTURA_PSP;
			break;
		    case AVVISO:
			if (posDeb.getAvviso() != null && posDeb.getAvviso().getId() != null && posDeb.getAvviso().getId().getCodice() != null) {
			    this.payDocumentiService.delete(posDeb.getAvviso());
			    posDeb.setAvviso(null);
			}
			newRequestEvt = TipiEvento.RECUPERA_AVVISO_PSP;
			break;
		    default:
			break;
		    }
		    /*
		     * se si tratta di fattura o avviso e lo stato documento restituito dal connettore è RICHIESTO
		     * memorizzo una richiesta in PAY_RICHIESTE per indicare al nodo che dovrà recuperare il documento
		     * tramite l'invocazione di altri servizi (in realtà se tali servizi non sono configurati e
		     * schedulati il nodo non farà più nulla). La richiesta memorizzata serve anche al nodo per
		     * distinguere lo stato del documento che dovrà restituire al client nel caso in cui incontri la
		     * data generazione fattura o la data invio avviso già impostate ma non trovi il documento già
		     * associato alla posizione debitoria. Se sarà presente la richiesta in PAY_RICHIESTE lo stato
		     * documento che viene restituito sarà RICHIESTO se la richiesta non è presente sarà restituito lo
		     * stato NON_DISPONIBILE per informare in modo corretto il client sulla possibilità o meno di
		     * recuperare il documento ripetendo altre volte la stessa chiamata.
		     */
		    if (esitoDoc.getStatoDocumento().equals(StatoDocumentoType.RICHIESTO) && newRequestEvt != null) {
			this.registraRichiestaPerPosizioneDebitoria(posDeb, newRequestEvt);
		    }
		}
		this.payPosizioniDebitorieService.update(posDeb);
	    }
	} else {
	    log.warn(
		    "registraEsitoRichiestaDocumentoTrans - impossibile aggiornare l'esito della richiesta perché manca il riferimento alla richiesta nella posizione debitoria");
	}
    }
}
