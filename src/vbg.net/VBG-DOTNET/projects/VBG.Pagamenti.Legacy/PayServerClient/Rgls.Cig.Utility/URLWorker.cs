using System;
using System.IO;
using System.Net;
using System.Net.Security;
using System.Runtime.CompilerServices;
using System.Security.Cryptography.X509Certificates;
using System.Text;

namespace Rgls.Cig.Utility
{
    public class URLWorker
    {
        [CompilerGenerated]
#pragma warning disable IDE0044 // Add readonly modifier
#pragma warning disable IDE0051 // Remove unused private members
#pragma warning disable IDE1006 // Naming Styles
        private static RemoteCertificateValidationCallback __CachedAnonymousMethodDelegate1;
#pragma warning restore IDE1006 // Naming Styles
#pragma warning restore IDE0051 // Remove unused private members
#pragma warning restore IDE0044 // Add readonly modifier

        public string URL { get; set; } = "";

        public string ProxyPort { get; set; } = "";

        public string ProxyServer { get; set; } = "";

        public string BufferIn { get; set; } = "";

        public string BufferOut { get; set; } = "";

        public URLWorker()
        {
            ServicePointManager.ServerCertificateValidationCallback = (RemoteCertificateValidationCallback)Delegate.Combine(ServicePointManager.ServerCertificateValidationCallback, new RemoteCertificateValidationCallback((object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors sslPolicyErrors) => true));
        }

        public string DoGet()
        {
            return this.DoRequest("GET");
        }

        public string DoPost()
        {
            return this.DoRequest("POST");
        }

        public string DoRequest(string sTipo)
        {
            try
            {
                Uri oURL;
                if (this.BufferIn.Length != 0 && sTipo.Equals("GET"))
                {
                    oURL = new Uri(this.URL + "?" + this.BufferIn);
                }
                else
                {
                    oURL = new Uri(this.URL);
                }
                HttpWebRequest oHTTPConn = (HttpWebRequest)WebRequest.Create(oURL);
                if (this.ProxyServer != null && this.ProxyServer != "" && this.ProxyPort != null && this.ProxyPort != "")
                {
                    string sUri = this.ProxyServer + ":" + this.ProxyPort;
                    oHTTPConn.Proxy = new WebProxy(sUri);
                }
                oHTTPConn.Method = sTipo;
                if (this.BufferIn.Length != 0 && sTipo.Equals("POST"))
                {
                    ASCIIEncoding encoding = new ASCIIEncoding();
                    byte[] bBuffer = encoding.GetBytes(this.BufferIn);
                    oHTTPConn.ContentType = "application/x-www-form-urlencoded";
                    oHTTPConn.ContentLength = (long)bBuffer.Length;
                    Stream newStream = oHTTPConn.GetRequestStream();
                    newStream.Write(bBuffer, 0, bBuffer.Length);
                    newStream.Close();
                }
                StreamReader oIn = new StreamReader(oHTTPConn.GetResponse().GetResponseStream(), Encoding.UTF8);
                this.BufferOut = oIn.ReadToEnd();
                oIn.Close();
                int ini;
                for (ini = 0; ini < this.BufferOut.Length; ini++)
                {
                    char ch = this.BufferOut[ini];
                    if (ch != '\n' && ch != '\r')
                    {
                        break;
                    }
                }
                int end;
                for (end = this.BufferOut.Length - 1; end > 0; end--)
                {
                    char ch = this.BufferOut[end];
                    if (ch != '\n' && ch != '\r')
                    {
                        break;
                    }
                }
                this.BufferOut = ((ini >= end) ? "" : this.BufferOut.Substring(ini, end - ini + 1));
            }
            catch (Exception ex)
            {
                return ex.Message;
            }
            return null;
        }
    }
}
