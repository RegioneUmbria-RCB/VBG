package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.helper.RegolamentoComunaleHelper;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.EndoLocale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteLocaleSchedaEndoTipo2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParteLocaleSchedaEndoTipo2Command extends BaseCommand {

    private Alberoproc alberoproc;
    private ParteLocaleSchedaEndoTipo2 entity;
    // Rappresenta l'endo di tipo uno dell' ElencoEndoRegionali (si è creato un campo stringa per facilitare
    // l'inserimento multiplo
    // nella realtà il campo è un bigInteger)
    private String elencoEndoRegionaliPrima;
    // recupera info per l'elenco degli endo locali
    private EndoLocale endoLocalePrima;
    // Rappresenta l'endo di tipo uno dell' ElencoEndoRegionali (si è creato un campo stringa per facilitare
    // l'inserimento multiplo
    // nella realtà il campo è un bigInteger)
    private String elencoEndoRegionaliDopo;
    // recupera info per l'elenco degli endo locali
    private EndoLocale endoLocaleDopo;
    // usati per visualizzare la lista (la jsp non riesce a vedere i campi estesi)
    // campi necessari per inserire un elemento dell'elenco NormativeLocaliEndoTipo1
    private List<RegolamentoComunaleHelper> regolamentoComunaleHelpers = new ArrayList<RegolamentoComunaleHelper>();
    // campi necessari per inserire un elemento dell'elenco NormativeLocaliEndoTipo2
    private List<RegolamentoComunaleHelper> regolamentoComunaleTipo2Helpers = new ArrayList<RegolamentoComunaleHelper>();
    // Utilizzate per acquisire le date nel formate correte
    private Date dataInizioValidita;
    private Date dataFineValidita;
    private List<StpEndoTipo1> inventarioprocedimentisPrima = new ArrayList<StpEndoTipo1>();
    private List<StpEndoTipo1> inventarioprocedimentisDopo = new ArrayList<StpEndoTipo1>();

    public ParteLocaleSchedaEndoTipo2Command() {

	super();
	this.endoLocalePrima = new EndoLocale();
	this.endoLocaleDopo = new EndoLocale();
    }

    public ParteLocaleSchedaEndoTipo2 getEntity() {

	return entity;
    }

    public void setEntity(ParteLocaleSchedaEndoTipo2 entity) {

	this.entity = entity;
    }

    public String getElencoEndoRegionaliPrima() {

	return elencoEndoRegionaliPrima;
    }

    public void setElencoEndoRegionaliPrima(String elencoEndoRegionaliPrima) {

	this.elencoEndoRegionaliPrima = elencoEndoRegionaliPrima;
    }

    public EndoLocale getEndoLocalePrima() {

	return endoLocalePrima;
    }

    public void setEndoLocalePrima(EndoLocale endoLocalePrima) {

	this.endoLocalePrima = endoLocalePrima;
    }

    public String getElencoEndoRegionaliDopo() {

	return elencoEndoRegionaliDopo;
    }

    public void setElencoEndoRegionaliDopo(String elencoEndoRegionaliDopo) {

	this.elencoEndoRegionaliDopo = elencoEndoRegionaliDopo;
    }

    public EndoLocale getEndoLocaleDopo() {

	return endoLocaleDopo;
    }

    public void setEndoLocaleDopo(EndoLocale endoLocaleDopo) {

	this.endoLocaleDopo = endoLocaleDopo;
    }

    public List<RegolamentoComunaleHelper> getRegolamentoComunaleHelpers() {

	return regolamentoComunaleHelpers;
    }

    public void setRegolamentoComunaleHelpers(List<RegolamentoComunaleHelper> regolamentoComunaleHelpers) {

	this.regolamentoComunaleHelpers = regolamentoComunaleHelpers;
    }

    public List<RegolamentoComunaleHelper> getRegolamentoComunaleTipo2Helpers() {

	return regolamentoComunaleTipo2Helpers;
    }

    public void setRegolamentoComunaleTipo2Helpers(List<RegolamentoComunaleHelper> regolamentoComunaleTipo2Helpers) {

	this.regolamentoComunaleTipo2Helpers = regolamentoComunaleTipo2Helpers;
    }

    public Date getDataInizioValidita() {

	return dataInizioValidita;
    }

    public void setDataInizioValidita(Date dataInizioValidita) {

	this.dataInizioValidita = dataInizioValidita;
    }

    public Date getDataFineValidita() {

	return dataFineValidita;
    }

    public void setDataFineValidita(Date dataFineValidita) {

	this.dataFineValidita = dataFineValidita;
    }

    public List<StpEndoTipo1> getInventarioprocedimentisPrima() {

	return inventarioprocedimentisPrima;
    }

    public void setInventarioprocedimentisPrima(List<StpEndoTipo1> inventarioprocedimentisPrima) {

	this.inventarioprocedimentisPrima = inventarioprocedimentisPrima;
    }

    public List<StpEndoTipo1> getInventarioprocedimentisDopo() {

	return inventarioprocedimentisDopo;
    }

    public void setInventarioprocedimentisDopo(List<StpEndoTipo1> inventarioprocedimentisDopo) {

	this.inventarioprocedimentisDopo = inventarioprocedimentisDopo;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
    }

    public Alberoproc getAlberoproc() {

	return alberoproc;
    }
}
