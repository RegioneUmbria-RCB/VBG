using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriRicercaAnagraficheBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriRicercaAnagrafiche>
    {
        private readonly IConfigurazione<ParametriSigeproSecurity> _parametriSigeproSecurity;

        public ParametriRicercaAnagraficheBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo, IConfigurazione<ParametriSigeproSecurity> parametriSigeproSecurity) : base(aliasSoftwareResolver, repo)
        {
            this._parametriSigeproSecurity = parametriSigeproSecurity;
        }


        #region IConfigurazioneBuilder<ParametriRicercaAnagrafiche> Members

        public ParametriRicercaAnagrafiche Build()
        {
            var cfg = this.GetConfig();

            var defaultUrl = $"{this._parametriSigeproSecurity.Parametri.LegacyAspNetBaseUrl}/WebServices/WsSIGeProAnagrafe/WsAnagrafe2.asmx";

            var urlRicercaPf = cfg.UrlWsRicercheAnagrafiche.PersoneFisiche;
            var urlRicercaPg = cfg.UrlWsRicercheAnagrafiche.PersoneGiuridiche;

            if (String.IsNullOrEmpty(urlRicercaPf))
            {
                urlRicercaPf = defaultUrl;
            }

            if (String.IsNullOrEmpty(urlRicercaPg))
            {
                urlRicercaPg = defaultUrl;
            }

            return new ParametriRicercaAnagrafiche(urlRicercaPf, urlRicercaPg);
        }

        #endregion
    }
}
