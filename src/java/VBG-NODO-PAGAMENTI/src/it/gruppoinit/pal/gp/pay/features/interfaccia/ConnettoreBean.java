package it.gruppoinit.pal.gp.pay.features.interfaccia;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ConnettoreBean {

    @XmlElement(name = "classe")
    private String classe;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "campi_gestiti")
    private CampiGestitiBean campiGestiti;
    @XmlElement(name = "ws_endpoint")
    private WsEndpointBean wsEndpoint;
    @XmlElement(name = "parametri_connettore")
    private List<ParametriCDH> parametriConnettore;
    @XmlElement(name = "parametri_causale")
    private List<ParametriCDH> parametriCausale;

    public ConnettoreBean() {

    }

    public ConnettoreBean(String classe, String descrizione) {

	this.classe = classe;
	this.descrizione = descrizione;
    }

    public String getClasse() {

	return classe;
    }

    public void setClasse(String classe) {

	this.classe = classe;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public CampiGestitiBean getCampiGestiti() {

	return campiGestiti;
    }

    public void setCampiGestiti(CampiGestitiBean value) {

	this.campiGestiti = value;
    }

    public WsEndpointBean getWsEndpoint() {

	return wsEndpoint;
    }

    public void setWsEndpoint(WsEndpointBean value) {

	this.wsEndpoint = value;
    }

    public List<ParametriCDH> getParametriConnettore() {

	if (this.parametriConnettore == null) {
	    this.parametriConnettore = new ArrayList<>();
	}
	return parametriConnettore;
    }

    public void setParametriConnettore(List<ParametriCDH> parametriConnettore) {

	this.parametriConnettore = parametriConnettore;
    }

    public List<ParametriCDH> getParametriCausale() {

	if (this.parametriCausale == null) {
	    this.parametriCausale = new ArrayList<>();
	}
	return parametriCausale;
    }

    public void setParametriCausale(List<ParametriCDH> parametriCausale) {

	this.parametriCausale = parametriCausale;
    }
}
