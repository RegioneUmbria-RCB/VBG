// -----------------------------------------------------------------------
// <copyright file="OneriDomandaService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri.Sincronizzazione;
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
	using log4net;

	public class OneriDomandaService
	{
		ILog _log = LogManager.GetLogger(typeof(OneriDomandaService));
		ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
		IAliasSoftwareResolver _aliasSoftwareResolver;
		ILogicaSincronizzazioneOneri _logicaSincronizzazioneOneri;
		IOneriRepository _oneriRepository;		

		public OneriDomandaService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAllegatiDomandaFoRepository allegatiDomandaFoRepository, IAliasSoftwareResolver aliasSoftwareResolver, ILogicaSincronizzazioneOneri logicaSincronizzazioneOneri, IOneriRepository oneriRepository)
		{
			this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
			this._logicaSincronizzazioneOneri = logicaSincronizzazioneOneri;
			this._oneriRepository = oneriRepository;
		}

		public void SincronizzaOneri(int idDomanda)
		{
			try
			{
				_log.Debug("inizio sincronizzazione oneri domanda");

				var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

				this._logicaSincronizzazioneOneri.SincronizzaOneri(domanda);

				_salvataggioDomandaStrategy.Salva(domanda);

				_log.Debug("sincronizzazione oneri domanda terminata");
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la sincronizzazione degli oneri della domanda: {0}", ex.ToString());

				throw;
			}
		}

		public void SpecificaEstremiPagamento(int idDomanda, IEnumerable<EstremiPagamento> estremiPagamenti)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Oneri.CancellaEstremiPagamento();

			foreach (var e in estremiPagamenti)
			{
				if (e.Provenienza == ProvenienzaOnere.Intervento)
				{
					domanda.WriteInterface.Oneri.ImpostaEstremiPagamentoOnereIntervento(e.IdOnere, e.CodiceInterventoOEndo, e.TipoPagamento.Codice, e.TipoPagamento.Descrizione, e.Data, e.Riferimento, e.ImportoPagato);
				}
				else
				{
					domanda.WriteInterface.Oneri.ImpostaEstremiPagamentoOnereEndo(e.IdOnere, e.CodiceInterventoOEndo, e.TipoPagamento.Codice, e.TipoPagamento.Descrizione, e.Data, e.Riferimento, e.ImportoPagato);
				}
			}

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void InserisciAttestazioneDiPagamento(int idDomanda, BinaryFile allegato)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			var esitoSalvataggio = _allegatiDomandaFoRepository.SalvaAllegato(idDomanda, allegato, false);

			domanda.WriteInterface.Oneri.SalvaAttestazioneDiPagamento(esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void ImpostaDichiarazioneDiAssenzaOneriDaPagare(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Oneri.ImpostaDichiarazioneAssenzaOneriDaPagare();

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void RimuoviDichiarazioneDiAssenzaOneriDaPagare(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Oneri.RimuoviDichiarazioneAssenzaOneriDaPagare();

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void EliminaAttestazioneDiPagamento(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Oneri.EliminaAttestazioneDiPagamento();

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public IEnumerable<TipoPagamento> GetListaModalitaPagamento()
		{
			return this
					._oneriRepository
					.GetModalitaPagamento()
					.Select(x => new TipoPagamento(x.Codice, x.Descrizione));

		}
	}
}
