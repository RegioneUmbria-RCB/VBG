
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    public partial class VerticalizzazioneProtocolloEProt : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_EPROT";

        public VerticalizzazioneProtocolloEProt()
        {

        }

        public VerticalizzazioneProtocolloEProt(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        /// <summary>
        /// Parametro che sta ad indicare la url base di dove possono essere invocati i metodi del web service, da notare che il web service è di tipo rest e che viene utilizzata un''autenticazione di tipo basic, questo sta a significare che i metodi vengono inseriti direttamente sulla stringa relativa alla url, ad esempio, se si vuole invocare il metodo di lettura delle tipologie di documento va aggiunto, alla url, "/richiesta_tipi_documento", per il titolario "/richiesta_titolario", per le protocollazioni in entrata e uscita "/richiesta_protocollazione_ingresso" e "/richiesta_protocollazione_uscita, questo comunque viene fatto direttamente dal sistema e non va indicato in questo parametro, dove invece va inserita solamente la url base
        /// </summary>
        public string UrlBase
        {
            get { return GetString("URL_BASE"); }
            set { SetString("URL_BASE", value); }
        }

        /// <summary>
        /// Indicare la username per autenticarsi al web service che utilizza un tipo di autenticazione basic, le credenziali sono riferite agli utenti del protocollo.
        /// </summary>
        public string Username
        {
            get { return GetString("USERNAME"); }
            set { SetString("USERNAME", value); }
        }

        /// <summary>
        /// Indicare la password per autenticarsi al web service che utilizza un tipo di autenticazione basic, le credenziali sono riferite agli utenti del protocollo.
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Indicare il codice del registro di protocollo su cui si intende protocollare.
        /// </summary>
        public string Registro
        {
            get { return GetString("REGISTRO"); }
            set { SetString("REGISTRO", value); }
        }

        /// <summary>
        /// Valorizzare a 1 se si intende non inviare la voce di classifica al protocollo. Inoltre, sempre se valorizzato a 1, il titolario non sarà recuperato da web service e qualunque valore sarà impostato (la classifica per il backoffice è un dato obbligatorio da inviare al web service, quindi dovrà comunque essere dato un valore alla classifica per superare la validazione) non sarà inviato.
        /// </summary>
        public string EscludiClassifica
        {
            get { return GetString("ESCLUDI_CLASSIFICA"); }
            set { SetString("ESCLUDI_CLASSIFICA", value); }
        }

        /// <summary>
        /// Indicare, separati da punto e virgola, le estensioni accettate dal sistema di protocollo relative agli allegati. Ad esempio pdf;docx;rtf;
        /// </summary>
        public string EstensioniAllegati
        {
            get { return GetString("ESTENSIONI_ALLEGATI"); }
            set { SetString("ESTENSIONI_ALLEGATI", value); }
        }

    }
}
