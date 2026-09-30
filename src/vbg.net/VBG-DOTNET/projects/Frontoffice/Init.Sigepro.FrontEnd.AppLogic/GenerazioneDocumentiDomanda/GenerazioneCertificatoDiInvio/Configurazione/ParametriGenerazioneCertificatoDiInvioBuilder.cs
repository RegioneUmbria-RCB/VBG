using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.Configurazione
{
    internal class ParametriGenerazioneCertificatoDiInvioBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriGenerazioneCertificatoInvio>
    {
        public ParametriGenerazioneCertificatoDiInvioBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }

        public ParametriGenerazioneCertificatoInvio Build()
        {
            var cfg = this.GetConfig();

            return new ParametriGenerazioneCertificatoInvio(cfg.GenerazioneCertificatoDiInvio.NomeFile, cfg.GenerazioneCertificatoDiInvio.DescrizioneFile);
        }
    }
}
