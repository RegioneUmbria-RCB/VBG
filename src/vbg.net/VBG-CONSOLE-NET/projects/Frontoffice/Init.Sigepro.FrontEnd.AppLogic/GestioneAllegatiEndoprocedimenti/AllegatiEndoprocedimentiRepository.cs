// -----------------------------------------------------------------------
// <copyright file="AllegatiEndoprocedimentiRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using CuttingEdge.Conditions;

	public interface IAllegatiEndoprocedimentiRepository
	{
		IEnumerable<InventarioProcedimenti> GetDatiProcedimenti(string idComune, List<int> codiciEndoSelezionati);
	}

	internal class WsAllegatiEndoprocedimentiRepository : IAllegatiEndoprocedimentiRepository
	{
		AreaRiservataServiceCreator _serviceCreator;

		public WsAllegatiEndoprocedimentiRepository(AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}

		public IEnumerable<InventarioProcedimenti> GetDatiProcedimenti(string idComune, List<int> codiciEndoSelezionati)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				// Leggo la lista degli endo selezionati
				//var endoSelezionati = dataSet.ISTANZEPROCEDIMENTI.GetCodiciEndoSelezionati(ignoraPresenti);

				if (codiciEndoSelezionati.Count == 0)
					return new List<InventarioProcedimenti>();

				var endoSelezionatiStr = new string[codiciEndoSelezionati.Count];

				for (int i = 0; i < codiciEndoSelezionati.Count; i++)
				{
					endoSelezionatiStr[i] = codiciEndoSelezionati[i].ToString();
				}

				return ws.Service.GetAllegatiEndoprocedimenti(ws.Token, endoSelezionatiStr, AmbitoRicerca.AreaRiservata).ToList();
			}
		}
	}
}
