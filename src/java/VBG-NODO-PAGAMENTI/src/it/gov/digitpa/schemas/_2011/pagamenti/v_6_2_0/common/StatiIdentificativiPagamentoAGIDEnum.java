package it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.common;

import java.util.HashMap;
import java.util.Map;

public enum StatiIdentificativiPagamentoAGIDEnum {

    //  id_stato	desc_stato	modificabile
    //  0	Da pagare	true
    DA_PAGARE(0, "Da Pagare", true),
    //  1	Compilato	true
    COMPILATO(1, "Compilato", true),
    //  2	In attesa	true
    IN_ATTESA(2, "In Attesa", true),
    //  3	Fallito	true
    FALLITO(3, "Fallito", true),
    //  4	Successo	false
    SUCCESSO(4, "Successo", false),
    //  5	Annullato	true
    ANNULLATO(5, "Annullato", true),
    //  6	Transazione inizializzata	true
    TRANSAZIONE_INIZIALIZZATA(6, "Transazione inizializzata", true),
    //  7	Transazione avviata	true
    TRANSAZIONE_AVVIATA(7, "Transazione avviata", true),
    //  8	Transazione errore	true
    TRANSAZIONE_ERRORE(8, "Transazione errore", true),
    //  9	Invalidato dall'ente	false
    INVALIDATO_DA_ENTE(9, "Invalidato dall'ente", false),
    //  10	Pagamento revocato	false
    PAGAMENTO_REVOCATO(10, "Pagamento revocato", false);

    private String descrizione;
    private boolean modificabile;
    private int idStato;

    private StatiIdentificativiPagamentoAGIDEnum(int idStato, String descrizione, boolean modificabile) {

	this.descrizione = descrizione;
	this.idStato = idStato;
	this.modificabile = modificabile;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public boolean isModificabile() {

	return modificabile;
    }

    public int getIdStato() {

	return idStato;
    }

    public static StatiIdentificativiPagamentoAGIDEnum fromIdStato(int idStato) {

	StatiIdentificativiPagamentoAGIDEnum ret = map.get(idStato);
	if (ret == null) {
	    throw new IllegalArgumentException(
		    "Id stato " + idStato + " non valido per l'enumerazione " + StatiIdentificativiPagamentoAGIDEnum.class);
	}
	return ret;
    }

    public static void main(String[] args) {

	for (int i = 0; i < 12; i++) {
	    System.out.println(fromIdStato(i));
	}
    }

    private static Map<Integer, StatiIdentificativiPagamentoAGIDEnum> map = new HashMap<>();
    static {
	for (StatiIdentificativiPagamentoAGIDEnum stato : StatiIdentificativiPagamentoAGIDEnum.values()) {
	    map.put(stato.idStato, stato);
	}
    }

    @Override
    public String toString() {

	return name() + "[ idStato: " + this.idStato + ", descrizione: " + this.descrizione + ", modificabile: " + this.modificabile + "]";
    }
}
