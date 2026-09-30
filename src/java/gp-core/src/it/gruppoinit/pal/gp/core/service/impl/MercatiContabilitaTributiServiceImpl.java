package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiContabilitaTributiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiContabilitaTributiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class MercatiContabilitaTributiServiceImpl extends BaseServiceImpl<MercatiContabilitaTributi, PkId> implements
	MercatiContabilitaTributiService {

    private MercatiContabilitaTributiDAO mercaticontabilitatributiDAO;

    @Autowired
    public void setMercatiContabilitaTributiDAO(MercatiContabilitaTributiDAO mercaticontabilitatributiDAO) {

	this.mercaticontabilitatributiDAO = mercaticontabilitatributiDAO;
    }

    @Override
    protected Class<MercatiContabilitaTributi> getEntityClass() {

	return MercatiContabilitaTributi.class;
    }

    @Override
    public List<MercatiContabilitaTributi> findAll(Integer firstResult, Integer maxResult) {

	return mercaticontabilitatributiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MercatiContabilitaTributi entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    mercaticontabilitatributiDAO.insert(entity);
	    List<MercatiContabilitaTributi> mercatiContabilitaTributis = this.findByFormula(entity.getMercatiFormuleCalcolo().getId().getCodice());
	    if (mercatiContabilitaTributis.size() > 1) {
		mercatiContabilitaTributis = this.findByFormulaAndWithoutDataFine(entity.getMercatiFormuleCalcolo().getId().getCodice());
		if (!mercatiContabilitaTributis.isEmpty()) {
		    MercatiContabilitaTributi mtcp = mercatiContabilitaTributis.get(0);
		    mtcp.setDataFineValidita(Utilities.addAndremoveDays(entity.getDataInizioValidita(), 1, false));
		    this.update(mtcp);
		}
	    }
	}
    }

    @Override
    public MercatiContabilitaTributi findById(PkId id) {

	return mercaticontabilitatributiDAO.findById(id);
    }

    @Override
    public void update(MercatiContabilitaTributi entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    mercaticontabilitatributiDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiContabilitaTributi entity) {

	if (isDeleteAllowed(entity)) {
	    mercaticontabilitatributiDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiContabilitaTributi> findByFormulaAndWithoutDataFine(Integer codiceFormula) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceFormula, "mercatiFormuleCalcolo", Integer.class));
	fr.addFilterField(FilterUtils.isNull("dataFineValidita"));
	ft.addRestriction(fr);
	return mercaticontabilitatributiDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiContabilitaTributi> findByFormula(Integer codiceFormula) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceFormula, "mercatiFormuleCalcolo", Integer.class));
	ft.addRestriction(fr);
	return mercaticontabilitatributiDAO.findByFilterTable(ft);
    }

    private boolean isInserOrUpdateAllowed(MercatiContabilitaTributi entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getDataFineValidita() != null) {
	    if (entity.getDataInizioValidita().after(entity.getDataFineValidita())) {
		String mess = getMessageFromBundle("service_error_data_fine_antecendente", null);
		_ivs.add(new InvalidValue(mess, MercatiContabilitaTributi.class, "dataFineValidita", entity, new MercatiContabilitaTributi()));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    private void dataIntegration(MercatiContabilitaTributi entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro MercatiContabilitaTributi da validare è nullo");
	}
	if (StringUtils.isBlank(entity.getDescrizione())) {
	    entity.setDescrizione(entity.getConti().getDescrizioneConto());
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MercatiContabilitaTributi entity) {

    }

    protected boolean isDeleteAllowed(MercatiContabilitaTributi entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }
}
