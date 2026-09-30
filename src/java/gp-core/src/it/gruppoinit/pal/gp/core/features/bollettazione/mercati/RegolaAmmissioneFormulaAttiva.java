package it.gruppoinit.pal.gp.core.features.bollettazione.mercati;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.features.bollettazione.AbstractRegolaAmmissione;
import it.gruppoinit.pal.gp.core.utils.DateUtils;

public class RegolaAmmissioneFormulaAttiva extends AbstractRegolaAmmissione {

    List<Date> intervalloBollettazione = new ArrayList<Date>(0);
    private Date dataInizio;
    private Date dataFine;
    private List<MercatiFormuleCalcolo> formule;

    public RegolaAmmissioneFormulaAttiva(Date dataInizio, Date dataFine, List<MercatiFormuleCalcolo> formule) {

	this.dataInizio = dataInizio;
	this.dataFine = dataFine;
	this.formule = formule;
	if (this.dataInizio == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare RegolaAmmissioneFormulaAttiva senza valorizzare il parametro dataInizio");
	}
	if (this.dataFine == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare RegolaAmmissioneFormulaAttiva senza valorizzare il parametro dataFine");
	}
	if (this.formule == null || this.formule.size() == 0) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare RegolaAmmissioneFormulaAttiva senza valorizzare il parametro formule con almeno una formula");
	}
	this.intervalloBollettazione = super.getGiorniBollettazione(dataInizio, dataFine);
    }

    /**
     * Controlla che per il periodo della bollettazione, tutti i mercati abbiano almeno una formula valida per l'intera
     * durata di tale periodo
     * 
     */
    @Override
    public boolean valida() {

	for (MercatiFormuleCalcolo formula : formule) {
	    Date dataInizioFormula = formula.getDataInizioValidita();
	    Date dataFineFormula = formula.getDataFineValidita();
	    List<Date> intervalloDateFormula = super.getGiorniBollettazione(dataInizioFormula, dataFineFormula);
	    this.intervalloBollettazione.removeAll(intervalloDateFormula);
	}
	if (this.intervalloBollettazione.size() == 0) {
	    return true;
	}
	String messaggio = String.format("Le date {} non sono coperte da formule per calcolarne gli importi",
		DateUtils.getDateConcatenate(this.intervalloBollettazione, super.getSeparatore()));
	this.setMessaggio(messaggio);
	return false;
    }
}
