package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;

public class QueryElencoPosizioniDaSanare extends BaseQueryHelper {

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idComune", Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	return "select " +
		" dett_posizione_debitoria.idcomune as idComune, " +
		" dett_posizione_debitoria.id, " +
		" coalesce(istanze.codicecomune,boll_istanze.codicecomune,mercati.codicecomune,'TUTTI') as codiCecomune, " +
		" coalesce(istanze.software,boll_istanze.software,mercati.software) as software " +
		"from " +
		" dett_posizione_debitoria  " +
		"  left join istoneri_dett_posizioni on" +
		"   dett_posizione_debitoria.idcomune = istoneri_dett_posizioni.idcomune and" +
        	"   dett_posizione_debitoria.id = istoneri_dett_posizioni.fk_dettposdebitoria_id"+
        	"  left join istanzeoneri on"+
        	"   istoneri_dett_posizioni.idcomune = istanzeoneri.idcomune and"+
        	"   istoneri_dett_posizioni.fk_istanzeoneri_id = istanzeoneri.id"+
		"  left join istanze on  " +
		"    istanzeoneri.idcomune = istanze.idcomune and  " +
		"    istanzeoneri.codiceistanza = istanze.codiceistanza " +
		"  left join boll_gest_dettaglio on  " +
		"    dett_posizione_debitoria.idcomune = boll_gest_dettaglio.idcomune and  " +
		"    dett_posizione_debitoria.id = boll_gest_dettaglio.fk_posdebdettaglio_id " +
		"  left join boll_gest_istanzeoneri on  " +
		"    boll_gest_dettaglio.idcomune = boll_gest_istanzeoneri.idcomune and  " +
		"    boll_gest_dettaglio.id = boll_gest_istanzeoneri.fk_bollgestdet_id " +
		"  left join istanzeoneri boll_istanzeoneri on  " +
		"    boll_gest_istanzeoneri.idcomune = boll_istanzeoneri.idcomune and  " +
		"    boll_gest_istanzeoneri.fk_codiceistanzeoneri = boll_istanzeoneri.id " +
		"  left join istanze boll_istanze on  " +
		"    boll_istanzeoneri.idcomune = boll_istanze.idcomune and  " +
		"    boll_istanzeoneri.codiceistanza = boll_istanze.codiceistanza     " +
		"  left join boll_gest_dett_autorizz on  " +
		"    boll_gest_dettaglio.idcomune = boll_gest_dett_autorizz.idcomune and  " +
		"    boll_gest_dettaglio.id = boll_gest_dett_autorizz.fk_bollgestdet_id " +
		"  left join boll_gest_mercati_dett on " +
		"    boll_gest_dett_autorizz.idcomune = boll_gest_mercati_dett.idcomune and  " +
		"    boll_gest_dett_autorizz.id = boll_gest_mercati_dett.fk_id_gest_autorizzazioni " +
		"  left join mercati_d on " +
		"    boll_gest_mercati_dett.idcomune = mercati_d.idcomune and  " +
		"    boll_gest_mercati_dett.fk_idposteggio = mercati_d.idposteggio " +
		"  left join mercati on " +
		"    mercati_d.idcomune = mercati.idcomune and  " +
		"    mercati_d.fkcodicemercato = mercati.codicemercato " +
		"where " +
		" dett_posizione_debitoria.cf_ente_creditore is null";
    }
}
