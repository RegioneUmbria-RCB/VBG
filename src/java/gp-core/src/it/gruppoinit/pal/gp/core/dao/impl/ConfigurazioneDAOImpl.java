package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import org.springframework.stereotype.Repository;

@Repository
public class ConfigurazioneDAOImpl extends BaseDAOImpl<Configurazione, ConfigurazioneId> implements ConfigurazioneDAO {

    @Override
    public void delete(Configurazione entity) {

	throw new NotImplementedException();
    }

    @Override
    public Class<Configurazione> getEntityClass() {

	return Configurazione.class;
    }

    @Override
    public Integer[] getCodiciAmministrazioniSistema() {

	ConfigurazioneId id = new ConfigurazioneId();
	id.setSoftware(WebConstants.SOFTWARE_TT);
	Configurazione conf = this.findById(id);
	Integer[] codici = null;
	if (conf != null) {
	    codici = new Integer[3];
	    if (conf.getCodammsportellounico() == null) {
		throw new BusinessValidationException(
			"Errore nella configurazione non sono stati settati i valori delle amministrazioni di sistema. Campo CONFIGURAZIONE.Codammsportellounico (valore tipico 0 vedi tabella amministrazioni).");
	    }
	    codici[0] = conf.getCodammsportellounico();
	    if (conf.getCodicelastessaamministrazione() == null) {
		throw new BusinessValidationException(
			"Errore nella configurazione non sono stati settati i valori delle amministrazioni di sistema. Campo CONFIGURAZIONE.Codicelastessaamministrazione (valore tipico -2 vedi tabella amministrazioni).");
	    }
	    codici[1] = conf.getCodicelastessaamministrazione();
	    if (conf.getCodicetutteamministrazioni() == null) {
		throw new BusinessValidationException(
			"Errore nella configurazione non sono stati settati i valori delle amministrazioni di sistema. Campo CONFIGURAZIONE.Codicetutteamministrazioni (valore tipico -1 vedi tabella amministrazioni).");
	    }
	    codici[2] = conf.getCodicetutteamministrazioni();
	}
	return codici;
    }

    @Override
    public Integer[] getCodiciTutteEStessaAmministrazioniSistema() {

	ConfigurazioneId id = new ConfigurazioneId();
	id.setSoftware(WebConstants.SOFTWARE_TT);
	Configurazione conf = this.findById(id);
	Integer[] codici = null;
	if (conf != null) {
	    codici = new Integer[2];
	    if (conf.getCodicelastessaamministrazione() == null) {
		throw new BusinessValidationException(
			"Errore nella configurazione non sono stati settati i valori delle amministrazioni di sistema. Campo CONFIGURAZIONE.Codicelastessaamministrazione (valore tipico -2 vedi tabella amministrazioni).");
	    }
	    codici[0] = conf.getCodicelastessaamministrazione();
	    if (conf.getCodicetutteamministrazioni() == null) {
		throw new BusinessValidationException(
			"Errore nella configurazione non sono stati settati i valori delle amministrazioni di sistema. Campo CONFIGURAZIONE.Codicetutteamministrazioni (valore tipico -1 vedi tabella amministrazioni).");
	    }
	    codici[1] = conf.getCodicetutteamministrazioni();
	}
	return codici;
    }
}
