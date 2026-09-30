package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.EmailanagrDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Emailanagr;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.EmailanagrService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class EmailanagrServiceImpl extends BaseServiceImpl<Emailanagr, PkId> implements EmailanagrService {

    private EmailanagrDAO emailanagrDAO;

    @Autowired
    public void setEmailanagrDAO(EmailanagrDAO emailanagrDAO) {

	this.emailanagrDAO = emailanagrDAO;
    }

    @Override
    protected Class<Emailanagr> getEntityClass() {

	return Emailanagr.class;
    }

    @Override
    public List<Emailanagr> findAll(Integer firstResult, Integer maxResult) {

	return emailanagrDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Emailanagr entity) {

	if (validateEntity(entity)) {
	    emailanagrDAO.insert(entity);
	}
    }

    @Override
    public Emailanagr findById(PkId id) {

	return emailanagrDAO.findById(id);
    }

    @Override
    public void update(Emailanagr entity) {

	if (validateEntity(entity)) {
	    emailanagrDAO.update(entity);
	}
    }

    @Override
    public void delete(Emailanagr entity) {

	if (isDeleteAllowed(entity)) {
	    emailanagrDAO.delete(entity);
	}
    }

    // protected boolean isDeleteAllowed(Emailanagr entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
    @Override
    public List<Emailanagr> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return emailanagrDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
