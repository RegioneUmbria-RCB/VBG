using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento
{
    public class AllegatiInterventoService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(AllegatiInterventoService));
        private readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly ILogicaSincronizzazioneAllegatiIntervento _logicaSincronizzazioneAllegatiIntervento;

        public AllegatiInterventoService(IAllegatiDomandaFoRepository allegatiDomandaFoRepository,
                                         ISalvataggioDomandaStrategy salvataggioDomandaStrategy,
                                         ILogicaSincronizzazioneAllegatiIntervento logicaSincronizzazioneAllegatiIntervento)
        {


            this._allegatiDomandaFoRepository = allegatiDomandaFoRepository ?? throw new ArgumentNullException(nameof(allegatiDomandaFoRepository));
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy ?? throw new ArgumentNullException(nameof(salvataggioDomandaStrategy));
            this._logicaSincronizzazioneAllegatiIntervento = logicaSincronizzazioneAllegatiIntervento ?? throw new ArgumentNullException(nameof(logicaSincronizzazioneAllegatiIntervento));
        }


        public void Sincronizza(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            this._logicaSincronizzazioneAllegatiIntervento.Sincronizza(domanda);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void EliminaOggettoUtente(int idDomanda, int idDocumento)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Documenti.EliminaAllegatoADocumentoDaIdDocumento(idDocumento);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void Salva(int idDomanda, int idDocumento, BinaryFile file)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var documento = domanda.ReadInterface.Documenti.Intervento.GetById(idDocumento);

            var result = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, false);

            domanda.WriteInterface.Documenti.AllegaFileADocumento(idDocumento, result.CodiceOggetto, result.NomeFile, result.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void Salva(int idDomanda, int idDocumento, int codiceOggetto)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var result = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, codiceOggetto);

            domanda.WriteInterface.Documenti.AllegaFileADocumento(idDocumento, result.CodiceOggetto, result.NomeFile, result.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void AggiungiAllegatoLibero(int idDomanda, string descrizione, BinaryFile file, int codiceCategoria = -1, string descrizioneCategoria = "Altri allegati", bool verificaFirma = false)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            try
            {
                var result = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, verificaFirma);

                domanda.WriteInterface.Documenti.AggiungiDocumentoInterventoLibero(descrizione, result.CodiceOggetto, result.NomeFile, codiceCategoria, descrizioneCategoria, result.FirmatoDigitalmente);

                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in AggiungiAllegatoLibero: {0}", ex.ToString());

                throw;
            }
        }

        public void AggiungiAllegatoLibero(int idDomanda, string descrizione, int codiceOggetto, int codiceCategoria = -1, string descrizioneCategoria = "Altri allegati")
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            try
            {
                var result = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, codiceOggetto);

                domanda.WriteInterface.Documenti.AggiungiDocumentoInterventoLibero(descrizione, result.CodiceOggetto, result.NomeFile, codiceCategoria, descrizioneCategoria, result.FirmatoDigitalmente);

                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in AggiungiAllegatoLibero: {0}", ex.ToString());

                throw;
            }
        }

        public DocumentoDomanda GetById(int idDomanda, int idAllegato)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return domanda.ReadInterface.Documenti.Intervento.GetById(idAllegato);
        }


    }
}
