package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.upgr;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;

@SuppressWarnings("rawtypes")
@Repository
public class UpgrIstanzeCollegateDAOImpl extends BaseDAOImpl implements UpgrIstanzeCollegateDAO {

    @Override
    public Class getEntityClass() {

	return Istanzecollegate.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CollegamentiDuplicatiBean> findCollegamentiDuplicati() {

	Session session = getSession();
	String sql = "select " + //
		"  idcomune as idComune, progressivo, codiceistanza as codiceIstanza, " + //
		"  codiceistanzacollegata as codiceIstanzaCollegata, max(id) as id, count(*) as conteggio " + //
		" from " + //
		"  istanzecollegate " + //
		" group by " + //
		"  idcomune, progressivo, codiceistanza, codiceistanzacollegata " + //
		" having " + //
		"  count(*) > 1 " + //
		" order by " + //
		"  idcomune, progressivo ";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(this.getEntityClass());
	q.addScalar("idComune", Hibernate.STRING);
	q.addScalar("progressivo", Hibernate.INTEGER);
	q.addScalar("codiceIstanza", Hibernate.INTEGER);
	q.addScalar("codiceIstanzaCollegata", Hibernate.INTEGER);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("conteggio", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(CollegamentiDuplicatiBean.class));
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProgressivoOrdineDuplicatoBean> findProgressivoOrdineDuplicati() {

	Session session = getSession();
	String sql = "select " + //
		"  idcomune as idComune, progressivo, ordine, count(*) as conteggio " + //
		" from " + //
		"  istanzecollegate " + //
		" group by " + //
		"  idcomune, progressivo, ordine " + //
		" having " + //
		"  count(*) > 1 " + //
		" order by " + //
		"  idcomune, progressivo, ordine ";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(this.getEntityClass());
	q.addScalar("idComune", Hibernate.STRING);
	q.addScalar("progressivo", Hibernate.INTEGER);
	q.addScalar("ordine", Hibernate.INTEGER);
	q.addScalar("conteggio", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(ProgressivoOrdineDuplicatoBean.class));
	return q.list();
    }

    @Override
    public void deleteByPk(String idComune, int id) {

	String sql = "delete from istanzecollegate where idcomune=:idcomune and id=:id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzecollegate.class);
	q.setString("idcomune", idComune);
	q.setInteger("id", id);
	q.executeUpdate();
    }

    @Override
    public void deleteDoppioni(String idComune, int progressivo, int codiceIstanza, Integer codiceIstanzaCollegata, int idDaEscludere) {

	String sql = "delete " + //
		"from " + //
		" istanzecollegate " + //
		"where " + //
		" idcomune=:idcomune and " + //
		" progressivo=:progressivo and " + //
		" codiceistanza=:codiceistanza and ";
	sql += (codiceIstanzaCollegata == null) ? "codiceistanzacollegata is null and " : "codiceistanzacollegata=:codiceistanzacollegata and ";
	sql += "id!=:iddaescludere";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzecollegate.class);
	q.setString("idcomune", idComune);
	q.setInteger("progressivo", progressivo);
	q.setInteger("codiceistanza", codiceIstanza);
	if (codiceIstanzaCollegata != null) {
	    q.setInteger("codiceistanzacollegata", codiceIstanzaCollegata);
	}
	q.setInteger("iddaescludere", idDaEscludere);
	q.executeUpdate();
    }

    @Override
    public void deleteCollegamentiStessaIstanza() {

	String sql = "delete " + //
		"from " + //
		" istanzecollegate " + //
		"where " + //
		" codiceistanza = codiceistanzacollegata";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzecollegate.class);
	q.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeCollegateBean> findByIdcomuneEProgressivo(String idComune, int progressivo) {

	Session session = getSession();
	String sql = "select " + //
		"  istanzecollegate.idcomune as idComune, istanzecollegate.id " + //
		" from " + //
		"  istanzecollegate " + //
		"  inner join istanze on " + //
		"    istanzecollegate.idcomune = istanze.idcomune and " + //
		"    istanzecollegate.codiceistanza = istanze.codiceistanza " + //
		" where " + //
		"  istanzecollegate.idcomune=:idcomune and " + //
		"  istanzecollegate.progressivo=:progressivo " + //
		" order by " + //
		"  istanzecollegate.ordine asc, " + //
		"  coalesce(istanze.datavalidita,istanze.data) asc, " + //
		"  istanze.codiceistanza asc";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(this.getEntityClass());
	//
	q.setString("idcomune", idComune);
	q.setInteger("progressivo", progressivo);
	//
	q.addScalar("idComune", Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeCollegateBean.class));
	return q.list();
    }

    @Override
    public void updateOrdine(String idComune, int id, int ordine) {

	String sql = "update istanzecollegate set ordine=:ordine where idcomune=:idcomune and id=:id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzecollegate.class);
	q.setInteger("ordine", ordine);
	q.setString("idcomune", idComune);
	q.setInteger("id", id);
	q.executeUpdate();
    }
}
