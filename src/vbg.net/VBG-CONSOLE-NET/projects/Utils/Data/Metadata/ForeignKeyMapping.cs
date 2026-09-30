using System;
using System.Linq;
using System.Reflection;

namespace PersonalLib2.Data.Metadata
{
    public class ForeignKeyMapping
    {
        public readonly PropertyInfo SourceProperty;
        public readonly PropertyInfo ForeignProperty;
        public string ForeignColumnName
        {
            get
            {
                this.EnsureForeignMetadataAreInitialized();

                return this._foreignColumnName;
            }
        }

        public string ForeignTableName
        {
            get
            {
                this.EnsureForeignMetadataAreInitialized();
                return this._foreignTableName;
            }
        }

        private DataClassMetadata? _foreignMetadata;
        private string _foreignColumnName = String.Empty;
        private string _foreignTableName = String.Empty;

        private void EnsureForeignMetadataAreInitialized()
        {
            if (this._foreignMetadata != null)
            {
                return;
            }

            this._foreignMetadata = MetadataStore.Instance.GetMetadata(this.ForeignProperty.DeclaringType);

            this._foreignColumnName = this._foreignMetadata.AssignableProperties.FirstOrDefault(x => x.Property.Name == this.ForeignProperty.Name)?.ColumnName;
            this._foreignTableName = this._foreignMetadata.TableName;
        }

        public Func<object, object> ExtractForeignValue { get; private set; }
        public ForeignKeyMapping(PropertyInfo source, PropertyInfo destination)
        {
            this.SourceProperty = source;
            this.ForeignProperty = destination;

            this.ExtractForeignValue = this.InitializePropertyMappingMethod();
        }

        private Func<object, object> InitializePropertyMappingMethod()
        {
            var sourcePropertyType = this.SourceProperty.PropertyType;
            var foreignPropertyType = this.ForeignProperty.PropertyType;

            if (sourcePropertyType == foreignPropertyType)
            {
                return (foreignValue) => foreignValue;
            }

            if (foreignPropertyType.IsGenericType && foreignPropertyType.GetGenericTypeDefinition() == typeof(Nullable<>))
            {
                if (sourcePropertyType != foreignPropertyType.GetGenericArguments()[0])
                {
                    return (foreignValue) => Convert.ChangeType(foreignValue, foreignPropertyType.GetGenericArguments()[0]);
                }

                return (foreignValue) => foreignValue;
            }

            return (foreignValue) => Convert.ChangeType(foreignValue, foreignPropertyType);
        }


    }
}
