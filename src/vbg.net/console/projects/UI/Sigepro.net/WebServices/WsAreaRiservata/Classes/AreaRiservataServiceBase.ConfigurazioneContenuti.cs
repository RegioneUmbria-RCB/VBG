using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.SIGePro.Manager.DTO.Configurazione;
using System.Web.Services;
using Init.SIGePro.Manager;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public ConfigurazioneContenutiDto GetConfigurazioneContenutiFrontoffice(string token, string software)
		{
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
			{
				return new ConfigurazioneMgr(db)
							.GetConfigurazioneContenutiFrontoffice(ai.IdComune, ai.Alias, software);
			}
		}
	}
}
