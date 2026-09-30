package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2datiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.Istanzedyn2datiDTO;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;

@Repository
public class Istanzedyn2datiDAOImpl extends BaseDAOImpl<Istanzedyn2dati, Istanzedyn2datiId> implements Istanzedyn2datiDAO {

    @Override
    public Class<Istanzedyn2dati> getEntityClass() {

	return Istanzedyn2dati.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzedyn2dati> findByIstanza(PkId idIstanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	DetachedCriteria istanzaCrit = criteria.createCriteria("istanza");
	istanzaCrit.add(Restrictions.eq("id", idIstanza));
	return (List<Istanzedyn2dati>) getHibernateTemplate().findByCriteria(criteria);
    }

    /**
    *
    *
    */
    @Override
    public List<Istanzedyn2dati> findByIstanzasAndDyn2Campi(Integer idCampo, Integer codiceAttivita, List<Integer> listaIstanze, Integer firstResult,
	    Integer maxResult) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanza.attivita", "_attivita", DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.eq("_attivita.id.codice", codiceAttivita));
	criteria.createCriteria("dyn2Campi", "_dyn2Campi", DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.eq("_dyn2Campi.id.codice", idCampo));
	criteria.add(Restrictions.isNotNull("_istanza.datavalidita"));
	if (listaIstanze != null && !listaIstanze.isEmpty()) {
	    criteria.add(Restrictions.in("_istanza.id.codice", listaIstanze));
	}
	// Imposto gli ordinamenti
	criteria.addOrder((OrderBySqlFormula.desc("_istanza.datavalidita", FunctionsEnum.NVL_FUNCTION, "'01/01/2999'",
		OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE)));
	criteria.addOrder(Order.asc("_istanza.attivitaOrdine"));
	if (null != firstResult && null != maxResult) {
	    return (List<Istanzedyn2dati>) getHibernateTemplate().findByCriteria(criteria, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Istanzedyn2dati>) getHibernateTemplate().findByCriteria(criteria);
	}
    }

    @Override
    public String findValoreById(Istanzedyn2datiId id) {

	List resultSet = getSession().createQuery(
		"select id2d.valore from Istanzedyn2dati id2d where id2d.id.idcomune=? and id2d.id.codiceistanza=? and id2d.id.fkD2cId=? and id2d.id.indice=? and id2d.id.indiceMolteplicita=?")
		.setParameter(0, id.getIdcomune()).setParameter(1, id.getCodiceistanza()).setParameter(2, id.getFkD2cId())
		.setParameter(3, id.getIndice()).setParameter(4, id.getIndiceMolteplicita()).list();
	if (!resultSet.isEmpty()) {
	    return (String) resultSet.get(0);
	}
	return null;
    }

    /**
     * select I2D.* from ISTANZEDYN2DATI i2d
     * 
     * inner join DYN2_CAMPI D2C on D2C.IDCOMUNE=I2D.IDCOMUNE and D2C.id=I2D.FK_D2C_ID <br />
     * inner join TIPIBANDOOUTPUT TBO on TBO.IDCOMUNE=D2C.IDCOMUNE and TBO.FK_D2C_ID_OUT=D2C.id <br />
     * inner join TIPIGRADUATORIET TGT on TGT.IDCOMUNE=TBO.IDCOMUNE and TGT.id=TBO.FK_TGT_ID <br />
     * inner join GRADUATORIET GT on GT.IDCOMUNE=TGT.IDCOMUNE and GT.FK_TGT_ID=TGT.id <br />
     * inner join GRADUATORIED GD on GD.IDCOMUNE=GT.IDCOMUNE and GD.FK_GT_ID=GT.id <br />
     * 
     * and GD.CODICEISTANZA=I2D.CODICEISTANZA and GD.IDCOMUNE=I2D.IDCOMUNE <br />
     * 
     * where gd.id=26018 and gd.idcomune='DEF';
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzedyn2datiDTO> findBandoOutput(Integer graduatoriedId) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.createAlias("dyn2Campi", "d2c", DetachedCriteria.INNER_JOIN);
	crit.createAlias("d2c.tipibandooutputsForFkTipibandooutputD2cOut", "tbo", DetachedCriteria.INNER_JOIN);
	crit.createAlias("tbo.tipigraduatoriet", "tgt", DetachedCriteria.INNER_JOIN);
	crit.createCriteria("tgt.graduatoriets", "gt", DetachedCriteria.INNER_JOIN);
	crit.createCriteria("gt.graduatorieds", "gd", DetachedCriteria.INNER_JOIN);
	crit.add(Restrictions.eqProperty("gd.istanza", "istanza"));
	crit.add(Restrictions.eq("gd.id.codice", graduatoriedId));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.property("valore"), "VALORE");
	projectionList.add(Projections.property("valoredecodificato"), "VALOREDECODIFICATO");
	crit.setProjection(projectionList);
	crit.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(Istanzedyn2datiDTO.class));
	return (List<Istanzedyn2datiDTO>) getHibernateTemplate().findByCriteria(crit);
    }

    @Override
    public List<CodiceDescrizioneBean> findModelliCheUsanoLocalizzazioneByUUID(Integer codiceistanza, String uuid, Integer firstResult,
	    Integer maxResults) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	StringBuilder sql = new StringBuilder();
	sql.append("SELECT dyn2_modellit.id as codice ,dyn2_modellit.descrizione as descrizione,istanzedyn2dati.valore as uuid ");
	sql.append("FROM " + schema + ".istanzedyn2modellit,  ");
	sql.append("  " + schema + ".dyn2_modellid,  ");
	sql.append("  " + schema + ".dyn2_campi,  ");
	sql.append("  " + schema + ".istanzedyn2dati,  ");
	sql.append("  " + schema + ".dyn2_modellit  ");
	sql.append("WHERE dyn2_modellid.idcomune         = istanzedyn2modellit.idcomune  ");
	sql.append("AND dyn2_modellid.fk_d2mt_id         = istanzedyn2modellit.fk_d2mt_id  ");
	sql.append("AND dyn2_modellit.idcomune           = dyn2_modellid.idcomune  ");
	sql.append("AND dyn2_modellit.id                 = dyn2_modellid.fk_d2mt_id  ");
	sql.append("AND dyn2_campi.idcomune              = dyn2_modellid.idcomune  ");
	sql.append("AND dyn2_campi.id                    = dyn2_modellid.fk_d2c_id  ");
	sql.append("AND istanzedyn2dati.idcomune         = istanzedyn2modellit.idcomune  ");
	sql.append("AND istanzedyn2dati.CodiceIstanza    = istanzedyn2modellit.CodiceIstanza  ");
	sql.append("AND istanzedyn2dati.idcomune         = dyn2_campi.idcomune  ");
	sql.append("AND istanzedyn2dati.fk_d2c_id        = dyn2_campi.id  ");
	sql.append("AND istanzedyn2modellit.idcomune     = ?  ");
	sql.append("AND istanzedyn2modellit.CodiceIstanza= ? ");
	sql.append("AND dyn2_campi.tipodato              = ?  ");
	sql.append(" order by dyn2_modellit.descrizione asc ");
	SQLQuery q = session.createSQLQuery(sql.toString());
	if (firstResult != null) {
	    q.setFirstResult(firstResult);
	}
	if (maxResults != null) {
	    q.setMaxResults(maxResults);
	}
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceistanza);
	q.setString(2, TipoControlloEnum.Localizzazione.name());
	q.addScalar("codice", Hibernate.INTEGER);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("uuid", Hibernate.STRING);
	List result = q.list();
	List<CodiceDescrizioneBean> output = new ArrayList<CodiceDescrizioneBean>();
	if (!result.isEmpty()) {
	    for (Object o : result) {
		if (o instanceof Object[]) {
		    Object[] os = (Object[]) o;
		    Integer codice = (Integer) os[0];
		    String descrizione = (String) os[1];
		    String valore = (String) os[2];
		    if (StringUtils.isNotBlank(valore)
			    && valore.equalsIgnoreCase(StringUtils.defaultString(uuid, "tmp_" + System.currentTimeMillis()))) {
			CodiceDescrizioneBean b = new CodiceDescrizioneBean();
			b.setCodice(String.valueOf(codice));
			b.setDescrizione(descrizione);
			output.add(b);
		    }
		}
	    }
	}
	return output;
    }

    @Override
    public List<Istanzedyn2datiDTO> findValoreDecodificatoByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo, Integer indice,
	    Integer indiceMolteplicita) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("id.codiceistanza", codiceIstanza));
	crit.add(Restrictions.eq("id.fkD2cId", codiceCampo));
	if (indice != null)
	    crit.add(Restrictions.eq("id.indice", indice));
	if (indiceMolteplicita != null)
	    crit.add(Restrictions.eq("id.indiceMolteplicita", indiceMolteplicita));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.property("valore"), "VALORE");
	projectionList.add(Projections.property("valoredecodificato"), "VALOREDECODIFICATO");
	projectionList.add(Projections.property("id.indiceMolteplicita"), "MOLTEPLICITA");
	crit.setProjection(projectionList);
	crit.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(Istanzedyn2datiDTO.class));
	return (List<Istanzedyn2datiDTO>) getHibernateTemplate().findByCriteria(crit);
    }

    @Override
    public List<Istanzedyn2datiDTO> findDTOByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("id.codiceistanza", codiceIstanza));
	crit.add(Restrictions.eq("id.fkD2cId", codiceCampo));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.property("valore"), "VALORE");
	projectionList.add(Projections.property("valoredecodificato"), "VALOREDECODIFICATO");
	projectionList.add(Projections.property("id.fkD2cId"), "ID_FKD2CID");
	projectionList.add(Projections.property("id.codiceistanza"), "ID_CODICEISTANZA");
	projectionList.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	projectionList.add(Projections.property("id.indice"), "ID_INDICE");
	projectionList.add(Projections.property("id.indiceMolteplicita"), "ID_INDICEMOLTEPLICITA");
	crit.addOrder(Order.asc("id.indice"));
	crit.addOrder(Order.asc("id.indiceMolteplicita"));
	crit.setProjection(projectionList);
	crit.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(Istanzedyn2datiDTO.class));
	return (List<Istanzedyn2datiDTO>) getHibernateTemplate().findByCriteria(crit);
    }
}
