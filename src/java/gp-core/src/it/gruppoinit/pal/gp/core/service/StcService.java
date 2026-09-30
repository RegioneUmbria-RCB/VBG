package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import org.hibernate.validator.InvalidValue;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.Dyn2ModellitValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.TipimovStcAltridatiValoreBean;
import it.gruppoinit.pal.gp.core.service.exception.STCNotificaAttivitaException;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.ValoreParametroType;

public interface StcService {

    public static final String ALTRO_DATO_RISERVATO_RIFPROTO = "$_RIF_PROT_$";
    public static final String ALTRO_DATO_RISERVATO_DESELEZIONA_TUTTI_I_FILE = "$DESELEZIONA_TUTTI_I_FILE$";
    public static final String ALTRO_DATO_RISERVATO_RIFPROTO_NUM = "$_RIF_PROT_NUM_$";
    public static final String ALTRO_DATO_UUID_ISTANZA = "$UUID_ISTANZA$";
    public static final String ALTRO_DATO_RISERVATO_RIFPROTO_DATA = "$_RIF_PROT_DATA_$";
    public static final String ALTRO_DATO_RISERVATO_TIPO_OPERAZIONE_CART = "tipo_operazione";
    public static final String ALTRO_DATO_RISERVATO_TIPO_OPERAZIONE_CART_NOTIFICA = "notifica";
    public static final String ALTRO_DATO_GUID_ZIP_LOGICO_DI_ORIGINE = "$GUID_ZIP_LOGICO_DI_ORIGINE";
    public static final String ALTRO_DATO_PARAMETRO_RECUPERA_INFO_FASCICOLO_PRATICA = "$RECUPERA_INFO_FASCICOLO_PRATICA$";
    public static final String ALTRO_DATO_PRATICA_NUMERO_ANNO_FASCICOLO = "$PRATICA_NUMERO_ANNO_FASCICOLO$";

    public void cancellaAttivita(SportelloType sportello, Integer codiceMovimento);

    /**
     * 
     * @param codiceMovimento
     *            Codice del movimento per il quale effettuare la notifica
     * @param altriDatiList
     *            lista dei campi aggiuntivi immessi dall'utente
     * @param modelliList
     *            lista dei modelli dinamici da inviare scelti dall'utente. Ogni modello inviato va aggiunto come riga
     *            come altro dato
     * @param istanzeAllegatiList
     *            lista degli allegati dell'endoprocedimento scelti dall'utente
     * @param documentiistanzaList
     *            lista dei documenti istanza scelti dall'utente
     * @param movimentiallegatiList
     *            lista degli allegati del movimento scelti dall'utente
     * @param rifPraticaDestinatario
     *            riferimenti pratica del destinatario
     * @param requirePraticaDestinatario
     *            se forzare la richiesta dei riferimenti pratica destinatario
     * @param isNonInviareProcedimenti
     * @param amministrazioneStc
     *            L'amministrazione di destinazione della notifica
     * @param codiceMovimentoRifProto
     *            il codice del movimento per il quale inviare i riferimenti di protocollo
     * 
     * @return
     */
    public String notificaAttivita(Integer codiceMovimento, List<TipimovStcAltridatiValoreBean> altriDatiList,
	    List<Dyn2ModellitValoreBean> modelliList, List<IstanzeallegatiDTO> istanzeallegatis, List<DocumentiistanzaDTO> documentiistanzas,
	    List<MovimentiallegatiDTO> movimentiallegatis, List<AnagrafedocumentiDTO> documentiAnagrafe, List<IstanzeprocureDTO> documentiprocuras,
	    RiferimentiPraticaType rifPraticaDestinatario, boolean requirePraticaDestinatario, boolean isNonInviareProcedimenti,
	    Amministrazioni amministrazioneStc, Integer codiceMovimentoRifProto);

    /**
     * Ricerca nel sistema remoto la pratica collegata
     * 
     * @param codiceIstanza
     * @param codiceMovimento
     * @param codiceAmministrazioneSTC
     * @return
     */
    //public RichiestaPraticaCollegataResponse richiestaPraticaCollegata(Integer codiceIstanza, Integer codiceMovimento,Integer codiceAmministrazioneSTC);
    public RichiestaPraticaCollegataResponse richiestaPraticaCollegata(SportelloType mittente, SportelloType destinatario, Integer codiceIstanza,
	    String idProcedimento);

    /**
     * Visualizza l'allegato di una pratica remota dati i riferimenti del codice iddocumento ed idallegato
     * 
     * @param codiceIstanza
     * @param codiceMovimento
     * @param stcIddocumento
     * @param stcIdallegato
     * @return
     */
    public AllegatoBinarioResponse allegatoBinario(Integer codiceIstanza, Integer codiceMovimento, String stcIddocumento, String stcIdallegato);

    /**
     * Richiesta di informazioni su una pratica su un sistema remoto di cui sono inputati i riferimenti serve per
     * ricercare dati i riferimenti se una pratica esiste o meno
     * 
     * @param codiceMovimento
     * @param riferimentiPraticaType
     *            i riferimenti immessi come filtri dall'utente
     * @return
     */
    public RichiestaPraticaResponse richiestaPratica(Integer codiceMovimento, RiferimentiPraticaType riferimentiPraticaType);

    public RichiestaPraticaResponse richiestaPratica(SportelloType sportelloDestinatario, RiferimentiPraticaType riferimentiPraticaType);

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaMittente(SportelloType sportello, Integer codiceMovimento);

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaDestinataria(SportelloType sportello, Integer codiceMovimento);

    /**
     * Verifica che la configurazione del movimento sia corretta
     * 
     * @param movimento
     * @return
     */
    public boolean validateConfigurazione(Movimenti movimento);

    /**
     * Verifica i dati minimi per per l'invio STC
     * 
     * @param movimento
     * @return
     */
    public List<InvalidValue> validateDatiPerInvio(Movimenti movimento);

    public boolean checkSeAbilitareRichiestaPraticaCollegata(Integer codiceIstanza);

    /**
     * Effettua la notifica automatica dell'attività a partire da un codice movimento
     * 
     * @param codiceMovimento
     * @param amministrazioneStc
     * @return
     */
    public String notificaAutomaticaAttivita(Integer codiceMovimento, Amministrazioni amministrazioneStc) throws STCNotificaAttivitaException;

    /**
     * Copia l'oggetto in locale e inoltre lega il riferimento alla rispettiva tabella di provenienza (DOCUMENTIISTANZA,
     * ISTANZEALLEGATI, MOVIMENTIALLEGATI), a seconda del contesto passoto.
     * 
     * @param codiceIstanza
     * @param codiceMovimento
     * @param stcIddocumento
     * @param stcIdallegato
     * @param contesto
     * @param codice
     */
    public void insertAllegatoInLocale(Integer codiceIstanza, Integer codiceMovimento, String stcIddocumento, String stcIdallegato, String contesto,
	    Integer codice);

    /**
     * Controlla se per l'istanza passata esistono allegati provenieti da STC, appena ne trova uno esce e ritorna true
     * ,se non ne trova ritorna false
     * 
     * @param codiceIstanza
     * @return
     */
    public boolean isAllegatiStcExist(Integer codiceIstanza);

    /**
     * <pre>
     * Il metodo recupera tutte le schede associate all'istanza che possono essere invite dalla notifica STC.
     * Il metodo ritorna una lista Dyn2ModellitValoreBean che ha una struttura chiave valore dove:
     * 
     * <pre>
     * <b>chiave</b> : è l'oggetto Dyn2Modellit
     * <b>valore</b> : è un booleano che è:
     * 		true : se la scheda deve essere presentata sulla maschera di invio selezionata di default da inviare
     *          false: se la schede deve essere selezionata manualmente dall'operaratore se vuole che venga inviata.
     *                                    
     * La creazione della lista segue due logiche, in base alla configurazione del tipo movimento che genera la notifica:
     *          se TipimovStcMapping.flagInviaschedeistanza legato al tipo movimento e all'amministrazione stc è uguale:
     * 
     * 		Caso 1- true : Tutte gli oggetti Dyn2ModellitValoreBean vengono popolati con valore uguale a <b>true</b>
     * 		Caso 2- false: Solo gli oggetti Dyn2ModellitValoreBean, che hanno come chiave un oggetto Dyn2Modellit configurato nel tipo movimento che genera
     *  				    la notifica e per l'amministrazione stc, vengono popolati con valore uguale a <b>true</b>
     * </pre>
     * 
     * @param movimento
     * @param amministrazioniSTC
     *            : può essere null, se passato come null viene utlizzato quello del movimento (in caso di notifica
     *            automatica è necessario specificarlo)
     * @return
     * 
     *         </pre>
     */
    public List<Dyn2ModellitValoreBean> findListaIstanzeModelli(Movimenti movimento, Amministrazioni amministrazioniSTC)
	    throws STCNotificaAttivitaException;

    /**
     * Recupera tutti gli allegati presenti nei movimenti associati all'istanza, la struttura così recuperata permette
     * in visualizzazione di mostrare gli allegati divisi per movimenti; se gli allegati sono quelli del movimento che
     * genera la notifica allora verrà popolato il campo transiet "TransientSegnaPerInvio" a true (Il campo permette in
     * visualizzazione di mostrare gli allegati movimenti "come da inviare" di default)
     * 
     * @param istanza
     * @param movimento
     * @return
     */
    public List<ChiaveValoreBean<String, List<Movimentiallegati>>> findListaMovimentiAllegati(Istanze istanza, Movimenti movimento)
	    throws STCNotificaAttivitaException;

    /**
     * Recupera tutti gli allegati presenti negli endoprocedimenti associati all'istanza, la struttura così recuperata
     * permette in visualizzazione di mostrare gli allegati divisi per endoprocedimenti,se gli allegati sono quelli
     * dell' endo legato al movimento che genera la notifica allora verrà popolato il canpo transient
     * "TransientSegnaPerInvio" a true
     * 
     * @param istanza
     * @return
     */
    public List<ChiaveValoreBean<String, List<Istanzeallegati>>> findListaIstanzeallegati(Istanze istanza, Movimenti movimenti)
	    throws STCNotificaAttivitaException;

    /**
     * Recupera tutti i documenti associati all'istanza, genera la notifica allora verrà popolato il canpo transient
     * "TransientSegnaPerInvio" a true
     * 
     * @param istanza
     * @return
     */
    public List<Documentiistanza> findListaDocumentiistanza(Istanze istanza, Movimenti movimenti) throws STCNotificaAttivitaException;

    public InserimentoPraticaResponse inviaPratica(InserimentoPraticaRequest request);

    /**
     * LA funzione ritorna la lista di valori associati ad una chiave
     * 
     * @param parametri
     * @param nome
     * @return
     */
    public List<ValoreParametroType> getValoriFromParametroTypeByNome(List<ParametroType> parametri, String nome);
}
