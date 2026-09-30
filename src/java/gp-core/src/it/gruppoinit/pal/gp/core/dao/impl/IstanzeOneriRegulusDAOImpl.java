/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IstanzeOneriRegulusDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IstanzeOneriRegulus;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * @author francescop
 * 
 */
@Repository
public class IstanzeOneriRegulusDAOImpl extends BaseDAOImpl<IstanzeOneriRegulus, PkId> implements IstanzeOneriRegulusDAO {

    @Override
    public Class<IstanzeOneriRegulus> getEntityClass() {

	return IstanzeOneriRegulus.class;
    }

    @Override
    public void deleteByIdOnere(int istanzeOneriId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction r = new FilterRestriction();
	r.addFilterField(FilterUtils.equals("istanzeoneri.id.codice", istanzeOneriId, int.class));
	ft.addRestriction(r);
	List<IstanzeOneriRegulus> righe = super.findByFilterTable(ft);
	for (IstanzeOneriRegulus riga : righe) {
	    this.delete(riga);
	}
    }

    @Override
    public List<IstanzeOneriRegulus> findByIdIstanzeOneri(Integer idIstanzeOneri) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction r = new FilterRestriction();
	r.addFilterField(FilterUtils.equals("istanzeoneri.id.codice", idIstanzeOneri, int.class));
	ft.addRestriction(r);
	return super.findByFilterTable(ft);
    }
}
