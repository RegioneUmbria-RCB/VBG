// -----------------------------------------------------------------------
// <copyright file="RiepilogoMovimentoDiOrigineViewModel.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
    using CuttingEdge.Conditions;
    using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using log4net;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class RiepilogoMovimentoDiOrigineViewModel : IStepViewModel
    {
        IScadenzeService _scadenzeService;
        ICommandSender _eventBus;
        IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;
        ILog _log = LogManager.GetLogger(typeof(RiepilogoMovimentoDiOrigineViewModel));

        int _idMovimento;

        public RiepilogoMovimentoDiOrigineViewModel(ICommandSender eventBus, IMovimentiDaEffettuareRepository readRepository, 
                                                    IScadenzeService scadenzeService, IMovimentiDiOrigineRepository movimentiDiOrigineRepository)
        {
            Condition.Requires(eventBus, "eventBus").IsNotNull();
            Condition.Requires(readRepository, "readRepository").IsNotNull();
            Condition.Requires(scadenzeService, "scadenzeService").IsNotNull();
            Condition.Requires(movimentiDiOrigineRepository, "movimentiDiOrigineRepository").IsNotNull();

            this._scadenzeService = scadenzeService;
            this._eventBus = eventBus;
            this._movimentiDaEffettuareRepository = readRepository;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
        }

        public MovimentoDiOrigine GetMovimentoDiOrigine(int idMovimento)
        {
            //var datiScadenza = this._scadenzeService.GetById(idMovimento);
            var movimentodaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);

            if (movimentodaEffettuare == null)
            {
                CreaMovimento(idMovimento);

                movimentodaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);
            }

            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(movimentodaEffettuare);
            //var idMovimentoDiOrigine = Convert.ToInt32(datiScadenza.CodMovimento);

            //var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(idMovimentoDiOrigine);
            //var movimentoDaEffettuare = this._movimentiDiOrigineRepository.GetById(idMovimento);

            //if (movimentoDaEffettuare.PubblicaSchede)
            //{
            //    movimentoDiOrigine.SchedeDinamiche = movimentoDaEffettuare.SchedeDinamiche;
            //}

            return movimentoDiOrigine;
        }

        public string GetAttivitaRichiesta(int idMovimento)
        {
            var datiScadenza = this._scadenzeService.GetById(idMovimento);

            return datiScadenza.DescrMovimentoDaFare;
        }

        public void CreaMovimento(int idMovimento)
        {
            var movimentoDaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);

            if (movimentoDaEffettuare != null)
            {
                return;
            }

            var datiScadenza = this._scadenzeService.GetById(idMovimento);
            
            var cmd = new CreaMovimento(datiScadenza.CodEnte, idMovimento, Convert.ToInt32(datiScadenza.CodMovimento));

            this._eventBus.Send(cmd);
        }

        public bool CanEnterStep()
        {
            return true;
        }

        public bool CanExitStep()
        {
            var movimentoDaEffettuare = this._movimentiDaEffettuareRepository.GetById(this._idMovimento);

            if (movimentoDaEffettuare == null)
            {
                _log.ErrorFormat("Impossibile uscire dallo step perchè il movimento con id {0} non è stato creato", this._idMovimento);
            }

            return true;
        }

        public void SetIdMovimento(int idMovimento)
        {
            this._idMovimento = idMovimento;
        }
    }
}
