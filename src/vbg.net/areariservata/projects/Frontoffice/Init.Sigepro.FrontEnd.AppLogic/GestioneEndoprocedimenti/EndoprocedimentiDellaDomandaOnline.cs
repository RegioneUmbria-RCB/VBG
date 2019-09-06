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
    using Init.Sigepro.FrontEnd.AppLogic.Adapters.LogicaEstrazioneEndoprocedimenti;
    using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

	public class EndoprocedimentiDellaDomandaOnline
	{
		public readonly IEnumerable<int> IdSelezionatiDefault;

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
 
            // Gli endo che sono selezionati per default sono gli endo attivati e quelli principali
			this.IdSelezionatiDefault = this.EndoPrincipali
                                            .Select(x => x.Codice)
                                            .Distinct()
                                            .Union(this.EndoAttivati
                                                        .Select(x => x.Codice)
                                                        .Distinct());

            // Lista di endo che devo rimuovere dalla lista degli endo facoltativi perchè già presenti tra quelli attivati attivabili o principali
            var listaEndoGiaPresenti = this.IdSelezionatiDefault.Union(this.EndoAttivabili.Select(x => x.Codice));

			this._logicaEstrazioneEndoFacoltativi.RimuoviEndoGiaPresenti(listaEndoGiaPresenti);

			this.FamiglieEndoprocedimentiFacoltativi = this._logicaEstrazioneEndoFacoltativi.EndoFacoltativi.ToList();

			OrdinaEndo(this.FamiglieEndoprocedimentiPrincipali);    // è veramente necessario???
			OrdinaEndo(this.FamiglieEndoprocedimentiAttivati);      // è veramente necessario???
			OrdinaEndo(this.FamiglieEndoprocedimentiAttivabili);    // è veramente necessario???
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

		public IEnumerable<FamigliaEndoprocedimentoDto> GetEndoprocedimentiFacoltativiSelezionatiDaListaId(List<int> idSelezionatiDallUtente)
		{
			return this._logicaEstrazioneEndoFacoltativi.GetListaFamiglieDaIdEndoSelezionati(idSelezionatiDallUtente);
		}
	}
}
