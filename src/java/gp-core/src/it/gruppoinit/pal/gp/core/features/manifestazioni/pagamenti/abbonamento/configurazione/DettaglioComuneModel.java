package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dettaglio")
@XmlAccessorType(XmlAccessType.FIELD)
public class DettaglioComuneModel {

    @XmlElement(name = "codice")
    private String codice;
    @XmlElement(name = "comune")
    private String comune;
    @XmlElement(name = "messaggio")
    private String msgRicaricaNonDisponibile;
    @XmlElement(name = "informative")
    private TreeSet<DettaglioInformativaModel> informative = new TreeSet<DettaglioInformativaModel>(new DettaglioInformativaModelComparator());
    @XmlElement(name = "ricariche")
    private TreeSet<DettaglioRicaricaModel> ricariche = new TreeSet<DettaglioRicaricaModel>(new DettaglioRicaricaModelComparator());

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getMsgRicaricaNonDisponibile() {

	return msgRicaricaNonDisponibile;
    }

    public void setMsgRicaricaNonDisponibile(String msgRicaricaNonDisponibile) {

	this.msgRicaricaNonDisponibile = msgRicaricaNonDisponibile;
    }

    public TreeSet<DettaglioInformativaModel> getInformative() {

	return informative;
    }

    public void setInformative(TreeSet<DettaglioInformativaModel> informative) {

	this.informative = informative;
    }

    public TreeSet<DettaglioRicaricaModel> getRicariche() {

	return ricariche;
    }

    public void setRicariche(TreeSet<DettaglioRicaricaModel> ricariche) {

	this.ricariche = ricariche;
    }
}
