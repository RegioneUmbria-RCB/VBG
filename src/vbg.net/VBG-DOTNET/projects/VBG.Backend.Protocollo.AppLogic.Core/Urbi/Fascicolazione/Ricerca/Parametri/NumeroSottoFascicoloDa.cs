using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class NumeroSottoFascicoloDa : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public NumeroSottoFascicoloDa(string numeroSottoFascicolo)
        {
            this.Parametro.Add("PRCORE03_99991014_NumeroSottoFascicoloDa", numeroSottoFascicolo);
        }
    }
}
