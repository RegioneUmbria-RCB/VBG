package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.Soggetto;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.NuovaPendenza;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NuovoPagamento", propOrder = { "urlRitorno", "contoAddebito", "dataEsecuzionePagamento", "credenzialiPagatore", "soggettoVersante",
	"autenticazioneSoggetto", "pendenze", })
public class NuovoPagamento {

    @XmlElement(name = "urlRitorno")
    private String urlRitorno = null;
    @XmlElement(name = "contoAddebito")
    private Conto contoAddebito = null;
    @XmlElement(name = "dataEsecuzionePagamento")
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar dataEsecuzionePagamento = null;
    @XmlElement(name = "credenzialiPagatore")
    private String credenzialiPagatore = null;
    @XmlElement(name = "soggettoVersante")
    private Soggetto soggettoVersante = null;
    @XmlElement(name = "autenticazioneSoggetto")
    private TipoAutenticazioneSoggetto autenticazioneSoggetto = TipoAutenticazioneSoggetto.N_A;
    @XmlElements({ @XmlElement(name = "pendenze", type = NuovaPendenza.class), @XmlElement(name = "pendenze", type = RiferimentoPendenza.class), @XmlElement(name = "pendenze", type = RiferimentoAvviso.class) })
    private List<Object> pendenze = new ArrayList<>();

    /**
     * url di ritorno al portale al termine della sessione di pagamento
     **/
    public NuovoPagamento urlRitorno(String urlRitorno) {

	this.urlRitorno = urlRitorno;
	return this;
    }

    public String getUrlRitorno() {

	return urlRitorno;
    }

    public void setUrlRitorno(String urlRitorno) {

	this.urlRitorno = urlRitorno;
    }

    /**
     **/
    public NuovoPagamento contoAddebito(Conto contoAddebito) {

	this.contoAddebito = contoAddebito;
	return this;
    }

    public Conto getContoAddebito() {

	return contoAddebito;
    }

    public void setContoAddebito(Conto contoAddebito) {

	this.contoAddebito = contoAddebito;
    }

    /**
     * data in cui si richiede che venga effettuato il pagamento, se diversa dalla data corrente.
     **/
    public NuovoPagamento dataEsecuzionePagamento(XMLGregorianCalendar dataEsecuzionePagamento) {

	this.dataEsecuzionePagamento = dataEsecuzionePagamento;
	return this;
    }

    public XMLGregorianCalendar getDataEsecuzionePagamento() {

	return dataEsecuzionePagamento;
    }

    public void setDataEsecuzionePagamento(XMLGregorianCalendar dataEsecuzionePagamento) {

	this.dataEsecuzionePagamento = dataEsecuzionePagamento;
    }

    /**
     * Eventuali credenziali richieste dal PSP necessarie per completare l'operazione (ad esempio un codice bilaterale
     * utilizzabile una sola volta).
     **/
    public NuovoPagamento credenzialiPagatore(String credenzialiPagatore) {

	this.credenzialiPagatore = credenzialiPagatore;
	return this;
    }

    public String getCredenzialiPagatore() {

	return credenzialiPagatore;
    }

    public void setCredenzialiPagatore(String credenzialiPagatore) {

	this.credenzialiPagatore = credenzialiPagatore;
    }

    /**
     **/
    public NuovoPagamento soggettoVersante(Soggetto soggettoVersante) {

	this.soggettoVersante = soggettoVersante;
	return this;
    }

    public Soggetto getSoggettoVersante() {

	return soggettoVersante;
    }

    public void setSoggettoVersante(Soggetto soggettoVersante) {

	this.soggettoVersante = soggettoVersante;
    }

    /**
     **/
    public NuovoPagamento autenticazioneSoggetto(TipoAutenticazioneSoggetto autenticazioneSoggetto) {

	this.autenticazioneSoggetto = autenticazioneSoggetto;
	return this;
    }

    public String getAutenticazioneSoggetto() {

	if (this.autenticazioneSoggetto != null) {
	    return this.autenticazioneSoggetto.toString();
	} else {
	    return null;
	}
    }

    public void setAutenticazioneSoggetto(String autenticazioneSoggetto) throws RuntimeException {

	if (autenticazioneSoggetto != null) {
	    this.autenticazioneSoggetto = TipoAutenticazioneSoggetto.fromValue(autenticazioneSoggetto);
	    if (this.autenticazioneSoggetto == null)
		throw new RuntimeException("valore [" + autenticazioneSoggetto + "] non ammesso per la property autenticazioneSoggetto");
	}
    }

    /**
     * pendenze o riferimenti alle pendenze oggetto del pagamento
     **/
    public NuovoPagamento pendenze(List<Object> pendenze) {

	this.pendenze = pendenze;
	return this;
    }

    public List<Object> getPendenze() {

	return pendenze;
    }

    public void setPendenze(List<Object> pendenze) {

	this.pendenze = pendenze;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	NuovoPagamento nuovoPagamento = (NuovoPagamento) o;
	return Objects.equals(urlRitorno, nuovoPagamento.urlRitorno) && Objects.equals(contoAddebito, nuovoPagamento.contoAddebito)
		&& Objects.equals(dataEsecuzionePagamento, nuovoPagamento.dataEsecuzionePagamento)
		&& Objects.equals(credenzialiPagatore, nuovoPagamento.credenzialiPagatore)
		&& Objects.equals(soggettoVersante, nuovoPagamento.soggettoVersante)
		&& Objects.equals(autenticazioneSoggetto, nuovoPagamento.autenticazioneSoggetto) && Objects.equals(pendenze, nuovoPagamento.pendenze);
    }

    @Override
    public int hashCode() {

	return Objects.hash(urlRitorno, contoAddebito, dataEsecuzionePagamento, credenzialiPagatore, soggettoVersante, autenticazioneSoggetto,
		pendenze);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NuovoPagamento {\n");
	sb.append("    urlRitorno: ").append(toIndentedString(urlRitorno)).append("\n");
	sb.append("    contoAddebito: ").append(toIndentedString(contoAddebito)).append("\n");
	sb.append("    dataEsecuzionePagamento: ").append(toIndentedString(dataEsecuzionePagamento)).append("\n");
	sb.append("    credenzialiPagatore: ").append(toIndentedString(credenzialiPagatore)).append("\n");
	sb.append("    soggettoVersante: ").append(toIndentedString(soggettoVersante)).append("\n");
	sb.append("    autenticazioneSoggetto: ").append(toIndentedString(autenticazioneSoggetto)).append("\n");
	sb.append("    pendenze: ").append(toIndentedString(pendenze)).append("\n");
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
