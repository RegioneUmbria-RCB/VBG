using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class NumeroSottoFascicoloA : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public NumeroSottoFascicoloA(string numeroSottoFascicolo)
        {
            this.Parametro.Add("PRCORE03_99991014_NumeroSottoFascicoloA", numeroSottoFascicolo);
        }
    }
}
