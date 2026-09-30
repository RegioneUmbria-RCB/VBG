package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class AutorizzazioneSpuntistaHelper {

    private Integer idautorizzazione;
    private Date autorizdata;
    private String autoriznumero;
    private Integer codiceanagrafe;
    private String nominativo;
    private String nome;
    private String partitaiva;
    private String codicefiscale;
    private Date dataregditte;
    private Date dataanzianita;
    private Integer numpresenze;
    private String descrizioneRichiedente;
    private String idcomune;
    private Date dataiscrrea;
    private String autorizcodcomune;
    private String autorizcomune;
    private String nomegerente;
    private String nominativogerente;
    private Integer codiceGerente;
    private String partitaivagerente;
    private String codicefiscalegerente;
    private String autoriginnumero;
    private String descrizioneGerente;
    private String autorizcomprov;
    private String autorizcomsiglaprov;
    private String protocolloaut;
    private Date dataprotocolloaut;
    // 
    private Integer codicetitolare;
    private String nominativotitolare;
    private String nometitolare;
    private String partitaivatitolare;
    private String codicefiscaletitolare;
    private Date dataiscrreatitolare;
    private Date dataregdittetitolare;

    public Integer getIdautorizzazione() {

	return idautorizzazione;
    }

    public void setIdautorizzazione(Integer idautorizzazione) {

	this.idautorizzazione = idautorizzazione;
    }

    public Date getAutorizdata() {

	return autorizdata;
    }

    public void setAutorizdata(Date autorizdata) {

	this.autorizdata = autorizdata;
    }

    public String getAutoriznumero() {

	return autoriznumero;
    }

    public void setAutoriznumero(String autoriznumero) {

	this.autoriznumero = autoriznumero;
    }

    public Integer getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(Integer codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getPartitaiva() {

	return partitaiva;
    }

    public void setPartitaiva(String partitaiva) {

	this.partitaiva = partitaiva;
    }

    public String getCodicefiscale() {

	return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {

	this.codicefiscale = codicefiscale;
    }

    public Date getDataregditte() {

	return dataregditte;
    }

    public void setDataregditte(Date dataregditte) {

	this.dataregditte = dataregditte;
    }

    public Date getDataanzianita() {

	return dataanzianita;
    }

    public void setDataanzianita(Date dataanzianita) {

	this.dataanzianita = dataanzianita;
    }

    public Integer getNumpresenze() {

	return numpresenze;
    }

    public void setNumpresenze(Integer numpresenze) {

	this.numpresenze = numpresenze;
    }

    public String getDescrizioneRichiedente() {

	String answer = "";
	String tNominativo = getNominativo() == null ? "" : getNominativo();
	answer = tNominativo;
	if (StringUtils.isNotBlank(getNome())) {
	    answer += " " + getNome();
	}
	if (StringUtils.isNotBlank(getPartitaiva())) {
	    answer += " P.Iva: " + getPartitaiva();
	}
	if (StringUtils.isNotBlank(getCodicefiscale())) {
	    answer += " CF: " + getCodicefiscale();
	}
	setDescrizioneRichiedente(answer);
	return descrizioneRichiedente;
    }

    public void setDescrizioneRichiedente(String descrizioneRichiedente) {

	this.descrizioneRichiedente = descrizioneRichiedente;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Date getDataiscrrea() {

	return dataiscrrea;
    }

    public void setDataiscrrea(Date dataiscrrea) {

	this.dataiscrrea = dataiscrrea;
    }

    public String getAutorizcodcomune() {

	return autorizcodcomune;
    }

    public void setAutorizcodcomune(String autorizcodcomune) {

	this.autorizcodcomune = autorizcodcomune;
    }

    public String getAutorizcomune() {

	return autorizcomune;
    }

    public void setAutorizcomune(String autorizcomune) {

	this.autorizcomune = autorizcomune;
    }

    public String getNomegerente() {

	return nomegerente;
    }

    public void setNomegerente(String nomegerente) {

	this.nomegerente = nomegerente;
    }

    public String getNominativogerente() {

	return nominativogerente;
    }

    public void setNominativogerente(String nominativogerente) {

	this.nominativogerente = nominativogerente;
    }

    public Integer getCodiceGerente() {

	return codiceGerente;
    }

    public void setCodiceGerente(Integer codiceGerente) {

	this.codiceGerente = codiceGerente;
    }

    public String getPartitaivagerente() {

	return partitaivagerente;
    }

    public void setPartitaivagerente(String partitaivagerente) {

	this.partitaivagerente = partitaivagerente;
    }

    public String getCodicefiscalegerente() {

	return codicefiscalegerente;
    }

    public void setCodicefiscalegerente(String codicefiscalegerente) {

	this.codicefiscalegerente = codicefiscalegerente;
    }

    public String getAutoriginnumero() {

	return autoriginnumero;
    }

    public void setAutoriginnumero(String autoriginnumero) {

	this.autoriginnumero = autoriginnumero;
    }

    public String getDescrizioneGerente() {

	String answer = "";
	String tNominativo = getNominativogerente() == null ? "" : getNominativogerente();
	answer = tNominativo;
	if (StringUtils.isNotBlank(getNomegerente())) {
	    answer += "" + getNomegerente();
	}
	if (StringUtils.isNotBlank(getPartitaivagerente())) {
	    answer += " P.Iva: " + getPartitaivagerente();
	}
	if (StringUtils.isNotBlank(getCodicefiscalegerente())) {
	    answer += " CF: " + getCodicefiscalegerente();
	}
	setDescrizioneGerente(answer);
	return descrizioneGerente;
    }

    public void setDescrizioneGerente(String descrizioneGerente) {

	this.descrizioneGerente = descrizioneGerente;
    }

    public String getAutorizcomprov() {

	return autorizcomprov;
    }

    public void setAutorizcomprov(String autorizcomprov) {

	this.autorizcomprov = autorizcomprov;
    }

    public String getAutorizcomsiglaprov() {

	return autorizcomsiglaprov;
    }

    public void setAutorizcomsiglaprov(String autorizcomsiglaprov) {

	this.autorizcomsiglaprov = autorizcomsiglaprov;
    }

    public String getProtocolloaut() {

	return protocolloaut;
    }

    public void setProtocolloaut(String protocolloaut) {

	this.protocolloaut = protocolloaut;
    }

    public Date getDataprotocolloaut() {

	return dataprotocolloaut;
    }

    public void setDataprotocolloaut(Date dataprotocolloaut) {

	this.dataprotocolloaut = dataprotocolloaut;
    }

    public Integer getCodicetitolare() {

	return codicetitolare;
    }

    public void setCodicetitolare(Integer codicetitolare) {

	this.codicetitolare = codicetitolare;
    }

    public String getNominativotitolare() {

	return nominativotitolare;
    }

    public void setNominativotitolare(String nominativotitolare) {

	this.nominativotitolare = nominativotitolare;
    }

    public String getNometitolare() {

	return nometitolare;
    }

    public void setNometitolare(String nometitolare) {

	this.nometitolare = nometitolare;
    }

    public String getPartitaivatitolare() {

	return partitaivatitolare;
    }

    public void setPartitaivatitolare(String partitaivatitolare) {

	this.partitaivatitolare = partitaivatitolare;
    }

    public String getCodicefiscaletitolare() {

	return codicefiscaletitolare;
    }

    public void setCodicefiscaletitolare(String codicefiscaletitolare) {

	this.codicefiscaletitolare = codicefiscaletitolare;
    }

    public Date getDataiscrreatitolare() {

	return dataiscrreatitolare;
    }

    public void setDataiscrreatitolare(Date dataiscrreatitolare) {

	this.dataiscrreatitolare = dataiscrreatitolare;
    }

    public Date getDataregdittetitolare() {

	return dataregdittetitolare;
    }

    public void setDataregdittetitolare(Date dataregdittetitolare) {

	this.dataregdittetitolare = dataregdittetitolare;
    }

    public String getDescrizioneTitolare() {

	String answer = "";
	String tNominativo = getNominativotitolare() == null ? "" : getNominativotitolare();
	answer = tNominativo;
	if (StringUtils.isNotBlank(getNometitolare())) {
	    answer += " " + getNometitolare();
	}
	if (StringUtils.isNotBlank(getPartitaivatitolare())) {
	    answer += " P.Iva: " + getPartitaivatitolare();
	}
	if (StringUtils.isNotBlank(getCodicefiscaletitolare())) {
	    answer += " CF: " + getCodicefiscaletitolare();
	}
	return answer;
    }
}
