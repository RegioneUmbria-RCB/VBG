using Init.SIGePro.Protocollo.Logs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma
{
    public class ServiceWrapperBase
    {
        protected CredentialsInfo Credentials;

        protected ServiceWrapperBase(CredentialsInfo credentials)
        {
            this.Credentials = credentials;
        }

        protected void AggiungiCredenzialiAContextScope()
        {
            if (!String.IsNullOrEmpty(this.Credentials.Username))
            {
                var request = new HttpRequestMessageProperty();
                request.Headers[System.Net.HttpRequestHeader.Authorization] = "Basic " + this.Credentials.CredentialInfo;

                OperationContext.Current.OutgoingMessageProperties.Add(HttpRequestMessageProperty.Name, request);
            }
        }
    }
}
