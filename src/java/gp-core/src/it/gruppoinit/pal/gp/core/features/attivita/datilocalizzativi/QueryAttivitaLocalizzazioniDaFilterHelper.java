package it.gruppoinit.pal.gp.core.features.attivita.datilocalizzativi;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;

public class QueryAttivitaLocalizzazioniDaFilterHelper extends BaseQueryHelper {

    private static final Logger logger = LoggerFactory.getLogger(QueryAttivitaLocalizzazioniDaFilterHelper.class);
    private IAttivitaFilter filter;

    public QueryAttivitaLocalizzazioniDaFilterHelper(Dyn2CampiDAO dyn2CampiDAO, IAttivitaFilter filter) {

	this.dyn2CampiDAO = dyn2CampiDAO;
	this.filter = filter;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    logger.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idComune", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.addScalar("comune", Hibernate.STRING);
	q.addScalar("codiceIstat", Hibernate.STRING);
	q.addScalar("idAttivita", Hibernate.INTEGER);
	q.addScalar("denominazione", Hibernate.STRING);
	q.addScalar("codiceIstanza", Hibernate.INTEGER);
	q.addScalar("numeroIstanza", Hibernate.STRING);
	q.addScalar("uuidIstanzeStradario", Hibernate.STRING);
	q.addScalar("codiceViario", Hibernate.STRING);
	q.addScalar("civico", Hibernate.STRING);
	q.addScalar("prefisso", Hibernate.STRING);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("km", Hibernate.STRING);
	q.addScalar("primario", Hibernate.INTEGER);
	q.addScalar("latitudine", Hibernate.STRING);
	q.addScalar("longitudine", Hibernate.STRING);
    }

    public String buildQuery() {

	int position = 0;
	String result = "" + //
		"select" + //
		" i_attivita.idcomune as idComune, istanze.software, istanze.codicecomune as codiceComune," + //
		" comuni.comune, comuni.codiceistat as codiceIstat, i_attivita.id as idAttivita, i_attivita.denominazione," + //
		" istanze.codiceistanza as codiceIstanza," + //
		" istanze.numeroistanza as numeroIstanza, istanzestradario.uuid as uuidIstanzeStradario," + //
		" stradario.codviario as codiceViario, istanzestradario.civico, stradario.prefisso, stradario.descrizione," + //
		" istanzestradario.km, istanzestradario.primario," + //
		" istanzestradario.latitudine, istanzestradario.longitudine " + //
		"from" + //
		" i_attivita " + //
		"  inner join istanze on i_attivita.idcomune = istanze.idcomune and i_attivita.codiceistanzaultima = istanze.codiceistanza" + //
		"  inner join alberoproc on istanze.idcomune = alberoproc.idcomune and istanze.codiceinterventoproc = alberoproc.sc_id " + //
		"  inner join comuni on istanze.codicecomune = comuni.codicecomune" + //
		"  inner join istanzestradario on istanze.idcomune = istanzestradario.idcomune and istanze.codiceistanza = istanzestradario.codiceistanza";
	if (Boolean.TRUE.equals(filter.getSoloStradarioPrimario())) {
	    result += " and istanzestradario.primario = ?";
	    parameters.add(new ParameterHelper(position, 1, new IntegerType()));
	    position++;
	}
	result += "  inner join stradario on istanzestradario.idcomune = stradario.idcomune and istanzestradario.codicestradario = stradario.codicestradario " + //
		"where" + //
		"  i_attivita.idcomune = ?";
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	//id attivita
	if (filter.getCodiceAttivita() != null) {
	    result += " and i_attivita.id = ?";
	    parameters.add(new ParameterHelper(position, filter.getCodiceAttivita(), new IntegerType()));
	    position++;
	}
	//elenco id attivita
	if (filter.getListaCodiceAttivita() != null && !filter.getListaCodiceAttivita().isEmpty()) {
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
	//denominazione
	if (isStringNotEmptyOrWildCard(filter.getDenominazioneAttivita())) {
	    result += " and lower(i_attivita.denominazione) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getDenominazioneAttivita().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	//attiva / non attiva
	if (filter.getAttiva() != null) {
	    result += " and lower(i_attivita.attiva) = ?";
	    Integer attiva = filter.getAttiva().booleanValue() ? 1 : 0;
	    parameters.add(new ParameterHelper(position, attiva, new IntegerType()));
	    position++;
	}
	//tipologia
	if (filter.getAttivitaTipologie() != null) {
	    if (filter.getAttivitaTipologie().getId() != null) {
		if (filter.getAttivitaTipologie().getId().getCodice() != null) {
		    result += " and i_attivita.fk_iatipologie_id = ?";
		    parameters.add(new ParameterHelper(position, filter.getAttivitaTipologie().getId().getCodice(), new IntegerType()));
		    position++;
		}
	    }
	}
	//operante / non operante
	if (filter.getOperante() != null) {
	    result += " and lower(i_attivita.operante) = ?";
	    Integer operante = filter.getOperante().booleanValue() ? 1 : 0;
	    parameters.add(new ParameterHelper(position, operante, new IntegerType()));
	    position++;
	}
	//codice osservatorio
	if (filter.getCodiceOsservatorio() != null) {
	    result += " and exists (select 1 from " +
		    "i_attivita_snapshot ias2 where ias2.idcomune=i_attivita.idcomune and ias2.FK_IA_ID=i_attivita.id and ias2.codice_osservatorio=?)";
	    parameters.add(new ParameterHelper(position, filter.getCodiceOsservatorio(), new IntegerType()));
	    position++;
	}
	//intervento istanza
	boolean isSAI = filter.getCheckIntervento() == null ? false : filter.getCheckIntervento().booleanValue();
	String prefissoIstanze = "istanze.";
	if (isSAI) {
	    result += " and exists (select 1 from " + "istanze i2 where i2.idcomune=i_attivita.idcomune and i2.FK_IDI_ATTIVITA=i_attivita.id ";
	    prefissoIstanze = "i2.";
	}
	//software istanza
	if (filter.getSoftware() != null && StringUtils.isNotBlank(filter.getSoftware().getCodice())) {
	    result += " and " + prefissoIstanze + "software=?";
	    parameters.add(new ParameterHelper(position, filter.getSoftware().getCodice(), new StringType()));
	    position++;
	}
	//codice comune istanza
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
	//archivio istanza
	if (EntityUtils.getNestedProperty(filter, "tipiarchivioistanze.id.codice") != null) {
	    result += " and " + prefissoIstanze + "tipoarchivio=?";
	    parameters.add(new ParameterHelper(position, filter.getTipiarchivioistanze().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getPosizioneInArchivio())) {
	    result += " and lower(" + prefissoIstanze + "posizionearchivio) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getPosizioneInArchivio().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	//tipologia istanza
	if (EntityUtils.getNestedProperty(filter, "tipologiaistanza.id.codice") != null) {
	    result += " and " + prefissoIstanze + "fkidtipologiaistanza=?";
	    parameters.add(new ParameterHelper(position, filter.getTipologiaistanza().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//alberoproc istanza
	if (isStringNotEmptyOrWildCard(filter.getScCodice())) {
	    if (isSAI) {
		result += " and exists (select 1 from " +
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
	//lavori istanza
	if (isStringNotEmptyOrWildCard(filter.getDescrizioneLavori())) {
	    result += " and lower(" + prefissoIstanze + "lavori) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getDescrizioneLavori().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	//lavori estesa istanza
	if (isStringNotEmptyOrWildCard(filter.getNote())) {
	    result += " and lower(" + prefissoIstanze + "lavoriestesa) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getNote().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	//richiedente
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
		result += " and exists ( select 1 from anagrafe a1 where a1.idcomune=" + prefissoIstanze + "idcomune and ";
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
	//aree
	if (EntityUtils.getNestedProperty(filter, "aree.id.codice") != null) {
	    result += " and exists (select 1 from " +
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
	// localizzazione
	boolean cercaLocalizzazione = false;
	if (EntityUtils.getNestedProperty(filter.getStradario(), "id.codice") != null
		|| EntityUtils.getNestedProperty(filter.getStradariozone(), "id.codice") != null || StringUtils.isNotBlank(filter.getScala())
		|| StringUtils.isNotBlank(filter.getPiano()) || StringUtils.isNotBlank(filter.getInterno())
		|| StringUtils.isNotBlank(filter.getEsponenteinterno()) || StringUtils.isNotBlank(filter.getFabbricato())
		|| StringUtils.isNotBlank(filter.getFrazione()) || StringUtils.isNotBlank(filter.getCap())
		|| StringUtils.isNotBlank(filter.getQuartiere()) || (EntityUtils.getNestedProperty(filter.getStradariocolore(), "id") != null)
			&& (StringUtils.isNotBlank(filter.getStradariocolore().getId().getCodicecolore()))) {
	    cercaLocalizzazione = true;
	}
	if (cercaLocalizzazione) {
	    result += " and exists (select 1 from " +
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
	// movimenti
	if (StringUtils.isNotBlank((String) EntityUtils.getNestedProperty(filter, "tipimovimento.id.tipomovimento"))) {
	    result += " and exists (select 1 from " +
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
	//endoprocedimenti
	boolean isIstanzeProcedimenti = false;
	if (EntityUtils.getNestedProperty(filter.getInventarioprocedimenti(), "id.codice") != null) {
	    isIstanzeProcedimenti = true;
	}
	if (isIstanzeProcedimenti) {
	    result += " and exists (select 1 from " +
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
	//tipo informazione o dettaglio informazione
	boolean isIstanzeAttivita = false;
	if (StringUtils.isNotBlank(filter.getTipoInformazione().getId().getCodicesettore())
		|| StringUtils.isNotBlank(filter.getDettaglioInformazione().getId().getCodiceistat())) {
	    isIstanzeAttivita = true;
	}
	if (isIstanzeAttivita) {
	    result += " and exists (select 1 from " +
		    "istanzeattivita iatt inner join " +
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
	//Schede dinamiche o campi dinamici
	MutableInt posRef = new MutableInt(position);
	result += createSQLFragment("istanze.", "fk_idi_attivita", "fk_ia_id", "i_attivitadyn2dati", schemaName, filter.getSchedaDinamicaFilter(),
		posRef);
	position = posRef.intValue();
	//chiusura parentesi
	if (isSAI) {
	    result += ")";
	}
	logger.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }
}
