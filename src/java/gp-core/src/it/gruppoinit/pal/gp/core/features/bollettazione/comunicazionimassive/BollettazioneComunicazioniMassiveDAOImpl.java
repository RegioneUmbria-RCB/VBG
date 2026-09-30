package it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.BollMassiveD;
import it.gruppoinit.pal.gp.core.domain.BollMassiveT;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ComunicazioneBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.DettaglioBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.FiltriRicercaDettagli;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.FiltriRicercaTestata;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@SuppressWarnings("rawtypes")
@Repository
public class BollettazioneComunicazioniMassiveDAOImpl extends BaseDAOImpl<BollMassiveT, PkId> implements IBollettazioneComunicazioniMassiveDAO {

    private static final Logger log = LoggerFactory.getLogger(BollettazioneComunicazioniMassiveDAOImpl.class);

    @Override
    public Class getEntityClass() {

	return BollMassiveT.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void collegaBollettazioneAComunicazioni(int idTestata, int idBollettazione) {

	BollMassiveT entity = new BollMassiveT();
	entity.setBollGestTestata((BollGestTestata) getById(BollGestTestata.class, new PkId(idBollettazione)));
	entity.setMassiveTestata((MassiveTestata) getById(MassiveTestata.class, new PkId(idTestata)));
	saveEntity(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void collegaDettaglioBollettazioneADettaglioComunicazioni(int idDettaglioComunicazione, int idDettaglioBollettazione) {

	BollMassiveD entity = new BollMassiveD();
	entity.setBollGestDettaglio((BollGestDettaglio) getById(BollGestDettaglio.class, new PkId(idDettaglioBollettazione)));
	entity.setMassiveDettaglio((MassiveDettaglio) getById(MassiveDettaglio.class, new PkId(idDettaglioComunicazione)));
	// per la bollettazione non sono previsti movimenti
	saveEntity(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DettaglioBollettazione> getDettagli(FiltriRicercaDettagli filtri) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	ComunicazioniBollGestDettaglioQueryHelper queryHelper = new ComunicazioniBollGestDettaglioQueryHelper(sessimpl, filtri);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollMassiveT.class);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(DettaglioBollettazione.class));
	return q.list();
    }

    @Override
    public ComunicazioneBollettazione getComunicazioneBollettazione(FiltriRicercaTestata filtri) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	ComunicazioneBollettazioneQueryHelper queryHelper = new ComunicazioneBollettazioneQueryHelper(sessimpl, filtri);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollMassiveT.class);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(ComunicazioneBollettazione.class));
	return (ComunicazioneBollettazione) q.list().get(0);
    }

    @Override
    public boolean exists(Integer idTestata) {

	if (idTestata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione per la bollettazione senza passare l'id della comunicazione");
	}
	String sql = "select count(id) as conteggio from boll_massive_t where idcomune = ? and fkid_testata = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollMassiveT.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idTestata);
	query.addScalar("conteggio", Hibernate.INTEGER);
	return new Integer(query.uniqueResult().toString()) > 0;
    }

    @Override
    public BollMassiveT findByIdTestata(Integer idTestata) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("massiveTestata.id.codice", idTestata, Integer.class));
	ft.addRestriction(fr);
	List<BollMassiveT> massive = this.findByFilterTable(ft);
	if (massive == null || massive.isEmpty()) {
	    return new BollMassiveT();
	}
	if (massive.size() > 1) {
	    throw new RuntimeException(
		    "Caso anomalo: la comunicazione massiva con id " + idTestata + " è collegata a " + massive.size() + " bollettazioni");
	}
	return massive.get(0);
    }
}
