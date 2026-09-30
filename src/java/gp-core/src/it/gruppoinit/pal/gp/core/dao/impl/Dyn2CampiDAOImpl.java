package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.ProprietaCampi;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;

@Repository
public class Dyn2CampiDAOImpl extends BaseDAOImpl<Dyn2Campi, PkId> implements Dyn2CampiDAO {

    @Override
    public Class<Dyn2Campi> getEntityClass() {

	return Dyn2Campi.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dyn2Campi> findByFilterAndIdModello(Dyn2Campi entity, Integer idModello) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (!(entity.getDescrizione() == null || entity.getDescrizione().equals(""))) {
	    det.add(Restrictions.or(Restrictions.ilike("descrizione", entity.getDescrizione(), MatchMode.ANYWHERE),
		    Restrictions.ilike("nomecampo", entity.getNomecampo(), MatchMode.ANYWHERE)));
	}
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	det.createCriteria("dyn2Modellids", "d2Modellid");
	det.add(Restrictions.eq("d2Modellid.dyn2Modellit.id.codice", idModello));
	return (List<Dyn2Campi>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dyn2Campi> findByFilterAndIdModelloForNumeric(Dyn2Campi entity, Integer idModello) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (!(entity.getDescrizione() == null || entity.getDescrizione().equals(""))) {
	    det.add(Restrictions.or(Restrictions.ilike("descrizione", entity.getDescrizione(), MatchMode.ANYWHERE),
		    Restrictions.ilike("nomecampo", entity.getNomecampo(), MatchMode.ANYWHERE)));
	}
	det.add(Restrictions.like("tipodato", "Numerico%"));
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	det.createCriteria("dyn2Modellids", "d2Modellid");
	det.add(Restrictions.eq("d2Modellid.dyn2Modellit.id.codice", idModello));
	return (List<Dyn2Campi>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dyn2Campi> findByIdModello(Integer idModello) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	det.createCriteria("dyn2Modellids", "d2Modellid");
	det.add(Restrictions.eq("d2Modellid.dyn2Modellit.id.codice", idModello));
	return (List<Dyn2Campi>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ChiaveValoreBean<String, String>> findValoriPerCampo(String textToSearch, Integer codiceCampo, int numMaxResults) {

	List<ChiaveValoreBean<String, String>> result = new ArrayList<ChiaveValoreBean<String, String>>();
	Dyn2Campi d2c = this.findById(new PkId(codiceCampo));
	if (d2c == null) {
	    throw new RuntimeException("Non esiste un campo dinamico con il codice [" + codiceCampo + "," + ORMHelper.getIdcomune() + "]");
	}
	if (d2c.getTipodato() == null) {
	    throw new RuntimeException("Non è stato settato il tipo dato per il campo dinamico " + d2c.getId());
	}
	if (d2c.getTipodato().equalsIgnoreCase(TipoControlloEnum.Ricerca.name())
		|| d2c.getTipodato().equalsIgnoreCase(TipoControlloEnum.ListaSIGePro.name())) {
	} else {
	    throw new RuntimeException("Il campo dinamico con il codice [" +
		    codiceCampo +
		    "," +
		    ORMHelper.getIdcomune() +
		    "] non è definito come " +
		    TipoControlloEnum.Ricerca.name() +
		    " o " +
		    TipoControlloEnum.ListaSIGePro.name());
	}
	Set<Dyn2Campiproprieta> proprietaCampo = d2c.getDyn2Campiproprietas();
	String campiSelect = getProprietaCampo(proprietaCampo, ProprietaCampi.CampiSelect, "");
	String campoRicercaCodice = getProprietaCampo(proprietaCampo, ProprietaCampi.CampoRicercaCodice, "");
	String campoRicercaDescrizione = getProprietaCampo(proprietaCampo, ProprietaCampi.CampoRicercaDescrizione, "");
	String completionSetCount = getProprietaCampo(proprietaCampo, ProprietaCampi.CompletionSetCount, "");
	String condizioniJoin = getProprietaCampo(proprietaCampo, ProprietaCampi.CondizioniJoin, "");
	String condizioniWhere = getProprietaCampo(proprietaCampo, ProprietaCampi.CondizioniWhere, "");
	//String descriptionBoxColumns = getProprietaCampo(proprietaCampo, ProprietaCampi.DescriptionBoxColumns, "");
	String nomeCampoTesto = getProprietaCampo(proprietaCampo, ProprietaCampi.NomeCampoTesto, "");
	String nomeCampoValore = getProprietaCampo(proprietaCampo, ProprietaCampi.NomeCampoValore, "");
	// String obbligatorio = getProprietaCampo(proprietaCampo, ProprietaCampi.Obbligatorio, "");
	String tabelleSelect = getProprietaCampo(proprietaCampo, ProprietaCampi.TabelleSelect, "");
	String tipoRicerca = getProprietaCampo(proprietaCampo, ProprietaCampi.TipoRicerca, "");
	if (StringUtils.isNotBlank(condizioniWhere)) {
	    condizioniWhere += " and ";
	}
	String nomeCampoCodice = getProprietaCampo(proprietaCampo, ProprietaCampi.CampoRicercaCodice, "");
	String nomeCampoDescrizione = getProprietaCampo(proprietaCampo, ProprietaCampi.CampoRicercaDescrizione, "");
	if (StringUtils.isNotBlank(nomeCampoCodice)) {
	    nomeCampoValore = nomeCampoCodice;
	}
	if (StringUtils.isNotBlank(nomeCampoDescrizione)) {
	    nomeCampoTesto = nomeCampoDescrizione;
	}
	// String valueBoxColumns = getProprietaCampo(proprietaCampo, ProprietaCampi.ValueBoxColumns, "");
	String sql = "SELECT " +
		campiSelect +
		" FROM " +
		tabelleSelect +
		" " +
		StringUtils.defaultIfEmpty(condizioniJoin, "") +
		" WHERE " +
		StringUtils.defaultIfEmpty(condizioniWhere, "") +
		" (lower(" +
		nomeCampoValore +
		") like ? or lower(" +
		nomeCampoTesto +
		") like ?) order by " +
		nomeCampoTesto;
	Query queryid = getSession().createSQLQuery(sql);
	String preLike = "";
	if (StringUtils.defaultIfEmpty(tipoRicerca, "1").equalsIgnoreCase("0")) {
	    preLike = "%";
	}
	String postLike = "%";
	queryid.setString(0, preLike + textToSearch.toLowerCase() + postLike);
	queryid.setString(1, preLike + textToSearch.toLowerCase() + postLike);
	if (StringUtils.isNotBlank(completionSetCount)) {
	    int maxResult = Integer.valueOf(completionSetCount);
	    queryid.setMaxResults(maxResult);
	} else {
	    queryid.setMaxResults(numMaxResults);
	}
	List recs = queryid.list();
	for (Object obj : recs) {
	    ChiaveValoreBean<String, String> record = new ChiaveValoreBean<String, String>();
	    if (obj instanceof Object[]) {
		Object[] cols = (Object[]) obj;
		Object codice = cols[0];
		String codiceStr = "";
		if (codice instanceof BigDecimal) {
		    codiceStr = String.valueOf(((BigDecimal) codice).intValue());
		} else if (codice instanceof Integer) {
		    codiceStr = String.valueOf(((Integer) codice).intValue());
		} else if (codice instanceof String) {
		    codiceStr = (String) codice;
		}
		record.setChiave(codiceStr);
		String descrizione = (String) cols[1];
		record.setValore(descrizione);
		result.add(record);
	    }
	}
	return result;
    }

    private String getProprietaCampo(Set<Dyn2Campiproprieta> proprietaCampo, ProprietaCampi proprieta, String defaultValue) {

	String result = StringUtils.defaultIfEmpty(defaultValue, "");
	for (Dyn2Campiproprieta dyn2Campiproprieta : proprietaCampo) {
	    if (dyn2Campiproprieta.getId().getProprieta().equalsIgnoreCase(proprieta.name())) {
		result = dyn2Campiproprieta.getValore();
	    }
	}
	return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isCampoUsedInIstanze(Integer codiceCampo, Integer codiceModello) {

	List recs = null;
	if (codiceModello != null) {
	    String sqlConModello = "SELECT Count(fk_d2c_id) as numero_campi_usati FROM istanzedyn2dati,istanzedyn2modellit " +
		    " where istanzedyn2dati.idcomune=istanzedyn2modellit.idcomune " +
		    " AND istanzedyn2dati.codiceistanza=istanzedyn2modellit.codiceistanza AND istanzedyn2modellit.idcomune=? AND " +
		    " istanzedyn2modellit.fk_d2mt_id=? and istanzedyn2dati.fk_d2c_id = ?";
	    SQLQuery queryid = getSession().createSQLQuery(sqlConModello);
	    queryid.setString(0, ORMHelper.getIdcomune());
	    queryid.setInteger(1, codiceModello);
	    queryid.setInteger(2, codiceCampo);
	    queryid.addScalar("numero_campi_usati", Hibernate.BIG_DECIMAL);
	    recs = queryid.list();
	} else {
	    String sqlSenzaModello = "SELECT Count(fk_d2c_id) as numero_campi_usati FROM istanzedyn2dati where istanzedyn2dati.idcomune=? AND " +
		    " istanzedyn2dati.fk_d2c_id = ?";
	    SQLQuery queryid = getSession().createSQLQuery(sqlSenzaModello);
	    queryid.setString(0, ORMHelper.getIdcomune());
	    queryid.setInteger(1, codiceCampo);
	    queryid.addScalar("numero_campi_usati", Hibernate.BIG_DECIMAL);
	    recs = queryid.list();
	}
	if (recs == null) {
	    return false;
	}
	if (recs.size() == 0) {
	    return false;
	} else {
	    int ris = ((BigDecimal) recs.get(0)).intValue();
	    return ris > 0;
	}
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isCampoUsedInAttivita(Integer codiceCampo, Integer codiceModello) {

	List recs = null;
	if (codiceModello != null) {
	    String sqlConModello = "SELECT Count(fk_d2c_id) as numero_campi_usati FROM i_attivitadyn2dati,i_attivitadyn2modellit " +
		    " where i_attivitadyn2dati.idcomune=i_attivitadyn2modellit.idcomune AND i_attivitadyn2dati.fk_ia_id=i_attivitadyn2modellit.fk_ia_id AND " +
		    " i_attivitadyn2modellit.idcomune=? AND i_attivitadyn2modellit.fk_d2mt_id=? and i_attivitadyn2dati.fk_d2c_id=?";
	    SQLQuery queryid = getSession().createSQLQuery(sqlConModello);
	    queryid.setString(0, ORMHelper.getIdcomune());
	    queryid.setInteger(1, codiceModello);
	    queryid.setInteger(2, codiceCampo);
	    queryid.addScalar("numero_campi_usati", Hibernate.BIG_DECIMAL);
	    recs = queryid.list();
	} else {
	    String sqlSenzaModello = "SELECT Count(fk_d2c_id) as numero_campi_usati FROM i_attivitadyn2dati " +
		    " where i_attivitadyn2dati.idcomune=? and i_attivitadyn2dati.fk_d2c_id=?";
	    SQLQuery queryid = getSession().createSQLQuery(sqlSenzaModello);
	    queryid.setString(0, ORMHelper.getIdcomune());
	    queryid.setInteger(1, codiceCampo);
	    queryid.addScalar("numero_campi_usati", Hibernate.BIG_DECIMAL);
	    recs = queryid.list();
	}
	if (recs == null) {
	    return false;
	}
	if (recs.size() == 0) {
	    return false;
	} else {
	    int ris = ((BigDecimal) recs.get(0)).intValue();
	    return ris > 0;
	}
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isCampoUsedInAnagrafe(Integer codiceCampo, Integer codiceModello) {

	List recs = null;
	if (codiceModello != null) {
	    String sqlConModello = "SELECT Count(fk_d2c_id) as numero_campi_usati FROM anagrafedyn2dati,anagrafedyn2modellit " +
		    " where  anagrafedyn2dati.idcomune=anagrafedyn2modellit.idcomune AND " +
		    " anagrafedyn2dati.codiceanagrafe = anagrafedyn2modellit.codiceanagrafe " +
		    " AND anagrafedyn2modellit.idcomune = ? AND " +
		    " anagrafedyn2modellit.fk_d2mt_id = ? and anagrafedyn2dati.fk_d2c_id = ?";
	    SQLQuery queryid = getSession().createSQLQuery(sqlConModello);
	    queryid.setString(0, ORMHelper.getIdcomune());
	    queryid.setInteger(1, codiceModello);
	    queryid.setInteger(2, codiceCampo);
	    queryid.addScalar("numero_campi_usati", Hibernate.BIG_DECIMAL);
	    recs = queryid.list();
	} else {
	    String sqlSenzaModello = "SELECT Count(fk_d2c_id) as numero_campi_usati FROM anagrafedyn2dati where anagrafedyn2dati.idcomune=? AND " +
		    " anagrafedyn2dati.fk_d2c_id=?";
	    SQLQuery queryid = getSession().createSQLQuery(sqlSenzaModello);
	    queryid.setString(0, ORMHelper.getIdcomune());
	    queryid.setInteger(1, codiceCampo);
	    queryid.addScalar("numero_campi_usati", Hibernate.BIG_DECIMAL);
	    recs = queryid.list();
	}
	if (recs == null) {
	    return false;
	}
	if (recs.size() == 0) {
	    return false;
	} else {
	    int ris = ((BigDecimal) recs.get(0)).intValue();
	    return ris > 0;
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dyn2Campi> findByIdModelloAndIdCampo(Integer codiceScheda, Integer codice) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.codice", codice));
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	det.createCriteria("dyn2Modellids", "d2Modellid");
	det.add(Restrictions.eq("d2Modellid.dyn2Modellit.id.codice", codiceScheda));
	return (List<Dyn2Campi>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Dyn2Campi> findAllByDescrizione(String descrizione) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(descrizione)) {
	    det.add(Restrictions.ilike("nomecampo", descrizione, MatchMode.ANYWHERE));
	}
	DetachedCriteria software = det.createCriteria("software");
	software.addOrder(Order.asc("ordine"));
	det.addOrder(Order.asc("nomecampo"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
