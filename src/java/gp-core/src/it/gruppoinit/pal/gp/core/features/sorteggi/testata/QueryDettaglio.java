package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryDettaglio extends BaseQueryHelper {

    private Integer idTestata;

    public QueryDettaglio(SessionFactoryImplementor sessimpl, Integer idTestata) {

	if (sessimpl != null) {
	    Dialect dialetto = sessimpl.getDialect();
	    String hibernateDialect = dialetto.toString();
	    this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	    this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	}
	this.idTestata = idTestata;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    // log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("codiceIstanza", Hibernate.INTEGER);
	q.addScalar("numeroIstanza", Hibernate.STRING);
	q.addScalar("dataIstanza", Hibernate.DATE);
	q.addScalar("numeroProtocollo", Hibernate.STRING);
	q.addScalar("dataProtocollo", Hibernate.DATE);
	q.addScalar("intervento", Hibernate.STRING);
	q.addScalar("archivio", Hibernate.STRING);
	q.addScalar("lavori", Hibernate.STRING);
	q.addScalar("operatore", Hibernate.STRING);
	q.addScalar("responsabileProcedimento", Hibernate.STRING);
	q.addScalar("responsabileIstruttoria", Hibernate.STRING);
	q.addScalar("sorteggiata", Hibernate.BOOLEAN);
	q.addScalar("richiedenteNome", Hibernate.STRING);
	q.addScalar("richiedenteNominativo", Hibernate.STRING);
	q.addScalar("titolareLegaleNominativo", Hibernate.STRING);
	q.addScalar("flagSpecificaDescrizione", Hibernate.BOOLEAN);
	q.addScalar("descrSoggetto", Hibernate.STRING);
	q.addScalar("tipoSoggetto", Hibernate.STRING);
	q.addScalar("comune", Hibernate.STRING);
	q.addScalar("localizzazionePrefisso", Hibernate.STRING);
	q.addScalar("localizzazioneDescrizione", Hibernate.STRING);
	q.addScalar("localizzazioneCAP", Hibernate.STRING);
	q.addScalar("localizzazioneLocfraz", Hibernate.STRING);
	q.addScalar("localizzazioneCodViario", Hibernate.STRING);
	q.addScalar("localizzazioneCivico", Hibernate.STRING);
	q.addScalar("localizzazioneColore", Hibernate.STRING);
	q.addScalar("localizzazionePrimaria", Hibernate.BOOLEAN);
	q.addScalar("localizzazioneEsponente", Hibernate.STRING);
	q.addScalar("flagInterventoObbligatorio", Hibernate.BOOLEAN);	
    }

    @Override
    public String buildQuery() {

	String sql = "select" + //
		" istanze.codiceistanza as codiceIstanza, istanze.numeroistanza as numeroIstanza," + //
		" istanze.data as dataIstanza,istanze.numeroprotocollo as numeroProtocollo, istanze.dataprotocollo as dataProtocollo," + //
		" alberoproc.descrizione_completa as intervento, tipiarchivioistanze.archivio, istanze.lavori," + //
		" operatore.responsabile as operatore, resp_proc.responsabile as responsabileProcedimento," + //
		" resp_istr.responsabile as responsabileIstruttoria, sorteggidettaglio.sorteggiata, richiedente.nome as richiedenteNome," + //
		" richiedente.nominativo as richiedenteNominativo, azienda.nominativo as titolareLegaleNominativo," + //
		" tipisoggetto.flg_specificadescrizione as flagSpecificaDescrizione, istanze.descrsoggetto as descrSoggetto," + //
		" tipisoggetto.tiposoggetto as tipoSoggetto, comuni.comune, stradario.prefisso as localizzazionePrefisso," + //
		" stradario.descrizione as localizzazioneDescrizione, istanzestradario.cap as localizzazioneCAP," + //
		" stradario.locfraz as localizzazioneLocfraz, stradario.codviario as localizzazioneCodViario," + //
		" istanzestradario.civico as localizzazioneCivico, istanzestradario.colore as localizzazioneColore," + //
		" istanzestradario.primario as localizzazionePrimaria, istanzestradario.esponente as localizzazioneEsponente, sorteggidettaglio.flag_intervento_obbligatorio as flagInterventoObbligatorio " + //
		"from" + //
		" sorteggitestata" + //
		"   inner join sorteggidettaglio on " + //
		"     sorteggitestata.idcomune = sorteggidettaglio.idcomune and " + //
		"	 sorteggitestata.st_id = sorteggidettaglio.sd_fk_stid" + //
		"   inner join istanze on " + //
		"     sorteggidettaglio.idcomune = istanze.idcomune and " + //
		"	 sorteggidettaglio.codiceistanza = istanze.codiceistanza" + //
		"   inner join alberoproc on " + //
		"     istanze.idcomune = alberoproc.idcomune and " + //
		"	 istanze.codiceinterventoproc = alberoproc.sc_id " + //
		"   left  join tipiarchivioistanze on " + //
		"     istanze.idcomune = tipiarchivioistanze.idcomune and " + //
		"	 istanze.codicearchivio = tipiarchivioistanze.codicearchivio" + //
		"   inner join responsabili operatore on " + //
		"     istanze.idcomune = operatore.idcomune and " + //
		"	 istanze.codiceresponsabile = operatore.codiceresponsabile" + //
		"   left  join responsabili resp_proc on " + //
		"     istanze.idcomune = resp_proc.idcomune and " + //
		"	 istanze.codiceresponsabileproc = resp_proc.codiceresponsabile" + //
		"   left  join responsabili resp_istr on " + //
		"     istanze.idcomune = resp_istr.idcomune and " + //
		"	 istanze.codiceistruttore = resp_istr.codiceresponsabile" + //
		"   inner join anagrafe richiedente on " + //
		"     istanze.idcomune = richiedente.idcomune and " + //
		"	 istanze.codicerichiedente = richiedente.codiceanagrafe" + //
		"   left  join anagrafe azienda on " + //
		"     istanze.idcomune = azienda.idcomune and " + //
		"	 istanze.codicetitolarelegale = azienda.codiceanagrafe" + //
		"   left  join tipisoggetto on " + //
		"     istanze.idcomune = tipisoggetto.idcomune and " + //
		"	 istanze.fkcodicesoggetto = tipisoggetto.codicetiposoggetto" + //
		"   inner join comuni on " + //
		"     istanze.codicecomune = comuni.codicecomune" + //
		"   left  join istanzestradario on " + //
		"     istanze.idcomune = istanzestradario.idcomune and " + //
		"	 istanze.codiceistanza = istanzestradario.codiceistanza and " + //
		"	 istanzestradario.primario = 1" + //
		"   left  join stradario on " + //
		"     istanzestradario.idcomune = stradario.idcomune and " + //
		"	 istanzestradario.codicestradario = stradario.codicestradario " + //
		"where" + //
		" sorteggitestata.idcomune = ? and" + //
		" sorteggitestata.st_id = ?";
	int idx = 0;
	parameters.add(new ParameterHelper(idx++, ORMHelper.getIdcomune(), new StringType()));
	parameters.add(new ParameterHelper(idx++, this.idTestata, new IntegerType()));
	return sql;
    }
}
