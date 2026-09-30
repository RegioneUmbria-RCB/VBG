// -----------------------------------------------------------------------
// <copyright file="InvioMovimentoService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.STC.Service;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.Converter;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using log4net;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface ITrasmissioneMovimentoService
    {
        void Trasmetti(int idMovimento);
    }

    public class TrasmissioneMovimentoService : ITrasmissioneMovimentoService
    {
        private readonly IStcService _stcService;
        private readonly ILog _log = LogManager.GetLogger(typeof(TrasmissioneMovimentoService));
        private readonly IMovimentoDaEffettuareToNotificaAttivitaRequestConverter _converter;
        private readonly IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        private readonly IMovimentiDiOrigineRepository _movimentoDiOrigineRepository;

        public TrasmissioneMovimentoService(IStcService stcService, IMovimentoDaEffettuareToNotificaAttivitaRequestConverter converter, IMovimentiDaEffettuareRepository movimentiDaEffettuareRepository, IMovimentiDiOrigineRepository movimentoDiOrigineRepository)
        {
            this._stcService = stcService;
            this._converter = converter;
            this._movimentiDaEffettuareRepository = movimentiDaEffettuareRepository;
            this._movimentoDiOrigineRepository = movimentoDiOrigineRepository;
        }

        #region ITrasmissioneMovimentoService Members

        public void Trasmetti(int idMovimento)
        {
            var request = this._converter.Convert(idMovimento);

            this._stcService.NotificaAttivita(request, sportelloDestinatario =>
            {
                var movimentoDaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);
                var movimentoDiOrigine = this._movimentoDiOrigineRepository.GetById(movimentoDaEffettuare);

                sportelloDestinatario.idSportello = movimentoDiOrigine.Software;
            });
        }


        #endregion
    }

}
