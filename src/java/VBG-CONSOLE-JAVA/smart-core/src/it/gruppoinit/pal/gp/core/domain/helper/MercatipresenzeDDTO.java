package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class MercatipresenzeDDTO {

    private PkId id;
    private MercatipresenzeTDTO mercatiPresenzeT;
    private MercatiDDTO posteggio;
    private AnagrafeDTO occupante;
    private AnagrafeDTO concessionario;
    private Boolean spuntista;
    private Boolean flagAssenzaGiust;
    private String motivazione;
    private Boolean presente;
    private Integer proprietario;
    private String catMerc;
    private AutorizzazioniDTO autorizzazioni;
    private AutorizzazioniDTO autorizzazioneConcessionarioAssente;
    private AutorizzazioniDTO transientAutDaSchedaDyn;

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
}
