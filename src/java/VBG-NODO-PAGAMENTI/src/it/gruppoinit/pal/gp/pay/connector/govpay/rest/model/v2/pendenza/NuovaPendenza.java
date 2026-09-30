package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.Soggetto;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NuovaPendenza", propOrder = { "idA2A", "idPendenza", "idTipoPendenza", "idDominio", "idUnitaOperativa", "causale",
	"soggettoPagatore", "importo", "numeroAvviso", "tassonomia", "tassonomiaAvviso", "tassonomiaAvvisoEnum", "direzione", "divisione",
	"dataValidita", "dataScadenza", "annoRiferimento", "cartellaPagamento", "datiAllegati", "documento", "dataNotificaAvviso",
	"dataPromemoriaScadenza", "voci", })
public class NuovaPendenza {

    @XmlElement(name = "idA2A")
    private String idA2A = null;
    @XmlElement(name = "idPendenza")
    private String idPendenza = null;
    @XmlElement(name = "idTipoPendenza")
    private String idTipoPendenza = null;
    @XmlElement(name = "idDominio")
    private String idDominio = null;
    @XmlElement(name = "idUnitaOperativa")
    private String idUnitaOperativa = null;
    @XmlElement(name = "causale")
    private String causale = null;
    @XmlElement(name = "soggettoPagatore")
    private Soggetto soggettoPagatore = null;
    @XmlElement(name = "importo")
    private BigDecimal importo = null;
    @XmlElement(name = "numeroAvviso")
    private String numeroAvviso = null;
    @XmlElement(name = "tassonomia")
    private String tassonomia = null;
    @XmlElement(name = "tassonomiaAvvisoEnum")
    private TassonomiaAvviso tassonomiaAvvisoEnum = null;
    @XmlElement(name = "tassonomiaAvviso")
    private String tassonomiaAvviso = null;
    @XmlElement(name = "direzione")
    private String direzione = null;
    @XmlElement(name = "divisione")
    private String divisione = null;
    @XmlElement(name = "dataValidita")
    private String dataValidita = null;
    @XmlElement(name = "dataScadenza")
    private String dataScadenza = null;
    @XmlElement(name = "annoRiferimento")
    private BigDecimal annoRiferimento = null;
    @XmlElement(name = "cartellaPagamento")
    private String cartellaPagamento = null;
    @XmlElement(name = "datiAllegati")
    private Object datiAllegati = null;
    @XmlElement(name = "documento")
    private NuovoDocumento documento = null;
    @XmlElement(name = "dataNotificaAvviso")
    private String dataNotificaAvviso = null;
    @XmlElement(name = "dataPromemoriaScadenza")
    private String dataPromemoriaScadenza = null;
    @XmlElement(name = "voci")
    private List<NuovaVocePendenza> voci = new ArrayList<>();

    /**
     * Identificativo della tipologia pendenza
     **/
    public NuovaPendenza idTipoPendenza(String idTipoPendenza) {

	this.idTipoPendenza = idTipoPendenza;
	return this;
    }

    public String getIdTipoPendenza() {

	return idTipoPendenza;
    }

    public void setIdTipoPendenza(String idTipoPendenza) {

	this.idTipoPendenza = idTipoPendenza;
    }

    /**
     * Identificativo del dominio creditore
     **/
    public NuovaPendenza idDominio(String idDominio) {

	this.idDominio = idDominio;
	return this;
    }

    public String getIdDominio() {

	return idDominio;
    }

    public void setIdDominio(String idDominio) {

	this.idDominio = idDominio;
    }

    /**
     * Identificativo dell'unita' operativa
     **/
    public NuovaPendenza idUnitaOperativa(String idUnitaOperativa) {

	this.idUnitaOperativa = idUnitaOperativa;
	return this;
    }

    public String getIdUnitaOperativa() {

	return idUnitaOperativa;
    }

    public void setIdUnitaOperativa(String idUnitaOperativa) {

	this.idUnitaOperativa = idUnitaOperativa;
    }

    /**
     * Descrizione della pendenza
     **/
    public NuovaPendenza causale(String causale) {

	this.causale = causale;
	return this;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    /**
     **/
    public NuovaPendenza soggettoPagatore(Soggetto soggettoPagatore) {

	this.soggettoPagatore = soggettoPagatore;
	return this;
    }

    public Soggetto getSoggettoPagatore() {

	return soggettoPagatore;
    }

    public void setSoggettoPagatore(Soggetto soggettoPagatore) {

	this.soggettoPagatore = soggettoPagatore;
    }

    /**
     * Importo della pendenza. Deve corrispondere alla somma delle singole voci.
     **/
    public NuovaPendenza importo(BigDecimal importo) {

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
     * Identificativo univoco versamento, assegnato se pagabile da psp
     **/
    public NuovaPendenza numeroAvviso(String numeroAvviso) {

	this.numeroAvviso = numeroAvviso;
	return this;
    }

    public String getNumeroAvviso() {

	return numeroAvviso;
    }

    public void setNumeroAvviso(String numeroAvviso) {

	this.numeroAvviso = numeroAvviso;
    }

    /**
     * Macro categoria della pendenza secondo la classificazione del creditore
     **/
    public NuovaPendenza tassonomia(String tassonomia) {

	this.tassonomia = tassonomia;
	return this;
    }

    public String getTassonomia() {

	return tassonomia;
    }

    public void setTassonomia(String tassonomia) {

	this.tassonomia = tassonomia;
    }

    /**
     **/
    public NuovaPendenza tassonomiaAvvisoEnum(TassonomiaAvviso tassonomiaAvviso) {

	this.tassonomiaAvvisoEnum = tassonomiaAvviso;
	return this;
    }

    public TassonomiaAvviso getTassonomiaAvvisoEnum() {

	return this.tassonomiaAvvisoEnum;
    }

    public void setTassonomiaAvvisoEnum(TassonomiaAvviso tassonomiaAvviso) {

	this.tassonomiaAvvisoEnum = tassonomiaAvviso;
    }

    /**
     **/
    public NuovaPendenza tassonomiaAvviso(String tassonomiaAvviso) {

	this.setTassonomiaAvviso(tassonomiaAvviso);
	return this;
    }

    public String getTassonomiaAvviso() {

	return this.tassonomiaAvviso;
    }

    public void setTassonomiaAvviso(String tassonomiaAvviso) {

	this.tassonomiaAvviso = tassonomiaAvviso;
    }

    /**
     * Identificativo della direzione interna all'ente creditore
     **/
    public NuovaPendenza direzione(String direzione) {

	this.direzione = direzione;
	return this;
    }

    public String getDirezione() {

	return direzione;
    }

    public void setDirezione(String direzione) {

	this.direzione = direzione;
    }

    /**
     * Identificativo della divisione interna all'ente creditore
     **/
    public NuovaPendenza divisione(String divisione) {

	this.divisione = divisione;
	return this;
    }

    public String getDivisione() {

	return divisione;
    }

    public void setDivisione(String divisione) {

	this.divisione = divisione;
    }

    /**
     * Data di validita dei dati della pendenza, decorsa la quale la pendenza può subire variazioni.
     **/
    public NuovaPendenza dataValidita(String dataValidita) {

	this.dataValidita = dataValidita;
	return this;
    }

    public String getDataValidita() {

	return dataValidita;
    }

    public void setDataValidita(String dataValidita) {

	this.dataValidita = dataValidita;
    }

    /**
     * Data di scadenza della pendenza, decorsa la quale non è più pagabile.
     **/
    public NuovaPendenza dataScadenza(String dataScadenza) {

	this.dataScadenza = dataScadenza;
	return this;
    }

    public String getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    /**
     * Anno di riferimento della pendenza
     **/
    public NuovaPendenza annoRiferimento(BigDecimal annoRiferimento) {

	this.annoRiferimento = annoRiferimento;
	return this;
    }

    public BigDecimal getAnnoRiferimento() {

	return annoRiferimento;
    }

    public void setAnnoRiferimento(BigDecimal annoRiferimento) {

	this.annoRiferimento = annoRiferimento;
    }

    /**
     * Identificativo della cartella di pagamento a cui afferisce la pendenza
     **/
    public NuovaPendenza cartellaPagamento(String cartellaPagamento) {

	this.cartellaPagamento = cartellaPagamento;
	return this;
    }

    public String getCartellaPagamento() {

	return cartellaPagamento;
    }

    public void setCartellaPagamento(String cartellaPagamento) {

	this.cartellaPagamento = cartellaPagamento;
    }

    /**
     * Dati applicativi allegati dal gestionale secondo un formato proprietario.
     **/
    public NuovaPendenza datiAllegati(Object datiAllegati) {

	this.datiAllegati = datiAllegati;
	return this;
    }

    public Object getDatiAllegati() {

	return datiAllegati;
    }

    public void setDatiAllegati(Object datiAllegati) {

	this.datiAllegati = datiAllegati;
    }

    /**
     **/
    public NuovaPendenza documento(NuovoDocumento documento) {

	this.documento = documento;
	return this;
    }

    public NuovoDocumento getDocumento() {

	return documento;
    }

    public void setDocumento(NuovoDocumento documento) {

	this.documento = documento;
    }

    /**
     * Data in cui inviare il promemoria di pagamento.
     **/
    public NuovaPendenza dataNotificaAvviso(String dataNotificaAvviso) {

	this.dataNotificaAvviso = dataNotificaAvviso;
	return this;
    }

    public String getDataNotificaAvviso() {

	return dataNotificaAvviso;
    }

    public void setDataNotificaAvviso(String dataNotificaAvviso) {

	this.dataNotificaAvviso = dataNotificaAvviso;
    }

    /**
     * Data in cui inviare il promemoria di scadenza della pendenza.
     **/
    public NuovaPendenza dataPromemoriaScadenza(String dataPromemoriaScadenza) {

	this.dataPromemoriaScadenza = dataPromemoriaScadenza;
	return this;
    }

    public String getDataPromemoriaScadenza() {

	return dataPromemoriaScadenza;
    }

    public void setDataPromemoriaScadenza(String dataPromemoriaScadenza) {

	this.dataPromemoriaScadenza = dataPromemoriaScadenza;
    }

    public String getIdA2A() {

	return idA2A;
    }

    public void setIdA2A(String idA2A) {

	this.idA2A = idA2A;
    }

    /**
     **/
    public NuovaPendenza voci(List<NuovaVocePendenza> voci) {

	this.voci = voci;
	return this;
    }

    public List<NuovaVocePendenza> getVoci() {

	return voci;
    }

    public void setVoci(List<NuovaVocePendenza> voci) {

	this.voci = voci;
    }

    public String getIdPendenza() {

	return idPendenza;
    }

    public void setIdPendenza(String idPendenza) {

	this.idPendenza = idPendenza;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	NuovaPendenza nuovaPendenza = (NuovaPendenza) o;
	return Objects.equals(idTipoPendenza, nuovaPendenza.idTipoPendenza) && Objects.equals(idDominio, nuovaPendenza.idDominio)
		&& Objects.equals(idUnitaOperativa, nuovaPendenza.idUnitaOperativa) && Objects.equals(causale, nuovaPendenza.causale)
		&& Objects.equals(soggettoPagatore, nuovaPendenza.soggettoPagatore) && Objects.equals(importo, nuovaPendenza.importo)
		&& Objects.equals(numeroAvviso, nuovaPendenza.numeroAvviso) && Objects.equals(tassonomia, nuovaPendenza.tassonomia)
		&& Objects.equals(tassonomiaAvviso, nuovaPendenza.tassonomiaAvviso) && Objects.equals(direzione, nuovaPendenza.direzione)
		&& Objects.equals(divisione, nuovaPendenza.divisione) && Objects.equals(dataValidita, nuovaPendenza.dataValidita)
		&& Objects.equals(dataScadenza, nuovaPendenza.dataScadenza) && Objects.equals(annoRiferimento, nuovaPendenza.annoRiferimento)
		&& Objects.equals(cartellaPagamento, nuovaPendenza.cartellaPagamento) && Objects.equals(datiAllegati, nuovaPendenza.datiAllegati)
		&& Objects.equals(documento, nuovaPendenza.documento) && Objects.equals(dataNotificaAvviso, nuovaPendenza.dataNotificaAvviso)
		&& Objects.equals(dataPromemoriaScadenza, nuovaPendenza.dataPromemoriaScadenza) && Objects.equals(voci, nuovaPendenza.voci);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idTipoPendenza, idDominio, idUnitaOperativa, causale, soggettoPagatore, importo, numeroAvviso, tassonomia,
		tassonomiaAvviso, direzione, divisione, dataValidita, dataScadenza, annoRiferimento, cartellaPagamento, datiAllegati, documento,
		dataNotificaAvviso, dataPromemoriaScadenza, voci);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NuovaPendenza {\n");
	sb.append("    idTipoPendenza: ").append(toIndentedString(idTipoPendenza)).append("\n");
	sb.append("    idDominio: ").append(toIndentedString(idDominio)).append("\n");
	sb.append("    idUnitaOperativa: ").append(toIndentedString(idUnitaOperativa)).append("\n");
	sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
	sb.append("    soggettoPagatore: ").append(toIndentedString(soggettoPagatore)).append("\n");
	sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
	sb.append("    numeroAvviso: ").append(toIndentedString(numeroAvviso)).append("\n");
	sb.append("    tassonomia: ").append(toIndentedString(tassonomia)).append("\n");
	sb.append("    tassonomiaAvviso: ").append(toIndentedString(tassonomiaAvviso)).append("\n");
	sb.append("    direzione: ").append(toIndentedString(direzione)).append("\n");
	sb.append("    divisione: ").append(toIndentedString(divisione)).append("\n");
	sb.append("    dataValidita: ").append(toIndentedString(dataValidita)).append("\n");
	sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
	sb.append("    annoRiferimento: ").append(toIndentedString(annoRiferimento)).append("\n");
	sb.append("    cartellaPagamento: ").append(toIndentedString(cartellaPagamento)).append("\n");
	sb.append("    datiAllegati: ").append(toIndentedString(datiAllegati)).append("\n");
	sb.append("    documento: ").append(toIndentedString(documento)).append("\n");
	sb.append("    dataNotificaAvviso: ").append(toIndentedString(dataNotificaAvviso)).append("\n");
	sb.append("    dataPromemoriaScadenza: ").append(toIndentedString(dataPromemoriaScadenza)).append("\n");
	sb.append("    voci: ").append(toIndentedString(voci)).append("\n");
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
