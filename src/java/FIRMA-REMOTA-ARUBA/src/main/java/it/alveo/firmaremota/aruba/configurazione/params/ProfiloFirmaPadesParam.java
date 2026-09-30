package it.alveo.firmaremota.aruba.configurazione.params;

public class ProfiloFirmaPadesParam extends BaseParam {

    @Override
    public String getChiave() {

	return "PROFILO_FIRMA_PADES";
    }

    @Override
    public String getDescrizione() {

	return "Indica il tipo di profilo applicato alla firma PAdES : BASIC, BES. In caso il profilo sia passato dalla maschera di firma, il valore presente sarà sovrascritto. Valore di default BES";
    }

    public static ProfiloFirmaPadesParam newParam() {

	return new ProfiloFirmaPadesParam("BES");
    }

    public ProfiloFirmaPadesParam(String valore) {

	super(valore);
    }
}
