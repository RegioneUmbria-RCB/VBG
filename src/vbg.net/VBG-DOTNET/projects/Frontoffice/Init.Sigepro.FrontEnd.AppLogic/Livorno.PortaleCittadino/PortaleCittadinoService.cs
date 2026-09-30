using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino
{
    public class PortaleCittadinoService : IPortaleCittadinoService
    {
        IInterventiAllegatiRepository _interventiAllegatiRepository;
        ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        ILog _log = LogManager.GetLogger(typeof(PortaleCittadinoService));
        SchedeDrupalWsClient _schedeDrupalClient;

        public PortaleCittadinoService(IInterventiAllegatiRepository interventiAllegatiRepository, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, SchedeDrupalWsClient schedeDrupalClient)
        {
            this._interventiAllegatiRepository = interventiAllegatiRepository;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._schedeDrupalClient = schedeDrupalClient;
        }

        public PCScheda GetSchedaDaIdIntervento(int idIntervento)
        {
            var idDrupal = this._interventiAllegatiRepository.GetIdDrupalDaIdIntervento(idIntervento);

            if (String.IsNullOrEmpty(idDrupal))
            {
                this._log.DebugFormat("Nessun id drupal configurato per l'id intervento {0}", idIntervento);
                
                return null;
            }

            return this._schedeDrupalClient.GetSchedaById(Convert.ToInt32(idDrupal));
        }

        public void SincronizzaAllegati(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
            var logicaSincronizzazione = new LogicaSincronizzazioneAllegati();

            logicaSincronizzazione.Sincronizza(domanda, this);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }
    }
}
