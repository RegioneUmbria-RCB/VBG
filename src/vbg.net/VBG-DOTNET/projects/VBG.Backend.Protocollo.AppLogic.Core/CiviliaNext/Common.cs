using RestSharp;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext
{
    internal static class Common
    {
        internal static RestRequest GetRestRequest(string serializedJsonRequest, string token, Method method = Method.Post)
        {
            var request = new RestRequest();
            request.Method = method;
            request.AddHeader("Authorization", $"Bearer {token}");
            request.AddHeader("Accept", "application/json");
            // NON serve settare Content-Type manualmente
            request.AddStringBody(serializedJsonRequest, DataFormat.Json);

            return request;
        }
    }
}
