using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ApSystems.DataSetApSystems.Corrispondenti.InsertCorrispondente;

namespace Init.SIGePro.Protocollo.ApSystems.DataSetApSystems
{
    public static class DataSetCorrispondenteExtensions
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
