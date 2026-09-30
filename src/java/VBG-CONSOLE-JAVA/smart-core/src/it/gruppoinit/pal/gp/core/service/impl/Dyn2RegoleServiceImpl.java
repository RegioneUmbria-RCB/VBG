/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.Dyn2EspressioniDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2RegoleDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService;
import it.gruppoinit.pal.gp.core.service.regole.Dyn2RegoleHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francol
 * 
 */
@Service
public class Dyn2RegoleServiceImpl extends BaseServiceImpl<Dyn2Regole, PkId> implements Dyn2RegoleService {

    private static final Logger log = LoggerFactory.getLogger(Dyn2RegoleServiceImpl.class);
    private Dyn2RegoleDAO dyn2RegoleDAO;
    private Dyn2EspressioniDAO dyn2EspressioniDAO;
    private Dyn2ModellidService dyn2ModellidService;

    /**
     * 
     */
    public Dyn2RegoleServiceImpl() {

    }

    @Autowired
    public void setDyn2RegoleDAO(Dyn2RegoleDAO dyn2RegoleDAO) {

	this.dyn2RegoleDAO = dyn2RegoleDAO;
    }

    @Autowired
    public void setDyn2EspressioniDAO(Dyn2EspressioniDAO dyn2EspressioniDAO) {

	this.dyn2EspressioniDAO = dyn2EspressioniDAO;
    }

    @Autowired
    public void setDyn2ModellidService(Dyn2ModellidService dyn2ModellidService) {

	this.dyn2ModellidService = dyn2ModellidService;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#insert(java.lang.Object)
     */
    @Override
    public void insert(Dyn2Regole entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Dyn2Espressioni> exprs = entity.getDyn2Espressionis();
	    entity.setDyn2Espressionis(new HashSet<Dyn2Espressioni>());
	    dyn2RegoleDAO.insert(entity);
	    for (Dyn2Espressioni expr : exprs) {
		expr.setDyn2Regole(entity);
		dyn2EspressioniDAO.insert(expr);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("insert - inserita nuova regola: {}, PK={}, composta da {} espressioni",
			new Object[] { entity.getDescrizione(), entity.getId(), exprs.size() });
	    }
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#update(java.lang.Object)
     */
    @Override
    public void update(Dyn2Regole entity) {

	childDataUpdate(entity);
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Dyn2Espressioni> exprs = entity.getDyn2Espressionis();
	    if (validateEspressioni(exprs)) {
		entity.setDyn2Espressionis(new HashSet<Dyn2Espressioni>());
		dyn2RegoleDAO.update(entity);
		for (Dyn2Espressioni expr : exprs) {
		    expr.setDyn2Regole(entity);
		    dyn2EspressioniDAO.insertOrUpdate(expr, expr.getId(), true);
		}
		if (log.isDebugEnabled()) {
		    log.debug("update - aggiornata regola: {}, PK={}, composta da {} espressioni",
			    new Object[] { entity.getDescrizione(), entity.getId(), exprs.size() });
		}
	    }
	}
    }

    @Override
    public void deleteAndupdate(Dyn2Regole entity, List<Dyn2Espressioni> espressioniDaCancellare) {

	for (Dyn2Espressioni dyn2Espressioni : espressioniDaCancellare) {
	    dyn2EspressioniDAO.delete(dyn2Espressioni);
	}
	this.update(entity);
    }

    private boolean validateEspressioni(Set<Dyn2Espressioni> exprs) {

	for (Dyn2Espressioni dyn2Espressioni : exprs) {
	    if (EntityUtils.getNestedProperty(dyn2Espressioni.getDyn2Campi(), "id.codice") == null
		    && StringUtils.isBlank(dyn2Espressioni.getIdSemanticoCart()) && StringUtils.isBlank(dyn2Espressioni.getAttributoStc())) {
		this.throwValidationMessage(new InvalidValue("service_error.c_dinamico_id_semantico_attr_stc", Dyn2Regole.class, null, null, null));
	    }
	}
	return true;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#delete(java.lang.Object)
     */
    @Override
    public void delete(Dyn2Regole entity) {

	childDataDelete(entity);
	if (isDeleteAllowed(entity)) {
	    dyn2RegoleDAO.delete(entity);
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<Dyn2Regole> findAll(Integer firstResult, Integer maxResult) {

	return this.dyn2RegoleDAO.findAll(firstResult, maxResult);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findById(java.io.Serializable)
     */
    @Override
    public Dyn2Regole findById(PkId id) {

	return this.dyn2RegoleDAO.findById(id);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl#getEntityClass()
     */
    @Override
    protected Class<Dyn2Regole> getEntityClass() {

	return Dyn2Regole.class;
    }

    @Override
    public List<Dyn2Regole> findRegoleDipendentiDaCampoInModello(Integer idCampo, Integer idModellot) {

	return this.dyn2RegoleDAO.findByCampoDinamicoInModello(idCampo, idModellot);
    }

    @Override
    public List<Dyn2Regole> findByDescrizioneAndSoftware(String textToSearch, String _codicesoftware) {

	return dyn2RegoleDAO.findByDescrizioneAndSoftware(textToSearch, _codicesoftware);
    }

    @Override
    public List<Dyn2RegoleSyntaxError> validateExpressionSyntax(Dyn2Regole regola) {

	List<Dyn2RegoleSyntaxError> errs = new ArrayList<Dyn2RegoleSyntaxError>();
	Iterator<Dyn2Espressioni> exprIter = regola.getDyn2Espressionis().iterator();
	Dyn2RegoleSyntaxError error = null;
	int index = 0;
	int pOpen = 0;
	int pClosed = 0;
	Integer progressivo = null;
	Integer ultimoProgressivo = null;
	String opConf = null;
	String valConf = null;
	while (exprIter.hasNext()) {
	    Dyn2Espressioni expr = (Dyn2Espressioni) exprIter.next();
	    if (index > 0) {
		//1: tutte le espressioni tranne la prima devono avere un operatore logico
		if (StringUtils.isBlank(expr.getOperatoreLogico())) {
		    error = new Dyn2RegoleSyntaxError("dyn2regole.error.missing_operatorelogico");
		    error.setExpressionIndex(index);
		    error.addWrongExpressionProperty("operatoreLogico");
		    errs.add(error);
		}
	    } else {
		//1: la prima espressione non deve avere un operatore logico
		if (StringUtils.isNotBlank(expr.getOperatoreLogico())) {
		    error = new Dyn2RegoleSyntaxError("dyn2regole.error.first_operatorelogico");
		    error.setExpressionIndex(index);
		    error.addWrongExpressionProperty("operatoreLogico");
		    errs.add(error);
		}
	    }
	    String stcAttribute = expr.getAttributoStc();
	    Dyn2Campi campo = expr.getDyn2Campi();
	    boolean noAttribute = StringUtils.isBlank(stcAttribute) && (campo == null || campo.getId().getCodice() == null);
	    boolean doubleAttribute = StringUtils.isNotBlank(stcAttribute) && campo != null && campo.getId().getCodice() != null;
	    if (noAttribute || doubleAttribute) {
		//2: in ciascuna espressione almeno uno fra FK_CAMPO, ATTRIBUTO_STC deve essere valorizzato
		error = new Dyn2RegoleSyntaxError("dyn2regole.error.missing_campoattributo");
		error.setExpressionIndex(index);
		error.addWrongExpressionProperty("dyn2Campi");
		error.addWrongExpressionProperty("attributoStc");
		errs.add(error);
	    }
	    progressivo = expr.getProgressivo();
	    if (progressivo == null) {
		//3: il progressivo deve essere sempre valorizzato
		error = new Dyn2RegoleSyntaxError("dyn2regole.error.missing_progressivo");
		error.setExpressionIndex(index);
		error.addWrongExpressionProperty("progressivo");
		errs.add(error);
	    } else if (ultimoProgressivo != null) {
		if (progressivo.equals(ultimoProgressivo)) {
		    //3: il progressivo deve essere sempre diverso
		    error = new Dyn2RegoleSyntaxError("dyn2regole.error.duplicated_progressivo");
		    error.setExpressionIndex(index);
		    error.addWrongExpressionProperty("progressivo");
		    errs.add(error);
		} else if (progressivo.compareTo(ultimoProgressivo) < 0) {
		    //3: Le espressioni devono essere sempre ordinate per progressivo
		    log.error("Impossibile validare la regola dinamica {} perchè le espressioni non sono ordinate per progressivo.",
			    new Object[] { regola.getId() });
		    throw new RuntimeException("Impossibile validare la regola dinamica perchè le espressioni non sono ordinate per progressivo.");
		    //TODO non lanciare eccezione ma fare solo il log.error.
		}
	    }
	    opConf = expr.getOperatoreConfronto();
	    valConf = expr.getValoreConfronto();
	    if (StringUtils.isBlank(opConf)) {
		//4: l'operatore di confronto deve essere specificato
		error = new Dyn2RegoleSyntaxError("dyn2regole.error.missing_operatoreconfronto");
		error.setExpressionIndex(index);
		error.addWrongExpressionProperty("operatoreConfronto");
		errs.add(error);
	    } else if (!opConf.equals(OperatoreConfrontoEnum.IS_NULL.toString()) && !opConf.equals(OperatoreConfrontoEnum.NOT_IS_NULL.toString())) {
		//5: se l'operatore di confronto è diverso da IS_NULL e da NOT_IS_NULL allora il valore deve essere specificato
		if (StringUtils.isBlank(valConf)) {
		    error = new Dyn2RegoleSyntaxError("dyn2regole.error.missing_valoreconfronto");
		    error.setExpressionIndex(index);
		    error.addWrongExpressionProperty("valoreConfronto");
		    errs.add(error);
		}
	    }
	    //6: se l'operatore di confronto è MATCHES o NOT_MATCHES il campo valore deve contenere una stringa valida come regex java
	    if (opConf.equals(OperatoreConfrontoEnum.MATCHES.toString()) || opConf.equals(OperatoreConfrontoEnum.NOT_MATCHES)) {
		try {
		    if (StringUtils.isNotBlank(valConf)) {
			Pattern.compile(valConf);
		    } else {
			error = new Dyn2RegoleSyntaxError("dyn2regole.error.missing_valoreconfronto");
			error.setExpressionIndex(index);
			error.addWrongExpressionProperty("valoreConfronto");
			errs.add(error);
		    }
		} catch (PatternSyntaxException e) {
		    error = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_valoreconfronto_regex");
		    error.setExpressionIndex(index);
		    error.addWrongExpressionProperty("valoreConfronto");
		    errs.add(error);
		}
	    }
	    if (StringUtils.isNotBlank(expr.getParentesiAperta())) {
		pOpen += Dyn2RegoleHelper.contaParentesi(expr.getParentesiAperta());
	    }
	    if (StringUtils.isNotBlank(expr.getParentesiChiusa())) {
		pClosed += Dyn2RegoleHelper.contaParentesi(expr.getParentesiChiusa());
	    }
	    //8: il valore immesso in ATTRIBUTO_STC deve essere un'espressione XPath valida per l'XML del messaggio di inserimentoPratica di STC
	    if (StringUtils.isNotBlank(stcAttribute)) {
		if (!validateStcXPath(stcAttribute)) {
		    error = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_attributostc");
		    error.setExpressionIndex(index);
		    error.addWrongExpressionProperty("attributoStc");
		    errs.add(error);
		}
	    }
	    index++;
	}
	if (pOpen > pClosed) {
	    //7: parentesi chiusa mancante
	    error = new Dyn2RegoleSyntaxError("dyn2regole.error.missing_parentesichiusa");
	    error.addWrongExpressionProperty("parentesiChiusa");
	    errs.add(error);
	} else if (pClosed > pOpen) {
	    //7: parentesi aperta mancante
	    error = new Dyn2RegoleSyntaxError("dyn2regole.error.missing_parentesiaperta");
	    error.addWrongExpressionProperty("parentesiAperta");
	    errs.add(error);
	}
	return errs;
    }

    @Override
    protected boolean isDeleteAllowed(Dyn2Regole entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Dyn2Modellid> dyn2Modellids = dyn2ModellidService.findByRegola(entity.getId().getCodice());
	if (!dyn2Modellids.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "DYN2_MODELLI_D", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    /*
     * METODI PRIVATI
     */
    private void childDataUpdate(Dyn2Regole entity) {

	//TODO aggiornare le espressioni della regola
    }

    private void childDataDelete(Dyn2Regole entity) {

	//non serve di cancellare le espressioni della regola per via della FK ON DELETE CASCADE
    }

    private void dataIntegration(Dyn2Regole entity) {

	//TODO resettare i progressivi nel giusto ordine partendo da 0
	//	Set<Dyn2Espressioni> espressionis = entity.getDyn2Espressionis();
	//	int progressivo = 0;
	//	for (Dyn2Espressioni dyn2Espressioni : entity.getDyn2Espressionis()) {
	//	    dyn2Espressioni.setProgressivo(progressivo);
	//	    progressivo++;
	//	}
    }

    private boolean validateStcXPath(String stcAttribute) {

	boolean isValid = true;
	//TODO implementare la validazione dell'XPath di STC
	return isValid;
    }
}
