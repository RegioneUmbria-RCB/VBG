package it.gruppoinit.stc.service.impl;

import it.gruppoinit.stc.dao.SicurezzaDAO;
import it.gruppoinit.stc.domain.Configurazione;
import it.gruppoinit.stc.domain.Sicurezza;
import it.gruppoinit.stc.service.ConfigurazioneService;
import it.gruppoinit.stc.service.SicurezzaService;
import it.gruppoinit.stc.service.helper.TokenGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SicurezzaServiceImpl extends BaseServiceImpl<Sicurezza, Integer> implements SicurezzaService {

    private Logger log = LoggerFactory.getLogger(SicurezzaServiceImpl.class);
    @Autowired
    private SicurezzaDAO sicurezzaDAO;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private ConfigurazioneService configurazioneService;

    @Override
    protected Class<Sicurezza> getEntityClass() {

	return Sicurezza.class;
    }

    @Override
    public void delete(Sicurezza entity) {

	sicurezzaDAO.delete(entity);
    }

    @Override
    public List<Sicurezza> findAll(Integer firstResult, Integer maxResult) {

	return sicurezzaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Sicurezza findById(Integer id) {

	return sicurezzaDAO.findById(id);
    }

    @Override
    public void insert(Sicurezza entity) {

	sicurezzaDAO.insert(entity);
    }

    @Override
    public void update(Sicurezza entity) {

	sicurezzaDAO.update(entity);
    }

    @Override
    public String getToken(Configurazione nodo) {

	Configurazione conf = configurazioneService.findById(nodo.getIdnodo());
	Set<Sicurezza> sicurezzas = conf.getSicurezzasForFkidnodo();
	String token = "";
	if (sicurezzas.isEmpty()) {
	    Sicurezza sicurezza = new Sicurezza();
	    token = tokenGenerator.getToken();
	    Calendar cal = new GregorianCalendar();
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    Date scadenza = cal.getTime();
	    sicurezza.setScadenza(scadenza);
	    sicurezza.setToken(token);
	    sicurezza.setConfigurazioneByFkidnodo(conf);
	    this.insert(sicurezza);
	} else {
	    for (Sicurezza sicurezza : sicurezzas) {
		if (sicurezza.getScadenza() != null && (sicurezza.getScadenza().compareTo(GregorianCalendar.getInstance().getTime()) < 0)) {
		    token = tokenGenerator.getToken();
		    sicurezza.setToken(token);
		    Calendar cal = new GregorianCalendar();
		    cal.add(Calendar.DAY_OF_MONTH, 1);
		    Date scadenza = cal.getTime();
		    sicurezza.setScadenza(scadenza);
		    this.update(sicurezza);
		} else {
		    token = sicurezza.getToken();
		}
		break;
	    }
	}
	return token;
    }

    @Override
    public boolean checkToken(String token) {

	boolean success = false;
	Sicurezza sicurezza = sicurezzaDAO.findByToken(token);
	if (sicurezza != null) {
	    success = true;
	}
	if (!success) {
	    log.error("Token non valido: " + token);
	    throw new RuntimeException("Token non valido: " + token);
	}
	return success;
    }
}
