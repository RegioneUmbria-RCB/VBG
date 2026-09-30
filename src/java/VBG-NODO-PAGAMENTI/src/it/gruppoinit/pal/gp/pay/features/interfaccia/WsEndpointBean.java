package it.gruppoinit.pal.gp.pay.features.interfaccia;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;

@XmlRootElement(name = "ws_endpoint")
public class WsEndpointBean {

    private static final String CARICAMENTO = "FK_WS_CARICAMENTO";
    private static final String ANNULLAMENTO = "FK_WS_ANNULLAMENTO";
    private static final String VERIFICA = "FK_WS_VERIFICA";
    private static final String ATTIVA_SESSIONE = "FK_WS_ATTIVA_SESSIONE";
    private static final String AVVISO = "FK_WS_AVVISO";
    private static final String NOTIFICA = "FK_WS_NOTIFICA";
    private static final String SECURITY = "FK_WS_SECURITY";
    private static final String FATTURA = "FK_WS_FATTURA";
    private static final String RICEVUTA = "FK_WS_RICEVUTA";
    private static final String IUV = "FK_WS_IUV";
    /////
    @XmlElement(name = "annullamento")
    private ParametroEtichetta parametroAnnullamento;
    @XmlElement(name = "caricamento")
    private ParametroEtichetta parametroCaricamento;
    @XmlElement(name = "avviso")
    private ParametroEtichetta parametroAvviso;
    @XmlElement(name = "ricevuta")
    private ParametroEtichetta parametroRicevuta;
    @XmlElement(name = "verifica")
    private ParametroEtichetta parametroVerifica;
    @XmlElement(name = "attiva_sessione")
    private ParametroEtichetta parametroAttivaSessione;
    @XmlElement(name = "notifica")
    private ParametroEtichetta parametroNotifica;
    @XmlElement(name = "security")
    private ParametroEtichetta parametroSecurity;
    @XmlElement(name = "iuv")
    private ParametroEtichetta parametroIuv;
    @XmlElement(name = "fattura")
    private ParametroEtichetta parametroFattura;

    public ParametroEtichetta getParametroAnnullamento() {

	return parametroAnnullamento;
    }

    public void setParametroAnnullamento(ParametroEtichetta parametroAnnullamento) {

	this.parametroAnnullamento = parametroAnnullamento;
    }

    public ParametroEtichetta getParametroCaricamento() {

	return parametroCaricamento;
    }

    public void setParametroCaricamento(ParametroEtichetta parametroCaricamento) {

	this.parametroCaricamento = parametroCaricamento;
    }

    public ParametroEtichetta getParametroAvviso() {

	return parametroAvviso;
    }

    public void setParametroAvviso(ParametroEtichetta parametroAvviso) {

	this.parametroAvviso = parametroAvviso;
    }

    public ParametroEtichetta getParametroRicevuta() {

	return parametroRicevuta;
    }

    public void setParametroRicevuta(ParametroEtichetta parametroRicevuta) {

	this.parametroRicevuta = parametroRicevuta;
    }

    public ParametroEtichetta getParametroVerifica() {

	return parametroVerifica;
    }

    public void setParametroVerifica(ParametroEtichetta parametroVerifica) {

	this.parametroVerifica = parametroVerifica;
    }

    public ParametroEtichetta getParametroAttivaSessione() {

	return parametroAttivaSessione;
    }

    public void setParametroAttivaSessione(ParametroEtichetta parametroAttivaSessione) {

	this.parametroAttivaSessione = parametroAttivaSessione;
    }

    public ParametroEtichetta getParametroNotifica() {

	return parametroNotifica;
    }

    public void setParametroNotifica(ParametroEtichetta parametroNotifica) {

	this.parametroNotifica = parametroNotifica;
    }

    public ParametroEtichetta getParametroSecurity() {

	return parametroSecurity;
    }

    public void setParametroSecurity(ParametroEtichetta parametroSecurity) {

	this.parametroSecurity = parametroSecurity;
    }

    public ParametroEtichetta getParametroIuv() {

	return parametroIuv;
    }

    public void setParametroIuv(ParametroEtichetta parametroIuv) {

	this.parametroIuv = parametroIuv;
    }

    public ParametroEtichetta getParametroFattura() {

	return parametroFattura;
    }

    public void setParametroFattura(ParametroEtichetta parametroFattura) {

	this.parametroFattura = parametroFattura;
    }

    public static WsEndpointBean popolaWsEndpoint(PayConnectorConfig c) {

	WsEndpointBean wsEndpoint = new WsEndpointBean();
	boolean popolato = false;
	if (c.getWsAnnullamento() != null) {
	    ParametroEtichetta annullamento = new ParametroEtichetta(ANNULLAMENTO, c.getWsAnnullamento().getDescrizione());
	    wsEndpoint.setParametroAnnullamento(annullamento);
	    popolato = true;
	}
	if (c.getWsCaricamento() != null) {
	    ParametroEtichetta caricamento = new ParametroEtichetta(CARICAMENTO, c.getWsCaricamento().getDescrizione());
	    wsEndpoint.setParametroCaricamento(caricamento);
	    popolato = true;
	}
	if (c.getWsAvviso() != null) {
	    ParametroEtichetta avviso = new ParametroEtichetta(AVVISO, c.getWsAvviso().getDescrizione());
	    wsEndpoint.setParametroAvviso(avviso);
	    popolato = true;
	}
	if (c.getWsRicevuta() != null) {
	    ParametroEtichetta ricevuta = new ParametroEtichetta(RICEVUTA, c.getWsRicevuta().getDescrizione());
	    wsEndpoint.setParametroRicevuta(ricevuta);
	    popolato = true;
	}
	if (c.getWsVerifica() != null) {
	    ParametroEtichetta verifica = new ParametroEtichetta(VERIFICA, c.getWsVerifica().getDescrizione());
	    wsEndpoint.setParametroVerifica(verifica);
	    popolato = true;
	}
	if (c.getWsAttivaSessione() != null) {
	    ParametroEtichetta as = new ParametroEtichetta(ATTIVA_SESSIONE, c.getWsAttivaSessione().getDescrizione());
	    wsEndpoint.setParametroAttivaSessione(as);
	    popolato = true;
	}
	if (c.getWsNotifica() != null) {
	    ParametroEtichetta notifica = new ParametroEtichetta(NOTIFICA, c.getWsNotifica().getDescrizione());
	    wsEndpoint.setParametroNotifica(notifica);
	    popolato = true;
	}
	if (c.getWsSecurity() != null) {
	    ParametroEtichetta security = new ParametroEtichetta(SECURITY, c.getWsSecurity().getDescrizione());
	    wsEndpoint.setParametroSecurity(security);
	    popolato = true;
	}
	if (c.getWsIUV() != null) {
	    ParametroEtichetta iuv = new ParametroEtichetta(IUV, c.getWsIUV().getDescrizione());
	    wsEndpoint.setParametroIuv(iuv);
	    popolato = true;
	}
	if (c.getWsFattura() != null) {
	    ParametroEtichetta fattura = new ParametroEtichetta(FATTURA, c.getWsFattura().getDescrizione());
	    wsEndpoint.setParametroFattura(fattura);
	    popolato = true;
	}
	return popolato ? wsEndpoint : null;
    }
}
