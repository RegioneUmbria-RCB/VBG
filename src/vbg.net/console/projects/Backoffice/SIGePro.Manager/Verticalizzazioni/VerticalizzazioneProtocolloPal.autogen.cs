using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public partial class VerticalizzazioneProtocolloPal : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_PAL";

        public VerticalizzazioneProtocolloPal(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        /// <summary>
        /// Codice aoo dell'ente facente parte delle credenziali per poter accedere alle funzionalità esposte dal web service. Parametro obbligatorio che serve per poter ottenere il token generato dalla chiamata a creaToken del servizio REST, che successivamente sarà passato nell'intestazione delle altre chiamate a web service. Il parametro, nella chiamata a creaToken è denominato -codaoo-, ed è a tutti gli effetti il codice ipa ufficiale dell'ente (http://www.indicepa.gov.it/).
        /// </summary>
        public string CodiceAoo
        {
            get { return GetString("CODICE_AOO"); }
            set { SetString("CODICE_AOO", value); }
        }

        /// <summary>
        /// Codice istat dell'ente facente parte delle credenziali per poter accedere alle funzionalità esposte dal web service. Parametro obbligatorio che serve per poter ottenere il token generato dalla chiamata a creaToken del servizio REST, che successivamente sarà passato nell'intestazione delle altre chiamate a web service. Il parametro, nella chiamata a creaToken è denominato -codente-, ma è a tutti gli effetti il codice istat dell'ente.
        /// </summary>
        public string CodiceIstat
        {
            get { return GetString("CODICE_ISTAT"); }
            set { SetString("CODICE_ISTAT", value); }
        }

        /// <summary>
        /// Codice dell'utente facente parte delle credenziali per poter accedere alle funzionalità esposte dal web service. Parametro obbligatorio che serve per poter ottenere il token generato dalla chiamata a creaToken del servizio REST, che successivamente sarà passato nell'intestazione delle altre chiamate a web service. Il parametro, nella chiamata a creaToken è denominato -codute-.
        /// </summary>
        public string Username
        {
            get { return GetString("USERNAME"); }
            set { SetString("USERNAME", value); }
        }

        /// <summary>
        /// Indica la url di base del servizio REST del protocollo PAL, non va quindi indicato il metodo in questo parametro che sarà aggiunto direttamente da codice, ad esempio nell'ambiente di test il valore è http://cw2.gruppoapra.com/cw2/services/
        /// </summary>
        public string UrlBase
        {
            get { return GetString("URL_BASE"); }
            set { SetString("URL_BASE", value); }
        }

        /// <summary>
        /// Password dell'utente facente parte delle credenziali per poter accedere alle funzionalità esposte dal web service. Parametro obbligatorio che serve per poter ottenere il token generato dalla chiamata a creaToken del servizio REST, che successivamente sarà passato nell'intestazione delle altre chiamate a web service. Il parametro, nella chiamata a creaToken è denominato -password-. Il valore di questo parametro dovrà poi essere codificato in base64 e poi facendo un urlencode.
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Valorizzare a 1 se si desidera utilizzare la funzionalità di invio pec per i protocolli in partenza, qualsiasi altro valore se non si vuole utilizzarla. NB. tale funzionalità è stata introdotta in un secondo momento dal fornitore di protocollo PAL Informatica, quindi verificare, sempre con il fornitore, se tale funzionalità è abilitata o meno, al caso non valorizzare a 1 questo parametro.
        /// </summary>
        public string InvioPec
        {
            get { return GetString("INVIOPEC"); }
            set { SetString("INVIOPEC", value); }
        }
    }
}
