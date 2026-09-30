using System;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;

namespace Init.SIGePro.Manager.Utils
{
    public class BasicSoapAuthenticationCredentials
    {
        private readonly string _username;
        private readonly string _password;

        public BasicSoapAuthenticationCredentials(string username, string password)
        {
            this._username = username;
            this._password = password;
        }

        public void AggiungiCredenzialiAContextScope()
        {
            var credentials = GetCredentials(this._username, this._password);
            var request = new HttpRequestMessageProperty();

            request.Headers[System.Net.HttpRequestHeader.Authorization] = "Basic " + credentials;

            OperationContext.Current.OutgoingMessageProperties.Add(HttpRequestMessageProperty.Name, request);

        }

        private static string GetCredentials(string username, string password)
        {
            var credentials = username + ":" + password;

            return Convert.ToBase64String(Encoding.UTF8.GetBytes(credentials));
        }
    }
}
