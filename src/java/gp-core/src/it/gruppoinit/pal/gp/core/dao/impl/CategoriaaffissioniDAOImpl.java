package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CategoriaaffissioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Categoriaaffissioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CategoriaaffissioniDAOImpl extends BaseDAOImpl<Categoriaaffissioni, PkId> implements CategoriaaffissioniDAO {

    @Override
    public Class<Categoriaaffissioni> getEntityClass() {

	return Categoriaaffissioni.class;
    }

    @Override
    public List<Categoriaaffissioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "categoria", DAOOrderTypeEnum.ASC);
    }
}
