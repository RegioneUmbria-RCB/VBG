package it.gruppoinit.pal.gp.core.features.autorizzazioni.query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.MercatoSrvRequest;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class AutorizzazioniMercatoSrvBeanQueryHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(AutorizzazioniMercatoSrvBeanQueryHelper.class);
    private MercatoSrvRequest req;
    private DialettoEnum dialetto = null;

    public AutorizzazioniMercatoSrvBeanQueryHelper(MercatoSrvRequest req, SessionFactoryImplementor sessimpl) {

	super();
	this.req = req;
	dialetto = DialettoEnum.fromHibernateDialect(sessimpl.getDialect().toString());
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idaut", Hibernate.INTEGER);
	q.addScalar("autnum", Hibernate.STRING);
	q.addScalar("autdata");
	q.addScalar("flagattiva", Hibernate.BOOLEAN);
	q.addScalar("notesistema", Hibernate.STRING);
	q.addScalar("datarilascio");
	q.addScalar("statoautoriz", Hibernate.STRING);
	q.addScalar("statowarning", Hibernate.STRING);
	q.addScalar("caussosp", Hibernate.STRING);
	//
	q.addScalar("titcodicefiscale", Hibernate.STRING);
	q.addScalar("titnominativo", Hibernate.STRING);
	q.addScalar("titcodice", Hibernate.INTEGER);
	//
	q.addScalar("occcodicefiscale", Hibernate.STRING);
	q.addScalar("occnominativo", Hibernate.STRING);
	q.addScalar("occcodice", Hibernate.INTEGER);
	//
	q.addScalar("gercodicefiscale", Hibernate.STRING);
	q.addScalar("gernominativo", Hibernate.STRING);
	q.addScalar("gercodice", Hibernate.INTEGER);
	//
	q.addScalar("mercato", Hibernate.STRING);
	q.addScalar("uso", Hibernate.STRING);
	q.addScalar("codiceposteggio", Hibernate.STRING);
	q.addScalar("giorno", Hibernate.STRING);
	//
	q.addScalar("tipomanif", Hibernate.STRING);
	q.addScalar("comune", Hibernate.STRING);
	q.addScalar("datacessazione");
    }

    @Override
    public String buildQuery() {

	int position = 0;
	String sql = "SELECT " + //
		" autorizzazioni.id AS idaut," + //
		" autorizzazioni.autoriznumero AS autnum," + //
		" autorizzazioni.autorizdata AS autdata," + //
		" autorizzazioni.flag_attiva AS flagattiva," + //
		" autorizzazioni.note_sistema AS notesistema," + //
		" autorizzazioni.data_rilascio AS datarilascio," + //
		" autorizzazioni.data_cessazione AS datacessazione," + //
		" autorizzazioni_csi.stato_autorizzazione AS statoautoriz," + //
		" autorizzazioni_csi.stato_warning AS statowarning," + //
		" autorizzazioni_csi.causale_sospensione AS caussosp," + //
		" titolare.codicefiscale AS titcodicefiscale,"; //
	sql += concatNominativo("titolare.nominativo", "titolare.nome") +
		" AS titnominativo," + //
		" titolare.codiceanagrafe AS titcodice," + //
		" occupante.codicefiscale AS occcodicefiscale,"; //
	sql += concatNominativo("occupante.nominativo", "occupante.nome") +
		" AS occnominativo," + //
		" occupante.codiceanagrafe AS occcodice," + //
		" gerente.codicefiscale AS gercodicefiscale,"; //
	sql += concatNominativo("gerente.nominativo", "gerente.nome") +
		" AS gernominativo," + //
		" gerente.codiceanagrafe AS gercodice," + //
		" mercati.descrizione AS mercato," + //
		" mercati_uso.descrizione AS uso," + //
		" mercati_d.codiceposteggio AS codiceposteggio," + //
		" giornisettimana.gs_descrizione AS giorno," + //
		" manifestazioni.descrizione AS tipomanif," + //
		" vw_entilocali.comune AS comune" + //
		" FROM TIPOLOGIAREGISTRI" + //
		" INNER JOIN autorizzazioni "; //
	sql += forceIndex() + //	FORCE INDEX (PRIMARY ) " + // 
		" ON " + //
		" autorizzazioni.IDCOMUNE=TIPOLOGIAREGISTRI.IDCOMUNE AND" + //
		" autorizzazioni.FKIDREGISTRO=TIPOLOGIAREGISTRI.TR_ID " + //
		" INNER JOIN ANAGRAFE TITOLARE ON " + //
		" TITOLARE.IDCOMUNE = AUTORIZZAZIONI.IDCOMUNE AND " + //
		" TITOLARE.CODICEANAGRAFE = AUTORIZZAZIONI.FK_CODICEANAGRAFE" + //
		" INNER JOIN ANAGRAFE OCCUPANTE ON " + //
		" OCCUPANTE.IDCOMUNE = AUTORIZZAZIONI.IDCOMUNE AND " + //
		" OCCUPANTE.CODICEANAGRAFE = AUTORIZZAZIONI.CODICEOCCUPANTE" + //
		" INNER JOIN vw_entilocali ON " + //
		" vw_entilocali.codicecomune=autorizzazioni.autorizcomune " + //
		" LEFT JOIN AUTORIZZAZIONI_CSI ON " + //
		" AUTORIZZAZIONI_CSI.IDCOMUNE= AUTORIZZAZIONI.IDCOMUNE AND" + //
		" AUTORIZZAZIONI_CSI.FK_AUTORIZZAZIONI_ID = AUTORIZZAZIONI.ID" + //
		" LEFT JOIN ANAGRAFE GERENTE ON " + //
		" GERENTE.IDCOMUNE = AUTORIZZAZIONI_CSI.IDCOMUNE AND " + //
		" GERENTE.CODICEANAGRAFE = AUTORIZZAZIONI_CSI.FK_CODICEGERENTE" + //
		" LEFT JOIN autorizzazioni_concessioni ON" + //
		" autorizzazioni_concessioni.idcomune=autorizzazioni.idcomune AND " + //
		" autorizzazioni_concessioni.FK_IDAUT_ATTUALE=autorizzazioni.id" + //
		" LEFT JOIN mercati_d ON " + //
		" mercati_d.idcomune=autorizzazioni_concessioni.idcomune AND " + //
		" mercati_d.idposteggio=autorizzazioni_concessioni.FK_IDPOSTEGGIO" + //
		" LEFT JOIN mercati ON " + //
		" mercati.idcomune=autorizzazioni_concessioni.idcomune AND " + //
		" mercati.CODICEMERCATO=autorizzazioni_concessioni.FK_CODICEMERCATO" + //
		" LEFT JOIN manifestazioni ON " + //
		" manifestazioni.CODICE=mercati.TIPO_MANIFEST " + //
		" LEFT JOIN mercati_uso ON " + //
		" mercati_uso.idcomune=autorizzazioni_concessioni.idcomune AND " + //
		" mercati_uso.id=autorizzazioni_concessioni.FK_IDMERCATIUSO" + //
		" LEFT JOIN giornisettimana ON " + //
		" mercati_uso.FKGSID=giornisettimana.gs_id" + //
		" WHERE " + //
		" TIPOLOGIAREGISTRI.idcomune=? " + //
		" AND TIPOLOGIAREGISTRI.SOFTWARE=? " + //
		" AND TIPOLOGIAREGISTRI.flag_manifestazioni=? "; //
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, ORMHelper.getSoftware(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, Integer.valueOf(1), new IntegerType()));
	position++;
	if (!req.getListaCfUnivoci().isEmpty()) {
	    sql += " AND " + //		     
		    " ( 1=2 ";
	    for (String cf : req.getListaCfUnivoci()) {
		sql += " OR ( UPPER(TITOLARE.CODICEFISCALE)=? OR " + //
			"       UPPER(OCCUPANTE.CODICEFISCALE)=? OR " + //
			"       UPPER(GERENTE.CODICEFISCALE)=? OR" + //
			"       UPPER(TITOLARE.PARTITAIVA)=? OR " + //
			"       UPPER(OCCUPANTE.PARTITAIVA)=? OR " + //
			"       UPPER(GERENTE.PARTITAIVA)=? " + //
			" ) "; //
		parameters.add(new ParameterHelper(position, cf, new StringType()));
		position++;
		parameters.add(new ParameterHelper(position, cf, new StringType()));
		position++;
		parameters.add(new ParameterHelper(position, cf, new StringType()));
		position++;
		parameters.add(new ParameterHelper(position, cf, new StringType()));
		position++;
		parameters.add(new ParameterHelper(position, cf, new StringType()));
		position++;
		parameters.add(new ParameterHelper(position, cf, new StringType()));
		position++;
	    }
	    sql += ")"; //
	}
	if (!req.getAutorizzazione().isEmpty()) {
	    sql += " AND " + //		     
		    " ( 1=2 ";
	    for (String aut : req.getAutorizzazione()) {
		sql += " OR ( UPPER(autorizzazioni.autoriznumero)=? ) "; //
		parameters.add(new ParameterHelper(position, aut, new StringType()));
		position++;
	    }
	    sql += ")"; //
	}
	if (req.isSoloAttivi()) {
	    sql += " and autorizzazioni.flag_attiva=? ";
	    parameters.add(new ParameterHelper(position, 1, new IntegerType()));
	    position++;
	}
	if (StringUtils.isNotBlank(req.getComuni())) {
	    sql += " and autorizzazioni.autorizcomune=? ";
	    parameters.add(new ParameterHelper(position, req.getComuni(), new StringType()));
	    position++;
	}
	log.debug("raggiunta la posizione di parametri {}", position);
	sql += " " + //
		" ORDER BY AUTORIZNUMERO"; //
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }

    private String forceIndex() {

	if (DialettoEnum.MYSQL.equals(dialetto)) {
	    return StringUtils.EMPTY; // return " FORCE INDEX (PRIMARY ) "; 
	}
	return StringUtils.EMPTY;
    }

    private String concatNominativo(String nominativo, String nome) {

	// 
	if (DialettoEnum.MYSQL.equals(dialetto)) {
	    return " TRIM(CONCAT_WS(' '," + nominativo + "," + nome + ")) ";
	} else if (DialettoEnum.ORACLE.equals(dialetto)) {
	    return " trim(" + nominativo + " || ' ' || " + nome + ") ";
	}
	return "trim(" + nominativo + " + ' '+ " + nome + ") ";
    }
}
