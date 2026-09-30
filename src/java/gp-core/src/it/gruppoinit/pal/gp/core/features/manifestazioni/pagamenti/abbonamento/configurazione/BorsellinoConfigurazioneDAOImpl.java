package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.List;
import java.util.TreeSet;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigComune;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigurazione;
import it.gruppoinit.pal.gp.core.domain.BorsellinoInformative;
import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;

@Repository
public class BorsellinoConfigurazioneDAOImpl extends BaseDAOImpl<BorsellinoConfigurazione, PkId> implements IBorsellinoConfigurazioneDAO {

    @Override
    public Class<BorsellinoConfigurazione> getEntityClass() {

	return BorsellinoConfigurazione.class;
    }

    @Override
    public BorsellinoConfigurazione findConfigurazione() {

	List<BorsellinoConfigurazione> cfg = this.findAll(null, null);
	if (cfg == null || cfg.size() == 0) {
	    return null;
	}
	if (cfg.size() > 1) {
	    throw new InvalidConfigurationException(
		    "Errore: non è possibile avere più configurazioni per la gestione degli abbonamenti. Contattare l'assistenza");
	}
	return cfg.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TreeSet<ButtonComuneModel> findComuni() {

	String sql = "select" + // 
		" comuni.codicecomune as codiceComune," + //
		" comuni.comune," + // 
		" sum(case when borsellino_config_comune.msg_nodopag_nondisp is null then 0 else 1 end) as messaggi," + //
		" sum(case when borsellino_informative.codicecomune is null then 0 else 1 end) as informative," + //
		" sum(case when borsellino_ricariche.codicecomune is null then 0 else 1 end) as ricariche " + //
		"from" + //
		" comuniassociati" + //
		"  inner join comuni on comuniassociati.codicecomune = comuni.codicecomune" + //
		"  left join borsellino_config_comune on comuniassociati.idcomune = borsellino_config_comune.idcomune and comuniassociati.codicecomune = borsellino_config_comune.codicecomune" + //
		"  left join borsellino_informative on comuniassociati.idcomune = borsellino_informative.idcomune and comuniassociati.codicecomune = borsellino_informative.codicecomune" + //
		"  left join borsellino_ricariche on comuniassociati.idcomune = borsellino_ricariche.idcomune and comuniassociati.codicecomune = borsellino_ricariche.codicecomune " + //
		"where" + //
		" comuniassociati.idcomune = ? " + //
		"group by" + //
		" comuni.codicecomune, comuni.comune " + //
		"order by" + //
		" comuni.comune asc";
	SQLQuery query = getSession().createSQLQuery(sql) //
		.addSynchronizedEntityClass(BorsellinoConfigComune.class) //
		.addSynchronizedEntityClass(BorsellinoInformative.class) //
		.addSynchronizedEntityClass(BorsellinoRicariche.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.addScalar("codiceComune", Hibernate.STRING);
	query.addScalar("comune", Hibernate.STRING);
	query.addScalar("messaggi", Hibernate.INTEGER);
	query.addScalar("informative", Hibernate.INTEGER);
	query.addScalar("ricariche", Hibernate.INTEGER);
	query.setResultTransformer(Transformers.aliasToBean(ButtonComuneModel.class));
	TreeSet<ButtonComuneModel> listaOrdinata = new TreeSet<ButtonComuneModel>(new ButtonComuneModelComparator());
	listaOrdinata.addAll(query.list());
	return listaOrdinata;
    }
}
