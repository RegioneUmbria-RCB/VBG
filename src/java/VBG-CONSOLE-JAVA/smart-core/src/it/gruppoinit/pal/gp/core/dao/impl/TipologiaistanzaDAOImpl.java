/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipologiaistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class TipologiaistanzaDAOImpl extends BaseDAOImpl<Tipologiaistanza, PkId> implements TipologiaistanzaDAO {

    @Override
    public Class<Tipologiaistanza> getEntityClass() {

	return Tipologiaistanza.class;
    }

    @Override
    public List<Tipologiaistanza> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "tiDescrizione", DAOOrderTypeEnum.ASC);
    }
}
