using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Reflection;

namespace PersonalLib2.Data.Metadata
{
    public class PropertyMetadata
    {
        public readonly PropertyInfo Property;
        public readonly BaseFieldAttribute CustomAttribute;
        public readonly bool CastDateToString;
        public readonly string ColumnName;
        public readonly object ValoreDefault;
        public readonly Type TargetType;
        private readonly string TableName;
        public readonly string FullColumnName;

        public PropertyMetadata(string tableName, PropertyInfo property, BaseFieldAttribute customAttribute)
        {
            this.TableName = tableName;
            this.Property = property;
            this.CustomAttribute = customAttribute;
            this.ColumnName = customAttribute.ColumnName.Substring(customAttribute.ColumnName.IndexOf(".") + 1);
            this.ValoreDefault = Sql.DataFieldUtility.ValoreDefault(property.PropertyType);

            var isDateType = (property.PropertyType == typeof(DateTime) || property.PropertyType == typeof(DateTime?));
            this.CastDateToString = isDateType && !(customAttribute.Type == DbType.Date || customAttribute.Type == DbType.DateTime);

            this.TargetType = property.PropertyType;

            if (this.TargetType.IsGenericType && this.TargetType.GetGenericTypeDefinition() == typeof(Nullable<>))
            {
                this.TargetType = this.TargetType.GetGenericArguments()[0];
            }

            this.FullColumnName = $"{this.TableName}.{this.ColumnName}";
        }
    }
}
