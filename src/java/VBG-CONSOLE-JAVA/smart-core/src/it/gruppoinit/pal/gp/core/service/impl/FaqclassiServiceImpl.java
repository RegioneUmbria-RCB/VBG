package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.FaqclassiDAO;
import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FaqclassiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class FaqclassiServiceImpl extends BaseServiceImpl<Faqclassi, PkId> implements FaqclassiService {

    private FaqclassiDAO faqclassiDAO;

    @Autowired
    public void setFaqclassiDAO(FaqclassiDAO faqclassiDAO) {

	this.faqclassiDAO = faqclassiDAO;
    }

    @Override
    protected Class<Faqclassi> getEntityClass() {

	return Faqclassi.class;
    }

    @Override
    public List<Faqclassi> findAll(Integer firstResult, Integer maxResult) {

	return faqclassiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Faqclassi entity) {

	if (validateEntity(entity)) {
	    faqclassiDAO.insert(entity);
	}
    }

    @Override
    public Faqclassi findById(PkId id) {

	return faqclassiDAO.findById(id);
    }

    @Override
    public void update(Faqclassi entity) {

	if (validateEntity(entity)) {
	    faqclassiDAO.update(entity);
	}
    }

    @Override
    public void delete(Faqclassi entity) {

	if (isDeleteAllowed(entity)) {
	    faqclassiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Faqclassi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getFaqs().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "FAQ", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Faqclassi> findByFaqclasse(String faqclasse) {

	return faqclassiDAO.findByFaqclasse(faqclasse);
    }

    @Override
    public List<Faqclassi> findBySoftwareAndFaq(String software, boolean isCercaPerTT) {

	return faqclassiDAO.findBySoftwareAndFaq(software, isCercaPerTT);
    }
}
