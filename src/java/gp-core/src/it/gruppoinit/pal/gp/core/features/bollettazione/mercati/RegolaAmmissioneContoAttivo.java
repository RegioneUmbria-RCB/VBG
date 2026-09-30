package it.gruppoinit.pal.gp.core.features.bollettazione.mercati;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.features.bollettazione.AbstractRegolaAmmissione;
import it.gruppoinit.pal.gp.core.utils.DateUtils;

public class RegolaAmmissioneContoAttivo extends AbstractRegolaAmmissione {

    List<Date> intervalloBollettazione = new ArrayList<Date>(0);
    private Date dataInizio;
    private Date dataFine;
    private List<MercatiContabilitaTributi> conti;

    public RegolaAmmissioneContoAttivo(Date dataInizio, Date dataFine, List<MercatiContabilitaTributi> conti) {

	this.dataInizio = dataInizio;
	this.dataFine = dataFine;
	this.conti = conti;
	if (this.dataInizio == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare RegolaAmmissioneContoAttivo senza valorizzare il parametro dataInizio");
	}
	if (this.dataFine == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare RegolaAmmissioneContoAttivo senza valorizzare il parametro dataFine");
	}
	if (this.conti == null || this.conti.size() == 0) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare RegolaAmmissioneContoAttivo senza valorizzare il parametro conti con almeno un conto del mercato");
	}
	this.intervalloBollettazione = super.getGiorniBollettazione(dataInizio, dataFine);
    }

    /**
     * Controlla che per il periodo della bollettazione, tutti i mercati abbiano almeno un conto valido per l'intera
     * durata di tale periodo
     * 
     */
    @Override
    public boolean valida() {

	for (MercatiContabilitaTributi conto : this.conti) {
	    Date dataInizioConto = conto.getDataInizioValidita();
	    Date dataFineConto = conto.getDataFineValidita();
	    List<Date> intervalloDateConti = super.getGiorniBollettazione(dataInizioConto, dataFineConto);
	    this.intervalloBollettazione.removeAll(intervalloDateConti);
	}
	if (this.intervalloBollettazione.size() == 0) {
	    return true;
	}
	String messaggio = String.format("Le date {} non sono coperte da conti per poter associare gli importi calcolati",
		DateUtils.getDateConcatenate(this.intervalloBollettazione, super.getSeparatore()));
	this.setMessaggio(messaggio);
	return false;
    }
}
