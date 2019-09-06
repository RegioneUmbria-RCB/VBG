using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneProtocolloProtInf : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_PROTOINF";

        public VerticalizzazioneProtocolloProtInf(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint a cui fa riferimento il web service di protocollazione.
        /// </summary>
        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }

        /// <summary>
        /// In questo parametro va indicato il codice ente definito nel sistema di protocollo, che fa parte delle credenziali per accedere sia all'applicativo che al web service stesso.
        /// </summary>
        public string CodiceEnte
        {
            get { return GetString("CODICE_ENTE"); }
            set { SetString("CODICE_ENTE", value); }
        }

        /// <summary>
        /// In questo parametro va indicato lo username facente parte delle credenziali fornite dagli amministratori di protocollo, che poi sarà l'utente che eseguirà le operazioni di protocollazione invocandone i metodi dei web services.
        /// </summary>
        public string Username
        {
            get { return GetString("USERNAME"); }
            set { SetString("USERNAME", value); }
        }

        /// <summary>
        /// In questo parametro va indicata la password facente parte delle credenziali fornite dagli amministratori di protocollo e riferita al parametro USERNAME.
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Indircare il codice dell'amministrazione relativo al protocollo, questo dato va recuperato dalla tabella di configurazione di ProtoInf2 (PI_DECODI)
        /// </summary>
        public string CodiceAmministrazione
        {
            get { return GetString("CODICEAMMINISTRAZIONE"); }
            set { SetString("CODICEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// questo dato va recuperato dalla tabella di configurazione di ProtoInf2 (PI_DECODI)
        /// </summary>
        public string CodiceAoo
        {
            get { return GetString("CODICEAOO"); }
            set { SetString("CODICEAOO", value); }
        }

        /// <summary>
        /// Percorso base di dove dovranno essere inseriti in upload i files allegati, il percorso dovrà essere raggiungibile dal backoffice. Successivamente il componente si occuperà di creare una sotto-struttura ad albero utile a distinguere i files stessi, il percorso generato prenderà in consederazione il codice dell'oggetto e sarà "paddato" con 10 zeri, il file successivametne avrà come prefisso il codice dell'oggetto stesso. Ad esempio, codice oggetto 24258, 0000\02\42\58\24258-[nome_file]
        /// </summary>
        public string PathBase
        {
            get { return GetString("PATHBASE"); }
            set { SetString("PATHBASE", value); }
        }

        /// <summary>
        /// Indicare il percorso base visibile dalla macchina di protocollo relativamente ai files inviati dal backoffice.
        /// </summary>
        public string BasePath_Proto
        {
            get { return GetString("BASEPATH_FILES_PROTO"); }
            set { SetString("BASEPATH_FILES_PROTO", value); }
        }

        /// <summary>
        /// Indicare il percorso base visibile dalla macchina di backoffice relativamente ai files inviati dal backoffice
        /// </summary>
        public string BasePath_Local
        {
            get { return GetString("BASEPATH_FILES_LOCAL"); }
            set { SetString("BASEPATH_FILES_LOCAL", value); }
        }

        /// <summary>
        /// Indicare il codice del protocollatore esposto su PROTOINF, il valore è da recuperare nella tabella PI_PERSONA campo PI_PE_DIPENDENTE.
        /// </summary>
        public string Protocollatore
        {
            get { return GetString("PROTOCOLLATORE"); }
            set { SetString("PROTOCOLLATORE", value); }
        }

        /// <summary>
        /// Indicare il codice del proprietario del protocollo esposto su PROTOINF, il valore è da recuperare dalla tabella PI_PERSONA campo PI_PE_PERSONA
        /// </summary>
        public string Proprietario
        {
            get { return GetString("PROPRIETARIO"); }
            set { SetString("PROPRIETARIO", value); }
        }

        /// <summary>
        /// In questo campo va necessariamente indicato il codice dell''amministrazione del backoffice alla quale saranno inviati i dati come destinatario fisso dei protocolli in partenza. In particolare saranno inviati i dati relativi a: DENOMINAZIONE, PEC, INDIRIZZO, UFFICIO (Codice UO). Da notare che solo la denominazione è obbligatoria.
        /// </summary>
        public string CodiceAmministrazioneDestinatario
        {
            get { return GetString("CODAMM_DESTINATARIO"); }
            set { SetString("CODAMM_DESTINATARIO", value); }
        }

        /// <summary>
        /// Se valorizzato a 1 ignorerà qualsiasi valore proveniente dalla classifica, il tutto in quanto tale voce non è obbligatoria nel sistema di protocollo mentre lo è in VBG.
        /// </summary>
        public string IgnoraClassifica
        {
            get { return GetString("IGNORA_CLASSIFICA"); }
            set { SetString("IGNORA_CLASSIFICA", value); }
        }

        /// <summary>
        /// Indicare la credenziale di username per l''accesso in modalità share di rete utile per il passaggio dei file nel percorso indicato nel parametro BASEPATH_FILES_LOCAL
        /// </summary>
        public string NetworkUsername
        {
            get { return GetString("NETWORK_USERNAME"); }
            set { SetString("NETWORK_USERNAME", value); }
        }

        /// <summary>
        /// Indicare la credenziale di password per l''accesso in modalità share di rete utile per il passaggio dei file nel percorso indicato nel parametro BASEPATH_FILES_LOCAL
        /// </summary>
        public string NetworkPassword
        {
            get { return GetString("NETWORK_PASSWORD"); }
            set { SetString("NETWORK_PASSWORD", value); }
        }

        /// <summary>
        /// Indicare la credenziale di password per l''accesso in modalità share di rete utile per il passaggio dei file nel percorso indicato nel parametro BASEPATH_FILES_LOCAL
        /// </summary>
        public string NetworkDomain
        {
            get { return GetString("NETWORK_DOMAIN"); }
            set { SetString("NETWORK_DOMAIN", value); }
        }

        public string CodiciEndoProcedimentiSmistamento
        {
            get { return GetString("CODICI_ENDOPROC_SMISTAMENTI"); }
            set { SetString("CODICI_ENDOPROC_SMISTAMENTI", value); }
        }
    }
}
