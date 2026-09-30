package it.gruppoinit.pal.gp.core.service.helper;

public class ProprietaTracciatoRipartizioneRataBean {

    public static enum CAMPI {
	tipo_operazione, tipo_codice_ente, codice_ente, tipologia_entrata, anno_debito, identificativo_debito, //  
	numero_rata, identificativo_rata, tipo_ripartizione, codice_ripartizione, anno_riferimento_ripartizione, //
	numero_sub_accertamento, importo_per_ripartizione, flag_bollo, filler, versione_specifiche
    }

    public static enum TYPE {
	NUMBER_PAD, STRING, DATE
    }

    public ProprietaTracciatoRipartizioneRataBean(CAMPI name, int length, String format, TYPE type) {

	super();
	this.name = name;
	this.length = length;
	this.format = format;
	this.type = type;
    }

    private CAMPI name;
    private int length;
    private String format;
    private TYPE type;
    private String valore;

    public CAMPI getName() {

	return name;
    }

    public void setName(CAMPI name) {

	this.name = name;
    }

    public int getLength() {

	return length;
    }

    public void setLength(int length) {

	this.length = length;
    }

    public String getFormat() {

	return format;
    }

    public TYPE getType() {

	return type;
    }

    public void setType(TYPE type) {

	this.type = type;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
