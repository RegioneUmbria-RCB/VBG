using System.Collections.Generic;
using System.ComponentModel;
using System.Web.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using PersonalLib2.Sql;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for AllegatiEndoprocedimenti
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	public class AllegatiEndoprocedimentiService : System.Web.Services.WebService
	{
		[WebMethod]
		public List<InventarioProcedimenti> GetAllegatiEndoprocedimenti(string token , List<string> codiciEndoSelezionati , AmbitoRicerca ambitoRicercaDocumenti)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				throw new InvalidTokenException(token);

			string whereIn = string.Join(",", codiciEndoSelezionati.ToArray());

			var filtro = new InventarioProcedimenti();
			filtro.Idcomune = authInfo.IdComune;
			filtro.OthersWhereClause.Add("codiceinventario in (" + whereIn + ")");
			//filtro.OthersWhereClause.Add("pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumenti) + ")");
			filtro.UseForeign = useForeignEnum.Recoursive;

			using (var db = authInfo.CreateDatabase())
			{
				var invProc = new InventarioProcedimentiMgr(db).GetList(filtro);
				var flagPubblicaAccettati = new List<string>(FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumenti).Split(','));

				var oggettiManager = new OggettiMgr(db);

				// Rimuovo gli allegati che hanno flag pubblica = 0
				foreach (var endoprocedimento in invProc)
				{
					var allegatiDaRimuovere = new List<Allegati>();
					var allegatiGiaInseriti = new List<int>();

					foreach (var all in endoprocedimento.Allegati)
					{
						var flagPubblica = all.Pubblica.GetValueOrDefault(0).ToString();

						if (!flagPubblicaAccettati.Contains(flagPubblica) ||
							allegatiGiaInseriti.Contains(all.Id.Value) )
						{
							allegatiDaRimuovere.Add(all);
							continue;
						}

						allegatiGiaInseriti.Add(all.Id.Value);

						if (all.Codiceoggetto.HasValue)
							all.NomeFile = oggettiManager.GetNomeFile(authInfo.IdComune, all.Codiceoggetto.Value);
					}

					foreach (var allegatoDaRimuovere in allegatiDaRimuovere)
						endoprocedimento.Allegati.Remove(allegatoDaRimuovere);
				}

				return invProc;
			}
		}
	}
}
