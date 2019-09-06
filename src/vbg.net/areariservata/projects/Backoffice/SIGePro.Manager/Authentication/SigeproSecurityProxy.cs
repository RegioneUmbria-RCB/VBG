using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.WsSigeproSecurity;
using System.ServiceModel.Channels;
using System.ServiceModel;
using Init.SIGePro.Manager.Properties;
using log4net;
using System.Web;

namespace Init.SIGePro.Manager.Authentication
{
    public static class SigeproSecurityProxy
    {
        const string SERVICE_NAME = "sigeproSecurityService";

        public static LoginResponse Login(LoginRequest req)
        {
			var logger = LogManager.GetLogger(typeof(SigeproSecurityProxy));

            using (var ws = new sigeproSecurityClient(SERVICE_NAME))
			{
				try
				{
					OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

					OperationContext.Current.OutgoingMessageHeaders.Add( CreateHeader() );

                    return ws.Login( req );
				}
				catch (Exception ex)
				{
					logger.ErrorFormat("SigeproSecurityProxy.Login: errore duranre il login->{0}" , ex.ToString());


                    throw;
				}
			}
        }

        public static CheckTokenResponse CheckToken(CheckTokenRequest req)
        {
            using (var ws = new sigeproSecurityClient(SERVICE_NAME))
            {
                try
                {
                    OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

                    OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

                    return ws.CheckToken(req);
                }
                catch (Exception ex)
                {
                    // TODO: log dell'errore
                    throw;
                }
            }
        }

        public static GetDbConnectionInfoResponse GetDbConnectionInfo(GetDbConnectionInfoRequest req)
        {
            using (var ws = new sigeproSecurityClient(SERVICE_NAME))
            {
                try
                {
                    OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

                    OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

                    return ws.GetDbConnectionInfo(req);
                }
                catch (Exception ex)
                {
                    // TODO: log dell'errore
                    throw;
                }
            }
        }

        internal static ApplicationInfoType[] GetApplicationInfo(GetApplicationInfoRequest req)
        {
            using (var ws = new sigeproSecurityClient(SERVICE_NAME))
            {
                try
                {
                    OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

                    OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

                    return ws.GetApplicationInfo(req);
                }
                catch (Exception ex)
                {
                    // TODO: log dell'errore
                    throw;
                }
            }
        }

		public static string GetValoreParametro(string nomeParametro)
		{
			var req = new GetApplicationInfoRequest
			{
				param = nomeParametro
			};

			var rVal = GetApplicationInfo(req);

			if (rVal.Length == 0)
				return String.Empty;

			return rVal[0].value;
		}
        /*
		/// <summary>
		/// Ottiene l'url assoluto dell'applicazione java
		/// </summary>
		/// <returns></returns>
		public static string GetJavaBaseUrl()
		{
			var baseUrl = SigeproSecurityProxy.GetValoreParametro("WSHOSTURL_JAVA");
			var appJava = SigeproSecurityProxy.GetValoreParametro("APP_JAVA");

			if (String.IsNullOrEmpty(baseUrl))
			{
				var req = HttpContext.Current.Request;
				baseUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

				if (!baseUrl.EndsWith("/"))
					baseUrl += "/";

				if (!appJava.EndsWith("/"))
					appJava += "/";

				baseUrl = baseUrl + appJava;
			}

			if (!baseUrl.EndsWith("/"))
				baseUrl += "/";

			return baseUrl ;
		}
        */
        public static SecurityListType[] GetSecurityList(GetSecurityListRequest req)
        {
            using (var ws = new sigeproSecurityClient(SERVICE_NAME))
            {
                try
                {
                    OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

                    OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

                    return ws.GetSecurityList(req);
                }
                catch (Exception ex)
                {
                    // TODO: log dell'errore
                    throw;
                }
            }
        }

        public static GetTokenPartnerAppResponse GetTokenDocEr(GetTokenPartnerAppRequest request)
        {
            using (var ws = new sigeproSecurityClient(SERVICE_NAME))
            {
                try
                {
                    OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

                    OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

                    return ws.GetTokenPartnerApp(request);
                }
                catch (Exception)
                {
                    throw;
                }
            }
        }

        public static GetTokenPartnerAppPerComuneESoftwareResponse GetTokenDocErPerComuneeSoftware(GetTokenPartnerAppPerComuneESoftwareRequest request)
        {
            using (var ws = new sigeproSecurityClient(SERVICE_NAME))
            {
                try
                {
                    OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

                    OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

                    return ws.GetTokenPartnerAppPerComuneESoftware(request);
                }
                catch (Exception)
                {
                    throw;
                }
            }
        }

		//public static LoginSSOResponse LoginSSO(LoginSSORequest req)
		//{
		//    using (var ws = new sigeproSecurityClient(SERVICE_NAME))
		//    {
		//        try
		//        {
		//            OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

		//            OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

		//            return ws.LoginSSO(req);
		//        }
		//        catch (Exception ex)
		//        {
		//            // TODO: log dell'errore
		//            throw;
		//        }
		//    }
		//}

        private static MessageHeader CreateHeader()
        {
            return UserNameSecurityTokenHeader.FromUserNamePassword( Settings.Default.SigeproSecurityUsername , 
                                                                     Settings.Default.SigeproSecurityPassword );
        }

    }
}
