package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRicevutaTelematica;
import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRichiestaPagamentoTelematico;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.PendenzaIndex;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.Segnalazione;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RppIndex", propOrder = { "stato", "dettaglioStato", "segnalazioni", "rpt", "rt", "pendenza", })
@XmlRootElement(name = "rpp")
public class RppIndex {

    @XmlElement(name = "stato")
    private String stato = null;
    @XmlElement(name = "dettaglioStato")
    private String dettaglioStato = null;
    @XmlElement(name = "segnalazioni")
    private List<Segnalazione> segnalazioni = null;
    @XmlElement(name = "rpt")
    private CtRichiestaPagamentoTelematico rpt = null;
    @XmlElement(name = "rt")
    private CtRicevutaTelematica rt = null;
    @XmlElement(name = "pendenza")
    private PendenzaIndex pendenza = null;

    /**
     * Stato della richiesta di pagamento sulla piattaforma PagoPA.
     **/
    public RppIndex stato(String stato) {

	this.stato = stato;
	return this;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    /**
     * Dettaglio fornito dal Nodo dei Pagamenti sullo stato della richiesta.
     **/
    public RppIndex dettaglioStato(String dettaglioStato) {

	this.dettaglioStato = dettaglioStato;
	return this;
    }

    public String getDettaglioStato() {

	return dettaglioStato;
    }

    public void setDettaglioStato(String dettaglioStato) {

	this.dettaglioStato = dettaglioStato;
    }

    /**
     **/
    public RppIndex segnalazioni(List<Segnalazione> segnalazioni) {

	this.segnalazioni = segnalazioni;
	return this;
    }

    public List<Segnalazione> getSegnalazioni() {

	return segnalazioni;
    }

    public void setSegnalazioni(List<Segnalazione> segnalazioni) {

	this.segnalazioni = segnalazioni;
    }

    /**
     * Rpt inviata a PagoPa. {http://www.digitpa.gov.it/schemas/2011/Pagamenti/} ctRichiestaPagamentoTelematico
     **/
    public RppIndex rpt(CtRichiestaPagamentoTelematico rpt) {

	this.rpt = rpt;
	return this;
    }

    public Object getRpt() {

	return rpt;
    }

    public void setRpt(CtRichiestaPagamentoTelematico rpt) {

	this.rpt = rpt;
    }

    /**
     * Rt inviata da PagoPa. {http://www.digitpa.gov.it/schemas/2011/Pagamenti/} ctRicevutaTelematica
     **/
    public RppIndex rt(CtRicevutaTelematica rt) {

	this.rt = rt;
	return this;
    }

    public Object getRt() {

	return rt;
    }

    public void setRt(CtRicevutaTelematica rt) {

	this.rt = rt;
    }

    /**
     **/
    public RppIndex pendenza(PendenzaIndex pendenza) {

	this.pendenza = pendenza;
	return this;
    }

    public PendenzaIndex getPendenza() {

	return pendenza;
    }

    public void setPendenza(PendenzaIndex pendenza) {

	this.pendenza = pendenza;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	RppIndex rppIndex = (RppIndex) o;
	return Objects.equals(stato, rppIndex.stato) && Objects.equals(dettaglioStato, rppIndex.dettaglioStato)
		&& Objects.equals(segnalazioni, rppIndex.segnalazioni) && Objects.equals(rpt, rppIndex.rpt) && Objects.equals(rt, rppIndex.rt)
		&& Objects.equals(pendenza, rppIndex.pendenza);
    }

    @Override
    public int hashCode() {

	return Objects.hash(stato, dettaglioStato, segnalazioni, rpt, rt, pendenza);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class RppIndex {\n");
	sb.append("    ").append(toIndentedString(super.toString())).append("\n");
	sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
	sb.append("    dettaglioStato: ").append(toIndentedString(dettaglioStato)).append("\n");
	sb.append("    segnalazioni: ").append(toIndentedString(segnalazioni)).append("\n");
	sb.append("    rpt: ").append(toIndentedString(rpt)).append("\n");
	sb.append("    rt: ").append(toIndentedString(rt)).append("\n");
	sb.append("    pendenza: ").append(toIndentedString(pendenza)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
