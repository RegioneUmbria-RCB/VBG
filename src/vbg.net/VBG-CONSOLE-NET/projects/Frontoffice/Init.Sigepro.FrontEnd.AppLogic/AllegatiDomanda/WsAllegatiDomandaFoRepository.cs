using System;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Utils;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda
{
	internal class WsAllegatiDomandaFoRepository : WsAreaRiservataRepositoryBase, IAllegatiDomandaFoRepository
	{
		ILog _log = LogManager.GetLogger(typeof(WsDatiDomandaFoRepository));
		IVerificaFirmaDigitaleService _firmaDigitaleService;
		IFirmaDigitaleMetadataService _firmaDigitaleMetadataService;
		IOggettiService _oggettiService;
		IAliasResolver _aliasResolver;

		public WsAllegatiDomandaFoRepository(IAliasResolver aliasresolver, IVerificaFirmaDigitaleService firmaDigitaleService, IFirmaDigitaleMetadataService firmaDigitaleMetadataService, AreaRiservataServiceCreator sc, IOggettiService oggettiService)
			: base(sc)
		{
			this._firmaDigitaleService = firmaDigitaleService;
			this._oggettiService = oggettiService;
			this._aliasResolver = aliasresolver;
			this._firmaDigitaleMetadataService = firmaDigitaleMetadataService;
		}

		/// <summary>
		/// Salva un allegato di una domanda e ne ritorna l'id nel database
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="idDomanda"></param>
		/// <param name="binaryFile"></param>
		/// <returns></returns>
		internal SalvataggioAllegatoResult SalvaAllegato(int idDomanda, BinaryFile binaryFile)
		{
			return SalvaAllegato(idDomanda, binaryFile, false);
		}

		public SalvataggioAllegatoResult SalvaAllegato(int idDomanda, BinaryFile binaryFile, bool richiedeFirmaDigitale)
		{
			var aliasComune = _aliasResolver.AliasComune;
			var esitoVerifica = _firmaDigitaleService.VerificaFirmaDigitale(binaryFile);
			var firmatoDigitalmente = new FirmaValidaSpecification().IsSatisfiedBy(esitoVerifica);

			if (richiedeFirmaDigitale && !firmatoDigitalmente)
				throw new FirmaDigitaleNonValidaException("Si è verificato un errore durante la verifica della firma digitale: " + esitoVerifica.Errore);

			var codiceOggetto = _oggettiService.InserisciOggetto(binaryFile.FileName, binaryFile.MimeType, binaryFile.FileContent);

			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				ws.Service.SalvaAllegatoDomanda(ws.Token, idDomanda, codiceOggetto);

				return new SalvataggioAllegatoResult(codiceOggetto, binaryFile.FileName, firmatoDigitalmente);
			}
		}

		public SalvataggioAllegatoResult SalvaAllegatoConfrontaHash(int idDomanda, BinaryFile file, string hashConfronto)
		{
			var fileInChiaro = _firmaDigitaleMetadataService.GetFileInChiaro(file);

			var bytesToHash = (fileInChiaro == null) ? file.FileContent : fileInChiaro.FileContent;
			var hashFile = new Hasher().ComputeHash(bytesToHash);

			if (hashFile.ToUpper() != hashConfronto.ToUpper())
				throw new ArgumentException("Il file inviato non corrisponde al file che è stato scaricato: è possibile che il file scaricato sia stato modificato. <br />Scaricare, firmare e caricare di nuovo il file per risolvere il problema");

			using (var cp1 = new CodeProfiler("Salvataggio allegato"))
			{
				return SalvaAllegato(idDomanda, file, false);
			}
		}

		/// <summary>
		/// Elimina l'allegato di una domanda
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="idDomanda"></param>
		/// <param name="idAllegato"></param>
		public void EliminaAllegato(int idDomanda, int idAllegato)
		{
			var aliasComune = _aliasResolver.AliasComune;

			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				try
				{
					ws.Service.EliminaAllegatoDomanda(ws.Token, idDomanda, idAllegato);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore durante l'eliminazione dell'allegato {0} della domanda {1} sull'idcomune {2}: {3}", idAllegato, idDomanda, idAllegato, ex.ToString());

					throw;
				}
			}
		}


		/// <summary>
		/// Legge l'allegato di una domanda
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="idDomanda"></param>
		/// <param name="idAllegato"></param>
		/// <returns></returns>
		public BinaryFile LeggiAllegato(int idDomanda, int idAllegato)
		{
			try
			{
				var aliasComune = _aliasResolver.AliasComune;

				using (var ws = _serviceCreator.CreateClient(aliasComune))
				{
					if (!ws.Service.OggettoAppartieneADomanda(ws.Token, idDomanda, idAllegato))
						throw new Exception("L'oggetto con codice " + idAllegato + " non appartiene alla domanda con id " + idDomanda);
				}

				return this._oggettiService.GetById(idAllegato);

			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in LeggiAllegato: {0}", ex.ToString());

				throw;
			}
		}
	}
}
