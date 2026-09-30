package it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerInterventiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerProcedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.SorteggidettaglioDTO;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCommand;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeOnLineHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzePerAttivitaFilter;
import it.gruppoinit.pal.gp.core.domain.web.TracciatoEquitaliaFilter;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.HelperTypeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.DettaglioRigaIstanze;
import it.gruppoinit.pal.gp.core.features.istanze.rest.AggiornaRiferimentiProtocolloIstanzaRequest;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.AggiornamentoProtocolloException;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.IPostIstanzeInsertCallBack;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniAccessiHelper;
import it.gruppoinit.pal.gp.core.service.helper.ResponsabiliAssegnazioniHelper;
import it.gruppoinit.pal.gp.core.service.helper.TempisticaIstanzaHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

public class FakeIstanzeService implements IstanzeService {

    private Istanze istanza;

    public FakeIstanzeService(Istanze istanza) {

	this.istanza = istanza;
    }

    @Override
    public Istanze findById(PkId id) {

	return this.istanza;
    }

    @Override
    public Istanze bindDomainObject(Istanze entity, Class<?> idClass, String idPath) {

	return null;
    }

    @Override
    public PkId newIdFromSequencetable(Istanze entity) {

	return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public List<Istanze> findAll(Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public List<Istanze> findByNumeroistanzaOrRichiedente(String filterString) {

	return null;
    }

    @Override
    public List<Istanze> findByProtocolloNumeroRichiedenteAziendaOrPEC(String filterString, boolean protocolloInMovimenti) {

	return null;
    }

    @Override
    public List<Istanze> findIstanzeDaGraduatoria(Integer codiceGraduatoria, String tipoMovimento, String soggettoMovimento) {

	return null;
    }

    @Override
    public List<Istanze> findByFilterTable(FilterTable filterTable) {

	return null;
    }

    @Override
    public List<Istanze> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public List<Istanze> findByFilter(IstanzeFilter filter) {

	return null;
    }

    @Override
    public TipoAccessoEnum checkAccessoIstanza(Istanze istanza, Responsabili responsabile) {

	return null;
    }

    @Override
    public String findProgressivoIstanza(Integer alberoprocCodice, boolean scriviSubito) {

	return null;
    }

    @Override
    public String checkModificaIntervento(Istanze istanza) {

	return null;
    }

    @Override
    public void inserisciPermessoIstanza(Istanze entity, Responsabili responsabile) {

    }

    @Override
    public void inserisciRuoloIstanza(Istanze entity, Ruoli ruolo) {

    }

    @Override
    public void insert(Istanze entity) {

    }

    @Override
    public void insert(Istanze entity, TipoInserimento tipoInserimento, IPostIstanzeInsertCallBack postCallBack) {

    }

    @Override
    public Movimenti insertMovimentoChiusura(Integer codice, boolean isEsitoPositivo) {

	return null;
    }

    @Override
    public void delete(Istanze entity) {

    }

    @Override
    public Mailtipo findProtocolloOggetto(Istanze entity) {

	return null;
    }

    @Override
    public String findFascicoloOggetto(Istanze entity) {

	return null;
    }

    @Override
    public void operazioniAutomatiche(Istanze entity, TipoInserimento tipoInserimento, AlberoprocHelper helper,
	    IPostIstanzeInsertCallBack postCallBack) throws OperazioniAutomaticheException {

    }

    @Override
    public void insertEventiCheckCodiceFiscale(Istanze entity) throws OperazioniAutomaticheException {

    }

    @Override
    public void update(Istanze entity) {

    }

    @Override
    public void elabora(Integer codiceIstanza, boolean forzaElaborazionePerIstanzeChiuse, Date elaboraAttivitaDopoData) {

    }

    @Override
    public void elabora(Integer codiceIstanza, boolean forzaElaborazionePerIstanzeChiuse) {

    }

    @Override
    public void primaElaborazione(Istanze istanza) {

    }

    @Override
    public int calcolaDurataProcedimento(Istanze istanza) {

	return 0;
    }

    @Override
    public void calcolaDataValidita(Integer codiceIstanza) {

    }

    @Override
    public Date calcolaDataInizioIstanza(Istanze istanza) {

	return null;
    }

    @Override
    public Movimenti dataUltimaTrasmissione(Istanze istanza) {

	return null;
    }

    @Override
    public String checkDeleteIstanza(Istanze istanza) {

	return null;
    }

    @Override
    public boolean checkModificaIstanza(Istanze istanza) {

	return false;
    }

    @Override
    public IstanzeOnLineHelper findIstanzeOnline() {

	return null;
    }

    @Override
    public void calcolaTempisticaIstanza(Istanze entity) {

    }

    @Override
    public MovimentiHelper updateStatoIstanza(Istanze istanza, String nuovoStato) {

	return null;
    }

    @Override
    public void updateComuneIstanza(Integer codiceIstanza, String codiceComune) {

    }

    @Override
    public void updateLavoriestesa(Integer codiceIstanza, String lavoriestesa) {

    }

    @Override
    public void updateTipoProtFallita(Integer codiceIstanza, String tipoProtFallita) {

    }

    @Override
    public void updateNumeroistanza(Integer codiceIstanza, String numeroistanza) {

    }

    @Override
    public void updateMqIstanza(Integer codiceIstanza, BigDecimal metriquadrati) {

    }

    @Override
    public List<Istanze> findByIstanzePerAttivitaFilter(IstanzePerAttivitaFilter istanzePerAttivitaFilter) {

	return null;
    }

    @Override
    public int countByFilter(IstanzeFilter filter) {

	return 0;
    }

    @Override
    public int countByFilterTable(FilterTable filter) {

	return 0;
    }

    @Override
    public List<Istanze> findByFilter(IstanzeFilter filter, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public List<IstanzeListHelper> findIstanzeListHelperByFilter(IstanzeFilter filter, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public int countIstanzeListHelperByFilter(IstanzeFilter filter) {

	return 0;
    }

    @Override
    public List<Istanze> findIstanzePerInserimentoMassivo(IstanzeFilter filter) {

	return null;
    }

    @Override
    public List<Istanze> visualizzaRepliche(Istanze istanza) {

	return null;
    }

    @Override
    public Istanze isReplicata(Istanze istanza) {

	return null;
    }

    @Override
    public Istanze createTemplateFromIstanzaForReplica(Istanze istanza) {

	return null;
    }

    @Override
    public List<Integer> findTuttiCodiciIstanzaPerSoftwareAndIntervento(String psoftware, List<Integer> codiceIntervento) {

	return null;
    }

    @Override
    public void scriviProgressivo(String numeroIstanza, Integer alberoProcCodice, String codiceSoftware, boolean eseguiCommitImmediata) {

    }

    @Override
    public List<Istanze> findByAnagrafeRichiedente(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public List<Istanze> findByAnagrafeProfessionista(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public List<Istanze> findByAnagrafeTitolarelegale(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public List<Istanze> findByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public int countByAlberoproc(Integer codiceAlberoproc) {

	return 0;
    }

    @Override
    public void updateDataInizioIstanzaPerProcedura(Tipiprocedure tipiprocedure, Integer limiteIstanzeAggiornabiliPerCiclo) {

    }

    @Override
    public List<Istanze> findByProcedure(Tipiprocedure tipiprocedure, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public int countByProcedure(Tipiprocedure tipiprocedure) {

	return 0;
    }

    @Override
    public void updateDataValiditaIstanzaPerProcedura(Tipiprocedure tipiprocedure, Integer limiteIstanzeAggiornabiliPerCiclo) {

    }

    @Override
    public List<Istanze> findProfessionistaOrRichiedenteOrTitLegaleStorico(Anagrafestorico anagrafeStorico) {

	return null;
    }

    @Override
    public void updateCopiaSchedeIstanza(Istanze istanzaSorgente, Istanze istanzaDestinatario) {

    }

    @Override
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, Integer ordine) {

	return null;
    }

    @Override
    public boolean findIfIsAttivitaAttivaFromIstanze(List<Istanze> istanzes) {

	return false;
    }

    @Override
    public List<Istanze> findByAttivita(Integer codice) {

	return null;
    }

    @Override
    public List<Istanze> findByAttivitaAndIntervalloDataValidita(Integer codiceattivita, Date fromDate, Date toDate) {

	return null;
    }

    @Override
    public List<Istanze> findByAttivitaAndBeforeDataValiditaIstanza(Integer codiceAttivita, Date dataValidita, boolean includiDataValiditaNull) {

	return null;
    }

    @Override
    public List<Istanze> findByAttivitaAndDataValidita(Integer codiceattivita, Date dataValidita) {

	return null;
    }

    @Override
    public Istanze findIstanzaUltimaAttivitaAllaData(Integer codiceAttivita, Date dataValidita) {

	return null;
    }

    @Override
    public void updateCalcoloIattivitaOrdine(Istanze istanza, IAttivita attivita) {

    }

    @Override
    public int findOrdineAttivitaMaxByData(IAttivita attivita, Date datavalidita) {

	return 0;
    }

    @Override
    public void populateCambioInterventoCommand(Istanze istanza, CambioInterventoCommand cambioInterventoCommand) {

    }

    @Override
    public void updateCambioInterventoIstanza(Istanze istanza, CambioInterventoCommand cambioInterventoCommand) {

    }

    @Override
    public List<Istanze> findByTipoSoggetto(Integer codiceTiposoggetto, int firstResult, int maxResults) {

	return null;
    }

    @Override
    public List<Istanze> findByTipologiaIstanza(Integer codiceTipologia, int firstResult, int maxResults) {

	return null;
    }

    @Override
    public List<Istanze> findByTipimovimentoAvvio(String tipomovimento, int firstResult, int maxResults) {

	return null;
    }

    @Override
    public List<Istanze> findByTipiarchivioistanze(Integer codiceTipoarchivio, int firstResult, int maxResults) {

	return null;
    }

    @Override
    public List<Istanze> findByImpianti(Integer codiceImpianto, int firstResult, int maxResults) {

	return null;
    }

    @Override
    public List<Istanze> findByAree2(Integer codiceArea2, int firstResult, int maxResults) {

	return null;
    }

    @Override
    public List<IstanzeDTO> findAttivitaOrdineByAttivitaAndDatavalidita(Integer codiceAttivita, Date dataValidita) {

	return null;
    }

    @Override
    public void updateOrdineAttivita(Integer codIstanza, Integer nuovoOrdine) {

    }

    @Override
    public Istanze findIstanzaOrigineAttivita(Integer codiceAttivita) {

	return null;
    }

    @Override
    public void evict(Istanze entity) {

    }

    @Override
    public byte[] export(IstanzeFilter filter, Integer codiceEsp, String idComuneEsportazione, String emailResponsabile, boolean isInviaMail) {

	return null;
    }

    @Override
    public String exportModalitaPentaho(IstanzeFilter filter, Esportazioni esportazioni, String emailResponsabile, boolean isInviaMail) {

	return null;
    }

    @Override
    public List<IstanzeDaChiudereHelper> findIstanzedaChiudere() {

	return null;
    }

    @Override
    public List<IstanzePerInterventiHelper> countNumeroIstanzeGrupByInterventi(String codiceSoftware, Date fromDate, Date toDate, Integer startRow,
	    Integer maxRow) {

	return null;
    }

    @Override
    public List<IstanzePerProcedimentiHelper> countNumeroIstanzeGrupByProcedimenti(String codiceSoftware, Date fromDate, Date toDate,
	    Integer startRow, Integer maxRow) {

	return null;
    }

    @Override
    public int countIstanzeDaAssegnareAdIstruttoreByRespProcedimento(Responsabili responsabile) {

	return 0;
    }

    @Override
    public int countIstanzeAssegnareAdIstruttoreByRespProcedimento(Responsabili currentlyAuthenticatedUserDetails) {

	return 0;
    }

    @Override
    public void accettaOrRifiutaRuoloIstruttore(Integer codiceIstanza, Responsabili istruttore, Boolean accetta, Boolean rigetta) {

    }

    @Override
    public void sendEmailNoticheFunzionalitaAntiCorruzione(Istanze istanza, Responsabili istruttore, boolean accetta, boolean rigetta,
	    boolean assegnazione) throws FunzioneBusinessRemotaException {

    }

    @Override
    public void updateIstruttore(Integer codiceIstanza, Integer codiceIstruttore, boolean tracciaInEventi) {

    }

    @Override
    public Istanze findByUiid(String uuid) {

	return null;
    }

    @Override
    public SorteggidettaglioDTO findByIstanza(Integer codiceIstanza) {

	return null;
    }

    @Override
    public ResponsabiliAssegnazioniHelper calcolaAssegnazioneResponsabiliProc(Integer codiceIstanza) {

	return null;
    }

    @Override
    public ResponsabiliAssegnazioniHelper calcolaAssegnazioneIstruttori(Integer codiceIstanza) {

	return null;
    }

    @Override
    public Set<Integer> findDocumentiIstanzaByMetadati(Integer codiceistanza, List<CodiceDescrizioneBean> mds) {

	return null;
    }

    @Override
    public void updateContatori(boolean processaSoloIlPrimoGiornoDEllanno) {

    }

    @Override
    public boolean verificaResponsabileNellIstanze(Integer codiceResponsabile) {

	return false;
    }

    @Override
    public void updatePrendiInCarico(Integer codiceIstanza, Integer codice) {

    }

    @Override
    public int countIstanzeConStatoInWarning() {

	return 0;
    }

    @Override
    public int countByAmministrazioni(Integer codice) {

	return 0;
    }

    @Override
    public String findEmailSoggettoPratica(Integer codiceAnagrafe, Integer codiceIstanza, Integer codiceMovimento) {

	return null;
    }

    @Override
    public boolean isDataSuccessivaAllaChiusura(Integer codiceIstanza, Date data) {

	return false;
    }

    @Override
    public void updateStatoIstanze(Set<Integer> istanzes, String codicestato) {

    }

    @Override
    public List<IstanzeListHelper> findIstanzaLocalizzazioneSimile(Integer codiceStradario, String civico, String esponente, String colore,
	    Integer codiceIstanza, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public boolean findIfIsAttivitaAttivaFromDataValuditaAndAttivita(Date data, Integer codice) {

	return false;
    }

    @Override
    public AutorizzazioniAccessiHelper findAutorizzazioniAccessiHelper(String numeroAutorizzazione, Date dataAutorizzazione, String cfImpresa,
	    String pivaImpresa) throws BusinessValidationException {

	return null;
    }

    @Override
    public AutorizzazioniAccessiHelper findAutorizzazioniAccessiHelper(Integer idAutorizzazione) throws BusinessValidationException {

	return null;
    }

    @Override
    public List<Integer> findIstanzaPerTracciatoEquitalia(TracciatoEquitaliaFilter tracciatoEquitaliaFilter, boolean isInviate, Integer firstResult,
	    Integer maxResults) {

	return null;
    }

    @Override
    public String findNumeroIstanzaPadre(Integer codiceIstanza) {

	return null;
    }

    @Override
    public DatiProtocolloResponseType insertProtocolloEfascicolo(Istanze entity, TipoInserimento tipoInserimento) {

	return null;
    }

    @Override
    public List<Integer> findIstanzaProtocollazioneFallitaByTipoProtocollazione(String[] codicetipoprotocollazione) {

	return null;
    }

    @Override
    public void collegaIstanzaAdAttivita(IAttivita attivita, Istanze istanza) {

    }

    @Override
    public void eseguiFormuleDelleSchedeDinamiche(Istanze istanza) throws OperazioniAutomaticheException {

    }

    @Override
    public void insert(Istanze entity, TipoInserimento tipoInserimento) {

    }

    @Override
    public void updateRiferimentiProtocollo(AggiornaRiferimentiProtocolloIstanzaRequest request) throws AggiornamentoProtocolloException {

    }

    @Override
    public Set<String> getCfRichiedentiPrincipaliIstanza(Integer codiceIstanza) {

	return null;
    }

    @Override
    public TempisticaIstanzaHelper tempisticaDettaglio(Integer codiceIstanza) {

	return null;
    }

    @Override
    public Set<String> getCfRichiedentiPrincipaliIstanzaAut(Integer codiceIstanza) {

	return null;
    }

    @Override
    public boolean isChiusa(Integer codiceIstanza) {

	return false;
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice) {

	return null;
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorso(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice) {

	return null;
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeResponsabiliPerIstanzeInCorsoNew(Integer codiceIstanza,
	    Map<Integer, Integer> codResp, String scCodice, Integer idTestata) {

	return null;
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeIstruttoriPerIstanzeInCorsoNew(Integer codiceIstanza, Map<Integer, Integer> codResp,
	    String scCodice, Integer idTestata) {

	return null;
    }

    @Override
    public boolean existsById(Integer codiceIstanza) {

	return false;
    }

    @Override
    public List<DettaglioRigaIstanze> findIstanzeListHelperByFilterMass(IstanzeFilter filter, HelperTypeEnum type, Integer firstResult,
	    Integer maxResult) {

	return null;
    }

    @Override
    public boolean aggiornaLocalizzazionePrimariaDaCartografico(Integer codiceIstanza) {

	return false;
    }
}
