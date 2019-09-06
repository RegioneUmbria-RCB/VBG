// -----------------------------------------------------------------------
// <copyright file="CaricamentoRiepiloghiSchedeViewModel.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
    using Init.Sigepro.FrontEnd.Infrastructure;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici;
    using Init.SIGePro.DatiDinamici;
    using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
    using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche;
    using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneCaricamentoAllegati;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class CaricamentoRiepiloghiSchedeViewModel : IStepViewModel
    {
        IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;
        ICommandSender _commandSender;
        IGenerazioneRiepilogoSchedeDinamicheService _generazioneRiepilogoSchedeDinamicheService;
        IConfigurazione<ParametriIntegrazioniDocumentali> _cfg;
        CaricamentoAllegatiService _caricamentoAllegatiService;


        MovimentoDaEffettuare _movimentoDaEffettuare;
        ConfigurazioneMovimentoDaEffettuare _configurazioneMovimento;

        public CaricamentoRiepiloghiSchedeViewModel(IMovimentiDaEffettuareRepository readRepository, 
                                                    ICommandSender commandSender, IGenerazioneRiepilogoSchedeDinamicheService generazioneRiepilogoSchedeDinamicheService, 
                                                    IMovimentiDiOrigineRepository movimentiDiOrigineRepository, IConfigurazione<ParametriIntegrazioniDocumentali> cfg,
                                                    CaricamentoAllegatiService caricamentoAllegatiService)
        {
            this._movimentiDaEffettuareRepository= readRepository;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
            this._commandSender = commandSender;
            this._generazioneRiepilogoSchedeDinamicheService = generazioneRiepilogoSchedeDinamicheService;
            this._cfg = cfg;
            this._caricamentoAllegatiService = caricamentoAllegatiService;
        }

        public IEnumerable<RiepilogoSchedaDinamica> GetListaRiepiloghi()
        {
            var movimento = this._movimentiDaEffettuareRepository.GetById(this._movimentoDaEffettuare.Id);
            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(movimento);

            return movimento.GetRiepiloghiSchedeDinamiche(movimentoDiOrigine.SchedeDinamiche);
        }

        public void CaricaRiepilogoScheda(int idScheda, BinaryFile file)
        {
            var esitoCaricamento = this._caricamentoAllegatiService.Carica(file);

            var cmd = new AllegaRiepilogoSchedaDinamicaAMovimentoV2(this._movimentoDaEffettuare.AliasComune,
                                                                    this._movimentoDaEffettuare.Id,
                                                                    idScheda,
                                                                    esitoCaricamento.CodiceOggetto,
                                                                    file.FileName,
                                                                    esitoCaricamento.FirmatoDigitalmente);

            this._commandSender.Send(cmd);
        }

        public bool RichiedeFirmaDigitale
        {
            get
            {
                return this._configurazioneMovimento.RichiedeFirmaDocumenti;
            }
        }

        public void EliminaRiepilogoScheda(int idScheda)
        {
            var cmd = new RimuoviRiepilogoSchedaDinamicaDalMovimento(this._movimentoDaEffettuare.AliasComune,
                                                                      this._movimentoDaEffettuare.Id,
                                                                      idScheda);

            this._commandSender.Send(cmd);
        }

        public BinaryFile GeneraHtmlScheda(int idScheda)
        {
            var movimento = this._movimentiDaEffettuareRepository.GetById(this._movimentoDaEffettuare.Id);

            return this._generazioneRiepilogoSchedeDinamicheService.GeneraRiepilogoScheda(movimento, idScheda);
        }

        public string GetNomeAttivitaDaEffettuare()
        {
            var movimento = this._movimentiDaEffettuareRepository.GetById(this._movimentoDaEffettuare.Id);

            return movimento.NomeAttivita;
        }

        public IEnumerable<string> GetNomiSchedeNonCompilate()
        {
            return this.GetListaRiepiloghi().Where(x => !x.CodiceOggetto.HasValue).Select(x => x.NomeScheda);
        }

        public void SetIdMovimento(int idMovimento)
        {
            this._movimentoDaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);
            this._configurazioneMovimento = this._movimentiDaEffettuareRepository.GetConfigurazioneMovimento(idMovimento);
        }

        public bool CanEnterStep()
        {
            if (this._cfg.Parametri.MovimentoDaEffettuare.BloccaUploadRiepiloghiSchede)
            {
                return false;
            }

            return this._movimentoDaEffettuare.ListaIdSchedeCompilate.Count() > 0;
        }

        public bool CanExitStep()
        {
            return this.GetListaRiepiloghi().Count(x => !x.CodiceOggetto.HasValue) == 0;
        }
    }
}
