
namespace VBG.Backend.Protocollo.Verticalizzazioni.Shared
{
    public partial class VerticalizzazioneProtocolloAttivo
    {
        /// <summary>
        /// * Obbligatorio. E' il codice amministrazione di SIGePro che ha configurati i parametri del protocollo "unità organizzativa" e/o "ruolo" ed è utilizzata come amministrazione destinataria dei protocolli quali domanda on-line o comunicazioni STC. La configurazione dei parametri "unità organizzativa" e/o "ruolo" dell'amministrazione servono a collegarla appunto all' unità organizzativa del protocollo.
        /// </summary>
        public string Codiceamministrazionedefault
        {
            get
            {
                if (String.IsNullOrEmpty(this.GetString("CODICEAMMINISTRAZIONEDEFAULT")))
                    throw new Exception("IL CODICE AMMINISTRAZIONE NON VALORIZZATO SULL'ALBERO DEGLI INTERVENTI E NEL PARAMETRO CODICEAMMINISTRAZIONEDEFAULT DELLA VERTICALIZZAZIONE (REGOLA) PROTOCOLLO_ATTIVO");
                return this.GetString("CODICEAMMINISTRAZIONEDEFAULT");
            }
            set { this.SetString("CODICEAMMINISTRAZIONEDEFAULT", value); }
        }

        public IEnumerable<string> EstensioniAmmesse
        {
            get
            {
                var elenco = this.GetString(Constants.EstensioniAmmesse);
                if (String.IsNullOrEmpty(elenco))
                {
                    return new List<string>();
                }


                return elenco.Split(';');
            }
        }

        public bool TrasformaInPdf => this.GetBool(Constants.TrasformaInPDF);
    }
}