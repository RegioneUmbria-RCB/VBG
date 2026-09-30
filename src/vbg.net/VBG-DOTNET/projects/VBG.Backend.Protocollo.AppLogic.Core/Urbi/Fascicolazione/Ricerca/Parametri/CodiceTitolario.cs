using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class CodiceTitolario : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public CodiceTitolario(string codiceTitolario)
        {
            this.Parametro.Add("PRCORE03_99991014_CodiceTitolario", codiceTitolario);
        }
    }
}
