package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.EquitaliatracciatoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Equitaliatracciato;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class EquitaliatracciatoDAOImpl extends BaseDAOImpl<Equitaliatracciato, PkId> implements EquitaliatracciatoDAO {

    @Override
    public Class<Equitaliatracciato> getEntityClass() {

	return Equitaliatracciato.class;
    }

    @Override
    public List<Equitaliatracciato> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.codice", DAOOrderTypeEnum.ASC);
    }
}
