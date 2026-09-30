using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Configurazione
{
    public class VerificaConfigurazioneNodoPagamentiService : IVerificaConfigurazioneNodoPagamentiService
    {
        private readonly IConfigurazioneNodoPagamentiRepository _repository;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public VerificaConfigurazioneNodoPagamentiService(IConfigurazioneNodoPagamentiRepository repository, ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            this._repository = repository;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        public bool ConfigurazioneValida(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var config = this._repository.GetConfigurazione(domanda.ReadInterface.AltriDati.CodiceComune);

            return config.ConfigurazioneValida;
        }
    }
}
