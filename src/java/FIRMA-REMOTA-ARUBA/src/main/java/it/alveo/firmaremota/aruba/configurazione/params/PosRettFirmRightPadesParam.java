package it.alveo.firmaremota.aruba.configurazione.params;

public class PosRettFirmRightPadesParam extends BaseParam {

    @Override
    public String getChiave() {

	return "POS_RETT_FIRM_RIGHT_PADES";
    }

    @Override
    public String getDescrizione() {

	return "Indica la coppia ascissa, ordinata (x,y) del margine alto del rettangolo di firma. Unità di misura espressa in px; Es. 10,6";
    }

    public static PosRettFirmRightPadesParam newParam() {

	return new PosRettFirmRightPadesParam("");
    }

    public PosRettFirmRightPadesParam(String valore) {

	super(valore);
    }
}
