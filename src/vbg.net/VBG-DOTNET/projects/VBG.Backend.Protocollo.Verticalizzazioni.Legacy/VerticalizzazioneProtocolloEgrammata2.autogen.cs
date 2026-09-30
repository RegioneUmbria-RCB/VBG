using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Legacy
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_EGRAMMATA2 il 26/08/2014 17.28.00
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Sistema di protocollazione E-Grammata usato al momento della creazione solamente dal Comune di Ferrara, il fornitore è Engineering. Questa versione sostituisce quella facente riferimento alla verticalizzazione PROTOCOLLO_EGRAMMATA in quanto, quella precedente, non supportava l'invio degli allegati.
    /// </summary>
    public partial class VerticalizzazioneProtocolloEgrammata2 : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_EGRAMMATA2";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloEgrammata2()
        {

        }

        public VerticalizzazioneProtocolloEgrammata2(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Codice ente che richiama il WS, si tratta del codice numerico che identifica la AOO (obbligatorio)
        /// </summary>
        public string Codiceente
        {
            get { return this.GetString("CODICEENTE"); }
            set { this.SetString("CODICEENTE", value); }
        }

        /// <summary>
        /// Indica il codice per conoscenza da utilizzare in fase di protocollazione. Sulla maschera di protocollazione è presente un campo a tendina denominato -Trasmesso per- legato all'anagrafica (Mittente / Destinatario), questo campo viene popolato dai valori presenti nella tabella PROTOCOLLO_MODALITAINVIO, indicando il codice in questo campo, quando l'operatore selezionerà nella tendina -Trasmesso per- il valore con lo stesso codice, l'anagrafica selezionata sarà considerata per conoscenza. Se non valorizzato il destinatario (mittente) del protocollo non sarà mai per conoscenza.
        /// </summary>
        public string CodiceCc
        {
            get { return this.GetString("CODICE_CC"); }
            set { this.SetString("CODICE_CC", value); }
        }

        /// <summary>
        /// Password dell’utente con cui si autentica al WS (obbligatorio)
        /// </summary>
        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Password dell’utente con cui si identifica ed autentica all’Applicazione di Protocollo, lo username viene identificato con il parametro USERAPP (obbligatorio)
        /// </summary>
        public string PasswordUserapp
        {
            get { return this.GetString("PASSWORD_USERAPP"); }
            set { this.SetString("PASSWORD_USERAPP", value); }
        }

        /// <summary>
        /// Cinquina che identifica la postazione dell’utente applicativo che si autentica al Protocollo (cinque numeri separati dal carattere '-' Esempio 92-25-0-0-0).
        /// </summary>
        public string Postazione
        {
            get { return this.GetString("POSTAZIONE"); }
            set { this.SetString("POSTAZIONE", value); }
        }

        /// <summary>
        /// Url relativo al web service che consente leggere i protocolli. In particolare consente di recuperare una lista di protocolli a partire da determinati parametri di ricerca.
        /// </summary>
        public string UrlLeggiproto
        {
            get { return this.GetString("URL_LEGGIPROTO"); }
            set { this.SetString("URL_LEGGIPROTO", value); }
        }

        /// <summary>
        /// Url relativo al web service che consente di protocollare (obbligatorio)
        /// </summary>
        public string UrlProtocollo
        {
            get { return this.GetString("URL_PROTOCOLLO"); }
            set { this.SetString("URL_PROTOCOLLO", value); }
        }

        /// <summary>
        /// UserApp dell’utente con cui si identifica ed autentica all’Applicazione di Protocollo (obbligatorio)
        /// </summary>
        public string Userapp
        {
            get { return this.GetString("USERAPP"); }
            set { this.SetString("USERAPP", value); }
        }

        /// <summary>
        /// Username dell’utente con cui si autentica al WS (obbligatorio)
        /// </summary>
        public string Username
        {
            get { return this.GetString("USERNAME"); }
            set { this.SetString("USERNAME", value); }
        }

        /// <summary>
        /// Indicare qui l'url del web service VBG sviluppato lato java che farà poi la chiamata al web service egrammata di protocollazione. Questo servizio si è reso necessario in quanto i file non possono essere inviati via mime (come richiesto dal web service di egrammata) con componenti .net, quindi viene invocato un web service ponte sviluppato in java che svolge questa funzionalità e invoca il web service di protocollazione.
        /// </summary>
        public string UrlProtoallegati
        {
            get { return this.GetString("URL_PROTOALLEGATI"); }
            set { this.SetString("URL_PROTOALLEGATI", value); }
        }

        /// <summary>
        /// 'Url del web service e-grammata2 che consente di svolgere operazioni con le anagrafiche.
        /// </summary>
        public string UrlLeggiAnagrafiche
        {
            get { return this.GetString("URL_LEGGIANAGRAFICHE"); }
            set { this.SetString("URL_LEGGIANAGRAFICHE", value); }
        }

        /// <summary>
        /// Questo parametro si rende necessario per le protocollazioni in partenza, dove è obbligatorio indicare il fascicolo, con questo parametro si va ad indicare un fascicolo fisso, il formato da inserire è #ANNO#|NUMERO FASCICOLO|NUMERO SOTTO FASCICOLO dove per #ANNO# si intende l'anno corrente (va inserito proprio come indicato), questo è utile per indicare l'anno corrente e per far si che il sistema modifichi automaticamente l'anno al momento del bisogno. Un esempio su come inserire qusto parametro è: #ANNO#|1|0
        /// </summary>
        public string Fascicolo
        {
            get { return this.GetString("FASCICOLO"); }
            set { this.SetString("FASCICOLO", value); }
        }

    }
}
