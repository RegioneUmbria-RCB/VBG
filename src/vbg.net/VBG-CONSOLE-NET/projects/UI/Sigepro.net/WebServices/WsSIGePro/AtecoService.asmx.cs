using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Interventi;
using Sigepro.net.WebServices.WsAreaRiservata.Classes;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for AtecoService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class AtecoService : System.Web.Services.WebService
	{

		[WebMethod]
		public Ateco GetDettagliAteco(string token , int id)
		{
			var ai = CheckToken(token);

			var cls = new AtecoMgr(ai.CreateDatabase()).GetById(id);

			cls.Descrizione = String.IsNullOrEmpty(cls.Descrizione) ? String.Empty : cls.Descrizione.Replace("\n", "<br /><br />");

			return cls;
		}

		[WebMethod]
		public List<Ateco> GetNodiFiglioAteco(string token, int? idPadre)
		{
			var ai = CheckToken(token);

			return new AtecoMgr(ai.CreateDatabase()).GetNodiFiglioByIdPadre(idPadre);
		}

		[WebMethod]
		public List<Ateco> RicercaAteco(string token, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca)
		{
			var ai = CheckToken(token);

			return new AtecoMgr(ai.CreateDatabase()).RicercaAteco(matchParziale, matchCount, modoRicerca , tipoRicerca);
		}

		[WebMethod]
		public List<int> CaricaListaIdGerarchiaAteco(string token, int id)
		{
			var ai = CheckToken(token);

			return new AtecoMgr(ai.CreateDatabase()).CaricaListaIdGerarchia(id);
		}


		[WebMethod]
		public NodoAlberoInterventiDto GetAlberoProcDaIdAteco(string token, int idAteco, AmbitoRicerca ambitoRicerca)
		{
			var ai = CheckToken(token);

			var listaNodi = new AtecoMgr(ai.CreateDatabase()).GetListaAlberoProcDaIdAteco(ai.IdComune, idAteco,ambitoRicerca);

			return new GeneratoreAlberoInterventiDaListaInterventi(listaNodi).GeneraAlbero();
		}

		private AuthenticationInfo CheckToken(string token)
		{
			AuthenticationInfo ai = AuthenticationManager.CheckToken(token);

			if (ai == null)
				throw new InvalidTokenException(token);

			return ai;
		}
	}
}
