using System;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione
{
    public class EsitoProtocollazioneCommissione : IEstremiProtocollazioneCommissione
    {
        public static EsitoProtocollazioneCommissione Fallito(Exception ex)
        {
            return new EsitoProtocollazioneCommissione
            {
                Esito = false,
                MessaggioErrore = ex.Message,
                ErroreEsteso = ex.ToString()
            };
        }

        public static EsitoProtocollazioneCommissione Fallito(string errore, string erroreEsteso)
        {
            return new EsitoProtocollazioneCommissione
            {
                Esito = false,
                MessaggioErrore = errore,
                ErroreEsteso = erroreEsteso
            };
        }

        public static EsitoProtocollazioneCommissione Riuscito(string idProtocollo, string numeroProtocollo, string dataProtocollo)
        {
            return new EsitoProtocollazioneCommissione
            {
                Esito = true,
                DataProtocollo = dataProtocollo,
                NumeroProtocollo = numeroProtocollo,
                IdProtocollo = idProtocollo
            };
        }

        private EsitoProtocollazioneCommissione()
        {

        }

        public bool Esito { get; private set; } = false;

        public string MessaggioErrore { get; private set; } = "Errore non disponibile";

        public string ErroreEsteso { get; private set; } = "Errore esteso non disponbile";

        public string IdProtocollo { get; private set; } = "";

        public string DataProtocollo { get; private set; } = "";

        public string NumeroProtocollo { get; private set; } = "";
    }
}