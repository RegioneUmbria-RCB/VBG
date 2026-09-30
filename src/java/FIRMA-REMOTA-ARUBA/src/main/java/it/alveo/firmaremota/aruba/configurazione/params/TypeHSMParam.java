package it.alveo.firmaremota.aruba.configurazione.params;

public class TypeHSMParam extends BaseParam {

    @Override
    public String getChiave() {

	return "TYPE_HSM";
    }

    @Override
    public String getDescrizione() {

	return "Stringa contenente il tipo di HSM. Da specifiche Aruba valorizzare con COSING";
    }

    public static TypeHSMParam newParam() {

	return new TypeHSMParam("COSIGN");
    }

    public TypeHSMParam(String valore) {

	super(valore);
    }
}