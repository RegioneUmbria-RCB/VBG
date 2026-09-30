using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.SIGePro.Manager.DTO.Scadenzario;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
    public class IntegrazioneSITDaScadenzarioViewModel : IStepViewModel
    {
        private readonly IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        private readonly IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;
        private MovimentoDaEffettuare _movimentoDaEffettuare;
        private MovimentoDiOrigine _movimentoDiOrigine;
        private ConfigurazioneMovimentoDaEffettuareDto _configurazioneMovimento = null;

        public IntegrazioneSITDaScadenzarioViewModel(IMovimentiDaEffettuareRepository movimentiDaEffettuareRepository, IMovimentiDiOrigineRepository movimentiDiOrigineRepository/*, ConfigurazioneMovimentoDaEffettuareDto configurazioneMovimento*/)
        {
            this._movimentiDaEffettuareRepository = movimentiDaEffettuareRepository;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
            // this._configurazioneMovimento = configurazioneMovimento;
        }

        public MovimentoDaEffettuare GetMovimentoDaEffettuare()
        {
            return this._movimentoDaEffettuare;
        }

        public MovimentoDiOrigine GetMovimentoDiOrigine()
        {
            return this._movimentoDiOrigine;
        }

        public bool CanEnterStep()
        {
            return (this._configurazioneMovimento.RichiedeInterazioneConSIT);
        }

        public bool CanExitStep()
        {
            return true;
        }

        public void SetIdMovimento(int idMovimento)
        {
            this._movimentoDaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);
            this._movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(this._movimentoDaEffettuare);
            this._configurazioneMovimento = this._movimentiDaEffettuareRepository.GetConfigurazioneMovimento(idMovimento);
        }
    }
}
