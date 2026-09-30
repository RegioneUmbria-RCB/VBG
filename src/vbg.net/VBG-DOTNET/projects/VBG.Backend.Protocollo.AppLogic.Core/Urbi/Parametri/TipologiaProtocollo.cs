using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    public class TipologiaProtocollo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public TipologiaProtocollo(string tipologia)
        {
            this.Parametro.Add("PRCORE03_Sezione", tipologia);
        }
    }
}
