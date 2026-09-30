package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.dao.QuesitiDAO;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Quesiti;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.SessionDetails;
import it.gruppoinit.pal.gp.core.service.FaqService;
import it.gruppoinit.pal.gp.core.service.FaqclassiService;
import it.gruppoinit.pal.gp.core.service.QuesitiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class QuesitiServiceImpl extends BaseServiceImpl<Quesiti, PkId> implements QuesitiService {

    private static final Logger log = LoggerFactory.getLogger(QuesitiServiceImpl.class);
    private QuesitiDAO quesitiDAO;
    private FaqService faqService;
    private SoftwareService softwareService;
    private FaqclassiService faqclassiService;
    private MailServiceWSClient mailServiceWSClient;

    @Autowired
    public void setQuesitiDAO(QuesitiDAO quesitiDAO) {

	this.quesitiDAO = quesitiDAO;
    }

    @Autowired
    public void setFaqService(FaqService faqService) {

	this.faqService = faqService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setFaqclassiService(FaqclassiService faqclassiService) {

	this.faqclassiService = faqclassiService;
    }

    @Autowired
    public void setMailServiceWSClient(MailServiceWSClient mailServiceWSClient) {

	this.mailServiceWSClient = mailServiceWSClient;
    }

    @Override
    protected Class<Quesiti> getEntityClass() {

	return Quesiti.class;
    }

    @Override
    public List<Quesiti> findAll(Integer firstResult, Integer maxResult) {

	return quesitiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Quesiti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    quesitiDAO.insert(entity);
	}
    }

    @Override
    public Quesiti findById(PkId id) {

	return quesitiDAO.findById(id);
    }

    @Override
    public void update(Quesiti entity) {

	if (validateEntity(entity)) {
	    quesitiDAO.update(entity);
	}
    }

    @Override
    public void delete(Quesiti entity) {

	if (isDeleteAllowed(entity)) {
	    quesitiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Faq entity) {

	boolean delete = true;
	return delete;
    }

    @Override
    public List<Quesiti> findByFilter(Set<Software> softwareList) {

	return quesitiDAO.findByFilter(softwareList);
    }

    @Override
    public void insertFaq(Quesiti entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isUpdateAllowed(entity)) {
	    quesitiDAO.update(entity);
	    Quesiti quesito = quesitiDAO.findById(new PkId(entity.getId().getCodice()));
	    if (isInsertFaqAllowed(quesito)) {
		Faq faq = new Faq();
		faq.setDomanda(entity.getQuesito());
		faq.setRisposta(entity.getRisposta());
		faq.setFaqclassi(entity.getFaqclassi());
		faq.setSoftware(entity.getSoftware());
		faq.setData(new Date());
		faq.setPubblicare(true);
		faqService.insert(faq);
	    }
	}
    }

    @Override
    public void update(Quesiti entity, SessionDetails sessionDetails) {

	dataIntegration(entity);
	if (validateEntity(entity) && isUpdateAllowed(entity)) {
	    quesitiDAO.update(entity);
	    if (StringUtils.isNotBlank(entity.getEmail())) {
		sendMessage(entity, sessionDetails);
	    }
	}
    }

    private void sendMessage(Quesiti entity, SessionDetails sessionDetails) {

	try {
	    if (null == sessionDetails) {
		throw new RuntimeException("SessionDetails è obbligatorio");
	    } else {
		if (StringUtils.isBlank(sessionDetails.getToken())) {
		    throw new RuntimeException("SessionDetails.token è vuoto");
		}
	    }
	    //TODO se è presente la verticalizzazione PEC allora la configurazione della mail è relativa 
	    //ad un server PEC per cui forse non devo inviare la mail.(sentire chiocci)
	    MailMessageType message = new MailMessageType();
	    message.setOggetto("RE: " + entity.getQuesito());
	    message.setCorpoMail(entity.getRisposta());
	    message.setDestinatari(entity.getEmail());
	    message.setInviaComeHtml(true);
	    // mailServiceWSClient.sendMail(null, entity.getSoftware().getCodice(), sessionDetails.getToken(), message);
	    mailServiceWSClient.sendMail2(null, entity.getSoftware().getCodice(), null, sessionDetails.getToken(), message);
	} catch (Exception e) {
	    log.error("sendMessage: {}", e.getMessage());
	    throw new RuntimeException(e.getMessage());
	}
    }

    /**
     * Verifica che il campo Risposta non sia nullo
     * 
     */
    protected boolean isUpdateAllowed(Quesiti entity) {

	boolean isAllowed = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getRisposta() == null || entity.getRisposta().equals("")) {
	    _ivs.add(new InvalidValue("service_error.risposta_non_presente", entity.getClass(), "risposta", null, entity));
	    isAllowed = false;
	}
	if (!isAllowed) {
	    this.throwValidationMessages(_ivs);
	}
	return isAllowed;
    }

    /**
     * Verifica che i campi Risposta, Software e Faqclassi non siano nulli
     * 
     */
    protected boolean isInsertFaqAllowed(Quesiti entity) {

	boolean isAllowed = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getRisposta() == null || entity.getRisposta().equals("")) {
	    _ivs.add(new InvalidValue("service_error.risposta_non_presente", entity.getClass(), "risposta", null, entity));
	    isAllowed = false;
	}
	if (entity.getFaqclassi() == null) {
	    _ivs.add(new InvalidValue("service_error.categoria_non_presente", entity.getClass(), "faqclassi", null, entity));
	    isAllowed = false;
	}
	if (entity.getSoftware() == null) {
	    _ivs.add(new InvalidValue("service_error.tipo_non_presente", entity.getClass(), "software", null, entity));
	    isAllowed = false;
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isAllowed;
    }

    private void dataIntegration(Quesiti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Quesiti passato è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getLetto() == null) {
	    entity.setLetto(Boolean.FALSE);
	}
    }

    protected void fixMergeEntityProperties(Quesiti entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Faqclassi faqclassi = faqclassiService.bindDomainObject(entity.getFaqclassi(), PkId.class, "id.codice");
	entity.setFaqclassi(faqclassi);
    }
}
