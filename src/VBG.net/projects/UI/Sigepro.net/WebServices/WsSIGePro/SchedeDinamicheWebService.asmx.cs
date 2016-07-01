using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Manager.Logic.DatiDinamici.EsecuzioneScriptDaWebService;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for SchedeDinamicheWebService
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class SchedeDinamicheWebService : SigeproWebService
	{
		public class EseguiScriptSchedaIstanzaRequest
		{
			public int CodiceIstanza{get;set;}
			public bool EseguiScriptCaricamento{get;set;}
			public bool EseguiScriptSalvataggio{get;set;}
		}

		public class EseguiScriptSingolaSchedaIstanzaRequest
		{
			public int CodiceIstanza { get; set; }
			public int IdScheda { get; set; }
			public bool EseguiScriptCaricamento { get; set; }
			public bool EseguiScriptSalvataggio { get; set; }
		}

		public class EseguiScriptSchedaAttivitaRequest
		{
			public int CodiceAttivita { get; set; }
			public bool EseguiScriptCaricamento { get; set; }
			public bool EseguiScriptSalvataggio { get; set; }
		}

		public class EseguiScriptSingolaSchedaAttivitaRequest 
		{
			public int CodiceAttivita { get; set; }
			public int IdScheda { get; set; }
			public bool EseguiScriptCaricamento { get; set; }
			public bool EseguiScriptSalvataggio { get; set; }			
		}

		public class RisultatoEsecuzioneScript
		{
			public List<string> ErroriSalvataggio { get; set; }

			public RisultatoEsecuzioneScript()
			{
				this.ErroriSalvataggio = new List<string>();
			}
		}

		[WebMethod]
		public RisultatoEsecuzioneScript EseguiScriptSchedeIstanza(string token, EseguiScriptSchedaIstanzaRequest request)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				var svc = new EsecuzioneScriptSchedeDinamicheService(db, authInfo.IdComune);

				var errori = svc.EseguiScriptSchedeIstanza(request.CodiceIstanza, request.EseguiScriptCaricamento, request.EseguiScriptSalvataggio);

				return new RisultatoEsecuzioneScript { ErroriSalvataggio = errori };
			}
		}

		[WebMethod]
		public RisultatoEsecuzioneScript EseguiScriptSingolaSchedaIstanza(string token, EseguiScriptSingolaSchedaIstanzaRequest request)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				var svc = new EsecuzioneScriptSchedeDinamicheService(db, authInfo.IdComune);

				var errori = svc.EseguiScriptSchedaSingolaIstanza( request.CodiceIstanza , request.IdScheda , request.EseguiScriptCaricamento , request.EseguiScriptSalvataggio );

				return new RisultatoEsecuzioneScript { ErroriSalvataggio = errori };
			}
		}

		[WebMethod]
		public RisultatoEsecuzioneScript EseguiScriptSchedeAttivita(string token, EseguiScriptSchedaAttivitaRequest request)
		{
			throw new NotImplementedException();
		}

		[WebMethod]
		public RisultatoEsecuzioneScript EseguiScriptSingolaSchedaAttivita(string token, EseguiScriptSingolaSchedaAttivitaRequest request)
		{
			throw new NotImplementedException();
		}
	}
}
