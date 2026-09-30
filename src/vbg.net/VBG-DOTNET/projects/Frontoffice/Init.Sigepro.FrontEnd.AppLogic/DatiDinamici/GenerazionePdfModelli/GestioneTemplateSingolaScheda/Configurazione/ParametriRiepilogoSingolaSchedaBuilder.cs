using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli.GestioneTemplateSingolaScheda.Configurazione
{
    public class ParametriRiepilogoSingolaSchedaBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriRiepilogoSingolaScheda>
    {
        private readonly IOggettiRepository _oggettiRepository;

        public ParametriRiepilogoSingolaSchedaBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository, IOggettiRepository oggettiRepository) : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
            this._oggettiRepository = oggettiRepository;
        }

        public ParametriRiepilogoSingolaScheda Build()
        {
            var parametriWs = this.GetConfig();

            var oggettoTemplate = parametriWs.CodiceOggettoRiepilogoSchede.HasValue ?
                                    this._oggettiRepository.GetOggetto(parametriWs.CodiceOggettoRiepilogoSchede.Value) :
                                    null;

            return new ParametriRiepilogoSingolaScheda(oggettoTemplate);
        }
    }

}
