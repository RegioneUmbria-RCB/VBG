package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AnagrafeVerificheMailDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AnagrafeVerificheMail;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.anagrafe.model.CodiceVerificaMailAnagrafeBean;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Repository
public class AnagrafeVerificheMailDAOImpl extends BaseDAOImpl<AnagrafeVerificheMail, PkId> implements AnagrafeVerificheMailDAO {

    private static final String ID = "id";
    private static final String CODICE_VERIFICA = "codice_verifica";
    private static final String DATA_SCADENZA = "data_scadenza";
    private static final String FK_CODICEANAGRAFE = "fk_codiceanagrafe";
    private static final String IDCOMUNE = "idcomune";

    @Override
    public Class<AnagrafeVerificheMail> getEntityClass() {

	return AnagrafeVerificheMail.class;
    }

    @Override
    public void deleteByAnagrafe(Integer codiceAnagrafe) {

	String sql = "delete from anagrafe_verifiche_mail where idcomune=:idcomune and fk_codiceanagrafe=:fk_codiceanagrafe";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Anagrafe.class)
		.addSynchronizedEntityClass(AnagrafeVerificheMail.class);
	q.setString(IDCOMUNE, ORMHelper.getIdcomune());
	q.setInteger(FK_CODICEANAGRAFE, codiceAnagrafe);
	q.executeUpdate();
	flush();
    }

    @Override
    public boolean isProceduraVerificaInCorso(Integer codiceAnagrafe) {

	String sql = "select coalesce(count(*),0) as conta from anagrafe_verifiche_mail where idcomune = :idcomune and fk_codiceanagrafe=:fk_codiceanagrafe and data_scadenza >= :data_scadenza and data_verifica is null";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(IDCOMUNE, ORMHelper.getIdcomune());
	query.setInteger(FK_CODICEANAGRAFE, codiceAnagrafe);
	query.setDate(DATA_SCADENZA, Calendar.getInstance().getTime());
	query.addScalar("conta", Hibernate.INTEGER);
	return (Integer) query.list().get(0) > 0;
    }

    @Override
    public void eliminaVerificheInCorso(Integer codiceAnagrafe) {

	String sql = "update anagrafe_verifiche_mail set data_scadenza=:data_scadenza where idcomune=:idcomune and fk_codiceanagrafe=:fk_codiceanagrafe and data_verifica is null";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Anagrafe.class)
		.addSynchronizedEntityClass(AnagrafeVerificheMail.class);
	q.setDate(DATA_SCADENZA, Utilities.addDays(Calendar.getInstance().getTime(), -36500)); // aggiorno la data scadenza a -100 anni (-36500 giorni)
											       // in modo tale che so recuperare quando scadeva esattamente
	q.setString(IDCOMUNE, ORMHelper.getIdcomune());
	q.setInteger(FK_CODICEANAGRAFE, codiceAnagrafe);
	q.executeUpdate();
	flush();
    }

    @Override
    public void generaCodiceVerificaMail(CodiceVerificaMailAnagrafeBean cvmb) {

	AnagrafeVerificheMail entity = new AnagrafeVerificheMail();
	Anagrafe a = new Anagrafe();
	a.setId(new PkId(cvmb.getCodiceAnagrafe()));
	entity.setAnagrafe(a);
	entity.setCodiceVerifica(cvmb.getCodiceVerifica());
	entity.setDataScadenza(cvmb.getDataScadenza());
	entity.setNuovaEmail(cvmb.getNuovaMail());
	insert(entity);
    }

    @Override
    public String updateVerificaCambioMail(Integer codiceAnagrafe, String codiceVerifica) {

	String sql = "select id from anagrafe_verifiche_mail where idcomune=:idcomune " + //
		     " and fk_codiceanagrafe=:fk_codiceanagrafe " + //
		     " and codice_verifica=:codice_verifica" + //
		     " and data_scadenza >= :data_scadenza " + //		     
		     " and data_verifica is null ";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(IDCOMUNE, ORMHelper.getIdcomune());
	query.setInteger(FK_CODICEANAGRAFE, codiceAnagrafe);
	query.setString(CODICE_VERIFICA, codiceVerifica);
	query.setDate(DATA_SCADENZA, Calendar.getInstance().getTime());
	query.addScalar(ID, Hibernate.INTEGER);
	List<Integer> list = query.list();
	if (list.size() == 1) {
	    //	recupero la riga da aggiornare
	    //	se non scaduta
	    //	aggiorno data verifica e torno true
	    Integer id = list.get(0);
	    if (id != null) {
		AnagrafeVerificheMail entity = findById(new PkId(id));
		entity.setDataverifica(Calendar.getInstance().getTime());
		update(entity);
		return entity.getNuovaEmail();
	    }
	}
	return null;
    }

    @Override
    public String recuperaUltimoCodiceVerificaPerAnagrafe(Integer codiceAnagrafe) {

	String sql = "select codice_verifica from anagrafe_verifiche_mail where idcomune=:idcomune " + //
		     " and fk_codiceanagrafe=:fk_codiceanagrafe " + //		     	     
		     " and data_verifica is null " + //
		     " order by data_scadenza desc";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(IDCOMUNE, ORMHelper.getIdcomune());
	query.setInteger(FK_CODICEANAGRAFE, codiceAnagrafe);
	query.addScalar(CODICE_VERIFICA, Hibernate.STRING);
	List<String> list = query.addSynchronizedEntityClass(Anagrafe.class).addSynchronizedEntityClass(AnagrafeVerificheMail.class).list();
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }
}
