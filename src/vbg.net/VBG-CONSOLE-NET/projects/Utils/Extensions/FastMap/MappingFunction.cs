using System;
using System.Data;
using System.Reflection;

namespace PersonalLib2.Extensions.FastMap
{
    internal class MappingFunction
    {
        private readonly PropertyInfo _mappedProperty;
        private readonly int _dataReaderIndex;
        private readonly Func<IDataReader, object> _mapValueFunction;

        public MappingFunction(PropertyInfo pi, int dataReaderIndex)
        {
            this._mappedProperty = pi;
            this._dataReaderIndex = dataReaderIndex;
            this._mapValueFunction = this.GetValueExtractionFunction();
        }

        private Func<IDataReader, object> GetValueExtractionFunction()
        {
            var targetType = this._mappedProperty.PropertyType;
            var nullableType = Nullable.GetUnderlyingType(targetType);
            var isNullable = nullableType != null;

            if (isNullable)
            {
                targetType = nullableType;
            }

            var isValueType = targetType.IsValueType;

            return (IDataReader dr) =>
            {
                var tmp = dr[this._dataReaderIndex];
                var tmpType = tmp.GetType();

                if (tmp == null || tmp == DBNull.Value)
                {
                    if (isValueType)
                    {
                        return Activator.CreateInstance(targetType);
                    }

                    return null;
                }

                return tmpType != targetType ? Convert.ChangeType(tmp, targetType) : tmp;
            };
            /*
            return targetType.Name switch
            {
                nameof(String) => (IDataReader dr) => dr.GetStringSafe(this._dataReaderIndex),
                nameof(Int32) => (IDataReader dr) => dr.GetIntSafe(this._dataReaderIndex),
                nameof(Int64) => (IDataReader dr) => dr.GetIntSafe(this._dataReaderIndex),
                nameof(Decimal) => (IDataReader dr) => dr.GetDecimalSafe(this._dataReaderIndex),
                nameof(Double) => (IDataReader dr) => dr.GetDoubleSafe(this._dataReaderIndex),
                nameof(DateTime) => (IDataReader dr) => dr.GetDateTimeSafe(this._dataReaderIndex),
                nameof(Single) => (IDataReader dr) => dr.GetFloatSafe(this._dataReaderIndex),
                _ => (IDataReader dr) =>
                {
                    var tmp = dr[this._dataReaderIndex];

                    return tmp == DBNull.Value ? null : tmp;
                }
            };
            */
        }

        public virtual void Map(object instance, IDataReader dr)
        {
            var value = this._mapValueFunction(dr);
            /*
            if (value != null && value.GetType() != this._mappedProperty.PropertyType && value.GetType() != this._mappedProperty.PropertyType.GetGenericArguments()[0])
            {
                var targetType = this._mappedProperty.PropertyType;

                if (value.GetType() != this._mappedProperty.PropertyType.GetGenericArguments()[0])
                {
                    targetType = this._mappedProperty.PropertyType.GetGenericArguments()[0];
                }

                value = Convert.ChangeType(value, targetType);
            }
            */
            this._mappedProperty.SetValue(instance, value);
        }
    }
}
