using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Configurazione;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public ConfigurazioneAreaRiservataDto LeggiConfigurazioneFrontoffice(string token, string software)
		{
			return new DatiConfigurazioneAreaRiservataService().LeggiConfigurazione(token, software);
		}
	}
}
