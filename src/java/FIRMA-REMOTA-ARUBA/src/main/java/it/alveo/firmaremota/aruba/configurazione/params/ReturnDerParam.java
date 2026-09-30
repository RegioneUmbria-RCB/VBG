package it.alveo.firmaremota.aruba.configurazione.params;

public class ReturnDerParam extends BaseParam {

    @Override
    public String getChiave() {

	return "RETURN_DER";
    }

    @Override
    public String getDescrizione() {

	return "Utilizzato per la generazione della busta CAdES in formato DER. Può assumere i valori 0 o 1. 0: Busta CAdES in formato BER; 1: Busta CAdES in formato DER (default)";
    }

    public static ReturnDerParam newParam() {

	return new ReturnDerParam("1");
    }

    public ReturnDerParam(String valore) {

	super(valore);
    }
}