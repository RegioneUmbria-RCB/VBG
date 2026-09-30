using log4net;
using PersonalLib2.Data.Metadata;
using PersonalLib2.Data.Providers;
using PersonalLib2.Data.V2;
using PersonalLib2.Data.V2.Legacy;
using PersonalLib2.Exceptions;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Utils;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.Reflection;

namespace PersonalLib2.Data
{

    public class DataBase : IDatabase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(DataBase));

        private readonly IDataProviderFactory _provider;
        private readonly SqlEngine _sqlEngine = null;


        /// <summary>
        /// Enumerazione utilizzata internamente alla classe per stabilire se la connessione è
        /// stata passata aperta o è stata aperta all'interno della classe.
        /// </summary>
        public enum OpenType
        {
            /// <summary>
            /// Indica che la connessione è stata aperta internamente alla classe.
            /// </summary>
            Open,
            /// <summary>
            /// Indica che la connessione è stata passata già aperta.
            /// </summary>
            AlreadyOpen
        }

        #region Costructors

        private readonly bool _traceSelect = false;

        public DataBase(String connectionString, ProviderType provider)
        {
            this.ConnectionDetails = new ConnectionDetails(connectionString, provider);

            this._provider = new DataProviderFactoryV2(provider);

            this.Connection = this._provider.CreateConnection(connectionString);

            if (this._provider.Specifics == null)
            {
                //Ricreo il provider dopo aver creato la connessione così viene creata sistematicamente
                //anche la proprietà _provider.specifics che per i provider OleDb non viene impostata 
                //con il costruttore DataProviderFactory(ProviderType)
                this._provider = new DataProviderFactory(this.Connection);
            }

            this._sqlEngine = new SqlEngine(this._provider);
            this._traceSelect = this._log.IsDebugEnabled;
        }
        /*
        public DataBase(IDbTransaction transaction)
        {
            this._provider = new DataProviderFactory(transaction.Connection);
            this.Transaction = transaction;
            this.Connection = transaction.Connection;
            this._sqlEngine = new SqlEngine(this._provider);

            this.ConnectionDetails = new ConnectionDetails(this.Connection.ConnectionString, this._provider.Provider);

            this._traceSelect = this._log.IsDebugEnabled;
        }
        */
        #endregion

        #region Properties

        /// <summary>
        /// Ottiene la connessione utilizzata dalla classe.
        /// </summary>
        public IDbConnection Connection { get; }

        public IDbTransaction Transaction { get; private set; }

        public ConnectionDetails ConnectionDetails { get; }

        public virtual bool IsInTransaction => this.Transaction != null;

        /// <summary>
        /// Ottiene le caratteristiche specifiche del provider istanziato con la connessione.
        /// Es. il nome dei parametri o alcune funzioni come UPPER per oracle.
        /// </summary>
        public IProvider Specifics => this._provider.Specifics;

        public string DBMSName => this._provider.Specifics.DBMSName().ToString();
        #endregion

        #region private
        private readonly Stack<OpenType> _stackAperture = new(); // Tiene traccia di tutte le connessioni che vengono aperte

        private void Open()
        {

            try
            {
                var tipoApertura = OpenType.AlreadyOpen;

                if (this.Connection.State == ConnectionState.Closed)
                {
                    //TODO: non appena è stato risolto il problema dell'utilizzo delle classi vb6 nei manager occorre aprire
                    //qui la transazione
                    if (String.IsNullOrEmpty(this.Connection.ConnectionString))
                        this.Connection.ConnectionString = this.ConnectionDetails.ConnectionString;

                    // this._log.Debug(this.Connection.ConnectionString);

                    this.Connection.Open();
                    tipoApertura = OpenType.Open;
                }

                this._stackAperture.Push(tipoApertura);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nell'apertura del database con connection string {0}: {1}", this.Connection.ConnectionString, ex);

                throw;
            }
        }

        private void Close()
        {
            var tipoApertura = this._stackAperture.Pop();

            if (this.Connection != null && tipoApertura == OpenType.Open)
            {
                this.Connection.Close();
            }
        }

        private void AddKeyIdentity(DataClass dataClass)
        {
            PropertyInfo keyIdentityProperty = null;
            var objType = dataClass.GetType();
            var targetClass = (DataClass)Activator.CreateInstance(objType);
            var properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);

            var keyFieldsProperties = new List<PropertyInfo>();
            var keyFieldsAttributes = new List<KeyFieldAttribute>();

            foreach (var property in properties)
            {
                var attributes = (KeyFieldAttribute[])property.GetCustomAttributes(typeof(KeyFieldAttribute), true);

                foreach (var attribute in attributes)
                {
                    if (attribute.KeyIdentity)
                    {
                        keyIdentityProperty = property;
                    }
                    else
                    {
                        property.SetValue(targetClass, property.GetValue(dataClass, null), null);

                        keyFieldsProperties.Add(property);
                        keyFieldsAttributes.Add(attribute);
                    }
                }
            }

            if (keyIdentityProperty != null)
            {
                var columnName = ((KeyFieldAttribute)keyIdentityProperty.GetCustomAttributes(typeof(KeyFieldAttribute), true)[0]).ColumnName;

                #region 12/07/2006 - Nicola: Creazione manuale della where per l'estrazione del campo identity
                // Creo la where manualmente per tutti i keyFields diversi da keyIdentityProperty. Questo per evitare
                // Il problema dei campi numerici o bool che vengono accodati nella condizione where da BuildQuery
                // IDbCommand command = _sqlEngine.BuildQuery(newDataClass);

                var query = $"Select ( {this._provider.Specifics.NvlFunction(this._provider.Specifics.MaxFunction(columnName), "0")} + 1 ) as {columnName} from {dataClass.DataTableName} ";

                using (var command = this.CreateCommand(query))
                {
                    var whereFilter = new List<string>();

                    for (var i = 0; i < keyFieldsProperties.Count; i++)
                    {
                        var property = keyFieldsProperties[i];
                        BaseFieldAttribute attribute = keyFieldsAttributes[i];

                        whereFilter.Add(this._sqlEngine.CreateWhereNoAnd(targetClass, null, attribute, property, command));
                    }

                    if (whereFilter.Count > 0)
                    {
                        query = $"{query} where {String.Join(" and ", whereFilter)}";
                    }

                    command.CommandText = query;

                    var identityValue = command.ExecuteScalar();
                    var propertyType = keyIdentityProperty.PropertyType;

                    if (propertyType.IsGenericType && propertyType.GetGenericTypeDefinition() == typeof(Nullable<>))
                    {
                        if (identityValue.GetType() != propertyType.GetGenericArguments()[0])
                        {
                            identityValue = Convert.ChangeType(identityValue, propertyType.GetGenericArguments()[0]);
                        }
                    }
                    else
                    {
                        identityValue = Convert.ChangeType(identityValue, propertyType);
                    }


                    keyIdentityProperty.SetValue(dataClass, identityValue, null);
                }

                #endregion
            }
        }

        #endregion

        #region Methods
        #region Gestione delle transazioni
        public virtual void BeginTransaction()
        {
            if (this.Transaction != null)
                throw new InvalidOperationException("Impossibile avviare una nuova transazione. Una transazione è già in corso.");

            if (this.Connection == null)
                throw new InvalidOperationException("Nessuna connessione associata all'oggetto.");

            this.Open();

            this.Transaction = this.Connection.BeginTransaction();
        }

        public virtual void CommitTransaction()
        {
            if (this.Transaction == null)
                throw new InvalidOperationException("Impossibile effettuare il commit di una transazione. Nessuna transazione in corso.");

            this.Transaction.Commit();
            this.Transaction = null;

            this.Close();
        }

        public virtual void RollbackTransaction()
        {
            if (this.Transaction == null)
                throw new InvalidOperationException("Impossibile effettuare il rollback di una transazione. Nessuna transazione in corso.");

            this.Transaction.Rollback();
            this.Transaction = null;

            this.Close();
        }

        #endregion



        /// <summary>
        /// Ritorna l'istanza di una singola classe in base ai parametri passati in DataClass.
        /// Se la query generata ritorna più di una riga solleva un eccezione. Se non è stato trovato alcun record ritorna null
        /// </summary>
        /// <param name="dataClass"><see cref="DataClass"/> contenente i parametri per effettuare la query</param>
        /// <returns>Istanza della classe corrispondente ai parametri passati o null se non è stata trovata alcuna riga nel db</returns>
        public T GetClass<T>(T dataClass) where T : DataClass, new()
        {
            var queryResult = this.GetClassList(dataClass, true);

            if (queryResult.Count == 0)
                return null;

            return queryResult[0];
        }

        public T GetClass<T>(IDbCommand cmd) where T : DataClass, new()
        {
            var l = this.GetClassList<T>(cmd);

            if (l.Count == 0)
                return null;

            return l[0];
        }



        /// <summary>
        /// Ritorna un ArrayList di DataClass. La dimensione dell'ArrayList è il numero di record letti.
        /// </summary>
        /// <param name="command">E' il comando che specifica la query da eseguire.</param>
        /// <param name="dataClass">E' il tipo di DataClass che deve popolare l'ArrayList di ritorno.</param>
        /// <param name="singleRowException">Se True allora va in exception se il command ritorna più di un record.</param>
        /// <returns>Un'ArrayList contenente tante classi DataClass quanti sono i record letti dal command.</returns>
        private List<T> GetClassList<T>(IDbCommand command, T dataClass, bool singleRowException) where T : DataClass, new()
        {
            var objType = dataClass.GetType();

            MetadataAnalyzer.Instance.EnsureAnalysisExistsFor(objType);

            var traceStart = DateTime.Now;

            if (this._traceSelect)
            {
                Debug.WriteLine("--------------------------------------------------------");
                Debug.WriteLine("GetClassList:");
                Debug.WriteLine(CommandTracer.Trace(command));
                Debug.WriteLine(Environment.NewLine);
            }

            try
            {
                var retVal = new List<T>();

                Debug.WriteLine($"Lettura classe {objType.Name}");

                using (var dr = command.ExecuteReader())
                {
                    var mappedProperties = MetadataStore.Instance.GetMetadata(objType).AssignableProperties;

                    var indices = Enumerable.Range(1, mappedProperties.Count).Select(x => -1).ToArray();

                    while (dr.Read())
                    {
                        var targetClass = (T)Activator.CreateInstance(objType);

                        //foreach (var prop in classProperties)
                        for (var i = 0; i < mappedProperties.Count; i++)
                        {
                            var prop = mappedProperties[i];
                            var currentProperty = prop.Property;
                            var customAttribute = prop.CustomAttribute;
                            var columnName = prop.ColumnName;

                            try
                            {

                                if (indices[i] == -1)
                                {
                                    indices[i] = dr.GetOrdinal(columnName);
                                    Debug.WriteLine(prop.ColumnName);
                                }

                                var val = dr.GetValue(indices[i]);

                                if (val == DBNull.Value || val == null)
                                {
                                    val = prop.ValoreDefault;
                                }

                                if (prop.CastDateToString && val != null)
                                {
                                    //se il campo del db NON è di tipo Date o DateTime e la proprietà è DateTime allora va parsato e convertito
                                    var formatExpression = customAttribute.DateFormat;
                                    val = DateTime.ParseExact(val.ToString(), formatExpression, null);
                                }

                                if (val != null)
                                {
                                    val = Convert.ChangeType(val, prop.TargetType);

                                    // Assegno il valore alla proprietà
                                    currentProperty.SetValue(targetClass, val, null);
                                }
                            }
                            catch (IndexOutOfRangeException)
                            {
                                //										Stefano Mendichi 12/07/2005
                                //										Commentata perchè nelle classi alcune proprietà potrebbero essere legate alle 
                                //										foreign key e non essendo stato fatto l'addForeignClause della classe tali proprietà 
                                //										non vengono estratte dalla query.
                                //										throw new DatabaseException("La colonna " + propName + "non esiste in IDataReader MyData. IDataReader MyData[" + propName + "]",  e );
                            }
                        }

                        retVal.Add(targetClass);
                    }

                    Debug.WriteLine(String.Format("{0} records letti", retVal.Count));
                }

                foreach (var targetClass in retVal)
                {
                    var en = dataClass.UseForeign;

                    switch (en)
                    {
                        case useForeignEnum.No:
                            break;
                        case useForeignEnum.Yes:
                            this.AddForeign(targetClass, false);
                            break;
                        case useForeignEnum.Recoursive:
                            this.AddForeign(targetClass, true);
                            break;
                    }
                }

                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message + "\r\n" + CommandTracer.Trace(command), ex);
            }
            finally
            {
                // this.Close(); //Chiude la connessione se è stata aperta internamente al metodo

                if (this._traceSelect)
                {
                    var traceEnd = DateTime.Now - traceStart;
                    Debug.Write("Tempo di esecuzione totale:");
                    Debug.Write(traceEnd.Milliseconds);
                    Debug.Write("ms");
                    Debug.WriteLine(Environment.NewLine);
                    Debug.WriteLine(Environment.NewLine);
                }
            }
        }

        public List<T> GetClassList<T>(FormattableString sql, GetClassListFlags flags = null) where T : DataClass, new()
        {
            var parNames = Enumerable.Range(0, sql.ArgumentCount)
                           .Select(i => $"p{i}")
                           .Select(x => new
                           {
                               QueryParameterName = this.Specifics.QueryParameterName(x),
                               ParameterName = x
                           })
                           .ToArray();

            var query = String.Format(sql.Format, parNames.Select(x => x.QueryParameterName).ToArray());

            using (var cmd = this.CreateCommand(query))
            {
                for (var i = 0; i < sql.ArgumentCount; i++)
                {
                    cmd.Parameters.Add(this.CreateParameter(parNames[i].ParameterName, sql.GetArgument(i)));
                }

                return this.GetClassList<T>(cmd, flags);
            }
        }

        public List<T> GetClassList<T>(IDbCommand cmd, GetClassListFlags flags = null) where T : DataClass, new()
        {

            try
            {
                this.Open();

                if (flags == null)
                {
                    flags = new GetClassListFlags(useForeignEnum.No);
                }
                var cls = new T() { UseForeign = flags.UseForeign };

                return this.GetClassList(cmd, cls, flags.SingleRowException);
            }
            finally
            {
                this.Close();
            }
        }

        /// <summary>
        /// Ritorna un ArrayList di DataClass del tipo passato.
        /// </summary>
        /// <param name="dataClass">E' la DataClass utilizzata per creare la query che deve leggere
        /// i dati dal database e contemporaneamente per popolare l'ArrayList tornato.</param>
        /// <param name="singleRowException">Se True allora va in exception se il command ritorna più di un record.</param>
        /// <returns>Un'ArrayList contenente tante classi DataClass quanti sono i record letti dal command.</returns>
        public List<T> GetClassList<T>(T dataClass, bool singleRowException = false) where T : DataClass, new()
        {
            return this.GetClassList(dataClass, null, singleRowException);
        }

        /// <summary>
        /// Ritorna un ArrayList di DataClass del tipo passato.
        /// </summary>
        /// <param name="dataClass">E' la DataClass utilizzata per creare la query che deve leggere
        /// i dati dal database e contemporaneamente per popolare l'ArrayList tornato.</param>
        /// <param name="dataClassCompare">E' la DataClass utilizzata per modificare l'attributo Compare (<see cref="BaseFieldAttribute"/>) della classe
        /// dataClass passata al parametro precedente.
        /// In dataClassCompare il valore delle proprietà BaseFieldAttribute non devono contenere il valore della colonna che rappresento
        /// ma l'operatore di confronto che sostituisce quello di default (attributo Compare).
        /// Es. dataClassCompare.CODICE="LIKE" confronta la colonna rappresentata da CODICE con il valore della proprietà dataClass.CODICE attraverso la LIKE specificata in dataClassCompare.</param>
        /// <param name="singleRowException">Se True allora va in exception se il command ritorna più di un record.</param>
        /// <returns>Un'ArrayList contenente tante classi DataClass quanti sono i record letti dal command.</returns>
        public List<T> GetClassList<T>(T dataClass, T dataClassCompare, bool singleRowException = false) where T : DataClass, new()
        {
            var closeConnection = false;

            if (!this.IsInTransaction)
            {
                this.Open();
                closeConnection = true;
            }

            try
            {
                using (var cmd = this.CreateCommand())
                {
                    this._sqlEngine.BuildQuery(dataClass, dataClassCompare, cmd);

                    return this.GetClassList(cmd, dataClass, singleRowException);
                }
            }
            finally
            {
                if (closeConnection)
                {
                    this.Close();
                }
            }
        }


        /// <summary>
        /// Popola, nella dataClass passata, il contenuto delle proprietà contrassegnate
        /// con l'attributo ForeignKeyAttribute.
        /// </summary>
        /// <param name="dataClassCollection">E' la collection di classi nelle quali devono essere popolate le proprietà con attributo
        /// ForeignKeyAttribute.</param>
        /// <param name="recursive">Se true viene eseguito il metodo per tutte le sottoclassi ricorsivamente.</param>
        private void AddForeign(List<DataClass> dataClassCollection, bool recursive = false)
        {
            for (var i = 0; i < dataClassCollection.Count; i++)
            {
                this.AddForeign(dataClassCollection[i], recursive);
            }
        }

        /// <summary>
        /// Popola, nella dataClass passata, il contenuto delle proprietà contrassegnate
        /// con l'attributo ForeignKeyAttribute.
        /// </summary>
        /// <param name="dataClass">E' la classe nella quale devono essere popolate le proprietà con attributo
        /// ForeignKeyAttribute.</param>
        /// <param name="recursive">Se true viene eseguito il metodo per tutte le sottoclassi ricorsivamente.</param>
        private void AddForeign(DataClass dataClass, bool recursive)
        {
            var objType = dataClass.GetType();

            var foreignKeys = MetadataStore.Instance.GetMetadata(objType).ForeignKeys;

            foreach (var fk in foreignKeys)
            {
                var dataClassProperty = fk.Property;
                var foreignKeyAttribute = fk.Attribute;

                var addForeign = true;

                var foreignDataClass = ForeignKeyAttribute.InstantiateDataClass(dataClassProperty);

                /*
                foreach (var mapping in fk.PropertyMappings)
                {
                    var foreignValue = mapping.SourceProperty.GetValue(dataClass, null);

                    if (DataFieldUtility.IsFieldEmpty(mapping.SourceProperty, foreignValue))
                    {
                        addForeign = false;
                        break;
                    }

                    try
                    {
                        var convertedForeignValue = mapping.ExtractForeignValue(foreignValue);

                        mapping.ForeignProperty.SetValue(foreignDataClass, convertedForeignValue, null);
                    }
                    catch (NullReferenceException)
                    {
                        throw new DataClassException("Non esiste una proprietà chiamata " + mapping.ForeignProperty.Name + " nella classe " + foreignDataClass.ToString());
                    }
                    catch (ArgumentException)
                    {
                        throw new DataClassException("Il valore contenuto nella proprietà " + objType.ToString() + "." + mapping.SourceProperty.Name + " non è convertibile nel tipo di dati " + mapping.ForeignProperty.PropertyType.ToString() + " della proprietà " + dataClassProperty.GetType().ToString() + "." + mapping.ForeignProperty.Name);
                    }
                }
                */

                //Se addForeign = false significa che una delle colonne che va in foreign non è impostata.
                if (addForeign)
                {
                    var cmd = fk.CreateSelectCommand(dataClass, this);
                    // TODO: Brutto, da sistemare
                    foreignDataClass.UseForeign = recursive ? useForeignEnum.Recoursive : useForeignEnum.No;
                    var classList = this.GetClassList(cmd, foreignDataClass, fk.KeyType == ForeignKeyType.OneToOne);

                    // OLD
                    // var classList = this.GetClassList(foreignDataClass, fk.KeyType == ForeignKeyType.OneToOne);

                    if (fk.KeyType == ForeignKeyType.OneToMany)  // La proprietà è una lista, aggiungo tutte le classi ritornate dalla query
                    {
                        var lista = (IList)dataClassProperty.GetValue(dataClass, null);

                        foreignKeyAttribute.CopyToList(lista, classList);
                    }
                    else    // La proprietà non è una lista, assegno solo il primo valore ritornato dalla query
                    {
                        if (classList.Count == 1)
                        {
                            dataClassProperty.SetValue(dataClass, classList[0], null);
                        }
                        else if (classList.Count > 1)
                        {
                            var fmtErr = "la risoluzione della foreign key associata alla proprietà {0} della classe {1} ha ritornato più di un record";
                            throw new InvalidOperationException(String.Format(fmtErr, dataClassProperty.Name, dataClass.GetType().Name));
                        }
                    }
                    /*
                    if (recursive)
                    {
                        //Per ogni proprietà foreign popolata viene invocato ricorsivamente il
                        //metodo AddForeign
                        this.AddForeign(classList, recursive);
                    }
                    */
                }
            }
        }

        /// <summary>
        /// Inserisce il contenuto della class dataClass nella tabella associata a dataClass,
        /// specificata nell'attributo DataTableAttribute.
        /// </summary>
        /// <param name="dataClass">E' la classe che descrive la tabella del database.</param>
        /// <returns>Il numero di record inseriti.</returns>
        public int Insert(DataClass dataClass)
        {
            IDbCommand command = null;
            try
            {
                this.Open();
                this.AddKeyIdentity(dataClass);
                command = this._sqlEngine.buildInsert(dataClass);
                command.Transaction = this.Transaction;
                command.Connection = this.Connection;

                var row = command.ExecuteNonQuery();
                command.Dispose();

                return row;
            }
            catch (Exception ex)
            {
                if (command != null)
                {
                    throw new DatabaseException(CommandTracer.Trace(command), ex);
                }
                else
                {
                    throw new DatabaseException(ex);
                }
            }
            finally
            {
                this.Close(); //Chiuse la connessione se è stata aperta internamente al metodo
            }
        }

        /// <summary>
        /// Aggiorna il contenuto della tabella associata a dataClass,
        /// specificata nell'attributo DataTableAttribute.
        /// L'aggiornamento avviene per chiave primaria quindi una classe aggiorna un solo record.
        /// Le colonne del database aggiornate sono tutte quelle 
        /// </summary>
        /// <param name="dataClass"></param>
        /// <returns></returns>
        public int Update(DataClass dataClass)
        {
            IDbCommand command = null;
            try
            {
                this.Open();
                command = this._sqlEngine.buildUpdate(dataClass);
                command.Transaction = this.Transaction;
                command.Connection = this.Connection;

                var row = command.ExecuteNonQuery();
                command.Dispose();


                return row;
            }
            catch (Exception ex)
            {
                throw new DatabaseException(CommandTracer.Trace(command), ex);
            }
            finally
            {
                this.Close(); //Chiuse la connessione se è stata aperta internamente al metodo
            }
        }

        /// <summary>
        /// Cancalla un record nel DB utilizzando la chiave primaria impostata in dataClass.
        /// </summary>
        /// <param name="dataClass">E' la classe che descrive il DB.</param>
        /// <returns>
        /// Un intero con il numero di record cancellati: 1 o 0.
        /// Non può cancellare più di un record se la chiave primaria definita in 
        /// dataClass è quella del DB.
        /// </returns>
        public int Delete(DataClass dataClass)
        {
            IDbCommand command = null;
            try
            {
                this.Open();
                command = this._sqlEngine.buildDelete(dataClass);
                command.Transaction = this.Transaction;
                command.Connection = this.Connection;

                var row = command.ExecuteNonQuery();
                command.Dispose();

                return row;
            }
            catch (Exception ex)
            {
                if (command != null)
                {
                    throw new DatabaseException(CommandTracer.Trace(command), ex);
                }
                else
                {
                    throw new DatabaseException(ex);
                }
            }
            finally
            {
                this.Close(); //Chiuse la connessione se è stata aperta internamente al metodo
            }
        }


        /// <summary>
        /// Crea un IDbCommand con la connessione e la transazione specificate nell'oggetto DataBase.
        /// </summary>
        /// <returns></returns>
        public IDbCommand CreateCommand()
        {
            var cnn = this.Connection;

            if (cnn == null && this.Transaction != null)
            {
                cnn = this.Transaction.Connection;
            }

            var cmd = cnn.CreateCommand();
            cmd.Connection = this.Connection;
            cmd.Transaction = this.Transaction;

            return cmd;
        }


        /// <summary>
        /// Crea un IDbCommand con la connessione e la transazione specificate nell'oggetto DataBase.
        /// </summary>
        /// <param name="dataClass">E' la classe con cui creare il command.</param>
        /// <returns></returns>
        private IDbCommand CreateCommand(DataClass dataClass, DataClass dataClassCompare)
        {
            var command = this._sqlEngine.BuildQuery(dataClass, dataClassCompare);
            command.Connection = this.Connection;
            command.Transaction = this.Transaction;
            return command;
        }

        /// <summary>
        /// Crea un IDbCommand con la connessione e la transazione specificate nell'oggetto DataBase.
        /// </summary>
        /// <param name="commandText">IDbCommadn.commandText.</param>
        /// <returns></returns>
        public IDbCommand CreateCommand(string commandText)
        {
            var cmd = this.CreateCommand();

            cmd.CommandText = commandText;

            return cmd; // _provider.CreateCommand(commandText, this._dbConnection, this._dbTransaction);
        }

        public IDbCommand CreateCommand(DataClass dataClass)
        {
            return this.CreateCommand(dataClass, null);
        }

        public IDbDataAdapter CreateDataAdapter(IDbCommand command)
        {
            var adapter = this._provider.CreateDataAdapter(command);
            return adapter;
        }

        #endregion

        /// <summary>
        /// Crea un parametro che può essere utilizzato in una query parametrica.
        /// Il nome del parametro viene automaticamente convertito in base al formato richiesto dal db
        /// </summary>
        /// <param name="name">Nome del parametro</param>
        /// <param name="value">Valore del parametro</param>
        /// <returns>Parametro</returns>
        public IDbDataParameter CreateParameter(string name, object value)
        {
            var par = this._provider.CreateDataParameter(this.Specifics.ParameterName(name), value);
            return par;
        }


        #region IDisposable

        public event EventHandler Disposed;

        public void Dispose()
        {
            while (this._stackAperture.Count > 0)
                this.Close();

            if (this.Transaction == null)
                this.Connection.Dispose();

            if (Disposed != null)
                Disposed(this, EventArgs.Empty);
        }

        #endregion

        #region Estensioni per gestione delle operazioni più comuni

        public virtual IEnumerable<T> ExecuteReader<T>(FormattableString sql, Func<IDataReader, T> mapItem)
        {
            var parNames = Enumerable.Range(0, sql.ArgumentCount)
                                       .Select(i => $"p{i}")
                                       .Select(x => new
                                       {
                                           QueryParameterName = this.Specifics.QueryParameterName(x),
                                           ParameterName = x
                                       })
                                       .ToArray();

            var query = String.Format(sql.Format, parNames.Select(x => x.QueryParameterName).ToArray());

            return this.ExecuteReader<T>(query, (f) =>
            {
                for (var i = 0; i < sql.ArgumentCount; i++)
                {
                    f.Add(parNames[i].ParameterName, sql.GetArgument(i));
                }
            }, mapItem);

        }

        // Virtuale per semplificare l'utilizzo nei test
        public virtual IEnumerable<T> ExecuteReader<T>(string sql, Action<ICommandParameterFactory> callback, Func<IDataReader, T> mapItem)
        {
            var parametersfactory = new DatabaseCommandParameterFactory(this);

            if (callback != null)
            {
                callback(parametersfactory);
            }

            var closeConnection = false;

            if (this.Connection.State == ConnectionState.Closed)
            {
                this.Connection.Open();
                closeConnection = true;
            }

            try
            {
                var parameterNames = parametersfactory.GetParameters()
                                                       .Select(x => x.ParameterName)
                                                       .ToArray();

                sql = String.Format(sql, parameterNames);

                Debug.WriteLine("-----------------------------------------------");
                Debug.WriteLine("Query:");
                Debug.WriteLine(sql);
                Debug.WriteLine("Parameters:");

                using (var cmd = this.Connection.CreateCommand())
                {
                    cmd.Connection = this.Connection;
                    cmd.Transaction = this.Transaction;
                    cmd.CommandText = sql;

                    foreach (var parameter in parametersfactory.GetParameters())
                    {
                        cmd.Parameters.Add(parameter);

                        Debug.WriteLine($"- {parameter.ParameterName}={parameter.Value}");
                    }

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new List<T>();

                        while (dr.Read())
                        {
                            rVal.Add(mapItem(dr));
                        }

                        return rVal;
                    }
                }
            }
            finally
            {
                if (closeConnection)
                {
                    this.Connection.Close();
                }

                Debug.WriteLine("");
            }
        }

        // Virtuale per semplificare l'utilizzo nei test
        public virtual T ExecuteScalar<T>(string sql, T defaultValue, Action<ICommandParameterFactory> callback)
        {
            var parametersfactory = new DatabaseCommandParameterFactory(this);

            if (callback != null)
            {
                callback(parametersfactory);
            }

            var closeConnection = false;

            if (this.Connection.State == ConnectionState.Closed)
            {
                this.Connection.Open();
                closeConnection = true;
            }

            try
            {
                var parameterNames = parametersfactory.GetParameters()
                                                       .Select(x => x.ParameterName)
                                                       .ToArray();

                sql = String.Format(sql, parameterNames);

                using (var cmd = this.CreateCommand(sql))
                {
                    foreach (var parameter in parametersfactory.GetParameters())
                    {
                        cmd.Parameters.Add(parameter);
                    }

                    var obj = cmd.ExecuteScalar();

                    if (obj == null || obj == DBNull.Value)
                    {
                        return defaultValue;
                    }

                    return (T)Convert.ChangeType(obj, typeof(T));
                }
            }
            finally
            {
                if (closeConnection)
                {
                    this.Connection.Close();
                }
            }
        }

        public virtual T ExecuteScalar<T>(FormattableString sql, T defaultValue)
        {
            var parNames = Enumerable.Range(0, sql.ArgumentCount)
                                       .Select(i => $"p{i}")
                                       .Select(x => new
                                       {
                                           QueryParameterName = this.Specifics.QueryParameterName(x),
                                           ParameterName = x
                                       })
                                       .ToArray();
            var query = String.Format(sql.Format, parNames.Select(x => x.QueryParameterName).ToArray());

            return this.ExecuteScalar<T>(query, defaultValue, (f) =>
            {
                for (var i = 0; i < sql.ArgumentCount; i++)
                {
                    f.Add(parNames[i].ParameterName, sql.GetArgument(i));
                }
            });
        }



        // Virtuale per semplificare l'utilizzo nei test
        public virtual int ExecuteNonQuery(string sql, Action<ICommandParameterFactory> callback)
        {
            var parametersfactory = new DatabaseCommandParameterFactory(this);

            if (callback != null)
            {
                callback(parametersfactory);
            }

            var closeConnection = false;

            if (this.Connection.State == ConnectionState.Closed)
            {
                this.Connection.Open();
                closeConnection = true;
            }

            try
            {
                var parameterNames = parametersfactory.GetParameters()
                                                       .Select(x => x.ParameterName)
                                                       .ToArray();

                sql = String.Format(sql, parameterNames);

                using (var cmd = this.CreateCommand(sql))
                {
                    foreach (var parameter in parametersfactory.GetParameters())
                    {
                        cmd.Parameters.Add(parameter);
                    }

                    return cmd.ExecuteNonQuery();
                }
            }
            finally
            {
                if (closeConnection)
                {
                    this.Connection.Close();
                }
            }
        }

        public virtual int ExecuteNonQuery(FormattableString sql)
        {
            var parNames = Enumerable.Range(0, sql.ArgumentCount)
                                       .Select(i => $"p{i}")
                                       .Select(x => new
                                       {
                                           QueryParameterName = this.Specifics.QueryParameterName(x),
                                           ParameterName = x
                                       })
                                       .ToArray();
            var query = String.Format(sql.Format, parNames.Select(x => x.QueryParameterName).ToArray());
            return this.ExecuteNonQuery(query, (f) =>
            {
                for (var i = 0; i < sql.ArgumentCount; i++)
                {
                    f.Add(parNames[i].ParameterName, sql.GetArgument(i));
                }
            });
        }


        public string QueryParameter(string parameterName)
        {
            return this.Specifics.QueryParameterName(parameterName);
        }

        #endregion
    }

    public enum Provider { ORACLE, SQL, MYSQL, POSTGRESQL }
}