package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.TransactionHelper;
import org.hibernate.id.Configurable;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.enhanced.AccessCallback;
import org.hibernate.id.enhanced.Optimizer;
import org.hibernate.id.enhanced.OptimizerFactory;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.jdbc.util.FormatStyle;
import org.hibernate.mapping.Table;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;
import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An enhanced version of table-based id generation.
 * <p/>
 * Unlike the simplistic legacy one (which, btw, was only ever intended for subclassing support) we "segment" the table
 * into multiple values. Thus a single table can actually serve as the persistent storage for multiple independent
 * generators. One approach would be to segment the values by the name of the entity for which we are performing
 * generation, which would mean that we would have a row in the generator table for each entity name. Or any
 * configuration really; the setup is very flexible.
 * <p/>
 * In this respect it is very simliar to the legacy {@link org.hibernate.id.MultipleHiLoPerTableGenerator} in terms of
 * the underlying storage structure (namely a single table capable of holding multiple generator values). The
 * differentiator is, as with {@link SequenceStyleGenerator} as well, the externalized notion of an optimizer.
 * <p/>
 * <b>NOTE</b> that by default we use a single row for all genertators (based on {@link #DEF_SEGMENT_VALUE}). The
 * configuration parameter {@link #CONFIG_PREFER_SEGMENT_PER_ENTITY} can be used to change that to instead default to
 * using a row for each entity name.
 * <p/>
 * Configuration parameters:
 * <table>
 * <tr>
 * <td><b>NAME</b></td>
 * <td><b>DEFAULT</b></td>
 * <td><b>DESCRIPTION</b></td>
 * </tr>
 * <tr>
 * <td>{@link #TABLE_PARAM}</td>
 * <td>{@link #DEF_TABLE}</td>
 * <td>The name of the table to use to store/retrieve values</td>
 * </tr>
 * <tr>
 * <td>{@link #VALUE_COLUMN_PARAM}</td>
 * <td>{@link #DEF_VALUE_COLUMN}</td>
 * <td>The name of column which holds the sequence value for the given segment</td>
 * </tr>
 * <tr>
 * <td>{@link #SEGMENT_COLUMN_PARAM}</td>
 * <td>{@link #DEF_SEGMENT_COLUMN}</td>
 * <td>The name of the column which holds the segment key</td>
 * </tr>
 * <tr>
 * <td>{@link #SEGMENT_VALUE_PARAM}</td>
 * <td>{@link #DEF_SEGMENT_VALUE}</td>
 * <td>The value indicating which segment is used by this generator; refers to values in the
 * {@link #SEGMENT_COLUMN_PARAM} column</td>
 * </tr>
 * <tr>
 * <td>{@link #SEGMENT_LENGTH_PARAM}</td>
 * <td>{@link #DEF_SEGMENT_LENGTH}</td>
 * <td>The data length of the {@link #SEGMENT_COLUMN_PARAM} column; used for schema creation</td>
 * </tr>
 * <tr>
 * <td>{@link #INITIAL_PARAM}</td>
 * <td>{@link #DEFAULT_INITIAL_VALUE}</td>
 * <td>The initial value to be stored for the given segment</td>
 * </tr>
 * <tr>
 * <td>{@link #INCREMENT_PARAM}</td>
 * <td>{@link #DEFAULT_INCREMENT_SIZE}</td>
 * <td>The increment size for the underlying segment; see the discussion on {@link Optimizer} for more details.</td>
 * </tr>
 * <tr>
 * <td>{@link #OPT_PARAM}</td>
 * <td><i>depends on defined increment size</i></td>
 * <td>Allows explicit definition of which optimization strategy to use</td>
 * </tr>
 * </table>
 * 
 * @author Steve Ebersole
 */
public class PkIdGenerator extends TransactionHelper implements PersistentIdentifierGenerator, Configurable {

    private static final Logger log = LoggerFactory.getLogger(PkIdGenerator.class);
    public static final String CONFIG_PREFER_SEGMENT_PER_ENTITY = "prefer_entity_table_as_segment_value";
    public static final String TABLE_PARAM = "table_name";
    public static final String DEF_TABLE = "hibernate_sequences";
    public static final String VALUE_COLUMN_PARAM = "value_column_name";
    public static final String DEF_VALUE_COLUMN = "next_val";
    public static final String SEGMENT_COLUMN_PARAM = "segment_column_name";
    public static final String DEF_SEGMENT_COLUMN = "sequence_name";
    public static final String SEGMENT_VALUE_PARAM = "segment_value";
    public static final String DEF_SEGMENT_VALUE = "default";
    public static final String SEGMENT_LENGTH_PARAM = "segment_value_length";
    public static final int DEF_SEGMENT_LENGTH = 255;
    public static final String INITIAL_PARAM = "initial_value";
    public static final int DEFAULT_INITIAL_VALUE = 1;
    public static final String INCREMENT_PARAM = "increment_size";
    public static final int DEFAULT_INCREMENT_SIZE = 1;
    public static final String OPT_PARAM = "optimizer";
    private static Set<String> sequenzaTabellaMaster = new HashSet<String>();
    //    private static final String SEQUENZE_INSTALLAZIONE_MASTER = "ALBEROPROC.SC_ID,ALBEROPROCPEOPLEOPER.ID,ALBEROPROCPEOPLEHREF.ID,ALBEROPROC_DOCUMENTI.SM_ID,ALBEROPROC_ONERI.AO_ID,ALBEROPROC_LEGGI.SL_ID,"
    //	    + "ALLEGATI.ID,AMMINISTRAZIONI.CODICEAMMINISTRAZIONE,AMMINISTRAZIONIREFERENTI.ID,DYN2_CAMPI.ID,DYN2_MODELLID.ID,DYN2_MODELLIDTESTI.ID,"
    //	    + "DYN2_MODELLIT.ID,INVENTARIOPROCEDIMENTI.CODICEINVENTARIO,INVENTARIOPROCEDIMENTIINCOMP.ID,"
    //	    + "INVENTARIOPROCEDIMENTIONERI.ID,INVENTARIOPROC_LEGGI.ID,LEGGI.LE_ID,LEGGITIPI.LT_ID,NATURAENDO.CODICENATURA,"
    //	    + "NORMATIVE.CODICENORMATIVA,OGGETTI.CODICEOGGETTO,TEMPIFICAZIONI.CODICETEMPIFICAZIONE,TIPIENDO.CODICE,"
    //	    + "TIPIFAMIGLIEENDO.CODICE,TIPIPROCEDURE.CODICEPROCEDURA,INVENTARIOPROC_TIPITITOLO.ID";
    private Type identifierType;
    private String tableName;
    private String segmentColumnName;
    private String segmentValue;
    private int segmentValueLength;
    private String valueColumnName;
    private int initialValue;
    private int incrementSize;
    private String selectQuery;
    private String selectMaxQuery;
    private String selectMasterMaxQuery;
    private String selectExistRecordQuery;
    private String insertQuery;
    private String updateQuery;
    private Optimizer optimizer;
    private long accessCount = 0;
    private String masterKeyTableName;
    // private boolean isInstallazioneMaster;
    /**
     * IL LIMITE AL DI SOPRA DEL QUALE NON POSSONO ESSERE STRAPPATE SEQUENZE VEDI BUG ID 90
     */
    public static final int MAX_HI_VALUE = 90000000;

    /**
     * Type mapping for the identifier.
     * 
     * @return The identifier type mapping.
     */
    public final Type getIdentifierType() {

	return identifierType;
    }

    /**
     * The name of the table in which we store this generator's persistent state.
     * 
     * @return The table name.
     */
    public final String getTableName() {

	return tableName;
    }

    /**
     * The name of the column in which we store the segment to which each row belongs. The value here acts as PK.
     * 
     * @return The segment column name
     */
    public final String getSegmentColumnName() {

	return segmentColumnName;
    }

    /**
     * The value in {@link #getSegmentColumnName segment column} which corresponding to this generator instance. In
     * other words this value indicates the row in which this generator instance will store values.
     * 
     * @return The segment value for this generator instance.
     */
    public final String getSegmentValue() {

	return segmentValue;
    }

    /**
     * The size of the {@link #getSegmentColumnName segment column} in the underlying table.
     * <p/>
     * <b>NOTE</b> : should really have been called 'segmentColumnLength' or even better 'segmentColumnSize'
     * 
     * @return the column size.
     */
    public final int getSegmentValueLength() {

	return segmentValueLength;
    }

    /**
     * The name of the column in which we store our persistent generator value.
     * 
     * @return The name of the value column.
     */
    public final String getValueColumnName() {

	return valueColumnName;
    }

    /**
     * The initial value to use when we find no previous state in the generator table corresponding to our sequence.
     * 
     * @return The initial value to use.
     */
    public final int getInitialValue() {

	return initialValue;
    }

    /**
     * The amount of increment to use. The exact implications of this depends on the {@link #getOptimizer() optimizer}
     * being used.
     * 
     * @return The increment amount.
     */
    public final int getIncrementSize() {

	return incrementSize;
    }

    /**
     * The optimizer being used by this generator.
     * 
     * @return Out optimizer.
     */
    public final Optimizer getOptimizer() {

	return optimizer;
    }

    /**
     * Getter for property 'tableAccessCount'. Only really useful for unit test assertions.
     * 
     * @return Value for property 'tableAccessCount'.
     */
    public final long getTableAccessCount() {

	return accessCount;
    }

    /**
     * {@inheritDoc}
     */
    public void configure(Type type, Properties params, Dialect dialect) throws MappingException {

	identifierType = type;
	tableName = determneGeneratorTableName(params);
	segmentColumnName = determineSegmentColumnName(params);
	valueColumnName = determineValueColumnName(params);
	segmentValue = determineSegmentValue(params);
	segmentValueLength = determineSegmentColumnSize(params);
	initialValue = determineInitialValue(params);
	incrementSize = determineIncrementSize(params);
	this.selectQuery = buildSelectQuery(dialect);
	this.selectMaxQuery = buildSelectMaxQuery(false);
	this.selectMasterMaxQuery = buildSelectMaxQuery(true);
	this.selectExistRecordQuery = buildSelectExistRecordQuery();
	this.updateQuery = buildUpdateQuery();
	this.insertQuery = buildInsertQuery();
	String defOptStrategy = incrementSize <= 1 ? OptimizerFactory.NONE : OptimizerFactory.POOL;
	String optimizationStrategy = PropertiesHelper.getString(OPT_PARAM, params, defOptStrategy);
	optimizer = OptimizerFactory.buildOptimizer(optimizationStrategy, /* identifierType.getReturnedClass() */Integer.class, incrementSize);
	// this.isInstallazioneMaster = determinaTipologiaInstallazione();
	this.masterKeyTableName = determineMasterKeyTableName(params);
    }

    /**
     * Determine the table name to use for the generator values.
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getTableName()
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @return The table name to use.
     */
    protected String determneGeneratorTableName(Properties params) {

	String name = PropertiesHelper.getString(TABLE_PARAM, params, DEF_TABLE);
	boolean isGivenNameUnqualified = name.indexOf('.') < 0;
	if (isGivenNameUnqualified) {
	    // if the given name is un-qualified we may neen to qualify it
	    String schemaName = params.getProperty(SCHEMA);
	    String catalogName = params.getProperty(CATALOG);
	    name = Table.qualify(catalogName, schemaName, name);
	}
	return name;
    }

    /**
     * Determine the table name to use for the generator values.
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getTableName()
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @return The table name to use.
     */
    protected String determineMasterKeyTableName(Properties params) {

	String name = "MASTERKEY";
	// if the given name is un-qualified we may neen to qualify it
	String schemaName = params.getProperty(SCHEMA);
	String catalogName = params.getProperty(CATALOG);
	name = Table.qualify(catalogName, schemaName, name);
	return name;
    }

    /**
     * Determine the name of the column used to indicate the segment for each row. This column acts as the primary key.
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getSegmentColumnName()
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @return The name of the segment column
     */
    protected String determineSegmentColumnName(Properties params) {

	return PropertiesHelper.getString(SEGMENT_COLUMN_PARAM, params, DEF_SEGMENT_COLUMN);
    }

    /**
     * Determine the name of the column in which we will store the generator persistent value.
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getValueColumnName()
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @return The name of the value column
     */
    protected String determineValueColumnName(Properties params) {

	return PropertiesHelper.getString(VALUE_COLUMN_PARAM, params, DEF_VALUE_COLUMN);
    }

    /**
     * Determine the segment value corresponding to this generator instance.
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getSegmentValue()
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @return The name of the value column
     */
    protected String determineSegmentValue(Properties params) {

	String segmentValue = params.getProperty(SEGMENT_VALUE_PARAM);
	if (StringHelper.isEmpty(segmentValue)) {
	    segmentValue = determineDefaultSegmentValue(params);
	}
	return segmentValue;
    }

    /**
     * Used in the cases where {@link #determineSegmentValue} is unable to determine the value to use.
     * 
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @return The default segment value to use.
     */
    protected String determineDefaultSegmentValue(Properties params) {

	boolean preferSegmentPerEntity = PropertiesHelper.getBoolean(CONFIG_PREFER_SEGMENT_PER_ENTITY, params, false);
	String defaultToUse = preferSegmentPerEntity ? params.getProperty(TABLE) : DEF_SEGMENT_VALUE;
	log.info("explicit segment value for id generator [" + tableName + '.' + segmentColumnName + "] suggested; using default [" + defaultToUse
		+ "]");
	return defaultToUse;
    }

    /**
     * Determine the size of the {@link #getSegmentColumnName segment column}
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getSegmentValueLength()
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @return The size of the segment column
     */
    protected int determineSegmentColumnSize(Properties params) {

	return PropertiesHelper.getInt(SEGMENT_LENGTH_PARAM, params, DEF_SEGMENT_LENGTH);
    }

    protected int determineInitialValue(Properties params) {

	return PropertiesHelper.getInt(INITIAL_PARAM, params, DEFAULT_INITIAL_VALUE);
    }

    protected int determineIncrementSize(Properties params) {

	return PropertiesHelper.getInt(INCREMENT_PARAM, params, DEFAULT_INCREMENT_SIZE);
    }

    @SuppressWarnings("unchecked")
    protected String buildSelectQuery(Dialect dialect) {

	final String alias = "tbl";
	String query = "select " + StringHelper.qualify(alias, valueColumnName) + " from " + tableName + " " + alias + " where "
		+ StringHelper.qualify(alias, segmentColumnName) + "=?  and " + StringHelper.qualify(alias, WebConstants.IDCOMUNE) + "=?";
	HashMap lockMap = new HashMap();
	lockMap.put(alias, LockMode.UPGRADE);
	Map updateTargetColumnsMap = Collections.singletonMap(alias, new String[] { valueColumnName });
	return dialect.applyLocksToSql(query, lockMap, updateTargetColumnsMap);
    }

    protected String buildUpdateQuery() {

	return "update " + tableName + " set " + valueColumnName + "=?" + " where " + segmentColumnName + "=?" + " and " + WebConstants.IDCOMUNE
		+ "=?";
    }

    protected String buildInsertQuery() {

	return "insert into " + tableName + " (" + segmentColumnName + ", " + valueColumnName + ", " + WebConstants.IDCOMUNE + ") "
		+ " values (?,?,?)";
    }

    /**
     * La query fa una max sulle tabelle per determinare il prossimo progressivo di una tabella. <br/>
     * Se {@link #isInstallazioneMaster} è <b>false</b> allora la max è per valori inferiori a {@link #MAX_HI_VALUE}[
     * {@value #MAX_HI_VALUE} ].<br/>
     * Se {@link #isInstallazioneMaster} è <b>true</b> allora la max è per valori superiori a {@link #MAX_HI_VALUE}[
     * {@value #MAX_HI_VALUE} ].
     * 
     * @see WebConstants#get_TIPOLOGIA_INSTALLAZIONE()
     * @return
     */
    protected String buildSelectMaxQuery(boolean isInstallazioneMaster) {

	String _segment_value = getSegmentValue();
	String _tableName = _segment_value.substring(0, _segment_value.lastIndexOf("."));
	String _init_value = "select max(" + _segment_value + ") from " + _tableName + " where " + WebConstants.IDCOMUNE + " = ? and ";
	if (isInstallazioneMaster) {
	    _init_value += _segment_value + " > ?";
	} else {
	    _init_value += _segment_value + " < ?";
	}
	return _init_value;
    }

    protected String buildSelectExistRecordQuery() {

	String _segment_value = getSegmentValue();
	String _init_value = "";
	if (_segment_value.indexOf(".") > 0) {
	    String _tableName = _segment_value.substring(0, _segment_value.lastIndexOf("."));
	    _init_value = "select " + _segment_value + " from " + _tableName + " where " + WebConstants.IDCOMUNE + " = ?  and " + _segment_value
		    + "=?";
	} else {
	    _init_value = "select " + _segment_value + " from " + _segment_value + " where " + WebConstants.IDCOMUNE + " = ?  and " + _segment_value
		    + "=?";
	}
	return _init_value;
    }

    /**
     * {@inheritDoc}
     */
    public Serializable generate(final SessionImplementor session, Object obj) {

	PkId id = new PkId();
	Number codice = (Number) EntityUtils.getNestedProperty(obj, "id.codice");
	if (codice == null) {
	    codice = (Number) optimizer.generate(new AccessCallback() {

		public long getNextValue() {

		    return ((Number) doWorkInNewTransaction(session)).longValue();
		}
	    });
	}
	id.setCodice((Integer) codice);
	id.setIdcomune(ORMHelper.getIdcomune());
	return id;
    }

    /**
     * {@inheritDoc}
     */
    public Serializable doWorkInCurrentTransaction(Connection conn, String sql) throws SQLException {

	if (WebConstants.getCODICI_INSTALLAZIONE_MASTER().contains(ORMHelper.getIdcomune())) {
	    // installazione MASTER
	    return prossimoValoreDaSequenza(conn, true);
	} else {
	    // INSTALLAZIONE NORMALE
	    return prossimoValoreDaSequenza(conn, false);
	}
    }

    /**
     * Verifico che all'interno di una installazione master la sequenza sia delle tabelle di configurazione impostate
     * nella costante {@link #SEQUENZE_INSTALLAZIONE_MASTER}
     * 
     * @param segmentValue
     * @return
     * @throws SQLException
     */
    private boolean isInstallazioneMasterTable(Connection conn, String segmentValue) throws SQLException {

	if (StringUtils.isBlank(segmentValue)) {
	    return false;
	}
	if (PkIdGenerator.sequenzaTabellaMaster == null) {
	    PkIdGenerator.sequenzaTabellaMaster = new HashSet<String>();
	}
	if (PkIdGenerator.sequenzaTabellaMaster.isEmpty()) {
	    String selectMasterKey = "select * from " + masterKeyTableName;
	    SQL_STATEMENT_LOGGER.logStatement(selectMasterKey, FormatStyle.BASIC);
	    PreparedStatement selectPS = conn.prepareStatement(selectMasterKey);
	    ResultSet selectRS = null;
	    try {
		selectRS = selectPS.executeQuery();
		while (selectRS.next()) {
		    String sequenza = selectRS.getString("tablename");
		    sequenza += "." + selectRS.getString("columnname");
		    PkIdGenerator.sequenzaTabellaMaster.add(sequenza);
		}
	    } catch (SQLException sqle) {
		log.error("could not read or init a hi value", sqle);
		throw sqle;
	    } finally {
		try {
		    selectRS.close();
		} catch (Exception e) {
		}
		try {
		    selectPS.close();
		} catch (Exception e) {
		}
	    }
	}
	return PkIdGenerator.sequenzaTabellaMaster.contains(segmentValue);
    }

    private Serializable prossimoValoreDaSequenza(Connection conn, boolean isInstallazioneMaster) throws SQLException {

	int result;
	int rows;
	do {
	    //.. esiste la sequenza in sequence table per la tabella.id? e l'idcomune 
	    SQL_STATEMENT_LOGGER.logStatement(selectQuery, FormatStyle.BASIC);
	    PreparedStatement selectPS = conn.prepareStatement(selectQuery);
	    try {
		selectPS.setString(1, segmentValue);
		selectPS.setString(2, ORMHelper.getIdcomune());
		ResultSet selectRS = selectPS.executeQuery();
		if (!selectRS.next()) {
		    // .. non esiste la sequenza allora faccio una Max della colonna sulla tabella
		    result = initialValue;
		    // la select max effettua una max per valori inferiori a MAX_HI_VALUE
		    SQL_STATEMENT_LOGGER.logStatement(selectMaxQuery, FormatStyle.BASIC);
		    PreparedStatement selectMaxPS = null;
		    if (isInstallazioneMaster) {
			if (isInstallazioneMasterTable(conn, segmentValue)) {
			    // se è una tabella delle configurazioni allora la max deve tener conto di valori superiori a MAX_HI_VALUE
			    selectMaxPS = conn.prepareStatement(selectMasterMaxQuery);
			} else {
			    // altrimenti la tratto come una normale tabella e la max deve tener conto di valori inferiori a MAX_HI_VALUE
			    selectMaxPS = conn.prepareStatement(selectMaxQuery);
			}
		    } else {
			selectMaxPS = conn.prepareStatement(selectMaxQuery);
		    }
		    selectMaxPS.setString(1, ORMHelper.getIdcomune());
		    selectMaxPS.setInt(2, MAX_HI_VALUE);
		    ResultSet selectMaxRS = selectMaxPS.executeQuery();
		    if (selectMaxRS.next()) {
			// .. assegno il valore alla variabile result
			result = selectMaxRS.getInt(1) + 1;
		    }
		    selectMaxRS.close();
		    selectMaxPS.close();
		    PreparedStatement insertPS = null;
		    try {
			// .. inserisco il valore della max in sequence table
			SQL_STATEMENT_LOGGER.logStatement(insertQuery, FormatStyle.BASIC);
			insertPS = conn.prepareStatement(insertQuery);
			insertPS.setString(1, segmentValue);
			insertPS.setLong(2, result);
			insertPS.setString(3, ORMHelper.getIdcomune());
			insertPS.execute();
		    } finally {
			if (insertPS != null) {
			    insertPS.close();
			}
		    }
		} else {
		    result = selectRS.getInt(1) + 1;
		}
		selectRS.close();
	    } catch (SQLException sqle) {
		log.error("could not read or init a hi value", sqle);
		throw sqle;
	    } finally {
		selectPS.close();
	    }
	    SQL_STATEMENT_LOGGER.logStatement(updateQuery, FormatStyle.BASIC);
	    PreparedStatement updatePS = conn.prepareStatement(updateQuery);
	    try {
		if (!isInstallazioneMaster) {
		    // questo controllo limita le sequenze a MAX_HI_VALUE così codici maggiori possono essere utilizzati da un MASTER, 
		    // in caso di superamento non strappiamo la sequenza e diamo un errore chiaro
		    if (result >= MAX_HI_VALUE) {
			throw new SQLException("Superato il limite massimo [" + MAX_HI_VALUE + "] per ottenere una sequenza per il campo ["
				+ segmentValue + "] e idcomune [" + ORMHelper.getIdcomune() + "]. Contattare l'assistenza.");
		    }
		} else {
		    if (isInstallazioneMasterTable(conn, segmentValue)) {
			if (result < MAX_HI_VALUE) {
			    // siamo in un'installazione MASTER e il progressivo deve partire da MAX_HI_VALUE
			    result = MAX_HI_VALUE + 1;
			}
		    }
		}
		updatePS.setLong(1, result);
		updatePS.setString(2, segmentValue);
		updatePS.setString(3, ORMHelper.getIdcomune());
		rows = updatePS.executeUpdate();
		if (rows > 0) {
		    // il valore è stato aggiornato e la sequenza è stata strappata
		    // devo controllare che il record non esista già nel database
		    SQL_STATEMENT_LOGGER.logStatement(selectExistRecordQuery, FormatStyle.BASIC);
		    PreparedStatement selectExistsPS = conn.prepareStatement(selectExistRecordQuery);
		    selectExistsPS.setString(1, ORMHelper.getIdcomune());
		    selectExistsPS.setInt(2, result);
		    ResultSet selectExistsRS = selectExistsPS.executeQuery();
		    if (selectExistsRS.next()) {
			// Il numero è già usato da un'altro record e si tratta di un eventuale buco
			// passo al prossimo numero settando rows=0
			rows = 0;
		    }
		    selectExistsRS.close();
		    selectExistsPS.close();
		}
	    } catch (SQLException sqle) {
		log.error("could not updateQuery hi value in: " + tableName, sqle);
		throw sqle;
	    } finally {
		updatePS.close();
	    }
	} while (rows == 0);
	accessCount++;
	return Integer.valueOf(result);
    }

    /**
     * {@inheritDoc}
     */
    public String[] sqlCreateStrings(Dialect dialect) throws HibernateException {

	return new String[] { new StringBuffer().append(dialect.getCreateTableString()).append(' ').append(tableName).append(" ( ")
		.append(WebConstants.IDCOMUNE).append(' ').append(dialect.getTypeName(Types.VARCHAR, 6, 0, 0)).append(",  ")
		.append(segmentColumnName).append(' ').append(dialect.getTypeName(Types.VARCHAR, segmentValueLength, 0, 0)).append(",  ")
		.append(valueColumnName).append(' ').append(dialect.getTypeName(Types.BIGINT)).append(", primary key ( ")
		.append(WebConstants.IDCOMUNE).append(",  ").append(segmentColumnName).append(" ) ) ").toString() };
    }

    /**
     * {@inheritDoc}
     */
    public String[] sqlDropStrings(Dialect dialect) throws HibernateException {

	StringBuffer sqlDropString = new StringBuffer().append("drop table ");
	if (dialect.supportsIfExistsBeforeTableName()) {
	    sqlDropString.append("if exists ");
	}
	sqlDropString.append(tableName).append(dialect.getCascadeConstraintsString());
	if (dialect.supportsIfExistsAfterTableName()) {
	    sqlDropString.append(" if exists");
	}
	return new String[] { sqlDropString.toString() };
    }

    /**
     * {@inheritDoc}
     */
    public Object generatorKey() {

	return tableName;
    }
}
