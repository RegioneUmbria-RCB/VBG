package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LavoricategorieDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class LavoricategorieDAOImpl extends BaseDAOImpl<Lavoricategorie, PkId> implements LavoricategorieDAO {

    @Override
    public Class<Lavoricategorie> getEntityClass() {

	return Lavoricategorie.class;
    }

    @Override
    public List<Lavoricategorie> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "categoria", DAOOrderTypeEnum.ASC);
    }
}
