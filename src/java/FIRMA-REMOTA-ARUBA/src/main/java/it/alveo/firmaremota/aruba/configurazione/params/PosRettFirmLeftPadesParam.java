package it.alveo.firmaremota.aruba.configurazione.params;

public class PosRettFirmLeftPadesParam extends BaseParam {

    @Override
    public String getChiave() {

	return "POS_RETT_FIRM_LEFT_PADES";
    }

    @Override
    public String getDescrizione() {

	return "Indica la coppia ascissa, ordinata (x,y) del margine basso del rettangolo di firma. Unità di misura espressa in px; Es. 10,6";
    }

    public static PosRettFirmLeftPadesParam newParam() {

	return new PosRettFirmLeftPadesParam("");
    }

    public PosRettFirmLeftPadesParam(String valore) {

	super(valore);
    }
}
