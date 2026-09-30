package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.math.BigDecimal;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NuovaVocePendenza", propOrder = { "idVocePendenza", "importo", "descrizione", "datiAllegati", "descrizioneCausaleRPT", "codEntrata",
	"ibanAccredito", "ibanAppoggio", "tipoContabilita", "codiceContabilita", "tipoBollo", "hashDocumento", "provinciaResidenza", })
public class NuovaVocePendenza {

    @XmlElement(name = "idVocePendenza")
    private String idVocePendenza = null;
    @XmlElement(name = "importo")
    private BigDecimal importo = null;
    @XmlElement(name = "descrizione")
    private String descrizione = null;
    @XmlElement(name = "datiAllegati")
    private Object datiAllegati = null;
    @XmlElement(name = "descrizioneCausaleRPT")
    private String descrizioneCausaleRPT = null;
    @XmlElement(name = "tipoBollo")
    private TipoBolloEnum tipoBollo = null;
    @XmlElement(name = "hashDocumento")
    private String hashDocumento = null;
    @XmlElement(name = "provinciaResidenza")
    private String provinciaResidenza = null;
    @XmlElement(name = "ibanAccredito")
    private String ibanAccredito = null;
    @XmlElement(name = "ibanAppoggio")
    private String ibanAppoggio = null;
    @XmlElement(name = "tipoContabilita")
    private TipoContabilita tipoContabilita = null;
    @XmlElement(name = "codiceContabilita")
    private String codiceContabilita = null;
    @XmlElement(name = "codEntrata")
    private String codEntrata = null;

    /**
     * Identificativo della voce di pedenza nel gestionale proprietario
     **/
    public NuovaVocePendenza idVocePendenza(String idVocePendenza) {

	this.idVocePendenza = idVocePendenza;
	return this;
    }

    public String getIdVocePendenza() {

	return idVocePendenza;
    }

    public void setIdVocePendenza(String idVocePendenza) {

	this.idVocePendenza = idVocePendenza;
    }

    /**
     * Importo della voce
     **/
    public NuovaVocePendenza importo(BigDecimal importo) {

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
     * descrizione della voce di pagamento
     **/
    public NuovaVocePendenza descrizione(String descrizione) {

	this.descrizione = descrizione;
	return this;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    /**
     * Dati applicativi allegati dal gestionale secondo un formato proprietario.
     **/
    public NuovaVocePendenza datiAllegati(Object datiAllegati) {

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
     * Testo libero per la causale versamento
     **/
    public NuovaVocePendenza descrizioneCausaleRPT(String descrizioneCausaleRPT) {

	this.descrizioneCausaleRPT = descrizioneCausaleRPT;
	return this;
    }

    public String getDescrizioneCausaleRPT() {

	return descrizioneCausaleRPT;
    }

    public void setDescrizioneCausaleRPT(String descrizioneCausaleRPT) {

	this.descrizioneCausaleRPT = descrizioneCausaleRPT;
    }

    /**
     **/
    public NuovaVocePendenza codEntrata(String codEntrata) {

	this.codEntrata = codEntrata;
	return this;
    }

    public String getCodEntrata() {

	return codEntrata;
    }

    public void setCodEntrata(String codEntrata) {

	this.codEntrata = codEntrata;
    }

    /**
     **/
    public NuovaVocePendenza ibanAccredito(String ibanAccredito) {

	this.ibanAccredito = ibanAccredito;
	return this;
    }

    public String getIbanAccredito() {

	return ibanAccredito;
    }

    public void setIbanAccredito(String ibanAccredito) {

	this.ibanAccredito = ibanAccredito;
    }

    /**
     **/
    public NuovaVocePendenza ibanAppoggio(String ibanAppoggio) {

	this.ibanAppoggio = ibanAppoggio;
	return this;
    }

    public String getIbanAppoggio() {

	return ibanAppoggio;
    }

    public void setIbanAppoggio(String ibanAppoggio) {

	this.ibanAppoggio = ibanAppoggio;
    }

    /**
     **/
    public NuovaVocePendenza tipoContabilita(TipoContabilita tipoContabilita) {

	this.tipoContabilita = tipoContabilita;
	return this;
    }

    public TipoContabilita getTipoContabilita() {

	return tipoContabilita;
    }

    public void setTipoContabilita(TipoContabilita tipoContabilita) {

	this.tipoContabilita = tipoContabilita;
    }

    /**
     * Codifica del capitolo di bilancio
     **/
    public NuovaVocePendenza codiceContabilita(String codiceContabilita) {

	this.codiceContabilita = codiceContabilita;
	return this;
    }

    public String getCodiceContabilita() {

	return codiceContabilita;
    }

    public void setCodiceContabilita(String codiceContabilita) {

	this.codiceContabilita = codiceContabilita;
    }

    /**
     * Tipologia di Bollo digitale
     */
    public enum TipoBolloEnum {

	_01("01");

	private String value;

	TipoBolloEnum(String value) {

	    this.value = value;
	}

	@Override
	public String toString() {

	    return String.valueOf(value);
	}

	public static TipoBolloEnum fromValue(String text) {

	    for (TipoBolloEnum b : TipoBolloEnum.values()) {
		if (String.valueOf(b.value).equals(text)) {
		    return b;
		}
	    }
	    return null;
	}
    }

    /**
     * Tipologia di Bollo digitale
     **/
    public NuovaVocePendenza tipoBollo(TipoBolloEnum tipoBollo) {

	this.tipoBollo = tipoBollo;
	return this;
    }

    public TipoBolloEnum getTipoBollo() {

	return tipoBollo;
    }

    public void setTipoBollo(TipoBolloEnum tipoBollo) {

	this.tipoBollo = tipoBollo;
    }

    /**
     * Digest in base64 del documento informatico associato alla marca da bollo
     **/
    public NuovaVocePendenza hashDocumento(String hashDocumento) {

	this.hashDocumento = hashDocumento;
	return this;
    }

    public String getHashDocumento() {

	return hashDocumento;
    }

    public void setHashDocumento(String hashDocumento) {

	this.hashDocumento = hashDocumento;
    }

    /**
     * Sigla automobilistica della provincia di residenza del soggetto pagatore
     **/
    public NuovaVocePendenza provinciaResidenza(String provinciaResidenza) {

	this.provinciaResidenza = provinciaResidenza;
	return this;
    }

    public String getProvinciaResidenza() {

	return provinciaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza) {

	this.provinciaResidenza = provinciaResidenza;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	NuovaVocePendenza nuovaVocePendenza = (NuovaVocePendenza) o;
	return Objects.equals(idVocePendenza, nuovaVocePendenza.idVocePendenza) && Objects.equals(importo, nuovaVocePendenza.importo)
		&& Objects.equals(descrizione, nuovaVocePendenza.descrizione) && Objects.equals(datiAllegati, nuovaVocePendenza.datiAllegati)
		&& Objects.equals(descrizioneCausaleRPT, nuovaVocePendenza.descrizioneCausaleRPT)
		&& Objects.equals(codEntrata, nuovaVocePendenza.codEntrata) && Objects.equals(ibanAccredito, nuovaVocePendenza.ibanAccredito)
		&& Objects.equals(ibanAppoggio, nuovaVocePendenza.ibanAppoggio) && Objects.equals(tipoContabilita, nuovaVocePendenza.tipoContabilita)
		&& Objects.equals(codiceContabilita, nuovaVocePendenza.codiceContabilita) && Objects.equals(tipoBollo, nuovaVocePendenza.tipoBollo)
		&& Objects.equals(hashDocumento, nuovaVocePendenza.hashDocumento)
		&& Objects.equals(provinciaResidenza, nuovaVocePendenza.provinciaResidenza);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idVocePendenza, importo, descrizione, datiAllegati, descrizioneCausaleRPT, codEntrata, ibanAccredito, ibanAppoggio,
		tipoContabilita, codiceContabilita, tipoBollo, hashDocumento, provinciaResidenza);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NuovaVocePendenza {\n");
	sb.append("    ").append(toIndentedString(super.toString())).append("\n");
	sb.append("    idVocePendenza: ").append(toIndentedString(idVocePendenza)).append("\n");
	sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
	sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
	sb.append("    datiAllegati: ").append(toIndentedString(datiAllegati)).append("\n");
	sb.append("    descrizioneCausaleRPT: ").append(toIndentedString(descrizioneCausaleRPT)).append("\n");
	sb.append("    codEntrata: ").append(toIndentedString(codEntrata)).append("\n");
	sb.append("    ibanAccredito: ").append(toIndentedString(ibanAccredito)).append("\n");
	sb.append("    ibanAppoggio: ").append(toIndentedString(ibanAppoggio)).append("\n");
	sb.append("    tipoContabilita: ").append(toIndentedString(tipoContabilita)).append("\n");
	sb.append("    codiceContabilita: ").append(toIndentedString(codiceContabilita)).append("\n");
	sb.append("    tipoBollo: ").append(toIndentedString(tipoBollo)).append("\n");
	sb.append("    hashDocumento: ").append(toIndentedString(hashDocumento)).append("\n");
	sb.append("    provinciaResidenza: ").append(toIndentedString(provinciaResidenza)).append("\n");
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
