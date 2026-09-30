package it.alveo.firmaremota.aruba.configurazione.params;

public class CertIdParam extends BaseParam {

    @Override
    public String getChiave() {

	return "CERT_ID";
    }

    @Override
    public String getDescrizione() {

	return "Da specifiche di Aruba valorizzare con AS0";
    }

    public static CertIdParam newParam() {

	return new CertIdParam("AS0");
    }

    public CertIdParam(String valore) {

	super(valore);
    }
}