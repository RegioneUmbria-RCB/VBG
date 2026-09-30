namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti
{
    using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti.LogicaSincronizzazione;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
    using Init.SIGePro.Manager.DTO.Endoprocedimenti;
    using log4net;
    using System;
    using System.Collections.Generic;

    public class AllegatiEndoprocedimentiService : IAllegatiEndoprocedimentiService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IAllegatiEndoprocedimentiRepository _endoAllegatiRepository;
        private readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
        private readonly ILog _log = LogManager.GetLogger(typeof(AllegatiEndoprocedimentiService));

        public AllegatiEndoprocedimentiService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy,
                                                IAllegatiEndoprocedimentiRepository endoAllegatiRepository,
                                                IAllegatiDomandaFoRepository allegatiDomandaFoRepository)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._endoAllegatiRepository = endoAllegatiRepository;
            this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
        }

        public void SincronizzaAllegati(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            new LogicaSincronizzazioneAllegatiEndo(domanda, this).Sincronizza();

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public DocumentoDomanda GetById(int idDomanda, int idDocumento)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return domanda.ReadInterface.Documenti.Endo.GetById(idDocumento);
        }

        public void AggiungiAllegatoLibero(int idDomanda, int codiceEndo, string descrizione, BinaryFile file, bool verificaFirma)
        {
            if (string.IsNullOrEmpty(descrizione))
                throw new ArgumentException("Specificare una descrizione per l'allegato");

            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            try
            {
                var fileSalvato = this._allegatiDomandaFoRepository.SalvaAllegato(domanda.DataKey.IdPresentazione, file, verificaFirma);

                domanda.WriteInterface.Documenti.AggiungiDocumentoEndoLibero(codiceEndo, descrizione, fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in AggiungiAllegatoLibero: {0}", ex.ToString());

                throw;
            }
            /*
			 * NELLA PAGINA ERA:
							BinaryFile file = new BinaryFile(fuUploadNuovo.FileName, fuUploadNuovo.PostedFile.ContentType, fuUploadNuovo.FileBytes);

				Master.IstanzeDataSource.OGGETTI.AggiungiAllegatoLibero(IdComune, IdDomanda, Master.IstanzeDataSource.OGGETTI.TIPO_DOCUMENTO_INTERVENTO,
																		 ddlTipoAllegato.Value,
																		 ddlTipoAllegato.Item.SelectedItem.Text,
																		 txtDescrizioneAllegato.Value, file);
			 */
        }

        public void AggiungiAllegatoLibero(int idDomanda, int codiceEndo, string descrizione, int codiceOggetto)
        {
            if (string.IsNullOrEmpty(descrizione))
                throw new ArgumentException("Specificare una descrizione per l'allegato");

            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            try
            {
                var fileSalvato = this._allegatiDomandaFoRepository.SalvaAllegato(domanda.DataKey.IdPresentazione, codiceOggetto);

                domanda.WriteInterface.Documenti.AggiungiDocumentoEndoLibero(codiceEndo, descrizione, fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in AggiungiAllegatoLibero: {0}", ex.ToString());

                throw;
            }
            /*
			 * NELLA PAGINA ERA:
							BinaryFile file = new BinaryFile(fuUploadNuovo.FileName, fuUploadNuovo.PostedFile.ContentType, fuUploadNuovo.FileBytes);

				Master.IstanzeDataSource.OGGETTI.AggiungiAllegatoLibero(IdComune, IdDomanda, Master.IstanzeDataSource.OGGETTI.TIPO_DOCUMENTO_INTERVENTO,
																		 ddlTipoAllegato.Value,
																		 ddlTipoAllegato.Item.SelectedItem.Text,
																		 txtDescrizioneAllegato.Value, file);
			 */
        }


        public void EliminaOggettoUtente(int idDomanda, int idDocumento)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Documenti.EliminaAllegatoADocumentoDaIdDocumento(idDocumento);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void AggiungiAllegatoAEndo(int idDomanda, int idEndo, BinaryFile file)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var fileSalvato = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, false);

            domanda.WriteInterface.Documenti.AllegaFileADocumento(idEndo, fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void AggiungiAllegatoAEndo(int idDomanda, int idEndo, int codiceOggetto)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var fileSalvato = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, codiceOggetto);

            domanda.WriteInterface.Documenti.AllegaFileADocumento(idEndo, fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public IEnumerable<AllegatiPerEndoprocedimentoDto> GetDatiProcedimenti(List<int> codiciEndoSelezionati)
        {
            return this._endoAllegatiRepository.GetAllegatiProcedimenti(codiciEndoSelezionati);
        }
    }
}
