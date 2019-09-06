using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public partial class VerticalizzazioneProtocolloUrbi : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_URBI";

        public VerticalizzazioneProtocolloUrbi()
        {
                
        }

        public VerticalizzazioneProtocolloUrbi(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string Username
        {
            get { return GetString("USERNAME"); }
            set { SetString("USERNAME", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string Aoo
        {
            get { return GetString("AOO"); }
            set { SetString("AOO", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string ReplaceTitolario
        {
            get { return GetString("REPLACE_TITOLARIO"); }
            set { SetString("REPLACE_TITOLARIO", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string InvioPec
        {
            get { return GetString("INVIO_PEC"); }
            set { SetString("INVIO_PEC", value); }
        }

        public string DestinatarioCoAutomatici
        {
            get { return GetString("DEST_UTENTI_CO_AUTOMATICI"); }
            set { SetString("DEST_UTENTI_CO_AUTOMATICI", value); }
        }
    }
}
