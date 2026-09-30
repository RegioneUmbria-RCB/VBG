package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.sorteggi.FiltriSorteggioBean;

public class QueryIstanzeDaSorteggiare extends BaseQueryHelper {

    private FiltriSorteggioBean filtro;

    public QueryIstanzeDaSorteggiare(SessionFactoryImplementor sessimpl, FiltriSorteggioBean filtro) {

	if (sessimpl != null) {
	    Dialect dialetto = sessimpl.getDialect();
	    String hibernateDialect = dialetto.toString();
	    this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	    this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	}
	this.filtro = filtro;
    }

    public List<ParameterHelper> getParameters() {

	return this.parameters;
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
    }

    @Override
    public String buildQuery() {

	if (!StringUtils.isBlank(this.filtro.getTipoMovimento())) {
	    return this.buildQueryDaMovimenti();
	}
	return this.buildQueryDaIstanze();
    }

    private String buildQueryDaIstanze() {

	String sql = "select " + //
		" istanze.codiceistanza " + //
		"from " + //
		" istanze " + //
		"   inner join alberoproc on istanze.idcomune = alberoproc.idcomune and istanze.codiceinterventoproc = alberoproc.sc_id " + //
		"where " + //
		" istanze.idcomune = ? ";
	if (!StringUtils.isBlank(this.filtro.getCodiceComune())) {
	    sql += "and istanze.codicecomune = ? ";
	}
	sql += "and istanze.software = ? ";
	if (filtro.getIdTipiArchivioIstanza() != null) {
	    sql += "and istanze.codicearchivio = ? ";
	}
	boolean filtroProcedura = filtro.getIdProcedura() != null && !filtro.getIdProcedura().isEmpty();
	boolean filtroEndoprocedimenti = filtro.getIdEndoprocedimenti() != null && !filtro.getIdEndoprocedimenti().isEmpty();
	boolean filtroNature = filtro.getIdNatureEndo() != null && !filtro.getIdNatureEndo().isEmpty();
	if (filtroProcedura || filtroEndoprocedimenti || filtroNature) {
	    sql += "and (";
	    if (filtroProcedura) {
		String inProcedure = StringUtils.repeat("?,", filtro.getIdProcedura().size());
		inProcedure = inProcedure.substring(0, inProcedure.length() - 1);
		sql += " istanze.codiceprocedura in (" + inProcedure + ")";
		if (filtroEndoprocedimenti) {
		    sql += " " + filtro.getModalitaSelezioneEndo() + " ";
		}
	    }
	    if (filtroEndoprocedimenti || filtroNature) {
		sql += " exists " + //
			" ( " + //
			"   select 1  " + //
			"   from  " + //
			"     istanzeprocedimenti " + //
			"       inner join inventarioprocedimenti on " + //
			"         istanzeprocedimenti.idcomune = inventarioprocedimenti.idcomune and " + //
			"         istanzeprocedimenti.codiceinventario = inventarioprocedimenti.codiceinventario" + //
			"   where" +
			"     istanze.idcomune = istanzeprocedimenti.idcomune and " + //
			"     istanze.codiceistanza = istanzeprocedimenti.codiceistanza ";
		if (filtroEndoprocedimenti) {
		    String inProcedimenti = StringUtils.repeat("?,", filtro.getIdEndoprocedimenti().size());
		    inProcedimenti = inProcedimenti.substring(0, inProcedimenti.length() - 1);
		    sql += "and inventarioprocedimenti.codiceinventario in (" + inProcedimenti + ") ";
		}
		if (filtroNature) {
		    String inNature = StringUtils.repeat("?,", filtro.getIdNatureEndo().size());
		    inNature = inNature.substring(0, inNature.length() - 1);
		    sql += "and inventarioprocedimenti.codicenatura in (" + inNature + ") ";
		}
		sql += ") ";
	    }
	    sql += ") ";
	}
	if (filtro.getCodiciStatoIstanza() != null && !filtro.getCodiciStatoIstanza().isEmpty()) {
	    String inStatiIstanza = StringUtils.repeat("?,", filtro.getCodiciStatoIstanza().size());
	    inStatiIstanza = inStatiIstanza.substring(0, inStatiIstanza.length() - 1);
	    sql += "and istanze.chiusura in (" + inStatiIstanza + ") ";
	}
	if (filtro.getDataDal() != null) {
	    sql += "and istanze.data >= ? ";
	}
	if (filtro.getDataAl() != null) {
	    sql += "and istanze.data <= ? ";
	}
	if (filtro.getScCodiciInterventoProc() != null && !filtro.getScCodiciInterventoProc().isEmpty()) {
	    String inScCodici = StringUtils.repeat(" alberoproc.sc_codice like ? or", filtro.getScCodiciInterventoProc().size());
	    inScCodici = inScCodici.substring(0, inScCodici.length() - 2);
	    sql += "and (" + inScCodici + ") ";
	}
	sql += "and alberoproc.flagescludisorteggio <> ? ";
	boolean escludiSorteggi = this.filtro.getIdSorteggiDaEscludere() != null && !this.filtro.getIdSorteggiDaEscludere().isEmpty();
	boolean escludiCategorie = this.filtro.getIdCategorieDaEscludere() != null && !this.filtro.getIdCategorieDaEscludere().isEmpty();
	if (escludiSorteggi || escludiCategorie) {
	    sql += "and not exists " + //
		    "( " + //
		    "  select 1 " + //
		    "  from sorteggidettaglio " + //
		    "    inner join sorteggitestata on " + //
		    "      sorteggidettaglio.idcomune = sorteggitestata.idcomune and " + //
		    "      sorteggidettaglio.sd_fk_stid =  sorteggitestata.st_id " + //
		    "  where " + //
		    "   istanze.idcomune = sorteggidettaglio.idcomune and " + //
		    "   istanze.codiceistanza = sorteggidettaglio.codiceistanza and " + //
		    " ( ";
	    if (escludiSorteggi) {
		String inSorteggi = StringUtils.repeat("?,", filtro.getIdSorteggiDaEscludere().size());
		inSorteggi = inSorteggi.substring(0, inSorteggi.length() - 1);
		sql += "sorteggitestata.st_id in (" + inSorteggi + ") ";
	    }
	    if (escludiSorteggi && escludiCategorie) {
		sql += " or ";
	    }
	    if (escludiCategorie) {
		String inCategorie = StringUtils.repeat("?,", filtro.getIdCategorieDaEscludere().size());
		inCategorie = inCategorie.substring(0, inCategorie.length() - 1);
		sql += "sorteggitestata.fk_idcategoria in (" + inCategorie + ") ";
	    }
	    sql += ")) ";
	}
	int idx = 0;
	parameters.add(new ParameterHelper(idx++, ORMHelper.getIdcomune(), new StringType()));
	if (!StringUtils.isBlank(this.filtro.getCodiceComune())) {
	    parameters.add(new ParameterHelper(idx++, this.filtro.getCodiceComune(), new StringType()));
	}
	parameters.add(new ParameterHelper(idx++, this.filtro.getSoftware(), new StringType()));
	if (filtro.getIdTipiArchivioIstanza() != null) {
	    parameters.add(new ParameterHelper(idx++, this.filtro.getIdTipiArchivioIstanza(), new IntegerType()));
	}
	if (filtroProcedura) {
	    for (Integer idProcedura : filtro.getIdProcedura()) {
		parameters.add(new ParameterHelper(idx++, idProcedura, new IntegerType()));
	    }
	}
	if (filtroEndoprocedimenti) {
	    for (Integer idProcedimento : filtro.getIdEndoprocedimenti()) {
		parameters.add(new ParameterHelper(idx++, idProcedimento, new IntegerType()));
	    }
	}
	if (filtroNature) {
	    for (Integer idNatura : filtro.getIdNatureEndo()) {
		parameters.add(new ParameterHelper(idx++, idNatura, new IntegerType()));
	    }
	}
	if (filtro.getCodiciStatoIstanza() != null && !filtro.getCodiciStatoIstanza().isEmpty()) {
	    for (String idStatoIstanza : filtro.getCodiciStatoIstanza()) {
		parameters.add(new ParameterHelper(idx++, idStatoIstanza, new StringType()));
	    }
	}
	if (filtro.getDataDal() != null) {
	    parameters.add(new ParameterHelper(idx++, this.filtro.getDataDal(), new  org.hibernate.type.TimestampType()));
	}
	if (filtro.getDataAl() != null) {
	    parameters.add(new ParameterHelper(idx++, this.filtro.getDataAl(), new  org.hibernate.type.TimestampType()));
	}
	if (filtro.getScCodiciInterventoProc() != null && !filtro.getScCodiciInterventoProc().isEmpty()) {
	    for (String scCodice : filtro.getScCodiciInterventoProc()) {
		parameters.add(new ParameterHelper(idx++, scCodice + "%", new StringType()));
	    }
	}
	parameters.add(new ParameterHelper(idx++, 1, new IntegerType())); //alberoproc.flagescludisorteggio
	if (escludiSorteggi) {
	    for (Integer idSorteggio : filtro.getIdSorteggiDaEscludere()) {
		parameters.add(new ParameterHelper(idx++, idSorteggio, new IntegerType()));
	    }
	}
	if (escludiCategorie) {
	    for (Integer idCategoria : filtro.getIdCategorieDaEscludere()) {
		parameters.add(new ParameterHelper(idx++, idCategoria, new IntegerType()));
	    }
	}
	return sql;
    }

    private String buildQueryDaMovimenti() {

	String sql = "select " + //
		" istanze.codiceistanza " + //
		"from " + //
		"  movimenti" + //
		"    inner join istanze on movimenti.idcomune = istanze.idcomune and movimenti.codiceistanza = istanze.codiceistanza" + //
		"    inner join alberoproc on istanze.idcomune = alberoproc.idcomune and istanze.codiceinterventoproc = alberoproc.sc_id " + //
		"where " + //
		" movimenti.idcomune = ? and" + // 
		" movimenti.tipomovimento = ? ";
	switch (filtro.getTipoRicercaMovimento()) {
	    case 0: { // Utlizzo le date come range per la data del movimento (movimenti effettuati)
		if (filtro.getDataDal() != null) {
		    sql += "and movimenti.data >= ? ";
		}
		if (filtro.getDataAl() != null) {
		    sql += "and movimenti.data <= ? ";
		}
		break;
	    }
	    case 1: { // Utlizzo le date come range per la data di scadenza del movimento (movimenti da effettuare)
		sql += "and movimenti.data is null ";
		if (filtro.getDataDal() != null) {
		    sql += "and movimenti.data_scadenza >= ? ";
		}
		if (filtro.getDataAl() != null) {
		    sql += "and movimenti.data_scadenza <= ? ";
		}
		break;
	    }
	    case 2: { // Utilizzo le date come range sia per la data del movimento che per quella di scadenza mettendole in or
		if (filtro.getDataDal() != null) {
		    sql += "and (movimenti.data >= ? or movimenti.data_scadenza >= ?) ";
		}
		if (filtro.getDataAl() != null) {
		    sql += "and (movimenti.data <= ? or movimenti.data_scadenza <= ?) ";
		}
		break;
	    }
	}
	if (!StringUtils.isBlank(this.filtro.getCodiceComune())) {
	    sql += "and istanze.codicecomune = ? ";
	}
	sql += "and istanze.software = ? ";
	if (filtro.getIdTipiArchivioIstanza() != null) {
	    sql += "and istanze.codicearchivio = ? ";
	}
	boolean filtroProcedura = filtro.getIdProcedura() != null && !filtro.getIdProcedura().isEmpty();
	boolean filtroEndoprocedimenti = filtro.getIdEndoprocedimenti() != null && !filtro.getIdEndoprocedimenti().isEmpty();
	boolean filtroNature = filtro.getIdNatureEndo() != null && !filtro.getIdNatureEndo().isEmpty();
	if (filtroProcedura || filtroEndoprocedimenti || filtroNature) {
	    sql += "and (";
	    if (filtroProcedura) {
		String inProcedure = StringUtils.repeat("?,", filtro.getIdProcedura().size());
		inProcedure = inProcedure.substring(0, inProcedure.length() - 1);
		sql += " istanze.codiceprocedura in (" + inProcedure + ")";
		if (filtroEndoprocedimenti) {
		    sql += " " + filtro.getModalitaSelezioneEndo() + " ";
		}
	    }
	    if (filtroEndoprocedimenti || filtroNature) {
		sql += " exists " + //
			" ( " + //
			"   select 1  " + //
			"   from  " + //
			"     istanzeprocedimenti " + //
			"       inner join inventarioprocedimenti on " + //
			"         istanzeprocedimenti.idcomune = inventarioprocedimenti.idcomune and " + //
			"         istanzeprocedimenti.codiceinventario = inventarioprocedimenti.codiceinventario" + //
			"   where" +
			"     istanze.idcomune = istanzeprocedimenti.idcomune and " + //
			"     istanze.codiceistanza = istanzeprocedimenti.codiceistanza ";
		if (filtroEndoprocedimenti) {
		    String inProcedimenti = StringUtils.repeat("?,", filtro.getIdEndoprocedimenti().size());
		    inProcedimenti = inProcedimenti.substring(0, inProcedimenti.length() - 1);
		    sql += "and inventarioprocedimenti.codiceinventario in (" + inProcedimenti + ") ";
		}
		if (filtroNature) {
		    String inNature = StringUtils.repeat("?,", filtro.getIdNatureEndo().size());
		    inNature = inNature.substring(0, inNature.length() - 1);
		    sql += "and inventarioprocedimenti.codicenatura in (" + inNature + ") ";
		}
		sql += ") ";
	    }
	    sql += ") ";
	}
	if (filtro.getCodiciStatoIstanza() != null && !filtro.getCodiciStatoIstanza().isEmpty()) {
	    String inStatiIstanza = StringUtils.repeat("?,", filtro.getCodiciStatoIstanza().size());
	    inStatiIstanza = inStatiIstanza.substring(0, inStatiIstanza.length() - 1);
	    sql += "and istanze.chiusura in (" + inStatiIstanza + ") ";
	}
	if (filtro.getScCodiciInterventoProc() != null && !filtro.getScCodiciInterventoProc().isEmpty()) {
	    String inScCodici = StringUtils.repeat(" alberoproc.sc_codice like ? or", filtro.getScCodiciInterventoProc().size());
	    inScCodici = inScCodici.substring(0, inScCodici.length() - 2);
	    sql += "and (" + inScCodici + ") ";
	}
	sql += "and alberoproc.flagescludisorteggio <> ? ";
	boolean escludiSorteggi = this.filtro.getIdSorteggiDaEscludere() != null && !this.filtro.getIdSorteggiDaEscludere().isEmpty();
	boolean escludiCategorie = this.filtro.getIdCategorieDaEscludere() != null && !this.filtro.getIdCategorieDaEscludere().isEmpty();
	if (escludiSorteggi || escludiCategorie) {
	    sql += "and not exists " + //
		    "( " + //
		    "  select 1 " + //
		    "  from sorteggidettaglio " + //
		    "    inner join sorteggitestata on " + //
		    "      sorteggidettaglio.idcomune = sorteggitestata.idcomune and " + //
		    "      sorteggidettaglio.sd_fk_stid =  sorteggitestata.st_id " + //
		    "  where " + //
		    "   istanze.idcomune = sorteggidettaglio.idcomune and " + //
		    "   istanze.codiceistanza = sorteggidettaglio.codiceistanza ";
	    if (escludiSorteggi) {
		String inSorteggi = StringUtils.repeat("?,", filtro.getIdSorteggiDaEscludere().size());
		inSorteggi = inSorteggi.substring(0, inSorteggi.length() - 1);
		sql += "and sorteggitestata.st_id in (" + inSorteggi + ") ";
	    }
	    if (escludiCategorie) {
		String inCategorie = StringUtils.repeat("?,", filtro.getIdCategorieDaEscludere().size());
		inCategorie = inCategorie.substring(0, inCategorie.length() - 1);
		sql += "and sorteggitestata.fk_idcategoria in (" + inCategorie + ") ";
	    }
	    sql += ") ";
	}
	int idx = 0;
	parameters.add(new ParameterHelper(idx++, ORMHelper.getIdcomune(), new StringType()));
	parameters.add(new ParameterHelper(idx++, this.filtro.getTipoMovimento(), new StringType()));
	switch (filtro.getTipoRicercaMovimento()) {
	    case 2: {
		if (filtro.getDataDal() != null) {
		    parameters.add(new ParameterHelper(idx++, this.filtro.getDataDal(), new org.hibernate.type.TimestampType()));
		    parameters.add(new ParameterHelper(idx++, this.filtro.getDataDal(), new org.hibernate.type.TimestampType()));
		}
		if (filtro.getDataAl() != null) {
		    parameters.add(new ParameterHelper(idx++, this.filtro.getDataAl(), new org.hibernate.type.TimestampType()));
		    parameters.add(new ParameterHelper(idx++, this.filtro.getDataAl(), new org.hibernate.type.TimestampType()));
		}
		break;
	    }
	    default: {
		if (filtro.getDataDal() != null) {
		    parameters.add(new ParameterHelper(idx++, this.filtro.getDataDal(), new org.hibernate.type.TimestampType()));
		}
		if (filtro.getDataAl() != null) {
		    parameters.add(new ParameterHelper(idx++, this.filtro.getDataAl(), new org.hibernate.type.TimestampType()));
		}
		break;
	    }
	}
	if (!StringUtils.isBlank(this.filtro.getCodiceComune())) {
	    parameters.add(new ParameterHelper(idx++, this.filtro.getCodiceComune(), new StringType()));
	}
	parameters.add(new ParameterHelper(idx++, this.filtro.getSoftware(), new StringType()));
	if (filtro.getIdTipiArchivioIstanza() != null) {
	    parameters.add(new ParameterHelper(idx++, this.filtro.getIdTipiArchivioIstanza(), new IntegerType()));
	}
	if (filtroProcedura) {
	    for (Integer idProcedura : filtro.getIdProcedura()) {
		parameters.add(new ParameterHelper(idx++, idProcedura, new IntegerType()));
	    }
	}
	if (filtroEndoprocedimenti) {
	    for (Integer idProcedimento : filtro.getIdEndoprocedimenti()) {
		parameters.add(new ParameterHelper(idx++, idProcedimento, new IntegerType()));
	    }
	}
	if (filtroNature) {
	    for (Integer idNatura : filtro.getIdNatureEndo()) {
		parameters.add(new ParameterHelper(idx++, idNatura, new IntegerType()));
	    }
	}
	if (filtro.getCodiciStatoIstanza() != null && !filtro.getCodiciStatoIstanza().isEmpty()) {
	    for (String idStatoIstanza : filtro.getCodiciStatoIstanza()) {
		parameters.add(new ParameterHelper(idx++, idStatoIstanza, new StringType()));
	    }
	}
	if (filtro.getScCodiciInterventoProc() != null && !filtro.getScCodiciInterventoProc().isEmpty()) {
	    for (String scCodice : filtro.getScCodiciInterventoProc()) {
		parameters.add(new ParameterHelper(idx++, scCodice + "%", new StringType()));
	    }
	}
	parameters.add(new ParameterHelper(idx++, 1, new IntegerType())); //alberoproc.flagescludisorteggio
	if (escludiSorteggi) {
	    for (Integer idSorteggio : filtro.getIdSorteggiDaEscludere()) {
		parameters.add(new ParameterHelper(idx++, idSorteggio, new IntegerType()));
	    }
	}
	if (escludiCategorie) {
	    for (Integer idCategoria : filtro.getIdCategorieDaEscludere()) {
		parameters.add(new ParameterHelper(idx++, idCategoria, new IntegerType()));
	    }
	}
	return sql;
    }
}
