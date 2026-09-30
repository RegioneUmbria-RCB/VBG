package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author
 */
@SuppressWarnings("rawtypes")
@Repository
public class MovimentiZipLogicoDAOImpl extends BaseDAOImpl<MovimentiZipLogico, PkId> implements MovimentiZipLogicoDAO {

    @Override
    public Class<MovimentiZipLogico> getEntityClass() {

	return MovimentiZipLogico.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MovimentiZipLogico> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MovimentiZipLogicoDTO> findMovimentiZipLogicoDTOByMovimento(Integer codicemovimento) {

	DetachedCriteria detachedCriteria = newCriteria(codicemovimento);
	ProjectionList projectionList = newProjectionList();
	detachedCriteria.setProjection(projectionList);
	detachedCriteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MovimentiZipLogicoDTO.class));
	return getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    private DetachedCriteria newCriteria(Integer codicemovimento) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	///
	detachedCriteria.createAlias("movimenti", "_movimenti", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("oggetti", "_oggetti", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("movimentiallegati", "_movimentiallegati", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("documentiistanza", "_documentiistanza", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("istanzeallegati", "_istanzeallegati", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("anagrafedocumenti", "_anagrafedocumenti", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("istanzeprocure", "_istanzeprocure", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("cdsatti", "_cdsatti", CriteriaSpecification.LEFT_JOIN);
	///
	detachedCriteria.add(Restrictions.eq("_movimenti.id.codice", codicemovimento));
	///
	detachedCriteria.createAlias("_documentiistanza.istanza", "_istanza", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("_istanzeallegati.inventarioprocedimenti", "_inventarioprocedimenti", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("_anagrafedocumenti.anagrafe", "_anagrafe", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("_anagrafedocumenti.tipidocumento", "_tipidocumento", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("_istanzeprocure.anagrafeProcuratore", "_anagrafeprocuratore", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("_istanzeprocure.oggettiDocIdent", "_oggettiDocIdent", CriteriaSpecification.LEFT_JOIN);
	detachedCriteria.createAlias("_cdsatti.cds", "_cds", CriteriaSpecification.LEFT_JOIN);
	return detachedCriteria;
    }

    private ProjectionList newProjectionList() {

	ProjectionList projectionList = Projections.projectionList();
	///
	projectionList.add(Projections.property("id.codice"), "ID_CODICE");
	projectionList.add(Projections.property("_movimenti.id.codice"), "CODICEMOVIMENTO");
	projectionList.add(Projections.property("_oggetti.id.codice"), "CODICEOGGETTO");
	projectionList.add(Projections.property("_istanza.id.codice"), "CODICEISTANZA");
	projectionList.add(Projections.property("_inventarioprocedimenti.id.codice"), "CODICEINVENTARIO");
	projectionList.add(Projections.property("_anagrafe.id.codice"), "CODICEANAGRAFE");
	projectionList.add(Projections.property("_documentiistanza.id.codice"), "CODICEDOCUMENTIISTANZA");
	projectionList.add(Projections.property("_movimentiallegati.id.codice"), "CODICEMOVIMENTIALLEGATI");
	projectionList.add(Projections.property("_istanzeallegati.id.codice"), "CODICEISTANZEALLEGATI");
	projectionList.add(Projections.property("_anagrafedocumenti.id.codice"), "CODICEANAGRAFEDOCUMENTI");
	projectionList.add(Projections.property("_istanzeprocure.id.codice"), "CODICEISTANZEPROCURE");
	projectionList.add(Projections.property("_cds.id.codice"), "CODICECDSATTI");
	projectionList.add(Projections.property("_istanza.numeroistanza"), "NUMEROISTANZA");
	projectionList.add(Projections.property("_oggetti.nomefile"), "NOMEFILE");
	projectionList.add(Projections.property("_movimenti.movimento"), "MOVIMENTO");
	projectionList.add(Projections.property("_inventarioprocedimenti.procedimento"), "PROCEDIMENTO");
	projectionList.add(Projections.property("_anagrafe.nominativo"), "NOMINATIVO");
	projectionList.add(Projections.property("_anagrafe.nome"), "NOME");
	projectionList.add(Projections.property("_anagrafeprocuratore.nominativo"), "PROCURE");
	projectionList.add(Projections.property("_documentiistanza.documento"), "DESCFILEDOCUMENTIISTANZA");
	projectionList.add(Projections.property("_movimentiallegati.descrizione"), "DESCFILEMOVIMENTIALLEGATI");
	projectionList.add(Projections.property("_istanzeallegati.allegatoextra"), "DESCFILEISTANZEALLEGATI");
	projectionList.add(Projections.property("_tipidocumento.documento"), "DESCFILEDOCUMENTIANAGRAFE");
	projectionList.add(Projections.property("_oggettiDocIdent.nomefile"), "DESCFILEISTANZEPROCURE");
	projectionList.add(Projections.property("_cdsatti.fileverbale"), "DESCFILECDSATTI");
	return projectionList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<MovimentiZipLogico> findMovimentiZipLogicoByMovimento(Integer codicemovimento) {

	Assert.notNull(codicemovimento);
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("movimenti.id.codice", codicemovimento));
	return new HashSet<MovimentiZipLogico>(getHibernateTemplate().findByCriteria(det));
    }

    public MovimentiZipLogico getMovimentiZipLogicoById(PkId id) {

	return this.getById(MovimentiZipLogico.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void eliminaZipLogicoByCodiceMovimento(Integer codiceMovimento) {

	Set<MovimentiZipLogico> lista = this.findMovimentiZipLogicoByMovimento(codiceMovimento);
	for (MovimentiZipLogico dettaglio : lista) {
	    this._delete(dettaglio);
	}
    }

    @Override
    public Set<MovimentiZipLogicoTestataHelper> recuperaTestateMancanti() {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryTestateMancantiHelper queryHelper = new QueryTestateMancantiHelper(sessimpl);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(MovimentiZipLogicoTestataHelper.class));
	return new HashSet<MovimentiZipLogicoTestataHelper>(q.list());
    }

    public Boolean isDocumentoPresenteInZipLogico(Integer codiceZipLogico, Integer codiceMovimento, Integer codiceDocumento, String associationPath) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (codiceZipLogico != null) {
	    fr.addFilterField(FilterUtils.equals("codice", codiceZipLogico, "id", Integer.class));
	}
	if (codiceMovimento != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceMovimento, "movimenti", Integer.class));
	}
	if (codiceDocumento != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceDocumento, associationPath, Integer.class));
	}
	filterTable.addRestriction(fr);
	return this.existsRecords(filterTable);
    }
}
