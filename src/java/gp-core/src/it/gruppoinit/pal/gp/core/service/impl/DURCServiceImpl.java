package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.DURCService;
import it.gruppoinit.pal.gp.core.service.InfoDurcService;
import it.gruppoinit.pal.gp.core.service.exception.DurcNonValidoException;
import it.gruppoinit.pal.gp.core.service.helper.NuovoDURCHelper;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DURCServiceImpl implements DURCService {

    private VerticalizzazioniService verticalizzazioniService;
    private InfoDurcService infoDurcService;

    @Autowired
    public void setInfoDurcService(InfoDurcService infoDurcService) {

	this.infoDurcService = infoDurcService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public Anagrafedocumenti verificaEsistenzaDURC(Integer codiceAnagrafe, Integer codiceIstanza, Date dataVerifica)
	    throws FunzioneBusinessRemotaException, InvalidConfigurationException, DurcNonValidoException {

	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSDURC);
	if (isAttiva) {
	    Verticalizzazioniparametri tipoDURC = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_WSDURC,
		    WebConstants.VERTICALIZZAZIONE_WSDURC_SERVIZIO_ATTIVO);
	    if (tipoDURC != null) {
		if (StringUtils.isNotBlank(tipoDURC.getValore())) {
		    if (tipoDURC.getValore().equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC)) {
			return infoDurcService.verificaEsistenzaDURC(codiceAnagrafe, codiceIstanza, dataVerifica);
		    }
		}
	    }
	}
	throw new InvalidConfigurationException("Il servizio non è configurato correttamente");
    }

    @Override
    public Anagrafedocumenti nuovaRichiestaDURC(NuovoDURCHelper nuovoDURCHelper) throws FunzioneBusinessRemotaException,
	    InvalidConfigurationException {

	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSDURC);
	if (isAttiva) {
	    Verticalizzazioniparametri tipoDURC = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_WSDURC,
		    WebConstants.VERTICALIZZAZIONE_WSDURC_SERVIZIO_ATTIVO);
	    if (tipoDURC != null) {
		if (StringUtils.isNotBlank(tipoDURC.getValore())) {
		    if (tipoDURC.getValore().equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC)) {
			return infoDurcService.nuovaRichiestaDURC(nuovoDURCHelper);
		    }
		}
	    }
	}
	throw new InvalidConfigurationException("Il servizio non è configurato correttamente");
    }

    @Override
    public NuovoDURCHelper validaNuovaRichiestaDURC(Integer codiceAnagrafe) {

	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSDURC);
	if (isAttiva) {
	    Verticalizzazioniparametri tipoDURC = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_WSDURC,
		    WebConstants.VERTICALIZZAZIONE_WSDURC_SERVIZIO_ATTIVO);
	    if (tipoDURC != null) {
		if (StringUtils.isNotBlank(tipoDURC.getValore())) {
		    if (tipoDURC.getValore().equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC)) {
			return infoDurcService.validaNuovaRichiestaDURC(codiceAnagrafe);
		    }
		}
	    }
	}
	throw new InvalidConfigurationException("Il servizio non è configurato correttamente");
    }

    @Override
    public NuovoDURCHelper validaDURCHelper(NuovoDURCHelper nuovoDURCHelper) {

	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSDURC);
	if (isAttiva) {
	    Verticalizzazioniparametri tipoDURC = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_WSDURC,
		    WebConstants.VERTICALIZZAZIONE_WSDURC_SERVIZIO_ATTIVO);
	    if (tipoDURC != null) {
		if (StringUtils.isNotBlank(tipoDURC.getValore())) {
		    if (tipoDURC.getValore().equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC)) {
			return infoDurcService.validaDURCHelper(nuovoDURCHelper);
		    }
		}
	    }
	}
	throw new InvalidConfigurationException("Il servizio non è configurato correttamente");
    }
}
