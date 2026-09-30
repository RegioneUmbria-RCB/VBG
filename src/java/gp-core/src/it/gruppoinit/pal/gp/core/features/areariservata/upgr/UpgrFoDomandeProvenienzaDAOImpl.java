package it.gruppoinit.pal.gp.core.features.areariservata.upgr;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.FoDomande;

@SuppressWarnings("rawtypes")
@Repository
public class UpgrFoDomandeProvenienzaDAOImpl extends BaseDAOImpl implements IUpgrFoDomandeProvenienzaDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InterventoSoloDomandaOnlineBean> recuperaInterventiSoloDomandaOnline() {

	String sql = "select idcomune as idComune, software, sc_id as id, sc_codice as codice, sc_padre as padre, sc_pubblica as pubblica from alberoproc where sc_pubblica = ?";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setInteger(0, 4); //Solo Domanda on line
	query.addScalar("idComune", Hibernate.STRING);
	query.addScalar("software", Hibernate.STRING);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("codice", Hibernate.STRING);
	query.addScalar("padre", Hibernate.BOOLEAN);
	query.addScalar("pubblica", Hibernate.INTEGER);
	query.setResultTransformer(Transformers.aliasToBean(InterventoSoloDomandaOnlineBean.class));
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InterventoSoloDomandaOnlineBean> recuperaFigli(InterventoSoloDomandaOnlineBean interventoPadre) {

	String scCodice = interventoPadre.getCodice() + "%";
	Integer length = interventoPadre.getCodice().length() + 2;
	String sql = "select idcomune as idComune, software, sc_id as id, sc_codice as codice, sc_padre as padre, sc_pubblica as pubblica from alberoproc where idcomune = ? and software = ? and sc_codice like ? and length(sc_codice) = ?";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, interventoPadre.getIdComune());
	query.setString(1, interventoPadre.getSoftware());
	query.setString(2, scCodice);
	query.setInteger(3, length);
	query.addScalar("idComune", Hibernate.STRING);
	query.addScalar("software", Hibernate.STRING);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("codice", Hibernate.STRING);
	query.addScalar("padre", Hibernate.BOOLEAN);
	query.addScalar("pubblica", Hibernate.INTEGER);
	query.setResultTransformer(Transformers.aliasToBean(InterventoSoloDomandaOnlineBean.class));
	return query.list();
    }

    @Override
    public void aggiornaProvenienza(String provenienza) {

	if (StringUtils.isBlank(provenienza)) {
	    throw new RuntimeException("Impossibile aggiornare il campo fo_domande.provenienza senza passare la provenienza");
	}
	String sql = "update fo_domande set provenienza = ? where provenienza is null";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(FoDomande.class);
	query.setString(0, provenienza);
	query.executeUpdate();
    }

    @Override
    public void aggiornaProvenienza(String provenienza, List<InterventoSoloDomandaOnlineBean> interventi) {

	if (StringUtils.isBlank(provenienza)) {
	    throw new RuntimeException("Impossibile aggiornare il campo fo_domande.provenienza senza passare la provenienza");
	}
	if (interventi == null || interventi.isEmpty()) {
	    return;
	}
	for (InterventoSoloDomandaOnlineBean intervento : interventi) {
	    String sql = "update fo_domande set provenienza = ? where idcomune = ? and codiceintervento = ? and provenienza is null";
	    SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(FoDomande.class);
	    query.setString(0, provenienza);
	    query.setString(1, intervento.getIdComune());
	    query.setInteger(2, intervento.getId());
	    query.executeUpdate();
	}
    }
}
