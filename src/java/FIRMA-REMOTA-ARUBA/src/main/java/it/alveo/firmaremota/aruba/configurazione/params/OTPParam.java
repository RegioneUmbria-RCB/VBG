package it.alveo.firmaremota.aruba.configurazione.params;

public class OTPParam extends BaseParam {

    @Override
    public String getChiave() {

	return "OTP";
    }

    @Override
    public String getDescrizione() {

	return "Codice OTP";
    }

    public static OTPParam newParam() {

	return new OTPParam("");
    }

    public OTPParam(String valore) {

	super(valore);
    }
}