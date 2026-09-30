package it.gruppoinit.pal.gp.core.service.helper;

public class ProprietaTracciatoLottoBean {

    public static enum CAMPI {
	tipo_operazione, tipo_codice_ente, codice_ente, tipologia_entrata, identificativo_lotto, //
	data_creazione_lotto, numero_totale_debiti, numero_totale_rate, importo_totale_debiti, importo_totale_rate, //
	tipologia_documento_di_pag_da_emettere, flag_accorpamento_debiti, nome_documento_lotto, numero_facciate_documento_lotto, //
	flag_fronte_retro_lotto, flag_bianco_nero_colore_lotto, tipo_postalizzazione, identificativo_vettore, prima_parte_denominazione_creditore_mitt, //
	seconda_parte_denominazione_creditore_mitt, indirizzo_creditore_mittente, completamento_indirizzo_creditore_mittente, cap_creditore_mittente, //
	comune_e_provincia_creditore_mittente, sigla_provincia_creditore_mittente, prima_parte_denominazione_ente, seconda_parte_denominazione_ente, //
	iban_conto_corrente_postale, autorizzazione_bollettino_postale, iban_conto_corrente_bancario, indirizzo_ente, cap_localita_provincia_ente, //
	telefono_ente, municipio, filler, versione_specifiche
    }

    public static enum TYPE {
	NUMBER_PAD, NUMBER, STRING, DATE
    }

    public ProprietaTracciatoLottoBean(CAMPI name, int length, String format, TYPE type) {

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
