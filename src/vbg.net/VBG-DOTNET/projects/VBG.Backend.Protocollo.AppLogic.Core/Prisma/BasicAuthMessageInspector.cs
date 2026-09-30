using System.ServiceModel;
using System.ServiceModel.Channels;
using System.ServiceModel.Dispatcher;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma
{
    public class BasicAuthMessageInspector : IClientMessageInspector
    {
        private readonly string _credentialInfo;

        public BasicAuthMessageInspector(string credentialInfo)
        {
            _credentialInfo = credentialInfo;
        }

        public object BeforeSendRequest(ref Message request, IClientChannel channel)
        {
            if (!string.IsNullOrEmpty(_credentialInfo))
            {
                HttpRequestMessageProperty httpRequest;

                if (request.Properties.ContainsKey(HttpRequestMessageProperty.Name))
                {
                    httpRequest = (HttpRequestMessageProperty)request.Properties[HttpRequestMessageProperty.Name];
                }
                else
                {
                    httpRequest = new HttpRequestMessageProperty();
                    request.Properties.Add(HttpRequestMessageProperty.Name, httpRequest);
                }

                httpRequest.Headers["Authorization"] = $"Basic {_credentialInfo}";
            }

            return null;
        }

        public void AfterReceiveReply(ref Message reply, object correlationState)
        {
        }
    }
}
