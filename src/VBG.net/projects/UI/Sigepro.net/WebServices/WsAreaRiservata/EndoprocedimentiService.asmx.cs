using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.WebServices.WsAreaRiservata.Classes;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.DTO;
using log4net;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for EndoprocedimentiService
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class EndoprocedimentiService : System.Web.Services.WebService
	{
		ILog _log = LogManager.GetLogger(typeof(EndoprocedimentiService));

		[WebMethod]
		public List<BaseDto<int,string>> GetEndoprocedimentiPropostiDaCodiceIntervento(string token , int codiceIntervento)
		{
			var authInfo = CheckToken( token );

			return new InventarioProcedimentiMgr(authInfo.CreateDatabase()).GetEndoprocedimentiPropostiDaCodiceIntervento(authInfo.IdComune, codiceIntervento);
		}

		[WebMethod]
		public EndoprocedimentoDto GetEndoprocedimentoById(string token, int codiceEndo, AmbitoRicerca ambitoRicercaDocumenti)
		{
			var authInfo = CheckToken( token );

			using(var db = authInfo.CreateDatabase())
			{
				var idComune = authInfo.IdComune;

				var mgr = new InventarioProcedimentiMgr(db);

				var sigEndo = mgr.GetById(authInfo.IdComune, codiceEndo);

				if (sigEndo == null)
					return new EndoprocedimentoDto();

				var amministrazione = new AmministrazioniMgr(db).GetById(idComune, sigEndo.Amministrazione.GetValueOrDefault(-999));
				var tipoMovimento = new TipiMovimentoMgr(db).GetById(String.IsNullOrEmpty(sigEndo.Tipomovimento) ? "-999" : sigEndo.Tipomovimento, idComune);
				var natura = new NaturaEndoMgr(db).GetById( idComune , sigEndo.Codicenatura.GetValueOrDefault(-1));
				var tempificazione = new TempificazioniMgr(db).GetById(idComune, sigEndo.Tempificazione.GetValueOrDefault(-1));
				var tipoEndo = new TipiEndoMgr(db).GetById( sigEndo.Codicetipo.GetValueOrDefault(-1), idComune);
				var testiEstesi = new TestiEstesiMgr(db).GetByCodiceInventario(idComune, sigEndo.Codiceinventario.Value);
				var allegati = new AllegatiMgr(db).GetByCodiceInventario(idComune, sigEndo.Codiceinventario.Value,ambitoRicercaDocumenti);
				var normative = new InventarioprocLeggiMgr(db).GetByCodiceInventario(idComune, sigEndo.Codiceinventario.Value);
				var oneri = mgr.GetOneriDaCodiceEndo(idComune, codiceEndo);


				var endo = new EndoprocedimentoDto
				{
					Codice = sigEndo.Codiceinventario.Value,
					Adempimenti = sigEndo.Adempimenti,
					Amministrazione = amministrazione == null ? String.Empty : amministrazione.AMMINISTRAZIONE,
					Applicazione = sigEndo.Campoapplicazione,
					DatiGenerali = sigEndo.Datigenerali,
					Descrizione = sigEndo.Procedimento,
					Movimento = tipoMovimento == null ? String.Empty : tipoMovimento.Movimento,
					CodiceNatura = natura == null ? (int?)null : Convert.ToInt32(natura.CODICENATURA),
					Natura = natura == null ? String.Empty : natura.NATURA,
					BinarioDipendenze = natura == null ? 0 : Convert.ToInt32(natura.BINARIODIPENDENZE),
					NormativaNazionale = sigEndo.Normativana,
					NormativaRegionale = sigEndo.Normativare,
					NormativaUE = sigEndo.Normativaue,
					Regolamenti = sigEndo.Regolamenti,
					Tempificazione = tempificazione == null ? String.Empty : tempificazione.Tempificazione,
					Tipologia = tipoEndo == null ? String.Empty : tipoEndo.Tipo,
					UltimoAggiornamento = sigEndo.Dataaggiornamento,
					Normative = normative,
					Oneri = oneri
				};

				var normativeMgr = new NormativeMgr(db);

				for (int i = 0; i < testiEstesi.Count; i++)
				{
					var el = testiEstesi[i];

					var normativa = normativeMgr.GetById(idComune, el.Tiponorma.GetValueOrDefault(-999));

					var te = new TestiEstesiDto
					{
						Codice = el.Id.Value,
						Descrizione = el.Normativa,
						Normativa = normativa == null ? String.Empty : normativa.Normativa,
						CodiceOggetto = el.Codiceoggetto,
						Link = el.Indirizzoweb
					};

					endo.TestiEstesi.Add(te);
				}

				for (int i = 0; i < allegati.Count; i++)
				{
					var el = allegati[i];

					var al = new AllegatoDto
					{
						Codice = el.Id.Value,
						Descrizione = el.Allegato,
						Link = el.Indirizzoweb,
						CodiceOggetto = el.Codiceoggetto,
						FormatiDownload = el.FoTipodownload
					};

					endo.Allegati.Add(al);
				}

				return endo;
			}
		}

		[WebMethod]
		public List<EndoprocedimentoDto> GetEndoprocedimentiList(string token, List<int> listaId)
		{
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
			{
				var endoSigepro = new InventarioProcedimentiMgr(db).GetEndoprocedimentiList( ai.IdComune, listaId);

				var rVal = new List<EndoprocedimentoDto>();

				endoSigepro.ForEach( sigEndo => 
				{
					var natura = new NaturaEndoMgr(db).GetById(ai.IdComune, sigEndo.Codicenatura.GetValueOrDefault(-1));

					rVal.Add( new EndoprocedimentoDto
					{
						Codice = sigEndo.Codiceinventario.Value,
						Adempimenti = sigEndo.Adempimenti,
						Applicazione = sigEndo.Campoapplicazione,
						DatiGenerali = sigEndo.Datigenerali,
						CodiceNatura = natura == null ? (int?)null : Convert.ToInt32(natura.CODICENATURA),
						Natura = natura == null ? String.Empty : natura.NATURA,
						BinarioDipendenze = natura == null ? 0 : Convert.ToInt32(natura.BINARIODIPENDENZE),
						Descrizione = sigEndo.Procedimento,
						NormativaNazionale = sigEndo.Normativana,
						NormativaRegionale = sigEndo.Normativare,
						NormativaUE = sigEndo.Normativaue,
						Regolamenti = sigEndo.Regolamenti
					});
				});

				return rVal;
			}
		}

		[WebMethod]
		public ListaEndoDaIdInterventoDto GetListaEndoDaIdIntervento(string token, int codiceIntervento)
		{
			try
			{
				var ai = CheckToken(token);

				using (var db = ai.CreateDatabase())
				{
					var idComune = ai.IdComune;
					var rVal = new ListaEndoDaIdInterventoDto();
					var interventiMgr = new AlberoProcMgr(db);

					rVal.EndoIntervento = interventiMgr.GetEndoDaIdIntervento(idComune, codiceIntervento, AmbitoRicerca.AreaRiservata);
					rVal.EndoFacoltativi = interventiMgr.GetEndoFacoltativiDaIdIntervento(idComune, codiceIntervento);

					return rVal;
				}
			}
			catch(Exception ex)
			{
				_log.ErrorFormat( "Errore nella chiamata a GetListaEndoDaIdIntervento con token {0} e codiceIntervento {1}:\r\n{2}", token , codiceIntervento , ex.ToString() );

				throw;
			}
		}

		private AuthenticationInfo CheckToken(string token)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				throw new InvalidTokenException(token);

			return authInfo;
		}
	}
}
