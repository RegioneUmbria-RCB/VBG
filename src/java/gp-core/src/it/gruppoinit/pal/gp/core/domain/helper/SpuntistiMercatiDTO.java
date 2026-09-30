package it.gruppoinit.pal.gp.core.domain.helper;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

public class SpuntistiMercatiDTO {

    /**
     * 
     <PRE>
     *     AUTORIZZAZIONI.ID AS IDAUTORIZZAZIONE
     *     SPUNTISTI_MERCATI.ID AS IDSPUNTISTIMERCATI
     *     SPUNTISTI_MERCATI.DATA_REGISTRAZIONE AS DATAREGISTRAZIONE
     *     SPUNTISTI_MERCATI.IDCOMUNE AS IDCOMUNE 
     *     MERCATI.CODICEMERCATO AS CODICEMERCATO
     *     MERCATI.DESCRIZIONE AS MERCATO
     *     MERCATI_USO.ID AS CODICEGIORNO
     *     MERCATI_USO.DESCRIZIONE AS GIORNO
     *     ANAGRAFE.CODICEANAGRAFE AS CODICEANAGRAFE 
     *     ANAGRAFE.NOME AS NOME  
     *     ANAGRAFE.NOMINATIVO AS NOMINATIVO
     *     ANAGRAFE.CODICEFISCALE AS CODICEFISCALE 
     *     ANAGRAFE.PARTITAIVA AS PARTITAIVA
     * </PRE>
     **/
    private BigDecimal idautorizzazione;
    private String autoriznumero;
    private BigDecimal idspuntistimercati;
    private Date dataregistrazione;
    private String idcomune;
    private BigDecimal codicemercato;
    private String mercato;
    private BigDecimal codicegiorno;
    private String giorno;
    private BigDecimal codiceanagrafe;
    private String nome;
    private String nominativo;
    private String codicefiscale;
    private String partitaiva;
    private Date dataregistrazionepresenza;
    //    private String tipoanagrafe;
    //    private String formagiuridica;
    //
    private String descrizioneRichiedente;

    public BigDecimal getIdautorizzazione() {

	return idautorizzazione;
    }

    public void setIdautorizzazione(BigDecimal idautorizzazione) {

	this.idautorizzazione = idautorizzazione;
    }

    public String getAutoriznumero() {

	return autoriznumero;
    }

    public void setAutoriznumero(String autoriznumero) {

	this.autoriznumero = autoriznumero;
    }

    public BigDecimal getIdspuntistimercati() {

	return idspuntistimercati;
    }

    public void setIdspuntistimercati(BigDecimal idspuntistimercati) {

	this.idspuntistimercati = idspuntistimercati;
    }

    public Date getDataregistrazione() {

	return dataregistrazione;
    }

    public void setDataregistrazione(Date dataregistrazione) {

	this.dataregistrazione = dataregistrazione;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public BigDecimal getCodicemercato() {

	return codicemercato;
    }

    public void setCodicemercato(BigDecimal codicemercato) {

	this.codicemercato = codicemercato;
    }

    public String getMercato() {

	return mercato;
    }

    public void setMercato(String mercato) {

	this.mercato = mercato;
    }

    public BigDecimal getCodicegiorno() {

	return codicegiorno;
    }

    public void setCodicegiorno(BigDecimal codicegiorno) {

	this.codicegiorno = codicegiorno;
    }

    public String getGiorno() {

	return giorno;
    }

    public void setGiorno(String giorno) {

	this.giorno = giorno;
    }

    public BigDecimal getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(BigDecimal codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getCodicefiscale() {

	return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {

	this.codicefiscale = codicefiscale;
    }

    public String getPartitaiva() {

	return partitaiva;
    }

    public void setPartitaiva(String partitaiva) {

	this.partitaiva = partitaiva;
    }

    public Date getDataregistrazionepresenza() {

	return dataregistrazionepresenza;
    }

    public void setDataregistrazionepresenza(Date dataregistrazionepresenza) {

	this.dataregistrazionepresenza = dataregistrazionepresenza;
    }

    @Transient
    public String getDescrizioneRichiedente() {

	String answer = "";
	//	if (StringUtils.isBlank(getTipoanagrafe())) {
	//	    return getNominativo();
	//	}
	String tNominativo = getNominativo() == null ? "" : getNominativo();
	answer = tNominativo + " " + (getNome() == null ? "" : getNome());
	if (StringUtils.isNotBlank(getPartitaiva())) {
	    answer += " P.Iva: " + getPartitaiva();
	}
	if (StringUtils.isNotBlank(getCodicefiscale())) {
	    answer += " CF: " + getCodicefiscale();
	}
	descrizioneRichiedente = answer;
	return descrizioneRichiedente;
    }
}
