package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MetadatiDizBaseDAO;
import it.gruppoinit.pal.gp.core.domain.MetadatiDizBase;
import it.gruppoinit.pal.gp.core.service.MetadatiDizBaseService;

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
public class MetadatiDizBaseServiceImpl extends BaseServiceImpl<MetadatiDizBase, String> implements MetadatiDizBaseService {

    private MetadatiDizBaseDAO metadatidizbaseDAO;

    @Autowired
    public void setMetadatiDizBaseDAO(MetadatiDizBaseDAO metadatidizbaseDAO) {

	this.metadatidizbaseDAO = metadatidizbaseDAO;
    }

    @Override
    protected Class<MetadatiDizBase> getEntityClass() {

	return MetadatiDizBase.class;
    }

    @Override
    public List<MetadatiDizBase> findAll(Integer firstResult, Integer maxResult) {

	return metadatidizbaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MetadatiDizBase entity) {

	if (validateEntity(entity)) {
	    metadatidizbaseDAO.insert(entity);
	}
    }

    @Override
    public MetadatiDizBase findById(String id) {

	return metadatidizbaseDAO.findById(id);
    }

    @Override
    public void update(MetadatiDizBase entity) {

	if (validateEntity(entity)) {
	    metadatidizbaseDAO.update(entity);
	}
    }

    @Override
    public void delete(MetadatiDizBase entity) {

	if (isDeleteAllowed(entity)) {
	    metadatidizbaseDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(MetadatiDizBase entity) {

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
}
