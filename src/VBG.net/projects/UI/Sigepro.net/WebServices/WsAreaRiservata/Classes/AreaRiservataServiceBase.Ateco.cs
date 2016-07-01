using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Manager;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public Ateco GetDettagliAteco(string token, int id)
		{
			return new AtecoService().GetDettagliAteco(token, id);
		}

		[WebMethod]
		public List<Ateco> GetNodiFiglioAteco(string token, int? idPadre)
		{
			return new AtecoService().GetNodiFiglioAteco(token, idPadre);
		}

		[WebMethod]
		public List<Ateco> RicercaAteco(string token, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca)
		{
			return new AtecoService().RicercaAteco(token, matchParziale , matchCount,modoRicerca , tipoRicerca);
		}

		[WebMethod]
		public List<int> CaricaListaIdGerarchiaAteco(string token, int id)
		{
			return new AtecoService().CaricaListaIdGerarchiaAteco(token, id);
		}

		[WebMethod]
		public NodoAlberoInterventiDto GetAlberoProcDaIdAteco(string token, int idAteco, AmbitoRicerca ambitoRicerca)
		{
			return new AtecoService().GetAlberoProcDaIdAteco(token, idAteco,ambitoRicerca);
		}

		[WebMethod]
		public bool VerificaEsistenzaNodiCollegatiAIdAteco(string token, string software, int idAteco, AmbitoRicerca ambitoRicerca)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				var res = new AtecoMgr(db).CercaNodiConLink(authInfo.IdComune, software, idAteco, ambitoRicerca);

				return res >= 0;
			}
		}
	}
}
