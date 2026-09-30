using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Legacy
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_SIGEPRO il 26/08/2014 17.28.01
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Viene utilizzato solo per configurare alcuni parametri del protocollo SiGePro, l'attivazione del protocollo avviene aggiungendo il software PR in comuni security. E' il sistema di protocollazione proprietario di In.i.t. e presente al momento solamente al Comune di Firenze.
    /// </summary>
    public partial class VerticalizzazioneProtocolloSigepro : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_SIGEPRO";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloSigepro()
        {

        }

        public VerticalizzazioneProtocolloSigepro(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Codice dell'amministrazione
        /// </summary>
        public string Codiceamministrazione
        {
            get { return this.GetString("CODICEAMMINISTRAZIONE"); }
            set { this.SetString("CODICEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// Codice dell'AOO
        /// </summary>
        public string Codiceaoo
        {
            get { return this.GetString("CODICEAOO"); }
            set { this.SetString("CODICEAOO", value); }
        }

        /// <summary>
        /// Permette di impostare l'oggetto di default che viene visualizzato in fase di inserimento di un protocollo per l'inserimento di un movimento SiGePro
        /// </summary>
        public string DefOggettoMovimenti
        {
            get { return this.GetString("DEF_OGGETTO_MOVIMENTI"); }
            set { this.SetString("DEF_OGGETTO_MOVIMENTI", value); }
        }

        /// <summary>
        /// Denominazione dell'amministrazione
        /// </summary>
        public string Denominazioneamministrazione
        {
            get { return this.GetString("DENOMINAZIONEAMMINISTRAZIONE"); }
            set { this.SetString("DENOMINAZIONEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// Denominazione dell'AOO
        /// </summary>
        public string Denominazioneaoo
        {
            get { return this.GetString("DENOMINAZIONEAOO"); }
            set { this.SetString("DENOMINAZIONEAOO", value); }
        }

        /// <summary>
        /// Indirizzo telematico
        /// </summary>
        public string Indirizzotelematico
        {
            get { return this.GetString("INDIRIZZOTELEMATICO"); }
            set { this.SetString("INDIRIZZOTELEMATICO", value); }
        }


    }
}
