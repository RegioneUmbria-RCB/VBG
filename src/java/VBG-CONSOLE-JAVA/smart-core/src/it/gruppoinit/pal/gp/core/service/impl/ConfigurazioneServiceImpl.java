package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.OrariEContattiBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.SupportoBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatisoftwareService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MailtipoService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.ProtocolloConfigurazioneService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurazioneServiceImpl extends BaseServiceImpl<Configurazione, ConfigurazioneId> implements ConfigurazioneService {

    private ConfigurazioneDAO configurazioneDAO;
    private ComuniassociatiService comuniassociatiService;
    private ComuniassociatisoftwareService comuniassociatisoftwareService;
    private OggettiService oggettiService;
    private ProtocolloConfigurazioneService protocolloConfigurazioneService;
    private MailtipoService mailtipoService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setComuniassociatisoftwareService(ComuniassociatisoftwareService comuniassociatisoftwareService) {

	this.comuniassociatisoftwareService = comuniassociatisoftwareService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setConfigurazioneDAO(ConfigurazioneDAO configurazioneDAO) {

	this.configurazioneDAO = configurazioneDAO;
    }

    @Autowired
    public void setProtocolloConfigurazioneService(ProtocolloConfigurazioneService protocolloConfigurazioneService) {

	this.protocolloConfigurazioneService = protocolloConfigurazioneService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Override
    protected Class<Configurazione> getEntityClass() {

	return Configurazione.class;
    }

    @Override
    public Integer[] getCodiciAmministrazioniSistema() {

	return configurazioneDAO.getCodiciAmministrazioniSistema();
    }

    @Override
    public void delete(Configurazione entity) {

	throw new NotImplementedException("Il metodo delete non è implementato");
	// configurazioneDAO.delete(entity);
    }

    @Override
    public List<Configurazione> findAll(Integer firstResult, Integer maxResult) {

	return configurazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Configurazione findById(ConfigurazioneId id) {

	Configurazione configurazione = configurazioneDAO.findById(id);
	return configurazione;
    }

    @Override
    public void insert(Configurazione entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggettomoddoctipo", false, entity.getId());
	    configurazioneDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void insertNoValidate(Configurazione entity) {

	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggettomoddoctipo", false, entity.getId());
	configurazioneDAO.insert(entity);
	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    @Override
    public void update(Configurazione entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggettomoddoctipo", false, entity.getId());
	    configurazioneDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void insertDatigenerali(Configurazione entity) {

	if (validateEntityDatigenerali(entity)) {
	    this.insertNoValidate(entity);
	}
    }

    @Override
    public void insertConfigurazionedatigeneraliAndComuniassociatesoftware(Configurazione configurazione,
	    Comuniassociatisoftware comuniassociatisoftware, Comuniassociati comuniassociati) {

	if (validateEntityDatigenerali(configurazione)) {
	    this.insertNoValidate(configurazione);
	    Boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	    // andiamo ad inserire l'oggetto comuni associati software solo se stiamo trattando un associazione di
	    // comuni
	    // quello che andiamo a inserire sarà condiviso da tutti i comuni dell'associazione, ed avrà l'ogggeto
	    // comuni
	    // uguale a null
	    if (isComuniAssociati) {
		comuniassociatisoftware.setComuni(null);
		comuniassociatisoftware.setConfigurazione(configurazione);
		comuniassociatisoftware.setSoftware(ORMHelper.getSoftware());
		comuniassociatisoftwareService.insert(comuniassociatisoftware);
	    } else {
		comuniassociatiService.insert(comuniassociati);
		comuniassociatisoftware.setComuniassociati(comuniassociati);
		comuniassociatisoftware.setComuni(comuniassociati.getComune());
		comuniassociatisoftware.setSoftware(ORMHelper.getSoftware());
		comuniassociatisoftwareService.insert(comuniassociatisoftware);
	    }
	}
    }

    /**
     * Inserisce nell'oggetto configurazione i campi : scrittaregione,oggettoLogoregione,oggettoLogocomune
     */
    @Override
    public void insertLoghi(Configurazione entity) {

	Integer codiceOggettoLogoregione = controllaCancellaOggetti(entity, "oggettoLogoregione", false, entity.getId());
	Integer codiceOggettoLogocomune = controllaCancellaOggetti(entity, "oggettoLogocomune", false, entity.getId());
	configurazioneDAO.insert(entity);
	if (codiceOggettoLogoregione != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoLogoregione));
	    oggettiService.delete(oggettoDaCancellare);
	}
	if (codiceOggettoLogocomune != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoLogocomune));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    /**
     * Fa l'update nell'oggetto configurazione i campi : scrittaregione,oggettoLogoregione,oggettoLogocomune
     */
    @Override
    public void updateLoghi(Configurazione entity) {

	Integer codiceOggettoLogoregione = controllaCancellaOggetti(entity, "oggettoLogoregione", false, entity.getId());
	Integer codiceOggettoLogocomune = controllaCancellaOggetti(entity, "oggettoLogocomune", false, entity.getId());
	configurazioneDAO.update(entity);
	if (codiceOggettoLogoregione != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoLogoregione));
	    oggettiService.delete(oggettoDaCancellare);
	}
	if (codiceOggettoLogocomune != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoLogocomune));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    @Override
    public void insertOrUpdateConfigurazioneMailAntTestiTipo(Configurazione configurazione, ProtocolloConfigurazione protocolloConfigurazione) {

	// Faccio l'update dell'oggetto configurazione (mailtipoAmministrazioneEndo,mailtipoMovimentoNegativo,mailtipoMovimentoRichiedente
	//,mailtipoMovimentoAmministrazione )
	Configurazione configurazioneDB = configurazioneDAO.findById(new ConfigurazioneId());
	dataIntegrationConfigurazioneMailAntTestiTipo(configurazione, protocolloConfigurazione);
	if (configurazioneDB != null) {
	    configurazioneDAO.update(configurazione);
	} else {
	    configurazioneDAO.insert(configurazione);
	}
	// Faccio l'update dell'oggetto protocolloConfigurazione (mailtipoByFkIstanza,mailtipoByFkMovimento)
	ProtocolloConfigurazione protocolloConfigurazioneDB = protocolloConfigurazioneService.findById(new ProtocolloConfigurazioneId());
	if (protocolloConfigurazioneDB != null) {
	    protocolloConfigurazioneService.update(protocolloConfigurazione);
	} else {
	    protocolloConfigurazioneService.insert(protocolloConfigurazione);
	}
    }

    @Override
    public List<Configurazione> findbyResponsabile(Integer codiceResponsabile) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.codice", codiceResponsabile, "responsabili", Integer.class));
	filterTable.addRestriction(filterRestriction);
	return configurazioneDAO.findByFilterTable(filterTable);
    }

    private void dataIntegrationConfigurazioneMailAntTestiTipo(Configurazione entity, ProtocolloConfigurazione protocolloConfigurazione) {

	fixMergeEntityPropertiesConfigurazioneMailAntTestiTipo(entity, protocolloConfigurazione);
    }

    private void fixMergeEntityPropertiesConfigurazioneMailAntTestiTipo(Configurazione entity, ProtocolloConfigurazione protocolloConfigurazione) {

	Mailtipo mailtipoAmministrazioneEndo = mailtipoService.bindDomainObject(entity.getMailtipoAmministrazioneEndo(), PkId.class, "id.codice");
	entity.setMailtipoAmministrazioneEndo(mailtipoAmministrazioneEndo);
	Mailtipo mailtipoMovimentoAmministrazione = mailtipoService.bindDomainObject(entity.getMailtipoMovimentoAmministrazione(), PkId.class,
		"id.codice");
	entity.setMailtipoMovimentoAmministrazione(mailtipoMovimentoAmministrazione);
	Mailtipo mailtipoMovimentoNegativo = mailtipoService.bindDomainObject(entity.getMailtipoMovimentoNegativo(), PkId.class, "id.codice");
	entity.setMailtipoMovimentoNegativo(mailtipoMovimentoNegativo);
	Mailtipo mailtipoMovimentoRichiedente = mailtipoService.bindDomainObject(entity.getMailtipoMovimentoRichiedente(), PkId.class, "id.codice");
	entity.setMailtipoMovimentoRichiedente(mailtipoMovimentoRichiedente);
	Mailtipo mailtipoMailtipoByFkIstanza = mailtipoService.bindDomainObject(protocolloConfigurazione.getMailtipoByFkIstanza(), PkId.class,
		"id.codice");
	protocolloConfigurazione.setMailtipoByFkIstanza(mailtipoMailtipoByFkIstanza);
	Mailtipo mailtipoMailtipoByFkMovimento = mailtipoService.bindDomainObject(protocolloConfigurazione.getMailtipoByFkMovimento(), PkId.class,
		"id.codice");
	protocolloConfigurazione.setMailtipoByFkMovimento(mailtipoMailtipoByFkMovimento);
    }

    private boolean validateEntityDatigenerali(Configurazione entity) {

	boolean isInsert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (StringUtils.isBlank(entity.getDenominazione())) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", Configurazione.class, "denominazione", entity.getDenominazione(), entity));
	}
	if (StringUtils.isBlank(entity.getFax())) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", Configurazione.class, "fax", entity.getFax(), entity));
	}
	if (StringUtils.isBlank(entity.getTelefono())) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", Configurazione.class, "telefono", entity.getTelefono(), entity));
	}
	if (StringUtils.isBlank(entity.getProvincia())) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", Configurazione.class, "provincia", entity.getProvincia(), entity));
	}
	if (StringUtils.isBlank(entity.getCap())) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", Configurazione.class, "cap", entity.getCap(), entity));
	}
	if (StringUtils.isBlank(entity.getIndirizzo())) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", Configurazione.class, "indirizzo", entity.getIndirizzo(), entity));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isInsert;
    }

    @Override
    public OrariEContattiBean getOrariEContatti() {

	OrariEContattiBean result = new OrariEContattiBean();
	SupportoBean s = new SupportoBean();
	Configurazione c = findById(new ConfigurazioneId(ORMHelper.getSoftware()));
	// Necessario per recuperare l'informazione sulla denominazione del comune, che è popolata
	// per software == TT
	Configurazione cTT = findById(new ConfigurazioneId(WebConstants.SOFTWARE_TT));
	if (c != null) {
	    result.setOrari(c.getOrario());
	    s.setTelefono(c.getTelefono());
	    s.setFax(c.getFax());
	    s.setEmail(c.getEmailresponsabile());
	    s.setPec(c.getEmailresponsabilepec());
	    result.setSupporto(s);
	    result.setDenominazioneSportello(StringUtils.defaultIfEmpty(c.getDenominazione(), ""));
	    result.setIndirizzoSportello(StringUtils.defaultIfEmpty(c.getIndirizzo(), ""));
	    if (EntityUtils.getNestedProperty(c.getResponsabili(), "id.codice") != null) {
		result.setResponsabileSportello(StringUtils.defaultIfEmpty(c.getResponsabili().getResponsabile(), ""));
	    }
	}
	if (cTT != null) {
	    result.setDenominazioneComune(StringUtils.defaultIfEmpty(cTT.getDenominazione(), ""));
	}
	return result;
    }
}
