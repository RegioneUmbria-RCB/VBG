using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    internal class OggettoFascicolo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public OggettoFascicolo(string oggetto)
        {
            this.Parametro.Add("PRCORE03_OggettoFascicolo", oggetto);
        }
    }
}