using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class AllaData : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public AllaData(DateTime? allaData)
        {
            if (!allaData.HasValue)
            {
                return;
            }

            this.Parametro.Add("PRCORE03_99991014_AData", allaData.Value.ToString("dd-MM-yyyy"));
        }
    }
}
