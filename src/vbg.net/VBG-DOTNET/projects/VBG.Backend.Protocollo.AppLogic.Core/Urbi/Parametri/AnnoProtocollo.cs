using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    public class AnnoProtocollo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public AnnoProtocollo(int annoProtocollo)
        {
            this.Parametro.Add("PRCORE03_Anno", annoProtocollo.ToString());
        }
    }
}
