package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.TmpStatiComunicazioniDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PassoCreazioneComunicazioneEnum;
import it.gruppoinit.pal.gp.core.domain.TmpStatiComunicazioniD;

@Repository
public class TmpStatiComunicazioniDDAOImpl extends BaseDAOImpl<TmpStatiComunicazioniD, Integer> implements TmpStatiComunicazioniDDAO {

    protected final String SCHEMA_NAME = "#SCHEMA_NAME#";

    @Override
    public Class<TmpStatiComunicazioniD> getEntityClass() {

	return TmpStatiComunicazioniD.class;
    }

    @Override
    public void insert(Integer posizione, Integer fkComunicazioniD, String stato) {

	//String queryInsert = "INSERT INTO table_name (idcomune,posizione, fkComunicazioniD, evento, stato) VALUES (?,?,?,?,?);";
	String queryInsert = "INSERT INTO TMP_STATI_COMUNICAZIONI_D (idcomune,posizione,fk_comunicazioni_d,stato) VALUES (?,?,?,?)";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryInsert.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, posizione);
	q.setInteger(2, fkComunicazioniD);
	q.setString(3, stato);
	q.executeUpdate();
    }

    @Override
    public List<TmpStatiComunicazioniD> findByIdComunicazioned(Integer codiceComunicazione) {

	String queryInsert = "SELECT TMP_STATI_COMUNICAZIONI_D.POSIZIONE, TMP_STATI_COMUNICAZIONI_D.FK_COMUNICAZIONI_D, TMP_STATI_COMUNICAZIONI_D.EVENTO, TMP_STATI_COMUNICAZIONI_D.STATO, TMP_STATI_COMUNICAZIONI_D.IDCOMUNE FROM TMP_STATI_COMUNICAZIONI_D WHERE idcomune = ? and fk_comunicazioni_d = ? ORDER BY posizione asc";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryInsert.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceComunicazione);
	List<Object[]> list = (List<Object[]>) q.list();
	List<TmpStatiComunicazioniD> comunicazioniDs = new ArrayList<TmpStatiComunicazioniD>();
	TmpStatiComunicazioniD dest = null;
	for (Object[] objects : list) {
	    dest = new TmpStatiComunicazioniD();
	    BigDecimal posizione = (BigDecimal) objects[0];
	    BigDecimal fk_com = (BigDecimal) objects[1];
	    String evento = (String) objects[2];
	    String stato = (String) objects[3];
	    String idcomune = (String) objects[4];
	    dest.setPosizione(posizione.intValue());
	    dest.setFkComunicazioniD(fk_com.intValue());
	    dest.setEvento(evento);
	    dest.setStato(stato);
	    dest.setIdcomune(idcomune);
	    comunicazioniDs.add(dest);
	}
	return comunicazioniDs;
    }

    @Override
    public void update(Integer codiceComunicazioneD, PassoCreazioneComunicazioneEnum insertMovimento, String errore) {

	String queryUpdate = "UPDATE TMP_STATI_COMUNICAZIONI_D SET EVENTO = ? WHERE IDCOMUNE = ? AND FK_COMUNICAZIONI_D = ? AND STATO = ? ";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryUpdate.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, StringUtils.abbreviate(errore, 490));
	q.setString(1, ORMHelper.getIdcomune());
	q.setInteger(2, codiceComunicazioneD);
	q.setString(3, insertMovimento.toString());
	q.executeUpdate();
    }

    @Override
    public void delete(Integer codiceComunicazioneD, PassoCreazioneComunicazioneEnum insertMovimento) {

	String queryDelete = "DELETE FROM TMP_STATI_COMUNICAZIONI_D WHERE IDCOMUNE = ? AND FK_COMUNICAZIONI_D = ? AND STATO = ?";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryDelete.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceComunicazioneD);
	q.setString(2, insertMovimento.toString());
	q.executeUpdate();
    }

    @Override
    public void delete(Integer codiceComunicazioneD) {

	String queryDelete = "DELETE FROM TMP_STATI_COMUNICAZIONI_D WHERE IDCOMUNE = ? AND FK_COMUNICAZIONI_D = ? ";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryDelete.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceComunicazioneD);
	q.executeUpdate();
    }

    @Override
    public TmpStatiComunicazioniD findPrimoPassoConErrore(Integer idcomunicazioned) {

	String queryInsert = "SELECT TMP_STATI_COMUNICAZIONI_D.POSIZIONE, TMP_STATI_COMUNICAZIONI_D.FK_COMUNICAZIONI_D, TMP_STATI_COMUNICAZIONI_D.EVENTO, TMP_STATI_COMUNICAZIONI_D.STATO, TMP_STATI_COMUNICAZIONI_D.IDCOMUNE FROM TMP_STATI_COMUNICAZIONI_D WHERE idcomune = ? and fk_comunicazioni_d = ? and evento is not null ORDER BY posizione asc";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryInsert.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idcomunicazioned);
	List<Object[]> list = (List<Object[]>) q.list();
	if (list != null && !list.isEmpty()) {
	    List<TmpStatiComunicazioniD> comunicazioniDs = new ArrayList<TmpStatiComunicazioniD>();
	    TmpStatiComunicazioniD dest = null;
	    for (Object[] objects : list) {
		dest = new TmpStatiComunicazioniD();
		BigDecimal posizione = (BigDecimal) objects[0];
		BigDecimal fk_com = (BigDecimal) objects[1];
		String evento = (String) objects[2];
		String stato = (String) objects[3];
		String idcomune = (String) objects[4];
		dest.setPosizione(posizione.intValue());
		dest.setFkComunicazioniD(fk_com.intValue());
		dest.setEvento(evento);
		dest.setStato(stato);
		dest.setIdcomune(idcomune);
		comunicazioniDs.add(dest);
	    }
	    return comunicazioniDs.get(0);
	} else {
	    return null;
	}
    }

    @Override
    public List<TmpStatiComunicazioniD> findComunicazioniBloccateInvioEmail(Integer codiceCominicazioneT) {

	String querySelect = "SELECT TMP_STATI_COMUNICAZIONI_D.POSIZIONE, TMP_STATI_COMUNICAZIONI_D.FK_COMUNICAZIONI_D, TMP_STATI_COMUNICAZIONI_D.EVENTO, TMP_STATI_COMUNICAZIONI_D.STATO, TMP_STATI_COMUNICAZIONI_D.IDCOMUNE " // 
		+ " FROM TMP_STATI_COMUNICAZIONI_D " //
		+ " left join COMUNICAZIONI_D on TMP_STATI_COMUNICAZIONI_D.IDCOMUNE = COMUNICAZIONI_D.IDCOMUNE AND TMP_STATI_COMUNICAZIONI_D.fk_comunicazioni_d = COMUNICAZIONI_D.ID "//
		+ " WHERE COMUNICAZIONI_D.idcomune = ? and FK_COMUNICAZIONI_T = ? and stato = ? and evento is not null";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = querySelect.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceCominicazioneT);
	q.setString(2, PassoCreazioneComunicazioneEnum.INVIO_MAIL.toString());
	List<Object[]> list = (List<Object[]>) q.list();
	List<TmpStatiComunicazioniD> comunicazioniDs = new ArrayList<TmpStatiComunicazioniD>();
	if (list != null && !list.isEmpty()) {
	    TmpStatiComunicazioniD dest = null;
	    for (Object[] objects : list) {
		dest = new TmpStatiComunicazioniD();
		BigDecimal posizione = (BigDecimal) objects[0];
		BigDecimal fk_com = (BigDecimal) objects[1];
		String evento = (String) objects[2];
		String stato = (String) objects[3];
		String idcomune = (String) objects[4];
		dest.setPosizione(posizione.intValue());
		dest.setFkComunicazioniD(fk_com.intValue());
		dest.setEvento(evento);
		dest.setStato(stato);
		dest.setIdcomune(idcomune);
		comunicazioniDs.add(dest);
	    }
	}
	return comunicazioniDs;
    }
}
