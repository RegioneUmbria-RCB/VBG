using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
    [DataContract]
    public class RisultatoVerificaAccessoIntervento
    {
        [DataMember(Order = 1)]
        public TipoAccessibilitaIntervento Risultato { get; set; } = TipoAccessibilitaIntervento.NonPubblicato;
        [DataMember(Order = 2)]
        public string MessaggioErrore { get; set; } = "";

        public RisultatoVerificaAccessoIntervento()
        {
        }
        public RisultatoVerificaAccessoIntervento(TipoAccessibilitaIntervento risultato, string messaggioErrore = "")
        {
            this.Risultato = risultato;
            this.MessaggioErrore = messaggioErrore;
        }
    }
}
