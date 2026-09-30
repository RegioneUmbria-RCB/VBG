using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Utils;
using Init.SIGePro.Exceptions.Token;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for ConfigurazioneComune
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	public class ConfigurazioneComune : System.Web.Services.WebService
	{

		[WebMethod]
		public Init.SIGePro.Data.Configurazione LeggiConfigurazioneComune(string token, string software)
		{
			AuthenticationInfo ai = AuthenticationManager.CheckToken(token);

			if (ai == null)
				throw new InvalidTokenException(token);

			try
			{
				return new ConfigurazioneMgr(ai.CreateDatabase()).GetByIdComuneESoftwareSovrascrivendoTT(ai.IdComune, software);
			}
			catch (Exception ex)
			{
				Logger.LogEvent(ai, "ConfigurazioneComune.LeggiConfigurazioneComune", ex.Message, "");
				throw ex;
			}
		}

	}
}
