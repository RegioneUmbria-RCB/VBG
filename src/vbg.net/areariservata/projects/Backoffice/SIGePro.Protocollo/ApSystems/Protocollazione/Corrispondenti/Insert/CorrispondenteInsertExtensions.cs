using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.Protocollazione.Corrispondenti.Insert
{
    public static class CorrispondenteInsertExtensions
    {
        public static bool ContieneErroreCorrispondente(this corrispondenti dataSet)
        {
            return dataSet.Tables["errore"] != null && dataSet.Tables["errore"].Rows.Count > 0;
        }

        public static string GetDescrizioneErroreCorrispondente(this corrispondenti dataSet)
        {
            return dataSet.ContieneErroreCorrispondente() ? dataSet.Tables["errore"].Rows[0]["descrizione"].ToString() : String.Empty;
        }
    }
}
