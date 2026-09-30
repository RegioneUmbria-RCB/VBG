package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AlberoprocDocSoggFirmatariDAOImpl extends BaseDAOImpl<AlberoprocDocSoggFirmatari, PkId> implements AlberoprocDocSoggFirmatariDAO {

    @Override
    public Class<AlberoprocDocSoggFirmatari> getEntityClass() {

	return AlberoprocDocSoggFirmatari.class;
    }

    @Override
    public List<AlberoprocDocSoggFirmatari> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }
    
    @Override
    public Integer countSoggettiInUsoForDocuments(List<Integer> alberoprocids, Integer tipisoggetto ) {
	Query q = getSession().createSQLQuery("SELECT COUNT(*) FROM ALBEROPROC JOIN ALBEROPROC_DOCUMENTI ON ALBEROPROC.IDCOMUNE = ALBEROPROC_DOCUMENTI.IDCOMUNE AND ALBEROPROC.SC_ID = ALBEROPROC_DOCUMENTI.SM_FKSCID " + 
		"JOIN ALBEROPROC_DOC_SOGG_FIRMATARI ON ALBEROPROC_DOCUMENTI.IDCOMUNE = ALBEROPROC_DOC_SOGG_FIRMATARI.IDCOMUNE AND ALBEROPROC_DOCUMENTI.sm_id = ALBEROPROC_DOC_SOGG_FIRMATARI.FK_ALBEROPROC_DOCUMENTI " + 
		"WHERE ALBEROPROC.IDCOMUNE = :idcomune AND ALBEROPROC.SC_ID IN (:alberoprocids) AND ALBEROPROC_DOC_SOGG_FIRMATARI.FK_TIPO_SOGGETTO = :tipisoggetto");
	q.setParameter("idcomune", ORMHelper.getIdcomune());
	q.setParameterList("alberoprocids", alberoprocids);
	q.setParameter("tipisoggetto", tipisoggetto);
	Number r = (Number)q.list().get(0);
	return r.intValue();
    }
}
