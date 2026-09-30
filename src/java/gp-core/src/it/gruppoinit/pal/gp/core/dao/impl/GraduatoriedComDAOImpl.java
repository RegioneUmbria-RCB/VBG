package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.GraduatoriedComDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedComDTO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class GraduatoriedComDAOImpl extends BaseDAOImpl<GraduatoriedCom, PkId> implements GraduatoriedComDAO {

    @Override
    public Class<GraduatoriedCom> getEntityClass() {

	return GraduatoriedCom.class;
    }

    @Override
    public List<GraduatoriedCom> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer countGraduatoriedComDomande(Integer codiceGraduatoriatCom) {

	DetachedCriteria criteria = getBaseCountCriteria(codiceGraduatoriatCom);
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	Integer numero = 0;
	if (!list.isEmpty()) {
	    numero = list.get(0);
	}
	return numero;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer countGraduatoriedComMovimenti(Integer codiceGraduatoriatCom) {

	//GRADUATORIED_COM con IDCOMUNE, FK_TESTATA = GRADUATORIET_COM.ID + count 
	DetachedCriteria criteria = getBaseCountCriteria(codiceGraduatoriatCom);
	//GRADUATORIED_COM.CODICEMOVIMENTO NOT NULL
	criteria.createAlias("movimenti", "_movimenti", DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.isNotNull("_movimenti.id.codice"));
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	Integer numero = 0;
	if (!list.isEmpty()) {
	    numero = list.get(0);
	}
	return numero;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer countGraduatoriedComAllegati(Integer codiceGraduatoriatCom) {

	//GRADUATORIED_COM con IDCOMUNE, FK_TESTATA = GRADUATORIET_COM.ID + count 
	DetachedCriteria criteria = getBaseCountCriteria(codiceGraduatoriatCom);
	// GRADUATORIED_COM.CODICEOGGETTO NOT NULL (codice dell'oggetto collegato al movimento allegato)
	criteria.createAlias("oggetti", "_oggetti", DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.isNotNull("_oggetti.id.codice"));
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	Integer numero = 0;
	if (!list.isEmpty()) {
	    numero = list.get(0);
	}
	return numero;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer countGraduatoriedComMailInviate(Integer codiceGraduatoriatCom) {

	//GRADUATORIED_COM con IDCOMUNE, FK_TESTATA = GRADUATORIET_COM.ID + count 
	DetachedCriteria criteria = getBaseCountCriteria(codiceGraduatoriatCom);
	//GRADUATORIED_COM.FK_IDMAIL NOT NULL 
	criteria.createAlias("movimentimail", "_movimentimail", DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.isNotNull("_movimentimail.id.codice"));
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	Integer numero = 0;
	if (!list.isEmpty()) {
	    numero = list.get(0);
	}
	return numero;
    }

    /**
     * Crea il criterio base idcomune,FK_GT_ID = GRADUATORIET_COM.ID
     */
    private DetachedCriteria getBaseCountCriteria(Integer codiceGraduatoriatCom) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("graduatorietCom", "_graduatorietCom", DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.eq("_graduatorietCom.id.codice", codiceGraduatoriatCom));
	criteria.setProjection(Projections.rowCount());
	return criteria;
    }

    @Override
    public List<GraduatoriedComDTO> findGraduatoriedComDTOByGraduatoriatCom(Integer codiceGraduatoriatCom, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = getBaseCriteriaFilterByGraduatoriaT(codiceGraduatoriatCom);
	List<GraduatoriedComDTO> list = null;
	if (null != firstResult && null != maxResult) {
	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    list = getHibernateTemplate().findByCriteria(criteria);
	}
	return list;
    }

    /**
     * <pre>
     * Il metodo privato ha il compito di settare il criteri di base di:
     * 		a.SELECT	:
     * 		b.WHERE 	: graduatoriet.id.codice
     * 		c.ORDERBY 	: posizione
     * @param graduatoriet
     * @return criteria :DetachedCriteria
     * </pre>
     */
    private DetachedCriteria getBaseCriteriaFilterByGraduatoriaT(Integer codiceGraduatoriatCom) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("graduatorietCom.id.codice", codiceGraduatoriatCom));
	criteria.createAlias("graduatoried", "_graduatoried");
	criteria.createAlias("graduatoried.istanza", "_istanza");
	criteria.createAlias("oggetti", "_oggetti", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("movimenti", "_movimenti", DetachedCriteria.LEFT_JOIN);
	//	criteria.createAlias("_movimenti.istanzeeventis", "_istanzeeventis", Criteria.LEFT_JOIN);
	criteria.createAlias("movimentimail", "_movimentimail", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanza.richiedente", "_richiedente", Criteria.LEFT_JOIN);
	criteria.createAlias("_richiedente.formagiuridica", "_richiedenteFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegale", "_titolarelegale", Criteria.LEFT_JOIN);
	criteria.createAlias("_titolarelegale.formagiuridica", "_titolarelegaleFormaGiuridica", Criteria.LEFT_JOIN);
	//	criteria.createAlias("_istanzeeventis.categorieeventibase", "_categorieeventibase", Criteria.LEFT_JOIN);
	//	criteria.add(Restrictions.eq("_categorieeventibase.id", IstanzeeventiConstants.CATEGORIA_COMUNICAZIONI_GRADUATORIE));
	criteria.addOrder(Order.asc("_graduatoried.posizione"));
	ProjectionList plist = Projections.projectionList();
	//GRADUATORIEDCOM
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("_oggetti.id.codice"), "OGGETTO");
	plist.add(Projections.property("_movimenti.id.codice"), "MOVIMENTI");
	plist.add(Projections.property("_movimenti.movimento"), "DESCMOVIMENTI");
	plist.add(Projections.property("_movimentimail.id.codice"), "MOVIMENTIMAIL");
	//	plist.add(Projections.property("_istanzeeventis.id"), "ISTANZEEVENTI_ID");
	//GRADUATORIED
	plist.add(Projections.property("_graduatoried.id.codice"), "GRADUATORIED_ID_CODICE");
	plist.add(Projections.property("_graduatoried.posizione"), "GRADUATORIED_POSIZIONE");
	//ISTANZA
	plist.add(Projections.property("_istanza.id.codice"), "GRADUATORIED_ISTANZA_ID_CODICE");
	plist.add(Projections.property("_istanza.numeroistanza"), "GRADUATORIED_ISTANZA_NUMEROISTANZA");
	plist.add(Projections.property("_istanza.software.codice"), "GRADUATORIED_ISTANZA_SOFTWARE");
	//RICHIEDENTE
	plist.add(Projections.property("_richiedente.id.codice"), "GRADUATORIED_ISTANZA_RICHIEDENTE_ID_CODICE");
	plist.add(Projections.property("_richiedente.tipoanagrafe"), "GRADUATORIED_ISTANZA_RICHIEDENTE_TIPOANAGRAFE");
	plist.add(Projections.property("_richiedente.nominativo"), "GRADUATORIED_ISTANZA_RICHIEDENTE_NOMINATIVO");
	plist.add(Projections.property("_richiedente.nome"), "GRADUATORIED_ISTANZA_RICHIEDENTE_NOME");
	plist.add(Projections.property("_richiedente.codicefiscale"), "GRADUATORIED_ISTANZA_RICHIEDENTE_CODICEFISCALE");
	plist.add(Projections.property("_richiedenteFormaGiuridica.formagiuridica"), "GRADUATORIED_ISTANZA_RICHIEDENTE_FORMAGIURIDICA");
	plist.add(Projections.property("_richiedente.partitaiva"), "GRADUATORIED_ISTANZA_RICHIEDENTE_PARTITAIVA");
	plist.add(Projections.property("_richiedente.tipologia"), "GRADUATORIED_ISTANZA_RICHIEDENTE_TIPOLOGIA");
	plist.add(Projections.property("_richiedente.flagDisabilitato"), "GRADUATORIED_ISTANZA_RICHIEDENTE_FLAGDISABILITATO");
	//TITOLARE LEGALE
	plist.add(Projections.property("_titolarelegale.id.codice"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_ID_CODICE");
	plist.add(Projections.property("_titolarelegale.tipoanagrafe"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_TIPOANAGRAFE");
	plist.add(Projections.property("_titolarelegale.nominativo"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_NOMINATIVO");
	plist.add(Projections.property("_titolarelegale.nome"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_NOME");
	plist.add(Projections.property("_titolarelegale.codicefiscale"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_CODICEFISCALE");
	plist.add(Projections.property("_titolarelegaleFormaGiuridica.formagiuridica"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_FORMAGIURIDICA");
	plist.add(Projections.property("_titolarelegale.partitaiva"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_PARTITAIVA");
	plist.add(Projections.property("_titolarelegale.tipologia"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_TIPOLOGIA");
	plist.add(Projections.property("_titolarelegale.flagDisabilitato"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_FLAGDISABILITATO");
	//
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(GraduatoriedComDTO.class));
	return criteria;
    }
}
