package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ConfigurazioneutenteDAO;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class ConfigurazioneutenteServiceImpl extends BaseServiceImpl<Configurazioneutente, ConfigurazioneutenteId>
	implements ConfigurazioneutenteService {

    private static final Logger log = LoggerFactory.getLogger(ConfigurazioneutenteServiceImpl.class);
    private ConfigurazioneutenteDAO configurazioneutenteDAO;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setConfigurazioneutenteDAO(ConfigurazioneutenteDAO configurazioneutenteDAO) {

	this.configurazioneutenteDAO = configurazioneutenteDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    public List<Configurazioneutente> findByResponsabile(Responsabili responsabile) {

	boolean find = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (responsabile == null || responsabile.getId() == null || responsabile.getId().getCodice() == null) {
	    _ivs.add(new InvalidValue("errors.configurazioneutente.responsabile.vuoto", null, "", "", null));
	    find = false;
	}
	if (!find) {
	    this.throwValidationMessages(_ivs);
	}
	return configurazioneutenteDAO.findByResponsabile(responsabile);
    }

    @Override
    public void delete(Configurazioneutente entity) {

	configurazioneutenteDAO.delete(entity);
    }

    @Override
    public List<Configurazioneutente> findAll(Integer firstResult, Integer maxResult) {

	return configurazioneutenteDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Configurazioneutente findById(ConfigurazioneutenteId id) {

	return configurazioneutenteDAO.findById(id);
    }

    @Override
    public void insert(Configurazioneutente entity) {

	try {
	    // siccome la configurazione utente
	    // viene usata per salvare la configurazione di qualche parametro all'interno dei controller gestisco
	    // l'eccezione solamente loggandola
	    if (validateEntity(entity)) {
		configurazioneutenteDAO.insert(entity);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
    }

    @Override
    public void update(Configurazioneutente entity) {

	try {
	    // siccome la configurazione utente
	    // viene usata per salvare la configurazione di qualche parametro all'interno dei controller gestisco
	    // l'eccezione solamente loggandola
	    if (validateEntity(entity)) {
		configurazioneutenteDAO.update(entity);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
    }

    @Override
    protected Class<Configurazioneutente> getEntityClass() {

	return Configurazioneutente.class;
    }

    @Override
    public void insertOrUpdate(Configurazioneutente configurazioneutente, String parametro, String valore, Responsabili responsabile) {

	if (configurazioneutente != null) {
	    configurazioneutente.setValore(valore);
	    configurazioneutenteDAO.update(configurazioneutente);
	} else {
	    configurazioneutente = new Configurazioneutente();
	    ConfigurazioneutenteId configurazioneutenteId = null;
	    if (parametro.equals(WebConstants.CONF_UTENTE_NUMERO_RECORD_LISTE)) {
		configurazioneutenteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), WebConstants.CONF_UTENTE_NUMERO_RECORD_LISTE);
	    }
	    if (parametro.equals(WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI)) {
		configurazioneutenteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI);
	    }
	    if (parametro.equals(WebConstants.CONF_UTENTE_STILE_BO)) {
		configurazioneutenteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), WebConstants.CONF_UTENTE_STILE_BO);
	    }
	    configurazioneutente.setId(configurazioneutenteId);
	    configurazioneutente.setResponsabile(responsabile);
	    configurazioneutente.setValore(valore);
	    configurazioneutenteDAO.insert(configurazioneutente);
	}
    }

    @Override
    public String leggiParametroConfigurazioneUtente(String nomeParametro, String valorePredefinito) {

	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	//LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	//PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	//	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), nomeParametro);
	Configurazioneutente configurazioneUtente = this.findById(confUteId);
	if (configurazioneUtente == null) {
	    return valorePredefinito;
	} else {
	    return configurazioneUtente.getValore();
	}
    }

    @Override
    public void insertOrUpdate(String parametro, String valore, Integer codiceresponsabile) {

	ConfigurazioneutenteId showConfId = new ConfigurazioneutenteId(codiceresponsabile, parametro);
	Configurazioneutente configurazioneutente = this.findById(showConfId);
	if (configurazioneutente != null) {
	    configurazioneutente.setValore(valore);
	    configurazioneutenteDAO.update(configurazioneutente);
	} else {
	    configurazioneutente = new Configurazioneutente();
	    ConfigurazioneutenteId configurazioneutenteId = new ConfigurazioneutenteId(codiceresponsabile, parametro);
	    configurazioneutente.setId(configurazioneutenteId);
	    configurazioneutente.setResponsabile(configurazioneutenteDAO.getById(Responsabili.class, new PkId(codiceresponsabile)));
	    configurazioneutente.setValore(valore);
	    configurazioneutenteDAO.insert(configurazioneutente);
	}
    }

    @Override
    public Map<String, String> mapByResponsabile(Responsabili responsabile) {

	List<Configurazioneutente> l = this.findByResponsabile(responsabile);
	Map<String, String> ret = new HashMap<String, String>();
	for (Configurazioneutente c : l) {
	    ret.put(c.getId().getNomeparametro(), StringUtils.defaultString(c.getValore()));
	}
	return ret;
    }
}
