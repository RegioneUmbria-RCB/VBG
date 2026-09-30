using System;
using System.Net;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi
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
            ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
            return client;
        }
    }
}
