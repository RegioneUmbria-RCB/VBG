using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{

    public class EseguiSoloFascicolazione : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public EseguiSoloFascicolazione(bool soloFascicolazione)
        {
            this.Parametro.Add("PRCORE03_EseguiSoloFascicolazione", soloFascicolazione ? "S" : "");
        }
    }
}
