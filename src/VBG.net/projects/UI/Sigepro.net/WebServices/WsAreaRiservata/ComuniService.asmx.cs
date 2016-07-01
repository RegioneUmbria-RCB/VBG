using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Sigepro.net.WebServices.WsSIGePro;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for ComuniService
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class ComuniService : SigeproWebService
	{
		[WebMethod]
		public List<ComuniMgr.DatiComuneCompatto> FindComuniDaMatchParziale(string token, string matchComune)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
				return new ComuniMgr(db).FindComuniDaMatchParziale(matchComune);
		}

		[WebMethod]
		public List<ComuniMgr.DatiComuneCompatto> GetListaComuni(string token, string siglaProvincia)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
				return new ComuniMgr(db).GetListaComuni(siglaProvincia);
		}

		[WebMethod]
		public ComuniMgr.DatiComuneCompatto GetDatiComune(string token, string codiceComune)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
				return new ComuniMgr(db).GetDaticomune(codiceComune);
		}

		[WebMethod]
		public ComuniMgr.DatiProvinciaCompatto GetDatiProvincia(string token, string siglaProvincia)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
				return new ComuniMgr(db).GetDatiProvincia(siglaProvincia);
		}

		[WebMethod]
		public ComuniMgr.DatiProvinciaCompatto GetDatiProvinciaDaCodiceComune(string token, string codiceComune)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
				return new ComuniMgr(db).GetProvinciaDaCodiceComune(codiceComune);
		}

		[WebMethod]
		public List<ComuniMgr.DatiProvinciaCompatto> GetListaProvincie(string token)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
				return new ComuniMgr(db).GetListaProvincie();
		}

		[WebMethod]
		public List<ComuniMgr.DatiComuneCompatto> GetComuniAssociati(string token)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
				return new ComuniMgr(db).GetComuniAssociati(authResult.IdComune);
		}

		[WebMethod]
		public string GetPecComuneAssociato(string token, string codiceComuneAssociato, string software)
		{
			var authResult = CheckToken(token);

			using (var db = authResult.CreateDatabase())
				return new ComuniAssociatiSoftwareMgr(db).GetIndirizzoPecComuneAssociato(authResult.IdComune, codiceComuneAssociato, software);
		}


		[WebMethod]
		public List<Cittadinanza> GetCittadinanze(string token)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
			{
				var filtro = new Cittadinanza
				{
					Disabilitato = 0,
					OrderBy = "CITTADINANZA ASC"
				};

				return new CittadinanzaMgr(db).GetList(filtro);
			}
		}
	}
}
