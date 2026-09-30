package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ProtocolloMezziDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ProtocolloMezziService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ProtocolloMezziServiceImpl extends BaseServiceImpl<ProtocolloMezzi, PkId> implements ProtocolloMezziService {

    private ProtocolloMezziDAO protocollomezziDAO;

    @Autowired
    public void setProtocolloMezziDAO(ProtocolloMezziDAO protocollomezziDAO) {

	this.protocollomezziDAO = protocollomezziDAO;
    }

    @Override
    protected Class<ProtocolloMezzi> getEntityClass() {

	return ProtocolloMezzi.class;
    }

    @Override
    public List<ProtocolloMezzi> findAll(Integer firstResult, Integer maxResult) {

	return protocollomezziDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ProtocolloMezzi entity) {

	if (validateEntity(entity)) {
	    protocollomezziDAO.insert(entity);
	}
    }

    @Override
    public ProtocolloMezzi findById(PkId id) {

	return protocollomezziDAO.findById(id);
    }

    @Override
    public ProtocolloMezzi findByCodiceMezzoAndComuneAndSoftware(String codiceMezzo, String codiceComune, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("software.codice", new Object[] { "TT", software }, String.class));
	fr.addFilterField(FilterUtils.equals("codice", codiceMezzo, String.class));
	if (StringUtils.isBlank(codiceComune)) {
	    fr.addFilterField(FilterUtils.isNull("_comune.codicecomune"));
	} else {
	    FilterRestriction orComuni = new FilterRestriction();
	    orComuni.setAndOrRestriction(AndOrRestriction.OR);
	    orComuni.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    orComuni.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	    ft.addRestriction(orComuni);
	}
	ft.addRestriction(fr);
	List<ProtocolloMezzi> list = protocollomezziDAO.findByFilterTable(ft);
	if (list != null && list.size() > 0) {
	    if (list.size() == 1) {
		return list.get(0);
	    } else {
		String key = (codiceComune == null ? "TUTTI" : codiceComune) + "-" + software;
		String keyTT = (codiceComune == null ? "TUTTI" : codiceComune) + "-" + WebConstants.SOFTWARE_TT;
		Map<String, ProtocolloMezzi> m = new HashMap<String, ProtocolloMezzi>();
		for (ProtocolloMezzi v : list) {
		    String kloc = (v.getComune() == null ? "TUTTI" : v.getComune().getCodicecomune()) + "-" + v.getSoftware().getCodice();
		    m.put(kloc, v);
		}
		if (m.get(key) != null) {
		    return m.get(key);
		} else {
		    return m.get(keyTT);
		}
	    }
	}
	return null;
    }

    @Override
    public ProtocolloMezzi findByCodiceMezzoAndComune(String codiceMezzo, String codiceComune) {

	return this.findByCodiceMezzoAndComuneAndSoftware(codiceMezzo, codiceMezzo, ORMHelper.getSoftware());
    }

    @Override
    public void update(ProtocolloMezzi entity) {

	if (validateEntity(entity)) {
	    protocollomezziDAO.update(entity);
	}
    }

    @Override
    public void delete(ProtocolloMezzi entity) {

	if (isDeleteAllowed(entity)) {
	    protocollomezziDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ProtocolloMezzi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<ProtocolloMezzi> findByComuneAndSoftware(String codicecomune, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	FilterField<?> isNUllComune = FilterUtils.isNull("comune", "comune");
	if (StringUtils.isBlank(codicecomune)) {
	    fr.addFilterField(isNUllComune);
	} else {
	    FilterRestriction orComune = new FilterRestriction();
	    orComune.setAndOrRestriction(AndOrRestriction.OR);
	    orComune.addFilterField(FilterUtils.equals("codicecomune", codicecomune, "comune", String.class));
	    orComune.addFilterField(isNUllComune);
	    ft.addRestriction(orComune);
	}
	fr.addFilterField(FilterUtils.in("software.codice", new String[] { WebConstants.SOFTWARE_TT, software }, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("comune", "comune", FunctionsEnum.NVL_FUNCTION, "'AAAAAAAAAAAAAAA'"));
	ft.addOrder(FilterUtils.orderAsc("moduloopzionale", "software"));
	List<ProtocolloMezzi> list = protocollomezziDAO.findByFilterTable(ft);
	Map<String, ProtocolloMezzi> m = new HashMap<String, ProtocolloMezzi>();
	for (ProtocolloMezzi ps : list) {
	    m.put(StringUtils.defaultString(ps.getCodice(), "NNNNNNNNNNNN"), ps);
	}
	List<ProtocolloMezzi> result = new ArrayList<ProtocolloMezzi>();
	if (!m.isEmpty()) {
	    for (Entry<String, ProtocolloMezzi> me : m.entrySet()) {
		result.add(me.getValue());
	    }
	}
	return result;
    }
}
