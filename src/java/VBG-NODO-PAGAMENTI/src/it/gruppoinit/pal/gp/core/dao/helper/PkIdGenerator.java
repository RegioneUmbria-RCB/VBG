/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.helper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.MappingException;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedNameParser;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.engine.spi.SessionEventListenerManager;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.ExportableColumn;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.enhanced.Optimizer;
import org.hibernate.id.enhanced.OptimizerFactory;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.jdbc.WorkExecutor;
import org.hibernate.jdbc.WorkExecutorVisitable;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Table;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.jboss.logging.Logger;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.PkIdGenerationException;

/**
 * @author francol
 *
 */
public class PkIdGenerator implements PersistentIdentifierGenerator, Configurable {

    private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class, TableGenerator.class.getName());
    private Type identifierType;
    private QualifiedName qualifiedTableName;
    private String renderedTableName;
    private String segmentColumnName;
    private String segmentValue;
    private int segmentValueLength;
    private String valueColumnName;
    private int initialValue;
    private int incrementSize;
    private String selectQuery;
    private String insertQuery;
    private String selectMaxQuery;
    private String selectExistsQuery;
    private String updateQuery;
    private Optimizer optimizer;
    private long accessCount;

    protected String buildSelectQuery(Dialect dialect) {

	final String alias = "tbl";
	final String query = "select " + StringHelper.qualify(alias, valueColumnName) + " from " + renderedTableName + ' ' + alias
		+ " where IDCOMUNE=? and " + StringHelper.qualify(alias, segmentColumnName) + "=?";
	final LockOptions lockOptions = new LockOptions(LockMode.PESSIMISTIC_WRITE);
	lockOptions.setAliasSpecificLockMode(alias, LockMode.PESSIMISTIC_WRITE);
	final Map updateTargetColumnsMap = Collections.singletonMap(alias, new String[] { valueColumnName });
	return dialect.applyLocksToSql(query, lockOptions, updateTargetColumnsMap);
    }

    protected String buildUpdateQuery() {

	return "update " + renderedTableName + " set " + valueColumnName + "=? " + " where " + segmentColumnName + "=? and IDCOMUNE=?";
    }

    protected String buildInsertQuery() {

	return "insert into " + renderedTableName + " (IDCOMUNE, " + segmentColumnName + ", " + valueColumnName + ") " + " values (?,?,?)";
    }

    /**
     * Determine the table name to use for the generator values.
     * <p/>
     * Called during {@link #configure configuration}.
     *
     * @see #getTableName()
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @param jdbcEnvironment
     *            The JDBC environment
     * @return The table name to use.
     */
    @SuppressWarnings("UnusedParameters")
    protected QualifiedName determineGeneratorTableName(Properties params, JdbcEnvironment jdbcEnvironment) {

	final String tableName = ConfigurationHelper.getString(TableGenerator.TABLE_PARAM, params, TableGenerator.DEF_TABLE);
	if (tableName.contains(".")) {
	    return QualifiedNameParser.INSTANCE.parse(tableName);
	} else {
	    // todo : need to incorporate implicit catalog and schema names
	    final Identifier catalog = jdbcEnvironment.getIdentifierHelper().toIdentifier(ConfigurationHelper.getString(CATALOG, params));
	    final Identifier schema = jdbcEnvironment.getIdentifierHelper().toIdentifier(ConfigurationHelper.getString(SCHEMA, params));
	    return new QualifiedNameParser.NameParts(catalog, schema, jdbcEnvironment.getIdentifierHelper().toIdentifier(tableName));
	}
    }

    /**
     * Determine the name of the column used to indicate the segment for each row. This column acts as the primary key.
     * <p/>
     * Called during {@link #configure configuration}.
     *
     * @see #getSegmentColumnName()
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @param jdbcEnvironment
     *            The JDBC environment
     * @return The name of the segment column
     */
    @SuppressWarnings("UnusedParameters")
    protected String determineSegmentColumnName(Properties params, JdbcEnvironment jdbcEnvironment) {

	final String name = ConfigurationHelper.getString(TableGenerator.SEGMENT_COLUMN_PARAM, params, TableGenerator.DEF_SEGMENT_COLUMN);
	return jdbcEnvironment.getIdentifierHelper().toIdentifier(name).render(jdbcEnvironment.getDialect());
    }

    /**
     * Determine the name of the column in which we will store the generator persistent value.
     * <p/>
     * Called during {@link #configure configuration}.
     *
     * @see #getValueColumnName()
     * @param params
     *            The params supplied in the generator config (plus some standard useful extras).
     * @param jdbcEnvironment
     *            The JDBC environment
     * @return The name of the value column
     */
    @SuppressWarnings("UnusedParameters")
    protected String determineValueColumnName(Properties params, JdbcEnvironment jdbcEnvironment) {

	final String name = ConfigurationHelper.getString(TableGenerator.VALUE_COLUMN_PARAM, params, TableGenerator.DEF_VALUE_COLUMN);
	return jdbcEnvironment.getIdentifierHelper().toIdentifier(name).render(jdbcEnvironment.getDialect());
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

	String segmentValue = params.getProperty(TableGenerator.SEGMENT_VALUE_PARAM);
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

	final boolean preferSegmentPerEntity = ConfigurationHelper.getBoolean(TableGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, params, false);
	final String defaultToUse = preferSegmentPerEntity ? params.getProperty(TABLE) : TableGenerator.DEF_SEGMENT_VALUE;
	LOG.usingDefaultIdGeneratorSegmentValue(qualifiedTableName.render(), segmentColumnName, defaultToUse);
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

	return ConfigurationHelper.getInt(TableGenerator.SEGMENT_LENGTH_PARAM, params, TableGenerator.DEF_SEGMENT_LENGTH);
    }

    protected int determineInitialValue(Properties params) {

	return ConfigurationHelper.getInt(TableGenerator.INITIAL_PARAM, params, TableGenerator.DEFAULT_INITIAL_VALUE);
    }

    protected int determineIncrementSize(Properties params) {

	return ConfigurationHelper.getInt(TableGenerator.INCREMENT_PARAM, params, TableGenerator.DEFAULT_INCREMENT_SIZE);
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {

	identifierType = type;
	final JdbcEnvironment jdbcEnvironment = serviceRegistry.getService(JdbcEnvironment.class);
	qualifiedTableName = determineGeneratorTableName(params, jdbcEnvironment);
	segmentColumnName = determineSegmentColumnName(params, jdbcEnvironment);
	valueColumnName = determineValueColumnName(params, jdbcEnvironment);
	segmentValue = determineSegmentValue(params);
	segmentValueLength = determineSegmentColumnSize(params);
	initialValue = determineInitialValue(params);
	incrementSize = determineIncrementSize(params);
	final String optimizationStrategy = ConfigurationHelper.getString(TableGenerator.OPT_PARAM, params,
		OptimizerFactory.determineImplicitOptimizerName(incrementSize, params));
	optimizer = OptimizerFactory.buildOptimizer(optimizationStrategy, Integer.class, incrementSize,
		ConfigurationHelper.getInt(TableGenerator.INITIAL_PARAM, params, -1));
	//	optimizer = OptimizerFactory.buildOptimizer(optimizationStrategy, identifierType.getReturnedClass(), incrementSize,
	//		ConfigurationHelper.getInt(TableGenerator.INITIAL_PARAM, params, -1));
    }

    private SqlStatementLogger statementLogger = null;
    private SessionEventListenerManager statsCollector = null;

    @Override
    public Serializable generate(final SessionImplementor session, final Object obj) {

	this.statementLogger = session.getFactory().getServiceRegistry().getService(JdbcServices.class).getSqlStatementLogger();
	this.statsCollector = session.getEventListenerManager();
	return session.getTransactionCoordinator().createIsolationDelegate().delegateWork(new WorkExecutorVisitable<PkId>() {

	    @Override
	    public PkId accept(WorkExecutor<PkId> executor, Connection connection) throws SQLException {

		PkId newId = new PkId();
		try {
		    int prossimoValoreDaSequenza = prossimoValoreDaSequenza(connection);
		    newId.setCodice(prossimoValoreDaSequenza);
		} catch (SQLException e1) {
		    throw new PkIdGenerationException(e1);
		}
		return newId;
	    }
	}, true);
    }

    protected String buildSelectMaxQuery() {

	String tableName = segmentValue.substring(0, segmentValue.lastIndexOf("."));
	return "select max(" + segmentValue + ") from " + tableName + " where IDCOMUNE = ?";
    }

    protected String buildSelectExistRecordQuery() {

	String tableName = segmentValue.substring(0, segmentValue.lastIndexOf("."));
	return "select " + segmentValue + " from " + tableName + " where IDCOMUNE = ?  and " + segmentValue + "=?";
    }

    private int prossimoValoreDaSequenza(Connection conn) throws SQLException {

	int result;
	int rows;
	do {
	    //.. esiste la sequenza in sequence table per la tabella.id? e l'idcomune 
	    PreparedStatement selectPS = prepareStatement(conn, selectQuery);
	    try {
		selectPS.setString(1, ORMHelper.getIdcomune());
		selectPS.setString(2, segmentValue);
		ResultSet selectRS = executeResultSetStatement(selectPS); //selectPS.executeQuery();
		if (!selectRS.next()) {
		    // .. non esiste la sequenza allora faccio una Max della colonna sulla tabella
		    result = initialValue;
		    // la select max effettua una max per valori inferiori a MAX_HI_VALUE
		    PreparedStatement selectMaxPS = null;
		    selectMaxPS = prepareStatement(conn, selectMaxQuery);
		    selectMaxPS.setString(1, ORMHelper.getIdcomune());
		    ResultSet selectMaxRS = executeResultSetStatement(selectMaxPS); // selectMaxPS.executeQuery();
		    if (selectMaxRS.next()) {
			// .. assegno il valore alla variabile result
			result = selectMaxRS.getInt(1) + 1;
		    }
		    selectMaxRS.close();
		    selectMaxPS.close();
		    PreparedStatement insertPS = null;
		    try {
			// .. inserisco il valore della max in sequence table
			insertPS = prepareStatement(conn, insertQuery);
			insertPS.setString(1, ORMHelper.getIdcomune());
			insertPS.setString(2, segmentValue);
			insertPS.setLong(3, result);
			executeStatement(insertPS); //insertPS.execute();
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
		LOG.error("could not read or init a hi value", sqle);
		throw sqle;
	    } finally {
		selectPS.close();
	    }
	    PreparedStatement updatePS = prepareStatement(conn, updateQuery);
	    try {
		updatePS.setLong(1, result);
		updatePS.setString(2, segmentValue);
		updatePS.setString(3, ORMHelper.getIdcomune());
		rows = executeUpdateStatement(updatePS);// updatePS.executeUpdate();
		if (rows > 0) {
		    // il valore Ã¨ stato aggiornato e la sequenza Ã¨ stata strappata
		    // devo controllare che il record non esista giÃ  nel database
		    PreparedStatement selectExistsPS = prepareStatement(conn, selectExistsQuery);
		    selectExistsPS.setString(1, ORMHelper.getIdcomune());
		    selectExistsPS.setInt(2, result);
		    ResultSet selectExistsRS = executeResultSetStatement(selectExistsPS); //selectExistsPS.executeQuery();
		    if (selectExistsRS.next()) {
			// Il numero Ã¨ giÃ  usato da un'altro record e si tratta di un eventuale buco
			// passo al prossimo numero settando rows=0
			rows = 0;
		    }
		    selectExistsRS.close();
		    selectExistsPS.close();
		}
	    } catch (SQLException sqle) {
		LOG.error("could not updateQuery hi value in: " + segmentValue, sqle);
		throw sqle;
	    } finally {
		updatePS.close();
	    }
	} while (rows == 0);
	accessCount++;
	return result;
    }

    private PreparedStatement prepareStatement(Connection connection, String sql) throws SQLException {

	this.statementLogger.logStatement(sql, FormatStyle.BASIC.getFormatter());
	try {
	    this.statsCollector.jdbcPrepareStatementStart();
	    return connection.prepareStatement(sql);
	} finally {
	    this.statsCollector.jdbcPrepareStatementEnd();
	}
    }

    private ResultSet executeResultSetStatement(PreparedStatement preparedStatement) throws SQLException {

	// LOG.debugf("PkIdGenerator#executeStatement executing %s", preparedStatement);
	//	try {
	//	    this.statsCollector.jdbcExecuteStatementStart();
	return preparedStatement.executeQuery();
	//	} finally {
	//	    this.statsCollector.jdbcExecuteStatementEnd();
	//	}
    }

    private int executeUpdateStatement(PreparedStatement preparedStatement) throws SQLException {

	//	LOG.debugf("PkIdGenerator#executeUpdateStatement executing %s", preparedStatement);
	//	try {
	//	    this.statsCollector.jdbcExecuteStatementStart();
	return preparedStatement.executeUpdate();
	//	} finally {
	//	    this.statsCollector.jdbcExecuteStatementEnd();
	//	}
    }

    private boolean executeStatement(PreparedStatement preparedStatement) throws SQLException {

	//	LOG.debugf("PkIdGenerator#executeInsertStatement executing %s", preparedStatement);
	//	try {
	//	    this.statsCollector.jdbcExecuteStatementStart();
	return preparedStatement.execute();
	//	} finally {
	//	    this.statsCollector.jdbcExecuteStatementEnd();
	//	}
    }

    @Override
    public Object generatorKey() {

	return qualifiedTableName.render();
    }

    @Override
    public String[] sqlCreateStrings(Dialect dialect) throws HibernateException {

	return new String[] { dialect.getCreateTableString() + ' ' + renderedTableName + " ( IDCOMUNE, " + segmentColumnName + ' '
		+ dialect.getTypeName(Types.VARCHAR, segmentValueLength, 0, 0) + " not null " + ", " + valueColumnName + ' '
		+ dialect.getTypeName(Types.BIGINT) + ", primary key ( IDCOMUNE, " + segmentColumnName + " ) )" + dialect.getTableTypeString() };
    }

    @Override
    public String[] sqlDropStrings(Dialect dialect) throws HibernateException {

	return new String[] { dialect.getDropTableString(renderedTableName) };
    }

    @Override
    public void registerExportables(Database database) {

	final Dialect dialect = database.getJdbcEnvironment().getDialect();
	final Namespace namespace = database.locateNamespace(qualifiedTableName.getCatalogName(), qualifiedTableName.getSchemaName());
	Table table = namespace.locateTable(qualifiedTableName.getObjectName());
	if (table == null) {
	    table = namespace.createTable(qualifiedTableName.getObjectName(), false);
	    // todo : note sure the best solution here. do we add the columns if missing?
	    // other?
	    final Column segmentColumn = new ExportableColumn(database, table, segmentColumnName, StringType.INSTANCE,
		    dialect.getTypeName(Types.VARCHAR, segmentValueLength, 0, 0));
	    segmentColumn.setNullable(false);
	    table.addColumn(segmentColumn);
	    // lol
	    table.setPrimaryKey(new PrimaryKey(table));
	    table.getPrimaryKey().addColumn(segmentColumn);
	    final Column valueColumn = new ExportableColumn(database, table, valueColumnName, LongType.INSTANCE);
	    table.addColumn(valueColumn);
	}
	// allow physical naming strategies a chance to kick in
	this.renderedTableName = database.getJdbcEnvironment().getQualifiedObjectNameFormatter().format(table.getQualifiedTableName(), dialect);
	this.selectQuery = buildSelectQuery(dialect);
	this.updateQuery = buildUpdateQuery();
	this.insertQuery = buildInsertQuery();
	this.selectMaxQuery = buildSelectMaxQuery();
	this.selectExistsQuery = buildSelectExistRecordQuery();
    }
}
