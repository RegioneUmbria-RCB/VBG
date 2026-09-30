
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione SIT_QUAESTIOFLORENZIA il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se 1 indica che il sit QUAESTIOFLORENZIA è attivo.
    /// </summary>
    public partial class VerticalizzazioneSitQuaestioflorenzia : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SIT_QUAESTIOFLORENZIA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneSitQuaestioflorenzia() : base()
        {

        }

        public VerticalizzazioneSitQuaestioflorenzia(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// contiene il valore dell’id del ruolo che permette all’operatore che ne fa parte di poter eseguire operazioni di editino sul componente cartografico
        /// </summary>
        public string CodRuoloEditing
        {
            get { return this.GetString("COD_RUOLO_EDITING"); }
            set { this.SetString("COD_RUOLO_EDITING", value); }
        }

        /// <summary>
        /// contiene il valore da assegnare ad ogni livello gestito da SIGePro (verrà configurato per ogni software)
        /// </summary>
        public string LayerKey
        {
            get { return this.GetString("LAYER_KEY"); }
            set { this.SetString("LAYER_KEY", value); }
        }

        /// <summary>
        /// contiene il valore da assegnare al pannello che raccoglie tutti i livelli utili a gestire le procedure che vengono trattate tramite SIGePro (verrà configurato una sola volta per il software TT)
        /// </summary>
        public string PanelKey
        {
            get { return this.GetString("PANEL_KEY"); }
            set { this.SetString("PANEL_KEY", value); }
        }

        /// <summary>
        /// contiene il valore da assegnare al modo con cui rappresentare le attività/pratiche attive di ciascun software. (verrà configurato per ogni software)
        /// </summary>
        public string RendererKeyAttivo
        {
            get { return this.GetString("RENDERER_KEY_ATTIVO"); }
            set { this.SetString("RENDERER_KEY_ATTIVO", value); }
        }

        /// <summary>
        /// contiene il valore da assegnare al modo con cui rappresentare le attività/pratiche cessate di ciascun software. (verrà configurato per ogni software)
        /// </summary>
        public string RendererKeyCessato
        {
            get { return this.GetString("RENDERER_KEY_CESSATO"); }
            set { this.SetString("RENDERER_KEY_CESSATO", value); }
        }

        /// <summary>
        /// Url del web service da invocare
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
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
        /// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da colore e civico
        /// </summary>
        public string Urlcartstradario
        {
            get { return this.GetString("URLCARTSTRADARIO"); }
            set { this.SetString("URLCARTSTRADARIO", value); }
        }

        /// <summary>
        /// Url da utilizzare per evitare problemi di cross-domain con il componente GeoIn
        /// </summary>
        public string UrlProxy
        {
            get { return this.GetString("URL_PROXY"); }
            set { this.SetString("URL_PROXY", value); }
        }
    }
}
