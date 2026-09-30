package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimentiImporti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoMovimentiImportiDAO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class BorsellinoMovimentiDAOImpl extends BaseDAOImpl<BorsellinoMovimenti, PkId> implements IBorsellinoMovimentiDAO {

    private IBorsellinoMovimentiImportiDAO borsellinoMovimentiImportiDAO;

    @Autowired
    public void setBorsellinoMovimentiImportiDAO(IBorsellinoMovimentiImportiDAO borsellinoMovimentiImportiDAO) {

	this.borsellinoMovimentiImportiDAO = borsellinoMovimentiImportiDAO;
    }

    @Override
    public Class<BorsellinoMovimenti> getEntityClass() {

	return BorsellinoMovimenti.class;
    }

    @Override
    public void insert(BorsellinoMovimenti entity) {

	//1. Tolgo gli importi child
	Set<BorsellinoMovimentiImporti> importi = entity.getImporti();
	entity.setImporti(new HashSet<BorsellinoMovimentiImporti>());
	super.insert(entity);
	entity.setImporti(importi);
	this.childDataIntegration(entity);
	this.childInsert(entity);
    }

    private void childDataIntegration(BorsellinoMovimenti entity) {

	for (BorsellinoMovimentiImporti importo : entity.getImporti()) {
	    importo.setBorsellinoMovimenti(entity);
	}
    }

    private void childInsert(BorsellinoMovimenti entity) {

	for (BorsellinoMovimentiImporti importo : entity.getImporti()) {
	    this.borsellinoMovimentiImportiDAO.insert(importo);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer findIdNonStornatoByRiferimenti(Integer idPosteggio, Integer idGiornata, Integer idAutorizzazione) {

	if (idPosteggio == null) {
	    throw new IllegalArgumentException(
		    "Impossibile ricercare una movimentazione del borsellino per riferimenti senza passare l'id del posteggio");
	}
	if (idGiornata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile ricercare una movimentazione del borsellino per riferimenti senza passare l'id della giornata");
	}
	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile ricercare una movimentazione del borsellino per riferimenti senza passare l'id dell'autorizzazione");
	}
	String sql = "select" + // 
		     " id " + // 
		     "from" + //
		     " borsellino_movimenti " + //
		     "where" + // 
		     " borsellino_movimenti.idcomune = ? and" + //
		     " borsellino_movimenti.fkid_mercatid = ? and" + //
		     " borsellino_movimenti.fkid_mercatipresenzet = ? and" + //
		     " borsellino_movimenti.fkid_autorizzazioni = ? and" + //
		     " borsellino_movimenti.tipo = ? and" + //
		     " borsellino_movimenti.fkid_movimentostorno is null";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idPosteggio, new IntegerType());
	q.setParameter(2, idGiornata, new IntegerType());
	q.setParameter(3, idAutorizzazione, new IntegerType());
	q.setParameter(4, TipoEnum.USCITA.toString(), new StringType());
	q.addScalar("id", Hibernate.INTEGER);
	List<Integer> idTrovati = q.list();
	if (idTrovati.isEmpty()) {
	    return null;
	}
	if (idTrovati.size() > 1) {
	    throw new RuntimeException("Situazione anomala, ci sono più movimenti non stornati a parità di giornata/posteggio/autorizzazione");
	}
	return idTrovati.get(0);
    }

    @Override
    public List<BorsellinoMovimenti> findByBorsellino(Integer idBorsellino) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("borsellinoID", idBorsellino, Integer.class));
	ft.addOrder(FilterUtils.orderDesc("data")); // dall'ultimo al primo
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }
        
    @SuppressWarnings("unchecked")
    @Override
    public List<BorsellinoMovimenti> findByBorsellino(Integer idBorsellino, Date dalladata, Date alladata, Integer firstResult, Integer maxResult, List<TipoEnum> tipoenums) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	det.add(Restrictions.eq("borsellinoID", idBorsellino));
	
	if(dalladata != null){
	    det.add(Restrictions.ge("data", dalladata));
	}
	
	if(alladata != null){
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(alladata);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    det.add(Restrictions.lt("data", cal.getTime()));
	}
	
	if(tipoenums != null){
	    List<String> tipi = new ArrayList<String>();
	    for(TipoEnum tipoenum : tipoenums){
		tipi.add(tipoenum.name());
	    }
	    det.add(Restrictions.in("tipo", tipi));
	}
	
	det.addOrder(Order.desc("data"));
	det.addOrder(Order.desc("id.codice"));
	
	if( firstResult != null && maxResult != null ){
	    return (List<BorsellinoMovimenti>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	}else{
	    return (List<BorsellinoMovimenti>) getHibernateTemplate().findByCriteria(det);
	}
	
    }
    
    @Override
    public List<BorsellinoMovimenti> findByPosizioneDebitoria(Integer dettPosizioneDebitoriaId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("dettPosizioneDebitoriaId", dettPosizioneDebitoriaId, Integer.class));
	ft.addOrder(FilterUtils.orderAsc("data"));
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isPresenzaMovimentata(Integer idGiornata) {

	if (idGiornata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una movimentazione del borsellino per giornata senza passare l'id della giornata");
	}
	String sql = "select" + // 
		     " count(borsellino_movimenti.id) as conteggio " + // 
		     "from" + //
		     " borsellino_movimenti " + //
		     "where" + // 
		     " borsellino_movimenti.idcomune = ? and" + //
		     " borsellino_movimenti.fkid_mercatipresenzet = ?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idGiornata, new IntegerType());
	q.addScalar("conteggio", Hibernate.INTEGER);
	List<Integer> conteggio = q.list();
	if (conteggio.isEmpty()) {
	    return false;
	}
	return conteggio.get(0).compareTo(Integer.valueOf(0)) > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isAutorizzazioneMovimentata(Integer idAutorizzazione) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una movimentazione del borsellino per l'autorizzazione senza passare l'id dell'autorizzazione");
	}
	String sql = "select" + // 
		     " count(borsellino_movimenti.id) as conteggio " + // 
		     "from" + //
		     " borsellino_movimenti " + //
		     "where" + // 
		     " borsellino_movimenti.idcomune = ? and" + //
		     " borsellino_movimenti.fkid_autorizzazioni = ?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idAutorizzazione, new IntegerType());
	q.addScalar("conteggio", Hibernate.INTEGER);
	List<Integer> conteggio = q.list();
	if (conteggio.isEmpty()) {
	    return false;
	}
	return conteggio.get(0).compareTo(Integer.valueOf(0)) > 0;
    }

    @Override
    public boolean isPresenzaPagataDaBorsellino(Integer idGiornata, Integer idAutorizzazione, Integer idPosteggio) {

	String sql = "select count(*) as conteggio from borsellino_movimenti" + //
		     " where" + //
		     " idcomune=:idcomune" + //
		     " and fkid_mercatipresenzet = :idgiornata " + //
		     " and fkid_mercatid = :idposteggio " + //
		     " and fkid_autorizzazioni = :idautorizzazione " + //
		     " and tipo = :tipo " + // le uscite non stornate
		     " and fkid_movimentostorno is null";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter("idcomune", ORMHelper.getIdcomune(), new StringType());
	q.setParameter("idgiornata", idGiornata, new IntegerType());
	q.setParameter("idposteggio", idPosteggio, new IntegerType());
	q.setParameter("idautorizzazione", idAutorizzazione, new IntegerType());
	q.setParameter("tipo", TipoEnum.USCITA.toString(), new StringType());
	q.addScalar("conteggio", Hibernate.INTEGER);
	return ((Integer) q.list().get(0)) > 0;
    }
}
