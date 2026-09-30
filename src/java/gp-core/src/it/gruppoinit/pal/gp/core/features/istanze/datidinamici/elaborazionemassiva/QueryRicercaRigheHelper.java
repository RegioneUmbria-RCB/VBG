package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.StatiistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.SchedaDinamicaModel;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

public class QueryRicercaRigheHelper extends BaseQueryHelper {

    private AlberoprocDAO alberoprocDAO;
    private CreaTestataRequest request;
    private StatiistanzaDAO statiistanzaDAO;

    public QueryRicercaRigheHelper(AlberoprocDAO alberoprocDAO, CreaTestataRequest request, StatiistanzaDAO statiistanzaDAO) {

	this.alberoprocDAO = alberoprocDAO;
	this.request = request;
	this.statiistanzaDAO = statiistanzaDAO;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	q.setString(0, ORMHelper.getIdcomune()); // IDCOMUNE
	q.setString(1, ORMHelper.getSoftware()); // software
	for (ParameterHelper parameter : parameters) {
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("codiceistanza", Hibernate.INTEGER);
    }

    @Override
    public String buildQuery() {

	String sql = "select istanze.codiceistanza from istanze where istanze.idcomune=? and istanze.software=? ";
	int position = 2;
	// GESTIONE INTERVENTI
	List<Alberoproc> listaInterventi = getListaInterventi(request.getInterventi());
	if (!listaInterventi.isEmpty()) {
	    
		sql += " and istanze.codiceistanza in (select codiceistanza from istanze ist2 inner join " +
			       "alberoproc ap2 on ist2.idcomune = ap2.idcomune and ist2.codiceinterventoproc = ap2.sc_id " +
			       "where ist2.idcomune = istanze.idcomune " +
			       "and ist2.codiceistanza = istanze.codiceistanza and ap2.software = ist2.software and ( ";
		
		 for (Alberoproc intervento : listaInterventi) {
		     sql += " ap2.sc_codice like ? or";
		     parameters.add(new ParameterHelper(position, intervento.getScCodice().trim() + "%", new StringType()));
		     position++;
		 }
		 
		 sql = sql.substring(0, sql.length()-3);
		 sql += ")) ";

	}
	// GESTIONE STATI ISTANZA
	String stato = request.getStatoIstanza();
	if (stato.equalsIgnoreCase("stato_chiuse") || stato.equalsIgnoreCase("stato_aperte") || stato.equalsIgnoreCase("stato_chiuse_negativamente")
		|| stato.equalsIgnoreCase("stato_chiuse_positivamente") || stato.equalsIgnoreCase("stato_tutte")) {
	    List<Statiistanza> statistStatiistanzas = new ArrayList<Statiistanza>();
	    if (stato.equalsIgnoreCase("stato_chiuse")) {
		statistStatiistanzas = statiistanzaDAO.findByStatocomportamentoChiuse();
	    } else if (stato.equalsIgnoreCase("stato_chiuse_negativamente")) {
		statistStatiistanzas = statiistanzaDAO.findByStatocomportamentoChiuseNegativamente();
	    } else if (stato.equalsIgnoreCase("stato_chiuse_positivamente")) {
		statistStatiistanzas = statiistanzaDAO.findByStatocomportamentoChiusePositivamente();
	    } else if (stato.equalsIgnoreCase("stato_aperte")) {
		statistStatiistanzas = statiistanzaDAO.findByStatocomportamentoAperte();
	    } else {
		// tutti
	    }
	    if (!statistStatiistanzas.isEmpty()) {
		String[] stati = new String[statistStatiistanzas.size()];
		String qm = StringUtils.repeat("?,", stati.length);
		qm = qm.substring(0, qm.length() - 1);
		sql += " and istanze.chiusura in (" + qm + ")";
		for (Statiistanza statiistanza : statistStatiistanzas) {
		    parameters.add(new ParameterHelper(position, statiistanza.getId().getCodicestato(), new StringType()));
		    position++;
		}
	    }
	} else {
	    sql += " and istanze.chiusura=?";
	    parameters.add(new ParameterHelper(position, stato.trim(), new StringType()));
	    position++;
	}
	// REGISTRI DELLE AUTORIZZAZIONI
	if (!(request.getRegistri() == null || request.getRegistri().isEmpty())) {
	    sql += " and exists (select 1 from autorizzazioni aut " +
		   " where aut.idcomune=istanze.idcomune and aut.fkidistanza=istanze.codiceistanza ";
	    String[] registri = new String[request.getRegistri().size()];
	    String qm = StringUtils.repeat("?,", registri.length);
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " and aut.fkidregistro in (" + qm + ")";
	    for (Integer reg : request.getRegistri()) {
		parameters.add(new ParameterHelper(position, reg, new IntegerType()));
		position++;
	    }
	    sql += ") ";
	}
	// schede
	if (!(request.getSchedeDaElaborare() == null || request.getSchedeDaElaborare().isEmpty())) {
	    sql += " and exists (select 1 from istanzedyn2modellit d2mt " +
		   " where d2mt.idcomune=istanze.idcomune and d2mt.codiceistanza=istanze.codiceistanza ";
	    String[] schede = new String[request.getSchedeDaElaborare().size()];
	    String qm = StringUtils.repeat("?,", schede.length);
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " and d2mt.fk_d2mt_id in (" + qm + ")";
	    for (SchedaDinamicaModel reg : request.getSchedeDaElaborare()) {
		parameters.add(new ParameterHelper(position, reg.getIdScheda(), new IntegerType()));
		position++;
	    }
	    sql += ") ";
	}
	return sql;
    }

    private String getStati(String sql, CreaTestataRequest request2, int position) {

	// TODO Auto-generated method stub
	return null;
    }

    private List<Alberoproc> getListaInterventi(Set<Integer> codiciIntervento) {

	if (!(codiciIntervento == null || codiciIntervento.isEmpty())) {
	    FilterTable ftable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction filterRestriction = new FilterRestriction();
	    Integer[] inte = new Integer[codiciIntervento.size()];
	    inte = codiciIntervento.toArray(inte);
	    filterRestriction.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
	    filterRestriction.addFilterField(FilterUtils.in("id.codice", inte, Integer.class));
	    ftable.addRestriction(filterRestriction);
	    return alberoprocDAO.findByFilterTable(ftable, null, null);
	}
	return new ArrayList<Alberoproc>();
    }
}
