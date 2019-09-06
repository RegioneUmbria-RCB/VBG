using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneLDP
{
    internal class BasicSoapAuthenticationCredentials
    {
        string _username;
        string _password;

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
