package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrDismissioneVertAreaRiservataVisura")
public class UpgrDismissioneVertAreaRiservataVisura extends BaseJavaTask {

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	try {
	    String querySQL = getQuery();
	    activityLogInfo("Inizio la dismissione dei parametri di verticalizzazione AREA_RISERVATA eseguendo la query" + querySQL);
	    SQLQuery query = session.createSQLQuery(querySQL);
	    query.addScalar("idcomune", Hibernate.STRING);
	    query.addScalar("parametro", Hibernate.STRING);
	    List list = query.list();
	    Map<String, List<String>> mappaParametriPerEnte = new HashMap<String, List<String>>();
	    for (Object values : list) {
		Object[] vals = (Object[]) values;
		String idcomune = (String) vals[0];
		String parametro = (String) vals[1];
		List<String> listParams = mappaParametriPerEnte.get(idcomune);
		if (listParams == null) {
		    listParams = new ArrayList<String>();
		}
		listParams.add(parametro);
		mappaParametriPerEnte.put(idcomune, listParams);
	    }
	    if (!mappaParametriPerEnte.isEmpty()) {
		Set<Entry<String, List<String>>> entrySet = mappaParametriPerEnte.entrySet();
		for (Entry<String, List<String>> entry : entrySet) {
		    String idComune = entry.getKey();
		    List<String> listParams = entry.getValue();
		    String parametri = "";
		    for (String parametro : listParams) {
			parametri += parametro + ",";
		    }
		    handleErrorCondition(String.format(message(), idComune, parametri));
		}
	    }
	    activityLogInfo("Terminata la dismissione dei parametri di verticalizzazione AREA_RISERVATA");
	} catch (Exception e) {
	    handleErrorCondition(e, e.getMessage());
	}
	return 0;
    }

    private String message() {

	return "L'idcomune %s ha il parametro %s della verticalizzazione AREA_RISERVATA attivo. Il parametro è stato dismesso, informare il consulting team di riferimento per riconfigurare l'accesso alla visura in base alle indicazioni presenti all'indirizzo http://devel3.init.gruppoinit.it/vbg-docs/configurazione/area-riservata/visura/";
    }

    private String getQuery() {

	return "SELECT idcomune,parametro FROM verticalizzazioniparametri WHERE modulo = 'AREA_RISERVATA' AND parametro IN ( 'VIS_FIL_CERCA_AZIENDA','VIS_FIL_CERCA_PARTITAIVA','VIS_FIL_CERCA_RICHIEDENTE','VIS_FIL_CERCA_SOGG_COLL','VIS_FIL_CERCA_TECNICO','VIS_NT_CERCA_AZIENDA','VIS_NT_CERCA_PARTITAIVA','VIS_NT_CERCA_RICHIEDENTE','VIS_NT_CERCA_SOGG_COLL','VIS_NT_CERCA_TECNICO','VIS_T_CERCA_AZIENDA','VIS_T_CERCA_PARTITAIVA','VIS_T_CERCA_RICHIEDENTE','VIS_T_CERCA_SOGG_COLL','VIS_T_CERCA_TECNICO' ) GROUP BY idcomune,parametro order by idcomune,parametro ";
    }
}
