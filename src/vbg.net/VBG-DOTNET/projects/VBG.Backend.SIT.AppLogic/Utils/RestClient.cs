using System.IO;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.Text;

namespace VBG.Backend.SIT.AppLogic.Utils
{
    internal enum HttpVerb
    {
        GET,
        POST,
        PUT,
        DELETE
    }

    internal class RestClient
    {
        private static class Constants
        {
            public const string DefaultEncodingName = "iso-8859-1";
            public const string DefaultContentType = "text/xml";
        }

        public string EndPoint { get; set; }
        public HttpVerb Method { get; set; }
        public string ContentType { get; set; }
        public string PostData { get; set; }
        public WebHeaderCollection? Headers { get; set; }
        public bool ManageWebResponseErrors { get; set; }
        public string EncodingName { get; set; } = Constants.DefaultEncodingName;

        internal RestClient()
        {
            this.EndPoint = "";
            this.Method = HttpVerb.GET;
            this.ContentType = Constants.DefaultContentType;
            this.PostData = "";
            this.ManageWebResponseErrors = false;
        }
        internal RestClient(string endpoint)
        {
            this.EndPoint = endpoint;
            this.Method = HttpVerb.GET;
            this.ContentType = Constants.DefaultContentType;
            this.PostData = "";
            this.ManageWebResponseErrors = false;
        }
        internal RestClient(string endpoint, HttpVerb method)
        {
            this.EndPoint = endpoint;
            this.Method = method;
            this.ContentType = Constants.DefaultContentType;
            this.PostData = "";
            this.ManageWebResponseErrors = false;
        }

        internal RestClient(string endpoint, HttpVerb method, string postData)
        {
            this.EndPoint = endpoint;
            this.Method = method;
            this.ContentType = Constants.DefaultContentType;
            this.PostData = postData;
            this.ManageWebResponseErrors = false;
        }


        public string MakeRequest()
        {
            return this.MakeRequest("");
        }

        public string MakeRequest(string parameters)
        {
            var request = (HttpWebRequest)WebRequest.Create(this.EndPoint + parameters);

            if (this.Headers != null)
            {
                request.Headers = this.Headers;
            }

            request.Method = this.Method.ToString();
            request.ContentLength = 0;
            request.ContentType = this.ContentType;

            if (this.EndPoint.StartsWith("https"))
            {
                System.Net.ServicePointManager.SecurityProtocol = System.Net.SecurityProtocolType.Tls12 |
                    System.Net.SecurityProtocolType.Tls11;
                System.Net.ServicePointManager.ServerCertificateValidationCallback = (object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors sslPolicyErrors) => true;
            }

            if (!string.IsNullOrEmpty(this.PostData) && this.Method == HttpVerb.POST)
            {
                var encoding = new UTF8Encoding();
                var bytes = Encoding.GetEncoding(this.EncodingName).GetBytes(this.PostData);
                request.ContentLength = bytes.Length;

                using (var writeStream = request.GetRequestStream())
                {
                    writeStream.Write(bytes, 0, bytes.Length);
                }
            }

            try
            {
                using (var response = (HttpWebResponse)request.GetResponse())
                {
                    return this.GetResponse(response);
                }
            }
            catch (WebException ex)
            {
                var response = (HttpWebResponse)ex.Response;
                if (this.ManageWebResponseErrors && response.StatusCode == HttpStatusCode.InternalServerError)
                {
                    return this.GetResponse(response);
                }

                throw;
            }
        }

        private string GetResponse(HttpWebResponse response)
        {
            var responseValue = string.Empty;

            //if (response.StatusCode != HttpStatusCode.OK)
            //{
            //    var message = String.Format("Request failed. Received HTTP {0}", response.StatusCode);
            //    throw new ApplicationException(message);
            //}

            // grab the response
            using (var responseStream = response.GetResponseStream())
            {
                if (responseStream != null)
                    using (var reader = new StreamReader(responseStream))
                    {
                        responseValue = reader.ReadToEnd();
                    }
            }

            return responseValue;
        }
    }
}
