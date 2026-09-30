package it.gruppoinit.pal.gp.core.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.validator.NotNull;

/*
 * La classe non è una classe di dominio ma è utilizzata per memorizzare dei dati da mettere in sessione per il calcolo
 * di alcuni parametri
 */
public class Registrazionimercato {

    private Integer step;
    private Integer anno;
    private TipiScadenza tipiScadenza;
    private Periodicita periodicita;
    private RegistrazioniCausali registrazioniCausali;
    private MercatiUso mercatiUso;
    private List<MercatipresenzeT> MercatipresenzeTList;
    private Responsabili utenteLoggato;
    private Date dataRegistrazione;
    private Integer tipoCalcolo;
    private Integer tipoCalcoloAnnualeScadenzaRate;
    private Boolean imputaConsorzio;

    // 1 fine mese
    // 2 15 mese
    public Registrazionimercato() {

	this.anno = (new GregorianCalendar()).get(Calendar.YEAR);
	this.periodicita = new Periodicita();
	this.tipiScadenza = new TipiScadenza();
	this.registrazioniCausali = new RegistrazioniCausali();
	this.utenteLoggato = new Responsabili();
	this.mercatiUso = new MercatiUso();
    }

    public Integer getStep() {

	return step;
    }

    public void setStep(Integer step) {

	this.step = step;
    }

    public Integer getAnno() {

	return anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    public TipiScadenza getTipiScadenza() {

	return tipiScadenza;
    }

    public void setTipiScadenza(TipiScadenza tipiScadenza) {

	this.tipiScadenza = tipiScadenza;
    }

    @NotNull
    public Periodicita getPeriodicita() {

	return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {

	this.periodicita = periodicita;
    }

    public RegistrazioniCausali getRegistrazioniCausali() {

	return registrazioniCausali;
    }

    public void setRegistrazioniCausali(RegistrazioniCausali registrazioniCausali) {

	this.registrazioniCausali = registrazioniCausali;
    }

    public void setUtenteLoggato(Responsabili utenteLoggato) {

	this.utenteLoggato = utenteLoggato;
    }

    public Responsabili getUtenteLoggato() {

	return utenteLoggato;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public Date getDataRegistrazione() {

	return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {

	this.dataRegistrazione = dataRegistrazione;
    }

    public List<MercatipresenzeT> getMercatipresenzeTList() {

	return MercatipresenzeTList;
    }

    public void setMercatipresenzeTList(List<MercatipresenzeT> mercatipresenzeTList) {

	MercatipresenzeTList = mercatipresenzeTList;
    }

    public Integer getTipoCalcolo() {

	return tipoCalcolo;
    }

    public void setTipoCalcolo(Integer tipoCalcolo) {

	this.tipoCalcolo = tipoCalcolo;
    }

    public Integer getTipoCalcoloAnnualeScadenzaRate() {

	return tipoCalcoloAnnualeScadenzaRate;
    }

    public void setTipoCalcoloAnnualeScadenzaRate(Integer tipoCalcoloAnnualeScadenzaRate) {

	this.tipoCalcoloAnnualeScadenzaRate = tipoCalcoloAnnualeScadenzaRate;
    }

    public Boolean getImputaConsorzio() {

	return imputaConsorzio;
    }

    public void setImputaConsorzio(Boolean imputaConsorzio) {

	this.imputaConsorzio = imputaConsorzio;
    }
}
