using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
	public class AllegatiInterventoService
	{
		ILog _log = LogManager.GetLogger(typeof(AllegatiInterventoService));

		readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
		readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		readonly IInterventiAllegatiRepository _interventiAllegatiRepository;
		readonly GenerazioneRiepilogoDomandaService _generazioneriepilogoService;
		readonly IInterventiRepository _interventiRepository;
        readonly ILogicaSincronizzazioneAllegatiIntervento _logicaSincronizzazioneAllegatiIntervento;
        readonly IConfigurazione<ParametriWorkflow> _configurazione;

		public AllegatiInterventoService(IAllegatiDomandaFoRepository allegatiDomandaFoRepository,
										 ISalvataggioDomandaStrategy salvataggioDomandaStrategy,
										 IInterventiAllegatiRepository interventiAllegatiRepository,
										 GenerazioneRiepilogoDomandaService generazioneriepilogoService,
										 IInterventiRepository interventiRepository,
                                         ILogicaSincronizzazioneAllegatiIntervento logicaSincronizzazioneAllegatiIntervento,
                                         IConfigurazione<ParametriWorkflow> configurazione)
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
				.Requires(generazioneriepilogoService, "generazioneriepilogoService")
				.IsNotNull();

			Condition
				.Requires(interventiRepository, "interventiRepository")
				.IsNotNull();

			this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
			this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._interventiAllegatiRepository = interventiAllegatiRepository;
			this._generazioneriepilogoService = generazioneriepilogoService;
			this._interventiRepository = interventiRepository;
            this._logicaSincronizzazioneAllegatiIntervento = logicaSincronizzazioneAllegatiIntervento;
            this._configurazione = configurazione;
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

		private void SalvaOggettoRiepilogo(DomandaOnline domanda, BinaryFile file, bool ignoraVerificaHash = false)
		{
			var row = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();
			var verificaHashes = _configurazione.Parametri.VerificaHashFilesFirmati;
			var hashConfronto = row.AllegatoDellUtente == null ? String.Empty : row.AllegatoDellUtente.Md5;

			SalvataggioAllegatoResult result = null;

            if (!ignoraVerificaHash && verificaHashes)
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

			this._logicaSincronizzazioneAllegatiIntervento.Sincronizza(domanda);

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

        public void AggiungiAllegatoLibero(int idDomanda, string descrizione, BinaryFile file, int codiceCategoria = -1, string descrizioneCategoria = "Altri allegati", bool verificaFirma = false)
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

        public DocumentoDomanda GetById(int idDomanda, int idAllegato)
        {
            var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

            return domanda.ReadInterface.Documenti.Intervento.GetById(idAllegato);
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

            var riepilogo = _generazioneriepilogoService.GeneraRiepilogoDomanda(idDomanda, true, false);

			SalvaOggettoRiepilogo(domanda, riepilogo, true);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void EliminaRiepiloghiDomandaInEccesso(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);
            var idIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;

			var idDocumentoDaMantenere = this._interventiRepository.GetidDocumentoRiepilogoDaIdIntervento(idIntervento);

			domanda.WriteInterface.Documenti.EliminaRiepiloghiDomandainEccesso(idDocumentoDaMantenere.Value);

            // Se non sono presenti riepiloghi domanda lo allego
            var riepilogo = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();

            if (riepilogo == null && idDocumentoDaMantenere.HasValue)
            {
                var nextId = 1;
                
                if(domanda.ReadInterface.Documenti.Intervento.Documenti.Count() > 0)
                {
                    nextId = domanda.ReadInterface.Documenti.Intervento.Documenti.Max(x => x.Id) + 1;
                }
                

                var allegatiIntervento = this._interventiAllegatiRepository.GetAllegatiDaIdintervento(idIntervento, AmbitoRicerca.AreaRiservata);

                var documento = allegatiIntervento.Where(x => x.Codice == idDocumentoDaMantenere.Value).FirstOrDefault();

                if (documento != null)
                {
                    var codiceDocumento = documento.Codice;
                    var descrizione = documento.Descrizione;
                    var linkInformazioni = documento.LinkInformazioni;
                    var codiceOggetto = documento.CodiceOggettoModello;
                    var richiesto = documento.Richiesto;
                    var richiedeFirma = documento.RichiedeFirma;
                    var tipoDownload = documento.TipoDownload;
                    var ordine = documento.Ordine.GetValueOrDefault(0);
                    var nomeFileModello = documento.NomeFileModello;
                    var codiceCategoria = GestioneDocumentiConstants.CategorieDocumenti.AltriAllegatiCodice;
                    var descrizioneCategoria = GestioneDocumentiConstants.CategorieDocumenti.AltriAllegatiDescrizione;
                    var riepilogoDomanda = documento.RiepilogoDomanda;
                    var note = documento.Note;

                    if (documento.Categoria != null)
                    {
                        codiceCategoria = documento.Categoria.Codice;
                        descrizioneCategoria = documento.Categoria.Descrizione;
                    }

                    domanda.WriteInterface.Documenti.AggiungiOAggiornaDocumentoIntervento(codiceDocumento,
                                                                                    descrizione,
                                                                                    linkInformazioni,
                                                                                    codiceOggetto,
                                                                                    richiesto,
                                                                                    richiedeFirma,
                                                                                    tipoDownload,
                                                                                    ordine,
                                                                                    nomeFileModello,
                                                                                    riepilogoDomanda,
                                                                                    codiceCategoria,
                                                                                    descrizioneCategoria,
                                                                                    note);
                }


            }

			_salvataggioDomandaStrategy.Salva(domanda);
		}
	}
}
