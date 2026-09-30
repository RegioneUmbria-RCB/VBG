package it.gruppoinit.pal.gp.core.domain.helper;

import java.math.BigDecimal;
import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class MercatipresenzeDDTO {

    private PkId id;
    private MercatipresenzeTDTO mercatiPresenzeT;
    private MercatiDDTO posteggio;
    private AnagrafeDTO occupante;
    private AnagrafeDTO concessionario;
    private AnagrafeDTO collaboratore;
    private Boolean spuntista;
    private Boolean flagAssenzaGiust;
    private String motivazione;
    private Boolean presente;
    private Integer proprietario;
    private Integer numeropresenze;
    private String catMerc;
    private AutorizzazioniDTO autorizzazioni;
    private AutorizzazioniDTO autorizzazioneConcessionarioAssente;
    private String faseSpunta;
    private AutorizzazioniDTO transientAutDaSchedaDyn;
    private Boolean flagPagato;
    private BigDecimal importo;
    private Boolean flagRinunciaPresenza;
    private String codPostRinunciato;
    // AutorizzazioniCSI
    private AnagrafeDTO gerente;
    private String statoAutorizzazione;
    private String statoWarning;
    private Date dataSospDa;
    private Date dataSospA;
    private Date dataFineGerenza;
    private String causaleSospensione;
    private Boolean validaSpunta;
    private Date dataInizioGerenza;
    // AutorizzazioniCSI CONCESSIONARIO
    private AnagrafeDTO gerentecon;
    private String statoautorizzazioneconc;
    private String statowarningcon;
    private Date datasospdacon;
    private Date datasospacon;
    private Date datafinegerenzacon;
    private String causalesospensionecon;
    private Boolean validaspuntacon;
    private Date datainiziogerenzacon;
    //AUTORIZZAZIONI_CSI AUT PREC
    private String autprecedentenumero;
    private Date autprecedentedata;
    private String autprecedentecomune;
    // i dati del gerente dello spuntista e concessionario della presenza di giornata
    private AnagrafeDTO gerentePresD;
    private AnagrafeDTO gerenteConcPresD;
    private String protocolloAut;
    private Date dataProtocolloAut;
    private String protocolloAutCon;
    private Date dataProtocolloAutCon;
    private Integer dettPosizioneDebitoriaId;

    //    AUT_PRECEDENTE_NUMERO
    //    AUT_PRECEDENTE_DATA
    //    AUT_PRECEDENTE_COMUNE   
    public MercatipresenzeDDTO() {

	this.id = new PkId();
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public MercatipresenzeTDTO getMercatiPresenzeT() {

	return mercatiPresenzeT;
    }

    public void setMercatiPresenzeT(MercatipresenzeTDTO mercatiPresenzeT) {

	this.mercatiPresenzeT = mercatiPresenzeT;
    }

    public MercatiDDTO getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(MercatiDDTO posteggio) {

	this.posteggio = posteggio;
    }

    public AnagrafeDTO getOccupante() {

	return occupante;
    }

    public void setOccupante(AnagrafeDTO occupante) {

	this.occupante = occupante;
    }

    public AnagrafeDTO getConcessionario() {

	return concessionario;
    }

    public void setConcessionario(AnagrafeDTO concessionario) {

	this.concessionario = concessionario;
    }

    public AnagrafeDTO getCollaboratore() {

	return collaboratore;
    }

    public void setCollaboratore(AnagrafeDTO collaboratore) {

	this.collaboratore = collaboratore;
    }

    public Boolean getSpuntista() {

	return spuntista;
    }

    public void setSpuntista(Boolean spuntista) {

	this.spuntista = spuntista;
    }

    public Boolean getFlagAssenzaGiust() {

	return flagAssenzaGiust;
    }

    public void setFlagAssenzaGiust(Boolean flagAssenzaGiust) {

	this.flagAssenzaGiust = flagAssenzaGiust;
    }

    public String getMotivazione() {

	return motivazione;
    }

    public void setMotivazione(String motivazione) {

	this.motivazione = motivazione;
    }

    public Boolean getPresente() {

	return presente;
    }

    public void setPresente(Boolean presente) {

	this.presente = presente;
    }

    public Integer getProprietario() {

	return proprietario;
    }

    public void setProprietario(Integer proprietario) {

	this.proprietario = proprietario;
    }

    public Integer getNumeropresenze() {

	return numeropresenze;
    }

    public void setNumeropresenze(Integer numeropresenze) {

	this.numeropresenze = numeropresenze;
    }

    public String getCatMerc() {

	return catMerc;
    }

    public void setCatMerc(String catMerc) {

	this.catMerc = catMerc;
    }

    public AutorizzazioniDTO getAutorizzazioni() {

	return autorizzazioni;
    }

    public void setAutorizzazioni(AutorizzazioniDTO autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    public AutorizzazioniDTO getAutorizzazioneConcessionarioAssente() {

	return autorizzazioneConcessionarioAssente;
    }

    public void setAutorizzazioneConcessionarioAssente(AutorizzazioniDTO autorizzazioneConcessionarioAssente) {

	this.autorizzazioneConcessionarioAssente = autorizzazioneConcessionarioAssente;
    }

    public AutorizzazioniDTO getTransientAutDaSchedaDyn() {

	return transientAutDaSchedaDyn;
    }

    public void setTransientAutDaSchedaDyn(AutorizzazioniDTO transientAutDaSchedaDyn) {

	this.transientAutDaSchedaDyn = transientAutDaSchedaDyn;
    }

    public String getFaseSpunta() {

	return faseSpunta;
    }

    public void setFaseSpunta(String faseSpunta) {

	this.faseSpunta = faseSpunta;
    }

    public Boolean getFlagPagato() {

	return flagPagato;
    }

    public void setFlagPagato(Boolean flagPagato) {

	this.flagPagato = flagPagato;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public Boolean getFlagRinunciaPresenza() {

	return flagRinunciaPresenza;
    }

    public void setFlagRinunciaPresenza(Boolean flagRinunciaPresenza) {

	this.flagRinunciaPresenza = flagRinunciaPresenza;
    }

    public String getCodPostRinunciato() {

	return codPostRinunciato;
    }

    public void setCodPostRinunciato(String codPostRinunciato) {

	this.codPostRinunciato = codPostRinunciato;
    }

    // Campi auto CSI
    public AnagrafeDTO getGerente() {

	return gerente;
    }

    public void setGerente(AnagrafeDTO gerente) {

	this.gerente = gerente;
    }

    public String getStatoAutorizzazione() {

	return statoAutorizzazione;
    }

    public void setStatoAutorizzazione(String statoAutorizzazione) {

	this.statoAutorizzazione = statoAutorizzazione;
    }

    public String getStatoWarning() {

	return statoWarning;
    }

    public void setStatoWarning(String statoWarning) {

	this.statoWarning = statoWarning;
    }

    public Date getDataSospDa() {

	return dataSospDa;
    }

    public void setDataSospDa(Date dataSospDa) {

	this.dataSospDa = dataSospDa;
    }

    public Date getDataSospA() {

	return dataSospA;
    }

    public void setDataSospA(Date dataSospA) {

	this.dataSospA = dataSospA;
    }

    public Date getDataFineGerenza() {

	return dataFineGerenza;
    }

    public void setDataFineGerenza(Date dataFineGerenza) {

	this.dataFineGerenza = dataFineGerenza;
    }

    public String getCausaleSospensione() {

	return causaleSospensione;
    }

    public void setCausaleSospensione(String causaleSospensione) {

	this.causaleSospensione = causaleSospensione;
    }

    public Boolean getValidaSpunta() {

	return validaSpunta;
    }

    public void setValidaSpunta(Boolean validaSpunta) {

	this.validaSpunta = validaSpunta;
    }

    public Date getDataInizioGerenza() {

	return dataInizioGerenza;
    }

    public void setDataInizioGerenza(Date dataInizioGerenza) {

	this.dataInizioGerenza = dataInizioGerenza;
    }

    // autorizzazioni csi concessionario
    public AnagrafeDTO getGerentecon() {

	return gerentecon;
    }

    public void setGerentecon(AnagrafeDTO gerentecon) {

	this.gerentecon = gerentecon;
    }

    public String getStatoautorizzazioneconc() {

	return statoautorizzazioneconc;
    }

    public void setStatoautorizzazioneconc(String statoautorizzazioneconc) {

	this.statoautorizzazioneconc = statoautorizzazioneconc;
    }

    public String getStatowarningcon() {

	return statowarningcon;
    }

    public void setStatowarningcon(String statowarningcon) {

	this.statowarningcon = statowarningcon;
    }

    public Date getDatasospdacon() {

	return datasospdacon;
    }

    public void setDatasospdacon(Date datasospdacon) {

	this.datasospdacon = datasospdacon;
    }

    public Date getDatasospacon() {

	return datasospacon;
    }

    public void setDatasospacon(Date datasospacon) {

	this.datasospacon = datasospacon;
    }

    public Date getDatafinegerenzacon() {

	return datafinegerenzacon;
    }

    public void setDatafinegerenzacon(Date datafinegerenzacon) {

	this.datafinegerenzacon = datafinegerenzacon;
    }

    public String getCausalesospensionecon() {

	return causalesospensionecon;
    }

    public void setCausalesospensionecon(String causalesospensionecon) {

	this.causalesospensionecon = causalesospensionecon;
    }

    public Boolean getValidaspuntacon() {

	return validaspuntacon;
    }

    public void setValidaspuntacon(Boolean validaspuntacon) {

	this.validaspuntacon = validaspuntacon;
    }

    public Date getDatainiziogerenzacon() {

	return datainiziogerenzacon;
    }

    public void setDatainiziogerenzacon(Date datainiziogerenzacon) {

	this.datainiziogerenzacon = datainiziogerenzacon;
    }

    public String getAutprecedentenumero() {

	return autprecedentenumero;
    }

    public void setAutprecedentenumero(String autprecedentenumero) {

	this.autprecedentenumero = autprecedentenumero;
    }

    public Date getAutprecedentedata() {

	return autprecedentedata;
    }

    public void setAutprecedentedata(Date autprecedentedata) {

	this.autprecedentedata = autprecedentedata;
    }

    public String getAutprecedentecomune() {

	return autprecedentecomune;
    }

    public void setAutprecedentecomune(String autprecedentecomune) {

	this.autprecedentecomune = autprecedentecomune;
    }

    public AnagrafeDTO getGerentePresD() {

	return gerentePresD;
    }

    public void setGerentePresD(AnagrafeDTO gerentePresD) {

	this.gerentePresD = gerentePresD;
    }

    public AnagrafeDTO getGerenteConcPresD() {

	return gerenteConcPresD;
    }

    public void setGerenteConcPresD(AnagrafeDTO gerenteConcPresD) {

	this.gerenteConcPresD = gerenteConcPresD;
    }

    public String getProtocolloAut() {

	return protocolloAut;
    }

    public void setProtocolloAut(String protocolloAut) {

	this.protocolloAut = protocolloAut;
    }

    public Date getDataProtocolloAut() {

	return dataProtocolloAut;
    }

    public void setDataProtocolloAut(Date dataProtocolloAut) {

	this.dataProtocolloAut = dataProtocolloAut;
    }

    public String getProtocolloAutCon() {

	return protocolloAutCon;
    }

    public void setProtocolloAutCon(String protocolloAutCon) {

	this.protocolloAutCon = protocolloAutCon;
    }

    public Date getDataProtocolloAutCon() {

	return dataProtocolloAutCon;
    }

    public void setDataProtocolloAutCon(Date dataProtocolloAutCon) {

	this.dataProtocolloAutCon = dataProtocolloAutCon;
    }

    public Integer getDettPosizioneDebitoriaId() {

	return dettPosizioneDebitoriaId;
    }

    public void setDettPosizioneDebitoriaId(Integer dettPosizioneDebitoriaId) {

	this.dettPosizioneDebitoriaId = dettPosizioneDebitoriaId;
    }
}
