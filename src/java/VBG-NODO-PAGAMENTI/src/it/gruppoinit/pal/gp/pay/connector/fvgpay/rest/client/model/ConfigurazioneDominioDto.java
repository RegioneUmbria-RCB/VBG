package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

public class ConfigurazioneDominioDto {

    private String autDataDaPoste = null;
    private Integer avvisaturaImmediata = null;
    private String cap = null;
    private String cf = null;
    private String codiceInterbancario = null;
    private Double commissionePa = null;
    private String comune = null;
    private String dataSospensione = null;
    private String dataValidita = null;
    private String datiSpecificiIncasso = null;
    private String denominazioneContatto1 = null;
    private String denominazioneContatto2 = null;
    private String denominazioneContatto3 = null;
    private String denominazioneContatto4 = null;
    private String denominazioneContatto5 = null;
    private String denominazioneContatto6 = null;
    private String denominazioneContatto7 = null;
    private String denominazioneContatto8 = null;
    private String descrizionePartitario = null;
    private String email = null;
    private String emailContatto1 = null;
    private String emailContatto2 = null;
    private String emailContatto3 = null;
    private String emailContatto4 = null;
    private String emailContatto5 = null;
    private String emailContatto6 = null;
    private String emailContatto7 = null;
    private String emailContatto8 = null;
    private String flagServizio = null;
    private Long idConfigurazione = null;
    private Long idDominio = null;
    private Long idEnte = null;
    private Double importoMinimo = null;
    private String indirizzo = null;
    private String intestatarioCcPostale = null;
    private String intestazione = null;
    private Integer maxRate = null;
    private String modelliPagamento = null;
    private String nazione = null;
    private String numeroCcPostale = null;
    private String pec = null;
    private String piva = null;
    private String provincia = null;
    private Integer rateObbligatorie = null;
    private String riferim1 = null;
    private String riferim2 = null;
    private String riferim3 = null;
    private String riferim4 = null;
    private String riferim5 = null;
    private String riferim6 = null;
    private String riferim7 = null;
    private String riferim8 = null;
    private String settore = null;
    private String sitoWeb = null;
    private String sitoWebEnte = null;
    private String telefono = null;
    private String telefonoContatto1 = null;
    private String telefonoContatto2 = null;
    private String telefonoContatto3 = null;
    private String telefonoContatto4 = null;
    private String telefonoContatto5 = null;
    private String telefonoContatto6 = null;
    private String telefonoContatto7 = null;
    private String telefonoContatto8 = null;
    private String testoLiberoBeneficiario = null;
    private Integer tipoAvvisatura = null;
    private Integer tipoContabilita = null;
    private String tipoPartitario = null;

    /**
     * Get autDataDaPoste
     * 
     * @return autDataDaPoste
     **/
    @XmlElement(name = "autDataDaPoste")
    public String getAutDataDaPoste() {

	return autDataDaPoste;
    }

    public void setAutDataDaPoste(String autDataDaPoste) {

	this.autDataDaPoste = autDataDaPoste;
    }

    public ConfigurazioneDominioDto autDataDaPoste(String autDataDaPoste) {

	this.autDataDaPoste = autDataDaPoste;
	return this;
    }

    /**
     * Get avvisaturaImmediata
     * 
     * @return avvisaturaImmediata
     **/
    @XmlElement(name = "avvisaturaImmediata")
    public Integer getAvvisaturaImmediata() {

	return avvisaturaImmediata;
    }

    public void setAvvisaturaImmediata(Integer avvisaturaImmediata) {

	this.avvisaturaImmediata = avvisaturaImmediata;
    }

    public ConfigurazioneDominioDto avvisaturaImmediata(Integer avvisaturaImmediata) {

	this.avvisaturaImmediata = avvisaturaImmediata;
	return this;
    }

    /**
     * Get cap
     * 
     * @return cap
     **/
    @XmlElement(name = "cap")
    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public ConfigurazioneDominioDto cap(String cap) {

	this.cap = cap;
	return this;
    }

    /**
     * Get cf
     * 
     * @return cf
     **/
    @XmlElement(name = "cf")
    public String getCf() {

	return cf;
    }

    public void setCf(String cf) {

	this.cf = cf;
    }

    public ConfigurazioneDominioDto cf(String cf) {

	this.cf = cf;
	return this;
    }

    /**
     * Get codiceInterbancario
     * 
     * @return codiceInterbancario
     **/
    @XmlElement(name = "codiceInterbancario")
    public String getCodiceInterbancario() {

	return codiceInterbancario;
    }

    public void setCodiceInterbancario(String codiceInterbancario) {

	this.codiceInterbancario = codiceInterbancario;
    }

    public ConfigurazioneDominioDto codiceInterbancario(String codiceInterbancario) {

	this.codiceInterbancario = codiceInterbancario;
	return this;
    }

    /**
     * Get commissionePa
     * 
     * @return commissionePa
     **/
    @XmlElement(name = "commissionePa")
    public Double getCommissionePa() {

	return commissionePa;
    }

    public void setCommissionePa(Double commissionePa) {

	this.commissionePa = commissionePa;
    }

    public ConfigurazioneDominioDto commissionePa(Double commissionePa) {

	this.commissionePa = commissionePa;
	return this;
    }

    /**
     * Get comune
     * 
     * @return comune
     **/
    @XmlElement(name = "comune")
    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public ConfigurazioneDominioDto comune(String comune) {

	this.comune = comune;
	return this;
    }

    /**
     * Get dataSospensione
     * 
     * @return dataSospensione
     **/
    @XmlElement(name = "dataSospensione")
    public String getDataSospensione() {

	return dataSospensione;
    }

    public void setDataSospensione(String dataSospensione) {

	this.dataSospensione = dataSospensione;
    }

    public ConfigurazioneDominioDto dataSospensione(String dataSospensione) {

	this.dataSospensione = dataSospensione;
	return this;
    }

    /**
     * Get dataValidita
     * 
     * @return dataValidita
     **/
    @XmlElement(name = "dataValidita")
    public String getDataValidita() {

	return dataValidita;
    }

    public void setDataValidita(String dataValidita) {

	this.dataValidita = dataValidita;
    }

    public ConfigurazioneDominioDto dataValidita(String dataValidita) {

	this.dataValidita = dataValidita;
	return this;
    }

    /**
     * Get datiSpecificiIncasso
     * 
     * @return datiSpecificiIncasso
     **/
    @XmlElement(name = "datiSpecificiIncasso")
    public String getDatiSpecificiIncasso() {

	return datiSpecificiIncasso;
    }

    public void setDatiSpecificiIncasso(String datiSpecificiIncasso) {

	this.datiSpecificiIncasso = datiSpecificiIncasso;
    }

    public ConfigurazioneDominioDto datiSpecificiIncasso(String datiSpecificiIncasso) {

	this.datiSpecificiIncasso = datiSpecificiIncasso;
	return this;
    }

    /**
     * Get denominazioneContatto1
     * 
     * @return denominazioneContatto1
     **/
    @XmlElement(name = "denominazioneContatto1")
    public String getDenominazioneContatto1() {

	return denominazioneContatto1;
    }

    public void setDenominazioneContatto1(String denominazioneContatto1) {

	this.denominazioneContatto1 = denominazioneContatto1;
    }

    public ConfigurazioneDominioDto denominazioneContatto1(String denominazioneContatto1) {

	this.denominazioneContatto1 = denominazioneContatto1;
	return this;
    }

    /**
     * Get denominazioneContatto2
     * 
     * @return denominazioneContatto2
     **/
    @XmlElement(name = "denominazioneContatto2")
    public String getDenominazioneContatto2() {

	return denominazioneContatto2;
    }

    public void setDenominazioneContatto2(String denominazioneContatto2) {

	this.denominazioneContatto2 = denominazioneContatto2;
    }

    public ConfigurazioneDominioDto denominazioneContatto2(String denominazioneContatto2) {

	this.denominazioneContatto2 = denominazioneContatto2;
	return this;
    }

    /**
     * Get denominazioneContatto3
     * 
     * @return denominazioneContatto3
     **/
    @XmlElement(name = "denominazioneContatto3")
    public String getDenominazioneContatto3() {

	return denominazioneContatto3;
    }

    public void setDenominazioneContatto3(String denominazioneContatto3) {

	this.denominazioneContatto3 = denominazioneContatto3;
    }

    public ConfigurazioneDominioDto denominazioneContatto3(String denominazioneContatto3) {

	this.denominazioneContatto3 = denominazioneContatto3;
	return this;
    }

    /**
     * Get denominazioneContatto4
     * 
     * @return denominazioneContatto4
     **/
    @XmlElement(name = "denominazioneContatto4")
    public String getDenominazioneContatto4() {

	return denominazioneContatto4;
    }

    public void setDenominazioneContatto4(String denominazioneContatto4) {

	this.denominazioneContatto4 = denominazioneContatto4;
    }

    public ConfigurazioneDominioDto denominazioneContatto4(String denominazioneContatto4) {

	this.denominazioneContatto4 = denominazioneContatto4;
	return this;
    }

    /**
     * Get denominazioneContatto5
     * 
     * @return denominazioneContatto5
     **/
    @XmlElement(name = "denominazioneContatto5")
    public String getDenominazioneContatto5() {

	return denominazioneContatto5;
    }

    public void setDenominazioneContatto5(String denominazioneContatto5) {

	this.denominazioneContatto5 = denominazioneContatto5;
    }

    public ConfigurazioneDominioDto denominazioneContatto5(String denominazioneContatto5) {

	this.denominazioneContatto5 = denominazioneContatto5;
	return this;
    }

    /**
     * Get denominazioneContatto6
     * 
     * @return denominazioneContatto6
     **/
    @XmlElement(name = "denominazioneContatto6")
    public String getDenominazioneContatto6() {

	return denominazioneContatto6;
    }

    public void setDenominazioneContatto6(String denominazioneContatto6) {

	this.denominazioneContatto6 = denominazioneContatto6;
    }

    public ConfigurazioneDominioDto denominazioneContatto6(String denominazioneContatto6) {

	this.denominazioneContatto6 = denominazioneContatto6;
	return this;
    }

    /**
     * Get denominazioneContatto7
     * 
     * @return denominazioneContatto7
     **/
    @XmlElement(name = "denominazioneContatto7")
    public String getDenominazioneContatto7() {

	return denominazioneContatto7;
    }

    public void setDenominazioneContatto7(String denominazioneContatto7) {

	this.denominazioneContatto7 = denominazioneContatto7;
    }

    public ConfigurazioneDominioDto denominazioneContatto7(String denominazioneContatto7) {

	this.denominazioneContatto7 = denominazioneContatto7;
	return this;
    }

    /**
     * Get denominazioneContatto8
     * 
     * @return denominazioneContatto8
     **/
    @XmlElement(name = "denominazioneContatto8")
    public String getDenominazioneContatto8() {

	return denominazioneContatto8;
    }

    public void setDenominazioneContatto8(String denominazioneContatto8) {

	this.denominazioneContatto8 = denominazioneContatto8;
    }

    public ConfigurazioneDominioDto denominazioneContatto8(String denominazioneContatto8) {

	this.denominazioneContatto8 = denominazioneContatto8;
	return this;
    }

    /**
     * Get descrizionePartitario
     * 
     * @return descrizionePartitario
     **/
    @XmlElement(name = "descrizionePartitario")
    public String getDescrizionePartitario() {

	return descrizionePartitario;
    }

    public void setDescrizionePartitario(String descrizionePartitario) {

	this.descrizionePartitario = descrizionePartitario;
    }

    public ConfigurazioneDominioDto descrizionePartitario(String descrizionePartitario) {

	this.descrizionePartitario = descrizionePartitario;
	return this;
    }

    /**
     * Get email
     * 
     * @return email
     **/
    @XmlElement(name = "email")
    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public ConfigurazioneDominioDto email(String email) {

	this.email = email;
	return this;
    }

    /**
     * Get emailContatto1
     * 
     * @return emailContatto1
     **/
    @XmlElement(name = "emailContatto1")
    public String getEmailContatto1() {

	return emailContatto1;
    }

    public void setEmailContatto1(String emailContatto1) {

	this.emailContatto1 = emailContatto1;
    }

    public ConfigurazioneDominioDto emailContatto1(String emailContatto1) {

	this.emailContatto1 = emailContatto1;
	return this;
    }

    /**
     * Get emailContatto2
     * 
     * @return emailContatto2
     **/
    @XmlElement(name = "emailContatto2")
    public String getEmailContatto2() {

	return emailContatto2;
    }

    public void setEmailContatto2(String emailContatto2) {

	this.emailContatto2 = emailContatto2;
    }

    public ConfigurazioneDominioDto emailContatto2(String emailContatto2) {

	this.emailContatto2 = emailContatto2;
	return this;
    }

    /**
     * Get emailContatto3
     * 
     * @return emailContatto3
     **/
    @XmlElement(name = "emailContatto3")
    public String getEmailContatto3() {

	return emailContatto3;
    }

    public void setEmailContatto3(String emailContatto3) {

	this.emailContatto3 = emailContatto3;
    }

    public ConfigurazioneDominioDto emailContatto3(String emailContatto3) {

	this.emailContatto3 = emailContatto3;
	return this;
    }

    /**
     * Get emailContatto4
     * 
     * @return emailContatto4
     **/
    @XmlElement(name = "emailContatto4")
    public String getEmailContatto4() {

	return emailContatto4;
    }

    public void setEmailContatto4(String emailContatto4) {

	this.emailContatto4 = emailContatto4;
    }

    public ConfigurazioneDominioDto emailContatto4(String emailContatto4) {

	this.emailContatto4 = emailContatto4;
	return this;
    }

    /**
     * Get emailContatto5
     * 
     * @return emailContatto5
     **/
    @XmlElement(name = "emailContatto5")
    public String getEmailContatto5() {

	return emailContatto5;
    }

    public void setEmailContatto5(String emailContatto5) {

	this.emailContatto5 = emailContatto5;
    }

    public ConfigurazioneDominioDto emailContatto5(String emailContatto5) {

	this.emailContatto5 = emailContatto5;
	return this;
    }

    /**
     * Get emailContatto6
     * 
     * @return emailContatto6
     **/
    @XmlElement(name = "emailContatto6")
    public String getEmailContatto6() {

	return emailContatto6;
    }

    public void setEmailContatto6(String emailContatto6) {

	this.emailContatto6 = emailContatto6;
    }

    public ConfigurazioneDominioDto emailContatto6(String emailContatto6) {

	this.emailContatto6 = emailContatto6;
	return this;
    }

    /**
     * Get emailContatto7
     * 
     * @return emailContatto7
     **/
    @XmlElement(name = "emailContatto7")
    public String getEmailContatto7() {

	return emailContatto7;
    }

    public void setEmailContatto7(String emailContatto7) {

	this.emailContatto7 = emailContatto7;
    }

    public ConfigurazioneDominioDto emailContatto7(String emailContatto7) {

	this.emailContatto7 = emailContatto7;
	return this;
    }

    /**
     * Get emailContatto8
     * 
     * @return emailContatto8
     **/
    @XmlElement(name = "emailContatto8")
    public String getEmailContatto8() {

	return emailContatto8;
    }

    public void setEmailContatto8(String emailContatto8) {

	this.emailContatto8 = emailContatto8;
    }

    public ConfigurazioneDominioDto emailContatto8(String emailContatto8) {

	this.emailContatto8 = emailContatto8;
	return this;
    }

    /**
     * Get flagServizio
     * 
     * @return flagServizio
     **/
    @XmlElement(name = "flagServizio")
    public String getFlagServizio() {

	return flagServizio;
    }

    public void setFlagServizio(String flagServizio) {

	this.flagServizio = flagServizio;
    }

    public ConfigurazioneDominioDto flagServizio(String flagServizio) {

	this.flagServizio = flagServizio;
	return this;
    }

    /**
     * Get idConfigurazione
     * 
     * @return idConfigurazione
     **/
    @XmlElement(name = "idConfigurazione")
    public Long getIdConfigurazione() {

	return idConfigurazione;
    }

    public void setIdConfigurazione(Long idConfigurazione) {

	this.idConfigurazione = idConfigurazione;
    }

    public ConfigurazioneDominioDto idConfigurazione(Long idConfigurazione) {

	this.idConfigurazione = idConfigurazione;
	return this;
    }

    /**
     * Get idDominio
     * 
     * @return idDominio
     **/
    @XmlElement(name = "idDominio")
    public Long getIdDominio() {

	return idDominio;
    }

    public void setIdDominio(Long idDominio) {

	this.idDominio = idDominio;
    }

    public ConfigurazioneDominioDto idDominio(Long idDominio) {

	this.idDominio = idDominio;
	return this;
    }

    /**
     * Get idEnte
     * 
     * @return idEnte
     **/
    @XmlElement(name = "idEnte")
    public Long getIdEnte() {

	return idEnte;
    }

    public void setIdEnte(Long idEnte) {

	this.idEnte = idEnte;
    }

    public ConfigurazioneDominioDto idEnte(Long idEnte) {

	this.idEnte = idEnte;
	return this;
    }

    /**
     * Get importoMinimo
     * 
     * @return importoMinimo
     **/
    @XmlElement(name = "importoMinimo")
    public Double getImportoMinimo() {

	return importoMinimo;
    }

    public void setImportoMinimo(Double importoMinimo) {

	this.importoMinimo = importoMinimo;
    }

    public ConfigurazioneDominioDto importoMinimo(Double importoMinimo) {

	this.importoMinimo = importoMinimo;
	return this;
    }

    /**
     * Get indirizzo
     * 
     * @return indirizzo
     **/
    @XmlElement(name = "indirizzo")
    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    public ConfigurazioneDominioDto indirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
	return this;
    }

    /**
     * Get intestatarioCcPostale
     * 
     * @return intestatarioCcPostale
     **/
    @XmlElement(name = "intestatarioCcPostale")
    public String getIntestatarioCcPostale() {

	return intestatarioCcPostale;
    }

    public void setIntestatarioCcPostale(String intestatarioCcPostale) {

	this.intestatarioCcPostale = intestatarioCcPostale;
    }

    public ConfigurazioneDominioDto intestatarioCcPostale(String intestatarioCcPostale) {

	this.intestatarioCcPostale = intestatarioCcPostale;
	return this;
    }

    /**
     * Get intestazione
     * 
     * @return intestazione
     **/
    @XmlElement(name = "intestazione")
    public String getIntestazione() {

	return intestazione;
    }

    public void setIntestazione(String intestazione) {

	this.intestazione = intestazione;
    }

    public ConfigurazioneDominioDto intestazione(String intestazione) {

	this.intestazione = intestazione;
	return this;
    }

    /**
     * Get maxRate
     * 
     * @return maxRate
     **/
    @XmlElement(name = "maxRate")
    public Integer getMaxRate() {

	return maxRate;
    }

    public void setMaxRate(Integer maxRate) {

	this.maxRate = maxRate;
    }

    public ConfigurazioneDominioDto maxRate(Integer maxRate) {

	this.maxRate = maxRate;
	return this;
    }

    /**
     * Get modelliPagamento
     * 
     * @return modelliPagamento
     **/
    @XmlElement(name = "modelliPagamento")
    public String getModelliPagamento() {

	return modelliPagamento;
    }

    public void setModelliPagamento(String modelliPagamento) {

	this.modelliPagamento = modelliPagamento;
    }

    public ConfigurazioneDominioDto modelliPagamento(String modelliPagamento) {

	this.modelliPagamento = modelliPagamento;
	return this;
    }

    /**
     * Get nazione
     * 
     * @return nazione
     **/
    @XmlElement(name = "nazione")
    public String getNazione() {

	return nazione;
    }

    public void setNazione(String nazione) {

	this.nazione = nazione;
    }

    public ConfigurazioneDominioDto nazione(String nazione) {

	this.nazione = nazione;
	return this;
    }

    /**
     * Get numeroCcPostale
     * 
     * @return numeroCcPostale
     **/
    @XmlElement(name = "numeroCcPostale")
    public String getNumeroCcPostale() {

	return numeroCcPostale;
    }

    public void setNumeroCcPostale(String numeroCcPostale) {

	this.numeroCcPostale = numeroCcPostale;
    }

    public ConfigurazioneDominioDto numeroCcPostale(String numeroCcPostale) {

	this.numeroCcPostale = numeroCcPostale;
	return this;
    }

    /**
     * Get pec
     * 
     * @return pec
     **/
    @XmlElement(name = "pec")
    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }

    public ConfigurazioneDominioDto pec(String pec) {

	this.pec = pec;
	return this;
    }

    /**
     * Get piva
     * 
     * @return piva
     **/
    @XmlElement(name = "piva")
    public String getPiva() {

	return piva;
    }

    public void setPiva(String piva) {

	this.piva = piva;
    }

    public ConfigurazioneDominioDto piva(String piva) {

	this.piva = piva;
	return this;
    }

    /**
     * Get provincia
     * 
     * @return provincia
     **/
    @XmlElement(name = "provincia")
    public String getProvincia() {

	return provincia;
    }

    public void setProvincia(String provincia) {

	this.provincia = provincia;
    }

    public ConfigurazioneDominioDto provincia(String provincia) {

	this.provincia = provincia;
	return this;
    }

    /**
     * Get rateObbligatorie
     * 
     * @return rateObbligatorie
     **/
    @XmlElement(name = "rateObbligatorie")
    public Integer getRateObbligatorie() {

	return rateObbligatorie;
    }

    public void setRateObbligatorie(Integer rateObbligatorie) {

	this.rateObbligatorie = rateObbligatorie;
    }

    public ConfigurazioneDominioDto rateObbligatorie(Integer rateObbligatorie) {

	this.rateObbligatorie = rateObbligatorie;
	return this;
    }

    /**
     * Get riferim1
     * 
     * @return riferim1
     **/
    @XmlElement(name = "riferim1")
    public String getRiferim1() {

	return riferim1;
    }

    public void setRiferim1(String riferim1) {

	this.riferim1 = riferim1;
    }

    public ConfigurazioneDominioDto riferim1(String riferim1) {

	this.riferim1 = riferim1;
	return this;
    }

    /**
     * Get riferim2
     * 
     * @return riferim2
     **/
    @XmlElement(name = "riferim2")
    public String getRiferim2() {

	return riferim2;
    }

    public void setRiferim2(String riferim2) {

	this.riferim2 = riferim2;
    }

    public ConfigurazioneDominioDto riferim2(String riferim2) {

	this.riferim2 = riferim2;
	return this;
    }

    /**
     * Get riferim3
     * 
     * @return riferim3
     **/
    @XmlElement(name = "riferim3")
    public String getRiferim3() {

	return riferim3;
    }

    public void setRiferim3(String riferim3) {

	this.riferim3 = riferim3;
    }

    public ConfigurazioneDominioDto riferim3(String riferim3) {

	this.riferim3 = riferim3;
	return this;
    }

    /**
     * Get riferim4
     * 
     * @return riferim4
     **/
    @XmlElement(name = "riferim4")
    public String getRiferim4() {

	return riferim4;
    }

    public void setRiferim4(String riferim4) {

	this.riferim4 = riferim4;
    }

    public ConfigurazioneDominioDto riferim4(String riferim4) {

	this.riferim4 = riferim4;
	return this;
    }

    /**
     * Get riferim5
     * 
     * @return riferim5
     **/
    @XmlElement(name = "riferim5")
    public String getRiferim5() {

	return riferim5;
    }

    public void setRiferim5(String riferim5) {

	this.riferim5 = riferim5;
    }

    public ConfigurazioneDominioDto riferim5(String riferim5) {

	this.riferim5 = riferim5;
	return this;
    }

    /**
     * Get riferim6
     * 
     * @return riferim6
     **/
    @XmlElement(name = "riferim6")
    public String getRiferim6() {

	return riferim6;
    }

    public void setRiferim6(String riferim6) {

	this.riferim6 = riferim6;
    }

    public ConfigurazioneDominioDto riferim6(String riferim6) {

	this.riferim6 = riferim6;
	return this;
    }

    /**
     * Get riferim7
     * 
     * @return riferim7
     **/
    @XmlElement(name = "riferim7")
    public String getRiferim7() {

	return riferim7;
    }

    public void setRiferim7(String riferim7) {

	this.riferim7 = riferim7;
    }

    public ConfigurazioneDominioDto riferim7(String riferim7) {

	this.riferim7 = riferim7;
	return this;
    }

    /**
     * Get riferim8
     * 
     * @return riferim8
     **/
    @XmlElement(name = "riferim8")
    public String getRiferim8() {

	return riferim8;
    }

    public void setRiferim8(String riferim8) {

	this.riferim8 = riferim8;
    }

    public ConfigurazioneDominioDto riferim8(String riferim8) {

	this.riferim8 = riferim8;
	return this;
    }

    /**
     * Get settore
     * 
     * @return settore
     **/
    @XmlElement(name = "settore")
    public String getSettore() {

	return settore;
    }

    public void setSettore(String settore) {

	this.settore = settore;
    }

    public ConfigurazioneDominioDto settore(String settore) {

	this.settore = settore;
	return this;
    }

    /**
     * Get sitoWeb
     * 
     * @return sitoWeb
     **/
    @XmlElement(name = "sitoWeb")
    public String getSitoWeb() {

	return sitoWeb;
    }

    public void setSitoWeb(String sitoWeb) {

	this.sitoWeb = sitoWeb;
    }

    public ConfigurazioneDominioDto sitoWeb(String sitoWeb) {

	this.sitoWeb = sitoWeb;
	return this;
    }

    /**
     * Get sitoWebEnte
     * 
     * @return sitoWebEnte
     **/
    @XmlElement(name = "sitoWebEnte")
    public String getSitoWebEnte() {

	return sitoWebEnte;
    }

    public void setSitoWebEnte(String sitoWebEnte) {

	this.sitoWebEnte = sitoWebEnte;
    }

    public ConfigurazioneDominioDto sitoWebEnte(String sitoWebEnte) {

	this.sitoWebEnte = sitoWebEnte;
	return this;
    }

    /**
     * Get telefono
     * 
     * @return telefono
     **/
    @XmlElement(name = "telefono")
    public String getTelefono() {

	return telefono;
    }

    public void setTelefono(String telefono) {

	this.telefono = telefono;
    }

    public ConfigurazioneDominioDto telefono(String telefono) {

	this.telefono = telefono;
	return this;
    }

    /**
     * Get telefonoContatto1
     * 
     * @return telefonoContatto1
     **/
    @XmlElement(name = "telefonoContatto1")
    public String getTelefonoContatto1() {

	return telefonoContatto1;
    }

    public void setTelefonoContatto1(String telefonoContatto1) {

	this.telefonoContatto1 = telefonoContatto1;
    }

    public ConfigurazioneDominioDto telefonoContatto1(String telefonoContatto1) {

	this.telefonoContatto1 = telefonoContatto1;
	return this;
    }

    /**
     * Get telefonoContatto2
     * 
     * @return telefonoContatto2
     **/
    @XmlElement(name = "telefonoContatto2")
    public String getTelefonoContatto2() {

	return telefonoContatto2;
    }

    public void setTelefonoContatto2(String telefonoContatto2) {

	this.telefonoContatto2 = telefonoContatto2;
    }

    public ConfigurazioneDominioDto telefonoContatto2(String telefonoContatto2) {

	this.telefonoContatto2 = telefonoContatto2;
	return this;
    }

    /**
     * Get telefonoContatto3
     * 
     * @return telefonoContatto3
     **/
    @XmlElement(name = "telefonoContatto3")
    public String getTelefonoContatto3() {

	return telefonoContatto3;
    }

    public void setTelefonoContatto3(String telefonoContatto3) {

	this.telefonoContatto3 = telefonoContatto3;
    }

    public ConfigurazioneDominioDto telefonoContatto3(String telefonoContatto3) {

	this.telefonoContatto3 = telefonoContatto3;
	return this;
    }

    /**
     * Get telefonoContatto4
     * 
     * @return telefonoContatto4
     **/
    @XmlElement(name = "telefonoContatto4")
    public String getTelefonoContatto4() {

	return telefonoContatto4;
    }

    public void setTelefonoContatto4(String telefonoContatto4) {

	this.telefonoContatto4 = telefonoContatto4;
    }

    public ConfigurazioneDominioDto telefonoContatto4(String telefonoContatto4) {

	this.telefonoContatto4 = telefonoContatto4;
	return this;
    }

    /**
     * Get telefonoContatto5
     * 
     * @return telefonoContatto5
     **/
    @XmlElement(name = "telefonoContatto5")
    public String getTelefonoContatto5() {

	return telefonoContatto5;
    }

    public void setTelefonoContatto5(String telefonoContatto5) {

	this.telefonoContatto5 = telefonoContatto5;
    }

    public ConfigurazioneDominioDto telefonoContatto5(String telefonoContatto5) {

	this.telefonoContatto5 = telefonoContatto5;
	return this;
    }

    /**
     * Get telefonoContatto6
     * 
     * @return telefonoContatto6
     **/
    @XmlElement(name = "telefonoContatto6")
    public String getTelefonoContatto6() {

	return telefonoContatto6;
    }

    public void setTelefonoContatto6(String telefonoContatto6) {

	this.telefonoContatto6 = telefonoContatto6;
    }

    public ConfigurazioneDominioDto telefonoContatto6(String telefonoContatto6) {

	this.telefonoContatto6 = telefonoContatto6;
	return this;
    }

    /**
     * Get telefonoContatto7
     * 
     * @return telefonoContatto7
     **/
    @XmlElement(name = "telefonoContatto7")
    public String getTelefonoContatto7() {

	return telefonoContatto7;
    }

    public void setTelefonoContatto7(String telefonoContatto7) {

	this.telefonoContatto7 = telefonoContatto7;
    }

    public ConfigurazioneDominioDto telefonoContatto7(String telefonoContatto7) {

	this.telefonoContatto7 = telefonoContatto7;
	return this;
    }

    /**
     * Get telefonoContatto8
     * 
     * @return telefonoContatto8
     **/
    @XmlElement(name = "telefonoContatto8")
    public String getTelefonoContatto8() {

	return telefonoContatto8;
    }

    public void setTelefonoContatto8(String telefonoContatto8) {

	this.telefonoContatto8 = telefonoContatto8;
    }

    public ConfigurazioneDominioDto telefonoContatto8(String telefonoContatto8) {

	this.telefonoContatto8 = telefonoContatto8;
	return this;
    }

    /**
     * Get testoLiberoBeneficiario
     * 
     * @return testoLiberoBeneficiario
     **/
    @XmlElement(name = "testoLiberoBeneficiario")
    public String getTestoLiberoBeneficiario() {

	return testoLiberoBeneficiario;
    }

    public void setTestoLiberoBeneficiario(String testoLiberoBeneficiario) {

	this.testoLiberoBeneficiario = testoLiberoBeneficiario;
    }

    public ConfigurazioneDominioDto testoLiberoBeneficiario(String testoLiberoBeneficiario) {

	this.testoLiberoBeneficiario = testoLiberoBeneficiario;
	return this;
    }

    /**
     * Get tipoAvvisatura
     * 
     * @return tipoAvvisatura
     **/
    @XmlElement(name = "tipoAvvisatura")
    public Integer getTipoAvvisatura() {

	return tipoAvvisatura;
    }

    public void setTipoAvvisatura(Integer tipoAvvisatura) {

	this.tipoAvvisatura = tipoAvvisatura;
    }

    public ConfigurazioneDominioDto tipoAvvisatura(Integer tipoAvvisatura) {

	this.tipoAvvisatura = tipoAvvisatura;
	return this;
    }

    /**
     * Get tipoContabilita
     * 
     * @return tipoContabilita
     **/
    @XmlElement(name = "tipoContabilita")
    public Integer getTipoContabilita() {

	return tipoContabilita;
    }

    public void setTipoContabilita(Integer tipoContabilita) {

	this.tipoContabilita = tipoContabilita;
    }

    public ConfigurazioneDominioDto tipoContabilita(Integer tipoContabilita) {

	this.tipoContabilita = tipoContabilita;
	return this;
    }

    /**
     * Get tipoPartitario
     * 
     * @return tipoPartitario
     **/
    @XmlElement(name = "tipoPartitario")
    public String getTipoPartitario() {

	return tipoPartitario;
    }

    public void setTipoPartitario(String tipoPartitario) {

	this.tipoPartitario = tipoPartitario;
    }

    public ConfigurazioneDominioDto tipoPartitario(String tipoPartitario) {

	this.tipoPartitario = tipoPartitario;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class ConfigurazioneDominioDto {\n");
	sb.append("    autDataDaPoste: ").append(toIndentedString(autDataDaPoste)).append("\n");
	sb.append("    avvisaturaImmediata: ").append(toIndentedString(avvisaturaImmediata)).append("\n");
	sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
	sb.append("    cf: ").append(toIndentedString(cf)).append("\n");
	sb.append("    codiceInterbancario: ").append(toIndentedString(codiceInterbancario)).append("\n");
	sb.append("    commissionePa: ").append(toIndentedString(commissionePa)).append("\n");
	sb.append("    comune: ").append(toIndentedString(comune)).append("\n");
	sb.append("    dataSospensione: ").append(toIndentedString(dataSospensione)).append("\n");
	sb.append("    dataValidita: ").append(toIndentedString(dataValidita)).append("\n");
	sb.append("    datiSpecificiIncasso: ").append(toIndentedString(datiSpecificiIncasso)).append("\n");
	sb.append("    denominazioneContatto1: ").append(toIndentedString(denominazioneContatto1)).append("\n");
	sb.append("    denominazioneContatto2: ").append(toIndentedString(denominazioneContatto2)).append("\n");
	sb.append("    denominazioneContatto3: ").append(toIndentedString(denominazioneContatto3)).append("\n");
	sb.append("    denominazioneContatto4: ").append(toIndentedString(denominazioneContatto4)).append("\n");
	sb.append("    denominazioneContatto5: ").append(toIndentedString(denominazioneContatto5)).append("\n");
	sb.append("    denominazioneContatto6: ").append(toIndentedString(denominazioneContatto6)).append("\n");
	sb.append("    denominazioneContatto7: ").append(toIndentedString(denominazioneContatto7)).append("\n");
	sb.append("    denominazioneContatto8: ").append(toIndentedString(denominazioneContatto8)).append("\n");
	sb.append("    descrizionePartitario: ").append(toIndentedString(descrizionePartitario)).append("\n");
	sb.append("    email: ").append(toIndentedString(email)).append("\n");
	sb.append("    emailContatto1: ").append(toIndentedString(emailContatto1)).append("\n");
	sb.append("    emailContatto2: ").append(toIndentedString(emailContatto2)).append("\n");
	sb.append("    emailContatto3: ").append(toIndentedString(emailContatto3)).append("\n");
	sb.append("    emailContatto4: ").append(toIndentedString(emailContatto4)).append("\n");
	sb.append("    emailContatto5: ").append(toIndentedString(emailContatto5)).append("\n");
	sb.append("    emailContatto6: ").append(toIndentedString(emailContatto6)).append("\n");
	sb.append("    emailContatto7: ").append(toIndentedString(emailContatto7)).append("\n");
	sb.append("    emailContatto8: ").append(toIndentedString(emailContatto8)).append("\n");
	sb.append("    flagServizio: ").append(toIndentedString(flagServizio)).append("\n");
	sb.append("    idConfigurazione: ").append(toIndentedString(idConfigurazione)).append("\n");
	sb.append("    idDominio: ").append(toIndentedString(idDominio)).append("\n");
	sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
	sb.append("    importoMinimo: ").append(toIndentedString(importoMinimo)).append("\n");
	sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
	sb.append("    intestatarioCcPostale: ").append(toIndentedString(intestatarioCcPostale)).append("\n");
	sb.append("    intestazione: ").append(toIndentedString(intestazione)).append("\n");
	sb.append("    maxRate: ").append(toIndentedString(maxRate)).append("\n");
	sb.append("    modelliPagamento: ").append(toIndentedString(modelliPagamento)).append("\n");
	sb.append("    nazione: ").append(toIndentedString(nazione)).append("\n");
	sb.append("    numeroCcPostale: ").append(toIndentedString(numeroCcPostale)).append("\n");
	sb.append("    pec: ").append(toIndentedString(pec)).append("\n");
	sb.append("    piva: ").append(toIndentedString(piva)).append("\n");
	sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
	sb.append("    rateObbligatorie: ").append(toIndentedString(rateObbligatorie)).append("\n");
	sb.append("    riferim1: ").append(toIndentedString(riferim1)).append("\n");
	sb.append("    riferim2: ").append(toIndentedString(riferim2)).append("\n");
	sb.append("    riferim3: ").append(toIndentedString(riferim3)).append("\n");
	sb.append("    riferim4: ").append(toIndentedString(riferim4)).append("\n");
	sb.append("    riferim5: ").append(toIndentedString(riferim5)).append("\n");
	sb.append("    riferim6: ").append(toIndentedString(riferim6)).append("\n");
	sb.append("    riferim7: ").append(toIndentedString(riferim7)).append("\n");
	sb.append("    riferim8: ").append(toIndentedString(riferim8)).append("\n");
	sb.append("    settore: ").append(toIndentedString(settore)).append("\n");
	sb.append("    sitoWeb: ").append(toIndentedString(sitoWeb)).append("\n");
	sb.append("    sitoWebEnte: ").append(toIndentedString(sitoWebEnte)).append("\n");
	sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
	sb.append("    telefonoContatto1: ").append(toIndentedString(telefonoContatto1)).append("\n");
	sb.append("    telefonoContatto2: ").append(toIndentedString(telefonoContatto2)).append("\n");
	sb.append("    telefonoContatto3: ").append(toIndentedString(telefonoContatto3)).append("\n");
	sb.append("    telefonoContatto4: ").append(toIndentedString(telefonoContatto4)).append("\n");
	sb.append("    telefonoContatto5: ").append(toIndentedString(telefonoContatto5)).append("\n");
	sb.append("    telefonoContatto6: ").append(toIndentedString(telefonoContatto6)).append("\n");
	sb.append("    telefonoContatto7: ").append(toIndentedString(telefonoContatto7)).append("\n");
	sb.append("    telefonoContatto8: ").append(toIndentedString(telefonoContatto8)).append("\n");
	sb.append("    testoLiberoBeneficiario: ").append(toIndentedString(testoLiberoBeneficiario)).append("\n");
	sb.append("    tipoAvvisatura: ").append(toIndentedString(tipoAvvisatura)).append("\n");
	sb.append("    tipoContabilita: ").append(toIndentedString(tipoContabilita)).append("\n");
	sb.append("    tipoPartitario: ").append(toIndentedString(tipoPartitario)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private static String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
