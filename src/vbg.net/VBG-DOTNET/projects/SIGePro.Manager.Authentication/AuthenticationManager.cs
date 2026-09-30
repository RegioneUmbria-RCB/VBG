using VBG.Shared.Infrastructure.Caching;
using Init.SIGePro.Manager.Authentication.WsSigeproSecurity;
using Init.Utils;
using System;
using System.Reflection;

namespace Init.SIGePro.Manager.Authentication
{
    public enum ContextType { Anagrafe = 1, Operatore, ExternalUsers = 4 }

    public class AuthenticationManager : IAuthenticationManager
    {
        private readonly AuthenticationInfoRepositoryFactory _authenticationInfoRepositoryFactory;
        private readonly SigeproSecurityProxy _sigeproSecurityProxy = new SigeproSecurityProxy();

        public AuthenticationManager(ITimedCache timedCache)
        {
            this._authenticationInfoRepositoryFactory = new AuthenticationInfoRepositoryFactory(new SigeproSecurityProxy(), timedCache);
        }

        public AuthenticationInfo GetTokenApplicativo(string alias)
        {
            var parametriSecurity = ParametriSecurityStorage.GetParametriSecurity();
            return this.Login(alias, parametriSecurity.Username, parametriSecurity.Password, ContextType.ExternalUsers);
        }

        public AuthenticationInfo CheckToken(string token, TipiAmbiente ambiente = TipiAmbiente.DOTNET)
        {
            using (CodeProfiler.Track(MethodInfo.GetCurrentMethod()))
            {
                IAuthenticationInfoRepository repository = this._authenticationInfoRepositoryFactory.Create();

                var ai = repository.GetByToken(token, ambiente);

                return ai;
            }
        }

        public AuthenticationInfo Login(string alias, string userId, string password, ContextType tipoContesto)
        {
            IAuthenticationInfoRepository repository = this._authenticationInfoRepositoryFactory.Create();

            return repository.GetByLoginInfo(alias, userId, password, tipoContesto);
        }

        public ApplicationInfoType[] GetApplicationInfo()
        {
            return this._sigeproSecurityProxy.GetApplicationInfo();
        }

        /////////////// SPOSTATO in ConfigurazioneGenerale
        //
        //public string GetApplicationInfoValue(string param)
        //{
        //    if (string.IsNullOrEmpty(param))
        //        return String.Empty;

        //    var cfgValue = FileBasedConfiguration.GetSetting(param);

        //    if (!String.IsNullOrEmpty(cfgValue))
        //    {
        //        return cfgValue;
        //    }

        //    return this._sigeproSecurityProxy.GetValoreParametro(param);
        //}
    }
}
