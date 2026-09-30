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
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggettiService;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati;

    public class OggettiService : IOggettiService
    {
        private readonly IOggettiRepository _oggettiRepository;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IMetadatiOggettoProvider _metadatiOggettiProvider;

        public static IOggettiService CreaSuServizio(string idcomune, string software)
        {
            var aliasSoftwareResolver = new StaticAliasSoftwareResolver(idcomune, software);

            var sigeproSecurityConfig = new ConfigurazioneImpl<ParametriSigeproSecurity>(
                                        new ParametriSigeproSecurityBuilder());

            var tokenApplicazioneService = new TokenApplicazioneService(
                                                new TokenApplicazioneRepository(new SigeproSecurityProxy(), sigeproSecurityConfig),
                                                aliasSoftwareResolver
                                           );

            var oggettiRepository = new WsOggettiRepository(
                            new OggettiServiceCreator(
                                sigeproSecurityConfig,
                            tokenApplicazioneService),
                        aliasSoftwareResolver,
                        new AreaRiservataServiceCreator(aliasSoftwareResolver, sigeproSecurityConfig, tokenApplicazioneService)
                    );

            return new OggettiService(aliasSoftwareResolver,
                        oggettiRepository,
                        new NullMetadatiOggettoProvider());
        }

        public OggettiService(IAliasSoftwareResolver aliasSoftwareResolver, IOggettiRepository oggettiRepository, IMetadatiOggettoProvider metadatiOggettiProvider)
        {
            this._oggettiRepository = oggettiRepository;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._metadatiOggettiProvider = metadatiOggettiProvider;
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
            return this._oggettiRepository.InserisciOggetto(nomeFile, mimeType, data, this._metadatiOggettiProvider);
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
