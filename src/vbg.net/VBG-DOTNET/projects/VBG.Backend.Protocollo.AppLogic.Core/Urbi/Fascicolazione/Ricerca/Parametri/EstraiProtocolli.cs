using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{

    internal class EstraiProtocolli : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public EstraiProtocolli(bool estraiProtocolli)
        {
            this.Parametro.Add("PRCORE03_99991014_EstraiProtocolli", estraiProtocolli ? "S" : "");
        }
    }
}
