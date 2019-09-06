
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
    /// 
    /// </summary>
    public partial class VerticalizzazioneProtocolloArchiflow : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_ARCHIFLOW";

        public VerticalizzazioneProtocolloArchiflow()
        {

        }

        public VerticalizzazioneProtocolloArchiflow(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        /// <summary>
        /// 
        /// </summary>
        public string CodiceEnte
        {
            get { return GetString("CODICE_ENTE"); }
            set { SetString("CODICE_ENTE", value); }
        }

        /// <summary>
        /// 
        /// </summary>
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


    }
}
