

using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione SIT_7DBTL il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se 1 indica che il sit 7DBTL è attivo.
    /// </summary>
    /// 
    public partial class VerticalizzazioneSit7dbtl : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SIT_7DBTL";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneSit7dbtl()
        {

        }

        public VerticalizzazioneSit7dbtl(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// Stringa di connessione del db le cui viste vengono interrogate o del db collegato tramite dblink alla base dati che ha le viste
        /// </summary>
        public string Connectionstring
        {
            get { return this.GetString("CONNECTIONSTRING"); }
            set { this.SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// Nome del proprietario delle viste/tabelle
        /// </summary>
        public string Owner
        {
            get { return this.GetString("OWNER"); }
            set { this.SetString("OWNER", value); }
        }

        /// <summary>
        /// Provider  del db le cui viste vengono interrogate  o del db collegato tramite dblink alla base dati che ha le viste
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



        public string UrlZoomDaMappale
        {
            get { return this.GetString("URL_ZOOM_DA_MAPPALE"); }
            set { this.SetString("URL_ZOOM_DA_MAPPALE", value); }
        }

        public string UrlZoomDaCivico
        {
            get { return this.GetString("URL_ZOOM_DA_CIVICO"); }
            set { this.SetString("URL_ZOOM_DA_CIVICO", value); }
        }

        public string IgnoraDatiToponomastica
        {
            get { return this.GetString("IGNORA_DATI_TOPONOMASTICA"); }
            set { this.SetString("IGNORA_DATI_TOPONOMASTICA", value); }
        }
    }
}
