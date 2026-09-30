using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneAccessoAgliAtti : Verticalizzazione
    {
        private static class Constants
        {
            public const string NOME_VERTICALIZZAZIONE = "ACCESSO_AGLI_ATTI";
            public const string ArMostraDatiMovimenti = "AR_MOSTRA_DATI_MOVIMENTI";
        }

        public override string NomeVerticalizzazione => Constants.NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneAccessoAgliAtti() : base() { }

        public VerticalizzazioneAccessoAgliAtti(string idComuneAlias, string software) : base(idComuneAlias, Constants.NOME_VERTICALIZZAZIONE, software) { }

        public bool ArMostraDatiMovimenti => this.GetStringOrDefault(Constants.ArMostraDatiMovimenti, "0") == "1";
    }
}
