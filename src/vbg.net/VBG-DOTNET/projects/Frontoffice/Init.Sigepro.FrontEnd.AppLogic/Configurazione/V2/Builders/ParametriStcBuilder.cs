using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.SIGePro.Manager.DTO.Configurazione;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriStcBuilder : IConfigurazioneBuilder<ParametriStc>
    {


        private readonly IAliasSoftwareResolver _aliasResolver;
        private readonly IConfigurazioneAreaRiservataRepository _configurazioneAreaRiservataRepository;
        private readonly IAppConfigurationReader _appConfigurationReader;

        public ParametriStcBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository, IAppConfigurationReader appConfigurationReader)
        {
            this._aliasResolver = aliasResolver;
            this._configurazioneAreaRiservataRepository = configurazioneAreaRiservataRepository;
            this._appConfigurationReader = appConfigurationReader;
        }


        #region IBuilder<ParametriStc> Members

        public ParametriStc Build()
        {
            var cfg = this._appConfigurationReader.GetParametriStc();
            var wsCfg = this.GetWsConfig();

            var nodoMittente = new NodoStc(cfg.IdNodoMittente, cfg.IdEnteMittente, cfg.IdSportelloMittente);
            var nodoDestinatario = new NodoStc(cfg.IdNodoDestinatario, cfg.IdEnteDestinatario, cfg.IdSportelloDestinatario);
            var includiTecnicoInSoggettiCollegati = wsCfg.TecnicoInSoggettiCollegati;

            return new ParametriStc(cfg.UrlInvio, cfg.Username, cfg.Password, nodoMittente, nodoDestinatario, includiTecnicoInSoggettiCollegati);
        }

        #endregion

        protected ConfigurazioneAreaRiservataDto GetWsConfig()
        {
            return this._configurazioneAreaRiservataRepository.DatiConfigurazione(this._aliasResolver.AliasComune, this._aliasResolver.Software);
        }

    }
}
