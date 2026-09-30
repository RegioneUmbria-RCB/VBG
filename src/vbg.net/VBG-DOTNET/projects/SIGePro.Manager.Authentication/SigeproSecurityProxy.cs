using Init.SIGePro.Manager.Authentication.WsSigeproSecurity;
using log4net;
using System;
using System.Collections.Concurrent;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Channels;

namespace Init.SIGePro.Manager.Authentication
{
    public class SigeproSecurityProxy
    {
        private const string SERVICE_NAME = "sigeproSecurityService";

        private static readonly Object _appInfoLock = new Object();
        private static readonly ConcurrentDictionary<string, GetDbConnectionInfoResponse> _connectionInfo = new ConcurrentDictionary<string, GetDbConnectionInfoResponse>();
        private static ApplicationInfoType[] _applicationInfo = null;


        private sigeproSecurityClient CreateService()
        {
            var parametriSecurity = ParametriSecurityStorage.GetParametriSecurity();
            var endpoint = new EndpointAddress(parametriSecurity.WebServiceUrl);
            var binding = new BasicHttpBinding();

            var svc = new sigeproSecurityClient(binding, endpoint);
            return svc;
        }

        private OperationContextScope AddHeaderToContextScope(sigeproSecurityClient svc)
        {
            var scope = new OperationContextScope(svc.InnerChannel);
            OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

            return scope;
        }

        public LoginResponse Login(LoginRequest req)
        {
            var logger = LogManager.GetLogger(typeof(SigeproSecurityProxy));

            using (var ws = this.CreateService())
            {
                using (var scope = this.AddHeaderToContextScope(ws))
                {
                    try
                    {
                        return ws.Login(req);
                    }
                    catch (Exception ex)
                    {
                        logger.ErrorFormat("SigeproSecurityProxy.Login: errore duranre il login->{0}", ex.ToString());


                        throw;
                    }
                }
            }
        }

        public CheckTokenResponse CheckToken(CheckTokenRequest req)
        {
            using (var ws = this.CreateService())
            {
                using (var scope = this.AddHeaderToContextScope(ws))
                {
                    try
                    {
                        return ws.CheckToken(req);
                    }
                    catch (Exception)
                    {
                        ws.Abort();
                        // TODO: log dell'errore
                        throw;
                    }
                }
            }
        }

        public GetDbConnectionInfoResponse GetDbConnectionInfo(string alias, AmbienteType ambiente = AmbienteType.DOTNET)
        {
            return this.GetDbConnectionInfo(new GetDbConnectionInfoRequest
            {
                alias = alias,
                ambiente = ambiente
            });
        }

        public GetDbConnectionInfoResponse GetDbConnectionInfo(GetDbConnectionInfoRequest req)
        {
            var cacheKey = $"DbConnectionInfo.{req.alias}.{req.ambiente}";

            return _connectionInfo.GetOrAdd(cacheKey, _ => this.GetDbConnectionInfoInternal(req));
        }

        private GetDbConnectionInfoResponse GetDbConnectionInfoInternal(GetDbConnectionInfoRequest req)
        {
            using (var ws = this.CreateService())
            {
                using (var scope = this.AddHeaderToContextScope(ws))
                {
                    try
                    {
                        return ws.GetDbConnectionInfo(req);
                    }
                    catch (Exception)
                    {
                        ws.Abort();
                        // TODO: log dell'errore
                        throw;
                    }
                }
            }
        }

        internal ApplicationInfoType[] GetApplicationInfo()
        {
            lock (_appInfoLock)
            {
                if (_applicationInfo == null)
                {
                    _applicationInfo = this.GetApplicationInfoInternal();
                }

                return _applicationInfo;
            }
        }



        private ApplicationInfoType[] GetApplicationInfoInternal()
        {
            using (var ws = this.CreateService())
            {
                using (var scope = this.AddHeaderToContextScope(ws))
                {
                    try
                    {
                        return ws.GetApplicationInfo(new GetApplicationInfoRequest());
                    }
                    catch (Exception)
                    {
                        ws.Abort();
                        // TODO: log dell'errore
                        throw;
                    }
                }
            }
        }

        public string GetValoreParametro(string nomeParametro)
        {
            var rVal = this.GetApplicationInfo();
            var parametro = rVal.FirstOrDefault(x => x.param.Equals(nomeParametro, StringComparison.InvariantCultureIgnoreCase));

            return parametro?.value ?? "";
        }

        public SecurityListType[] GetSecurityList()
        {
            using (var ws = this.CreateService())
            {
                using (var scope = this.AddHeaderToContextScope(ws))
                {
                    try
                    {
                        var appInfo = ws.GetSecurityList(new GetSecurityListRequest());

                        return appInfo;
                    }
                    catch (Exception)
                    {
                        ws.Abort();
                        // TODO: log dell'errore
                        throw;
                    }
                }
            }
        }

        [Obsolete]
        public GetTokenPartnerAppPerComuneESoftwareResponse GetTokenDocErPerComuneeSoftware(GetTokenPartnerAppPerComuneESoftwareRequest request)
        {
            using (var ws = this.CreateService())
            {
                using (var scope = this.AddHeaderToContextScope(ws))
                {
                    try
                    {
                        return ws.GetTokenPartnerAppPerComuneESoftware(request);
                    }
                    catch (Exception)
                    {
                        ws.Abort();
                        throw;
                    }
                }
            }
        }


        private static MessageHeader CreateHeader()
        {
            var parametriSecurity = ParametriSecurityStorage.GetParametriSecurity();

            return UserNameSecurityTokenHeader.FromUserNamePassword(parametriSecurity.Username, parametriSecurity.Password);
        }

        public static void ClearCache()
        {
            lock (_appInfoLock)
            {
                _applicationInfo = null;
                _connectionInfo.Clear();
            }
        }
    }
}
