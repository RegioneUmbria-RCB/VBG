using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    internal class TipoDiChiusuraFascicolo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public TipoDiChiusuraFascicolo(int? tipoChiusura)
        {
            if (!tipoChiusura.HasValue)
            {
                return;
            }
            this.Parametro.Add("PRCORE03_TipoDiChiusuraFascicolo", tipoChiusura.ToString());
        }
    }
}
