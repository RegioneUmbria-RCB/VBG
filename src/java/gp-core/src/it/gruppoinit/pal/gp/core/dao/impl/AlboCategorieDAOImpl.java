package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlboCategorieDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlboCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class AlboCategorieDAOImpl extends BaseDAOImpl<AlboCategorie, PkId> implements AlboCategorieDAO {

    @Override
    public Class<AlboCategorie> getEntityClass() {

	return AlboCategorie.class;
    }

    @Override
    public List<AlboCategorie> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "ordine", DAOOrderTypeEnum.ASC);
    }
}
