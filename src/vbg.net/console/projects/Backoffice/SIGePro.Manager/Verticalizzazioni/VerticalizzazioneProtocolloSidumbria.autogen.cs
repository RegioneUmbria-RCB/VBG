
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_SIDUMBRIA il 14/01/2015 9.53.20
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// E' il protocollo gestito dalla Regione Umbria con intermediario webred.
    /// </summary>
    public partial class VerticalizzazioneProtocolloSidumbria : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_SIDUMBRIA";

        public VerticalizzazioneProtocolloSidumbria(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        public VerticalizzazioneProtocolloSidumbria()
        {

        }


        /// <summary>
        /// In questo parametro va indicato il codice presente nella MODALITA' di TRASMISSIONE che fa riferimento al destinatario per conoscenza, la tabella di riferimento è PROTOCOLLO_MODALITAINVIO. In questa tabella saranno presenti due valori che saranno poi associati ai destinatari per i protocolli in partenza (questo dato viene ignorato per i protocolli in entrata), se al destinatario viene associato lo stesso codice presente in questo parametro allora significherà che quel determinato destinatario, il protocollo, è per conoscenza.
        /// </summary>
        public string DestinatarioCc
        {
            get { return GetString("DESTINATARIO_CC"); }
            set { SetString("DESTINATARIO_CC", value); }
        }

        /// <summary>
        /// Il Service è uno dei due dati relativi all'autenticazione al web service (l'altro è il Token), deve essere fornito dal fornitore di protocollo, in alternativa dal cliente, sarebbe il codice servizio abilitato, in teoria una specie di Registro.
        /// </summary>
        public string Service
        {
            get { return GetString("SERVICE"); }
            set { SetString("SERVICE", value); }
        }

        /// <summary>
        /// Il Token è uno dei due dati relativi all'autenticazione al web service (l'altro è il Service), deve essere fornito dal fornitore di protocollo, in alternativa dal cliente, sarebbe il codice di autorizzazione.
        /// </summary>
        public string Token
        {
            get { return GetString("TOKEN"); }
            set { SetString("TOKEN", value); }
        }

        /// <summary>
        /// In questo parametro va indicato l'url del web service sidumbria, è possibile indicare anche l'indirizzo del wsdl.
        /// </summary>
        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }


    }
}
