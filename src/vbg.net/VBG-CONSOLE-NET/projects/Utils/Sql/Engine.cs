using PersonalLib2.Data;
using PersonalLib2.Data.Metadata;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Reflection;
using System.Text;

namespace PersonalLib2.Sql
{
    /// <summary>
    /// Motore per la generazione di query di comando e selezione.
    /// Manipola classi che descrivono il DB.
    /// </summary>
    internal class SqlEngine
    {
        private readonly IDataProviderFactory _provider = null;

        public SqlEngine(IDataProviderFactory provider)
        {
            this._provider = provider;
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
        public IDbCommand BuildQuery(DataClass dataClass, DataClass dataClassCompare = null, IDbCommand existingCommand = null)
        {
            MetadataAnalyzer.Instance.EnsureAnalysisExistsFor(dataClass.GetType());

            var command = existingCommand == null ? this._provider.CreateCommand() : existingCommand;
            var objType = dataClass.GetType();
            var metadata = MetadataStore.Instance.GetMetadata(objType);

            var tableName = metadata.TableName;

            // string clause = "";
            var filtriWhere = new List<string>();
            var campiSelect = new List<string>();

            //Seleziona solo i campi indicati nella proprietà dataClass.SelectColumns            
            if (!String.IsNullOrEmpty(dataClass.SelectColumns))
            {
                campiSelect.Add(dataClass.SelectColumns);
            }
            else
            {
                campiSelect.Add(metadata.DefaultSelectColumns);
            }

            foreach (var prop in metadata.WhereClauseProperties)
            {
                var baseField = prop.CustomAttribute;
                var classProperty = prop.Property;

                //La proprietà ha impostato l'attributo BaseFieldScope.Select
                //if ((baseField.DbScope & BaseFieldScope.Select) > 0)
                //{
                //    //Create "Select ColumnName			
                //    if (String.IsNullOrEmpty(dataClass.SelectColumns))
                //    {
                //        campiSelect.Add($"{tableName}.{prop.ColumnName}");
                //    }
                //}

                // Se la proprietà contiene una data e la data è 01/01/0001 significa che non è impostata e quindi viene
                // considerato come un valore nullo.
                // Se un numero (int, long, double, float ) è impostato al suo valore minimo (proprietà .MinValue) viene
                // considerato come un valore nullo.
                // TODO: come comportaqrsi con i bool???

                object comparePropertyValue = null;

                object propertyValue = this.GetCustomValue(prop, dataClass);

                if (dataClassCompare != null)
                {
                    comparePropertyValue = this.GetCustomValue(prop, dataClassCompare);
                }

                if (comparePropertyValue != null && comparePropertyValue.ToString().Trim().ToUpper().IndexOf("IS") == 0)
                {
                    filtriWhere.Add($" {baseField.ColumnName} {comparePropertyValue} ");
                }
                else
                {
                    if (propertyValue != null)
                    {
                        if (dataClassCompare != null)
                        {
                            filtriWhere.Add(this.CreateWhereNoAnd(dataClass, dataClassCompare, baseField, classProperty, command));
                        }
                        else
                        {
                            filtriWhere.Add(this.CreateWhereNoAnd2(prop, propertyValue, command));
                        }
                    }
                }
            }

            //Aggiunge i campi indicati nella proprietà OthersSelectColumns
            if (dataClass.OthersSelectColumns != null && dataClass.OthersSelectColumns.Count > 0)
            {
                campiSelect.AddRange(dataClass.OthersSelectColumns);
            }

            var sb = new StringBuilder("SELECT ");
            sb.Append(String.Join(", ", campiSelect));
            sb.Append(" FROM ");

            if (dataClass.OthersTables != null && dataClass.OthersTables.Count > 0)
            {
                sb.Append(String.Join(",", dataClass.OthersTables));
                sb.Append(", ");
            }

            sb.Append(tableName);

            if (dataClass.OthersJoinClause != null && dataClass.OthersJoinClause.Count > 0)
            {
                sb.Append(" ");
                sb.Append(String.Join(" ", dataClass.OthersJoinClause));
            }

            if (dataClass.OthersWhereClause != null && dataClass.OthersWhereClause.Count > 0)
            {
                filtriWhere.AddRange(dataClass.OthersWhereClause);
            }

            if (filtriWhere.Count > 0)
            {
                sb.Append(" WHERE ");
                sb.Append(String.Join(" and ", filtriWhere));
            }

            if (!String.IsNullOrEmpty(dataClass.OrderBy))
            {
                sb.Append($" order by {dataClass.OrderBy}");
            }

            command.CommandText = sb.ToString();

            return command;
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
            var command = this._provider.CreateCommand();
            var objType = dataClass.GetType();

            //var properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance)
            //                        .Cast<PropertyInfo>()
            //                        .Select(x => new
            //                        {
            //                            Property = x,
            //                            CustomAttribute = (BaseFieldAttribute)Attribute.GetCustomAttribute(x, typeof(BaseFieldAttribute), true)
            //                        })
            //                        .Where(x => x.CustomAttribute != null && (x.CustomAttribute.DbScope & BaseFieldScope.Insert) > 0)
            //                        .Select(x => new DataReaderRecordInfo(x.Property, x.CustomAttribute));

            var metadata = MetadataStore.Instance.GetMetadata(objType);

            var parameterNames = new List<string>();
            var columnNames = new List<string>();

            foreach (var prop in metadata.AssignableProperties)
            {
                var itemValue = this.GetCustomValue(prop, dataClass);

                if (itemValue != null)
                {
                    columnNames.Add(prop.ColumnName);
                    parameterNames.Add(this.AddParameters(command, itemValue, prop.Property.Name, prop.CustomAttribute, null));
                }
            }

            command.CommandText = $"Insert Into {metadata.TableName} ({String.Join(", ", columnNames)}) values ({String.Join(", ", parameterNames)})";

            return command;
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
            var command = this._provider.CreateCommand();
            Type objType = dataClass.GetType();

            //DataTableAttribute[] dataTables = (DataTableAttribute[]) objType.GetCustomAttributes(typeof (DataTableAttribute), true);

            if (dataClass.DataTableName.Length == 0)
            {
                throw new ArgumentException($"L'attributo DataTable non è stato trovato nell'oggetto di tipo {objType.FullName}");
            }

            //var properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance)
            //                        .Cast<PropertyInfo>()
            //                        .Select(x => new
            //                        {
            //                            Property = x,
            //                            CustomAttribute = (BaseFieldAttribute)Attribute.GetCustomAttribute(x, typeof(BaseFieldAttribute), true)
            //                        })
            //                        .Where(x => x.CustomAttribute != null && (x.CustomAttribute.DbScope & BaseFieldScope.Update) > 0)
            //                        .Select(x => new DataReaderRecordInfo(x.Property, x.CustomAttribute));

            var metadata = MetadataStore.Instance.GetMetadata(objType);

            var keyFields = new List<PropertyMetadata>();
            var setCommands = new List<string>();
            var filters = new List<string>();

            foreach (var prop in metadata.AssignableProperties)
            {
                if (prop.CustomAttribute is DataFieldAttribute)
                {
                    var tValue = this.GetCustomValue(prop, dataClass);
                    var cmd = prop.ColumnName + "=" + this.AddParameters(command, tValue, prop.Property.Name, prop.CustomAttribute, null);

                    setCommands.Add(cmd);
                }

                //Estrae la chiave primaria
                if (prop.CustomAttribute is KeyFieldAttribute)
                {
                    keyFields.Add(prop);
                }
            }

            foreach (var prop in keyFields)
            {
                object propertyValue = this.GetCustomValue(prop, dataClass);

                if (propertyValue == null)
                {
                    throw new ArgumentException("The key attribute cannot be null");
                }

                filters.Add(this.CreateWhereNoAnd(dataClass, null, prop.CustomAttribute, prop.Property, command));
            }

            var sql = $"update {dataClass.DataTableName} set {String.Join(", ", setCommands)} where {String.Join(" and ", filters)}";

            command.CommandText = sql;

            return command;
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
            IDbCommand result = this._provider.CreateCommand();
            Type objType = dataClass.GetType();

            DataTableAttribute[] dataTables = (DataTableAttribute[])objType.GetCustomAttributes(typeof(DataTableAttribute), true);

            if (dataTables.Length > 0)
            {
                PropertyInfo[] properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);

                StringBuilder sb = new StringBuilder("Delete From " + dataTables[0].TableName + " ");
                StringBuilder clause = new StringBuilder("WHERE ");

                for (int i = 0; i < properties.Length; i++)
                {
                    //Estrae la chiave primaria
                    BaseFieldAttribute[] fields = (BaseFieldAttribute[])properties[i].GetCustomAttributes(typeof(KeyFieldAttribute), true);
                    if (fields.Length > 0)
                    {
                        if (properties[i].GetValue(dataClass, null) != null && properties[i].GetValue(dataClass, null).ToString() != "")
                        {
                            if (properties[i].GetValue(dataClass, null) != null && properties[i].GetValue(dataClass, null).ToString() != "")
                            {
                                clause.Append(this.CreateWhere(dataClass, null, fields[0], properties[i], result));
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

        private object GetCustomValue(PropertyMetadata metadata, DataClass dataClass)
        {
            object tValue = metadata.Property.GetValue(dataClass, null);

            if (DataFieldUtility.IsFieldEmpty(metadata.Property, tValue)) return null;

            if (metadata.CastDateToString)
            {
                //se il campo del db NON è di tipo Date o DateTime e la proprietà
                //è DateTime allora va convertito
                tValue = ((DateTime)tValue).ToString(metadata.CustomAttribute.DateFormat);
            }

            return tValue;
        }

        /*
        private object GetCustomValue(DataReaderRecordInfo dataReaderRecordInfo, DataClass dataClass)
        {
            object tValue = dataReaderRecordInfo.Property.GetValue(dataClass, null);

            if (DataFieldUtility.IsFieldEmpty(dataReaderRecordInfo.Property, tValue)) return null;

            if (dataReaderRecordInfo.CastDateToString)
            {
                //se il campo del db NON è di tipo Date o DateTime e la proprietà
                //è DateTime allora va convertito
                tValue = ((DateTime)tValue).ToString(dataReaderRecordInfo.CustomAttribute.DateFormat);
            }

            //			fieldName=baseFieldAttribute.ColumnName;
            //	
            //			if (tValue!=null && !baseFieldAttribute.CaseSensitive && (baseFieldAttribute.Type==DbType.String  || baseFieldAttribute.Type==DbType.StringFixedLength || baseFieldAttribute.Type==DbType.AnsiString || baseFieldAttribute.Type==DbType.AnsiStringFixedLength))
            //			{
            //				fieldName = _provider.Specifics.UCaseFunction(fieldName,_dataBase.Connection);
            //			}							
            return tValue;
        }
        */


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
        private object GetCustomValue(PropertyInfo property, DataClass dataClass, BaseFieldAttribute baseFieldAttribute)
        {
            object tValue = property.GetValue(dataClass, null);

            if (DataFieldUtility.IsFieldEmpty(property, tValue)) return null;

            bool campoData = (property.PropertyType == typeof(DateTime) || property.PropertyType == typeof(DateTime?));

            if (tValue != null && campoData && !(baseFieldAttribute.Type == DbType.Date || baseFieldAttribute.Type == DbType.DateTime))
            {
                //se il campo del db NON è di tipo Date o DateTime e la proprietà
                //è DateTime allora va convertito
                tValue = ((DateTime)tValue).ToString(baseFieldAttribute.DateFormat);
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
            var compareOperator = baseField.Compare;
            var propertyValue = property.GetValue(dataClass, null);

            if (dataClassCompare != null && !DataFieldUtility.IsFieldEmpty(property, property.GetValue(dataClassCompare, null)))
            {
                compareOperator = property.GetValue(dataClassCompare, null).ToString();
            }

            var isCampoData = (property.PropertyType == typeof(DateTime) || property.PropertyType == typeof(DateTime?));

            if (isCampoData && !(baseField.Type == DbType.Date || baseField.Type == DbType.DateTime))
            {
                //se il campo del db NON è di tipo Date o DateTime e la proprietà
                //è DateTime allora va convertito
                propertyValue = ((DateTime)propertyValue).ToString(baseField.DateFormat);
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

            if (compareOperator.ToUpper().Trim() == "LIKE")
            {
                string val = propertyValue.ToString();

                if (val == String.Empty || (!val.StartsWith("%") && !val.EndsWith("%")))
                {
                    propertyValue = $"%{propertyValue}%";
                }
            }

            string columnName = baseField.ColumnName.IndexOf(".") == -1 ?
                                $"{dataClass.DataTableName}.{baseField.ColumnName}" :
                                baseField.ColumnName;

            var isDbString = baseField.Type == DbType.String ||
                             baseField.Type == DbType.StringFixedLength ||
                             baseField.Type == DbType.AnsiString ||
                             baseField.Type == DbType.AnsiStringFixedLength;

            if (!baseField.CaseSensitive && isDbString)
            {
                columnName = this._provider.Specifics.UCaseFunction(columnName);
                propertyValue = propertyValue.ToString().ToUpper();
            }

            return $"{columnName} {compareOperator} {this.AddParameters(dbCommand, propertyValue, property.Name, baseField, compareOperator)} and ";
        }

        private string CreateWhereNoAnd2(PropertyMetadata metadata, object propertyValue, IDbCommand dbCommand)
        {
            PropertyInfo property = metadata.Property;
            BaseFieldAttribute baseField = metadata.CustomAttribute;
            var compareOperator = baseField.Compare;

            var isCampoData = (property.PropertyType == typeof(DateTime) || property.PropertyType == typeof(DateTime?));

            if (isCampoData && baseField.Type != DbType.Date && baseField.Type != DbType.DateTime)
            {
                //se il campo del db NON è di tipo Date o DateTime e la proprietà
                //è DateTime allora va convertito
                propertyValue = ((DateTime)propertyValue).ToString(baseField.DateFormat);
            }

            if (compareOperator.ToUpper().Trim() == "LIKE")
            {
                string val = propertyValue.ToString();

                if (val == String.Empty || (!val.StartsWith("%") && !val.EndsWith("%")))
                {
                    propertyValue = $"%{propertyValue}%";
                }
            }

            string columnName = baseField.ColumnName.IndexOf(".") == -1 ?
                                metadata.FullColumnName :
                                baseField.ColumnName;

            var isDbString = baseField.Type == DbType.String ||
                             baseField.Type == DbType.StringFixedLength ||
                             baseField.Type == DbType.AnsiString ||
                             baseField.Type == DbType.AnsiStringFixedLength;

            if (!baseField.CaseSensitive && isDbString)
            {
                columnName = this._provider.Specifics.UCaseFunction(columnName);
                propertyValue = propertyValue.ToString().ToUpper();
            }

            return $"{columnName} {compareOperator} {this.AddParameters(dbCommand, propertyValue, property.Name, baseField, compareOperator)}";
        }



        internal string CreateWhereNoAnd(DataClass dataClass, DataClass dataClassCompare, BaseFieldAttribute baseField, PropertyInfo property, IDbCommand dbCommand)
        {
            var compareOperator = baseField.Compare;
            var propertyValue = property.GetValue(dataClass, null);

            if (dataClassCompare != null && !DataFieldUtility.IsFieldEmpty(property, property.GetValue(dataClassCompare, null)))
            {
                compareOperator = property.GetValue(dataClassCompare, null).ToString();
            }

            var isCampoData = (property.PropertyType == typeof(DateTime) || property.PropertyType == typeof(DateTime?));

            if (isCampoData && !(baseField.Type == DbType.Date || baseField.Type == DbType.DateTime))
            {
                //se il campo del db NON è di tipo Date o DateTime e la proprietà
                //è DateTime allora va convertito
                propertyValue = ((DateTime)propertyValue).ToString(baseField.DateFormat);
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

            if (compareOperator.ToUpper().Trim() == "LIKE")
            {
                string val = propertyValue.ToString();

                if (val == String.Empty || (!val.StartsWith("%") && !val.EndsWith("%")))
                {
                    propertyValue = $"%{propertyValue}%";
                }
            }

            string columnName = baseField.ColumnName.IndexOf(".") == -1 ?
                                $"{dataClass.DataTableName}.{baseField.ColumnName}" :
                                baseField.ColumnName;

            var isDbString = baseField.Type == DbType.String ||
                             baseField.Type == DbType.StringFixedLength ||
                             baseField.Type == DbType.AnsiString ||
                             baseField.Type == DbType.AnsiStringFixedLength;

            if (!baseField.CaseSensitive && isDbString)
            {
                columnName = this._provider.Specifics.UCaseFunction(columnName);
                propertyValue = propertyValue.ToString().ToUpper();
            }

            return $"{columnName} {compareOperator} {this.AddParameters(dbCommand, propertyValue, property.Name, baseField, compareOperator)}";
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
                    retQueryParameters = retQueryParameters + this.AddParameter(parameterName + postfixParameterName, valore, column, command) + ",";
                    I++;
                }

                return "(" + retQueryParameters.Remove(retQueryParameters.Length - 1, 1) + ")";
            }

            return this.AddParameter(parameterName, parameterValue, column, command);
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
            var columnName = column.ColumnName;
            var parameter = command.CreateParameter();// _provider.CreateDataParameter();

            parameter.ParameterName = this._provider.Specifics.ParameterName(parameterName);
            parameter.SourceColumn = columnName;

            if (column.Type == DbType.Binary)
            {
                this._provider.Specifics.ConfigureBlobParameter(parameter);
            }

            parameter.Value = parameterValue;

            if (parameterValue == null || (column.Type != DbType.String && parameterValue.ToString() == String.Empty))
            {
                parameter.Value = DBNull.Value;
            }
            else
            {
                if (parameter.Value.ToString().Length == 0)
                {
                    parameter.Size = 1;
                }
            }

            command.Parameters.Add(parameter);
            return this._provider.Specifics.QueryParameterName(parameterName);
        }

        #endregion
    }
}