package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiContiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneContiMercato;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiContiHelper;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiContiService;
import it.gruppoinit.pal.gp.core.service.MercatiDContiService;
import it.gruppoinit.pal.gp.core.service.MercatiService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatiContiServiceImpl extends BaseServiceImpl<MercatiConti, PkId> implements MercatiContiService {

    private static final Logger log = LoggerFactory.getLogger(MercatiContiServiceImpl.class);
    private MercatiContiDAO mercatiContiDAO;

    @Autowired
    public void setMercatiContiDAO(MercatiContiDAO mercatiContiDAO) {

	this.mercatiContiDAO = mercatiContiDAO;
    }

    private MercatiService mercatiService;

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    private MercatiDContiService mercatiDContiService;

    @Autowired
    public void setMercatiDContiService(MercatiDContiService mercatiDContiService) {

	this.mercatiDContiService = mercatiDContiService;
    }

    private ContiService contiService;

    @Autowired
    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_MERCATICONTI" })
    @Override
    public void delete(MercatiConti entity) {

	mercatiContiDAO.delete(entity);
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_MERCATICONTI" })
    @Override
    public List<MercatiConti> findAll(Integer firstResult, Integer maxResult) {

	return mercatiContiDAO.findAll(firstResult, maxResult);
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_MERCATICONTI" })
    @Override
    public MercatiConti findById(PkId id) {

	return mercatiContiDAO.findById(id);
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_MERCATICONTI" })
    @Override
    public void insert(MercatiConti entity) {

	if (validateEntity(entity)) {
	    if (validateDuplicateLine(entity, true)) {
		mercatiContiDAO.insert(entity);
	    }
	}
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_MERCATICONTI" })
    @Override
    public void update(MercatiConti entity) {

	if (validateEntity(entity)) {
	    if (validateDuplicateLine(entity, false)) {
		mercatiContiDAO.update(entity);
	    }
	}
    }

    @Override
    protected Class<MercatiConti> getEntityClass() {

	return MercatiConti.class;
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_MERCATICONTI" })
    @Override
    public List<MercatiConti> findByMercati(MercatiConti entity) {

	return mercatiContiDAO.findByMercati(entity);
    }

    /**
     * Validazione ulteriore per evitare record duplicati non è possibile inserire due righe per lo stesso mercato che
     * abbiano lo stesso codice conto e lo stesso anno
     * 
     * @param entity
     * @param isInsert
     *            se false allora deve saltare il controllo sullo stesso record
     * @return
     */
    private boolean validateDuplicateLine(MercatiConti entity, boolean isInsert) {

	List<MercatiConti> listMc = mercatiContiDAO.findByMercati(entity);
	Integer anno = entity.getAnno();
	Conti conto = entity.getConti();
	// Controllo che non ci sia per quel mercato due righe replicate ossia con lo stesso anno e conto
	// Nullpointer exception evitato perché le proprietà da controllare sono state validate dal metodo validate
	// che controlla le proprietà dell'oggetto di dominio
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	boolean isValid = true;
	for (MercatiConti mercatiConti : listMc) {
	    if (anno.intValue() == mercatiConti.getAnno().intValue()) {
		if (conto.getId().getCodice().intValue() == mercatiConti.getConti().getId().getCodice().intValue()) {
		    if (mercatiConti.getContesto().equals(entity.getContesto())) {
			// se non sono in inserimento ed aggiorno devo controllare che non sto
			// duplicando
			// un record già inserito
			if (!isInsert) {
			    if (entity.getId().getCodice().intValue() != mercatiConti.getId().getCodice().intValue()) {
				InvalidValue iv = new InvalidValue("errors.validator.mercaticonti.duplicate", entity.getClass(), "conti", conto,
					entity);
				_ivs.add(iv);
				isValid = false;
				break;
			    }
			} else {
			    InvalidValue iv = new InvalidValue("errors.validator.mercaticonti.duplicate", entity.getClass(), "conti", conto, entity);
			    _ivs.add(iv);
			    isValid = false;
			    break;
			}
		    }
		}
	    }
	}
	if (!(isValid)) {
	    this.throwValidationMessages(_ivs);
	}
	return isValid;
    }

    @Override
    public void sistemaContiPerAnno(ConfigurazioneContiMercato configurazioneContiMercato) {

	Mercati mercato = mercatiService.findById(configurazioneContiMercato.getMercati().getId());
	Integer anno = configurazioneContiMercato.getAnno();
	List<MercatiContiHelper> mercatiContiHelperList = configurazioneContiMercato.getContiHelperList();
	MercatiConti mercatiConti = null;
	for (MercatiContiHelper mercatiContiHelper : mercatiContiHelperList) {
	    if (mercatiContiHelper.isUsa()) {
		Conti contoNew = contiService.findById(mercatiContiHelper.getContoNew().getId());
		mercatiConti = new MercatiConti();
		mercatiConti.setAnno(anno);
		mercatiConti.setConti(contoNew);
		mercatiConti.setMercati(mercato);
		mercatiConti.setContesto(mercatiContiHelper.getMercatiConti().getContesto());
		mercatiConti.setFlagCanone(mercatiContiHelper.getMercatiConti().getFlagCanone());
		mercatiConti.setFlagValore(mercatiContiHelper.getMercatiConti().getFlagValore());
		BigDecimal importo = mercatiContiHelper.getMercatiConti().getValore();
		importo = importo.multiply(mercatiContiHelper.getMoltiplicatore());
		mercatiConti.setValore(importo);
		this.insert(mercatiConti);
	    }
	}
	Set<MercatiD> posteggis = mercato.getMercatiDs();
	// ..per ogni posteggio devo configurare i conti per l'anno passato con il moltiplicatore e la definizione di
	// ..conto configurati per l'utente
	// ..quindi è necessario per ogni riga di conto di ogni posteggio, se presente, controllare che sia uguale al
	// .. vecchio conto e con anno = annopassato-1 ed aggiornare l'importo ed il conto con nuovo conto
	// ..
	for (MercatiD posteggio : posteggis) { // per tutti i posteggi
	    Set<MercatiDConti> posteggiconti = posteggio.getListaContiPosteggio();
	    MercatiDConti contoPosteggio = null;
	    for (MercatiDConti mercatiDConti : posteggiconti) { // per tutti i conti del posteggio
		for (MercatiContiHelper mercatiContiHelper : mercatiContiHelperList) { // .. per ogni conto configurato
		    // dall'utente
		    if (log.isDebugEnabled()) {
			log.debug("posteggio:" + posteggio.getCodiceposteggio());
			log.debug("conto:" + mercatiDConti.getConto().getDescrizione());
			log.debug("anno:" + mercatiDConti.getAnno());
			log.debug("helper conto old:" + mercatiContiHelper.getContoOld().getDescrizione());
			log.debug("helper conto new:" + mercatiContiHelper.getContoNew().getDescrizione());
			log.debug("helper usa:" + mercatiContiHelper.isUsa());
			log.debug("mercatiContiHelper.getContoNew().getId().getCodice().intValue():"
				+ mercatiContiHelper.getContoNew().getId().getCodice().intValue());
			log.debug("mercatiContiHelper.getContoOld().getId().getCodice().intValue():"
				+ mercatiContiHelper.getContoOld().getId().getCodice().intValue());
			log.debug("mercatiDConti.getConto().getId().getCodice().intValue():"
				+ mercatiDConti.getConto().getId().getCodice().intValue());
		    }
		    if (mercatiContiHelper.isUsa()) { // .. flag usa = true
			if (mercatiContiHelper.getContoOld().getId().getCodice().intValue() == mercatiDConti.getConto().getId().getCodice()
				.intValue()) { // e il conto è tra quelli configurati
			    if (mercatiContiHelper.getMercatiConti().getContesto().equals(mercatiDConti.getContesto())) {
				int annoprecedente = anno.intValue() - 1;
				if (mercatiDConti.getAnno().intValue() == annoprecedente) { // .. e l'anno è
				    // quello precedente
				    contoPosteggio = new MercatiDConti();
				    contoPosteggio.setAnno(anno.shortValue());
				    contoPosteggio.setContesto(mercatiDConti.getContesto());
				    Conti contoNew = contiService.findById(mercatiContiHelper.getContoNew().getId());
				    contoPosteggio.setConto(contoNew);
				    contoPosteggio.setPosteggio(posteggio);
				    contoPosteggio.setFlagCanone(mercatiDConti.getFlagCanone());
				    contoPosteggio.setFlagValore(mercatiDConti.getFlagValore());
				    BigDecimal importo = mercatiDConti.getValore();
				    importo = importo.multiply(mercatiContiHelper.getMoltiplicatore());
				    contoPosteggio.setValore(importo);
				    mercatiDContiService.insert(contoPosteggio);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_MERCATICONTI" })
    public List<MercatiConti> findByMercatiAndAnno(Mercati mercati, Integer anno) {

	return mercatiContiDAO.findByMercatiAndAnno(mercati, anno);
    }

    @Override
    public void adeguaPercentualeIstat(Mercati mercato, Integer anno_precedente, Integer anno_da_adeguare,
	    Map<Integer, Integer> vecchioContoNuovoConto, BigDecimal coefficiente_adeguamento) {

	List<MercatiConti> mercatiContis = this.findByMercatiAndAnno(mercato, anno_precedente);
	for (MercatiConti mercatiConti : mercatiContis) {
	    MercatiConti mc = new MercatiConti();
	    mc.setAnno(anno_da_adeguare);
	    mc.setContesto(mercatiConti.getContesto());
	    Conti c = recuperaConto(mercatiConti.getConti(), vecchioContoNuovoConto);
	    mc.setConti(c);
	    mc.setFlagCanone(mercatiConti.getFlagCanone());
	    mc.setFlagImportomensile(mercatiConti.getFlagImportomensile());
	    mc.setFlagValore(mercatiConti.getFlagValore());
	    mc.setMercati(mercato);
	    mc.setPercentualeConsorzio(mercatiConti.getPercentualeConsorzio());
	    mc.setValore(adeguaValore(mercatiConti.getValore(), coefficiente_adeguamento));
	    this.insert(mc);
	}
	List<MercatiDConti> mercatidContis = mercatiDContiService.findByMercatiAndAnno(mercato, anno_precedente);
	for (MercatiDConti mercatiDConti : mercatidContis) {
	    MercatiDConti mc = new MercatiDConti();
	    mc.setAnno(anno_da_adeguare.shortValue());
	    mc.setContesto(mercatiDConti.getContesto());
	    Conti c = recuperaConto(mercatiDConti.getConto(), vecchioContoNuovoConto);
	    mc.setConto(c);
	    mc.setFlagCanone(mercatiDConti.getFlagCanone());
	    mc.setFlagImportomensile(mercatiDConti.getFlagImportomensile());
	    mc.setFlagValore(mercatiDConti.getFlagValore());
	    mc.setPosteggio(mercatiDConti.getPosteggio());
	    mc.setPercentualeConsorzio(mercatiDConti.getPercentualeConsorzio());
	    mc.setValore(adeguaValore(mercatiDConti.getValore(), coefficiente_adeguamento));
	    mercatiDContiService.insert(mc);
	}
    }

    private BigDecimal adeguaValore(BigDecimal valore, BigDecimal coefficiente_adeguamento) {

	BigDecimal moltiplicando = coefficiente_adeguamento.divide(BigDecimal.valueOf(100));
	BigDecimal result = valore.multiply(moltiplicando).add(valore).setScale(5, BigDecimal.ROUND_HALF_UP);
	return result;
    }

    private Conti recuperaConto(Conti conti, Map<Integer, Integer> vecchioContoNuovoConto) {

	Integer codiceVecchioConto = conti.getId().getCodice();
	Integer nuovoConto = vecchioContoNuovoConto.get(codiceVecchioConto);
	Conti result = contiService.findById(new PkId(nuovoConto));
	if (result == null) {
	    Conti c = contiService.findById(new PkId(codiceVecchioConto));
	    throw new RuntimeException("Non è stato mappato correttamente il nuovo conto per il conto [" + c.getDescrizioneConto() + "]");
	}
	return result;
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_MERCATICONTI" })
    @Override
    public List<MercatiConti> findByCodiceMercato(Integer codiceMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	ft.addRestriction(fr);
	return mercatiContiDAO.findByFilterTable(ft);
    }
}
