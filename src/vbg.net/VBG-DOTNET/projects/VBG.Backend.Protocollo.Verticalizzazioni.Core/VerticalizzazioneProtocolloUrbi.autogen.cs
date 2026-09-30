
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    public partial class VerticalizzazioneProtocolloUrbi : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_URBI";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloUrbi()
        {

        }

        public VerticalizzazioneProtocolloUrbi(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string Username
        {
            get { return this.GetString("USERNAME"); }
            set { this.SetString("USERNAME", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string Aoo
        {
            get { return this.GetString("AOO"); }
            set { this.SetString("AOO", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string ReplaceTitolario
        {
            get { return this.GetString("REPLACE_TITOLARIO"); }
            set { this.SetString("REPLACE_TITOLARIO", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string InvioPec
        {
            get { return this.GetString("INVIO_PEC"); }
            set { this.SetString("INVIO_PEC", value); }
        }

        public string DestinatarioCoAutomatici
        {
            get { return this.GetString("DEST_UTENTI_CO_AUTOMATICI"); }
            set { this.SetString("DEST_UTENTI_CO_AUTOMATICI", value); }
        }

        public string UsaUfficioDestinatarioPartenza
        {
            get { return this.GetString("USA_UFFICIO_DESTINATARIO_PARTENZA"); }
            set { this.SetString("USA_UFFICIO_DESTINATARIO_PARTENZA", value); }
        }

        public string AttivaFascicolazioneContestuale
        {
            get { return this.GetString("ATTIVA_FASC_CONTESTUALE"); }
            set { this.SetString("ATTIVA_FASC_CONTESTUALE", value); }
        }

        public string TipoInvioPEC
        {
            get { return GetString("TIPO_INVIO_PEC"); }
            set { SetString("TIPO_INVIO_PEC", value); }
        }

        public string NoAvvioIter
        {
            get { return GetString("NO_AVVIO_ITER"); }
            set { SetString("NO_AVVIO_ITER", value); }
        }

        public string DataSwitch
        {
            get { return GetString("DATA_SWITCH"); }
            set { SetString("DATA_SWITCH", value); }
        }

        public string IdFascicoloGenerico
        {
            get { return GetString("ID_FASCICOLO_GENERICO"); }
            set { SetString("ID_FASCICOLO_GENERICO", value); }
        }
    }
}
