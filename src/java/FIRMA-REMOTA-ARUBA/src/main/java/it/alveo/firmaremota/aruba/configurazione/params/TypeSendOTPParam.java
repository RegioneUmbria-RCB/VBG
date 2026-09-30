package it.alveo.firmaremota.aruba.configurazione.params;

public class TypeSendOTPParam extends BaseParam {

    @Override
    public String getChiave() {

	return "TYPE_SEND_OTP";
    }

    @Override
    public String getDescrizione() {

	return "Modalità con cui viene inviato l'otp. Possibili valori: SMS - tramite messaggio, CALL - tramite chiamata.";
    }

    public static TypeSendOTPParam newParam() {

	return new TypeSendOTPParam("");
    }

    public TypeSendOTPParam(String valore) {

	super(valore);
    }
}