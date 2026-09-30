using System;
using System.Data;
using System.Text;

namespace PersonalLib2.Data
{
    public static class IDataReaderExtensions
    {
        // per indice
        public static int? GetIntSafe(this IDataReader dr, int indiceColonna)
        {
            var val = dr[indiceColonna];

            if (val == DBNull.Value)
            {
                return (int?)null;
            }

            return Convert.ToInt32(val);
        }

        public static decimal? GetDecimalSafe(this IDataReader dr, int indiceColonna)
        {
            var val = dr[indiceColonna];

            if (val == DBNull.Value)
            {
                return (decimal?)null;
            }

            return Convert.ToDecimal(val);
        }

        public static double? GetDoubleSafe(this IDataReader dr, int indiceColonna)
        {
            var val = dr[indiceColonna];

            if (val == DBNull.Value)
            {
                return (double?)null;
            }

            return Convert.ToDouble(val);
        }

        public static float? GetFloatSafe(this IDataReader dr, int indiceColonna)
        {
            var val = dr[indiceColonna];

            if (val == DBNull.Value)
            {
                return (float?)null;
            }

            return Convert.ToSingle(val);
        }

        public static string GetStringSafe(this IDataReader dr, int indiceColonna)
        {
            return dr[indiceColonna].ToString();
        }

        public static DateTime? GetDateTimeSafe(this IDataReader dr, int indiceColonna)
        {
            var val = dr[indiceColonna];

            if (val == DBNull.Value)
            {
                return (DateTime?)null;
            }

            return Convert.ToDateTime(val);
        }


        // Per nome colonna
        public static int? GetInt(this IDataReader dr, string nomeColonna)
        {
            var val = dr[nomeColonna];

            if (val == DBNull.Value)
            {
                return (int?)null;
            }

            return Convert.ToInt32(val);
        }

        public static int GetInt(this IDataReader dr, string nomeColonna, int valoreDefault)
        {
            var val = dr[nomeColonna];

            if (val == DBNull.Value)
            {
                return valoreDefault;
            }

            return Convert.ToInt32(val);
        }

        public static decimal? GetDecimal(this IDataReader dr, string nomeColonna)
        {
            var val = dr[nomeColonna];

            if (val == DBNull.Value)
            {
                return (decimal?)null;
            }

            return Convert.ToDecimal(val);
        }

        public static double? GetDouble(this IDataReader dr, string nomeColonna)
        {
            var val = dr[nomeColonna];

            if (val == DBNull.Value)
            {
                return (double?)null;
            }

            return Convert.ToDouble(val);
        }

        public static string GetBytesAsString(this IDataReader dr, string nomeColonna)
        {
            var val = dr[nomeColonna];

            if (val == DBNull.Value)
            {
                return null;
            }

            return Encoding.UTF8.GetString((byte[])val);
        }

        public static byte[] GetBytes(this IDataReader dr, string nomeColonna)
        {
            var val = dr[nomeColonna];

            if (val == DBNull.Value)
            {
                return null;
            }

            return (byte[])val;
        }

        public static string GetString(this IDataReader dr, string columnName)
        {
            return dr[columnName].ToString();
        }

        public static float? GetFloat(this IDataReader dr, string columnName)
        {
            var val = dr[columnName];

            if (val == DBNull.Value)
            {
                return (float?)null;
            }

            return Convert.ToSingle(val);
        }

        public static DateTime? GetDateTime(this IDataReader dr, string columnName)
        {
            var val = dr[columnName];

            if (val == DBNull.Value)
            {
                return (DateTime?)null;
            }

            return Convert.ToDateTime(val);
        }
    }
}
