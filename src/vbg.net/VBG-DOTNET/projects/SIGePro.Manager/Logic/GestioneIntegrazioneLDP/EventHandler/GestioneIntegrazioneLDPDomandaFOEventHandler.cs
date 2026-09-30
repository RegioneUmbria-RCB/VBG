using Init.SIGePro.Manager.Events;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.EventHandler
{
    class GestioneIntegrazioneLDPDomandaFOEventHandler :
        IEventSubscriber<DomandaFOInCancellazioneEvent>
    {
        private readonly GestioneIntegrazioneLDPService _gestioneIntegrazioneLDPService;

        public GestioneIntegrazioneLDPDomandaFOEventHandler(GestioneIntegrazioneLDPService gestioneIntegrazioneLDPService)
        {
            this._gestioneIntegrazioneLDPService = gestioneIntegrazioneLDPService;
        }

        public void OnEvento(DomandaFOInCancellazioneEvent e)
        {
            this._gestioneIntegrazioneLDPService.AnnullaPrenotazione(e.IdDomanda);
        }
    }
}
