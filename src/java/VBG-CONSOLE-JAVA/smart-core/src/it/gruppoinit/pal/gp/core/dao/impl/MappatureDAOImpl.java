package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MappatureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mappature;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class MappatureDAOImpl extends BaseDAOImpl<Mappature, PkId> implements MappatureDAO {

    @Override
    public Class<Mappature> getEntityClass() {

	return Mappature.class;
    }

    @Override
    public List<Mappature> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "nometagpeople", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findByNometagpeopleDistinct(String textToSearch) {

	String hqlQuery = "Select m.nometagpeople FROM Mappature m WHERE m.id.idcomune = ? and lower(m.nometagpeople) like ? and m.dyn2Campi.software.codice = ? group by m.nometagpeople order by m.nometagpeople asc";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), "%" + StringUtils.lowerCase(textToSearch) + "%", ORMHelper.getSoftware() };
	List<String> nometagpeopleList = (List<String>) getHibernateTemplate().find(hqlQuery, values);
	return nometagpeopleList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findSoftwareByNometagpeople(String textToSearch, String nometagpeople) {

	String hqlQuery = "Select d2mt.software.codice FROM Dyn2Modellit d2mt inner join d2mt.mappatures as m WHERE d2mt.id.idcomune = ? and m.id.idcomune = ? and lower(d2mt.software.descrizione) like ? and m.nometagpeople = ? group by d2mt.software.codice order by d2mt.software.codice asc";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), ORMHelper.getIdcomune(), "%" + StringUtils.lowerCase(textToSearch) + "%",
		nometagpeople };
	if (StringUtils.isBlank(nometagpeople)) {
	    hqlQuery = "Select sw.codice FROM Software sw inner join sw.softwareattivis as sa WHERE sa.id.idcomune = ? and lower(sw.descrizione) like ? order by sw.descrizione";
	    values = new Object[] { ORMHelper.getIdcomune(), "%" + StringUtils.lowerCase(textToSearch) + "%" };
	}
	List<String> softwareList = (List<String>) getHibernateTemplate().find(hqlQuery, values);
	return softwareList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findSchedaByTagAndSoftware(String textToSearch, String nometagpeople, String codicesoftware) {

	// 1. NOMETAGPEOPLE specificato
	String hqlQuery = "Select d2mt.id.codice FROM Dyn2Modellit d2mt inner join d2mt.mappatures as m WHERE d2mt.id.idcomune = ? and m.id.idcomune = ? and lower(d2mt.descrizione) like ? and m.nometagpeople = ? and d2mt.software.codice = ? group by d2mt.id.codice order by d2mt.id.codice asc";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), ORMHelper.getIdcomune(), "%" + StringUtils.lowerCase(textToSearch) + "%",
		nometagpeople, codicesoftware };
	// 2. NOMETAGPEOPLE non specificato
	if (StringUtils.isBlank(nometagpeople)) {
	    hqlQuery = "Select d2mt.id.codice FROM Dyn2Modellit d2mt WHERE d2mt.id.idcomune = ? and d2mt.software.codice = ? and lower(d2mt.descrizione) like ? order by d2mt.id.codice asc";
	    values = new Object[] { ORMHelper.getIdcomune(), codicesoftware, "%" + StringUtils.lowerCase(textToSearch) + "%" };
	}
	List<Integer> schedeList = (List<Integer>) getHibernateTemplate().find(hqlQuery, values);
	return schedeList;
    }
}
