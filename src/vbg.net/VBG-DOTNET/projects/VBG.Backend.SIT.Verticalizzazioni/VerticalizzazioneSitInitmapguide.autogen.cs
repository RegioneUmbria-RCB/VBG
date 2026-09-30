
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione SIT_INITMAPGUIDE il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se 1 indica che il sit INIT_MAPGUIDE è attivo.
    /// </summary>
    public partial class VerticalizzazioneSitInitmapguide : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SIT_INITMAPGUIDE";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneSitInitmapguide()
        {

        }

        public VerticalizzazioneSitInitmapguide(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// Stringa di connessione del db le cui tabelle vengono interrogate
        /// </summary>
        public string Connectionstring
        {
            get { return this.GetString("CONNECTIONSTRING"); }
            set { this.SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// Provider  del db le cui tabelle vengono interrogate
        /// </summary>
        public string Provider
        {
            get { return this.GetString("PROVIDER"); }
            set { this.SetString("PROVIDER", value); }
        }

        /// <summary>
        /// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da codice viario e civico
        /// </summary>
        public string Urlcartstradario
        {
            get { return this.GetString("URLCARTSTRADARIO"); }
            set { this.SetString("URLCARTSTRADARIO", value); }
        }
    }
}
