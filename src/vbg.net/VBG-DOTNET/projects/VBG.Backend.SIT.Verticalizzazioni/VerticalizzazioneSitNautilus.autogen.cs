
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione SIT_NAUTILUS il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se 1 indica che il sit NAUTILUS è attivo.
    /// </summary>
    public partial class VerticalizzazioneSitNautilus : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SIT_NAUTILUS";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneSitNautilus()
        {

        }

        public VerticalizzazioneSitNautilus(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


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
        /// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da sezione, foglio e particella
        /// </summary>
        public string Urlcartmappale
        {
            get { return this.GetString("URLCARTMAPPALE"); }
            set { this.SetString("URLCARTMAPPALE", value); }
        }

        /// <summary>
        /// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da codice viario e civico
        /// </summary>
        public string Urlcartstradario
        {
            get { return this.GetString("URLCARTSTRADARIO"); }
            set { this.SetString("URLCARTSTRADARIO", value); }
        }

        /// <summary>
        /// E' l'url da utilizzare per richiamare l'applicativo POC a partire da sezione,foglio e particella
        /// </summary>
        public string Urlpocmappale
        {
            get { return this.GetString("URLPOCMAPPALE"); }
            set { this.SetString("URLPOCMAPPALE", value); }
        }

        /// <summary>
        /// E' l'url da utilizzare per richiamare l'applicativo POC a partire da codice viario e civico
        /// </summary>
        public string Urlpocstradario
        {
            get { return this.GetString("URLPOCSTRADARIO"); }
            set { this.SetString("URLPOCSTRADARIO", value); }
        }

        /// <summary>
        /// E' l'url da utilizzare per richiamare l'applicativo RUE a partire da sezione,foglio e particella
        /// </summary>
        public string Urlruemappale
        {
            get { return this.GetString("URLRUEMAPPALE"); }
            set { this.SetString("URLRUEMAPPALE", value); }
        }

        /// <summary>
        /// E' l'url da utilizzare per richiamare l'applicativo RUE a partire da codice viario e civico
        /// </summary>
        public string Urlruestradario
        {
            get { return this.GetString("URLRUESTRADARIO"); }
            set { this.SetString("URLRUESTRADARIO", value); }
        }
    }
}
