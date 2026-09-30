
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_HALLEY il 26/08/2014 17.28.00
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// E' il protocollo gestito dalla ditta Halley Informatica, in teoria, questo protocollo, dovrebbe essere standard docarea, in realtà non lo è in quanto il metodo inserimento descritto dal wsdl è diverso da quello descritto sulle specifiche docarea (è presente un parametro in più), quindi è stato necessario creare una verticalizzazione e relativi parametri uguali a quelli della verticalizzazione PROTOCOLLO_DOCAREA. Un esempio di wsdl si trova sull'ambiente di test di Halley, http://213.82.177.135:4080/HalleySuapDOCAREA/suap/suap.php?wsdl
    /// </summary>
    public partial class VerticalizzazioneProtocolloHalley : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_HALLEY";

        public VerticalizzazioneProtocolloHalley()
        {

        }

        public VerticalizzazioneProtocolloHalley(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// (Obbligatorio) Username di autenticazione al web service del protocollo Halley, è il parametro username che viene passato al web method Login. Deve essere fornito dal fornitore del protocollo
        /// </summary>
        public string Operatore
        {
            get { return GetString("OPERATORE"); }
            set { SetString("OPERATORE", value); }
        }

        /// <summary>
        /// (Obbligatorio) End point del web service per invocare i servizi, ad esempio per il protocollo halley l'ambiente di test è presente su http://213.82.177.135:4080/HalleySuapDOCAREA/suap/suap.php?wsdl
        /// </summary>
        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }

        /// <summary>
        /// Password di autenticazione al web service del protocollo Halley, è il parametro password che viene passato al web method Login. Deve essere fornito dal fornitore del protocollo
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Codice Ente, questo parametro va ad aggiungersi a username e password e serve per l'autenticazione al web service del protocollo Halley, è il parametro codiceente che viene passato al web method Login. Deve essere fornito dal fornitore del protocollo
        /// </summary>
        public string Codiceente
        {
            get { return GetString("CODICEENTE"); }
            set { SetString("CODICEENTE", value); }
        }

        /// <summary>
        /// (Facoltativo) Se valorizzato a 1 va a cercare in automatico, solamente durante la protocollazione da istanza, gli allegati caricati nel movimento di avvio dell'istanza stessa, se esistono questi vengono inviati al protocollo HALLEY.Se valorizzato con valore diverso da 1 o non valorizzato la funzionalità appena descritta non sarà svolta.
        /// </summary>
        public string InviaAllMovAvvio
        {
            get { return GetString("INVIA_ALL_MOV_AVVIO"); }
            set { SetString("INVIA_ALL_MOV_AVVIO", value); }
        }

        /// <summary>
        /// (Facoltativo) Se valorizzato a 1 consente, in fase di protocollazione istanza o movimento da backoffice, di inviare il file segnatura.xml che sarebbe il file che viene inviato al web service per eseguire la protocollazione; se valorizzato a 0 o non valorizzato non esegue nessuna funzionalità.
        /// </summary>
        public string InviaSegnatura
        {
            get { return GetString("INVIA_SEGNATURA"); }
            set { SetString("INVIA_SEGNATURA", value); }
        }

        /// <summary>
        /// (Facoltativo) E' il codice dell'Area Organizzativa Omogenea, va fornito dagli amministratori del protocollo. In precedenza veniva passato un parametro fisso con valore = 'AOO' è stato modificato in quanto in alcuni casi (ad esempio Piacenza e Asp Roma) veniva richiesto un valore diverso. Sulle specifiche HALLEY (che sono le stesse docarea) viene descritto come: viene inizializzato con un valore che identifica l'ambito dell'applicazione.Ad esempio questo codice potrebbe essere utilizzato,per individuare i messaggi provenienti dal portale.Ovviamente si puo’ scegliere come nel caso a) di usare una codifica del tipo P_X dove X e’ il nome dell’applicazione chiamante.Questo valore viene inserito nel file segnatura.xml dentro Intestazione-->Identificatore-->CodiceAOO e dentro Intestazione -->Classifica -->CodiceAOO Se non valorizzato il parametro prenderà il valore di AOO
        /// </summary>
        public string CodiceAoo
        {
            get { return GetString("CODICE_AOO"); }
            set { SetString("CODICE_AOO", value); }
        }

        /// <summary>
        /// (Facoltativo) Specificare il valore del tipo documento allegato ossia quei documenti allegati al protocollo escluso quello principale. Se non valorizzato prenderà il valore = 'Allegato' che era il valore fisso che veniva passato in precedenza. Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento successivamente a quello principale.
        /// </summary>
        public string TipoDocumentoAllegato
        {
            get { return GetString("TIPO_DOCUMENTO_ALLEGATO"); }
            set { SetString("TIPO_DOCUMENTO_ALLEGATO", value); }
        }

        /// <summary>
        /// (Facoltativo) Specificare il valore del tipo documento principale. ossia quel documento principale o di richiesta del protocollo. Se non valorizzato prenderà il valore = 'Principale' che era il valore fisso che veniva passato in precedenza. E' stato parametrizzato perchè a Piacenza viene richiesto un valore specifico che è 'TRAS'. Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento
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
        /// (Facoltativo) Indica l'ufficio di smistamento del protocollo HALLEY, questa voce sarà inserita dentro il file segnatura.xml dentro il nodo <ApplicativoProtocollo> valorizzando un nuovo parametro con nome -uo-. E' facoltativo ma il protocollo GS4 di ADS (Piacenza) lo richiede necessariamente.
        /// </summary>
        public string Uo
        {
            get { return GetString("UO"); }
            set { SetString("UO", value); }
        }

        /// <summary>
        /// Indicare in questo parametro il Codice dell'amministrazione, questo dato andrà a valorizzare gli elementi CODICEAMMINISTRAZIONE della segnatura che viene inviata al metodo protocollazione del web service. Se non valorizzato questo dato verrà preso dal parametro CODICEENTE come succedeva in passato.
        /// </summary>
        public string Codiceamministrazione
        {
            get { return GetString("CODICEAMMINISTRAZIONE"); }
            set { SetString("CODICEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// Inserire qui l'endpoint che indica il web service per fare le chiamate alle funzionalità di recupero dati relativo alle classifiche e uffici
        /// </summary>
        public string UrlWsDizionario
        {
            get { return GetString("URL_WS_DIZIONARIO"); }
            set { SetString("URL_WS_DIZIONARIO", value); }
        }

        /// <summary>
        /// Se = a 1 indica che le classifiche devono essere recuperate tramite web service messo a disposizione dal protocollo halley, altrimenti il sistema funzionerà in maniera standard, ossia mappando le varie classifiche direttamente sull'albero degli interventi. Questo parametro è direttamente legato al parametro URL_WS_DIZIONARIO, infatti se si decide di recuperare le classifiche da web service, deve ovviamente essere indicato anche l'end point del web service
        /// </summary>
        public string UsaWsClassifiche
        {
            get { return GetString("USA_WS_CLASSIFICHE"); }
            set { SetString("USA_WS_CLASSIFICHE", value); }
        }

        /// <summary>
        /// Valori ammessi 0, 1 o null. 
        /// Se valorizzato a 1 il sistema genera, in caso di protocollazione di un'istanza in ingresso, un file xml in base alle seguenti regole:<br> 
        /// - Se nell’istanza c’è l’azienda allora il file da creare deve rispettare l’xsd pubblicato su impresa in un giorno (nome: <cf_piva>-<ddmmyyyy-hh24miss>.SUAP.XML e specifiche "Definizione XSD del modello di riepilogo della pratica SUAP (SUAP.XML).xsd")<br>
        /// - Se nell’istanza non c’è l’azienda allora (nome: <cf_piva>-<ddmmyyyy-hh24miss>.SUE.xml e "Definizione SUE.xsd")<br>
        /// - Se il file già esiste non va creato<br>
        /// - Il file va allegato al protocollo e non deve essere salvato nei documenti dell’istanza.
        /// Se il parametro viene impostato con valori diversi da 1 il sistema continuerà a funzionare senza generare alcun file.
        /// </summary>
        public string GeneraSuapXml
        {
            get { return GetString("GENERA_SUAP_XML"); }
            set { SetString("GENERA_SUAP_XML", value); }
        }

        /// <summary>
        /// Questa funzionalità si attiva solamente se il parametro GENERA_SUAP_XML è valorizzato a 1, va indicata la radice dell'albero dell'edilizia (ad esempio 04, 07 o 0401....) serve per specificare se la pratica è relativa all'edilizia in fase di creazione SUAP o SUEXML. Il sistema infatti, in fase di protocollazione istanza, andrà a verificare se l'intervento specificato ha la stessa radice indicata in questo parametro, in caso positivo si andrà a creare il file SUEXML, altrimenti SUAPXML, in sostanza è la discriminante tra la creazione per il SUAP e il SUE
        /// </summary>
        public string RadiceAlberoEdilizia
        {
            get { return GetString("RADICE_ALBERO_EDILIZIA"); }
            set { SetString("RADICE_ALBERO_EDILIZIA", value); }
        }

        /// <summary>
        /// Serve per limitare al minimo gli errori dovuti al protocollo halley e riguardanti le anagrafiche presenti in modo non univoco 
        /// sulle loro anagrafiche. Il web service di protocollo infatti confronta le anagrafiche che arrivano sulla request 
        /// con quelle di protocollo per NOME e COGNOME, nel caso in cui fossero presenti più di una volta vengono restituiti 
        /// gli errori 108 (Protocollo in Arrivo) e 112 (Protocollo in Partenza). 
        /// Valorizzando a 1 questo parametro, solo nel caso in cui vengono restituiti i due errori appena descritti dal web service, 
        /// il plug in di protocollo riproverà a protocollare aggiungendo al mittente / destinatario anche il codice fiscale / partita iva.
        /// </summary>
        public string InviaCf
        {
            get { return GetString("INVIA_CF"); }
            set { SetString("INVIA_CF", value); }
        }

        /// <summary>
        /// Questo parametro indica se il componente che crea la segnatura 
        /// da inviare come request al web service HALLEY debba inviare tutte le anagrafiche che arrivano dal backoffice 
        /// (valore uguale a 1), oppure solo la prima (non presente, valore vuoto o qualsiasi altro valore diverso da 1).
        /// Questo parametro si rende necessario in quanto le versioni più vecchie di halley seguivano le specifiche DocArea che non consentiva 
        /// l'utilizzo di più di un mittente / destinatario.
        /// </summary>
        public string MultiMittDest
        {
            get { return GetString("MULTI_MITT_DEST"); }
            set { SetString("MULTI_MITT_DEST", value); }
        }

        public string DisabilitaFascicolazione
        {
            get { return GetString("DISABILITA_FASCICOLAZIONE"); }
            set { SetString("DISABILITA_FASCICOLAZIONE", value); }
        }

    }
}
