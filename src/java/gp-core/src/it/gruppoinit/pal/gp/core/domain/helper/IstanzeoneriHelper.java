package it.gruppoinit.pal.gp.core.domain.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;

public class IstanzeoneriHelper {

    private List<Istanzeoneri> istanzeoneris = new ArrayList<Istanzeoneri>();
    // Totale oneri causale per raggruppamento
    private BigDecimal totaleOneriCausaleRaggruppamento;
    // Totale oneri istruttoria per raggruppamento
    private BigDecimal totaleOneriIstruttoriaRaggruppamento;
    // Totale oneri causale e istruttoria per raggruppamento
    private BigDecimal totaleOneriCausaleAndIstruttoriaRaggruppamento;
    // Totale oneri versati in entrata (relativi agli oneri in ingresso al comune)
    private BigDecimal totaleOneriIncassatiRaggruppamento;
    // Totale oneri versati in uscita (relativi agli oneri versati dal comune a enti terzi) per raggruppamento
    private BigDecimal totaleOneriRiversatiRaggruppamento;
    // Saldo degli oneri Incassati per raggruppamento
    private BigDecimal totaleSaldoIncassiRaggruppamento;
    // Saldo degli oneri riversati per raggruppamento
    private BigDecimal totaleSaldoRiversatiRaggruppamento;
    // Totale delle uscite (previste,nn effettuate) per singolo raggruppamento
    private BigDecimal totaliUsciteRaggruppamento;
    // Totale oneri causale
    private BigDecimal totaleOneriCausale;
    // Totale oneri istruttoria
    private BigDecimal totaleOneriIstruttoria;
    // Totale oneri causale e istruttoria
    private BigDecimal totaleOneriCausaleAndIstruttoria;
    // Totale oneri uscite (previste e non effettuate) 
    private BigDecimal totaliUscite;
    // Saldo degli oneri Incassati 
    private BigDecimal totaleOneriIncassati;
    // Totale oneri versati in uscita (relativi agli oneri versati dal comune a enti terzi)
    private BigDecimal totaleOneriRiversati;
    // Saldo degli oneri Incassati 
    private BigDecimal totaleSaldoIncassi;
    // Saldo degli oneri riversati
    private BigDecimal totaleSaldoRiversati;
    private BigDecimal totaliRibassiRaggruppamento;
    private BigDecimal totaliRibassi;
    // Utilizzato per la visualizzazione raggruppata.
    private Boolean isFirst;
    private Boolean isLast;

    private IstanzeoneriHelper() {

	super();
	this.isFirst = false;
	this.totaleOneriCausale = new BigDecimal(0);
	this.totaleOneriIstruttoria = new BigDecimal(0);
	this.totaleOneriIstruttoriaRaggruppamento = new BigDecimal(0);
	this.totaleOneriCausaleAndIstruttoriaRaggruppamento = new BigDecimal(0);
	this.totaleOneriIncassatiRaggruppamento = new BigDecimal(0);
	this.totaleOneriRiversatiRaggruppamento = new BigDecimal(0);
	this.totaleOneriCausaleRaggruppamento = new BigDecimal(0);
	this.totaliUsciteRaggruppamento = new BigDecimal(0);
	this.totaleOneriCausaleAndIstruttoria = new BigDecimal(0);
	this.totaliUscite = new BigDecimal(0);
	this.totaliRibassiRaggruppamento = new BigDecimal(0);
	this.totaliUscite = new BigDecimal(0);
    }

    public static IstanzeoneriHelper fromIstanzeOneri(Istanzeoneri istanzeOnere) {

	IstanzeoneriHelper helper = new IstanzeoneriHelper();
	helper.setIstanzeoneris(new ArrayList<Istanzeoneri>());
	helper.getIstanzeoneris().add(istanzeOnere);
	return helper;
    }

    public static IstanzeoneriHelper fromIstanzeOneri(List<Istanzeoneri> istanzeOneri) {

	IstanzeoneriHelper helper = new IstanzeoneriHelper();
	helper.setIstanzeoneris(istanzeOneri);
	return helper;
    }

    public List<Istanzeoneri> getIstanzeoneris() {

	return istanzeoneris;
    }

    public void setIstanzeoneris(List<Istanzeoneri> istanzeoneris) {

	this.istanzeoneris = istanzeoneris;
    }

    public BigDecimal getTotaleOneriCausaleRaggruppamento() {

	return totaleOneriCausaleRaggruppamento;
    }

    public void setTotaleOneriCausaleRaggruppamento(BigDecimal totaleOneriCausaleRaggruppamento) {

	this.totaleOneriCausaleRaggruppamento = totaleOneriCausaleRaggruppamento;
    }

    public BigDecimal getTotaleOneriIstruttoriaRaggruppamento() {

	return totaleOneriIstruttoriaRaggruppamento;
    }

    public void setTotaleOneriIstruttoriaRaggruppamento(BigDecimal totaleOneriIstruttoriaRaggruppamento) {

	this.totaleOneriIstruttoriaRaggruppamento = totaleOneriIstruttoriaRaggruppamento;
    }

    public BigDecimal getTotaleOneriCausaleAndIstruttoriaRaggruppamento() {

	return totaleOneriCausaleAndIstruttoriaRaggruppamento;
    }

    public void setTotaleOneriCausaleAndIstruttoriaRaggruppamento(BigDecimal totaleOneriCausaleAndIstruttoriaRaggruppamento) {

	this.totaleOneriCausaleAndIstruttoriaRaggruppamento = totaleOneriCausaleAndIstruttoriaRaggruppamento;
    }

    public BigDecimal getTotaleOneriCausale() {

	return totaleOneriCausale;
    }

    public void setTotaleOneriCausale(BigDecimal totaleOneriCausale) {

	this.totaleOneriCausale = totaleOneriCausale;
    }

    public BigDecimal getTotaleOneriIstruttoria() {

	return totaleOneriIstruttoria;
    }

    public void setTotaleOneriIstruttoria(BigDecimal totaleOneriIstruttoria) {

	this.totaleOneriIstruttoria = totaleOneriIstruttoria;
    }

    public BigDecimal getTotaleOneriCausaleAndIstruttoria() {

	return totaleOneriCausaleAndIstruttoria;
    }

    public BigDecimal getTotaleOneriIncassati() {

	return totaleOneriIncassati;
    }

    public void setTotaleOneriIncassati(BigDecimal totaleOneriIncassati) {

	this.totaleOneriIncassati = totaleOneriIncassati;
    }

    public BigDecimal getTotaleOneriRiversati() {

	return totaleOneriRiversati;
    }

    public BigDecimal getTotaleSaldoIncassi() {

	return totaleSaldoIncassi;
    }

    public void setTotaleSaldoIncassi(BigDecimal totaleSaldoIncassi) {

	this.totaleSaldoIncassi = totaleSaldoIncassi;
    }

    public BigDecimal getTotaleSaldoRiversati() {

	return totaleSaldoRiversati;
    }

    public void setTotaleSaldoRiversati(BigDecimal totaleSaldoRiversati) {

	this.totaleSaldoRiversati = totaleSaldoRiversati;
    }

    public void setTotaleOneriRiversati(BigDecimal totaleOneriRiversati) {

	this.totaleOneriRiversati = totaleOneriRiversati;
    }

    public void setTotaleOneriCausaleAndIstruttoria(BigDecimal totaleOneriCausaleAndIstruttoria) {

	this.totaleOneriCausaleAndIstruttoria = totaleOneriCausaleAndIstruttoria;
    }

    public BigDecimal getTotaleOneriIncassatiRaggruppamento() {

	return totaleOneriIncassatiRaggruppamento;
    }

    public void setTotaleOneriIncassatiRaggruppamento(BigDecimal totaleOneriIncassatiRaggruppamento) {

	this.totaleOneriIncassatiRaggruppamento = totaleOneriIncassatiRaggruppamento;
    }

    public BigDecimal getTotaleOneriRiversatiRaggruppamento() {

	return totaleOneriRiversatiRaggruppamento;
    }

    public void setTotaleOneriRiversatiRaggruppamento(BigDecimal totaleOneriRiversatiRaggruppamento) {

	this.totaleOneriRiversatiRaggruppamento = totaleOneriRiversatiRaggruppamento;
    }

    public BigDecimal getTotaleSaldoIncassiRaggruppamento() {

	return totaleSaldoIncassiRaggruppamento;
    }

    public void setTotaleSaldoIncassiRaggruppamento(BigDecimal totaleSaldoIncassiRaggruppamento) {

	this.totaleSaldoIncassiRaggruppamento = totaleSaldoIncassiRaggruppamento;
    }

    public BigDecimal getTotaleSaldoRiversatiRaggruppamento() {

	return totaleSaldoRiversatiRaggruppamento;
    }

    public void setTotaleSaldoRiversatiRaggruppamento(BigDecimal totaleSaldoRiversatiRaggruppamento) {

	this.totaleSaldoRiversatiRaggruppamento = totaleSaldoRiversatiRaggruppamento;
    }

    public BigDecimal getTotaliUsciteRaggruppamento() {

	return totaliUsciteRaggruppamento;
    }

    public void setTotaliUsciteRaggruppamento(BigDecimal totaliUsciteRaggruppamento) {

	this.totaliUsciteRaggruppamento = totaliUsciteRaggruppamento;
    }

    public BigDecimal getTotaliUscite() {

	return totaliUscite;
    }

    public void setTotaliUscite(BigDecimal totaliUscite) {

	this.totaliUscite = totaliUscite;
    }

    public BigDecimal getTotaliRibassiRaggruppamento() {

	return totaliRibassiRaggruppamento;
    }

    public void setTotaliRibassiRaggruppamento(BigDecimal totaliRibassiRaggruppamento) {

	this.totaliRibassiRaggruppamento = totaliRibassiRaggruppamento;
    }

    public BigDecimal getTotaliRibassi() {

	return totaliRibassi;
    }

    public void setTotaliRibassi(BigDecimal totaliRibassi) {

	this.totaliRibassi = totaliRibassi;
    }

    public Boolean getIsFirst() {

	return isFirst;
    }

    public void setIsFirst(Boolean isFirst) {

	this.isFirst = isFirst;
    }

    public Boolean getIsLast() {

	return isLast;
    }

    public void setIsLast(Boolean isLast) {

	this.isLast = isLast;
    }
}
