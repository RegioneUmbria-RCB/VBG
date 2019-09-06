using Init.Sigepro.FrontEnd.AppLogic.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
    public class UserCredentialsStorage: IUserCredentialsStorage
    {
        private static class Constants
        {
            public const string Key = "UserCredentialsStorage:UserKey";
        }

        IResolveHttpContext _contextResolver;

        public UserCredentialsStorage(IResolveHttpContext contextResolver)
        {
            this._contextResolver = contextResolver;
        }

        public void Set(UserAuthenticationResult uar)
        {
            this._contextResolver.Get.Items[Constants.Key] = uar;
        }

        public UserAuthenticationResult Get()
        {
            var obj = this._contextResolver.Get.Items[Constants.Key];

            return (UserAuthenticationResult)obj;
        }
    }
}
