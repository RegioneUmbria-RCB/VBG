using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Script.Services;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using System.Data;

namespace Sigepro.net.Istanze.SitFirenze
{
	/// <summary>
	/// Summary description for GeoInService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	[System.Web.Script.Services.ScriptService]
	public class GeoinService : System.Web.Services.WebService
	{

		[WebMethod]
		[ScriptMethod]
		public object GetParametriVerticalizzazione(string token, string idComune, string software)
		{
			var authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				return new { errore = "Token " + token + " non valido" };

			using (var db = authInfo.CreateDatabase())
			{
				// Codice preso da GeoInGestioneVert
				if (software == "CART")
				{
					return new
					{
						panelKey = "background",
						layerKey = "",
						rendererKeyAttive = "",
						rendererKeyCessate = "",
						urlProxy = ""
					};
				}

				if (software == "TOPO")
				{
					return new
					{
						panelKey = "topo",
						layerKey = "",
						rendererKeyAttive = "",
						rendererKeyCessate = "",
						urlProxy = ""
					};
				}


                var vert = new VerticalizzazioneSitQuaestioflorenzia(authInfo.Alias, software);

				if (vert.Attiva)
				{
					return new
					{
						panelKey = vert.PanelKey,
						layerKey = vert.LayerKey,
						rendererKeyAttive = vert.RendererKeyAttivo,
						rendererKeyCessate = vert.RendererKeyCessato,
						urlProxy = vert.UrlProxy
					};
				}


				return new { errore = "La verticalizzazione QuaestioFlorentia non è attiva" };
			}
		}

		[WebMethod]
		[ScriptMethod]
		public object GetCodiciCiviciAttivita(string token, string idComune, string software, string stato)
		{
			var authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				return new { errore = "Token " + token + " non valido" };

			using (var db = authInfo.CreateDatabase())
			{
				try
				{
					db.Connection.Open();

					var result = new List<string>();

					var datiVerticalizzazione = new VerticalizzazioneIAttivita(authInfo.Alias, software);

					if (!datiVerticalizzazione.Attiva)
						return result;

					var sql = @"SELECT 
									ID_PUNTO_SIT 
								FROM 
									I_ATTIVITA,
									istanzestradario,
									istanze
								WHERE
									istanze.idcomune            = I_ATTIVITA.idcomune AND
									istanze.codiceistanza       = I_ATTIVITA.codiceistanzaultima AND
									istanzestradario.idcomune   = istanze.idcomune AND
									istanzestradario.codiceistanza = istanze.codiceistanza and
									istanze.idcomune = {0} and
									istanze.software = {1} and
									I_ATTIVITA.attiva	= {2} and
									istanzestradario.ID_PUNTO_SIT IS NOT null";

					sql = String.Format(sql, db.Specifics.QueryParameterName("idcomune"),
											 db.Specifics.QueryParameterName("software"),
											 db.Specifics.QueryParameterName("attiva"));

					using (var cmd = db.CreateCommand(sql))
					{
						cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
						cmd.Parameters.Add(db.CreateParameter("software", software));
						cmd.Parameters.Add(db.CreateParameter("attiva", stato == "attive" ? 1 : 0));

						using (var dr = cmd.ExecuteReader())
						{
							while (dr.Read())
								result.Add(dr[0].ToString());

							return result;
						}
					}
				}
				finally
				{
					db.Connection.Close();
				}
			}
		}

		[WebMethod]
		[ScriptMethod]
		public object GetPermessiEditing(string token, string idComune, string software, string modalita)
		{
			var authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				return new { errore = "Token " + token + " non valido" };

			return GetPermessoEditing(authInfo, modalita, software);
		}

		[WebMethod]
		[ScriptMethod]
		public object SalvaIdPunto(string token, string idComune, int idStradario, string idPunto) 
		{
			var authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				return new { errore = "Token " + token + " non valido" };

			var mgr = new IstanzeStradarioMgr( authInfo.CreateDatabase() );
			
			var stradario = mgr.GetById( idComune, idStradario );

			stradario.IdPuntoSit = idPunto;

			mgr.Update(stradario);

			return true;
		}

		[WebMethod]
		[ScriptMethod]
		public object RimuoviIdPunto(string token, string idComune, int idStradario)
		{
			var authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				return new { errore = "Token " + token + " non valido" };

			var mgr = new IstanzeStradarioMgr(authInfo.CreateDatabase());

			var stradario = mgr.GetById(authInfo.IdComune, idStradario);

			stradario.IdPuntoSit = null;

			mgr.Update(stradario);

			return true;
		}

		[WebMethod]
		[ScriptMethod]
		public object GetDettagliIstanzaDaIdPunto(string token, string idPunto)
		{
			var authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				return new { errore = "Token " + token + " non valido" };


			var closeCnn = false;
			var db = authInfo.CreateDatabase();

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = @"SELECT 
								vw_i_attivitalista.*
							FROM                          
								vw_i_attivitalista,
								istanzestradario
							WHERE
								istanzestradario.idcomune       = vw_i_attivitalista.idcomune AND
								istanzestradario.codiceistanza  = vw_i_attivitalista.i_codiceistanzaultima AND
								istanzestradario.idComune = {0} and
								istanzestradario.ID_PUNTO_SIT   = {1}";

				sql = string.Format(sql, db.Specifics.QueryParameterName("idComune"), db.Specifics.QueryParameterName("idPunto"));

				using (var cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", authInfo.IdComune));
					cmd.Parameters.Add(db.CreateParameter("idPunto", idPunto));

					using (var dr = cmd.ExecuteReader())
					{
						if (!dr.Read())
							return null;

						return new
						{
							Denominazione = dr["denominazione"].ToString(),
							Attiva = dr["attiva"].ToString(),
							Operante = dr["operante"].ToString(),
							Richiedente = dr["richiedente"].ToString(),
							TipoSoggetto = dr["tiposoggetto"].ToString(),
							Azienda = dr["aziendarichiedente"].ToString()
						};
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}


		private bool GetPermessoEditing(AuthenticationInfo authInfo, string modalita, string software)
		{
			if (modalita == "I")
				return VerificaPermessoOperatore(authInfo, software);

			return false;

			// ERA:
			////Verifico se la pagina GeoIn.aspx viene aperta da attività o istanza
			//bool bPermesso = false;
			//switch (modalita)
			//{
			//    case "A":
			//        break;
			//    case "I":
			//        bPermesso = 
			//        break;
			//}

			//return bPermesso;
		}

		/// <summary>
		/// Verifico se l'operatore è dotato del ruolo che permette l'editing
		/// </summary>
		/// <param name="software"></param>
		/// <returns></returns>
		private bool VerificaPermessoOperatore(AuthenticationInfo authInfo, string software)
		{
			using (var db = authInfo.CreateDatabase())
			{
				var resp = new ResponsabiliMgr(db).GetById(authInfo.IdComune, authInfo.CodiceResponsabile.Value);

				//Se l'operatore è di sola lettura o disabilitato non può eseguire editing
				if ((resp.READONLY == "1") || (resp.DISABILITATO == "1"))
					return false;

				var datiVerticalizzazione = new VerticalizzazioneSitQuaestioflorenzia(authInfo.Alias, software);

				var codiceRuoloChePermetteEditing = datiVerticalizzazione.Attiva ? datiVerticalizzazione.CodRuoloEditing : string.Empty;

				var list = new ResponsabiliRuoliMgr(db).GetList(new ResponsabiliRuoli
				{
					IDCOMUNE = authInfo.IdComune,
					CODICERESPONSABILE = authInfo.CodiceResponsabile.Value.ToString()
				});

				foreach (var elem in list)
				{
					if (elem.IDRUOLO == codiceRuoloChePermetteEditing)
						return true;
				}

				return false;
			}
		}
	}
}
