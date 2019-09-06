
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se 1 indica che è attivo l'allineamento dell'anagrafe dell'Unione della Romagna Forlivese
    /// </summary>
    public partial class VerticalizzazioneAllineamentoAnagrafeForlivese : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "ALLINEAMENTO_ANAGRAFE_FORLIV";

        public VerticalizzazioneAllineamentoAnagrafeForlivese()
        {

        }

        public VerticalizzazioneAllineamentoAnagrafeForlivese(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// Stringa di connessione della vista da interrogare
        /// </summary>
        public string Connectionstring
        {
            get { return GetString("CONNECTIONSTRING"); }
            set { SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// Owner della vista da interrogare
        /// </summary>
        public string Owner
        {
            get { return GetString("OWNER"); }
            set { SetString("OWNER", value); }
        }

        /// <summary>
        /// Provider della vista da interrogare
        /// </summary>
        public string Provider
        {
            get { return GetString("PROVIDER"); }
            set { SetString("PROVIDER", value); }
        }

        /// <summary>
        /// Nome della vista da interrogare
        /// </summary>
        public string View
        {
            get { return GetString("VIEW"); }
            set { SetString("VIEW", value); }
        }

        /// <summary>
        /// Indicare da quale data deve essere effettuato l''aggiornamento, la data si riferisce al campo DATA_ULTIMA_VARIAZ della vista esposta nel parametro VIEW. Tale campo si riferisce quando è stata fatta l''ultima modifica dell''anagrafica, questo parametro va a filtrare proprio quella data. Il formato deve essere dd/MM/yyyy
        /// </summary>
        public string DataUltimaVariazioneDa
        {
            get { return GetString("DATA_AGGIORNAMENTO_DA"); }
            set { SetString("DATA_AGGIORNAMENTO_DA", value); }
        }

        /// <summary>
        /// Indicare a quale data deve essere effettuato l''aggiornamento, la data si riferisce al campo DATA_ULTIMA_VARIAZ della vista esposta nel parametro VIEW. Tale campo si riferisce quando è stata fatta l''ultima modifica dell''anagrafica, questo parametro va a filtrare proprio quella data. Il formato deve essere dd/MM/yyyy
        /// </summary>
        public string DataUltimaVariazioneA
        {
            get { return GetString("DATA_AGGIORNAMENTO_A"); }
            set { SetString("DATA_AGGIORNAMENTO_A", value); }
        }

        /// <summary>
        /// Indicare su quanti giorni indietro (dalla data odierna) deve essere fatto l''aggiornamento in base alle ultime variazioni (campo DATA_ULTIMA_VARIAZ della vista indicata nel parametro VIEW). Non indicare, o indicare 0 se si vuole controllare solo la data odierna.
        /// </summary>
        public string NumeroGiorniIndietroUltimaVariazione
        {
            get { return GetString("NUM_GIORNI_AGGIORNAMENTO"); }
            set { SetString("NUM_GIORNI_AGGIORNAMENTO", value); }
        }

    }
}
