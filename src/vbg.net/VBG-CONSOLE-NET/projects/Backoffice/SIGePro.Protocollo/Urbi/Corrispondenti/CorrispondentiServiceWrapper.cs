using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.Corrispondenti
{
    public class CorrispondentiServiceWrapper : BaseServiceWrapper
    {
        public CorrispondentiServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url)
            : base(logs, serializer, username, password, url)
        {

        }

        public xapirestTypeCorrispondenti GetCorrispondente(NameValueCollection parametri)
        {
            try
            {
                using (var client = GetHttpClient())
                {
                    client.QueryString = parametri;
                    client.QueryString.Add(_nomeParametroMetodo, "getElencoCorrispondenti");

                    _logs.InfoFormat("RICERCA CORRISPONDENTE, REQUEST: {0}", Utility.NameValueCollectionToString(client.QueryString));
                    var res = client.DownloadString(_url);
                    var corrispondente = (xapirestTypeCorrispondenti)_serializer.Deserialize(res, typeof(xapirestTypeCorrispondenti));
                    _logs.InfoFormat("RICERCA DEI CORRISPONDENTI AVVENUTA CORRETTAMENTE, NUMERO CORRISPONDENTI TROVATI {0}", corrispondente.getElencoCorrispondenti_Result.NumCorrispondenti);

                    return corrispondente;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI DEL CORRISPONDENTE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public xapirestTypeInsertCorrispondenti InsertCorrispondente(NameValueCollection parametri)
        {
            try
            {
                using (var client = GetHttpClient())
                {
                    client.QueryString = parametri;
                    client.QueryString.Add(_nomeParametroMetodo, "insCorrispondente");
                    _logs.InfoFormat("INSERIMENTO CORRISPONDENTE, REQUEST: {0}", Utility.NameValueCollectionToString(client.QueryString));
                    var res = client.DownloadString(_url);
                    _logs.InfoFormat("INSERIMENTO CORRISPONDENTE AVVENUTA CORRETTAMENTE {0}", res);
                    var corrispondente = (xapirestTypeInsertCorrispondenti)_serializer.Deserialize(res, typeof(xapirestTypeInsertCorrispondenti));
                    return corrispondente;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DEL CORRISPONDENTE, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
