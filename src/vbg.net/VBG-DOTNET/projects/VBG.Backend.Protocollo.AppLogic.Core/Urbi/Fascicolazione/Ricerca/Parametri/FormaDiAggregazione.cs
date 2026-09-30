using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class FormaDiAggregazione : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public FormaDiAggregazione(string codiceFormaAggregazione)
        {
            this.Parametro.Add("PRCORE03_99991014_FormaDiAggregazione", codiceFormaAggregazione);
        }
    }
}
