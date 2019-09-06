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
		private string p_sURL = "";

		private string p_sProxyPort = "";

		private string p_sProxyServer = "";

		private string p_sBufferOut = "";

		private string p_sBufferIn = "";

		[CompilerGenerated]
		private static RemoteCertificateValidationCallback __CachedAnonymousMethodDelegate1;

		public string URL
		{
			get
			{
				return this.p_sURL;
			}
			set
			{
				this.p_sURL = value;
			}
		}

		public string ProxyPort
		{
			get
			{
				return this.p_sProxyPort;
			}
			set
			{
				this.p_sProxyPort = value;
			}
		}

		public string ProxyServer
		{
			get
			{
				return this.p_sProxyServer;
			}
			set
			{
				this.p_sProxyServer = value;
			}
		}

		public string BufferIn
		{
			get
			{
				return this.p_sBufferIn;
			}
			set
			{
				this.p_sBufferIn = value;
			}
		}

		public string BufferOut
		{
			get
			{
				return this.p_sBufferOut;
			}
			set
			{
				this.p_sBufferOut = value;
			}
		}

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
			string result;
			try
			{
				Uri oURL;
				if (this.BufferIn.Length != 0 && sTipo.Equals("GET"))
				{
					oURL = new Uri(this.p_sURL + "?" + this.p_sBufferIn);
				}
				else
				{
					oURL = new Uri(this.p_sURL);
				}
				HttpWebRequest oHTTPConn = (HttpWebRequest)WebRequest.Create(oURL);
				if (this.p_sProxyServer != null && this.p_sProxyServer != "" && this.p_sProxyPort != null && this.p_sProxyPort != "")
				{
					string sUri = this.p_sProxyServer + ":" + this.p_sProxyPort;
					oHTTPConn.Proxy = new WebProxy(sUri);
				}
				oHTTPConn.Method = sTipo;
				if (this.BufferIn.Length != 0 && sTipo.Equals("POST"))
				{
					ASCIIEncoding encoding = new ASCIIEncoding();
					byte[] bBuffer = encoding.GetBytes(this.p_sBufferIn);
					oHTTPConn.ContentType = "application/x-www-form-urlencoded";
					oHTTPConn.ContentLength = (long)bBuffer.Length;
					Stream newStream = oHTTPConn.GetRequestStream();
					newStream.Write(bBuffer, 0, bBuffer.Length);
					newStream.Close();
				}
				StreamReader oIn = new StreamReader(oHTTPConn.GetResponse().GetResponseStream(), Encoding.UTF8);
				this.p_sBufferOut = oIn.ReadToEnd();
				oIn.Close();
				int ini;
				for (ini = 0; ini < this.p_sBufferOut.Length; ini++)
				{
					char ch = this.p_sBufferOut[ini];
					if (ch != '\n' && ch != '\r')
					{
						break;
					}
				}
				int end;
				for (end = this.p_sBufferOut.Length - 1; end > 0; end--)
				{
					char ch = this.p_sBufferOut[end];
					if (ch != '\n' && ch != '\r')
					{
						break;
					}
				}
				this.p_sBufferOut = ((ini >= end) ? "" : this.p_sBufferOut.Substring(ini, end - ini + 1));
			}
			catch (Exception ex)
			{
				result = ex.Message;
				return result;
			}
			result = null;
			return result;
		}
	}
}
