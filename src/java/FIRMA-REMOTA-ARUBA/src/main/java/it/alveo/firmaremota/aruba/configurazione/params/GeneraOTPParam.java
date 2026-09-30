package it.alveo.firmaremota.aruba.configurazione.params;

public class GeneraOTPParam extends BaseParam {

    @Override
    public String getChiave() {

	return "IS_GENERA_OTP";
    }

    @Override
    public String getDescrizione() {

	return "Può assumere i valori 0 o 1. 0: non attiva la funzionalità per generare OTP, 1: attiva la funzionalità per generare OTP. La generazione dell' OTP virtuale deve essere attivato anche a livello di servizio sul provider";
    }

    public static GeneraOTPParam newParam() {

	return new GeneraOTPParam("0");
    }

    public GeneraOTPParam(String valore) {

	super(valore);
    }
}