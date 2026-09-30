package it.gruppoinit.pal.gp.core.features.suapxml;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class VerticalizzazioneSuapXmlServiceImpl implements IVerticalizzazioneSuapXmlService {

    public static final String NOME_VERTICALIZZAZIONE = "SUAP_XML";
    public static final String PAR_URL = "URL";
    public static final String PAR_GENERA_SU_INSERIMENTO_ISTANZA = "GENERA_SU_INSERIMENTO_ISTANZA";
    public static final String PAR_VIS_BOTTONE_SU_PROT_MOVIMENTO = "VIS_BOTTONE_SU_PROT_MOVIMENTO";
    public static final String PAR_VALIDA = "VALIDA";
    private VerticalizzazioniService verticalizzazioniService;
    private String codiceComune;

    public VerticalizzazioneSuapXmlServiceImpl(VerticalizzazioniService verticalizzazioniService, String codiceComune) {

	if (codiceComune == null) {
	    throw new IllegalArgumentException(
		    "È stata richiamata la verticalizzazione " + NOME_VERTICALIZZAZIONE + " senza passare il codice comune");
	}
	this.codiceComune = codiceComune;
	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public boolean isAttiva() {

	return this.verticalizzazioniService.isAttivaPerComune(NOME_VERTICALIZZAZIONE, this.codiceComune);
    }

    @Override
    public String url() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE, PAR_URL,
		this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    return null;
	}
	return parametro.getValore();
    }

    @Override
    public boolean generaSuInserimentoIstanza() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_GENERA_SU_INSERIMENTO_ISTANZA, this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    return false;
	}
	return "1".equalsIgnoreCase(parametro.getValore());
    }

    @Override
    public boolean visualizzaBottoneSuProtocollazioneMovimento() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_VIS_BOTTONE_SU_PROT_MOVIMENTO, this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    return false;
	}
	return "1".equalsIgnoreCase(parametro.getValore());
    }

    @Override
    public boolean valida() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_VALIDA, this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    return false;
	}
	return "1".equalsIgnoreCase(parametro.getValore());
    }
}
