using System;
using System.Linq;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.ComponentModel;
using Init.SIGePro.Data;
using System.Collections.Generic;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.DTO.AllegatiDomandaOnline;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for Interventi
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	public class Interventi : SigeproWebService
	{

		[WebMethod]
		public List<AllegatoInterventoDomandaOnlineDto> GetDocumentiDaCodiceIntervento(string token, int codiceIntervento, AmbitoRicerca ambitoRicercaDocumenti)
		{
			var authInfo = CheckToken(token);
			var idcomune = authInfo.IdComune;
			
			using(var db = authInfo.CreateDatabase())
			{
				var risoluzioneNomeFile = new Func<int, string>((x) => { return new OggettiMgr(db).GetNomeFile(idcomune, x); });

				// Documenti dell'intervento
				var alberoProcDocumentiMgr = new AlberoProcDocumentiMgr(db);
				var allegatiIntervento = alberoProcDocumentiMgr.GetListDaCodiceIntervento(authInfo.IdComune, codiceIntervento, ambitoRicercaDocumenti)
															   .Select(doc => AllegatoInterventoDomandaOnlineDto.FromAllegatoIntervento(doc, risoluzioneNomeFile ));

				// Documenti della procedura
				var codiceProcedura = new AlberoProcMgr(db).CodiceProceduraDaIdIntervento(authInfo.IdComune, codiceIntervento);

				if (!codiceProcedura.HasValue)
					return allegatiIntervento.ToList();

				var allegatiProcedura = new TipiProcedureDocumentiMgr(db).GetByIdProcedura(authInfo.IdComune, codiceProcedura.Value, ambitoRicercaDocumenti)
																		 .Select(doc => AllegatoInterventoDomandaOnlineDto.FromAllegatoProcedura(doc, risoluzioneNomeFile ));

				return allegatiIntervento.Union(allegatiProcedura).ToList();
			}
		}


		[WebMethod]
		public List<AlberoProcDocumentiCat> GetCategorieAllegatiChePermettonoUpload(string token, string software)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			AlberoProcDocumentiCat filtro = new AlberoProcDocumentiCat();
			filtro.Idcomune = authInfo.IdComune;
			filtro.Software = software;
			filtro.OthersWhereClause.Add( "FO_NONPERMETTEUPLOAD<>1" );
			filtro.OrderBy = "descrizione asc";

			return new AlberoProcDocumentiCatMgr(authInfo.CreateDatabase()).GetList(filtro);
		}

		[WebMethod]
		public List<BaseDto<int, string>> RicercaTestualeInterventi(string token, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, AmbitoRicerca ambitoRicerca)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				return new AlberoProcMgr(db).RicercaTestualeInterventi(authInfo.IdComune, software, matchParziale, matchCount, modoRicerca, tipoRicerca, ambitoRicerca);
			}
		}


		[WebMethod]
		public List<int> GetListaIdNodiPadre(string token, int codiceIntervento)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				return new AlberoProcMgr(db).GetListaIdNodiPadre(authInfo.IdComune, codiceIntervento);
			}
		}
	}
}
