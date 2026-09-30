package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryBollettazioneMercatiPresenzeAssenzeHelper extends AbstractQueryBollettazioneMercatiHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryBollettazioneIstanzeHelper.class);
    private List<Integer> filtriMercati;
    private IntervalloDate intervalloDate;
    private String guid;

    public QueryBollettazioneMercatiPresenzeAssenzeHelper(String guid, SessionFactoryImplementor sessimpl, List<Integer> filtriMercati,
	    IntervalloDate intervalloDate) {

	super();
	this.guid = guid;
	this.filtriMercati = filtriMercati;
	this.intervalloDate = intervalloDate;
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryBollettazioneMercatiHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryBollettazioneMercatiHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryBollettazioneMercatiHelper: schemaName={}", schemaName);
    }

    @Override
    public String buildQuery() {

	int position = 0;
	String qm = StringUtils.repeat("?,", filtriMercati.size());
	qm = qm.substring(0, qm.length() - 1);
	String sql = "select" + //
		" mercatipresenze_t.idcomune AS idComune, " + //
		" ? as guid, " + //
		" 'PRESENZE' as provenienza," + //
		" 0 as subentro," + //
		" mercatipresenze_t.id as idGiornata," + //
		" mercatipresenze_t.dataregistrazione as dataGiornata," + //
		" autorizzazioni_concessioni.fk_idposteggio as idPosteggio," + //
		" mercatipresenze_d.codiceconcessionario as idAnagrafe," + //
		" autorizzazioni.id as idRiferimento," + //
		" autorizzazioni_concessioni.id as idAutorizzazioneConcessione, " + //
		" 0 as assenzaGiustificata, " + //
		" 1 as concpresente, " + //
		" coalesce(mercatipresenze_d.spuntista,0) as spuntpresente, " + //
		" mercatipresenze_d.fk_codiceistat as catmerc, " + //
		" mercatipresenze_t.fkidmercatiuso as idUso, " +
		" NULL as idAutorizzazioneSubentri " +
		"from" + //
		"  mercatipresenze_t" + //
		"    inner join mercatipresenze_d on mercatipresenze_d.idcomune = mercatipresenze_t.idcomune and mercatipresenze_d.fkidtestata = mercatipresenze_t.id and mercatipresenze_d.spuntista = ?" + //
		"    inner join autorizzazioni on autorizzazioni.idcomune = mercatipresenze_d.idcomune and autorizzazioni.id = mercatipresenze_d.fk_autorizzazioni_id " + //
		"    inner join autorizzazioni_concessioni on " + //
		" autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune and " + //
		" autorizzazioni_concessioni.fk_idaut_attuale = autorizzazioni.id and" + //
		" autorizzazioni_concessioni.fk_codicemercato = mercatipresenze_t.fkcodicemercato and" + //
		" autorizzazioni_concessioni.fk_idmercatiuso = mercatipresenze_t.fkidmercatiuso and" + //
		" autorizzazioni_concessioni.fk_idposteggio = mercatipresenze_d.fkidposteggio " + //
		"where" + //
		" mercatipresenze_t.idcomune = ?  and" + //
		" mercatipresenze_t.fkcodicemercato IN (" + //
		qm +
		") and" + //
		" mercatipresenze_t.dataregistrazione >= ? and" + //
		" mercatipresenze_t.dataregistrazione <= ? and" + //
		" autorizzazioni.autorizdata <= mercatipresenze_t.dataregistrazione and " + //
		" (autorizzazioni.datascadenza is null or autorizzazioni.datascadenza > mercatipresenze_t.dataregistrazione)" + //
		" union " + //
		"select" + //
		" mercatipresenze_t.idcomune AS idComune," + //
		" ? as guid," + //
		" 'PRESENZE' as provenienza," + //
		" 1 as subentro," + //
		" mercatipresenze_t.id as idgiornata," + //
		" mercatipresenze_t.dataregistrazione as datagiornata," + //
		" autorizzazioni_subentri_conc.fk_idposteggio as idposteggio," + //
		" mercatipresenze_d.codiceconcessionario as idanagrafe," + //
		" autorizzazioni_subentri.fk_idaut_attuale as idriferimento," + //
		" autorizzazioni_subentri_conc.id as idautorizzazioneconcessione, " + //
		" 0 as assenzagiustificata," + //
		" 1 as concpresente, " + //
		" coalesce(mercatipresenze_d.spuntista, 0) as spuntpresente, " + //
		" mercatipresenze_d.fk_codiceistat as catmerc, " + //
		" mercatipresenze_t.fkidmercatiuso as idUso, " +
		" autorizzazioni_subentri.id as idAutorizzazioneSubentri " +
		"from" + //
		" mercatipresenze_t" + //
		" inner join mercatipresenze_d on mercatipresenze_d.idcomune = mercatipresenze_t.idcomune" + //
		"   and mercatipresenze_d.fkidtestata = mercatipresenze_t.id" + //
		"   and mercatipresenze_d.spuntista = ?" + //
		" inner join autorizzazioni_subentri on autorizzazioni_subentri.idcomune = mercatipresenze_d.idcomune" + //
		"   and autorizzazioni_subentri.fk_idaut_attuale = mercatipresenze_d.fk_autorizzazioni_id" + //
		" inner join autorizzazioni_subentri_conc on autorizzazioni_subentri_conc.idcomune = autorizzazioni_subentri.idcomune" + //
		"   and autorizzazioni_subentri_conc.fk_autsub_id = autorizzazioni_subentri.id" + //
		"   and autorizzazioni_subentri_conc.fk_codicemercato = mercatipresenze_t.fkcodicemercato" + //
		"   and autorizzazioni_subentri_conc.fk_idmercatiuso = mercatipresenze_t.fkidmercatiuso" + //
		"   and autorizzazioni_subentri_conc.fk_idposteggio = mercatipresenze_d.fkidposteggio " + //
		"where" + //
		" mercatipresenze_t.idcomune = ?" + //
		" and   mercatipresenze_t.fkcodicemercato in ( " + //
		qm +
		" )" + //
		" and   mercatipresenze_t.dataregistrazione >= ?" + //
		" and   mercatipresenze_t.dataregistrazione <= ?" + //
		" and   autorizzazioni_subentri.autorizdata <= mercatipresenze_t.dataregistrazione" + //
		" and   autorizzazioni_subentri.data_cessazione > mercatipresenze_t.dataregistrazione" + //
		" and   (" + //
		"     autorizzazioni_subentri.datascadenza is null" + //
		"     or    autorizzazioni_subentri.datascadenza > mercatipresenze_t.dataregistrazione" + //
		" ) " + //
		" union " + //
		"select" + //
		" mercatipresenze_t.idcomune AS idComune," + //
		" ? as guid," + //
		" 'ASSENZE' as provenienza," + //
		" 0 as subentro," + //
		" mercatipresenze_t.id as idGiornata," + //
		" mercatipresenze_t.dataregistrazione as dataGiornata," + //
		" autorizzazioni_concessioni.fk_idposteggio as idPosteggio," + //
		" mercatipresenze_d.codiceconcessionario as idAnagrafe," + //
		" autorizzazioni.id as idRiferimento," + //
		" autorizzazioni_concessioni.id as idAutorizzazioneConcessione," + //
		" coalesce(mercatipresenze_d.flag_assenza_giust,0) as assenzaGiustificata ," + //
		" 0 as concpresente, " + //
		" coalesce(mercatipresenze_d.spuntista, 0) as spuntpresente, " + //
		" mercatipresenze_d.fk_codiceistat as catmerc, " + //
		" mercatipresenze_t.fkidmercatiuso as idUso, " +
		" NULL as idAutorizzazioneSubentri " +
		" from" + //
		"  mercatipresenze_t" + //
		"    inner join mercatipresenze_d on " + //
		" mercatipresenze_d.idcomune = mercatipresenze_t.idcomune and " + //
		" mercatipresenze_d.fkidtestata = mercatipresenze_t.id " + //
		" and coalesce(mercatipresenze_t.flg_gg_nulla, 0) = ?" + //
		"    inner join autorizzazioni_concessioni on " + //
		" autorizzazioni_concessioni.idcomune = mercatipresenze_t.idcomune and " + //
		" autorizzazioni_concessioni.fk_codicemercato = mercatipresenze_t.fkcodicemercato and" + //
		" autorizzazioni_concessioni.fk_idmercatiuso = mercatipresenze_t.fkidmercatiuso and" + //
		" autorizzazioni_concessioni.fk_idposteggio = mercatipresenze_d.fkidposteggio" + //
		"    inner join autorizzazioni on " + //
		" autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune and " + //
		" autorizzazioni_concessioni.fk_idaut_attuale = autorizzazioni.id and" + //
		" autorizzazioni.autorizdata <= mercatipresenze_t.dataregistrazione " + //
		" and ( " + //
		"   ( " + //
		// -- la data cessazione è impostata e  maggiore uguale della data della giornata " + //
		"   autorizzazioni.data_cessazione > mercatipresenze_t.dataregistrazione ) " + //
		"   or" + //
		"   ( " + //
		//  -- la data della cessazione è nulla e la data di scadenza è impostata e maggiore uguale alla giornata 
		"  autorizzazioni.data_cessazione is null and autorizzazioni.datascadenza > mercatipresenze_t.dataregistrazione " + //
		"   )" + //
		" or" + //
		"   ( " + //
		//  -- non c'è data cessazione e non c'è data scadenza
		"  autorizzazioni.data_cessazione is null and autorizzazioni.datascadenza is null" + //
		"   )" + //
		"  ) " + //
		" WHERE" + //
		" mercatipresenze_t.idcomune = ?  and" + //
		" mercatipresenze_t.fkcodicemercato in (" + //
		qm +
		") and" + //
		" mercatipresenze_t.dataregistrazione >= ? and" + //
		" mercatipresenze_t.dataregistrazione <= ? and" + //
		" (mercatipresenze_d.fk_autorizzazioni_id is null or mercatipresenze_d.fk_autorizzazioni_id <> autorizzazioni.id)" + //
		" union " + //
		"select " + //
		" mercatipresenze_t.idcomune AS idComune," + //
		" ? as guid," + //
		" 'ASSENZE' as provenienza, " + //
		" 1 as subentro, " + //
		" mercatipresenze_t.id as idgiornata, " + //
		" mercatipresenze_t.dataregistrazione as datagiornata, " + //
		" autorizzazioni_subentri_conc.fk_idposteggio as idposteggio, " + //
		" mercatipresenze_d.codiceconcessionario as idanagrafe, " + //
		" autorizzazioni_subentri.fk_idaut_attuale  as idriferimento, " + //
		" autorizzazioni_subentri_conc.id as idautorizzazioneconcessione, " + //
		" coalesce(mercatipresenze_d.flag_assenza_giust, 0) as assenzagiustificata, " + //
		" 0 as concpresente, " + //
		" coalesce(mercatipresenze_d.spuntista, 0) as spuntpresente, " + //
		" mercatipresenze_d.fk_codiceistat as catmerc, " + //
		" mercatipresenze_t.fkidmercatiuso as idUso, " +
		" autorizzazioni_subentri.id as idAutorizzazioneSubentri " +
		"from" + //
		" mercatipresenze_t" + //
		" inner join mercatipresenze_d on mercatipresenze_d.idcomune = mercatipresenze_t.idcomune " + //
		"    and mercatipresenze_d.fkidtestata = mercatipresenze_t.id " + //
		" and coalesce(mercatipresenze_t.flg_gg_nulla, 0) = ?" + //
		" inner join autorizzazioni_subentri_conc on autorizzazioni_subentri_conc.idcomune = mercatipresenze_t.idcomune " + //
		"    and autorizzazioni_subentri_conc.fk_codicemercato = mercatipresenze_t.fkcodicemercato " + //
		"    and autorizzazioni_subentri_conc.fk_idmercatiuso = mercatipresenze_t.fkidmercatiuso " + //
		"    and autorizzazioni_subentri_conc.fk_idposteggio = mercatipresenze_d.fkidposteggio " + //
		" inner join autorizzazioni_subentri on autorizzazioni_subentri_conc.idcomune = autorizzazioni_subentri.idcomune " + //
		"    and autorizzazioni_subentri_conc.fk_autsub_id = autorizzazioni_subentri.id " + //
		"    and autorizzazioni_subentri.autorizdata <= mercatipresenze_t.dataregistrazione " + //
		"  AND (" + //
		"   ( " + //
		// "  -- la DATA cessazione è impostata e  maggiore uguale della DATA della giornata " + //
		"   autorizzazioni_subentri.DATA_CESSAZIONE > mercatipresenze_t.dataregistrazione ) " + //
		"   OR" + //
		"   ( " + //
		// "  -- la DATA della cessazione è nulla e la DATA di scadenza è impostata e maggiore uguale alla giornata " + //
		"  autorizzazioni_subentri.DATA_CESSAZIONE IS NULL AND autorizzazioni_subentri.datascadenza > mercatipresenze_t.dataregistrazione " + //
		"   )" + //
		" OR" + //
		"   ( " + //
		//"  -- non c'è data cessazione e non c'è DATA scadenza" + //
		"  autorizzazioni_subentri.DATA_CESSAZIONE IS NULL AND autorizzazioni_subentri.datascadenza IS NULL" + //
		"   )" + //
		"  ) " + //
		" where " + //
		" mercatipresenze_t.idcomune = ? " + //
		" and   mercatipresenze_t.fkcodicemercato in ( " + //
		qm +
		" )" + //
		" and   mercatipresenze_t.dataregistrazione >= ? " + //
		" and   mercatipresenze_t.dataregistrazione <= ? " + //
		" and   autorizzazioni_subentri.data_cessazione > mercatipresenze_t.dataregistrazione " + //
		" and   ( " + //
		"     mercatipresenze_d.fk_autorizzazioni_id is null " + //
		"     or    mercatipresenze_d.fk_autorizzazioni_id <> autorizzazioni_subentri.fk_idaut_attuale " + //
		" ) " + //
		" union " + //
		"select" + //
		" mercatipresenze_t.idcomune AS idComune," + //
		" ? as guid," + //
		" 'PROIEZIONE' AS provenienza," + //
		" 0 as subentro," + //
		" mercatipresenze_t.id as idGiornata," + //
		" mercatipresenze_t.dataregistrazione as dataGiornata," + //
		" autorizzazioni_concessioni.fk_idposteggio as idPosteggio," + //
		" autorizzazioni.codiceoccupante as idanagrafe," + //
		" autorizzazioni.id as idRiferimento," + //
		" autorizzazioni_concessioni.id as idAutorizzazioneConcessione, " + //
		" 0 as assenzaGiustificata, " + //
		" 1 as concpresente, " + //
		" 0 as spuntpresente, " + //
		" '' as catmerc, " + //
		" mercatipresenze_t.fkidmercatiuso as idUso, " +
		" NULL as idAutorizzazioneSubentri " +
		"from" + //
		" mercatipresenze_t" + //
		"   inner join autorizzazioni_concessioni on " + //
		" mercatipresenze_t.idcomune = autorizzazioni_concessioni.idcomune and " + //
		" mercatipresenze_t.fkcodicemercato = autorizzazioni_concessioni.fk_codicemercato and " + //
		" mercatipresenze_t.fkidmercatiuso = autorizzazioni_concessioni.fk_idmercatiuso " + //
		" inner join autorizzazioni on " + //
		" autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune and " + //
		" autorizzazioni_concessioni.fk_idaut_attuale = autorizzazioni.id and " + //
		" autorizzazioni.autorizdata <= mercatipresenze_t.dataregistrazione " + //
		" and ( " + //
		"   ( " + //
		// " -- la data cessazione è impostata e  maggiore uguale della data della giornata " + //
		"   autorizzazioni.data_cessazione > mercatipresenze_t.dataregistrazione ) " + //
		"   or" + //
		"   ( " + //
		//  -- la data della cessazione è nulla e la data di scadenza è impostata e maggiore uguale alla giornata 
		"  autorizzazioni.data_cessazione is null and autorizzazioni.datascadenza > mercatipresenze_t.dataregistrazione " + //
		"   )" + //
		" or" + //
		"   ( " + //
		//  -- non c'è data cessazione e non c'è data scadenza
		"  autorizzazioni.data_cessazione is null and autorizzazioni.datascadenza is null" + //
		"   )" + //
		"  ) " + //
		// gestione dell'affitto
		" inner join  istanze on " + //
		" istanze.idcomune=autorizzazioni.idcomune and " + //
		" istanze.codiceistanza=autorizzazioni.fkidistanza " +
		" left join concessionicausali on " + //
		" concessionicausali.idcomune = autorizzazioni.idcomune and " + //
		" concessionicausali.codicecausale=autorizzazioni.fk_causale_acquisizione and " + //
		" concessionicausali.flag_causali_affitto = ? and  " + //
		" autorizzazioni.data_fine_affitto > mercatipresenze_t.dataregistrazione " + //
		// gestione dell'affitto
		" where" + //
		" mercatipresenze_t.idcomune = ? and" + //
		" mercatipresenze_t.fkcodicemercato in (" + //
		qm +
		") and" + //
		" mercatipresenze_t.dataregistrazione >= ? and" + //
		" mercatipresenze_t.dataregistrazione <= ? and" + //
		" not exists " + //
		" (" + //
		"    select 1 from mercatipresenze_d " + //
		" where " + //
		" mercatipresenze_d.idcomune = mercatipresenze_t.idcomune and " + //
		" mercatipresenze_d.fkidtestata = mercatipresenze_t.id and " + //
		" mercatipresenze_d.flag_assenza_giust = ?" + //
		" ) " + //
		" union " + //
		"SELECT " + // 
		" mercatipresenze_t.idcomune AS idComune," + //
		" ? as guid," + //
		" 'PROIEZIONE_SUBENTRI' AS provenienza, " + //
		" 1 as subentro, " + //
		" mercatipresenze_t.id AS idGiornata, " + //
		" mercatipresenze_t.dataregistrazione AS dataGiornata, " + //
		" autorizzazioni_concessioni.fk_idposteggio AS idPosteggio, " + //
		" autorizzazioni.codiceoccupante AS idanagrafe, " + //
		" autorizzazioni.fk_idaut_attuale AS idRiferimento, " + //
		" autorizzazioni_concessioni.id AS idAutorizzazioneConcessione, " + //
		" 0 AS assenzaGiustificata, " + //
		" 1 AS concpresente, " + //
		" 0 AS spuntpresente, " + //
		" '' AS catmerc, " + //
		" mercatipresenze_t.fkidmercatiuso as idUso, " +
		" autorizzazioni.id as idAutorizzazioneSubentri " +
		" FROM " + //
		" mercatipresenze_t " + //
		" INNER JOIN autorizzazioni_subentri_conc autorizzazioni_concessioni ON " + //
		" mercatipresenze_t.idcomune = autorizzazioni_concessioni.idcomune AND " + //
		" mercatipresenze_t.fkcodicemercato = autorizzazioni_concessioni.fk_codicemercato AND " + //
		" mercatipresenze_t.fkidmercatiuso = autorizzazioni_concessioni.fk_idmercatiuso " + //
		" INNER JOIN autorizzazioni_subentri autorizzazioni ON " + //
		" autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune AND " + //
		" autorizzazioni_concessioni.FK_AUTSUB_ID = autorizzazioni.id AND " + //
		" autorizzazioni.autorizdata <= mercatipresenze_t.dataregistrazione " + //
		" AND " + //
		" autorizzazioni.data_cessazione > mercatipresenze_t.dataregistrazione " + //
		" INNER JOIN istanze ON " + //
		" istanze.idcomune=autorizzazioni.idcomune AND " + //
		" istanze.codiceistanza=autorizzazioni.fkidistanza " + //
		" LEFT JOIN concessionicausali ON " + //
		" concessionicausali.idcomune = autorizzazioni.idcomune AND " + //
		" concessionicausali.codicecausale=autorizzazioni.fk_causale_acquisizione AND " + //
		" concessionicausali.flag_causali_affitto = ? AND " + //
		" autorizzazioni.data_fine_affitto > mercatipresenze_t.dataregistrazione " + //
		" " + //
		" WHERE " + //
		" mercatipresenze_t.idcomune = ? AND " + //
		" mercatipresenze_t.fkcodicemercato IN ( " + //
		qm +
		" ) AND " + //
		" mercatipresenze_t.dataregistrazione >= ? AND " + //
		" mercatipresenze_t.dataregistrazione <= ? AND " + //
		" NOT EXISTS " + //
		" ( " + //
		" SELECT 1 FROM mercatipresenze_d " + //
		" WHERE " + //
		" mercatipresenze_d.idcomune = mercatipresenze_t.idcomune AND " + //
		" mercatipresenze_d.fkidtestata = mercatipresenze_t.id AND " + //
		" mercatipresenze_d.flag_assenza_giust = ? " + //
		" )";
	parameters.add(new ParameterHelper(position++, this.guid, new StringType())); //guid
	parameters.add(new ParameterHelper(position++, 0, new IntegerType())); //spuntista
	parameters.add(new ParameterHelper(position++, ORMHelper.getIdcomune(), new StringType())); //idcomune
	for (Integer codiceMercato : filtriMercati) { //mercati
	    parameters.add(new ParameterHelper(position++, codiceMercato, new IntegerType()));
	}
	// TODO RICORDARSI DI CALCOLARE LA DATA CON LE ORE MINUTI SECONDI
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataInizio(), new TimestampType())); //dataregistrazione >=
	// TODO RICORDARSI DI CALCOLARE LA DATA CON LE ORE MINUTI SECONDI
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataFine(), new TimestampType())); //dataregistrazione <=
	//***********************************************************************************************************
	parameters.add(new ParameterHelper(position++, this.guid, new StringType())); //guid
	parameters.add(new ParameterHelper(position++, 0, new IntegerType())); //spuntista
	parameters.add(new ParameterHelper(position++, ORMHelper.getIdcomune(), new StringType())); //idcomune
	for (Integer codiceMercato : filtriMercati) { //mercati
	    parameters.add(new ParameterHelper(position++, codiceMercato, new IntegerType()));
	}
	// TODO RICORDARSI DI CALCOLARE LA DATA CON LE ORE MINUTI SECONDI
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataInizio(), new TimestampType())); //dataregistrazione >=
	// TODO RICORDARSI DI CALCOLARE LA DATA CON LE ORE MINUTI SECONDI
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataFine(), new TimestampType())); //dataregistrazione <=
	//***********************************************************************************************************
	parameters.add(new ParameterHelper(position++, this.guid, new StringType())); //guid
	parameters.add(new ParameterHelper(position++, 0, new IntegerType())); //flg_gg_nulla
	parameters.add(new ParameterHelper(position++, ORMHelper.getIdcomune(), new StringType())); //idcomune
	for (Integer codiceMercato : filtriMercati) { //mercati
	    parameters.add(new ParameterHelper(position++, codiceMercato, new IntegerType()));
	}
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataInizio(), new TimestampType())); //dataregistrazione >=
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataFine(), new TimestampType())); //dataregistrazione <=
	//***********************************************************************************************************
	parameters.add(new ParameterHelper(position++, this.guid, new StringType())); //guid
	parameters.add(new ParameterHelper(position++, 0, new IntegerType())); //flg_gg_nulla
	parameters.add(new ParameterHelper(position++, ORMHelper.getIdcomune(), new StringType())); //idcomune
	for (Integer codiceMercato : filtriMercati) { //mercati
	    parameters.add(new ParameterHelper(position++, codiceMercato, new IntegerType()));
	}
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataInizio(), new TimestampType())); //dataregistrazione >=
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataFine(), new TimestampType())); //dataregistrazione <=
	//***********************************************************************************************************
	parameters.add(new ParameterHelper(position++, this.guid, new StringType())); //guid
	parameters.add(new ParameterHelper(position++, 1, new IntegerType())); //flag_causali_affitto
	parameters.add(new ParameterHelper(position++, ORMHelper.getIdcomune(), new StringType())); //idcomune
	for (Integer codiceMercato : filtriMercati) { //mercati
	    parameters.add(new ParameterHelper(position++, codiceMercato, new IntegerType()));
	}
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataInizio(), new TimestampType())); //dataregistrazione >=
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataFine(), new TimestampType())); //dataregistrazione <=
	parameters.add(new ParameterHelper(position++, 0, new IntegerType())); //flag_assenza_giust
	//***********************************************************************************************************
	parameters.add(new ParameterHelper(position++, this.guid, new StringType())); //guid
	parameters.add(new ParameterHelper(position++, 1, new IntegerType())); //flag_causali_affitto
	parameters.add(new ParameterHelper(position++, ORMHelper.getIdcomune(), new StringType())); //idcomune
	for (Integer codiceMercato : filtriMercati) { //mercati
	    parameters.add(new ParameterHelper(position++, codiceMercato, new IntegerType()));
	}
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataInizio(), new TimestampType())); //dataregistrazione >=
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataFine(), new TimestampType())); //dataregistrazione <=
	parameters.add(new ParameterHelper(position++, 0, new IntegerType())); //flag_assenza_giust
	//***********************************************************************************************************
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
