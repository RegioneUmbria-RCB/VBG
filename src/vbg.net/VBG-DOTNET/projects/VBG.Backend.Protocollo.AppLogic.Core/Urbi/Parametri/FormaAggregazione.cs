using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    internal class FormaAggregazione : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public FormaAggregazione(int? formaAggregazione)
        {
            if (!formaAggregazione.HasValue)
            {
                return;
            }

            this.Parametro.Add("PRCORE03_FormaAggregazione", formaAggregazione.ToString());
        }
    }
}
