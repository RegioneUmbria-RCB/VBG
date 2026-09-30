using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Script.Services;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Utils;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;

namespace Init.Sigepro.FrontEnd.Public.WebServices
{
	/// <summary>
	/// Summary description for AutocompleteComuni
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	[System.Web.Script.Services.ScriptService]
	public class AutocompleteComuni : Ninject.Web.WebServiceBase
	{
		[Inject]
		public IComuniService _comuniRepository { get; set; }


		[WebMethod(EnableSession = true)]
		[ScriptMethod]
		public DatiComuneCompatto[] RicercaComune(string aliasComune,string matchComune)
		{
			if (matchComune.Length < 2 || matchComune.IndexOf('%') != -1)
				return new DatiComuneCompatto[0];

			return _comuniRepository.FindComuneDaMatchParziale(aliasComune, matchComune).ToArray();
		}

		[WebMethod(EnableSession = true)]
		[ScriptMethod]
		public DatiProvinciaCompatto[] RicercaProvincia(string aliasComune, string matchProvincia)
		{
			if (matchProvincia.Length < 2 || matchProvincia.IndexOf('%') != -1)
				return new DatiProvinciaCompatto[0];

			return _comuniRepository.FindProvinciaDaMatchParziale(aliasComune, matchProvincia).ToArray();
		}
	}
}
