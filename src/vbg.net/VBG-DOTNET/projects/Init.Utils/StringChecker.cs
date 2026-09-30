using System;
using System.Collections;

namespace Init.Utils
{
    /// <summary>
    /// Descrizione di riepilogo per StringChecker.
    /// </summary>
    public static class StringChecker
    {
        public static bool IsObjectEmpty(object str, bool raiseError = true)
        {
            if (str == null) return true;

            if (str.GetType().IsGenericType && str.GetType().GetGenericTypeDefinition() == typeof(Nullable<>))
                return (str == null);

            if (str.GetType() == typeof(decimal)) return false;

            // Un tipo valore non può essere null
            if (str.GetType() == typeof(bool)) return false;

            if (str.GetType() == typeof(String))
                return String.IsNullOrEmpty(str.ToString());

            if (str.GetType() == typeof(int))
                return String.IsNullOrEmpty(str.ToString());

            if (str.GetType() == typeof(Int16))
                return String.IsNullOrEmpty(str.ToString());

            if (str.GetType() == typeof(Int32))
                return String.IsNullOrEmpty(str.ToString());

            if (str.GetType() == typeof(Int64))
                return String.IsNullOrEmpty(str.ToString());

            if (str.GetType() == typeof(Array))
                return ((str as Array).Length == 0);

            if (str.GetType() == typeof(Byte[]))
                return ((str as Byte[]).Length == 0);

            if (str.GetType() == typeof(ArrayList))
                return ((str as ArrayList).Count == 0);

            if (str.GetType() == typeof(DateTime))
                return (((DateTime)str).CompareTo(new DateTime()) == 0);

            if (str is ICollection)
                return ((ICollection)str).Count == 0;



            if (raiseError)
                throw new ApplicationException("Invalid Object Type");

            return false;
        }

        public static bool IsNumeric(string str)
        {
            if (string.IsNullOrWhiteSpace(str))
                return false;

            return double.TryParse(str,
                System.Globalization.NumberStyles.Number,
                System.Globalization.CultureInfo.InvariantCulture,
                out _);
        }

    }
}