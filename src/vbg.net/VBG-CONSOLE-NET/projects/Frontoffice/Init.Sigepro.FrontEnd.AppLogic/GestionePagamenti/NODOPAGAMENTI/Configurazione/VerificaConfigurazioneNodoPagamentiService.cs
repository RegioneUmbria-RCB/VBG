using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Configurazione
{
    public class VerificaConfigurazioneNodoPagamentiService : IVerificaConfigurazioneNodoPagamentiService
    {
        private readonly IConfigurazioneNodoPagamentiRepository _repository;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public VerificaConfigurazioneNodoPagamentiService(IConfigurazioneNodoPagamentiRepository repository, ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            _repository = repository;
            _salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        public bool ConfigurazioneValida(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var config = this._repository.GetConfigurazione(domanda.DataKey.Software, domanda.ReadInterface.AltriDati.CodiceComune);

            return config.ConfigurazioneValida;
        }
    }
}
