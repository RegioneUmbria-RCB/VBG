using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Script.Services;

using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.IoC;

namespace Init.Sigepro.FrontEnd.Public.WebServices
{
	/// <summary>
	/// Summary description for TipiSoggettoService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	[ScriptService]
	public class TipiSoggettoJsService : Ninject.Web.WebServiceBase
	{
		[Inject]
		public ITipiSoggettoService _tipiSoggettoRepository { get; set; }

		[WebMethod(EnableSession = true)]
		[ScriptMethod(ResponseFormat = ResponseFormat.Json)]
		public bool RichiedeDescrizioneEstesa(string idSoggetto)
		{
			if (String.IsNullOrEmpty(idSoggetto))
				return false;
		
			var sogg = _tipiSoggettoRepository.GetById(Convert.ToInt32(idSoggetto));

			return (sogg != null && sogg.RichiedeDescrizioneEstesa);
		}

		[WebMethod(EnableSession = true)]
		[ScriptMethod(ResponseFormat = ResponseFormat.Json)]
		public TipoSoggetto GetById(string idSoggetto)
		{
			if (String.IsNullOrEmpty(idSoggetto))
				return new TipoSoggetto();

			return _tipiSoggettoRepository.GetById(Convert.ToInt32(idSoggetto));
		}
	}
}
