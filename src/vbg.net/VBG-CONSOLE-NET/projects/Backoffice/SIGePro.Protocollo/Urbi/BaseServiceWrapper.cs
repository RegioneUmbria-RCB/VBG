using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Urbi.Classificazione;
using Init.SIGePro.Protocollo.Urbi.Protocollazione;
using Init.SIGePro.Protocollo.Urbi.TipiDocumento;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi
{
    public class BaseServiceWrapper
    {
        protected ProtocolloLogs _logs;
        protected ProtocolloSerializer _serializer;
        protected string _username;
        protected string _password;
        protected string _url;
        protected const string _nomeParametroMetodo = "WTDK_REQ"; //è il nome del parametro in querystring da valorizzare con il nome del metodo.

        public BaseServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url)
        {
            _logs = logs;
            _serializer = serializer;
            _username = username;
            _password = password;
            _url = url;
        }

        protected WebClient GetHttpClient()
        {
            var client = new WebClient();

            var credentials = _username + ":" + _password;
            string authInfo = Convert.ToBase64String(Encoding.UTF8.GetBytes(credentials));
            client.Headers.Add(HttpRequestHeader.Authorization, "Basic " + authInfo);
            client.Credentials = new NetworkCredential(_username, _password);

            return client;
        }
    }
}
