using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.PaDoc.LeggiProtocollo;
using Init.SIGePro.Protocollo.Serialize;
using Init.Utils;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.Services
{
    public class LeggiProtocolloService
    {
        private static class Constants
        {
            public const string Login = "login";
            public const string Password = "password";
            public const string Numero = "numero";
            public const string Anno = "anno";
            public const string StatoKO = "KO";
        }

        string _url;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;

        public LeggiProtocolloService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _url = url;
            _logs = logs;
            _serializer = serializer;
        }

        public LeggiProtocolloResponseSegnatura LeggiProtocollo(string username, string password, string numeroProtocollo, string annoProtocollo)
        {
            try
            {
                using (var client = new HttpClient())
                {
                    var url = String.Format("{0}/?debug=1", _url);

                    var list = new List<KeyValuePair<string, string>>();
                    list.Add(new KeyValuePair<string, string>(Constants.Login, username));
                    list.Add(new KeyValuePair<string, string>(Constants.Password, password));
                    list.Add(new KeyValuePair<string, string>(Constants.Numero, numeroProtocollo));
                    list.Add(new KeyValuePair<string, string>(Constants.Anno, annoProtocollo));

                    client.MaxResponseContentBufferSize = 65536000;

                    var formContent = new FormUrlEncodedContent(list.ToArray());

                    _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO, NUMERO: {0}, ANNO: {1}", numeroProtocollo, annoProtocollo);
                    var response = client.PostAsync(_url, formContent).Result;

                    var responseString = StreamUtils.StreamToString(response.Content.ReadAsStreamAsync().Result);
                    var segnatura = (LeggiProtocolloResponseSegnatura)_serializer.Deserialize(responseString, typeof(LeggiProtocolloResponseSegnatura));

                    if (segnatura.intestazione.stato == Constants.StatoKO)
                        throw new Exception(String.Format("CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", segnatura.intestazione.codice_errore, segnatura.intestazione.descrizione_errore));

                    _logs.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO, NUMERO: {0}, ANNO: {1} AVVENUTA CON SUCCESSO", numeroProtocollo, annoProtocollo);

                    return segnatura;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO NUMERO {0} ANNO {1}, {2}", numeroProtocollo, annoProtocollo, ex.Message), ex);
            }
        }
    }
}
