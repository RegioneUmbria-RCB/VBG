using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.WebServices.WsSIGePro;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public Init.SIGePro.Data.Configurazione LeggiConfigurazioneComune(string token, string software)
		{
			return new ConfigurazioneComune().LeggiConfigurazioneComune(token, software);
		}

	}
}
