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

        public static string GetString(this IDataReader dr, string columnName)
        {
            return dr[columnName].ToString();
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