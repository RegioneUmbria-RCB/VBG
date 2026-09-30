package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

import java.util.ArrayList;
import java.util.List;

public class DatiRispostaREA {

    public static DatiRispostaREA fromRispostaREA(RispostaREA rispostaREA) {

	DatiRispostaREA ret = new DatiRispostaREA();
	if (!rispostaREA.getAllegati().isEmpty()) {
	    ret.getAllegati().addAll(rispostaREA.getAllegati());
	}
	ret.setDettaglioEsitoRichiesta(rispostaREA.getDettaglioEsitoRichiesta());
	ret.setEsitoRichiesta(rispostaREA.getEsitoRichiesta());
	ret.setProtocollo(DatiRispostaProtocollo.fromREAProtocollo(rispostaREA.getProtocollo()));
	return ret;
    }

    public static DatiRispostaREA fromRispostaREAStd(RispostaREAStd rispostaREA) {

	DatiRispostaREA ret = new DatiRispostaREA();
	if (!rispostaREA.getAllegati().isEmpty()) {
	    ret.getAllegati().addAll(rispostaREA.getAllegati());
	}
	ret.setDettaglioEsitoRichiesta(rispostaREA.getDettaglioEsitoRichiesta());
	ret.setEsitoRichiesta(rispostaREA.getEsitoRichiesta());
	ret.setProtocollo(DatiRispostaProtocollo.fromREAStdProtocollo(rispostaREA.getProtocollo()));
	return ret;
    }

    protected List<AllegatoSUAP> allegati;
    protected String dettaglioEsitoRichiesta;
    protected int esitoRichiesta;
    protected DatiRispostaProtocollo protocollo;

    /**
     * Gets the value of the allegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the allegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link AllegatoSUAP }
     * 
     * 
     */
    public List<AllegatoSUAP> getAllegati() {

	if (allegati == null) {
	    allegati = new ArrayList<AllegatoSUAP>();
	}
	return this.allegati;
    }

    /**
     * Gets the value of the dettaglioEsitoRichiesta property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDettaglioEsitoRichiesta() {

	return dettaglioEsitoRichiesta;
    }

    /**
     * Sets the value of the dettaglioEsitoRichiesta property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDettaglioEsitoRichiesta(String value) {

	this.dettaglioEsitoRichiesta = value;
    }

    /**
     * Gets the value of the esitoRichiesta property.
     * 
     */
    public int getEsitoRichiesta() {

	return esitoRichiesta;
    }

    /**
     * Sets the value of the esitoRichiesta property.
     * 
     */
    public void setEsitoRichiesta(int value) {

	this.esitoRichiesta = value;
    }

    public DatiRispostaProtocollo getProtocollo() {

	return protocollo;
    }

    public void setProtocollo(DatiRispostaProtocollo protocollo) {

	this.protocollo = protocollo;
    }
}
