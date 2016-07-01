using System.Collections.Generic;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
	public class LogicaEstrazioneEndoDaIntervento
	{
		protected class FamigliaIndexer
		{
			public int Codice { get; set; }
			public string Descrizione { get; set; }
			public Dictionary<int, TipoEndoIndexer> TipiEndo { get; private set; }

			public FamigliaIndexer()
			{
				TipiEndo = new Dictionary<int, TipoEndoIndexer>();
			}
		}

		protected class TipoEndoIndexer
		{
			public int Codice { get; set; }
			public string Descrizione { get; set; }
			public List<EndoprocedimentoDto> Endo { get; private set; }

			public TipoEndoIndexer()
			{
				Endo = new List<EndoprocedimentoDto>();
			}
		}


		public List<FamigliaEndoprocedimentoDto> FamiglieEndoPrincipale { get; protected set; }
		public List<FamigliaEndoprocedimentoDto> FamiglieEndoAttivati { get; protected set; }
		public List<FamigliaEndoprocedimentoDto> FamiglieEndoFacoltativi { get; protected set; }

		public List<EndoprocedimentoDto> EndoPrincipali { get; protected set; }
		public List<EndoprocedimentoDto> EndoAttivati { get; protected set; }
		public List<EndoprocedimentoDto> EndoAttivabili { get; protected set; }

		public Dictionary<int, EndoprocedimentoDto> IndiceEndo { get; protected set; }

		public LogicaEstrazioneEndoDaIntervento(FamigliaEndoprocedimentoDto[] endoIntervento)
		{
			IndiceEndo = new Dictionary<int, EndoprocedimentoDto>();

			var endoPrincipaliIndexerDict = new Dictionary<int, FamigliaIndexer>();
			var endoAttivatiIndexerDict = new Dictionary<int, FamigliaIndexer>();
			var endoFacoltativiIndexerDict = new Dictionary<int, FamigliaIndexer>();

			foreach (var famigliaEndo in endoIntervento)
			{
				foreach (var tipoEndo in famigliaEndo.TipiEndoprocedimenti)
				{
					foreach (var endo in tipoEndo.Endoprocedimenti)
					{
						if (!IndiceEndo.ContainsKey(endo.Codice))
							IndiceEndo.Add(endo.Codice, endo);

						if (endo.Principale)
						{
							endo.Richiesto = true;

							AggiungiEndo(famigliaEndo, tipoEndo, endo, endoPrincipaliIndexerDict);
							continue;
						}

						if (endo.Richiesto)
						{
							AggiungiEndo(famigliaEndo, tipoEndo, endo, endoAttivatiIndexerDict);
							continue;
						}

						AggiungiEndo(famigliaEndo, tipoEndo, endo, endoFacoltativiIndexerDict);
					}
				}
			}

			// Rigenero le strutture dai dati ottenuti
			FamiglieEndoPrincipale = new List<FamigliaEndoprocedimentoDto>();
			EndoPrincipali = new List<EndoprocedimentoDto>();

			FamiglieEndoAttivati = new List<FamigliaEndoprocedimentoDto>();
			EndoAttivati = new List<EndoprocedimentoDto>();

			FamiglieEndoFacoltativi = new List<FamigliaEndoprocedimentoDto>();
			EndoAttivabili = new List<EndoprocedimentoDto>();

			RigeneraListaDaDictionary(endoPrincipaliIndexerDict, FamiglieEndoPrincipale,  EndoPrincipali);
			RigeneraListaDaDictionary(endoAttivatiIndexerDict,  FamiglieEndoAttivati,  EndoAttivati);
			RigeneraListaDaDictionary(endoFacoltativiIndexerDict,  FamiglieEndoFacoltativi,  EndoAttivabili);
		}

		/// <summary>
		/// Aggiunge un endoprocedimento alla lista passata come parametro "lista"
		/// </summary>
		/// <param name="famiglia">famiglie dell'endo</param>
		/// <param name="tipo">Tipo endo</param>
		/// <param name="endo">endo</param>
		/// <param name="lista">lista a cui aggiungere l'endo</param>
		protected void AggiungiEndo(FamigliaEndoprocedimentoDto famiglia, TipoEndoprocedimentoDto tipo, EndoprocedimentoDto endo, Dictionary<int, FamigliaIndexer> lista)
		{
			if (!lista.ContainsKey(famiglia.Codice))
			{
				var nuovafamiglia = new FamigliaIndexer
				{
					Codice = famiglia.Codice,
					Descrizione = famiglia.Descrizione
				};

				lista.Add(nuovafamiglia.Codice, nuovafamiglia);
			}

			var famigliaTrovata = lista[famiglia.Codice];

			if (!famigliaTrovata.TipiEndo.ContainsKey(tipo.Codice))
			{
				var nuovoEndo = new TipoEndoIndexer
				{
					Codice = tipo.Codice,
					Descrizione = tipo.Descrizione
				};

				famigliaTrovata.TipiEndo.Add(nuovoEndo.Codice, nuovoEndo);
			}

			famigliaTrovata.TipiEndo[tipo.Codice].Endo.Add(endo);
		}

		protected void RigeneraListaDaDictionary(Dictionary<int, FamigliaIndexer> struttura, List<FamigliaEndoprocedimentoDto> listaFamiglie,  List<EndoprocedimentoDto> listaEndo)
		{
			foreach (var famiglia in struttura.Values)
			{
				listaFamiglie.Add(new FamigliaEndoprocedimentoDto
				{
					Codice = famiglia.Codice,
					Descrizione = famiglia.Descrizione
				});

				var tmpTipiList = new List<TipoEndoprocedimentoDto>();

				foreach (var tipo in famiglia.TipiEndo.Values)
				{
					tmpTipiList.Add(new TipoEndoprocedimentoDto
					{
						Codice = tipo.Codice,
						Descrizione = tipo.Descrizione
					});

					var tmpEndoList = new List<EndoprocedimentoDto>();

					foreach (var endo in tipo.Endo)
					{
						tmpEndoList.Add(endo);
						listaEndo.Add(endo);
					}

					tmpEndoList.Sort((a, b) => 
					{
						var ordine = a.Ordine - b.Ordine;

						if (ordine == 0)
						{
							ordine = b.Descrizione.CompareTo(a.Descrizione);
						}

						return ordine;
					});

					tmpTipiList.Last().Endoprocedimenti = tmpEndoList.ToArray();
				}

				listaFamiglie.Last().TipiEndoprocedimenti = tmpTipiList.ToArray();

			}
		}
	}
}
