
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
	public partial class VerticalizzazioneProtocolloAttivo
	{
        /// <summary>
        /// * Obbligatorio. E' il codice amministrazione di SIGePro che ha configurati i parametri del protocollo "unità organizzativa" e/o "ruolo" ed è utilizzata come amministrazione destinataria dei protocolli quali domanda on-line o comunicazioni STC. La configurazione dei parametri "unità organizzativa" e/o "ruolo" dell'amministrazione servono a collegarla appunto all' unità organizzativa del protocollo.
        /// </summary>
        public string Codiceamministrazionedefault
        {
            get { 
                if (String.IsNullOrEmpty(GetString("CODICEAMMINISTRAZIONEDEFAULT")))
                       throw new Exception("IL CODICE AMMINISTRAZIONE NON VALORIZZATO SULL'ALBERO DEGLI INTERVENTI E NEL PARAMETRO CODICEAMMINISTRAZIONEDEFAULT DELLA VERTICALIZZAZIONE (REGOLA) PROTOCOLLO_ATTIVO");
                return GetString("CODICEAMMINISTRAZIONEDEFAULT"); 
            }
            set { SetString("CODICEAMMINISTRAZIONEDEFAULT", value); }
        }
	}
}