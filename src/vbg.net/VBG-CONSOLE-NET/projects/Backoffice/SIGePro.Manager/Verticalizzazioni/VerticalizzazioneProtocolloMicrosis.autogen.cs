using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneProtocolloMicrosis : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_MICROSIS";

        public VerticalizzazioneProtocolloMicrosis(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) 
        { 
        
        }

        /// <summary>
        /// Indicare in questo parametro la url relativa all'endpoit del web service di protocollazione MICROSIS.
        /// </summary>
        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }

        /// <summary>
        /// Indicare in questo parametro lo username facente parte delle credenziali di accesso per creare un protocollo, più in generale per poter utilizzare i metodi esposti dal web service MICROSIS.
        /// </summary>
        public string Username
        {
            get { return GetString("USERNAME"); }
            set { SetString("USERNAME", value); }
        }

        /// <summary>
        /// Indicare in questo parametro la password relativa all'utente specificato nel parametro USERNAME facente parte delle credenziali di accesso per creare un protocollo, più in generale per poter utilizzare i metodi esposti dal web service MICROSIS.
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

    }
}
