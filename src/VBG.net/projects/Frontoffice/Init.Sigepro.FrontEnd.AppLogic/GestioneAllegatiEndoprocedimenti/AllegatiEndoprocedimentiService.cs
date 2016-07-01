// -----------------------------------------------------------------------
// <copyright file="AllegatiEndoprocedimentiService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti
{
	using System;
	using System.Collections.Generic;
	using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
	using log4net;


	public interface IAllegatiEndoprocedimentiService
	{
		void AggiungiAllegatoAEndo(int idDomanda, int idAllegato, Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.BinaryFile file);
		void AggiungiAllegatoLibero(int idDomanda, int codiceEndo, string descrizione, Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.BinaryFile file, bool verificaFirma);
		void EliminaOggettoUtente(int idDomanda, int idAllegato);
		void SincronizzaAllegati(int idDomanda);
		IEnumerable<InventarioProcedimenti> GetDatiProcedimenti( List<int> codiciEndoSelezionati);
	}

	public class AllegatiEndoprocedimentiService : IAllegatiEndoprocedimentiService
	{
		ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		IAllegatiEndoprocedimentiRepository _endoAllegatiRepository;
		IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
		IAliasSoftwareResolver _aliasSoftwareResolver;
		ILog _log = LogManager.GetLogger(typeof(AllegatiEndoprocedimentiService));

		public AllegatiEndoprocedimentiService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy,
												IAllegatiEndoprocedimentiRepository endoAllegatiRepository,
												IAllegatiDomandaFoRepository allegatiDomandaFoRepository,
												IAliasSoftwareResolver aliasSoftwareResolver)
		{
			_salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			_endoAllegatiRepository = endoAllegatiRepository;
			_allegatiDomandaFoRepository = allegatiDomandaFoRepository;
			_aliasSoftwareResolver = aliasSoftwareResolver;
		}

		public void SincronizzaAllegati(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			new LogicaSincronizzazioneAllegatiEndo(domanda,this).Sincronizza();

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void AggiungiAllegatoLibero(int idDomanda, int codiceEndo, string descrizione, BinaryFile file, bool verificaFirma)
		{
			if (String.IsNullOrEmpty(descrizione))
				throw new ArgumentException("Specificare una descrizione per l'allegato");

			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			try
			{
				var fileSalvato = _allegatiDomandaFoRepository.SalvaAllegato( domanda.DataKey.IdPresentazione, file, verificaFirma);

				domanda.WriteInterface.Documenti.AggiungiDocumentoEndoLibero(codiceEndo, descrizione, fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

				_salvataggioDomandaStrategy.Salva(domanda);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in AggiungiAllegatoLibero: {0}", ex.ToString());

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
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Documenti.EliminaAllegatoADocumentoDaIdDocumento(idDocumento);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void AggiungiAllegatoAEndo(int idDomanda, int idDocumento, BinaryFile file)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			var rigaOggetti = domanda.ReadInterface.Documenti.Endo.GetById(idDocumento);

			var fileSalvato = _allegatiDomandaFoRepository.SalvaAllegato( idDomanda, file, false);

			domanda.WriteInterface.Documenti.AllegaFileADocumento( idDocumento, fileSalvato.CodiceOggetto , fileSalvato.NomeFile , fileSalvato.FirmatoDigitalmente );

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public IEnumerable<InventarioProcedimenti> GetDatiProcedimenti( List<int> codiciEndoSelezionati)
		{
			return this._endoAllegatiRepository.GetDatiProcedimenti(this._aliasSoftwareResolver.AliasComune, codiciEndoSelezionati);
		}
	}
}
