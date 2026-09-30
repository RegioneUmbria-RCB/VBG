/**
 * 
 */
package it.gruppoinit.pal.gp.core.features.oneri;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.xml.crypto.Data;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeoneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Chiusureistanza;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.ImportiRateizzati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeOneriRegulus;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniO;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniOId;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriD;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriT;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstoneriDettPosizioni;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentooneri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.OneriEntrateUsciteAmministrazioneHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RateizzazioniHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DatiPagamento;
import it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni.IstanzecalcolocanoniOService;
import it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni.IstanzecalcolocanoniTService;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoOnereIstanzaAggiornato;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoOnereIstanzaInserito;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoPagamentoOnereRegistrato;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoPreOnereIstanzaEliminato;
import it.gruppoinit.pal.gp.core.features.oneri.messaggi.MessaggioOnereCopiatoDaIstanza;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.features.oneri.regulus.IstanzeOneriRegulusService;
import it.gruppoinit.pal.gp.core.features.rateizzazioni.IstanzeoneriDerateizzatoBean;
import it.gruppoinit.pal.gp.core.features.rateizzazioni.TemplateRata;
import it.gruppoinit.pal.gp.core.features.scadenzario.ScadenzarioOneri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.TipologiaOnere;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ChiusureistanzaService;
import it.gruppoinit.pal.gp.core.service.InteressiLegaliService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipicausalioninteressiService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.TipimovimentooneriService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * @author francescop
 * 
 */
@Service
public class IstanzeoneriServiceImpl extends BaseServiceImpl<Istanzeoneri, PkId> implements IstanzeoneriService, IOneriPosizioniDebitorieService {

    private AmministrazioniService amministrazioniService;
    private ChiusureistanzaService chiusureistanzaService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private IstanzeService istanzeService;
    private IstanzeoneriDAO istanzeoneriDAO;
    private MovimentiService movimentiService;
    private ResponsabiliService responsabiliService;
    private TipicausalioneriService tipicausalioneriService;
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    private TipiMovimentoService tipiMovimentoService;
    private TipimovimentooneriService tipimovimentooneriService;
    private VerticalizzazioniService verticalizzazioniService;
    private UserSecurityService userSecurityService;
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    private InteressiLegaliService interessiLegaliService;
    private IEventPublisher eventPublisher;
    private TipicausalioninteressiService tipicausalioninteressiService;
    private IstanzeOneriRegulusService istanzeOneriRegulusService;
    private static final Logger log = LoggerFactory.getLogger(IstanzeoneriServiceImpl.class);

    @Autowired
    public void setInteressiLegaliService(InteressiLegaliService interessiLegaliService) {

	this.interessiLegaliService = interessiLegaliService;
    }

    @Autowired
    public void setOneritipirateizzazioneService(OneritipirateizzazioneService oneritipirateizzazioneService) {

	this.oneritipirateizzazioneService = oneritipirateizzazioneService;
    }

    @Autowired
    public void setEventPublisher(IEventPublisher eventPublisher) {

	this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setChiusureistanzaService(ChiusureistanzaService chiusureistanzaService) {

	this.chiusureistanzaService = chiusureistanzaService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeoneriDAO(IstanzeoneriDAO istanzeoneriDAO) {

	this.istanzeoneriDAO = istanzeoneriDAO;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Autowired
    public void setTipimodalitapagamentoService(TipimodalitapagamentoService tipimodalitapagamentoService) {

	this.tipimodalitapagamentoService = tipimodalitapagamentoService;
    }

    @Autowired
    public void setTipimovimentooneriService(TipimovimentooneriService tipimovimentooneriService) {

	this.tipimovimentooneriService = tipimovimentooneriService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setTipicausalioninteressiService(TipicausalioninteressiService tipicausalioninteressiService) {

	this.tipicausalioninteressiService = tipicausalioninteressiService;
    }

    @Autowired
    public void setIstanzeOneriRegulusService(IstanzeOneriRegulusService istanzeOneriRegulusService) {

	this.istanzeOneriRegulusService = istanzeOneriRegulusService;
    }

    @Override
    protected Class<Istanzeoneri> getEntityClass() {

	return Istanzeoneri.class;
    }

    @Override
    public void delete(Istanzeoneri entity) {

	if (isDeleteAllowed(entity)) {
	    this.eventPublisher.publish(new EventoPreOnereIstanzaEliminato(entity.getId().getCodice()));
	    childDelete(entity);
	    istanzeoneriDAO.delete(entity);
	}
    }

    @Override
    protected boolean isDeleteAllowed(Istanzeoneri entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.isPresentiPosizioniDebitorie()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_CANCELLAZIONE_ISTANZE_ONERI_CON_POSIZIONE_DEBITORIA, null, "",
		    "ISTONERI_DETT_POSIZIONI.FK_ISTANZEONERI_ID", null));
	    delete = false;
	}
	if (entity.getIstanzeoneriReguluses() != null && !entity.getIstanzeoneriReguluses().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ISTANZEONERI_REGULUS", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    protected void childDelete(Istanzeoneri entity) {

	// La cancellazione manuale delle FK è gestita tramite l'evento EventoPreOnereIstanzaEliminato
	// 	vd. metodo delete
    }

    @Override
    public List<Istanzeoneri> findAll(Integer firstResult, Integer maxResult) {

	return istanzeoneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Istanzeoneri findById(PkId id) {

	return istanzeoneriDAO.findById(id);
    }

    @Override
    public void insert(Istanzeoneri entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    istanzeoneriDAO.insert(entity);
	    eventPublisher.publish(new EventoOnereIstanzaInserito(entity.getId().getCodice()));
	}
    }

    @Override
    public void update(Istanzeoneri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeoneriDAO.update(entity);
	    eventPublisher.publish(new EventoOnereIstanzaAggiornato(entity.getId().getCodice()));
	}
    }

    private void dataIntegration(Istanzeoneri entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro istanze oneri è nullo");
	}
	if (entity.getFlentratauscita() == null) {
	    entity.setFlentratauscita(Boolean.FALSE);
	}
	if (entity.getFlribasso() == null) {
	    entity.setFlribasso(Boolean.FALSE);
	}
	if (entity.getPrezzo() == null) {
	    entity.setPrezzo(new BigDecimal(0));
	}
	if (entity.getPrezzoistruttoria() == null) {
	    entity.setPrezzoistruttoria(new BigDecimal(0));
	}
	if (entity.getImportopagato() == null) {
	    entity.setImportopagato(new BigDecimal(0));
	}
	if (entity.getFlagNondovuto() == null) {
	    entity.setFlagNondovuto(false);
	}
	if (entity.getFlagOnereRateizzato() == null) {
	    entity.setFlagOnereRateizzato(false);
	}
	if (entity.getImportoInteresse() == null) {
	    entity.setImportoInteresse(new BigDecimal(0));
	}
	fixMergeEntityProperties(entity);
	// controllo l'inserimento di nrDocumento
	Integer codiceCausaleOnere = (Integer) EntityUtils.getNestedProperty(entity, "tipicausalioneri.id.codice");
	if (StringUtils.isBlank(entity.getNrDocumento())) {
	    if (codiceCausaleOnere != null) {
		Tipicausalioneri tco = entity.getTipicausalioneri();
		if (tco != null) {
		    if (BooleanUtils.isTrue(tco.getPagamentiregulus())) {
			String nrDocumento = "";
			// se c'è già una riga negli oneri dell'istanza con quella causale, allora
			// NR_DOCUMENTO è
			// identico
			List<Istanzeoneri> istoneris = this.findByIstanzaAndTipicausalioneri(entity.getIstanza(), entity.getTipicausalioneri());
			for (Istanzeoneri istanzeoneri : istoneris) {
			    nrDocumento = istanzeoneri.getNrDocumento();
			    if (StringUtils.isNotBlank(nrDocumento)) {
				entity.setNrDocumento(nrDocumento);
				break;
			    }
			}
			if (StringUtils.isBlank(nrDocumento)) {
			    // altrimenti lo leggo dalla configurazione del parametro NUMEROPAGAMENTO delle
			    // verticalizzazione VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO
			    if (EntityUtils.getNestedProperty(entity, "istanza.id.codice") != null) {
				Verticalizzazioniparametri vertparnrdoc = verticalizzazioniService.getVerticalizzazioniparametri(
					WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO,
					WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_NUMERODOCUMENTO);
				if (vertparnrdoc != null) {
				    nrDocumento = vertparnrdoc.getValore();
				    if (StringUtils.isNotBlank(nrDocumento)) {
					nrDocumento = nrDocumento.replaceAll("\\[FKIDTIPOCAUSALE\\]", String.valueOf(tco.getId().getCodice()));
					Istanze istanza = istanzeService.findById(new PkId(entity.getIstanza().getId().getCodice()));
					nrDocumento = nrDocumento.replaceAll("\\[CODICEISTANZA\\]", String.valueOf(istanza.getId().getCodice()));
					nrDocumento = nrDocumento.replaceAll("\\[NUMEROPROTOCOLLO\\]",
						StringUtils.defaultIfEmpty(istanza.getNumeroprotocollo(), ""));
					nrDocumento = nrDocumento.replaceAll("\\[NUMEROISTANZA\\]", istanza.getNumeroistanza());
					SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
					String dataProtocollo = "";
					if (istanza.getDataprotocollo() != null) {
					    dataProtocollo = sdf.format(istanza.getDataprotocollo());
					}
					nrDocumento = nrDocumento.replaceAll("\\[DATAPROTOCOLLO\\]", dataProtocollo);
					nrDocumento = nrDocumento.replaceAll("\\[DATA\\]", sdf.format(istanza.getData()));
					nrDocumento = nrDocumento.replaceAll("\\[SOFTWARE\\]", istanza.getSoftware().getCodice());
					nrDocumento = nrDocumento.replaceAll("\\[CODICECOMUNE\\]", istanza.getComune().getCodicecomune());
					entity.setNrDocumento(nrDocumento);
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	if (entity.getNumerorata() == null && codiceCausaleOnere != null) {
	    Integer numerorata = this.findNumeroRata(entity.getIstanza(), entity.getTipicausalioneri());
	    entity.setNumerorata(numerorata);
	}
	// Controllo se non ho impostato la data di scadenza, nel caso controllo se è
	// possibile configurarla tramite il movimento.
	// Se il movimento che associamo all'onere è stato eseguito nell'istanza
	// impostiamo come data di scadenza quella la data del
	// movimento eventualmente aumenta del numero dei giorni impostati sul tipo
	// movimento.
	if (entity.getDatascadenza() == null && EntityUtils.getNestedProperty(entity.getTipomovimento(), "id.tipomovimento") != null) {
	    // Se esistono più movimenti di questo tipo per l'istanza ritorna il più
	    // recente.
	    Movimenti movimento = movimentiService.findMovimentiByTipoMovimento(entity.getIstanza().getId().getCodice(),
		    entity.getTipomovimento().getId().getTipomovimento());
	    Calendar calendar = new GregorianCalendar();
	    if (EntityUtils.getNestedProperty(movimento, "id.codice") != null && movimento.getData() != null) {
		Date date = movimento.getData();
		calendar.setTime(date);
		Tipimovimentooneri tipimovimentooneri = tipimovimentooneriService.findByTipomovimentoAndCausaleOnere(movimento.getTipomovimento(),
			entity.getTipicausalioneri());
		// Il metodo se non trova niete ritorna null
		if (tipimovimentooneri != null) {
		    calendar.add(Calendar.DATE, tipimovimentooneri.getGgscadenza());
		}
		Date pastDate = calendar.getTime();
		entity.setDatascadenza(pastDate);
	    }
	}
    }

    protected void fixMergeEntityProperties(Istanzeoneri entity) {

	Amministrazioni amministrazione = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazione);
	Inventarioprocedimenti endo = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(), PkId.class, "id.codice");
	entity.setInventarioprocedimenti(endo);
	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanza);
	Responsabili responsabile = responsabiliService.bindDomainObject(entity.getResponsabile(), PkId.class, "id.codice");
	entity.setResponsabile(responsabile);
	Tipicausalioneri tipicausalioneri = tipicausalioneriService.bindDomainObject(entity.getTipicausalioneri(), PkId.class, "id.codice");
	entity.setTipicausalioneri(tipicausalioneri);
	Tipimodalitapagamento tipimodalitapagamento = tipimodalitapagamentoService.bindDomainObject(entity.getTipimodalitapagamento(), PkId.class,
		"id.codice");
	entity.setTipimodalitapagamento(tipimodalitapagamento);
	Tipimovimento tipimovimento = tipiMovimentoService.bindDomainObject(entity.getTipomovimento(), TipimovimentoId.class, "id.tipomovimento");
	entity.setTipomovimento(tipimovimento);
    }

    @Override
    public List<Istanzeoneri> getDebtSituationIstanzeOneri(String codiceFiscale, Date DATAINIZIO, Date DATAFINE, String annoDocumento,
	    String codiceTributo) {

	return istanzeoneriDAO.getDebtSituationIstanzeOneri(codiceFiscale, DATAINIZIO, DATAFINE, annoDocumento, codiceTributo);
    }

    @Override
    public Integer getNumeroRateInScadenza(Tipicausalioneri tipicausalioneri) {

	return istanzeoneriDAO.getNumeroRateInScadenza(tipicausalioneri);
    }

    @Override
    public List<Istanzeoneri> getBillDetailsIstanzeOneri(String nrDocumento, String codicefiscale, String annoDocumento, String codiceTributo) {

	return istanzeoneriDAO.getBillDetailsIstanzeOneri(nrDocumento, codicefiscale, annoDocumento, codiceTributo);
    }

    @Override
    public Istanze getIstanzeByNrDocumento(String nrDocumento) {

	return istanzeoneriDAO.getIstanzeByNrDocumento(nrDocumento);
    }

    @Override
    public Istanzeoneri getOneriByNrDocRata(String nrDocumento, Short nrRata) {

	return istanzeoneriDAO.getOneriByNrDocRata(nrDocumento, nrRata);
    }

    @Override
    public List<Istanzeoneri> getOnereBollo(Integer codiceCausaleBollo, Istanze istanza) {

	return istanzeoneriDAO.getOnereBollo(codiceCausaleBollo, istanza);
    }

    @Override
    public Istanzeoneri getOnereBolloByOnere(Integer codiceCausale, Istanze istanza) {

	return istanzeoneriDAO.getOnereBolloByOnere(codiceCausale, istanza);
    }

    @Override
    public Integer findNumeroRata(Istanze istanza, Tipicausalioneri tipicausalioneri) {

	int numerorata = 1;
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.equals("tipicausalioneriId", tipicausalioneri.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("numerorata"));
	List<Istanzeoneri> istanzeoneris = istanzeoneriDAO.findByFilterTable(ft);
	for (Istanzeoneri istanzeoneri : istanzeoneris) {
	    if (istanzeoneri.getNumerorata() != null) {
		numerorata = istanzeoneri.getNumerorata().intValue();
		numerorata++;
		break;
	    }
	}
	return numerorata;
    }

    @Override
    public void inserisciOnereDaMovimento(Movimenti movimento) {

	// Se il tipo movimento ha degli oneri configurati e ricavo il tipocausaleonere.
	// Per ogni tipo causale se non ci sono record per [istanze oneri dove idcomune,
	// codice istanza,
	// tipicausalioneri ovvero L'inserimento avviene solamente se l'onere non è
	// stato inserito precedentemente per
	// quel tipocausaleonere.
	// Come data Viene messa la data del movimento
	// Il numero documento viene calcolato ne dataintegration
	// "(CODICEINVENTARIO,CODICEISTANZA, PREZZO,
	// FLENTRATAUSCITA,DATA,CODICEUTENTE,FLRIBASSO," & _
	// "PERCRIBASSO,IDCOMUNE,ID,PREZZOISTRUTTORIA,FKIDTIPOCAUSALE,NUMERORATA,NR_DOCUMENTO
	// " & _
	// ")
	// (" &CodiceInventario & "," & CodiceIstanza & "," & "0,1," & _
	// "to_date('" & Data.ToStringIT & "','dd/mm/yyyy'),0,0,100,'" & IdComune & "',"
	// & _
	// maxId & ",0," & lrs("FK_COID") & ",1,'" & nrDocumento & "')"
	// Tipimovimento tm =
	// tipiMovimentoService.findById(movimento.getTipomovimento().getId());
	List<Tipimovimentooneri> tmos = tipimovimentooneriService.findByTipimovimento(movimento.getTipomovimento().getId().getTipomovimento());
	for (Tipimovimentooneri tipimovimentooneri : tmos) {
	    Integer codicecomportamento = tipimovimentooneri.getId().getCodicecomportamento();
	    if (null != codicecomportamento) {
		if (codicecomportamento.intValue() == WebConstants.ONERI_COMPORTAMENTO_INSERISCE_ONERE) {
		    Tipicausalioneri tco = tipimovimentooneri.getTipicausalioneri();
		    Tipicausalioneri tc = tipicausalioneriService.findById(new PkId(tco.getId().getCodice()));
		    boolean onereDisabilitato = tc.getCoDisabilitato() == null ? false : tc.getCoDisabilitato().booleanValue();
		    if (!onereDisabilitato) {
			List<Istanzeoneri> oneripresenti = this.findByIstanzaAndTipicausalioneri(movimento.getIstanza(), tco);
			if (oneripresenti.size() == 0) {
			    Istanzeoneri istanzeoneri = new Istanzeoneri();
			    istanzeoneri.setInventarioprocedimenti(movimento.getEndoprocedimento());
			    istanzeoneri.setIstanza(movimento.getIstanza());
			    istanzeoneri.setPrezzo(BigDecimal.ZERO);
			    istanzeoneri.setFlentratauscita(Boolean.TRUE);
			    istanzeoneri.setData(movimento.getData());
			    istanzeoneri.setFlribasso(Boolean.FALSE);
			    istanzeoneri.setPercribasso(100);
			    istanzeoneri.setPrezzoistruttoria(BigDecimal.ZERO);
			    istanzeoneri.setTipicausalioneri(tco);
			    istanzeoneri.setNumerorata(1);
			    this.insert(istanzeoneri);
			}
		    }
		}
	    }
	}
    }

    public List<Istanzeoneri> findByIstanzaAndTipicausalioneri(Istanze istanza, Tipicausalioneri tipicausalioneri) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.equals("tipicausalioneriId", tipicausalioneri.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	return istanzeoneriDAO.findByFilterTable(ft);
    }

    @Override
    public List<Istanzeoneri> findByIstanzaAndCausale(Integer codiceIstanza, Integer codiceCausale) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceIstanza e' nullo");
	}
	if (codiceCausale == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceCausale e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	istanza.addFilterField(FilterUtils.equals("tipicausalioneriId", codiceCausale, Integer.class));
	filterTable.addRestriction(istanza);
	filterTable.addOrder(FilterUtils.orderAsc("data"));
	filterTable.addOrder(
		FilterUtils.orderAsc("datascadenza", FunctionsEnum.NVL_FUNCTION, "'01/01/0001'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	filterTable.addOrder(FilterUtils.orderAsc("id.codice"));
	return istanzeoneriDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Istanzeoneri> findOneriNonPagatiESenzaPosizioniDebitoriePerCausali(Integer codiceIstanza, List<Integer> codiciCausali) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceIstanza e' nullo");
	}
	if (codiciCausali.isEmpty()) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceCausale e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filtro = new FilterRestriction();
	filtro.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	filtro.addFilterField(FilterUtils.in("id.codice", codiciCausali.toArray(), "tipicausalioneri", Integer.class));
	filtro.addFilterField(FilterUtils.isNull("datapagamento"));
	filtro.addFilterField(FilterUtils.isEmpty("bollGestIstanzeoneris"));
	filtro.addFilterField(FilterUtils.isEmpty("istoneriDettPosizioni"));
	filterTable.addRestriction(filtro);
	filterTable.addOrder(FilterUtils.orderAsc("numerorata"));
	return istanzeoneriDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Istanzeoneri> findByIstanzaCausaleRata(Integer codiceIstanza, Integer codiceCausale, Integer numeroRata) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceIstanza e' nullo");
	}
	if (codiceCausale == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceCausale e' nullo");
	}
	if (numeroRata == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro numeroRata e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	istanza.addFilterField(FilterUtils.equals("tipicausalioneriId", codiceCausale, Integer.class));
	istanza.addFilterField(FilterUtils.equals("numerorata", numeroRata, Integer.class));
	filterTable.addRestriction(istanza);
	filterTable.addOrder(FilterUtils.orderAsc("data"));
	filterTable.addOrder(
		FilterUtils.orderAsc("datascadenza", FunctionsEnum.NVL_FUNCTION, "'01/01/0001'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	filterTable.addOrder(FilterUtils.orderAsc("id.codice"));
	return istanzeoneriDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Istanzeoneri> findByIstanzaAndEndoAndCausale(Integer codiceIstanza, Integer codiceInventario, Integer codiceCausale) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceIstanza e' nullo");
	}
	if (codiceInventario == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceInventario e' nullo");
	}
	if (codiceCausale == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceCausale e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	istanza.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceInventario, Integer.class));
	istanza.addFilterField(FilterUtils.equals("tipicausalioneriId", codiceCausale, Integer.class));
	filterTable.addRestriction(istanza);
	filterTable.addOrder(FilterUtils.orderAsc("data"));
	filterTable.addOrder(
		FilterUtils.orderAsc("datascadenza", FunctionsEnum.NVL_FUNCTION, "'01/01/0001'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	filterTable.addOrder(FilterUtils.orderAsc("id.codice"));
	return istanzeoneriDAO.findByFilterTable(filterTable);
    }

    @Override
    public void richiedePagamentoOnereDaMovimento(Movimenti movimento) {

	// Cerca la causale onere da tipimovimentooneri where codice comportamento = 2 e
	// tipo movimento
	//
	// Per ogni causale onere cerca tutti gli oneri inseriti che abbiano quella
	// causale e aggiorna la data_pagamento
	// alla data del movimento
	// Tipimovimento tm =
	// tipiMovimentoService.findById(movimento.getTipomovimento().getId());
	List<Tipimovimentooneri> tmos = tipimovimentooneriService.findByTipimovimento(movimento.getTipomovimento().getId().getTipomovimento());
	for (Tipimovimentooneri tipimovimentooneri : tmos) {
	    Integer codicecomportamento = tipimovimentooneri.getId().getCodicecomportamento();
	    if (null != codicecomportamento) {
		if (codicecomportamento.intValue() == WebConstants.ONERI_COMPORTAMENTO_RICHIEDE_PAGAMENTO) {
		    Tipicausalioneri tco = tipimovimentooneri.getTipicausalioneri();
		    List<Istanzeoneri> oneripresenti = this.findByIstanzaAndTipicausalioneri(movimento.getIstanza(), tco);
		    for (Istanzeoneri istanzeoneri : oneripresenti) {
			if (istanzeoneri.getDatapagamento() == null) {
			    istanzeoneri.setDatapagamento(movimento.getData());
			    this.update(istanzeoneri);
			}
		    }
		}
	    }
	}
    }

    @Override
    public void spostaImportoOneriDaMovimento(Movimenti movimento) {

	//
	// Cerca la causale onere da tipimovimentooneri where codice comportamento = 4 e
	// tipo movimento.
	// Per ogni causale cerca gli oneri che anno quella causale, prezzo > 0,
	// prezzoistruttoria nullo,
	// flentratauscita=1, datapagamento nulla. Se sono stati trovati questi record
	// li aggiorna a prezzoistruttoria =
	// prezzo
	// Tipimovimento tm =
	// tipiMovimentoService.findById(movimento.getTipomovimento().getId());
	List<Tipimovimentooneri> tmos = tipimovimentooneriService.findByTipimovimento(movimento.getTipomovimento().getId().getTipomovimento());
	for (Tipimovimentooneri tipimovimentooneri : tmos) {
	    Integer codicecomportamento = tipimovimentooneri.getId().getCodicecomportamento();
	    if (null != codicecomportamento) {
		if (codicecomportamento.intValue() == WebConstants.ONERI_COMPORTAMENTO_SPOSTA_IMPORTO1_SU_IMPORTO2) {
		    Tipicausalioneri tco = tipimovimentooneri.getTipicausalioneri();
		    List<Istanzeoneri> oneripresenti = this.findByIstanzaAndTipicausalioneri(movimento.getIstanza(), tco);
		    for (Istanzeoneri isto : oneripresenti) {
			BigDecimal prezzo = (isto.getPrezzo() == null) ? BigDecimal.ZERO : isto.getPrezzo();
			// BigDecimal prezzoistruttoria = (isto.getPrezzoistruttoria() == null) ?
			// BigDecimal.ZERO : isto.getPrezzoistruttoria();
			if (BooleanUtils.isTrue(isto.getFlentratauscita()) && (isto.getDatapagamento() == null)
				&& (prezzo.compareTo(BigDecimal.ZERO) == 1) && (isto.getPrezzoistruttoria() == null)) {
			    // flentratauscita = 1
			    // datapagamento ==null
			    // prezzo>0
			    // prezzoistruttoria==0
			    isto.setPrezzoistruttoria(isto.getPrezzo());
			    this.update(isto);
			}
		    }
		}
	    }
	}
    }

    @Override
    public void settaScadenzeOneri(Movimenti movimento) {

	movimento = movimentiService.bindDomainObject(movimento, PkId.class, "id.codice");
	settaScadenzeOneriPerMovimento(movimento);
	// EFFETTUARE LA STESSA OPERAZIONE SUI CONTROMOVIMENTI EFFETTUATI
	// RICERCARE I CONTROMOVIMENTI ED ESEGUIRE PER OGNI MOVIMENTO L'OPERAZIONE
	List<Movimenti> contromovimenti = movimentiService.findContromovimentiEffettuati(movimento);
	for (Movimenti movimenti : contromovimenti) {
	    settaScadenzeOneriPerMovimento(movimenti);
	}
    }

    /**
     * @param movimento
     * @param dataMovimento
     */
    private void settaScadenzeOneriPerMovimento(Movimenti movimento) {

	Date dataMovimento = movimento.getData();
	if (dataMovimento != null) {
	    List<Tipimovimentooneri> tipimovimentooneris = tipimovimentooneriService
		    .findByTipimovimento(movimento.getTipomovimento().getId().getTipomovimento());
	    for (Tipimovimentooneri tipimovimentooneri : tipimovimentooneris) {
		if (EntityUtils.getNestedProperty(tipimovimentooneri.getOnericomportamento(), "codicecomportamento") != null) {
		    if (tipimovimentooneri.getOnericomportamento().getCodicecomportamento().intValue() == 1) {
			int ngg = tipimovimentooneri.getGgscadenza() == null ? 0 : tipimovimentooneri.getGgscadenza().intValue();
			Integer causaleOnere = tipimovimentooneri.getTipicausalioneri().getId().getCodice();
			Date nuovaScadenza = Utilities.addDays(dataMovimento, ngg);
			FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
			FilterRestriction fr = new FilterRestriction();
			fr.addFilterField(FilterUtils.equals("istanzaId", movimento.getIstanza().getId().getCodice(), Integer.class));
			fr.addFilterField(FilterUtils.equals("tipicausalioneriId", causaleOnere, Integer.class));
			fr.addFilterField(FilterUtils.isNull("datascadenza"));
			ft.addRestriction(fr);
			FilterRestriction orTipomovimento = new FilterRestriction();
			orTipomovimento.setAndOrRestriction(AndOrRestriction.OR);
			orTipomovimento.addFilterField(
				FilterUtils.equals("tipomovimentoId", movimento.getTipomovimento().getId().getTipomovimento(), String.class));
			orTipomovimento.addFilterField(FilterUtils.isNull("tipomovimentoId"));
			ft.addRestriction(orTipomovimento);
			List<Istanzeoneri> list = istanzeoneriDAO.findByFilterTable(ft);
			for (Istanzeoneri istanzeoneri : list) {
			    boolean update = true;
			    if (EntityUtils.getNestedProperty(istanzeoneri.getTipomovimento(), "id.tipomovimento") == null) {
				// DEVO CONTROLLARE CHE ANCHE LA DATA SIA NULLA
				if (istanzeoneri.getDatascadenza() != null) {
				    update = false;
				} else {
				    istanzeoneri.setTipomovimento(movimento.getTipomovimento());
				}
			    }
			    if (update) {
				istanzeoneri.setDatascadenza(nuovaScadenza);
				this.update(istanzeoneri);
			    }
			}
		    }
		}
	    }
	}
    }

    @Override
    public List<Istanzeoneri> findByIstanzaAndEndo(Istanze istanza, Inventarioprocedimenti inventarioprocedimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("istanza", istanza, Istanze.class));
	criterio.addFilterField(FilterUtils.equals("inventarioprocedimenti", inventarioprocedimento, Inventarioprocedimenti.class));
	ft.addRestriction(criterio);
	List<Istanzeoneri> list = istanzeoneriDAO.findByFilterTable(ft);
	return list;
    }

    // @Override
    // public List<Istanzeoneri> findByIstanza(Istanze istanza) {
    //
    // FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
    // FilterRestriction criterio = new FilterRestriction();
    // criterio.addFilterField(FilterUtils.equals("istanza", istanza,
    // Istanze.class));
    // ft.addOrder(FilterUtils.orderAsc("rcoDescr",
    // "tipicausalioneri.raggruppamentocausalioneri"));
    // ft.addRestriction(criterio);
    // List<Istanzeoneri> list = istanzeoneriDAO.findByFilterTable(ft);
    // return list;
    // }
    @Override
    public List<Istanzeoneri> findByIstanzaAndRaggruppamentiAndData(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    boolean isDateNullAsGruop, Date data) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("istanza", istanza, Istanze.class));
	if (raggruppamentocausalioneri != null) {
	    criterio.addFilterField(FilterUtils.equals("raggruppamentocausalioneri", raggruppamentocausalioneri, "tipicausalioneri",
		    Raggruppamentocausalioneri.class));
	} else {// Caso in cui non ha un raggruppamento (non è un campo obbligario per la
		// causale dell'onere scelto)
	    criterio.addFilterField(FilterUtils.isNull("raggruppamentocausalioneri.id.codice", "tipicausalioneri"));
	}
	// se isDateNullAsGruop allora considero data null come un gruppo e filtro per
	// data nulla
	if (isDateNullAsGruop && data == null) {
	    criterio.addFilterField(FilterUtils.isNull("datapagamento"));
	} else {// filtro per data solo se il parametro passato è diverso da null
	    if (data != null) {
		criterio.addFilterField(FilterUtils.equals("datapagamento", data, Data.class));
	    }
	}
	ft.addOrder(FilterUtils.orderAsc("tipicausalioneri"));
	ft.addOrder(FilterUtils.orderAsc("datapagamento"));
	ft.addRestriction(criterio);
	List<Istanzeoneri> list = istanzeoneriDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public void inserisciOneriDaIstanzelavoriTs(Istanze istanza, Responsabili responsabile) {

	List<IstanzelavoriD> istanzelavoriDs = new ArrayList<IstanzelavoriD>();
	Set<IstanzelavoriT> istanzelavoriTs = istanza.getIstanzelavoriTs();
	for (IstanzelavoriT istanzelavoriT : istanzelavoriTs) {
	    for (IstanzelavoriD istanzelavoriD : istanzelavoriT.getIstanzelavoriDs()) {
		if (isInsertFromIstanzelavoriTAllowed(istanza, istanzelavoriD.getTipicausalioneri())) {
		    istanzelavoriDs.add(istanzelavoriD);
		}
	    }
	}
	for (IstanzelavoriD istanzelavoriD : istanzelavoriDs) {
	    boolean onereDisabilitato = false;
	    Tipicausalioneri tc = null;
	    if (EntityUtils.getNestedProperty(istanzelavoriD, "tipicausalioneri.id.codice") != null) {
		tc = tipicausalioneriService.findById(new PkId(istanzelavoriD.getTipicausalioneri().getId().getCodice()));
		onereDisabilitato = tc.getCoDisabilitato() == null ? false : tc.getCoDisabilitato().booleanValue();
	    }
	    if (!onereDisabilitato) {
		if (tc != null) {
		    Istanzeoneri istanzeoneri = new Istanzeoneri();
		    istanzeoneri.setIstanza(istanza);
		    if (istanzelavoriD.getTotale() != null) {
			istanzeoneri.setPrezzo(istanzelavoriD.getTotale());
		    }
		    istanzeoneri.setTipicausalioneri(tc);
		    istanzeoneri.setFlentratauscita(Boolean.TRUE);
		    istanzeoneri.setData(new Date());
		    istanzeoneri.setResponsabile(responsabile);
		    istanzeoneri.setFlribasso(Boolean.FALSE);
		    istanzeoneri.setPercribasso(0);
		    this.insert(istanzeoneri);
		}
	    }
	}
    }

    private boolean isInsertFromIstanzelavoriTAllowed(Istanze istanza, Tipicausalioneri tipicausalioneri) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Istanzeoneri> istanzeoneris = this.findByIstanzaAndTipicausalioneri(istanza, tipicausalioneri);
	if (istanzeoneris != null && !istanzeoneris.isEmpty()) {
	    _ivs.add(new InvalidValue(
		    getMessageFromBundle(WebConstants.ALERT_ISTANZEONERI_PRESENTE, new Object[] { tipicausalioneri.getCoDescrizione() }), null, "",
		    "ISTANZEONERI", null));
	    insert = false;
	}
	if (!insert) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    public int countByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("countByIstanza: il parametro codiceIstanza e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	filterTable.addRestriction(istanza);
	int count = istanzeoneriDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public int countByTipicausalioneri(int codicetipocausalioneri) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("tipicausalioneriId", codicetipocausalioneri, Integer.class));
	filterTable.addRestriction(istanza);
	return istanzeoneriDAO.countRecord(filterTable);
    }

    @Override
    public BigDecimal sumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    TipologiaOnere tipologiaOnere, Boolean isEntrata) {

	return istanzeoneriDAO.sumOneriCausaliByIstanzaAndRaggruppamento(istanza, raggruppamentocausalioneri, tipologiaOnere, isEntrata);
    }

    @Override
    public BigDecimal sumRibassiOneriByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri) {

	return istanzeoneriDAO.sumRibassiOneriByIstanzaAndRaggruppamento(istanza, raggruppamentocausalioneri);
    }

    @Override
    public BigDecimal sumOneriCausaliByIstanza(Istanze istanza, TipologiaOnere tipologiaOnere, Boolean isEntrata) {

	return istanzeoneriDAO.sumOneriCausaliByIstanza(istanza, tipologiaOnere, isEntrata);
    }

    @Override
    public BigDecimal sumRibassiOneriByIstanza(Istanze istanza) {

	return istanzeoneriDAO.sumRibassiOneriByIstanza(istanza);
    }

    @Override
    public BigDecimal sumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    Date datapagamento, TipologiaOnere tipologiaOnere) {

	return istanzeoneriDAO.sumOneriCausaliByIstanzaAndRaggruppamento(istanza, raggruppamentocausalioneri, datapagamento, tipologiaOnere);
    }

    @Override
    public Map<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> findDettaglioOneri(Istanze istanza) {

	//1. Recupero la lista di istanzeoneri
	TreeSet<Istanzeoneri> oneri = new TreeSet<Istanzeoneri>(new IstanzeoneriComparator());
	oneri.addAll(istanza.getIstanzeoneris());
	TreeMap<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> mappa = new TreeMap<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>>(
		new ChiavePerCausaleDatPagamentoDataScadenzaTipologiaComparator());
	for (Istanzeoneri onere : oneri) {
	    //2. Creo la chiave della mappa
	    String raggruppamento = (onere.getTipicausalioneri().getRaggruppamentocausalioneri() != null
		    ? onere.getTipicausalioneri().getRaggruppamentocausalioneri().getRcoDescr()
		    : null);
	    ChiavePerCausaleDatPagamentoDataScadenzaTipologia chiave = ChiavePerCausaleDatPagamentoDataScadenzaTipologia
		    .fromRaggruppamento(raggruppamento);
	    //3. Verifico l'esistenza in mappa
	    if (mappa.get(chiave) == null) {
		mappa.put(chiave, new ArrayList<Istanzeoneri>());
	    }
	    //4. Aggiungo l'onere alla lista della chiave in mappa
	    mappa.get(chiave).add(onere);
	}
	return mappa;
    }

    @Override
    public Map<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> findRaggruppamentoOneri(Istanze istanza) {

	//1. Recupero la lista di istanzeoneri
	TreeSet<Istanzeoneri> oneri = new TreeSet<Istanzeoneri>(new IstanzeoneriComparator());
	oneri.addAll(istanza.getIstanzeoneris());
	TreeMap<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> mappa = new TreeMap<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>>(
		new ChiavePerCausaleDatPagamentoDataScadenzaTipologiaComparator());
	for (Istanzeoneri onere : oneri) {
	    //2. Nella visualizzazione raggruppata non devono essere inserite i record di istanzeoneri con sole posizioni
	    //debitorie annullate
	    if (onere.isPresentiSoloPosizioniDebitorieAnnullate()) {
		continue;
	    }
	    //2. Escludo gli oneri in uscita in quanto nella visualizzazione raggruppata si vedono
	    //soltanto quelli in entrata
	    if (EnumTipologiaOnereType.fromDettaglio(onere.getFlentratauscita(), onere.isPagato()).equals(EnumTipologiaOnereType.USCITA)
		    || EnumTipologiaOnereType.fromDettaglio(onere.getFlentratauscita(), onere.isPagato()).equals(EnumTipologiaOnereType.RIVERSATO)) {
		continue;
	    }
	    //2. Creo la chiave della mappa
	    ChiavePerCausaleDatPagamentoDataScadenzaTipologia chiave = ChiavePerCausaleDatPagamentoDataScadenzaTipologia.fromIstanzeOneri(onere);
	    //3. Verifico l'esistenza in mappa
	    if (mappa.get(chiave) == null) {
		mappa.put(chiave, new ArrayList<Istanzeoneri>());
	    }
	    //4. Aggiungo l'onere alla lista della chiave in mappa
	    mappa.get(chiave).add(onere);
	}
	return mappa;
    }

    private boolean isInsertAllowed(Istanzeoneri entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// se l acausale onere configurata richiede endo collegato, all'inserimento
	// dell'onere il campo inventario procedimento
	// diventa obligatorio.
	if (entity.getTipicausalioneri().getCoSerichiedeendo() != null && entity.getTipicausalioneri().getCoSerichiedeendo() == true) {
	    if (EntityUtils.getNestedProperty(entity.getInventarioprocedimenti(), "id.codice") == null) {
		_ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", entity.getClass(), "inventarioprocedimenti",
			entity.getInventarioprocedimenti(), entity));
		insert = false;
	    }
	}
	if (!insert) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    public boolean isExistIstanzeOnereWithEndo(Istanze istanze) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("istanza", istanze, Istanze.class));
	filterRestriction.addFilterField(FilterUtils.isNotNull("inventarioprocedimentiId"));
	filterTable.addRestriction(filterRestriction);
	int count = istanzeoneriDAO.countRecord(filterTable);
	if (count > 0) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean isExistIstanzeOnereWithUscita(Istanze istanza) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("istanza", istanza, Istanze.class));
	filterRestriction.addFilterField(FilterUtils.equals("flentratauscita", false, Boolean.class));
	filterTable.addRestriction(filterRestriction);
	int count = istanzeoneriDAO.countRecord(filterTable);
	if (count > 0) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public List<OneriEntrateUsciteAmministrazioneHelper> findOneriEntrateUsciteForAmministrazione(Istanze istanze) {

	// Ritorna tutte el amministrazione legate agli oneri del'istanza in esame
	List<Amministrazioni> list = this.findAmministrazioniInIstanzeOneri(istanze);
	List<OneriEntrateUsciteAmministrazioneHelper> risultato = new ArrayList<OneriEntrateUsciteAmministrazioneHelper>();
	OneriEntrateUsciteAmministrazioneHelper helper = null;
	// Clico le amministrazioni tovate e calcolo gli oneri in uscita e in entrate
	for (Amministrazioni amministrazioni : list) {
	    BigDecimal sommaEntrare = new BigDecimal(0);
	    BigDecimal sommaUscite = new BigDecimal(0);
	    BigDecimal sommaRibassiUscite = new BigDecimal(0);
	    sommaRibassiUscite = this.sumRibassiOneriByIstanzaAndAmministazioni(istanze, amministrazioni);
	    sommaEntrare = this.sumOneriCausaliByIstanzaAndAmministrazione(istanze, amministrazioni, TipologiaOnere.CAUSALE_ONERE, true);
	    sommaUscite = this.sumOneriCausaliByIstanzaAndAmministrazione(istanze, amministrazioni, TipologiaOnere.CAUSALE_ONERE, false);
	    // Alla somma delle uscite tolgo la somma dei ribassi per avere il vero importo
	    // da versare all'ammistrazione
	    sommaUscite = sommaUscite.subtract(sommaRibassiUscite);
	    BigDecimal disavanzo = new BigDecimal(0);
	    disavanzo = sommaUscite.subtract(sommaEntrare);
	    // Se il disavanzo (uscite - entrate)>0 allora crea un oggetto
	    // OneriEntrateUsciteAmministrazioneHelper
	    if (disavanzo.compareTo(new BigDecimal(0)) == 1) {
		helper = new OneriEntrateUsciteAmministrazioneHelper();
		helper.setAmministrazione(amministrazioni);
		helper.setTotaleEntratePerAmministrazione(sommaEntrare);
		helper.setTotaleUscitePerAmministrazione(sommaUscite);
		helper.setDisavanzo(disavanzo);
		risultato.add(helper);
	    }
	}
	return risultato;
    }

    @Override
    public List<Amministrazioni> findAmministrazioniInIstanzeOneri(Istanze istanza) {

	return istanzeoneriDAO.findAmministrazioniInIstanzeOneri(istanza);
    }

    @Override
    public BigDecimal sumOneriCausaliByIstanzaAndAmministrazione(Istanze istanza, Amministrazioni amministrazioni, TipologiaOnere tipologiaOnere,
	    Boolean isEntrata) {

	return istanzeoneriDAO.sumOneriCausaliByIstanzaAndAmministrazione(istanza, amministrazioni, tipologiaOnere, isEntrata);
    }

    @Override
    public String update(Istanzeoneri istanzeoneri, String valore, String campo) {

	if (campo.equals("prezzo")) {
	    if (StringUtils.isNotBlank(valore)) {
		if (!valore.contains(",")) {
		    istanzeoneri.setPrezzo(new BigDecimal(valore));
		} else {
		    valore = valore.replaceFirst(",", ".");
		    istanzeoneri.setPrezzo(new BigDecimal(valore));
		}
	    } else {
		istanzeoneri.setPrezzo(null);
	    }
	} else if (campo.equals("prezzoistruttoria")) {
	    if (StringUtils.isNotBlank(valore)) {
		if (!valore.contains(",")) {
		    istanzeoneri.setPrezzoistruttoria(new BigDecimal(valore));
		} else {
		    valore = valore.replaceFirst(",", ".");
		    istanzeoneri.setPrezzoistruttoria(new BigDecimal(valore));
		}
	    } else {
		istanzeoneri.setPrezzoistruttoria(null);
	    }
	} else if (campo.equals("importopagato")) {
	    if (StringUtils.isNotBlank(valore)) {
		if (!valore.contains(",")) {
		    istanzeoneri.setImportopagato(new BigDecimal(valore));
		} else {
		    valore = valore.replaceFirst(",", ".");
		    istanzeoneri.setImportopagato(new BigDecimal(valore));
		}
	    } else {
		istanzeoneri.setImportopagato(null);
	    }
	} else if (campo.equals("datapagamento")) {
	    Date date = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    try {
		if (StringUtils.isNotBlank(valore)) {
		    date = dateFormat.parse(valore);
		} else {
		    date = null;
		}
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	    istanzeoneri.setDatapagamento(date);
	} else if (campo.equals("datascadenza")) {
	    Date date = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    try {
		if (StringUtils.isNotBlank(valore)) {
		    date = dateFormat.parse(valore);
		} else {
		    date = null;
		}
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	    istanzeoneri.setDatascadenza(date);
	} else {
	    return "Attenzione: Il metodo IstanzeoneriService.update(Istanzeoneri istanzeoneri, String valore, String campo), non riconosce il contesto" +
		   campo;
	}
	istanzeoneriDAO.update(istanzeoneri);
	return "OK";
    }

    @Override
    public void updateRiferimentiPagamento(Istanzeoneri istanzeoneri) {

	Istanzeoneri istanzeoneriDB = istanzeoneriDAO.findById(new PkId(istanzeoneri.getId().getCodice()));
	istanzeoneriDB.setDocriferimento(istanzeoneri.getDocriferimento());
	istanzeoneriDB.setNote(istanzeoneri.getNote());
	Tipimodalitapagamento tipimodalitapagamento = tipimodalitapagamentoService
		.findById(new PkId(istanzeoneri.getTipimodalitapagamento().getId().getCodice()));
	istanzeoneriDB.setTipimodalitapagamento(tipimodalitapagamento);
	this.update(istanzeoneriDB);
    }

    @Override
    public BigDecimal sumRibassiOneriByIstanzaAndAmministazioni(Istanze istanza, Amministrazioni amministrazioni) {

	return istanzeoneriDAO.sumRibassiOneriByIstanzaAndAmministazioni(istanza, amministrazioni);
    }

    @Override
    public TipoAccessoEnum checkAccessoIstanzaOneri(Istanze istanza, Responsabili responsabile) {

	// Controlla se l'utente ha accesso agli oneri indipendentemente se sono
	// bloccati o no
	Responsabili userlogged = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	boolean isAbilitaBloccaOneri = userlogged.getFlagBloccaOneri() == null ? false : userlogged.getFlagBloccaOneri().booleanValue();
	if (isAbilitaBloccaOneri) {
	    return TipoAccessoEnum.CONSENTITO;
	} else { // Controlla se gli oneri sono stati bloccati per quell'istanza.
	    Chiusureistanza ci = chiusureistanzaService.findById(new PkId(istanza.getId().getCodice()));
	    if (ci != null && ci.getOneri() != null && ci.getOneri().equals(true)) {
		return TipoAccessoEnum.NON_CONSENTITO;
	    } else {
		return TipoAccessoEnum.CONSENTITO;
	    }
	}
    }

    @Override
    public List<Istanzeoneri> findByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException(getClass() + "#findByIstanza: il parametro istanza non può essere nullo");
	}
	FilterTable ft = getFilterTableByIstanza(codiceIstanza);
	return istanzeoneriDAO.findByFilterTable(ft);
    }

    @Override
    public List<Istanzeoneri> findOneriNonPagatiByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException(getClass() + "#findByIstanza: il parametro istanza non può essere nullo");
	}
	FilterTable ft = getFilterTableByIstanza(codiceIstanza);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("importopagato", BigDecimal.ZERO, BigDecimal.class));
	fr.addFilterField(FilterUtils.isNull("importopagato"));
	ft.addRestriction(fr);
	return istanzeoneriDAO.findByFilterTable(ft);
    }

    private FilterTable getFilterTableByIstanza(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("data"));
	ft.addOrder(FilterUtils.orderAsc("datascadenza", FunctionsEnum.NVL_FUNCTION, "'01/01/0001'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return ft;
    }

    @Override
    public int countOneriWithEndoByIstanza(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.isNotNull("id.codice", "inventarioprocedimenti"));
	ft.addRestriction(fr);
	return istanzeoneriDAO.countRecord(ft);
    }

    @Override
    public int countOneriByIstanza(Integer codiceIstanza, boolean isEntrata) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("flentratauscita", new Boolean(isEntrata), Boolean.class));
	ft.addRestriction(fr);
	return istanzeoneriDAO.countRecord(ft);
    }

    @Override
    public BigDecimal sumOneriImportoVersatoByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    Boolean isEntrata) {

	return istanzeoneriDAO.sumOneriImportoVersatoByIstanzaAndRaggruppamento(istanza, raggruppamentocausalioneri, isEntrata);
    }

    @Override
    public BigDecimal sumOneriImportoVersatoByIstanza(Istanze istanza, Boolean isEntrata) {

	return istanzeoneriDAO.sumOneriImportoVersatoByIstanza(istanza, isEntrata);
    }

    @Override
    public void copiaOneriNonPagatiDaIstanzaSorgenteADestinazione(Integer codiceIstanzaSubentrata, Integer codiceIstanzaCheSubentra) {

	if (codiceIstanzaCheSubentra == null || codiceIstanzaSubentrata == null) {
	    throw new IllegalArgumentException("Parametri non valorizzati correttamente codiceIstanzaSubentrata=" + codiceIstanzaSubentrata + ", " +
					       "codiceIstanzaCheSubentra=" + codiceIstanzaCheSubentra);
	}
	if (codiceIstanzaCheSubentra.equals(codiceIstanzaSubentrata)) {
	    return;
	}
	// RECUPERARE L'ISTANZA SUBENTRATA RECUPERARE TUTTE LE RIGHE DI ISTANZE ONERI
	// SALVARE LE RIGHE DI ISTANZE ONERI CON IL RIFERIMENTO ALLA NUOVA ISTANZA
	List<Istanzeoneri> findByIstanza = this.findOneriNonPagatiByIstanza(codiceIstanzaSubentrata);
	Istanze src = istanzeService.findById(new PkId(codiceIstanzaSubentrata));
	Istanze dest = istanzeService.findById(new PkId(codiceIstanzaCheSubentra));
	// String messaggio = "\nOneri copiati dalla pratica " + src.getNumeroistanza()
	// + " in data " + Utilities.getToday(true);
	List<Integer> istanzeOneriCopiati = new ArrayList<Integer>();
	for (Istanzeoneri istanzeoneri : findByIstanza) {
	    istanzeOneriCopiati.add(istanzeoneri.getId().getCodice());
	    istanzeoneri.setIstanza(dest);
	    // istanzeoneri.setNote(StringUtils.left(StringUtils.defaultString(istanzeoneri.getNote())
	    // + messaggio, 4000));
	    istanzeoneri.aggiungiNote(new MessaggioOnereCopiatoDaIstanza(src.getNumeroistanza()));
	    this.update(istanzeoneri);
	}
	EventoOneriCopiati evento = new EventoOneriCopiati(codiceIstanzaSubentrata, codiceIstanzaCheSubentra, istanzeOneriCopiati);
	eventPublisher.publish(evento);
    }

    @Override
    public void registraPagamentoAvvenutoByIdPosizioneDebitoria(DatiPagamento datiPagamento, String cfEnteCreditore) {

	List<IstanzeOneriNodoPagamentiHelper> oneriPagati = this.findByIdPosizioneDebitoria(datiPagamento.getIdPosizioneDebitoria(), cfEnteCreditore);
	log.debug("registraPagamentoAvvenutoByIdPosizioneDebitoria oneriPagati.size() {}", oneriPagati.size());
	BigDecimal importoCalcolato = BigDecimal.ZERO;
	for (IstanzeOneriNodoPagamentiHelper ioh : oneriPagati) {
	    importoCalcolato = importoCalcolato.add(ioh.getImportoTotale());
	}
	BigDecimal importoPagato = datiPagamento.getImporto();
	if (importoPagato == null) {
	    importoPagato = BigDecimal.ZERO;
	}
	boolean importoCorretto = importoCalcolato.doubleValue() == importoPagato.doubleValue();
	log.debug("registraPagamentoAvvenutoByIdPosizioneDebitoria importoCalcolato {} è corretto? {} - datiPagamento.getImporto() {}",
		new Object[] { importoCalcolato, importoCorretto, datiPagamento.getImporto() });
	Tipimodalitapagamento modalitaPagamento = this.tipimodalitapagamentoService.findById(new PkId(datiPagamento.getIdModalitaPagamento()));
	// recupera somma dell'importo dagli oneri dell'istanza filtrato per posizione debitoria
	// se la somma è = a importo pagato allora si chiama il metodo sotto senza passare importo pagato così il DAO imposta importopagato=prezzo 
	for (IstanzeOneriNodoPagamentiHelper onere : oneriPagati) {
	    log.debug(
		    "registraPagamentoAvvenutoByIdPosizioneDebitoria: onere.getIdIstanzeOneri() {}, datiPagamento.getDataPagamento(), {}, datiPagamento.getRiferimentoPagamento() {}",
		    new Object[] { onere.getIdIstanzeOneri(), datiPagamento.getDataPagamento(), datiPagamento.getRiferimentoPagamento() });
	    if (importoCorretto) {
		this.istanzeoneriDAO.impostaOnerePagatoByIdPosizioneDebitoria(onere.getIdIstanzeOneri(), datiPagamento.getDataPagamento(),
			modalitaPagamento, datiPagamento.getRiferimentoPagamento());
	    } else {
		// TODO VERIFICARE LA POSSIBILITA' DI AGGIUNGERE UN EVENTO DELL'ISTANZA
		this.istanzeoneriDAO.impostaOnerePagatoConImportoByIdPosizioneDebitoria(onere.getIdIstanzeOneri(), datiPagamento.getDataPagamento(),
			datiPagamento.getImporto(), modalitaPagamento, datiPagamento.getRiferimentoPagamento());
	    }
	    eventPublisher.publish(new EventoPagamentoOnereRegistrato(onere.getIdIstanzeOneri()));
	}
    }

    @Override
    public List<IstanzeOneriNodoPagamentiHelper> findByIdPosizioneDebitoria(Integer idPosizioneDebitoria, String cfEnteCreditore) {

	return this.istanzeoneriDAO.findByIdPosizioneDebitoria(idPosizioneDebitoria, cfEnteCreditore);
    }

    @Override
    public Istanzeoneri findById(Integer codice) {

	return this.findById(new PkId(codice));
    }

    @Override
    public void delete(int idOnere) {

	this.delete(this.findById(idOnere));
    }

    @Override
    public void rateizzaIstanzeOneriRagguppamento(Integer codiceIstanza, Integer codiceRaggruppamento, Integer codiceTipoRateizzazione,
	    Date dataInizioInteressi, Date dataInizioRate) throws IllegalArgumentException, InvalidConfigurationException {

	// CERCA LE CAUSALI DEGLI ONERI DELL'ISTANZA PER IL CODICERAGGRUPPAMENTO
	List<Istanzeoneri> findOneriPerRaggruppamento = this.findOneriPerRaggruppamento(codiceRaggruppamento, codiceIstanza);
	String guid = UUID.randomUUID().toString();
	for (Istanzeoneri istanzeoneri : findOneriPerRaggruppamento) {
	    if (!(istanzeoneri.isCollegataABollettazione() || istanzeoneri.isPresentiPosizioniDebitorie())) {
		rateizzaIstanzeOneriInternal(codiceIstanza, istanzeoneri.getId().getCodice(), codiceTipoRateizzazione, dataInizioInteressi,
			dataInizioRate, true, guid);
	    }
	}
    }

    private List<Istanzeoneri> findOneriPerRaggruppamento(Integer codiceRaggruppamento, Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("raggruppamentocausalioneriId", codiceRaggruppamento, "tipicausalioneri", Integer.class));
	fr.addFilterField(FilterUtils.isNull("datapagamento"));
	ft.addRestriction(fr);
	return istanzeoneriDAO.findByFilterTable(ft);
    }

    @Override
    public void rateizzaIstanzeOneri(Integer codiceIstanza, Integer codiceIstanzeOneri, Integer codiceTipoRateizzazione, Date dataInizioInteressi,
	    Date dataInizioRate) throws IllegalArgumentException, InvalidConfigurationException {

	rateizzaIstanzeOneriInternal(codiceIstanza, codiceIstanzeOneri, codiceTipoRateizzazione, dataInizioInteressi, dataInizioRate, false, null);
    }

    @Override
    public boolean isRateizzato(Integer codiceIstanzaOnere) {

	Istanzeoneri istanzaonere = this.findById(codiceIstanzaOnere);
	return Boolean.TRUE.equals(istanzaonere.getFlagOnereRateizzato());
    }

    private void rateizzaIstanzeOneriInternal(Integer codiceIstanza, Integer codiceIstanzeOneri, Integer codiceTipoRateizzazione,
	    Date dataInizioInteressi, Date dataInizioRate, boolean isRaggruppamento, String guidRaggruppamento)
	    throws IllegalArgumentException, InvalidConfigurationException {

	Istanzeoneri io = this.findById(new PkId(codiceIstanzeOneri));
	if (io == null || !io.getIstanza().getId().getCodice().equals(codiceIstanza)) {
	    throw new IllegalArgumentException("Dati non validi CI=" + codiceIstanza + ", Codice Istanza oneri" + codiceIstanzeOneri);
	}
	if (io.isCollegataABollettazione() || io.isPresentiPosizioniDebitorie()) {
	    throw new IllegalArgumentException(
		    "NOn è possibile rateizzare un onere colelgato a bollettazione o con posizioni debitorie associate. Codice Istanza oneri" +
					       codiceIstanzeOneri);
	}
	Oneritipirateizzazione oneritipirateizzazione = oneritipirateizzazioneService.findById(new PkId(codiceTipoRateizzazione));
	checkRateizzazione(oneritipirateizzazione, dataInizioInteressi, dataInizioRate);
	checkRateizzazioneOnere(io);
	RateizzazioniHelper rateizzazioniHelper = new RateizzazioniHelper(oneritipirateizzazione, interessiLegaliService);
	List<ImportiRateizzati> rata = rateizzazioniHelper.rateizzaImporto(io.getPrezzo(), dataInizioRate, dataInizioInteressi,
		rateizzazioniHelper.getPeriodicitaEnum(), rateizzazioniHelper.getTipologiaRateizzazioneEnum());
	List<ImportiRateizzati> rataIstruttoria = new ArrayList<ImportiRateizzati>();
	if (io.getPrezzoistruttoria() != null && BigDecimal.ZERO.compareTo(io.getPrezzoistruttoria()) > 0) {
	    rataIstruttoria = rateizzazioniHelper.rateizzaImporto(io.getPrezzoistruttoria(), dataInizioRate, dataInizioInteressi,
		    rateizzazioniHelper.getPeriodicitaEnum(), rateizzazioniHelper.getTipologiaRateizzazioneEnum());
	}
	// verifica se su calcolo canoni è presente il  record per quell'onere e recupera la testata
	IstanzecalcolocanoniT canoneT = istanzecalcolocanoniOService.findTestataByIstanzeOneri(codiceIstanzeOneri);
	TemplateRata tr = new TemplateRata(io, guidRaggruppamento);
	// cancella istanze oneri (io)
	this.delete(codiceIstanzeOneri);
	// per ogni rata inserisce un nuovo istanze oneri
	for (int i = 0; i < rata.size(); i++) {
	    BigDecimal prezzoIstruttoria = BigDecimal.ZERO;
	    if (rataIstruttoria.size() == rata.size()) {
		prezzoIstruttoria = rataIstruttoria.get(i).getImportoRateizzato();
	    }
	    Istanzeoneri onere = newRataIstanzeonere(rata.get(i), tr, prezzoIstruttoria);
	    if (onere.getNumerorata().intValue() == 1 && oneritipirateizzazione.getSpeseRateizzazione() != null
		    && BigDecimal.ZERO.compareTo(oneritipirateizzazione.getSpeseRateizzazione()) > 0) {
		// aggiungo le spese di rateizzazione
		onere.setPrezzo(onere.getPrezzo().add(oneritipirateizzazione.getSpeseRateizzazione()));
	    }
	    this.insert(onere);
	    if (canoneT != null) {
		insertIOC(onere, canoneT.getId().getCodice());
	    }
	}
    }

    private void insertIOC(Istanzeoneri onere, Integer codiceTestataCalcoloCanone) {

	IstanzecalcolocanoniT canoneT = istanzecalcolocanoniTService.findById(new PkId(codiceTestataCalcoloCanone));
	IstanzecalcolocanoniO ioc = new IstanzecalcolocanoniO();
	ioc.setIstanzecalcolocanoniT(canoneT);
	ioc.setIstanzeoneri(onere);
	ioc.setTipicausalioneri(onere.getTipicausalioneri());
	ioc.setId(
		new IstanzecalcolocanoniOId(canoneT.getId().getCodice(), onere.getTipicausalioneri().getId().getCodice(), onere.getId().getCodice()));
	istanzecalcolocanoniOService.insert(ioc);
    }

    @Autowired
    private IstanzecalcolocanoniOService istanzecalcolocanoniOService;
    @Autowired
    private IstanzecalcolocanoniTService istanzecalcolocanoniTService;

    private Istanzeoneri newRataIstanzeonere(ImportiRateizzati importiRateizzati, TemplateRata t, BigDecimal prezzoIstruttoria) {
	// recupera istanza e altri dati da io

	// 		tipicausali oneri
	//	    private Boolean flentratauscita;
	//	    private Tipicausalioneri tipicausalioneri;
	//	    private Date data;
	//	    private String note;
	//	    private Boolean flribasso;
	//	    private Integer percribasso;
	//	    private BigDecimal prezzoistruttoria;
	//	    private String docriferimento;
	//	    private Amministrazioni amministrazioni;
	//	    private String nrDocumento;
	//	    private BigDecimal importopagato;
	//	    private Istanzeoneri istanzeoneriPadre;
	//	    private Boolean flagNondovuto;
	//	    private BigDecimal importoInteresse;
	//	    private Boolean flagOnereRateizzato == 1
	Istanzeoneri io = new Istanzeoneri();
	io.setTipicausalioneri(t.getTipicausalioneri());
	io.setAmministrazioni(t.getAmministrazioni());
	io.setInventarioprocedimenti(t.getInventarioprocedimenti());
	io.setIstanza(t.getIstanze());
	io.setData(t.getDataOnere());
	io.setFlagOnereRateizzato(t.isFlagOnereRateizzato());
	io.setNote(t.getNote());
	io.setNrDocumento(t.getNrDocumento());
	io.setNumerorata(importiRateizzati.getNumerorata());
	io.setPrezzo(importiRateizzati.getImportoRateizzato());
	io.setImportoInteresse(importiRateizzati.getImportoInteresse());
	io.setDatascadenza(importiRateizzati.getScadenza());
	io.setFlentratauscita(t.isFlagEntrataUscita());
	io.setPrezzoistruttoria(prezzoIstruttoria);
	io.setGuidRateizzazione(t.getGuidRateizzazione());
	return io;
    }

    private void checkRateizzazioneOnere(Istanzeoneri io) {

	if (StringUtils.isNotBlank(io.getGuidRateizzazione())
		&& countRateByGuidAndIstanza(io.getIstanza().getId().getCodice(), io.getGuidRateizzazione()) > 1) {
	    // se già rateizzato
	    // a meno che non sia una unica rata
	    throw new IllegalArgumentException("L'onere risulta già rateizzato");
	}
	// verificare che non sia presente una posizione debitoria associata
	// non posso rateizzare debiti con posizioni debitorie collegate, a meno che non siano annullate
	// o sia possibile annullarle (o in stato chiusura negativa o non andate a buon fine)
	isDeleteAllowed(io); // qui verifico se la posso cancellare, se non la posso cancellare non la posso rateizzare
    }

    private int countRateByGuidAndIstanza(Integer codiceIstanza, String guidRateizzazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("guidRateizzazione", guidRateizzazione, String.class));
	ft.addRestriction(fr);
	return istanzeoneriDAO.countRecord(ft);
    }

    private void checkRateizzazione(Oneritipirateizzazione oneritipirateizzazione, Date dataInizioInteressi, Date dataInizioRate) {

	if (BooleanUtils.toBoolean(oneritipirateizzazione.getFlagInteressiLegali())) {
	    if (dataInizioInteressi == null) {
		throw new IllegalArgumentException("Data inizio &egrave; nulla per un tipo di rateizzazione che ha il flag Interessi Legali settato");
	    } else {
		if (dataInizioInteressi.compareTo(dataInizioRate) > 0) {
		    throw new InvalidConfigurationException("La data iniziale per gli Interessi legali &egrave; successiva alla data finale.");
		}
	    }
	}
    }

    @Override
    public void derateizzaIstanzeOneriRagguppamento(Integer codiceIstanza, Integer codiceRaggruppamento)
	    throws IllegalArgumentException, InvalidConfigurationException {

	// TROVA LE CAUSALI PER RAGGRUPPAMENTO
	// PER OGNI CAUSALE ESEGUE LA DERATEIZZAZIONE PER CAUSALE
	List<Tipicausalioneri> causaliRaggruppate = tipicausalioneriService.findByCodiceRaggruppamento(codiceRaggruppamento);
	for (Tipicausalioneri tco : causaliRaggruppate) {
	    derateizzaIstanzeOneri(codiceIstanza, tco.getId().getCodice());
	}
    }

    @Override
    public void derateizzaIstanzeOneri(Integer codiceIstanza, Integer codiceCausaleOneri)
	    throws IllegalArgumentException, InvalidConfigurationException {

	// LA DERATEIZZAZIONE PER CAUSALE TROVA I RECORD DA INSERIRE
	List<IstanzeoneriDerateizzatoBean> list = istanzeoneriDAO.findOneriDaDerateizzare(codiceIstanza, codiceCausaleOneri);
	for (IstanzeoneriDerateizzatoBean bean : list) {
	    // TROVA I RECORD DA ELIMINARE SEGNANGO ANCHE IL RIFERIMENTO PER LA TESTATA DEL CALCOLO CANONE
	    // ELIMINA I VECCHI ONERI ED INSERISCE I NUOVI
	    this.deleteIstanzeoneriPerRateizzazione(codiceIstanza, codiceCausaleOneri, bean.getFkcanonetestata());
	    Istanzeoneri io = populateIstanzeOneriDerateizzato(bean, codiceIstanza, codiceCausaleOneri);
	    this.insert(io);
	    if (bean.getFkcanonetestata() != null) {
		insertIOC(io, bean.getFkcanonetestata());
	    }
	}
    }

    private Istanzeoneri populateIstanzeOneriDerateizzato(IstanzeoneriDerateizzatoBean bean, Integer codiceIstanza, Integer codiceCausaleOneri) {

	Istanzeoneri io = new Istanzeoneri();
	// Inserisce i nuovi oneri impostando:
	io.setTipicausalioneri(tipicausalioneriService.findById(new PkId(codiceCausaleOneri)));
	io.setIstanza(istanzeService.findById(new PkId(codiceIstanza)));
	io.setNumerorata(1);
	io.setGuidRateizzazione(null);
	io.setNrDocumento(null);
	//     Somma degli importi letti dalla query precedente – somma degli interessi letti da query precedente
	BigDecimal importo = bean.getPrezzo() == null ? BigDecimal.ZERO : bean.getPrezzo();
	if (bean.getInteresse() != null && //
		BigDecimal.ZERO.compareTo(bean.getInteresse()) != 0 && // 
		BigDecimal.ZERO.compareTo(importo) != 0) {
	    importo = importo.subtract(bean.getInteresse());
	}
	io.setPrezzo(importo);
	io.setPrezzoistruttoria(bean.getPrezzoistruttoria());
	io.setImportoInteresse(BigDecimal.ZERO);
	io.setFlagOnereRateizzato(Boolean.FALSE);
	io.setFlentratauscita(Boolean.TRUE);
	io.setData(bean.getData());
	io.setDatascadenza(bean.getDatascadenza());
	//
	//
	return io;
    }

    private void deleteIstanzeoneriPerRateizzazione(Integer codiceIstanza, Integer codiceCausaleOneri, Integer fkcanonetestata) {

	List<Integer> list = istanzeoneriDAO.findOneriDerateizzatiDaEliminare(codiceIstanza, codiceCausaleOneri, fkcanonetestata);
	for (Integer codiceIstanzaOnere : list) {
	    this.delete(codiceIstanzaOnere);
	}
    }

    @Override
    public List<Istanzeoneri> findRateizzatiByIstanzaAndCausale(Integer codiceIstanza, Integer codiceCausale) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceIstanza e' nullo");
	}
	if (codiceCausale == null) {
	    throw new IllegalArgumentException("findByIstanzaAndEndoAndCausale: il parametro codiceCausale e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipicausalioneriId", codiceCausale, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagOnereRateizzato", Boolean.TRUE, Boolean.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("data"));
	filterTable.addOrder(
		FilterUtils.orderAsc("datascadenza", FunctionsEnum.NVL_FUNCTION, "'01/01/0001'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	filterTable.addOrder(FilterUtils.orderAsc("id.codice"));
	return istanzeoneriDAO.findByFilterTable(filterTable);
    }

    @Override
    public IstoneriDettPosizioni inserisciPosizioneDebitoriaSuOnere(Integer idIstanzeoneri, Integer idDettPosizioneDebitoria) {

	IstoneriDettPosizioni ret = new IstoneriDettPosizioni();
	ret.setDettPosizioneDebitoria(istanzeoneriDAO.getById(DettPosizioneDebitoria.class, new PkId(idDettPosizioneDebitoria)));
	ret.setIstanzeoneri(this.findById(new PkId(idIstanzeoneri)));
	istanzeoneriDAO.saveEntity(ret);
	return ret;
    }

    @Override
    public boolean posizioneDebitoriaAppartieneAOnere(Integer idDettaglioPosizioneDebitoria) {

	Istanzeoneri onere = this.istanzeoneriDAO.findOnereByIdDettaglioPosizioneDebitoria(idDettaglioPosizioneDebitoria);
	return (onere != null);
    }

    @Override
    public void impostaNoteOnerePerPosizioneDebitoriaAnnullata(Integer idDettaglioPosizioneDebitoria, String noteAnnullamento) {

	Istanzeoneri onere = this.istanzeoneriDAO.findOnereByIdDettaglioPosizioneDebitoria(idDettaglioPosizioneDebitoria);
	log.debug("Inizio aggiornamento delle note di annullamento dell'onere associato alla posizione debitoria {}", idDettaglioPosizioneDebitoria);
	if (onere == null) {
	    String errMsg = String.format("La posizione debitoria con id dettaglio %d non è associata a nessun onere", idDettaglioPosizioneDebitoria);
	    log.error(errMsg);
	    throw new IllegalArgumentException(errMsg);
	}
	log.debug("La posizione debitoria è associata all'onere con id {}", onere.getId());
	onere.aggiungiNote(new MessaggioPosizioneDebitoriaPerOnereAnnullata(noteAnnullamento));
	log.debug("Note onere aggiornate, id onere: {}, note: {}", onere.getId(), onere.getNote());
	this.istanzeoneriDAO.saveEntity(onere);
    }

    @Override
    public List<ScadenzarioOneri> findTabellaScadenzario(Date dataOdierna, String[] codiciComune) {

	return istanzeoneriDAO.findTabellaScadenzario(dataOdierna, codiciComune);
    }

    @Override
    public List<Integer> findCausaliPerMappatureConti(Integer codiceIstanza, Set<String> listaMappaturePerVersamento) {

	return istanzeoneriDAO.findCausaliPerMappatureConti(codiceIstanza, listaMappaturePerVersamento);
    }

    @Override
    public List<Integer> findOneriConMappaturaNPById(Set<Integer> idIstanzeOneri) {

	return istanzeoneriDAO.findOneriConMappaturaNPById(idIstanzeOneri);
    }

    @Override
    public Integer findCodiceIstanzaByDettPosDebitoria(Integer codice) {

	return this.istanzeoneriDAO.findCodiceIstanzaByDettPosDebitoria(codice);
    }

    @Override
    public String calcolaInteressiDiMora(Integer idIstanzeOneri) throws Exception {

	Istanzeoneri istanzaOn = this.istanzeoneriDAO.findById(new PkId(idIstanzeOneri));
	//1. Verifico se l'onere è associato con una causale di mora 
	List<CalcolaInteressiDiMoraBean> cI = this.istanzeoneriDAO.calcolaInteressiDiMora(idIstanzeOneri);
	if (cI.size() > 0) {
	    CalcolaInteressiDiMoraBean clIntMora = cI.get(0);
	    Istanzeoneri figlia = this.istanzeoneriDAO.findById(new PkId(clIntMora.getIdfiglia()));
	    if (figlia != null && figlia.getId() != null && figlia.getId().getCodice() != null) {
		if (clIntMora.getDatapagamento() == null) {
		    clIntMora.setMessaggio("Togliendo la data di pagamento verranno tolti gli interessi di mora");
		    deleteIstanzeOneriInteressiMoraByFigliaId(figlia.getId().getCodice());
		    return clIntMora.getMessaggio();
		}
		if (clIntMora.getPrezzo() == null) {
		    clIntMora.setMessaggio("Togliendo l'importo da pagare verranno tolti gli interessi di mora");
		    deleteIstanzeOneriInteressiMoraByFigliaId(figlia.getId().getCodice());
		    return clIntMora.getMessaggio();
		}
		if (clIntMora.getDatascadenza() == null) {
		    clIntMora.setMessaggio("Togliendo la data di scadenza verranno tolti gli interessi di mora");
		    deleteIstanzeOneriInteressiMoraByFigliaId(figlia.getId().getCodice());
		    return clIntMora.getMessaggio();
		}
	    }
	    if (clIntMora.getIdtipicausaliinteressi() == null) {
		return "";
	    }
	    //Verifico la percentuale associata
	    if (clIntMora.getDatascadenza() != null && clIntMora.getDatapagamento() != null) {
		Integer ggPassati = getDateDiff(clIntMora.getDatascadenza(), clIntMora.getDatapagamento(), TimeUnit.DAYS).intValue();
		if (ggPassati != null && ggPassati > 0) {
		    List<Tipicausalioninteressi> rsRitardo = tipicausalioninteressiService.findByIdEGGpassati(clIntMora.getIdtipicausaliinteressi(),
			    ggPassati);
		    if (rsRitardo.size() > 0) {
			Tipicausalioninteressi tCI = rsRitardo.get(0);
			//Se la maggiorazione è pari a 0 ... esco!!!!
			if (tCI.getPercentuale().equals(0)) {
			    return "";
			}
			//Calcolo il nuovo prezzo
			BigDecimal nuovoPrezzo = clIntMora.getPrezzo().multiply(tCI.getPercentuale()).divide(BigDecimal.valueOf(100));
			NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
			if (figlia != null && figlia.getId() != null && figlia.getId().getCodice() != null) {
			    Istanzeoneri istOn = this.istanzeoneriDAO.findById(figlia.getId());
			    istOn.setPrezzo(nuovoPrezzo);
			    this.istanzeoneriDAO.update(istOn);
			    String formatted = formatter.format(nuovoPrezzo);
			    clIntMora.setMessaggio("Sono presenti " + formatted + " di mora per questa registrazione");
			    return clIntMora.getMessaggio();
			} else {
			    //Salvo gli interessi di mora
			    Istanzeoneri istanzeoneri = new Istanzeoneri();
			    istanzeoneri.setAmministrazioni(istanzaOn.getAmministrazioni());
			    Tipicausalioneri tpCsOn = tipicausalioneriService.findById(new PkId(clIntMora.getIdtipicausaliinteressi()));
			    istanzeoneri.setTipicausalioneri(tpCsOn);
			    Istanze istanza = istanzeService.findById(new PkId(clIntMora.getCodiceistanza()));
			    istanzeoneri.setIstanza(istanza);
			    istanzeoneri.setPrezzo(nuovoPrezzo);
			    istanzeoneri.setFlentratauscita(true);
			    istanzeoneri.setData(clIntMora.getDatapagamento());
			    istanzeoneri.setNumerorata(1);
			    istanzeoneri.setIstanzeoneriPadre(istanzaOn);
			    istanzeoneri.setFlribasso(false);
			    istanzeoneri.setResponsabile(istanzaOn.getResponsabile());
			    this.istanzeoneriDAO.saveEntity(istanzeoneri);
			    String formatted = formatter.format(nuovoPrezzo);
			    clIntMora.setMessaggio("Sono stati inseriti " + formatted + " di mora per questa registrazione");
			    return clIntMora.getMessaggio();
			}
		    } else {
			clIntMora.setMessaggio(
				" Attenzione!!! Errore: non è configurata la percentuale di interessi di mora per un periodo di ritardo di " +
					       ggPassati + " giorni");
			return clIntMora.getMessaggio();
			//			throw new Exception(
			//				"GestioneOneri.VerificaInteressiDiMora Errore: non è configurata la percentuale di interessi di mora per un periodo di ritardo di " +
			//					    ggPassati + " giorni");
		    }
		}
	    }
	}
	return "";
    }

    private void deleteIstanzeOneriInteressiMoraByFigliaId(Integer idFiglia) throws Exception {

	List<IstanzeOneriRegulus> istOnReg = istanzeOneriRegulusService.findByIdIstanzeOneri(idFiglia);
	if (istOnReg.size() > 0) {
	    if (istOnReg.get(0) != null) {
		throw new Exception("Impossibile cancellare gli oneri dell'istanza perchè sono stati pagati tramite sistema di pagamento esterno");
	    }
	}
	//cancello la riga da istanzeoneri con fk_idpadre = all'id passato perchè generata da quest'ultima
	this.istanzeoneriDAO.deleteByIdPadre(idFiglia);
	this.istanzecalcolocanoniOService.deleteByIdOnere(idFiglia);
	Istanzeoneri istOn = this.istanzeoneriDAO.findById(new PkId(idFiglia));
	this.istanzeoneriDAO.delete(istOn);
    }

    public static Long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {

	if (date2 != null && date1 != null) {
	    Long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
	return null;
    }
}
