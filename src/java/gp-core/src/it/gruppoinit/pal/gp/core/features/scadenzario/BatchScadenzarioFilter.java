package it.gruppoinit.pal.gp.core.features.scadenzario;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvv;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmSca;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.web.MovimentiFilter;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BatchScadenzarioFilter {

    private Date dallaData;
    private Date allaData;
    private String numeroIstanza;
    private Responsabili responsabile;
    private Alberoproc intervento;
    private Tipimovimento tipoMovimentoFatto;
    private Tipimovimento tipoMovimentoDaFare;
    private List<Statiistanza> statiIstanza;
    private Software scadSoftware;
    private Integer scadComportamento;
    private Responsabili utenteLoggato;
    private Boolean soloScadenzeImportanti;
    private List<ResponsabiliTmSca> tipimovimentoSca = new ArrayList<ResponsabiliTmSca>();
    private List<ResponsabiliTmAvv> tipimovimentoAvv = new ArrayList<ResponsabiliTmAvv>();
    private Boolean flagLetto;
    private OrderTypeEnum ordinamentoScadenze = OrderTypeEnum.DESC;
    private MovimentiFilter movimentiFilter;

    public OrderTypeEnum getOrdinamentoScadenze() {

	return ordinamentoScadenze;
    }

    public void setOrdinamentoScadenze(OrderTypeEnum ordinamentoScadenze) {

	this.ordinamentoScadenze = ordinamentoScadenze;
    }

    public Boolean getFlagLetto() {

	return flagLetto;
    }

    public void setFlagLetto(Boolean flagLetto) {

	this.flagLetto = flagLetto;
    }

    public List<ResponsabiliTmSca> getTipimovimentoSca() {

	return tipimovimentoSca;
    }

    public void setTipimovimentoSca(List<ResponsabiliTmSca> tipimovimentoSca) {

	this.tipimovimentoSca = tipimovimentoSca;
    }

    public List<ResponsabiliTmAvv> getTipimovimentoAvv() {

	return tipimovimentoAvv;
    }

    public void setTipimovimentoAvv(List<ResponsabiliTmAvv> tipimovimentoAvv) {

	this.tipimovimentoAvv = tipimovimentoAvv;
    }

    public Boolean getSoloScadenzeImportanti() {

	return soloScadenzeImportanti;
    }

    public void setSoloScadenzeImportanti(Boolean soloScadenzeImportanti) {

	this.soloScadenzeImportanti = soloScadenzeImportanti;
    }

    public BatchScadenzarioFilter() {

	//	Calendar cal = new GregorianCalendar();
	//	cal.set(Calendar.HOUR_OF_DAY, 0);
	//	cal.set(Calendar.MINUTE, 0);
	//	cal.set(Calendar.SECOND, 0);
	//	cal.set(Calendar.MILLISECOND, 0);
	//	dallaData = cal.getTime();
	//	allaData = cal.getTime();
	responsabile = new Responsabili();
	intervento = new Alberoproc();
	tipoMovimentoFatto = new Tipimovimento();
	tipoMovimentoDaFare = new Tipimovimento();
	statiIstanza = new ArrayList<Statiistanza>();
	scadSoftware = new Software();
	movimentiFilter = new MovimentiFilter();
    }

    public Date getDallaData() {

	return dallaData;
    }

    public void setDallaData(Date dallaData) {

	this.dallaData = dallaData;
    }

    public Date getAllaData() {

	return allaData;
    }

    public void setAllaData(Date allaData) {

	this.allaData = allaData;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public Responsabili getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(Responsabili responsabile) {

	this.responsabile = responsabile;
    }

    public Alberoproc getIntervento() {

	return intervento;
    }

    public void setIntervento(Alberoproc intervento) {

	this.intervento = intervento;
    }

    public Tipimovimento getTipoMovimentoFatto() {

	return tipoMovimentoFatto;
    }

    public void setTipoMovimentoFatto(Tipimovimento tipoMovimentoFatto) {

	this.tipoMovimentoFatto = tipoMovimentoFatto;
    }

    public Tipimovimento getTipoMovimentoDaFare() {

	return tipoMovimentoDaFare;
    }

    public void setTipoMovimentoDaFare(Tipimovimento tipoMovimentoDaFare) {

	this.tipoMovimentoDaFare = tipoMovimentoDaFare;
    }

    public List<Statiistanza> getStatiIstanza() {

	return statiIstanza;
    }

    public void setStatiIstanza(List<Statiistanza> statiIstanza) {

	this.statiIstanza = statiIstanza;
    }

    public Software getScadSoftware() {

	return scadSoftware;
    }

    public void setScadSoftware(Software scadSoftware) {

	this.scadSoftware = scadSoftware;
    }

    public Integer getScadComportamento() {

	return scadComportamento;
    }

    public void setScadComportamento(Integer scadComportamento) {

	this.scadComportamento = scadComportamento;
    }

    public Responsabili getUtenteLoggato() {

	return utenteLoggato;
    }

    public void setUtenteLoggato(Responsabili utenteLoggato) {

	this.utenteLoggato = utenteLoggato;
    }

    public MovimentiFilter getMovimentiFilter() {

	return movimentiFilter;
    }

    public void setMovimentiFilter(MovimentiFilter movimentiFilter) {

	this.movimentiFilter = movimentiFilter;
    }
}
