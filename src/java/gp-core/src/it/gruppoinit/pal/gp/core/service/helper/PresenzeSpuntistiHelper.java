package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniCsi;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;

public class PresenzeSpuntistiHelper {

    private Integer mercatipresenzeDid;
    private Integer numeropresenze;
    private Date dataCciaa;
    private Date dataAutorizzazione;
    private Autorizzazioni aut;
    private String faseSpunta;
    private MercatipresenzeD mercatipresenzeD;
    private AutorizzazioniCsi autCsi;
    private Date dataAnzianita;

    public Integer getMercatipresenzeDid() {

	return mercatipresenzeDid;
    }

    public void setMercatipresenzeDid(Integer mercatipresenzeDid) {

	this.mercatipresenzeDid = mercatipresenzeDid;
    }

    public int getNumeropresenze() {

	return numeropresenze;
    }

    public void setNumeropresenze(int numeropresenze) {

	this.numeropresenze = numeropresenze;
    }

    public Date getDataCciaa() {

	return dataCciaa;
    }

    public void setDataCciaa(Date dataCciaa) {

	this.dataCciaa = dataCciaa;
    }

    public Date getDataAutorizzazione() {

	return dataAutorizzazione;
    }

    public void setDataAutorizzazione(Date dataAutorizzazione) {

	this.dataAutorizzazione = dataAutorizzazione;
    }

    public Autorizzazioni getAut() {

	return aut;
    }

    public void setAut(Autorizzazioni aut) {

	this.aut = aut;
    }

    public String getFaseSpunta() {

	return faseSpunta;
    }

    public void setFaseSpunta(String faseSpunta) {

	this.faseSpunta = faseSpunta;
    }

    public MercatipresenzeD getMercatipresenzeD() {

	return mercatipresenzeD;
    }

    public void setMercatipresenzeD(MercatipresenzeD mercatipresenzeD) {

	this.mercatipresenzeD = mercatipresenzeD;
    }

    public void setAutCsi(AutorizzazioniCsi autCsi) {

	this.autCsi = autCsi;
    }

    public AutorizzazioniCsi getAutCsi() {

	return autCsi;
    }

    public Date getDataAnzianita() {

	return dataAnzianita;
    }

    public void setDataAnzianita(Date dataAnzianita) {

	this.dataAnzianita = dataAnzianita;
    }
}
