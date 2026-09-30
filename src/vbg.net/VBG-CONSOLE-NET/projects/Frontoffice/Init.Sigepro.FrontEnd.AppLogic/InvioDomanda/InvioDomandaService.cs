using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{


    public enum TipoInvioEnum
    {
        Firma,
        Sottoscrizione
    }


    public class InvioDomandaService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly WorkflowInvioDomanda _workflowInvioDomanda;
        private readonly ILog m_logger = LogManager.GetLogger(typeof(InvioDomandaService));
        private readonly IConfigurazione<ParametriInvio> _parametriInvioService;
        private readonly IAliasResolver _aliasResolver;

        public InvioDomandaService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, WorkflowInvioDomanda workflowInvioDomanda, IConfigurazione<ParametriInvio> parametriInvioService, IAliasResolver aliasResolver)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._workflowInvioDomanda = workflowInvioDomanda;
            this._parametriInvioService = parametriInvioService;
            this._aliasResolver = aliasResolver;
        }


        public IInvioIstanzaResult Invia(int idPresentazione, string pecDestinatario)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idPresentazione);

            var result = this._workflowInvioDomanda.Processa(domanda, pecDestinatario);

            domanda.ImpostaComePresentata();

            this._salvataggioDomandaStrategy.Salva(domanda);

            return result;
        }

        public void MarcaDomandaComePresentata(int idPresentazione)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idPresentazione);

            domanda.ImpostaComePresentata();

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public bool InviaABackendEntiTerzi()
        {
            return (this._parametriInvioService.Parametri.AliasBackendEntiTerzi == this._aliasResolver.AliasComune);
        }
    }
}
