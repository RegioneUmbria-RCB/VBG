package it.gruppoinit.pal.gp.core.features.oneri;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;

public class IstanzeOneriListModel {

    private Map<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> elenco;
    private Map<String, IstanzeOneriListTotali> totaliPerRaggruppamento = new HashMap<String, IstanzeOneriListTotali>();
    private IstanzeOneriListTotali totali = new IstanzeOneriListTotali();

    public IstanzeOneriListModel(Map<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> elenco) {

	super();
	this.elenco = elenco;
	this.totali = new IstanzeOneriListTotali();
	if (this.elenco == null || this.elenco.isEmpty()) {
	    return;
	}
	for (Map.Entry<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> elemento : this.elenco.entrySet()) {
	    if (!totaliPerRaggruppamento.containsKey(elemento.getKey().getRaggruppamento())) {
		totaliPerRaggruppamento.put(elemento.getKey().getRaggruppamento(), new IstanzeOneriListTotali());
	    }
	    IstanzeOneriListTotali totaleRaggruppamento = totaliPerRaggruppamento.get(elemento.getKey().getRaggruppamento());
	    for (Istanzeoneri onere : elemento.getValue()) {
		if (onere.isPresentiSoloPosizioniDebitorieAnnullate()) {
		    continue;
		}
		switch (EnumTipologiaOnereType.fromDettaglio(onere.getFlentratauscita(), onere.isPagato())) {
		case ENTRATA:
		    this.totali.aggiungiEntrata(onere);
		    totaleRaggruppamento.aggiungiEntrata(onere);
		    break;
		case USCITA:
		    this.totali.aggiungiUscita(onere);
		    totaleRaggruppamento.aggiungiUscita(onere);
		    break;
		case INCASSATO:
		    this.totali.aggiungiIncassato(onere);
		    totaleRaggruppamento.aggiungiIncassato(onere);
		    break;
		case RIVERSATO:
		    this.totali.aggiungiRiversato(onere);
		    totaleRaggruppamento.aggiungiRiversato(onere);
		    break;
		}
	    }
	}
    }

    public Map<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> getElenco() {

	return elenco;
    }

    public IstanzeOneriListTotali getTotali() {

	return totali;
    }

    public IstanzeOneriListTotali getTotaleRaggruppamento(String raggruppamento) {

	return this.totaliPerRaggruppamento.get(raggruppamento);
    }

    public IstanzeOneriListTotali getTotaleRaggruppamentoDataPagamentoEScadenza(String raggruppamento, Date dataPagamento, Date dataScadenza) {

	ChiavePerCausaleDatPagamentoDataScadenzaTipologia chiave = ChiavePerCausaleDatPagamentoDataScadenzaTipologia
		.fromRaggruppamentoDataPagamentoEScadenza(raggruppamento, dataPagamento, dataScadenza);
	List<Istanzeoneri> lista = this.elenco.get(chiave);
	if (lista == null) {
	    return new IstanzeOneriListTotali();
	}
	IstanzeOneriListTotali retVal = new IstanzeOneriListTotali();
	for (Istanzeoneri onere : this.elenco.get(chiave)) {
	    switch (EnumTipologiaOnereType.fromDettaglio(onere.getFlentratauscita(), onere.isPagato())) {
	    case ENTRATA:
		retVal.aggiungiEntrata(onere);
		break;
	    case USCITA:
		retVal.aggiungiUscita(onere);
		break;
	    case INCASSATO:
		retVal.aggiungiIncassato(onere);
		break;
	    case RIVERSATO:
		retVal.aggiungiRiversato(onere);
		break;
	    }
	}
	return retVal;
    }
}
