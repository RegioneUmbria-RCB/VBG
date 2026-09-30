package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OggettiMetadatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OggettiMetadatiDAOImpl extends BaseDAOImpl<OggettiMetadati, OggettiMetadatiId> implements OggettiMetadatiDAO {

    private Logger log = LoggerFactory.getLogger(OggettiMetadatiDAOImpl.class);

    @Override
    public Class<OggettiMetadati> getEntityClass() {

	return OggettiMetadati.class;
    }

    @Override
    public void deleteByOggetto(Integer codiceOggetto, String idcomune) {

	if (codiceOggetto == null) {
	    log.warn("deleteByOggetto: il parametro codiceOggetto passato è nullo");
	    return;
	}
	if (log.isDebugEnabled()) {
	    log.debug("deleteByOggetto# cancello i metadati dell'oggetto id:{}, idcomune:{}", String.valueOf(codiceOggetto), ORMHelper.getIdcomune());
	}
	String hql = "delete OggettiMetadati om where om.id.idcomune=? and om.id.codiceoggetto=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { idcomune, codiceOggetto });
	if (log.isDebugEnabled()) {
	    log.debug("deleteByOggetto# cancellati {} metadati dell'oggetto {}", i, String.valueOf(codiceOggetto));
	}
    }

    @Override
    public void insertInNewTransaction(final Integer codiceOggetto, final String chiave, final String valore, final String idcomune) {

	getSession().doWork(new Work() {

	    @Override
	    public void execute(Connection c) throws SQLException {

		boolean ro = c.isReadOnly();
		c.setReadOnly(false);
		String sql = "insert into oggetti_metadati (idcomune,codiceoggetto, chiave, valore) values (?,?,?,?)";
		PreparedStatement p = c.prepareStatement(sql);
		p.setString(1, idcomune);
		p.setInt(2, codiceOggetto);
		p.setString(3, chiave);
		p.setString(4, valore);
		p.executeUpdate();
		c.setReadOnly(ro);
	    }
	});
    }

    @Override
    public void updateInNewTransaction(final Integer codiceOggetto, final String chiave, final String valore, final String idcomune) {

	getSession().doWork(new Work() {

	    @Override
	    public void execute(Connection c) throws SQLException {

		boolean ro = c.isReadOnly();
		c.setReadOnly(false);
		String sql = "update oggetti_metadati set valore=? where idcomune=? and codiceoggetto=? and chiave=?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setString(1, valore);
		p.setString(2, idcomune);
		p.setInt(3, codiceOggetto);
		p.setString(4, chiave);
		p.executeUpdate();
		c.setReadOnly(ro);
	    }
	});
    }
}
