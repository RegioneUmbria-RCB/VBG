using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Reflection;
using System.Text;

namespace PersonalLib2.Data.Metadata
{
    public class ForeignKeyMetadata
    {
        public List<ForeignKeyMapping> PropertyMappings { get; }
        public PropertyInfo Property { get; }
        public ForeignKeyAttribute Attribute { get; }
        public ForeignKeyType KeyType { get; }
        public Type _foreignType { get; }

        private string _baseForeignSql = string.Empty;

        public ForeignKeyMetadata(PropertyInfo property, ForeignKeyAttribute attribute, ForeignKeyType keyType)
        {
            this.Property = property;
            this.Attribute = attribute;
            this.KeyType = keyType;

            if (this.Attribute.ClassPropertiesNames.Length != this.Attribute.ForeignPropertiesNames.Length)
                throw new ConfigurationErrorsException($"{property.GetType().ToString()}.{property.Name}: Il numero di valori contenuti nelle proprietà ClassPropertiesName e ForeignPropertiesName dell'attributo ForeignKeyAttribute non è corrispondente.");

            if (!this.Attribute.ClassPropertiesNameHasValue)
                throw new Exception("L'attributo ClassPropertiesName della proprietà " + this.Property.Name + " della classe " + this.Property.DeclaringType.ToString() + " non è impostato.");

            this._foreignType = ForeignKeyAttribute.InstantiateDataClass(this.Property).GetType();

            this.PropertyMappings = this.CreateMappings();
        }

        private List<ForeignKeyMapping> CreateMappings()
        {
            var rVal = new List<ForeignKeyMapping>();

            //Lista delle proprietà della classe cls che vanno in join con quelle della classe foreign
            string[] classPropertiesNames = this.Attribute.ClassPropertiesNames;

            //Lista delle proprietà della classe ForeignDataClass che devono essere messe in join con classPropertiesNames
            string[] foreignPropertiesNames = this.Attribute.ForeignPropertiesNames;

            for (int i = 0; i < classPropertiesNames.Length; i++)
            {
                string classPropertyName = classPropertiesNames[i];
                string foreignPropertyName = foreignPropertiesNames[i];

                //Per ogni proprietà della classe foreignDataClass si impostano i valori prendendoli
                //dalla classe cls (dataClass passata come parametro)
                PropertyInfo pi = this.Property.DeclaringType.GetProperty(classPropertyName);
                PropertyInfo foreignProperty = this._foreignType.GetProperty(foreignPropertyName);

                if (pi == null)
                    throw new Exception($"Validazione delle fk della classe {this.Property.DeclaringType.Name}: Il tipo {this.Property.DeclaringType.Name} (non foreign class) non contiene una proprietà con nome {classPropertyName}");

                if (foreignProperty == null)
                    throw new Exception($"Validazione delle fk della classe {this.Property.DeclaringType.Name}: Il tipo {this._foreignType.Name}(foreign class) non contiene una proprietà con nome {foreignPropertyName}");


                rVal.Add(new ForeignKeyMapping(pi, foreignProperty));
            }

            return rVal;
        }


        internal IDbCommand CreateSelectCommand(DataClass sourceClass, DataBase dataBase)
        {
            List<string> whereClauses = new List<string>();
            List<IDbDataParameter> parameters = new List<IDbDataParameter>();

            foreach (var mapping in this.PropertyMappings)
            {
                var sourcePropertyName = mapping.SourceProperty.Name;
                var foreignColumnName = mapping.ForeignColumnName;
                var foreignTableName = mapping.ForeignTableName;

                var paramName = dataBase.Specifics.QueryParameterName(sourcePropertyName);

                whereClauses.Add($"{foreignTableName}.{foreignColumnName} = {paramName}");

                var value = mapping.SourceProperty.GetValue(sourceClass) ?? DBNull.Value;
                parameters.Add(dataBase.CreateParameter(sourcePropertyName, value));
            }

            var selectSql = $"{this.GetBaseForeignSql()} {string.Join(" AND ", whereClauses)}";

            var command = dataBase.CreateCommand(selectSql);

            foreach (var par in parameters)
            {
                command.Parameters.Add(par);
            }

            return command;
        }

        private string GetBaseForeignSql()
        {
            if (!String.IsNullOrEmpty(this._baseForeignSql))
            {
                return this._baseForeignSql;
            }

            var foreignMetadata = MetadataStore.Instance.GetMetadata(this._foreignType);

            StringBuilder sb = new StringBuilder();
            sb.Append("SELECT ");
            sb.Append(string.Join(", ", foreignMetadata.AssignableProperties.Select(x => x.FullColumnName)));
            sb.Append($" FROM {foreignMetadata.TableName} ");
            sb.Append(" WHERE ");

            this._baseForeignSql = sb.ToString();
            return this._baseForeignSql;
        }
    }
}
