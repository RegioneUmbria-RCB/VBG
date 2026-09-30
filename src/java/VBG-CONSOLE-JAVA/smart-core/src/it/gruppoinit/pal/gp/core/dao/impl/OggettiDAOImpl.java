package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OggettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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

    /*
     * private LobHandler lobHandler;
     * 
     * 
     * public void setLobHandler(LobHandler lobHandler) { this.lobHandler = lobHandler; }
     * 
     * public void streamImage(final Integer codiceoggetto, final OutputStream contentStream) throws DataAccessException
     * { getHibernateTemplate().("SELECT oggetto FROM oggetti WHERE codiceoggetto=? AND idcomune=?", new Object[] {
     * codiceoggetto, ORMHelper.getIdcomune() }, new AbstractLobStreamingResultSetExtractor() { protected void
     * handleNoRowFound() throws LobRetrievalFailureException { throw new EmptyResultDataAccessException(
     * "Oggetto con codice " + codiceoggetto + " non trovato", 1); }
     * 
     * public void streamData(ResultSet rs) throws SQLException, IOException { InputStream is =
     * lobHandler.getBlobAsBinaryStream(rs, 1); if (is != null) { FileCopyUtils.copy(is, contentStream); } } }); }
     */
    //    @Override
    //    public boolean controllaCancellaOggetto(String nomeTabella, String nomeColonna, Integer codiceOggetto) {
    //
    //	if (log.isDebugEnabled()) {
    //	    log.debug("Cerco se posso cancellare l'oggetto ({}) per la tabella {} e la colonna {}", new Object[] { codiceOggetto, nomeTabella,
    //		    nomeColonna });
    //	}
    //	if (!isObjectUsed(codiceOggetto, nomeTabella, nomeColonna)) {
    //	    if (log.isDebugEnabled()) {
    //		log.debug("L'oggetto ({}) per la tabella {} e la colonna {} è stato cancellato", new Object[] { codiceOggetto, nomeTabella,
    //			nomeColonna });
    //	    }
    //	    return true;
    //	}
    //	return false;
    //    }
    //    private boolean isObjectUsed(Integer id, String excludedTable, String excludedColumn) {
    //
    //	String strSQL = "SELECT NOMETABELLA, NOMECAMPO FROM MAPOGGETTI ORDER BY NOMETABELLA";
    //	SQLQuery query = getSession().createSQLQuery(strSQL);
    //	List listaTabelle = query.list();
    //	for (Object object : listaTabelle) {
    //	    if (object instanceof Object[]) {
    //		Object[] a = (Object[]) object;
    //		String nomeTabella = a[0].toString();
    //		String nomeCampo = a[1].toString();
    //		boolean esaminaOggetto = true;
    //		if (!nomeTabella.equals("OGGETTI")) {
    //		    // if (nomeTabella.equals(excludedTable) && nomeCampo.equals(excludedColumn)) {
    //		    //	 esaminaOggetto = false;
    //		    // }
    //		    if (esaminaOggetto) {
    //			// controllo se l'oggetto è usato in altre tabelle rispetto a excludedTable, excludedColumn
    //			if (!(nomeTabella.equals(excludedTable) && nomeCampo.equals(excludedColumn))) {
    //			    if (checkUsed(nomeTabella, nomeCampo, id)) {
    //				if (log.isDebugEnabled()) {
    //				    log.debug("L'oggetto codice {} è usato nella tabella: {}, nella colonna: {}", new Object[] { id, nomeTabella,
    //					    nomeCampo });
    //				}
    //				return true;
    //			    }
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	// controllo se l'oggetto è usato in più record nella stessa tabella che sto prendendo in considerazione.
    //	// se si allora non posso cancellare l'oggetto
    //	if (checkMoreThanOneRecord(excludedTable, excludedColumn, id)) {
    //	    if (log.isDebugEnabled()) {
    //		log.debug("L'oggetto codice {} è usato in altri record nella tabella: {}, nella colonna: {}", new Object[] { id, excludedTable,
    //			excludedColumn });
    //	    }
    //	    return true;
    //	}
    //	return false;
    //    }
    /**
     * La funzione controlla se nella tabella il codice oggetto è usato più di una volta
     * 
     * @return
     */
    //    private boolean checkMoreThanOneRecord(String nomeTabella, String nomeCampo, Integer codiceOggetto) {
    //
    //	if (log.isDebugEnabled()) {
    //	    log.debug("controllo la tabella; {}, nomecampo: {}, codiceoggetto: {}", new Object[] { nomeTabella, nomeCampo, codiceOggetto });
    //	}
    //	String strSQL = "select " + nomeCampo + " from " + nomeTabella + " where idcomune=:idcomune and " + nomeCampo + "=:codiceoggetto";
    //	SQLQuery query = getSession().createSQLQuery(strSQL);
    //	query.setString("idcomune", ORMHelper.getIdcomune());
    //	query.setInteger("codiceoggetto", codiceOggetto);
    //	List<Integer> result = query.list();
    //	if (result.size() > 1) {
    //	    return true;
    //	}
    //	return false;
    //    }
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
	if (result.size() > 0) {
	    return true;
	}
	return false;
    }

    @Override
    public void updateOggetto(String nomeTabella, String nomeColonna, Integer codiceOggetto, Integer codiceOggettoNew) {

	if (log.isDebugEnabled()) {
	    log.debug("Aggiorno l'oggetto ({}) per la tabella {} e la colonna {}", new Object[] { codiceOggetto, nomeTabella, nomeColonna });
	}
	updateObjectUsed(codiceOggetto, codiceOggettoNew, nomeTabella, nomeColonna);
	if (log.isDebugEnabled()) {
	    log.debug("L'oggetto ({}) per la tabella {} e la colonna {}" + " è stato aggiornato", new Object[] { codiceOggetto, nomeTabella,
		    nomeColonna });
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
				log.debug("L'oggetto codice {} è usato nella tabella: {}, nella colonna: {}", new Object[] { id, nomeTabella,
					nomeCampo });
			    }
			    String strSQLUpdate = "UPDATE " + nomeTabella + " SET " + nomeCampo + "=:codiceoggettoNew where idcomune=:idcomune and "
				    + nomeCampo + "=:codiceoggetto";
			    SQLQuery queryUpdate = getSession().createSQLQuery(strSQLUpdate);
			    queryUpdate.setString("idcomune", ORMHelper.getIdcomune());
			    queryUpdate.setInteger("codiceoggettoNew", codiceOggettoNew);
			    queryUpdate.setInteger("codiceoggetto", id);
			    int returnRow = queryUpdate.executeUpdate();
			    if (log.isDebugEnabled()) {
				log.debug("Sono state aggiornate " + returnRow
					+ " righe nella tabella: {}. E\' stato aggiornato il valore del campo {}({}) con il codice {}", new Object[] {
					nomeTabella, nomeCampo, id, codiceOggettoNew });
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
	if (resultSet.size() > 0) {
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
    public InputStream getInputStreamFromBLOB(final String idcomune, final Integer codiceOggetto) {

	OggettiJDBCWorker worker = new OggettiJDBCWorker(idcomune, codiceOggetto);
	getSession().doWork(worker);
	return worker.getInputStream();
    }

    private class OggettiJDBCWorker implements Work {

	private InputStream inputStream = null;
	private Integer codiceOggetto = null;
	private String idcomune = null;

	public OggettiJDBCWorker(String idcomune, Integer codiceOggetto) {

	    this.codiceOggetto = codiceOggetto;
	    this.idcomune = idcomune;
	}

	@Override
	public void execute(Connection c) throws SQLException {

	    String query = new String("SELECT OGGETTO FROM OGGETTI WHERE IDCOMUNE = ? AND CODICEOGGETTO = ?");
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ps = c.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    ps.setString(1, idcomune);
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
    public List<Integer> trovaOggettiCMIS(int numFilesDaSpostare, boolean verificaNellaSottoTabella) {

	String hql = "Select a.id.codice from Oggetti a where a.id.idcomune=? and a.nonUsareContenutoBLOB is null and a.percorso is not null";
	hql += " and a.percorso like ? ";
	hql += " order by a.id.codice";
	int paramPos = 0;
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setFirstResult(0);
	q.setMaxResults(numFilesDaSpostare);
	q.setString(paramPos, ORMHelper.getIdcomune());
	paramPos++;
	q.setString(paramPos, "workspace:%");
	paramPos++;
	List<Integer> oggetti = q.list();
	return oggetti;
    }

    @Override
    public void updateBLOB(Integer codiceoggetto, byte[] contenutoBlob, boolean setPercorsoNull) {

	Oggetti o = this.findById(new PkId(codiceoggetto));
	if (o == null) {
	    throw new RuntimeException("oggetto con codice " + codiceoggetto + " nullo");
	}
	o.setNonUsareContenutoBLOB(contenutoBlob);
	if (setPercorsoNull) {
	    o.setPercorso(null);
	}
	this.update(o);
    }

    @Override
    public List<Integer> trovaOggettiFilesystem(int numFilesDaSpostare, boolean verificaNellaSottoTabella) {

	String sql = "Select codiceoggetto from Oggetti a where a.idcomune=? and a.oggetto is null and a.percorso is not null";
	sql += " and not a.percorso like ? ";
	if (verificaNellaSottoTabella) {
	    sql += " and codiceoggetto in (select codiceoggetto from oggetti_consolle)";
	}
	sql += " order by a.codiceoggetto";
	int paramPos = 0;
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	SQLQuery q = s.createSQLQuery(sql);
	q.setFirstResult(0);
	q.setMaxResults(numFilesDaSpostare);
	q.setString(paramPos, ORMHelper.getIdcomune());
	paramPos++;
	q.setString(paramPos, "workspace:%");
	paramPos++;
	q.addScalar("codiceoggetto", Hibernate.INTEGER);
	List<Integer> oggetti = q.list();
	return oggetti;
    }
}
