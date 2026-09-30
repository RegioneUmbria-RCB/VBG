package it.gruppoinit.pal.gp.core.dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.OggettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class OggettiDAOImpl extends BaseDAOImpl<Oggetti, PkId> implements OggettiDAO {

    private Logger log = LoggerFactory.getLogger(OggettiDAOImpl.class);

    @Override
    public Class<Oggetti> getEntityClass() {

	return Oggetti.class;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String findNomeById(PkId id) {

	String hql = "select o.nomefile from Oggetti o where o.id.idcomune=? and o.id.codice=?";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), id.getCodice() };
	List result = getHibernateTemplate().find(hql, values);
	if (!result.isEmpty()) {
	    return (String) result.get(0);
	}
	return null;
    }

    /**
     * La funzione controlla se nella tabella il codice oggetto è usato più di una volta
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean checkUsed(String nomeTabella, String nomeCampo, Integer codiceOggetto) {

	if (log.isDebugEnabled()) {
	    log.debug("controllo la tabella; {}, nomecampo: {}, codiceoggetto: {}", new Object[] { nomeTabella, nomeCampo, codiceOggetto });
	}
	String strSQL = "select " + nomeCampo + " from " + nomeTabella + " where idcomune=:idcomune and " + nomeCampo + "=:codiceoggetto";
	SQLQuery query = getSession().createSQLQuery(strSQL);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("codiceoggetto", codiceOggetto);
	List<Integer> result = query.list();
	return (!result.isEmpty());
    }

    @Override
    public void updateOggetto(String nomeTabella, String nomeColonna, Integer codiceOggetto, Integer codiceOggettoNew) {

	if (log.isDebugEnabled()) {
	    log.debug("Aggiorno l'oggetto ({}) per la tabella {} e la colonna {}", new Object[] { codiceOggetto, nomeTabella, nomeColonna });
	}
	updateObjectUsed(codiceOggetto, codiceOggettoNew, nomeTabella, nomeColonna);
	if (log.isDebugEnabled()) {
	    log.debug("L'oggetto ({}) per la tabella {} e la colonna {}" + " è stato aggiornato",
		    new Object[] { codiceOggetto, nomeTabella, nomeColonna });
	}
    }

    @SuppressWarnings("rawtypes")
    private void updateObjectUsed(Integer id, Integer codiceOggettoNew, String excludedTable, String excludedColumn) {

	String strSQL = "SELECT NOMETABELLA, NOMECAMPO FROM MAPOGGETTI ORDER BY NOMETABELLA";
	SQLQuery query = getSession().createSQLQuery(strSQL);
	List listaTabelle = query.list();
	for (Object object : listaTabelle) {
	    if (object instanceof Object[]) {
		Object[] a = (Object[]) object;
		String nomeTabella = a[0].toString();
		String nomeCampo = a[1].toString();
		boolean esaminaOggetto = true;
		if (!nomeTabella.equals("OGGETTI")) {
		    if (nomeTabella.equals(excludedTable) && nomeCampo.equals(excludedColumn)) {
			esaminaOggetto = false;
		    }
		    if (esaminaOggetto) {
			if (checkUsed(nomeTabella, nomeCampo, id)) {
			    if (log.isDebugEnabled()) {
				log.debug("L'oggetto codice {} è usato nella tabella: {}, nella colonna: {}",
					new Object[] { id, nomeTabella, nomeCampo });
			    }
			    String strSQLUpdate = "UPDATE " +
				    nomeTabella +
				    " SET " +
				    nomeCampo +
				    "=:codiceoggettoNew where idcomune=:idcomune and " +
				    nomeCampo +
				    "=:codiceoggetto";
			    SQLQuery queryUpdate = getSession().createSQLQuery(strSQLUpdate);
			    queryUpdate.setString("idcomune", ORMHelper.getIdcomune());
			    queryUpdate.setInteger("codiceoggettoNew", codiceOggettoNew);
			    queryUpdate.setInteger("codiceoggetto", id);
			    int returnRow = queryUpdate.executeUpdate();
			    if (log.isDebugEnabled()) {
				log.debug(
					"Sono state aggiornate " +
						returnRow +
						" righe nella tabella: {}. E\' stato aggiornato il valore del campo {}({}) con il codice {}",
					new Object[] { nomeTabella, nomeCampo, id, codiceOggettoNew });
			    }
			}
		    }
		}
	    }
	}
    }

    @Override
    public Oggetti findByIdLazy(PkId id) {

	List resultSet = getSession()
		.createQuery("select o.nomefile,o.percorso,o.dimensioneFile from Oggetti o where o.id.idcomune=? and o.id.codice=?")
		.setParameter(0, id.getIdcomune()).setParameter(1, id.getCodice()).list();
	Oggetti dto = null;
	if (!resultSet.isEmpty()) {
	    Object obj = resultSet.get(0);
	    if (obj instanceof Object[]) {
		Object[] objs = (Object[]) obj;
		dto = new Oggetti();
		dto.setId(id);
		dto.setNomefile((String) objs[0]);
		dto.setPercorso((String) objs[1]);
		dto.setDimensioneFile((Integer) objs[2]);
	    }
	}
	return dto;
    }

    @Override
    public void updatePercorsoOggetto(Integer codiceOggetto, String percorso) {

	String hqlUpdate = "update Oggetti c set c.percorso = :percorso where c.id.idcomune=:idcomune and c.id.codice = :codiceOggetto";
	getSession().createQuery(hqlUpdate).setString("percorso", percorso).setString("idcomune", ORMHelper.getIdcomune())
		.setInteger("codiceOggetto", codiceOggetto).executeUpdate();
    }

    @Override
    public InputStream getInputStreamFromBLOB(final Integer codiceOggetto) {

	OggettiJDBCWorker worker = new OggettiJDBCWorker(codiceOggetto);
	getSession().doWork(worker);
	return worker.getInputStream();
    }

    private class OggettiJDBCWorker implements Work {

	private InputStream inputStream = null;
	private Integer codiceOggetto = null;

	public OggettiJDBCWorker(Integer codiceOggetto) {

	    this.codiceOggetto = codiceOggetto;
	}

	@Override
	public void execute(Connection c) throws SQLException {

	    String query = new String("SELECT OGGETTO FROM OGGETTI WHERE IDCOMUNE = ? AND CODICEOGGETTO = ?");
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ps = c.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    ps.setString(1, ORMHelper.getIdcomune());
	    ps.setInt(2, codiceOggetto);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		inputStream = rs.getBlob("OGGETTO").getBinaryStream();
	    }
	}

	public InputStream getInputStream() {

	    return inputStream;
	}
    }

    @Override
    public Oggetti findByIdLazyComplete(Integer codiceOggetto) {

	List resultSet = getSession().createQuery(
		"select o.nomefile, o.nonUsareContenutoBLOB, o.dimensioneFile, o.percorso from Oggetti o where o.id.idcomune=? and o.id.codice=?")
		.setParameter(0, ORMHelper.getIdcomune()).setParameter(1, codiceOggetto).list();
	Oggetti dto = null;
	if (!resultSet.isEmpty()) {
	    Object obj = resultSet.get(0);
	    if (obj instanceof Object[]) {
		Object[] objs = (Object[]) obj;
		dto = new Oggetti();
		dto.setId(new PkId(codiceOggetto));
		dto.setNomefile((String) objs[0]);
		dto.setNonUsareContenutoBLOB((byte[]) objs[1]);
		dto.setDimensioneFile((Integer) objs[2]);
		dto.setPercorso((String) objs[3]);
	    }
	}
	return dto;
    }
}
