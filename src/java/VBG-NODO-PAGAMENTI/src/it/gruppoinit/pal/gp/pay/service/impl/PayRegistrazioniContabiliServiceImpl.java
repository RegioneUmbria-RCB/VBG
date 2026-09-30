/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.dao.PayRegistrazioniContabiliDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniContabiliService;
import it.gruppoinit.pal.gp.pay.service.PaySoggettiDebitoriService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PosizioneDebitoriaTypeComparator;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;

/**
 * @author francol
 *
 */
@Service
public class PayRegistrazioniContabiliServiceImpl extends BaseServiceImpl<PayRegistrazioniContabili, PkId>
	implements PayRegistrazioniContabiliService {

    @Autowired
    private PayRegistrazioniContabiliDAO payRegistrazioniContabiliDAO;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PaySoggettiDebitoriService paySoggettiDebitoriService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;

    @Override
    public void insert(PayRegistrazioniContabili entity) {

	fixMergeEntityProperties(entity);
	if (this.validateEntity(entity)) {
	    this.payRegistrazioniContabiliDAO.insert(entity);
	}
    }

    @Override
    public void update(PayRegistrazioniContabili entity) {

	fixMergeEntityProperties(entity);
	if (this.validateEntity(entity)) {
	    this.payRegistrazioniContabiliDAO.update(entity);
	}
    }

    @Override
    public void delete(PayRegistrazioniContabili entity) {

	if (this.isDeleteAllowed(entity)) {
	    this.payRegistrazioniContabiliDAO.delete(entity);
	}
    }

    @Override
    public List<PayRegistrazioniContabili> findAll(Integer firstResult, Integer maxResult) {

	return this.payRegistrazioniContabiliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayRegistrazioniContabili findById(PkId id) {

	return this.payRegistrazioniContabiliDAO.findById(id);
    }

    
    private PayRegistrazioniContabili creaRegistrazioneContabile(RegistrazioneContabileType regCont, PayConfigurationHelper payCfg, boolean otf,
    		String iuv, String codiceavviso, String idpsp, StatiPagamento stato, Date dataevento)
	    throws PayException {

	PayRegistrazioniContabili payRegCont = populateDomainObject(regCont, payCfg);
	this.insert(payRegCont);
	//gestione dati soggetto debitore
	SoggettoDebitoreType soggDeb = regCont.getSoggettoDebitore();
	PaySoggettiDebitori paySoggDeb = null;
	if (soggDeb != null) {
	    paySoggDeb = this.paySoggettiDebitoriService.registraSoggettoDebitore(soggDeb);
	} else {
	    throw new PayInvalidRequestException("Dati del soggetto debitore mancanti.");
	}
	//inserimento registrazione contabile se è attiva la gestione contabile
	if (payCfg.isContabilitaAttiva()) {
	    this.payRegistrazioniContabiliDAO.insert(payRegCont);
	}
	//inserimento posizioni debitorie
	List<PosizioneDebitoriaType> pos4reg = this.calcolaOrdineRate(regCont.getRate().getRata());
	Set<PayPosizioniDebitorie> payPos4reg = new HashSet<PayPosizioniDebitorie>();
	//List<PayStatoPagamenti> statiPos = new ArrayList<PayStatoPagamenti>();
	for (int i = 0; i < pos4reg.size(); i++) {
	    PosizioneDebitoriaType posDeb = pos4reg.get(i);
	    posDeb.setNumeroRata(BigInteger.valueOf(i + 1));
	    PayPosizioniDebitorie payPosDeb = null;
	    //Andrebbe fatto un check sulla size per vedere se e 1 oppure no, ma per ora lasciamo cosi
	    payPosDeb = this.payPosizioniDebitorieService.inserisciDatiPosizioneDebitoria(posDeb, paySoggDeb, payRegCont, otf, iuv, codiceavviso, idpsp);
	    payPos4reg.add(payPosDeb);
	    PayStatoPagamenti statoPosizione = new PayStatoPagamenti();
	    statoPosizione.setDataEvento(dataevento);
	    //StatiPagamento stato = StatiPagamento.ACQUISITO
	    statoPosizione.setStato(stato.name());
	    statoPosizione.setDescStato(stato.description());
	    statoPosizione.setPosizioneDebitoria(payPosDeb);
	    this.payStatoPagamentiService.insert(statoPosizione);
	    //statiPos.add(statoPosizione);
	    payPosDeb.impostaStatoCorrente(statoPosizione);
	}
	payRegCont.setPosizioniDebitorie(payPos4reg);
	return payRegCont;
    }
    
    @Override
    public PayRegistrazioniContabili creaRegistrazioneContabile(RegistrazioneContabileType regCont, PayConfigurationHelper payCfg, boolean otf)
	    throws PayException {
    	return creaRegistrazioneContabile(regCont, payCfg, otf, null, null, null, StatiPagamento.ACQUISITO, new Date());
    }
    
    @Override
    public PayRegistrazioniContabili creaRegistrazioneContabileSingolaPd(RegistrazioneContabileType regCont, PayConfigurationHelper payCfg, boolean otf,
    		String iuv, String codiceavviso, String idpsp, StatiPagamento stato, Date dataevento)
	    throws PayException {
    	return creaRegistrazioneContabile(regCont, payCfg, otf, iuv, codiceavviso, idpsp, stato, dataevento);
    }

    @Override
    protected Class<PayRegistrazioniContabili> getEntityClass() {

	return PayRegistrazioniContabili.class;
    }

    @Override
    protected void fixMergeEntityProperties(PayRegistrazioniContabili entity) {

    }

    private PayRegistrazioniContabili populateDomainObject(RegistrazioneContabileType regCont, PayConfigurationHelper payCfg) throws PayException {

	PayRegistrazioniContabili payRegCont = null;
	if (regCont != null) {
	    payRegCont = new PayRegistrazioniContabili();
	    Date dataReg = regCont.getData() != null ? Utilities.getDate(regCont.getData()) : new Date();
	    Integer anno = regCont.getAnno();
	    if (anno == null) {
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(dataReg);
		anno = gc.get(Calendar.YEAR);
	    }
	    payRegCont.setAnno(anno);
	    payRegCont.setDataRegistrazione(dataReg);
	    payRegCont.setDescrizione(regCont.getDescrizione());
	    payRegCont.setNote(regCont.getNote());
	    payRegCont.setImporto(regCont.getImporto());
	}
	return payRegCont;
    }

    private List<PosizioneDebitoriaType> calcolaOrdineRate(List<PosizioneDebitoriaType> rate) {

	List<PosizioneDebitoriaType> sorted = new ArrayList<PosizioneDebitoriaType>(rate);
	Collections.sort(sorted, new PosizioneDebitoriaTypeComparator());
	return sorted;
    }

    @Override
    public int countPosizioniByIdRegistrazione(Integer idRegistrazioneContabile) {

	return payRegistrazioniContabiliDAO.countPosizioniByIdRegistrazione(idRegistrazioneContabile);
    }

    @Override
    public List<Integer> findIdPosizioniByIdRegistrazione(Integer idRegistrazioneContabile) {

	return payRegistrazioniContabiliDAO.findIdPosizioniByIdRegistrazione(idRegistrazioneContabile);
    }
}
