package it.alveo.firmaremota.aruba.configurazione.params;

public class ProfiloFirmaParam extends BaseParam {

    @Override
    public String getChiave() {

	return "PROFILO_FIRMA_REMOTA";
    }

    @Override
    public String getDescrizione() {

	return "Profilo di firma";
    }

    public static ProfiloFirmaParam newParam() {

	return new ProfiloFirmaParam("");
    }

    public ProfiloFirmaParam(String valore) {

	super(valore);
    }
}
