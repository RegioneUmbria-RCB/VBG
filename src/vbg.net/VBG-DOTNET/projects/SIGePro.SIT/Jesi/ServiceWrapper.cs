using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using RestClient = RestSharp.RestClient;

namespace Init.SIGePro.Sit.Jesi
{
    public class ServiceWrapper<T>
    {
        string _url;
        string _username;
        string _sha1Password;
        string _credentials;
        JesiRestClientResponseSuccess<T> _responseSuccess;
        JesiRestClientResponseError<T> _responseError;

        public ServiceWrapper(string url, string username, string sha1Password)
        {
            this._url = url;
            this._username = username;
            this._sha1Password = sha1Password;
        }

        public IEnumerable<IEnumerable<T>> GetSuccessResponse()
        {
            return _responseSuccess.HxSucc;
        }

        public List<JesiRestResponseError> GetErrorResponse()
        {
            return _responseError.ErrorResponse;
        }

        public bool QWS<TRequest>(RequestJSON<TRequest> jsonPostData, AuthenticationRestClient authenticationRestClient, bool returnEsitoOnError = false, int retryCount = 0)
        {
            try
            {
                var rr = new RestRequest()
                {
                    Method = Method.POST,
                    Resource = _url,
                };

                rr.AddJsonBody(JsonSerializer.Serialize(jsonPostData));
                rr.AddHeader("Authorization", "Bearer " + authenticationRestClient.GetAuthToken());

                var client = new RestClient();
                System.Net.ServicePointManager.SecurityProtocol = System.Net.SecurityProtocolType.Tls12;
                client.RemoteCertificateValidationCallback = (sender, certificate, chain, sslPolicyErrors) => true;

                IRestResponse response = client.Execute(rr);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {
                    _responseSuccess = JsonSerializer.Deserialize<JesiRestClientResponseSuccess<T>>(response.Content);
                    return true;
                }
                else if (response.StatusCode == System.Net.HttpStatusCode.Unauthorized && retryCount < 2)
                {
                    retryCount++;
                    authenticationRestClient.SetTokenExpired();
                    return QWS<TRequest>(jsonPostData, authenticationRestClient, returnEsitoOnError, retryCount);
                }
                else if (returnEsitoOnError)
                {
                    _responseError = new JesiRestClientResponseError<T>()
                    {
                        ErrorResponse = JsonSerializer.Deserialize<List<JesiRestResponseError>>(response.Content)
                    };
                    return false;
                }
                else
                {
                    var errors = JsonSerializer.Deserialize<List<JesiRestResponseError>>(response.Content);
                    throw new Exception(string.Join(" -- ", errors.Select(err => err.Message)));
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA COMUNICAZIONE CON IL SISTEMA SIT, {ex.Message}", ex);
            }
        }
    }
}
