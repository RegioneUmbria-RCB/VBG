using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    internal class IdentificativoFascicolo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public IdentificativoFascicolo(string identificativo)
        {
            this.Parametro.Add("PRCORE03_IdentificativoFascicolo", identificativo);
        }
    }
}
