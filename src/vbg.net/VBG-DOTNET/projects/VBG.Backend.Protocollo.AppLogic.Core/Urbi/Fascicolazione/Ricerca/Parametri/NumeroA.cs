using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class NumeroA : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public NumeroA(string numeroA)
        {
            this.Parametro.Add("PRCORE03_99991014_NumeroFascicoloA", numeroA);
        }
    }
}
