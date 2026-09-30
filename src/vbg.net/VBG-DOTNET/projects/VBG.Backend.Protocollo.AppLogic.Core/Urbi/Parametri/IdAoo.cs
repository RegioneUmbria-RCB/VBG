using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    internal class IdAoo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public IdAoo(string idAOO)
        {
            this.Parametro.Add("PRCORE03_IdAOO", idAOO);
        }
    }
}
