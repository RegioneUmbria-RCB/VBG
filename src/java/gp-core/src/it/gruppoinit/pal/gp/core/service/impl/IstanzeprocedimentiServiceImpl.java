/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeprocedimentiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.IstanzeprocedimentiId;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Inventarioprocdyn2modellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiBaseService.SceltaMovimentiEnum;
import it.gruppoinit.pal.gp.core.service.MovimentiContromovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
@Service
public class IstanzeprocedimentiServiceImpl extends BaseServiceImpl<Istanzeprocedimenti, IstanzeprocedimentiId>
	implements IstanzeprocedimentiService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeprocedimentiServiceImpl.class);
    private InventarioprocedimentiService inventarioprocedimentiService;
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;
    private IstanzeService istanzeService;
    private IstanzeallegatiService istanzeallegatiService;
    private Istanzedyn2modellitService istanzedyn2modellitService;
    private IstanzeeventiService istanzeeventiService;
    private IstanzeoneriService istanzeoneriService;
    private IstanzeprocedimentiDAO istanzeprocedimentiDAO;
    private MovimentiContromovimentiService movimentiContromovimentiService;
    private MovimentiService movimentiService;
    private TipiMovimentoService tipiMovimentoService;
    private UserSecurityService userSecurityService;
    private TipicausalioneriService tipicausalioneriService;
    private Inventarioprocdyn2modellitService inventarioprocdyn2modellitService;

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setInventarioprocedimentisoftwareService(InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService) {

	this.inventarioprocedimentisoftwareService = inventarioprocedimentisoftwareService;
    }

    @Autowired
    public void setIstanzedyn2modellitService(Istanzedyn2modellitService istanzedyn2modellitService) {

	this.istanzedyn2modellitService = istanzedyn2modellitService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setIstanzeprocedimentiDAO(IstanzeprocedimentiDAO istanzeprocedimentiDAO) {

	this.istanzeprocedimentiDAO = istanzeprocedimentiDAO;
    }

    @Autowired
    public void setMovimentiContromovimentiService(MovimentiContromovimentiService movimentiContromovimentiService) {

	this.movimentiContromovimentiService = movimentiContromovimentiService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setInventarioprocdyn2modellitService(Inventarioprocdyn2modellitService inventarioprocdyn2modellitService) {

	this.inventarioprocdyn2modellitService = inventarioprocdyn2modellitService;
    }

    @Override
    protected Class<Istanzeprocedimenti> getEntityClass() {

	return Istanzeprocedimenti.class;
    }

    @Override
    public List<Istanzeprocedimenti> findByIstanze(Istanze istanze) {

	return istanzeprocedimentiDAO.findByIstanze(istanze);
    }

    @Override
    public void delete(Istanzeprocedimenti entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    istanzeprocedimentiDAO.delete(entity);
	}
    }

    @Override
    public List<Istanzeprocedimenti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Istanzeprocedimenti findById(IstanzeprocedimentiId id) {

	return istanzeprocedimentiDAO.findById(id);
    }

    @Override
    public void insert(Istanzeprocedimenti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeprocedimentiDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    @Override
    public void update(Istanzeprocedimenti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    childDataUpdate(entity);
	    istanzeprocedimentiDAO.update(entity);
	}
    }

    @Override
    public void insertListIstanzeprocedimenti(List<Istanzeprocedimenti> listIstanzeprocedimentiTot) {

	for (Istanzeprocedimenti istanzeprocedimenti : listIstanzeprocedimentiTot) {
	    this.insert(istanzeprocedimenti);
	}
    }

    private void childDataUpdate(Istanzeprocedimenti entity) {

	inserisciMovimentoIstanzeprocedimenti(entity);
    }

    private void dataIntegration(Istanzeprocedimenti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza procedimento passata è nulla");
	}
	fixMergeEntityProperties(entity);
	if (entity.getAcquisito() == null) {
	    entity.setAcquisito(Boolean.FALSE);
	}
	if (entity.getPerprovvedimento() == null) {
	    entity.setPerprovvedimento(Boolean.TRUE);
	}
	if (entity.getFlagCommissione() == null) {
	    entity.setFlagCommissione(Boolean.FALSE);
	}
	if (entity.getEliminato() == null) {
	    entity.setEliminato(Boolean.FALSE);
	}
	if (EntityUtils.getNestedProperty(entity.getInventarioprocedimenti(), "id.codice") != null) {
	    if (StringUtils.isBlank(entity.getDescrizioneEndoprocedimento())) {
		Inventarioprocedimenti ip = inventarioprocedimentiService.findById(new PkId(entity.getInventarioprocedimenti().getId().getCodice()));
		entity.setDescrizioneEndoprocedimento(ip.getProcedimento());
	    }
	}
    }

    protected void fixMergeEntityProperties(Istanzeprocedimenti entity) {

	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanze);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(), PkId.class,
		"id.codice");
	entity.setInventarioprocedimenti(inventarioprocedimenti);
	if (istanze != null) {
	    if (EntityUtils.getNestedProperty(entity, "id.codiceistanza") == null) {
		entity.getId().setCodiceistanza(istanze.getId().getCodice());
	    }
	}
	if (inventarioprocedimenti != null) {
	    if (EntityUtils.getNestedProperty(entity, "id.codiceinventario") == null) {
		entity.getId().setCodiceinventario(inventarioprocedimenti.getId().getCodice());
	    }
	}
    }

    /**
     * La funzione controlla se possibile inserire gli oneri se duplicati. il problema è emerso quando da Online si
     * inseriscono pratiche che mandano anche dati su oneri e questi veinvano duplicati. <br/>
     * la funzione controlla se siano già stati inseriti oneri per l'endo con la causale e con lo stesso importo.<br/>
     * Se sì allora non viene inserito l'onere. Se l'importo è diverso allora viene lasciata la riga inserita e generato
     * un evento dell'istanza. se Non viene trovato allora inserisco l'onere.
     * 
     * @param codiceInventarioC
     * @param codiceCausaleC
     * @param aop
     * @param istanzeoneris
     * @return
     */
    private boolean controllaSeInserireOnere(Integer codiceInventarioC, Integer codiceCausaleC, BigDecimal aop, List<Istanzeoneri> istanzeoneris) {

	if (codiceCausaleC == null) {
	    codiceCausaleC = -100;
	}
	if (istanzeoneris != null) {
	    for (Istanzeoneri ion : istanzeoneris) {
		Integer codiceCausaleIO = null;
		if (ion.getTipicausalioneri() != null) {
		    if (ion.getTipicausalioneri().getId() != null) {
			codiceCausaleIO = ion.getTipicausalioneri().getId().getCodice();
		    }
		}
		if (codiceCausaleIO == null) {
		    codiceCausaleIO = -200;
		}
		Integer codiceInventario = (Integer) EntityUtils.getNestedProperty(ion.getInventarioprocedimenti(), "id.codice");
		if (codiceCausaleC.intValue() == codiceCausaleIO.intValue()) {
		    if (codiceInventario != null) {
			if (codiceInventario.intValue() == codiceInventarioC.intValue()) {
			    // se il prezzo è uguale non lo inserisco
			    BigDecimal iop = ion.getPrezzo();
			    if (iop == null) {
				iop = BigDecimal.valueOf(0);
			    }
			    if (aop == null) {
				aop = BigDecimal.valueOf(0);
			    }
			    if (iop.doubleValue() == aop.doubleValue()) {
				return false;
			    } else {
				istanzeeventiService.insert(
					"Durante l'inserimento dell'onere configurato in inventarioprocedimenti [" +
						codiceInventarioC +
						"-" +
						ORMHelper.getIdcomune() +
						"] è stato trovato un onere con causale (" +
						codiceCausaleIO.intValue() +
						") e importo=" +
						aop.doubleValue() +
						" mentre è stato ricevuto un importo=" +
						iop.doubleValue() +
						". L'onere della configurazione non è stato inserito",
					IstanzeeventiConstants.CATEGORIA_STC_IP, null, ion.getIstanza());
				// Se il prezzo è diverso metto su istanze eventi
				// il fatto che l'importo è diverso
				return false;
			    }
			}
		    }
		}
	    }
	}
	return true;
    }

    private void childDataInsert(Istanzeprocedimenti entity) {

	Inventarioprocedimenti endo = entity.getInventarioprocedimenti();
	endo = inventarioprocedimentiService.findById(endo.getId());
	IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	boolean isOneriRule = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteOneriIstanza.name());
	if (isOneriRule) {
	    Set<Inventarioprocedimentioneri> inventarioprocedimentioneris = endo.getInventarioprocedimentioneris();
	    // inserisco gli oneri legati all'endo
	    for (Inventarioprocedimentioneri inventarioprocedimentioneri : inventarioprocedimentioneris) {
		if (EntityUtils.getNestedProperty(inventarioprocedimentioneri.getTipicausalioneri(), "id.codice") != null) {
		    Tipicausalioneri tc = tipicausalioneriService
			    .findById(new PkId(inventarioprocedimentioneri.getTipicausalioneri().getId().getCodice()));
		    boolean onereDisabilitato = tc.getCoDisabilitato() == null ? false : tc.getCoDisabilitato().booleanValue();
		    if (!onereDisabilitato) {
			List<Istanzeoneri> ioneris = istanzeoneriService.findByIstanzaAndEndoAndCausale(entity.getIstanza().getId().getCodice(),
				endo.getId().getCodice(), inventarioprocedimentioneri.getTipicausalioneri().getId().getCodice());
			boolean isInserimentoOnere = controllaSeInserireOnere(endo.getId().getCodice(),
				inventarioprocedimentioneri.getTipicausalioneri().getId().getCodice(), inventarioprocedimentioneri.getImporto(),
				ioneris);
			if (isInserimentoOnere) {
			    Istanzeoneri istanzeoneri = new Istanzeoneri();
			    istanzeoneri.setInventarioprocedimenti(endo);
			    istanzeoneri.setIstanza(entity.getIstanza());
			    istanzeoneri.setData(entity.getDataattivazione());
			    istanzeoneri.setAmministrazioni(endo.getAmministrazioni());
			    istanzeoneri.setFlentratauscita(Boolean.TRUE);
			    istanzeoneri.setFlribasso(Boolean.FALSE);
			    istanzeoneri.setPercribasso(100);
			    // NON DOVREBBE MAI AVVENIRE
			    Integer numerorata = istanzeoneriService.findNumeroRata(entity.getIstanza(),
				    inventarioprocedimentioneri.getTipicausalioneri());
			    istanzeoneri.setNumerorata(numerorata); // E' il progressivo in base a IDCOMUNE, ISTANZA, CAUSALEONERE
			    istanzeoneri.setTipicausalioneri(inventarioprocedimentioneri.getTipicausalioneri());
			    istanzeoneri.setResponsabile(entity.getIstanza().getResponsabile());
			    istanzeoneri.setTipimodalitapagamento(inventarioprocedimentioneri.getTipimodalitapagamento());
			    istanzeoneri.setPrezzo(inventarioprocedimentioneri.getImporto());
			    istanzeoneri.setPrezzoistruttoria(inventarioprocedimentioneri.getImportoistruttoria());
			    istanzeoneriService.insert(istanzeoneri);
			}
		    }
		} else {
		    List<String> msgs = FlashMessages.getWarnings();
		    String descrizioneEvento = "Non è stato inserito l'onere configurato per l'endo [" +
			    endo.getProcedimento() +
			    "-" +
			    endo.getId().getCodice() +
			    "] perchè ha tipicausalioneri nullo [id:" +
			    inventarioprocedimentioneri.getId() +
			    "]!!!";
		    msgs.add(descrizioneEvento);
		    //		    log.error("childDataInsert: inventarioprocedimentioneri [{}] ha tipicausalioneri nullo!!!", inventarioprocedimentioneri.getId());
		    //		    throw new BusinessValidationException("inventarioprocedimentioneri [" + inventarioprocedimentioneri.getId()
		    //			    + "] ha tipicausalioneri nullo!!!");
		    try {
			istanzeeventiService.insert(descrizioneEvento, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, entity.getIstanza());
		    } catch (Exception ex) {
			log.error("gestProtocolloEFascicolo: non è stato possibile inserire l'evento a causa di={}", ex.getMessage());
		    }
		    FlashMessages.setWarnings(msgs);
		}
	    }
	}
	// inserisco gli allegati legati all'endo
	boolean isDocumentiRule = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteDocumentiIstanza.name());
	// Se è abilitata dalle regole l'inserimento degli allegati dell'endo nell' esecuzione normale allora inserisco. 
	// nel caso di inserimento pratica da stc vengono di norma disabilitati
	if (isDocumentiRule) {
	    Set<Allegati> allegatis = endo.getAllegatis();
	    for (Allegati allegato : allegatis) {
		// IstanzeallegatiId id = new IstanzeallegatiId();
		// id.setCodiceistanza(entity.getIstanza().getId().getCodice());
		// id.setCodiceinventario(endo.getId().getCodice());
		// istanzeallegati.setId(id);
		Istanzeallegati istanzeallegati = new Istanzeallegati();
		istanzeallegati.setInventarioprocedimenti(endo);
		istanzeallegati.setIstanza(entity.getIstanza());
		istanzeallegati.setInventarioprocedimenti(endo);
		istanzeallegati.setSeendo(Boolean.FALSE);
		istanzeallegati.setControllook(null);
		istanzeallegati.setVerificato(Boolean.FALSE);
		istanzeallegati.setPresente(Boolean.FALSE);
		istanzeallegati.setCostopresunto(allegato.getCosto());
		istanzeallegati.setAllegatoextra(allegato.getAllegato());
		istanzeallegati.setAllegati(allegato);
		istanzeallegatiService.insert(istanzeallegati);
	    }
	}
	// sistema l'eventuale generazione del movimento
	inserisciMovimentoIstanzeprocedimenti(entity);
	// inserisco i modelli da endo
	boolean isModelliRule = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamenteModelliDinamiciIstanza.name());
	if (isModelliRule) {
	    //Set<Inventarioprocdyn2modellit> modelli = endo.getInventarioprocdyn2modellits();
	    List<Inventarioprocdyn2modellit> modelli = inventarioprocdyn2modellitService.findByInventarioprocedimento(endo.getId().getCodice());
	    for (Inventarioprocdyn2modellit inventarioprocdyn2modellit : modelli) {
		Istanzedyn2modellitId id = new Istanzedyn2modellitId(entity.getIstanza().getId().getCodice(),
			inventarioprocdyn2modellit.getDyn2Modellit().getId().getCodice());
		Istanzedyn2modellit modello = istanzedyn2modellitService.findById(id);
		if (modello == null) {
		    modello = new Istanzedyn2modellit();
		    modello.setId(id);
		    modello.setDyn2Modellit(inventarioprocdyn2modellit.getDyn2Modellit());
		    modello.setIstanza(entity.getIstanza());
		    istanzedyn2modellitService.insert(modello);
		}
	    }
	}
    }

    public void inserisciMovimentoIstanzeprocedimenti(Istanzeprocedimenti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("inserisciMovimentoIstanzeprocedimenti. Il parametro Istanzeprocedimenti è nullo");
	}
	if (EntityUtils.getNestedProperty(entity.getInventarioprocedimenti(), "id.codice") == null) {
	    throw new IllegalArgumentException("inserisciMovimentoIstanzeprocedimenti. Il parametro Istanzeprocedimenti.endo è nullo o non valido");
	}
	// compatibilità con le vecchie istanze che inserivano anche l'endo 0
	// in questo caso non devo inserire generare movimenti 
	if (entity.getId().getCodiceinventario().equals(0)) {
	    return;
	}
	// Il movimento da inserire è quello specificato dalla colonna TIPOMOVIMENTO dell'endo nel caso di un software diverso da 'TT'. 
	// Se l'endo appartiene al software 'TT' allora il movimento cerca tra i 
	// movimenti configurati in inventarioprocedimentisoftwares e se non lo trova sempre nella colonna TIPOMOVIMENTO dell'endo.
	Inventarioprocedimenti endo = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(), PkId.class, "id.codice");
	boolean endoDelSoftwareDellaPratica = endo.getSoftware().getCodice().equalsIgnoreCase(entity.getIstanza().getSoftware().getCodice());
	log.debug("inserisciMovimentoIstanzeprocedimenti: endoDelSoftwareDellaPratica {}, software dell'endo {}", endoDelSoftwareDellaPratica,
		endo.getSoftware().getCodice());
	// inserisco il movimento (come scadenza) ed i permessi legati all'endo se l'endo ha un movimento di trasmissione legato
	String tipoMovimento = (String) EntityUtils.getNestedProperty(endo, "tipomovimento.id.tipomovimento");
	// SE L'ENDOPROCEDIMENTO è CON SOFTWARE TT op pure è un endo di altro software della pratica
	// MODIFICA SOFTWARE CHE PERMETTE DI VERIFICARE LE CONFIGURAZIONI DI ATTIVA IN ANCHE SU ALTRI SOFTWARE
	// ALLORA DEVO CONTROLLARE SE PRESENTE SU INVENTARIOPROCEDIMENTISOFTWARE
	boolean opzionale = false;
	if (endo.getSoftware().getCodice().equals(WebConstants.SOFTWARE_TT) || !endoDelSoftwareDellaPratica) {
	    List<Inventarioprocedimentisoftware> iss = inventarioprocedimentisoftwareService
		    .findByEndoprocedimentiAndSoftware(endo.getId().getCodice(), entity.getIstanza().getSoftware().getCodice());
	    if (!iss.isEmpty()) {
		for (Inventarioprocedimentisoftware inventarioprocedimentisoftware : iss) {
		    log.debug("inserisciMovimentoIstanzeprocedimenti: endoDelSoftwareDellaPratica {}, {}",
			    new Object[] { endoDelSoftwareDellaPratica, inventarioprocedimentisoftware.getId().getCodice() });
		    String tipoMovimentoSoftware = (String) EntityUtils.getNestedProperty(inventarioprocedimentisoftware,
			    "tipimovimento.id.tipomovimento");
		    opzionale = BooleanUtils.isTrue(inventarioprocedimentisoftware.getFlagMovOpzionale());
		    if ((endoDelSoftwareDellaPratica || endo.getSoftware().getCodice().equals(WebConstants.SOFTWARE_TT)) && // MODIFICA SOFTWARE CHE PERMETTE DI VERIFICARE LE CONFIGURAZIONI DI ATTIVA IN ANCHE SU ALTRI SOFTWARE
		    // prendo il movimento dell'endo solo se l'endo appartiene al software della pratica
			    StringUtils.isBlank(tipoMovimentoSoftware) && // 
			    endo.getTipomovimento() != null && // 
			    endo.getTipomovimento().getId() != null && // 
			    StringUtils.isNotBlank(endo.getTipomovimento().getId().getTipomovimento())) {
			tipoMovimentoSoftware = endo.getTipomovimento().getId().getTipomovimento();
			log.debug(
				"inserisciMovimentoIstanzeprocedimenti: endoDelSoftwareDellaPratica {}, software dell'endo {}, tipoMovimentoSoftware {}",
				new Object[] { endoDelSoftwareDellaPratica, endo.getSoftware().getCodice(), tipoMovimentoSoftware });
		    }
		    if (StringUtils.isNotBlank(tipoMovimentoSoftware)) {
			if (inventarioprocedimentisoftware.getAmministrazioni() != null) {
			    gestisciMovimentoEndo(entity, endo, tipoMovimentoSoftware, inventarioprocedimentisoftware.getAmministrazioni(),
				    opzionale);
			} else {
			    if (endo.getAmministrazioni() != null) {
				gestisciMovimentoEndo(entity, endo, tipoMovimentoSoftware, endo.getAmministrazioni(), opzionale);
			    }
			}
		    }
		}
	    }
	} else {
	    if (StringUtils.isNotBlank(tipoMovimento)) {
		if (endo.getAmministrazioni() != null) {
		    gestisciMovimentoEndo(entity, endo, tipoMovimento, endo.getAmministrazioni(), opzionale);
		}
	    } else {
		// non è stata trovata la configurazione per il tipomovimento dell'endo
		// 	Non rilancio l'eccezione
	    }
	}
    }

    private void gestisciMovimentoEndo(Istanzeprocedimenti entity, Inventarioprocedimenti endo, String tipoMovimento, Amministrazioni amministrazioni,
	    boolean opzionale) {

	Integer codiceAmministrazione = null;
	if (amministrazioni != null) {
	    codiceAmministrazione = amministrazioni.getId().getCodice();
	} else {
	    log.error("gestisciMovimentoEndo# Amministrazione non configurata per l'endo: " + endo.getProcedimento() + "[" + endo.getId() + "]");
	    throw new RuntimeException("Amministrazione non configurata per l'endo: " + endo.getProcedimento() + "[" + endo.getId() + "]");
	}
	if (BooleanUtils.isTrue(entity.getPerprovvedimento())) {
	    // cerco tutti i movimenti inseriti per l'endo 	    
	    List<Movimenti> movimentis = movimentiService.findMovimentiByIstanzeprocedimentiAndAmministrazione(entity, codiceAmministrazione,
		    SceltaMovimentiEnum.ESEGUITI);
	    int ggTrasmissione = 0;
	    Tipiprocedure procedura = entity.getIstanza().getProcedura();
	    if (procedura != null) {
		if (procedura.getNumgginvio() != null) {
		    ggTrasmissione = procedura.getNumgginvio();
		}
	    }
	    Date dataattivazione = entity.getDataattivazione();
	    Calendar cal = GregorianCalendar.getInstance();
	    cal.setTime(dataattivazione);
	    cal.add(Calendar.DATE, ggTrasmissione);
	    Date dataScadenza = cal.getTime();
	    // se non ci sono movimenti già fatti per l'endo
	    if (movimentis.isEmpty()) {
		movimentis = movimentiService.findMovimentiByIstanzeprocedimentiAndAmministrazione(entity, codiceAmministrazione,
			SceltaMovimentiEnum.NON_ESEGUITI);
		boolean inserisciTrasmissione = true;
		if (movimentis.size() > 0) {
		    inserisciTrasmissione = false;
		    for (Movimenti movimenti : movimentis) {
			if (tipoMovimento.equalsIgnoreCase(movimenti.getTipomovimento().getId().getTipomovimento())) {
			    // se il movimento di quel tipo è stato già inserito non lo inserisco
			    if (movimenti.getDataScadenza() != null) {
				// aggiorno la data scadenza solamente se sono diverse
				if (dataScadenza.compareTo(movimenti.getDataScadenza()) != 0) {
				    movimenti.setFlagCmovObblig(!opzionale);
				    movimenti.setDataScadenza(dataScadenza);
				    movimentiService.updateScadenza(movimenti);
				}
			    }
			}
		    }
		}
		if (inserisciTrasmissione) {
		    Movimenti trasmissione = new Movimenti();
		    Tipimovimento tipimovimento = new Tipimovimento();
		    tipimovimento.getId().setTipomovimento(tipoMovimento);
		    tipimovimento = tipiMovimentoService.bindDomainObject(tipimovimento, TipimovimentoId.class, "id.tipomovimento");
		    trasmissione.setTipomovimento(tipimovimento);
		    trasmissione.setAmministrazioni(amministrazioni);
		    trasmissione.setEndoprocedimento(endo);
		    trasmissione.setIstanza(entity.getIstanza());
		    trasmissione.setDataScadenza(dataScadenza);
		    trasmissione.setFlagCmovObblig(!opzionale);
		    movimentiService.insertScadenza(trasmissione);
		}
	    } else {
		if (movimentis.size() > 0) {
		    Responsabili resp = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
		    for (Movimenti movimenti : movimentis) {
			if (tipoMovimento.equalsIgnoreCase(movimenti.getTipomovimento().getId().getTipomovimento())) {
			    if (movimentiService.isEffettuato(movimenti)) {
				if (movimentiService.checkPermessiMovimento(movimenti, resp, true)) {
				    if (movimenti.getDataScadenza() != null) {
					// aggiorno la data scadenza solamente se sono diverse
					if (dataScadenza.compareTo(movimenti.getDataScadenza()) != 0) {
					    movimenti.setDataScadenza(dataScadenza);
					}
				    }
				    // se il movimento è eseguito scateno l'elaborazione del movimento
				    movimentiService.update(movimenti);
				}
				break;
			    }
			}
		    }
		}
	    }
	} else {
	    // L'endo non deve eseguire movimenti (PerProvvedimento==false). Devo controllare se è stato inserito il movimento dell'endo. 
	    // Se il movimento è stato inserito come scadenza (ossia non è stato effettuato) allora viene cancellato dalle scadenze dell'elaborazione
	    List<Movimenti> movimentis = movimentiService.findMovimentiByIstanzeprocedimentiAndAmministrazione(entity, codiceAmministrazione,
		    SceltaMovimentiEnum.NON_ESEGUITI);
	    if (movimentis.size() > 0) {
		Responsabili resp = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
		for (Movimenti movimenti : movimentis) {
		    if (tipoMovimento.equalsIgnoreCase(movimenti.getTipomovimento().getId().getTipomovimento())) {
			if (movimentiService.checkPermessiMovimento(movimenti, resp, true)) {
			    movimentiService.delete(movimenti);
			}
			break;
		    }
		}
	    }
	}
	updateIstanzeProcedimentiAcquisito(entity, tipoMovimento, codiceAmministrazione);
    }

    /**
     * @param entity
     * @param tipoMovimento
     */
    private void updateIstanzeProcedimentiAcquisito(Istanzeprocedimenti entity, String tipoMovimento, Integer codiceAmministrazione) {

	if (BooleanUtils.isTrue(entity.getAcquisito())) {
	    // se è anche acquisito devo eliminare gli eventuali contromovimenti non eseguiti
	    List<Movimenti> movimentis = movimentiService.findMovimentiByIstanzeprocedimentiAndAmministrazione(entity, codiceAmministrazione,
		    SceltaMovimentiEnum.ESEGUITI);
	    if (movimentis.size() > 0) {
		for (Movimenti movimenti : movimentis) {
		    // cerco il movimento di partenza
		    if (tipoMovimento.equalsIgnoreCase(movimenti.getTipomovimento().getId().getTipomovimento())) {
			// da questo elimino gli eventuali contromovimenti non eseguiti
			List<MovimentiContromovimenti> mcs = movimentiContromovimentiService.findByMovimentoByFkPadre(movimenti);
			for (MovimentiContromovimenti mc : mcs) {
			    if (movimentiService.isEffettuato(mc.getMovimentoByFkFiglio()) == false) {
				movimentiContromovimentiService.delete(mc);
				movimentiService.delete(mc.getMovimentoByFkFiglio());
			    }
			}
		    }
		}
	    }
	}
    }

    protected void childDelete(Istanzeprocedimenti entity) {

	List<Istanzeallegati> listAllegati = istanzeallegatiService.findByIstanzaAndEndo(entity.getIstanza().getId().getCodice(),
		entity.getInventarioprocedimenti().getId().getCodice());
	for (Istanzeallegati istanzeallegati : listAllegati) {
	    istanzeallegatiService.delete(istanzeallegati);
	}
	List<Istanzeoneri> lististanzeoneri = istanzeoneriService.findByIstanzaAndEndo(entity.getIstanza(), entity.getInventarioprocedimenti());
	for (Istanzeoneri istanzeoneri : lististanzeoneri) {
	    istanzeoneriService.delete(istanzeoneri);
	}
	// posso cancellare solamente i movimenti non eseguiti
	List<Movimenti> movimentis = movimentiService.findMovimentiByIstanzeprocedimenti(entity, SceltaMovimentiEnum.NON_ESEGUITI);
	for (Movimenti movimenti : movimentis) {
	    movimentiService.delete(movimenti);
	}
    }

    @Override
    protected boolean isDeleteAllowed(Istanzeprocedimenti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// 1. Verifica che non siano stati effettuati movimenti per quell'endo
	List<Movimenti> movimentis = movimentiService.findMovimentiByIstanzeprocedimenti(entity, SceltaMovimentiEnum.ESEGUITI);
	if (movimentis.size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MOVIMENTI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Istanzeprocedimenti> findByFilterTable(FilterTable filterTable) {

	return istanzeprocedimentiDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<IstanzeprocedimentiHelper> findRiepilogoEndo(Istanze istanze) {

	List<IstanzeprocedimentiHelper> result = new ArrayList<IstanzeprocedimentiHelper>();
	List<Istanzeprocedimenti> istanzeprocedimentis = this.findByIstanze(istanze);
	for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
	    IstanzeprocedimentiHelper helper = new IstanzeprocedimentiHelper();
	    helper.setIstanzeprocedimenti(istanzeprocedimenti);
	    Movimenti trasmissione = movimentiService.findMovimentoTrasmissioneByIstanzeprocedimenti(istanzeprocedimenti);
	    helper.setMovimentoTrasmissione(trasmissione);
	    Movimenti ritorno = movimentiService.findMovimentoRitornoByIstanzeprocedimenti(istanzeprocedimenti);
	    helper.setMovimentoRitorno(ritorno);
	    result.add(helper);
	}
	return result;
    }

    @Override
    public int countByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("countByIstanza: il parametro codiceIstanza e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("id.codiceistanza", codiceIstanza, Integer.class));
	filterTable.addRestriction(istanza);
	int count = istanzeprocedimentiDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public int countByInventarioprocedimento(Integer codiceProcedimento) {

	if (codiceProcedimento == null) {
	    throw new IllegalArgumentException("countByInventarioprocedimento: il parametro codiceProcedimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceProcedimento, "inventarioprocedimenti", Integer.class));
	filterTable.addRestriction(fr);
	int count = istanzeprocedimentiDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public List<Istanzeprocedimenti> findByIstanze(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("findByIstanze: il parametro codiceIstanza e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("id.codiceistanza", codiceIstanza, Integer.class));
	filterTable.addRestriction(istanza);
	return istanzeprocedimentiDAO.findByFilterTable(filterTable);
    }
}
