using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Net;


namespace Init.SIGePro.Manager.Utils
{
    public enum HttpVerb
    {
        GET,
        POST,
        PUT,
        DELETE
    }

    public class RestClient
    {
        public string EndPoint { get; set; }
        public HttpVerb Method { get; set; }
        public string ContentType { get; set; }
        public string PostData { get; set; }
        public WebHeaderCollection Headers { get; set; }
        public bool ManageWebResponseErrors { get; set; }
        public string EncodingName { get; set; } = "iso-8859-1";

        public RestClient()
        {
            this.EndPoint = "";
            this.Method = HttpVerb.GET;
            this.ContentType = "text/xml";
            this.PostData = "";
            this.ManageWebResponseErrors = false;
        }
        public RestClient(string endpoint)
        {
            this.EndPoint = endpoint;
            this.Method = HttpVerb.GET;
            this.ContentType = "text/xml";
            this.PostData = "";
            this.ManageWebResponseErrors = false;
        }
        public RestClient(string endpoint, HttpVerb method)
        {
            this.EndPoint = endpoint;
            this.Method = method;
            this.ContentType = "text/xml";
            this.PostData = "";
            this.ManageWebResponseErrors = false;
        }

        public RestClient(string endpoint, HttpVerb method, string postData)
        {
            this.EndPoint = endpoint;
            this.Method = method;
            this.ContentType = "text/xml";
            this.PostData = postData;
            this.ManageWebResponseErrors = false;
        }


        public string MakeRequest()
        {
            return MakeRequest("");
        }

        public string MakeRequest(string parameters)
        {
            var request = (HttpWebRequest)WebRequest.Create(EndPoint + parameters);

            if (this.Headers != null)
            {
                request.Headers = this.Headers;
            }

            request.Method = Method.ToString();
            request.ContentLength = 0;
            request.ContentType = ContentType;

            if (!string.IsNullOrEmpty(PostData) && Method == HttpVerb.POST)
            {
                var encoding = new UTF8Encoding();
                var bytes = Encoding.GetEncoding(this.EncodingName).GetBytes(PostData);
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
                    return GetResponse(response);
                }
            }
            catch (WebException ex)
            {
                var response = (HttpWebResponse)ex.Response;
                if (ManageWebResponseErrors && response.StatusCode == HttpStatusCode.InternalServerError)
                {
                    return GetResponse(response);
                }
                
                throw new WebException(ex.Message, ex);
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