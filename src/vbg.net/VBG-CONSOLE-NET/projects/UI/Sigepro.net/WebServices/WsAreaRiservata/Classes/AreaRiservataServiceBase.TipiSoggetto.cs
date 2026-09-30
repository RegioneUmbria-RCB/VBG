using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Manager;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<TipiSoggetto> GetListaTipiSoggettoFrontoffice(string token, string software, string tipoAnagrafe, int? codiceIntervento)
		{
			var ai = CheckToken(token);

			return new TipiSoggettoMgr(ai.CreateDatabase(), ai.IdComune).GetTipiSoggettoDaCodiceIntervento(software, tipoAnagrafe, codiceIntervento).ToList();
		}

		[WebMethod]
		public TipiSoggetto GetTipoSoggettoById(string token, int codiceTipoSoggetto)
		{
			var ai = CheckToken(token);

			return new TipiSoggettoMgr(ai.CreateDatabase(), ai.IdComune).GetById(codiceTipoSoggetto);
		}
	}
}
