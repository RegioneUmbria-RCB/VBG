using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems
{
    public static class DataSetExtensions
    {
        public static bool ContieneErrori(this DataSet dataSet)
        {
            return dataSet.Tables["errori"] != null && dataSet.Tables["errori"].Rows.Count > 0;
        }

        public static string GetDescrizioneErrore(this DataSet dataSet)
        {
            return dataSet.ContieneErrori() ? dataSet.Tables["errori"].Rows[0]["descrizione"].ToString() : String.Empty;
        }
    }
}
