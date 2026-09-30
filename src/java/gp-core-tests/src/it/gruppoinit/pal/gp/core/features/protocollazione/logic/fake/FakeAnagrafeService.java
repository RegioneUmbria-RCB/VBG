package it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeRicercaBean;
import it.gruppoinit.pal.gp.core.dao.helper.RicercaAnagraficeCollegateEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeAvvisiHelper;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.FiltroSoggetti;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.anagrafe.model.CodiceVerificaMailAnagrafeBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoOperazioneAggiornamento;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.helper.TipoWSAnagrafeAttivoEnum;

public class FakeAnagrafeService implements AnagrafeService {

    private Anagrafe anagrafe;

    public FakeAnagrafeService(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    @Override
    public void insert(Anagrafe entity) {

    }

    @Override
    public void update(Anagrafe entity) {

    }

    @Override
    public void delete(Anagrafe entity) {

    }

    @Override
    public Anagrafe findById(PkId id) {

	return this.anagrafe;
    }

    @Override
    public Anagrafe bindDomainObject(Anagrafe entity, Class<?> idClass, String idPath) {

	return this.anagrafe;
    }

    @Override
    public PkId newIdFromSequencetable(Anagrafe entity) {

	return null;
    }

    @Override
    public List<Anagrafe> findActiveByFilter(Anagrafe anagrafe) {

	return null;
    }

    @Override
    public List<Anagrafe> findAllByFilter(Anagrafe anagrafe) {

	return null;
    }

    @Override
    public List<Anagrafe> findDisabledByFilter(Anagrafe anagrafe) {

	return null;
    }

    @Override
    public List<Anagrafe> findByFilter(AnagrafeFilter filtro) {

	return null;
    }

    @Override
    public List<Anagrafe> findAnagrafeByRegistrazioni(Anagrafe entity) {

	return null;
    }

    @Override
    public List<Anagrafe> findAll(Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public Anagrafestorico findStoricoId(Anagrafe entity, Date dataValidita) {

	return null;
    }

    @Override
    public Anagrafestorico findStoricoId(Anagrafe entity) {

	return null;
    }

    @Override
    public String calcolaCodicefiscale(String nominativo, String nome, Date datanascita, String sesso, String codicecomune) {

	return null;
    }

    @Override
    public Anagrafe findAnagrafeAggiornataByCF(Anagrafe anagrafeSigepro) {

	return null;
    }

    @Override
    public Anagrafe findAnagrafeAggiornataByPI(Anagrafe anagrafeSigepro) {

	return null;
    }

    @Override
    public List<Anagrafe> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public int countRecord(FilterTable filterTable) {

	return 0;
    }

    @Override
    public Anagrafe insertNewStoricoFromAnagrafe(Anagrafe entity, Anagrafe oldAnagrafe, Date dataInizioValidità) {

	return null;
    }

    @Override
    public FilterTable createFilterTableByEntity(Anagrafe anagrafe) {

	return null;
    }

    @Override
    public List<Anagrafe> findByDescrizione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult) {

	return null;
    }

    @Override
    public void convertPersonaFisicaToGiuridica(Anagrafe anagrafe) {

    }

    @Override
    public void convertPersonaGiuridicaToFisica(Anagrafe anagrafe) {

    }

    @Override
    public Anagrafe findDatiAnagrafeDaWs(String cfPivaRicercaWs, Anagrafe anagrafe) {

	return null;
    }

    @Override
    public Anagrafe findDatiAnagrafeDaWs(String cfPivaRicercaWs, Anagrafe anagrafe, boolean rilanciaEccezione) {

	return null;
    }

    @Override
    public List<Anagrafestorico> findStorico(Anagrafe anagrafe) {

	return null;
    }

    @Override
    public Anagrafestorico findAnagrafeStoricoById(PkId id) {

	return null;
    }

    @Override
    public List<AnagrafeRicercaBean> findRichiedentiByIstanza(String textToSearch, Integer codiceIstanza, String tipoAnagrafe) {

	return null;
    }

    @Override
    public Anagrafe findAnagrafeAggiornataByFE(Integer codiceFoRichiesta) {

	return null;
    }

    @Override
    public Anagrafe popolateAnagrafeByFoRichiesta(Integer codiceFoRichiesta) {

	return null;
    }

    @Override
    public void leggiXmlFoRichiesta(byte[] file, it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafeFrontend) {

    }

    @Override
    public void deleteAnagrafeStorico(Integer codiceAnagrafeStorico) {

    }

    @Override
    public List<Integer> findCodiciAnagrafe() {

	return null;
    }

    @Override
    public AnagrafeAvvisiHelper findAvvisiAnagrafe(Integer codiceAnagrafe) {

	return null;
    }

    @Override
    public Anagrafedocumenti insertVisuraParix(Integer codiceAnagrafe, Integer codiceIstanza) {

	return null;
    }

    @Override
    public List<Anagrafe> findByFormegiuridiche(Integer codice, int firstResult, int maxResults) {

	return null;
    }

    @Override
    public List<Anagrafe> findByTitoli(Integer codice, int firstResult, int maxResults) {

	return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void updateAbiltaOrDisabilita(Integer codiceanagrafe, Boolean stato) {

    }

    @Override
    public void updateCfAnagrafe(Integer codiceanagrafe, String cf) {

    }

    @Override
    public boolean inviaMailUtente(Mailtipo m, Integer codiceAnagrafe, boolean forzaCreazionePassword) throws FunzioneBusinessRemotaException {

	return false;
    }

    @Override
    public void updateIdentificato(Integer codiceAnagrafe, Responsabili responsabile) {

    }

    @Override
    public void updateRimuoviIdentificato(Integer codiceAnagrafe, Responsabili responsabile) {

    }

    @Override
    public List<Anagrafe> findByCf(String codiceFiscale, String tipoAnagrafe, boolean escludiDisabilitati) {

	return null;
    }

    @Override
    public List<Anagrafe> findByPI(String partitaIva, boolean escludiDisabilitati, boolean iscercaPIinCFAzienda) {

	return null;
    }

    @Override
    public String getDettaglioImpresa(String provinciaREA, String numeroREA) throws FunzioneBusinessRemotaException {

	return null;
    }

    @Override
    public String getRicercaImpreseNoncessateByCodiceFiscale(String codiceFiscale) throws FunzioneBusinessRemotaException {

	return null;
    }

    @Override
    public void evict(Anagrafe entity) {

    }

    @Override
    public TipoWSAnagrafeAttivoEnum findServizioWSAnagrafeAttivo() {

	return null;
    }

    @Override
    public String visuraParixHTML(String cfImpresa) {

	return null;
    }

    @Override
    public List<Anagrafe> findAnagraficheConAutorizzazione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult) {

	return null;
    }

    @Override
    public boolean inviaMailResetCredenziali(String oggetto, String corpo, String email, String cf, Integer codiceAnagrafe)
	    throws FunzioneBusinessRemotaException {

	return false;
    }

    @Override
    public List<Anagrafe> findAnagraficheCollegate(Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum contesto) {

	return null;
    }

    @Override
    public Set<Integer> findCodiciAnagraficheCollegate(Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum contesto) {

	return null;
    }

    @Override
    public DettaglioAnagrafeRestBean populateDettaglioAnagrafeRestBean(Anagrafe rich) {

	return null;
    }

    @Override
    public void aggiornaMailEPec(Integer codiceAnagrafe, String email, String pec) {

    }

    @Override
    public CodiceVerificaMailAnagrafeBean generaCodiceVerificaMail(Integer codiceAnagrafe, String nuovaEmail)
	    throws FunzioneBusinessRemotaException, InvalidConfigurationException {

	return null;
    }

    @Override
    public EsitoOperazioneAggiornamento updateVerificaMail(Integer codiceAnagrafe, String codiceVerifica) {

	return null;
    }

    @Override
    public Set<FiltroSoggetti> findSoggettiPersoneCollegate(Integer codice, RicercaAnagraficeCollegateEnum soloIstanzeConAutorizzazioniMercati) {

	return null;
    }
}
