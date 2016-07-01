using System.Collections.Generic;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneRiepilogoDomanda
{
	public class FacSimileDomanda
	{
		IInterventiRepository _interventiRepository;
		EndoprocedimentiService _endoprocedimentiService;
		IDatiDinamiciRepository _datiDinamiciRepository;
		IInterventiAllegatiRepository _interventiAllegatiRepository;
		IAllegatiEndoprocedimentiService _allegatiEndoService;


		public FacSimileDomanda( IInterventiRepository interventiRepository, EndoprocedimentiService endoprocedimentiService, IDatiDinamiciRepository datiDinamiciRepository, IInterventiAllegatiRepository interventiAllegatiRepository, IAllegatiEndoprocedimentiService allegatiEndoService)
		{
			this._interventiRepository		= interventiRepository;
			this._endoprocedimentiService	= endoprocedimentiService;
			this._datiDinamiciRepository	= datiDinamiciRepository;
			this._interventiAllegatiRepository = interventiAllegatiRepository;
			this._allegatiEndoService		= allegatiEndoService;

		}

		public DomandaOnline Genera(string aliasComune, string software,int idIntervento, IEnumerable<int> endoFacoltativiAttivati)
		{
			var domanda = DomandaOnline.FacSimile(aliasComune, software);

			// CodiceComune
			domanda.WriteInterface.AltriDati.ImpostaCodiceComune(aliasComune);

			// Intervento
			domanda.WriteInterface.AltriDati.ImpostaIntervento(idIntervento, _interventiRepository.EstraiDescrizioneEstesa(idIntervento), (int?)null, new NullWorkflowService());

			// Endo
			var endoprocedimenti = this._endoprocedimentiService.LeggiEndoprocedimentiDaCodiceIntervento(aliasComune, idIntervento);

			var listaIdEndoAttivati = EstraiListaEndo(endoprocedimenti.FamiglieEndoprocedimentiPrincipali).Select(x => x.Codice)
										  .Union(EstraiListaEndo(endoprocedimenti.FamiglieEndoprocedimentiAttivati).Select(x => x.Codice))
										  .Union(EstraiListaEndo(endoprocedimenti.FamiglieEndoprocedimentiAttivabili)
																				.Where(x => endoFacoltativiAttivati.Contains(x.Codice))
																				.Select(x => x.Codice))
										  .Union(EstraiListaEndo(endoprocedimenti.FamiglieEndoprocedimentiFacoltativi)
																				.Where(x => endoFacoltativiAttivati.Contains(x.Codice))
																				.Select(x => x.Codice));

			domanda.WriteInterface.Endoprocedimenti.AggiungiESincronizza(listaIdEndoAttivati, new LogicaSincronizzazioneEndo(domanda, this._endoprocedimentiService));

			// modelli dinamici
			var schedeDinamicheRichieste = _datiDinamiciRepository.GetSchedeDaInterventoEEndo(aliasComune, idIntervento, listaIdEndoAttivati, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

			var schedeintervento = schedeDinamicheRichieste.
									SchedeIntervento.
									Select(x => new ModelloDinamicoInterventoDaSincronizzare( x.CodiceIntervento, x.Codice, x.Descrizione, TipoFirmaEnum.NessunaFirma, x.Facoltativa, x.Ordine.GetValueOrDefault(999)));

			var schedeEndo = schedeDinamicheRichieste.
									SchedeEndoprocedimenti.
									Select(x => new ModelloDinamicoEndoprocedimentoDaSincronizzare(x.CodiceEndo, x.Codice, x.Descrizione, TipoFirmaEnum.NessunaFirma, x.Facoltativa, x.Ordine.GetValueOrDefault(999)));


			domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(new SincronizzaModelliDinamiciCommand(schedeintervento, schedeEndo, null));

			foreach (var modello in domanda.ReadInterface.DatiDinamici.Modelli)
				domanda.WriteInterface.DatiDinamici.ModificaStatoCompilazioneModello(modello.IdModello, 0, true);

			// Allegati intervento
			new LogicaSincronizzazioneAllegatiIntervento(domanda, _interventiAllegatiRepository).Sincronizza();

			foreach (var allegato in domanda.ReadInterface.Documenti.Intervento.Documenti)
				domanda.WriteInterface.Documenti.AllegaFileADocumento(allegato.Id, -1, allegato.Richiesto ? "Allegato Obbligatorio" : "Allegato Non Obbligatorio", false);

			// Allegati endo
			new LogicaSincronizzazioneAllegatiEndo(domanda, _allegatiEndoService).Sincronizza();

			foreach (var allegato in domanda.ReadInterface.Documenti.Endo.Documenti)
				domanda.WriteInterface.Documenti.AllegaFileADocumento(allegato.Id, -1, allegato.Richiesto ? "Allegato Obbligatorio" : "Allegato Non Obbligatorio", false);


			domanda.ReadInterface.Invalidate();

			return domanda;
		}


		private IEnumerable<EndoprocedimentoDto> EstraiListaEndo(IEnumerable<FamigliaEndoprocedimentoDto> famiglie)
		{
			return famiglie.SelectMany(x => x.TipiEndoprocedimenti).SelectMany(x => x.Endoprocedimenti);
		}
	}
}
