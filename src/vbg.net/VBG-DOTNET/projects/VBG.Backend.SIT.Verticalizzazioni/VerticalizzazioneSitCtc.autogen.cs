
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    public partial class VerticalizzazioneSitCtc : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SIT_CTC";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneSitCtc()
        {

        }

        public VerticalizzazioneSitCtc(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// Stringa di connessione del db le cui viste vengono interrogate o del db collegato al server in cui sono presenti le viste
        /// </summary>
        public string Connectionstring
        {
            get { return this.GetString("CONNECTIONSTRING"); }
            set { this.SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// Db in cui sono presenti le viste
        /// </summary>
        public string Database
        {
            get { return this.GetString("DATABASE"); }
            set { this.SetString("DATABASE", value); }
        }

        /// <summary>
        /// Provider  del db le cui viste vengono interrogate  o del db collegato al server in cui sono presenti le viste
        /// </summary>
        public string Provider
        {
            get { return this.GetString("PROVIDER"); }
            set { this.SetString("PROVIDER", value); }
        }

        /// <summary>
        /// Nome del proprietario delle viste
        /// </summary>
        public string Schema
        {
            get { return this.GetString("SCHEMA"); }
            set { this.SetString("SCHEMA", value); }
        }

        /// <summary>
        /// Server in cui sono presenti le viste
        /// </summary>
        public string Server
        {
            get { return this.GetString("SERVER"); }
            set { this.SetString("SERVER", value); }
        }

        /// <summary>
        /// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da sezione, foglio e particella
        /// </summary>
        public string Urlcartmappale
        {
            get { return this.GetString("URLCARTMAPPALE"); }
            set { this.SetString("URLCARTMAPPALE", value); }
        }


    }
}
