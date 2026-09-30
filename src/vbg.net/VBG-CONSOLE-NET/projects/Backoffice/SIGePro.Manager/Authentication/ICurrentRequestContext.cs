using Init.SIGePro.Authentication;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Authentication
{
    public interface ICurrentRequestContext
    {
        AuthenticationInfo GetCurrentUser();
    }
}
