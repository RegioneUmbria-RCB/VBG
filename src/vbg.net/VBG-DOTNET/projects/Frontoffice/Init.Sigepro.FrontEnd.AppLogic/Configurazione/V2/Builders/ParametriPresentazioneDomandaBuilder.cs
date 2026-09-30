using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public class ParametriPresentazioneDomandaBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriPresentazioneDomanda>
    {
        public ParametriPresentazioneDomandaBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository) : base(aliasResolver, configurazioneAreaRiservataRepository)
        {

        }

        public ParametriPresentazioneDomanda Build()
        {
            var cfg = this.GetConfig();

            var richiedentePf = cfg.RichiedenteSoloPersonaFisica;
            var abilitaTemplateDomanda = cfg.AbilitaTemplateDomanda;
            var verificaFirmaSoggettiRiepilogo = cfg.VerificaFirmaSoggettiRiepilogo;

            return new ParametriPresentazioneDomanda(richiedentePf, abilitaTemplateDomanda, verificaFirmaSoggettiRiepilogo);
        }
    }
}
