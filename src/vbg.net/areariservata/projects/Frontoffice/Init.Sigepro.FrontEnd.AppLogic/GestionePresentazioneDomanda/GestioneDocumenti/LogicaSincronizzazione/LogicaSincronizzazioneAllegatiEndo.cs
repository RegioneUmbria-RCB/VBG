using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione
{
	internal class LogicaSincronizzazioneAllegatiEndo
	{
		IAllegatiEndoprocedimentiService _allegatiEndoService;
		DomandaOnline _domanda;

		internal LogicaSincronizzazioneAllegatiEndo( DomandaOnline domanda, IAllegatiEndoprocedimentiService allegatiEndoService)
		{
			Condition.Requires(allegatiEndoService, "allegatiEndoService").IsNotNull();
			Condition.Requires(domanda, "domanda").IsNotNull();

			this._allegatiEndoService = allegatiEndoService;
			this._domanda = domanda;
		}


		internal void Sincronizza()
		{
			IDomandaOnlineReadInterface readInterface	= this._domanda.ReadInterface;
			IDomandaOnlineWriteInterface writeInterface = this._domanda.WriteInterface;

			var listaIdEndoSelezionati = readInterface.Endoprocedimenti.NonAcquisiti.Select(x => x.Codice);
			var listaEndo				= _allegatiEndoService.GetDatiProcedimenti( listaIdEndoSelezionati.ToList());
			var listaIdAllegati			= new List<int>();

			foreach (var endo in listaEndo)
			{
				if (endo.Allegati != null)
				{
					foreach (var allegato in endo.Allegati)
					{
						listaIdAllegati.Add(allegato.Id.Value);

						var descrizione			= allegato.Allegato;
						var linkInformazioni	= allegato.Indirizzoweb;
						var codiceOggetto		= allegato.Codiceoggetto;

						var codiceDocumento		= allegato.Id.Value;
						var richiesto			= allegato.Richiesto.GetValueOrDefault(0) == 1;
						var richiedeFirma		= allegato.FoRichiedefirma.GetValueOrDefault(0) == 1;
						var tipoDownload		= allegato.FoTipodownload;
						var codiceEndo			= endo.Codiceinventario.Value;
						var ordine				= allegato.Ordine.GetValueOrDefault(0);
						var nomeFileModello		= allegato.NomeFile;
						var note				= allegato.NoteFrontend;
                        var dimensioneMassima = allegato.FoDimensioneMassima.GetValueOrDefault(0);
                        var estensioniAmmesse = allegato.FoEstensioniAmmesse;

						writeInterface.Documenti.AggiungiOAggiornaDocumentoEndo(codiceDocumento, descrizione, linkInformazioni, codiceOggetto, richiesto,
																				richiedeFirma, tipoDownload, codiceEndo, ordine, nomeFileModello, note,
                                                                                dimensioneMassima, estensioniAmmesse);
					}
				}
			}

			// Elimino tutti i documenti relativi ad endo che non sono più selezionati
			readInterface.Documenti.Endo
									.WhereIdEndoNotIn(listaIdEndoSelezionati)
									.ToList()
									.ForEach(x => writeInterface.Documenti.EliminaDocumento(x.Id));

			// Elimino tutti i documenti relativi a documenti non più  in uso
			readInterface.Documenti.Endo
									.GetDocumentiDiSistema()
									.WhereIdDocumentoNotIn( listaIdAllegati )
									.ToList()
									.ForEach(x => writeInterface.Documenti.EliminaDocumento(x.Id));

			// Elimino tutti i documenti liberi che non contengono un allegato (è possibile che esistano???)
			readInterface.Documenti.Endo
									.GetDocumentiAllegatiDaUtenteSenzaOggetto()
									.ToList()
									.ForEach(x => writeInterface.Documenti.EliminaDocumento(x.Id));
		}
	}
}
