using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Verticalizzazioni
{
    public partial class VerticalizzazioneProtocolloItCity : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_ITCITY";

        public VerticalizzazioneProtocolloItCity()
        {

        }

        public VerticalizzazioneProtocolloItCity(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        /// <summary>
        /// Endpoint del web service di It City
        /// </summary>
        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }

        /// <summary>
        /// Username relativo alle credenziali per autenticarsi al web service, da non confondere con gli utenti del protocollo.
        /// </summary>
        public string Username
        {
            get { return GetString("USERNAME"); }
            set { SetString("USERNAME", value); }

        }

        /// <summary>
        /// Password relativa alle credenziali per autenticarsi al web service, da non confondere con gli utenti del protocollo.
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Indica il registro di protocollazione, se non valorizzato il web service lo imposterà, di default, a PG (Protocollo Generale), questo su tutte le operazioni che richiedono questo parametro.
        /// </summary>
        public string Sigla
        {
            get { return GetString("SIGLA"); }
            set { SetString("SIGLA", value); }
        }

    }
}
