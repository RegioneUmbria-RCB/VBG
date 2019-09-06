
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
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IstanzeDyn2ModelliTStoricoMgr
    {
		/// <summary>
		/// Restituisce il numero di righe presenti nell'archivio storico
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="codiceIstanza"></param>
		/// <param name="idModello"></param>
		/// <returns></returns>
		public int ContaRigheStorico(string idComune, int codiceIstanza, int idModello)
		{
			return GetList(idComune, codiceIstanza, idModello).Count;
		}

		/// <summary>
		/// Restituisce la lista di righe corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="codiceIstanza"></param>
		/// <param name="idModello"></param>
		/// <returns></returns>
		public List<IstanzeDyn2ModelliTStorico> GetList(string idComune, int codiceIstanza, int idModello)
		{
			IstanzeDyn2ModelliTStorico filtro = new IstanzeDyn2ModelliTStorico();
			
			filtro.Idcomune = idComune;
			filtro.Codiceistanza = codiceIstanza;
			filtro.FkD2mtId = idModello;
			filtro.OrderBy = "IDVERSIONE ASC";

			return GetList(filtro);
		}

		private IstanzeDyn2ModelliTStorico DataIntegrations(IstanzeDyn2ModelliTStorico cls)
		{
			if (!cls.Dataversione.HasValue)
				cls.Dataversione = DateTime.Now;

			if (!cls.Idversione.HasValue)
			{
				var where = new List<KeyValuePair<string, object>>{
																	new KeyValuePair<string,object>( "codiceistanza", cls.Codiceistanza ),
																	new KeyValuePair<string,object>( "fk_d2mt_id", cls.FkD2mtId )
					 											};


				cls.Idversione = FindMax("idversione", "istanzedyn2modellit_storico", cls.Idcomune, where);
			}

			return cls;
		}


		private void EffettuaCancellazioneACascata(IstanzeDyn2ModelliTStorico cls)
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
			IstanzeDyn2DatiStoricoMgr datiMgr = new IstanzeDyn2DatiStoricoMgr(db);

			IstanzeDyn2DatiStorico filtro = new IstanzeDyn2DatiStorico();
			filtro.Idcomune = cls.Idcomune;
			filtro.Idversione = cls.Idversione;
			filtro.Codiceistanza = cls.Codiceistanza;
			filtro.FkD2mtId = cls.FkD2mtId;

			List<IstanzeDyn2DatiStorico> datiDaEliminare = datiMgr.GetList(filtro);

			for (int i = 0; i < datiDaEliminare.Count; i++)
			{
				datiMgr.Delete(datiDaEliminare[i]);
			}
		}
	}
}
				