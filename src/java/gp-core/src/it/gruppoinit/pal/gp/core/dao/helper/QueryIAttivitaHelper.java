package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class QueryIAttivitaHelper extends BaseQueryHelper {

    private Date dataEsportazione;
    private TipoQueryHelperEnum tipoQueryHelperEnum;

    public QueryIAttivitaHelper(IAttivitaFilter iattivitaFilter, SessionFactoryImplementor sessimpl, Dyn2CampiDAO dyn2CampiDAO, boolean isCountQuery,
	    Date dataEsportazione, TipoQueryHelperEnum tipoQueryHelperEnum) {

	this.dyn2CampiDAO = dyn2CampiDAO;
	this.filter = iattivitaFilter;
	log.debug("QueryIAttivitaHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryIAttivitaHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryIAttivitaHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryIAttivitaHelper: schemaName={}", schemaName);
	this.isCountQuery = isCountQuery;
	this.dataEsportazione = dataEsportazione;
	this.tipoQueryHelperEnum = tipoQueryHelperEnum;
    }

    @Override
    public String buildQuery() {

	//FK_IA_ID
	String selectPentahoExpQuery = "select i_attivita.idcomune,'" +
		ORMHelper.getToken() +
		"',i_attivita.id,istanze.codicecomune, " +
		stringToDate_DDMMYYYY(Utilities.formatDate(new Date(), false), _dialetto);
	String selectPentahoExpSnapshot = "select i_attivita_snapshot.idcomune,i_attivita_snapshot.fk_ia_id, max(i_attivita_snapshot.data) as ultima_data";
	String result = "";
	int position = 2;
	// dal filtro costruisco la query
	switch (tipoQueryHelperEnum) {
	    case COUNT:
		result = countQuery + fromQuery + whereQuery;
		position = 2;
		break;
	    case EXPORT_IN_DATA:
		result = selectQueryExportSnapshot + fromQuery + fromExportSnapshotQueryToAppich + whereQuery + whereExportSnapshotQueryToAppich;
		position = 3;
		break;
	    case SELECT:
		result = selectQuery + fromQuery + whereQuery;
		position = 2;
		break;
	    case PENTAHO_EXP:
		result = selectPentahoExpQuery + fromQuery + whereQuery;
		position = 2;
		break;
	    case PENTAHO_EXP_IN_DATA:
		result = selectPentahoExpSnapshot + fromQuery + fromExportSnapshotQueryToAppich + whereQuery + whereExportSnapshotQueryToAppich;
		position = 3;
		break;
	    default:
		break;
	}
	//	
	//	// dal filtro costruisco la query
	//	String result = isCountQuery == true ? countQuery : (isExportInData == false ? selectQuery : selectQueryExportSnapshot);
	//	result += fromQuery;
	//	if (isExportInData) {
	//	    result += fromExportSnapshotQueryToAppich;
	//	}
	//	result += whereQuery;
	//	if (isExportInData) {
	//	    result += whereExportSnapshotQueryToAppich;
	//	}
	//	int position = 2;
	//	if (isCountQuery) {
	//	    position = 2;
	//	}
	//	if (isExportInData) {
	//	    position = 3;
	//	}
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	// FILTRI
	// DATI ATTIVTA'
	if (filter.getCodiceAttivita() != null) {
	    result += " and i_attivita.id = ?";
	    parameters.add(new ParameterHelper(position, filter.getCodiceAttivita(), new IntegerType()));
	    position++;
	}
	if (filter.getListaCodiceAttivita() != null) {
	    if (!filter.getListaCodiceAttivita().isEmpty()) {
		if (filter.getListaCodiceAttivita().size() == 1) {
		    Integer idAttivita = filter.getListaCodiceAttivita().get(0);
		    if (idAttivita != null) {
			result += " and i_attivita.id = ?";
			parameters.add(new ParameterHelper(position, idAttivita, new IntegerType()));
			position++;
		    }
		} else {
		    if (filter.getListaCodiceAttivita().size() < 1000) {
			String qm = StringUtils.repeat("?,", filter.getListaCodiceAttivita().size());
			qm = qm.substring(0, qm.length() - 1);
			result += " and i_attivita.id in (" + qm + ")";
		    } else {
			int num = filter.getListaCodiceAttivita().size();
			Double filter_getListaCodiceAttivita_length = Double.valueOf(num);
			Double cicli = filter_getListaCodiceAttivita_length / 1000;
			int cicliDaMille = cicli.intValue();
			int resto = num - (cicliDaMille * 1000);
			result += " and ( 1=2 ";
			for (int i = 0; i < cicliDaMille; i++) {
			    String qm = StringUtils.repeat("?,", 1000);
			    qm = qm.substring(0, qm.length() - 1);
			    result += " or i_attivita.id in (" + qm + ")";
			}
			if (resto > 0) {
			    String qm = StringUtils.repeat("?,", resto);
			    qm = qm.substring(0, qm.length() - 1);
			    result += " or i_attivita.id in (" + qm + ")";
			}
			result += ")";
		    }
		    for (Integer codiceattivita : filter.getListaCodiceAttivita()) {
			parameters.add(new ParameterHelper(position, codiceattivita, new IntegerType()));
			position++;
		    }
		}
	    }
	}
	if (isStringNotEmptyOrWildCard(filter.getDenominazioneAttivita())) {
	    result += " and lower(i_attivita.denominazione) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getDenominazioneAttivita().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (filter.getAttiva() != null) {
	    result += " and lower(i_attivita.attiva) = ?";
	    Integer attiva = filter.getAttiva().booleanValue() ? 1 : 0;
	    parameters.add(new ParameterHelper(position, attiva, new IntegerType()));
	    position++;
	}
	if (filter.getAttivitaTipologie() != null) {
	    if (filter.getAttivitaTipologie().getId() != null) {
		if (filter.getAttivitaTipologie().getId().getCodice() != null) {
		    result += " and i_attivita.fk_iatipologie_id = ?";
		    parameters.add(new ParameterHelper(position, filter.getAttivitaTipologie().getId().getCodice(), new IntegerType()));
		    position++;
		}
	    }
	}
	if (filter.getOperante() != null) {
	    result += " and lower(i_attivita.operante) = ?";
	    Integer operante = filter.getOperante().booleanValue() ? 1 : 0;
	    parameters.add(new ParameterHelper(position, operante, new IntegerType()));
	    position++;
	}
	if (filter.getCodiceOsservatorio() != null) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "i_attivita_snapshot ias2 where ias2.idcomune=i_attivita.idcomune and ias2.FK_IA_ID=i_attivita.id and ias2.codice_osservatorio=?)";
	    parameters.add(new ParameterHelper(position, filter.getCodiceOsservatorio(), new IntegerType()));
	    position++;
	}
	boolean isSAI = filter.getCheckIntervento() == null ? false : filter.getCheckIntervento().booleanValue();
	String prefissoIstanze = "istanze.";
	if (isSAI) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanze i2 where i2.idcomune=i_attivita.idcomune and i2.FK_IDI_ATTIVITA=i_attivita.id ";
	    prefissoIstanze = "i2.";
	}
	// DATI ISTANZA	
	if (filter.getSoftware() != null && StringUtils.isNotBlank(filter.getSoftware().getCodice())) {
	    result += " and " + prefissoIstanze + "software=?";
	    parameters.add(new ParameterHelper(position, filter.getSoftware().getCodice(), new StringType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "comune.codicecomune") != null) {
	    if (StringUtils.isNotBlank(filter.getComune().getCodicecomune())) {
		if (filter.getComune().getCodicecomune().indexOf(",") > 0) {
		    String[] comuni = filter.getComune().getCodicecomune().split(",");
		    String qm = StringUtils.repeat("?,", comuni.length);
		    qm = qm.substring(0, qm.length() - 1);
		    result += " and " + prefissoIstanze + "codicecomune in (" + qm + ")";
		    for (String codicecomune : comuni) {
			parameters.add(new ParameterHelper(position, codicecomune, new StringType()));
			position++;
		    }
		} else {
		    result += " and " + prefissoIstanze + "codicecomune=?";
		    parameters.add(new ParameterHelper(position, filter.getComune().getCodicecomune(), new StringType()));
		    position++;
		}
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "tipiarchivioistanze.id.codice") != null) {
	    result += " and " + prefissoIstanze + "tipoarchivio=?";
	    parameters.add(new ParameterHelper(position, filter.getTipiarchivioistanze().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getPosizioneInArchivio())) {
	    //  UPPER CASE???
	    result += " and lower(" + prefissoIstanze + "posizionearchivio) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getPosizioneInArchivio().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "tipologiaistanza.id.codice") != null) {
	    result += " and " + prefissoIstanze + "fkidtipologiaistanza=?";
	    parameters.add(new ParameterHelper(position, filter.getTipologiaistanza().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getScCodice())) {
	    if (isSAI) {
		result += " and exists (select 1 from " +
			SCHEMA_NAME +
			"alberoproc ap2 where ap2.idcomune=" +
			prefissoIstanze +
			"idcomune and ap2.sc_id=" +
			prefissoIstanze +
			"codiceinterventoproc and ap2.sc_codice like ?) ";
	    } else {
		result += " and alberoproc.sc_codice like ?";
	    }
	    parameters.add(new ParameterHelper(position, filter.getScCodice() + "%", new StringType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getDescrizioneLavori())) {
	    result += " and lower(" + prefissoIstanze + "lavori) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getDescrizioneLavori().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getNote())) {
	    result += " and lower(" + prefissoIstanze + "lavoriestesa) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getNote().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "richiedente.id.codice") != null) {
	    result += " and ( ";
	    result += " ( " +
		    prefissoIstanze +
		    "codicerichiedente = ? or " +
		    prefissoIstanze +
		    "codicetitolarelegale = ? or " +
		    prefissoIstanze +
		    "codiceprofessionista = ? ) ";
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    result += " OR ";
	    result += " exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzerichiedenti ir where ir.idcomune=" +
		    prefissoIstanze +
		    "idcomune and ir.codiceistanza=" +
		    prefissoIstanze +
		    "codiceistanza " +
		    " and  (ir.codicerichiedente = ? or ir.codiceanagrafecoll = ? or ir.codiceprocuratore = ? )";
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    result += " ) )";
	}
	if (EntityUtils.getNestedProperty(filter.getCittadinanza(), "codice") != null) {
	    if (isSAI) {
		result += " and exists ( select 1 from  " + SCHEMA_NAME + "anagrafe a1 where a1.idcomune=" + prefissoIstanze + "idcomune and ";
		result += " a1.codiceanagrafe=" + prefissoIstanze + "codicerichiedente ";
		result += "  and a1.codicecittadinanza = ?  ";
		parameters.add(new ParameterHelper(position, filter.getCittadinanza().getCodice(), new IntegerType()));
		position++;
		result += " ) ";
	    } else {
		result += " and ( ";
		result += "  richiedente.codicecittadinanza = ?  ";
		parameters.add(new ParameterHelper(position, filter.getCittadinanza().getCodice(), new IntegerType()));
		position++;
		result += " ) ";
	    }
	}
	// DATI DELLA LOCALIZZAZIONE
	if (EntityUtils.getNestedProperty(filter, "aree.id.codice") != null) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzearee iaa " +
		    " where iaa.idcomune=" +
		    prefissoIstanze +
		    "idcomune and iaa.codiceistanza=" +
		    prefissoIstanze +
		    "codiceistanza ";
	    result += " and iaa.codicearea = ? ) ";
	    parameters.add(new ParameterHelper(position, filter.getAree().getId().getCodice(), new IntegerType()));
	    position++;
	}
	boolean cercaLocalizzazione = false;
	if (EntityUtils.getNestedProperty(filter.getStradario(), "id.codice") != null
		|| EntityUtils.getNestedProperty(filter.getStradariozone(), "id.codice") != null
		// || StringUtils.isNotBlank(filter.getIstanzestradario().getNote())
		|| StringUtils.isNotBlank(filter.getEsponente()) || StringUtils.isNotBlank(filter.getScala())
		|| StringUtils.isNotBlank(filter.getPiano()) || StringUtils.isNotBlank(filter.getInterno())
		|| StringUtils.isNotBlank(filter.getEsponenteinterno()) || StringUtils.isNotBlank(filter.getFabbricato())
		|| StringUtils.isNotBlank(filter.getFrazione()) || StringUtils.isNotBlank(filter.getCap())
		|| StringUtils.isNotBlank(filter.getQuartiere()) || (EntityUtils.getNestedProperty(filter.getStradariocolore(), "id") != null)
			&& (StringUtils.isNotBlank(filter.getStradariocolore().getId().getCodicecolore()))) {
	    cercaLocalizzazione = true;
	}
	if (cercaLocalizzazione) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzestradario istr " +
		    " where istr.idcomune=" +
		    prefissoIstanze +
		    "idcomune and istr.codiceistanza=" +
		    prefissoIstanze +
		    "codiceistanza ";
	    if (EntityUtils.getNestedProperty(filter.getStradario(), "id.codice") != null) {
		result += " and istr.codicestradario = ? ";
		parameters.add(new ParameterHelper(position, filter.getStradario().getId().getCodice(), new IntegerType()));
		position++;
		if (StringUtils.isNotBlank(filter.getCivico())) {
		    result += " and lower(istr.civico) like ? ";
		    parameters.add(new ParameterHelper(position, filter.getCivico().toLowerCase().trim(), new StringType()));
		    position++;
		}
	    }
	    ////////////
	    if (StringUtils.isNotBlank(filter.getEsponente())) {
		result += " and lower(istr.esponente) like ? ";
		parameters.add(new ParameterHelper(position, filter.getEsponente().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getScala())) {
		result += " and lower(istr.scala) like ? ";
		parameters.add(new ParameterHelper(position, filter.getScala().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getPiano())) {
		result += " and lower(istr.piano) like ? ";
		parameters.add(new ParameterHelper(position, filter.getPiano().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getInterno())) {
		result += " and lower(istr.interno) like ? ";
		parameters.add(new ParameterHelper(position, filter.getInterno().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getEsponenteinterno())) {
		result += " and lower(istr.esponenteinterno) like ? ";
		parameters.add(new ParameterHelper(position, filter.getEsponenteinterno().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getFabbricato())) {
		result += " and lower(istr.fabbricato) like ? ";
		parameters.add(new ParameterHelper(position, filter.getFabbricato().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getFrazione())) {
		result += " and lower(istr.frazione) like ? ";
		parameters.add(new ParameterHelper(position, filter.getFrazione().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getCap())) {
		result += " and lower(istr.cap) like ? ";
		parameters.add(new ParameterHelper(position, filter.getCap().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getQuartiere())) {
		result += " and lower(istr.quartiere) like ? ";
		parameters.add(new ParameterHelper(position, filter.getQuartiere().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //////////// 
	    if (EntityUtils.getNestedProperty(filter.getStradariozone(), "id.codice") != null) {
		result += " and lower(istr.circoscrizione) like ? ";
		parameters.add(new ParameterHelper(position, "%" + filter.getStradariozone().getZona().toLowerCase().trim() + "%", new StringType()));
		position++;
	    }
	    if (EntityUtils.getNestedProperty(filter.getStradariocolore(), "id.codicecolore") != null) {
		if (StringUtils.isNotBlank(filter.getStradariocolore().getId().getCodicecolore())) {
		    result += " and istr.colore = ? ";
		    parameters.add(new ParameterHelper(position, filter.getStradariocolore().getId().getCodicecolore(), new StringType()));
		    position++;
		}
	    }
	    result += " )";
	}
	// DATI PROGETTO
	if (StringUtils.isNotBlank((String) EntityUtils.getNestedProperty(filter, "tipimovimento.id.tipomovimento"))) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "movimenti mv " +
		    " where mv.idcomune=" +
		    prefissoIstanze +
		    "idcomune and mv.codiceistanza=" +
		    prefissoIstanze +
		    "codiceistanza and mv.data is not null ";
	    result += " and mv.tipomovimento = ?";
	    parameters.add(new ParameterHelper(position, filter.getTipimovimento().getId().getTipomovimento(), new StringType()));
	    position++;
	    result += " )";
	}
	boolean isIstanzeProcedimenti = false;
	if (EntityUtils.getNestedProperty(filter.getInventarioprocedimenti(), "id.codice") != null) {
	    isIstanzeProcedimenti = true;
	}
	if (isIstanzeProcedimenti) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzeprocedimenti istp " +
		    " where istp.idcomune=" +
		    prefissoIstanze +
		    "idcomune and istp.codiceistanza=" +
		    prefissoIstanze +
		    "codiceistanza ";
	    result += " and istp.codiceinventario = ? ";
	    parameters.add(new ParameterHelper(position, filter.getInventarioprocedimenti().getId().getCodice(), new IntegerType()));
	    position++;
	    result += " )";
	}
	boolean isIstanzeAttivita = false;
	if (StringUtils.isNotBlank(filter.getTipoInformazione().getId().getCodicesettore())
		|| StringUtils.isNotBlank(filter.getDettaglioInformazione().getId().getCodiceistat())) {
	    isIstanzeAttivita = true;
	}
	if (isIstanzeAttivita) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzeattivita iatt join " +
		    SCHEMA_NAME +
		    "attivita att on " +
		    "att.idcomune=iatt.idcomune and att.codiceistat=iatt.codiceattivita " +
		    " where iatt.idcomune=" +
		    prefissoIstanze +
		    "idcomune and iatt.codiceistanza=" +
		    prefissoIstanze +
		    "codiceistanza and ";
	    if (StringUtils.isNotBlank(filter.getTipoInformazione().getId().getCodicesettore())) {
		if (StringUtils.isBlank(filter.getDettaglioInformazione().getId().getCodiceistat())) {
		    result += " att.codicesettore = ? ";
		    parameters.add(new ParameterHelper(position, filter.getTipoInformazione().getId().getCodicesettore(), new StringType()));
		    position++;
		}
	    }
	    if (StringUtils.isNotBlank(filter.getDettaglioInformazione().getId().getCodiceistat())) {
		result += " iatt.codiceattivita = ? ";
		parameters.add(new ParameterHelper(position, filter.getDettaglioInformazione().getId().getCodiceistat(), new StringType()));
		position++;
	    }
	    result += " )";
	}
	MutableInt posRef = new MutableInt(position);
	result += createSQLFragment("istanze.", "fk_idi_attivita", "fk_ia_id", "i_attivitadyn2dati", schemaName, filter.getSchedaDinamicaFilter(),
		posRef);
	position = posRef.intValue();
	// aggiungere al filtro PER escludere le attività che hanno la scheda che si va ad aggiungere
	if (filter.getCodSchedaPerEscludereLeAttivita() != null) {
	    result += " and not exists (select 1 from " +
		    SCHEMA_NAME +
		    "i_attivitadyn2modellit id2mt where id2mt.idcomune=i_attivita.idcomune" +
		    " and id2mt.fk_ia_id=i_attivita.id and id2mt.fk_d2mt_id = ?) ";
	    parameters.add(new ParameterHelper(position, filter.getCodSchedaPerEscludereLeAttivita(), new IntegerType()));
	    position++;
	}
	// ///ORDINAMENTI
	if (isSAI) {
	    result += ")";
	}
	switch (tipoQueryHelperEnum) {
	    case COUNT:
		break;
	    case EXPORT_IN_DATA:
		result += " group by i_attivita.idcomune,i_attivita_snapshot.fk_ia_id";
		result = "select iasnps.idcomune as idcomune, iasnps.id as codice, iss.codicecomune as codicecomune from " +
			SCHEMA_NAME +
			"i_attivita_snapshot iasnps inner join "
			//
			+
			SCHEMA_NAME
			//
			+
			"istanze iss on iasnps.idcomune=iss.idcomune and iasnps.codiceistanzaultima=iss.codiceistanza ,("
			//
			+
			result +
			") ultimo "
			//
			+
			"where iasnps.idcomune = ultimo.idcomune and iasnps.fk_ia_id = ultimo.fk_ia_id and iasnps.data = ultimo.ultima_data";
		break;
	    case SELECT:
		result += " order by i_attivita.denominazione";
		break;
	    case PENTAHO_EXP:
		break;
	    case PENTAHO_EXP_IN_DATA:
		result += " group by i_attivita_snapshot.idcomune,i_attivita_snapshot.fk_ia_id";
		result = "select iasnps.idcomune as idcomune,'" +
			ORMHelper.getToken() +
			"', iasnps.id as codice, iss.codicecomune as codicecomune," +
			stringToDate_DDMMYYYY(Utilities.formatDate(this.dataEsportazione, false), _dialetto) +
			" from " +
			SCHEMA_NAME +
			"i_attivita_snapshot iasnps inner join "
			//
			+
			SCHEMA_NAME
			//
			+
			"istanze iss on iasnps.idcomune=iss.idcomune and iasnps.codiceistanzaultima=iss.codiceistanza ,("
			//
			+
			result +
			") ultimo "
			//
			+
			"where iasnps.idcomune = ultimo.idcomune and iasnps.fk_ia_id = ultimo.fk_ia_id and iasnps.data = ultimo.ultima_data";
		//
		break;
	    default:
		break;
	}
	//	if (isExportInData) {
	//	    result += " group by i_attivita.idcomune,i_attivita_snapshot.fk_ia_id";
	//	}
	//	if (!isCountQuery && !isExportInData) {
	//	    result += " order by i_attivita.denominazione";
	//	}
	//	////////////////////////////////////////////////
	//	////////////////////////////////////////////////
	//	if (isExportInData) {
	//	    result = "select iasnps.idcomune as idcomune, iasnps.id as codice, iss.codicecomune as codicecomune from " + SCHEMA_NAME
	//		    + "i_attivita_snapshot iasnps inner join "
	//		    //
	//		    + SCHEMA_NAME
	//		    //
	//		    + "istanze iss on iasnps.idcomune=iss.idcomune and iasnps.codiceistanzaultima=iss.codiceistanza ,("
	//		    //
	//		    + result + ") ultimo "
	//		    //
	//		    + "where iasnps.idcomune = ultimo.idcomune and iasnps.fk_ia_id = ultimo.fk_ia_id and iasnps.data = ultimo.ultima_data";
	//	}
	if (StringUtils.isNotBlank(schemaName)) {
	    result = result.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int _position = 0;
	log.debug("param {}={}", _position, 1);
	q.setInteger(_position, 1); // PRIMARIO=1
	_position++;
	log.debug("param {}={}", _position, ORMHelper.getIdcomune());
	q.setString(_position, ORMHelper.getIdcomune()); // IDCOMUNE
	_position++;
	switch (tipoQueryHelperEnum) {
	    case COUNT:
		break;
	    case EXPORT_IN_DATA:
		log.debug("param {}={}", _position, dataEsportazione);
		q.setDate(_position, dataEsportazione); // IDCOMUNE
		_position++;
		break;
	    case SELECT:
		break;
	    case PENTAHO_EXP:
		break;
	    case PENTAHO_EXP_IN_DATA:
		log.debug("param {}={}", _position, dataEsportazione);
		q.setDate(_position, dataEsportazione); // IDCOMUNE
		_position++;
		break;
	    default:
		break;
	}
	//	if (isExportInData) {
	//	    log.debug("param {}={}", _position, dataEsportazione);
	//	    q.setDate(_position, dataEsportazione); // IDCOMUNE
	//	    _position++;
	//	}
	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	if (tipoQueryHelperEnum == TipoQueryHelperEnum.EXPORT_IN_DATA) {
	    q.addScalar("idcomune", Hibernate.STRING);
	    q.addScalar("codice", Hibernate.INTEGER);
	    q.addScalar("codicecomune", Hibernate.STRING);
	} else {
	    q.addScalar("idcomune", Hibernate.STRING);
	    q.addScalar("id", Hibernate.BIG_DECIMAL);
	    q.addScalar("denominazione", Hibernate.STRING);
	    q.addScalar("codiceistanzaultima", Hibernate.BIG_DECIMAL);
	    q.addScalar("attiva", Hibernate.BOOLEAN);
	    q.addScalar("operante", Hibernate.BOOLEAN);
	    q.addScalar("codiceistanza", Hibernate.BIG_DECIMAL);
	    q.addScalar("numeroistanza", Hibernate.STRING);
	    q.addScalar("software", Hibernate.STRING);
	    q.addScalar("data");
	    q.addScalar("numeroprotocollo", Hibernate.STRING);
	    q.addScalar("dataprotocollo");
	    q.addScalar("fkidtipologiaistanza", Hibernate.BIG_DECIMAL);
	    q.addScalar("tipologiaistanza", Hibernate.STRING);
	    q.addScalar("codicerichiedente", Hibernate.BIG_DECIMAL);
	    q.addScalar("richiedentenominativo", Hibernate.STRING);
	    q.addScalar("richiedentenome", Hibernate.STRING);
	    q.addScalar("richiedentecodicefiscale", Hibernate.STRING);
	    q.addScalar("richiedentepartitaiva", Hibernate.STRING);
	    q.addScalar("codicetiposoggetto", Hibernate.BIG_DECIMAL);
	    q.addScalar("tiposoggetto", Hibernate.STRING);
	    q.addScalar("descrizionesoggetto", Hibernate.STRING);
	    q.addScalar("aziendanominativo", Hibernate.STRING);
	    q.addScalar("aziendanome", Hibernate.STRING);
	    q.addScalar("aziendacodicefiscale", Hibernate.STRING);
	    q.addScalar("aziendapartitaiva", Hibernate.STRING);
	    q.addScalar("richstoricoid", Hibernate.BIG_DECIMAL);
	    q.addScalar("richstoriconominativo", Hibernate.STRING);
	    q.addScalar("richstoriconome", Hibernate.STRING);
	    q.addScalar("richstoricocodicefiscale", Hibernate.STRING);
	    q.addScalar("richstoricopartitaiva", Hibernate.STRING);
	    q.addScalar("azstoricoid", Hibernate.BIG_DECIMAL);
	    q.addScalar("azstoriconominativo", Hibernate.STRING);
	    q.addScalar("azstoriconome", Hibernate.STRING);
	    q.addScalar("azstoricocodicefiscale", Hibernate.STRING);
	    q.addScalar("azstoricopartitaiva", Hibernate.STRING);
	    q.addScalar("codiceoperatore", Hibernate.BIG_DECIMAL);
	    q.addScalar("operatorenome", Hibernate.STRING);
	    q.addScalar("codiceresponsabileproc", Hibernate.BIG_DECIMAL);
	    q.addScalar("responsabileprocnome", Hibernate.STRING);
	    q.addScalar("codiceistruttore", Hibernate.BIG_DECIMAL);
	    q.addScalar("istruttorenome", Hibernate.STRING);
	    q.addScalar("codicestradarioprimario", Hibernate.BIG_DECIMAL);
	    q.addScalar("stradarioprefisso", Hibernate.STRING);
	    q.addScalar("stradariodescrizione", Hibernate.STRING);
	    q.addScalar("stradariocivico", Hibernate.STRING);
	    q.addScalar("stradariocap", Hibernate.STRING);
	    q.addScalar("stradarioesponente", Hibernate.STRING);
	    q.addScalar("stradariocolore", Hibernate.STRING);
	    q.addScalar("stradariolocalitafrazione", Hibernate.STRING);
	    q.addScalar("stradarioscala", Hibernate.STRING);
	    q.addScalar("stradariointerno", Hibernate.STRING);
	    q.addScalar("stradarioespinterno", Hibernate.STRING);
	    q.addScalar("stradariopiano", Hibernate.STRING);
	    q.addScalar("stradarioquartiere", Hibernate.STRING);
	    q.addScalar("codiceinterventoproc", Hibernate.BIG_DECIMAL);
	    q.addScalar("interventoproc", Hibernate.STRING);
	    q.addScalar("codiceprocedura", Hibernate.BIG_DECIMAL);
	    q.addScalar("procedura", Hibernate.STRING);
	    q.addScalar("oggettoistanza", Hibernate.STRING);
	    q.addScalar("tipoarchivio", Hibernate.BIG_DECIMAL);
	    q.addScalar("archivio", Hibernate.STRING);
	    q.addScalar("posizionearchivio", Hibernate.STRING);
	    q.addScalar("codicestatoistanza", Hibernate.STRING);
	    q.addScalar("statoistanza", Hibernate.STRING);
	    q.addScalar("codicecomune", Hibernate.STRING);
	    q.addScalar("comune", Hibernate.STRING);
	    q.addScalar("tipologiaattivita", Hibernate.STRING);
	    q.addScalar("aziendaIndirizzo", Hibernate.STRING);
	    q.addScalar("aziendaCitta", Hibernate.STRING);
	    q.addScalar("aziendaCap", Hibernate.STRING);
	    q.addScalar("aziendaComune", Hibernate.STRING);
	    q.addScalar("aziendaProvincia", Hibernate.STRING);
	    q.addScalar("emailRichiedente", Hibernate.STRING);
	    q.addScalar("pecRichiedente", Hibernate.STRING);
	    q.addScalar("emailAziendaRichiedente", Hibernate.STRING);
	    q.addScalar("pecAziendaRichiedente", Hibernate.STRING);
	    q.addScalar("domicilioElettronico", Hibernate.STRING);
	    /////
	    q.addScalar("countstradari", Hibernate.BIG_DECIMAL);
	    q.addScalar("countschede", Hibernate.BIG_DECIMAL);
	}
    }

    private String countQuery = "select count(*) as conteggio_attivita ";
    private String selectQueryExportSnapshot = "select i_attivita.idcomune,i_attivita_snapshot.fk_ia_id, max(i_attivita_snapshot.data) as ultima_data ";
    //    private String selectPentahoExpQuery = "select i_attivita.idcomune,'" + ORMHelper.getToken() + "',i_attivita.id,istanze.codicecomune, "
    //	    + stringToDate_DDMMYYYY(Utilities.formatDate(new Date(), false), _dialetto);
    //    private String selectPentahoExpSnapshot = "select i_attivita_snapshot.idcomune,'" + ORMHelper.getToken()
    //	    + "',i_attivita_snapshot.fk_ia_id,istanze.codicecomune,"
    //	    + stringToDate_DDMMYYYY(Utilities.formatDate(this.dataEsportazione, false), _dialetto);
    private String selectQuery = "select i_attivita.idcomune,"
    //
	    + "i_attivita.id,"
	    //
	    + "i_attivita.denominazione,"
	    //
	    + "i_attivita.codiceistanzaultima, "
	    //
	    + "i_attivita.attiva, "
	    //
	    + "i_attivita.operante, "
	    //
	    + "istanze.codiceistanza, "
	    //
	    + "istanze.numeroistanza, "
	    //
	    + "istanze.software,"
	    //
	    + " istanze.data,"
	    //
	    + " istanze.numeroprotocollo,"
	    //
	    + " istanze.dataprotocollo,"
	    //
	    + " istanze.fkidtipologiaistanza,"
	    //
	    + " tipologiaistanza.ti_descrizione as tipologiaistanza,"
	    //
	    + " istanze.codicerichiedente,"
	    //
	    + " richiedente.nominativo richiedentenominativo,"
	    //
	    + " richiedente.nome richiedentenome,"
	    //
	    + " richiedente.codicefiscale richiedentecodicefiscale,"
	    //
	    + " richiedente.partitaiva richiedentepartitaiva,"
	    //
	    + " richiedente.email as emailRichiedente,"
	    //
	    + " richiedente.pec as pecRichiedente,"
	    //
	    + " tipisoggetto.codicetiposoggetto,"
	    //
	    + " tipisoggetto.tiposoggetto, "
	    //
	    + "istanze.descrsoggetto as descrizionesoggetto, "
	    //
	    + " aziendarichiedente.nominativo aziendanominativo,"
	    //
	    + " aziendarichiedente.nome aziendanome,"
	    //
	    + " aziendarichiedente.codicefiscale aziendacodicefiscale,"
	    // 
	    + " aziendarichiedente.partitaiva aziendapartitaiva,"
	    //
	    + " aziendarichiedente.email as emailAziendaRichiedente,"
	    //
	    + " aziendarichiedente.pec as pecAziendaRichiedente,"
	    //
	    + " istanze.fk_richiedentestorico_id richstoricoid,"
	    //
	    + " richiedentestorico.nominativo richstoriconominativo,"
	    //
	    + " richiedentestorico.nome richstoriconome,"
	    //
	    + " richiedentestorico.codicefiscale richstoricocodicefiscale,"
	    //
	    + " richiedentestorico.partitaiva richstoricopartitaiva,"
	    //
	    + " istanze.fk_titolarelegalestorico_id azstoricoid,"
	    //
	    + " aziendastorico.nominativo azstoriconominativo,"
	    //
	    + " aziendastorico.nome azstoriconome,"
	    //
	    + " aziendastorico.codicefiscale azstoricocodicefiscale,"
	    //
	    + " aziendastorico.partitaiva azstoricopartitaiva,"
	    //
	    + " istanze.codiceresponsabile as codiceoperatore,"
	    //
	    + " operatore.responsabile as operatorenome,"
	    //
	    + " istanze.codiceresponsabileproc,"
	    //
	    + " responsabileprocedimento.responsabile as responsabileprocnome,"
	    //
	    + " istanze.codiceistruttore,"
	    //
	    + " istruttore.responsabile as istruttorenome,"
	    //
	    + " istanzestradario.codicestradario as codicestradarioprimario,"
	    //
	    + " stradario.prefisso as stradarioprefisso,"
	    //
	    + " stradario.descrizione as stradariodescrizione, "
	    //
	    + "istanzestradario.civico as stradariocivico,"
	    //
	    + " istanzestradario.cap as stradariocap,"
	    //
	    + "istanzestradario.esponente as stradarioesponente, "
	    //
	    + "colore.colore as stradariocolore, "
	    //
	    + "istanzestradario.frazione as stradariolocalitafrazione, "
	    //
	    + " istanzestradario.scala as stradarioscala, "
	    //
	    + "istanzestradario.interno as stradariointerno,"
	    //
	    + " istanzestradario.esponenteinterno as stradarioespinterno,"
	    //
	    + " istanzestradario.piano as stradariopiano, "
	    //
	    + "istanzestradario.quartiere as stradarioquartiere,"
	    //
	    + " istanze.codiceinterventoproc,"
	    //
	    + " alberoproc.descrizione_completa as interventoproc,"
	    //
	    + "istanze.codiceprocedura,"
	    //
	    + " tipiprocedure.procedura, "
	    //
	    + "istanze.lavori as oggettoistanza, "
	    //
	    + "istanze.tipoarchivio, "
	    //
	    + "tipiarchivioistanze.archivio,"
	    //
	    + " istanze.posizionearchivio, "
	    //
	    + " istanze.chiusura as codicestatoistanza, "
	    //
	    + "istanze.domicilio_elettronico as domicilioElettronico, "
	    //
	    + " statiistanza.stato as statoistanza, "
	    //
	    + "istanze.codicecomune, "
	    //
	    + "comuni.comune, "
	    //
	    + " i_attivita_tipologie.descrizione as tipologiaattivita, "
	    //
	    + "aziendarichiedente.indirizzo as aziendaIndirizzo, "
	    //
	    + "aziendarichiedente.citta as aziendaCitta, "
	    //
	    + "aziendarichiedente.cap as aziendaCap, "
	    //
	    + "aziendarichiedente.provincia as aziendaProvincia, "
	    //
	    +
	    "aziendacomunesl.comune as aziendaComune, " +
	    " (select count(*) from " +
	    SCHEMA_NAME +
	    "istanzestradario where istanzestradario.idcomune=istanze.idcomune and istanzestradario.codiceistanza=istanze.codiceistanza) as countstradari, " +
	    " (select count(*) from " +
	    SCHEMA_NAME +
	    "I_ATTIVITADYN2MODELLIT where I_ATTIVITADYN2MODELLIT.idcomune=i_attivita.idcomune and I_ATTIVITADYN2MODELLIT.FK_IA_ID=i_attivita.ID ) as countschede ";
    //    select count(*) 
    //    from I_ATTIVITADYN2MODELLIT,i_attivita 
    //    where i_attivita.idcomune=i_attivita.idcomune and I_ATTIVITADYN2MODELLIT.FK_IA_ID=i_attivita.ID 
    private String fromQuery = " from" +
	    " " +
	    SCHEMA_NAME +
	    "i_attivita " +
	    " left join " +
	    SCHEMA_NAME +
	    "i_attivita_tipologie on i_attivita.idcomune=i_attivita_tipologie.idcomune and i_attivita.fk_iatipologie_id=i_attivita_tipologie.id" +
	    " join " +
	    SCHEMA_NAME +
	    "istanze on i_attivita.idcomune=istanze.idcomune and i_attivita.codiceistanzaultima=istanze.codiceistanza" +
	    " join " +
	    SCHEMA_NAME +
	    "anagrafe richiedente on istanze.idcomune=richiedente.idcomune and istanze.codicerichiedente=richiedente.codiceanagrafe" +
	    " left join " +
	    SCHEMA_NAME +
	    "anagrafestorico richiedentestorico on istanze.idcomune=richiedentestorico.idcomune and istanze.fk_richiedentestorico_id=richiedentestorico.id " +
	    " left join " +
	    SCHEMA_NAME +
	    "anagrafestorico aziendastorico on istanze.idcomune=aziendastorico.idcomune and istanze.fk_titolarelegalestorico_id=aziendastorico.id " +
	    " join " +
	    SCHEMA_NAME +
	    "responsabili operatore on istanze.idcomune=operatore.idcomune and istanze.codiceresponsabile=operatore.codiceresponsabile" +
	    " join " +
	    SCHEMA_NAME +
	    "statiistanza on istanze.idcomune=statiistanza.idcomune and istanze.software=statiistanza.software and istanze.chiusura=statiistanza.codicestato" +
	    " join " +
	    SCHEMA_NAME +
	    "alberoproc on istanze.idcomune=alberoproc.idcomune and istanze.codiceinterventoproc=alberoproc.sc_id" +
	    " join " +
	    SCHEMA_NAME +
	    "tipiprocedure on istanze.idcomune=tipiprocedure.idcomune and istanze.codiceprocedura=tipiprocedure.codiceprocedura" +
	    " join " +
	    SCHEMA_NAME +
	    "comuni on istanze.codicecomune=comuni.codicecomune" +
	    " left join " +
	    SCHEMA_NAME +
	    "anagrafe aziendarichiedente on istanze.idcomune=aziendarichiedente.idcomune and istanze.codicetitolarelegale=aziendarichiedente.codiceanagrafe" +
	    " left join " +
	    SCHEMA_NAME +
	    "comuni aziendacomunesl on aziendacomunesl.codicecomune=aziendarichiedente.comuneresidenza " +
	    " left join " +
	    SCHEMA_NAME +
	    "responsabili responsabileprocedimento on istanze.idcomune=responsabileprocedimento.idcomune and istanze.codiceresponsabileproc=responsabileprocedimento.codiceresponsabile" +
	    " left join " +
	    SCHEMA_NAME +
	    "responsabili istruttore on istanze.idcomune=istruttore.idcomune and istanze.codiceistruttore=istruttore.codiceresponsabile" +
	    " left join " +
	    SCHEMA_NAME +
	    "istanzestradario on istanze.idcomune=istanzestradario.idcomune and istanze.codiceistanza=istanzestradario.codiceistanza and istanzestradario.primario=?" +
	    " left join " +
	    SCHEMA_NAME +
	    "stradario on istanzestradario.idcomune=stradario.idcomune and istanzestradario.codicestradario=stradario.codicestradario" +
	    " left join " +
	    SCHEMA_NAME +
	    "tipiarchivioistanze on istanze.idcomune=tipiarchivioistanze.idcomune and istanze.tipoarchivio=tipiarchivioistanze.codicearchivio" +
	    " left join " +
	    SCHEMA_NAME +
	    "tipologiaistanza on istanze.idcomune=tipologiaistanza.idcomune and istanze.fkidtipologiaistanza=tipologiaistanza.ti_id" +
	    " left join " +
	    SCHEMA_NAME +
	    "tipisoggetto on istanze.idcomune=tipisoggetto.idcomune and istanze.fkcodicesoggetto=tipisoggetto.codicetiposoggetto left join " +
	    SCHEMA_NAME +
	    "stradariocolore colore on istanzestradario.idcomune=colore.idcomune and istanzestradario.colore=colore.codicecolore ";
    private String fromExportSnapshotQueryToAppich = " inner join " +
	    SCHEMA_NAME +
	    "i_attivita_snapshot on i_attivita.idcomune = i_attivita_snapshot.idcomune and i_attivita.id = i_attivita_snapshot.fk_ia_id  ";
    private String whereQuery = " where i_attivita.idcomune=?";
    private String whereExportSnapshotQueryToAppich = " and i_attivita_snapshot.data <= ?";
    private IAttivitaFilter filter;
    private static final Logger log = LoggerFactory.getLogger(QueryIAttivitaHelper.class);
}
