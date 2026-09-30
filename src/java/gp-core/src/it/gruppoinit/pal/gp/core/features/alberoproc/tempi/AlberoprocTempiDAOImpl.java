package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTempi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTempiId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class AlberoprocTempiDAOImpl extends BaseDAOImpl<AlberoprocTempi, AlberoprocTempiId> implements AlberoprocTempiDAO {

    @Override
    public Class<AlberoprocTempi> getEntityClass() {

	return AlberoprocTempi.class;
    }

    @Override
    public List<AlberoprocTempi> findByAlberoProcId(Integer codiceIntervento) {

	FilterTable ftable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.fkscid", codiceIntervento, Integer.class));
	ftable.addRestriction(filterRestriction);
	return findByFilterTable(ftable, null, null);
    }

    @Override
    public void deleteById(Integer codiceIntervento, Integer codiceTempoFo) {

	AlberoprocTempi tempi = this.findById(new AlberoprocTempiId(codiceIntervento, codiceTempoFo));
	this.delete(tempi);
    }

    @Override
    public boolean existByIdTempoFoT(Integer codiceTempoFo) {

	FilterTable ftable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.fkidtempi", codiceTempoFo, Integer.class));
	ftable.addRestriction(filterRestriction);
	return existsRecords(ftable);
    }
}
