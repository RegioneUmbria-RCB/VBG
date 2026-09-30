using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager.DTO.Configurazione;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;

namespace Sigepro.net.WebServices.WsBari
{
	/// <summary>
	/// Summary description for BariConfigService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class BariConfigService : SigeproWebService
	{
		[WebMethod]
		public ConfigurazioneCidBariDto GetConfigurazioneCid(string token, string software)
		{
			var ai = CheckToken(token);

			var vert = new VerticalizzazioneCidBari(ai.Alias, software);

			if (!vert.Attiva)
				return null;

			return new ConfigurazioneCidBariDto
			{
				IdentificativoUtente = vert.IdentificativoUtente,
				Password = vert.Password,
				UrlServizio = vert.UrlServizio
			};
		}

		[WebMethod]
		public ConfigurazioneTaresBariDto GetConfigurazioneTares(string token, string software)
		{
			var ai = CheckToken(token);

			var verticalizzazioneTares = new VerticalizzazioneTaresBari(ai.Alias, software);

			if (!verticalizzazioneTares.Attiva)
				return null;

			return new ConfigurazioneTaresBariDto
			{
				IdentificativoUtente = verticalizzazioneTares.IdentificativoUtente,
				IndirizzoPec = verticalizzazioneTares.IndirizzoPec,
				Password = verticalizzazioneTares.Password,
				UrlServizioAgevolazioniTares = verticalizzazioneTares.UrlServizio,
				UrlServizioAgevolazioniTasi = verticalizzazioneTares.UrlServizioAgevolazioneTasi,
				UrlServizioAgevolazioniImu = verticalizzazioneTares.UrlServizioAgevolazioneImu,
                CodiceFiscaleCafFittizio = verticalizzazioneTares.CodiceFiscaleCafFittizio,
                EmailCafFittizio = verticalizzazioneTares.EmailCafFittizio,
				UrlServizioFirmaCid = verticalizzazioneTares.UrlServizioFirmaCid
			};
		}

	}
}
