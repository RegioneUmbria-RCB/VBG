using System;
using System.Collections;
using System.Data;
using System.Diagnostics;
using System.Reflection;
using System.Text;
using PersonalLib2.Exceptions;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql.Collections;
using PersonalLib2.TypeTools;
using PersonalLib2.Data.Providers;
using PersonalLib2.Utils;
using System.ComponentModel;
using System.Collections.Generic;
using System.Configuration;
using PersonalLib2.Properties;


namespace PersonalLib2.Data
{
	/// <summary>
	/// Espone i dettagli della connessione come il tipo di provider e la connectionstring e le funzioni specifiche del provider.
	/// </summary>
	public class ConnectionDetails
	{
		private string m_connectionString;
		private ProviderType m_providertype;
		private string m_token = null;

		public string ConnectionString
		{
			get { return m_connectionString; }
		}

		public ProviderType ProviderType
		{
			get { return m_providertype; }
		}

		public string Token
		{
			get { return m_token == null ? String.Empty : m_token; }
			set { m_token = value; }
		}

		internal ConnectionDetails(string connectionString, ProviderType providertype)
		{
			m_connectionString = connectionString;
			m_providertype = providertype;
		}
	}


	/// <summary>
	/// 
	/// </summary>
	public class DataBase : IDisposable
	{
#if TRACCIACONNESSIONIAPERTE
		static List<IDbConnection> m_connectionList = null;
		public static void StoreConnection(IDbConnection cnn)
		{
			if (m_connectionList == null)
				m_connectionList = new List<IDbConnection>();
			m_connectionList.Add(cnn);
		}

		public static string DumpConnections()
		{
			if (m_connectionList == null) return "Nessuna connessione aperta";

			Debug.WriteLine("------------------------------STATO DELLE CONNESSIONI----------------------");

			StringBuilder gsb = new StringBuilder();

			for (int i = 0; i < m_connectionList.Count; i++)
			{
				StringBuilder sb = new StringBuilder();
				sb.Append(i);
				sb.Append("\t");
				sb.Append(m_connectionList[i].ConnectionString);
				sb.Append("\tStato: ");

				if (m_connectionList[i].State == ConnectionState.Open)
				{
					sb.Append("<span style='color:#f00'>Connessione aperta</span>");
				}
				else
				{
					sb.Append(m_connectionList[i].State.ToString());
				}
				

				Debug.WriteLine(sb.ToString());

				gsb.Append( sb.ToString() ).Append(Environment.NewLine);
			}

			m_connectionList.Clear();

			return gsb.ToString();
		}
#endif


		#region Eventi
		public delegate void DbEventDelegate(object sender, DbEventArgs e);
		protected event DbEventDelegate OnInsert;
		protected event DbEventDelegate OnUpdate;
		protected event DbEventDelegate OnDelete;

		public void SetInsertDelegate(DbEventDelegate evtDelegate)
		{
			if (this.OnInsert == null)
				this.OnInsert = evtDelegate;
		}

		public void SetUpdateDelegate(DbEventDelegate evtDelegate)
		{
			if (this.OnUpdate == null)
				this.OnUpdate = evtDelegate;
		}

		public void SetDeleteDelegate(DbEventDelegate evtDelegate)
		{
			if (this.OnDelete == null)
				this.OnDelete = evtDelegate;
		}

		#endregion


		private IDbConnection _dbConnection;
		private IDbTransaction _dbTransaction = null;
		private DataProviderFactory _provider;
		private SqlEngine _sqlEngine = null;
		private JoinType _join = JoinType.SqlServerAndOracle9_Join;
		private ConnectionDetails _connectionDetails;


		/// <summary>
		/// Enumerazione utilizzata internamente alla classe per stabilire se la connessione è
		/// stata passata aperta o è stata aperta all'interno della classe.
		/// </summary>
		public enum openType
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

		public DataBase(String connectionString, ProviderType provider)
		{
			_connectionDetails = new ConnectionDetails(connectionString, provider);

			_provider = new DataProviderFactory(provider);

			_dbConnection = _provider.CreateConnection(connectionString);
#if TRACCIACONNESSIONIAPERTE			
			StoreConnection(_dbConnection);
#endif
			if (_provider.Specifics == null)
			{
				//Ricreo il provider dopo aver creato la connessione così viene creata sistematicamente
				//anche la proprietà _provider.specifics che per i provider OleDb non viene impostata 
				//con il costruttore DataProviderFactory(ProviderType)
				_provider = new DataProviderFactory(_dbConnection);
			}

			_sqlEngine = new SqlEngine(_provider);
		}

		public DataBase(IDbTransaction transaction)
		{
			_provider = new DataProviderFactory(transaction.Connection);
			_dbTransaction = transaction;
			_dbConnection = transaction.Connection;
			_sqlEngine = new SqlEngine(_provider);

			_connectionDetails = new ConnectionDetails(_dbConnection.ConnectionString, _provider.Provider);
		}

		#endregion

		#region Properties

		/// <summary>
		/// Specifica il tipo di Join che il DataBase supporta;
		/// </summary>
		public JoinType Join
		{
			get { return _join; }
			set { _join = value; }
		}

		/// <summary>
		/// Ottiene la connessione utilizzata dalla classe.
		/// </summary>
		public IDbConnection Connection
		{
			get { return _dbConnection; }
		}

		public IDbTransaction Transaction
		{
			get { return _dbTransaction; }
		}

		public ConnectionDetails ConnectionDetails
		{
			get { return _connectionDetails; }
		}

		public bool IsInTransaction
		{
			get
			{
				if (_dbTransaction != null)
					return true;
				else
					return false;
			}
		}

		/// <summary>
		/// Ottiene le caratteristiche specifiche del provider istanziato con la connessione.
		/// Es. il nome dei parametri o alcune funzioni come UPPER per oracle.
		/// </summary>
		public IProvider Specifics
		{
			get { return _provider.Specifics; }
		}

		public string DBMSName
		{
			get
			{
				return _provider.Specifics.DBMSName().ToString();
			}
		}
		#endregion

		#region private
		Stack<openType> m_stackAperture = new Stack<openType>();	// Tiene traccia di tutte le connessioni che vengono aperte

		private void Open()
		{
			openType tipoApertura = openType.AlreadyOpen;

			if (_dbConnection.State == ConnectionState.Closed)
			{
				//TODO: non appena è stato risolto il problema dell'utilizzo delle classi vb6 nei manager occorre aprire
				//qui la transazione
				if (String.IsNullOrEmpty(_dbConnection.ConnectionString))
					_dbConnection.ConnectionString = _connectionDetails.ConnectionString;

				_dbConnection.Open();
				tipoApertura = openType.Open;
			}

			m_stackAperture.Push(tipoApertura);
		}

		private void Close()
		{
			openType tipoApertura = m_stackAperture.Pop();

			if (_dbConnection != null && tipoApertura == openType.Open)
			{
				_dbConnection.Close();
			}
		}


		private void AddKeyIdentity(DataClass dataClass)
		{
			PropertyInfo keyIdentityProperty = null;
			Type objType = dataClass.GetType();
			DataClass newDataClass = (DataClass)Activator.CreateInstance(objType);
			PropertyInfo[] properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);

			ArrayList keyFieldsProperties = new ArrayList();
			ArrayList keyFieldsAttributes = new ArrayList();

			foreach (PropertyInfo property in properties)
			{
				KeyFieldAttribute[] fields = (KeyFieldAttribute[])property.GetCustomAttributes(typeof(KeyFieldAttribute), true);
				if (fields.Length > 0)
				{
					foreach (KeyFieldAttribute field in fields)
					{
						if (field.KeyIdentity)
						{
							keyIdentityProperty = property;
						}
						else
						{
							property.SetValue(newDataClass, property.GetValue(dataClass, null), null);

							keyFieldsProperties.Add(property);
							keyFieldsAttributes.Add(field);
						}
					}
				}
			}

			if (keyIdentityProperty != null)
			{
				string columnName = ((KeyFieldAttribute)keyIdentityProperty.GetCustomAttributes(typeof(KeyFieldAttribute), true)[0]).ColumnName;

				#region 12/07/2006 - Nicola: Creazione manuale della where per l'estrazione del campo identity
				// Creo la where manualmente per tutti i keyFields diversi da keyIdentityProperty. Questo per evitare
				// Il problema dei campi numerici o bool che vengono accodati nella condizione where da BuildQuery
				// IDbCommand command = _sqlEngine.BuildQuery(newDataClass);

				#region vecchio codice
				//				IDbCommand command = _sqlEngine.BuildQuery(newDataClass);
				//				string query = command.CommandText;
				//				query = query.Substring(query.IndexOf("FROM"));
				//				query = "Select (" + _provider.Specifics.NvlFunction(_provider.Specifics.MaxFunction(columnName),0) + " + 1) as " + columnName + " " + query;
				//				command.CommandText = query;
				//				newDataClass = GetClassList(command, newDataClass, true, false)[0];
				//				keyIdentityProperty.SetValue(dataClass, keyIdentityProperty.GetValue(newDataClass, null), null);
				//				command.Dispose();
				#endregion

				string fmtString = "Select ( {0} + 1 ) as {1} from {2} ";

				string query = String.Format(fmtString, _provider.Specifics.NvlFunction(_provider.Specifics.MaxFunction(columnName), "0"),
					columnName,
					dataClass.DataTableName);//  "Select (" + _provider.Specifics.NvlFunction(_provider.Specifics.MaxFunction(columnName),0) + " + 1) as " + columnName + " From " + dataClass.DataTableName + " ";

				using (IDbCommand command = CreateCommand(query))
				{
					string whereFilter = String.Empty;

					if (keyFieldsProperties.Count != 0)
					{
						for (int i = 0; i < keyFieldsProperties.Count; i++)
						{
							PropertyInfo property = (PropertyInfo)keyFieldsProperties[i];
							BaseFieldAttribute attribute = (BaseFieldAttribute)keyFieldsAttributes[i];

							whereFilter += _sqlEngine.CreateWhere(newDataClass, null, attribute, property, command);
						}
					}

					if (whereFilter.Length > 0)
					{
						query += " where ";
						whereFilter = whereFilter.Substring(0, whereFilter.Length - 5);
					}

					command.CommandText = query + whereFilter;

					object identityValue = command.ExecuteScalar();

					if (keyIdentityProperty.PropertyType.IsGenericType && keyIdentityProperty.PropertyType.GetGenericTypeDefinition() == typeof(Nullable<>))
					{
						if (identityValue.GetType() != keyIdentityProperty.PropertyType.GetGenericArguments()[0])
						{
							identityValue = Convert.ChangeType(identityValue, keyIdentityProperty.PropertyType.GetGenericArguments()[0]);
						}
					}
					else
					{
						identityValue = Convert.ChangeType(identityValue, keyIdentityProperty.PropertyType);
					}


					keyIdentityProperty.SetValue(dataClass, Convert.ChangeType(identityValue, keyIdentityProperty.PropertyType), null);
				}

				#endregion
			}
		}

		#endregion

		#region Methods
		#region Gestione delle transazioni
		public void BeginTransaction()
		{
			if (_dbTransaction != null)
				throw new InvalidOperationException("Impossibile avviare una nuova transazione. Una transazione è già in corso.");

			if (_dbConnection == null)
				throw new InvalidOperationException("Nessuna connessione associata all'oggetto.");

			this.Open();

			_dbTransaction = _dbConnection.BeginTransaction();
		}

		public void CommitTransaction()
		{
			if (_dbTransaction == null)
				throw new InvalidOperationException("Impossibile effettuare il commit di una transazione. Nessuna transazione in corso.");

			_dbTransaction.Commit();
			_dbTransaction = null;

			this.Close();
		}

		public void RollbackTransaction()
		{
			if (_dbTransaction == null)
				throw new InvalidOperationException("Impossibile effettuare il rollback di una transazione. Nessuna transazione in corso.");

			_dbTransaction.Rollback();
			_dbTransaction = null;

			this.Close();
		}

		#endregion



		/// <summary>
		/// Ritorna l'istanza di una singola classe in base ai parametri passati in DataClass.
		/// Se la query generata ritorna più di una riga solleva un eccezione. Se non è stato trovato alcun record ritorna null
		/// </summary>
		/// <param name="dataClass"><see cref="DataClass"/> contenente i parametri per effettuare la query</param>
		/// <returns>Istanza della classe corrispondente ai parametri passati o null se non è stata trovata alcuna riga nel db</returns>
		public DataClass GetClass(DataClass dataClass)
		{
			DataClassCollection queryResult = GetClassList(dataClass, true, false);

			if (queryResult.Count == 0)
				return null;

			return queryResult[0];
		}

		public T GetClass<T>(IDbCommand cmd) where T: DataClass, new()
		{
			var l = GetClassList<T>(cmd);

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
		/// <param name="ignoreSetMode">Nelle proprietà della classe di tipo DataClass passata è presente l'attributo
		/// SetMode (<see cref="PersonalLib2.Sql.Attributes.SetType"/>) che indica, se SetType.None o SetType.SetFromControl,
		/// che la rispettiva proprièta non deve essere impostata dalle letture dal database e quindi da questo metodo.
		/// Se True allora questa impostazione delle classe per l'attuale esecuzione del metodo deve essere ignorata.
		/// </param>
		/// <returns>Un'ArrayList contenente tante classi DataClass quanti sono i record letti dal command.</returns>
		public DataClassCollection GetClassList(IDbCommand command, DataClass dataClass, bool singleRowException, bool ignoreSetMode)
		{
			this.Open();

			DateTime traceStart = DateTime.Now;
			bool traceSelect = Settings.Default.TraceSelect;

			if (traceSelect)
			{
				Debug.WriteLine("--------------------------------------------------------");
				Debug.WriteLine("GetClassList:");
				Debug.WriteLine(CommandTracer.Trace(command));
				Debug.WriteLine(Environment.NewLine);
			}

			try
			{
				Type objType = dataClass.GetType();
				command.Transaction = _dbTransaction;
				command.Connection = _dbConnection;


				IDbDataAdapter dataAdapter = _provider.CreateDataAdapter(command);
				DataSet myDataSet = new DataSet();
				dataAdapter.Fill(myDataSet);

				if (singleRowException && myDataSet.Tables[0].Rows.Count > 1) throw new SingleRowException();


				DataClassCollection retVal = new DataClassCollection();
				PropertyInfo[] classProperties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);


				foreach (DataRow dr in myDataSet.Tables[0].Rows)
				{
					DataClass targetClass = (DataClass)Activator.CreateInstance(objType);

					for (int i = 0; i < classProperties.Length; i++)
					{
						PropertyInfo currentProperty = classProperties[i];

						// Leggo gli attributi custom
						BaseFieldAttribute[] customAttributes = (BaseFieldAttribute[])currentProperty.GetCustomAttributes(typeof(BaseFieldAttribute), true);

						// Una proprietà non può essere decorata da più di un attributo
						if (customAttributes.Length > 1)
							throw new InvalidOperationException("Alla proprietà " + currentProperty.Name + " è associato più di un attributo di tipo BaseFieldAttribute");

						if (customAttributes.Length != 1) continue;

						// Assegno il valore alla proprietà marcata con l'attributo
						if (ignoreSetMode || customAttributes[0].SetMode == SetType.Always || customAttributes[0].SetMode == SetType.SetFromDB)
						{
							string dbColumnName = customAttributes[0].ColumnName;

							// se il nome della colonna è specificato nel formato TABELLA.COLONNA prendo solo il nome della colonna
							int pos = dbColumnName.IndexOf(".");
							if (pos > 0)
								dbColumnName = dbColumnName.Substring(pos + 1);

							try
							{
								object val = dr[dbColumnName];

								if (val == DBNull.Value || val == null)
									val = Sql.DataFieldUtility.ValoreDefault(classProperties[i].PropertyType);

								bool campoData = (classProperties[i].PropertyType == typeof(DateTime) || classProperties[i].PropertyType == typeof(DateTime?));

								if (campoData && !(customAttributes[0].Type == DbType.Date || customAttributes[0].Type == DbType.DateTime) && val != null)
								{
									//se il campo del db NON è di tipo Date o DateTime e la proprietà è DateTime allora va parsato e convertito
									string formatExpression = customAttributes[0].DateFormat;
									val = DateTime.ParseExact(val.ToString(), formatExpression, null);
								}

								if (val != null)
								{
									if (classProperties[i].PropertyType.IsGenericType && classProperties[i].PropertyType.GetGenericTypeDefinition() == typeof(Nullable<>))
									{
										if (val.GetType() != classProperties[i].PropertyType.GetGenericArguments()[0])
										{
											val = Convert.ChangeType(val, classProperties[i].PropertyType.GetGenericArguments()[0]);
										}
									}
									else
									{
										val = Convert.ChangeType(val, classProperties[i].PropertyType);
									}


									// Assegno il valore alla proprietà
									classProperties[i].SetValue(targetClass, val, null);
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
					}

					// Gestione delle foreign keys
					PropertyInfo property = objType.GetProperty("UseForeign");
					object obj = property.GetValue(dataClass, null);

					useForeignEnum en = (useForeignEnum)obj;

					switch (en)
					{
						case useForeignEnum.No:
							break;
						case useForeignEnum.Yes:
							AddForeign(targetClass, false);
							break;
						case useForeignEnum.Recoursive:
							AddForeign(targetClass, true);
							break;
					}

					retVal.Add(targetClass);

				}

				return retVal;
			}
			catch (Exception ex)
			{
				throw new DatabaseException(CommandTracer.Trace(command), ex);
			}
			finally
			{
				this.Close(); //Chiude la connessione se è stata aperta internamente al metodo

				if (traceSelect)
				{
					TimeSpan traceEnd = DateTime.Now - traceStart;
					Debug.Write("Tempo di esecuzione totale:");
					Debug.Write(traceEnd.Milliseconds);
					Debug.Write("ms");
					Debug.WriteLine(Environment.NewLine);
					Debug.WriteLine(Environment.NewLine);
				}
			}
		}





        public List<T> GetClassList<T>(IDbCommand cmd, GetClassListFlags flags = null) where T : DataClass, new()
        {
            if (flags == null)
            {
                flags = new GetClassListFlags(useForeignEnum.No);
            }
            var cls = new T() { UseForeign = flags.UseForeign };

            var coll = GetClassList(cmd, cls, flags.SingleRowException, flags.IgnoreSetMode);

            return coll.ToList<T>();
        }


		public DataClassCollection GetClassList(DataClass dataClass)
		{
			using (IDbCommand command = _sqlEngine.BuildQuery(dataClass))
				return GetClassList(command, dataClass, false, false);
		}
		/// <summary>
		/// Ritorna un ArrayList di DataClass del tipo passato.
		/// </summary>
		/// <param name="dataClass">E' la DataClass utilizzata per creare la query che deve leggere
		/// i dati dal database e contemporaneamente per popolare l'ArrayList tornato.</param>
		/// <param name="singleRowException">Se True allora va in exception se il command ritorna più di un record.</param>
		/// <param name="ignoreSetMode">Nelle proprietà della classe di tipo DataClass passata è presente l'attributo
		/// SetMode (<see cref="PersonalLib2.Sql.Attributes.SetType"/>) che indica, se SetType.None o SetType.SetFromControl,
		/// che la rispettiva proprièta non deve essere impostata dalle letture dal database e quindi da questo metodo.
		/// Se True allora questa impostazione delle classe per l'attuale esecuzione del metodo deve essere ignorata.
		/// </param>
		/// <returns>Un'ArrayList contenente tante classi DataClass quanti sono i record letti dal command.</returns>
		public DataClassCollection GetClassList(DataClass dataClass, bool singleRowException, bool ignoreSetMode)
		{
			using (IDbCommand command = _sqlEngine.BuildQuery(dataClass))
				return GetClassList(command, dataClass, singleRowException, ignoreSetMode);
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
		/// <param name="ignoreSetMode">Nelle proprietà della classe di tipo DataClass passata è presente l'attributo
		/// SetMode (<see cref="PersonalLib2.Sql.Attributes.SetType"/>) che indica, se SetType.None o SetType.SetFromControl,
		/// che la rispettiva proprièta non deve essere impostata dalle letture dal database e quindi da questo metodo.
		/// Se True allora questa impostazione delle classe per l'attuale esecuzione del metodo deve essere ignorata.
		/// </param>
		/// <returns>Un'ArrayList contenente tante classi DataClass quanti sono i record letti dal command.</returns>
		public DataClassCollection GetClassList(DataClass dataClass, DataClass dataClassCompare, bool singleRowException, bool ignoreSetMode)
		{
			using (IDbCommand command = _sqlEngine.BuildQuery(dataClass, dataClassCompare))
				return GetClassList(command, dataClass, singleRowException, ignoreSetMode);
		}


		/// <summary>
		/// Popola, nella dataClass passata, il contenuto delle proprietà contrassegnate
		/// con l'attributo ForeignKeyAttribute.
		/// Questa operazione NON avviene ricorsivamente per tutte le sottoclassi.
		/// </summary>
		/// <param name="dataClassCollection">E' la collection di classi nelle quali devono essere popolate le proprietà con attributo
		/// ForeignKeyAttribute.</param>
		public void AddForeign(DataClassCollection dataClassCollection)
		{
			AddForeign(dataClassCollection, false);
		}

		/// <summary>
		/// Popola, nella dataClass passata, il contenuto delle proprietà contrassegnate
		/// con l'attributo ForeignKeyAttribute.
		/// </summary>
		/// <param name="dataClassCollection">E' la collection di classi nelle quali devono essere popolate le proprietà con attributo
		/// ForeignKeyAttribute.</param>
		/// <param name="recursive">Se true viene eseguito il metodo per tutte le sottoclassi ricorsivamente.</param>
		public void AddForeign(DataClassCollection dataClassCollection, bool recursive)
		{
			for (int i = 0; i < dataClassCollection.Count; i++)
			{
				AddForeign(dataClassCollection[i], recursive);
			}
		}

		/// <summary>
		/// Popola, nella dataClass passata, il contenuto delle proprietà contrassegnate
		/// con l'attributo ForeignKeyAttribute.
		/// Questa operazione NON avviene ricorsivamente per tutte le sottoclassi.
		/// </summary>
		/// <param name="dataClass">E' la classe nella quale devono essere popolate le proprietà con attributo
		/// ForeignKeyAttribute.</param>
		public void AddForeign(DataClass dataClass)
		{
			AddForeign(dataClass, false);
		}

		/// <summary>
		/// Popola, nella dataClass passata, il contenuto delle proprietà contrassegnate
		/// con l'attributo ForeignKeyAttribute.
		/// </summary>
		/// <param name="dataClass">E' la classe nella quale devono essere popolate le proprietà con attributo
		/// ForeignKeyAttribute.</param>
		/// <param name="recursive">Se true viene eseguito il metodo per tutte le sottoclassi ricorsivamente.</param>
		public void AddForeign(DataClass dataClass, bool recursive)
		{
			Type objType = dataClass.GetType();
			PropertyInfo[] dataClassProperties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);

			for (int propIdx = 0; propIdx < dataClassProperties.Length; propIdx++)
			{
				PropertyInfo dataClassProperty = dataClassProperties[propIdx];

				ForeignKeyAttribute[] foreignKeyAttributes = (ForeignKeyAttribute[])dataClassProperty.GetCustomAttributes(typeof(ForeignKeyAttribute), true);

				if (foreignKeyAttributes.Length == 0) continue;

				bool addForeign = true;

				if (String.IsNullOrEmpty(foreignKeyAttributes[0].ClassPropertiesName))
					throw new DataClassException("L'attributo ClassPropertiesName della proprietà " + dataClassProperty.Name + " della classe " + dataClass.GetType().ToString() + " non è impostato.");

				//Lista delle proprietà della classe cls che vanno in join con quelle della classe foreign
				string[] classPropertiesNames = foreignKeyAttributes[0].ClassPropertiesName.Split(',');

				//Lista delle proprietà della classe ForeignDataClass che devono essere messe in join con classPropertiesNames
				string[] foreignPropertiesNames = foreignKeyAttributes[0].ForeignPropertiesName.Split(',');

				if (classPropertiesNames.Length != foreignPropertiesNames.Length)
					throw new DataClassException("Il numero di valori contenuti nelle proprietà ClassPropertiesName e ForeignPropertiesName dell'attributo ForeignKeyAttribute non è corrispondente. Proprietà " + dataClassProperty.Name + " della classe " + dataClass.GetType().ToString() + ".");

				DataClass foreignDataClass = ForeignKeyAttribute.InstantiateDataClass(dataClassProperty);

				for (int foreignIdx = 0; foreignIdx <= classPropertiesNames.Length - 1; foreignIdx++)
				{
					string classPropertyName = classPropertiesNames[foreignIdx].Trim();
					string foreignPropertyName = foreignPropertiesNames[foreignIdx].Trim();

					object foreignValue = null;

					//Per ogni proprietà della classe foreignDataClass si impostano i valori prendendoli
					//dalla classe cls (dataClass passata come parametro)
					PropertyInfo pi = objType.GetProperty(classPropertyName);
					PropertyInfo foreignProperty = foreignDataClass.GetType().GetProperty(foreignPropertyName);

					if (pi == null)
						throw new DataClassException("Il tipo " + objType.Name + " (non foreign class) non contiene una proprietà con nome " + classPropertyName);

					if (foreignProperty == null)
						throw new DataClassException("Il tipo " + foreignDataClass.GetType().Name + " (foreign class) non contiene una proprietà con nome " + foreignPropertyName);

					foreignValue = pi.GetValue(dataClass, null);

					if (DataFieldUtility.IsFieldEmpty(pi, foreignValue))
					{
						addForeign = false;
						break;
					}

					try
					{
						object convertedForeignValue = null;
						if (foreignProperty.PropertyType.IsGenericType && foreignProperty.PropertyType.GetGenericTypeDefinition() == typeof(Nullable<>))
						{
							if (foreignValue.GetType() != foreignProperty.PropertyType.GetGenericArguments()[0])
							{
								convertedForeignValue = Convert.ChangeType(foreignValue, foreignProperty.PropertyType.GetGenericArguments()[0]);
							}
							else
							{
								convertedForeignValue = foreignValue;
							}
						}
						else
						{
							convertedForeignValue = Convert.ChangeType(foreignValue, foreignProperty.PropertyType);
						}

						foreignProperty.SetValue(foreignDataClass, convertedForeignValue, null);
					}
					catch (NullReferenceException)
					{
						throw new DataClassException("Non esiste una proprietà chiamata " + foreignPropertyName + " nella classe " + foreignDataClass.ToString());
					}
					catch (ArgumentException)
					{
						throw new DataClassException("Il valore contenuto nella proprietà " + objType.ToString() + "." + classPropertyName + " non è convertibile nel tipo di dati " + foreignProperty.PropertyType.ToString() + " della proprietà " + dataClassProperty.GetType().ToString() + "." + foreignPropertyName.ToString());
					}
				}

				//Se addForeign = false significa che una delle colonne che va in foreign non è impostata.
				if (addForeign)
				{
					DataClassCollection classList = null;

					if (ForeignKeyAttribute.PropertyIsList(dataClassProperty))	// La proprietà è una lista, aggiungo tutte le classi ritornate dalla query
					{
						classList = this.GetClassList(foreignDataClass, false, false);
						IList lista = (IList)dataClassProperty.GetValue(dataClass, null);
						lista.Clear();

						for (int i = 0; i < classList.Count; i++)
							lista.Add(classList[i]);
					}
					else	// La proprietà non è una lista, assegno solo il primo valore ritornato dalla query
					{
						classList = this.GetClassList(foreignDataClass, true, false);

						if (classList.Count == 1)
							dataClassProperty.SetValue(dataClass, classList[0], null);
						else if (classList.Count > 1)
						{
							string fmtErr = "la risoluzione della foreign key associata alla proprietà {0} della classe {1} ha ritornato più di un record";
							throw new InvalidOperationException(String.Format(fmtErr, dataClassProperty.Name, dataClass.GetType().Name));
						}
					}

					if (recursive)
					{
						//Per ogni proprietà foreign popolata viene invocato ricorsivamente il
						//metodo AddForeign
						AddForeign(classList, recursive);
					}
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
				AddKeyIdentity(dataClass);
				command = _sqlEngine.buildInsert(dataClass);
				command.Transaction = _dbTransaction;
				command.Connection = _dbConnection;

				if (this.OnInsert != null)
					this.OnInsert(this, new DbEventArgs(TipoOperazioneDb.Insert, dataClass, command));

				int row = command.ExecuteNonQuery();
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
				command = _sqlEngine.buildUpdate(dataClass);
				command.Transaction = _dbTransaction;
				command.Connection = _dbConnection;

				if (this.OnUpdate != null)
					this.OnUpdate(this, new DbEventArgs(TipoOperazioneDb.Update, dataClass, command));


				int row = command.ExecuteNonQuery();
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
				command = _sqlEngine.buildDelete(dataClass);
				command.Transaction = _dbTransaction;
				command.Connection = _dbConnection;

				if (this.OnDelete != null)
					this.OnDelete(this, new DbEventArgs(TipoOperazioneDb.Delete, dataClass, command));

				int row = command.ExecuteNonQuery();
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
		/// <param name="dataClass">E' la classe con cui creare il command.</param>
		/// <returns></returns>
		public IDbCommand CreateCommand(DataClass dataClass, DataClass dataClassCompare)
		{
			IDbCommand command = _sqlEngine.BuildQuery(dataClass,dataClassCompare);
			command.Connection = _dbConnection;
			command.Transaction = _dbTransaction;
			return command;
		}

		public IDbCommand CreateCommand(DataClass dataClass)
		{
			return CreateCommand(dataClass, null);
		}

		public IDbDataAdapter CreateDataAdapter(IDbCommand command)
		{
			IDbDataAdapter adapter = _provider.CreateDataAdapter(command);
			return adapter;
		}

		/// <summary>
		/// Crea un IDbCommand con la connessione e la transazione specificate nell'oggetto DataBase.
		/// </summary>
		/// <param name="commandText">IDbCommadn.commandText.</param>
		/// <returns></returns>
		public IDbCommand CreateCommand(string commandText)
		{
			return _provider.CreateCommand(commandText, this._dbConnection, this._dbTransaction);
		}

		/// <summary>
		/// Crea un IDbCommand con la connessione e la transazione specificate nell'oggetto DataBase.
		/// </summary>
		/// <returns></returns>
		public IDbCommand CreateCommand()
		{
			IDbCommand command = _provider.CreateCommand();
			command.Connection = _dbConnection;
			command.Transaction = _dbTransaction;

			return command;
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
			IDbDataParameter par = _provider.CreateDataParameter(Specifics.ParameterName(name), value);
			return par;
		}


		#region IDisposable

		public event EventHandler Disposed;

		public void Dispose()
		{
			while (m_stackAperture.Count > 0)
				this.Close();

			if (_dbTransaction == null)
				_dbConnection.Dispose();

			if (Disposed != null)
				Disposed(this,EventArgs.Empty);
		}

		#endregion
	}

	public enum Provider { ORACLE, SQL, MYSQL, POSTGRESQL, PERVASIVE }
}