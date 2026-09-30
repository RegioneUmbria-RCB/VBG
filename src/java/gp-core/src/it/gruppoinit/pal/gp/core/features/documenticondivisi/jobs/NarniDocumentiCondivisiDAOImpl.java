package it.gruppoinit.pal.gp.core.features.documenticondivisi.jobs;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.DocumentiCondivisiHelper;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.DocumentoMancanteInCondivisioneDocumentale;

@Repository
public class NarniDocumentiCondivisiDAOImpl extends BaseDAOImpl implements NarniDocumentiCondivisiDAO {

    private Integer numeroMassimoDocumenti;

    @Override
    public Class getEntityClass() {

	return NarniDocumentiCondivisiDAOImpl.class;
    }

    public Integer getNumeroMassimoDocumenti() {

	return numeroMassimoDocumenti;
    }

    public void setNumeroMassimoDocumenti(Integer numeroMassimoDocumenti) {

	this.numeroMassimoDocumenti = numeroMassimoDocumenti;
    }

    public List<DocumentiCondivisiHelper> getDocumentiDaInviare() {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	NarniQueryDocCondivisiHelper queryHelper = new NarniQueryDocCondivisiHelper(sessimpl);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(DocumentiCondivisiHelper.class));
	if (this.numeroMassimoDocumenti != null) {
	    q.setMaxResults(this.numeroMassimoDocumenti);
	}
	return (List<DocumentiCondivisiHelper>) q.list();
    }

    @Override
    public List<DocumentoMancanteInCondivisioneDocumentale> getDocumentiDaCondividere() {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	NarniQueryDocDaCondividereHelper queryHelper = new NarniQueryDocDaCondividereHelper(sessimpl);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(DocumentoMancanteInCondivisioneDocumentale.class));
	if (this.numeroMassimoDocumenti != null) {
	    q.setMaxResults(this.numeroMassimoDocumenti);
	}
	return (List<DocumentoMancanteInCondivisioneDocumentale>) q.list();
    }
}
