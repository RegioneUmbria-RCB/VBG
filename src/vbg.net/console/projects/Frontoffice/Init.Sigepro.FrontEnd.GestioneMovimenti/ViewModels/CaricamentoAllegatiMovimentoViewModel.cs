using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneCaricamentoAllegati;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
    public class CaricamentoAllegatiMovimentoViewModel : IStepViewModel
    {
        IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        CaricamentoAllegatiService _caricamentoAllegatiService;
        ICommandSender _eventBus;
        IConfigurazione<ParametriIntegrazioniDocumentali> _cfg;

        MovimentoDaEffettuare _movimentoDaEffettuare;
        ConfigurazioneMovimentoDaEffettuare _configurazioneMovimento;

        public CaricamentoAllegatiMovimentoViewModel(ICommandSender eventBus, IMovimentiDaEffettuareRepository movimentiDaEffettuareRepository, CaricamentoAllegatiService caricamentoAllegatiService, IConfigurazione<ParametriIntegrazioniDocumentali> cfg)
        {
            this._movimentiDaEffettuareRepository = movimentiDaEffettuareRepository;
            this._caricamentoAllegatiService = caricamentoAllegatiService;
            this._eventBus = eventBus;
            this._cfg = cfg;
        }

        public bool RichiedeFirmaDigitale
        {
            get
            {
                return this._configurazioneMovimento.RichiedeFirmaDocumenti;
            }
        }

        public void CaricaAllegato(string descrizione, BinaryFile file)
        {
            if (String.IsNullOrEmpty(descrizione))
                throw new ValidationException("Specificare una descrizione per l'allegato");

            var esitoCaricamento = this._caricamentoAllegatiService.Carica(file);

            var cmd = new AggiungiAllegatoAlMovimentoV2(this._movimentoDaEffettuare.AliasComune, this._movimentoDaEffettuare.Id, esitoCaricamento.CodiceOggetto, file.FileName, descrizione, esitoCaricamento.FirmatoDigitalmente);

            this._eventBus.Send(cmd);
        }

        public void EliminaAllegato(int idAllegato)
        {
            var cmd = new RimuoviAllegatoDalMovimento(this._movimentoDaEffettuare.AliasComune, this._movimentoDaEffettuare.Id, idAllegato);

            this._eventBus.Send(cmd);
        }

        public MovimentoDaEffettuare GetMovimentoDaEffettuare()
        {
            return this._movimentoDaEffettuare;
        }


        public IEnumerable<string> GetErroriFilesNonFirmati()
        {
            if (!this._configurazioneMovimento.RichiedeFirmaDocumenti)
            {
                return Enumerable.Empty<string>();
            }

            return this.GetMovimentoDaEffettuare()
                .Allegati
                .Where(x => !x.FirmatoDigitalmente)
                .Select(x => "Il file \"" + x.Note + "\" non è stato firmato digitalmente oppure non contiene firme digitali valide");
        }


        public void SetIdMovimento(int idMovimento)
        {
            this._movimentoDaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);
            this._configurazioneMovimento = this._movimentiDaEffettuareRepository.GetConfigurazioneMovimento(idMovimento);
        }

        public bool CanEnterStep()
        {
            return !this._cfg.Parametri.MovimentoDaEffettuare.BloccaUploadAllegati;
        }

        public bool CanExitStep()
        {
            return GetErroriFilesNonFirmati().Count() == 0;
        }
    }
}
