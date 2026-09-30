using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Linq;

namespace PersonalLib2.Data.Metadata
{
    internal class DataClassMetadata
    {
        public Type Type { get; }
        public string TableName { get; }
        public ReadOnlyCollection<PropertyMetadata> AssignableProperties { get; }
        public ReadOnlyCollection<ForeignKeyMetadata> ForeignKeys { get; }

        public string DefaultSelectColumns { get; }
        public IEnumerable<PropertyMetadata> WhereClauseProperties { get; }

        public DataClassMetadata(Type type, string tableName, PropertyMetadata[] assignableProperties, ForeignKeyMetadata[] foreignKeys)
        {
            this.Type = type;
            this.TableName = tableName;
            this.AssignableProperties = new ReadOnlyCollection<PropertyMetadata>(assignableProperties);
            this.ForeignKeys = new ReadOnlyCollection<ForeignKeyMetadata>(foreignKeys);

            this.DefaultSelectColumns = String.Join(", ", this.AssignableProperties.Where(x => (x.CustomAttribute.DbScope & BaseFieldScope.Select) > 0).Select(x => x.FullColumnName));
            this.WhereClauseProperties = this.AssignableProperties.Where(x => (x.CustomAttribute.DbScope & BaseFieldScope.Where) > 0);
        }

        internal void DumpToDebugConsole()
        {
            this.WriteToDebug("--------------------------------------------------");
            this.WriteToDebug($"{this.Type.FullName} ({this.TableName})");
            this.WriteToDebug("Properties:");

            foreach (var prop in this.AssignableProperties)
            {
                this.WriteToDebug($"- {prop.Property.Name} ({prop.ColumnName})", 1);
            }

            this.WriteToDebug("Foreign keys:");

            foreach (var fk in this.ForeignKeys)
            {
                this.WriteToDebug($"- {fk.Property.Name} [{fk.KeyType}]", 1);

                for (var i = 0; i < fk.Attribute.ClassPropertiesNames.Length; i++)
                {
                    this.WriteToDebug($"  - src.{fk.Attribute.ClassPropertiesNames[i]} => dst.{fk.Attribute.ForeignPropertiesNames[i]}", 2);
                }
            }
        }

        private void WriteToDebug(string text, int indent = 0)
        {
            Debug.WriteLine(new string(' ', indent * 4) + text);
        }
    }
}