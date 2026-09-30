package it.alveo.firmaremota.aruba.configurazione.params;

public class UserNameParam extends BaseParam {

    @Override
    public String getChiave() {

	return "USERNAME";
    }

    @Override
    public String getDescrizione() {

	return "Username";
    }

    public static UserNameParam newParam() {

	return new UserNameParam("");
    }

    public UserNameParam(String valore) {

	super(valore);
    }
}
