package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegate;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegateId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class IstanzecollegateDAOImpl extends BaseDAOImpl<Istanzecollegate, PkId> implements IstanzecollegateDAO {

    private IstanzeDAO istanzeDAO;

    @Autowired
    public void setIstanzeService(IstanzeDAO istanzeDAO) {

	this.istanzeDAO = istanzeDAO;
    }

    @Override
    public Class<Istanzecollegate> getEntityClass() {

	return Istanzecollegate.class;
    }

    @Override
    public List<Istanzecollegate> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer maxOrdineByProgressivo(Integer progressivo) {

	// §§§BEGIN§§§
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("progressivo", progressivo));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("ordine"));
	criteria.setProjection(projectionList);
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    //LION 23-01-14 se la tabella era vuota restituiva null anzichè 0 (almeno su Oracle)
	    if (null != list.get(0)) {
		return list.get(0);
	    } else {
		return 0;
	    }
	    //End LION 23-01-14
	}
	// §§§END§§§
	return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer maxProgressivo() {

	// §§§BEGIN§§§
	DetachedCriteria criteria = getIdcomuneCriteria();
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("progressivo"));
	criteria.setProjection(projectionList);
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    //LION 16-01-14 se la tabella era vuota restituiva null anzichè 0 (almeno su Oracle)
	    if (null != list.get(0)) {
		return list.get(0);
	    } else {
		return 0;
	    }
	    //End LION 16-01-14
	}
	// §§§END§§§
	return 0;
    }

    @Override
    public IstanzecollegateHelper getSchemaPrecedentiAndSuccessive(Istanze istanza) {

	// §§§BEGIN§§§
	IstanzecollegateHelper istanzecollegateHelper = new IstanzecollegateHelper();
	//Recupero della lista delle istanze precedenti
	// Creo un citerio che mi restituisce i codici dell'istanze che sono state collegate 
	// all'istanza in esame.
	// Al criterio verrà applicata un condizione distinc per mostrare le istanze collegate una sola volta.
	// (possono esistere più istanze collegate identiche , ma con progressivi diversi)
	DetachedCriteria criteriaPrecedenti = getIdcomuneCriteria();
	criteriaPrecedenti.add(Restrictions.eq("istanzaId", istanza.getId().getCodice()));
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	projectionListPrecedenti.add(Projections.distinct(Projections.property("istanzaDacollegareId")));
	criteriaPrecedenti.setProjection(projectionListPrecedenti);
	List<Integer> listCodiciPrecedenti = getHibernateTemplate().findByCriteria(criteriaPrecedenti);
	List<Istanze> listPrecedenti = new ArrayList<Istanze>();
	// recupero le istanze dalla lista dei codici trovati
	//	if (listCodiciPrecedenti != null && !listCodiciPrecedenti.isEmpty() && listCodiciPrecedenti.get(0) != null) {
	Istanze istanzecollegate = null;
	for (Integer codiceistanzecollegate : listCodiciPrecedenti) {
	    if (codiceistanzecollegate != null) {
		istanzecollegate = istanzeDAO.findById(new PkId(codiceistanzecollegate));
		listPrecedenti.add(istanzecollegate);
	    }
	}
	//	}
	istanzecollegateHelper.setListaIstanzePrecedenti(listPrecedenti);
	//Recupero della lista delle istanze successive
	// Creo un citerio che mi restituisce i codici dell'istanze a cui è collegata 
	// l' istanza in esame.
	// Al criterio verrà applicata un condizione distinc per mostrare le istanze a cui è collegata una sola volta.
	// (possono esistere più istanze a cui è collegata identiche , ma con progressivi diversi)
	DetachedCriteria criteriaSuccessive = getIdcomuneCriteria();
	criteriaSuccessive.add(Restrictions.eq("istanzaDacollegareId", istanza.getId().getCodice()));
	ProjectionList projectionListSuccessive = Projections.projectionList();
	projectionListSuccessive.add(Projections.distinct(Projections.property("istanzaId")));
	criteriaSuccessive.setProjection(projectionListSuccessive);
	List<Integer> listCodiciSuccessive = getHibernateTemplate().findByCriteria(criteriaSuccessive);
	List<Istanze> listSuccessive = new ArrayList<Istanze>();
	Istanze istanzecollegate1 = null;
	// recupero le istanze dalla lista dei codici trovati
	for (Integer codiceistanzecollegate : listCodiciSuccessive) {
	    istanzecollegate1 = istanzeDAO.findById(new PkId(codiceistanzecollegate));
	    listSuccessive.add(istanzecollegate1);
	}
	istanzecollegateHelper.setListaIstanzeSuccessive(listSuccessive);
	return istanzecollegateHelper;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Istanzecollegate> findByIdcomuneEProgressivo(String idComune, int progressivo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idComune, String.class));
	fr.addFilterField(FilterUtils.equals("progressivo", progressivo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderDesc("datavalidita", "istanza", FunctionsEnum.NVL_FUNCTION, "'01/01/1900'"));
	ft.addOrder(FilterUtils.orderDesc("id.codice", "istanza"));
	return findByFilterTable(ft);
    }

    @Override
    public List<IstanzecollegateHelper> findIstanzecollegateByIstanzaPerVisualizzazione(Integer codiceIstanza) {

	StringBuilder sql = new StringBuilder();
	sql.append(
		"select istanzecollegate.idcomune as idcomune, istanzecollegate.progressivo as progressivo, istanzecollegate.codiceistanza as codiceistanza, istanzecollegate.ordine as ordine");
	sql.append(" from istanzecollegate where istanzecollegate.idcomune=:idcomune ");
	sql.append(" and istanzecollegate.codiceistanza=:codiceistanza ");
	sql.append(" group by istanzecollegate.idcomune,istanzecollegate.progressivo,istanzecollegate.codiceistanza,istanzecollegate.ordine");
	sql.append(" order by istanzecollegate.progressivo");
	SQLQuery q = getSession().createSQLQuery(sql.toString());
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("progressivo", Hibernate.INTEGER);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("ordine", Hibernate.INTEGER);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("codiceistanza", codiceIstanza);
	q.setResultTransformer(Transformers.aliasToBean(VwIstanzecollegateId.class));
	List<VwIstanzecollegateId> ic = q.list();
	List<IstanzecollegateHelper> istanzecollegateHelpers = new ArrayList<IstanzecollegateHelper>();
	int i = 0;
	//Passo 1
	for (VwIstanzecollegateId vwIstanzecollegate : ic) {
	    //Passo 2
	    List<VwIstanzecollegate> listIstanzecollegate = new ArrayList<VwIstanzecollegate>();
	    IstanzecollegateHelper istanzecollegateHelper = new IstanzecollegateHelper();
	    sql = new StringBuilder();
	    sql.append(
		    "select istanzecollegate.idcomune as idcomune, istanzecollegate.progressivo as progressivo, istanzecollegate.codiceistanza as codiceistanza, istanzecollegate.ordine as ordine");
	    sql.append(" from istanzecollegate where istanzecollegate.idcomune=:idcomune ");
	    sql.append(" and istanzecollegate.progressivo=:progressivo ");
	    sql.append(" group by istanzecollegate.idcomune,istanzecollegate.progressivo,istanzecollegate.codiceistanza,istanzecollegate.ordine");
	    sql.append(" order by istanzecollegate.ordine");
	    q = getSession().createSQLQuery(sql.toString());
	    q.addScalar("idcomune", Hibernate.STRING);
	    q.addScalar("progressivo", Hibernate.INTEGER);
	    q.addScalar("codiceistanza", Hibernate.INTEGER);
	    q.addScalar("ordine", Hibernate.INTEGER);
	    q.setString("idcomune", ORMHelper.getIdcomune());
	    q.setInteger("progressivo", vwIstanzecollegate.getProgressivo());
	    q.setResultTransformer(Transformers.aliasToBean(VwIstanzecollegateId.class));
	    List<VwIstanzecollegateId> ic2 = q.list();
	    //Passo 3
	    istanzecollegateHelper.setIstanza(istanzeDAO.findById(new PkId(vwIstanzecollegate.getCodiceistanza())));
	    for (VwIstanzecollegateId istcollegata : ic2) {
		VwIstanzecollegate iscoll = new VwIstanzecollegate();
		iscoll.setId(istcollegata);
		iscoll.setIstanza(istanzeDAO.findById(new PkId(istcollegata.getCodiceistanza())));
		listIstanzecollegate.add(iscoll);
	    }
	    istanzecollegateHelper.setIstanzecollegates(listIstanzecollegate);
	    //Passo 4
	    istanzecollegateHelpers.add(i, istanzecollegateHelper);
	    i++;
	}
	return istanzecollegateHelpers;
    }
}
