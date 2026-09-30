using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
//using RestClient = RestSharp.RestClient;

namespace VBG.Backend.SIT.AppLogic.Jesi
{
    public class ServiceWrapper<T>
    {
        private readonly string _url;
        private readonly string _username;
        private readonly string _sha1Password;
        private readonly string _credentials;
        private JesiRestClientResponseSuccess<T> _responseSuccess;
        private JesiRestClientResponseError<T> _responseError;

        public ServiceWrapper(string url, string username, string sha1Password)
        {
            this._url = url;
            this._username = username;
            this._sha1Password = sha1Password;
        }

        public IEnumerable<IEnumerable<T>> GetSuccessResponse()
        {
            return this._responseSuccess.HxSucc;
        }

        public List<JesiRestResponseError> GetErrorResponse()
        {
            return this._responseError.ErrorResponse;
        }

        public bool QWS<TRequest>(RequestJSON<TRequest> jsonPostData, AuthenticationRestClient authenticationRestClient, bool returnEsitoOnError = false, int retryCount = 0)
        {
            try
            {
                var rr = new RestRequest()
                {
                    Method = Method.Post,
                    Resource = this._url,
                };

                rr.AddJsonBody(JsonSerializer.Serialize(jsonPostData));
                rr.AddHeader("Authorization", "Bearer " + authenticationRestClient.GetAuthToken());

                var options = new RestClientOptions()
                {
                    RemoteCertificateValidationCallback = (sender, certificate, chain, sslPolicyErrors) => true,
                };

                var client = new RestClient(options);
                System.Net.ServicePointManager.SecurityProtocol = System.Net.SecurityProtocolType.Tls12;

                var response = client.Execute(rr);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {
                    this._responseSuccess = JsonSerializer.Deserialize<JesiRestClientResponseSuccess<T>>(response.Content);
                    return true;
                }
                else if (response.StatusCode == System.Net.HttpStatusCode.Unauthorized && retryCount < 2)
                {
                    retryCount++;
                    authenticationRestClient.SetTokenExpired();
                    return this.QWS(jsonPostData, authenticationRestClient, returnEsitoOnError, retryCount);
                }
                else if (returnEsitoOnError)
                {
                    this._responseError = new JesiRestClientResponseError<T>()
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
