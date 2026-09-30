using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;

namespace IntegrazioneCUnicoWS
{
    public static class DateUtils
    {
        public static int? DateToCunicoWSDate(DateTime? data) 
        {
            if (data == null)
            {
                return null;
            }

            return Convert.ToInt32(data.Value.ToString("yyyyMMdd"));
        }

        public static DateTime CunicoWSDateToDate(int cunicoWsDate)
        {
            return DateTime.ParseExact(cunicoWsDate.ToString(), "yyyyMMdd", CultureInfo.InvariantCulture);
        }
    }
}
