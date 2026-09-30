/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.dao.PayPosizioniDebitorieDAO;
import it.gruppoinit.pal.gp.pay.dao.utils.PosizioneDebitoriaFiltrata;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClient;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClientId;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayDettaglioImportiService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosdebRifClientService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.rest.NuoviPagamentiRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiAnnullatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiEffettuatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PosizioneDebitoriaInfoRestResponse;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaListResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaRequestType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistraIUVRequestType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistraIUVResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;

/**
 * @author francol
 *
 */
@Service
public class PayPosizioniDebitorieServiceImpl extends BaseServiceImpl<PayPosizioniDebitorie, PkId> implements PayPosizioniDebitorieService {

    @Autowired
    private PayPosizioniDebitorieDAO payPosizioniDebitorieDAO;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PayDettaglioImportiService payDettaglioImportiService;
    @Autowired
    private PayPagamentiService payPagamentiService;
    @Autowired
    private PayPosdebRifClientService payPosdebRifClientService;
    @Autowired
    private PagoPAService pagoPAService;

    @Override
    public void insert(PayPosizioniDebitorie entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    this.payPosizioniDebitorieDAO.insert(entity);
	}
    }

    @Override
    public void update(PayPosizioniDebitorie entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    this.payPosizioniDebitorieDAO.update(entity);
	}
    }

    @Override
    public void delete(PayPosizioniDebitorie entity) {

	if (isDeleteAllowed(entity)) {
	    this.payPosizioniDebitorieDAO.delete(entity);
	}
    }

    @Override
    public List<PayPosizioniDebitorie> findAll(Integer firstResult, Integer maxResult) {

	return this.payPosizioniDebitorieDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayPosizioniDebitorie findById(PkId id) {

	return this.payPosizioniDebitorieDAO.findById(id);
    }

    @Override
    public PayPosizioniDebitorie findByIdExtended(Integer idPosizioneDebitoria) throws PayException {

	PayPosizioniDebitorie posizione = this.findById(new PkId(idPosizioneDebitoria));
	if (posizione == null) {
	    throw new PayException("Posizione non trovata con codice " + idPosizioneDebitoria);
	}
	posizione.setPagamenti(this.payPagamentiService.findByIdPosizioneDebitoria(idPosizioneDebitoria));
	List<PayStatoPagamenti> elencoStati = this.payStatoPagamentiService.getCronologiaPosizioneDebitoria(idPosizioneDebitoria);
	Set<PayStatoPagamenti> stati = new HashSet<>(elencoStati);
	posizione.setStati(stati);
	posizione.impostaStatoCorrente(elencoStati.get(0));
	return posizione;
    }

    @Override
    protected Class<PayPosizioniDebitorie> getEntityClass() {

	return PayPosizioniDebitorie.class;
    }

    private void dataIntegration(PayPosizioniDebitorie entity, boolean isInsert) {

	if (isInsert && StringUtils.isBlank(entity.getUuid())) {
	    entity.setUuid(UUID.randomUUID().toString());
	}
    }

    @Override
    public PayPosizioniDebitorie findByIUV(String iuv) {

	return this.payPosizioniDebitorieDAO.findByRiferimenti(iuv, null);
    }

    @Override
    public PayPosizioniDebitorie inserisciDatiPosizioneDebitoria(PosizioneDebitoriaType posDeb, PaySoggettiDebitori datiSoggetto,
	    PayRegistrazioniContabili regCont, boolean otf) throws PayException {

	return inserisciDatiPosizioneDebitoria(posDeb, datiSoggetto, regCont, otf, null, null, null);
    }

    @Override
    public PayPosizioniDebitorie inserisciDatiPosizioneDebitoria(PosizioneDebitoriaType posDeb, PaySoggettiDebitori datiSoggetto,
	    PayRegistrazioniContabili regCont, boolean otf, String iuv, String codiceavviso, String idpsp) throws PayException {

	PayPosizioniDebitorie newPosDeb = populateDomainObject(posDeb, null, regCont);
	newPosDeb.setFlagOTF(otf);
	newPosDeb.setSoggettoDebitore(datiSoggetto);
	if (regCont.getId().getCodice() != null) {
	    newPosDeb.setRegistrazioneContabile(regCont);
	}
	if (iuv != null) {
	    newPosDeb.setIuv(iuv);
	}
	if (codiceavviso != null) {
	    newPosDeb.setCodiceAvviso(codiceavviso);
	}
	if (idpsp != null) {
	    newPosDeb.setIdPosizionePsp(idpsp);
	}
	this.insert(newPosDeb);
	if (!posDeb.getRiferimentiClient().isEmpty()) {
	    inserisciRiferimentiClient(newPosDeb, posDeb.getRiferimentiClient());
	}
	List<PayDettaglioImporti> impDett = this.payDettaglioImportiService.inserisciDettagliImportoPosizioneDebitoria(newPosDeb,
		posDeb.getImporto());
	newPosDeb.getDettagliImporto().addAll(impDett);
	return newPosDeb;
    }

    private void inserisciRiferimentiClient(PayPosizioniDebitorie newPosDeb, List<String> riferimentiClient) {

	for (String rifClient : riferimentiClient) {
	    if (StringUtils.isNotBlank(rifClient)) {
		PayPosdebRifClient e = new PayPosdebRifClient();
		PayPosdebRifClientId id = new PayPosdebRifClientId(newPosDeb.getId().getIdcomune(), UUID.randomUUID().toString());
		e.setId(id);
		e.setPosizioneDebitoria(newPosDeb);
		e.setRiferimentoClient(rifClient);
		payPosdebRifClientService.insert(e);
	    }
	}
    }

    private PayPosizioniDebitorie populateDomainObject(PosizioneDebitoriaType posDeb, PayPosizioniDebitorie payPosDeb,
	    PayRegistrazioniContabili regContabile) {

	if (posDeb != null) {
	    if (payPosDeb == null) {
		payPosDeb = new PayPosizioniDebitorie();
	    }
	    Date dataAnno = new Date();
	    payPosDeb.setDataRegistrazione(regContabile.getDataRegistrazione() != null ? regContabile.getDataRegistrazione() : dataAnno);
	    Integer anno = null;
	    if (posDeb.getDataScadenza() != null) {
		Date inputDate = dataScadenza(posDeb.getDataScadenza());
		payPosDeb.setDataScadenza(inputDate);
		dataAnno = inputDate;
	    }
	    if (regContabile.getAnno() != null) {
		anno = regContabile.getAnno();
	    }
	    if (anno == null) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(dataAnno);
		anno = cal.get(Calendar.YEAR);
	    }
	    payPosDeb.setAnno(anno);
	    if (StringUtils.isNotEmpty(posDeb.getDescrizione())) {
		payPosDeb.setDescrizioneCausale(posDeb.getDescrizione());
	    } else if (regContabile != null && StringUtils.isNotEmpty(regContabile.getDescrizione())) {
		payPosDeb.setDescrizioneCausale(regContabile.getDescrizione());
	    }
	    PayProfiliEntiCreditori prof = PayConfigurationHelper.getProfiloEnteCreditore();
	    payPosDeb.setProfiloEnte(prof);
	    if (posDeb.getNumeroRata() != null) {
		payPosDeb.setNumRata(posDeb.getNumeroRata().intValue());
	    }
	}
	return payPosDeb;
    }

    @Override
    public void aggiornaPosizioneDebitoria(PayPosizioniDebitorie updateValues, StatiPagamento newStatus, String descStato) {

	this.update(updateValues);
	if (newStatus != null) {
	    PayStatoPagamenti statoPos = new PayStatoPagamenti();
	    statoPos.setDataEvento(new Date());
	    statoPos.setPosizioneDebitoria(updateValues);
	    if (StringUtils.isBlank(descStato)) {
		descStato = newStatus.description();
	    }
	    statoPos.setStato(newStatus.name());
	    statoPos.setDescStato(descStato);
	    this.payStatoPagamentiService.insert(statoPos);
	}
    }

    @Override
    public PayPosizioniDebitorie findByRiferimentoPosizione(RiferimentoPosizioneDebitoriaType posRef) {

	Integer idPosd = posRef.getIdPosizione() != null ? posRef.getIdPosizione().intValue() : null;
	return this.payPosizioniDebitorieDAO.findByRiferimenti(posRef.getIUV(), idPosd);
    }

    @Override
    public PayPosizioniDebitorie findByIdPosizionePSP(String idPSP) {

	return this.payPosizioniDebitorieDAO.findByIdPosizionePSP(idPSP);
    }

    @Override
    public List<PayPosizioniDebitorie> findAllByIdPosizionePSP(String idPSP) {

	return this.payPosizioniDebitorieDAO.findAllByIdPosizionePSP(idPSP);
    }

    @Override
    public PayPosizioniDebitorie findByCodiceAvviso(String codiceAvviso) {

	return this.payPosizioniDebitorieDAO.findByCodiceAvviso(codiceAvviso);
    }

    @Override
    public PosizioneDebitoriaListResponseType findByJsonRequestFilter(PosizioneDebitoriaRequestType richiesta, Integer offset, Integer limit) {

	List<PosizioneDebitoriaResponseType> retVal = new ArrayList<>();
	int total = this.payPosizioniDebitorieDAO.countByJsonRequestFilter(richiesta);
	if (total > 0) {
	    List<Integer> idPosizioni = this.payPosizioniDebitorieDAO.findByJsonRequestFilter(richiesta, offset, limit);
	    List<PosizioneDebitoriaFiltrata> posizioni = this.payPosizioniDebitorieDAO.findByIdPosizioni(idPosizioni);
	    PosizioneDebitoriaResponseType posizioneDebitoria = null;
	    if (!posizioni.isEmpty()) {
		for (PosizioneDebitoriaFiltrata posizione : posizioni) {
		    if (posizioneDebitoria == null
			    || posizioneDebitoria.getIdPosizioneDebitoria().compareTo(posizione.getIdPosizioneDebitoria()) != 0) {
			retVal.add(new PosizioneDebitoriaResponseType(posizione));
			posizioneDebitoria = retVal.get((retVal.size() - 1));
		    }
		    posizioneDebitoria.aggiorna(posizione);
		}
	    }
	}
	return new PosizioneDebitoriaListResponseType(total, offset, limit, retVal);
    }

    @Override
    public RegistraIUVResponseType registraIUV(RegistraIUVRequestType richiesta) {

	RegistraIUVResponseType response = new RegistraIUVResponseType();
	String messaggioErrore = validateRequestRegistraIUV(richiesta);
	if (StringUtils.isBlank(messaggioErrore)) {
	    PayPosizioniDebitorie payPos = this.findById(new PkId(richiesta.getIdPosizioneDebitoria()));
	    if (payPos == null) {
		messaggioErrore = "Posizione debitoria non trovata con id " + richiesta.getIdPosizioneDebitoria();
	    } else {
		PayStatoPagamenti statoPosizioneDebitoria = payStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
		if (statoPosizioneDebitoria.getStato().equalsIgnoreCase(StatiPagamento.ACQUISITO.name()) && StringUtils.isBlank(payPos.getIuv())) {
		    // aggiorno solo se acquisito e iuv della posizione debitoria non sia settato
		    StatiPagamento newStatus = StatiPagamento.ATTIVATO_IN_PSP;
		    payPos.setIuv(richiesta.getIuv());
		    if (StringUtils.isBlank(payPos.getCodiceAvviso())) {
			payPos.setCodiceAvviso(richiesta.getCodiceAvviso());
			if (StringUtils.isBlank(payPos.getQrCode())) {
			    payPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
			}
		    }
		    this.aggiornaPosizioneDebitoria(payPos, newStatus, StatiPagamento.ATTIVATO_IN_PSP.description());
		}
	    }
	}
	response.setErrore(messaggioErrore);
	return response;
    }

    public static void main(String[] args) {

	System.out.println(StatiPagamento.ACQUISITO.name() + "----" + StatiPagamento.ACQUISITO.description());
    }

    private String validateRequestRegistraIUV(RegistraIUVRequestType richiesta) {

	if (richiesta == null || richiesta.getIdPosizioneDebitoria() == null || StringUtils.isBlank(richiesta.getIuv())) {
	    return "Richiesta non valida " + richiesta.toString();
	}
	return null;
    }

    @Override
    public Date dataScadenza(XMLGregorianCalendar c) {

	java.util.Date dt = null;
	if (c != null) {
	    try {
		Calendar ct = c.toGregorianCalendar();
		if (c.getHour() < 0) {
		    ct.set(Calendar.HOUR, 23);
		}
		if (c.getMinute() < 0) {
		    ct.set(Calendar.MINUTE, 59);
		}
		if (c.getSecond() < 0) {
		    ct.set(Calendar.SECOND, 59);
		}
		dt = ct.getTime();
	    } catch (Exception e) {
		throw new RuntimeException("Non è stato possibile costruire un oggetto Date a causa di:" + e.getMessage(), e);
	    }
	}
	return dt;
    }

    @Override
    public Date dataFineValidita(XMLGregorianCalendar c) {

	java.util.Date dt = null;
	if (c != null) {
	    try {
		Calendar ct = c.toGregorianCalendar();
		ct.set(Calendar.HOUR_OF_DAY, 0);
		ct.set(Calendar.MINUTE, 0);
		ct.set(Calendar.SECOND, 0);
		ct.set(Calendar.MILLISECOND, 0);
		dt = ct.getTime();
	    } catch (Exception e) {
		throw new RuntimeException("Non è stato possibile costruire un oggetto Date a causa di:" + e.getMessage(), e);
	    }
	}
	return dt;
    }

    @Override
    public List<PayPosizioniDebitorie> findByIdRegistrazioneContabile(Integer idRegistrazioneContabile) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("registrazioneContabileId", idRegistrazioneContabile, Integer.class));
	ft.addRestriction(fr);
	return payPosizioniDebitorieDAO.findByFilterTable(ft);
    }

    @Override
    public PayPosizioniDebitorie findByUuid(String uuid) throws PayException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("uuid", uuid, String.class));
	ft.addRestriction(fr);
	List<PayPosizioniDebitorie> ret = payPosizioniDebitorieDAO.findByFilterTable(ft);
	if (ret.size() == 1) {
	    return findByIdExtended(ret.get(0).getId().getCodice());
	}
	return null;
    }

    @Override
    public List<PosizioneDebitoriaInfoRestResponse> findNuoviPagamentiDeiConnettori(NuoviPagamentiRequest richiesta) {

	return payPosizioniDebitorieDAO.findNuoviPagamentiDeiConnettori(richiesta);
    }

    @Override
    public List<PosizioneDebitoriaInfoRestResponse> findPagamentiAnnullatiDeiConnettori(PagamentiAnnullatiRestRequest richiesta) {

	return payPosizioniDebitorieDAO.findPagamentiAnnullatiDeiConnettori(richiesta);
    }

    @Override
    public List<PosizioneDebitoriaInfoRestResponse> findPagamentiEffettuatiDeiConnettori(PagamentiEffettuatiRestRequest richiesta) {

	return payPosizioniDebitorieDAO.findPagamentiEffettuatiDeiConnettori(richiesta);
    }

    @Override
    public PayPosizioniDebitorie findByIdPosizionePSPOrIUVOrCodiceAvviso(String idPSP, String iuv, String codiceAvviso) {

	return this.payPosizioniDebitorieDAO.findByIdPosizionePSPOrIUVOrCodiceAvviso(idPSP, iuv, codiceAvviso);
    }

    @Override
    public Set<String> findRiferimentiClientByPosizioneDebitoria(Integer idPosizioneDebitoria) {

	return this.payPosizioniDebitorieDAO.findRiferimentiClientByPosizioneDebitoria(idPosizioneDebitoria);
    }
}
