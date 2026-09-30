using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    public interface ICodiceAccreditamentoHelper
    {
        string GetCodiceAccreditamento();
    }


    public class CodiceAccreditamentoHelper : ICodiceAccreditamentoHelper
    {
        IConfigurazioneVbgRepository _configurazioneVbgRepository;
        ISoftwareResolver _aliasSoftwareResolver;

        public CodiceAccreditamentoHelper(IConfigurazioneVbgRepository configurazioneVbgRepository, ISoftwareResolver aliasSoftwareResolver)
        {
            this._configurazioneVbgRepository = configurazioneVbgRepository;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string GetCodiceAccreditamento()
        {
            var cfg = _configurazioneVbgRepository.LeggiConfigurazioneComune(this._aliasSoftwareResolver.Software);

            return cfg.CodiceAccreditamento;
        }
    }
}
