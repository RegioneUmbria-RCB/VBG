package it.gruppoinit.pal.gp.core.features.stc.richiestapratica;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.init.sigepro.rte.types.SportelloType;

public class RichiestaPraticaFromNotificaPraticaStoricaParams {

    private StcService stcService;
    private Integer codiceIstanza;
    private SportelloType mittente;
    private SportelloType destinatario;
    private String idProcedimento;
    private Integer codiceAmministrazioneSTC;

    public RichiestaPraticaFromNotificaPraticaStoricaParams(StcService stcService, VerticalizzazioniService verticalizzazioniService,
	    Movimenti movimento) {

	// TODO CHECK amministrazioneStc NPE E RILANCIA ECCEZIONE
	this.stcService = stcService;
	this.codiceIstanza = movimento.getIstanza().getId().getCodice();
	this.setMittente(verticalizzazioniService);
	this.destinatario = populateSportelloFromMovimento(movimento.getAmministrazioniStc());
	this.codiceAmministrazioneSTC = movimento.getAmministrazioniStc().getId().getCodice();
	this.idProcedimento = populateProcedimentoFromMovimento(movimento);
    }

    public String getIdProcedimento() {

	return idProcedimento;
    }

    private String populateProcedimentoFromMovimento(Movimenti movimento) {

	String idProcedimentoRet = decodeEndoProcedimento(movimento.getEndoprocedimento());
	// quando sarà presente nella tabella movimenti il campo codiceProcedimentoSTC(settato durante la notifica) andrà recuperato
	// questo invece di quello delle mappature
	// gestione mappature (configurate nel tipo procedimento)
	Set<TipimovStcMapping> mappings = movimento.getTipomovimento().getTipimovStcMappings();
	for (TipimovStcMapping tipimovStcMapping : mappings) {
	    Amministrazioni ammTipiMov = tipimovStcMapping.getAmministrazioni();
	    // se trovo codiceprocedimento lo sovrascrivo
	    if (ammTipiMov.getId().getCodice().intValue() == codiceAmministrazioneSTC.intValue()
		    && StringUtils.isNotBlank(tipimovStcMapping.getCodiceprocedimento())) {
		idProcedimentoRet = tipimovStcMapping.getCodiceprocedimento();
		break;
	    }
	}
	return idProcedimentoRet;
    }

    protected String decodeEndoProcedimento(Inventarioprocedimenti endoProcedimento) {

	String result = "";
	if (endoProcedimento != null && endoProcedimento.getId() != null && endoProcedimento.getId().getCodice() != null) {
	    result = endoProcedimento.getId().getCodice().toString();
	}
	return result;
    }

    public StcService getStcService() {

	return this.stcService;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public SportelloType getMittente() {

	return mittente;
    }

    public SportelloType getDestinatario() {

	return destinatario;
    }

    private void setMittente(VerticalizzazioniService verticalizzazioniService) {

	Verticalizzazioniparametri vparam = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC, "NLA_IDNODO");
	if (vparam == null) {
	    throw new InvalidConfigurationException("Attenzione! non è stato configurato il parametro NLA_IDNODO della verticalizzazione STC");
	}
	this.mittente = new SportelloType();
	this.mittente.setIdEnte(ORMHelper.getIdcomuneAlias());
	this.mittente.setIdSportello(ORMHelper.getSoftware());
	this.mittente.setIdNodo(vparam.getValore());
    }

    private SportelloType populateSportelloFromMovimento(Amministrazioni amministrazioneStc) {

	SportelloType sportello = new SportelloType();
	sportello.setIdEnte(amministrazioneStc.getStcIdente());
	sportello.setIdSportello(amministrazioneStc.getStcIdsportello());
	sportello.setIdNodo(amministrazioneStc.getStcIdnodo());
	return sportello;
    }
}
