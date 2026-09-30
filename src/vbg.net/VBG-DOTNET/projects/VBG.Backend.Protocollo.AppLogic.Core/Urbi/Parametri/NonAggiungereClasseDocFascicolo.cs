using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    internal class NonAggiungereClasseDocFascicolo : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public NonAggiungereClasseDocFascicolo(bool nonAssegnare)
        {
            this.Parametro.Add("PRCORE03_Non_Aggiungere_Classe_Doc_Fascicolo", nonAssegnare ? "S" : "");
        }
    }
}
