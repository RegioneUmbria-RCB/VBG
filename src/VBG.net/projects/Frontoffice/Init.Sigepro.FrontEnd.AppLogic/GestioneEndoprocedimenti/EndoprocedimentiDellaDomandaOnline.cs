// -----------------------------------------------------------------------
// <copyright file="EndoprocedimentiDellaDomandaOnline.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
	using System.Collections.Generic;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.Adapters;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

	public class EndoprocedimentiDellaDomandaOnline
	{
		public readonly List<int> IdSelezionatiDefault;

		public readonly List<FamigliaEndoprocedimentoDto> FamiglieEndoprocedimentiPrincipali;
		public readonly List<FamigliaEndoprocedimentoDto> FamiglieEndoprocedimentiAttivati;
		public readonly List<FamigliaEndoprocedimentoDto> FamiglieEndoprocedimentiAttivabili;
		public readonly List<FamigliaEndoprocedimentoDto> FamiglieEndoprocedimentiFacoltativi;

		private readonly List<EndoprocedimentoDto> EndoPrincipali;
		private readonly List<EndoprocedimentoDto> EndoAttivati;
		private readonly List<EndoprocedimentoDto> EndoAttivabili;

		private LogicaEstrazioneEndoFacoltativi _logicaEstrazioneEndoFacoltativi;

		internal EndoprocedimentiDellaDomandaOnline(FamigliaEndoprocedimentoDto[] endoLegatiAllAlbero, FamigliaEndoprocedimentoDto[] endoFacoltativi)
		{
			var gruppiEndo = new LogicaEstrazioneEndoDaIntervento(endoLegatiAllAlbero);

			this.FamiglieEndoprocedimentiPrincipali = gruppiEndo.FamiglieEndoPrincipale;
			this.FamiglieEndoprocedimentiAttivati = gruppiEndo.FamiglieEndoAttivati;
			this.FamiglieEndoprocedimentiAttivabili = gruppiEndo.FamiglieEndoFacoltativi;
			this.EndoPrincipali = gruppiEndo.EndoPrincipali;
			this.EndoAttivati = gruppiEndo.EndoAttivati;
			this.EndoAttivabili = gruppiEndo.EndoAttivabili;

			this._logicaEstrazioneEndoFacoltativi = new LogicaEstrazioneEndoFacoltativi(endoFacoltativi);
			this.IdSelezionatiDefault = PopolaListaIdSelezionatiPerDefault();

			var listaEndoGiaPresenti = this.EndoPrincipali.Select(x => x.Codice)
														  .Union(this.EndoAttivati.Select(x => x.Codice))
														  .Union(this.EndoAttivabili.Select(x => x.Codice));

			this._logicaEstrazioneEndoFacoltativi.RimuoviEndoGiaPresenti(listaEndoGiaPresenti);

			this.FamiglieEndoprocedimentiFacoltativi = this._logicaEstrazioneEndoFacoltativi.EndoFacoltativi.ToList();

			OrdinaEndo(this.FamiglieEndoprocedimentiPrincipali);
			OrdinaEndo(this.FamiglieEndoprocedimentiAttivati);
			OrdinaEndo(this.FamiglieEndoprocedimentiAttivabili);
		}

		private void OrdinaEndo(List<FamigliaEndoprocedimentoDto> famiglie)
		{
			foreach (var famiglia in famiglie)
			{
				foreach (var tipo in famiglia.TipiEndoprocedimenti)
				{
					tipo.Endoprocedimenti = tipo.Endoprocedimenti.OrderBy(x => x.Ordine).ThenBy( x => x.Descrizione).ToArray();
				}
			}
		}

		private List<int> PopolaListaIdSelezionatiPerDefault()
		{
			var rVal = new List<int>();

			foreach (var endo in this.EndoPrincipali)
				if (!rVal.Contains(endo.Codice))
					rVal.Add(endo.Codice);

			foreach (var endo in this.EndoAttivati)
				if (!rVal.Contains(endo.Codice))
					rVal.Add(endo.Codice);

			return rVal;
		}

		public IEnumerable<FamigliaEndoprocedimentoDto> GetEndoprocedimentiFacoltativiSelezionatiDaListaId(List<int> idSelezionatiDallUtente)
		{
			return this._logicaEstrazioneEndoFacoltativi.GetListaFamiglieDaIdEndoSelezionati(idSelezionatiDallUtente);
		}
	}
}
