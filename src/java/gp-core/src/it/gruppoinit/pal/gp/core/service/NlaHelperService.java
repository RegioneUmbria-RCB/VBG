package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeNlaHelper;
import it.gruppoinit.pal.gp.core.service.helper.NodoNLAEnum;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.StatoPraticaType;

public interface NlaHelperService {

    public final String ALTRI_DATI_AREARISERVATA_EVENTI = "AREARISERVATA_EVENTI";
    public final String ALTRI_DATI_DENOMINAZIONE_ATTIVITA = "$ISTANZA_NOMEATTIVITA$";
    public final String ALTRI_DATI_NATURA_ENDO_PRINCIPALE = "$ISTANZA_NATURA_ENDO_PRINCIPALE$";
    public final String ALTRI_DATI_NOTE_ISTANZESTRADARIO = "$NOTE_ISTANZESTRADARIO$-";
    public final String ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_ISTANZA = "$DOC_VALIDO_NON_VALIDO_DOC_ISTANZA$";
    public final String ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_ENDO = "$DOC_VALIDO_NON_VALIDO_DOC_ENDO$";
    public final String ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_PROCURE = "$DOC_VALIDO_NON_VALIDO_DOC_PROCURE$";
    public final String ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_MOVIMENTO = "$DOC_VALIDO_NON_VALIDO_DOC_MOVIMENTO$";
    public final String ALTRI_DATI_CODCIVICO_ISTANZESTRADARIO = "$CODCIVICO_ISTANZESTRADARIO$-";
    public static final String ALTRI_DATI_ISTANZE_MOVIMENTI_FKIDPROTOCOLLO = "$PROTOCOLLO_FIKIDPROTOCOLLO$";
    public static final String ALTRO_DATO_NOTIFICA_ATTIVITA_PRATICA_CREATA_DA_ATTIVITA = "$PRATICA_CREATA_DA_ATTIVITA$";
    public static final String CODICEOGGETTO_CONST_ALLEGATI = "CODICEOGGETTO:";
    public static final String CODICEOGGETTO_CONST_ALLEGATI_CODICE_ALTRO_SISTEMA = "CODICE_ALTRO_SISTEMA:";
    public static final String ALTRI_DATI_PARAM_INSERIMENTO_DIRETTO = "$INSERIMENTO_DIRETTO$";
    public final String ALTRI_DATI_ISTANZE_NUMERO_PROTOCOLLO_MITTENTE = "$NUMERO_PROTOCOLLO_MITTENTE$";
    public final String ALTRI_DATI_ISTANZE_DATA_PROTOCOLLO_MITTENTE = "$DATA_PROTOCOLLO_MITTENTE$";
    public static final String NOTIFICA_ATTIVITA_ALTRO_DATO_ALBEROPROC = "ALBEROPROC.SC_ID";
    /**
     * I PARAMETRI CHE PRESENTANO QUESTO DATO DURANTE LA NOTIFICA DELL'ATTIVITA' VENGONO ANCHE SPOSTATI DA STC NEGLI
     * ALTRI DATI DI DETTAGLIO PRATICA (ES {NLASTCBASESERVICEIMPL#NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC})
     */
    public static final String PREFISSO_STC_TRASFERISCE_DATO_SU_ISTANZA = "STC-TRASF-DATO-SU-IST";
    public static final String NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC = PREFISSO_STC_TRASFERISCE_DATO_SU_ISTANZA +
	    "$CODICE_AMMINISTRAZIONE_STC$";
    public static final String NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC_MITTENTE = PREFISSO_STC_TRASFERISCE_DATO_SU_ISTANZA +
	    "$CODICE_AMMINISTRAZIONE_STC_MITTENTE$";
    public static final String CODICE_AMMINISTRAZIONE_MOVIMENTO_MITTENTE = "$CODICE_AMMINISTRAZIONE_MOVIMENTO_MITTENTE$";
    /**
     * SE STC TROVA QUESTO DATO NELL'INSERIMENTO PRATICA COPIA LA LISTA DEGLI ENDO (OLTRE A QUELLO PRINCIPALE DELLA
     * NOTIFICA ATTIVITA') VERIFICANDO IL CODICE PRESENTE NEGLI ALTRI DATI CON QUELLI PRESENTI NELLA ISTANZA. SE
     * PRESENTE NOTIFICA INTERA PRATICA ALLORA VINCE QUESTA
     */
    public static final String NOTIFICA_ATTIVITA_LISTA_ENDO_PROCEDIMENTI_DA_COPIARE = PREFISSO_STC_TRASFERISCE_DATO_SU_ISTANZA +
	    "$INSERIMENTO_PRATICA_COPIA_GLI_ENDO_IN_LISTA$";
    public static final String NOTIFICA_ATTIVITA_PREFISSO_MOVIMENTO_METADATO = "METADATO_MOVIMENTO_";

    public String generateNumeroistanza(InserimentoPraticaNLARequest praticaNla, boolean isNuovaLogicaSuaper);

    public Integer generateCodiceIstanza();

    public IstanzeNlaHelper populateIstanza(Istanze istanza, InserimentoPraticaNLARequest praticaNla, boolean passaProtocollo,
	    boolean isNuovaLogicaSuaper, boolean isDaLocale);

    public Movimenti populateMovimenti(InserimentoAttivitaNLARequest request, Istanze istanza, String codiceAmministrazione, boolean passaProtocollo,
	    boolean isPecOpRoto);

    public boolean checkSportello(SportelloType sportelloType, NodoNLAEnum nodoNLAVertParamKey);

    /**
     * 
     * @param istanza
     * @param soloLeAttivitaEseguitePubblicate
     *            seleziona solamente le attività eseguite con flag pubblica true
     * @param soloDocESchedeDiQuestiEndo
     * @return
     */
    public RichiestaPraticaNLAResponse populateRichiestaPraticaNLAResponse(Istanze istanza, boolean soloLeAttivitaEseguitePubblicate,
	    Set<Integer> soloDocESchedeDiQuestiEndo);

    public StatoPraticaType decodeStatoPratica(Istanze istanza);

    public AnagrafeType populateAnagrafeType(Integer codiceAnagrafe);

    /**
     * La funzione controlla se i nodi chiamanti appartengono a nodi interni a SIGEPRO in modo tale da recuperare alcune
     * informazioni direttamente dalla base dati piuttosto che da chiamate WS (es.: AllegatoBinario, oppure
     * all'inserimento pratica/attività assegnazione degli oggetti direttamente da codiceoggetto e non da
     * stc_idallegato).
     * 
     * @param destinatario
     * @param mittente
     * @param consideraDatiConsole
     * @return
     */
    public boolean isChiamataDaNodoInterno(SportelloType destinatario, SportelloType mittente, boolean consideraDatiConsole);

    /**
     * Costruisce un oggetto ErroreType
     * 
     * @param idnodo
     * @param idente
     * @param idsportello
     * @param errCode
     * @param e
     * @param methodName
     * 
     * @return
     */
    public ErroreType populateErroreType(String idnodo, String idente, String idsportello, String errCode, Exception e, String methodName);

    /**
     * Torna il tipo di nodo associato allo sportello passato come argomento Se il nodo non è presente o configurato
     * allora torna {@link NodoNLAEnum#NLA_IDNODO_SCONOSCIUTO}
     * 
     * @param mittDest
     * @return
     */
    public NodoNLAEnum getTipoNodo(SportelloType mittDest);

    /**
     * Costruisce e popola un oggetto {@link AnagrafeType} a partire da un oggetto {@link Anagrafe}
     * 
     * @param anagrafe
     * @return
     */
    public AnagrafeType populateAnagrafeType(Anagrafe anagrafe);

    public RichiedenteType populateRichiedenteType(Anagrafe anagrafe, Integer codiceTiposoggetto, String tiposoggetto);

    public void addMetadatiOggetto(Integer codiceOggetto, DocumentiType documentoType, AllegatiType allegato);

    /**
     * metodo per la gestione degli allegati presenti nell'xml inviato da STC
     * 
     * @param documentiType
     * @param isNodoInterno
     * @return
     */
    public Oggetti getOggettoFromDocumentiType(DocumentiType documentiType, boolean isNodoInterno, SportelloType mittente, SportelloType destinatario,
	    String riferimentoPraticaStc, String tokenStc);

    /**
     * A partire dal codice istanza crela la lista di oggetti LocalizzazioneNelComuneType
     * 
     * @param codiceIstanze
     * @return
     */
    public List<LocalizzazioneNelComuneType> populateLocalizzazione(Integer codiceIstanze);

    /**
     * A partire dalla stato istanza ritona lo stato della pratica
     * 
     * @param codiceIstanze
     * @return
     */
    public StatoPraticaType decodeStatoPratica(Statiistanza statiistanza);

    /**
     * torna una lista di documenti presa dalla request di cui sia non nulla la proprietà allegati
     * 
     * @param dettaglioPratica
     * @return
     */
    public List<DocumentiType> getDocsPerPratica(DettaglioPraticaType dettaglioPratica);

    /**
     * 
     * @param praticaNla
     * @return
     */
    public boolean verificaNuovaLogicaSuaper(InserimentoPraticaNLARequest praticaNla);

    public Integer getValoreControlloOk(List<ParametroType> altriDati, String id, String chiaveDaAprire);

    public void addAltroDato(String chiaveAltroDato, Integer codice, Integer controllook, DettaglioPraticaType dettaglioPraticaType);

    public void addAltroDato(String chiaveAltroDato, Integer codice, Integer controllook, DettaglioAttivitaType dettaglioAttivitaType);

    public boolean isNodoARConsole(SportelloType mittente);

    /**
     * Torna true solo se due sportelli hanno stesso idnodo ma idente differente.
     * 
     * @param mittente
     * @param destinatario
     * @return
     */
    public boolean checkIsStessoNodoEnteDifferente(SportelloType mittente, SportelloType destinatario);

    /**
     * Verifica se i due nodi hanno lo stesso idnodo e stesso idente
     * 
     * @param mittente
     * @param destinatario
     * @return
     */
    public boolean checkIsStessoNodoStessoEnte(SportelloType mittente, SportelloType destinatario);

    public boolean isScaricaSubitoAllegatiFisiciPerNodo(SportelloType sportello);

    public Comuni getComune(DettaglioPraticaType dettaglioPratica);

    /**
     * Verifica se lo sportello è quello del nodo configurato come NON VBG o SUAPE per il quale viene attivata la
     * funzionalità invia pratiche come ZIP
     * 
     * @param sportelloMittente
     * @return
     */
    public boolean isNodoEnteNonLocalePraticaZIP(SportelloType sportelloMittente);

    public boolean isCodiceOggettoAltroSistema(SportelloType sportelloMittente, SportelloType sportelloDestinatario);
}
