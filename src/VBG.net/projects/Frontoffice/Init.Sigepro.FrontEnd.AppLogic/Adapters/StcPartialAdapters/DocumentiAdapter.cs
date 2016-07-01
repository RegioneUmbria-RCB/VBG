using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class DocumentiAdapter : IStcPartialAdapter
	{
		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			var listaDocumenti = new List<DocumentiType>();

			if (_readInterface.DelegaATrasmettere.Allegato != null)
			{
				listaDocumenti.Add(new DocumentiType
				{
					id = String.Empty,
					documento = "Delega a trasmettere",
					tipoDocumento = TipoDocumentoType.Altro,
					tipoDocumentoSpecified = true,
					allegati = new AllegatiType
					{
						id = _readInterface.DelegaATrasmettere.Allegato.CodiceOggetto.ToString(),
						allegato = _readInterface.DelegaATrasmettere.Allegato.NomeFile
					}
				});
			}

			// Popolo gli allegati dell'intervento
			listaDocumenti.AddRange(_readInterface.Documenti
												  .Intervento
												  .GetAllegatiPresenti()
												  .Select(x => x.AllegatoDellUtente.ToDocumentiType()));

			// Popolo gli allegati generati dai riepiloghi delle schede dinamiche dell'intervento o del cittadino extracomunitario
			// (gli allegati degli endo vengono popolati nella sezione degli endoprocedimenti)

			listaDocumenti.AddRange(_readInterface.RiepiloghiSchedeDinamiche.GetRiepiloghiInterventoConAllegatoUtente()
													.Union(_readInterface.RiepiloghiSchedeDinamiche
																		 .GetRigheRiepilogoCittadinoExtracomunitario())
													.Select(x => x.AllegatoDellUtente.ToDocumentiType()));

			// Popolo la ricevuta di pagamento degli oneri (se presente)
			if (_readInterface.Oneri.AttestazioneDiPagamento.Presente)
			{
				listaDocumenti.Add(_readInterface.Oneri.AttestazioneDiPagamento.ToDocumentiType());
			}

			_dettaglioPratica.documenti = listaDocumenti.ToArray();
		}
	}
}
