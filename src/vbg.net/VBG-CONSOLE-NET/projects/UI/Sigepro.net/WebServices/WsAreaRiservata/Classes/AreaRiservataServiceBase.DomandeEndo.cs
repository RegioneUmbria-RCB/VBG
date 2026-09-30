using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Manager.DTO.DomndeIndividuazioneEndo;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<DomandeFrontAlberoMgr.InfoAreaTematica> GetAreeTematiche(string token, string software, int idAreaPadre)
		{
			return new WsIndividuazioneProcedimenti().GetAreeTematiche(token ,software , idAreaPadre );
		}

		[WebMethod]
		public List<DomandeFrontAlberoMgr.InfoDomandaAreaTematica> GetDomandeArea(string token, int idArea)
		{
			return new WsIndividuazioneProcedimenti().GetDomandeArea( token , idArea );
		}

		[WebMethod]
		public List<DomandeFrontAlberoMgr.InfoEndo> GetEndoDomanda(string token, int idDomanda)
		{
			return new WsIndividuazioneProcedimenti().GetEndoDomanda(token, idDomanda);
		}

		[WebMethod]
		public List<AreaIndividuazioneEndoDto> GetStrutturaDomande(string token, string software)
		{
			return new WsIndividuazioneProcedimenti().GetStrutturaDomande(token, software);
		}
	}
}
