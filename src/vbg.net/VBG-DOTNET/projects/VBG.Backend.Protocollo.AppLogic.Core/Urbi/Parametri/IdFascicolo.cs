using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    public class IdFascicolo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public IdFascicolo(int? idFascicolo)
        {
            if (!idFascicolo.HasValue)
            {
                return;
            }

            this.Parametro.Add("PRCORE03_IdFascicolo", idFascicolo.ToString());
        }
    }
}
