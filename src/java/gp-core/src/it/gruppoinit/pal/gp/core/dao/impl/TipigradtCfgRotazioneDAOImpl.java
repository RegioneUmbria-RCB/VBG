package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipigradtCfgRotazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigradtCfgRotazione;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipigradtCfgRotazioneDAOImpl extends BaseDAOImpl<TipigradtCfgRotazione, PkId> implements TipigradtCfgRotazioneDAO {

    @Override
    public Class<TipigradtCfgRotazione> getEntityClass() {

	return TipigradtCfgRotazione.class;
    }

    @Override
    public List<TipigradtCfgRotazione> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }
}
