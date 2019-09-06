// -----------------------------------------------------------------------
// <copyright file="OggettiService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.Infrastructure.Caching;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IOggettiService
	{
		BinaryFile GetById(int codiceOggetto);
        int InserisciOggetto(BinaryFile file);
		int InserisciOggetto(string nomeFile, string mimeType, byte[] data);
		string GetNomeFile(int codiceOggetto);
		BinaryFile GetRisorsaFrontoffice(string idRisorsa);
		void AggiornaOggetto(int codiceOggetto, byte[] data);
	}

	public class OggettiService : IOggettiService
	{
		IOggettiRepository _oggettiRepository;
        IAliasResolver _aliasSoftwareResolver;


		public static IOggettiService CreaSuServizio(string idcomune)
		{
			var aliasResolver = new StaticAliasResolver(idcomune);

			var sigeproSecurityConfig = new ConfigurazioneImpl<ParametriSigeproSecurity>(
										new ParametriSigeproSecurityBuilder( aliasResolver ));

			var tokenApplicazioneService = new TokenApplicazioneService(
												new TokenApplicazioneRepository(new SigeproSecurityProxy(), sigeproSecurityConfig),
                                                aliasResolver
                                           );

			return new OggettiService( aliasResolver , 
						new WsOggettiRepository( 
							new OggettiServiceCreator( 
								sigeproSecurityConfig,
							tokenApplicazioneService),
						aliasResolver,
                        new AreaRiservataServiceCreator(aliasResolver, sigeproSecurityConfig, tokenApplicazioneService)
					));
		}

		public OggettiService(IAliasResolver aliasSoftwareResolver, IOggettiRepository oggettiRepository)
		{
			this._oggettiRepository = oggettiRepository;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		#region IOggettiService Members

		public BinaryFile GetById(int codiceOggetto)
		{
			return this._oggettiRepository.GetOggetto(this._aliasSoftwareResolver.AliasComune, codiceOggetto);
		}

        public int InserisciOggetto(BinaryFile file)
        {
            return InserisciOggetto(file.FileName, file.MimeType, file.FileContent);
        }

		public int InserisciOggetto(string nomeFile, string mimeType, byte[] data)
		{
			return this._oggettiRepository.InserisciOggetto(nomeFile, mimeType, data);
		}

		public void AggiornaOggetto(int codiceOggetto, byte[] data)
		{
			this._oggettiRepository.AggiornaOggetto(codiceOggetto, data);
		}

		public string GetNomeFile(int codiceOggetto)
		{
			return this._oggettiRepository.GetNomeFile(codiceOggetto);
		}

		public BinaryFile GetRisorsaFrontoffice(string idRisorsa)
		{
			BinaryFile file = null;

			string cacheKey = String.Format("CACHE_{0}_{1}", this._aliasSoftwareResolver.AliasComune, idRisorsa);

			if (CacheHelper.KeyExists(cacheKey))
				file = CacheHelper.GetEntry<BinaryFile>(cacheKey);
			else
			{
				file = this._oggettiRepository.GetRisorsaFrontoffice(this._aliasSoftwareResolver.AliasComune, idRisorsa);
				CacheHelper.AddEntry(cacheKey, file);
			}

			return file;
		}

		#endregion
	}
}
