package it.alveo.firmaremota.aruba.configurazione.params;

public class NumPaginaFirmaPadesParam extends BaseParam {

    @Override
    public String getChiave() {

	return "NUM_PAGINA_FIRMA_PADES";
    }

    @Override
    public String getDescrizione() {

	return "Indica la pagina su cui apporre la firma; valore obbligatorio. Se non presente, di default la firma verrà apposta sull'ultima pagina";
    }

    public static NumPaginaFirmaPadesParam newParam() {

	return new NumPaginaFirmaPadesParam("");
    }

    public NumPaginaFirmaPadesParam(String valore) {

	super(valore);
    }
}
