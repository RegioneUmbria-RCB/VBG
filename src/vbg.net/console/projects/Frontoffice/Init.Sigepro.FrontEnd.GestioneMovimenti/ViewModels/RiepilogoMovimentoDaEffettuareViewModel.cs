// -----------------------------------------------------------------------
// <copyright file="RiepilogoMovimentoDaEffettuareViewModel.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
    using Init.Sigepro.FrontEnd.Infrastructure;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
    using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneCaricamentoAllegati;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class RiepilogoMovimentoDaEffettuareViewModel : IStepViewModel
    {
        public class RigaRiepilogoSchedeDinamiche
        {
            public string NomeScheda { get; protected set; }
            public int CodiceOggetto { get; protected set; }
            public string NomeFile { get; protected set; }

            public RigaRiepilogoSchedeDinamiche(string nomeScheda, int codiceOggetto, string nomeFile)
            {
                this.NomeScheda = nomeScheda;
                this.CodiceOggetto = codiceOggetto;
                this.NomeFile = nomeFile;
            }
        }

        public class Documento
        {
            public int CodiceOggetto { get; set; }
            public string Nomefile { get; set; }
        }

        public class RigaRiepilogoSostituzioniDocumentali
        {
            public string Descrizione { get; set; }
            public Documento FileOriginale { get; set; }
            public Documento FileSostitutivo { get; set; }
        }


        ICommandSender _bus;
        IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;
        IAliasResolver _aliasResolver;
        CaricamentoAllegatiService _caricamentoAllegatiService;

        MovimentoDaEffettuare _movimentoDaEffettuare;
        MovimentoDiOrigine _movimentoDiOrigine;
        DocumentiIstanzaSostituibili _documentiSostituibili;
        ConfigurazioneMovimentoDaEffettuare _configurazioneMovimento;

        public RiepilogoMovimentoDaEffettuareViewModel(IAliasResolver aliasResolver, ICommandSender eventBus, IMovimentiDaEffettuareRepository movimentiDaEffettuareRepository, CaricamentoAllegatiService caricamentoAllegatiService, IMovimentiDiOrigineRepository movimentiDiOrigineRepository, ConfigurazioneMovimentoDaEffettuare configurazioneMovimento)
        {
            this._aliasResolver = aliasResolver;
            this._bus = eventBus;
            this._movimentiDaEffettuareRepository = movimentiDaEffettuareRepository;
            this._caricamentoAllegatiService = caricamentoAllegatiService;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
            this._configurazioneMovimento = configurazioneMovimento;
        }

        public MovimentoDaEffettuare GetMovimentoDaEffettuare()
        {
            return this._movimentoDaEffettuare;
        }


        public void CaricaAllegato(string descrizione, BinaryFile file)
        {
            if (String.IsNullOrEmpty(descrizione))
                throw new ValidationException("Specificare una descrizione per l'allegato");

            var esitoCaricamento = this._caricamentoAllegatiService.Carica(file);
            
            var cmd = new AggiungiAllegatoAlMovimentoV2(this._aliasResolver.AliasComune, this._movimentoDaEffettuare.Id, esitoCaricamento.CodiceOggetto, file.FileName, descrizione, esitoCaricamento.FirmatoDigitalmente);

            this._bus.Send(cmd);
        }

        public IEnumerable<string> ValidaPerInvio()
        {
            if (!this._configurazioneMovimento.RichiedeFirmaDocumenti)
            {
                return Enumerable.Empty<string>();
            }

            return this.GetMovimentoDaEffettuare().Allegati.Where(x => !x.FirmatoDigitalmente).Select(x => "Il file \"" + x.Note + "\" non è stato firmato digitalmente oppure non è firmato digitalmente oppure non contiene firme digitali valide");
        }

        public void Invia()
        {
            var cmd = new TrasmettiMovimento(this._aliasResolver.AliasComune, this._movimentoDaEffettuare.Id);

            this._bus.Send(cmd);
        }

        public void EliminaAllegato(int idAllegato)
        {
            var cmd = new RimuoviAllegatoDalMovimento(this._aliasResolver.AliasComune, this._movimentoDaEffettuare.Id, idAllegato);

            this._bus.Send(cmd);
        }

        public void AggiornaNoteMovimento(string note)
        {
            var cmd = new ModificaNoteMovimento(this._aliasResolver.AliasComune, this._movimentoDaEffettuare.Id, note);

            this._bus.Send(cmd);
        }

        public IEnumerable<RigaRiepilogoSostituzioniDocumentali> GetListaSostituzioni()
        {
            //var codiciOggettoOrigine = this._movimentoDaEffettuare.SostituzioniDocumentali.Select(x => x.CodiceOggettoOrigine);
            var documenti = this._documentiSostituibili
                                            .DocumentiIntervento
                                            .Documenti
                                            .Union(GetSostituzioniEndo());

            var sostituzioniPresenti = this._movimentoDaEffettuare
                                            .SostituzioniDocumentali
                                            .Select(x => new
                                            {
                                                Sostituzione = x,
                                                Origine = documenti.Where(y => y.CodiceOggetto == x.CodiceOggettoOrigine).FirstOrDefault()
                                            })
                                            .Where( x=> x.Origine != null);


            return sostituzioniPresenti
                        .Select(x => new RigaRiepilogoSostituzioniDocumentali
                        {
                            Descrizione = x.Origine.Descrizione,
                            FileOriginale = new Documento 
                            {
                                CodiceOggetto = x.Sostituzione.CodiceOggettoOrigine,
                                Nomefile = x.Sostituzione.NomeFileOrigine
                            },
                            FileSostitutivo = new Documento
                            {
                                CodiceOggetto = x.Sostituzione.CodiceOggettoSostitutivo,
                                Nomefile = x.Sostituzione.NomeFileSostitutivo
                            }
                        });
        }

        private IEnumerable<DocumentoSostituibileMovimentoDto> GetSostituzioniEndo()
        {
            if (this._documentiSostituibili.DocumentiEndo == null)
            {
                return Enumerable.Empty<DocumentoSostituibileMovimentoDto>();
            }

            return this._documentiSostituibili.DocumentiEndo.SelectMany(docEndo => docEndo.Documenti);
        }

        public IEnumerable<RigaRiepilogoSchedeDinamiche> GetListaSchedeCompilate()
        {
            var movimento = this.GetMovimentoDaEffettuare();

            return movimento.GetRiepiloghiSchedeDinamiche(this._movimentoDiOrigine.SchedeDinamiche)
                            .Where(x => x.CodiceOggetto.HasValue)
                            .Select(x => new RigaRiepilogoSchedeDinamiche(x.NomeScheda, x.CodiceOggetto.Value, x.NomeFile));
        }

        public void SetIdMovimento(int idMovimento)
        {
            this._movimentoDaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);
            this._movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(this._movimentoDaEffettuare);
            this._documentiSostituibili = this._movimentiDaEffettuareRepository.GetDocumentiSostituibili(idMovimento);
        }

        public bool CanEnterStep()
        {
            return true;
        }

        public bool CanExitStep()
        {
            return true;
        }
    }
}
