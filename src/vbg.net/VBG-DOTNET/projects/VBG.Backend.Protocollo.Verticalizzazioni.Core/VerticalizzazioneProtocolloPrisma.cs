using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    public class VerticalizzazioneProtocolloPrisma : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_PRISMA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloPrisma()
        {

        }

        public VerticalizzazioneProtocolloPrisma(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità Standard DocArea.
        /// </summary>
        public string UrlProtoDocArea
        {
            get { return this.GetString("URL_PROTO_DOCAREA"); }
            set { this.SetString("URL_PROTO_DOCAREA", value); }
        }

        /// <summary>
        /// Parametro Username relativo alle credenziali per accedere al web service, quest'utente inoltre è indicato in tutte le altre chiamate esposte da tutti i web services.
        /// </summary>
        public string Username
        {
            get { return this.GetString("USERNAME"); }
            set { this.SetString("USERNAME", value); }
        }

        /// <summary>
        /// Parametro Password relativo alle credenziali per accedere al web service
        /// </summary>
        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Parametro CodiceEnte relativo alle credenziali per accedere al web service
        /// </summary>
        public string CodiceEnte
        {
            get { return this.GetString("CODICEENTE"); }
            set { this.SetString("CODICEENTE", value); }
        }

        /// <summary>
        /// E' il codice dell'Area Organizzativa Omogenea dell'ente configurato nel sistema di protocollo Prisma deve essere comunicato dall'ente stesso.
        /// </summary>
        public string CodiceAoo
        {
            get { return this.GetString("CODICEAOO"); }
            set { this.SetString("CODICEAOO", value); }
        }

        /// <summary>
        /// (Facoltativo) Specificare il valore del tipo documento allegato ossia quei documenti allegati al protocollo escluso quello principale.
        /// Se non valorizzato prenderà il valore = 'Allegato' che era il valore fisso che veniva passato in precedenza. 
        /// Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento successivamente a quello principale.
        /// </summary>
        public string TipoDocumentoAllegato
        {
            get { return this.GetString("TIPO_DOCUMENTO_ALLEGATO"); }
            set { this.SetString("TIPO_DOCUMENTO_ALLEGATO", value); }
        }

        /// <summary>
        /// (Facoltativo) Specificare il valore del tipo documento principale. ossia quel documento principale o di richiesta del protocollo.
        /// Se non valorizzato prenderà il valore = 'Principale' che era il valore fisso che veniva passato in precedenza. 
        /// E' stato parametrizzato perchè a Piacenza viene richiesto un valore specifico che è 'TRAS'.
        /// Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento
        /// </summary>
        public string TipoDocumentoPrincipale
        {
            get { return this.GetString("TIPO_DOCUMENTO_PRINCIPALE"); }
            set { this.SetString("TIPO_DOCUMENTO_PRINCIPALE", value); }
        }

        /// <summary>
        /// (Facoltativo) Indica il nome dell'applicativo del protocollo, questa voce sarà inserita dentro il file segnatura.xml dentro l'attributo -nome- di <ApplicativoProtocollo/>. NB. Se lasciato vuoto o non attivato prenderà il valore inserito dentro il parametro CODICEENTE
        /// </summary>
        public string ApplicativoProtocollo
        {
            get { return this.GetString("APPLICATIVO_PROTOCOLLO"); }
            set { this.SetString("APPLICATIVO_PROTOCOLLO", value); }
        }

        /// <summary>
        /// (Facoltativo) Indica l'ufficio di smistamento del protocollo DOCAREA, questa voce sarà inserita dentro il file segnatura.xml dentro il nodo <ApplicativoProtocollo> valorizzando un nuovo parametro con nome -uo-. E' facoltativo ma il protocollo GS4 di ADS (Piacenza) lo richiede necessariamente.
        /// </summary>
        public string Uo
        {
            get { return this.GetString("UO"); }
            set { this.SetString("UO", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità di invio PEC.
        /// </summary>
        public string UrlPec
        {
            get { return this.GetString("URL_PEC"); }
            set { this.SetString("URL_PEC", value); }
        }

        /// <summary>
        /// Indicare in questo parametro il codice del registro, questo dato serve soprattutto in fase di lettura di un protocollo, visto che il metodo getProtocollo lo richiede espressamente.
        /// </summary>
        public string TipoRegistro
        {
            get { return this.GetString("TIPO_REGISTRO"); }
            set { this.SetString("TIPO_REGISTRO", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità di gestione degli allegati (download, aggiunta....), il web service è denominato Attach Service
        /// </summary>
        public string UrlAllegati
        {
            get { return this.GetString("URL_ALLEGATI"); }
            set { this.SetString("URL_ALLEGATI", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint a cui fa riferimento il web service di protocollazione relativamente alle funzionalità DocArea Extended, tra queste ci sono quelle di fascicolazione e quelle di recupero del titolario.
        /// </summary>
        public string UrlExtended
        {
            get { return this.GetString("URL_EXTENDED"); }
            set { this.SetString("URL_EXTENDED", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'unità organizzativa che deve essere usata per azionare la funzionalità di Smistamento e Presa in Carico, tale funzionalità si scatenerà solamente durante le protocollazioni da movimento. Se non valorizzato tali funzionalità non verranno azionate.
        /// </summary>
        public string UoSmistamentoMovimento
        {
            get { return this.GetString("UO_SMISTAMENTO_MOVIMENTO"); }
            set { this.SetString("UO_SMISTAMENTO_MOVIMENTO", value); }
        }

        /// <summary>
        /// In questo parametro va indicata la denominazione dell'ente, ad esempio COMUNE DI....Questo parametro, oltre tutto, andrà anche a valorizzare la denominazione del mittente dei protocolli in partenza, che quindi non recupererà alcun dato dai parametri UO e RUOLO delle amministrazioni.
        /// </summary>

        public string DenominazioneEnte
        {
            get { return this.GetString("DENOMINAZIONEENTE"); }
            set { this.SetString("DENOMINAZIONEENTE", value); }
        }

        /// <summary>
        /// Se impostato a 1 disabilita la funzionalità di invio PEC per le protocollazioni in partenza
        /// </summary>

        public string DisabilitaInvioPec
        {
            get { return this.GetString("DISABILITA_INVIOPEC"); }
            set { this.SetString("DISABILITA_INVIOPEC", value); }
        }

        /// <summary>
        /// Se impostato a 1 disabilita la funzionalità di carico ed eseguito che viene utilizzata per le protocollazioni dei movimenti
        /// </summary>

        public string DisabilitaEseguito
        {
            get { return this.GetString("DISABILITA_ESEGUITO"); }
            set { this.SetString("DISABILITA_ESEGUITO", value); }
        }

        /// <summary>
        /// Parametro che indica se un protocollo in ARRIVO deve essere smistato in automatico, invocando la chiamata smistamentoAction facendo inoltre l''eseguito, se impostato a 1 allora verrà attivata tale funzionalità altrimenti no.
        /// </summary>

        public string AttivaSmistamentoArrivo
        {
            get { return this.GetString("ATTIVA_SMISTAMENTO_ARRIVO"); }
            set { this.SetString("ATTIVA_SMISTAMENTO_ARRIVO", value); }
        }

        /// <summary>
        /// Parametro che indica se deve essere valorizzato il parametro dataDocumento, in alcune installazioni il parametro è opbbligatorio, nel caso specifico comunque può essere evitato di essere compilato facendo impostare, lato protocollo, il parametro DATA_ARRIVO_OB_1 a N. Se impostato a 1 riporterà la data di protocollazione corrente nel formato dd/MM/yyyy.
        /// </summary>

        public string ValorizzaDataDocumento
        {
            get { return this.GetString("VALORIZZA_DATADOCUMENTO"); }
            set { this.SetString("VALORIZZA_DATADOCUMENTO", value); }
        }

        /// <summary>
        /// Se impostato a 1 consente di disabilitare il recupero dei valori di classificazione tramite ws.
        /// </summary>

        public string DisabilitaWsClassifiche
        {
            get { return this.GetString("DISABILITA_WS_CLASSIFICHE"); }
            set { this.SetString("DISABILITA_WS_CLASSIFICHE", value); }
        }

    }
}
