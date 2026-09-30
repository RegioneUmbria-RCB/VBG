using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriScrivaniaEntiTerzi : IParametriConfigurazione
    {
        public readonly bool VerticalizzazioneAttiva;
        public readonly string CodiceSoftwareGestione;

        internal ParametriScrivaniaEntiTerzi(bool verticalizzazioneAttiva, string codiceSoftwareGestione)
        {
            if (verticalizzazioneAttiva && String.IsNullOrEmpty(codiceSoftwareGestione))
            {
                throw new Exception("La funzionalità “Scrivania enti terzi” è attiva ma non è stato configurato il modulo in cui è gestita");
            }

            this.VerticalizzazioneAttiva = verticalizzazioneAttiva;
            this.CodiceSoftwareGestione = codiceSoftwareGestione;
        }
    }
}
