package it.alveo.firmaremota.aruba.configurazione.params;

public class EmailNotificaParam extends BaseParam {

    @Override
    public String getChiave() {

	return "EMAIL_NOTIFICA";
    }

    @Override
    public String getDescrizione() {

	return "Indirizzo email di notifica in caso di marcatura temporale";
    }

    public static EmailNotificaParam newParam() {

	return new EmailNotificaParam("");
    }

    public EmailNotificaParam(String valore) {

	super(valore);
    }
}