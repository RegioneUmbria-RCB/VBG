package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribrRiduzDAO;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribrRiduz;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.OIcalcolocontribrRiduzService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 
 */
@Service
public class OIcalcolocontribrRiduzServiceImpl extends BaseServiceImpl<OIcalcolocontribrRiduz, PkId> implements OIcalcolocontribrRiduzService {

    private OIcalcolocontribrRiduzDAO oicalcolocontribrriduzDAO;

    @Autowired
    public void setOIcalcolocontribrRiduzDAO(OIcalcolocontribrRiduzDAO oicalcolocontribrriduzDAO) {

	this.oicalcolocontribrriduzDAO = oicalcolocontribrriduzDAO;
    }

    @Override
    protected Class<OIcalcolocontribrRiduz> getEntityClass() {

	return OIcalcolocontribrRiduz.class;
    }

    @Override
    public List<OIcalcolocontribrRiduz> findAll(Integer firstResult, Integer maxResult) {

	return oicalcolocontribrriduzDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OIcalcolocontribrRiduz entity) {

	if (validateEntity(entity)) {
	    oicalcolocontribrriduzDAO.insert(entity);
	}
    }

    @Override
    public OIcalcolocontribrRiduz findById(PkId id) {

	return oicalcolocontribrriduzDAO.findById(id);
    }

    @Override
    public void update(OIcalcolocontribrRiduz entity) {

	if (validateEntity(entity)) {
	    oicalcolocontribrriduzDAO.update(entity);
	}
    }

    @Override
    public void delete(OIcalcolocontribrRiduz entity) {

	if (isDeleteAllowed(entity)) {
	    oicalcolocontribrriduzDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OIcalcolocontribrRiduz entity) {

		boolean delete = true;
		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
		// TODO _validare_la_delete
		// esempio:
		// if (entity.getList().size() > 0) {
		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
		// }
		if (!_ivs.isEmpty()) {
			this.throwValidationMessages(_ivs);
		}
		return delete;
    }
}
