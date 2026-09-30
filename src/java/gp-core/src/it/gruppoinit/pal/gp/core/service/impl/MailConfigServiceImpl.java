package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MailConfigDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.MailConfigComuniService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.CryptoUtils;

/**
 * 
 * @author
 */
@Service
public class MailConfigServiceImpl extends BaseServiceImpl<MailConfig, PkId> implements MailConfigService {

    private static final Logger log = LoggerFactory.getLogger(MailConfigServiceImpl.class);
    private MailConfigDAO mailconfigDAO;
    private ComuniService comuniService;
    private MovimentimailService movimentimailService;
    private PecInboxService pecInboxService;
    private CryptoUtils crypto = new CryptoUtils();
    private MailConfigComuniService mailConfigComuniService;
    private ComuniassociatiService comuniassociatiService;

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setMailConfigComuniService(MailConfigComuniService mailConfigComuniService) {

	this.mailConfigComuniService = mailConfigComuniService;
    }

    @Autowired
    public void setMailConfigDAO(MailConfigDAO mailconfigDAO) {

	this.mailconfigDAO = mailconfigDAO;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setMovimentimailService(MovimentimailService movimentimailService) {

	this.movimentimailService = movimentimailService;
    }

    @Autowired
    public void setPecInboxService(PecInboxService pecInboxService) {

	this.pecInboxService = pecInboxService;
    }

    @Override
    protected Class<MailConfig> getEntityClass() {

	return MailConfig.class;
    }

    @Override
    public List<MailConfig> findAll(Integer firstResult, Integer maxResult) {

	return mailconfigDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MailConfig entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity) && insertOrUpdateAllowed(entity, false)) {
	    mailconfigDAO.insert(entity);
	}
    }

    @Override
    public MailConfig findById(PkId id) {

	MailConfig mc = mailconfigDAO.findById(id);
	this.mailconfigDAO.evict(mc);
	return mc;
    }

    @Override
    public void update(MailConfig entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity) && insertOrUpdateAllowed(entity, true)) {
	    mailconfigDAO.update(entity);
	}
    }

    @Override
    public List<MailConfig> findBySoftware(String codiceSoftware, Boolean abilitati) {

	return findBySoftwareAndCodiceComune(codiceSoftware, null, abilitati);
    }

    @Override
    public List<MailConfig> findBySoftwareAndCodiceComune(String codiceSoftware, String codicecomune, Boolean abilitati) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = getFilterSoftwareAndCodiceComune(codiceSoftware, codicecomune, abilitati);
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("flagPrincipale"));
	return mailconfigDAO.findByFilterTable(ft);
    }

    @Override
    public List<MailConfig> findBySoftwareAndListCodiceComune(String codiceSoftware, String[] codicicomune, boolean abilitati) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = getFilterSoftwareAndListaCodiceComune(codiceSoftware, codicicomune, abilitati);
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("flagPrincipale"));
	return mailconfigDAO.findByFilterTable(ft);
    }

    @Override
    public MailConfig findBySoftwareAndCodiceComuneAttiviAndPrincipali(String codiceSoftware, String codicecomune, Boolean abilitati) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = getFilterSoftwareAndCodiceComune(codiceSoftware, codicecomune, abilitati);
	fr.addFilterField(FilterUtils.equals("flagPrincipale", true, Boolean.class));
	ft.addRestriction(fr);
	List<MailConfig> l = mailconfigDAO.findByFilterTable(ft);
	if (!l.isEmpty()) {
	    return l.get(0);
	}
	return null;
    }

    @Override
    public List<MailConfig> findForInvioEmailBySoftwareAndCodiceComuneAbilitati(String codiceSoftware, String codicecomune, boolean cercaInTT) {

	List<MailConfig> l = new ArrayList<MailConfig>();
	if (codicecomune != null) {
	    List<MailConfig> l1 = this.findBySoftwareAndCodiceComune(codiceSoftware, codicecomune, true);
	    for (MailConfig mailConfig1 : l1) {
		l.add(mailConfig1);
	    }
	    if (cercaInTT) {
		List<MailConfig> l2 = this.findBySoftwareAndCodiceComune(WebConstants.SOFTWARE_TT, codicecomune, true);
		for (MailConfig mailConfig2 : l2) {
		    l.add(mailConfig2);
		}
	    }
	}
	List<MailConfig> l3 = this.findBySoftwareAndCodiceComune(codiceSoftware, null, true);
	for (MailConfig mailConfig3 : l3) {
	    l.add(mailConfig3);
	}
	if (cercaInTT) {
	    List<MailConfig> l4 = this.findBySoftwareAndCodiceComune(WebConstants.SOFTWARE_TT, null, true);
	    for (MailConfig mailConfig4 : l4) {
		l.add(mailConfig4);
	    }
	}
	return l;
    }

    @Override
    public List<MailConfig> findForInvioEmailBySoftwareAndCodiceComuneAbilitati(String codiceSoftware, String[] codicecomune) {

	List<MailConfig> l = new ArrayList<MailConfig>();
	if (codicecomune != null) {
	    List<MailConfig> l1 = this.findBySoftwareAndListCodiceComune(codiceSoftware, codicecomune, true);
	    for (MailConfig mailConfig1 : l1) {
		l.add(mailConfig1);
	    }
	}
	return l;
    }

    @Override
    public MailConfig findPrepopolaInvioEmailBySoftwareAndCodiceComune(String codiceSoftware, String codicecomune, boolean cercaInTT) {

	MailConfig mailConfig = null;
	if (codicecomune != null) {
	    List<MailConfig> l1 = this.findBySoftwareAndCodiceComune(codiceSoftware, codicecomune, true);
	    if (!l1.isEmpty()) {
		for (MailConfig mailConfig2 : l1) {
		    if (StringUtils.isNotBlank(mailConfig2.getSenderaddress())) {
			log.debug("findPrepopolaInvioEmailBySoftwareAndCodiceComune# Account per software={},codiceComune={},default={}",
				new Object[] { codiceSoftware, codicecomune, mailConfig2.getFlagPrincipale() });
			return mailConfig2;
		    }
		}
	    }
	    if (cercaInTT) {
		List<MailConfig> l2 = this.findBySoftwareAndCodiceComune(WebConstants.SOFTWARE_TT, codicecomune, true);
		if (!l2.isEmpty()) {
		    for (MailConfig mailConfig2 : l2) {
			if (StringUtils.isNotBlank(mailConfig2.getSenderaddress())) {
			    log.debug("findPrepopolaInvioEmailBySoftwareAndCodiceComune# Account per software={},codiceComune={},default={}",
				    new Object[] { WebConstants.SOFTWARE_TT, codicecomune, mailConfig2.getFlagPrincipale() });
			    return mailConfig2;
			}
		    }
		}
	    }
	}
	List<MailConfig> l3 = this.findBySoftwareAndCodiceComune(codiceSoftware, null, true);
	if (!l3.isEmpty()) {
	    for (MailConfig mailConfig2 : l3) {
		if (StringUtils.isNotBlank(mailConfig2.getSenderaddress())) {
		    log.debug("findPrepopolaInvioEmailBySoftwareAndCodiceComune# Account per software={},codiceComune={},default={}",
			    new Object[] { codiceSoftware, "TUTTI", mailConfig2.getFlagPrincipale() });
		    return mailConfig2;
		}
	    }
	}
	List<MailConfig> l4 = this.findBySoftwareAndCodiceComune(WebConstants.SOFTWARE_TT, null, true);
	if (!l4.isEmpty()) {
	    for (MailConfig mailConfig2 : l4) {
		if (StringUtils.isNotBlank(mailConfig2.getSenderaddress())) {
		    log.debug("findPrepopolaInvioEmailBySoftwareAndCodiceComune# Account per software={},codiceComune={},default={}",
			    new Object[] { WebConstants.SOFTWARE_TT, "TUTTI", mailConfig2.getFlagPrincipale() });
		    return mailConfig2;
		}
	    }
	}
	return mailConfig;
    }

    private FilterRestriction getFilterSoftwareAndListaCodiceComune(String codiceSoftware, String[] codicicomune, Boolean abilitati) {

	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codice", codiceSoftware, "software", String.class));
	if (codicicomune != null && codicicomune.length > 0) {
	    //fr.addFilterField(FilterUtils.in("codicecomune", codicicomune, "comuni", String.class));
	    fr.addFilterField(FilterUtils.in("comune.codicecomune", codicicomune, "mailConfigComuni", String.class));
	} else {
	    fr.addFilterField(FilterUtils.isNull("comune.codicecomune", "mailConfigComuni"));
	    //fr.addFilterField(FilterUtils.isNull("codicecomune", codicicomune, "mailConfigComuni", String.class));
	}
	//fr.addFilterField(FilterUtils.equals("flagPrincipale", true, Boolean.class));
	if (abilitati != null) {
	    fr.addFilterField(FilterUtils.equals("flagDisabilitato", !abilitati, Boolean.class));
	}
	return fr;
    }

    private FilterRestriction getFilterSoftwareAndCodiceComune(String codiceSoftware, String codicecomune, Boolean abilitati) {

	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codice", codiceSoftware, "software", String.class));
	if (codicecomune != null) {
	    //fr.addFilterField(FilterUtils.equals("codicecomune", codicecomune, "comuni", String.class));
	    fr.addFilterField(FilterUtils.equals("comune.codicecomune", codicecomune, "mailConfigComuni", String.class));
	}
	/*
	else {
	    //fr.addFilterField(FilterUtils.isNull("codicecomune", "comuni"));
	    fr.addFilterField(FilterUtils.isNull("comune.codicecomune", "mailConfigComuni"));
	}
	*/
	//fr.addFilterField(FilterUtils.equals("flagPrincipale", true, Boolean.class));
	if (abilitati != null) {
	    fr.addFilterField(FilterUtils.equals("flagDisabilitato", !abilitati, Boolean.class));
	}
	return fr;
    }

    @Override
    public MailConfig findBySoftwareAttiviAndPrincipali(String codiceSoftware, Boolean abilitati) {

	return findBySoftwareAndCodiceComuneAttiviAndPrincipali(codiceSoftware, null, abilitati);
    }

    private void dataIntegration(MailConfig entity, boolean insert) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'oggetto mailConfig passato è nullo");
	}
	if (entity.getUseauthentication() == null)
	    entity.setUseauthentication(Boolean.valueOf(false));
	if (entity.getUsessl() == null)
	    entity.setUsessl(new Integer(0));
	if (entity.getInUseauthentication() == null)
	    entity.setInUseauthentication(Boolean.valueOf(false));
	if (entity.getInUsessl() == null)
	    entity.setInUsessl(new Integer(0));
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(false);
	}
	if (entity.getFlagPrincipale() == null) {
	    entity.setFlagPrincipale(false);
	}
	if (insert) {
	    entity.setDataInserimento(new Date());
	}
	if (!insert) {
	    Integer idDB = entity.getId().getCodice();
	    StringBuffer sb = new StringBuffer();
	    MailConfig mailConfigDB = this.findById(new PkId(idDB));
	    if (!mailConfigDB.getLoginname().equals(StringUtils.defaultIfEmpty(entity.getInLoginname(), ""))) {
		sb.append("Utente (email ingresso): ").append(mailConfigDB.getLoginname()).append("--->").append(entity.getLoginname());
	    }
	    if (!mailConfigDB.getLoginname().equals(StringUtils.defaultIfEmpty(entity.getLoginname(), ""))) {
		sb.append("Utente (email uscita): ").append(mailConfigDB.getLoginname()).append("--->").append(entity.getLoginname());
	    }
	    if (!StringUtils.defaultIfEmpty(mailConfigDB.getSenderaddress(), "").equals(StringUtils.defaultIfEmpty(entity.getSenderaddress(), ""))) {
		sb.append("Email (email uscita): ").append(mailConfigDB.getSenderaddress()).append("--->").append(entity.getSenderaddress());
	    }
	    entity.setDescrizioneUiltimaModifica(sb.toString());
	    entity.setDataUltimaModifica(new Date());
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MailConfig entity) {

	Comuni comune = comuniService.bindDomainObject(entity.getComuni(), String.class, "codicecomune");
	entity.setComuni(comune);
    }

    @Override
    public void delete(MailConfig entity) {

	if (isDeleteAllowed(entity)) {
	    mailConfigComuniService.deleteByMailConfigId(entity.getId().getCodice());
	    mailconfigDAO.delete(entity);
	}
    }

    // usato solo in fase di setup per le vecchie versioni, non utilizzare nel backoffice
    @Deprecated
    @Override
    public MailConfig findMailConfig() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	fr.addFilterField(FilterUtils.equals("flagPrincipale", Boolean.TRUE, Boolean.class));
	fr.addFilterField(FilterUtils.equals("flagDisabilitato", Boolean.FALSE, Boolean.class));
	ft.addRestriction(fr);
	List<MailConfig> l = mailconfigDAO.findByFilterTable(ft);
	if (!l.isEmpty()) {
	    return l.get(0);
	} else {
	    FilterTable ftTT = new FilterTable(DAOEnum.FIND_ALL);
	    FilterRestriction frTT = new FilterRestriction();
	    frTT.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	    frTT.addFilterField(FilterUtils.equals("codice", WebConstants.SOFTWARE_TT, "software", String.class));
	    frTT.addFilterField(FilterUtils.equals("flagPrincipale", Boolean.TRUE, Boolean.class));
	    frTT.addFilterField(FilterUtils.equals("flagDisabilitato", Boolean.FALSE, Boolean.class));
	    ftTT.addRestriction(frTT);
	    List<MailConfig> lTT = mailconfigDAO.findByFilterTable(ftTT);
	    if (!lTT.isEmpty()) {
		return lTT.get(0);
	    }
	}
	return null;
    }

    private boolean insertOrUpdateAllowed(MailConfig entity, boolean isUpdate) {

	Integer idDB = entity.getId().getCodice();
	if (isUpdate) {
	    log.debug("insertOrUpdateAllowed# Upadte = {}", isUpdate);
	}
	Integer codiceAttuale = entity.getId().getCodice();
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// gestione blocco presenza più accout principali principali
	if (entity.getFlagPrincipale().equals(true)) {
	    if (entity.getComuni() != null && StringUtils.isNotBlank(entity.getComuni().getCodicecomune())) {
		// cerco principale con software e codicecomune
		MailConfig m = this.findBySoftwareAndCodiceComuneAttiviAndPrincipali(entity.getSoftware().getCodice(),
			entity.getComuni().getCodicecomune(), null);
		if (EntityUtils.getNestedProperty(m, "id.codice") != null && !m.getId().getCodice().equals(codiceAttuale)) {
		    String mess = getMessageFromBundle("service_error.mail_cfg_principale_presente",
			    new Object[] { m.getSoftware().getDescrizione(), entity.getComuni().getComune() });
		    _ivs.add(new InvalidValue(mess, null, null, null, null));
		}
	    } else {
		// cerco principale con software e codicecomune NULL
		MailConfig m = this.findBySoftwareAndCodiceComuneAttiviAndPrincipali(entity.getSoftware().getCodice(), null, null);
		if (EntityUtils.getNestedProperty(m, "id.codice") != null && !m.getId().getCodice().equals(codiceAttuale)) {
		    String mess = getMessageFromBundle("service_error.mail_cfg_principale_presente",
			    new Object[] { "TUTTI", m.getSoftware().getDescrizione() });
		    //		    _ivs.add(new InvalidValue(mess, null, null, null, null));
		    FlashMessages.getWarnings().add(mess+". Verificare la configurazione dei comuni");
		}
	    }
	}
	// controllo update campo utente e email se l'accounto è stato utilizzato per un invio di email 
	// e per processare le pec non potrà essere modificato,
	// ma si dovrà creare un nuovo accout e disabilitare il vecchio
	MailConfig mailConfigDB = this.findById(new PkId(idDB));
	if (isUpdate) {
	    if (entity.getFlagDisabilitato().equals(mailConfigDB.getFlagDisabilitato())) {
		int c = movimentimailService.countByAccountId(entity.getId().getCodice());
		int p = pecInboxService.countByAccountId(entity.getId().getCodice());
		if (!mailConfigDB.getLoginname().equals(StringUtils.defaultIfEmpty(entity.getInLoginname(), "")) && p > 0) {
		    String mess = getMessageFromBundle("service_error.mail_cfg_modifica_dati",
			    new Object[] { "Utente (Email in ingresso)", "PEC_INBOX" });
		    _ivs.add(new InvalidValue(mess, null, null, null, null));
		}
		if (!mailConfigDB.getLoginname().equals(StringUtils.defaultIfEmpty(entity.getLoginname(), "")) && c > 0) {
		    String mess = getMessageFromBundle("service_error.mail_cfg_modifica_dati",
			    new Object[] { "Utente (Email in uscita)", "MOVIMENTI_EMAIL" });
		    _ivs.add(new InvalidValue(mess, null, null, null, null));
		}
		if (!StringUtils.defaultIfEmpty(mailConfigDB.getSenderaddress(), "").equals(StringUtils.defaultIfEmpty(entity.getSenderaddress(), ""))
			&& c > 0) {
		    String mess = getMessageFromBundle("service_error.mail_cfg_modifica_dati",
			    new Object[] { "Indirizzo Email (Email in uscita)", "MOVIMENTI_EMAIL" });
		    _ivs.add(new InvalidValue(mess, null, null, null, null));
		}
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    protected boolean isDeleteAllowed(MailConfig entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	int c = movimentimailService.countByAccountId(entity.getId().getCodice());
	if (c > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTIMAIL", null));
	}
	int p = pecInboxService.countByAccountId(entity.getId().getCodice());
	if (p > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PEC_INBOX", null));
	}
	if (!_ivs.isEmpty()) {
	    String mess = getMessageFromBundle("service_error.disabilitare_mail_cfg", null);
	    _ivs.add(new InvalidValue(mess, null, null, null, null));
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    @Override
    public void encryptPassword(MailConfig mc) {

	if (mc != null) {
	    if (StringUtils.isNotEmpty(mc.getLoginpass())) {
		mc.setLoginpass(crypto.encrypt(CryptoUtils.DEFAULT_SECRET_KEY, mc.getLoginpass()));
	    }
	    if (StringUtils.isNotEmpty(mc.getInLoginpass())) {
		mc.setInLoginpass(crypto.encrypt(CryptoUtils.DEFAULT_SECRET_KEY, mc.getInLoginpass()));
	    }
	}
    }

    @Override
    public void decryptPassword(MailConfig mc) {

	if (mc != null) {
	    if (StringUtils.isNotEmpty(mc.getLoginpass())) {
		mc.setLoginpass(crypto.decrypt(CryptoUtils.DEFAULT_SECRET_KEY, mc.getLoginpass()));
	    }
	    if (StringUtils.isNotEmpty(mc.getInLoginpass())) {
		mc.setInLoginpass(crypto.decrypt(CryptoUtils.DEFAULT_SECRET_KEY, mc.getInLoginpass()));
	    }
	}
    }
}
