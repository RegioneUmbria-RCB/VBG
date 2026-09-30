

using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneRabbitMQ : Verticalizzazione
    {
        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;
        private static class Constants
        {
            public const string NomeVerticalizzazione = "RABBITMQ";
        }

        public VerticalizzazioneRabbitMQ() : base() { }

        public VerticalizzazioneRabbitMQ(string idComuneAlias) : base(idComuneAlias, Constants.NomeVerticalizzazione, "TT") { }
        
    }
}
