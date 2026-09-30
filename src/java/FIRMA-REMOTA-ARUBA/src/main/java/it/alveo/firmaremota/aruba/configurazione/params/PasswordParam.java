package it.alveo.firmaremota.aruba.configurazione.params;

public class PasswordParam extends BaseParam {

    @Override
    public String getChiave() {

	return "PASSWORD";
    }

    @Override
    public String getDescrizione() {

	return "Password";
    }

    public static PasswordParam newParam() {

	return new PasswordParam("");
    }

    public PasswordParam(String valore) {

	super(valore);
    }
}
