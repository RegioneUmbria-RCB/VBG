
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione SIT_ESC il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se 1 indica che il sit ESC è attivo.
    /// </summary>
    public partial class VerticalizzazioneSitEsc : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SIT_ESC";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneSitEsc()
        {

        }

        public VerticalizzazioneSitEsc(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// Stringa di connessione del db le cui viste vengono interrogate
        /// </summary>
        public string Connectionstring
        {
            get { return this.GetString("CONNECTIONSTRING"); }
            set { this.SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// Provider  del db le cui viste vengono interrogate
        /// </summary>
        public string Provider
        {
            get { return this.GetString("PROVIDER"); }
            set { this.SetString("PROVIDER", value); }
        }

        /// <summary>
        /// Url del web service da invocare
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }
    }
}
