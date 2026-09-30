package it.alveo.firmaremota.aruba.configurazione.params;

public class MarcaTemporaleRichiestaParam extends BaseParam {

    @Override
    public String getChiave() {

	return "IS_MARCA_TEMPORALE_RICHIESTA";
    }

    @Override
    public String getDescrizione() {

	return "Può assumere i valori 0 o 1. Utilizzato per pilotare la generazione della busta CAdES-T o PAdES-T (1); firma digitale con apposizione contestuale della marca temporale. In caso di parametro uguale ad 1, il server ARSS dovrà essere preventivamente configurato con account di marcatura temporale valido";
    }

    public static MarcaTemporaleRichiestaParam newParam() {

	return new MarcaTemporaleRichiestaParam("0");
    }

    public MarcaTemporaleRichiestaParam(String valore) {

	super(valore);
    }
}
