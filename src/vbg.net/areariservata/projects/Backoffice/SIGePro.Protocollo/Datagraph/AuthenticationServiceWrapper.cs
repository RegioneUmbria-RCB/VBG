using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Datagraph
{
    public class AuthenticationServiceWrapper : DatagraphServiceWrapperBase
    {
        public AuthenticationServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, logs, serializer)
        {

        }

        public string Login(string codiceEnte, string username, string password)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    this.Logs.Info($"CHIAMATA A LOGIN, USERNAME: {username}, PASSWORD: {password} CODICE ENTE: {codiceEnte}");
                    var response = ws.Login(codiceEnte, username, password);

                    if (response.lngErrNumber != 0)
                    {
                        throw new Exception($"NUMERO ERRORE: {response.lngErrNumber.ToString()}, DESCRIZIONE ERRORE: {response.strErrString}");
                    }

                    if (String.IsNullOrEmpty(response.strDST))
                    {
                        throw new Exception("IL TOKEN RESTITUITO DALL'AUTENTICAZIONE RISULTA ESSERE VUOTO");
                    }

                    this.Logs.Info($"AUTENTICAZIONE AL WEB SERVICE AVVENUTA CORRETTAMENTE, TOKEN RESTITUITO: {response.strDST}");

                    return response.strDST;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE {0}", ex.Message), ex);
            }
        }
    }
}
