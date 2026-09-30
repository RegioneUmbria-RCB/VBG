package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BachecalavoroconfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Bachecalavoroconfigurazione;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class BachecalavoroconfigurazioneDAOImpl extends BaseDAOImpl<Bachecalavoroconfigurazione, String> implements BachecalavoroconfigurazioneDAO {

    @Override
    public Class<Bachecalavoroconfigurazione> getEntityClass() {

	return Bachecalavoroconfigurazione.class;
    }

    @Override
    public List<Bachecalavoroconfigurazione> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
