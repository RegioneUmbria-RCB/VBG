using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using SIGePro.Net.WebServices.WsSIGePro;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod(Description = "Logga un errore nel log di sigepro")]
		public void Log(string token, string codiceErrore, string modulo, string descrizioneEstesa)
		{
			new LogErrori().Log(token, codiceErrore, modulo, descrizioneEstesa);
		}
	}
}
