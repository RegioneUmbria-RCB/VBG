package it.gruppoinit.pal.gp.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerArchiviazioneDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerInterventiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerProcedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.SorteggidettaglioDTO;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.TracciatoEquitaliaFilter;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.HelperTypeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.DettaglioRigaIstanze;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiIstanza;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiOggetto;
import it.gruppoinit.pal.gp.core.service.helper.IstanzePerArchiviazioneFilter;

public interface IstanzeDAO extends BaseDAO<Istanze, PkId> {

    /**
     * Metodo per ricercare tra le istanze. La stringa passata viene valuta come numeroistanza o descrizione del
     * richiedente
     * 
     * @param textToSearch
     * @return
     */
    public List<Istanze> findByNumeroistanzaOrRichiedente(String filterString);

    public void updateStatoIstanza(Istanze istanza, String nuovoStato);

    public void updateOrdineAttivita(Integer codIstanza, Integer nuovoOrdine);

    /**
     * Effettua una update della sola proprietà codiceComune
     * 
     * @param codiceIstanza
     * @param codiceComune
     */
    public void updateComuneIstanza(Integer codiceIstanza, String codiceComune);

    public void updateDatavalidita(String idcomune, Integer codiceIstanza, Date dataValidita);

    /**
     * Ritorna una lista di istanze filtrate per idcomune, software, alberoproc, data, tipomovimento (se non effettuato
     * o non presente). I valori per cui filtrare sono forniti tramite l'oggetto filter passato (IstanzeFilter)
     * 
     * @param IstanzeFilter
     * @return
     */
    public List<Istanze> findIstanzePerInserimentoMassivo(IstanzeFilter filter);

    public void updateMqIstanza(Integer codiceIstanza, BigDecimal metriquadrati);

    public List<Integer> findTuttiCodiciIstanzaPerSoftwareAndIntervento(String pSoftware, List<Integer> codiceIntervento);

    /**
     * 
     * @param filter
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<IstanzeListHelper> findIstanzeListHelperByFilter(IstanzeFilter filter, Integer firstResult, Integer maxResults);

    /**
     * 
     * @param filter
     * 
     * @return
     */
    public int countIstanzeListHelperByFilter(IstanzeFilter filter);

    /**
     * <pre>
     * Lista di istanze che appartengono all'attivita con data di validita <= della data di validità dell'istanza
     * passata (a parità di data verranno escluse quelle con ordine minore di quello passato). Es. Nun. Ist. Data
     * validità 1/2012 31/10/2012 2/2012 30/10/2012 4/2012 12/08/2012 8/2012 10/06/2012
     * 
     * @param codiceAttivita
     * @param dataValidità
     * @return Lista di istanze
     * 
     *         <pre>
     */
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, Integer ordine);

    /**
     * <pre>
     * Lista di istanze che appartengono all'attivita con data di validita <= della data di validità e nulla dell'
     * attività passata. Le istanze saranno ordinate per data validità asc (considerando il null come 01/01/1900) Es.
     * Nun. Ist. Data validità 1/2012 NULL 2/2012 10/06/2012 4/2012 12/08/2012 8/2012 30/09/2012
     * 
     * @param codice
     * @param codiceAttivita
     * @return Lista di istanze
     * 
     *         <pre>
     */
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, boolean includiDataValiditaNull);

    /**
     * Ritorna l' istanza rapppresentativa dell'atttivita alla data passata
     * 
     * @param codiceAttivita
     * @param dataValidita
     * @return
     */
    public Istanze findIstanzaUltimaAttivitaAllaData(Integer codiceAttivita, Date dataValidita);

    /**
     * Recupera il campo ordineattivita massimo per le istanze che appartengono all'attivita per la data passata
     * 
     * @param attivita
     * @param datavalidita
     * @return
     */
    public int findOrdineAttivitaMaxByData(IAttivita attivita, Date datavalidita);

    public void updateLavoriestesa(Integer codiceIstanza, String lavoriestesa);

    public void updateTipoProtFallita(Integer codiceIstanza, String tipoProtFallita);

    public void updateNumeroistanza(Integer codiceIstanza, String numeroistanza);

    public List<IstanzePerArchiviazioneDTO> findIstanzePerArchiviazioneDocumentale(IstanzePerArchiviazioneFilter filter);

    //
    public Set<IstanzePerArchiviazioneDTO> findIstanzeConOggettiPerArchiviazioneDocumentale(IstanzePerArchiviazioneFilter filter);

    public ArchiviazioneMetadatiIstanza findMetadatiIstanzaPerArchiviazioneDocumentale(Integer codiceIstanza);

    public List<ArchiviazioneMetadatiOggetto> findMetadatiOggettiPerArchiviazioneDocumentale(Integer codiceIstanza);

    public List<ArchiviazioneMetadatiOggetto> findMetadatiOggettiPerArchiviazioneDocumentale(Integer codiceIstanza,
	    IstanzePerArchiviazioneFilter filter, String algoritomoArchiviazione);

    /**
     * Recupera una lista di campi "istanza.attivitaOrdine" filtrando per attivita e data validità, ordinandoli in modo
     * crescente. I valori saranno salvati nell'oggetto istanzeDTO tramite la proprietà attivitaOrdine. L'oggetto
     * istanzeDTO conterrà anche il codice dell'istanza asscoiata.
     * 
     * @param codiceAttivita
     * @param dataValidita
     * @return
     */
    public List<IstanzeDTO> findAttivitaOrdineByAttivitaAndDatavalidita(Integer codiceAttivita, Date dataValidita);

    public List<CodiceDescrizioneBean> findMetadatiIstanza(Integer codiceIstanza);

    public List<IstanzeDaChiudereHelper> findIstanzedaChiudere();

    public List<IstanzePerInterventiHelper> countNumeroIstanzeGrupByInterventi(String codiceSoftware, Date fromDate, Date toDate, Integer startRow,
	    Integer maxRow);

    public List<IstanzePerProcedimentiHelper> countNumeroIstanzeGrupByProcedimenti(String codiceSoftware, Date fromDate, Date toDate,
	    Integer startRow, Integer maxRow);

    /**
     * <pre>
     * Il medoto sfrutta la creazione di QueryIstanzeHelper che crea una query di ricerca sulle istanze per i filtri passati. 
     * La query di select viene incapsulata in una query di insert.
     * ES.
     * 
     * Insert into NOME_TABELLA (nome_campo1,..nome_campoN) (select nome_campo1,..nome_campoN from NOME_TABELLA where condizioni
     * </pre>
     */
    public String exportModalitaPentaho(IstanzeFilter filter, Esportazioni esportazioni, String emailResponsabile, boolean isInviaMail);

    public void updateIstruttore(Integer codiceIstanza, Integer codiceIstruttore);

    public SorteggidettaglioDTO findByIstanza(Integer codiceIstanza);

    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice);

    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice);

    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorsoNew(Integer codiceIstanza,
	    Map<Integer, Integer> codResp, String scCodice, Integer idTestata);

    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorsoNew(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice, Integer idTestata);

    public void updateContatori(boolean processaSoloIlPrimoGiornoDEllanno);

    public void updatePrendiInCarico(Integer codiceIstanza, Integer codiceResponsabile);

    /**
     * <pre>
     * Recupera istanze simile andando a fare il match per stradario,civico,esponente,colore (se popolati). Verra esclusa 
     * l'istanza di partenza che verrà passata nel parametro "codiceistanza" (se null il filtro non viene applicato). La ricerca 
     * verrà effettuata su tutti i software.
     * &#64;param codiceStradario
     * &#64;param civico
     * &#64;param esponente
     * &#64;param colore
     * &#64;param codiceIstanza
     * &#64;param firstResult
     * @param maxResult
     * </pre>
     */
    public List<IstanzeListHelper> findIstanzaLocalizzazioneSimile(Integer codiceStradario, String civico, String esponente, String colore,
	    Integer codiceIstanza, Integer firstResult, Integer maxResult);

    public boolean findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita);

    public List<Integer> findIstanzaPerTracciatoEquitalia(TracciatoEquitaliaFilter tracciatoEquitaliaFilter, Boolean isInviate, Integer firstResult,
	    Integer maxResults);

    public Integer countPerTracciatoEquitalia(TracciatoEquitaliaFilter tracciatoEquitaliaFilter, Boolean isInviate);

    /**
     * <pre>
     * 	Ritorna tutte le istanze che hanno:
     * 	  1. istanze.tipoProtFallita != null
     * 	  2. istanze.numeroprotocollo != null
     * 	  3. tipo protocollazione passata
     * &#64;param codicetipoprotocollazione
     * @return
     * </pre>
     */
    public List<Integer> findIstanzaProtocollazioneFallitaByTipoProtocollazione(String[] codicetipoprotocollazione);

    public boolean isChiusa(Integer codiceIstanza);

    List<DettaglioRigaIstanze> findIstanzeListHelperByFilterMass(IstanzeFilter filter, HelperTypeEnum type, Integer firstResult, Integer maxResults);
}
