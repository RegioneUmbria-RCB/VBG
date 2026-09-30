package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.RppIndex;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.Soggetto;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.PendenzaIndex;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pagamento", propOrder = { "urlRitorno", "contoAddebito", "dataEsecuzionePagamento", "credenzialiPagatore", "soggettoVersante",
	"autenticazioneSoggetto", "id", "nome", "stato", "importo", "idSessionePortale", "idSessionePsp", "pspRedirectUrl", "dataRichiestaPagamento",
	"rpp", "pendenze", })
public class Pagamento {

    @XmlElement(name = "urlRitorno")
    private String urlRitorno = null;
    @XmlElement(name = "contoAddebito")
    private Conto contoAddebito = null;
    @XmlElement(name = "dataEsecuzionePagamento")
    private XMLGregorianCalendar dataEsecuzionePagamento = null;
    @XmlElement(name = "credenzialiPagatore")
    private String credenzialiPagatore = null;
    @XmlElement(name = "soggettoVersante")
    private Soggetto soggettoVersante = null;
    @XmlElement(name = "autenticazioneSoggetto")
    private TipoAutenticazioneSoggetto autenticazioneSoggetto = null;
    @XmlElement(name = "id")
    private String id = null;
    @XmlElement(name = "nome")
    private String nome = null;
    @XmlElement(name = "stato")
    private StatoPagamento stato = null;
    @XmlElement(name = "importo")
    private BigDecimal importo = null;
    @XmlElement(name = "idSessionePortale")
    private String idSessionePortale = null;
    @XmlElement(name = "idSessionePsp")
    private String idSessionePsp = null;
    @XmlElement(name = "pspRedirectUrl")
    private String pspRedirectUrl = null;
    @XmlElement(name = "dataRichiestaPagamento")
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar dataRichiestaPagamento = null;
    @XmlElement(name = "rpp")
    private List<RppIndex> rpp = null;
    @XmlElement(name = "pendenze")
    private List<PendenzaIndex> pendenze = new ArrayList<>();

    /**
     * url di ritorno al portale al termine della sessione di pagamento
     **/
    public Pagamento urlRitorno(String urlRitorno) {

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
    public Pagamento contoAddebito(Conto contoAddebito) {

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
    public Pagamento dataEsecuzionePagamento(XMLGregorianCalendar dataEsecuzionePagamento) {

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
    public Pagamento credenzialiPagatore(String credenzialiPagatore) {

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
    public Pagamento soggettoVersante(Soggetto soggettoVersante) {

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
    public Pagamento autenticazioneSoggetto(TipoAutenticazioneSoggetto autenticazioneSoggetto) {

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
     * Identificativo del pagamento assegnato da GovPay
     **/
    public Pagamento id(String id) {

	this.id = id;
	return this;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    /**
     * Identificativo del pagamento assegnato da GovPay
     **/
    public Pagamento nome(String nome) {

	this.nome = nome;
	return this;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    /**
     **/
    public Pagamento stato(StatoPagamento stato) {

	this.stato = stato;
	return this;
    }

    public StatoPagamento getStato() {

	return stato;
    }

    public void setStato(StatoPagamento stato) {

	this.stato = stato;
    }

    /**
     * Importo del pagamento. Corrisponde alla somma degli importi delle pendenze al momento della richiesta
     **/
    public Pagamento importo(BigDecimal importo) {

	this.importo = importo;
	return this;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    /**
     * Identificativo della sessione di pagamento assegnato dall'EC
     **/
    public Pagamento idSessionePortale(String idSessionePortale) {

	this.idSessionePortale = idSessionePortale;
	return this;
    }

    public String getIdSessionePortale() {

	return idSessionePortale;
    }

    public void setIdSessionePortale(String idSessionePortale) {

	this.idSessionePortale = idSessionePortale;
    }

    /**
     * Identificativo del pagamento assegnato dal psp utilizzato
     **/
    public Pagamento idSessionePsp(String idSessionePsp) {

	this.idSessionePsp = idSessionePsp;
	return this;
    }

    public String getIdSessionePsp() {

	return idSessionePsp;
    }

    public void setIdSessionePsp(String idSessionePsp) {

	this.idSessionePsp = idSessionePsp;
    }

    /**
     * Url di redirect al psp inviata al versante per perfezionare il pagamento, se previsto dal modello
     **/
    public Pagamento pspRedirectUrl(String pspRedirectUrl) {

	this.pspRedirectUrl = pspRedirectUrl;
	return this;
    }

    public String getPspRedirectUrl() {

	return pspRedirectUrl;
    }

    public void setPspRedirectUrl(String pspRedirectUrl) {

	this.pspRedirectUrl = pspRedirectUrl;
    }

    /**
     * Data in cui e' stato inserito il pagamento.
     **/
    public Pagamento dataRichiestaPagamento(XMLGregorianCalendar dataRichiestaPagamento) {

	this.dataRichiestaPagamento = dataRichiestaPagamento;
	return this;
    }

    public XMLGregorianCalendar getDataRichiestaPagamento() {

	return dataRichiestaPagamento;
    }

    public void setDataRichiestaPagamento(XMLGregorianCalendar dataRichiestaPagamento) {

	this.dataRichiestaPagamento = dataRichiestaPagamento;
    }

    /**
     **/
    public Pagamento rpp(List<RppIndex> rpp) {

	this.rpp = rpp;
	return this;
    }

    public List<RppIndex> getRpp() {

	return rpp;
    }

    public void setRpp(List<RppIndex> rpp) {

	this.rpp = rpp;
    }

    /**
     **/
    public Pagamento pendenze(List<PendenzaIndex> pendenze) {

	this.pendenze = pendenze;
	return this;
    }

    public List<PendenzaIndex> getPendenze() {

	return pendenze;
    }

    public void setPendenze(List<PendenzaIndex> pendenze) {

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
	Pagamento pagamento = (Pagamento) o;
	return Objects.equals(urlRitorno, pagamento.urlRitorno) && Objects.equals(contoAddebito, pagamento.contoAddebito)
		&& Objects.equals(dataEsecuzionePagamento, pagamento.dataEsecuzionePagamento)
		&& Objects.equals(credenzialiPagatore, pagamento.credenzialiPagatore) && Objects.equals(soggettoVersante, pagamento.soggettoVersante)
		&& Objects.equals(autenticazioneSoggetto, pagamento.autenticazioneSoggetto) && Objects.equals(id, pagamento.id)
		&& Objects.equals(nome, pagamento.nome) && Objects.equals(stato, pagamento.stato) && Objects.equals(importo, pagamento.importo)
		&& Objects.equals(idSessionePortale, pagamento.idSessionePortale) && Objects.equals(idSessionePsp, pagamento.idSessionePsp)
		&& Objects.equals(pspRedirectUrl, pagamento.pspRedirectUrl)
		&& Objects.equals(dataRichiestaPagamento, pagamento.dataRichiestaPagamento) && Objects.equals(rpp, pagamento.rpp)
		&& Objects.equals(pendenze, pagamento.pendenze);
    }

    @Override
    public int hashCode() {

	return Objects.hash(urlRitorno, contoAddebito, dataEsecuzionePagamento, credenzialiPagatore, soggettoVersante, autenticazioneSoggetto, id,
		nome, stato, importo, idSessionePortale, idSessionePsp, pspRedirectUrl, dataRichiestaPagamento, rpp, pendenze);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class Pagamento {\n");
	sb.append("    ").append(toIndentedString(super.toString())).append("\n");
	sb.append("    urlRitorno: ").append(toIndentedString(urlRitorno)).append("\n");
	sb.append("    contoAddebito: ").append(toIndentedString(contoAddebito)).append("\n");
	sb.append("    dataEsecuzionePagamento: ").append(toIndentedString(dataEsecuzionePagamento)).append("\n");
	sb.append("    credenzialiPagatore: ").append(toIndentedString(credenzialiPagatore)).append("\n");
	sb.append("    soggettoVersante: ").append(toIndentedString(soggettoVersante)).append("\n");
	sb.append("    autenticazioneSoggetto: ").append(toIndentedString(autenticazioneSoggetto)).append("\n");
	sb.append("    id: ").append(toIndentedString(id)).append("\n");
	sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
	sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
	sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
	sb.append("    idSessionePortale: ").append(toIndentedString(idSessionePortale)).append("\n");
	sb.append("    idSessionePsp: ").append(toIndentedString(idSessionePsp)).append("\n");
	sb.append("    pspRedirectUrl: ").append(toIndentedString(pspRedirectUrl)).append("\n");
	sb.append("    dataRichiestaPagamento: ").append(toIndentedString(dataRichiestaPagamento)).append("\n");
	sb.append("    rpp: ").append(toIndentedString(rpp)).append("\n");
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
