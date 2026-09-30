package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocAtecoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAteco;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAtecoId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class AlberoprocAtecoDAOImpl extends BaseDAOImpl<AlberoprocAteco, AlberoprocAtecoId> implements AlberoprocAtecoDAO {

    @Override
    public Class<AlberoprocAteco> getEntityClass() {

	return AlberoprocAteco.class;
    }

    @Override
    public List<AlberoprocAteco> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.fkIdateco", DAOOrderTypeEnum.ASC);
    }
}
