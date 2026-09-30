package it.alveo.firmaremota.aruba.configurazione.params;

public class FirmaCongiuntaCadesParam extends BaseParam {

    @Override
    public String getChiave() {

	return "FIRMA_CONGIUNTA_CADES";
    }

    @Override
    public String getDescrizione() {

	return "Valido solamente per firme di tipo CADES, applica una firma congiunta al documento. L'applicativo rilancia errore nel caso si scelga la firma congiunta ed il file non risulti già firmato.";
    }

    public static FirmaCongiuntaCadesParam newParam() {

	return new FirmaCongiuntaCadesParam("NO");
    }

    public FirmaCongiuntaCadesParam(String valore) {

	super(valore);
    }
}