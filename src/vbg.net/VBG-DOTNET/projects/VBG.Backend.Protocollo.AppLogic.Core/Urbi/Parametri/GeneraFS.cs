using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    internal class GeneraFS : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public GeneraFS(bool generaFS)
        {
            this.Parametro.Add("PRCORE03_GeneraFS", generaFS ? "S" : "A");
        }
    }
}