using System;
using System.Collections;
using System.Data;
using System.Reflection;
using System.Text;
using PersonalLib2.Data;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Text;

namespace PersonalLib2.Sql
{
	/// <summary>
	/// Motore per la generazione di query di comando e selezione.
	/// Manipola classi che descrivono il DB.
	/// </summary>
	internal class SqlEngine
	{
		private DataProviderFactory _provider = null;

		public SqlEngine(DataProviderFactory provider)
		{
			_provider = provider;
		}

		#region Methods

		/// <summary>
		/// Ritorna una stringa che contiene una query di selezione
		/// </summary>
		/// <param name="dataClass">-
		/// E' la classe che descrive la "tabella" dalla quale 
		/// creare la query. Le proprietà della classe (campi)
		/// contengono i valori da utilizzare nella clausola "Where" della query (string)
		/// di ritorno. Es. "NOMINATIVO like " + dataClass.NOMINATIVO.
		/// </param>
		/// <param name="dataClassCompare">-
		/// [opzionale] E' la medesima classe di dataClass
		/// ma le proprietà contengono i termini di confronto
		/// utilizzate per clausola "Where" della query (string)
		/// di ritorno.
		/// Es. "NOMINATIVO " + dataClassCompare.NOMINATIVO + " " + dataClass.NOMINATIVO
		///		sarà
		///     "NOMINATIVO >= Pippo"
		/// </param>
		/// <returns>La query di selezione generata dall'analisi della classe passata</returns>
		public IDbCommand BuildQuery(DataClass dataClass, DataClass dataClassCompare)
		{
			IDbCommand result = _provider.CreateCommand();
			Type objType = dataClass.GetType();

			DataTableAttribute[] dataTables = (DataTableAttribute[]) objType.GetCustomAttributes(typeof (DataTableAttribute), true);

			if (dataTables.Length > 0)
			{
				PropertyInfo[] properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);
				string clause = "";

				StringBuilder sb = new StringBuilder("SELECT ");

				//Seleziona solo i campi indicati nella proprietà dataClass.SelectColumns
				string SelectColumns = "";
				PropertyInfo property = objType.GetProperty("SelectColumns");
				if (property != null && property.GetValue(dataClass, null) != null)
				{
					SelectColumns = property.GetValue(dataClass, null).ToString();
					sb.Append(SelectColumns);
					sb.Append(", ");
				}

				for (int i = 0; i < properties.Length; i++)
				{
					BaseFieldAttribute[] fields = (BaseFieldAttribute[]) properties[i].GetCustomAttributes(typeof (BaseFieldAttribute), true);

					if (fields.Length > 0)
					{
						//La proprietà ha impostato l'attributo BaseFieldScope.Select
						if ((fields[0].DbScope & BaseFieldScope.Select) > 0)
						{
							//Create "Select ColumnName			
							if (SelectColumns == "")
							{
								sb.Append(" ");
								if (fields[0].ColumnName.IndexOf(".") > 0)
									sb.Append(fields[0].ColumnName);
								else
									sb.Append(dataTables[0].TableName + "." + fields[0].ColumnName);

								sb.Append(", ");
							}
                        }

						if ((fields[0].DbScope & BaseFieldScope.Where) == BaseFieldScope.Where) //La proprietà può essere utilizzata per creare le condizioni where
						{
							// Se la proprietà contiene una data e la data è 01/01/0001 significa che non è impostata e quindi viene
							// considerato come un valore nullo.
							// Se un numero (int, long, double, float ) è impostato al suo valore minimo (proprietà .MinValue) viene
							// considerato come un valore nullo.
							// TODO: come comportaqrsi con i bool???

							object comparePropertyValue=null;

							object propertyValue = GetCustomvalue( properties[i] , dataClass , fields[0] );
							if (dataClassCompare!=null)
							{
								comparePropertyValue = GetCustomvalue( properties[i] , dataClassCompare , fields[0] );
							}

							if (comparePropertyValue!=null && comparePropertyValue.ToString().Trim().ToUpper().IndexOf("IS")==0)
							{
								clause += " " + fields[0].ColumnName + " " + comparePropertyValue.ToString() + " and ";
							}
							else if ( propertyValue != null )
							{
								clause += CreateWhere(dataClass, dataClassCompare, fields[0], properties[i], result);
							}
						}
					}
				}

				//Aggiunge i campi indicati nella proprietà OthersSelectColumns
				property = objType.GetProperty("OthersSelectColumns");
				if (property != null && property.GetValue(dataClass, null) != null)
				{
					ArrayList OthersSelectColumns = (ArrayList) property.GetValue(dataClass, null);
					if (OthersSelectColumns.Count != 0)
					{
						sb.Append(StringTools.Join(OthersSelectColumns,","));
						sb.Append(", ");
					}
				}

				// remove the last ','
				sb.Remove(sb.Length - 2, 2);

				sb.Append(" FROM ");

				property = objType.GetProperty("OthersTables");
				if (property != null && property.GetValue(dataClass, null) != null)
				{
					ArrayList OthersTables = (ArrayList) property.GetValue(dataClass, null);
					if (OthersTables.Count != 0)
					{
						sb.Append(StringTools.Join(OthersTables,","));
						sb.Append(", ");
					}
				}
				sb.Append(dataTables[0].TableName);


				property = objType.GetProperty("OthersJoinClause");
				if ((property != null && property.GetValue(dataClass, null) != null) || clause != "")
				{
					if (property != null && property.GetValue(dataClass, null) != null)
					{
						ArrayList OthersJoinClause = (ArrayList) property.GetValue(dataClass, null);
						if (OthersJoinClause.Count != 0)
						{
							sb.Append(" ");
							sb.Append(StringTools.Join(OthersJoinClause, " "));
						}
					}
				}

				property = objType.GetProperty("OthersWhereClause");
				if ((property != null && property.GetValue(dataClass, null) != null) || clause != "")
				{
					if (property != null && property.GetValue(dataClass, null) != null)
					{
						ArrayList OthersWhereClause = (ArrayList) property.GetValue(dataClass, null);
						if (OthersWhereClause.Count != 0)
						{
							clause = StringTools.Join(OthersWhereClause, " and ") + " and " + clause;
						}
					}
					if (clause.Trim() != "")
					{
						sb.Append(" ");
						sb.Append("WHERE ");
						sb.Append(clause);
						sb.Remove(sb.Length - 5, 5);
					}
				}

				property = objType.GetProperty("OrderBy");
				if ((property != null && property.GetValue(dataClass, null) != null))
				{
					if (property != null && property.GetValue(dataClass, null) != null)
					{
						sb.Append(" ");
						sb.Append("Order By " + (string) property.GetValue(dataClass, null));
					}
				}

				result.CommandText = sb.ToString();
			}
			else
			{
				throw new ArgumentException("The DataTable attribute wasn't found in the object [keyValue parameter]");
			}

			return result;
		}

		public IDbCommand BuildQuery(DataClass dataClass)
		{
			return BuildQuery(dataClass, null);
		}

		/// <summary>
		/// Data una classe che descrive una tabella del DB, ne crea una NonQuery 
		/// per l'inserimento di dati (insert).
		/// </summary>
		/// <param name="dataClass">-
		/// E' la classe che descrive la "tabella" dalla quale 
		/// creare la Insert. Le proprietà della classe (campi)
		/// contengono i valori da inserire nel DB
		/// </param>
		/// <returns>La funzione ritorna una stringa che contiene una NonQuery di comando Insert</returns>
		public IDbCommand buildInsert(DataClass dataClass)
		{
			IDbCommand result = _provider.CreateCommand();
			Type objType = dataClass.GetType();

			DataTableAttribute[] dataTables = (DataTableAttribute[]) objType.GetCustomAttributes(typeof (DataTableAttribute), true);

			if (dataTables.Length > 0)
			{
				PropertyInfo[] properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);

				StringBuilder sb = new StringBuilder("Insert Into " + dataTables[0].TableName + " ");

				StringBuilder tValues = new StringBuilder("");
				StringBuilder tColumns = new StringBuilder("");
				for (int i = 0; i < properties.Length; i++)
				{
					BaseFieldAttribute[] fields = (BaseFieldAttribute[]) properties[i].GetCustomAttributes(typeof (BaseFieldAttribute), true);
					;
					if (fields.Length > 0)
					{
						if ((fields[0].DbScope & BaseFieldScope.Insert) > 0)
						{
							object tValue = GetCustomvalue( properties[i], dataClass, fields[0] );
							if ( tValue != null )
							{
								tColumns.Append(fields[0].ColumnName + ",");
								tValues.Append(AddParameters(result, tValue, properties[i].Name, fields[0], null) + ",");
							}
						}
					}
				}
				sb.Append("(");
				sb.Append(tColumns.Remove(tColumns.Length - 1, 1));
				sb.Append(")");
				sb.Append(" values ");
				sb.Append("(");
				sb.Append(tValues.Remove(tValues.Length - 1, 1));
				sb.Append(")");

				result.CommandText = sb.ToString();
			}
			else
			{
				throw new ArgumentException("The DataTable attribute wasn't found in the object [keyValue parameter]");
			}

			return result;
		}

		/// <summary>
		/// Data una classe che descrive una tabella del DB, ne crea un IDbCommand
		/// per l'aggiornamento di dati (update).
		/// </summary>
		/// <param name="dataClass">-
		/// è la classe che descrive la "tabella" dalla quale 
		/// creare l'Update. Le proprietà della classe (campi)
		/// contengono i valori da aggiornare nel DB.
		/// </param>
		/// <returns>Un IDbCommand per eseguire Update</returns>
		public IDbCommand buildUpdate(DataClass dataClass)
		{
			IDbCommand result = _provider.CreateCommand();
			Type objType = dataClass.GetType();

			//DataTableAttribute[] dataTables = (DataTableAttribute[]) objType.GetCustomAttributes(typeof (DataTableAttribute), true);

			if (dataClass.DataTableName.Length > 0)
			{
				PropertyInfo[] properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);

				StringBuilder sb = new StringBuilder("Update " + dataClass.DataTableName + " Set ");
				StringBuilder clause = new StringBuilder("WHERE ");

				ArrayList keyFields = new ArrayList();

				for (int i = 0; i < properties.Length; i++)
				{
					BaseFieldAttribute[] fields = (BaseFieldAttribute[]) properties[i].GetCustomAttributes(typeof (DataFieldAttribute), true);

					if (fields.Length > 0)
					{
						if ((fields[0].DbScope & BaseFieldScope.Update) > 0)
						{
							sb.Append(fields[0].ColumnName + "=");
							object tValue = GetCustomvalue(properties[i], dataClass, fields[0]);
							sb.Append(AddParameters(result, tValue, properties[i].Name, fields[0], null) + ",");
						}
					}

					//Estrae la chiave primaria
					fields = (BaseFieldAttribute[]) properties[i].GetCustomAttributes(typeof (KeyFieldAttribute), true);
					if (fields.Length > 0)
					{
						keyFields.Add( properties[i] );
					}
				}

				for (int i = 0; i < keyFields.Count; i++)
				{
					PropertyInfo propertyInfo = (PropertyInfo) keyFields[i];
					
					BaseFieldAttribute[] attribs = (BaseFieldAttribute[]) propertyInfo.GetCustomAttributes(typeof (KeyFieldAttribute), true);
					BaseFieldAttribute field = attribs[0];

					if ( ( field.DbScope & BaseFieldScope.Update ) > 0)
					{
						object propertyValue = GetCustomvalue( propertyInfo , dataClass , field );

						if ( propertyValue != null )
						{
							clause.Append( CreateWhere( dataClass, null, field, propertyInfo, result ) );
						}
						else
						{
							throw new ArgumentException("The key attribute cannot be null");
						}
					}
				}


				sb.Remove(sb.Length - 1, 1);
				clause.Remove(clause.Length - 5, 5);
				sb.Append(" " + clause.ToString());

				result.CommandText = sb.ToString();
			}
			else
			{
				throw new ArgumentException("The DataTable attribute wasn't found in the object [keyValue parameter]");
			}

			return result;
		}

		/// <summary>
		/// Data una classe che descrive una tabella del DB, ne crea una NonQuery
		/// per la cancellazione di dati (delete).
		/// </summary>
		/// <param name="dataClass">-
		/// è la classe che descrive la "tabella" dalla quale 
		/// creare la Delete. Le proprietà della classe (primaryKey)
		/// contengono i valori da cancellare nel DB.
		/// </param>
		/// <returns>Un IDbCommand con specificato il comando Delete</returns>
		public IDbCommand buildDelete(DataClass dataClass)
		{
			IDbCommand result = _provider.CreateCommand();
			Type objType = dataClass.GetType();

			DataTableAttribute[] dataTables = (DataTableAttribute[]) objType.GetCustomAttributes(typeof (DataTableAttribute), true);

			if (dataTables.Length > 0)
			{
				PropertyInfo[] properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);

				StringBuilder sb = new StringBuilder("Delete From " + dataTables[0].TableName + " ");
				StringBuilder clause = new StringBuilder("WHERE ");

				for (int i = 0; i < properties.Length; i++)
				{
					//Estrae la chiave primaria
					BaseFieldAttribute[] fields = (BaseFieldAttribute[]) properties[i].GetCustomAttributes(typeof (KeyFieldAttribute), true);
					if (fields.Length > 0)
					{
						if (properties[i].GetValue(dataClass, null) != null && properties[i].GetValue(dataClass, null).ToString() != "")
						{
							if (properties[i].GetValue(dataClass, null) != null && properties[i].GetValue(dataClass, null).ToString() != "")
							{
								clause.Append(CreateWhere(dataClass, null,  fields[0], properties[i], result));
							}
							else
							{
								throw new ArgumentException("The key attribute cannot to be null");
							}
						}
						else
						{
							throw new ArgumentException("The key attribute cannot to be null");
						}
					}
				}
				sb.Remove(sb.Length - 1, 1);
				clause.Remove(clause.Length - 5, 5);
				sb.Append(" " + clause.ToString());

				result.CommandText = sb.ToString();
			}
			else
			{
				throw new ArgumentException("The DataTable attribute wasn't found in the object [keyValue parameter]");
			}

			return result;
		}

		#endregion

		#region Private

		/// <summary>
		/// Legge dalla proprietà property della classe dataClass il valore e lo trasforma
		/// a seconda delle indicazioni presenti in baseFieldAttribute. Deve essere utilizzato
		/// per trasformare i valori delle colonne che saranno utilizzate nelle query di comando Insert o Update
		/// e non nelle condizioni where.
		/// </summary>
		/// <param name="property">E' il 'nome' della proprietà che in dataClass contiene il valore da trasformare.</param>
		/// <param name="dataClass">E' la classe che contiene il valore di property.</param>
		/// <param name="baseFieldAttribute">E' l'attributo associato alla proprietà il quale descrive le trasformazioni da effettuare.</param>
		/// <returns>Il valore trasformato.</returns>
		private object GetCustomvalue(PropertyInfo property, DataClass dataClass, BaseFieldAttribute baseFieldAttribute)
		{
			object tValue = property.GetValue(dataClass, null);

			if( DataFieldUtility.IsFieldEmpty( property , tValue ) ) return null;

            bool campoData = (property.PropertyType == typeof(DateTime) || property.PropertyType == typeof(DateTime?));

            if (tValue != null && campoData && !(baseFieldAttribute.Type == DbType.Date || baseFieldAttribute.Type == DbType.DateTime))
			{
				//se il campo del db NON è di tipo Date o DateTime e la proprietà
				//è DateTime allora va convertito
				tValue = ((DateTime) tValue).ToString(baseFieldAttribute.DateFormat);
			}

			//			fieldName=baseFieldAttribute.ColumnName;
			//	
			//			if (tValue!=null && !baseFieldAttribute.CaseSensitive && (baseFieldAttribute.Type==DbType.String  || baseFieldAttribute.Type==DbType.StringFixedLength || baseFieldAttribute.Type==DbType.AnsiString || baseFieldAttribute.Type==DbType.AnsiStringFixedLength))
			//			{
			//				fieldName = _provider.Specifics.UCaseFunction(fieldName,_dataBase.Connection);
			//			}							
			return tValue;
		}

		internal string CreateWhere(DataClass dataClass, DataClass dataClassCompare, BaseFieldAttribute baseField, PropertyInfo property, IDbCommand dbCommand)
		{
			string tCompare = baseField.Compare;

            if (dataClassCompare != null && !DataFieldUtility.IsFieldEmpty(property, property.GetValue(dataClassCompare, null)))
			{
				tCompare = property.GetValue(dataClassCompare, null).ToString();
			}

			Object tValue = property.GetValue(dataClass, null);

            bool campoData = (property.PropertyType == typeof(DateTime) || property.PropertyType == typeof(DateTime?));


            if (campoData && !(baseField.Type == DbType.Date || baseField.Type == DbType.DateTime))
			{
				//se il campo del db NON è di tipo Date o DateTime e la proprietà
				//è DateTime allora va convertito
				tValue = ((DateTime) tValue).ToString(baseField.DateFormat);
			}

			//////////////////////////////////////////////////////////////////////
			// Modificato il 28/09/2007 da Nicola Gargagli. 
			// Da errore se la proprietà fa un confronto con una like 
			// e il valore della proprietà è == String.Empty
			//////////////////////////////////////////////////////////////////////
//			if (tCompare.ToUpper().Trim() == "LIKE" && tValue.ToString().Substring(0, 1) != "%" && tValue.ToString().Substring(tValue.ToString().Length - 1, 1) != "%")
//			{
//				tValue += "%";
//			}

			if (tCompare.ToUpper().Trim() == "LIKE")
			{
				string val = tValue.ToString();

				if ( val == String.Empty || ( val.Substring(0, 1) != "%" && val.Substring( val.Length - 1, 1) != "%" ) )
                    tValue = "%" + tValue + "%";
			}

			string fieldName = baseField.ColumnName;
			if (fieldName.IndexOf(".") == -1)
			{
				fieldName = dataClass.DataTableName + "." + fieldName;
			}

			if (!baseField.CaseSensitive && (baseField.Type == DbType.String || baseField.Type == DbType.StringFixedLength || baseField.Type == DbType.AnsiString || baseField.Type == DbType.AnsiStringFixedLength))
			{
				fieldName = _provider.Specifics.UCaseFunction(fieldName);
				tValue = tValue.ToString().ToUpper();
			}

			return fieldName + " " + tCompare + " " + AddParameters(dbCommand, tValue, property.Name, baseField, tCompare) + " and ";
		}

		/// <summary>
		/// Aggiunge al command i parametri relativi alla proprietà passata.
		/// Se il tipo di confronto nella where è IN allora vengono aggiunti n parametri
		/// altrimenti la funzione richiama AddParameter.
		/// (<see cref="AddParameter"/>)
		/// </summary>
		/// <param name="command"></param>
		/// <param name="parameterValue"></param>
		/// <param name="parameterName"></param>
		/// <param name="column"></param>
		/// <param name="columnOperator">Operatore per confrontare la colonna ed il valore del parametro
		/// da creare. Es. "=" o "LIKE" etc...</param>
		/// <returns></returns>
		private string AddParameters(IDbCommand command, object parameterValue, string parameterName, BaseFieldAttribute column, string columnOperator)
		{
			if (columnOperator != null && columnOperator.ToUpper() == "IN")
			{
				string retQueryParameters = null;
				parameterValue = parameterValue.ToString().Trim(' ');
				if (parameterValue.ToString().Substring(0, 1) == "(")
				{
					parameterValue = parameterValue.ToString().Substring(1, parameterValue.ToString().Length);

					if (parameterValue.ToString().Substring(parameterValue.ToString().Length) == ")")
						parameterValue = parameterValue.ToString().Substring(1, parameterValue.ToString().Length);
				}

				int I = 0;
				foreach (Object valore in parameterValue.ToString().Split(','))
				{
					string postfixParameterName = "";
					if (I > 0) postfixParameterName = I.ToString();
					retQueryParameters = retQueryParameters + AddParameter(parameterName + postfixParameterName, valore, column, command) + ",";
					I ++;
				}

				return "(" + retQueryParameters.Remove(retQueryParameters.Length - 1, 1) + ")";
			}

			return AddParameter(parameterName, parameterValue, column, command);
		}

		/// <summary>
		/// Aggiunge un parametro a command
		/// </summary>
		/// <param name="parameterName">Nome del parametro</param>
		/// <param name="parameterValue">Valore del parametro</param>
		/// <param name="column">Nome della colonna del database.</param>
		/// <param name="command">E' il Command al quale aggiungere il parametro.</param>
		/// <returns>Il nome del parametro da utlizzare nella query (commandtext).</returns>
		private string AddParameter(string parameterName, object parameterValue, BaseFieldAttribute column, IDbCommand command)
		{
			string columnName = column.ColumnName;
			IDbDataParameter parameter = _provider.CreateDataParameter();
			parameter.ParameterName = _provider.Specifics.ParameterName(parameterName);
//			parameter.DbType = field.Type;
			parameter.SourceColumn = columnName;
			parameter.Value = parameterValue;
			if ( parameterValue == null || ( column.Type != DbType.String && parameterValue.ToString() == String.Empty ) )
				parameter.Value = DBNull.Value;
			else
				if (parameter.Value.ToString().Length==0)
					parameter.Size=1;

			command.Parameters.Add(parameter);
			return _provider.Specifics.QueryParameterName(parameterName);
		}

		#endregion
	}
}