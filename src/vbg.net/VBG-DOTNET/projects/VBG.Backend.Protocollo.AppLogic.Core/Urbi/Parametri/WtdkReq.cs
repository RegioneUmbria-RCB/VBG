using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    public class WtdkReq : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public WtdkReq(string nomeMetodo)
        {
            this.Parametro.Add("WTDK_REQ", nomeMetodo);
        }

    }
}
