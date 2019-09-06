using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;

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
                throw new ConfigurationErrorsException("La funzionalità “Scrivania enti terzi” è attiva ma non è stato configurato il modulo in cui è gestita");
            }

            this.VerticalizzazioneAttiva = verticalizzazioneAttiva;
            this.CodiceSoftwareGestione = codiceSoftwareGestione;
        }
    }
}
