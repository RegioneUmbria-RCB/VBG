using System;
using System.Linq;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
	public class AllegatiInterventoService
	{
		ILog _log = LogManager.GetLogger(typeof(AllegatiInterventoService));

		readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
		readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		readonly IInterventiAllegatiRepository _interventiAllegatiRepository;
		readonly IConfigurazione<ParametriWorkflow> _configurazione;
		readonly GenerazioneRiepilogoDomandaService _generazioneriepilogoService;
		readonly IInterventiRepository _interventiRepository;

		public AllegatiInterventoService(IAllegatiDomandaFoRepository allegatiDomandaFoRepository,
										 ISalvataggioDomandaStrategy salvataggioDomandaStrategy,
										 IInterventiAllegatiRepository interventiAllegatiRepository,
										 IConfigurazione<ParametriWorkflow> configurazione,
										 GenerazioneRiepilogoDomandaService generazioneriepilogoService,
										 IInterventiRepository interventiRepository)
		{
			Condition
				.Requires(allegatiDomandaFoRepository, "allegatiDomandaFoRepository")
				.IsNotNull();

			Condition
				.Requires(salvataggioDomandaStrategy, "salvataggioDomandaStrategy")
				.IsNotNull();

			Condition
				.Requires(interventiAllegatiRepository, "interventiAllegatiRepository")
				.IsNotNull();

			Condition
				.Requires(configurazione, "configurazione")
				.IsNotNull();

			Condition
				.Requires(generazioneriepilogoService, "generazioneriepilogoService")
				.IsNotNull();

			Condition
				.Requires(interventiRepository, "interventiRepository")
				.IsNotNull();




			_allegatiDomandaFoRepository = allegatiDomandaFoRepository;
			_salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			_interventiAllegatiRepository = interventiAllegatiRepository;
			_configurazione = configurazione;
			_generazioneriepilogoService = generazioneriepilogoService;
			_interventiRepository = interventiRepository;
		}

		public void EliminaOggettoRiepilogoDomanda(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			EliminaOggettoRiepilogoDomanda(domanda);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		private void EliminaOggettoRiepilogoDomanda(DomandaOnline domanda)
		{
			var row = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();

			if (row == null)
				return;

			domanda.WriteInterface.Documenti.EliminaAllegatoADocumentoDaIdDocumento(row.Id);
		}

		public void SalvaOggettoRiepilogo(int idDomanda, BinaryFile file)
		{
			try
			{
				if (file.FileContent.Length == 0)
					throw new ArgumentException("Il file caricato non è valido");

				var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

				SalvaOggettoRiepilogo(domanda, file);

				_salvataggioDomandaStrategy.Salva(domanda);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante il salvataggio del riepilogo della domanda: {0}", ex.ToString());

				throw;
			}
		}

		private void SalvaOggettoRiepilogo(DomandaOnline domanda, BinaryFile file)
		{
			var row = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();
			var verificaHashes = false; //_configurazione.Parametri.VerificaHashFilesFirmati;
			var hashConfronto = row.AllegatoDellUtente == null ? String.Empty : row.AllegatoDellUtente.Md5;

			SalvataggioAllegatoResult result = null;

			if (verificaHashes)
			{
				result = _allegatiDomandaFoRepository.SalvaAllegatoConfrontaHash(domanda.DataKey.IdPresentazione, file, hashConfronto);
			}
			else
			{
				result = _allegatiDomandaFoRepository.SalvaAllegato(domanda.DataKey.IdPresentazione, file, false);
			}

			domanda.WriteInterface.Documenti.AllegaFileADocumento(row.Id, result.CodiceOggetto, result.NomeFile, result.FirmatoDigitalmente);

		}

		public void Sincronizza(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			new LogicaSincronizzazioneAllegatiIntervento(domanda, _interventiAllegatiRepository).Sincronizza();

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void EliminaOggettoUtente(int idDomanda, int idDocumento)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Documenti.EliminaAllegatoADocumentoDaIdDocumento(idDocumento);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void Salva(int idDomanda, int idDocumento, BinaryFile file)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			var documento = domanda.ReadInterface.Documenti.Intervento.GetById(idDocumento);

			var result = _allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, false);

			domanda.WriteInterface.Documenti.AllegaFileADocumento(idDocumento, result.CodiceOggetto, result.NomeFile, result.FirmatoDigitalmente);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void AggiungiAllegatoLibero(int idDomanda, string descrizione, BinaryFile file, int codiceCategoria, string descrizioneCategoria, bool verificaFirma)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			try
			{
				var result = _allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, verificaFirma);

				domanda.WriteInterface.Documenti.AggiungiDocumentoInterventoLibero(descrizione, result.CodiceOggetto, result.NomeFile, codiceCategoria, descrizioneCategoria, result.FirmatoDigitalmente);

				_salvataggioDomandaStrategy.Salva(domanda);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in AggiungiAllegatoLibero: {0}", ex.ToString());

				throw;
			}
		}

		public int? GetCodiceOggettoDelModelloDiRiepilogo(int idIntervento)
		{
			var allegati = this._interventiAllegatiRepository.GetAllegatiDaIdintervento(idIntervento, AmbitoRicerca.AreaRiservata);

			var riepilogo = allegati.Where(x => x.RiepilogoDomanda).FirstOrDefault();

			return riepilogo == null ? null : riepilogo.CodiceOggettoModello;
		}

		public void RigeneraRiepilogoDomanda(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			EliminaOggettoRiepilogoDomanda(domanda);

			var codiceOggettoRiepilogo = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda().CodiceOggettoModello.Value;

			var riepilogo = _generazioneriepilogoService.GeneraRiepilogo(codiceOggettoRiepilogo, domanda, new GenerazioneRiepilogoSettings
			{
				AggiungiPdfSchedeAListaAllegati = true,
				FormatoConversione = "PDF",
				IdComune = domanda.DataKey.IdComune
			});

			SalvaOggettoRiepilogo(domanda, riepilogo);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void EliminaRiepiloghiDomandaInEccesso(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			var idDocumentoDaMantenere = this._interventiRepository.GetidDocumentoRiepilogoDaIdIntervento(domanda.ReadInterface.AltriDati.Intervento.Codice);

			domanda.WriteInterface.Documenti.EliminaRiepiloghiDomandainEccesso(idDocumentoDaMantenere.Value);

			_salvataggioDomandaStrategy.Salva(domanda);
		}
	}
}
