using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneProtocolloPrisma : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_PRISMA";

        public VerticalizzazioneProtocolloPrisma(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità Standard DocArea.
        /// </summary>
        public string UrlProtoDocArea
        {
            get { return GetString("URL_PROTO_DOCAREA"); }
            set { SetString("URL_PROTO_DOCAREA", value); }
        }

        /// <summary>
        /// Parametro Username relativo alle credenziali per accedere al web service, quest'utente inoltre è indicato in tutte le altre chiamate esposte da tutti i web services.
        /// </summary>
        public string Username
        {
            get { return GetString("USERNAME"); }
            set { SetString("USERNAME", value); }
        }

        /// <summary>
        /// Parametro Password relativo alle credenziali per accedere al web service
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Parametro CodiceEnte relativo alle credenziali per accedere al web service
        /// </summary>
        public string CodiceEnte
        {
            get { return GetString("CODICEENTE"); }
            set { SetString("CODICEENTE", value); }
        }

        /// <summary>
        /// E' il codice dell'Area Organizzativa Omogenea dell'ente configurato nel sistema di protocollo Prisma deve essere comunicato dall'ente stesso.
        /// </summary>
        public string CodiceAoo
        {
            get { return GetString("CODICEAOO"); }
            set { SetString("CODICEAOO", value); }
        }

        /// <summary>
        /// (Facoltativo) Specificare il valore del tipo documento allegato ossia quei documenti allegati al protocollo escluso quello principale.
        /// Se non valorizzato prenderà il valore = 'Allegato' che era il valore fisso che veniva passato in precedenza. 
        /// Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento successivamente a quello principale.
        /// </summary>
        public string TipoDocumentoAllegato
        {
            get { return GetString("TIPO_DOCUMENTO_ALLEGATO"); }
            set { SetString("TIPO_DOCUMENTO_ALLEGATO", value); }
        }

        /// <summary>
        /// (Facoltativo) Specificare il valore del tipo documento principale. ossia quel documento principale o di richiesta del protocollo.
        /// Se non valorizzato prenderà il valore = 'Principale' che era il valore fisso che veniva passato in precedenza. 
        /// E' stato parametrizzato perchè a Piacenza viene richiesto un valore specifico che è 'TRAS'.
        /// Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento
        /// </summary>
        public string TipoDocumentoPrincipale
        {
            get { return GetString("TIPO_DOCUMENTO_PRINCIPALE"); }
            set { SetString("TIPO_DOCUMENTO_PRINCIPALE", value); }
        }

        /// <summary>
        /// (Facoltativo) Indica il nome dell'applicativo del protocollo, questa voce sarà inserita dentro il file segnatura.xml dentro l'attributo -nome- di <ApplicativoProtocollo/>. NB. Se lasciato vuoto o non attivato prenderà il valore inserito dentro il parametro CODICEENTE
        /// </summary>
        public string ApplicativoProtocollo
        {
            get { return GetString("APPLICATIVO_PROTOCOLLO"); }
            set { SetString("APPLICATIVO_PROTOCOLLO", value); }
        }

        /// <summary>
        /// (Facoltativo) Indica l'ufficio di smistamento del protocollo DOCAREA, questa voce sarà inserita dentro il file segnatura.xml dentro il nodo <ApplicativoProtocollo> valorizzando un nuovo parametro con nome -uo-. E' facoltativo ma il protocollo GS4 di ADS (Piacenza) lo richiede necessariamente.
        /// </summary>
        public string Uo
        {
            get { return GetString("UO"); }
            set { SetString("UO", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità di invio PEC.
        /// </summary>
        public string UrlPec
        {
            get { return GetString("URL_PEC"); }
            set { SetString("URL_PEC", value); }
        }

        /// <summary>
        /// Indicare in questo parametro il codice del registro, questo dato serve soprattutto in fase di lettura di un protocollo, visto che il metodo getProtocollo lo richiede espressamente.
        /// </summary>
        public string TipoRegistro
        {
            get { return GetString("TIPO_REGISTRO"); }
            set { SetString("TIPO_REGISTRO", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità di gestione degli allegati (download, aggiunta....), il web service è denominato Attach Service
        /// </summary>
        public string UrlAllegati
        {
            get { return GetString("URL_ALLEGATI"); }
            set { SetString("URL_ALLEGATI", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità DocArea Extended, tra queste ci sono quelle di fascicolazione e quelle di recupero del titolario.
        /// </summary>
        public string UrlExtended
        {
            get { return GetString("URL_EXTENDED"); }
            set { SetString("URL_EXTENDED", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'unità organizzativa che deve essere usata per azionare la funzionalità di Smistamento e Presa in Carico, tale funzionalità si scatenerà solamente durante le protocollazioni da movimento. Se non valorizzato tali funzionalità non verranno azionate.
        /// </summary>
        public string UoSmistamentoMovimento
        {
            get { return GetString("UO_SMISTAMENTO_MOVIMENTO"); }
            set { SetString("UO_SMISTAMENTO_MOVIMENTO", value); }
        }

        /// <summary>
        /// In questo parametro va indicata la denominazione dell'ente, ad esempio COMUNE DI....Questo parametro, oltre tutto, andrà anche a valorizzare la denominazione del mittente dei protocolli in partenza, che quindi non recupererà alcun dato dai parametri UO e RUOLO delle amministrazioni.
        /// </summary>

        public string DenominazioneEnte
        {
            get { return GetString("DENOMINAZIONEENTE"); }
            set { SetString("DENOMINAZIONEENTE", value); }
        }

        /// <summary>
        /// Se impostato a 1 disabilita la funzionalità di invio PEC per le protocollazioni in partenza
        /// </summary>

        public string DisabilitaInvioPec
        {
            get { return GetString("DISABILITA_INVIOPEC"); }
            set { SetString("DISABILITA_INVIOPEC", value); }
        }

        /// <summary>
        /// Se impostato a 1 disabilita la funzionalità di carico ed eseguito che viene utilizzata per le protocollazioni dei movimenti
        /// </summary>

        public string DisabilitaEseguito
        {
            get { return GetString("DISABILITA_ESEGUITO"); }
            set { SetString("DISABILITA_ESEGUITO", value); }
        }

    }
}
