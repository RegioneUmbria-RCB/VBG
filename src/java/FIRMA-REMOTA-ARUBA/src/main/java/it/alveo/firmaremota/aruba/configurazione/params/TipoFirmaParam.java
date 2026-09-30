package it.alveo.firmaremota.aruba.configurazione.params;

public class TipoFirmaParam extends BaseParam {

    @Override
    public String getChiave() {

	return "TIPO_FIRMA";
    }

    @Override
    public String getDescrizione() {

	return "Valorizzare con CADES o PADES a seconda della tipologia di firma";
    }

    public static TipoFirmaParam newParam() {

	return new TipoFirmaParam("");
    }

    public TipoFirmaParam(String valore) {

	super(valore);
    }
}
