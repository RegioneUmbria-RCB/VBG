using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
    public interface IUserCredentialsStorage
    {
        void Set(UserAuthenticationResult uar);
        UserAuthenticationResult Get();
    }
}
