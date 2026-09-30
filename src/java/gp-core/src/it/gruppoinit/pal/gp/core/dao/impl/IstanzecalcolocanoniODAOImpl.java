package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IstanzecalcolocanoniODAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniO;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniOId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author
 */
@Repository
public class IstanzecalcolocanoniODAOImpl extends BaseDAOImpl<IstanzecalcolocanoniO, IstanzecalcolocanoniOId> implements IstanzecalcolocanoniODAO {

    @Override
    public Class<IstanzecalcolocanoniO> getEntityClass() {

	return IstanzecalcolocanoniO.class;
    }

    @Override
    public List<IstanzecalcolocanoniO> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }

    @Override
    public void deleteByIdOnere(int istanzeOneriId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction r = new FilterRestriction();
	r.addFilterField(FilterUtils.equals("id.fkIdistoneri", istanzeOneriId, int.class));
	ft.addRestriction(r);
	List<IstanzecalcolocanoniO> righe = super.findByFilterTable(ft);
	for (IstanzecalcolocanoniO riga : righe) {
	    this.delete(riga);
	}
    }
}
