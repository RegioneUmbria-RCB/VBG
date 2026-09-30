using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    public class NumeroProtocollo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public NumeroProtocollo(int numeroProtocollo)
        {
            this.Parametro.Add("PRCORE03_Numero", numeroProtocollo.ToString());
        }
    }
}
