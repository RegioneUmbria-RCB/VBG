using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class TipoRicerca : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public TipoRicerca(bool cercaFascicolo)
        {
            this.Parametro.Add("PRCORE03_99991014_FascicoloSottofascicolo", cercaFascicolo ? "F" : "S");
        }
    }
}
