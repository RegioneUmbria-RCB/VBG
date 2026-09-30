package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimenti;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimentiId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.TemplateMessaggioAppIo;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class AppIoCodaMovimentiDAOImpl extends BaseDAOImpl<AppIoCodaMovimenti, AppIoCodaMovimentiId> implements IAppIoCodaMovimentiDAO {

    @Override
    public Class<AppIoCodaMovimenti> getEntityClass() {

	return AppIoCodaMovimenti.class;
    }

    @Override
    public List<AppIoCodaMovimenti> findByMovimento(Integer codiceMovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceMovimento", codiceMovimento, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	ft.addRestriction(fr);
	List<AppIoCodaMovimenti> lst = findByFilterTable(ft);
	return lst;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TemplateMessaggioAppIo findByIdServizioMovimentoAndIntervento(String identServizio, String tipomovimento, Integer codiceIntervento) {

	//	    templateOggetto
	//	    templateMessaggio
	Dialect d = ((SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory()).getDialect();
	String hibernateDialect = d.toString();
	DialettoEnum dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	//..
	StringBuilder sq = new StringBuilder();
	sq.append("select  "); //
	sq.append(" gerarchia.sc_id as codiceintervento "); //
	//	sq.append(", gerarchia.sc_codice "); //
	//	sq.append(", gerarchia.sc_descrizione "); //
	sq.append("  from alberoproc  "); //
	sq.append(" left join alberoproc gerarchia on  "); //
	sq.append(" gerarchia.idcomune=alberoproc.idcomune and "); //
	sq.append(" gerarchia.software=alberoproc.software and "); //
	if (DialettoEnum.MYSQL.equals(dialetto)) {
	    sq.append(" alberoproc.sc_codice like concat_ws('',gerarchia.sc_codice, '%')  "); //
	} else if (DialettoEnum.ORACLE.equals(dialetto)) {
	    sq.append(" alberoproc.sc_codice like gerarchia.sc_codice || '%'  "); //
	} else {
	    throw new InvalidConfigurationException("findByIdServizioMovimentoAndIntervento# Dialetto Hibernate non implementato " + dialetto);
	}
	sq.append(" where  "); //
	sq.append(" alberoproc.idcomune=:idcomune and  "); //
	sq.append(" alberoproc.sc_id=:codiceintervento "); //
	sq.append(" order by gerarchia.sc_codice desc"); //
	SQLQuery q = getSession().createSQLQuery(sq.toString());
	q.addScalar("codiceintervento", Hibernate.INTEGER);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("codiceintervento", codiceIntervento);
	List<Integer> list = q.list();
	if (list.isEmpty()) {
	    return new TemplateMessaggioAppIo(null, null);
	}
	String sql = "select template_oggetto as templateOggetto, template_messaggio as templateMessaggio from tipimov_appioservizi_int where idcomune=:idcomune and tipomovimento=:tipomovimento and identificativo_servizio=:identificativo_servizio and fk_alberoproc_scid=:alberoproc";
	q = getSession().createSQLQuery(sql);
	q.addScalar("templateOggetto", Hibernate.STRING);
	q.addScalar("templateMessaggio", Hibernate.STRING);
	for (Integer codiceInterventoGerarchico : list) {
	    q.setString("idcomune", ORMHelper.getIdcomune());
	    q.setString("tipomovimento", tipomovimento);
	    q.setString("identificativo_servizio", identServizio);
	    q.setInteger("alberoproc", codiceInterventoGerarchico);
	    q.setResultTransformer(Transformers.aliasToBean(TemplateMessaggioAppIo.class));
	    List<TemplateMessaggioAppIo> ret = q.list();
	    if (!ret.isEmpty()) {
		return ret.get(0);
	    }
	}
	return new TemplateMessaggioAppIo(null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TemplateMessaggioAppIo findByIdServizioMovimentoAndEndo(String identServizio, String tipomovimento, Integer codiceinventario) {

	//	    private String templateOggetto;
	//	    private String templateMessaggio;
	String sql = "select template_oggetto as templateOggetto, template_messaggio as templateMessaggio from tipimov_appioservizi_endo where idcomune=:idcomune and tipomovimento=:tipomovimento and identificativo_servizio=:identificativo_servizio and codiceinventario=:codiceinventario";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("templateOggetto", Hibernate.STRING);
	q.addScalar("templateMessaggio", Hibernate.STRING);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setString("tipomovimento", tipomovimento);
	q.setString("identificativo_servizio", identServizio);
	q.setInteger("codiceinventario", codiceinventario);
	q.setResultTransformer(Transformers.aliasToBean(TemplateMessaggioAppIo.class));
	List<TemplateMessaggioAppIo> ret = q.list();
	if (!ret.isEmpty()) {
	    return ret.get(0);
	}
	return new TemplateMessaggioAppIo(null, null);
    }
}
