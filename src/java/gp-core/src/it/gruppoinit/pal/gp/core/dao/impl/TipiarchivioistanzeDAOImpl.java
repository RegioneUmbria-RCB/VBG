package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiarchivioistanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TipiarchivioistanzeDAOImpl extends BaseDAOImpl<Tipiarchivioistanze, PkId> implements TipiarchivioistanzeDAO {

    @Override
    public Class<Tipiarchivioistanze> getEntityClass() {

	return Tipiarchivioistanze.class;
    }

    @Override
    public List<Tipiarchivioistanze> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "archivio", DAOOrderTypeEnum.DESC);
    }
}
