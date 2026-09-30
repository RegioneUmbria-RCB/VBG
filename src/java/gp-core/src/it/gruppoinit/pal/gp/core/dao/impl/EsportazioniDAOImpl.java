package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.EsportazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
@Repository
public class EsportazioniDAOImpl extends BaseDAOImpl<Esportazioni, PkId> implements EsportazioniDAO {

    @Override
    public Class<Esportazioni> getEntityClass() {

	return Esportazioni.class;
    }

    @Override
    public List<Esportazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public void inserisci(Esportazioni esportazioni) {

	int maxId = maxid() + 1;
	String sql = "insert into Esportazioni (idcomune,id,descrizione,fk_tipicontestoesp_codice,trasformazione,flg_abilitata,software) values (?,?,?,?,?,?,?)";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Esportazioni.class);
	q.setString(0, esportazioni.getId().getIdcomune());
	q.setInteger(1, maxId);
	q.setString(2, esportazioni.getDescrizione());
	q.setString(3, esportazioni.getTipicontestoesportazione().getCodice());
	q.setString(4, esportazioni.getTrasformazione());
	q.setBoolean(5, esportazioni.isFlgAbilitata());
	q.setString(6, esportazioni.getSoftware().getCodice());
	int res = q.executeUpdate();
    }

    private int maxid() {

	String hql = "select max(id) from Esportazioni ";
	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
	SQLQuery queryid = session.createSQLQuery(hql);
	BigDecimal maxId = (BigDecimal) queryid.list().get(0);
	return maxId.intValue();
    }
}
