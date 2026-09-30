using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Data;

namespace SIGePro.Tasks
{
    public partial class TaskSorteggi : TaskBase
    {
        private const string NOME_TASK = "SORTEGGI";

        public TaskSorteggi(DataBase db, string idcomune, int idjob) : base(db, idcomune, idjob) { }

        /// <summary>
        /// E' la data fino alla quale sorteggiare le istanze, fa riferimento alla data presentazione della domanda. 
        /// Se non specificata prende la data dell'esecuzione.
        /// </summary>
        public DateTime? AllaData
        {
            get { return GetDate("ALLA DATA"); }
            set { SetDate("ALLA DATA", value.GetValueOrDefault(DateTime.MinValue)); }
        }

        /// <summary>
        /// I possibili valori sono: 
        /// INT-=arrotonda per difetto; 
        /// INT+=arrotonda per eccesso; 
        /// ROUND=arrotonda all'intero più vicino.
        /// </summary>
        public int? Arrotondamento
        {
            get { return GetInt("ARROTONDAMENTO"); }
            set { SetInt("ARROTONDAMENTO", value.GetValueOrDefault(int.MinValue)); }
        }

        /// <summary>
        /// E' l'amministrazione che effettua il movimento specificato nel parametro CREA MOVIMENTO. 
        /// Nel caso si debba creare un movimento per le istanze sorteggiate e questo parametro non viene impostato,
        /// verrà utilizzata l'amministrazione di comodo specificata nel Back-Office.
        /// </summary>
        public int? CodiceAmministrazione
        {
            get { return GetInt("CODICE AMMINISTRAZIONE"); }
            set { SetInt("CODICE AMMINISTRAZIONE", value.GetValueOrDefault(int.MinValue)); }
        }

        /// <summary>
        /// E' il codice del documento tipo al quale verranno sostituiti i segnaposti con i dati del sorteggio effettuato.ù
        /// Tale documento sarà poi allegato al sorteggio e, se configurato, inviato per email all'indirizzo specificato in configurazione alla voce "MAIL DESTINATARIO".
        /// </summary>
        public int? CodiceDocumentoTipo
        {
            get { return GetInt("CODICE DOCUMENTO TIPO"); }
            set { SetInt("CODICE DOCUMENTO TIPO", value.GetValueOrDefault(int.MinValue)); }
        }

        /// <summary>
        /// E' il codice della Mail Tipo dalla quale vengono recuperati Oggetto e Corpo per creare la mail da inviare automaticamente a fine sorteggio.
        /// </summary>
        public int? CodiceMailTipo
        {
            get { return GetInt("CODICE MAIL TIPO"); }
            set { SetInt("CODICE MAIL TIPO", value.GetValueOrDefault(int.MinValue)); }
        }

        /// <summary>
        /// E' l'operatore che effettua il movimento specificato nel parametro CREA MOVIMENTO. 
        /// Nel caso si debba creare un movimento per le istanze sorteggiate e questo parametro non viene impostato,
        /// verrà utilizzato il codice dell'operatore che ha inserito l'istanza in questione.
        /// </summary>
        public int? CodiceResponsabile
        {
            get { return GetInt("CODICE RESPONSABILE"); }
            set { SetInt("CODICE RESPONSABILE", value.GetValueOrDefault(int.MinValue)); }
        }

        /// <summary>
        /// Se Sigepro è installato in un associazione di comuni va valorizzato con il codice del comune per il quale effettuare la schedulazione.
        /// In caso contrario va lasciato vuoto.
        /// </summary>
        public string CodiceComune
        {
            get { return GetString("CODICECOMUNE"); }
            set { SetString("CODICECOMUNE", value); }
        }

        /// <summary>
        /// E' il TIPO MOVIMENTO da utilizzare per creare un movimento nelle istanze estratte. Se non viene specificato non verrà creato nessun movimento nelle istanze estratte.
        /// </summary>
        public string CreaMovimento
        {
            get { return GetString("CREA MOVIMENTO"); }
            set { SetString("CREA MOVIMENTO", value); }
        }

        /// <summary>
        /// E' la data dalla quale sorteggiare le istanze, fa riferimento alla data presentazione della domanda. Se non specificata viene utilizzata la data dell'ultima estrazione effettuata.
        /// </summary>
        public DateTime? DallaData
        {
            get { return GetDate("DALLA DATA"); }
            set { SetDate("DALLA DATA", value.GetValueOrDefault(DateTime.MinValue)); }
        }

        /// <summary>
        /// Default=1; 
        /// Se 1=non estrae istanze che hanno preso parte ad una precedente estrazione; 
        /// Se 0=non tiene conto delle estrazioni precedenti.
        /// </summary>
        public int? EscludiEstratte
        {
            get { return GetInt("ESCLUDI ESTRATTE"); }
            set { SetInt("ESCLUDI ESTRATTE", value.GetValueOrDefault(int.MinValue)); }
        }

        /// <summary>
        /// Nel caso venga specificato il 'TIPO MOVIMENTO' è l'esito del movimento. 
        /// 1=positivo
        /// 0=Negativo.
        /// </summary>
        public int? Esito
        {
            get { return GetInt("ESITO"); }
            set { SetInt("ESITO", value.GetValueOrDefault(int.MinValue)); }
        }

        /// <summary>
        /// Se specificato l'estrazione avviene a gruppi di istanze; 
        /// Ad es.ogni 10 istanze estrai il 20%, le istanze che non totalizzano un gruppo (su un totale di 32 istanze ne rimangono fuori 2) 
        /// non prenderanno parte all'estrazione.
        /// </summary>
        public int? Gruppi
        {
            get { return GetInt("GRUPPI"); }
            set { SetInt("GRUPPI", value.GetValueOrDefault(int.MinValue)); }
        }

        /// <summary>
        /// Se valorizzato, viene inviata automaticamente una mail a questo indirizzo una volta effettuato il sorteggio.
        /// E' possibile specificare più indirizzi email separandoli con ";".
        /// Per inviare la mail è necessario inoltre configurare anche il parametro CODICE MAIL TIPO con il modello della mail da spedire.
        /// Se al termine di un sorteggio viene generato automaticamente un documento, esso verrà allegato alla mail e spedito al destinatario.
        /// </summary>
        public string MailDestinatario
        {
            get { return GetString("MAIL DESTINATARIO"); }
            set { SetString("MAIL DESTINATARIO", value); }
        }

        /// <summary>
        /// E' la percentuale delle istanze che devono essere sorteggiate. 
        /// Se non speficicata viene utilizzato il 100%.
        /// </summary>
        public double? Percentuale
        {
            get { return GetDouble("PERCENTUALE"); }
            set { SetDouble("PERCENTUALE", value.GetValueOrDefault(double.MinValue)); }
        }

        /// <summary>
        /// E' il modulo sofrware sul quale effettuare il sorteggio.
        /// </summary>
        public string Software
        {
            get { return GetString("SOFTWARE"); }
            set { SetString("SOFTWARE", value); }
        }

        /// <summary>
        /// Indica lo stato delle istanze che devono essere selezionate. 
        /// Può contenere una lista di stati separati da virgola 
        /// Es. AT,CP 
        /// oppure i valori 
        /// 0=tutti gli stati attivi; 
        /// -1=tutti gli stati chiusi negativi; 
        /// 1=tutti gli stati chiusi positivi.
        /// </summary>
        public string Stato
        {
            get { return GetString("STATO"); }
            set { SetString("STATO", value); }
        }

        /// <summary>
        /// Voce dell'albero dei procedimenti da sorteggiare, 
        /// se non viene specificata verranno sorteggiate tutte le voci dell'albero che non hanno la voce "Escludi dal sorteggi" impostata.
        /// Possono essere impostate più voci dell'albero, separando i relativi codici tramite ","
        /// </summary>
        public string TipoIntervento
        {
            get { return GetString("TIPO INTERVENTO"); }
            set { SetString("TIPO INTERVENTO", value); }
        }

        /// <summary>
        /// Se impostato verranno sorteggiate le istanze che hanno il movimento eseguito (verde nell'elaborazione).
        /// </summary>
        public string TipoMovimento
        {
            get { return GetString("TIPO MOVIMENTO"); }
            set { SetString("TIPO MOVIMENTO", value); }
        }

        /// <summary>
        /// Se impostata verranno sorteggiate  le istanze con la procedura specificata.
        /// </summary>
        public int? TipoProcedura
        {
            get { return GetInt("TIPO PROCEDURA"); }
            set { SetInt("TIPO PROCEDURA", value.GetValueOrDefault(int.MinValue)); }
        }
    }
}
