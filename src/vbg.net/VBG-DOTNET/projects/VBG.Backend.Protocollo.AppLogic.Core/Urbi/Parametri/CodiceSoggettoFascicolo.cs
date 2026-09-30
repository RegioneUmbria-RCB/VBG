using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    internal class CodiceSoggettoFascicolo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public CodiceSoggettoFascicolo(string codiceSogggetto)
        {
            this.Parametro.Add("PRCORE03_CodiceSoggettoFascicolo", codiceSogggetto);
        }
    }
}
