package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeDaConsolidareHelper;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
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
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class MercatipresenzeDDAOImpl extends BaseDAOImpl<MercatipresenzeD, PkId> implements MercatipresenzeDDAO {

    @Override
    public Class<MercatipresenzeD> getEntityClass() {

	return MercatipresenzeD.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MercatipresenzeD findByMercatiPresenzeTAndPosteggio(MercatipresenzeT giornoMercato, MercatiD posteggio) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("mercatiPresenzeT.id.codice", giornoMercato.getId().getCodice()));
	criteria.add(Restrictions.eq("posteggio.id.codice", posteggio.getId().getCodice()));
	List<MercatipresenzeD> list = getHibernateTemplate().findByCriteria(criteria);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeD> findListaPresentiSenzaPosteggio(MercatipresenzeT giorno) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// pongo le condizioni di where
	criteria.add(Restrictions.eq("mercatiPresenzeT.id.codice", giorno.getId().getCodice()));
	// left join con MercatiD
	criteria.createAlias("posteggio", "_posteggio", Criteria.LEFT_JOIN);
	// no posteggio
	criteria.add(Restrictions.isNull("_posteggio.id.codice"));
	List<MercatipresenzeD> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    //    @SuppressWarnings("unchecked")
    //    @Override
    //    public List<MercatipresenzeD> findListaPosteggi(MercatipresenzeT giorno) {
    //
    //	DetachedCriteria criteria = getIdcomuneCriteria();
    //	// pongo le condizioni di where
    //	criteria.add(Restrictions.eq("mercatiPresenzeT.id.codice", giorno.getId().getCodice()));
    //	// join con MercatiD
    //	criteria.createAlias("posteggio", "_posteggio");
    //	// order by codiceposteggio
    //	criteria.addOrder(Order.asc("_posteggio.codiceposteggio"));
    //	List<MercatipresenzeD> list = getHibernateTemplate().findByCriteria(criteria);
    //	return list;
    //    }
    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeDDTO> findListaPosteggi(MercatipresenzeT giorno) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// pongo le condizioni di where
	criteria.add(Restrictions.eq("mercatiPresenzeT.id.codice", giorno.getId().getCodice()));
	//
	criteria.createAlias("posteggio", "_posteggio");
	criteria.createAlias("_posteggio.tipoSpazio", "_posteggioTipoSpazio", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("occupante", "_occupante", Criteria.LEFT_JOIN);
	criteria.createAlias("_occupante.formagiuridica", "_occupanteFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("concessionario", "_concessionario", Criteria.LEFT_JOIN);
	criteria.createAlias("_concessionario.formagiuridica", "_concessionarioFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioni", "_aut", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.autorizcomune", "_autAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.tipologiaregistro", "_autTipologiaRegistro", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioneConcessionarioAssente", "_autConcAssente", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.autorizcomune", "_autConcAssenteAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.tipologiaregistro", "_autConcAssenteTipologiaRegistro", Criteria.LEFT_JOIN);
	// order by codiceposteggio
	criteria.addOrder(Order.asc("_posteggio.codiceposteggio"));
	//
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("flagAssenzaGiust"), "FLAGASSENZAGIUST");
	plist.add(Projections.property("catMerc"), "CATMERC");
	plist.add(Projections.property("spuntista"), "SPUNTISTA");
	plist.add(Projections.property("motivazione"), "MOTIVAZIONE");
	plist.add(Projections.property("proprietario"), "PROPRIETARIO");
	//
	plist.add(Projections.property("_posteggio.id.codice"), "POSTEGGIO_ID_CODICE");
	plist.add(Projections.property("_posteggio.codiceposteggio"), "POSTEGGIO_CODICEPOSTEGGIO");
	plist.add(Projections.property("_posteggio.note"), "POSTEGGIO_NOTE");
	plist.add(Projections.property("_posteggio.superficie"), "POSTEGGIO_SUPERFICIE");
	plist.add(Projections.property("_posteggio.lunghezza"), "POSTEGGIO_LUNGHEZZA");
	plist.add(Projections.property("_posteggio.larghezza"), "POSTEGGIO_LARGHEZZA");
	plist.add(Projections.property("_posteggioTipoSpazio.tipospazio"), "POSTEGGIO_TIPOSPAZIO");
	//
	plist.add(Projections.property("_occupante.id.codice"), "OCCUPANTE_ID_CODICE");
	plist.add(Projections.property("_occupante.tipoanagrafe"), "OCCUPANTE_TIPOANAGRAFE");
	plist.add(Projections.property("_occupante.nominativo"), "OCCUPANTE_NOMINATIVO");
	plist.add(Projections.property("_occupante.nome"), "OCCUPANTE_NOME");
	plist.add(Projections.property("_occupante.codicefiscale"), "OCCUPANTE_CODICEFISCALE");
	plist.add(Projections.property("_occupanteFormaGiuridica.formagiuridica"), "OCCUPANTE_FORMAGIURIDICA");
	plist.add(Projections.property("_occupante.partitaiva"), "OCCUPANTE_PARTITAIVA");
	plist.add(Projections.property("_occupante.tipologia"), "OCCUPANTE_TIPOLOGIA");
	plist.add(Projections.property("_occupante.flagDisabilitato"), "OCCUPANTE_FLAGDISABILITATO");
	//
	plist.add(Projections.property("_concessionario.id.codice"), "CONCESSIONARIO_ID_CODICE");
	plist.add(Projections.property("_concessionario.tipoanagrafe"), "CONCESSIONARIO_TIPOANAGRAFE");
	plist.add(Projections.property("_concessionario.nominativo"), "CONCESSIONARIO_NOMINATIVO");
	plist.add(Projections.property("_concessionario.nome"), "CONCESSIONARIO_NOME");
	plist.add(Projections.property("_concessionario.codicefiscale"), "CONCESSIONARIO_CODICEFISCALE");
	plist.add(Projections.property("_concessionarioFormaGiuridica.formagiuridica"), "CONCESSIONARIO_FORMAGIURIDICA");
	plist.add(Projections.property("_concessionario.partitaiva"), "CONCESSIONARIO_PARTITAIVA");
	plist.add(Projections.property("_concessionario.tipologia"), "CONCESSIONARIO_TIPOLOGIA");
	plist.add(Projections.property("_concessionario.flagDisabilitato"), "CONCESSIONARIO_FLAGDISABILITATO");
	//
	plist.add(Projections.property("_aut.id.codice"), "AUTORIZZAZIONI_ID_CODICE");
	plist.add(Projections.property("_aut.autoriznumero"), "AUTORIZZAZIONI_AUTORIZNUMERO");
	plist.add(Projections.property("_aut.autorizdata"), "AUTORIZZAZIONI_AUTORIZDATA");
	plist.add(Projections.property("_autAutorizComune.comune"), "AUTORIZZAZIONI_AUTORIZCOMUNE");
	plist.add(Projections.property("_autTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONI_TIPOLOGIAREGISTRO");
	//
	plist.add(Projections.property("_autConcAssente.id.codice"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_ID_CODICE");
	plist.add(Projections.property("_autConcAssente.autoriznumero"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZNUMERO");
	plist.add(Projections.property("_autConcAssente.autorizdata"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZDATA");
	plist.add(Projections.property("_autConcAssenteAutorizComune.comune"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZCOMUNE");
	plist.add(Projections.property("_autConcAssenteTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_TIPOLOGIAREGISTRO");
	//
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MercatipresenzeDDTO.class));
	List<MercatipresenzeDDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MercatipresenzeD findByMercatiPosteggio(MercatiD posteggio) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("posteggio.id.codice", posteggio.getId().getCodice()));
	List<MercatipresenzeD> list = getHibernateTemplate().findByCriteria(criteria);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public MercatipresenzeDDTO findByIdLazy(Integer codicePresenza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.codice", codicePresenza));
	//
	criteria.createAlias("posteggio", "_posteggio");
	criteria.createAlias("_posteggio.tipoSpazio", "_posteggioTipoSpazio", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("occupante", "_occupante", Criteria.LEFT_JOIN);
	criteria.createAlias("_occupante.formagiuridica", "_occupanteFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("concessionario", "_concessionario", Criteria.LEFT_JOIN);
	criteria.createAlias("_concessionario.formagiuridica", "_concessionarioFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioni", "_aut", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.autorizcomune", "_autAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.tipologiaregistro", "_autTipologiaRegistro", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioneConcessionarioAssente", "_autConcAssente", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.autorizcomune", "_autConcAssenteAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.tipologiaregistro", "_autConcAssenteTipologiaRegistro", Criteria.LEFT_JOIN);
	//
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("flagAssenzaGiust"), "FLAGASSENZAGIUST");
	plist.add(Projections.property("catMerc"), "CATMERC");
	plist.add(Projections.property("spuntista"), "SPUNTISTA");
	plist.add(Projections.property("motivazione"), "MOTIVAZIONE");
	plist.add(Projections.property("proprietario"), "PROPRIETARIO");
	//
	plist.add(Projections.property("_posteggio.id.codice"), "POSTEGGIO_ID_CODICE");
	plist.add(Projections.property("_posteggio.codiceposteggio"), "POSTEGGIO_CODICEPOSTEGGIO");
	plist.add(Projections.property("_posteggio.note"), "POSTEGGIO_NOTE");
	plist.add(Projections.property("_posteggio.superficie"), "POSTEGGIO_SUPERFICIE");
	plist.add(Projections.property("_posteggio.lunghezza"), "POSTEGGIO_LUNGHEZZA");
	plist.add(Projections.property("_posteggio.larghezza"), "POSTEGGIO_LARGHEZZA");
	plist.add(Projections.property("_posteggioTipoSpazio.tipospazio"), "POSTEGGIO_TIPOSPAZIO");
	//
	plist.add(Projections.property("_occupante.id.codice"), "OCCUPANTE_ID_CODICE");
	plist.add(Projections.property("_occupante.tipoanagrafe"), "OCCUPANTE_TIPOANAGRAFE");
	plist.add(Projections.property("_occupante.nominativo"), "OCCUPANTE_NOMINATIVO");
	plist.add(Projections.property("_occupante.nome"), "OCCUPANTE_NOME");
	plist.add(Projections.property("_occupante.codicefiscale"), "OCCUPANTE_CODICEFISCALE");
	plist.add(Projections.property("_occupanteFormaGiuridica.formagiuridica"), "OCCUPANTE_FORMAGIURIDICA");
	plist.add(Projections.property("_occupante.partitaiva"), "OCCUPANTE_PARTITAIVA");
	plist.add(Projections.property("_occupante.tipologia"), "OCCUPANTE_TIPOLOGIA");
	plist.add(Projections.property("_occupante.flagDisabilitato"), "OCCUPANTE_FLAGDISABILITATO");
	plist.add(Projections.property("_concessionario.id.codice"), "CONCESSIONARIO_ID_CODICE");
	plist.add(Projections.property("_concessionario.tipoanagrafe"), "CONCESSIONARIO_TIPOANAGRAFE");
	plist.add(Projections.property("_concessionario.nominativo"), "CONCESSIONARIO_NOMINATIVO");
	plist.add(Projections.property("_concessionario.nome"), "CONCESSIONARIO_NOME");
	plist.add(Projections.property("_concessionario.codicefiscale"), "CONCESSIONARIO_CODICEFISCALE");
	plist.add(Projections.property("_concessionarioFormaGiuridica.formagiuridica"), "CONCESSIONARIO_FORMAGIURIDICA");
	plist.add(Projections.property("_concessionario.partitaiva"), "CONCESSIONARIO_PARTITAIVA");
	plist.add(Projections.property("_concessionario.tipologia"), "CONCESSIONARIO_TIPOLOGIA");
	plist.add(Projections.property("_concessionario.flagDisabilitato"), "CONCESSIONARIO_FLAGDISABILITATO");
	plist.add(Projections.property("_aut.id.codice"), "AUTORIZZAZIONI_ID_CODICE");
	plist.add(Projections.property("_aut.autoriznumero"), "AUTORIZZAZIONI_AUTORIZNUMERO");
	plist.add(Projections.property("_aut.autorizdata"), "AUTORIZZAZIONI_AUTORIZDATA");
	plist.add(Projections.property("_autAutorizComune.comune"), "AUTORIZZAZIONI_AUTORIZCOMUNE");
	plist.add(Projections.property("_autTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONI_TIPOLOGIAREGISTRO");
	plist.add(Projections.property("_autConcAssente.id.codice"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_ID_CODICE");
	plist.add(Projections.property("_autConcAssente.autoriznumero"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZNUMERO");
	plist.add(Projections.property("_autConcAssente.autorizdata"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZDATA");
	plist.add(Projections.property("_autConcAssenteAutorizComune.comune"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZCOMUNE");
	plist.add(Projections.property("_autConcAssenteTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_TIPOLOGIAREGISTRO");
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MercatipresenzeDDTO.class));
	List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	MercatipresenzeDDTO presenze = new MercatipresenzeDDTO();
	if (!list.isEmpty()) {
	    presenze = (MercatipresenzeDDTO) list.get(0);
	}
	return presenze;
    }

    @Override
    public List<PresenzeDaConsolidareHelper> findPresenzeDaConsolidarePerAnno(Integer codiceMercato, Integer anno) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	StringBuffer sql = new StringBuffer();
	sql.append("select sum(numeropresenze) as numeropresenze, ");
	sql.append("  sum(md.proprietario) as numeropresenzeproprietario, ");
	sql.append("  codiceanagrafe as codiceanagrafe, ");
	sql.append("  md.fk_autorizzazioni_id as codiceautorizzazione ");
	sql.append("from " + schema + ".mercatipresenze_d md ");
	sql.append("inner join " + schema + ".mercatipresenze_t mt ");
	sql.append("on md.idcomune=mt.idcomune ");
	sql.append("and md.fkidtestata=mt.id ");
	sql.append("where mt.idcomune=? ");
	sql.append("and mt.fkcodicemercato=? ");
	sql.append("and mt.anno=? ");
	sql.append("and (not md.fk_autorizzazioni_id is null )");
	sql.append("and (not md.codiceanagrafe is null )");
	sql.append("group by codiceanagrafe, ");
	sql.append("  md.fk_autorizzazioni_id");
	SQLQuery q = session.createSQLQuery(sql.toString());
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceMercato);
	q.setInteger(2, anno);
	q.addScalar("numeropresenze", Hibernate.INTEGER);
	q.addScalar("numeropresenzeproprietario", Hibernate.INTEGER);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
	q.addScalar("codiceautorizzazione", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(PresenzeDaConsolidareHelper.class));
	return (List<PresenzeDaConsolidareHelper>) q.list();
    }

    @Override
    public MercatipresenzeD findUltimaPresenzaPerMercatoAnagrafeAndAutorizzazione(Integer codiceMercato, Integer anno, Integer codiceAnagrafe,
	    Integer codiceAutorizzazione) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("occupante", "_occupante");
	criteria.createAlias("autorizzazioni", "_autorizzazioni");
	criteria.createAlias("mercatiPresenzeT", "_mercatiPresenzeT");
	criteria.createAlias("_mercatiPresenzeT.mercato", "_mercato");
	criteria.add(Restrictions.eq("_mercato.id.codice", codiceMercato));
	criteria.add(Restrictions.eq("_mercatiPresenzeT.anno", anno));
	criteria.add(Restrictions.eq("_occupante.id.codice", codiceAnagrafe));
	criteria.add(Restrictions.eq("_autorizzazioni.id.codice", codiceAutorizzazione));
	criteria.addOrder(Order.desc("_mercatiPresenzeT.dataRegistrazione"));
	List<MercatipresenzeD> list = getHibernateTemplate().findByCriteria(criteria, 0, 3);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public void updateAggiornaAZeroTutteLePresenze(Integer codiceMercato, Integer anno) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	StringBuffer sql = new StringBuffer();
	sql.append("update " + schema + ".mercatipresenze_d md ");
	sql.append("set md.numeropresenze=0, ");
	sql.append("  md.proprietario=0 ");
	sql.append("where md.idcomune=? ");
	sql.append("and fkidtestata in ");
	sql.append("  (select id ");
	sql.append("  from " + schema + ".mercatipresenze_t mt ");
	sql.append("  where mt.idcomune=? ");
	sql.append("  and mt.fkcodicemercato=? ");
	sql.append("  and mt.anno =? ");
	sql.append("  ) ");
	sql.append("and ((not md.fk_autorizzazioni_id is null ) ");
	sql.append("and (not md.codiceanagrafe is null ))");
	SQLQuery q = session.createSQLQuery(sql.toString());
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getIdcomune());
	q.setInteger(2, codiceMercato);
	q.setInteger(3, anno);
	q.executeUpdate();
    }
}
