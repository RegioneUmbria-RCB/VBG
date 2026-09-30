using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class AnnoFascicolo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public AnnoFascicolo(int? annoFascicolo)
        {
            if (!annoFascicolo.HasValue)
            {
                return;
            }

            this.Parametro.Add("PRCORE03_99991014_Anno", annoFascicolo.ToString());
        }
    }
}
