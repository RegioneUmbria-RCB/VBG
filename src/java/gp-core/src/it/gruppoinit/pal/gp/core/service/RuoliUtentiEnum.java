package it.gruppoinit.pal.gp.core.service;

public enum RuoliUtentiEnum {

    GESTIONE_POSIZIONI_DEBITORIE("Accesso alla funzionalità di gestione delle posizioni debitorie", 0), // 
    GESTIONE_CALENDARIO_MERCATI("Accesso alla funzionalità di gestione dei calendari di mercati", 1), //
    OP_ESTERNE_CARICA_ZIP("Accesso alla funzionalità di caricamento pratica da ZIP", 2),
    GESTIONE_PROCEDIMARCHE("Accesso alle funzionalità di modifica e pubblicazione della scheda descrittiva del procedimento su ProcediMarche", 3), //
    SPOSTAMENTO_PRATICHE("Abilita la possibilità di spostare le pratiche da una volce di intervento ad un'altra in maniera massiva", 4), //
    GESTIONE_ABBONAMENTO("Accesso alla funzionalità del borsellino", 5), //
    ATTIVA_CHIUSURA_MANUALE_GRUPPI_ISTRUTTORI("Permette all'operatore la chiusura manuale di un gruppo istruttori", 6), //
    GESTIONE_ACCERTAMENTI_ESECUTIVI("Permette all'operatore di accedere alla blacklist", 7),
    GESTIONE_REGOLE("Accesso alla funzionalità di gestione regole", 8),
    OPERATION("Ruolo Operation", 9);//

    private String descrizione;
    private int ordine;

    private RuoliUtentiEnum(String descrizione, int ordine) {

	this.descrizione = descrizione;
	this.ordine = ordine;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public int getOrdine() {

	return ordine;
    }
}
