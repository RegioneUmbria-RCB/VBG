using Init.SIGePro.Authentication;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.ServiziConsole
{
    public interface IConsoleService
    {
        SDEProxyDto GetParametriSdeProxy();
        UrlServiziConsole GetUrlServizi();
        AuthenticationInfo LoginSuAliasLocale();
    }
}
