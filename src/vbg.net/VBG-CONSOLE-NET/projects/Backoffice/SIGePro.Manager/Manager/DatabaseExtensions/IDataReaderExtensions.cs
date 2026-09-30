using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager
{
    public static class IDataReaderExtensions
    {
        public static int? GetInt(this IDataReader dr, string nomeColonna)
        {
            var val = dr[nomeColonna];

            if (val == DBNull.Value)
            {
                return (int?)null;
            }

            return Convert.ToInt32(val);
        }

        public static float? GetFloat(this IDataReader dr, string nomeColonna)
        {
            var val = dr[nomeColonna];

            if (val == DBNull.Value)
            {
                return (float?)null;
            }

            return Convert.ToSingle(val);
        }

        public static string GetString(this IDataReader dr, string columnName)
        {
            return dr[columnName].ToString();
        }
    }
}