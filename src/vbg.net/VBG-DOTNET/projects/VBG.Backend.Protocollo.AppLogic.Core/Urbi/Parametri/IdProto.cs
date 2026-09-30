using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    public class IdProto : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public IdProto(int? idProtocollo)
        {
            if (!idProtocollo.HasValue)
            {
                return;
            }

            this.Parametro.Add("PRCORE03_IdProto", idProtocollo.ToString());
        }
    }
}
