package it.alveo.firmaremota.aruba.configurazione.params;

public class UrlServizioParam extends BaseParam {

    @Override
    public String getChiave() {

	return "URL_BASE_SERVIZIO";
    }

    @Override
    public String getDescrizione() {

	return "Url del servizio di firma remota sottoscritto dall'ente";
    }

    public static UrlServizioParam newParam() {

	return new UrlServizioParam("");
    }

    public UrlServizioParam(String valore) {

	super(valore);
    }
}