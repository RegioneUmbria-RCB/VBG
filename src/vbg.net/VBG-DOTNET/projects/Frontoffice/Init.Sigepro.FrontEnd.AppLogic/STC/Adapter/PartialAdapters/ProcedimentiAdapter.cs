using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class ProcedimentiAdapter : IStcPartialAdapter
	{
		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			var endoProcedimenti = _readInterface.Endoprocedimenti.Endoprocedimenti;

			var listaProcedimenti = new List<ProcedimentoType>();

			foreach (var endo in endoProcedimenti)
			{
				// Popolo i dati del procedimento
				var proc = new ProcedimentoType
				{
					codice = endo.Codice.ToString(),
					descrizione = endo.Descrizione,
					dataAttivazione = DateTime.Now,
					principale = endo.Principale,
					principaleSpecified = true
				};

				if (endo.Riferimenti != null)
				{
					proc.estremiAtto = new EstremiAttoType
					{
						riferimento = endo.Riferimenti.NumeroAtto,
						tipoAtto = endo.Riferimenti.TipoTitolo.Descrizione,
						rilasciatoDa = endo.Riferimenti.RilasciatoDa,
						note = endo.Riferimenti.Note
					};

					if (endo.Riferimenti.DataAtto.HasValue)
					{
						proc.estremiAtto.data = endo.Riferimenti.DataAtto.Value;
						proc.estremiAtto.dataSpecified = true;
					}
				}

				// Popolo gli allegati del procedimento
				proc.documenti = _readInterface.Documenti
													.Endo
													.GetByIdEndo(endo.Codice)
													.Where(x => x.AllegatoDellUtente != null)
													.Select(x => x.AllegatoDellUtente.ToDocumentiType())
													.Union(
														_readInterface.RiepiloghiSchedeDinamiche
																	  .GetByCodiceEndo(endo.Codice)
																	  .Where(x => x.AllegatoDellUtente != null)
																	  .Select(x => x.AllegatoDellUtente.ToDocumentiType())
													)
													.Union(
														_readInterface
															.Endoprocedimenti
															.Acquisiti
															.Where(x => x.Codice == endo.Codice && x.Riferimenti.Allegato != null)
															.Select(x => x.Riferimenti.Allegato.ToDocumentiType("Documento comprovante il possesso del titolo \"" + endo.Descrizione + "\""))
													)
													.ToArray();

				listaProcedimenti.Add(proc);
			}

			_dettaglioPratica.procedimenti = listaProcedimenti.ToArray();
		}
	}
}
