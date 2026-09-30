package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiMovimentoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloRegistri;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvv;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmSca;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoComunicazioni;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbit;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentooneri;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit.ITipimovimentoRabbitService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologiedettService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipopareriService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.ProtocolloRegistriService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliTmAvvService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliTmScaService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipicontromovimentoService;
import it.gruppoinit.pal.gp.core.service.Tipimovimentidyn2modellitService;
import it.gruppoinit.pal.gp.core.service.TipimovimentoComunicazioniService;
import it.gruppoinit.pal.gp.core.service.TipimovimentooneriService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureavvioService;

@Service
public class TipiMovimentoServiceImpl extends BaseServiceImpl<Tipimovimento, TipimovimentoId> implements TipiMovimentoService {

    private Vector<Tipimovimento> movimentiVisitati = new Vector<Tipimovimento>();
    private TipiMovimentoDAO tipiMovimentoDAO;
    private AlberoprocService alberoprocService;
    private CommedilizieTipologiedettService commedilizieTipologiedettService;
    private CommedilizieTipopareriService commedilizieTipopareriService;
    private ResponsabiliTmAvvService responsabiliTmAvvService;
    private ResponsabiliTmScaService responsabiliTmScaService;
    private ResponsabilisoftwareService responsabilisoftwareService;
    private ResponsabiliService responsabiliService;
    private StatiistanzaService statiistanzaService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;
    private IstanzeService istanzeService;
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    private ProtocolloRegistriService protocolloRegistriService;
    private TipicontromovimentoService tipicontromovimentoService;
    private TipimovimentodoctipoService tipimovimentodoctipoService;
    private TipimovimentooneriService tipimovimentooneriService;
    private Tipimovimentidyn2modellitService tipimovimentidyn2modellitService;
    private TipiprocedureavvioService tipiprocedureavvioService;
    private TipiprocedureService tipiprocedureService;
    private TipimovimentoComunicazioniService tipimovimentoComunicazioniService;
    private LetteretipoService lettereTipoService;
    private MovimentiService movimentiService;
    private MailtipoService mailtipoService;
    private ITipimovimentoRabbitService tipimovimentoRabbitService;

    @Autowired
    public void setTipimovimentoRabbitService(ITipimovimentoRabbitService tipimovimentoRabbitService) {

	this.tipimovimentoRabbitService = tipimovimentoRabbitService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setCommedilizieTipologiedettService(CommedilizieTipologiedettService commedilizieTipologiedettService) {

	this.commedilizieTipologiedettService = commedilizieTipologiedettService;
    }

    @Autowired
    public void setCommedilizieTipopareriService(CommedilizieTipopareriService commedilizieTipopareriService) {

	this.commedilizieTipopareriService = commedilizieTipopareriService;
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
    public void setResponsabilisoftwareService(ResponsabilisoftwareService responsabilisoftwareService) {

	this.responsabilisoftwareService = responsabilisoftwareService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setResponsabiliTmScaService(ResponsabiliTmScaService responsabiliTmScaService) {

	this.responsabiliTmScaService = responsabiliTmScaService;
    }

    @Autowired
    public void setResponsabiliTmAvvService(ResponsabiliTmAvvService responsabiliTmAvvService) {

	this.responsabiliTmAvvService = responsabiliTmAvvService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Autowired
    public void setTipiMovimentoDAO(TipiMovimentoDAO tipiMovimentoDAO) {

	this.tipiMovimentoDAO = tipiMovimentoDAO;
    }

    @Autowired
    public void setProtocolloRegistriService(ProtocolloRegistriService protocolloRegistriService) {

	this.protocolloRegistriService = protocolloRegistriService;
    }

    @Autowired
    public void setOneritipirateizzazioneService(OneritipirateizzazioneService oneritipirateizzazioneService) {

	this.oneritipirateizzazioneService = oneritipirateizzazioneService;
    }

    @Autowired
    public void setTipicontromovimentoService(TipicontromovimentoService tipicontromovimentoService) {

	this.tipicontromovimentoService = tipicontromovimentoService;
    }

    @Autowired
    public void setTipimovimentodoctipoService(TipimovimentodoctipoService tipimovimentodoctipoService) {

	this.tipimovimentodoctipoService = tipimovimentodoctipoService;
    }

    @Autowired
    public void setTipimovimentooneriService(TipimovimentooneriService tipimovimentooneriService) {

	this.tipimovimentooneriService = tipimovimentooneriService;
    }

    @Autowired
    public void setTipimovimentoComunicazioniService(TipimovimentoComunicazioniService tipimovimentoComunicazioniService) {

	this.tipimovimentoComunicazioniService = tipimovimentoComunicazioniService;
    }

    @Autowired
    public void setTipimovimentidyn2modellitService(Tipimovimentidyn2modellitService tipimovimentidyn2modellitService) {

	this.tipimovimentidyn2modellitService = tipimovimentidyn2modellitService;
    }

    @Autowired
    public void setTipiprocedureavvioService(TipiprocedureavvioService tipiprocedureavvioService) {

	this.tipiprocedureavvioService = tipiprocedureavvioService;
    }

    @Autowired
    public void setTipiprocedureService(TipiprocedureService tipiprocedureService) {

	this.tipiprocedureService = tipiprocedureService;
    }

    @Autowired
    public void setLettereTipoService(LetteretipoService lettereTipoService) {

	this.lettereTipoService = lettereTipoService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Override
    protected Class<Tipimovimento> getEntityClass() {

	return Tipimovimento.class;
    }

    @Override
    public List<Tipimovimento> findByDescrizione(Tipimovimento entity, boolean includiDisabilitati) {

	List<Software> softwareList = new ArrayList<Software>();
	Software current = new Software();
	current.setCodice(ORMHelper.getSoftware());
	Software tt = new Software();
	tt.setCodice(WebConstants.SOFTWARE_TT);
	softwareList.add(current);
	softwareList.add(tt);
	return tipiMovimentoDAO.findByDescrizione(entity, includiDisabilitati, softwareList);
    }

    @Override
    public void delete(Tipimovimento entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    tipiMovimentoDAO.delete(entity);
	}
    }

    @Override
    public List<Tipimovimento> findAll(Integer firstResult, Integer maxResult) {

	return tipiMovimentoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Tipimovimento findById(TipimovimentoId id) {

	return tipiMovimentoDAO.findById(id);
    }

    @Override
    public void insert(Tipimovimento entity) {

	dataIntegration(entity);
	String codiceTipoMovimento = StringUtils.defaultIfEmpty(entity.getId().getTipomovimento(), "");
	if (codiceTipoMovimento.length() <= 6) {
	    entity.getId().setTipomovimento(ORMHelper.getSoftware() + codiceTipoMovimento);
	} else {
	    entity.getId().setTipomovimento(ORMHelper.getSoftware() + StringUtils.right(codiceTipoMovimento, 6));
	}
	if (validateEntity(entity) && isInsertAllowed(entity, false)) {
	    tipiMovimentoDAO.insert(entity);
	}
    }

    private void dataIntegration(Tipimovimento entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro è nullo Tipimovimento");
	}
	if (entity.getFlagCamcom() == null) {
	    entity.setFlagCamcom(Boolean.FALSE);
	}
	if (entity.getFlagCds() == null) {
	    entity.setFlagCds(Boolean.FALSE);
	}
	if (entity.getFlagEnmail() == null) {
	    entity.setFlagEnmail(Boolean.FALSE);
	}
	if (entity.getFlagEnmostra() == null) {
	    entity.setFlagEnmostra(Boolean.FALSE);
	}
	if (entity.getFlagFinesospinterr() == null) {
	    entity.setFlagFinesospinterr(Boolean.FALSE);
	}
	if (entity.getFlagNoamminterna() == null) {
	    entity.setFlagNoamminterna(Boolean.FALSE);
	}
	if (entity.getFlagNonoperante() == null) {
	    entity.setFlagNonoperante(Boolean.FALSE);
	}
	if (entity.getFlagOperante() == null) {
	    entity.setFlagOperante(Boolean.FALSE);
	}
	if (entity.getFlagProroga() == null) {
	    entity.setFlagProroga(Boolean.FALSE);
	}
	if (entity.getFlagPubblicamovimento() == null) {
	    entity.setFlagPubblicamovimento(Boolean.FALSE);
	}
	if (entity.getFlagPubblicaparere() == null) {
	    entity.setFlagPubblicaparere(Boolean.FALSE);
	}
	if (entity.getFlagRegistro() == null) {
	    entity.setFlagRegistro(Boolean.FALSE);
	}
	if (entity.getFlagRichiestaintegrazione() == null) {
	    entity.setFlagRichiestaintegrazione(Boolean.FALSE);
	}
	if (entity.getFlagStc() == null) {
	    entity.setFlagStc(Boolean.FALSE);
	}
	if (entity.getFlagUsadalprotocollo() == null) {
	    entity.setFlagUsadalprotocollo(Boolean.FALSE);
	}
	if (entity.getTipologiaesito() == null) {
	    entity.setTipologiaesito(0);
	}
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(Boolean.FALSE);
	}
	if (entity.getFlgInvialinkallmail() == null) {
	    entity.setFlgInvialinkallmail(Boolean.FALSE);
	}
	if (entity.getFlgProtocollalinkall() == null) {
	    entity.setFlgProtocollalinkall(Boolean.FALSE);
	}
	if (entity.getFlagSostDocumentale() == null) {
	    entity.setFlagSostDocumentale(Integer.valueOf(0));
	}
	if (entity.getFlagIntegrCheckFirma() == null) {
	    entity.setFlagIntegrCheckFirma(Boolean.FALSE);
	}
	if (entity.getFlagPubblSchede() == null) {
	    entity.setFlagPubblSchede(Boolean.FALSE);
	}
	if (entity.getFlagAccediSchede() == null) {
	    entity.setFlagAccediSchede(Boolean.FALSE);
	}
	if (entity.getFlagFoRichiamaSit() == null) {
	    entity.setFlagFoRichiamaSit(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Tipimovimento entity) {

	Statiistanza statiistanza = statiistanzaService.bindDomainObject(entity.getStatoistanza(), StatiistanzaId.class, "id.codicestato");
	entity.setStatoistanza(statiistanza);
	Letteretipo lettereTipo = lettereTipoService.bindDomainObject(entity.getLetteraTipoAllegati(), PkId.class, "id.codice");
	entity.setLetteraTipoAllegati(lettereTipo);
	Mailtipo mailtipoOggProt = mailtipoService.bindDomainObject(entity.getMailtipoOggProt(), PkId.class, "id.codice");
	entity.setMailtipoOggProt(mailtipoOggProt);
    }

    @Override
    protected void childDelete(Tipimovimento entity) {

	Set<Tipimovimentodoctipo> tipimovimentodoctipos = entity.getTipimovimentodoctipos();
	for (Tipimovimentodoctipo tipimovimentodoctipo : tipimovimentodoctipos) {
	    tipimovimentodoctipoService.delete(tipimovimentodoctipo);
	}
	Set<Tipimovimentidyn2modellit> tipimovimentidyn2modellits = entity.getTipimovimentidyn2modellits();
	for (Tipimovimentidyn2modellit tipimovimentidyn2modellit : tipimovimentidyn2modellits) {
	    tipimovimentidyn2modellitService.delete(tipimovimentidyn2modellit);
	}
	List<ResponsabiliTmAvv> rta = responsabiliTmAvvService.findByTipomovimento(entity.getId().getTipomovimento());
	for (ResponsabiliTmAvv responsabiliTmAvv : rta) {
	    responsabiliTmAvvService.delete(responsabiliTmAvv);
	}
	List<ResponsabiliTmSca> rts = responsabiliTmScaService.findByTipomovimento(entity.getId().getTipomovimento());
	for (ResponsabiliTmSca responsabiliTmSca : rts) {
	    responsabiliTmScaService.delete(responsabiliTmSca);
	}
	Set<Tipimovimentooneri> tipimovimentooneris = entity.getTipimovimentooneris();
	for (Tipimovimentooneri tipimovimentooneri : tipimovimentooneris) {
	    tipimovimentooneriService.delete(tipimovimentooneri);
	}
	List<TipimovimentoComunicazioni> tipimovimentoComunicazionis = tipimovimentoComunicazioniService
		.findByTipoMov(entity.getId().getTipomovimento());
	for (TipimovimentoComunicazioni tipimovimentoComunicazioni : tipimovimentoComunicazionis) {
	    tipimovimentoComunicazioniService.delete(tipimovimentoComunicazioni);
	}
	Set<TipimovimentoRabbit> tipimovimentoRabbit = entity.getTipimovimentoRabbit();
	for (TipimovimentoRabbit tmr : tipimovimentoRabbit) {
	    tipimovimentoRabbitService.delete(tmr);
	}
    }

    @Override
    public void update(Tipimovimento entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity, true))
	    tipiMovimentoDAO.update(entity);
    }

    @Override
    public List<Tipimovimento> findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity, String software, boolean includiDisabilitati) {

	return tipiMovimentoDAO.findTipimovimentoByDescrizioneAndSoftware(entity, software, includiDisabilitati);
    }

    @Override
    public List<Tipimovimento> findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity, String software, boolean includiDisabilitati,
	    boolean escludiNonUsatiInProtocollo) {

	return tipiMovimentoDAO.findTipimovimentoByDescrizioneAndSoftware(entity, software, includiDisabilitati, escludiNonUsatiInProtocollo);
    }

    @Override
    public Vector<Tipimovimento> visitaCMov(Tipimovimento mov, Integer codiceprocedimento) {

	Vector<Tipicontromovimento> cMovTemp = getContromovimenti(mov);
	// estra il codice procedura iniziale
	Tipiprocedure tipiprocedureIniziale = alberoprocService.getProcedura(codiceprocedimento);
	// controlla se c'è un movimento di chiusura
	if (tipiprocedureIniziale.getTipimovimentoChiusura() != null && tipiprocedureIniziale.getTipimovimentoChiusura().getId() != null
		&& !tipiprocedureIniziale.getTipimovimentoChiusura().getId().getTipomovimento().equals("")) {
	    Tipimovimento tipoMovimentoDiChiusura = tipiMovimentoDAO.findById(
		    new TipimovimentoId(ORMHelper.getIdcomune(), tipiprocedureIniziale.getTipimovimentoChiusura().getId().getTipomovimento()));
	    if (!movimentiVisitati.contains(tipoMovimentoDiChiusura))
		movimentiVisitati.add(tipoMovimentoDiChiusura);
	}
	Tipimovimento temp;
	for (int i = 0; i < cMovTemp.size(); i++) {
	    Tipicontromovimento tipicontromovimentoTemp = (Tipicontromovimento) cMovTemp.get(i);
	    // un nodo deve far parte dell'albero solo il suo codice di procedura è null(appartiene a tutti i
	    // procedimenti
	    // o ha lo stesso codice procedura di quello della procedura che si sta analizzando)
	    if (tipicontromovimentoTemp.getTipiprocedure().getId().getCodice() == null
		    || tipicontromovimentoTemp.getTipiprocedure().getId().getCodice() == tipiprocedureIniziale.getId().getCodice().shortValue()) {
		// creo id tipoMovimento
		TipimovimentoId id = new TipimovimentoId();
		id.setIdcomune(ORMHelper.getIdcomune());
		id.setTipomovimento(tipicontromovimentoTemp.getTipocontromovimento().getId().getTipomovimento());
		temp = tipiMovimentoDAO.findById(id);
		if (!isPresent(movimentiVisitati, temp)) {
		    movimentiVisitati.add(temp);
		    visitaCMov(temp, codiceprocedimento);
		}
	    }
	}
	return movimentiVisitati;
    }

    private boolean isPresent(Vector<Tipimovimento> movVisited, Tipimovimento mov) {

	boolean present = false;
	Tipimovimento temp;
	for (int i = 0; i < movVisited.size(); i++) {
	    temp = (Tipimovimento) movVisited.get(i);
	    if (temp.getId().getTipomovimento().equals(mov.getId().getTipomovimento())) {
		present = true;
		break;
	    }
	}
	return present;
    }

    private Vector<Tipicontromovimento> getContromovimenti(Tipimovimento movimento) {

	Vector<Tipicontromovimento> result = new Vector<Tipicontromovimento>();
	Set<Tipicontromovimento> listcontrmov = movimento.getTipicontromovimentos();
	for (Tipicontromovimento tipicontromovimento : listcontrmov) {
	    result.add(tipicontromovimento);
	}
	return result;
    }

    @Override
    public List<Tipimovimento> listMovimentiAssociatiAunProcedimento(Integer cod) {

	// serve per pulire la lista di movimenti visitati del procedimento precedente
	this.clearListaMovimentivisitati();
	Tipimovimento mov = alberoprocService.getMovimentoDefault(cod);
	List<Tipimovimento> listaMovimenti = new ArrayList<Tipimovimento>();
	// controlla se ha trovato un procedimento con una procedura collegata
	if (mov != null) {
	    visitaCMov(mov, cod);
	    Vector<Tipimovimento> list = getMovimentiVisitati();
	    for (int i = 0; i < list.size(); i++) {
		listaMovimenti.add((Tipimovimento) list.get(i));
	    }
	    listaMovimenti.add(0, mov);
	    // in caso non la trovi restituisci una lista nulla
	} else {
	    listaMovimenti = null;
	}
	return listaMovimenti;
    }

    public Vector<Tipimovimento> getMovimentiVisitati() {

	return movimentiVisitati;
    }

    @Override
    public void clearListaMovimentivisitati() {

	this.movimentiVisitati = new Vector<Tipimovimento>();
    }

    @Override
    public Tipimovimento getTipiMovimentoFlagCamcom() {

	return tipiMovimentoDAO.getTipiMovimentoFlagCamcom();
    }

    private boolean isInsertAllowed(Tipimovimento entity, boolean isUpdate) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!isUpdate) {
	    // controlla che già non esista un recordo con quel codice tipomovimento
	    // forzo il codice a maiuscolo la ricerca deve essere caseInsenitive
	    entity.getId().setTipomovimento(entity.getId().getTipomovimento().toUpperCase());
	    Tipimovimento objectDB = null;
	    objectDB = tipiMovimentoDAO.findById(entity.getId());
	    if (objectDB != null && !objectDB.getId().getTipomovimento().equals("")) {
		_ivs.add(new InvalidValue("service_error.duplicate_codice", null, null, entity.getId().getTipomovimento(), null));
	    }
	}
	if (entity.getFlgProtocollalinkall() && EntityUtils.getNestedProperty(entity.getLetteraTipoAllegati(), "id.codice") == null) {
	    _ivs.add(new InvalidValue("service_error.lettera_tipo_obbligatoria", null, null, entity.getLetteraTipoAllegati(), null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    protected boolean isDeleteAllowed(Tipimovimento entity) {

	Boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity != null && !entity.getTipimovStcAlberoprocs().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOV_STC_ALBEROPROC", null));
	}
	if (entity != null && !entity.getTipimovStcAltridatis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOV_STC_ALTRIDATI", null));
	}
	if (entity != null && !entity.getTipimovStcMappings().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOV_STC_MAPPING", null));
	}
	if (entity != null && !entity.getTipimovStcModellis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIMOV_STC_MODELLI", null));
	}
	if (entity != null && !entity.getProtocolloRegistris().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PROTOCOLLO_REGISTRI", null));
	}
	if (entity != null && !entity.getCommediliziePareriTmovs().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "COMMEDILIZIE_PARERI_TMOV", null));
	}
	if (entity != null && !entity.getTipicontromovimentos().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPICONTROMOVIMENTO", null));
	}
	if (entity != null && !entity.getTipimovimentos().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPICONTROMOVIMENTO", null));
	}
	if (entity != null && !entity.getInventarioprocedimentis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INVENTARIOPROCEDIMENTI", null));
	}
	if (entity != null && !entity.getTipiprocedureavvios().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIPROCEDUREAVVIO", null));
	}
	if (entity != null && !entity.getCommedilizieTipologiedetts().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "COMMEDILIZIE_TIPOLOGIEDETT", null));
	}
	if (entity != null && !entity.getOneritipirateizzaziones().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ONERITIPIRATEIZZAZIONE", null));
	}
	int c = movimentiService.countByTipimovimento(entity.getId().getTipomovimento());
	if (c > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI", null));
	}
	if (entity != null && !entity.getTempirispostas().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TEMPIRISPOSTA", null));
	}
	if (entity != null && !entity.getContromovimentotempirispostas().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TEMPIRISPOSTA", null));
	}
	List<Istanze> istanzes = istanzeService.findByTipimovimentoAvvio(entity.getId().getTipomovimento(), 0, 2);
	if (entity != null && !istanzes.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE", null));
	}
	if (!tipiprocedureService.findByTuttiCampiTipimovimento(entity.getId().getTipomovimento(), 0, 2).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIPROCEDURE", null));
	}
	// Controlla che il movimento non sia usato in nessuna ptocedura, come movimento che determina la data di validità dell'istanza legata alla procedura stessa
	List<Tipiprocedure> tipiproceduresMovimentoDatavalidita = tipiprocedureService
		.findByMovimetoDeterminazioneDataValiditaIstanza(entity.getId().getTipomovimento());
	if (entity != null && !tipiproceduresMovimentoDatavalidita.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIPROCEDURE", null));
	}
	if (entity != null && entity.getSistema() != null && entity.getSistema() == 1) {
	    _ivs.add(new InvalidValue("tipimovimento.service_error.tipo_mov_sistema", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    public List<Tipimovimento> findByTipimovAndResponsabile(String textToSearch, Integer codiceResponsabile, boolean includiDisabilitati) {

	if (codiceResponsabile == null) {
	    throw new RuntimeException("Il parametro codiceResponsabile è obbligatorio");
	}
	Responsabili responsabili = responsabiliService.findById(new PkId(codiceResponsabile));
	if (responsabili == null) {
	    throw new RuntimeException("Non è stato trovato nessun responsabile con il codice " + String.valueOf(codiceResponsabile));
	}
	List<Responsabilisoftware> rsoft = responsabilisoftwareService.findByResponsabile(responsabili);
	List<String> softList = new ArrayList<String>();
	for (Responsabilisoftware rs : rsoft) {
	    softList.add(rs.getId().getSoftware());
	}
	if (softList.isEmpty()) {
	    return new ArrayList<Tipimovimento>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.like("movimento", textToSearch));
	fr.addFilterField(FilterUtils.like("id.tipomovimento", textToSearch));
	ft.addRestriction(fr);
	FilterRestriction fr1 = new FilterRestriction();
	fr1.addFilterField(FilterUtils.in("software.codice", softList.toArray(), String.class));
	ft.addRestriction(fr1);
	if (includiDisabilitati == false) {
	    FilterRestriction fr2 = new FilterRestriction();
	    fr2.setAndOrRestriction(AndOrRestriction.OR);
	    fr2.addFilterField(FilterUtils.equals("flagDisabilitato", Boolean.FALSE, Boolean.class));
	    fr2.addFilterField(FilterUtils.isNull("flagDisabilitato"));
	    ft.addRestriction(fr2);
	}
	ft.addOrder(FilterUtils.orderAsc("movimento"));
	return tipiMovimentoDAO.findByFilterTable(ft);
    }

    @Override
    public List<Tipimovimento> findByLetteretipo(Letteretipo letteretipo, int firstResult, int maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("letteretipoId", letteretipo.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine", "software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "software"));
	ft.addOrder(FilterUtils.orderAsc("movimento"));
	return tipiMovimentoDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public boolean checkSeDisabilitare(Tipimovimento tm) {

	//	TIPIPROCEDURE.IDCONSMINISTRI 
	//	TIPIPROCEDURE.DETERMINAZIONEIDMOVIMENTO 
	//	TIPIPROCEDURE.IDAUTORICHIESTADOC 
	//	TIPIPROCEDURE.IDAUTOAUDIZTERZI 
	//	TIPIPROCEDURE.IDAUTOPUBBLICITA 
	//	TIPIPROCEDURE.IDAUTOSOSPENSIONE 
	//	TIPIPROCEDURE.IDAUTOCHIUSURACONTR 
	//	TIPIPROCEDURE.IDCHIUSURACDS 
	//	TIPIPROCEDURE.IDCOMUNCDS 
	//	TIPIPROCEDURE.IDESITOPROVVAUTORIZZATIVO 
	//	TIPIPROCEDURE.IDCHIUSURAISTANZA 
	//	TIPIPROCEDURE.IDTRASMNEGATIVA 
	List<Tipiprocedure> tipiprocedures = tipiprocedureService.findByTuttiCampiTipimovimento(tm.getId().getTipomovimento(), 0, 2);
	if (tipiprocedures.size() > 0) {
	    return false;
	}
	//	TIPIPROCEDUREAVVIO.TIPOMOVIMENTO 
	List<Tipiprocedureavvio> tipiprocedureavvios = tipiprocedureavvioService.findTipimovimento(tm.getId().getTipomovimento(), 0, 2);
	if (tipiprocedureavvios.size() > 0) {
	    return false;
	}
	//	COMMEDILIZIE_TIPOLOGIEDETT.TIPOMOVIMENTO 
	List<CommedilizieTipologiedett> commedilizieTipologiedetts = commedilizieTipologiedettService.findTipimovimento(tm.getId().getTipomovimento(),
		0, 2);
	if (commedilizieTipologiedetts.size() > 0) {
	    return false;
	}
	//	COMMEDILIZIE_TIPOPARERI.TIPOMOVIMENTO 
	List<CommedilizieTipopareri> commedilizieTipopareris = commedilizieTipopareriService
		.findConfigurazioniPerTipomovimento(tm.getId().getTipomovimento());
	if (!commedilizieTipopareris.isEmpty()) {
	    return false;
	}
	List<Inventarioprocedimenti> inventarioprocedimentis = inventarioprocedimentiService.findByTipimovimento(tm.getId().getTipomovimento(), 0, 2);
	//	INVENTARIOPROCEDIMENTI.TIPOMOVIMENTO 
	if (inventarioprocedimentis.size() > 0) {
	    return false;
	}
	//	INVENTARIOPROCEDIMENTISOFTWARE.TIPOMOVIMENTO 
	List<Inventarioprocedimentisoftware> inventarioprocedimentisoftwares = inventarioprocedimentisoftwareService
		.findByTipimovimento(tm.getId().getTipomovimento(), 0, 2);
	if (inventarioprocedimentisoftwares.size() > 0) {
	    return false;
	}
	//	ONERITIPIRATEIZZAZIONE.FK_TIPOMOV_DETERMDATAIN
	List<Oneritipirateizzazione> oneritipirateizzaziones = oneritipirateizzazioneService.findByTipimovimento(tm.getId().getTipomovimento(), 0, 2);
	if (oneritipirateizzaziones.size() > 0) {
	    return false;
	}
	//	PROTOCOLLO_REGISTRI.IDTIPOMOVIMENTO
	List<ProtocolloRegistri> protocolloRegistris = protocolloRegistriService.findByTipimovimento(tm.getId().getTipomovimento(), 0, 2);
	if (protocolloRegistris.size() > 0) {
	    return false;
	}
	//	TIPICONTROMOVIMENTO.TIPOCONTROMOVIMENTO
	List<Tipicontromovimento> tipicontromovimentomovs = tipicontromovimentoService.findByTipimovimento(tm.getId().getTipomovimento(), 0, 2);
	if (tipicontromovimentomovs.size() > 0) {
	    return false;
	}
	//	TIPICONTROMOVIMENTO.TIPOMOVIMENTO 
	List<Tipicontromovimento> tipicontromovimentocontros = tipicontromovimentoService.findByTipicontromovimento(tm.getId().getTipomovimento(), 0,
		2);
	if (tipicontromovimentocontros.size() > 0) {
	    return false;
	}
	return true;
    }

    @Override
    public boolean getFlagNoamminterna(String tipomovimento) {

	return tipiMovimentoDAO.getFlagNoamminterna(tipomovimento);
    }

    @Override
    public List<Tipimovimento> findByDescrizionePerTuttiISoftware(Tipimovimento tipoMovimento, Boolean includiDisabilitate) {

	return tipiMovimentoDAO.findByDescrizione(tipoMovimento, includiDisabilitate, null);
    }

    @Override
    public Tipimovimento findTipimovimentoBySoggettiEsterniAndRichiestaIntegrazioniAndCodiceIstanza(Integer codiceIstanza, String idcomune) {

	return tipiMovimentoDAO.findTipimovimentoBySoggettiEsterniAndRichiestaIntegrazioniAndCodiceIstanza(codiceIstanza, idcomune);
    }
}