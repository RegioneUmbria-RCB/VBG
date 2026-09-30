/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloFlussoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class ProtocolloFlussoDAOImpl extends BaseDAOImpl<ProtocolloFlusso, String> implements ProtocolloFlussoDAO {

    @Override
    public Class<ProtocolloFlusso> getEntityClass() {

	return ProtocolloFlusso.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProtocolloFlusso> findByResponsabile(Responsabili responsabili, List<String> escludiFlussi) {

	String hql = "select distinct p from Responsabili r inner join r.protocolloFlussos p where r.id.idcomune=? " + "and r.id.codice = ? ";
	int sizevalues = 2;
	if (escludiFlussi != null) {
	    if (escludiFlussi.size() > 0) {
		sizevalues += escludiFlussi.size();
		String questionMarks = "";
		for (String codicetipo : escludiFlussi) {
		    questionMarks += "?,";
		}
		if (escludiFlussi != null && escludiFlussi.size() > 0) {
		    questionMarks = questionMarks.substring(0, questionMarks.length() - 1);
		}
		hql += " and not p.codice in (" + questionMarks + ")";
	    }
	}
	hql += " order by p.descrizione asc";
	Object[] values = new Object[sizevalues];
	values[0] = ORMHelper.getIdcomune();
	values[1] = responsabili.getId().getCodice();
	if (escludiFlussi != null) {
	    if (escludiFlussi.size() > 0) {
		int contatore = 2;
		for (String codicetipo : escludiFlussi) {
		    values[contatore] = codicetipo;
		    contatore++;
		}
	    }
	}
	return getHibernateTemplate().find(hql, values);
    }

    @Override
    public List<ProtocolloFlusso> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
