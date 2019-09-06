using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Sigepro.net.WebServices.WsSIGePro;
using log4net;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using Init.SIGePro.Authentication;

namespace Sigepro.net.WebServices.WsSIGeProAnagrafe
{
	/// <summary>
	/// Summary description for WsAnagrafe2
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[System.ComponentModel.ToolboxItem(false)]
	[System.Web.Services.WebServiceBindingAttribute(Name = "WsAnagrafe2Soap", Namespace = "http://init.sigepro.it")]
	public class WsAnagrafe2 : SigeproWebService
	{
		ILog _log = LogManager.GetLogger(typeof(WsAnagrafe2));



		[WebMethod]
		public Anagrafe getPersonaFisica(String token, String codiceFiscale)
		{
			var authInfo = CheckToken(token);

			using( var db = authInfo.CreateDatabase())
			{
				var contestoRicerca = authInfo.Contesto == ContestoTokenEnum.Applicazione ? ContestoRicercaAnagraficaEnum.Frontoffice : ContestoRicercaAnagraficaEnum.Backoffice;
				var ricercheService = new RicercheAnagraficheService( db, authInfo.IdComune, authInfo.Alias, contestoRicerca);

				return ricercheService.GetByCodicefiscale( codiceFiscale );
			}
			
		}

		[WebMethod]
		public Anagrafe getPersonaGiuridica(String token, String cfImpresaPartitaIva)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				var contestoRicerca = authInfo.Contesto == ContestoTokenEnum.Applicazione ? ContestoRicercaAnagraficaEnum.Frontoffice: ContestoRicercaAnagraficaEnum.Backoffice;

                var ricercheService = new RicercheAnagraficheService(db, authInfo.IdComune, authInfo.Alias, contestoRicerca);

				return ricercheService.GetByPartitaIva(cfImpresaPartitaIva);
			}

		}
	}
}
