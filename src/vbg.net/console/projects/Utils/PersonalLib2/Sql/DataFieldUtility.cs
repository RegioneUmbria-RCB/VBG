using System;
using System.Collections.Generic;
using System.Text;
using System.Reflection;

namespace PersonalLib2.Sql
{
    static class DataFieldUtility
    {
        /// <summary>
        /// Ottiene il valore di default di un tipo in base alle convenzioni utilizzate all'interno della personallib 2
        /// x es: il default di un intero è int.MinValue e non 0 come definito nel CLR
        /// <remarks>
        /// Viene Chiamata quando il valore da assegnare ad una proprietà è null e si vuole sapere quale valore viene assegnato
        /// normalmente a quel tipo di dato
        /// </remarks>
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>
        public static object ValoreDefault(Type type)
        {
            if (type == typeof(short))
                return short.MinValue;

            if (type == typeof(int))
                return int.MinValue;

            if (type == typeof(float))
                return float.MinValue;

            if (type == typeof(long))
                return long.MinValue;

            if (type == typeof(double))
                return double.MinValue;

			if (type == typeof(DateTime))
				return DateTime.MinValue;

            return null;
        }

        public static bool IsFieldEmpty(PropertyInfo propertyInfo, object propValue)
        {
            if (propValue == null) return true;

            return IsEmptyString(propertyInfo, propValue) ||
                    IsEmptyDateTime(propertyInfo, propValue) ||
                    IsEmptyNumber(propertyInfo, propValue);

        }

        private static bool IsEmptyString(PropertyInfo propertyInfo, object propValue)
        {
            Type propertyType = propertyInfo.PropertyType;
            return (propertyType == typeof(string) && propValue.ToString() == null && propValue.ToString().Length == 0);
        }


        private static bool IsEmptyDateTime(PropertyInfo propertyInfo, object propValue)
        {
            Type propertyType = propertyInfo.PropertyType;
            return ((propertyType == typeof(DateTime) || propertyType == typeof(Nullable<DateTime>)) && ((DateTime)propValue).Year == 0001);
        }


        private static bool IsEmptyNumber(PropertyInfo propertyInfo, object propValue)
        {
            return IsEmptyInt(propertyInfo, propValue) ||
                    IsEmptyLong(propertyInfo, propValue) ||
                    IsEmptyDouble(propertyInfo, propValue) ||
                    IsEmptyFloat(propertyInfo, propValue);
        }

        private static bool IsEmptyLong(PropertyInfo propertyInfo, object propValue)
        {
            Type propertyType = propertyInfo.PropertyType;
            if (propValue == null) return true;
            return ((propertyType == typeof(long) || propertyType == typeof(Nullable<long>)) && (long)propValue == long.MinValue);
        }

        private static bool IsEmptyDouble(PropertyInfo propertyInfo, object propValue)
        {
            Type propertyType = propertyInfo.PropertyType;
            if (propValue == null) return true;
            return ((propertyType == typeof(double) || propertyType == typeof(Nullable<double>)) && (double)propValue < -1E37);
        }

        private static bool IsEmptyFloat(PropertyInfo propertyInfo, object propValue)
        {
            Type propertyType = propertyInfo.PropertyType;
            if (propValue == null) return true;
            return ((propertyType == typeof(float) || propertyType == typeof(Nullable<float>)) && (float)propValue == float.MinValue);
        }

        private static bool IsEmptyInt(PropertyInfo propertyInfo, object propValue)
        {
            Type propertyType = propertyInfo.PropertyType;
            if (propValue == null) return true;
            return ((propertyType == typeof(int) || propertyType == typeof(Nullable<int>)) && (int)propValue == int.MinValue);
        }
    }
}
