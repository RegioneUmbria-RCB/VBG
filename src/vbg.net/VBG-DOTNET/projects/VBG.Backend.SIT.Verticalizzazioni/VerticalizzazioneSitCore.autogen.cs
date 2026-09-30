
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    public partial class VerticalizzazioneSitCore : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SIT_CORE";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneSitCore()
        {

        }

        public VerticalizzazioneSitCore(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// Stringa di connessione del db le cui viste vengono interrogate o del db collegato tramite dblink alla base dati che ha le viste
        /// </summary>
        public string Connectionstring
        {
            get { return this.GetString("CONNECTIONSTRING"); }
            set { this.SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// Nome del dblink 
        /// </summary>
        public string Dblink
        {
            get { return this.GetString("DBLINK"); }
            set { this.SetString("DBLINK", value); }
        }

        /// <summary>
        /// Nome del proprietario delle viste/tabelle del db a cui punta il dblink
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
        /// Url del web service rest che restituisce i dati relativi al catasto.
        /// </summary>
        public string UrlWsCatasto
        {
            get { return this.GetString("URL_WS_CATASTO"); }
            set { this.SetString("URL_WS_CATASTO", value); }
        }

        /// <summary>
        /// Codice Catastale del comune che effettua la chiamata al servizio SIT.
        /// </summary>
        public string CodiceEnte
        {
            get { return this.GetString("CODICEENTE"); }
            set { this.SetString("CODICEENTE", value); }
        }
    }
}
