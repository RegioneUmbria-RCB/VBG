using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.SIGePro.Manager.DTO.Common;
using Microsoft.Extensions.Logging;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.RiepilogoDomanda
{
    public class RiepilogoDomandaAllegatoService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IConfigurazione<ParametriWorkflow> _configurazione;
        private readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
        private readonly ILogger<RiepilogoDomandaAllegatoService> _log;
        private readonly GenerazioneRiepilogoDomandaService _generazioneriepilogoService;
        private readonly IInterventiRepository _interventiRepository;
        private readonly IInterventiAllegatiRepository _interventiAllegatiRepository;
        private readonly ModelloDomandaReaderFactory _modelloDomandaReaderFactory;

        public RiepilogoDomandaAllegatoService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IConfigurazione<ParametriWorkflow> configurazione,
            IAllegatiDomandaFoRepository allegatiDomandaFoRepository, ILoggerFactory loggerFactory,
            GenerazioneRiepilogoDomandaService generazioneriepilogoService, IInterventiRepository interventiRepository,
            IInterventiAllegatiRepository interventiAllegatiRepository, ModelloDomandaReaderFactory modelloDomandaReaderFactory)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._configurazione = configurazione;
            this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
            this._log = loggerFactory.CreateLogger<RiepilogoDomandaAllegatoService>();
            this._generazioneriepilogoService = generazioneriepilogoService;
            this._interventiRepository = interventiRepository;
            this._interventiAllegatiRepository = interventiAllegatiRepository;
            this._modelloDomandaReaderFactory = modelloDomandaReaderFactory;
        }

        public int? GetCodiceOggettoDelModelloDiRiepilogo(int idIntervento)
        {
            var allegati = this._interventiAllegatiRepository.GetAllegatiDaIdintervento(idIntervento, AmbitoRicerca.AreaRiservata);

            var riepilogo = allegati.FirstOrDefault(x => x.RiepilogoDomanda);

            return riepilogo == null ? null : riepilogo.CodiceOggettoModello;
        }

        public void ResetStatoRiepilogoDomanda(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            this.EliminaOggettoRiepilogoDomanda(domanda);
            this.EliminaRiepiloghiDomandaInEccesso(domanda);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void EliminaOggettoRiepilogoDomanda(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            this.EliminaOggettoRiepilogoDomanda(domanda);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        private void EliminaOggettoRiepilogoDomanda(DomandaOnline domanda)
        {
            var row = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();

            if (row == null)
                return;

            domanda.WriteInterface.Documenti.EliminaAllegatoADocumentoDaIdDocumento(row.Id);
        }

        public void SalvaOggettoRiepilogo(int idDomanda, BinaryFile file)
        {
            try
            {
                if (file.FileContent.Length == 0)
                    throw new ArgumentException("Il file caricato non è valido");

                var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

                this.SalvaOggettoRiepilogo(domanda, file);

                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                this._log.LogError("Errore durante il salvataggio del riepilogo della domanda: {@ex}", ex);

                throw;
            }
        }

        private void SalvaOggettoRiepilogo(DomandaOnline domanda, BinaryFile file, bool ignoraVerificaHash = false)
        {
            var row = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();
            var verificaHashes = this._configurazione.Parametri.VerificaHashFilesFirmati;
            var hashConfronto = row?.AllegatoDellUtente == null ? string.Empty : row.AllegatoDellUtente.Md5;

            SalvataggioAllegatoResult result = null;

            if (!ignoraVerificaHash && verificaHashes)
            {
                result = this._allegatiDomandaFoRepository.SalvaAllegatoConfrontaHash(domanda.DataKey.IdPresentazione, file, hashConfronto);
            }
            else
            {
                result = this._allegatiDomandaFoRepository.SalvaAllegato(domanda.DataKey.IdPresentazione, file, false);
            }

            domanda.WriteInterface.Documenti.AllegaFileADocumento(row?.Id ?? -1, result.CodiceOggetto, result.NomeFile, result.FirmatoDigitalmente);
        }

        private async Task SalvaOggettoRiepilogoAsync(DomandaOnline domanda, BinaryFile file, bool ignoraVerificaHash = false)
        {
            var row = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();
            var verificaHashes = this._configurazione.Parametri.VerificaHashFilesFirmati;
            var hashConfronto = row.AllegatoDellUtente == null ? string.Empty : row.AllegatoDellUtente.Md5;

            SalvataggioAllegatoResult result = null;

            if (!ignoraVerificaHash && verificaHashes)
            {
                result = this._allegatiDomandaFoRepository.SalvaAllegatoConfrontaHash(domanda.DataKey.IdPresentazione, file, hashConfronto);
            }
            else
            {
                result = await this._allegatiDomandaFoRepository.SalvaAllegatoAsync(domanda.DataKey.IdPresentazione, file, false);
            }

            domanda.WriteInterface.Documenti.AllegaFileADocumento(row.Id, result.CodiceOggetto, result.NomeFile, result.FirmatoDigitalmente);
        }

        public async Task RigeneraRiepilogoDomandaAsync(int idDomanda)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            this.EliminaOggettoRiepilogoDomanda(domanda);

            var reader = await this._modelloDomandaReaderFactory.CreateAsync(idDomanda);

            var riepilogo = await this._generazioneriepilogoService.GeneraRiepilogoDomandaAsync(idDomanda, reader, null, true);

            await this.SalvaOggettoRiepilogoAsync(domanda, riepilogo, true);

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda);
        }

        public void EliminaRiepiloghiDomandaInEccesso(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
            this.EliminaRiepiloghiDomandaInEccesso(domanda);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        private void EliminaRiepiloghiDomandaInEccesso(DomandaOnline domanda)
        {
            var idIntervento = domanda.ReadInterface.AltriDati.Intervento?.Codice;

            if (idIntervento is null)
            {
                return;
            }

            var idDocumentoDaMantenere = this._interventiRepository.GetidDocumentoRiepilogoDaIdIntervento(idIntervento.Value);


            domanda.WriteInterface.Documenti.EliminaRiepiloghiDomandainEccesso(idDocumentoDaMantenere.GetValueOrDefault(-1));


            // Se non sono presenti riepiloghi domanda lo allego
            var riepilogo = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();

            if (idDocumentoDaMantenere.HasValue)
            {
                var allegatiIntervento = this._interventiAllegatiRepository.GetAllegatiDaIdintervento(idIntervento.Value, AmbitoRicerca.AreaRiservata);

                var documento = allegatiIntervento.FirstOrDefault(x => x.Codice == idDocumentoDaMantenere.Value);

                if (documento != null)
                {
                    var codiceDocumento = documento.Codice;
                    var descrizione = documento.Descrizione;
                    var linkInformazioni = documento.LinkInformazioni;
                    var codiceOggetto = documento.CodiceOggettoModello;
                    var richiesto = documento.Richiesto;
                    var richiedeFirma = documento.RichiedeFirma;
                    var tipoDownload = documento.TipoDownload;
                    var ordine = documento.Ordine.GetValueOrDefault(0);
                    var nomeFileModello = documento.NomeFileModello;
                    var codiceCategoria = GestioneDocumentiConstants.CategorieDocumenti.AltriAllegatiCodice;
                    var descrizioneCategoria = GestioneDocumentiConstants.CategorieDocumenti.AltriAllegatiDescrizione;
                    var riepilogoDomanda = documento.RiepilogoDomanda;
                    var note = documento.Note;

                    if (documento.Categoria != null)
                    {
                        codiceCategoria = documento.Categoria.Codice;
                        descrizioneCategoria = documento.Categoria.Descrizione;
                    }

                    domanda.WriteInterface.Documenti.AggiungiOAggiornaDocumentoIntervento(codiceDocumento,
                                                                                    descrizione,
                                                                                    linkInformazioni,
                                                                                    codiceOggetto,
                                                                                    richiesto,
                                                                                    richiedeFirma,
                                                                                    tipoDownload,
                                                                                    ordine,
                                                                                    nomeFileModello,
                                                                                    riepilogoDomanda,
                                                                                    codiceCategoria,
                                                                                    descrizioneCategoria,
                                                                                    note,
                                                                                    documento.DimensioneMassima.GetValueOrDefault(0),
                                                                                    documento.EstensioniAmmesse);
                }


            }
        }
    }
}
