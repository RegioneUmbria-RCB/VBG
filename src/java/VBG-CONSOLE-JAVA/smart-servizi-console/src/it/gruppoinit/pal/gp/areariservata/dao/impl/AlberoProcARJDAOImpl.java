package it.gruppoinit.pal.gp.areariservata.dao.impl;

import it.gruppoinit.pal.gp.areariservata.dao.AlberoProcARJDAO;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlberoProcARJDAOImpl extends BaseDAOImpl<Alberoproc, PkId> implements AlberoProcARJDAO {

    @Autowired
    private AlberoprocDAO alberoprocDAO;

    @SuppressWarnings("unchecked")
    @Override
    public List<Alberoproc> findSubTree(String idcomune, String scCodice, boolean filtraSoloComunica, boolean soloFlagModulisticaNazionale) {

	String hql = "";
	Integer l = new Integer(2);
	if (StringUtils.isEmpty(scCodice)) {
	    hql = "from Alberoproc a where a.id.idcomune=? and a.software.codice=? and length(a.scCodice)=? and a.scAttivo=0 and a.scPubblica in(1,2) order by a.scOrdine, a.scDescrizione";
	} else {
	    l = scCodice.length() + 2;
	    hql = "from Alberoproc a where a.id.idcomune=? and a.software.codice=? and length(a.scCodice)=? and a.scCodice like ? and a.scAttivo=0 and (a.scPubblica=null or a.scPubblica in(1,2)) order by a.scOrdine, a.scDescrizione";
	}
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString(0, idcomune);
	q.setString(1, ORMHelper.getSoftware());
	q.setInteger(2, l);
	if (StringUtils.isNotEmpty(scCodice)) {
	    q.setString(3, scCodice + "%");
	}
	List<Alberoproc> list = q.list();
	List<Alberoproc> result = new ArrayList<Alberoproc>();
	if (soloFlagModulisticaNazionale) {
	    for (Alberoproc ap : list) {
		boolean isComunica = alberoprocDAO.checkModulisticaNazionale(ap);
		if (isComunica) {
		    result.add(ap);
		}
	    }
	}
	if (filtraSoloComunica) {
	    List<Alberoproc> listComunica = new ArrayList<Alberoproc>();
	    if (soloFlagModulisticaNazionale) {
		for (Alberoproc ap : result) {
		    boolean isComunica = alberoprocDAO.checkComunica(ap);
		    if (isComunica) {
			listComunica.add(ap);
		    }
		}
	    } else {
		for (Alberoproc ap : list) {
		    boolean isComunica = alberoprocDAO.checkComunica(ap);
		    if (isComunica) {
			listComunica.add(ap);
		    }
		}
	    }
	    return listComunica;
	}
	if (soloFlagModulisticaNazionale) {
	    return result;
	}
	return list;
    }

    @Override
    public boolean hasSubTree(String idcomune, String scCodice) {

	Integer l = new Integer(2 + scCodice.length());
	String hql = "select count(*) from Alberoproc a where a.id.idcomune=? and a.software.codice=? and length(a.scCodice)=? and a.scCodice like ? and a.scAttivo=0 and (a.scPubblica=null or a.scPubblica in(1,2))";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString(0, idcomune);
	q.setString(1, ORMHelper.getSoftware());
	q.setInteger(2, l);
	q.setString(3, scCodice + "%");
	Long size = (Long) q.uniqueResult();
	if (size == null || size == 0) {
	    return false;
	}
	return true;
    }

    /**
     * return NotImplementedException
     */
    @Override
    public void clear() {

	throw new NotImplementedException();
    }

    /**
     * return NotImplementedException
     */
    @Override
    public void commit() {

	throw new NotImplementedException();
    }

    @Override
    public int countRecord(FilterTable arg0) {

	return this.countRecord(arg0);
    }

    /**
     * return NotImplementedException
     */
    @Override
    public void delete(Alberoproc arg0) {

	throw new NotImplementedException();
    }

    /**
     * return NotImplementedException
     */
    @Override
    public void evict(Alberoproc arg0) {

	throw new NotImplementedException();
    }

    @Override
    public boolean existsRecords(FilterTable arg0) {

	return this.existsRecords(arg0);
    }

    @Override
    public List<Alberoproc> findAll(Integer arg0, Integer arg1) {

	return this.findAll(arg0, arg1);
    }

    @Override
    public List<Alberoproc> findAll(Integer arg0, Integer arg1, DAOEnum arg2, String arg3, DAOOrderTypeEnum arg4) {

	return this.findAll(arg0, arg1, arg2, arg3, arg4);
    }

    @Override
    public List<Alberoproc> findByFilterTable(FilterTable arg0) {

	return this.findByFilterTable(arg0);
    }

    @Override
    public List<Alberoproc> findByFilterTable(FilterTable arg0, Integer arg1, Integer arg2) {

	return this.findByFilterTable(arg0, arg1, arg2);
    }

    @Override
    public Alberoproc findById(PkId arg0) {

	return this.findById(arg0);
    }

    /**
     * return NotImplementedException
     */
    @Override
    public void flush() {

	throw new NotImplementedException();
    }

    @Override
    public Class<Alberoproc> getEntityClass() {

	return Alberoproc.class;
    }

    /**
     * return NotImplementedException
     */
    @Override
    public void insert(Alberoproc arg0) {

	throw new NotImplementedException();
    }

    /**
     * return NotImplementedException
     */
    @Override
    public void insertOrUpdate(Alberoproc arg0, PkId arg1, boolean arg2) {

	throw new NotImplementedException();
    }

    /**
     * return NotImplementedException
     */
    @Override
    public PkId newIdFromSequence(Alberoproc arg0) {

	throw new NotImplementedException();
    }

    /**
     * return NotImplementedException
     */
    @Override
    public void update(Alberoproc arg0) {

	throw new NotImplementedException();
    }
}
