package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class AutorizzazioniFrontRestBean {

    private Integer id;
    private String numero;
    private Integer anno;
    private String data;
    private String comuneRilascio;
    private String numeroAutOrig;
    private String statoAutorizzazione;
    private String causaleSospensione;
    private boolean avviso;
    private boolean validaPerSpunta;
    private String ruoloAutorizzazione;
    private String autPrecedenteNumero;
    private String autPrecedenteComune;
    private AnagraferestBean proprietario;
    private AnagraferestBean gerente;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Integer getAnno() {

	return anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public String getData() {

	return data;
    }

    public void setData(String data) {

	this.data = data;
    }

    public String getComuneRilascio() {

	return comuneRilascio;
    }

    public void setComuneRilascio(String comuneRilascio) {

	this.comuneRilascio = comuneRilascio;
    }

    public String getNumeroAutOrig() {

	return numeroAutOrig;
    }

    public void setNumeroAutOrig(String numeroAutOrig) {

	this.numeroAutOrig = numeroAutOrig;
    }

    public String getStatoAutorizzazione() {

	return statoAutorizzazione;
    }

    public void setStatoAutorizzazione(String statoAutorizzazione) {

	this.statoAutorizzazione = statoAutorizzazione;
    }

    public String getRuoloAutorizzazione() {

	return ruoloAutorizzazione;
    }

    public void setRuoloAutorizzazione(String ruoloAutorizzazione) {

	this.ruoloAutorizzazione = ruoloAutorizzazione;
    }

    public AnagraferestBean getProprietario() {

	return proprietario;
    }

    public void setProprietario(AnagraferestBean proprietario) {

	this.proprietario = proprietario;
    }

    public AnagraferestBean getGerente() {

	return gerente;
    }

    public void setGerente(AnagraferestBean gerente) {

	this.gerente = gerente;
    }

    public String getAutPrecedenteNumero() {

	return autPrecedenteNumero;
    }

    public void setAutPrecedenteNumero(String autPrecedenteNumero) {

	this.autPrecedenteNumero = autPrecedenteNumero;
    }

    public String getAutPrecedenteComune() {

	return autPrecedenteComune;
    }

    public void setAutPrecedenteComune(String autPrecedenteComune) {

	this.autPrecedenteComune = autPrecedenteComune;
    }

    public String getCausaleSospensione() {

	return causaleSospensione;
    }

    public void setCausaleSospensione(String causaleSospensione) {

	this.causaleSospensione = causaleSospensione;
    }

    public boolean getAvviso() {

	return avviso;
    }

    public void setAvviso(boolean avviso) {

	this.avviso = avviso;
    }

    public boolean getValidaPerSpunta() {

	return validaPerSpunta;
    }

    public void setValidaPerSpunta(boolean validaPerSpunta) {

	this.validaPerSpunta = validaPerSpunta;
    }
}
