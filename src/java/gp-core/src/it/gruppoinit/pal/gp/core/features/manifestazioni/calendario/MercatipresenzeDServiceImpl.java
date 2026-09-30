package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ListaAutorizzazioniPerPeriodo;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniCsi;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiElabpresenze;
import it.gruppoinit.pal.gp.core.domain.MercatiSpunte;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTPrenot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniAttivitaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniCsiRestBean;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDPagamentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AnagraferestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoSpuntistaRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioInfoRestBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.PosteggioPerAutBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.model.DettaglioPresenzaComunicazioneModel;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calcolo.ICalcoloCostoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService.ManifestazioniStatoSpuntista;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.InfoGiornataPresenzaBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.IPagamentiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.SituazioneBorsellinoPerSoglia;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.messaggi.MessaggioBorsellinoSottoSoglia;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.IBorsellinoMovimentiDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.VerticalizzazioneAbbonamentoPosteggiServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PagamentoModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PosizioneDebitoriaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.StatoPagamentoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.utils.PresenzeNonPagateBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.FiltroPagamentoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniAttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniCsiService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiDattivitaistatService;
import it.gruppoinit.pal.gp.core.service.MercatiElabpresenzeService;
import it.gruppoinit.pal.gp.core.service.MercatiSpunteService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTPrenotService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiDaEffettuareHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoGiornoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PosteggioLiberoHelper;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeDaConsolidareHelper;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeSpuntistiHelper;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeSpuntistiHelperComparator;
import it.gruppoinit.pal.gp.core.service.helper.RestBeanHelper;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class MercatipresenzeDServiceImpl extends BaseServiceImpl<MercatipresenzeD, PkId> implements MercatipresenzeDService {

    public static final Logger log = LoggerFactory.getLogger(MercatipresenzeDServiceImpl.class);
    private MercatipresenzeDDAO mercatipresenzeDDAO;
    private MercatiDService mercatiDService;
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private AutorizzazioniCsiService autorizzazioniCsiService;
    private AnagrafeService anagrafeService;
    private MercatipresenzeTService mercatipresenzeTService;
    private MercatiElabpresenzeService mercatiElabpresenzeService;
    private UserSecurityService userSecurityService;
    @Autowired
    private AutorizzazioniAttivitaService autorizzazioniAttivitaService;
    @Autowired
    private MercatiDattivitaistatService mercatiDattivitaistatService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private MercatipresenzeTPrenotService mercatipresenzeTPrenotService;
    @Autowired
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;
    @Autowired
    private MercatiSpunteService mercatiSpunteService;
    @Autowired
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    private ICalcoloCostoPosteggiService calcoloCostoPosteggiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private IAbbonamentoService abbonamentoService;
    private MailtipoService mailTipoService;
    private AmministrazioniService amministrazioniService;
    @Autowired
    private IBorsellinoMovimentiDAO borsellinoMovimentiDAO;
    @Autowired
    private IPagamentiService pagamentiService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setMercatiElabpresenzeService(MercatiElabpresenzeService mercatiElabpresenzeService) {

	this.mercatiElabpresenzeService = mercatiElabpresenzeService;
    }

    @Autowired
    public void setMercatipresenzeTService(MercatipresenzeTService mercatipresenzeTService) {

	this.mercatipresenzeTService = mercatipresenzeTService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setMercatipresenzeDDAO(MercatipresenzeDDAO mercatipresenzeDDAO) {

	this.mercatipresenzeDDAO = mercatipresenzeDDAO;
    }

    @Autowired
    public void setMercatiCfgAttivitaService(MercatiCfgAttivitaService mercatiCfgAttivitaService) {

	this.mercatiCfgAttivitaService = mercatiCfgAttivitaService;
    }

    @Autowired
    public void setCalcoloCostoPosteggiService(ICalcoloCostoPosteggiService calcoloCostoPosteggiService) {

	this.calcoloCostoPosteggiService = calcoloCostoPosteggiService;
    }

    @Autowired
    public void setMailTipoService(MailtipoService mailTipoService) {

	this.mailTipoService = mailTipoService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Override
    protected Class<MercatipresenzeD> getEntityClass() {

	return MercatipresenzeD.class;
    }

    @Override
    public void delete(MercatipresenzeD entity) {

	this._delete(entity, true);
    }

    @Override
    public List<MercatipresenzeD> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return mercatipresenzeDDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatipresenzeD findById(PkId id) {

	return mercatipresenzeDDAO.findById(id);
    }

    @Override
    public void insert(MercatipresenzeD entity) {

	this._insert(entity, true);
    }

    @Override
    public void update(MercatipresenzeD entity) {

	this._update(entity, true);
    }

    private void dataIntegration(MercatipresenzeD entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Bean MercatipresenzeD non può essere nullo");
	}
	if (entity.getNumeropresenze() == null) {
	    entity.setNumeropresenze(Integer.valueOf(0));
	}
	if (entity.getFlagPagato() == null) {
	    entity.setFlagPagato(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(MercatipresenzeD entity) {

	MercatiD posteggio = mercatiDService.bindDomainObject(entity.getPosteggio(), PkId.class, "id.codice");
	entity.setPosteggio(posteggio);
	Autorizzazioni aut = autorizzazioniService.bindDomainObject(entity.getAutorizzazioni(), PkId.class, "id.codice");
	entity.setAutorizzazioni(aut);
	Autorizzazioni autCa = autorizzazioniService.bindDomainObject(entity.getAutorizzazioneConcessionarioAssente(), PkId.class, "id.codice");
	entity.setAutorizzazioneConcessionarioAssente(autCa);
	Anagrafe concessionario = anagrafeService.bindDomainObject(entity.getConcessionario(), PkId.class, "id.codice");
	entity.setConcessionario(concessionario);
	Anagrafe occupante = anagrafeService.bindDomainObject(entity.getOccupante(), PkId.class, "id.codice");
	entity.setOccupante(occupante);
	MercatipresenzeT giorno = mercatipresenzeTService.bindDomainObject(entity.getMercatiPresenzeT(), PkId.class, "id.codice");
	entity.setMercatiPresenzeT(giorno);
	MercatiSpunte ms = mercatiSpunteService.bindDomainObject(entity.getMercatiSpunte(), PkId.class, "id.codice");
	entity.setMercatiSpunte(ms);
	MercatiD posteggioR = mercatiDService.bindDomainObject(entity.getPosteggioRinunciato(), PkId.class, "id.codice");
	entity.setPosteggioRinunciato(posteggioR);
	caricaGerenti(entity); // carico i gerenti in automatico
    }

    private void caricaGerenti(MercatipresenzeD entity) {

	// solo se la giornata è aperta
	if (!mercatipresenzeTService.isGiornataMercatoChiusa(entity.getMercatiPresenzeT().getId().getCodice())) {
	    Anagrafe gerenteSpuntista = null;
	    Anagrafe gerenteConcessionario = null;
	    if (entity.isSpuntista()) {
		if (entity.getAutorizzazioni() != null && entity.getAutorizzazioni().getId() != null
			&& entity.getAutorizzazioni().getId().getCodice() != null) {
		    AutorizzazioniCsi autCsi = autorizzazioniCsiService.findByAutorizzazione(entity.getAutorizzazioni().getId().getCodice());
		    if (autCsi != null) {
			if (autCsi.getAnagrafe() != null) {
			    gerenteSpuntista = autCsi.getAnagrafe();
			}
		    }
		}
	    }
	    if (entity.getAutorizzazioneConcessionarioAssente() != null && entity.getAutorizzazioneConcessionarioAssente().getId() != null
		    && entity.getAutorizzazioneConcessionarioAssente().getId().getCodice() != null) {
		AutorizzazioniCsi autCsi = autorizzazioniCsiService
			.findByAutorizzazione(entity.getAutorizzazioneConcessionarioAssente().getId().getCodice());
		if (autCsi != null) {
		    if (autCsi.getAnagrafe() != null) {
			gerenteConcessionario = autCsi.getAnagrafe();
		    }
		}
	    }
	    entity.setGerenteSpuntista(gerenteSpuntista);
	    entity.setGerenteConcessionario(gerenteConcessionario);
	}
    }

    @Override
    public MercatipresenzeD findByMercatiPresenzeTAndPosteggio(Integer idGiornataMercato, Integer idPosteggio) {

	// §§§BEGIN§§§
	return mercatipresenzeDDAO.findByMercatiPresenzeTAndPosteggio(idGiornataMercato, idPosteggio);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    //    @Override
    //    public MercatipresenzeD findByMercatiPresenzeTAndAutorizzazione(MercatipresenzeT giornoMercato, Integer idAutorizzazione) {
    //
    //	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
    //	FilterRestriction fr = new FilterRestriction();
    //	fr.addFilterField(FilterUtils.equals("id.codice", giornoMercato.getId().getCodice(), "mercatiPresenzeT", Integer.class));
    //	fr.addFilterField(FilterUtils.equals("id.codice", idAutorizzazione, "autorizzazioni", Integer.class));
    //	List<MercatipresenzeD> list = mercatipresenzeDDAO.findByFilterTable(ft);
    //	if (list.isEmpty()) {
    //	    return null;
    //	}
    //	return list.get(0);
    //    }
    @Override
    public MercatipresenzeD findByMercatiPresenzeTAndAutorizzazione(Integer giornoMercato, Integer idAutorizzazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", giornoMercato, "mercatiPresenzeT", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", idAutorizzazione, "autorizzazioni", Integer.class));
	ft.addRestriction(fr);
	List<MercatipresenzeD> list = mercatipresenzeDDAO.findByFilterTable(ft);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public List<MercatipresenzeDDTO> findListaPosteggi(MercatipresenzeT giorno) {

	// §§§BEGIN§§§
	return mercatipresenzeDDAO.findListaPosteggi(giorno);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatipresenzeD findByMercatiPosteggio(MercatiD posteggio) {

	// §§§BEGIN§§§
	return mercatipresenzeDDAO.findByMercatiPosteggio(posteggio);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeD> findByAnagrafeOccupante(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeOccupante: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("concessionarioId", codiceAnagrafe, Integer.class));
	filterTable.addRestriction(fr);
	return mercatipresenzeDDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeD> findByAnagrafeConcessionario(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeConcessionario: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("concessionarioId", codiceAnagrafe, Integer.class));
	filterTable.addRestriction(fr);
	return mercatipresenzeDDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatipresenzeD findSpuntistaNoPosteggio(MercatipresenzeT giorno, Integer idAutorizzazione) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", giorno.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.equals("autorizzazioniId", idAutorizzazione, Integer.class));
	fr.addFilterField(FilterUtils.isNull("posteggioId"));
	filterTable.addRestriction(fr);
	List<MercatipresenzeD> list = mercatipresenzeDDAO.findByFilterTable(filterTable);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeD> findSpuntisti(MercatipresenzeT giorno) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", giorno.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.equals("spuntista", "true", Boolean.class));
	fr.addFilterField(FilterUtils.isNotNull("occupante"));
	filterTable.addRestriction(fr);
	return mercatipresenzeDDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public boolean isCloseMarketDayAllowed(MercatipresenzeT mercatipresenzeT) {

	boolean success = false;
	// Set<MercatipresenzeD> presenze = mercatipresenzeT.getListaPresenze();
	// controllo se il giorno di mercato è stato chiuso
	if (!mercatipresenzeT.getFlagPresenze()) {
	    // controlla se almeno un posteggio è stato assegnato
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", mercatipresenzeT.getId().getCodice(), Integer.class));
	    fr.addFilterField(FilterUtils.isNotNull("occupante"));
	    ft.addRestriction(fr);
	    return mercatipresenzeDDAO.existsRecords(ft);
	}
	return success;
    }

    @Override
    public List<PresenzeSpuntistiHelper> findListaPresentiSenzaPosteggio(MercatipresenzeT giorno) {

	List<PresenzeSpuntistiHelper> list = new ArrayList<PresenzeSpuntistiHelper>();
	List<MercatipresenzeD> lpsp = mercatipresenzeDDAO.findListaPresentiSenzaPosteggio(giorno);
	for (MercatipresenzeD mercatipresenzeD : lpsp) {
	    MercatiPresenzeDTO mps = mercatipresenzeStoricoService.findSommaDellePresenze(mercatipresenzeD.getAutorizzazioni(), giorno.getMercato(),
		    giorno.getMercatoUso(), null, null, null);
	    PresenzeSpuntistiHelper ps = new PresenzeSpuntistiHelper();
	    ps.setMercatipresenzeDid(mercatipresenzeD.getId().getCodice());
	    MercatipresenzeD p = this.findById(new PkId(ps.getMercatipresenzeDid()));
	    if (p.getMercatiSpunte() != null) {
		ps.setFaseSpunta(p.getMercatiSpunte().getDescrizione());
	    }
	    ps.setMercatipresenzeD(mercatipresenzeD);
	    ps.setNumeropresenze(mps.getPresenze());
	    ps.setAut(mercatipresenzeD.getAutorizzazioni());
	    ps.setDataAutorizzazione(mercatipresenzeD.getAutorizzazioni().getAutorizdata());
	    if (p.getAutorizzazioni().getOccupante() != null) {
		ps.setDataCciaa(mercatipresenzeD.getAutorizzazioni().getOccupante().getDataregditte());
	    } else {
		ps.setDataCciaa(mercatipresenzeD.getAutorizzazioni().getAnagrafe().getDataregditte());
	    }
	    ps.setDataAnzianita(mercatipresenzeD.getAutorizzazioni().getDataAnzianita());
	    AutorizzazioniCsi autcsi = autorizzazioniCsiService.findByAutorizzazione(mercatipresenzeD.getAutorizzazioni().getId().getCodice());
	    if (autcsi != null) {
		ps.setAutCsi(autcsi);
		if (autcsi.getAnagrafe() != null) {
		    ps.setDataCciaa(autcsi.getAnagrafe().getDataregditte());
		}
	    }
	    list.add(ps);
	}
	Collections.sort(list, new PresenzeSpuntistiHelperComparator());
	return list;
    }

    @Override
    public MercatiPresenzeDTO findSommaDellePresenzeDaiCalendari(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno, MercatipresenzeT giorno, boolean sommaAssenzeGiustificate) {

	return mercatipresenzeDDAO.findSommaDellePresenzeDaiCalendari(autorizzazione, mercato, uso, posteggio, catMerc, anno, giorno,
		sommaAssenzeGiustificate);
    }

    @Override
    public List<MercatipresenzeD> findByMercatiPresenzeT(Integer codiceMercatopresenzaT) {

	// §§§BEGIN§§§
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", codiceMercatopresenzaT, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("codiceposteggio", "posteggio"));
	return mercatipresenzeDDAO.findByFilterTable(ft);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public boolean updateConsolidaPresenzePerAnno(Integer codiceMercato, Integer anno) {

	List<PresenzeDaConsolidareHelper> dtos = mercatipresenzeDDAO.findPresenzeDaConsolidarePerAnno(codiceMercato, anno);
	mercatipresenzeDDAO.updateAggiornaAZeroTutteLePresenze(codiceMercato, anno);
	for (PresenzeDaConsolidareHelper helper : dtos) {
	    MercatipresenzeD presenza = mercatipresenzeDDAO.findUltimaPresenzaPerMercatoAndAutorizzazione(codiceMercato, anno,
		    helper.getCodiceautorizzazione());
	    if (presenza != null) {
		presenza.setNumeropresenze(1);
		presenza.setProprietario(0);
		if (helper.getNumeropresenzeproprietario() != null) {
		    if (helper.getNumeropresenzeproprietario().intValue() > 0) {
			presenza.setProprietario(1);
		    }
		}
		this._update(presenza, false);
		mercatipresenzeDDAO.flush();
	    }
	}
	// AGGIORNA I RECORD DI MERCATI_ELAB_PRESENZE
	mercatiElabpresenzeService.updateConsolidaAnnoMercato(codiceMercato, anno);
	return true;
    }

    private void _update(MercatipresenzeD entity, boolean eliminaElaborazioneAnnioConsolidati) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    if (eliminaElaborazioneAnnioConsolidati) {
		eliminaAnniElaborazione(entity);
	    }
	    mercatipresenzeDDAO.update(entity);
	}
	// §§§END§§§
    }

    private void _insert(MercatipresenzeD entity, boolean eliminaElaborazioneAnnioConsolidati) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatipresenzeDDAO.insert(entity);
	    if (eliminaElaborazioneAnnioConsolidati) {
		eliminaAnniElaborazione(entity);
	    }
	}
	// §§§END§§§
    }

    private void eliminaAnniElaborazione(MercatipresenzeD entity) {

	MercatipresenzeT mt = entity.getMercatiPresenzeT();
	List<MercatiElabpresenze> melps = mercatiElabpresenzeService.findByMercatiAndAnno(mt.getMercato().getId().getCodice(), mt.getAnno());
	for (MercatiElabpresenze melp : melps) {
	    Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    if (r != null) {
		String messaggio = "MERCATI_CONSOLIDA_PRESENZE: Il responsabile " +
			r.toString() +
			" ha aggiornato le presenze di un mercato che era stato consolidato con codice: " +
			mt.getMercato().getId().getCodice() +
			", anno: " +
			mt.getAnno();
		LoggerCancellazioni.log(messaggio);
	    }
	    mercatiElabpresenzeService.delete(melp);
	}
    }

    private void _delete(MercatipresenzeD entity, boolean eliminaElaborazioneAnnioConsolidati) {

	// §§§BEGIN§§§
	if (eliminaElaborazioneAnnioConsolidati) {
	    eliminaAnniElaborazione(entity);
	}
	mercatipresenzeDDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<MercatipresenzeDDTO> findByMercatipresenzaT(Integer codiceMercatopresenzaT) {

	return mercatipresenzeDDAO.findByMercatipresenzaT(codiceMercatopresenzaT);
    }

    @Override
    public List<String> avvisiSpuntisti(Integer idpresenzad) {

	List<String> avs = new ArrayList<String>();
	MercatipresenzeD mpd = this.findById(new PkId(idpresenzad));
	if (mpd != null) {
	    if (mpd.getPosteggio() != null && mpd.getAutorizzazioni() != null) {
		// recupero le merceologie del posteggio
		List<MercatiDattivitaistat> attivitaPost = mercatiDattivitaistatService.findAttivitaPosteggio(mpd.getPosteggio().getId().getCodice());
		if (!attivitaPost.isEmpty()) {
		    Attivita attivitaPresenza = mpd.getAttivita();
		    // recupero le merceologie dell'autorizzazione dello spuntista
		    if (attivitaPresenza != null && attivitaPresenza.getId() != null && attivitaPresenza.getId().getCodiceistat() != null) {
			Set<String> consentite = new HashSet<String>();
			Set<String> nonconsentite = new HashSet<String>();
			Set<String> aut = new HashSet<String>();
			//for (AutorizzazioniAttivita a : attivitaAut) {
			// aut.add(a.getAttivita().getId().getCodiceistat());
			//}
			aut.add(attivitaPresenza.getId().getCodiceistat());
			for (MercatiDattivitaistat mdas : attivitaPost) {
			    if (mdas.isFlagConsentito()) {
				consentite.add(mdas.getId().getFkcodiceattivitaistat());
			    } else {
				nonconsentite.add(mdas.getId().getFkcodiceattivitaistat());
			    }
			}
			for (String a : aut) {
			    boolean processata = false;
			    if (nonconsentite.contains(a)) {
				processata = true;
				Attivita att = attivitaService.findById(new AttivitaId(a));
				avs.add("La merceologia  " + att.getDescrizioneEstesa() + " non e' consentita");
			    }
			    if (!processata) {
				if (!consentite.contains(a)) {
				    Attivita att = attivitaService.findById(new AttivitaId(a));
				    avs.add("La merceologia  " + att.getDescrizioneEstesa() + " non e' consentita");
				}
			    }
			}
		    }
		    // faccio il match delle informazioni
		}
	    }
	}
	return avs;
    }

    @Override
    public List<PosteggioLiberoHelper> findListaPosteggiLiberi(Integer codiceGiornata) {

	List<PosteggioLiberoHelper> ret = new ArrayList<PosteggioLiberoHelper>();
	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(codiceGiornata));
	List<MercatipresenzeDDTO> findListaPosteggi = mercatipresenzeDDAO.findListaPosteggi(giorno);
	for (MercatipresenzeDDTO mpd : findListaPosteggi) {
	    if (EntityUtils.isNestedPropertyBlank(mpd, "occupante.id.codice")) {
		PosteggioLiberoHelper plh = new PosteggioLiberoHelper();
		plh.setIdPosteggio(mpd.getPosteggio().getId().getCodice());
		plh.setCodiceposteggio(mpd.getPosteggio().getCodiceposteggio());
		plh.setLunghezza(mpd.getPosteggio().getLunghezza());
		plh.setLarghezza(mpd.getPosteggio().getLarghezza());
		plh.setSuperficie(mpd.getPosteggio().getSuperficie());
		plh.setTipospazio(mpd.getPosteggio().getTipoSpazio());
		List<CodiceDescrizioneBean> merceologieConsentite = new ArrayList<CodiceDescrizioneBean>();
		List<CodiceDescrizioneBean> merceologieVietate = new ArrayList<CodiceDescrizioneBean>();
		List<MercatiDattivitaistat> attivitaPost = mercatiDattivitaistatService.findAttivitaPosteggio(mpd.getPosteggio().getId().getCodice());
		for (MercatiDattivitaistat mai : attivitaPost) {
		    CodiceDescrizioneBean e = new CodiceDescrizioneBean();
		    e.setCodice(mai.getId().getFkcodiceattivitaistat());
		    e.setDescrizione(mai.getAttivita().getIstat());
		    if (mai.isFlagConsentito()) {
			merceologieConsentite.add(e);
		    } else {
			merceologieVietate.add(e);
		    }
		}
		plh.setMerceologieConsentite(merceologieConsentite);
		plh.setMerceologieVietate(merceologieVietate);
		List<CodiceDescrizioneBean> prns = new ArrayList<CodiceDescrizioneBean>();
		List<MercatipresenzeTPrenot> prenots = mercatipresenzeTPrenotService.findByMercatipresenzeTAndPosteggio(codiceGiornata,
			mpd.getPosteggio().getId().getCodice());
		for (MercatipresenzeTPrenot p : prenots) {
		    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
		    cdb.setCodice(p.getAnagrafe().getCodicefiscale());
		    if (p.getDataInserimento() != null) {
			cdb.setDescrizione(Utilities.formatDate(p.getDataInserimento(), true));
		    }
		    prns.add(cdb);
		}
		plh.setPrenotazioni(prns);
		ret.add(plh);
	    }
	}
	return ret;
    }

    @Deprecated
    @Override
    public PagamentiMercatiHelper findPagamentiByCf(String cfOccupante) {

	PagamentiMercatiHelper ret = new PagamentiMercatiHelper();
	List<MercatipresenzeDPagamentiDTO> ps = mercatipresenzeDDAO.findPagamentiByCf(cfOccupante);
	for (MercatipresenzeDPagamentiDTO mp : ps) {
	    PagamentiMercatiDaEffettuareHelper pe = new PagamentiMercatiDaEffettuareHelper();
	    CodiceDescrizioneBean mercato = new CodiceDescrizioneBean();
	    mercato.setCodice(mp.getMercati().getId().getCodice().toString());
	    mercato.setDescrizione(mp.getMercati().getDescrizione());
	    CodiceDescrizioneBean posteggio = new CodiceDescrizioneBean();
	    posteggio.setCodice(mp.getPosteggio().getId().getCodice().toString());
	    posteggio.setDescrizione(mp.getPosteggio().getCodiceposteggio());
	    CodiceDescrizioneBean giorno = new CodiceDescrizioneBean();
	    giorno.setCodice(mp.getGiorno().getId().getCodice().toString());
	    giorno.setDescrizione(mp.getGiorno().getDescrizione());
	    pe.setMercato(mercato);
	    pe.setPosteggio(posteggio);
	    pe.setGiorno(giorno);
	    pe.setCodiceMercatoPresenza(mp.getId().getCodice());
	    pe.setImporto(mp.getImporto().doubleValue());
	    pe.setDataMercato(mp.getDataRegistrazione());
	    pe.setModalitaPagamento(mp.getModalitaPagamento());
	    pe.setRiferimentiPagamento(mp.getRiferimentiPagamento());
	    if (mp.getFlagPagato() != null && mp.getFlagPagato().booleanValue()) {
		ret.getPagamentiEffettuati().add(pe);
	    } else {
		ret.getPagamentiDaEffettuare().add(pe);
	    }
	}
	return ret;
    }

    @Override
    public void updateRimuoviRinunciaPosteggio(Integer presenzaCodice) {

	MercatipresenzeD mpd = this.findById(new PkId(presenzaCodice));
	boolean assegnaPresenza = true;
	if (BooleanUtils.toBoolean(mpd.getMercatiPresenzeT().getFlagChiusuraAppello())) {
	    assegnaPresenza = false;
	}
	mpd.setFlagRinunciaPresenza(Boolean.FALSE);
	mpd.setPosteggioRinunciato(null);
	if (assegnaPresenza) {
	    mpd.setNumeropresenze(1);
	}
	if (Boolean.FALSE.equals(mpd.getMercatiPresenzeT().getFlagConteggiaPresAss())) {
	    mpd.setNumeropresenze(0);
	}
	this.update(mpd);
    }

    public void updateSegnaRinunciaPosteggio(Integer presenzaCodice, Integer idposteggio) {

	MercatipresenzeD mpd = this.findById(new PkId(presenzaCodice));
	if (idposteggio != null) {
	    MercatiD postRinunciato = mercatiDService.findById(new PkId(idposteggio));
	    mpd.setPosteggioRinunciato(postRinunciato);
	}
	mpd.setNumeropresenze(0);
	mpd.setFlagRinunciaPresenza(Boolean.TRUE);
	this.update(mpd);
    }

    @Override
    public MercatipresenzeD isAssegnatoPosteggioASpuntistaAndAutNellaGiornata(Integer codiceMercato, Integer usoMercato, Integer autId,
	    Date giornoMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceMercato, "mercatiPresenzeT.mercato", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", usoMercato, "mercatiPresenzeT.mercatoUso", Integer.class));
	fr.addFilterField(FilterUtils.equals("dataRegistrazione", giornoMercato, "mercatiPresenzeT", Date.class));
	fr.addFilterField(FilterUtils.equals("id.codice", autId, "autorizzazioni", Integer.class));
	ft.addRestriction(fr);
	List<MercatipresenzeD> l = mercatipresenzeDDAO.findByFilterTable(ft, 0, 1);
	if (!l.isEmpty()) {
	    return l.get(0);
	}
	return null;
    }

    @Override
    public MercatipresenzeD findUltimaPresenza(Integer codiceMercato, Integer codiceUso, Integer codiceAutorizzazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceMercato, "mercatiPresenzeT.mercato", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceUso, "mercatiPresenzeT.mercatoUso", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAutorizzazione, "autorizzazioni", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataRegistrazione", "mercatiPresenzeT"));
	List<MercatipresenzeD> list = mercatipresenzeDDAO.findByFilterTable(ft, 0, 1);
	if (!list.isEmpty()) {
	    return list.get(0);
	} else {
	    return null;
	}
    }

    @Override
    public void updateAzzeraPresenzeByAutorizzazioneAndMercato(Integer codiceAutorizzazione, Integer codiceMercato, Integer codiceuso) {

	mercatipresenzeDDAO.updateAzzeraPresenzeByAutorizzazioneAndMercato(codiceAutorizzazione, codiceMercato, codiceuso);
    }

    @Override
    public List<GiornataMercatoSpuntistaRestBean> findSpuntistiGiornataMercatoRest(Integer idGiornata) {

	return _findSpuntistiGiornataMercatoRest(idGiornata, null);
    }

    @Override
    public GiornataMercatoSpuntistaRestBean findSpuntistiGiornataMercatoRest(Integer idGiornata, Integer idAut) {

	List<GiornataMercatoSpuntistaRestBean> l = _findSpuntistiGiornataMercatoRest(idGiornata, idAut);
	if (l != null && !l.isEmpty()) {
	    return l.get(0);
	}
	return null;
    }

    //@Override
    private List<GiornataMercatoSpuntistaRestBean> _findSpuntistiGiornataMercatoRest(Integer idGiornata, Integer idAut) {

	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	Calendar annoScorso = getCalendarAnnoPrecedente(giorno.getDataRegistrazione());
	Date inizioMese = new Date(annoScorso.getTimeInMillis());
	annoScorso.set(Calendar.DATE, annoScorso.getActualMaximum(Calendar.DAY_OF_MONTH));
	Date fineMese = new Date(annoScorso.getTimeInMillis());
	Integer codiceMercato = giorno.getMercato().getId().getCodice();
	Integer codiceUso = giorno.getMercatoUso().getId().getCodice();
	String codiceComune = giorno.getMercato().getComune().getCodicecomune();
	ListaAutorizzazioniPerPeriodo dati = new ListaAutorizzazioniPerPeriodo();
	if (idAut == null) {
	    log.debug("findSpuntistiGiornataMercatoRest# Id autorizzazione non passato");
	    dati = mercatipresenzeDDAO.findPresenzeSpuntistiRestHelper(idGiornata, codiceMercato, codiceUso, inizioMese, fineMese);
	    if (!giorno.getFlagPopolaConcessionari().booleanValue()) {
		dati.mergeRisultati(
			mercatipresenzeDDAO.findPresenzeConcessionariRestHelper(idGiornata, codiceMercato, codiceUso, inizioMese, fineMese));
	    }
	} else {
	    log.debug("findSpuntistiGiornataMercatoRest# Id autorizzazione =  {}", idAut);
	    dati.getAuts().add(idAut);
	    dati.getPresentiOggi().add(idAut);
	}
	List<AutorizzazioniAttivitaDTO> atts = autorizzazioniAttivitaService.findByAutorizzazioni(dati.getAuts());
	Map<Integer, List<CodiceDescrizioneBean>> mcm = new HashMap<Integer, List<CodiceDescrizioneBean>>();
	for (AutorizzazioniAttivitaDTO autorizzazioniAttivita : atts) {
	    if (mcm.get(autorizzazioniAttivita.getIdautorizzazione()) == null) {
		List<CodiceDescrizioneBean> attivita = new ArrayList<CodiceDescrizioneBean>();
		CodiceDescrizioneBean a = new CodiceDescrizioneBean();
		a.setCodice(autorizzazioniAttivita.getCodiceattivita());
		a.setDescrizione(autorizzazioniAttivita.getDescrizioneattivita());
		attivita.add(a);
		mcm.put(autorizzazioniAttivita.getIdautorizzazione(), attivita);
	    } else {
		List<CodiceDescrizioneBean> att = mcm.get(autorizzazioniAttivita.getIdautorizzazione());
		CodiceDescrizioneBean a = new CodiceDescrizioneBean();
		a.setCodice(autorizzazioniAttivita.getCodiceattivita());
		a.setDescrizione(autorizzazioniAttivita.getDescrizioneattivita());
		att.add(a);
		mcm.put(autorizzazioniAttivita.getIdautorizzazione(), att);
	    }
	}
	List<GiornataMercatoSpuntistaRestBean> result = new ArrayList<GiornataMercatoSpuntistaRestBean>();
	// estrarre anche il valore pagatato da mercati presenze_d
	Map<Integer, IdentificativoDescrizioneBean> mappaAutCollegate = autorizzazioniConcessioniService.findAutorizzazioniCollegate(dati.getAuts());
	List<AutorizzazioniRestHelper> hlps = autorizzazioniService.findRestHelper(dati.getAuts(), idGiornata, codiceMercato, codiceUso);
	int pos = 0;
	for (AutorizzazioniRestHelper arh : hlps) {
	    popolaMercatoSpuntistaRestBean(dati, mcm, result, arh, mappaAutCollegate, (9999999 - pos++));
	}
	popolaAvvisoSogliaBorsellino(codiceComune, result, dati.getAuts());
	return result;
    }

    private void popolaMercatoSpuntistaRestBean(ListaAutorizzazioniPerPeriodo dati, Map<Integer, List<CodiceDescrizioneBean>> mcm,
	    List<GiornataMercatoSpuntistaRestBean> result, AutorizzazioniRestHelper arh,
	    Map<Integer, IdentificativoDescrizioneBean> mappaAutCollegate, int progressivoPosizione) {

	GiornataMercatoSpuntistaRestBean ah = new GiornataMercatoSpuntistaRestBean();
	Integer idAutorizzazione = arh.getIdAutorizzazione();
	ah.setPosteggioOccupato(arh.getPosteggioOccupato());
	ah.setId(idAutorizzazione);
	ah.setPresenteAnnoScorso(dati.getPresentiAnnoScorso().contains(idAutorizzazione));
	ah.setPresenteUltimoMercato(dati.getPresentiUltimoMercato().contains(idAutorizzazione));
	// Avvisi
	StringBuilder statoWarning = new StringBuilder("");
	if (arh.getIdautorizzazionicsi() != null && StringUtils.isNotBlank(arh.getStatowarning())) {
	    statoWarning = statoWarning.append(arh.getStatowarning());
	    if (arh.getDataSospDa() != null) {
		statoWarning = statoWarning.append(" Da ").append(Utilities.formatDate(arh.getDataSospDa(), false));
	    }
	    if (arh.getDataSospA() != null) {
		statoWarning = statoWarning.append(" A ").append(Utilities.formatDate(arh.getDataSospA(), false));
	    }
	}
	// Add avvisi
	if (StringUtils.isNotBlank(statoWarning.toString())) {
	    ah.getAvvisi().add(statoWarning.toString());
	}
	//
	//Autorizzazione csi
	AutorizzazioniCsiRestBean autCsi = null;
	//
	if (arh.getIdautorizzazionicsi() != null) {
	    autCsi = new AutorizzazioniCsiRestBean();
	    RestBeanHelper.populateAutorizzazioneCSIRestBean(autCsi, arh);
	} else {
	    autCsi = new AutorizzazioniCsiRestBean();
	    autCsi.setValidaSpunta(Boolean.TRUE);
	}
	//
	ah.setAutorizzazioniCsi(autCsi);
	if (!BooleanUtils.toBoolean(autCsi.getValidaSpunta())) {
	    String nonValidoPerSpunta = getMessageFromBundle("label.non_valido_spunta", null);
	    ah.getAvvisi().add(nonValidoPerSpunta);
	}
	if (arh.getNumeropresenzeoggi() != null && arh.getNumeropresenzeoggi().equals(0)) {
	    ah.getAvvisi().add(getMessageFromBundle("label.presenza_non_maturata", null));
	}
	//. AUTORIZZAZIONE ORIGINALE
	AutorizzazioneRestBean autorizzazioneOriginaria = new AutorizzazioneRestBean();
	autorizzazioneOriginaria.setNumero(arh.getAutorignumero());
	if (arh.getAutorigdata() != null) {
	    autorizzazioneOriginaria.setData(Utilities.formatDate(arh.getAutorigdata(), false));
	}
	autorizzazioneOriginaria.setRilasciataDa(arh.getAutorigcomune());
	ah.setAutorizzazioneOriginaria(autorizzazioneOriginaria);
	ManifestazioniStatoSpuntista statoEnum = ManifestazioniStatoSpuntista.NonPresente;
	if (dati.getPresentiOggi().contains(idAutorizzazione)) {
	    statoEnum = ManifestazioniStatoSpuntista.Presente;
	}
	if (arh.getPresenzaRinunciata() != null && arh.getPresenzaRinunciata().booleanValue()) {
	    statoEnum = ManifestazioniStatoSpuntista.RinunciaAPosteggio;
	    if (StringUtils.isNotBlank(arh.getPosteggiorinunciato())) {
		ah.setPosteggioRinunciato(arh.getPosteggiorinunciato());
	    }
	}
	ah.setStato(statoEnum.name());
	AnagraferestBean titolare = new AnagraferestBean();
	RestBeanHelper.populateAnagraferestBean(titolare, arh.getCodiceTitolare(), arh.getTitNominativo(), arh.getTitNome(),
		arh.getTitCodicefiscale(), arh.getTitPartitaiva(), arh.getTitIndirizzo(), arh.getTitCap(), arh.getTitCitta(), arh.getTitProvincia(),
		arh.getTitNumiscrrea(), arh.getTitDataiscrrea(), arh.getTitTelefono(), arh.getTitDataInizioAttivita(), arh.getTitEmail(),
		arh.getTitDataregditte());
	ah.setTitolare(titolare);
	// .POPOLO ALTRI DATI (ANAGRAFE LEGATA ALL'AUTORIZZAZIONE)
	AnagraferestBean altriDati = new AnagraferestBean();
	if (arh.getCodiceOccupante() != null) { // sualtri dati ci va l'occupante / se non presente il titolare
	    log.debug("_findSpuntistiGiornataMercatoRest# populate altriDati. Oggetto di tipo AnagraferestBean");
	    RestBeanHelper.populateAnagraferestBean(altriDati, arh.getCodiceOccupante(), arh.getNominativo(), arh.getNome(), arh.getCodicefiscale(),
		    arh.getPartitaiva(), arh.getIndirizzo(), arh.getCap(), arh.getCitta(), arh.getProvincia(), arh.getNumiscrrea(),
		    arh.getDataiscrrea(), arh.getTelefono(), arh.getDataInizioAttivita(), arh.getEmail(), arh.getDataregditte());
	} else {
	    altriDati = titolare;
	}
	ah.setAltriDati(altriDati);
	// .POPOLO AUTORIZZAZIONE
	AutorizzazioneRestBean autorizzazione = new AutorizzazioneRestBean();
	if (arh.getAutorizdata() != null) {
	    autorizzazione.setData(Utilities.formatDate(arh.getAutorizdata(), false));
	}
	if (arh.getAutDataAnzianita() != null) {
	    autorizzazione.setDataAnzianita(Utilities.formatDate(arh.getAutDataAnzianita(), false));
	}
	if (arh.getDataCessazione() != null) {
	    autorizzazione.setDataChiusura(Utilities.formatDate(arh.getDataCessazione(), false));
	}
	autorizzazione.setId(idAutorizzazione);
	autorizzazione.setNumero(arh.getAutoriznumero());
	IdentificativoDescrizioneBean identificativoDescrizioneBean = mappaAutCollegate.get(idAutorizzazione);
	if (identificativoDescrizioneBean != null) {
	    autorizzazione.setNumero(arh.getAutoriznumero() + " (Aut. coll. " + identificativoDescrizioneBean.getDescrizione() + ")");
	}
	autorizzazione.setRilasciataDa(arh.getAutorizcomune());
	autorizzazione.setAnnotazioniOperatore(arh.getNote());
	autorizzazione.setAnnotazioniSistema(arh.getNoteSistema());
	// RestBeanHelper.populateAutorizzazioneRestBean(autorizzazioneRestBean, AutorizzazioniRestHelper);//
	ah.setAutorizzazione(autorizzazione);
	// POPOLO AUT PRECEDENTE
	if (StringUtils.isNotBlank(arh.getAutPrecedenteNumero())) {
	    AutorizzazioneRestBean ap = new AutorizzazioneRestBean();
	    ap.setNumero(arh.getAutPrecedenteNumero());
	    if (arh.getAutPrecedenteData() != null) {
		ap.setData(Utilities.formatDate(arh.getAutPrecedenteData(), false));
	    }
	    ap.setRilasciataDa(arh.getAutPrecComune());
	    ah.setAutorizzazionePrecedente(ap);
	}
	if (StringUtils.isNotBlank(arh.getCodicefiltracategoria())) {
	    CodiceDescrizioneBean filtraCategoria = new CodiceDescrizioneBean();
	    filtraCategoria.setCodice(arh.getCodicefiltracategoria());
	    filtraCategoria.setDescrizione(arh.getDescrizionefiltracategoria());
	    ah.setFiltraCategoria(filtraCategoria);
	}
	ah.setCategorieMerceologiche(mcm.get(idAutorizzazione));
	if (arh.getNumeropresenze() != null) {
	    ah.setTotalePresenze(arh.getNumeropresenze());
	} else {
	    ah.setTotalePresenze(0);
	}
	ah.setTotalePresenzePerOrdinamento(calcolaPosizione(ah.getTotalePresenze(), progressivoPosizione));
	String descrizione = arh.getNominativo();
	if (StringUtils.isNotBlank(arh.getNome())) {
	    descrizione += " " + arh.getNome();
	}
	if (arh.getGerentecodice() != null) { // il titolare dell'autorizzazione Ticket#2023100210000219 — Merc@TO NIVOLA TEST : regressione su Web App Vigili - errata visualizzazione proprietario / gerente
	    descrizione = titolare.getRagionesociale();
	}
	ah.setDescrizione(descrizione);
	AnagraferestBean coadiuvante = new AnagraferestBean();
	// .POPOLO IL COIADIUVANTE
	log.debug("_findSpuntistiGiornataMercatoRest# populate coadiuvante. Oggetto di tipo AnagraferestBean");
	RestBeanHelper.populateAnagraferestBean(coadiuvante, arh.getCoadiuvantecodice(), arh.getCoadiuvanteNominativo(), arh.getCoadiuvanteNome(),
		arh.getCoadiuvanteCodicefiscale(), arh.getCoadiuvantePartitaiva(), null, null, null, null, null, null, null, null,
		arh.getCoadiuvanteEmail(), null);
	ah.setCoadiuvante(coadiuvante);
	// TIPOLOGIA BATTITORI. AUTORIZZAZIONI_CSI.TIPOLOGIA BATTITORI
	ah.setTipologiaBattitore(arh.getTipologiaBattitori());
	result.add(ah);
    }

    private static BigDecimal calcolaPosizione(int totalePresenze, int progressivoPosizione) {

	String valore = totalePresenze + "." + progressivoPosizione;
	return new BigDecimal(valore);
    }

    private void popolaAvvisoSogliaBorsellino(String codiceComune, List<GiornataMercatoSpuntistaRestBean> listaSpuntisti, Set<Integer> auts) {

	Map<Integer, SituazioneBorsellinoPerSoglia> situazioneBorsellinoPerSoglia = abbonamentoService.situazioneBorsellinoPerSoglia(auts,
		codiceComune);
	for (GiornataMercatoSpuntistaRestBean spuntista : listaSpuntisti) {
	    SituazioneBorsellinoPerSoglia sb = situazioneBorsellinoPerSoglia.get(spuntista.getId());
	    if (sb != null && sb.isSottosoglia()) {
		spuntista.getAvvisi()
			.add(new MessaggioBorsellinoSottoSoglia(spuntista.getAutorizzazione(), new VerticalizzazioneAbbonamentoPosteggiServiceImpl(
				this.verticalizzazioniService, this.contiService, this.mailTipoService, this.amministrazioniService, codiceComune))
					.getTestoMessaggio());
	    }
	}
    }

    private Calendar getCalendarAnnoPrecedente(Date oggi) {

	Calendar annoScorso = Calendar.getInstance();
	annoScorso.setTime(oggi);
	annoScorso.set(Calendar.YEAR, (annoScorso.get(Calendar.YEAR) - 1));
	annoScorso.set(Calendar.DATE, 1);
	return annoScorso;
    }

    @Override
    public List<String> verificaAutorizzazionePresenteSuAltriMercati(Integer idGiornata, Integer idAutorizzazione) {

	List<String> errori = new ArrayList<String>();
	// 1° controllo: verifico che non si sia già presentato con l'autorizzazione in altro mercato lo stesso giorno
	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	if (!giorno.getFlagPopolaConcessionari().booleanValue()) {
	    // se la giornata non si svolge gestendo i concessionari (es giornata di domenica) e comunque 
	    // se MERCATIPRESENZE_T.FLAG_POPOLA_CONCESSIONARI = 0
	    return errori;
	}
	Integer codiceMercato = giorno.getMercato().getId().getCodice();
	Integer codiceUso = giorno.getMercatoUso().getId().getCodice();
	//	SELECT
	//	    mercatipresenze_t.dataregistrazione,
	//	    mercatipresenze_d.*
	//	FROM
	//	    mercatipresenze_d
	//	    INNER JOIN mercatipresenze_t ON mercatipresenze_t.idcomune = mercatipresenze_d.idcomune
	//	                                    AND mercatipresenze_t.id = mercatipresenze_d.fkidtestata
	//	WHERE
	//	    mercatipresenze_d.idcomune = 'L219'
	//	    AND   (
	//	        NOT fkidtestata = 868
	//	    )
	//	    AND   mercatipresenze_t.dataregistrazione = TO_DATE('21/02/2019','dd/MM/yyyy')
	//	    AND   mercatipresenze_d.fk_autorizzazioni_id = 10535
	// 	    AND   mercatipresenze_d.numpresenze=1
	boolean appelloTerminato = giorno.getFlagChiusuraAppello() == null ? false : giorno.getFlagChiusuraAppello().booleanValue();
	if (!appelloTerminato) {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.notEquals("mercatiPresenzeTId", idGiornata, Integer.class));
	    fr.addFilterField(FilterUtils.equals("autorizzazioniId", idAutorizzazione, Integer.class));
	    fr.addFilterField(FilterUtils.equals("dataRegistrazione", giorno.getDataRegistrazione(), "mercatiPresenzeT", Date.class));
	    fr.addFilterField(FilterUtils.equals("numeropresenze", 1, Integer.class));
	    filterTable.addRestriction(fr);
	    List<MercatipresenzeD> presenzeAltriMercati = mercatipresenzeDDAO.findByFilterTable(filterTable);
	    // Se matura la presenza ovvero non è terminato l'appello
	    for (MercatipresenzeD mpd : presenzeAltriMercati) {
		String messErroreSpuntista = getMessageFromBundle("label.error.spuntista presente_altro_mercato",
			new Object[] { mpd.getMercatiPresenzeT().getMercato().getDescrizione() });
		errori.add(messErroreSpuntista);
	    }
	} else {
	    // se non matura la presenza ovvero appello terminato
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.notEquals("mercatiPresenzeTId", idGiornata, Integer.class));
	    fr.addFilterField(FilterUtils.equals("autorizzazioniId", idAutorizzazione, Integer.class));
	    fr.addFilterField(FilterUtils.equals("dataRegistrazione", giorno.getDataRegistrazione(), "mercatiPresenzeT", Date.class));
	    filterTable.addRestriction(fr);
	    List<MercatipresenzeD> presenzeAltriMercati = mercatipresenzeDDAO.findByFilterTable(filterTable);
	    //	2.	Uno spuntista presente su CINCINNATO non ha ricevuto il posteggio perché non ce ne erano di disponibili, 
	    //              è andato su MARTINI per fare il FUORI SPUNTA e l’APP indicava che era presente su CINCINNATO.
	    //		a.	SOLUZIONE: se uno SPUNTISTA viene aggiunto e NON MATURA LA PRESENZA allora il controllo se è presente in altro 
	    //			mercato è valido solo se HA UN POSTEGGIO ASSEGNATO.
	    //	Se MATURA la PRESENZA allora il controllo che facciamo è VALIDO.	
	    for (MercatipresenzeD mpd : presenzeAltriMercati) {
		if (mpd.getPosteggio() != null && mpd.getPosteggio().getId() != null && mpd.getPosteggio().getId().getCodice() != null) {
		    String messErroreSpuntista = getMessageFromBundle("label.error.spuntista_presente_posteggio_altro_mercato",
			    new Object[] { mpd.getPosteggio().getCodiceposteggio(), mpd.getMercatiPresenzeT().getMercato().getDescrizione() });
		    errori.add(messErroreSpuntista);
		}
	    }
	}
	// 2° controllo
	// devo anche verificare che una concessione non possa presentarsi come spuntista in altro mercato lo stesso giorno in cui ha la concessione
	List<AutorizzazioniConcessioni> concs = autorizzazioniConcessioniService.findByAutorizzazioneAttuale(idAutorizzazione);
	for (AutorizzazioniConcessioni conc : concs) {
	    // se la concessione è di un altro mercato allora mi basta il confronto con i riferimenti della giornata
	    Integer codiceMercatoConcessione = conc.getMercati().getId().getCodice();
	    Integer codiceMercatiUsoConcessione = conc.getMercatiUso().getId().getCodice();
	    if (!(codiceMercato.equals(codiceMercatoConcessione) && codiceUso.equals(codiceMercatiUsoConcessione))) {
		// non è lo stesso mercato / uso faccio i l controllo
		Integer giornoSettimanaGiornata = -10;
		if (giorno.getMercatoUso().getGiornisettimana() != null && giorno.getMercatoUso().getGiornisettimana().getGsValore() != null) {
		    giornoSettimanaGiornata = giorno.getMercatoUso().getGiornisettimana().getGsValore();
		}
		Integer giornoSettimanaConcessione = -10;
		if (conc.getMercatiUso().getGiornisettimana() != null && conc.getMercatiUso().getGiornisettimana().getGsValore() != null) {
		    giornoSettimanaConcessione = conc.getMercatiUso().getGiornisettimana().getGsValore();
		}
		if (giornoSettimanaConcessione.equals(giornoSettimanaGiornata)) {
		    // richiesta sulla stessa giornata faccio il controllo 
		    // se fossero state differenti
		    //		2	Lunedì
		    //		3	Martedì
		    //		4	Mercoledì
		    //		5	Giovedì
		    //		6	Venerdì
		    //		7	Sabato
		    //		1	Domenica
		    //			Nessuno
		    errori.add(
			    "Risulta Concessionario in " + conc.getMercati().getDescrizione() + " [" + conc.getMercatiUso().getDescrizione() + "]");
		}
	    }
	}
	// 3° controllo
	// devo anche verificare che non sia presentecome concessionario sullo stesso mercato dove non può essere anche spuntista
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", idGiornata, Integer.class));
	fr.addFilterField(FilterUtils.equals("autorizzazioneConcessionarioAssenteId", idAutorizzazione, Integer.class));
	filterTable.addRestriction(fr);
	List<MercatipresenzeD> presenzeAltriMercati = mercatipresenzeDDAO.findByFilterTable(filterTable);
	for (MercatipresenzeD mpd : presenzeAltriMercati) {
	    String messErroreSpuntista = getMessageFromBundle("label.error.presente_come_concessionario_su_stesso_mercato",
		    new Object[] { mpd.getPosteggio().getCodiceposteggio() });
	    errori.add(messErroreSpuntista);
	}
	return errori;
    }

    @Override
    public void copiaInformazioniMercatopresenzeDOrigine(MercatipresenzeD origine, MercatipresenzeD destinatario) {

	MercatiSpunte spuntaOrigine = null;
	Attivita attOrig = null;
	if (origine.getMercatiSpunte() != null) {
	    spuntaOrigine = origine.getMercatiSpunte(); // se aveva già settato la FASE gliela reimposto
	}
	if (origine.getAttivita() != null) {
	    attOrig = origine.getAttivita();
	}
	BigDecimal importo = null;
	if (origine.getImporto() != null) {
	    importo = origine.getImporto();
	}
	Boolean flagPagato = BooleanUtils.toBoolean(origine.getFlagPagato());
	Tipimodalitapagamento tipimodalitapagamentoOrig = null;
	if (EntityUtils.getNestedProperty(origine.getTipimodalitapagamento(), "id.codice") != null) {
	    tipimodalitapagamentoOrig = origine.getTipimodalitapagamento();
	}
	String riferimentiPagamentoOrig = null;
	if (StringUtils.isNotBlank(origine.getRiferimentiPagamento())) {
	    riferimentiPagamentoOrig = origine.getRiferimentiPagamento();
	}
	Boolean flagRinunciaPresenzaOrig = BooleanUtils.toBoolean(origine.getFlagRinunciaPresenza());
	MercatiD mercatiDOrig = null;
	if (EntityUtils.getNestedProperty(origine.getPosteggioRinunciato(), "id.codice") != null) {
	    mercatiDOrig = origine.getPosteggioRinunciato();
	}
	destinatario.setNumeropresenze(origine.getNumeropresenze());
	destinatario.setMercatiSpunte(spuntaOrigine);
	destinatario.setAttivita(attOrig);
	destinatario.setImporto(importo);
	destinatario.setFlagPagato(flagPagato);
	destinatario.setTipimodalitapagamento(tipimodalitapagamentoOrig);
	destinatario.setRiferimentiPagamento(riferimentiPagamentoOrig);
	destinatario.setFlagRinunciaPresenza(flagRinunciaPresenzaOrig);
	destinatario.setPosteggioRinunciato(mercatiDOrig);
	Anagrafe collaboratore = null;
	if (EntityUtils.getNestedProperty(origine.getCollaboratore(), "id.codice") != null) {
	    collaboratore = origine.getCollaboratore();
	}
	destinatario.setCollaboratore(collaboratore);
	if (EntityUtils.getNestedProperty(origine.getDettPosizioneDebitoria(), "id.codice") != null) {
	    destinatario.setDettPosizioneDebitoria(origine.getDettPosizioneDebitoria());
	}
    }

    @Override
    public List<PagamentiMercatoRestHelper> getPosizioniDebitoriePerAutorizzazione(Integer[] idAutorizzazioni, boolean verificaStatoPosizioni,
	    FiltroPagamentoEnum statoPagamento, Date consideraIPagamentiDallaData) {

	List<PagamentiMercatoRestHelper> result = new ArrayList<PagamentiMercatoRestHelper>();
	if (idAutorizzazioni == null || idAutorizzazioni.length == 0) {
	    return result;
	}
	String[] stati = new String[] { StatoPagamentoType.ACQUISITO.name(), StatoPagamentoType.TRASMESSO_A_PSP.name(),
	    StatoPagamentoType.ATTIVATO_IN_PSP.name(), StatoPagamentoType.RENDICONTATO_DA_IC.name(), StatoPagamentoType.NOTIFICATO_DA_PSP.name(),
	    StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE.name(), StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO.name() };
	if (statoPagamento.equals(FiltroPagamentoEnum.DA_PAGARE)) {
	    stati = new String[] { StatoPagamentoType.ACQUISITO.name(), StatoPagamentoType.TRASMESSO_A_PSP.name(),
		StatoPagamentoType.ATTIVATO_IN_PSP.name() };
	} else if (statoPagamento.equals(FiltroPagamentoEnum.PAGATE)) {
	    stati = new String[] { StatoPagamentoType.RENDICONTATO_DA_IC.name(), StatoPagamentoType.NOTIFICATO_DA_PSP.name(),
		StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE.name(), StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO.name() };
	}
	List<MercatiPresenzeConPosDebBean> presenze = mercatipresenzeDDAO.findPresenzeConInfoPosDeb(idAutorizzazioni, stati,
		consideraIPagamentiDallaData);
	// mappa di mercati, uso, mpd
	Map<Integer, Map<Integer, Set<Integer>>> mercati = new HashMap<Integer, Map<Integer, Set<Integer>>>();
	Map<Integer, AutorizzazioniRestHelper> mAuts = new HashMap<Integer, AutorizzazioniRestHelper>();
	for (Integer idAut : idAutorizzazioni) {
	    AutorizzazioniRestHelper h = mAuts.get(idAut);
	    if (h == null) {
		log.debug(" popolo l'autorizzazione {}", new Object[] { idAut });
		mAuts.put(idAut, autorizzazioniService.findAutorizzazioneRestHelper(idAut));
	    }
	}
	for (MercatiPresenzeConPosDebBean mpd : presenze) {
	    boolean inserisci = true;
	    if (verificaStatoPosizioni) {
		log.debug("Stato del dettaglio posizione debitoria {} = {}", mpd.getIddettposdeb(), mpd.getStatopostdeb());
		if (StatoPagamentoType.ACQUISITO.name().equalsIgnoreCase(mpd.getStatopostdeb()) //
			|| StatoPagamentoType.TRASMESSO_A_PSP.name().equalsIgnoreCase(mpd.getStatopostdeb()) //
			|| StatoPagamentoType.ATTIVATO_IN_PSP.name().equalsIgnoreCase(mpd.getStatopostdeb()) /*con acquisito verifico se pagato*/) {
		    // se lo stato è acquisito provo a verificare se lo IUV è tornato mediante la chiamata a aggiornastato posizione debitoria
		    inserisci = false;
		    try {
			log.debug("Prima di chiamare l'aggiornamento dello stato per la posizione {}", mpd.getIddettposdeb());
			VerificaStatoPosizioniDebitorie statoAttuale = nodoPagamentiService
				.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(mpd.getIddettposdeb());
			log.debug("Stato del dettaglio posizione debitoria dopo l'aggiornamento {} = {}", mpd.getIddettposdeb(),
				statoAttuale.getStatoAttuale().getCodiceStato());
			if (!(StatoPagamentoType.ACQUISITO.name().equalsIgnoreCase(mpd.getStatopostdeb())
				|| StatoPagamentoType.TRASMESSO_A_PSP.name().equalsIgnoreCase(mpd.getStatopostdeb()))) {
			    // se lo stato tornato non è acquisito o trasmesso a psp visualizzo la posizione debitoria nello stato tornato variabile inserisci
			    inserisci = true;
			}
		    } catch (FunzioneBusinessRemotaException e) {
			log.error("errore nella chiamata al servizio verifica stato per la posizione {}", mpd.getIddettposdeb(), e);
			throw new RuntimeException(e);
		    }
		}
	    }
	    log.debug("Stato del dettaglio posizione debitoria {} = {} = inserisci {}",
		    new Object[] { mpd.getIddettposdeb(), mpd.getIddettposdeb(), inserisci });
	    if (inserisci) {
		Integer idMercato = mpd.getCodicemercato();
		if (mercati.get(idMercato) == null) {
		    mercati.put(idMercato, new HashMap<Integer, Set<Integer>>());
		}
		Integer idMercatoUso = mpd.getCodiceuso();
		Map<Integer, Set<Integer>> giorni = mercati.get(idMercato);
		if (giorni.get(idMercatoUso) == null) {
		    giorni.put(idMercatoUso, new HashSet<Integer>());
		}
		Set<Integer> pagamenti = giorni.get(idMercatoUso);
		pagamenti.add(mpd.getId());
		giorni.put(idMercatoUso, pagamenti);
		mercati.put(idMercato, giorni);
	    }
	}
	for (Map.Entry<Integer, Map<Integer, Set<Integer>>> struct : mercati.entrySet()) {
	    PagamentiMercatoRestHelper prh = new PagamentiMercatoRestHelper();
	    prh.setId(struct.getKey());
	    String descrizioneMercato = null;
	    Map<Integer, Set<Integer>> giorni = struct.getValue();
	    for (Entry<Integer, Set<Integer>> s : giorni.entrySet()) {
		String descrizioneGiorno = null;
		Integer codiceUso = s.getKey();
		Set<Integer> pres = s.getValue();
		PagamentiMercatoGiornoRestHelper g = new PagamentiMercatoGiornoRestHelper();
		g.setId(codiceUso);
		for (Integer p : pres) {
		    MercatipresenzeD pre = this.findById(new PkId(p));
		    descrizioneMercato = pre.getMercatiPresenzeT().getMercato().getDescrizione();
		    descrizioneMercato = Utilities.replaceDescrizioneGiorno(descrizioneMercato, pre.getMercatiPresenzeT().getDataRegistrazione());
		    descrizioneGiorno = pre.getMercatiPresenzeT().getMercatoUso().getDescrizione();
		    PagamentiMercatoPosizDebRestHelper pagam = populatePosizioneDebitoriaHelper(pre,
			    mAuts.get(pre.getAutorizzazioni().getId().getCodice()), false);
		    log.debug(" popolo populatePosizioneDebitoriaHelper autorizzazione {}, presenza {}",
			    new Object[] { pre.getAutorizzazioni().getId().getCodice(), pre.getId().getCodice() });
		    g.getPagamenti().add(pagam);
		}
		g.setDescrizione_giorno(descrizioneGiorno);
		prh.getGiorno().add(g);
	    }
	    prh.setDescrizione_mercato(descrizioneMercato);
	    result.add(prh);
	}
	return result;
    }

    private PagamentiMercatoPosizDebRestHelper populatePosizioneDebitoriaHelper(MercatipresenzeD pre, AutorizzazioniRestHelper aut,
	    boolean updateStato) {

	PagamentiMercatoPosizDebRestHelper pagam = new PagamentiMercatoPosizDebRestHelper();
	pagam.setData_presenza(pre.getMercatiPresenzeT().getDataRegistrazione());
	DettPosizioneDebitoria dettPosizioneDebitoria = pre.getDettPosizioneDebitoria();
	Boolean verificatoStatoPagamenti = null;
	if (updateStato) {
	    verificatoStatoPagamenti = verificaAndAggiornaStatoPagamenti(dettPosizioneDebitoria.getId().getCodice(), null, true);
	} else {
	    verificatoStatoPagamenti = verificatoStatoPagamentiAttuale(dettPosizioneDebitoria, null, true);
	}
	if (pre.getPosteggio() != null) {
	    pagam.setPosteggio(pre.getPosteggio().getCodiceposteggio());
	    if (pre.getPosteggio().getSuperficie() != null) {
		pagam.setSuperficie(pre.getPosteggio().getSuperficie().doubleValue());
	    }
	}
	if (aut == null) {
	    aut = autorizzazioniService.findAutorizzazioneRestHelper(pre.getAutorizzazioni().getId().getCodice());
	}
	pagam.setAutorizzazione(aut);
	mercatipresenzeDDAO.flush();
	mercatipresenzeDDAO.clear(); // forzo la rilettura di posizione debitoria altrimenti la data la torna come date e non come timestamp 
	//e si verifica un problema lato deserializzazione
	dettPosizioneDebitoria = dettPosizioneDebitoriaService.findById(new PkId(dettPosizioneDebitoria.getId().getCodice()));
	pagam.setCodice_iuv(dettPosizioneDebitoria.getIuv());
	pagam.setData_registrazione(dettPosizioneDebitoria.getDataRegistrazione());
	pagam.setId_pagamento(dettPosizioneDebitoria.getId().getCodice());
	pagam.setId_presenza(pre.getId().getCodice());
	BigDecimal importo = dettPosizioneDebitoria.getImportoIvato();
	pagam.setImporto(importo.doubleValue());
	pagam.setStato_pagamento(dettPosizioneDebitoria.getDescStato());
	pagam.setEffettuato(BooleanUtils.isTrue(verificatoStatoPagamenti));
	return pagam;
    }

    @Override
    public PagamentiMercatoPosizDebRestHelper populatePosizioneDebitoriaHelper(MercatipresenzeD pre) {

	return populatePosizioneDebitoriaHelper(pre, null, true);
    }

    private Boolean verificatoStatoPagamentiAttuale(DettPosizioneDebitoria dett, Boolean flagPagato, boolean isAttivoNodoPagamenti) {

	// StatoPosizioneDebitoria s = new StatoPosizioneDebitoria(dett.getStato(), dett.getDescStato(), dett.getDataUltimoStato());
	return dett.mostraComePagatoSuAppVigili();
    }

    @Override
    public Boolean verificaAndAggiornaStatoPagamenti(AutorizzazioniRestHelper arh, boolean isAttivoNodoPagamenti) {

	return this.verificaAndAggiornaStatoPagamenti(arh.getPosdebspunt(), arh.getPagamentoEffettuato(), isAttivoNodoPagamenti);
    }

    @Override
    public Boolean verificaAndAggiornaStatoPagamenti(Integer idDettaglioPosizioneDebitoria, Boolean flagPagato, boolean isAttivoNodoPagamenti) {

	if (isAttivoNodoPagamenti) {
	    if (idDettaglioPosizioneDebitoria == null) {
		return null;
	    } else {
		try {
		    log.debug("verificatoStatoPagamenti# prima di invocare il verifica stato per idDettaglioPosizioneDebitoria {}",
			    idDettaglioPosizioneDebitoria);
		    nodoPagamentiService.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(idDettaglioPosizioneDebitoria);
		    return dettPosizioneDebitoriaService.findById(new PkId(idDettaglioPosizioneDebitoria)).mostraComePagatoSuAppVigili();
		} catch (Exception e) {
		    log.error("verificatoStatoPagamenti# {}", e);
		    return false;
		}
	    }
	} else {
	    return BooleanUtils.toBoolean(flagPagato);
	}
    }

    @Override
    public List<MercatipresenzeDDTO> findByMercatiPresenzeTAndPosteggi(Integer idGiornata, List<Long> idPosteggi) {

	return mercatipresenzeDDAO.findByMercatiPresenzeTAndPosteggi(idGiornata, idPosteggi);
    }

    @Override
    public List<MercatipresenzeD> findByPosizioneDebitoria(Integer dettPosizioneDebitoriaId, boolean escludiLePosizioniSenzaPosteggio) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("dettPosizioneDebitoriaId", dettPosizioneDebitoriaId, Integer.class));
	if (escludiLePosizioniSenzaPosteggio) {
	    fr.addFilterField(FilterUtils.isNotNull("posteggioId"));
	}
	filterTable.addRestriction(fr);
	return mercatipresenzeDDAO.findByFilterTable(filterTable);
    }

    @Override
    public boolean isConcessionarioPresenteByIdGiornataEIdPosteggio(Integer idGiornata, Integer idPosteggio) {

	InfoGiornataPresenzaBean i = getInformazioniPresenza(idGiornata, idPosteggio);
	return i.isConcessionarioPresente();
    }

    @Override
    public boolean isSpuntistaPresenteByIdGiornataEIdPosteggio(Integer idGiornata, Integer idPosteggio) {

	InfoGiornataPresenzaBean i = getInformazioniPresenza(idGiornata, idPosteggio);
	return i.isSpuntistaPresente();
    }

    @Override
    public List<ValoriLivelloServizio> livelliDiServizioConfiguratiPerIdGiornataEIdPosteggio(Integer idGiornata, Integer idPosteggio) {

	MercatipresenzeT testata = this.mercatipresenzeTService.findById(new PkId(idGiornata));
	if (testata == null) {
	    throw new RuntimeException("Impossibile trovare la giornata con MERCATIPRESENZE_T.ID = " + idGiornata);
	}
	MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	if (posteggio == null) {
	    throw new RuntimeException("Impossibile trovare il posteggio con MERCATI_D.ID = " + idPosteggio);
	}
	//	MercatipresenzeD presenza = this.findByMercatiPresenzeTAndPosteggio(testata, posteggio);
	//	if (presenza == null) {
	//	    return false;
	//	}
	Date dataGiornata = testata.getDataRegistrazione();
	Integer idUso = testata.getMercatoUso().getId().getCodice();
	return this.mercatipresenzeDDAO.livelliDiServizioConfiguratiPerGiornataAndPosteggioAndUso(dataGiornata, idPosteggio, idUso);
    }

    @Override
    public boolean isAssenzaGiustificata(Integer idGiornata, Integer idPosteggio) {

	InfoGiornataPresenzaBean i = getInformazioniPresenza(idGiornata, idPosteggio);
	return i.isAssenzaGiustificata();
    }

    private List<MercatipresenzeD> getPresenzePerVerificaPagamento(Integer idgiornata, List<Integer> autorizzazioni) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", idgiornata, Integer.class));
	// BEGIN filtro i posteggi occupati posteggioId not null && autorizzazioniId not null 
	fr.addFilterField(FilterUtils.isNotNull("posteggioId"));
	fr.addFilterField(FilterUtils.isNotNull("autorizzazioniId"));
	// END filtro i posteggi occupati
	if (autorizzazioni != null && !autorizzazioni.isEmpty()) {
	    // se lo cerco per la specifica autorizzazione
	    fr.addFilterField(FilterUtils.in("autorizzazioniId", autorizzazioni.toArray(), Integer.class));
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("codiceposteggio", "posteggio"));
	return mercatipresenzeDDAO.findByFilterTable(ft);
    }

    @Override
    public StatiPosizioniDebitorieGiornataMercatoBean verificaStatoPosizioniDebitoriePerGiornata(Integer idgiornata) {

	return verificaStatoPosizioniDebitoriePerAutorizzazioni(idgiornata, null);
    }

    @Override
    public StatiPosizioniDebitorieGiornataMercatoBean verificaStatoPosizioniDebitoriePerAutorizzazioni(Integer idgiornata,
	    List<Integer> autorizzazioni) {

	StatiPosizioniDebitorieGiornataMercatoBean retVal = new StatiPosizioniDebitorieGiornataMercatoBean();
	List<MercatipresenzeD> presenze = getPresenzePerVerificaPagamento(idgiornata, autorizzazioni);
	for (MercatipresenzeD presenza : presenze) {
	    verificaStatoPagamentoPresenza(presenza, retVal);
	}
	return retVal;
    }

    private void verificaStatoPagamentoPresenza(MercatipresenzeD presenza, StatiPosizioniDebitorieGiornataMercatoBean retVal) {

	if (!presenza.isPosteggioOccupato()) {
	    // il posteggio non è occupato esco non c'è niente da pagare
	    // dovrebbe essere una anomalia in quanto ho filtrato le righe con posteggio occupato
	    return;
	}
	PagamentoModel stato = new PagamentoModel();
	stato.setIdAutorizzazione(presenza.getAutorizzazioni().getId().getCodice());
	stato.setStato(StatoPagamentoEnum.NienteDaPagare); // NienteDaPagare,
	if (presenza.getDettPosizioneDebitoria() != null) {
	    // è presente la posizione debitoria verifico lo stato
	    boolean pagata = presenza.getDettPosizioneDebitoria().mostraComePagatoSuAppVigili();
	    if (pagata) {
		stato.setStato(StatoPagamentoEnum.PagatoConPosizioneDebitoria); // PagatoConPosizioneDebitoria,
	    } else {
		stato.setStato(StatoPagamentoEnum.DaPagare); // lo stato è DaPagare;
	    }
	    // aggiungo i dettagli della posizione debitoria
	    stato.setPosizioneDebitoria(PosizioneDebitoriaModel.fromDettPosizioneDebitoria(presenza.getDettPosizioneDebitoria()));
	}
	if (borsellinoMovimentiDAO.isPresenzaPagataDaBorsellino(presenza.getMercatiPresenzeT().getId().getCodice(),
		presenza.getAutorizzazioni().getId().getCodice(), presenza.getPosteggio().getId().getCodice())) {
	    stato.setStato(StatoPagamentoEnum.PagatoConBorsellino); // PagatoConBorsellino,
	}
	if (stato.getStato().equals(StatoPagamentoEnum.NienteDaPagare)
		&& pagamentiService.nodoPagamentiAttivo(presenza.getMercatiPresenzeT().getMercato())) {
	    // Non ho niente da pagare ma il pagamento è attivato per il mercato
	    stato.setStato(StatoPagamentoEnum.PosizioneNonCreata);
	    if (presenza.isConcessionarioPresente()
		    && !pagamentiService.verificaDataPosDebConcessionariAllaData(presenza.getMercatiPresenzeT().getDataRegistrazione())) {
		stato.setStato(StatoPagamentoEnum.NienteDaPagare);
		// NienteDaPagare, 
		// è un concessionario ma la data giornata è antecedente alla
		// data di attivazione della verticalizzazione per la quale pagano anche i concessionari
	    }
	}
	if (presenza.isSpuntista()) {
	    retVal.getSpuntisti().add(stato);
	} else {
	    retVal.getConcessionari().add(stato);
	}
    }

    @Override
    public List<Integer> findPresenzeConcessionariSenzaPosizioniDebitorie(Date data) {

	return mercatipresenzeDDAO.findPresenzeConcessionariSenzaPosizioniDebitorie(data);
    }

    @Override
    public List<PresenzeNonPagateBean> findPresenzeNonAssociateAMetodoDiPagamento(Date dalladata, Date alladata, String[] codiceIstat) {

	return mercatipresenzeDDAO.findPresenzeNonAssociateAMetodoDiPagamento(dalladata, alladata, codiceIstat);
    }

    //    @Override
    private InfoGiornataPresenzaBean getInformazioniPresenza(Integer idGiornata, Integer idPosteggio) {

	log.debug("getInformazioniPresenza {},{} BEGIN", idGiornata, idPosteggio);
	MercatipresenzeD presenza = this.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	if (presenza == null) {
	    log.debug("getInformazioniPresenza {},{} null END", idGiornata, idPosteggio);
	    return new InfoGiornataPresenzaBean(false, false, false, null);
	}
	boolean spuntistaPresente = BooleanUtils.toBoolean(presenza.isSpuntista());
	String catMerc = null;
	if (presenza.getAttivita() != null && presenza.getAttivita().getId() != null) {
	    catMerc = presenza.getAttivita().getId().getCodiceistat();
	}
	log.debug("getInformazioniPresenza {},{} not null END", idGiornata, idPosteggio);
	return new InfoGiornataPresenzaBean((presenza.getOccupante() != null && !spuntistaPresente), spuntistaPresente,
		BooleanUtils.toBoolean(presenza.getFlagAssenzaGiust()), catMerc);
    }

    @Override
    public PosteggioInfoRestBean calcolaCostoPosteggio(Integer idMercatipresenzeD) {

	if (idMercatipresenzeD == null) {
	    throw new IllegalArgumentException(
		    "Impossibile calcolare il costo del posteggio senza passare il riferimento da cui ricavare il posteggio");
	}
	MercatipresenzeD pres = this.findById(new PkId(idMercatipresenzeD));
	if (pres == null) {
	    throw new IllegalArgumentException(
		    "Dati non corretti. Non è stata trovata la presenza con gli identificativi " + idMercatipresenzeD + " non esiste.");
	}
	if (pres.getPosteggio() == null) {
	    throw new IllegalArgumentException("Dati non corretti. Il posteggio non è stato registrato per la presenza con id " + idMercatipresenzeD);
	}
	if (pres.getAutorizzazioni() == null) {
	    throw new IllegalArgumentException("Dati non corretti. L' autorizzazione non presente per la presenza con id " + idMercatipresenzeD);
	}
	PosteggioInfoRestBean p = new PosteggioInfoRestBean();
	p.setCodicePosteggio(pres.getPosteggio().getCodiceposteggio());
	String contesto = WebConstants.MERCATO_CONTESTO_CONCESSIONARI;
	String codiceIstatMerceologiaPosteggio = null;
	if (pres.isSpuntista()) {
	    contesto = WebConstants.MERCATO_CONTESTO_SPUNTISTI;
	    Attivita attivita = pres.getAttivita();
	    if (attivita != null) {
		codiceIstatMerceologiaPosteggio = attivita.getId().getCodiceistat();
	    }
	}
	List<MercatiCfgAttivita> listMcfgAttivita = mercatiCfgAttivitaService.findAll(null, null);
	PosteggioImportoHelper costoPosteggioSpuntista = calcoloCostoPosteggiService.calcolaCostoPosteggio(pres, pres.getPosteggio(),
		listMcfgAttivita, pres.getMercatiPresenzeT().getAnno(), 1, null, contesto, codiceIstatMerceologiaPosteggio,
		pres.getMercatiPresenzeT().getMercatoUso().getId().getCodice(), pres.getMercatiPresenzeT().getDataRegistrazione(),
		MercatiFormuleCalcoloContestoEnum.PRESENZA);
	p.setImportoHelper(costoPosteggioSpuntista);
	BigDecimal importo = BigDecimal.ZERO;
	if (costoPosteggioSpuntista != null) {
	    importo = costoPosteggioSpuntista.getImporto();
	}
	if (pres != null && pres.getImporto() != null && (pres.getImporto().compareTo(BigDecimal.ZERO) != 0)) {
	    importo = pres.getImporto();
	}
	if (importo.compareTo(BigDecimal.ZERO) > 0) {
	    p.setImporto(importo.doubleValue());
	}
	return p;
    }

    @Override
    public List<DettaglioPresenzaComunicazioneModel> findPresenzeScalateDalBorsellino(int idGiornata) {

	return this.mercatipresenzeDDAO.findPresenzeScalateDalBorsellino(idGiornata);
    }

    @Override
    public List<MercatipresenzeD> findPresenzeConcessionarioDallaData(AutorizzazioniConcessioni autConc, Date dataCessazione, Integer firstresult,
	    Integer maxResults) {

	return mercatipresenzeDDAO.findByFilterTable(getFilterTablePerPresenzeConcessionario(autConc, dataCessazione, false), firstresult,
		maxResults);
    }

    @Override
    public int countPresenzeConcessionarioDallaData(AutorizzazioniConcessioni autConc, Date dataCessazione) {

	return mercatipresenzeDDAO.countRecord(getFilterTablePerPresenzeConcessionario(autConc, dataCessazione, true));
    }

    private FilterTable getFilterTablePerPresenzeConcessionario(AutorizzazioniConcessioni autConc, Date dataCessazione, boolean isCount) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", autConc.getMercati().getId().getCodice(), "mercatiPresenzeT", Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatoUsoId", autConc.getMercatiUso().getId().getCodice(), "mercatiPresenzeT", Integer.class));
	fr.addFilterField(FilterUtils.greaterEqual("dataRegistrazione", dataCessazione, "mercatiPresenzeT", Date.class));
	fr.addFilterField(FilterUtils.equals("autorizzazioneConcessionarioAssenteId",
		autConc.getAutorizzazioniByFkAutconcAutatt().getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.equals("posteggioId", autConc.getMercatiD().getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	if (!isCount) {
	    ft.addOrder(FilterUtils.orderAsc("dataRegistrazione", "mercatiPresenzeT"));
	}
	return ft;
    }

    @Override
    public List<PosteggioPerAutBean> findAutorizzazioniSganciateDaSIAP(Set<Integer> autSenzaPosteggi) {

	return mercatipresenzeDDAO.findAutorizzazioniSganciateDaSIAP(autSenzaPosteggi);
    }
}
