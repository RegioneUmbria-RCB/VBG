using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using System.Collections.Generic;
using Init.SIGePro.Manager.DTO.DomndeIndividuazioneEndo;
using Init.SIGePro.Data;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for WsDomandeIndividuazioneProcedimenti
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	public class WsIndividuazioneProcedimenti : SigeproWebService
	{
		
		[WebMethod]
		public List<DomandeFrontAlberoMgr.InfoAreaTematica> GetAreeTematiche(string token, string software, int idAreaPadre)
		{
			var authInfo = CheckToken(token);

			DomandeFrontAlberoMgr mgr = new DomandeFrontAlberoMgr(authInfo.CreateDatabase());
			return mgr.GetInfoAreeTematiche(authInfo.IdComune, software, idAreaPadre);
		}

		[WebMethod]
		public List<DomandeFrontAlberoMgr.InfoDomandaAreaTematica> GetDomandeArea(string token, int idArea)
		{
			var authInfo = CheckToken(token);

			DomandeFrontAlberoMgr mgr = new DomandeFrontAlberoMgr(authInfo.CreateDatabase());
			return mgr.GetDomandeAreaTematica(authInfo.IdComune, idArea);
		}

		[WebMethod]
		public List<DomandeFrontAlberoMgr.InfoEndo> GetEndoDomanda(string token, int idDomanda)
		{
			var authInfo = CheckToken(token);

			DomandeFrontAlberoMgr mgr = new DomandeFrontAlberoMgr(authInfo.CreateDatabase());
			return mgr.GetInfoEndoDomanda(authInfo.IdComune, idDomanda);
		}

		[WebMethod]
		public List<AreaIndividuazioneEndoDto> GetStrutturaDomande(string token, string software)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				// Leggo tutte le aree del software

				var filtro = new DomandeFrontAlbero{
					Idcomune = authInfo.IdComune,
					Software = software,
					OrderBy = "Ordine asc"
				};
				filtro.OthersWhereClause.Add("disattiva = 0");

				var mgr = new DomandeFrontAlberoMgr( db );
				var areeTematiche = mgr.GetList( filtro );

				var idxDomande = new Dictionary<int, AreaIndividuazioneEndoDto>();

				foreach (var area in areeTematiche)
				{
					var el = new AreaIndividuazioneEndoDto
					{
						Codice = area.Id.Value,
						Descrizione = area.Descrizione,
						Note = area.Note,
						IdPadre = area.Idpadre.GetValueOrDefault(-1)
					};

					idxDomande.Add(area.Id.Value, el);
				}

				// Ricreo l'albero delle aree
				var rVal = new List<AreaIndividuazioneEndoDto>();

				foreach (var area in idxDomande.Values)
				{
					if (area.IdPadre == -1)
						rVal.Add(area);
					else
						idxDomande[area.IdPadre].SottoAree.Add(area);
				}

				// Leggo la lista delle domande
				var filtroDomande = new DomandeFront
				{
					Idcomune = authInfo.IdComune,
					Software = software,
					OrderBy = "domanda asc"
				};

				var listaDomande = new DomandeFrontMgr(db).GetList(filtroDomande);

				// Associo le domande alle aree corrette
				foreach (var domanda in listaDomande)
				{
					var el = new DomandaIndividuazioneEndoDto
					{
						Codice = domanda.Codicedomanda.Value,
						Descrizione = domanda.Domanda
					};

					var idArea = domanda.FkDfaId.Value;

					if (idxDomande.ContainsKey(idArea))
						idxDomande[idArea].Domande.Add(el);
				}

				return rVal;
			}
		}
	}
}
