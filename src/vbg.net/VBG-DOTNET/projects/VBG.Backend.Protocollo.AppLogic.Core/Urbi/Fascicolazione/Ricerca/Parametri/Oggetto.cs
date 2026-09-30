using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class Oggetto : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public Oggetto(string oggetto)
        {
            this.Parametro.Add("PRCORE03_99991014_Oggetto", oggetto);
        }
    }
}
