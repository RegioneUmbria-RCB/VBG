package it.gruppoinit.pal.gp.pay.features.interfaccia;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;

public class ParametriCDH {

    @XmlElement(name = "chiave")
    private String chiave;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "help")
    private String help;

    public ParametriCDH() {

    }

    public ParametriCDH(String chiave, String descrizione, String help) {

	super();
	this.chiave = chiave;
	this.descrizione = descrizione;
	this.help = help;
    }

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getHelp() {

	return help;
    }

    public void setHelp(String help) {

	this.help = help;
    }

    public static List<ParametriCDH> fromPayConnectorConfigValuesHelper(List<PayConnectorConfigValuesHelper> pccv) {

	List<ParametriCDH> ret = new ArrayList<>();
	for (PayConnectorConfigValuesHelper h : pccv) {
	    ParametriCDH pcb = new ParametriCDH();
	    pcb.setChiave(h.getConfigPara());
	    pcb.setDescrizione(h.getDescrizione());
	    pcb.setHelp("");
	    ret.add(pcb);
	}
	return ret;
    }

    public static List<ParametriCDH> fromPayRegistrazioniCausali(List<PayRegcausaliParametri> regCausaliParams) {

	List<ParametriCDH> ret = new ArrayList<>();
	for (PayRegcausaliParametri regParam : regCausaliParams) {
	    ParametriCDH cdh = new ParametriCDH(regParam.getChiave(), regParam.getChiave(), "");
	    ret.add(cdh);
	}
	return ret;
    }
}
