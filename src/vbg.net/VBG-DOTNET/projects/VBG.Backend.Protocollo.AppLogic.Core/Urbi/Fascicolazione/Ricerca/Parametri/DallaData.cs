using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class DallaData : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public DallaData(DateTime? dallaData)
        {
            if (!dallaData.HasValue)
            {
                return;
            }

            this.Parametro.Add("PRCORE03_99991014_DaData", dallaData.Value.ToString("dd-MM-yyyy"));
        }
    }
}
