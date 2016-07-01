// -----------------------------------------------------------------------
// <copyright file="WsOggettiRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
	using System;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggettiService;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.Infrastructure.Caching;
	using log4net;

	internal class WsOggettiRepository : WsAreaRiservataRepositoryBase, IOggettiRepository
	{
		ILog _log = LogManager.GetLogger(typeof(WsOggettiRepository));

		OggettiServiceCreator _oggettiServiceCreator;
		IAliasSoftwareResolver _aliasResolver;

		public WsOggettiRepository(OggettiServiceCreator oggettiServiceCreator, IAliasSoftwareResolver aliasResolver, AreaRiservataServiceCreator sc)
			: base(sc)
		{
			this._aliasResolver = aliasResolver;
			this._oggettiServiceCreator = oggettiServiceCreator;
		}


		#region IOggettiService Members

		public string GetNomeFile(int codiceOggetto)
		{
			try
			{
				var aliasComune = this._aliasResolver.AliasComune;

				using (var ws = this._oggettiServiceCreator.CreateClient(aliasComune))
				{
					var req = new OggettiFindNomeRequest
					{
						token = ws.Token,
						id = codiceOggetto.ToString()
					};

					var res = ws.Service.OggettiFindNome(req);

					return res.fileName;
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.GetNomeFile: {0}", ex.ToString());
				throw;
			}
		}

		public BinaryFile GetOggetto(string aliasComune, int codiceOggetto)
		{
			try
			{
				using (var ws = this._oggettiServiceCreator.CreateClient(aliasComune))
				{
					var req = new OggettiFindRequest
					{
						token = ws.Token,
						id = codiceOggetto.ToString()
					};

					var res = ws.Service.OggettiFind(req);

					return new BinaryFile
					{
						FileName = res.fileName,
						MimeType = res.mimeType,
						FileContent = res.binaryData
					};
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.GetOggetto: {0}", ex.ToString());
				throw;
			}
		}

		public BinaryFile GetRisorsaFrontoffice(string aliasComune, string idRisorsa)
		{
			var cacheKey = String.Format("RISORSA_FO_{0}_{1}", aliasComune, idRisorsa);

			if (CacheHelper.KeyExists(cacheKey))
				return CacheHelper.GetEntry<BinaryFile>(cacheKey);

			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				var ogg = ws.Service.GetRisorsaFrontoffice(ws.Token, idRisorsa);
				var file = new BinaryFile(ogg.NomeFile, ogg.MimeType, ogg.Dati);

				return CacheHelper.AddEntry(cacheKey, file);
			}

		}

		public void EliminaOggetto(int codiceOggetto)
		{
			try
			{
				string aliasComune = _aliasResolver.AliasComune;

				using (var ws = this._oggettiServiceCreator.CreateClient(aliasComune))
				{
					var req = new OggettiDeleteRequest
					{
						token = ws.Token,
						id = codiceOggetto.ToString()
					};

					ws.Service.OggettiDelete(req);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.EliminaOggetto: {0}", ex.ToString());
				throw;
			}
		}

		public void AggiornaOggetto(int codiceOggetto, byte[] data)
		{
			try
			{
				string aliasComune = _aliasResolver.AliasComune;

				using (var ws = this._oggettiServiceCreator.CreateClient(aliasComune))
				{
					var req = new OggettiUpdateRequest
					{
						token = ws.Token,
						id = codiceOggetto.ToString(),
						binaryData = data
					};

					ws.Service.OggettiUpdate(req);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.AggiornaOggetto: {0}", ex.ToString());
				throw;
			}
		}

		public int InserisciOggetto(string nomeFile, string mimeType, byte[] data)
		{
			try
			{
				string aliasComune = _aliasResolver.AliasComune;

				using (var ws = this._oggettiServiceCreator.CreateClient(aliasComune))
				{
					var req = new OggettiInsertRequest
					{
						token = ws.Token,
						fileName = nomeFile,
						mimeType = mimeType,
						binaryData = data
					};

					var res = ws.Service.OggettiInsert(req);

					return Convert.ToInt32(res.id);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la chiamata a OggettiServiceJava.InserisciOggetto: {0}", ex.ToString());
				throw;
			}
		}
		
		#endregion
	}
}
