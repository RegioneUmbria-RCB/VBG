using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class NumeroPadre : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public NumeroPadre(string numero)
        {
            this.Parametro.Add("PRCORE03_99991014_NumeroFascicoloPadre", numero);
        }
    }
}
