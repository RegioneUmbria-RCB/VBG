package it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IstanzecalcolocanoniODAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniO;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniOId;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

/**
 * 
 * @author
 */
@Service
public class IstanzecalcolocanoniOServiceImpl extends BaseServiceImpl<IstanzecalcolocanoniO, IstanzecalcolocanoniOId>
	implements IstanzecalcolocanoniOService {

    private IstanzecalcolocanoniODAO istanzecalcolocanonioDAO;

    @Autowired
    public void setIstanzecalcolocanoniODAO(IstanzecalcolocanoniODAO istanzecalcolocanonioDAO) {

	this.istanzecalcolocanonioDAO = istanzecalcolocanonioDAO;
    }

    @Override
    protected Class<IstanzecalcolocanoniO> getEntityClass() {

	return IstanzecalcolocanoniO.class;
    }

    @Override
    public List<IstanzecalcolocanoniO> findAll(Integer firstResult, Integer maxResult) {

	return istanzecalcolocanonioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzecalcolocanoniO entity) {

	if (validateEntity(entity)) {
	    istanzecalcolocanonioDAO.insert(entity);
	}
    }

    @Override
    public IstanzecalcolocanoniO findById(IstanzecalcolocanoniOId id) {

	return istanzecalcolocanonioDAO.findById(id);
    }

    @Override
    public void update(IstanzecalcolocanoniO entity) {

	if (validateEntity(entity)) {
	    istanzecalcolocanonioDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzecalcolocanoniO entity) {

	if (isDeleteAllowed(entity)) {
	    istanzecalcolocanonioDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(IstanzecalcolocanoniO entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public void deleteByIdOnere(int istanzeOneriId) {

	this.istanzecalcolocanonioDAO.deleteByIdOnere(istanzeOneriId);
    }

    @Override
    public List<IstanzecalcolocanoniO> findByIstanzeOneri(Integer codiceIstanzeOneri) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkIdistoneri", codiceIstanzeOneri, Integer.class));
	ft.addRestriction(fr);
	return istanzecalcolocanonioDAO.findByFilterTable(ft);
    }

    @Override
    public IstanzecalcolocanoniT findTestataByIstanzeOneri(Integer codiceIstanzeOneri) {

	List<IstanzecalcolocanoniO> findByIstanzeOneri = this.findByIstanzeOneri(codiceIstanzeOneri);
	for (IstanzecalcolocanoniO istanzecalcolocanoniO : findByIstanzeOneri) {
	    return istanzecalcolocanoniO.getIstanzecalcolocanoniT();
	}
	return null;
    }
}
