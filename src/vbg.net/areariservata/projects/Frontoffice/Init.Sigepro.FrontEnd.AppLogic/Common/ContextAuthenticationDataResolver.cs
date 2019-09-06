using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
	internal class ContextAuthenticationDataResolver : IAuthenticationDataResolver
	{
		protected static class Constants
		{
			public const string UserAuthenticationResultItemName = "UserAuthenticationResult";
		}

		UserAuthenticationResult _datiAutenticazione;


		#region IAuthenticationDataResolver Members

		public UserAuthenticationResult DatiAutenticazione
		{
			get 
			{
				if(_datiAutenticazione != null)
					return _datiAutenticazione;

				_datiAutenticazione = HttpContext.Current.Items[Constants.UserAuthenticationResultItemName] as UserAuthenticationResult;

				if (_datiAutenticazione == null)
					throw new InvalidOperationException("UserAuthenticationResult non trovato nel contesto corrente");

				return _datiAutenticazione;
			}
		}

		#endregion
	}
}
