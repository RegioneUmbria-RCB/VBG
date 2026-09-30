package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public enum CommissioniCategorieEnum {

    COMMISSIONE_CREATA("Commissione creata"), // 
    COMMISSIONE_AGGIORNATA("Commissione aggiornata"), // 
    COMMISSIONE_RIAPERTA("Commissione riaperta"), //
    COMMISSIONE_CHIUSA("Commissione chiusa"), //
    AR_ACCESSO_A_VOTO_NEGATO("Accesso alla votazione della commissione da frontend non consentito"), //
    AR_ACCESSO_COMMISSIONE("Accesso a commissione da frontend"), // 
    AR_ACCESSO_COMMISSIONE_NEGATO("Accessoa commissione negato a utente frontend"), // 
    AR_ACCESSO_PRATICA("Accesso a pratica da frontend"), //
    AR_ACCESSO_PRATICA_NEGATO("Accesso a pratica da frontend negato"), // 
    AR_ACCESSO_FILE("Accesso a file da frontend"), //
    AR_ACCESSO_FILE_NEGATO("Accesso a file da frontend negato"), //
    CONVOCAZIONE_COMMISSIONE_CREATA("Convocazione commissione creata"), //
    CONVOCAZIONE_COMMISSIONE_AGGIORNATA("Convocazione commissione aggiornata"), //
    CONVOCAZIONE_COMMISSIONE_ELIMINATA("Convocazione commissione eliminata"), //
    ISTANZE_AGGIUNTE_COMMISSIONE("Istanze aggiunte in commissione"), //
    ISTANZA_ELIMINATA_COMMISSIONE("Istanza eliminata dalla commissione"), //
    DOCUMENTI_INSERITI("Aggiunto documento a commissione"), //
    DOCUMENTI_MODIFICATI("Modificato documento in commissione"), //
    DOCUMENTI_ELIMINATI("Eliminato documento dalla commissione"), //
    VOTAZIONE_COMMISSIONE_CREATA("Inserimento votazione commissione"), //
    VOTAZIONE_COMMISSIONE_MODIFICATA("Modifica votazione commissione"), //
    ESITO_VOTAZIONE_ELIMINATO("Esito votazione commissione eliminato"), //
    ESITO_VOTAZIONE_INSERITO("Esito votazione commissione inserito"), //
    ESITO_VOTAZIONE_MODIFICATO("Esito votazione commissione modificato"), //
    AR_VOTO_INSERITO("Voto pratica inserito da frontend"), //
    AR_VOTO_MODIFICATO("Voto pratica modificato da frontend"), //
    AR_ALLEGATO_APPROVATO("Allegato pratica approvato da frontend"), //
    AR_APPROVAZIONE_ALLEGATO_FALLITA("Approvazione allegato da frontend fallita"), //
    AR_PARERE_PROTOCOLLATO("Parere protocollato da frontend"), //
    AR_PARERE_ERRORE_PROTOCOLLAZIONE("Errore durante la protocollazione del parere"), //
    AR_UTENTE_ASSOCIATO("L'utente è stato associato ad una commissione"), //
    COMUNICAZIONE_COMMISSIONE_CREATA("Comunicazione Commissione creata"), //
    COMUNICAZIONE_COMMISSIONE_ELIMINATA("Comunicazione Commissione eliminata");

    CommissioniCategorieEnum(String s) {

	this.setNomeCategoria(s);
    }

    public String getNomeCategoria() {

	return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {

	this.nomeCategoria = nomeCategoria;
    }

    private String nomeCategoria;
}
