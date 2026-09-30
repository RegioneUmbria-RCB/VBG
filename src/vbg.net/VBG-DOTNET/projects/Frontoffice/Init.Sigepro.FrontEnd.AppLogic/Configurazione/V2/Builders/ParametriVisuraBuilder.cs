using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriVisuraBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriVisura>
    {
        private static class Constants
        {
            public const string ConfigKeyName = "limiteRecordsArchivioPratiche";
        }

        private readonly int _limiteRecordsArchivioPratiche = 200;

        public ParametriVisuraBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repository, IAppConfigurationReader appConfigurationReader)
            : base(aliasResolver, repository)
        {
            var cfgValue = appConfigurationReader.GetSetting(Constants.ConfigKeyName);

            if (!String.IsNullOrEmpty(cfgValue))
                this._limiteRecordsArchivioPratiche = int.Parse(cfgValue);
        }


        #region IBuilder<ParametriVisura> Members

        public ParametriVisura Build()
        {
            var cfg = this.GetConfig();

            return new ParametriVisura(
                this._limiteRecordsArchivioPratiche,
                cfg.IntestazioneDettaglioVisura,
                new ParametriVisura.ParametriRicerca(
                        cfg.ParametriRicercaVisuraTecnico.CercaComeTecnico,
                        cfg.ParametriRicercaVisuraTecnico.CercaComeRichiedente,
                        cfg.ParametriRicercaVisuraTecnico.CercaComeAzienda,
                        cfg.ParametriRicercaVisuraTecnico.CercaPartitaIva,
                        cfg.ParametriRicercaVisuraTecnico.CercaSoggettiCollegati
                    ),
                new ParametriVisura.ParametriRicerca(
                        cfg.ParametriRicercaVisuraNonTecnico.CercaComeTecnico,
                        cfg.ParametriRicercaVisuraNonTecnico.CercaComeRichiedente,
                        cfg.ParametriRicercaVisuraNonTecnico.CercaComeAzienda,
                        cfg.ParametriRicercaVisuraNonTecnico.CercaPartitaIva,
                        cfg.ParametriRicercaVisuraNonTecnico.CercaSoggettiCollegati
                    ),
                new ParametriVisura.ParametriRicerca(
                        cfg.ParametriRicercaVisuraFiltroRichiedente.CercaComeTecnico,
                        cfg.ParametriRicercaVisuraFiltroRichiedente.CercaComeRichiedente,
                        cfg.ParametriRicercaVisuraFiltroRichiedente.CercaComeAzienda,
                        cfg.ParametriRicercaVisuraFiltroRichiedente.CercaPartitaIva,
                        cfg.ParametriRicercaVisuraFiltroRichiedente.CercaSoggettiCollegati
                    ),
                new ParametriVisura.ParametriVisuramobile(
                    cfg.ParametriVisuraMobile.UrlServizioProfili,
                    cfg.ParametriVisuraMobile.AliasSportello
                    ),

                new ParametriVisura.ParametriDettaglioPratica(
                        cfg.DettaglioVisura.NascondiStatoIstanza,
                        cfg.DettaglioVisura.NascondiResponsabili,
                        cfg.DettaglioVisura.MostraPosizioneArchivio),

                cfg.ArpaCalabria,
                cfg.NascondiRigeneraRiepilogo
            );
        }

        #endregion
    }
}
