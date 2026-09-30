package it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.opensaml.artifact.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazione;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazioneId;

/**
 * 
 * @author
 */
@Repository
public class DocumentiAutorizzazioneDAOImpl extends BaseDAOImpl<DocumentiAutorizzazione, DocumentiAutorizzazioneId>
	implements DocumentiAutorizzazioneDAO {

    @Autowired
    private IstanzeDAO istanzeDAO;

    @Override
    public Class<DocumentiAutorizzazione> getEntityClass() {

	return DocumentiAutorizzazione.class;
    }

    @Override
    public List<DocumentiAutorizzazione> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentiAutorizzazioneDTO> findDocumentiAutorizzazioneDTOByAutorizzazione(Integer codiceAutorizzazione) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.idautorizzazione", codiceAutorizzazione));
	// creo gli alias
	criteria.createAlias("documentiistanza", "_documentiistanza", Criteria.LEFT_JOIN);
	criteria.createAlias("movimentiallegati", "_movimentiallegati", Criteria.LEFT_JOIN);
	criteria.createAlias("istanzeallegati", "_istanzeallegati", Criteria.LEFT_JOIN);
	criteria.createAlias("anagrafedocumenti", "_anagrafedocumenti", Criteria.LEFT_JOIN);
	criteria.createAlias("istanzeprocure", "_istanzeprocure", Criteria.LEFT_JOIN);
	criteria.createAlias("oggetti", "_oggetti", Criteria.LEFT_JOIN);
	criteria.createAlias("autorizzazioni", "_autorizzazioni", Criteria.LEFT_JOIN);
	criteria.createAlias("_documentiistanza.istanza", "_istanza", Criteria.LEFT_JOIN);
	criteria.createAlias("_movimentiallegati.movimento", "_movimento", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanzeallegati.inventarioprocedimenti", "_inventarioprocedimenti", Criteria.LEFT_JOIN);
	criteria.createAlias("_anagrafedocumenti.anagrafe", "_anagrafe", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanzeprocure.anagrafeProcuratore", "_anagrafeprocuratore", Criteria.LEFT_JOIN);
	criteria.createAlias("_anagrafedocumenti.tipidocumento", "_tipidocumento", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanzeprocure.oggettiDocIdent", "_oggettiDocIdent", Criteria.LEFT_JOIN);
	//
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codiceoggetto"), "ID_CODICEOGGETTO");
	plist.add(Projections.property("id.idautorizzazione"), "ID_IDAUTORIZZAZIONE");
	plist.add(Projections.property("_istanza.id.codice"), "CODICEISTANZA");
	plist.add(Projections.property("_movimento.id.codice"), "CODICEMOVIMENTO");
	plist.add(Projections.property("_inventarioprocedimenti.id.codice"), "CODICEINVENTARIO");
	plist.add(Projections.property("_anagrafe.id.codice"), "CODICEANAGRAFE");
	plist.add(Projections.property("_documentiistanza.id.codice"), "CODICEDOCUMENTIISTANZA");
	plist.add(Projections.property("_movimentiallegati.id.codice"), "CODICEMOVIMENTIALLEGATI");
	plist.add(Projections.property("_istanzeallegati.id.codice"), "CODICEISTANZEALLEGATI");
	plist.add(Projections.property("_anagrafedocumenti.id.codice"), "CODICEANAGRAFEDOCUMENTI");
	plist.add(Projections.property("_istanzeprocure.id.codice"), "CODICEISTANZEPROCURE");
	plist.add(Projections.property("_oggetti.nomefile"), "NOMEFILE");
	plist.add(Projections.property("_autorizzazioni.autoriznumero"), "NUMEROAUTORIZZAZIONE");
	plist.add(Projections.property("_istanza.numeroistanza"), "NUMEROISTANZA");
	plist.add(Projections.property("_movimento.movimento"), "MOVIMENTO");
	plist.add(Projections.property("_inventarioprocedimenti.procedimento"), "PROCEDIMENTO");
	plist.add(Projections.property("_anagrafe.nominativo"), "NOMINATIVO");
	plist.add(Projections.property("_anagrafe.nome"), "NOME");
	plist.add(Projections.property("_anagrafeprocuratore.nominativo"), "PROCURE");
	plist.add(Projections.property("_documentiistanza.documento"), "DESCFILEDOCUMENTIISTANZA");
	plist.add(Projections.property("_movimentiallegati.descrizione"), "DESCFILEMOVIMENTIALLEGATI");
	plist.add(Projections.property("_istanzeallegati.allegatoextra"), "DESCFILEISTANZEALLEGATI");
	plist.add(Projections.property("_tipidocumento.documento"), "DESCFILEDOCUMENTIANAGRAFE");
	plist.add(Projections.property("_oggettiDocIdent.nomefile"), "DESCFILEISTANZEPROCURE");
	plist.add(Projections.property("principale"), "PRINCIPALE");
	//
	criteria.setProjection(plist);
	criteria.addOrder(Order.desc("principale"));
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(DocumentiAutorizzazioneDTO.class));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public void impostaDocumentoPrincipale(Integer codiceAutorizzazione, Integer codiceOggetto) {

	if (codiceAutorizzazione == null) {
	    throw new InvalidArgumentException("Impossibile richiamare impostaDocumentoPrincipale senza passare il riferimento dell'autorizzazione");
	}
	//1. tolgo l'eventuale vecchio principale
	String sql = "update documenti_autorizzazione set principale = ? where idcomune = ? and id_autorizzazione = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(DocumentiAutorizzazione.class);
	query.setInteger(0, 0);
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, codiceAutorizzazione);
	query.executeUpdate();
	if (codiceOggetto == null) {
	    return;
	}
	//2- imposto il nuovo principale
	sql = "update documenti_autorizzazione set principale = ? where idcomune = ? and id_autorizzazione = ? and codiceoggetto = ?";
	query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(DocumentiAutorizzazione.class);
	query.setInteger(0, 1);
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, codiceAutorizzazione);
	query.setInteger(3, codiceOggetto);
	query.executeUpdate();
    }

    @Override
    public boolean documentPrincipalePresente(Integer idAutorizzazione) {

	String sql = "select count(*) as conteggio from documenti_autorizzazione where idcomune = ? and id_autorizzazione = ? and principale = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(DocumentiAutorizzazione.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAutorizzazione);
	query.setInteger(2, 1);
	query.addScalar("conteggio", Hibernate.INTEGER);
	return Integer.parseInt(query.uniqueResult().toString()) > 0;
    }
}
