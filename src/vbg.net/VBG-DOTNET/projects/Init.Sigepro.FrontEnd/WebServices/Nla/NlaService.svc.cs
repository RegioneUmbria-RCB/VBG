using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig;
using System;
using System.ServiceModel;
using VBG.Shared.Infrastructure.Caching.Framework;
using VBG.Shared.Infrastructure.ServiceModel.Framework;

namespace Init.Sigepro.FrontEnd.WebServices.Nla
{
    [ServiceBehavior(Namespace = "http://sigepro.init.it/rte/definitions")]
    public class NlaService : Nla
    {

        #region INlaService Members

        public RichiestaPraticheListaNLAResponse1 RichiestaPraticheListaNLA(RichiestaPraticheListaNLARequest1 request)
        {
            throw new NotImplementedException();
        }

        public InserimentoPraticaNLAResponse1 InserimentoPraticaNLA(InserimentoPraticaNLARequest1 request)
        {
            throw new NotImplementedException();
        }

        public RichiestaPraticaNLAResponse1 RichiestaPraticaNLA(RichiestaPraticaNLARequest1 request)
        {
            return new RichiestaPraticaNLAResponse1
            {
                RichiestaPraticaNLAResponse = new RichiestaPraticaNLAResponse
                {
                    dettaglioPratica = new DettaglioPraticaVisuraType
                    {
                        dettaglioPratica = new DettaglioPraticaType
                        {
                            idPratica = request.RichiestaPraticaNLARequest.rifPratica.idPratica,
                            numeroPratica = request.RichiestaPraticaNLARequest.rifPratica.idPratica
                        }
                    }
                }
            };

        }

        public TestNLAResponse1 TestNLA(TestNLARequest1 request)
        {
            return new TestNLAResponse1
            {
                TestNLAResponse = new TestNLAResponse
                {
                    nlaXsdVersion = XsdNlaVersion.V_1_13,
                    typesXsdVersion = XsdTypesVersion.V_1_13
                }
            };
        }

        public InserimentoAttivitaNLAResponse1 InserimentoAttivitaNLA(InserimentoAttivitaNLARequest1 request)
        {
            throw new NotImplementedException();
        }


        public static IOggettiService CreaOggettiService(string idcomune, string software)
        {
            var applicationCache = new ApplicationCache();
            var timedCache = new TimedCache();
            var aliasSoftwareResolver = new StaticAliasSoftwareResolver(idcomune, software);
            var configReader = new WebConfigReader(aliasSoftwareResolver);
            var bindingsFactory = new BindingFactory();
            var sigeproSecurity = new SigeproSecurityProxy(applicationCache, configReader, bindingsFactory);
            var cacheSigeproSecurity = new CacheParametriSigeproSecurity(sigeproSecurity, configReader);

            var sigeproSecurityConfig = new ConfigurazioneImpl<ParametriSigeproSecurity>(new ParametriSigeproSecurityBuilder(cacheSigeproSecurity, configReader));

            var tokenApplicazioneService = new TokenApplicazioneService(
                                                new TokenApplicazioneRepository(sigeproSecurity, sigeproSecurityConfig, timedCache),
                                                aliasSoftwareResolver
                                           );

            var oggettiRepository = new WsOggettiRepository(
                            new OggettiServiceCreator(
                                sigeproSecurityConfig,
                                tokenApplicazioneService,
                                bindingsFactory)
                    );

            return new OggettiService(
                        oggettiRepository,
                        new NullMetadatiOggettoProvider());
        }

        public AllegatoBinarioNLAResponse1 AllegatoBinarioNLA(AllegatoBinarioNLARequest1 request)
        {
            var aliasComune = request.AllegatoBinarioNLARequest.sportelloDestinatario.idEnte;
            var software = request.AllegatoBinarioNLARequest.sportelloDestinatario.idSportello;
            var codiceOggetto = request.AllegatoBinarioNLARequest.riferimentiAllegato.idAllegato;

            var oggettiService = CreaOggettiService(aliasComune, software);

            var file = oggettiService.GetById(Convert.ToInt32(codiceOggetto));

            return new AllegatoBinarioNLAResponse1
            {
                AllegatoBinarioNLAResponse = new AllegatoBinarioNLAResponse
                {
                    binaryData = file.FileContent,
                    fileName = file.FileName,
                    mimeType = file.MimeType
                }
            };
        }

        public AggiungiDocumentiNLAResponse AggiungiDocumentiNLA(AggiungiDocumentiNLARequest1 request)
        {
            throw new NotImplementedException();
        }

        #endregion



    }
}
