package it.alveo.firmaremota.aruba.configurazione.params;

public class DetachedParam extends BaseParam {

    @Override
    public String getChiave() {

	return "DETACHED";
    }

    @Override
    public String getDescrizione() {

	return "Utilizzato per la generazione della busta CAdES senza documento originale. Può assumere i valori 0(NULL), 1. 0: Busta CAdES contenente documento originale; 1: Busta CAdES non contenente documento originale";
    }

    public static DetachedParam newParam() {

	return new DetachedParam("");
    }

    public DetachedParam(String valore) {

	super(valore);
    }
}