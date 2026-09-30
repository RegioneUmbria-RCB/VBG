using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class NumeroDa : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public NumeroDa(string numeroDa)
        {
            this.Parametro.Add("PRCORE03_99991014_NumeroFascicoloDa", numeroDa);
        }
    }
}
