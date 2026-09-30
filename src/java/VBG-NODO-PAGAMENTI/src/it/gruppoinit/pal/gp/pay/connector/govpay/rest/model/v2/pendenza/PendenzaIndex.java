package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.Soggetto;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PendenzaIndex", propOrder = { "idA2A", "idPendenza", "idTipoPendenza", "dominio", "unitaOperativa", "stato", "descrizioneStato",
	"segnalazioni", "iuvAvviso", "iuvPagamento", "dataPagamento", "causale", "soggettoPagatore", "importo", "numeroAvviso", "dataCaricamento",
	"dataValidita", "dataScadenza", "annoRiferimento", "cartellaPagamento", "datiAllegati", "tassonomia", "tassonomiaAvviso", "direzione",
	"divisione", "documento", "tipo", "rpp", "pagamenti", })
public class PendenzaIndex {

    @XmlElement(name = "idA2A")
    private String idA2A = null;
    @XmlElement(name = "idPendenza")
    private String idPendenza = null;
    @XmlElement(name = "idTipoPendenza")
    private String idTipoPendenza = null;
    @XmlElement(name = "dominio")
    private Dominio dominio = null;
    @XmlElement(name = "unitaOperativa")
    private UnitaOperativa unitaOperativa = null;
    @XmlElement(name = "stato")
    private StatoPendenza stato = null;
    @XmlElement(name = "descrizioneStato")
    private String descrizioneStato = null;
    @XmlElement(name = "segnalazioni")
    private List<Segnalazione> segnalazioni = null;
    @XmlElement(name = "iuvAvviso")
    private String iuvAvviso = null;
    @XmlElement(name = "iuvPagamento")
    private String iuvPagamento = null;
    @XmlElement(name = "dataPagamento")
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar dataPagamento = null;
    @XmlElement(name = "causale")
    private String causale = null;
    @XmlElement(name = "soggettoPagatore")
    private Soggetto soggettoPagatore = null;
    @XmlElement(name = "importo")
    private BigDecimal importo = null;
    @XmlElement(name = "numeroAvviso")
    private String numeroAvviso = null;
    @XmlElement(name = "dataCaricamento")
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar dataCaricamento = null;
    @XmlElement(name = "dataValidita")
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar dataValidita = null;
    @XmlElement(name = "dataScadenza")
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar dataScadenza = null;
    @XmlElement(name = "annoRiferimento")
    private BigDecimal annoRiferimento = null;
    @XmlElement(name = "cartellaPagamento")
    private String cartellaPagamento = null;
    @XmlElement(name = "datiAllegati")
    private Object datiAllegati = null;
    @XmlElement(name = "tassonomia")
    private String tassonomia = null;
    @XmlElement(name = "tassonomiaAvviso")
    private TassonomiaAvviso tassonomiaAvviso = null;
    @XmlElement(name = "direzione")
    private String direzione = null;
    @XmlElement(name = "divisione")
    private String divisione = null;
    @XmlElement(name = "documento")
    private Documento documento = null;
    @XmlElement(name = "tipo")
    private TipoPendenzaTipologia tipo = null;
    @XmlElement(name = "rpp")
    private String rpp = null;
    @XmlElement(name = "pagamenti")
    private String pagamenti = null;

    /**
     * Identificativo del gestionale responsabile della pendenza
     **/
    public PendenzaIndex idA2A(String idA2A) {

	this.idA2A = idA2A;
	return this;
    }

    public String getIdA2A() {

	return idA2A;
    }

    public void setIdA2A(String idA2A) {

	this.idA2A = idA2A;
    }

    /**
     * Identificativo della pendenza nel gestionale responsabile
     **/
    public PendenzaIndex idPendenza(String idPendenza) {

	this.idPendenza = idPendenza;
	return this;
    }

    public String getIdPendenza() {

	return idPendenza;
    }

    public void setIdPendenza(String idPendenza) {

	this.idPendenza = idPendenza;
    }

    /**
     * Identificativo della tipologia pendenza
     **/
    public PendenzaIndex idTipoPendenza(String idTipoPendenza) {

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
     **/
    public PendenzaIndex dominio(Dominio dominio) {

	this.dominio = dominio;
	return this;
    }

    public Dominio getDominio() {

	return dominio;
    }

    public void setDominio(Dominio dominio) {

	this.dominio = dominio;
    }

    /**
     **/
    public PendenzaIndex unitaOperativa(UnitaOperativa unitaOperativa) {

	this.unitaOperativa = unitaOperativa;
	return this;
    }

    public UnitaOperativa getUnitaOperativa() {

	return unitaOperativa;
    }

    public void setUnitaOperativa(UnitaOperativa unitaOperativa) {

	this.unitaOperativa = unitaOperativa;
    }

    /**
     **/
    public PendenzaIndex stato(StatoPendenza stato) {

	this.stato = stato;
	return this;
    }

    public StatoPendenza getStato() {

	return stato;
    }

    public void setStato(StatoPendenza stato) {

	this.stato = stato;
    }

    /**
     * Descrizione estesa dello stato di elaborazione della pendenza
     **/
    public PendenzaIndex descrizioneStato(String descrizioneStato) {

	this.descrizioneStato = descrizioneStato;
	return this;
    }

    public String getDescrizioneStato() {

	return descrizioneStato;
    }

    public void setDescrizioneStato(String descrizioneStato) {

	this.descrizioneStato = descrizioneStato;
    }

    /**
     **/
    public PendenzaIndex segnalazioni(List<Segnalazione> segnalazioni) {

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
     * Iuv avviso, assegnato se pagabile da psp
     **/
    public PendenzaIndex iuvAvviso(String iuvAvviso) {

	this.iuvAvviso = iuvAvviso;
	return this;
    }

    public String getIuvAvviso() {

	return iuvAvviso;
    }

    public void setIuvAvviso(String iuvAvviso) {

	this.iuvAvviso = iuvAvviso;
    }

    /**
     * Iuv dell'ultimo pagamento eseguito con successo
     **/
    public PendenzaIndex iuvPagamento(String iuvPagamento) {

	this.iuvPagamento = iuvPagamento;
	return this;
    }

    public String getIuvPagamento() {

	return iuvPagamento;
    }

    public void setIuvPagamento(String iuvPagamento) {

	this.iuvPagamento = iuvPagamento;
    }

    /**
     * Data di pagamento della pendenza
     **/
    public PendenzaIndex dataPagamento(XMLGregorianCalendar dataPagamento) {

	this.dataPagamento = dataPagamento;
	return this;
    }

    public XMLGregorianCalendar getDataPagamento() {

	return dataPagamento;
    }

    public void setDataPagamento(XMLGregorianCalendar dataPagamento) {

	this.dataPagamento = dataPagamento;
    }

    /**
     * Descrizione da inserire nell'avviso di pagamento
     **/
    public PendenzaIndex causale(String causale) {

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
    public PendenzaIndex soggettoPagatore(Soggetto soggettoPagatore) {

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
    public PendenzaIndex importo(BigDecimal importo) {

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
    public PendenzaIndex numeroAvviso(String numeroAvviso) {

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
     * Data di emissione della pendenza
     **/
    public PendenzaIndex dataCaricamento(XMLGregorianCalendar dataCaricamento) {

	this.dataCaricamento = dataCaricamento;
	return this;
    }

    public XMLGregorianCalendar getDataCaricamento() {

	return dataCaricamento;
    }

    public void setDataCaricamento(XMLGregorianCalendar dataCaricamento) {

	this.dataCaricamento = dataCaricamento;
    }

    /**
     * Data di validita dei dati della pendenza, decorsa la quale la pendenza può subire variazioni.
     **/
    public PendenzaIndex dataValidita(XMLGregorianCalendar dataValidita) {

	this.dataValidita = dataValidita;
	return this;
    }

    public XMLGregorianCalendar getDataValidita() {

	return dataValidita;
    }

    public void setDataValidita(XMLGregorianCalendar dataValidita) {

	this.dataValidita = dataValidita;
    }

    /**
     * Data di scadenza della pendenza, decorsa la quale non è più pagabile.
     **/
    public PendenzaIndex dataScadenza(XMLGregorianCalendar dataScadenza) {

	this.dataScadenza = dataScadenza;
	return this;
    }

    public XMLGregorianCalendar getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(XMLGregorianCalendar dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    /**
     * Anno di riferimento della pendenza
     **/
    public PendenzaIndex annoRiferimento(BigDecimal annoRiferimento) {

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
    public PendenzaIndex cartellaPagamento(String cartellaPagamento) {

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
    public PendenzaIndex datiAllegati(Object datiAllegati) {

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
     * Macro categoria della pendenza secondo la classificazione del creditore
     **/
    public PendenzaIndex tassonomia(String tassonomia) {

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
    public PendenzaIndex tassonomiaAvviso(TassonomiaAvviso tassonomiaAvviso) {

	this.tassonomiaAvviso = tassonomiaAvviso;
	return this;
    }

    public TassonomiaAvviso getTassonomiaAvviso() {

	return tassonomiaAvviso;
    }

    public void setTassonomiaAvviso(TassonomiaAvviso tassonomiaAvviso) {

	this.tassonomiaAvviso = tassonomiaAvviso;
    }

    /**
     * Identificativo della direzione interna all'ente creditore
     **/
    public PendenzaIndex direzione(String direzione) {

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
    public PendenzaIndex divisione(String divisione) {

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
     **/
    public PendenzaIndex documento(Documento documento) {

	this.documento = documento;
	return this;
    }

    public Documento getDocumento() {

	return documento;
    }

    public void setDocumento(Documento documento) {

	this.documento = documento;
    }

    /**
     **/
    public PendenzaIndex tipo(TipoPendenzaTipologia tipo) {

	this.tipo = tipo;
	return this;
    }

    public TipoPendenzaTipologia getTipo() {

	return tipo;
    }

    public void setTipo(TipoPendenzaTipologia tipo) {

	this.tipo = tipo;
    }

    /**
     * Url per l'elenco delle rpp emesse per la pendenza
     **/
    public PendenzaIndex rpp(String rpp) {

	this.rpp = rpp;
	return this;
    }

    public String getRpp() {

	return rpp;
    }

    public void setRpp(String rpp) {

	this.rpp = rpp;
    }

    /**
     * Url per l'elenco dei pagamenti da portale comprensivi della pendenza
     **/
    public PendenzaIndex pagamenti(String pagamenti) {

	this.pagamenti = pagamenti;
	return this;
    }

    public String getPagamenti() {

	return pagamenti;
    }

    public void setPagamenti(String pagamenti) {

	this.pagamenti = pagamenti;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	PendenzaIndex pendenzaIndex = (PendenzaIndex) o;
	return Objects.equals(idA2A, pendenzaIndex.idA2A) && Objects.equals(idPendenza, pendenzaIndex.idPendenza)
		&& Objects.equals(idTipoPendenza, pendenzaIndex.idTipoPendenza) && Objects.equals(dominio, pendenzaIndex.dominio)
		&& Objects.equals(unitaOperativa, pendenzaIndex.unitaOperativa) && Objects.equals(stato, pendenzaIndex.stato)
		&& Objects.equals(descrizioneStato, pendenzaIndex.descrizioneStato) && Objects.equals(segnalazioni, pendenzaIndex.segnalazioni)
		&& Objects.equals(iuvAvviso, pendenzaIndex.iuvAvviso) && Objects.equals(iuvPagamento, pendenzaIndex.iuvPagamento)
		&& Objects.equals(dataPagamento, pendenzaIndex.dataPagamento) && Objects.equals(causale, pendenzaIndex.causale)
		&& Objects.equals(soggettoPagatore, pendenzaIndex.soggettoPagatore) && Objects.equals(importo, pendenzaIndex.importo)
		&& Objects.equals(numeroAvviso, pendenzaIndex.numeroAvviso) && Objects.equals(dataCaricamento, pendenzaIndex.dataCaricamento)
		&& Objects.equals(dataValidita, pendenzaIndex.dataValidita) && Objects.equals(dataScadenza, pendenzaIndex.dataScadenza)
		&& Objects.equals(annoRiferimento, pendenzaIndex.annoRiferimento)
		&& Objects.equals(cartellaPagamento, pendenzaIndex.cartellaPagamento) && Objects.equals(datiAllegati, pendenzaIndex.datiAllegati)
		&& Objects.equals(tassonomia, pendenzaIndex.tassonomia) && Objects.equals(tassonomiaAvviso, pendenzaIndex.tassonomiaAvviso)
		&& Objects.equals(direzione, pendenzaIndex.direzione) && Objects.equals(divisione, pendenzaIndex.divisione)
		&& Objects.equals(documento, pendenzaIndex.documento) && Objects.equals(tipo, pendenzaIndex.tipo)
		&& Objects.equals(rpp, pendenzaIndex.rpp) && Objects.equals(pagamenti, pendenzaIndex.pagamenti);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idA2A, idPendenza, idTipoPendenza, dominio, unitaOperativa, stato, descrizioneStato, segnalazioni, iuvAvviso,
		iuvPagamento, dataPagamento, causale, soggettoPagatore, importo, numeroAvviso, dataCaricamento, dataValidita, dataScadenza,
		annoRiferimento, cartellaPagamento, datiAllegati, tassonomia, tassonomiaAvviso, direzione, divisione, documento, tipo, rpp,
		pagamenti);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class PendenzaIndex {\n");
	sb.append("    ").append(toIndentedString(super.toString())).append("\n");
	sb.append("    idA2A: ").append(toIndentedString(idA2A)).append("\n");
	sb.append("    idPendenza: ").append(toIndentedString(idPendenza)).append("\n");
	sb.append("    idTipoPendenza: ").append(toIndentedString(idTipoPendenza)).append("\n");
	sb.append("    dominio: ").append(toIndentedString(dominio)).append("\n");
	sb.append("    unitaOperativa: ").append(toIndentedString(unitaOperativa)).append("\n");
	sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
	sb.append("    descrizioneStato: ").append(toIndentedString(descrizioneStato)).append("\n");
	sb.append("    segnalazioni: ").append(toIndentedString(segnalazioni)).append("\n");
	sb.append("    iuvAvviso: ").append(toIndentedString(iuvAvviso)).append("\n");
	sb.append("    iuvPagamento: ").append(toIndentedString(iuvPagamento)).append("\n");
	sb.append("    dataPagamento: ").append(toIndentedString(dataPagamento)).append("\n");
	sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
	sb.append("    soggettoPagatore: ").append(toIndentedString(soggettoPagatore)).append("\n");
	sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
	sb.append("    numeroAvviso: ").append(toIndentedString(numeroAvviso)).append("\n");
	sb.append("    dataCaricamento: ").append(toIndentedString(dataCaricamento)).append("\n");
	sb.append("    dataValidita: ").append(toIndentedString(dataValidita)).append("\n");
	sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
	sb.append("    annoRiferimento: ").append(toIndentedString(annoRiferimento)).append("\n");
	sb.append("    cartellaPagamento: ").append(toIndentedString(cartellaPagamento)).append("\n");
	sb.append("    datiAllegati: ").append(toIndentedString(datiAllegati)).append("\n");
	sb.append("    tassonomia: ").append(toIndentedString(tassonomia)).append("\n");
	sb.append("    tassonomiaAvviso: ").append(toIndentedString(tassonomiaAvviso)).append("\n");
	sb.append("    direzione: ").append(toIndentedString(direzione)).append("\n");
	sb.append("    divisione: ").append(toIndentedString(divisione)).append("\n");
	sb.append("    documento: ").append(toIndentedString(documento)).append("\n");
	sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
	sb.append("    rpp: ").append(toIndentedString(rpp)).append("\n");
	sb.append("    pagamenti: ").append(toIndentedString(pagamenti)).append("\n");
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
