// -----------------------------------------------------------------------
// <copyright file="InvioMovimentoService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
    using Init.Sigepro.FrontEnd.AppLogic.StcService;
    using System.Web;
    using System.IO;
    using ServiceStack.Text;
    using log4net;
    using AutoMapper;
    using Init.Sigepro.FrontEnd.AppLogic.STC;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.Converter;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface ITrasmissioneMovimentoService
    {
        void Trasmetti(int idMovimento);
    }

    public class TrasmissioneMovimentoService : ITrasmissioneMovimentoService
    {
        IStcService _stcService;
        ILog _log = LogManager.GetLogger(typeof(TrasmissioneMovimentoService));
        IMovimentoDaEffettuareToNotificaAttivitaRequestConverter _converter;
        IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        IMovimentiDiOrigineRepository _movimentoDiOrigineRepository;

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
