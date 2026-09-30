package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzestradarioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.SorteggidettaglioDTO;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioDAO;

/**
 * 
 * @author francescop
 */
@Repository
public class SorteggidettaglioDAOImpl extends BaseDAOImpl<Sorteggidettaglio, PkId> implements SorteggidettaglioDAO {

    private IstanzestradarioDAO istanzestradarioDAO;

    @Autowired
    public void setIstanzestradarioDAO(IstanzestradarioDAO istanzestradarioDAO) {

	this.istanzestradarioDAO = istanzestradarioDAO;
    }

    @Override
    public Class<Sorteggidettaglio> getEntityClass() {

	return Sorteggidettaglio.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Sorteggidettaglio findByIstanza(Istanze istanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("istanzeId", istanza.getId().getCodice()));
	DetachedCriteria testata = det.createCriteria("sorteggitestata");
	testata.addOrder(Order.desc("stDatasorteggio"));
	testata.addOrder(Order.desc("id.codice"));
	List<Sorteggidettaglio> sorteggidettaglios = getHibernateTemplate().findByCriteria(det);
	if (!sorteggidettaglios.isEmpty()) {
	    return sorteggidettaglios.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Sorteggidettaglio> findBySorteggitestata(Sorteggitestata sorteggitestata) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("sorteggitestata", sorteggitestata));
	det.createAlias("istanza", "_istanza", Criteria.LEFT_JOIN);
	det.addOrder(Order.desc("_istanza.data"));
	det.addOrder(Order.desc("_istanza.numeroistanza"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCodiciIstanzaBySorteggitestata(Integer codiceSorteggitestata) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("sorteggitestataId", codiceSorteggitestata));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.groupProperty("istanzeId"));
	det.setProjection(projectionList);
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCodiciIstanzaBySorteggicategoria(Integer codiceSorteggiCategoria) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("sorteggitestata", "_sorteggitestata", Criteria.INNER_JOIN);
	det.add(Restrictions.eq("_sorteggitestata.categoriaId", codiceSorteggiCategoria));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.groupProperty("istanzeId"));
	det.setProjection(projectionList);
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SorteggidettaglioDTO> findBySorteggitestata(Integer codice) {

	//////////////////////////////////////////// CONDIZIONI DI FROM ///////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("sorteggitestata.id.codice", codice));
	det.createAlias("istanza", "_istanza");
	det.createAlias("_istanza.alberoproc", "_alberoproc", Criteria.LEFT_JOIN);
	det.createAlias("_alberoproc.vwAlberoproc", "_vwAlberoproc", Criteria.LEFT_JOIN);
	det.createAlias("_istanza.tipiarchivioistanza", "_tipiarchivioistanza", Criteria.LEFT_JOIN);
	det.createAlias("_istanza.responsabile", "_responsabile", Criteria.LEFT_JOIN);
	det.createAlias("_istanza.istruttore", "_istruttore", Criteria.LEFT_JOIN);
	det.createAlias("_istanza.responsabileProcedimento", "_responsabileProcedimento", Criteria.LEFT_JOIN);
	det.createAlias("_istanza.richiedente", "_richiedente", Criteria.LEFT_JOIN);
	det.createAlias("_istanza.titolarelegale", "_titolarelegale", Criteria.LEFT_JOIN);
	det.createAlias("_istanza.tipisoggetto", "_tipisoggetto", Criteria.LEFT_JOIN);
	det.createAlias("_istanza.comune", "_comune", Criteria.LEFT_JOIN);
	///////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////// CONDIZIONI DI SELECT ///////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("_istanza.id.codice"), "codiceistanza");
	//istanza.numeroistanza
	plist.add(Projections.property("_istanza.numeroistanza"), "numeroistanza");
	//istanza.data
	plist.add(Projections.property("_istanza.data"), "dataistanza");
	//istanza.numeroprotocollo
	plist.add(Projections.property("_istanza.numeroprotocollo"), "numeroprotocollo");
	//istanza.dataprotocollo
	plist.add(Projections.property("_istanza.dataprotocollo"), "dataprotocollo");
	//istanza.alberoproc.vwAlberoproc.scDescrizione
	plist.add(Projections.property("_vwAlberoproc.scDescrizione"), "scDescrizione");
	//istanza.tipiarchivioistanza.archivio
	plist.add(Projections.property("_tipiarchivioistanza.archivio"), "archivio");
	//istanza.lavori
	plist.add(Projections.property("_istanza.lavori"), "lavori");
	//istanza.responsabile.responsabile
	plist.add(Projections.property("_responsabile.responsabile"), "responsabile");
	//istanza.istruttore.responsabile
	plist.add(Projections.property("_istruttore.responsabile"), "responsabileIstruttore");
	//istanza.istruttore.responsabile
	plist.add(Projections.property("_responsabileProcedimento.responsabile"), "responsabileprocedimento");
	//sorteggiata
	plist.add(Projections.property("sorteggiata"), "sorteggiata");
	//flagInterventoObbligatorio
	plist.add(Projections.property("flagInterventoObbligatorio"), "flagInterventoObbligatorio");
	//istanza.richiedente.nominativo
	plist.add(Projections.property("_richiedente.nominativo"), "richiedenteNominativo");
	//istanza.richiedente.nome
	plist.add(Projections.property("_richiedente.nome"), "richiedenteNome");
	//istanza.richiedente.nome
	plist.add(Projections.property("_titolarelegale.nominativo"), "titolarelegaleNominativo");
	plist.add(Projections.property("_tipisoggetto.flgSpecificadescrizione"), "flgSpecificadescrizione");
	plist.add(Projections.property("_tipisoggetto.tiposoggetto"), "tiposoggetto");
	plist.add(Projections.property("_istanza.descrsoggetto"), "descrsoggetto");
	plist.add(Projections.property("_comune.comune"), "comune");
	det.setProjection(plist);
	det.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(SorteggidettaglioDTO.class));
	det.addOrder(Order.desc("_istanza.data"));
	String[] padNumeroistanza = new String[] { "20", "' '" };
	det.addOrder(OrderBySqlFormula.desc("_istanza.numeroistanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
	//List<SorteggidettaglioDTO> result=new ArrayList<SorteggidettaglioDTO>();
	List<SorteggidettaglioDTO> list = (List<SorteggidettaglioDTO>) getHibernateTemplate().findByCriteria(det);
	for (SorteggidettaglioDTO sorteggidettaglioDTO : list) {
	    IstanzestradarioDTO istanzestradarioDTO = istanzestradarioDAO.findIstanzeStradarioDTOByIstanza(sorteggidettaglioDTO.getCodiceistanza());
	    if (istanzestradarioDTO != null) {
		sorteggidettaglioDTO.setIstanzestradarioDTO(istanzestradarioDTO);
	    } else {
		sorteggidettaglioDTO.setIstanzestradarioDTO(new IstanzestradarioDTO());
	    }
	}
	return list;
    }
}
