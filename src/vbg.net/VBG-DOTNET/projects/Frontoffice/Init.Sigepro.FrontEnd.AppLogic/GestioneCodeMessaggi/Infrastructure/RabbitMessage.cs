namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure
{
    /*
     * "header": {
        "versione": "1.0",
        "alias": "E256",
        "idComune": "E256",
        "software": "SS"
    },
    "body": {
        "provenienza": "DomandaOnLine/Console", // Necessario???
        "idDomanda": 1903,
        "identificativoDomanda": "E256_SS_GRGNCL79C19G478O_1903",
        "ultimaModifica": "2012-04-23T18:25:43.511Z",
        "richiedente": "Mario Rossi",
        "tipoIntervento": "Avvio",
        "oggetto": "",
        "eliminabile": true/false,
        "tags": [
            {
                "tipo": "Info", // "Warning", "Errore"
                "messaggio": "La domanda contiene oneri pagati",
            },
            {
                "tipo": "Info", // "Warning", "Errore"
                "messaggio": "La domanda contiene pagamenti in sospeso",
            }
        ]
    }
     */



    public class RabbitMessageHeader
    {
        public static class Constants
        {
            public const string Version1_0 = "1.0";
        }

        public string Versione { get; set; } = Constants.Version1_0;
        public string Alias { get; set; }
        // public string IdComune { get; set; }
        public string Software { get; set; }
    }

    public class RabbitMessage<T> where T : class
    {
        public RabbitMessageHeader Header { get; set; }

        public T Body { get; set; }
    }
}
