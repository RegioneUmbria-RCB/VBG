package it.alveo.firmaremota.aruba.configurazione.params;

public class TestoFirmaPadesParam extends BaseParam {

    @Override
    public String getChiave() {

	return "TESTO_FIRMA_PADES";
    }

    @Override
    public String getDescrizione() {

	return "Indica il testo che verrà applicato in caso di firma PAdES sul file pdf. Parametro non obbligatorio, in caso non sia valorizzato sul pdf non apparirà alcun testo, ma la firma rimarrà valida";
    }

    public static TestoFirmaPadesParam newParam() {

	return new TestoFirmaPadesParam("");
    }

    public TestoFirmaPadesParam(String valore) {

	super(valore);
    }
}
