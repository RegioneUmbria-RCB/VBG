package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ProtocolloModalitainvioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ProtocolloModalitainvioService;

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
public class ProtocolloModalitainvioServiceImpl extends BaseServiceImpl<ProtocolloModalitainvio, PkId> implements ProtocolloModalitainvioService {

    private ProtocolloModalitainvioDAO protocollomodalitainvioDAO;

    @Autowired
    public void setProtocolloModalitainvioDAO(ProtocolloModalitainvioDAO protocollomodalitainvioDAO) {

	this.protocollomodalitainvioDAO = protocollomodalitainvioDAO;
    }

    @Override
    protected Class<ProtocolloModalitainvio> getEntityClass() {

	return ProtocolloModalitainvio.class;
    }

    @Override
    public List<ProtocolloModalitainvio> findAll(Integer firstResult, Integer maxResult) {

	return protocollomodalitainvioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ProtocolloModalitainvio entity) {

	if (validateEntity(entity)) {
	    protocollomodalitainvioDAO.insert(entity);
	}
    }

    @Override
    public ProtocolloModalitainvio findById(PkId id) {

	return protocollomodalitainvioDAO.findById(id);
    }

    @Override
    public void update(ProtocolloModalitainvio entity) {

	if (validateEntity(entity)) {
	    protocollomodalitainvioDAO.update(entity);
	}
    }

    @Override
    public void delete(ProtocolloModalitainvio entity) {

	if (isDeleteAllowed(entity)) {
	    protocollomodalitainvioDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ProtocolloModalitainvio entity) {

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
    public ProtocolloModalitainvio findByCodiceModalitaAndComuneAndSoftware(String codiceModalita, String codiceComune, String software) {

	if (StringUtils.isBlank(software)) {
	    throw new RuntimeException("Il parametro software non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("software.codice", new Object[] { "TT", software }, String.class));
	fr.addFilterField(FilterUtils.equals("codice", codiceModalita, String.class));
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
	List<ProtocolloModalitainvio> list = protocollomodalitainvioDAO.findByFilterTable(ft);
	if (list != null && list.size() > 0) {
	    if (list.size() == 1) {
		return list.get(0);
	    } else {
		String key = (codiceComune == null ? "TUTTI" : codiceComune) + "-" + software;
		String keyTT = (codiceComune == null ? "TUTTI" : codiceComune) + "-" + WebConstants.SOFTWARE_TT;
		Map<String, ProtocolloModalitainvio> m = new HashMap<String, ProtocolloModalitainvio>();
		for (ProtocolloModalitainvio v : list) {
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
    public ProtocolloModalitainvio findByCodiceModalitaAndComune(String codiceModalita, String codiceComune) {

	return this.findByCodiceModalitaAndComuneAndSoftware(codiceModalita, codiceComune, ORMHelper.getSoftware());
    }

    @Override
    public List<ProtocolloModalitainvio> findByComuneAndSoftware(String codicecomune, String software) {

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
	List<ProtocolloModalitainvio> list = protocollomodalitainvioDAO.findByFilterTable(ft);
	Map<String, ProtocolloModalitainvio> m = new HashMap<String, ProtocolloModalitainvio>();
	for (ProtocolloModalitainvio ps : list) {
	    m.put(StringUtils.defaultString(ps.getCodice(), "NNNNNNNNNNNN"), ps);
	}
	List<ProtocolloModalitainvio> result = new ArrayList<ProtocolloModalitainvio>();
	if (!m.isEmpty()) {
	    for (Entry<String, ProtocolloModalitainvio> me : m.entrySet()) {
		result.add(me.getValue());
	    }
	}
	return result;
    }
}
