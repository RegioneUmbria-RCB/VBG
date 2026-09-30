package it.gruppoinit.pdd.utils;

import java.util.Date;

public class IstanzaHelper {

    private Integer codiceIstanza;
    private String idcomune;
    private String software;
    private String numeroistanza;
    private String numeroprotocollo;
    private Date dataprotocollo;
    private Date data;
    private String lavori;
    private String lavoriEstesa;
    private String intervento;
    private Integer codiceIntervento;
    private String procedura;
    private String domicilioElettronico;
    private String istComune;
    private String istComuneCodiceComune;
    private String istComuneCodiceCatastale;
    private String istComuneSiglaProvincia;
    private String istComuneProvincia;
    private RichiedenteHelper richiedente;
    private ImpresaHelper impresa;
    private String responsabile;
    private String responsabileUserid;
    private String responsabileProcedimento;
    private String responsabileProcedimentoUserid;
    private String codicePraticaTelematica;
    private String idDomandaSTC;
    private String codiceInterventoRi;
    private String codiceProcedimentoRi;
    private LocalizzazioniIstanzaHelper impianto;

    public String getCodicePraticaTelematica() {

	return codicePraticaTelematica;
    }

    public void setCodicePraticaTelematica(String codicePraticaTelematica) {

	this.codicePraticaTelematica = codicePraticaTelematica;
    }

    public String getIdDomandaSTC() {

	return idDomandaSTC;
    }

    public void setIdDomandaSTC(String idDomandaSTC) {

	this.idDomandaSTC = idDomandaSTC;
    }

    public String getResponsabileUserid() {

	return responsabileUserid;
    }

    public void setResponsabileUserid(String responsabileUserid) {

	this.responsabileUserid = responsabileUserid;
    }

    public String getResponsabileProcedimentoUserid() {

	return responsabileProcedimentoUserid;
    }

    public void setResponsabileProcedimentoUserid(String responsabileProcedimentoUserid) {

	this.responsabileProcedimentoUserid = responsabileProcedimentoUserid;
    }

    public String getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(String responsabile) {

	this.responsabile = responsabile;
    }

    public String getResponsabileProcedimento() {

	return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(String responsabileProcedimento) {

	this.responsabileProcedimento = responsabileProcedimento;
    }

    public String getIstComune() {

	return istComune;
    }

    public void setIstComune(String istComune) {

	this.istComune = istComune;
    }

    public String getIstComuneCodiceCatastale() {

	return istComuneCodiceCatastale;
    }

    public void setIstComuneCodiceCatastale(String istComuneCodiceCatastale) {

	this.istComuneCodiceCatastale = istComuneCodiceCatastale;
    }

    public String getIstComuneCodiceComune() {

	return istComuneCodiceComune;
    }

    public void setIstComuneCodiceComune(String istComuneCodiceComune) {

	this.istComuneCodiceComune = istComuneCodiceComune;
    }

    public String getIstComuneSiglaProvincia() {

	return istComuneSiglaProvincia;
    }

    public void setIstComuneSiglaProvincia(String istComuneSiglaProvincia) {

	this.istComuneSiglaProvincia = istComuneSiglaProvincia;
    }

    public String getIstComuneProvincia() {

	return istComuneProvincia;
    }

    public void setIstComuneProvincia(String istComuneProvincia) {

	this.istComuneProvincia = istComuneProvincia;
    }

    public RichiedenteHelper getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(RichiedenteHelper richiedente) {

	this.richiedente = richiedente;
    }

    public ImpresaHelper getImpresa() {

	return impresa;
    }

    public void setImpresa(ImpresaHelper impresa) {

	this.impresa = impresa;
    }

    public String getDomicilioElettronico() {

	return domicilioElettronico;
    }

    public void setDomicilioElettronico(String domicilioElettronico) {

	this.domicilioElettronico = domicilioElettronico;
    }

    private ProtocolloRIHelper protocolloRI;

    public ProtocolloRIHelper getProtocolloRI() {

	return protocolloRI;
    }

    public void setProtocolloRI(ProtocolloRIHelper protocolloRI) {

	this.protocolloRI = protocolloRI;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public String getNumeroprotocollo() {

	return numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo) {

	this.numeroprotocollo = numeroprotocollo;
    }

    public Date getDataprotocollo() {

	return dataprotocollo;
    }

    public void setDataprotocollo(Date dataprotocollo) {

	this.dataprotocollo = dataprotocollo;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getLavori() {

	return lavori;
    }

    public void setLavori(String lavori) {

	this.lavori = lavori;
    }

    public String getLavoriEstesa() {

	return lavoriEstesa;
    }

    public void setLavoriEstesa(String lavoriEstesa) {

	this.lavoriEstesa = lavoriEstesa;
    }

    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    public Integer getCodiceIntervento() {

	return codiceIntervento;
    }

    public void setCodiceIntervento(Integer codiceIntervento) {

	this.codiceIntervento = codiceIntervento;
    }

    public String getProcedura() {

	return procedura;
    }

    public void setProcedura(String procedura) {

	this.procedura = procedura;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public LocalizzazioniIstanzaHelper getImpianto() {

	return impianto;
    }

    public void setImpianto(LocalizzazioniIstanzaHelper impianto) {

	this.impianto = impianto;
    }

    public String getCodiceInterventoRi() {

	return codiceInterventoRi;
    }

    public void setCodiceInterventoRi(String codiceInterventoRi) {

	this.codiceInterventoRi = codiceInterventoRi;
    }

    public String getCodiceProcedimentoRi() {

	return codiceProcedimentoRi;
    }

    public void setCodiceProcedimentoRi(String codiceProcedimentoRi) {

	this.codiceProcedimentoRi = codiceProcedimentoRi;
    }
}
