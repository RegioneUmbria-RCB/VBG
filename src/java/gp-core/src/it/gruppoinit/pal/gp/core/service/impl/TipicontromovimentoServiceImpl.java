package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipicontromovimentoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.TempirispostaId;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TempirispostaService;
import it.gruppoinit.pal.gp.core.service.TipicontromovimentoService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipicontromovimentoServiceImpl extends BaseServiceImpl<Tipicontromovimento, PkId> implements TipicontromovimentoService {

    private static final Logger log = LoggerFactory.getLogger(TipicontromovimentoServiceImpl.class);
    private TipicontromovimentoDAO tipicontromovimentoDAO;
    private TempirispostaService tempirispostaService;

    @Autowired
    public void setTipicontromovimentoDAO(TipicontromovimentoDAO tipicontromovimentoDAO) {

	this.tipicontromovimentoDAO = tipicontromovimentoDAO;
    }

    @Autowired
    public void setTempirispostaService(TempirispostaService tempirispostaService) {

	this.tempirispostaService = tempirispostaService;
    }

    @Override
    protected Class<Tipicontromovimento> getEntityClass() {

	return Tipicontromovimento.class;
    }

    @Override
    public void delete(Tipicontromovimento entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    tipicontromovimentoDAO.delete(entity);
	}
    }

    @Override
    public List<Tipicontromovimento> findAll(Integer firstResult, Integer maxResult) {

	return tipicontromovimentoDAO.findAll(null, null);
    }

    @Override
    public Tipicontromovimento findById(PkId id) {

	return tipicontromovimentoDAO.findById(id);
    }

    @Override
    public void insert(Tipicontromovimento entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    tipicontromovimentoDAO.insert(entity);
	}
    }

    @Override
    public void update(Tipicontromovimento entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    tipicontromovimentoDAO.update(entity);
	}
    }

    @Override
    public void updateAndEliminaTempiRispostaNonValidi(Tipicontromovimento entity) {

	this.update(entity);
	// Elimina tempi di risposta non più validi
	Integer codiceAmministrazione = null;
	Integer codiceProcedura = null;
	if (EntityUtils.getNestedProperty(entity.getAmministrazioniTipiMovimento(), "id.codice") != null) {
	    codiceAmministrazione = entity.getAmministrazioniTipiMovimento().getId().getCodice();
	}
	if (EntityUtils.getNestedProperty(entity.getTipiprocedure(), "id.codice") != null) {
	    codiceProcedura = entity.getTipiprocedure().getId().getCodice();
	}
	List<Tempirisposta> tempirispostas = tempirispostaService.findByTipimovimentoAndTipicontromovimento(entity.getTipomovimento().getId()
		.getTipomovimento(), entity.getTipocontromovimento().getId().getTipomovimento());
	for (Tempirisposta tempirisposta : tempirispostas) {
	    boolean isCancella = false;
	    if (codiceAmministrazione != null) {
		// Controllo se l'amministrazione coincide
		if (!tempirisposta.getAmministrazione().getId().getCodice().equals(codiceAmministrazione)) {
		    isCancella = true;
		}
	    }
	    if (!isCancella && codiceProcedura != null) {
		tempirisposta.getTipiprocedure();
		// Controllo se la procedura coincide
		if (!tempirisposta.getTipiprocedure().getId().getCodice().equals(codiceProcedura)) {
		    isCancella = true;
		}
	    }
	    if (isCancella) {
		tempirispostaService.delete(tempirisposta);
	    }
	}
    }

    @Override
    public void aggiornaTipocontromovimento(Tipicontromovimento entity) {

	Tipicontromovimento tipicontromovimentoDB = tipicontromovimentoDAO.findById(entity.getId());
	String codiceNuovoTipoControMov = verificaControMovimentoModificato(entity, tipicontromovimentoDB);
	if (StringUtils.isNotBlank(codiceNuovoTipoControMov)) {
	    List<Tempirisposta> tempirispostas = tempirispostaService.findByTipimovimentoAndTipicontromovimento(tipicontromovimentoDB
		    .getTipomovimento().getId().getTipomovimento(), tipicontromovimentoDB.getTipocontromovimento().getId().getTipomovimento());
	    for (Tempirisposta tempirisposta : tempirispostas) {
		tempirispostaService.delete(tempirisposta);
		TempirispostaId id = new TempirispostaId();
		Tempirisposta tmp = new Tempirisposta();
		if (tempirisposta.getAmministrazione() != null) {
		    id.setCodiceamministrazione(tempirisposta.getAmministrazione().getId().getCodice());
		    tmp.setAmministrazione(tempirisposta.getAmministrazione());
		}
		if (tempirisposta.getTipiprocedure() != null) {
		    id.setCodiceprocedura(tempirisposta.getTipiprocedure().getId().getCodice());
		    tmp.setTipiprocedure(tempirisposta.getTipiprocedure());
		}
		id.setTipocontromovimento(entity.getTipocontromovimento().getId().getTipomovimento());
		id.setTipomovimento(tempirisposta.getTipimovimento().getId().getTipomovimento());
		tmp.setTipicontromovimento(entity.getTipocontromovimento());
		tmp.setTipimovimento(tempirisposta.getTipimovimento());
		tmp.setId(id);
		if (tempirisposta.getAttesa() != null) {
		    tmp.setAttesa(tempirisposta.getAttesa());
		}
		tmp.setCalcoladainizioistanza(BooleanUtils.toBoolean(tempirisposta.getCalcoladainizioistanza()));
		tempirispostaService.insert(tmp);
	    }
	}
	this.update(entity);
    }

    private String verificaControMovimentoModificato(Tipicontromovimento entity, Tipicontromovimento tipicontromovimentoDB) {

	if (entity.getTipocontromovimento() != null) {
	    String nuovoCodiceMov = entity.getTipocontromovimento().getId().getTipomovimento();
	    String vecchioCodiceMov = tipicontromovimentoDB.getTipocontromovimento().getId().getTipomovimento();
	    // Controllo se il contro movimento è cambiato
	    if (!nuovoCodiceMov.equals(vecchioCodiceMov)) {
		log.debug("verificaControMovimentoModificato# Il codice del contro movimento è cambiato. Nuovo: {} - Vecchio: {}", new Object[] {
			nuovoCodiceMov, vecchioCodiceMov });
		return nuovoCodiceMov;
	    }
	}
	log.debug("verificaControMovimentoModificato# Il codice del contro movimento non è cambiato. ");
	return null;
    }

    private void dataIntegration(Tipicontromovimento entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro tipicontromovimento è nullo");
	}
	if (StringUtils.isBlank(entity.getPropostostc())) {
	    entity.setPropostostc("0");
	}
    }

    @Override
    public List<Tipicontromovimento> findTipicontromovimentiByAmministrazioniTipiContromovimento(Amministrazioni amministrazioni) {

	return tipicontromovimentoDAO.findTipicontromovimentiByAmministrazioniTipiContromovimento(amministrazioni);
    }

    @Override
    public List<Tipicontromovimento> findTipicontromovimentiByAmministrazioniTipiMovimento(Amministrazioni amministrazioni) {

	return tipicontromovimentoDAO.findTipicontromovimentiByAmministrazioniTipiMovimento(amministrazioni);
    }

    @Override
    public Tipicontromovimento findByTipoMovimentoAndTipoContromovimento(Tipimovimento tipomovimento, Tipimovimento tipocontromovimento) {

	return tipicontromovimentoDAO.findByTipoMovimentoAndTipoContromovimento(tipomovimento, tipocontromovimento);
    }

    protected boolean isDeleteAllowed(Tipicontromovimento entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    protected boolean isInsertAllowed(Tipicontromovimento entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// controllo che non sto inserendo come contro movimento il movimento in esame
	//	if (entity.getTipomovimento().getId().getTipomovimento().equals(entity.getTipocontromovimento().getId().getTipomovimento())) {
	//	    _ivs.add(new InvalidValue("tipimovimento.service_error.movimento_corrente", null, null, null, null));
	//	}
	if (EntityUtils.getNestedProperty(entity.getAmministrazioniTipiContromovimento(), "id.codice") != null) {
	    if (entity.getAmministrazioniTipiContromovimento().getId().getCodice().intValue() == WebConstants.CODICETUTTEAMMINISTRAZIONI) {
		_ivs.add(new InvalidValue("tipimovimento.service_error.amministrazione_contro_mov_non_accettata", null, null, null, null));
	    }
	}
	if (EntityUtils.getNestedProperty(entity, "amministrazioniTipiMovimento.id.codice") != null) {
	    if (entity.getAmministrazioniTipiMovimento().getId().getCodice().intValue() == WebConstants.CODICELASTESSAAMMINISTRAZIONE) {
		_ivs.add(new InvalidValue("tipimovimento.service_error.amministrazione_mov_non_accettata", null, null, null, null));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    public List<Tipicontromovimento> findByTipimovimento(Tipimovimento tipomovimento) {

	String codicemov = "";
	if (tipomovimento != null) {
	    if (tipomovimento.getId() != null) {
		codicemov = tipomovimento.getId().getTipomovimento();
	    }
	}
	return findByTipimovimento(codicemov, null, null);
    }

    @Override
    public List<Tipicontromovimento> findByContromovimento(Tipimovimento controMovimento) {

	String codicemov = "";
	if (controMovimento != null) {
	    if (controMovimento.getId() != null) {
		codicemov = controMovimento.getId().getTipomovimento();
	    }
	}
	return findByTipicontromovimento(codicemov, null, null);
    }

    protected void childDelete(Tipicontromovimento entity) {

	// Cancellazione Tempirisposta
	List<Tempirisposta> tempirispostas = tempirispostaService.findByTipimovimentoAndTipicontromovimento(entity.getTipomovimento().getId()
		.getTipomovimento(), entity.getTipocontromovimento().getId().getTipomovimento());
	for (Tempirisposta tempirisposta : tempirispostas) {
	    tempirispostaService.delete(tempirisposta);
	}
    }

    @Override
    public void deleteTempirisposta(Tipicontromovimento entity) {

	// Cancellazione Tempirisposta
	List<Tempirisposta> tempirispostas = tempirispostaService.findByTipimovimentoAndTipicontromovimento(entity.getTipomovimento().getId()
		.getTipomovimento(), entity.getTipocontromovimento().getId().getTipomovimento());
	for (Tempirisposta tempirisposta : tempirispostas) {
	    tempirispostaService.delete(tempirisposta);
	}
    }

    @Override
    public List<Tipicontromovimento> findByTipiprocedure(Integer codiceProcedura, Integer firstResult, Integer maxResult) {

	if (codiceProcedura == null) {
	    throw new IllegalArgumentException("Il parametro codiceProcedura non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("tipiprocedureId", codiceProcedura, Integer.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("procedura", "tipiprocedure"));
	ft.addOrder(FilterUtils.orderAsc("movimento", "tipomovimento"));
	List<Tipicontromovimento> list = tipicontromovimentoDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Tipicontromovimento> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(tipomovimento)) {
	    throw new IllegalArgumentException("Il parametro tipo movimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("tipomovimentoId", tipomovimento, String.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("ordine", "tipomovimento.software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "tipomovimento.software"));
	ft.addOrder(FilterUtils.orderAsc("procedura", "tipiprocedure"));
	ft.addOrder(FilterUtils.orderAsc("movimento", "tipomovimento"));
	List<Tipicontromovimento> list = tipicontromovimentoDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Tipicontromovimento> findByTipicontromovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(tipomovimento)) {
	    throw new IllegalArgumentException("Il parametro tipo movimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("tipocontromovimentoId", tipomovimento, String.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("ordine", "tipomovimento.software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "tipomovimento.software"));
	ft.addOrder(FilterUtils.orderAsc("procedura", "tipiprocedure"));
	ft.addOrder(FilterUtils.orderAsc("movimento", "tipomovimento"));
	List<Tipicontromovimento> list = tipicontromovimentoDAO.findByFilterTable(ft);
	return list;
    }
}
