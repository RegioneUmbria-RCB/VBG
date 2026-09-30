// -----------------------------------------------------------------------
// <copyright file="OneriDomandaService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
    using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
    using log4net;
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;

    public partial class OneriDomandaService : IOneriDomandaService, ITipiPagamentoService
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
                this._log.Debug("inizio sincronizzazione oneri domanda");

                var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

                this._logicaSincronizzazioneOneri.ComportamentoOneriSenzaImporto = comportamentoOneriSenzaImporto;

                this._logicaSincronizzazioneOneri.SincronizzaOneri(domanda);

                this._salvataggioDomandaStrategy.Salva(domanda);

                this._log.Debug("sincronizzazione oneri domanda terminata");

            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la sincronizzazione degli oneri della domanda: {0}", ex.ToString());

                throw;
            }
        }

        public async ValueTask SpecificaEstremiPagamentoAsync(int idDomanda, IEnumerable<OnerePagato> oneriPagati)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            foreach (var o in oneriPagati)
            {
                domanda.WriteInterface.Oneri.ForzaEstremiPagamento(o.Id, o.ModalitaPagamento, o.EstremiPagamento);
            }

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda);
        }

        public void SpecificaEstremiPagamento(int idDomanda, IEnumerable<OnerePagato> oneriPagati)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            foreach (var o in oneriPagati)
            {
                domanda.WriteInterface.Oneri.ForzaEstremiPagamento(o.Id, o.ModalitaPagamento, o.EstremiPagamento);
            }

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public async ValueTask SpecificaEstremiPagamentoOneriNonPagatiOnlineAsync(int idDomanda, IEnumerable<OnerePagato> oneriPagati)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            domanda.WriteInterface.Oneri.CancellaEstremiPagamentoOneriNonPagatiOnline();

            foreach (var o in oneriPagati)
            {
                domanda.WriteInterface.Oneri.ImpostaEstremiPagamentoOneriNonPagatiOnline(o.Id, o.ModalitaPagamento, o.EstremiPagamento);
            }

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda);
        }

        public void SpecificaEstremiPagamentoOneriNonPagatiOnline(int idDomanda, IEnumerable<OnerePagato> oneriPagati)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Oneri.CancellaEstremiPagamentoOneriNonPagatiOnline();

            foreach (var o in oneriPagati)
            {
                domanda.WriteInterface.Oneri.ImpostaEstremiPagamentoOneriNonPagatiOnline(o.Id, o.ModalitaPagamento, o.EstremiPagamento);
            }

            this._salvataggioDomandaStrategy.Salva(domanda);
        }


        public void InserisciAttestazioneDiPagamento(int idDomanda, int codiceOggetto)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var esitoSalvataggio = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, codiceOggetto);

            domanda.WriteInterface.Oneri.SalvaAttestazioneDiPagamento(esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void InserisciAttestazioneDiPagamento(int idDomanda, BinaryFile allegato)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var esitoSalvataggio = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, allegato, false);

            domanda.WriteInterface.Oneri.SalvaAttestazioneDiPagamento(esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public async ValueTask ToggleDichiarazioneDiAssenzaOneriDaPagareAsync(bool toggle, int idDomanda)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            if (toggle)
            {
                domanda.WriteInterface.Oneri.ImpostaDichiarazioneAssenzaOneriDaPagare();
            }
            else
            {
                domanda.WriteInterface.Oneri.RimuoviDichiarazioneAssenzaOneriDaPagare();
            }

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda);
        }

        public void ToggleDichiarazioneDiAssenzaOneriDaPagare(bool toggle, int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            if (toggle)
            {
                domanda.WriteInterface.Oneri.ImpostaDichiarazioneAssenzaOneriDaPagare();
            }
            else
            {
                domanda.WriteInterface.Oneri.RimuoviDichiarazioneAssenzaOneriDaPagare();
            }
            this._salvataggioDomandaStrategy.Salva(domanda);
        }


        public void ImpostaDichiarazioneDiAssenzaOneriDaPagare(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Oneri.ImpostaDichiarazioneAssenzaOneriDaPagare();

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void RimuoviDichiarazioneDiAssenzaOneriDaPagare(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Oneri.RimuoviDichiarazioneAssenzaOneriDaPagare();

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void EliminaAttestazioneDiPagamento(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Oneri.EliminaAttestazioneDiPagamento();

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public IEnumerable<TipoPagamento> GetListaTipiPagamento()
        {
            return
                    this._oneriRepository
                    .GetTipiPagamento()
                    .Select(x => new TipoPagamento(x.Codice, x.Descrizione));

        }

        public TipoPagamento GetTipoPagamentoById(string idTipoPagamento)
        {
            var tp = this._oneriRepository.GetModalitaPagamentoById(idTipoPagamento);

            return tp == null ? null : new TipoPagamento(tp.Codice, tp.Descrizione);
        }

        public string GetCodiceCausaleOnereTraslazione(int idCausale)
        {
            return this._oneriRepository.GetCodiceCausaleOnereTraslazione(idCausale);
        }

    }
}
