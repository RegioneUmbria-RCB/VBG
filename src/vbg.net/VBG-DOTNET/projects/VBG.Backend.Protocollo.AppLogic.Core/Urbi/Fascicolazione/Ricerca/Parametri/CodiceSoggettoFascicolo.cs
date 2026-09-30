using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class CodiceSoggettoFascicolo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public CodiceSoggettoFascicolo(string codiceSoggetto)
        {
            this.Parametro.Add("PRCORE03_99991014_CodiceSoggettoFascicolo", codiceSoggetto);
        }
    }
}
