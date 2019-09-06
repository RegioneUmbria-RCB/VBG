using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneCaricamentoAllegati;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
    public class SostituzioniDocumentaliViewModel : IStepViewModel
    {
        IScadenzeService _scadenzeService;
        IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;
        ICommandSender _commandSender;
        CaricamentoAllegatiService _caricamentoAllegatiService;
        MovimentiBackofficeServiceCreator _serviceCreator;

        MovimentoDaEffettuare _movimentoDaEffettuare;
        MovimentoDiOrigine _movimentoDiOrigine;
        DocumentiIstanzaSostituibili _documentiSostituibili;
        ConfigurazioneMovimentoDaEffettuare _configurazioneMovimento;


        public SostituzioniDocumentaliViewModel(IScadenzeService scadenzeService, IMovimentiDiOrigineRepository movimentiDiOrigineRepository,
                                                IMovimentiDaEffettuareRepository movimentiDaEffettuareRepository, ICommandSender commandSender,
                                                CaricamentoAllegatiService caricamentoAllegatiService, MovimentiBackofficeServiceCreator serviceCreator)
        {
            this._scadenzeService = scadenzeService;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
            this._movimentiDaEffettuareRepository = movimentiDaEffettuareRepository;
            this._commandSender = commandSender;
            this._caricamentoAllegatiService = caricamentoAllegatiService;
            this._serviceCreator = serviceCreator;
        }

        public bool RichiedeFirmaDigitale
        {
            get
            {
                return this._configurazioneMovimento.RichiedeFirmaDocumenti;
            }
        }

        public void SetIdMovimento(int idMovimento)
        {
            this._movimentoDaEffettuare = this._movimentiDaEffettuareRepository.GetById(idMovimento);
            this._movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(this._movimentoDaEffettuare);
            this._documentiSostituibili = this._movimentiDaEffettuareRepository.GetDocumentiSostituibili(idMovimento);
            this._configurazioneMovimento = this._movimentiDaEffettuareRepository.GetConfigurazioneMovimento(idMovimento);
        }

        public DocumentiIstanzaSostituibili GetDocumentiSostituibili()
        {
            return this._documentiSostituibili;
        }

        public bool CanEnterStep()
        {
            bool permetteSostituzioniDocumentali = this._configurazioneMovimento.PermetteSostituzioneDocumentale;
            
            if (!permetteSostituzioniDocumentali)
            {
                return false;
            }

            return this._documentiSostituibili.DocumentiIntervento.Documenti.Count() > 0 ||
                   this._documentiSostituibili.DocumentiEndo.SelectMany(x => x.Documenti).Count() > 0;
        }

        public bool CanExitStep()
        {
            return true;
        }

        public SostituzioneDocumentale GetDocumentoSostitutivo(OrigineDocumentoSostituibileEnum origineDocumentoSostituibileEnum, int codiceOggettoOrigine)
        {
            var origineDocumento = origineDocumentoSostituibileEnum == OrigineDocumentoSostituibileEnum.Endoprocedimento ? TipoSostituzioneDocumentaleEnum.Endo : TipoSostituzioneDocumentaleEnum.Intervento;

            return this._movimentoDaEffettuare
                        .SostituzioniDocumentali
                        .Where(x => x.TipoDocumento == origineDocumento && x.CodiceOggettoOrigine == codiceOggettoOrigine)
                        .FirstOrDefault();
        }

        public void EffettuaSostituzione(OrigineDocumentoSostituibileEnum origineAllegato, int codiceOggettoOriginale, string nomeFileOriginale, BinaryFile file)
        {
            var esitoCaricamento = this._caricamentoAllegatiService.Carica(file);

            var esitoVerificaFirma = esitoCaricamento.FirmatoDigitalmente;
            var codiceOggetto = esitoCaricamento.CodiceOggetto;
            var origine = origineAllegato == OrigineDocumentoSostituibileEnum.Endoprocedimento ? OrigineDocumentoSostituzioneDocumentale.Endoprocedimento : OrigineDocumentoSostituzioneDocumentale.Intervento;

            if (this.RichiedeFirmaDigitale && !esitoCaricamento.FirmatoDigitalmente)
            {
                throw new InvalidOperationException("Il file caricato deve essere firmato digitalmente");
            }

            var cmd = new SostituisciDocumento(this._movimentoDaEffettuare.Id, origine, codiceOggettoOriginale, nomeFileOriginale, codiceOggetto, file.FileName);

            this._commandSender.Send(cmd);
        }

        public MovimentoDiOrigine GetMovimentoDiOrigine()
        {
            return this._movimentoDiOrigine;
        }



        public void AnnullaSostituzione(int codiceOggettoSostitutivo)
        {
            var cmd = new AnnullaSostituzioneDocumentale(this._movimentoDaEffettuare.Id, codiceOggettoSostitutivo);

            this._commandSender.Send(cmd);
        }
    }
}
