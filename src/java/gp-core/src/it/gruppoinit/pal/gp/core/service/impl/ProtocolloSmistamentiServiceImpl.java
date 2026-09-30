package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ProtocolloSmistamentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloSmistamenti;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ProtocolloSmistamentiService;

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
public class ProtocolloSmistamentiServiceImpl extends BaseServiceImpl<ProtocolloSmistamenti, PkId> implements ProtocolloSmistamentiService {

    private ProtocolloSmistamentiDAO protocollosmistamentiDAO;

    @Autowired
    public void setProtocolloSmistamentiDAO(ProtocolloSmistamentiDAO protocollosmistamentiDAO) {

	this.protocollosmistamentiDAO = protocollosmistamentiDAO;
    }

    @Override
    protected Class<ProtocolloSmistamenti> getEntityClass() {

	return ProtocolloSmistamenti.class;
    }

    @Override
    public List<ProtocolloSmistamenti> findAll(Integer firstResult, Integer maxResult) {

	return protocollosmistamentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ProtocolloSmistamenti entity) {

	if (validateEntity(entity)) {
	    protocollosmistamentiDAO.insert(entity);
	}
    }

    @Override
    public ProtocolloSmistamenti findById(PkId id) {

	return protocollosmistamentiDAO.findById(id);
    }

    @Override
    public void update(ProtocolloSmistamenti entity) {

	if (validateEntity(entity)) {
	    protocollosmistamentiDAO.update(entity);
	}
    }

    @Override
    public void delete(ProtocolloSmistamenti entity) {

	if (isDeleteAllowed(entity)) {
	    protocollosmistamentiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ProtocolloSmistamenti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
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
    public List<ProtocolloSmistamenti> findByComuneAndSoftware(String codicecomune, String software) {

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
	List<ProtocolloSmistamenti> list = protocollosmistamentiDAO.findByFilterTable(ft);
	Map<String, ProtocolloSmistamenti> m = new HashMap<String, ProtocolloSmistamenti>();
	for (ProtocolloSmistamenti ps : list) {
	    m.put(StringUtils.defaultString(ps.getCodice(), "NNNNNNNNNNNN"), ps);
	}
	List<ProtocolloSmistamenti> result = new ArrayList<ProtocolloSmistamenti>();
	if (!m.isEmpty()) {
	    for (Entry<String, ProtocolloSmistamenti> me : m.entrySet()) {
		result.add(me.getValue());
	    }
	}
	return result;
    }
}
