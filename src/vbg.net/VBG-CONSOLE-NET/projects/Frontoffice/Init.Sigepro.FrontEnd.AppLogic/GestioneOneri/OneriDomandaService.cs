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
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
	using log4net;

	public class OneriDomandaService
	{
		private readonly ILog _log = LogManager.GetLogger(typeof(OneriDomandaService));
		private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		private readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
		private readonly ILogicaSincronizzazioneOneri _logicaSincronizzazioneOneri;
		private readonly IOneriRepository _oneriRepository;		

		public OneriDomandaService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAllegatiDomandaFoRepository allegatiDomandaFoRepository, ILogicaSincronizzazioneOneri logicaSincronizzazioneOneri, IOneriRepository oneriRepository)
		{
			this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
			this._logicaSincronizzazioneOneri = logicaSincronizzazioneOneri;
			this._oneriRepository = oneriRepository;
		}

		public void SincronizzaOneri(int idDomanda, ComportamentoSincronizzazioneOneriSenzaImporto comportamentoOneriSenzaImporto)
		{
			try
			{
				_log.Debug("inizio sincronizzazione oneri domanda");

				var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

                this._logicaSincronizzazioneOneri.ComportamentoOneriSenzaImporto = comportamentoOneriSenzaImporto;
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

		[Obsolete]
        public void SpecificaEstremiPagamento(int idDomanda, IEnumerable<OnerePagato> oneriPagati)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Oneri.CancellaEstremiPagamentoOneriNonPagatiOnline();

            foreach (var o in oneriPagati)
			{
                domanda.WriteInterface.Oneri.ImpostaEstremiPagamentoOneriNonPagatiOnline(o.Id, o.ModalitaPagamento, o.EstremiPagamento);
			}

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void SpecificaEstremiPagamentoOneriNonPagatiOnline(int idDomanda, IEnumerable<OnerePagato> oneriPagati)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Oneri.CancellaEstremiPagamentoOneriNonPagatiOnline();

			foreach (var o in oneriPagati)
			{
				domanda.WriteInterface.Oneri.ImpostaEstremiPagamentoOneriNonPagatiOnline(o.Id, o.ModalitaPagamento, o.EstremiPagamento);
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
					.GetModalitaPagamento();

		}
    }
}
