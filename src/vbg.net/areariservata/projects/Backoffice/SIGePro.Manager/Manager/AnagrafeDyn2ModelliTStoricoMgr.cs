
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class AnagrafeDyn2ModelliTStoricoMgr
    {
		/// <summary>
		/// Restituisce il numero di righe presenti nell'archivio storico
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="codiceAnagrafe"></param>
		/// <param name="idModello"></param>
		/// <returns></returns>
		public int ContaRigheStorico(string idComune, int codiceAnagrafe, int idModello)
		{
			return GetList(idComune, codiceAnagrafe, idModello).Count;
		}


		/// <summary>
		/// Restituisce la lista di righe corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="codiceIstanza"></param>
		/// <param name="idModello"></param>
		/// <returns></returns>
		public List<AnagrafeDyn2ModelliTStorico> GetList(string idComune, int codiceAnagrafe, int idModello)
		{
			AnagrafeDyn2ModelliTStorico filtro = new AnagrafeDyn2ModelliTStorico();

			filtro.Idcomune = idComune;
			filtro.Codiceanagrafe = codiceAnagrafe;
			filtro.FkD2mtId = idModello;
			filtro.OrderBy = "IDVERSIONE ASC";

			return GetList(filtro);
		}


		private AnagrafeDyn2ModelliTStorico DataIntegrations(AnagrafeDyn2ModelliTStorico cls)
		{
			if (!cls.Dataversione.HasValue)
				cls.Dataversione = DateTime.Now;

			if (!cls.Idversione.HasValue)
			{
				var where = new List<KeyValuePair<string, object>>{
																	new KeyValuePair<string,object>( "codiceanagrafe", cls.Codiceanagrafe ),
																	new KeyValuePair<string,object>( "fk_d2mt_id", cls.FkD2mtId )
					 											};

				cls.Idversione = FindMax("idversione", "anagrafedyn2modellit_storico", cls.Idcomune, where);
			}

			return cls;
		}

		private void EffettuaCancellazioneACascata(AnagrafeDyn2ModelliTStorico cls)
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
			AnagrafeDyn2DatiStoricoMgr datiMgr = new AnagrafeDyn2DatiStoricoMgr(db);

			AnagrafeDyn2DatiStorico filtro = new AnagrafeDyn2DatiStorico();
			filtro.Idcomune = cls.Idcomune;
			filtro.Idversione = cls.Idversione;
			filtro.Codiceanagrafe = cls.Codiceanagrafe;
			filtro.FkD2mtId = cls.FkD2mtId;

			List<AnagrafeDyn2DatiStorico> datiDaEliminare = datiMgr.GetList(filtro);

			for (int i = 0; i < datiDaEliminare.Count; i++)
			{
				datiMgr.Delete(datiDaEliminare[i]);
			}
		}
	}
}
				