package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AllegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class AllegatiDAOImpl extends BaseDAOImpl<Allegati, PkId> implements AllegatiDAO {

    @Override
    public Class<Allegati> getEntityClass() {

	return Allegati.class;
    }

    @Override
    public List<Allegati> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "allegato", DAOOrderTypeEnum.ASC);
    }
}
