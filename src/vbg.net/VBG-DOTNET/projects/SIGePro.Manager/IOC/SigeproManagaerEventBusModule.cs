using Init.SIGePro.Manager.Events;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.EventHandler;
using Vbg.EventBus;

namespace Init.SIGePro.Manager.IOC
{
    public class SigeproManagaerEventBusModule : EventBusModule
    {
        public override void Load(EventSubscribersRegistry registry)
        {
            registry.Add<DomandaFOInCancellazioneEvent, GestioneIntegrazioneLDPDomandaFOEventHandler>();
        }
    }
}
