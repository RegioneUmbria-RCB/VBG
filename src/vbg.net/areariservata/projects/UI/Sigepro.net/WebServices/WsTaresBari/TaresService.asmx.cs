using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Manager.DTO.Configurazione;
using Sigepro.net.WebServices.WsSIGeProAnagrafe;
using Init.SIGePro.Manager.Logic.Bari.Tares;

namespace Sigepro.net.WebServices.WsTaresBari
{
	/// <summary>
	/// Summary description for TaresService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class TaresService : SigeproWebService
	{
		public class RiferimentiCaf
		{
			public string CodiceFiscale { get; set; }
			public string IndirizzoPec { get; set; }
		}

		[WebMethod]
		public bool OperatoreAppartieneACaf(string token, string codiceFiscaleOperatore)
		{
			var ai = CheckToken(token);

			return new TaresBariService(ai.CreateDatabase()).UtenteAppartieneACaf(codiceFiscaleOperatore);
		}

		[WebMethod]
		public string GetCodiceFiscaleCafDaCodiceFiscaleOperatore(string token, string codiceFiscaleOperatore)
		{
			var ai = CheckToken(token);

			return new TaresBariService(ai.CreateDatabase()).GetCodiceFiscaleCafDaCFOperatore(codiceFiscaleOperatore);
		}

		[WebMethod]
		public RiferimentiCaf GetRiferimentiCafDaCodiceFiscaleoperatore(string token, string codiceFiscaleOperatore)
		{
			var ai = CheckToken(token);

			var codiceFiscale = new TaresBariService(ai.CreateDatabase()).GetCodiceFiscaleCafDaCFOperatore(codiceFiscaleOperatore);

            if (String.IsNullOrEmpty(codiceFiscale))
                return null;

			var anagrafe = new WsAnagrafe2().getPersonaGiuridica(token, codiceFiscale);

			if (anagrafe == null)
				return null;

			return new RiferimentiCaf
			{
				CodiceFiscale = codiceFiscale,
				IndirizzoPec = anagrafe.Pec
			};
		}

		
	}
}
