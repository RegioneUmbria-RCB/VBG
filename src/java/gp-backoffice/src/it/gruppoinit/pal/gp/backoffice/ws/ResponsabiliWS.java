package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.responsabili.Responsabili;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.responsabili.Anagrafe;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.responsabili.ResponsabiliInsertRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.responsabili.ResponsabiliInsertResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.responsabili.ResponsabiliUpdateRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.responsabili.ResponsabiliUpdateResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.SessionFactoryTargetSource;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.jws.WebService;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@WebService(serviceName = "ResponsabiliService", portName = "ResponsabiliSoap11", targetNamespace = "http://gruppoinit.it/pal/gp/backoffice/definitions/responsabili", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.responsabili.Responsabili")
public class ResponsabiliWS extends BaseWS implements Responsabili {

    private static final Logger log = LoggerFactory.getLogger(ResponsabiliWS.class);
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public ResponsabiliInsertResponse responsabiliInsert(ResponsabiliInsertRequest req) {

	log.info("responsabiliInsert: responsabile={},userid={}", req.getDettaglio().getResponsabile(), req.getDettaglio().getUserid());
	ResponsabiliInsertResponse resp = new ResponsabiliInsertResponse();
	Session session = null;
	Transaction tx = null;
	try {
	    setORMHelper(WebConstants.SOFTWARE_TT, req.getToken());
	    // apro manualmente la transazione e la sessione perchè i service non vedono la sessione di hibernate per qualche motivo. ho provato di tutto interceptor, aop, niente da fare!
	    SessionFactoryTargetSource targetSource = (SessionFactoryTargetSource) applicationContext.getBean("sessionFactoryTarget");
	    SessionFactory sf = targetSource.getSessionFactoryWrapper().getSessionFactory();
	    session = sf.openSession();
	    tx = session.beginTransaction();
	    //
	    it.gruppoinit.pal.gp.core.domain.Responsabili r = getEntity(req.getDettaglio());
	    responsabiliService.insert(r);
	    if (StringUtils.isNotBlank(req.getDettaglio().getUseridCopiaPermessi())) {
		it.gruppoinit.pal.gp.core.domain.Responsabili master = responsabiliService.findByUserId(req.getDettaglio().getUseridCopiaPermessi());
		if (master != null) {
		    it.gruppoinit.pal.gp.core.domain.Responsabili master1 = (it.gruppoinit.pal.gp.core.domain.Responsabili) session.merge(master);
		    Set<it.gruppoinit.pal.gp.core.domain.Responsabili> slaveList = new HashSet<it.gruppoinit.pal.gp.core.domain.Responsabili>();
		    slaveList.add(r);
		    try {
			responsabiliService.saveReplicapermessi(slaveList, master1);
		    } catch (Exception e) {
			log.error("errore durante la copia dei permessi. useridCopiaPermessi={}", req.getDettaglio().getUseridCopiaPermessi(), e);
		    }
		} else {
		    log.error("Il responsabile specificato per la copia dei permessi non esiste. Permessi non copiati. userid={}", req.getDettaglio()
			    .getUseridCopiaPermessi());
		}
	    }
	    if (BooleanUtils.isTrue(req.getDettaglio().isAmministratore())) {
		//se amministratore gli aggiungo il menu dei responsabili (se non presente)
		responsabiliService.insertPermesso(r, 189, WebConstants.SOFTWARE_TT);
		responsabiliService.insertPermesso(r, 190, WebConstants.SOFTWARE_TT);
		responsabiliService.insertPermesso(r, 2, WebConstants.SOFTWARE_TT);
	    }
	    tx.commit();
	    resp.setId(BigInteger.valueOf(r.getId().getCodice().intValue()));
	} catch (Exception e) {
	    log.error("responsabiliInsert: responsabile={}, userid={}, err={}", new Object[] { req.getDettaglio().getResponsabile(),
		    req.getDettaglio().getUserid(), e });
	    if (tx != null) {
		tx.rollback();
	    }
	    throw new RuntimeException(e.getMessage());
	} finally {
	    resetThreadLocalVars();
	    if (session != null) {
		session.close();
	    }
	}
	log.info("responsabiliInsert: return id={}", resp.getId());
	return resp;
    }

    @Override
    public ResponsabiliUpdateResponse responsabiliUpdate(ResponsabiliUpdateRequest req) {

	log.info("responsabiliUpdate: responsabile={},userid={}", req.getDettaglio().getResponsabile(), req.getDettaglio().getUserid());
	ResponsabiliUpdateResponse resp = new ResponsabiliUpdateResponse();
	Session session = null;
	Transaction tx = null;
	try {
	    setORMHelper(WebConstants.SOFTWARE_TT, req.getToken());
	    //
	    SessionFactoryTargetSource targetSource = (SessionFactoryTargetSource) applicationContext.getBean("sessionFactoryTarget");
	    SessionFactory sf = targetSource.getSessionFactoryWrapper().getSessionFactory();
	    session = sf.openSession();
	    tx = session.beginTransaction();
	    //
	    it.gruppoinit.pal.gp.core.domain.Responsabili r = responsabiliService.findByUserId(req.getDettaglio().getUserid());
	    if (r == null) {
		throw new Exception("Il Responsabile specificato per l'aggiornamento non esiste. userid=" + req.getDettaglio().getUserid());
	    }
	    it.gruppoinit.pal.gp.core.domain.Responsabili r1 = (it.gruppoinit.pal.gp.core.domain.Responsabili) session.merge(r);
	    if (BooleanUtils.isTrue(req.getDettaglio().isAmministratore())) {
		r1.setAmministratore("1");
	    } else {
		r1.setAmministratore("0");
	    }
	    responsabiliService.update(r1);
	    if (BooleanUtils.isTrue(req.getDettaglio().isAmministratore())) {
		//se amministratore gli aggiungo il menu dei responsabili (se non presente)
		responsabiliService.insertPermesso(r1, 189, WebConstants.SOFTWARE_TT);
		responsabiliService.insertPermesso(r1, 190, WebConstants.SOFTWARE_TT);
		responsabiliService.insertPermesso(r1, 2, WebConstants.SOFTWARE_TT);
	    } else {
		//se non è amministratore gli elimino il menu dei responsabili (se presente)
		responsabiliService.deletePermesso(r1, 2, WebConstants.SOFTWARE_TT);
	    }
	    resp.setId(BigInteger.valueOf(r1.getId().getCodice().intValue()));
	    tx.commit();
	} catch (Exception e) {
	    log.error("responsabiliUpdate: responsabile={}, userid={}, err={}", new Object[] { req.getDettaglio().getResponsabile(),
		    req.getDettaglio().getUserid(), e });
	    if (tx != null) {
		tx.rollback();
	    }
	    throw new RuntimeException("Errore durante l'aggiornamento del responsabile");
	} finally {
	    resetThreadLocalVars();
	    if (session != null) {
		session.close();
	    }
	}
	log.info("responsabiliUpdate: return id={}", resp.getId());
	return resp;
    }

    private it.gruppoinit.pal.gp.core.domain.Responsabili getEntity(
	    it.gruppoinit.pal.gp.backoffice.schemas.messages.responsabili.Responsabili respType) {

	it.gruppoinit.pal.gp.core.domain.Responsabili r = new it.gruppoinit.pal.gp.core.domain.Responsabili();
	r.setResponsabile(respType.getResponsabile());
	r.setUserid(respType.getUserid());
	if (responsabiliService.checkPasswordPolicy(respType.getPassword())) {
	    r.setPasswordClear(respType.getPassword());
	} else {
	    r.setPasswordClear(Utilities.generaPassword(8) + "1");
	}
	r.setAmministratore("0");
	r.setAmministratoresoftware("0");
	if (BooleanUtils.isTrue(respType.isAmministratore())) {
	    r.setAmministratore("1");
	} else {
	    if (BooleanUtils.isTrue(respType.isAmministratoresoftware())) {
		r.setAmministratoresoftware("1");
	    }
	}
	if (respType.getComuniAssociati() != null) {
	    for (String codiceComune : respType.getComuniAssociati().getCodiceComune()) {
		Responsabilicomuni responsabilicomuni = new Responsabilicomuni();
		Comuni comune = new Comuni(codiceComune);
		responsabilicomuni.setComune(comune);
		r.getResponsabilicomunis().add(responsabilicomuni);
	    }
	}
	r.setDisabilitato(BooleanUtils.isTrue(respType.isDisabilitato()));
	r.setReadonly(BooleanUtils.isTrue(respType.isReadonly()));
	if (respType.getSoftwareAbilitati() != null) {
	    for (String codiceSoftware : respType.getSoftwareAbilitati().getSoftware()) {
		Responsabilisoftware responsabilisoftware = new Responsabilisoftware();
		responsabilisoftware.getSoftware().setCodice(codiceSoftware);
		r.getSoftwareAbilitati().add(responsabilisoftware);
	    }
	}
	Anagrafe anagrafeResponsabile = respType.getAnagrafe();
	if (anagrafeResponsabile != null) {
	    r.setCap(anagrafeResponsabile.getCap());
	    r.setCitta(anagrafeResponsabile.getCitta());
	    r.setCodicefiscale(anagrafeResponsabile.getCodicefiscale());
	    if (anagrafeResponsabile.getDatanascita() != null) {
		r.setDatanascita(Utilities.getDate(anagrafeResponsabile.getDatanascita()));
	    }
	    r.setEmail(anagrafeResponsabile.getEmail());
	    r.setFax(anagrafeResponsabile.getFax());
	    r.setIndirizzo(anagrafeResponsabile.getIndirizzo());
	    r.setMatricola(anagrafeResponsabile.getMatricola());
	    r.setProvincia(anagrafeResponsabile.getProvincia());
	    r.setQualifica(anagrafeResponsabile.getQualifica());
	    r.setTelefonoabitazione(anagrafeResponsabile.getTelefonoabitazione());
	    r.setTelefonocellulare(anagrafeResponsabile.getTelefonocellulare());
	    r.setTelefonolavoro(anagrafeResponsabile.getTelefonolavoro());
	    r.setTitolo(anagrafeResponsabile.getTitolo());
	}
	return r;
    }
}
