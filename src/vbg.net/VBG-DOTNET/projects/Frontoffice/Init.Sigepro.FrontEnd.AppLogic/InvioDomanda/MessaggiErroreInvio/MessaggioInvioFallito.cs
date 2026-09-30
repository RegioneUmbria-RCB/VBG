// -----------------------------------------------------------------------
// <copyright file="MessaggioInvioFallito.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
    using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class MessaggioInvioFallito
    {
        IConfigurazione<ParametriInvio> _configurazione;
        IConfigurazioneVbgRepository _configurazioneVbgRepository;
        ISoftwareResolver _aliasSoftwareResolver;

        public MessaggioInvioFallito(IConfigurazione<ParametriInvio> configurazione, IConfigurazioneVbgRepository configurazioneVbgRepository, ISoftwareResolver aliasSoftwareResolver)
        {
            if (configurazione == null)
                throw new System.ArgumentNullException(nameof(configurazione));

            if (configurazioneVbgRepository == null)
                throw new System.ArgumentNullException(nameof(configurazioneVbgRepository));

            if (aliasSoftwareResolver == null)
                throw new System.ArgumentNullException(nameof(aliasSoftwareResolver));
            //Condition.Requires(configurazione, "configurazione").IsNotNull();
            //Condition.Requires(configurazioneVbgRepository, "configurazioneVbgRepository").IsNotNull();
            //Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();

            _configurazione = configurazione;
            _configurazioneVbgRepository = configurazioneVbgRepository;
            _aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string Get(string idDomanda)
        {
            var fmtStr = _configurazione.Parametri.MessaggioInvioFallito;

            var boConfig = _configurazioneVbgRepository.LeggiConfigurazioneComune(_aliasSoftwareResolver.Software);

            var msgInvio = string.Format(fmtStr, boConfig.TELEFONO, boConfig.ORARIO);

            return msgInvio + "<br /><br /><span class=\"fw-bold\">Numero pratica:</span><br />" + idDomanda;
        }
    }
}
