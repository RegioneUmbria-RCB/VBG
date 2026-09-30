using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneCaricamentoAllegati;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.SIGePro.Manager.DTO.Scadenzario;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class CaricamentoRiepiloghiSchedeViewModel : IStepViewModel
    {
        private readonly IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        private readonly IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;
        private readonly ICommandSender _commandSender;
        private readonly IGenerazioneRiepilogoSchedeDinamicheService _generazioneRiepilogoSchedeDinamicheService;
        private readonly IConfigurazione<ParametriIntegrazioniDocumentali> _cfg;
        private readonly CaricamentoAllegatiService _caricamentoAllegatiService;

        private MovimentoDaEffettuare _movimentoDaEffettuare;
        private ConfigurazioneMovimentoDaEffettuareDto _configurazioneMovimento;

        public CaricamentoRiepiloghiSchedeViewModel(IMovimentiDaEffettuareRepository readRepository,
                                                    ICommandSender commandSender, IGenerazioneRiepilogoSchedeDinamicheService generazioneRiepilogoSchedeDinamicheService,
                                                    IMovimentiDiOrigineRepository movimentiDiOrigineRepository, IConfigurazione<ParametriIntegrazioniDocumentali> cfg,
                                                    CaricamentoAllegatiService caricamentoAllegatiService)
        {
            this._movimentiDaEffettuareRepository = readRepository;
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
            var retVal = movimento.GetRiepiloghiSchedeDinamiche(movimentoDiOrigine.SchedeDinamiche);

            return retVal;
        }

        public void RigeneraRiepiloghiSenzaOggetto()
        {
            if (this.RichiedeFirmaDigitale)
            {
                return;
            }

            var movimento = this._movimentiDaEffettuareRepository.GetById(this._movimentoDaEffettuare.Id);
            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(movimento);
            var fileRiepilogo = movimento.GetRiepiloghiSchedeDinamiche(movimentoDiOrigine.SchedeDinamiche)
                                             .Where(x => !x.CodiceOggetto.HasValue)
                                             .Select(x => new
                                             {
                                                 IdScheda = x.IdScheda,
                                                 Riepilogo = this.GeneraHtmlScheda(x.IdScheda, $"{this.StripInvalidCharacters(x.NomeScheda)}.pdf")
                                             }).ToArray();

            foreach (var file in fileRiepilogo)
            {
                this.CaricaRiepilogoScheda(file.IdScheda, file.Riepilogo);
            }
        }

        public class RiepilogoDaRigenerare
        {
            public int IdScheda { get; set; }
            public BinaryFile FileRiepilogo { get; set; }
        }

        public async Task RigeneraRiepiloghiSenzaOggettoAsync()
        {
            if (this.RichiedeFirmaDigitale)
            {
                return;
            }

            var movimento = this._movimentiDaEffettuareRepository.GetById(this._movimentoDaEffettuare.Id);
            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(movimento);
            var modelliDaRigenerare = movimento.GetRiepiloghiSchedeDinamiche(movimentoDiOrigine.SchedeDinamiche)
                                             .Where(x => !x.CodiceOggetto.HasValue);

            var fileRiepilogo = new List<RiepilogoDaRigenerare>();
            foreach (var modello in modelliDaRigenerare)
            {
                fileRiepilogo.Add(new RiepilogoDaRigenerare
                {
                    IdScheda = modello.IdScheda,
                    FileRiepilogo = await this.GeneraHtmlSchedaAsync(modello.IdScheda, $"{this.StripInvalidCharacters(modello.NomeScheda)}.pdf")
                });
            }

            foreach (var file in fileRiepilogo)
            {
                this.CaricaRiepilogoScheda(file.IdScheda, file.FileRiepilogo);
            }
        }

        private object StripInvalidCharacters(string nomeScheda)
        {
            return Regex.Replace(nomeScheda, "\\W+", "_");
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

        public BinaryFile GeneraHtmlScheda(int idScheda, string nomeFile)
        {
            var movimento = this._movimentiDaEffettuareRepository.GetById(this._movimentoDaEffettuare.Id);

            return this._generazioneRiepilogoSchedeDinamicheService.GeneraRiepilogoScheda(movimento, idScheda, nomeFile);
        }

        public async Task<BinaryFile> GeneraHtmlSchedaAsync(int idScheda, string nomeFile)
        {
            var movimento = this._movimentiDaEffettuareRepository.GetById(this._movimentoDaEffettuare.Id);

            return await this._generazioneRiepilogoSchedeDinamicheService.GeneraRiepilogoSchedaAsync(movimento, idScheda, nomeFile);
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
