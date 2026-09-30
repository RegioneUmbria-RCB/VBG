package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.EquitaliatracciatoDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.EquitaliatracciatoD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.EquitaliatracciatoDService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class EquitaliatracciatoDServiceImpl extends BaseServiceImpl<EquitaliatracciatoD, PkId> implements EquitaliatracciatoDService {

    private EquitaliatracciatoDDAO equitaliatracciatodDAO;

    @Autowired
    public void setEquitaliatracciatoDDAO(EquitaliatracciatoDDAO equitaliatracciatodDAO) {

	this.equitaliatracciatodDAO = equitaliatracciatodDAO;
    }

    @Override
    protected Class<EquitaliatracciatoD> getEntityClass() {

	return EquitaliatracciatoD.class;
    }

    @Override
    public List<EquitaliatracciatoD> findAll(Integer firstResult, Integer maxResult) {

	return equitaliatracciatodDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(EquitaliatracciatoD entity) {

	if (validateEntity(entity)) {
	    equitaliatracciatodDAO.insert(entity);
	}
    }

    @Override
    public EquitaliatracciatoD findById(PkId id) {

	return equitaliatracciatodDAO.findById(id);
    }

    @Override
    public void update(EquitaliatracciatoD entity) {

	if (validateEntity(entity)) {
	    equitaliatracciatodDAO.update(entity);
	}
    }

    @Override
    public void delete(EquitaliatracciatoD entity) {

	if (isDeleteAllowed(entity)) {
	    equitaliatracciatodDAO.delete(entity);
	}
    }

    @Override
    public List<EquitaliatracciatoD> findByTracciato(Integer codiceTracciato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceTracciato, "equitaliatracciato", Integer.class));
	ft.addRestriction(fr);
	List<EquitaliatracciatoD> l = equitaliatracciatodDAO.findByFilterTable(ft);
	return l;
    }

    protected boolean isDeleteAllowed(EquitaliatracciatoD entity) {

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
