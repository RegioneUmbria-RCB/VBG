package it.gruppoinit.pal.gp.pay.connector.easypa;

public class EasyPAConstants {

    /**
     * 0 = Firma non richiesta 1 = CaDes 3 = XaDes 4 = Elettronica avanzata
     */
    public enum TipoFirma {

	Firma_non_richiesta(0), CaDes(1), XaDes(3), Elettronica_avanzata(4);

	private int valore;

	private TipoFirma(int valore) {

	    this.valore = valore;
	}

	public int valore() {

	    return this.valore;
	}
    }

    /**
     * {@link TipoPagamento#Y} spontaneo <br/>
     * {@link TipoPagamento#N} predeterminato
     * 
     * @author riccardob
     *
     */
    public enum TipoPagamento {
	Y, N
    }

    public enum EsitiEasyPA {
	OK, KO
    }

    /**
     * F Persona Fisica, G persona giuridica
     * 
     * @author riccardob
     *
     */
    public enum TipoIdDebitoreAnagrafe {
	F, G
    }

    public enum CodiciErroreEasyPA {

	ERRORE_UTENZA_PASSWORD(
		"Autenticazione fallita La user e/o la password di autenticazione dell’ente creditore su Web Pay PA risultano errate."), //
	ERRORE_CAMPO_OBBLIGATORIO(
		"“NomeCampo” è un campo obbligatorio Controllo applicato a tutti i campi definiti obbligatori nella struttura della Request."), //
	SERVIZIO_NON_TROVATO(
		"Servizio non trovato Non esiste nella base dati Web Pay PA il servizio indicato oppure il servizio non è associato all’ente creditore."), //
	SERVIZIO_NON_TROVATO_MEDIANTE_CCP(" Non esiste nella base dati Web Pay PA il servizio associato al Conto Corrente Postale."), //
	ERRORE_SUL_NODO(
		"Dati specifici riscossione non presenti sul servizio di incasso L’errore si verifica in presenza di pagamento predeterminato e assenza del pagamento nella base dati Web Pay PA"), // 
	ERRORE_GENERAZIONE_IUV(
		"Il campo Identificativo Univoco Versamento non deve essere valorizzato per questo servizio L’errore si verifica in presenza di pagamento spontaneo con IUV valorizzato e generazione IUV in carico a Web Pay PA (configurazioni a livello di servizio). Il campo Identificativo Univoco Versamento deve essere valorizzato per questo servizio . L’errore si verifica quando l’IUV non è valorizzato in presenza di pagamento predeterminato oppure spontaneo con generazione IUV in carico all’ente (configurazioni a livello di servizio)."), // 
	SEMINCASSO_NON_RISCUOTIBILE(
		"Pagamento non eseguibile perché in stato incassato, revocato, stornato o errato L’errore si verifica in presenza di pagamento che non assume stato da incassare."), // 
	ERRORE_VALORE_NON_PREVISTO("Valore non previsto "), //
	ERRORE_DATA_NON_VALIDA("Data non valida: inserirla nel formato YYY-DD-MM Formato data non valida."), //
	ERRORE_IMPREVISTO("Errore non previsto in fase di verifica incasso Errore generico che non rientra nelle precedenti casistiche.");

	private String desc;

	private CodiciErroreEasyPA(String desc) {

	    this.desc = desc;
	}

	public String value() {

	    return name();
	}

	public String description() {

	    return desc;
	}
    }
}
