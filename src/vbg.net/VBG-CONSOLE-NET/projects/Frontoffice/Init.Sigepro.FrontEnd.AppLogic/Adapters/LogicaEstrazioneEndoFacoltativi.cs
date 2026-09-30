using System.Collections.Generic;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
	public class LogicaEstrazioneEndoFacoltativi
	{
		protected class TipoEndoprocedimentoReverseTreeNode
		{
			public FamigliaEndoprocedimentoDto Famiglia { get; set; }
			public TipoEndoprocedimentoDto Dto{get;set;}
		}

		protected class EndoprocedimentoDtoReverseTreeNode
		{
			public TipoEndoprocedimentoReverseTreeNode Tipo { get; set; }
			public EndoprocedimentoDto Dto { get; set; }

		}

		public FamigliaEndoprocedimentoDto[] EndoFacoltativi { get { return _endoFacoltativi; } }

		FamigliaEndoprocedimentoDto[] _endoFacoltativi;
		Dictionary<int, EndoprocedimentoDto> _endoprocedimentiByIdIndex;
		Dictionary<int, TipoEndoprocedimentoDto> _tipoByIdEndoIndex;
		Dictionary<int, FamigliaEndoprocedimentoDto> _famigliaByIdTipoEndoIndex;
		

		public LogicaEstrazioneEndoFacoltativi(FamigliaEndoprocedimentoDto[] endoFacoltativi)
		{
			_endoFacoltativi = endoFacoltativi;

			CostruisciIndiciEndoprocedimenti();
		}

		private void CostruisciIndiciEndoprocedimenti()
		{
			_endoprocedimentiByIdIndex = new Dictionary<int, EndoprocedimentoDto>();
			_famigliaByIdTipoEndoIndex = new Dictionary<int, FamigliaEndoprocedimentoDto>();
			_tipoByIdEndoIndex = new Dictionary<int, TipoEndoprocedimentoDto>();

			foreach (var famiglia in _endoFacoltativi)
			{
				foreach (var tipo in famiglia.TipiEndoprocedimenti)
				{

					if (!_famigliaByIdTipoEndoIndex.ContainsKey(tipo.Codice))
						_famigliaByIdTipoEndoIndex.Add(tipo.Codice, famiglia);

					foreach (var endo in tipo.Endoprocedimenti)
					{
						if (!_endoprocedimentiByIdIndex.ContainsKey(endo.Codice))
						{
							_endoprocedimentiByIdIndex.Add(endo.Codice, endo);
							_tipoByIdEndoIndex.Add(endo.Codice, tipo);
						}
					}
				}
			}
		}

		public void RimuoviEndoGiaPresenti(IEnumerable<int> codiciEndoprocedimentiDaRimuovere)
		{
			foreach (var codiceEndo in codiciEndoprocedimentiDaRimuovere)
			{
				if (!_tipoByIdEndoIndex.ContainsKey(codiceEndo))
					continue;

				var endo		= _endoprocedimentiByIdIndex[codiceEndo];
				var tipoEndo	= _tipoByIdEndoIndex[codiceEndo];				
				var listaEndo	= tipoEndo.Endoprocedimenti.ToList();

				listaEndo.Remove( endo );

				// Se il tipo endo non contiene più endoprocedimenti lo rimuovo
				if (listaEndo.Count != 0)
				{
					tipoEndo.Endoprocedimenti = listaEndo.ToArray();
					continue;
				}

				var famigliaEndo = _famigliaByIdTipoEndoIndex[tipoEndo.Codice];

				var listaTipiEndo = famigliaEndo.TipiEndoprocedimenti.ToList();

				listaTipiEndo.Remove(tipoEndo);

				if (listaTipiEndo.Count != 0)
				{
					famigliaEndo.TipiEndoprocedimenti = listaTipiEndo.ToArray();
					continue;
				}

				// Se la famiglia non contiene più tipi endo la rimuovo
				var listaFamiglie = _endoFacoltativi.ToList();

				listaFamiglie.Remove(famigliaEndo);

				_endoFacoltativi = listaFamiglie.ToArray();
			}

		}

		public IEnumerable<FamigliaEndoprocedimentoDto> GetListaFamiglieDaIdEndoSelezionati(List<int> idSelezionati)
		{
			var endoReverseList = new List<EndoprocedimentoDtoReverseTreeNode>();

			// Trovo la lista di endo eventualmente selezionati e costruisco la relazione 
			// inversa grazie alle classi helper.
			// Se prima la relazione era Famiglia->Tipo->Endo  ora diventa Endo->Tipo->Famiglia
			foreach (var famiglia in _endoFacoltativi)
			{
				foreach (var tipo in famiglia.TipiEndoprocedimenti)
				{
					foreach (var endo in tipo.Endoprocedimenti)
					{
						if (!idSelezionati.Contains(endo.Codice))
							continue;

						var tipoRev = new TipoEndoprocedimentoReverseTreeNode 
						{ 
							Famiglia = famiglia,
							Dto = tipo
						};

						var endoRev = new EndoprocedimentoDtoReverseTreeNode
						{
							Tipo = tipoRev,
							Dto = endo
						};

						endoReverseList.Add(endoRev);
					}
				}
			}

			// Costruisco gli indici e i dizionari che andrò ad utilizzare per ripopolare le strutture Famiglia, tipo e endo
			var dictFamiglie = new Dictionary<int, FamigliaEndoprocedimentoDto>();
			var dictTipi = new Dictionary<int, TipoEndoprocedimentoDto>();
			var dictEndo = new Dictionary<int, EndoprocedimentoDto>();

			// Mappa: per ogni id famiglia contiene tutti gli id tipo contenuti
			var famiglieTipiMap = new Dictionary<int, List<int>>();
			// Mappa: per ogni id tipo contiene la lista degli id endo contenuti
			var tipiEndoMap = new Dictionary<int, List<int>>();

			// Popolo i dizionari e le mappe
			foreach (var endo in endoReverseList)
			{
				var codiceFamiglia = endo.Tipo.Famiglia.Codice;
				var codiceTipo = endo.Tipo.Dto.Codice;
				var codiceEndo = endo.Dto.Codice;

				if (!dictFamiglie.ContainsKey(codiceFamiglia))
				{
					var newFamiglia = new FamigliaEndoprocedimentoDto
					{
						Codice = codiceFamiglia,
						Descrizione = endo.Tipo.Famiglia.Descrizione
					};

					dictFamiglie.Add(codiceFamiglia, newFamiglia);
					famiglieTipiMap.Add(codiceFamiglia, new List<int>());
				}

				if (!dictTipi.ContainsKey(codiceTipo))
				{
					var newTipo = new TipoEndoprocedimentoDto
					{
						Codice = codiceTipo,
						Descrizione = endo.Tipo.Dto.Descrizione
					};

					dictTipi.Add(codiceTipo, newTipo);
					tipiEndoMap.Add(codiceTipo, new List<int>());
					famiglieTipiMap[codiceFamiglia].Add(codiceTipo);
				}

				if(!dictEndo.ContainsKey(codiceEndo))
				{
					var newEndo = new EndoprocedimentoDto
					{
						Codice = codiceEndo,
						Descrizione = endo.Dto.Descrizione
					};

					dictEndo.Add( codiceEndo , newEndo );
				}

				tipiEndoMap[codiceTipo].Add(codiceEndo);
			}

			var rVal = new List<FamigliaEndoprocedimentoDto>( famiglieTipiMap.Keys.Count );

			// Per ogni id famiglia nella lista delle famiglie trovate
			foreach (var idFamiglia in famiglieTipiMap.Keys)
			{
				var famiglia = dictFamiglie[idFamiglia];

				// Inizializzo l'array dei tipi endo con il numero di tipi endo trovati
				famiglia.TipiEndoprocedimenti = new TipoEndoprocedimentoDto[ famiglieTipiMap[idFamiglia].Count ];

				var famigliaIdx = 0;	// indice all'interno dell'array di tipi endo

				// Popolo l'array delle famiglie
				foreach( var idTipo in famiglieTipiMap[idFamiglia] )
				{
					var tipoEndo = dictTipi[ idTipo ];

					// Inizializzo l'array degli endoprocedimenti del tipo che sto esaminando
					tipoEndo.Endoprocedimenti = new EndoprocedimentoDto[ tipiEndoMap[idTipo].Count ];

					var endoIdx = 0;	// indice all'interno dell'array di endo

					// Popolo larray di endo del tipo utilizzando l'indice creato in precedenza
					foreach(var idEndo in tipiEndoMap[idTipo])
					{
						var endo = dictEndo[idEndo];

						tipoEndo.Endoprocedimenti[endoIdx] = endo;

						endoIdx++;
					}

					famiglia.TipiEndoprocedimenti[famigliaIdx] = tipoEndo;

					famigliaIdx++;

				}


				rVal.Add(famiglia);
			}


			return rVal.ToArray();


		}
	}
}
