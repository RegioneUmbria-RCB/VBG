package it.alveo.firmaremota.aruba.configurazione.params;

public class MotivoFirmaPadesParam extends BaseParam {

    @Override
    public String getChiave() {

	return "REASON";
    }

    @Override
    public String getDescrizione() {

	return "Stringa utilizzata per la valorizzazione dell'entry \"Reason\" dell'oggetto Signature Dictionary. Ad esempio 'Per approvazione'";
    }

    public static MotivoFirmaPadesParam newParam() {

	return new MotivoFirmaPadesParam("");
    }

    public MotivoFirmaPadesParam(String valore) {

	super(valore);
    }
}