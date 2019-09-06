using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
	public interface IAuthenticationDataResolver
	{
		UserAuthenticationResult DatiAutenticazione { get;  }
	}
}
